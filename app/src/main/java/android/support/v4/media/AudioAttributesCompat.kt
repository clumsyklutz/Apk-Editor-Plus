package android.support.v4.media

import android.media.AudioAttributes
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.media.AudioAttributesCompatApi21
import android.util.SparseIntArray
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.Arrays

class AudioAttributesCompat {
    public static val CONTENT_TYPE_MOVIE = 3
    public static val CONTENT_TYPE_MUSIC = 2
    public static val CONTENT_TYPE_SONIFICATION = 4
    public static val CONTENT_TYPE_SPEECH = 1
    public static val CONTENT_TYPE_UNKNOWN = 0
    private static val FLAG_ALL = 1023
    private static val FLAG_ALL_PUBLIC = 273
    public static val FLAG_AUDIBILITY_ENFORCED = 1
    private static val FLAG_BEACON = 8
    private static val FLAG_BYPASS_INTERRUPTION_POLICY = 64
    private static val FLAG_BYPASS_MUTE = 128
    private static val FLAG_DEEP_BUFFER = 512
    public static val FLAG_HW_AV_SYNC = 16
    private static val FLAG_HW_HOTWORD = 32
    private static val FLAG_LOW_LATENCY = 256
    private static val FLAG_SCO = 4
    private static val FLAG_SECURE = 2
    private static final Array<Int> SDK_USAGES
    private static val SUPPRESSIBLE_CALL = 2
    private static val SUPPRESSIBLE_NOTIFICATION = 1
    private static final SparseIntArray SUPPRESSIBLE_USAGES
    private static val TAG = "AudioAttributesCompat"
    public static val USAGE_ALARM = 4
    public static val USAGE_ASSISTANCE_ACCESSIBILITY = 11
    public static val USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12
    public static val USAGE_ASSISTANCE_SONIFICATION = 13
    public static val USAGE_ASSISTANT = 16
    public static val USAGE_GAME = 14
    public static val USAGE_MEDIA = 1
    public static val USAGE_NOTIFICATION = 5
    public static val USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9
    public static val USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8
    public static val USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7
    public static val USAGE_NOTIFICATION_EVENT = 10
    public static val USAGE_NOTIFICATION_RINGTONE = 6
    public static val USAGE_UNKNOWN = 0
    private static val USAGE_VIRTUAL_SOURCE = 15
    public static val USAGE_VOICE_COMMUNICATION = 2
    public static val USAGE_VOICE_COMMUNICATION_SIGNALLING = 3
    private static Boolean sForceLegacyBehavior
    private AudioAttributesCompatApi21.Wrapper mAudioAttributesWrapper
    Int mContentType
    Int mFlags
    Integer mLegacyStream
    Int mUsage

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeUsage {
    }

    abstract class AudioManagerHidden {
        public static val STREAM_ACCESSIBILITY = 10
        public static val STREAM_BLUETOOTH_SCO = 6
        public static val STREAM_SYSTEM_ENFORCED = 7
        public static val STREAM_TTS = 9

        private constructor() {
        }
    }

    class Builder {
        private Object mAAObject
        private Int mContentType
        private Int mFlags
        private Integer mLegacyStream
        private Int mUsage

        constructor() {
            this.mUsage = 0
            this.mContentType = 0
            this.mFlags = 0
        }

        constructor(AudioAttributesCompat audioAttributesCompat) {
            this.mUsage = 0
            this.mContentType = 0
            this.mFlags = 0
            this.mUsage = audioAttributesCompat.mUsage
            this.mContentType = audioAttributesCompat.mContentType
            this.mFlags = audioAttributesCompat.mFlags
            this.mLegacyStream = audioAttributesCompat.mLegacyStream
            this.mAAObject = audioAttributesCompat.unwrap()
        }

        fun build() {
            if (!AudioAttributesCompat.sForceLegacyBehavior && Build.VERSION.SDK_INT >= 21) {
                if (this.mAAObject != null) {
                    return AudioAttributesCompat.wrap(this.mAAObject)
                }
                AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(this.mContentType).setFlags(this.mFlags).setUsage(this.mUsage)
                if (this.mLegacyStream != null) {
                    usage.setLegacyStreamType(this.mLegacyStream.intValue())
                }
                return AudioAttributesCompat.wrap(usage.build())
            }
            AudioAttributesCompat audioAttributesCompat = AudioAttributesCompat()
            audioAttributesCompat.mContentType = this.mContentType
            audioAttributesCompat.mFlags = this.mFlags
            audioAttributesCompat.mUsage = this.mUsage
            audioAttributesCompat.mLegacyStream = this.mLegacyStream
            audioAttributesCompat.mAudioAttributesWrapper = null
            return audioAttributesCompat
        }

        fun setContentType(Int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    this.mContentType = i
                    return this
                default:
                    this.mUsage = 0
                    return this
            }
        }

        fun setFlags(Int i) {
            this.mFlags = (i & AudioAttributesCompat.FLAG_ALL) | this.mFlags
            return this
        }

        fun setLegacyStreamType(Int i) {
            if (i == 10) {
                throw IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback")
            }
            this.mLegacyStream = Integer.valueOf(i)
            this.mUsage = AudioAttributesCompat.usageForStreamType(i)
            return this
        }

        fun setUsage(Int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    this.mUsage = i
                    return this
                case 16:
                    if (AudioAttributesCompat.sForceLegacyBehavior || Build.VERSION.SDK_INT <= 25) {
                        this.mUsage = 12
                    } else {
                        this.mUsage = i
                    }
                    return this
                default:
                    this.mUsage = 0
                    return this
            }
        }
    }

    static {
        SparseIntArray sparseIntArray = SparseIntArray()
        SUPPRESSIBLE_USAGES = sparseIntArray
        sparseIntArray.put(5, 1)
        SUPPRESSIBLE_USAGES.put(6, 2)
        SUPPRESSIBLE_USAGES.put(7, 2)
        SUPPRESSIBLE_USAGES.put(8, 1)
        SUPPRESSIBLE_USAGES.put(9, 1)
        SUPPRESSIBLE_USAGES.put(10, 1)
        SDK_USAGES = new Array<Int>{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16}
    }

    private constructor() {
        this.mUsage = 0
        this.mContentType = 0
        this.mFlags = 0
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setForceLegacyBehavior(Boolean z) {
        sForceLegacyBehavior = z
    }

    static Int toVolumeStreamType(Boolean z, Int i, Int i2) {
        if ((i & 1) == 1) {
            return z ? 1 : 7
        }
        if ((i & 4) == 4) {
            return z ? 0 : 6
        }
        switch (i2) {
            case 0:
                return z ? Integer.MIN_VALUE : 3
            case 1:
            case 12:
            case 14:
            case 16:
                return 3
            case 2:
                return 0
            case 3:
                return z ? 0 : 8
            case 4:
                return 4
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5
            case 6:
                return 2
            case 11:
                return 10
            case 13:
                return 1
            case 15:
            default:
                if (z) {
                    throw IllegalArgumentException("Unknown usage value " + i2 + " in audio attributes")
                }
                return 3
        }
    }

    static Int toVolumeStreamType(Boolean z, AudioAttributesCompat audioAttributesCompat) {
        return toVolumeStreamType(z, audioAttributesCompat.getFlags(), audioAttributesCompat.getUsage())
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun usageForStreamType(Int i) {
        switch (i) {
            case 0:
            case 6:
                return 2
            case 1:
            case 7:
                return 13
            case 2:
                return 6
            case 3:
                return 1
            case 4:
                return 4
            case 5:
                return 5
            case 8:
                return 3
            case 9:
            default:
                return 0
            case 10:
                return 11
        }
    }

    static String usageToString(Int i) {
        switch (i) {
            case 0:
                return String("USAGE_UNKNOWN")
            case 1:
                return String("USAGE_MEDIA")
            case 2:
                return String("USAGE_VOICE_COMMUNICATION")
            case 3:
                return String("USAGE_VOICE_COMMUNICATION_SIGNALLING")
            case 4:
                return String("USAGE_ALARM")
            case 5:
                return String("USAGE_NOTIFICATION")
            case 6:
                return String("USAGE_NOTIFICATION_RINGTONE")
            case 7:
                return String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST")
            case 8:
                return String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT")
            case 9:
                return String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED")
            case 10:
                return String("USAGE_NOTIFICATION_EVENT")
            case 11:
                return String("USAGE_ASSISTANCE_ACCESSIBILITY")
            case 12:
                return String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE")
            case 13:
                return String("USAGE_ASSISTANCE_SONIFICATION")
            case 14:
                return String("USAGE_GAME")
            case 15:
            default:
                return String("unknown usage " + i)
            case 16:
                return String("USAGE_ASSISTANT")
        }
    }

    @Nullable
    fun wrap(@NonNull Object obj) {
        if (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior) {
            return null
        }
        AudioAttributesCompat audioAttributesCompat = AudioAttributesCompat()
        audioAttributesCompat.mAudioAttributesWrapper = AudioAttributesCompatApi21.Wrapper.wrap((AudioAttributes) obj)
        return audioAttributesCompat
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj
        if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap().equals(audioAttributesCompat.unwrap())
        }
        if (this.mContentType == audioAttributesCompat.getContentType() && this.mFlags == audioAttributesCompat.getFlags() && this.mUsage == audioAttributesCompat.getUsage()) {
            if (this.mLegacyStream != null) {
                if (this.mLegacyStream.equals(audioAttributesCompat.mLegacyStream)) {
                    return true
                }
            } else if (audioAttributesCompat.mLegacyStream == null) {
                return true
            }
        }
        return false
    }

    fun getContentType() {
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || this.mAudioAttributesWrapper == null) ? this.mContentType : this.mAudioAttributesWrapper.unwrap().getContentType()
    }

    fun getFlags() {
        if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap().getFlags()
        }
        Int i = this.mFlags
        Int legacyStreamType = getLegacyStreamType()
        if (legacyStreamType == 6) {
            i |= 4
        } else if (legacyStreamType == 7) {
            i |= 1
        }
        return i & FLAG_ALL_PUBLIC
    }

    fun getLegacyStreamType() {
        return this.mLegacyStream != null ? this.mLegacyStream.intValue() : (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior) ? toVolumeStreamType(false, this.mFlags, this.mUsage) : AudioAttributesCompatApi21.toLegacyStreamType(this.mAudioAttributesWrapper)
    }

    fun getUsage() {
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || this.mAudioAttributesWrapper == null) ? this.mUsage : this.mAudioAttributesWrapper.unwrap().getUsage()
    }

    fun getVolumeControlStream() {
        if (this == null) {
            throw IllegalArgumentException("Invalid null audio attributes")
        }
        return (Build.VERSION.SDK_INT < 26 || sForceLegacyBehavior || unwrap() == null) ? toVolumeStreamType(true, this) : ((AudioAttributes) unwrap()).getVolumeControlStream()
    }

    fun hashCode() {
        return (Build.VERSION.SDK_INT < 21 || sForceLegacyBehavior || this.mAudioAttributesWrapper == null) ? Arrays.hashCode(new Array<Object>{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), this.mLegacyStream}) : this.mAudioAttributesWrapper.unwrap().hashCode()
    }

    fun toString() {
        StringBuilder sb = StringBuilder("AudioAttributesCompat:")
        if (unwrap() != null) {
            sb.append(" audioattributes=").append(unwrap())
        } else {
            if (this.mLegacyStream != null) {
                sb.append(" stream=").append(this.mLegacyStream)
                sb.append(" derived")
            }
            sb.append(" usage=").append(usageToString()).append(" content=").append(this.mContentType).append(" flags=0x").append(Integer.toHexString(this.mFlags).toUpperCase())
        }
        return sb.toString()
    }

    @Nullable
    fun unwrap() {
        if (this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap()
        }
        return null
    }

    String usageToString() {
        return usageToString(this.mUsage)
    }
}
