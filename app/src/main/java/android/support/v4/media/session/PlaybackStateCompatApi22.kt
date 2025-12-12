package android.support.v4.media.session

import android.media.session.PlaybackState
import android.os.Bundle
import android.support.annotation.RequiresApi
import java.util.Iterator
import java.util.List

@RequiresApi(22)
class PlaybackStateCompatApi22 {
    PlaybackStateCompatApi22() {
    }

    fun getExtras(Object obj) {
        return ((PlaybackState) obj).getExtras()
    }

    fun newInstance(Int i, Long j, Long j2, Float f, Long j3, CharSequence charSequence, Long j4, List list, Long j5, Bundle bundle) {
        PlaybackState.Builder builder = new PlaybackState.Builder()
        builder.setState(i, j, f, j4)
        builder.setBufferedPosition(j2)
        builder.setActions(j3)
        builder.setErrorMessage(charSequence)
        Iterator it = list.iterator()
        while (it.hasNext()) {
            builder.addCustomAction((PlaybackState.CustomAction) it.next())
        }
        builder.setActiveQueueItemId(j5)
        builder.setExtras(bundle)
        return builder.build()
    }
}
