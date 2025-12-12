package android.support.v4.media.session

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.util.Log
import android.view.KeyEvent
import java.util.List

class MediaButtonReceiver extends BroadcastReceiver {
    private static val TAG = "MediaButtonReceiver"

    class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private final Context mContext
        private final Intent mIntent
        private MediaBrowserCompat mMediaBrowser
        private final BroadcastReceiver.PendingResult mPendingResult

        MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
            this.mContext = context
            this.mIntent = intent
            this.mPendingResult = pendingResult
        }

        private fun finish() {
            this.mMediaBrowser.disconnect()
            this.mPendingResult.finish()
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        fun onConnected() {
            try {
                MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken()).dispatchMediaButtonEvent((KeyEvent) this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"))
            } catch (RemoteException e) {
                Log.e(MediaButtonReceiver.TAG, "Failed to create a media controller", e)
            }
            finish()
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        fun onConnectionFailed() {
            finish()
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback
        fun onConnectionSuspended() {
            finish()
        }

        Unit setMediaBrowser(MediaBrowserCompat mediaBrowserCompat) {
            this.mMediaBrowser = mediaBrowserCompat
        }
    }

    fun buildMediaButtonPendingIntent(Context context, Long j) {
        ComponentName mediaButtonReceiverComponent = getMediaButtonReceiverComponent(context)
        if (mediaButtonReceiverComponent != null) {
            return buildMediaButtonPendingIntent(context, mediaButtonReceiverComponent, j)
        }
        Log.w(TAG, "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.")
        return null
    }

    fun buildMediaButtonPendingIntent(Context context, ComponentName componentName, Long j) {
        if (componentName == null) {
            Log.w(TAG, "The component name of media button receiver should be provided.")
            return null
        }
        Int keyCode = PlaybackStateCompat.toKeyCode(j)
        if (keyCode == 0) {
            Log.w(TAG, "Cannot build a media button pending intent with the given action: " + j)
            return null
        }
        Intent intent = Intent("android.intent.action.MEDIA_BUTTON")
        intent.setComponent(componentName)
        intent.putExtra("android.intent.extra.KEY_EVENT", KeyEvent(0, keyCode))
        return PendingIntent.getBroadcast(context, keyCode, intent, 0)
    }

    static ComponentName getMediaButtonReceiverComponent(Context context) {
        Intent intent = Intent("android.intent.action.MEDIA_BUTTON")
        intent.setPackage(context.getPackageName())
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0)
        if (listQueryBroadcastReceivers.size() == 1) {
            ResolveInfo resolveInfo = listQueryBroadcastReceivers.get(0)
            return ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
        }
        if (listQueryBroadcastReceivers.size() > 1) {
            Log.w(TAG, "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.")
        }
        return null
    }

    private fun getServiceComponentByAction(Context context, String str) {
        PackageManager packageManager = context.getPackageManager()
        Intent intent = Intent(str)
        intent.setPackage(context.getPackageName())
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0)
        if (listQueryIntentServices.size() == 1) {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0)
            return ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
        }
        if (listQueryIntentServices.isEmpty()) {
            return null
        }
        throw IllegalStateException("Expected 1 service that handles " + str + ", found " + listQueryIntentServices.size())
    }

    fun handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        if (mediaSessionCompat == null || intent == null || !"android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) || !intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            return null
        }
        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT")
        mediaSessionCompat.getController().dispatchMediaButtonEvent(keyEvent)
        return keyEvent
    }

    private fun startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    @Override // android.content.BroadcastReceiver
    fun onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) || !intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            Log.d(TAG, "Ignore unsupported intent: " + intent)
            return
        }
        ComponentName serviceComponentByAction = getServiceComponentByAction(context, "android.intent.action.MEDIA_BUTTON")
        if (serviceComponentByAction != null) {
            intent.setComponent(serviceComponentByAction)
            startForegroundService(context, intent)
            return
        }
        ComponentName serviceComponentByAction2 = getServiceComponentByAction(context, MediaBrowserServiceCompat.SERVICE_INTERFACE)
        if (serviceComponentByAction2 == null) {
            throw IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.")
        }
        BroadcastReceiver.PendingResult pendingResultGoAsync = goAsync()
        Context applicationContext = context.getApplicationContext()
        MediaButtonConnectionCallback mediaButtonConnectionCallback = MediaButtonConnectionCallback(applicationContext, intent, pendingResultGoAsync)
        MediaBrowserCompat mediaBrowserCompat = MediaBrowserCompat(applicationContext, serviceComponentByAction2, mediaButtonConnectionCallback, null)
        mediaButtonConnectionCallback.setMediaBrowser(mediaBrowserCompat)
        mediaBrowserCompat.connect()
    }
}
