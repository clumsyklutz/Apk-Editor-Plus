package android.support.v4.media

import android.media.browse.MediaBrowser
import android.os.Parcel
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi

@RequiresApi(23)
class MediaBrowserCompatApi23 {

    interface ItemCallback {
        Unit onError(@NonNull String str)

        Unit onItemLoaded(Parcel parcel)
    }

    class ItemCallbackProxy extends MediaBrowser.ItemCallback {
        protected final ItemCallback mItemCallback

        constructor(ItemCallback itemCallback) {
            this.mItemCallback = itemCallback
        }

        @Override // android.media.browse.MediaBrowser.ItemCallback
        fun onError(@NonNull String str) {
            this.mItemCallback.onError(str)
        }

        @Override // android.media.browse.MediaBrowser.ItemCallback
        fun onItemLoaded(MediaBrowser.MediaItem mediaItem) {
            if (mediaItem == null) {
                this.mItemCallback.onItemLoaded(null)
                return
            }
            Parcel parcelObtain = Parcel.obtain()
            mediaItem.writeToParcel(parcelObtain, 0)
            this.mItemCallback.onItemLoaded(parcelObtain)
        }
    }

    MediaBrowserCompatApi23() {
    }

    fun createItemCallback(ItemCallback itemCallback) {
        return ItemCallbackProxy(itemCallback)
    }

    fun getItem(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).getItem(str, (MediaBrowser.ItemCallback) obj2)
    }
}
