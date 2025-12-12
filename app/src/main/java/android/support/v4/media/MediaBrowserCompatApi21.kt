package android.support.v4.media

import android.content.ComponentName
import android.content.Context
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import java.util.List

@RequiresApi(21)
class MediaBrowserCompatApi21 {
    static val NULL_MEDIA_ITEM_ID = "android.support.v4.media.MediaBrowserCompat.NULL_MEDIA_ITEM"

    interface ConnectionCallback {
        Unit onConnected()

        Unit onConnectionFailed()

        Unit onConnectionSuspended()
    }

    class ConnectionCallbackProxy extends MediaBrowser.ConnectionCallback {
        protected final ConnectionCallback mConnectionCallback

        constructor(ConnectionCallback connectionCallback) {
            this.mConnectionCallback = connectionCallback
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        fun onConnected() {
            this.mConnectionCallback.onConnected()
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        fun onConnectionFailed() {
            this.mConnectionCallback.onConnectionFailed()
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        fun onConnectionSuspended() {
            this.mConnectionCallback.onConnectionSuspended()
        }
    }

    class MediaItem {
        MediaItem() {
        }

        fun getDescription(Object obj) {
            return ((MediaBrowser.MediaItem) obj).getDescription()
        }

        fun getFlags(Object obj) {
            return ((MediaBrowser.MediaItem) obj).getFlags()
        }
    }

    interface SubscriptionCallback {
        Unit onChildrenLoaded(@NonNull String str, List list)

        Unit onError(@NonNull String str)
    }

    class SubscriptionCallbackProxy extends MediaBrowser.SubscriptionCallback {
        protected final SubscriptionCallback mSubscriptionCallback

        constructor(SubscriptionCallback subscriptionCallback) {
            this.mSubscriptionCallback = subscriptionCallback
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        fun onChildrenLoaded(@NonNull String str, List list) {
            this.mSubscriptionCallback.onChildrenLoaded(str, list)
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        fun onError(@NonNull String str) {
            this.mSubscriptionCallback.onError(str)
        }
    }

    MediaBrowserCompatApi21() {
    }

    fun connect(Object obj) {
        ((MediaBrowser) obj).connect()
    }

    fun createBrowser(Context context, ComponentName componentName, Object obj, Bundle bundle) {
        return MediaBrowser(context, componentName, (MediaBrowser.ConnectionCallback) obj, bundle)
    }

    fun createConnectionCallback(ConnectionCallback connectionCallback) {
        return ConnectionCallbackProxy(connectionCallback)
    }

    fun createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return SubscriptionCallbackProxy(subscriptionCallback)
    }

    fun disconnect(Object obj) {
        ((MediaBrowser) obj).disconnect()
    }

    fun getExtras(Object obj) {
        return ((MediaBrowser) obj).getExtras()
    }

    fun getRoot(Object obj) {
        return ((MediaBrowser) obj).getRoot()
    }

    fun getServiceComponent(Object obj) {
        return ((MediaBrowser) obj).getServiceComponent()
    }

    fun getSessionToken(Object obj) {
        return ((MediaBrowser) obj).getSessionToken()
    }

    fun isConnected(Object obj) {
        return ((MediaBrowser) obj).isConnected()
    }

    fun subscribe(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).subscribe(str, (MediaBrowser.SubscriptionCallback) obj2)
    }

    fun unsubscribe(Object obj, String str) {
        ((MediaBrowser) obj).unsubscribe(str)
    }
}
