package android.support.v4.media

import android.os.Build
import android.support.annotation.RestrictTo
import android.support.v4.media.VolumeProviderCompatApi21
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

abstract class VolumeProviderCompat {
    public static val VOLUME_CONTROL_ABSOLUTE = 2
    public static val VOLUME_CONTROL_FIXED = 0
    public static val VOLUME_CONTROL_RELATIVE = 1
    private Callback mCallback
    private final Int mControlType
    private Int mCurrentVolume
    private final Int mMaxVolume
    private Object mVolumeProviderObj

    abstract class Callback {
        public abstract Unit onVolumeChanged(VolumeProviderCompat volumeProviderCompat)
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ControlType {
    }

    constructor(Int i, Int i2, Int i3) {
        this.mControlType = i
        this.mMaxVolume = i2
        this.mCurrentVolume = i3
    }

    public final Int getCurrentVolume() {
        return this.mCurrentVolume
    }

    public final Int getMaxVolume() {
        return this.mMaxVolume
    }

    public final Int getVolumeControl() {
        return this.mControlType
    }

    fun getVolumeProvider() {
        if (this.mVolumeProviderObj == null && Build.VERSION.SDK_INT >= 21) {
            this.mVolumeProviderObj = VolumeProviderCompatApi21.createVolumeProvider(this.mControlType, this.mMaxVolume, this.mCurrentVolume, new VolumeProviderCompatApi21.Delegate() { // from class: android.support.v4.media.VolumeProviderCompat.1
                @Override // android.support.v4.media.VolumeProviderCompatApi21.Delegate
                fun onAdjustVolume(Int i) {
                    VolumeProviderCompat.this.onAdjustVolume(i)
                }

                @Override // android.support.v4.media.VolumeProviderCompatApi21.Delegate
                fun onSetVolumeTo(Int i) {
                    VolumeProviderCompat.this.onSetVolumeTo(i)
                }
            })
        }
        return this.mVolumeProviderObj
    }

    fun onAdjustVolume(Int i) {
    }

    fun onSetVolumeTo(Int i) {
    }

    fun setCallback(Callback callback) {
        this.mCallback = callback
    }

    public final Unit setCurrentVolume(Int i) {
        this.mCurrentVolume = i
        Object volumeProvider = getVolumeProvider()
        if (volumeProvider != null && Build.VERSION.SDK_INT >= 21) {
            VolumeProviderCompatApi21.setCurrentVolume(volumeProvider, i)
        }
        if (this.mCallback != null) {
            this.mCallback.onVolumeChanged(this)
        }
    }
}
