package android.support.v4.media.session

import android.media.session.PlaybackState
import android.os.Bundle
import android.support.annotation.RequiresApi
import java.util.Iterator
import java.util.List

@RequiresApi(21)
class PlaybackStateCompatApi21 {

    final class CustomAction {
        CustomAction() {
        }

        fun getAction(Object obj) {
            return ((PlaybackState.CustomAction) obj).getAction()
        }

        fun getExtras(Object obj) {
            return ((PlaybackState.CustomAction) obj).getExtras()
        }

        fun getIcon(Object obj) {
            return ((PlaybackState.CustomAction) obj).getIcon()
        }

        fun getName(Object obj) {
            return ((PlaybackState.CustomAction) obj).getName()
        }

        fun newInstance(String str, CharSequence charSequence, Int i, Bundle bundle) {
            PlaybackState.CustomAction.Builder builder = new PlaybackState.CustomAction.Builder(str, charSequence, i)
            builder.setExtras(bundle)
            return builder.build()
        }
    }

    PlaybackStateCompatApi21() {
    }

    fun getActions(Object obj) {
        return ((PlaybackState) obj).getActions()
    }

    fun getActiveQueueItemId(Object obj) {
        return ((PlaybackState) obj).getActiveQueueItemId()
    }

    fun getBufferedPosition(Object obj) {
        return ((PlaybackState) obj).getBufferedPosition()
    }

    fun getCustomActions(Object obj) {
        return ((PlaybackState) obj).getCustomActions()
    }

    fun getErrorMessage(Object obj) {
        return ((PlaybackState) obj).getErrorMessage()
    }

    fun getLastPositionUpdateTime(Object obj) {
        return ((PlaybackState) obj).getLastPositionUpdateTime()
    }

    fun getPlaybackSpeed(Object obj) {
        return ((PlaybackState) obj).getPlaybackSpeed()
    }

    fun getPosition(Object obj) {
        return ((PlaybackState) obj).getPosition()
    }

    fun getState(Object obj) {
        return ((PlaybackState) obj).getState()
    }

    fun newInstance(Int i, Long j, Long j2, Float f, Long j3, CharSequence charSequence, Long j4, List list, Long j5) {
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
        return builder.build()
    }
}
