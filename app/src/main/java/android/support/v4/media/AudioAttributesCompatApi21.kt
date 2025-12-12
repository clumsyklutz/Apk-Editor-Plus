package android.support.v4.media

import android.media.AudioAttributes
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RequiresApi(21)
class AudioAttributesCompatApi21 {
    private static val TAG = "AudioAttributesCompat"
    private static Method sAudioAttributesToLegacyStreamType

    final class Wrapper {
        private AudioAttributes mWrapped

        private constructor(AudioAttributes audioAttributes) {
            this.mWrapped = audioAttributes
        }

        fun wrap(@NonNull AudioAttributes audioAttributes) {
            if (audioAttributes == null) {
                throw IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null")
            }
            return Wrapper(audioAttributes)
        }

        public final AudioAttributes unwrap() {
            return this.mWrapped
        }
    }

    AudioAttributesCompatApi21() {
    }

    fun toLegacyStreamType(Wrapper wrapper) {
        AudioAttributes audioAttributesUnwrap = wrapper.unwrap()
        try {
            if (sAudioAttributesToLegacyStreamType == null) {
                sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class)
            }
            return ((Integer) sAudioAttributesToLegacyStreamType.invoke(null, audioAttributesUnwrap)).intValue()
        } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.w(TAG, "getLegacyStreamType() failed on API21+", e)
            return -1
        }
    }
}
