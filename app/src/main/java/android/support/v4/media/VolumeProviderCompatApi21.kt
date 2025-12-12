package android.support.v4.media

import android.media.VolumeProvider
import android.support.annotation.RequiresApi

@RequiresApi(21)
class VolumeProviderCompatApi21 {

    public interface Delegate {
        Unit onAdjustVolume(Int i)

        Unit onSetVolumeTo(Int i)
    }

    VolumeProviderCompatApi21() {
    }

    fun createVolumeProvider(Int i, Int i2, Int i3, final Delegate delegate) {
        return VolumeProvider(i, i2, i3) { // from class: android.support.v4.media.VolumeProviderCompatApi21.1
            @Override // android.media.VolumeProvider
            public final Unit onAdjustVolume(Int i4) {
                delegate.onAdjustVolume(i4)
            }

            @Override // android.media.VolumeProvider
            public final Unit onSetVolumeTo(Int i4) {
                delegate.onSetVolumeTo(i4)
            }
        }
    }

    fun setCurrentVolume(Object obj, Int i) {
        ((VolumeProvider) obj).setCurrentVolume(i)
    }
}
