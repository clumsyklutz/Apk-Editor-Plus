package android.support.v4.media

import android.content.Context
import android.service.media.MediaBrowserService
import android.support.annotation.RequiresApi
import android.support.v4.media.MediaBrowserServiceCompatApi21

@RequiresApi(23)
class MediaBrowserServiceCompatApi23 {

    class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy)
        }

        @Override // android.service.media.MediaBrowserService
        fun onLoadItem(String str, MediaBrowserService.Result result) {
            ((ServiceCompatProxy) this.mServiceProxy).onLoadItem(str, new MediaBrowserServiceCompatApi21.ResultWrapper(result))
        }
    }

    public interface ServiceCompatProxy extends MediaBrowserServiceCompatApi21.ServiceCompatProxy {
        Unit onLoadItem(String str, MediaBrowserServiceCompatApi21.ResultWrapper resultWrapper)
    }

    MediaBrowserServiceCompatApi23() {
    }

    fun createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return MediaBrowserServiceAdaptor(context, serviceCompatProxy)
    }
}
