package android.support.v4.media

import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser
import android.media.session.MediaSession
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.service.media.MediaBrowserService
import android.support.annotation.RequiresApi
import java.util.ArrayList
import java.util.Iterator
import java.util.List

@RequiresApi(21)
class MediaBrowserServiceCompatApi21 {

    class BrowserRoot {
        final Bundle mExtras
        final String mRootId

        BrowserRoot(String str, Bundle bundle) {
            this.mRootId = str
            this.mExtras = bundle
        }
    }

    class MediaBrowserServiceAdaptor extends MediaBrowserService {
        final ServiceCompatProxy mServiceProxy

        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            attachBaseContext(context)
            this.mServiceProxy = serviceCompatProxy
        }

        @Override // android.service.media.MediaBrowserService
        public MediaBrowserService.BrowserRoot onGetRoot(String str, Int i, Bundle bundle) {
            BrowserRoot browserRootOnGetRoot = this.mServiceProxy.onGetRoot(str, i, bundle == null ? null : Bundle(bundle))
            if (browserRootOnGetRoot == null) {
                return null
            }
            return new MediaBrowserService.BrowserRoot(browserRootOnGetRoot.mRootId, browserRootOnGetRoot.mExtras)
        }

        @Override // android.service.media.MediaBrowserService
        fun onLoadChildren(String str, MediaBrowserService.Result result) {
            this.mServiceProxy.onLoadChildren(str, ResultWrapper(result))
        }
    }

    class ResultWrapper {
        MediaBrowserService.Result mResultObj

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultObj = result
        }

        fun detach() {
            this.mResultObj.detach()
        }

        List parcelListToItemList(List list) {
            if (list == null) {
                return null
            }
            ArrayList arrayList = ArrayList()
            Iterator it = list.iterator()
            while (it.hasNext()) {
                Parcel parcel = (Parcel) it.next()
                parcel.setDataPosition(0)
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel))
                parcel.recycle()
            }
            return arrayList
        }

        fun sendResult(Object obj) {
            if (obj is List) {
                this.mResultObj.sendResult(parcelListToItemList((List) obj))
                return
            }
            if (!(obj is Parcel)) {
                this.mResultObj.sendResult(null)
                return
            }
            Parcel parcel = (Parcel) obj
            parcel.setDataPosition(0)
            this.mResultObj.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel))
            parcel.recycle()
        }
    }

    public interface ServiceCompatProxy {
        BrowserRoot onGetRoot(String str, Int i, Bundle bundle)

        Unit onLoadChildren(String str, ResultWrapper resultWrapper)
    }

    MediaBrowserServiceCompatApi21() {
    }

    fun createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return MediaBrowserServiceAdaptor(context, serviceCompatProxy)
    }

    fun notifyChildrenChanged(Object obj, String str) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str)
    }

    fun onBind(Object obj, Intent intent) {
        return ((MediaBrowserService) obj).onBind(intent)
    }

    fun onCreate(Object obj) {
        ((MediaBrowserService) obj).onCreate()
    }

    fun setSessionToken(Object obj, Object obj2) {
        ((MediaBrowserService) obj).setSessionToken((MediaSession.Token) obj2)
    }
}
