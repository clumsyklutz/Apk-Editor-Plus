package android.support.v4.app

import android.app.AppOpsManager
import android.app.Notification
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import android.os.DeadObjectException
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Message
import android.os.RemoteException
import android.provider.Settings
import android.support.annotation.GuardedBy
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.INotificationSideChannel
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.util.ArrayDeque
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class NotificationManagerCompat {
    public static val ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL"
    private static val CHECK_OP_NO_THROW = "checkOpNoThrow"
    public static val EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel"
    public static val IMPORTANCE_DEFAULT = 3
    public static val IMPORTANCE_HIGH = 4
    public static val IMPORTANCE_LOW = 2
    public static val IMPORTANCE_MAX = 5
    public static val IMPORTANCE_MIN = 1
    public static val IMPORTANCE_NONE = 0
    public static val IMPORTANCE_UNSPECIFIED = -1000
    static val MAX_SIDE_CHANNEL_SDK_VERSION = 19
    private static val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
    private static val SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    private static val SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000
    private static val SIDE_CHANNEL_RETRY_MAX_COUNT = 6
    private static val TAG = "NotifManCompat"

    @GuardedBy("sEnabledNotificationListenersLock")
    private static String sEnabledNotificationListeners

    @GuardedBy("sLock")
    private static SideChannelManager sSideChannelManager
    private final Context mContext
    private final NotificationManager mNotificationManager
    private static val sEnabledNotificationListenersLock = Object()

    @GuardedBy("sEnabledNotificationListenersLock")
    private static Set sEnabledNotificationListenerPackages = HashSet()
    private static val sLock = Object()

    class CancelTask implements Task {
        final Boolean all
        final Int id
        final String packageName
        final String tag

        CancelTask(String str) {
            this.packageName = str
            this.id = 0
            this.tag = null
            this.all = true
        }

        CancelTask(String str, Int i, String str2) {
            this.packageName = str
            this.id = i
            this.tag = str2
            this.all = false
        }

        @Override // android.support.v4.app.NotificationManagerCompat.Task
        fun send(INotificationSideChannel iNotificationSideChannel) {
            if (this.all) {
                iNotificationSideChannel.cancelAll(this.packageName)
            } else {
                iNotificationSideChannel.cancel(this.packageName, this.id, this.tag)
            }
        }

        fun toString() {
            StringBuilder sb = StringBuilder("CancelTask[")
            sb.append("packageName:").append(this.packageName)
            sb.append(", id:").append(this.id)
            sb.append(", tag:").append(this.tag)
            sb.append(", all:").append(this.all)
            sb.append("]")
            return sb.toString()
        }
    }

    class NotifyTask implements Task {
        final Int id
        final Notification notif
        final String packageName
        final String tag

        NotifyTask(String str, Int i, String str2, Notification notification) {
            this.packageName = str
            this.id = i
            this.tag = str2
            this.notif = notification
        }

        @Override // android.support.v4.app.NotificationManagerCompat.Task
        fun send(INotificationSideChannel iNotificationSideChannel) {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif)
        }

        fun toString() {
            StringBuilder sb = StringBuilder("NotifyTask[")
            sb.append("packageName:").append(this.packageName)
            sb.append(", id:").append(this.id)
            sb.append(", tag:").append(this.tag)
            sb.append("]")
            return sb.toString()
        }
    }

    class ServiceConnectedEvent {
        final ComponentName componentName
        final IBinder iBinder

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName
            this.iBinder = iBinder
        }
    }

    class SideChannelManager implements ServiceConnection, Handler.Callback {
        private static val MSG_QUEUE_TASK = 0
        private static val MSG_RETRY_LISTENER_QUEUE = 3
        private static val MSG_SERVICE_CONNECTED = 1
        private static val MSG_SERVICE_DISCONNECTED = 2
        private final Context mContext
        private final Handler mHandler
        private val mRecordMap = HashMap()
        private Set mCachedEnabledPackages = HashSet()
        private val mHandlerThread = HandlerThread("NotificationManagerCompat")

        class ListenerRecord {
            final ComponentName componentName
            INotificationSideChannel service
            Boolean bound = false
            ArrayDeque taskQueue = ArrayDeque()
            Int retryCount = 0

            ListenerRecord(ComponentName componentName) {
                this.componentName = componentName
            }
        }

        SideChannelManager(Context context) {
            this.mContext = context
            this.mHandlerThread.start()
            this.mHandler = Handler(this.mHandlerThread.getLooper(), this)
        }

        private fun ensureServiceBound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                return true
            }
            listenerRecord.bound = this.mContext.bindService(Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName), this, 33)
            if (listenerRecord.bound) {
                listenerRecord.retryCount = 0
            } else {
                Log.w(NotificationManagerCompat.TAG, "Unable to bind to listener " + listenerRecord.componentName)
                this.mContext.unbindService(this)
            }
            return listenerRecord.bound
        }

        private fun ensureServiceUnbound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                this.mContext.unbindService(this)
                listenerRecord.bound = false
            }
            listenerRecord.service = null
        }

        private fun handleQueueTask(Task task) {
            updateListenerMap()
            for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
                listenerRecord.taskQueue.add(task)
                processListenerQueue(listenerRecord)
            }
        }

        private fun handleRetryListenerQueue(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName)
            if (listenerRecord != null) {
                processListenerQueue(listenerRecord)
            }
        }

        private fun handleServiceConnected(ComponentName componentName, IBinder iBinder) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName)
            if (listenerRecord != null) {
                listenerRecord.service = INotificationSideChannel.Stub.asInterface(iBinder)
                listenerRecord.retryCount = 0
                processListenerQueue(listenerRecord)
            }
        }

        private fun handleServiceDisconnected(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName)
            if (listenerRecord != null) {
                ensureServiceUnbound(listenerRecord)
            }
        }

        private fun processListenerQueue(ListenerRecord listenerRecord) {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Processing component " + listenerRecord.componentName + ", " + listenerRecord.taskQueue.size() + " queued tasks")
            }
            if (listenerRecord.taskQueue.isEmpty()) {
                return
            }
            if (!ensureServiceBound(listenerRecord) || listenerRecord.service == null) {
                scheduleListenerRetry(listenerRecord)
                return
            }
            while (true) {
                Task task = (Task) listenerRecord.taskQueue.peek()
                if (task == null) {
                    break
                }
                try {
                    if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                        Log.d(NotificationManagerCompat.TAG, "Sending task " + task)
                    }
                    task.send(listenerRecord.service)
                    listenerRecord.taskQueue.remove()
                } catch (DeadObjectException e) {
                    if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                        Log.d(NotificationManagerCompat.TAG, "Remote service has died: " + listenerRecord.componentName)
                    }
                } catch (RemoteException e2) {
                    Log.w(NotificationManagerCompat.TAG, "RemoteException communicating with " + listenerRecord.componentName, e2)
                }
            }
            if (listenerRecord.taskQueue.isEmpty()) {
                return
            }
            scheduleListenerRetry(listenerRecord)
        }

        private fun scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (this.mHandler.hasMessages(3, listenerRecord.componentName)) {
                return
            }
            listenerRecord.retryCount++
            if (listenerRecord.retryCount > 6) {
                Log.w(NotificationManagerCompat.TAG, "Giving up on delivering " + listenerRecord.taskQueue.size() + " tasks to " + listenerRecord.componentName + " after " + listenerRecord.retryCount + " retries")
                listenerRecord.taskQueue.clear()
                return
            }
            Int i = (1 << (listenerRecord.retryCount - 1)) * 1000
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Scheduling retry for " + i + " ms")
            }
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, listenerRecord.componentName), i)
        }

        private fun updateListenerMap() {
            Set enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.mContext)
            if (enabledListenerPackages.equals(this.mCachedEnabledPackages)) {
                return
            }
            this.mCachedEnabledPackages = enabledListenerPackages
            List<ResolveInfo> listQueryIntentServices = this.mContext.getPackageManager().queryIntentServices(Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0)
            HashSet<ComponentName> hashSet = HashSet()
            for (ResolveInfo resolveInfo : listQueryIntentServices) {
                if (enabledListenerPackages.contains(resolveInfo.serviceInfo.packageName)) {
                    ComponentName componentName = ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
                    if (resolveInfo.serviceInfo.permission != null) {
                        Log.w(NotificationManagerCompat.TAG, "Permission present on component " + componentName + ", not adding listener record.")
                    } else {
                        hashSet.add(componentName)
                    }
                }
            }
            for (ComponentName componentName2 : hashSet) {
                if (!this.mRecordMap.containsKey(componentName2)) {
                    if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                        Log.d(NotificationManagerCompat.TAG, "Adding listener record for " + componentName2)
                    }
                    this.mRecordMap.put(componentName2, ListenerRecord(componentName2))
                }
            }
            Iterator it = this.mRecordMap.entrySet().iterator()
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next()
                if (!hashSet.contains(entry.getKey())) {
                    if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                        Log.d(NotificationManagerCompat.TAG, "Removing listener record for " + entry.getKey())
                    }
                    ensureServiceUnbound((ListenerRecord) entry.getValue())
                    it.remove()
                }
            }
        }

        @Override // android.os.Handler.Callback
        fun handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    handleQueueTask((Task) message.obj)
                    break
                case 1:
                    ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj
                    handleServiceConnected(serviceConnectedEvent.componentName, serviceConnectedEvent.iBinder)
                    break
                case 2:
                    handleServiceDisconnected((ComponentName) message.obj)
                    break
                case 3:
                    handleRetryListenerQueue((ComponentName) message.obj)
                    break
            }
            return true
        }

        @Override // android.content.ServiceConnection
        fun onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Connected to service " + componentName)
            }
            this.mHandler.obtainMessage(1, ServiceConnectedEvent(componentName, iBinder)).sendToTarget()
        }

        @Override // android.content.ServiceConnection
        fun onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                Log.d(NotificationManagerCompat.TAG, "Disconnected from service " + componentName)
            }
            this.mHandler.obtainMessage(2, componentName).sendToTarget()
        }

        fun queueTask(Task task) {
            this.mHandler.obtainMessage(0, task).sendToTarget()
        }
    }

    interface Task {
        Unit send(INotificationSideChannel iNotificationSideChannel)
    }

    private constructor(Context context) {
        this.mContext = context
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification")
    }

    @NonNull
    fun from(@NonNull Context context) {
        return NotificationManagerCompat(context)
    }

    @NonNull
    fun getEnabledListenerPackages(@NonNull Context context) {
        Set set
        String string = Settings.Secure.getString(context.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS)
        synchronized (sEnabledNotificationListenersLock) {
            if (string != null) {
                if (!string.equals(sEnabledNotificationListeners)) {
                    Array<String> strArrSplit = string.split(":", -1)
                    HashSet hashSet = HashSet(strArrSplit.length)
                    for (String str : strArrSplit) {
                        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str)
                        if (componentNameUnflattenFromString != null) {
                            hashSet.add(componentNameUnflattenFromString.getPackageName())
                        }
                    }
                    sEnabledNotificationListenerPackages = hashSet
                    sEnabledNotificationListeners = string
                }
                set = sEnabledNotificationListenerPackages
            } else {
                set = sEnabledNotificationListenerPackages
            }
        }
        return set
    }

    private fun pushSideChannelQueue(Task task) {
        synchronized (sLock) {
            if (sSideChannelManager == null) {
                sSideChannelManager = SideChannelManager(this.mContext.getApplicationContext())
            }
            sSideChannelManager.queueTask(task)
        }
    }

    private fun useSideChannelForNotification(Notification notification) {
        Bundle extras = NotificationCompat.getExtras(notification)
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL)
    }

    public final Boolean areNotificationsEnabled() throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.areNotificationsEnabled()
        }
        if (Build.VERSION.SDK_INT < 19) {
            return true
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService("appops")
        ApplicationInfo applicationInfo = this.mContext.getApplicationInfo()
        String packageName = this.mContext.getApplicationContext().getPackageName()
        Int i = applicationInfo.uid
        try {
            Class<?> cls = Class.forName(AppOpsManager.class.getName())
            return ((Integer) cls.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) cls.getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)).intValue()), Integer.valueOf(i), packageName)).intValue() == 0
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException e) {
            return true
        }
    }

    public final Unit cancel(Int i) {
        cancel(null, i)
    }

    public final Unit cancel(@Nullable String str, Int i) {
        this.mNotificationManager.cancel(str, i)
        if (Build.VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(CancelTask(this.mContext.getPackageName(), i, str))
        }
    }

    public final Unit cancelAll() {
        this.mNotificationManager.cancelAll()
        if (Build.VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(CancelTask(this.mContext.getPackageName()))
        }
    }

    public final Int getImportance() {
        return Build.VERSION.SDK_INT >= 24 ? this.mNotificationManager.getImportance() : IMPORTANCE_UNSPECIFIED
    }

    public final Unit notify(Int i, @NonNull Notification notification) {
        notify(null, i, notification)
    }

    public final Unit notify(@Nullable String str, Int i, @NonNull Notification notification) {
        if (!useSideChannelForNotification(notification)) {
            this.mNotificationManager.notify(str, i, notification)
        } else {
            pushSideChannelQueue(NotifyTask(this.mContext.getPackageName(), i, str, notification))
            this.mNotificationManager.cancel(str, i)
        }
    }
}
