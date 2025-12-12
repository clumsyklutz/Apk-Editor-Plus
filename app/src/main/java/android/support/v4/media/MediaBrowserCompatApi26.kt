package android.support.v4.media

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.v4.media.MediaBrowserCompatApi21
import java.util.List

@RequiresApi(26)
class MediaBrowserCompatApi26 {

    interface SubscriptionCallback extends MediaBrowserCompatApi21.SubscriptionCallback {
        Unit onChildrenLoaded(@NonNull String str, List list, @NonNull Bundle bundle)

        Unit onError(@NonNull String str, @NonNull Bundle bundle)
    }

    class SubscriptionCallbackProxy extends MediaBrowserCompatApi21.SubscriptionCallbackProxy {
        SubscriptionCallbackProxy(SubscriptionCallback subscriptionCallback) {
            super(subscriptionCallback)
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        fun onChildrenLoaded(@NonNull String str, List list, @NonNull Bundle bundle) {
            ((SubscriptionCallback) this.mSubscriptionCallback).onChildrenLoaded(str, list, bundle)
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        fun onError(@NonNull String str, @NonNull Bundle bundle) {
            ((SubscriptionCallback) this.mSubscriptionCallback).onError(str, bundle)
        }
    }

    MediaBrowserCompatApi26() {
    }

    static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return SubscriptionCallbackProxy(subscriptionCallback)
    }

    fun subscribe(Object obj, String str, Bundle bundle, Object obj2) {
        ((MediaBrowser) obj).subscribe(str, bundle, (MediaBrowser.SubscriptionCallback) obj2)
    }

    fun unsubscribe(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).unsubscribe(str, (MediaBrowser.SubscriptionCallback) obj2)
    }
}
