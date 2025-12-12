package android.support.v4.media

import android.content.Context
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.os.Parcel
import android.service.media.MediaBrowserService
import android.support.annotation.RequiresApi
import android.support.v4.media.MediaBrowserServiceCompatApi23
import android.util.Log
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Iterator
import java.util.List

@RequiresApi(26)
class MediaBrowserServiceCompatApi26 {
    private static val TAG = "MBSCompatApi26"
    private static Field sResultFlags

    class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            super(context, serviceCompatProxy)
        }

        @Override // android.service.media.MediaBrowserService
        fun onLoadChildren(String str, MediaBrowserService.Result result, Bundle bundle) {
            ((ServiceCompatProxy) this.mServiceProxy).onLoadChildren(str, ResultWrapper(result), bundle)
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

        fun sendResult(List list, Int i) throws IllegalAccessException, IllegalArgumentException {
            try {
                MediaBrowserServiceCompatApi26.sResultFlags.setInt(this.mResultObj, i)
            } catch (IllegalAccessException e) {
                Log.w(MediaBrowserServiceCompatApi26.TAG, e)
            }
            this.mResultObj.sendResult(parcelListToItemList(list))
        }
    }

    public interface ServiceCompatProxy extends MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        Unit onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle)
    }

    static {
        try {
            Field declaredField = MediaBrowserService.Result.class.getDeclaredField("mFlags")
            sResultFlags = declaredField
            declaredField.setAccessible(true)
        } catch (NoSuchFieldException e) {
            Log.w(TAG, e)
        }
    }

    MediaBrowserServiceCompatApi26() {
    }

    fun createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return MediaBrowserServiceAdaptor(context, serviceCompatProxy)
    }

    fun getBrowserRootHints(Object obj) {
        return ((MediaBrowserService) obj).getBrowserRootHints()
    }

    fun notifyChildrenChanged(Object obj, String str, Bundle bundle) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str, bundle)
    }
}
