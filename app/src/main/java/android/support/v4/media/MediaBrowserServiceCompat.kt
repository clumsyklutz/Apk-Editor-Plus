package android.support.v4.media

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.Parcel
import android.os.Parcelable
import android.os.RemoteException
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.app.BundleCompat
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompatApi21
import android.support.v4.media.MediaBrowserServiceCompatApi23
import android.support.v4.media.MediaBrowserServiceCompatApi26
import android.support.v4.media.session.IMediaSession
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.os.ResultReceiver
import android.support.v4.util.ArrayMap
import androidx.core.util.Pair
import android.text.TextUtils
import android.util.Log
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Iterator
import java.util.List

abstract class MediaBrowserServiceCompat extends Service {
    private static val EPSILON = 1.0E-5f

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val KEY_MEDIA_ITEM = "media_item"

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val KEY_SEARCH_RESULTS = "search_results"
    static val RESULT_ERROR = -1
    static val RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2
    static val RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4
    static val RESULT_FLAG_OPTION_NOT_HANDLED = 1
    static val RESULT_OK = 0
    static val RESULT_PROGRESS_UPDATE = 1
    public static val SERVICE_INTERFACE = "android.media.browse.MediaBrowserService"
    ConnectionRecord mCurConnection
    private MediaBrowserServiceImpl mImpl
    MediaSessionCompat.Token mSession
    static val TAG = "MBServiceCompat"
    static val DEBUG = Log.isLoggable(TAG, 3)
    val mConnections = ArrayMap()
    val mHandler = ServiceHandler()

    class BrowserRoot {
        public static val EXTRA_OFFLINE = "android.service.media.extra.OFFLINE"
        public static val EXTRA_RECENT = "android.service.media.extra.RECENT"
        public static val EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED"

        @Deprecated
        public static val EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS"
        private final Bundle mExtras
        private final String mRootId

        constructor(@NonNull String str, @Nullable Bundle bundle) {
            if (str == null) {
                throw IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.")
            }
            this.mRootId = str
            this.mExtras = bundle
        }

        public final Bundle getExtras() {
            return this.mExtras
        }

        public final String getRootId() {
            return this.mRootId
        }
    }

    class ConnectionRecord implements IBinder.DeathRecipient {
        ServiceCallbacks callbacks
        String pkg
        BrowserRoot root
        Bundle rootHints
        HashMap subscriptions = HashMap()

        ConnectionRecord() {
        }

        @Override // android.os.IBinder.DeathRecipient
        fun binderDied() {
            MediaBrowserServiceCompat.this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ConnectionRecord.1
                @Override // java.lang.Runnable
                fun run() {
                    MediaBrowserServiceCompat.this.mConnections.remove(ConnectionRecord.this.callbacks.asBinder())
                }
            })
        }
    }

    interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints()

        Unit notifyChildrenChanged(String str, Bundle bundle)

        IBinder onBind(Intent intent)

        Unit onCreate()

        Unit setSessionToken(MediaSessionCompat.Token token)
    }

    @RequiresApi(21)
    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl, MediaBrowserServiceCompatApi21.ServiceCompatProxy {
        Messenger mMessenger
        val mRootExtrasList = ArrayList()
        Object mServiceObj

        MediaBrowserServiceImplApi21() {
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null
            }
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods")
            }
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints != null) {
                return Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints)
            }
            return null
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun notifyChildrenChanged(String str, Bundle bundle) {
            notifyChildrenChangedForFramework(str, bundle)
            notifyChildrenChangedForCompat(str, bundle)
        }

        Unit notifyChildrenChangedForCompat(final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.3
                @Override // java.lang.Runnable
                fun run() {
                    Iterator it = MediaBrowserServiceCompat.this.mConnections.keySet().iterator()
                    while (it.hasNext()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get((IBinder) it.next())
                        List<Pair> list = (List) connectionRecord.subscriptions.get(str)
                        if (list != null) {
                            for (Pair pair : list) {
                                if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle) pair.second)) {
                                    MediaBrowserServiceCompat.this.performLoadChildren(str, connectionRecord, (Bundle) pair.second)
                                }
                            }
                        }
                    }
                }
            })
        }

        Unit notifyChildrenChangedForFramework(String str, Bundle bundle) {
            MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, str)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onBind(Intent intent) {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService(MediaBrowserServiceCompat.this, this)
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy
        public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String str, Int i, Bundle bundle) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Bundle extras
            if (bundle == null || bundle.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0) {
                extras = null
            } else {
                bundle.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION)
                this.mMessenger = Messenger(MediaBrowserServiceCompat.this.mHandler)
                Bundle bundle2 = Bundle()
                bundle2.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2)
                BundleCompat.putBinder(bundle2, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder())
                if (MediaBrowserServiceCompat.this.mSession != null) {
                    IMediaSession extraBinder = MediaBrowserServiceCompat.this.mSession.getExtraBinder()
                    BundleCompat.putBinder(bundle2, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder == null ? null : extraBinder.asBinder())
                    extras = bundle2
                } else {
                    this.mRootExtrasList.add(bundle2)
                    extras = bundle2
                }
            }
            BrowserRoot browserRootOnGetRoot = MediaBrowserServiceCompat.this.onGetRoot(str, i, bundle)
            if (browserRootOnGetRoot == null) {
                return null
            }
            if (extras == null) {
                extras = browserRootOnGetRoot.getExtras()
            } else if (browserRootOnGetRoot.getExtras() != null) {
                extras.putAll(browserRootOnGetRoot.getExtras())
            }
            return new MediaBrowserServiceCompatApi21.BrowserRoot(browserRootOnGetRoot.getRootId(), extras)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy
        fun onLoadChildren(String str, final MediaBrowserServiceCompatApi21.ResultWrapper resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.2
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun detach() {
                    resultWrapper.detach()
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun onResultSent(List list) {
                    ArrayList arrayList = null
                    if (list != null) {
                        ArrayList arrayList2 = ArrayList()
                        Iterator it = list.iterator()
                        while (it.hasNext()) {
                            MediaBrowserCompat.MediaItem mediaItem = (MediaBrowserCompat.MediaItem) it.next()
                            Parcel parcelObtain = Parcel.obtain()
                            mediaItem.writeToParcel(parcelObtain, 0)
                            arrayList2.add(parcelObtain)
                        }
                        arrayList = arrayList2
                    }
                    resultWrapper.sendResult(arrayList)
                }
            })
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.1
                @Override // java.lang.Runnable
                fun run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                    if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                        IMediaSession extraBinder = token.getExtraBinder()
                        if (extraBinder != null) {
                            Iterator it = MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator()
                            while (it.hasNext()) {
                                BundleCompat.putBinder((Bundle) it.next(), MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder())
                            }
                        }
                        MediaBrowserServiceImplApi21.this.mRootExtrasList.clear()
                    }
                    MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceImplApi21.this.mServiceObj, token.getToken())
                }
            })
        }
    }

    @RequiresApi(23)
    class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        MediaBrowserServiceImplApi23() {
            super()
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService(MediaBrowserServiceCompat.this, this)
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi23.ServiceCompatProxy
        fun onLoadItem(String str, final MediaBrowserServiceCompatApi21.ResultWrapper resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadItem(str, Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23.1
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun detach() {
                    resultWrapper.detach()
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                    if (mediaItem == null) {
                        resultWrapper.sendResult(null)
                        return
                    }
                    Parcel parcelObtain = Parcel.obtain()
                    mediaItem.writeToParcel(parcelObtain, 0)
                    resultWrapper.sendResult(parcelObtain)
                }
            })
        }
    }

    @RequiresApi(26)
    class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {
        MediaBrowserServiceImplApi26() {
            super()
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                return MediaBrowserServiceCompatApi26.getBrowserRootHints(this.mServiceObj)
            }
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null
            }
            return Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
        Unit notifyChildrenChangedForFramework(String str, Bundle bundle) {
            if (bundle != null) {
                MediaBrowserServiceCompatApi26.notifyChildrenChanged(this.mServiceObj, str, bundle)
            } else {
                super.notifyChildrenChangedForFramework(str, bundle)
            }
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23, android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi26.createService(MediaBrowserServiceCompat.this, this)
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi26.ServiceCompatProxy
        fun onLoadChildren(String str, final MediaBrowserServiceCompatApi26.ResultWrapper resultWrapper, Bundle bundle) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi26.1
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun detach() {
                    resultWrapper.detach()
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
                fun onResultSent(List list) throws IllegalAccessException, IllegalArgumentException {
                    ArrayList arrayList = null
                    if (list != null) {
                        ArrayList arrayList2 = ArrayList()
                        Iterator it = list.iterator()
                        while (it.hasNext()) {
                            MediaBrowserCompat.MediaItem mediaItem = (MediaBrowserCompat.MediaItem) it.next()
                            Parcel parcelObtain = Parcel.obtain()
                            mediaItem.writeToParcel(parcelObtain, 0)
                            arrayList2.add(parcelObtain)
                        }
                        arrayList = arrayList2
                    }
                    resultWrapper.sendResult(arrayList, getFlags())
                }
            }, bundle)
        }
    }

    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger

        MediaBrowserServiceImplBase() {
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods")
            }
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null
            }
            return Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun notifyChildrenChanged(@NonNull final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.2
                @Override // java.lang.Runnable
                fun run() {
                    Iterator it = MediaBrowserServiceCompat.this.mConnections.keySet().iterator()
                    while (it.hasNext()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get((IBinder) it.next())
                        List<Pair> list = (List) connectionRecord.subscriptions.get(str)
                        if (list != null) {
                            for (Pair pair : list) {
                                if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle) pair.second)) {
                                    MediaBrowserServiceCompat.this.performLoadChildren(str, connectionRecord, (Bundle) pair.second)
                                }
                            }
                        }
                    }
                }
            })
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder()
            }
            return null
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun onCreate() {
            this.mMessenger = Messenger(MediaBrowserServiceCompat.this.mHandler)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        fun setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.1
                @Override // java.lang.Runnable
                fun run() {
                    Iterator it = MediaBrowserServiceCompat.this.mConnections.values().iterator()
                    while (it.hasNext()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) it.next()
                        try {
                            connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras())
                        } catch (RemoteException e) {
                            Log.w(MediaBrowserServiceCompat.TAG, "Connection for " + connectionRecord.pkg + " is no longer valid.")
                            it.remove()
                        }
                    }
                }
            })
        }
    }

    class Result {
        private final Object mDebug
        private Boolean mDetachCalled
        private Int mFlags
        private Boolean mSendErrorCalled
        private Boolean mSendProgressUpdateCalled
        private Boolean mSendResultCalled

        Result(Object obj) {
            this.mDebug = obj
        }

        private fun checkExtraFields(Bundle bundle) {
            if (bundle != null && bundle.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                Float f = bundle.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)
                if (f < -1.0E-5f || f > 1.00001f) {
                    throw IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a Float number within [0.0, 1.0].")
                }
            }
        }

        fun detach() {
            if (this.mDetachCalled) {
                throw IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug)
            }
            if (this.mSendResultCalled) {
                throw IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug)
            }
            if (this.mSendErrorCalled) {
                throw IllegalStateException("detach() called when sendError() had already been called for: " + this.mDebug)
            }
            this.mDetachCalled = true
        }

        Int getFlags() {
            return this.mFlags
        }

        Boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled
        }

        Unit onErrorSent(Bundle bundle) {
            throw UnsupportedOperationException("It is not supported to send an error for " + this.mDebug)
        }

        Unit onProgressUpdateSent(Bundle bundle) {
            throw UnsupportedOperationException("It is not supported to send an interim update for " + this.mDebug)
        }

        Unit onResultSent(Object obj) {
        }

        fun sendError(Bundle bundle) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.mDebug)
            }
            this.mSendErrorCalled = true
            onErrorSent(bundle)
        }

        fun sendProgressUpdate(Bundle bundle) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw IllegalStateException("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: " + this.mDebug)
            }
            checkExtraFields(bundle)
            this.mSendProgressUpdateCalled = true
            onProgressUpdateSent(bundle)
        }

        fun sendResult(Object obj) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.mDebug)
            }
            this.mSendResultCalled = true
            onResultSent(obj)
        }

        Unit setFlags(Int i) {
            this.mFlags = i
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @interface ResultFlags {
    }

    class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        fun addSubscription(final String str, final IBinder iBinder, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.3
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder())
                    if (connectionRecord == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "addSubscription for callback that isn't registered id=" + str)
                    } else {
                        MediaBrowserServiceCompat.this.addSubscription(str, connectionRecord, iBinder, bundle)
                    }
                }
            })
        }

        fun connect(final String str, final Int i, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            if (!MediaBrowserServiceCompat.this.isValidPackage(str, i)) {
                throw IllegalArgumentException("Package/uid mismatch: uid=" + i + " package=" + str)
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.1
                @Override // java.lang.Runnable
                fun run() throws RemoteException {
                    IBinder iBinderAsBinder = serviceCallbacks.asBinder()
                    MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder)
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.ConnectionRecord()
                    connectionRecord.pkg = str
                    connectionRecord.rootHints = bundle
                    connectionRecord.callbacks = serviceCallbacks
                    connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(str, i, bundle)
                    if (connectionRecord.root == null) {
                        Log.i(MediaBrowserServiceCompat.TAG, "No root for client " + str + " from service " + getClass().getName())
                        try {
                            serviceCallbacks.onConnectFailed()
                            return
                        } catch (RemoteException e) {
                            Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str)
                            return
                        }
                    }
                    try {
                        MediaBrowserServiceCompat.this.mConnections.put(iBinderAsBinder, connectionRecord)
                        iBinderAsBinder.linkToDeath(connectionRecord, 0)
                        if (MediaBrowserServiceCompat.this.mSession != null) {
                            serviceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras())
                        }
                    } catch (RemoteException e2) {
                        Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str)
                        MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder)
                    }
                }
            })
        }

        fun disconnect(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.2
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.remove(serviceCallbacks.asBinder())
                    if (connectionRecord != null) {
                        connectionRecord.callbacks.asBinder().unlinkToDeath(connectionRecord, 0)
                    }
                }
            })
        }

        fun getMediaItem(final String str, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.5
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder())
                    if (connectionRecord == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "getMediaItem for callback that isn't registered id=" + str)
                    } else {
                        MediaBrowserServiceCompat.this.performLoadItem(str, connectionRecord, resultReceiver)
                    }
                }
            })
        }

        fun registerCallbacks(final ServiceCallbacks serviceCallbacks, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.6
                @Override // java.lang.Runnable
                fun run() throws RemoteException {
                    IBinder iBinderAsBinder = serviceCallbacks.asBinder()
                    MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder)
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.ConnectionRecord()
                    connectionRecord.callbacks = serviceCallbacks
                    connectionRecord.rootHints = bundle
                    MediaBrowserServiceCompat.this.mConnections.put(iBinderAsBinder, connectionRecord)
                    try {
                        iBinderAsBinder.linkToDeath(connectionRecord, 0)
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "IBinder is already dead.")
                    }
                }
            })
        }

        fun removeSubscription(final String str, final IBinder iBinder, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.4
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder())
                    if (connectionRecord == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription for callback that isn't registered id=" + str)
                    } else {
                        if (MediaBrowserServiceCompat.this.removeSubscription(str, connectionRecord, iBinder)) {
                            return
                        }
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription called for " + str + " which is not subscribed")
                    }
                }
            })
        }

        fun search(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.8
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder())
                    if (connectionRecord == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "search for callback that isn't registered query=" + str)
                    } else {
                        MediaBrowserServiceCompat.this.performSearch(str, bundle, connectionRecord, resultReceiver)
                    }
                }
            })
        }

        fun sendCustomAction(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty(str) || resultReceiver == null) {
                return
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.9
                @Override // java.lang.Runnable
                fun run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(serviceCallbacks.asBinder())
                    if (connectionRecord == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "sendCustomAction for callback that isn't registered action=" + str + ", extras=" + bundle)
                    } else {
                        MediaBrowserServiceCompat.this.performCustomAction(str, bundle, connectionRecord, resultReceiver)
                    }
                }
            })
        }

        fun unregisterCallbacks(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.7
                @Override // java.lang.Runnable
                fun run() {
                    IBinder iBinderAsBinder = serviceCallbacks.asBinder()
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder)
                    if (connectionRecord != null) {
                        iBinderAsBinder.unlinkToDeath(connectionRecord, 0)
                    }
                }
            })
        }
    }

    interface ServiceCallbacks {
        IBinder asBinder()

        Unit onConnect(String str, MediaSessionCompat.Token token, Bundle bundle)

        Unit onConnectFailed()

        Unit onLoadChildren(String str, List list, Bundle bundle)
    }

    class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks

        ServiceCallbacksCompat(Messenger messenger) {
            this.mCallbacks = messenger
        }

        private fun sendRequest(Int i, Bundle bundle) throws RemoteException {
            Message messageObtain = Message.obtain()
            messageObtain.what = i
            messageObtain.arg1 = 2
            messageObtain.setData(bundle)
            this.mCallbacks.send(messageObtain)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        fun asBinder() {
            return this.mCallbacks.getBinder()
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        fun onConnect(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2)
            Bundle bundle2 = Bundle()
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str)
            bundle2.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, token)
            bundle2.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, bundle)
            sendRequest(1, bundle2)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        fun onConnectFailed() throws RemoteException {
            sendRequest(2, null)
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        fun onLoadChildren(String str, List list, Bundle bundle) throws RemoteException {
            Bundle bundle2 = Bundle()
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str)
            bundle2.putBundle(MediaBrowserProtocol.DATA_OPTIONS, bundle)
            if (list != null) {
                bundle2.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list is ArrayList ? (ArrayList) list : new ArrayList<>(list))
            }
            sendRequest(3, bundle2)
        }
    }

    final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl

        ServiceHandler() {
            this.mServiceBinderImpl = MediaBrowserServiceCompat.this.ServiceBinderImpl()
        }

        @Override // android.os.Handler
        public final Unit handleMessage(Message message) {
            Bundle data = message.getData()
            switch (message.what) {
                case 1:
                    this.mServiceBinderImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS), ServiceCallbacksCompat(message.replyTo))
                    break
                case 2:
                    this.mServiceBinderImpl.disconnect(ServiceCallbacksCompat(message.replyTo))
                    break
                case 3:
                    this.mServiceBinderImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS), ServiceCallbacksCompat(message.replyTo))
                    break
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), ServiceCallbacksCompat(message.replyTo))
                    break
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), ServiceCallbacksCompat(message.replyTo))
                    break
                case 6:
                    this.mServiceBinderImpl.registerCallbacks(ServiceCallbacksCompat(message.replyTo), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS))
                    break
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(ServiceCallbacksCompat(message.replyTo))
                    break
                case 8:
                    this.mServiceBinderImpl.search(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), ServiceCallbacksCompat(message.replyTo))
                    break
                case 9:
                    this.mServiceBinderImpl.sendCustomAction(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), ServiceCallbacksCompat(message.replyTo))
                    break
                default:
                    Log.w(MediaBrowserServiceCompat.TAG, "Unhandled message: " + message + "\n  Service version: 2\n  Client version: " + message.arg1)
                    break
            }
        }

        public final Unit postOrRun(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run()
            } else {
                post(runnable)
            }
        }

        @Override // android.os.Handler
        public final Boolean sendMessageAtTime(Message message, Long j) {
            Bundle data = message.getData()
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader())
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid())
            return super.sendMessageAtTime(message, j)
        }
    }

    Unit addSubscription(String str, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        List list = (List) connectionRecord.subscriptions.get(str)
        List<Pair> arrayList = list == null ? ArrayList() : list
        for (Pair pair : arrayList) {
            if (iBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle, (Bundle) pair.second)) {
                return
            }
        }
        arrayList.add(Pair(iBinder, bundle))
        connectionRecord.subscriptions.put(str, arrayList)
        performLoadChildren(str, connectionRecord, bundle)
    }

    List applyOptions(List list, Bundle bundle) {
        if (list == null) {
            return null
        }
        Int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1)
        Int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)
        if (i == -1 && i2 == -1) {
            return list
        }
        Int i3 = i2 * i
        Int size = i3 + i2
        if (i < 0 || i2 <= 0 || i3 >= list.size()) {
            return Collections.EMPTY_LIST
        }
        if (size > list.size()) {
            size = list.size()
        }
        return list.subList(i3, size)
    }

    @Override // android.app.Service
    fun dump(FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints()
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.mSession
    }

    Boolean isValidPackage(String str, Int i) {
        if (str == null) {
            return false
        }
        for (String str2 : getPackageManager().getPackagesForUid(i)) {
            if (str2.equals(str)) {
                return true
            }
        }
        return false
    }

    fun notifyChildrenChanged(@NonNull String str) {
        if (str == null) {
            throw IllegalArgumentException("parentId cannot be null in notifyChildrenChanged")
        }
        this.mImpl.notifyChildrenChanged(str, null)
    }

    fun notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw IllegalArgumentException("parentId cannot be null in notifyChildrenChanged")
        }
        if (bundle == null) {
            throw IllegalArgumentException("options cannot be null in notifyChildrenChanged")
        }
        this.mImpl.notifyChildrenChanged(str, bundle)
    }

    @Override // android.app.Service
    fun onBind(Intent intent) {
        return this.mImpl.onBind(intent)
    }

    @Override // android.app.Service
    fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = MediaBrowserServiceImplApi26()
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = MediaBrowserServiceImplApi23()
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = MediaBrowserServiceImplApi21()
        } else {
            this.mImpl = MediaBrowserServiceImplBase()
        }
        this.mImpl.onCreate()
    }

    fun onCustomAction(@NonNull String str, Bundle bundle, @NonNull Result result) {
        result.sendError(null)
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, Int i, @Nullable Bundle bundle)

    public abstract Unit onLoadChildren(@NonNull String str, @NonNull Result result)

    fun onLoadChildren(@NonNull String str, @NonNull Result result, @NonNull Bundle bundle) {
        result.setFlags(1)
        onLoadChildren(str, result)
    }

    fun onLoadItem(String str, @NonNull Result result) {
        result.setFlags(2)
        result.sendResult(null)
    }

    fun onSearch(@NonNull String str, Bundle bundle, @NonNull Result result) {
        result.setFlags(4)
        result.sendResult(null)
    }

    Unit performCustomAction(String str, Bundle bundle, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        Result result = Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.4
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            Unit onErrorSent(Bundle bundle2) {
                resultReceiver.send(-1, bundle2)
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            Unit onProgressUpdateSent(Bundle bundle2) {
                resultReceiver.send(1, bundle2)
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            fun onResultSent(Bundle bundle2) {
                resultReceiver.send(0, bundle2)
            }
        }
        this.mCurConnection = connectionRecord
        onCustomAction(str, bundle, result)
        this.mCurConnection = null
        if (!result.isDone()) {
            throw IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle)
        }
    }

    Unit performLoadChildren(final String str, final ConnectionRecord connectionRecord, final Bundle bundle) {
        Result result = Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            fun onResultSent(List list) {
                if (MediaBrowserServiceCompat.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                    if (MediaBrowserServiceCompat.DEBUG) {
                        Log.d(MediaBrowserServiceCompat.TAG, "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + connectionRecord.pkg + " id=" + str)
                    }
                } else {
                    if ((getFlags() & 1) != 0) {
                        list = MediaBrowserServiceCompat.this.applyOptions(list, bundle)
                    }
                    try {
                        connectionRecord.callbacks.onLoadChildren(str, list, bundle)
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "Calling onLoadChildren() failed for id=" + str + " package=" + connectionRecord.pkg)
                    }
                }
            }
        }
        this.mCurConnection = connectionRecord
        if (bundle == null) {
            onLoadChildren(str, result)
        } else {
            onLoadChildren(str, result, bundle)
        }
        this.mCurConnection = null
        if (!result.isDone()) {
            throw IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connectionRecord.pkg + " id=" + str)
        }
    }

    Unit performLoadItem(String str, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        Result result = Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            fun onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                if ((getFlags() & 2) != 0) {
                    resultReceiver.send(-1, null)
                    return
                }
                Bundle bundle = Bundle()
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem)
                resultReceiver.send(0, bundle)
            }
        }
        this.mCurConnection = connectionRecord
        onLoadItem(str, result)
        this.mCurConnection = null
        if (!result.isDone()) {
            throw IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str)
        }
    }

    Unit performSearch(String str, Bundle bundle, ConnectionRecord connectionRecord, final ResultReceiver resultReceiver) {
        Result result = Result(str) { // from class: android.support.v4.media.MediaBrowserServiceCompat.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            fun onResultSent(List list) {
                if ((getFlags() & 4) != 0 || list == null) {
                    resultReceiver.send(-1, null)
                    return
                }
                Bundle bundle2 = Bundle()
                bundle2.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Array<Parcelable>) list.toArray(new MediaBrowserCompat.MediaItem[0]))
                resultReceiver.send(0, bundle2)
            }
        }
        this.mCurConnection = connectionRecord
        onSearch(str, bundle, result)
        this.mCurConnection = null
        if (!result.isDone()) {
            throw IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + str)
        }
    }

    Boolean removeSubscription(String str, ConnectionRecord connectionRecord, IBinder iBinder) {
        Boolean z
        if (iBinder == null) {
            return connectionRecord.subscriptions.remove(str) != null
        }
        List list = (List) connectionRecord.subscriptions.get(str)
        if (list != null) {
            Iterator it = list.iterator()
            z = false
            while (it.hasNext()) {
                if (iBinder == ((Pair) it.next()).first) {
                    it.remove()
                    z = true
                }
            }
            if (list.size() == 0) {
                connectionRecord.subscriptions.remove(str)
            }
        } else {
            z = false
        }
        return z
    }

    fun setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw IllegalArgumentException("Session token may not be null.")
        }
        if (this.mSession != null) {
            throw IllegalStateException("The session token has already been set.")
        }
        this.mSession = token
        this.mImpl.setSessionToken(token)
    }
}
