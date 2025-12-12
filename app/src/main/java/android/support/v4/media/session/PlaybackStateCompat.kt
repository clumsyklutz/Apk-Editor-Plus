package android.support.v4.media.session

import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.media.session.PlaybackStateCompatApi21
import android.text.TextUtils
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class PlaybackStateCompat implements Parcelable {
    public static val ACTION_FAST_FORWARD = 64
    public static val ACTION_PAUSE = 2
    public static val ACTION_PLAY = 4
    public static val ACTION_PLAY_FROM_MEDIA_ID = 1024
    public static val ACTION_PLAY_FROM_SEARCH = 2048
    public static val ACTION_PLAY_FROM_URI = 8192
    public static val ACTION_PLAY_PAUSE = 512
    public static val ACTION_PREPARE = 16384
    public static val ACTION_PREPARE_FROM_MEDIA_ID = 32768
    public static val ACTION_PREPARE_FROM_SEARCH = 65536
    public static val ACTION_PREPARE_FROM_URI = 131072
    public static val ACTION_REWIND = 8
    public static val ACTION_SEEK_TO = 256
    public static val ACTION_SET_CAPTIONING_ENABLED = 1048576
    public static val ACTION_SET_RATING = 128
    public static val ACTION_SET_REPEAT_MODE = 262144
    public static val ACTION_SET_SHUFFLE_MODE = 2097152

    @Deprecated
    public static val ACTION_SET_SHUFFLE_MODE_ENABLED = 524288
    public static val ACTION_SKIP_TO_NEXT = 32
    public static val ACTION_SKIP_TO_PREVIOUS = 16
    public static val ACTION_SKIP_TO_QUEUE_ITEM = 4096
    public static val ACTION_STOP = 1
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.PlaybackStateCompat.1
        @Override // android.os.Parcelable.Creator
        public final PlaybackStateCompat createFromParcel(Parcel parcel) {
            return PlaybackStateCompat(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<PlaybackStateCompat> newArray(Int i) {
            return new PlaybackStateCompat[i]
        }
    }
    public static val ERROR_CODE_ACTION_ABORTED = 10
    public static val ERROR_CODE_APP_ERROR = 1
    public static val ERROR_CODE_AUTHENTICATION_EXPIRED = 3
    public static val ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5
    public static val ERROR_CODE_CONTENT_ALREADY_PLAYING = 8
    public static val ERROR_CODE_END_OF_QUEUE = 11
    public static val ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7
    public static val ERROR_CODE_NOT_SUPPORTED = 2
    public static val ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6
    public static val ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4
    public static val ERROR_CODE_SKIP_LIMIT_REACHED = 9
    public static val ERROR_CODE_UNKNOWN_ERROR = 0
    private static val KEYCODE_MEDIA_PAUSE = 127
    private static val KEYCODE_MEDIA_PLAY = 126
    public static val PLAYBACK_POSITION_UNKNOWN = -1
    public static val REPEAT_MODE_ALL = 2
    public static val REPEAT_MODE_GROUP = 3
    public static val REPEAT_MODE_INVALID = -1
    public static val REPEAT_MODE_NONE = 0
    public static val REPEAT_MODE_ONE = 1
    public static val SHUFFLE_MODE_ALL = 1
    public static val SHUFFLE_MODE_GROUP = 2
    public static val SHUFFLE_MODE_INVALID = -1
    public static val SHUFFLE_MODE_NONE = 0
    public static val STATE_BUFFERING = 6
    public static val STATE_CONNECTING = 8
    public static val STATE_ERROR = 7
    public static val STATE_FAST_FORWARDING = 4
    public static val STATE_NONE = 0
    public static val STATE_PAUSED = 2
    public static val STATE_PLAYING = 3
    public static val STATE_REWINDING = 5
    public static val STATE_SKIPPING_TO_NEXT = 10
    public static val STATE_SKIPPING_TO_PREVIOUS = 9
    public static val STATE_SKIPPING_TO_QUEUE_ITEM = 11
    public static val STATE_STOPPED = 1
    final Long mActions
    final Long mActiveItemId
    final Long mBufferedPosition
    List mCustomActions
    final Int mErrorCode
    final CharSequence mErrorMessage
    final Bundle mExtras
    final Long mPosition
    final Float mSpeed
    final Int mState
    private Object mStateObj
    final Long mUpdateTime

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Actions {
    }

    class Builder {
        private Long mActions
        private Long mActiveItemId
        private Long mBufferedPosition
        private final List mCustomActions
        private Int mErrorCode
        private CharSequence mErrorMessage
        private Bundle mExtras
        private Long mPosition
        private Float mRate
        private Int mState
        private Long mUpdateTime

        constructor() {
            this.mCustomActions = ArrayList()
            this.mActiveItemId = -1L
        }

        constructor(PlaybackStateCompat playbackStateCompat) {
            this.mCustomActions = ArrayList()
            this.mActiveItemId = -1L
            this.mState = playbackStateCompat.mState
            this.mPosition = playbackStateCompat.mPosition
            this.mRate = playbackStateCompat.mSpeed
            this.mUpdateTime = playbackStateCompat.mUpdateTime
            this.mBufferedPosition = playbackStateCompat.mBufferedPosition
            this.mActions = playbackStateCompat.mActions
            this.mErrorCode = playbackStateCompat.mErrorCode
            this.mErrorMessage = playbackStateCompat.mErrorMessage
            if (playbackStateCompat.mCustomActions != null) {
                this.mCustomActions.addAll(playbackStateCompat.mCustomActions)
            }
            this.mActiveItemId = playbackStateCompat.mActiveItemId
            this.mExtras = playbackStateCompat.mExtras
        }

        public final Builder addCustomAction(CustomAction customAction) {
            if (customAction == null) {
                throw IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.")
            }
            this.mCustomActions.add(customAction)
            return this
        }

        public final Builder addCustomAction(String str, String str2, Int i) {
            return addCustomAction(CustomAction(str, str2, i, null))
        }

        public final PlaybackStateCompat build() {
            return PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorCode, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras)
        }

        public final Builder setActions(Long j) {
            this.mActions = j
            return this
        }

        public final Builder setActiveQueueItemId(Long j) {
            this.mActiveItemId = j
            return this
        }

        public final Builder setBufferedPosition(Long j) {
            this.mBufferedPosition = j
            return this
        }

        public final Builder setErrorMessage(Int i, CharSequence charSequence) {
            this.mErrorCode = i
            this.mErrorMessage = charSequence
            return this
        }

        public final Builder setErrorMessage(CharSequence charSequence) {
            this.mErrorMessage = charSequence
            return this
        }

        public final Builder setExtras(Bundle bundle) {
            this.mExtras = bundle
            return this
        }

        public final Builder setState(Int i, Long j, Float f) {
            return setState(i, j, f, SystemClock.elapsedRealtime())
        }

        public final Builder setState(Int i, Long j, Float f, Long j2) {
            this.mState = i
            this.mPosition = j
            this.mUpdateTime = j2
            this.mRate = f
            return this
        }
    }

    class CustomAction implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.PlaybackStateCompat.CustomAction.1
            @Override // android.os.Parcelable.Creator
            public final CustomAction createFromParcel(Parcel parcel) {
                return CustomAction(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<CustomAction> newArray(Int i) {
                return new CustomAction[i]
            }
        }
        private final String mAction
        private Object mCustomActionObj
        private final Bundle mExtras
        private final Int mIcon
        private final CharSequence mName

        class Builder {
            private final String mAction
            private Bundle mExtras
            private final Int mIcon
            private final CharSequence mName

            constructor(String str, CharSequence charSequence, Int i) {
                if (TextUtils.isEmpty(str)) {
                    throw IllegalArgumentException("You must specify an action to build a CustomAction.")
                }
                if (TextUtils.isEmpty(charSequence)) {
                    throw IllegalArgumentException("You must specify a name to build a CustomAction.")
                }
                if (i == 0) {
                    throw IllegalArgumentException("You must specify an icon resource id to build a CustomAction.")
                }
                this.mAction = str
                this.mName = charSequence
                this.mIcon = i
            }

            public final CustomAction build() {
                return CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras)
            }

            public final Builder setExtras(Bundle bundle) {
                this.mExtras = bundle
                return this
            }
        }

        CustomAction(Parcel parcel) {
            this.mAction = parcel.readString()
            this.mName = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
            this.mIcon = parcel.readInt()
            this.mExtras = parcel.readBundle()
        }

        CustomAction(String str, CharSequence charSequence, Int i, Bundle bundle) {
            this.mAction = str
            this.mName = charSequence
            this.mIcon = i
            this.mExtras = bundle
        }

        fun fromCustomAction(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            CustomAction customAction = CustomAction(PlaybackStateCompatApi21.CustomAction.getAction(obj), PlaybackStateCompatApi21.CustomAction.getName(obj), PlaybackStateCompatApi21.CustomAction.getIcon(obj), PlaybackStateCompatApi21.CustomAction.getExtras(obj))
            customAction.mCustomActionObj = obj
            return customAction
        }

        @Override // android.os.Parcelable
        public final Int describeContents() {
            return 0
        }

        public final String getAction() {
            return this.mAction
        }

        public final Object getCustomAction() {
            if (this.mCustomActionObj != null || Build.VERSION.SDK_INT < 21) {
                return this.mCustomActionObj
            }
            this.mCustomActionObj = PlaybackStateCompatApi21.CustomAction.newInstance(this.mAction, this.mName, this.mIcon, this.mExtras)
            return this.mCustomActionObj
        }

        public final Bundle getExtras() {
            return this.mExtras
        }

        public final Int getIcon() {
            return this.mIcon
        }

        public final CharSequence getName() {
            return this.mName
        }

        public final String toString() {
            return "Action:mName='" + ((Object) this.mName) + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras
        }

        @Override // android.os.Parcelable
        public final Unit writeToParcel(Parcel parcel, Int i) {
            parcel.writeString(this.mAction)
            TextUtils.writeToParcel(this.mName, parcel, i)
            parcel.writeInt(this.mIcon)
            parcel.writeBundle(this.mExtras)
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface MediaKeyAction {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface RepeatMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ShuffleMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    PlaybackStateCompat(Int i, Long j, Long j2, Float f, Long j3, Int i2, CharSequence charSequence, Long j4, List list, Long j5, Bundle bundle) {
        this.mState = i
        this.mPosition = j
        this.mBufferedPosition = j2
        this.mSpeed = f
        this.mActions = j3
        this.mErrorCode = i2
        this.mErrorMessage = charSequence
        this.mUpdateTime = j4
        this.mCustomActions = ArrayList(list)
        this.mActiveItemId = j5
        this.mExtras = bundle
    }

    PlaybackStateCompat(Parcel parcel) {
        this.mState = parcel.readInt()
        this.mPosition = parcel.readLong()
        this.mSpeed = parcel.readFloat()
        this.mUpdateTime = parcel.readLong()
        this.mBufferedPosition = parcel.readLong()
        this.mActions = parcel.readLong()
        this.mErrorMessage = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mCustomActions = parcel.createTypedArrayList(CustomAction.CREATOR)
        this.mActiveItemId = parcel.readLong()
        this.mExtras = parcel.readBundle()
        this.mErrorCode = parcel.readInt()
    }

    fun fromPlaybackState(Object obj) {
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null
        }
        List customActions = PlaybackStateCompatApi21.getCustomActions(obj)
        ArrayList arrayList = null
        if (customActions != null) {
            arrayList = ArrayList(customActions.size())
            Iterator it = customActions.iterator()
            while (it.hasNext()) {
                arrayList.add(CustomAction.fromCustomAction(it.next()))
            }
        }
        PlaybackStateCompat playbackStateCompat = PlaybackStateCompat(PlaybackStateCompatApi21.getState(obj), PlaybackStateCompatApi21.getPosition(obj), PlaybackStateCompatApi21.getBufferedPosition(obj), PlaybackStateCompatApi21.getPlaybackSpeed(obj), PlaybackStateCompatApi21.getActions(obj), 0, PlaybackStateCompatApi21.getErrorMessage(obj), PlaybackStateCompatApi21.getLastPositionUpdateTime(obj), arrayList, PlaybackStateCompatApi21.getActiveQueueItemId(obj), Build.VERSION.SDK_INT >= 22 ? PlaybackStateCompatApi22.getExtras(obj) : null)
        playbackStateCompat.mStateObj = obj
        return playbackStateCompat
    }

    fun toKeyCode(Long j) {
        if (j == 4) {
            return KEYCODE_MEDIA_PLAY
        }
        if (j == 2) {
            return KEYCODE_MEDIA_PAUSE
        }
        if (j == 32) {
            return 87
        }
        if (j == 16) {
            return 88
        }
        if (j == 1) {
            return 86
        }
        if (j == 64) {
            return 90
        }
        if (j == 8) {
            return 89
        }
        return j == 512 ? 85 : 0
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    public final Long getActions() {
        return this.mActions
    }

    public final Long getActiveQueueItemId() {
        return this.mActiveItemId
    }

    public final Long getBufferedPosition() {
        return this.mBufferedPosition
    }

    public final List getCustomActions() {
        return this.mCustomActions
    }

    public final Int getErrorCode() {
        return this.mErrorCode
    }

    public final CharSequence getErrorMessage() {
        return this.mErrorMessage
    }

    @Nullable
    public final Bundle getExtras() {
        return this.mExtras
    }

    public final Long getLastPositionUpdateTime() {
        return this.mUpdateTime
    }

    public final Float getPlaybackSpeed() {
        return this.mSpeed
    }

    public final Object getPlaybackState() {
        if (this.mStateObj == null && Build.VERSION.SDK_INT >= 21) {
            ArrayList arrayList = null
            if (this.mCustomActions != null) {
                arrayList = ArrayList(this.mCustomActions.size())
                Iterator it = this.mCustomActions.iterator()
                while (it.hasNext()) {
                    arrayList.add(((CustomAction) it.next()).getCustomAction())
                }
            }
            if (Build.VERSION.SDK_INT >= 22) {
                this.mStateObj = PlaybackStateCompatApi22.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId, this.mExtras)
            } else {
                this.mStateObj = PlaybackStateCompatApi21.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId)
            }
        }
        return this.mStateObj
    }

    public final Long getPosition() {
        return this.mPosition
    }

    public final Int getState() {
        return this.mState
    }

    public final String toString() {
        StringBuilder sb = StringBuilder("PlaybackState {")
        sb.append("state=").append(this.mState)
        sb.append(", position=").append(this.mPosition)
        sb.append(", buffered position=").append(this.mBufferedPosition)
        sb.append(", speed=").append(this.mSpeed)
        sb.append(", updated=").append(this.mUpdateTime)
        sb.append(", actions=").append(this.mActions)
        sb.append(", error code=").append(this.mErrorCode)
        sb.append(", error message=").append(this.mErrorMessage)
        sb.append(", custom actions=").append(this.mCustomActions)
        sb.append(", active item id=").append(this.mActiveItemId)
        sb.append("}")
        return sb.toString()
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeInt(this.mState)
        parcel.writeLong(this.mPosition)
        parcel.writeFloat(this.mSpeed)
        parcel.writeLong(this.mUpdateTime)
        parcel.writeLong(this.mBufferedPosition)
        parcel.writeLong(this.mActions)
        TextUtils.writeToParcel(this.mErrorMessage, parcel, i)
        parcel.writeTypedList(this.mCustomActions)
        parcel.writeLong(this.mActiveItemId)
        parcel.writeBundle(this.mExtras)
        parcel.writeInt(this.mErrorCode)
    }
}
