package android.support.v4.app

import android.app.Notification
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.compat.R
import android.support.v4.app.Person
import android.support.v4.text.BidiFormatter
import androidx.core.view.ViewCompat
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.TextAppearanceSpan
import android.util.SparseArray
import android.widget.RemoteViews
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.NumberFormat
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Iterator
import java.util.List

class NotificationCompat {
    public static val BADGE_ICON_LARGE = 2
    public static val BADGE_ICON_NONE = 0
    public static val BADGE_ICON_SMALL = 1
    public static val CATEGORY_ALARM = "alarm"
    public static val CATEGORY_CALL = "call"
    public static val CATEGORY_EMAIL = "email"
    public static val CATEGORY_ERROR = "err"
    public static val CATEGORY_EVENT = "event"
    public static val CATEGORY_MESSAGE = "msg"
    public static val CATEGORY_PROGRESS = "progress"
    public static val CATEGORY_PROMO = "promo"
    public static val CATEGORY_RECOMMENDATION = "recommendation"
    public static val CATEGORY_REMINDER = "reminder"
    public static val CATEGORY_SERVICE = "service"
    public static val CATEGORY_SOCIAL = "social"
    public static val CATEGORY_STATUS = "status"
    public static val CATEGORY_SYSTEM = "sys"
    public static val CATEGORY_TRANSPORT = "transport"

    @ColorInt
    public static val COLOR_DEFAULT = 0
    public static val DEFAULT_ALL = -1
    public static val DEFAULT_LIGHTS = 4
    public static val DEFAULT_SOUND = 1
    public static val DEFAULT_VIBRATE = 2
    public static val EXTRA_AUDIO_CONTENTS_URI = "android.audioContents"
    public static val EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri"
    public static val EXTRA_BIG_TEXT = "android.bigText"
    public static val EXTRA_COMPACT_ACTIONS = "android.compactActions"
    public static val EXTRA_CONVERSATION_TITLE = "android.conversationTitle"
    public static val EXTRA_HIDDEN_CONVERSATION_TITLE = "android.hiddenConversationTitle"
    public static val EXTRA_INFO_TEXT = "android.infoText"
    public static val EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation"
    public static val EXTRA_LARGE_ICON = "android.largeIcon"
    public static val EXTRA_LARGE_ICON_BIG = "android.largeIcon.big"
    public static val EXTRA_MEDIA_SESSION = "android.mediaSession"
    public static val EXTRA_MESSAGES = "android.messages"
    public static val EXTRA_MESSAGING_STYLE_USER = "android.messagingStyleUser"
    public static val EXTRA_PEOPLE = "android.people"
    public static val EXTRA_PICTURE = "android.picture"
    public static val EXTRA_PROGRESS = "android.progress"
    public static val EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate"
    public static val EXTRA_PROGRESS_MAX = "android.progressMax"
    public static val EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory"
    public static val EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName"
    public static val EXTRA_SHOW_CHRONOMETER = "android.showChronometer"
    public static val EXTRA_SHOW_WHEN = "android.showWhen"
    public static val EXTRA_SMALL_ICON = "android.icon"
    public static val EXTRA_SUB_TEXT = "android.subText"
    public static val EXTRA_SUMMARY_TEXT = "android.summaryText"
    public static val EXTRA_TEMPLATE = "android.template"
    public static val EXTRA_TEXT = "android.text"
    public static val EXTRA_TEXT_LINES = "android.textLines"
    public static val EXTRA_TITLE = "android.title"
    public static val EXTRA_TITLE_BIG = "android.title.big"
    public static val FLAG_AUTO_CANCEL = 16
    public static val FLAG_FOREGROUND_SERVICE = 64
    public static val FLAG_GROUP_SUMMARY = 512

    @Deprecated
    public static val FLAG_HIGH_PRIORITY = 128
    public static val FLAG_INSISTENT = 4
    public static val FLAG_LOCAL_ONLY = 256
    public static val FLAG_NO_CLEAR = 32
    public static val FLAG_ONGOING_EVENT = 2
    public static val FLAG_ONLY_ALERT_ONCE = 8
    public static val FLAG_SHOW_LIGHTS = 1
    public static val GROUP_ALERT_ALL = 0
    public static val GROUP_ALERT_CHILDREN = 2
    public static val GROUP_ALERT_SUMMARY = 1
    public static val PRIORITY_DEFAULT = 0
    public static val PRIORITY_HIGH = 1
    public static val PRIORITY_LOW = -1
    public static val PRIORITY_MAX = 2
    public static val PRIORITY_MIN = -2
    public static val STREAM_DEFAULT = -1
    public static val VISIBILITY_PRIVATE = 0
    public static val VISIBILITY_PUBLIC = 1
    public static val VISIBILITY_SECRET = -1

    class Action {
        static val EXTRA_SEMANTIC_ACTION = "android.support.action.semanticAction"
        static val EXTRA_SHOWS_USER_INTERFACE = "android.support.action.showsUserInterface"
        public static val SEMANTIC_ACTION_ARCHIVE = 5
        public static val SEMANTIC_ACTION_CALL = 10
        public static val SEMANTIC_ACTION_DELETE = 4
        public static val SEMANTIC_ACTION_MARK_AS_READ = 2
        public static val SEMANTIC_ACTION_MARK_AS_UNREAD = 3
        public static val SEMANTIC_ACTION_MUTE = 6
        public static val SEMANTIC_ACTION_NONE = 0
        public static val SEMANTIC_ACTION_REPLY = 1
        public static val SEMANTIC_ACTION_THUMBS_DOWN = 9
        public static val SEMANTIC_ACTION_THUMBS_UP = 8
        public static val SEMANTIC_ACTION_UNMUTE = 7
        public PendingIntent actionIntent
        public Int icon
        private Boolean mAllowGeneratedReplies
        private final Array<RemoteInput> mDataOnlyRemoteInputs
        final Bundle mExtras
        private final Array<RemoteInput> mRemoteInputs
        private final Int mSemanticAction
        Boolean mShowsUserInterface
        public CharSequence title

        class Builder {
            private Boolean mAllowGeneratedReplies
            private final Bundle mExtras
            private final Int mIcon
            private final PendingIntent mIntent
            private ArrayList mRemoteInputs
            private Int mSemanticAction
            private Boolean mShowsUserInterface
            private final CharSequence mTitle

            constructor(Int i, CharSequence charSequence, PendingIntent pendingIntent) {
                this(i, charSequence, pendingIntent, Bundle(), null, true, 0, true)
            }

            private constructor(Int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, Array<RemoteInput> remoteInputArr, Boolean z, Int i2, Boolean z2) {
                this.mAllowGeneratedReplies = true
                this.mShowsUserInterface = true
                this.mIcon = i
                this.mTitle = Builder.limitCharSequenceLength(charSequence)
                this.mIntent = pendingIntent
                this.mExtras = bundle
                this.mRemoteInputs = remoteInputArr == null ? null : ArrayList(Arrays.asList(remoteInputArr))
                this.mAllowGeneratedReplies = z
                this.mSemanticAction = i2
                this.mShowsUserInterface = z2
            }

            constructor(Action action) {
                this(action.icon, action.title, action.actionIntent, Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.mShowsUserInterface)
            }

            public final Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle)
                }
                return this
            }

            public final Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = ArrayList()
                }
                this.mRemoteInputs.add(remoteInput)
                return this
            }

            public final Action build() {
                ArrayList arrayList = ArrayList()
                ArrayList arrayList2 = ArrayList()
                if (this.mRemoteInputs != null) {
                    Iterator it = this.mRemoteInputs.iterator()
                    while (it.hasNext()) {
                        RemoteInput remoteInput = (RemoteInput) it.next()
                        if (remoteInput.isDataOnly()) {
                            arrayList.add(remoteInput)
                        } else {
                            arrayList2.add(remoteInput)
                        }
                    }
                }
                return Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayList2.isEmpty() ? null : (Array<RemoteInput>) arrayList2.toArray(new RemoteInput[arrayList2.size()]), arrayList.isEmpty() ? null : (Array<RemoteInput>) arrayList.toArray(new RemoteInput[arrayList.size()]), this.mAllowGeneratedReplies, this.mSemanticAction, this.mShowsUserInterface)
            }

            public final Builder extend(Extender extender) {
                extender.extend(this)
                return this
            }

            public final Bundle getExtras() {
                return this.mExtras
            }

            public final Builder setAllowGeneratedReplies(Boolean z) {
                this.mAllowGeneratedReplies = z
                return this
            }

            public final Builder setSemanticAction(Int i) {
                this.mSemanticAction = i
                return this
            }

            public final Builder setShowsUserInterface(Boolean z) {
                this.mShowsUserInterface = z
                return this
            }
        }

        public interface Extender {
            Builder extend(Builder builder)
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        class WearableExtender implements Extender {
            private static val DEFAULT_FLAGS = 1
            private static val EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS"
            private static val FLAG_AVAILABLE_OFFLINE = 1
            private static val FLAG_HINT_DISPLAY_INLINE = 4
            private static val FLAG_HINT_LAUNCHES_ACTIVITY = 2
            private static val KEY_CANCEL_LABEL = "cancelLabel"
            private static val KEY_CONFIRM_LABEL = "confirmLabel"
            private static val KEY_FLAGS = "flags"
            private static val KEY_IN_PROGRESS_LABEL = "inProgressLabel"
            private CharSequence mCancelLabel
            private CharSequence mConfirmLabel
            private Int mFlags
            private CharSequence mInProgressLabel

            constructor() {
                this.mFlags = 1
            }

            constructor(Action action) {
                this.mFlags = 1
                Bundle bundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS)
                if (bundle != null) {
                    this.mFlags = bundle.getInt(KEY_FLAGS, 1)
                    this.mInProgressLabel = bundle.getCharSequence(KEY_IN_PROGRESS_LABEL)
                    this.mConfirmLabel = bundle.getCharSequence(KEY_CONFIRM_LABEL)
                    this.mCancelLabel = bundle.getCharSequence(KEY_CANCEL_LABEL)
                }
            }

            private fun setFlag(Int i, Boolean z) {
                if (z) {
                    this.mFlags |= i
                } else {
                    this.mFlags &= i ^ (-1)
                }
            }

            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public final WearableExtender m0clone() {
                WearableExtender wearableExtender = WearableExtender()
                wearableExtender.mFlags = this.mFlags
                wearableExtender.mInProgressLabel = this.mInProgressLabel
                wearableExtender.mConfirmLabel = this.mConfirmLabel
                wearableExtender.mCancelLabel = this.mCancelLabel
                return wearableExtender
            }

            @Override // android.support.v4.app.NotificationCompat.Action.Extender
            public final Builder extend(Builder builder) {
                Bundle bundle = Bundle()
                if (this.mFlags != 1) {
                    bundle.putInt(KEY_FLAGS, this.mFlags)
                }
                if (this.mInProgressLabel != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel)
                }
                if (this.mConfirmLabel != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel)
                }
                if (this.mCancelLabel != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel)
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle)
                return builder
            }

            @Deprecated
            public final CharSequence getCancelLabel() {
                return this.mCancelLabel
            }

            @Deprecated
            public final CharSequence getConfirmLabel() {
                return this.mConfirmLabel
            }

            public final Boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0
            }

            public final Boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0
            }

            @Deprecated
            public final CharSequence getInProgressLabel() {
                return this.mInProgressLabel
            }

            public final Boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0
            }

            public final WearableExtender setAvailableOffline(Boolean z) {
                setFlag(1, z)
                return this
            }

            @Deprecated
            public final WearableExtender setCancelLabel(CharSequence charSequence) {
                this.mCancelLabel = charSequence
                return this
            }

            @Deprecated
            public final WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.mConfirmLabel = charSequence
                return this
            }

            public final WearableExtender setHintDisplayActionInline(Boolean z) {
                setFlag(4, z)
                return this
            }

            public final WearableExtender setHintLaunchesActivity(Boolean z) {
                setFlag(2, z)
                return this
            }

            @Deprecated
            public final WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.mInProgressLabel = charSequence
                return this
            }
        }

        constructor(Int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(i, charSequence, pendingIntent, Bundle(), null, null, true, 0, true)
        }

        Action(Int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, Array<RemoteInput> remoteInputArr, Array<RemoteInput> remoteInputArr2, Boolean z, Int i2, Boolean z2) {
            this.mShowsUserInterface = true
            this.icon = i
            this.title = Builder.limitCharSequenceLength(charSequence)
            this.actionIntent = pendingIntent
            this.mExtras = bundle == null ? Bundle() : bundle
            this.mRemoteInputs = remoteInputArr
            this.mDataOnlyRemoteInputs = remoteInputArr2
            this.mAllowGeneratedReplies = z
            this.mSemanticAction = i2
            this.mShowsUserInterface = z2
        }

        fun getActionIntent() {
            return this.actionIntent
        }

        fun getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies
        }

        public Array<RemoteInput> getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs
        }

        fun getExtras() {
            return this.mExtras
        }

        fun getIcon() {
            return this.icon
        }

        public Array<RemoteInput> getRemoteInputs() {
            return this.mRemoteInputs
        }

        fun getSemanticAction() {
            return this.mSemanticAction
        }

        fun getShowsUserInterface() {
            return this.mShowsUserInterface
        }

        fun getTitle() {
            return this.title
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface BadgeIconType {
    }

    class BigPictureStyle extends Style {
        private Bitmap mBigLargeIcon
        private Boolean mBigLargeIconSet
        private Bitmap mPicture

        constructor() {
        }

        constructor(Builder builder) {
            setBuilder(builder)
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.BigPictureStyle bigPictureStyleBigPicture = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigPicture(this.mPicture)
                if (this.mBigLargeIconSet) {
                    bigPictureStyleBigPicture.bigLargeIcon(this.mBigLargeIcon)
                }
                if (this.mSummaryTextSet) {
                    bigPictureStyleBigPicture.setSummaryText(this.mSummaryText)
                }
            }
        }

        fun bigLargeIcon(Bitmap bitmap) {
            this.mBigLargeIcon = bitmap
            this.mBigLargeIconSet = true
            return this
        }

        fun bigPicture(Bitmap bitmap) {
            this.mPicture = bitmap
            return this
        }

        fun setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence)
            return this
        }

        fun setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence)
            this.mSummaryTextSet = true
            return this
        }
    }

    class BigTextStyle extends Style {
        private CharSequence mBigText

        constructor() {
        }

        constructor(Builder builder) {
            setBuilder(builder)
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText)
                if (this.mSummaryTextSet) {
                    bigTextStyleBigText.setSummaryText(this.mSummaryText)
                }
            }
        }

        fun bigText(CharSequence charSequence) {
            this.mBigText = Builder.limitCharSequenceLength(charSequence)
            return this
        }

        fun setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence)
            return this
        }

        fun setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence)
            this.mSummaryTextSet = true
            return this
        }
    }

    class Builder {
        private static val MAX_CHARSEQUENCE_LENGTH = 5120

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public ArrayList mActions
        Int mBadgeIcon
        RemoteViews mBigContentView
        String mCategory
        String mChannelId
        Int mColor
        Boolean mColorized
        Boolean mColorizedSet
        CharSequence mContentInfo
        PendingIntent mContentIntent
        CharSequence mContentText
        CharSequence mContentTitle
        RemoteViews mContentView

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Context mContext
        Bundle mExtras
        PendingIntent mFullScreenIntent
        Int mGroupAlertBehavior
        String mGroupKey
        Boolean mGroupSummary
        RemoteViews mHeadsUpContentView
        ArrayList mInvisibleActions
        Bitmap mLargeIcon
        Boolean mLocalOnly
        Notification mNotification
        Int mNumber

        @Deprecated
        public ArrayList mPeople
        Int mPriority
        Int mProgress
        Boolean mProgressIndeterminate
        Int mProgressMax
        Notification mPublicVersion
        Array<CharSequence> mRemoteInputHistory
        String mShortcutId
        Boolean mShowWhen
        String mSortKey
        Style mStyle
        CharSequence mSubText
        RemoteViews mTickerView
        Long mTimeout
        Boolean mUseChronometer
        Int mVisibility

        @Deprecated
        constructor(Context context) {
            this(context, null)
        }

        constructor(@NonNull Context context, @NonNull String str) {
            this.mActions = ArrayList()
            this.mInvisibleActions = ArrayList()
            this.mShowWhen = true
            this.mLocalOnly = false
            this.mColor = 0
            this.mVisibility = 0
            this.mBadgeIcon = 0
            this.mGroupAlertBehavior = 0
            this.mNotification = Notification()
            this.mContext = context
            this.mChannelId = str
            this.mNotification.when = System.currentTimeMillis()
            this.mNotification.audioStreamType = -1
            this.mPriority = 0
            this.mPeople = ArrayList()
        }

        protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > MAX_CHARSEQUENCE_LENGTH) ? charSequence.subSequence(0, MAX_CHARSEQUENCE_LENGTH) : charSequence
        }

        private fun reduceLargeIconSize(Bitmap bitmap) throws Resources.NotFoundException {
            if (bitmap == null || Build.VERSION.SDK_INT >= 27) {
                return bitmap
            }
            Resources resources = this.mContext.getResources()
            Int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_width)
            Int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_height)
            if (bitmap.getWidth() <= dimensionPixelSize && bitmap.getHeight() <= dimensionPixelSize2) {
                return bitmap
            }
            Double dMin = Math.min(dimensionPixelSize / Math.max(1, bitmap.getWidth()), dimensionPixelSize2 / Math.max(1, bitmap.getHeight()))
            return Bitmap.createScaledBitmap(bitmap, (Int) Math.ceil(bitmap.getWidth() * dMin), (Int) Math.ceil(dMin * bitmap.getHeight()), true)
        }

        private fun setFlag(Int i, Boolean z) {
            if (z) {
                this.mNotification.flags |= i
            } else {
                this.mNotification.flags &= i ^ (-1)
            }
        }

        fun addAction(Int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(Action(i, charSequence, pendingIntent))
            return this
        }

        fun addAction(Action action) {
            this.mActions.add(action)
            return this
        }

        fun addExtras(Bundle bundle) {
            if (bundle != null) {
                if (this.mExtras == null) {
                    this.mExtras = Bundle(bundle)
                } else {
                    this.mExtras.putAll(bundle)
                }
            }
            return this
        }

        @RequiresApi(21)
        fun addInvisibleAction(Int i, CharSequence charSequence, PendingIntent pendingIntent) {
            return addInvisibleAction(Action(i, charSequence, pendingIntent))
        }

        @RequiresApi(21)
        fun addInvisibleAction(Action action) {
            this.mInvisibleActions.add(action)
            return this
        }

        fun addPerson(String str) {
            this.mPeople.add(str)
            return this
        }

        fun build() {
            return NotificationCompatBuilder(this).build()
        }

        fun extend(Extender extender) {
            extender.extend(this)
            return this
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getBigContentView() {
            return this.mBigContentView
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getColor() {
            return this.mColor
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getContentView() {
            return this.mContentView
        }

        fun getExtras() {
            if (this.mExtras == null) {
                this.mExtras = Bundle()
            }
            return this.mExtras
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getHeadsUpContentView() {
            return this.mHeadsUpContentView
        }

        @Deprecated
        fun getNotification() {
            return build()
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getPriority() {
            return this.mPriority
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun getWhenIfShowing() {
            if (this.mShowWhen) {
                return this.mNotification.when
            }
            return 0L
        }

        fun setAutoCancel(Boolean z) {
            setFlag(16, z)
            return this
        }

        fun setBadgeIconType(Int i) {
            this.mBadgeIcon = i
            return this
        }

        fun setCategory(String str) {
            this.mCategory = str
            return this
        }

        fun setChannelId(@NonNull String str) {
            this.mChannelId = str
            return this
        }

        fun setColor(@ColorInt Int i) {
            this.mColor = i
            return this
        }

        fun setColorized(Boolean z) {
            this.mColorized = z
            this.mColorizedSet = true
            return this
        }

        fun setContent(RemoteViews remoteViews) {
            this.mNotification.contentView = remoteViews
            return this
        }

        fun setContentInfo(CharSequence charSequence) {
            this.mContentInfo = limitCharSequenceLength(charSequence)
            return this
        }

        fun setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent
            return this
        }

        fun setContentText(CharSequence charSequence) {
            this.mContentText = limitCharSequenceLength(charSequence)
            return this
        }

        fun setContentTitle(CharSequence charSequence) {
            this.mContentTitle = limitCharSequenceLength(charSequence)
            return this
        }

        fun setCustomBigContentView(RemoteViews remoteViews) {
            this.mBigContentView = remoteViews
            return this
        }

        fun setCustomContentView(RemoteViews remoteViews) {
            this.mContentView = remoteViews
            return this
        }

        fun setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mHeadsUpContentView = remoteViews
            return this
        }

        fun setDefaults(Int i) {
            this.mNotification.defaults = i
            if ((i & 4) != 0) {
                this.mNotification.flags |= 1
            }
            return this
        }

        fun setDeleteIntent(PendingIntent pendingIntent) {
            this.mNotification.deleteIntent = pendingIntent
            return this
        }

        fun setExtras(Bundle bundle) {
            this.mExtras = bundle
            return this
        }

        fun setFullScreenIntent(PendingIntent pendingIntent, Boolean z) {
            this.mFullScreenIntent = pendingIntent
            setFlag(128, z)
            return this
        }

        fun setGroup(String str) {
            this.mGroupKey = str
            return this
        }

        fun setGroupAlertBehavior(Int i) {
            this.mGroupAlertBehavior = i
            return this
        }

        fun setGroupSummary(Boolean z) {
            this.mGroupSummary = z
            return this
        }

        fun setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = reduceLargeIconSize(bitmap)
            return this
        }

        fun setLights(@ColorInt Int i, Int i2, Int i3) {
            this.mNotification.ledARGB = i
            this.mNotification.ledOnMS = i2
            this.mNotification.ledOffMS = i3
            this.mNotification.flags = (this.mNotification.flags & (-2)) | (this.mNotification.ledOnMS != 0 && this.mNotification.ledOffMS != 0 ? 1 : 0)
            return this
        }

        fun setLocalOnly(Boolean z) {
            this.mLocalOnly = z
            return this
        }

        fun setNumber(Int i) {
            this.mNumber = i
            return this
        }

        fun setOngoing(Boolean z) {
            setFlag(2, z)
            return this
        }

        fun setOnlyAlertOnce(Boolean z) {
            setFlag(8, z)
            return this
        }

        fun setPriority(Int i) {
            this.mPriority = i
            return this
        }

        fun setProgress(Int i, Int i2, Boolean z) {
            this.mProgressMax = i
            this.mProgress = i2
            this.mProgressIndeterminate = z
            return this
        }

        fun setPublicVersion(Notification notification) {
            this.mPublicVersion = notification
            return this
        }

        fun setRemoteInputHistory(Array<CharSequence> charSequenceArr) {
            this.mRemoteInputHistory = charSequenceArr
            return this
        }

        fun setShortcutId(String str) {
            this.mShortcutId = str
            return this
        }

        fun setShowWhen(Boolean z) {
            this.mShowWhen = z
            return this
        }

        fun setSmallIcon(Int i) {
            this.mNotification.icon = i
            return this
        }

        fun setSmallIcon(Int i, Int i2) {
            this.mNotification.icon = i
            this.mNotification.iconLevel = i2
            return this
        }

        fun setSortKey(String str) {
            this.mSortKey = str
            return this
        }

        fun setSound(Uri uri) {
            this.mNotification.sound = uri
            this.mNotification.audioStreamType = -1
            if (Build.VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build()
            }
            return this
        }

        fun setSound(Uri uri, Int i) {
            this.mNotification.sound = uri
            this.mNotification.audioStreamType = i
            if (Build.VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(i).build()
            }
            return this
        }

        fun setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this)
                }
            }
            return this
        }

        fun setSubText(CharSequence charSequence) {
            this.mSubText = limitCharSequenceLength(charSequence)
            return this
        }

        fun setTicker(CharSequence charSequence) {
            this.mNotification.tickerText = limitCharSequenceLength(charSequence)
            return this
        }

        fun setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.mNotification.tickerText = limitCharSequenceLength(charSequence)
            this.mTickerView = remoteViews
            return this
        }

        fun setTimeoutAfter(Long j) {
            this.mTimeout = j
            return this
        }

        fun setUsesChronometer(Boolean z) {
            this.mUseChronometer = z
            return this
        }

        fun setVibrate(Array<Long> jArr) {
            this.mNotification.vibrate = jArr
            return this
        }

        fun setVisibility(Int i) {
            this.mVisibility = i
            return this
        }

        fun setWhen(Long j) {
            this.mNotification.when = j
            return this
        }
    }

    class CarExtender implements Extender {

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static val EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS"
        private static val EXTRA_COLOR = "app_color"
        private static val EXTRA_CONVERSATION = "car_conversation"

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static val EXTRA_INVISIBLE_ACTIONS = "invisible_actions"
        private static val EXTRA_LARGE_ICON = "large_icon"
        private static val KEY_AUTHOR = "author"
        private static val KEY_MESSAGES = "messages"
        private static val KEY_ON_READ = "on_read"
        private static val KEY_ON_REPLY = "on_reply"
        private static val KEY_PARTICIPANTS = "participants"
        private static val KEY_REMOTE_INPUT = "remote_input"
        private static val KEY_TEXT = "text"
        private static val KEY_TIMESTAMP = "timestamp"
        private Int mColor
        private Bitmap mLargeIcon
        private UnreadConversation mUnreadConversation

        class UnreadConversation {
            private final Long mLatestTimestamp
            private final Array<String> mMessages
            private final Array<String> mParticipants
            private final PendingIntent mReadPendingIntent
            private final RemoteInput mRemoteInput
            private final PendingIntent mReplyPendingIntent

            class Builder {
                private Long mLatestTimestamp
                private val mMessages = ArrayList()
                private final String mParticipant
                private PendingIntent mReadPendingIntent
                private RemoteInput mRemoteInput
                private PendingIntent mReplyPendingIntent

                constructor(String str) {
                    this.mParticipant = str
                }

                fun addMessage(String str) {
                    this.mMessages.add(str)
                    return this
                }

                fun build() {
                    return UnreadConversation((Array<String>) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new Array<String>{this.mParticipant}, this.mLatestTimestamp)
                }

                fun setLatestTimestamp(Long j) {
                    this.mLatestTimestamp = j
                    return this
                }

                fun setReadPendingIntent(PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent
                    return this
                }

                fun setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput
                    this.mReplyPendingIntent = pendingIntent
                    return this
                }
            }

            UnreadConversation(Array<String> strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, Array<String> strArr2, Long j) {
                this.mMessages = strArr
                this.mRemoteInput = remoteInput
                this.mReadPendingIntent = pendingIntent2
                this.mReplyPendingIntent = pendingIntent
                this.mParticipants = strArr2
                this.mLatestTimestamp = j
            }

            fun getLatestTimestamp() {
                return this.mLatestTimestamp
            }

            public Array<String> getMessages() {
                return this.mMessages
            }

            fun getParticipant() {
                if (this.mParticipants.length > 0) {
                    return this.mParticipants[0]
                }
                return null
            }

            public Array<String> getParticipants() {
                return this.mParticipants
            }

            fun getReadPendingIntent() {
                return this.mReadPendingIntent
            }

            fun getRemoteInput() {
                return this.mRemoteInput
            }

            fun getReplyPendingIntent() {
                return this.mReplyPendingIntent
            }
        }

        constructor() {
            this.mColor = 0
        }

        constructor(Notification notification) {
            this.mColor = 0
            if (Build.VERSION.SDK_INT < 21) {
                return
            }
            Bundle bundle = NotificationCompat.getExtras(notification) == null ? null : NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER)
            if (bundle != null) {
                this.mLargeIcon = (Bitmap) bundle.getParcelable(EXTRA_LARGE_ICON)
                this.mColor = bundle.getInt(EXTRA_COLOR, 0)
                this.mUnreadConversation = getUnreadConversationFromBundle(bundle.getBundle(EXTRA_CONVERSATION))
            }
        }

        @RequiresApi(21)
        private fun getBundleForUnreadConversation(@NonNull UnreadConversation unreadConversation) {
            Bundle bundle = Bundle()
            String str = null
            if (unreadConversation.getParticipants() != null && unreadConversation.getParticipants().length > 1) {
                str = unreadConversation.getParticipants()[0]
            }
            Array<Parcelable> parcelableArr = new Parcelable[unreadConversation.getMessages().length]
            for (Int i = 0; i < parcelableArr.length; i++) {
                Bundle bundle2 = Bundle()
                bundle2.putString(KEY_TEXT, unreadConversation.getMessages()[i])
                bundle2.putString(KEY_AUTHOR, str)
                parcelableArr[i] = bundle2
            }
            bundle.putParcelableArray(KEY_MESSAGES, parcelableArr)
            RemoteInput remoteInput = unreadConversation.getRemoteInput()
            if (remoteInput != null) {
                bundle.putParcelable(KEY_REMOTE_INPUT, new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build())
            }
            bundle.putParcelable(KEY_ON_REPLY, unreadConversation.getReplyPendingIntent())
            bundle.putParcelable(KEY_ON_READ, unreadConversation.getReadPendingIntent())
            bundle.putStringArray(KEY_PARTICIPANTS, unreadConversation.getParticipants())
            bundle.putLong(KEY_TIMESTAMP, unreadConversation.getLatestTimestamp())
            return bundle
        }

        @RequiresApi(21)
        private fun getUnreadConversationFromBundle(@Nullable Bundle bundle) {
            Array<String> strArr
            Boolean z = false
            if (bundle == null) {
                return null
            }
            Array<Parcelable> parcelableArray = bundle.getParcelableArray(KEY_MESSAGES)
            if (parcelableArray != null) {
                Array<String> strArr2 = new String[parcelableArray.length]
                Int i = 0
                while (true) {
                    if (i >= strArr2.length) {
                        z = true
                        break
                    }
                    if (!(parcelableArray[i] instanceof Bundle)) {
                        break
                    }
                    strArr2[i] = ((Bundle) parcelableArray[i]).getString(KEY_TEXT)
                    if (strArr2[i] == null) {
                        break
                    }
                    i++
                }
                if (!z) {
                    return null
                }
                strArr = strArr2
            } else {
                strArr = null
            }
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(KEY_ON_READ)
            PendingIntent pendingIntent2 = (PendingIntent) bundle.getParcelable(KEY_ON_REPLY)
            android.app.RemoteInput remoteInput = (android.app.RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT)
            Array<String> stringArray = bundle.getStringArray(KEY_PARTICIPANTS)
            if (stringArray == null || stringArray.length != 1) {
                return null
            }
            return UnreadConversation(strArr, remoteInput != null ? RemoteInput(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null) : null, pendingIntent2, pendingIntent, stringArray, bundle.getLong(KEY_TIMESTAMP))
        }

        @Override // android.support.v4.app.NotificationCompat.Extender
        public final Builder extend(Builder builder) {
            if (Build.VERSION.SDK_INT >= 21) {
                Bundle bundle = Bundle()
                if (this.mLargeIcon != null) {
                    bundle.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon)
                }
                if (this.mColor != 0) {
                    bundle.putInt(EXTRA_COLOR, this.mColor)
                }
                if (this.mUnreadConversation != null) {
                    bundle.putBundle(EXTRA_CONVERSATION, getBundleForUnreadConversation(this.mUnreadConversation))
                }
                builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle)
            }
            return builder
        }

        @ColorInt
        public final Int getColor() {
            return this.mColor
        }

        public final Bitmap getLargeIcon() {
            return this.mLargeIcon
        }

        public final UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation
        }

        public final CarExtender setColor(@ColorInt Int i) {
            this.mColor = i
            return this
        }

        public final CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap
            return this
        }

        public final CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation
            return this
        }
    }

    class DecoratedCustomViewStyle extends Style {
        private static val MAX_ACTION_BUTTONS = 3

        private fun createRemoteViews(RemoteViews remoteViews, Boolean z) {
            Boolean z2
            Int iMin
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(true, R.layout.notification_template_custom_big, false)
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.actions)
            if (!z || this.mBuilder.mActions == null || (iMin = Math.min(this.mBuilder.mActions.size(), 3)) <= 0) {
                z2 = false
            } else {
                for (Int i = 0; i < iMin; i++) {
                    remoteViewsApplyStandardTemplate.addView(R.id.actions, generateActionButton((Action) this.mBuilder.mActions.get(i)))
                }
                z2 = true
            }
            Int i2 = z2 ? 0 : 8
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.actions, i2)
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.action_divider, i2)
            buildIntoRemoteViews(remoteViewsApplyStandardTemplate, remoteViews)
            return remoteViewsApplyStandardTemplate
        }

        private fun generateActionButton(Action action) {
            Boolean z = action.actionIntent == null
            RemoteViews remoteViews = RemoteViews(this.mBuilder.mContext.getPackageName(), z ? R.layout.notification_action_tombstone : R.layout.notification_action)
            remoteViews.setImageViewBitmap(R.id.action_image, createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)))
            remoteViews.setTextViewText(R.id.action_text, action.title)
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent)
            }
            if (Build.VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action_container, action.title)
            }
            return remoteViews
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(new Notification.DecoratedCustomViewStyle())
            }
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView()
            if (bigContentView == null) {
                bigContentView = this.mBuilder.getContentView()
            }
            if (bigContentView != null) {
                return createRemoteViews(bigContentView, true)
            }
            return null
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return createRemoteViews(this.mBuilder.getContentView(), false)
            }
            return null
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null
            }
            RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView()
            RemoteViews contentView = headsUpContentView != null ? headsUpContentView : this.mBuilder.getContentView()
            if (headsUpContentView != null) {
                return createRemoteViews(contentView, true)
            }
            return null
        }
    }

    public interface Extender {
        Builder extend(Builder builder)
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface GroupAlertBehavior {
    }

    class InboxStyle extends Style {
        private ArrayList mTexts = ArrayList()

        constructor() {
        }

        constructor(Builder builder) {
            setBuilder(builder)
        }

        fun addLine(CharSequence charSequence) {
            this.mTexts.add(Builder.limitCharSequenceLength(charSequence))
            return this
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle)
                if (this.mSummaryTextSet) {
                    bigContentTitle.setSummaryText(this.mSummaryText)
                }
                Iterator it = this.mTexts.iterator()
                while (it.hasNext()) {
                    bigContentTitle.addLine((CharSequence) it.next())
                }
            }
        }

        fun setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence)
            return this
        }

        fun setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence)
            this.mSummaryTextSet = true
            return this
        }
    }

    class MessagingStyle extends Style {
        public static val MAXIMUM_RETAINED_MESSAGES = 25

        @Nullable
        private CharSequence mConversationTitle

        @Nullable
        private Boolean mIsGroupConversation
        private val mMessages = ArrayList()
        private Person mUser

        class Message {
            static val KEY_DATA_MIME_TYPE = "type"
            static val KEY_DATA_URI = "uri"
            static val KEY_EXTRAS_BUNDLE = "extras"
            static val KEY_NOTIFICATION_PERSON = "sender_person"
            static val KEY_PERSON = "person"
            static val KEY_SENDER = "sender"
            static val KEY_TEXT = "text"
            static val KEY_TIMESTAMP = "time"

            @Nullable
            private String mDataMimeType

            @Nullable
            private Uri mDataUri
            private Bundle mExtras

            @Nullable
            private final Person mPerson
            private final CharSequence mText
            private final Long mTimestamp

            constructor(CharSequence charSequence, Long j, @Nullable Person person) {
                this.mExtras = Bundle()
                this.mText = charSequence
                this.mTimestamp = j
                this.mPerson = person
            }

            @Deprecated
            constructor(CharSequence charSequence, Long j, CharSequence charSequence2) {
                this(charSequence, j, new Person.Builder().setName(charSequence2).build())
            }

            @NonNull
            static Array<Bundle> getBundleArrayForMessages(List list) {
                Array<Bundle> bundleArr = new Bundle[list.size()]
                Int size = list.size()
                for (Int i = 0; i < size; i++) {
                    bundleArr[i] = ((Message) list.get(i)).toBundle()
                }
                return bundleArr
            }

            @Nullable
            static Message getMessageFromBundle(Bundle bundle) {
                try {
                    if (!bundle.containsKey(KEY_TEXT) || !bundle.containsKey(KEY_TIMESTAMP)) {
                        return null
                    }
                    Message message = Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.containsKey(KEY_PERSON) ? Person.fromBundle(bundle.getBundle(KEY_PERSON)) : (!bundle.containsKey(KEY_NOTIFICATION_PERSON) || Build.VERSION.SDK_INT < 28) ? bundle.containsKey(KEY_SENDER) ? new Person.Builder().setName(bundle.getCharSequence(KEY_SENDER)).build() : null : Person.fromAndroidPerson((android.app.Person) bundle.getParcelable(KEY_NOTIFICATION_PERSON)))
                    if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                        message.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri) bundle.getParcelable(KEY_DATA_URI))
                    }
                    if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                        message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE))
                    }
                    return message
                } catch (ClassCastException e) {
                    return null
                }
            }

            @NonNull
            static List getMessagesFromBundleArray(Array<Parcelable> parcelableArr) {
                Message messageFromBundle
                ArrayList arrayList = ArrayList(parcelableArr.length)
                Int i = 0
                while (true) {
                    Int i2 = i
                    if (i2 >= parcelableArr.length) {
                        return arrayList
                    }
                    if ((parcelableArr[i2] instanceof Bundle) && (messageFromBundle = getMessageFromBundle((Bundle) parcelableArr[i2])) != null) {
                        arrayList.add(messageFromBundle)
                    }
                    i = i2 + 1
                }
            }

            private fun toBundle() {
                Bundle bundle = Bundle()
                if (this.mText != null) {
                    bundle.putCharSequence(KEY_TEXT, this.mText)
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp)
                if (this.mPerson != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mPerson.getName())
                    if (Build.VERSION.SDK_INT >= 28) {
                        bundle.putParcelable(KEY_NOTIFICATION_PERSON, this.mPerson.toAndroidPerson())
                    } else {
                        bundle.putBundle(KEY_PERSON, this.mPerson.toBundle())
                    }
                }
                if (this.mDataMimeType != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType)
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable(KEY_DATA_URI, this.mDataUri)
                }
                if (this.mExtras != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras)
                }
                return bundle
            }

            @Nullable
            public final String getDataMimeType() {
                return this.mDataMimeType
            }

            @Nullable
            public final Uri getDataUri() {
                return this.mDataUri
            }

            @NonNull
            public final Bundle getExtras() {
                return this.mExtras
            }

            @Nullable
            public final Person getPerson() {
                return this.mPerson
            }

            @Nullable
            @Deprecated
            public final CharSequence getSender() {
                if (this.mPerson == null) {
                    return null
                }
                return this.mPerson.getName()
            }

            @NonNull
            public final CharSequence getText() {
                return this.mText
            }

            public final Long getTimestamp() {
                return this.mTimestamp
            }

            public final Message setData(String str, Uri uri) {
                this.mDataMimeType = str
                this.mDataUri = uri
                return this
            }
        }

        private constructor() {
        }

        constructor(@NonNull Person person) {
            if (TextUtils.isEmpty(person.getName())) {
                throw IllegalArgumentException("User's name must not be empty.")
            }
            this.mUser = person
        }

        @Deprecated
        constructor(@NonNull CharSequence charSequence) {
            this.mUser = new Person.Builder().setName(charSequence).build()
        }

        @Nullable
        fun extractMessagingStyleFromNotification(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification)
            if (extras != null && !extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME) && !extras.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                return null
            }
            try {
                MessagingStyle messagingStyle = MessagingStyle()
                messagingStyle.restoreFromCompatExtras(extras)
                return messagingStyle
            } catch (ClassCastException e) {
                return null
            }
        }

        @Nullable
        private fun findLatestIncomingMessage() {
            for (Int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = (Message) this.mMessages.get(size)
                if (message.getPerson() != null && !TextUtils.isEmpty(message.getPerson().getName())) {
                    return message
                }
            }
            if (this.mMessages.isEmpty()) {
                return null
            }
            return (Message) this.mMessages.get(this.mMessages.size() - 1)
        }

        private fun hasMessagesWithoutSender() {
            for (Int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = (Message) this.mMessages.get(size)
                if (message.getPerson() != null && message.getPerson().getName() == null) {
                    return true
                }
            }
            return false
        }

        @NonNull
        private fun makeFontColorSpan(Int i) {
            return TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i), null)
        }

        private fun makeMessageLine(Message message) {
            BidiFormatter bidiFormatter = BidiFormatter.getInstance()
            SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder()
            Boolean z = Build.VERSION.SDK_INT >= 21
            Int color = z ? ViewCompat.MEASURED_STATE_MASK : -1
            CharSequence name = message.getPerson() == null ? "" : message.getPerson().getName()
            if (TextUtils.isEmpty(name)) {
                name = this.mUser.getName()
                if (z && this.mBuilder.getColor() != 0) {
                    color = this.mBuilder.getColor()
                }
            }
            CharSequence charSequenceUnicodeWrap = bidiFormatter.unicodeWrap(name)
            spannableStringBuilder.append(charSequenceUnicodeWrap)
            spannableStringBuilder.setSpan(makeFontColorSpan(color), spannableStringBuilder.length() - charSequenceUnicodeWrap.length(), spannableStringBuilder.length(), 33)
            spannableStringBuilder.append((CharSequence) "  ").append(bidiFormatter.unicodeWrap(message.getText() == null ? "" : message.getText()))
            return spannableStringBuilder
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        fun addCompatExtras(Bundle bundle) {
            super.addCompatExtras(bundle)
            bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUser.getName())
            bundle.putBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER, this.mUser.toBundle())
            bundle.putCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE, this.mConversationTitle)
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle)
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages))
            }
            if (this.mIsGroupConversation != null) {
                bundle.putBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION, this.mIsGroupConversation.booleanValue())
            }
        }

        fun addMessage(Message message) {
            this.mMessages.add(message)
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0)
            }
            return this
        }

        fun addMessage(CharSequence charSequence, Long j, Person person) {
            addMessage(Message(charSequence, j, person))
            return this
        }

        @Deprecated
        fun addMessage(CharSequence charSequence, Long j, CharSequence charSequence2) {
            this.mMessages.add(Message(charSequence, j, new Person.Builder().setName(charSequence2).build()))
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0)
            }
            return this
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Notification.MessagingStyle.Message message
            setGroupConversation(isGroupConversation())
            if (Build.VERSION.SDK_INT >= 24) {
                Notification.MessagingStyle messagingStyle = Build.VERSION.SDK_INT >= 28 ? new Notification.MessagingStyle(this.mUser.toAndroidPerson()) : new Notification.MessagingStyle(this.mUser.getName())
                if (this.mIsGroupConversation.booleanValue() || Build.VERSION.SDK_INT >= 28) {
                    messagingStyle.setConversationTitle(this.mConversationTitle)
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    messagingStyle.setGroupConversation(this.mIsGroupConversation.booleanValue())
                }
                for (Message message2 : this.mMessages) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        Person person = message2.getPerson()
                        message = new Notification.MessagingStyle.Message(message2.getText(), message2.getTimestamp(), person == null ? null : person.toAndroidPerson())
                    } else {
                        message = new Notification.MessagingStyle.Message(message2.getText(), message2.getTimestamp(), message2.getPerson() != null ? message2.getPerson().getName() : null)
                    }
                    if (message2.getDataMimeType() != null) {
                        message.setData(message2.getDataMimeType(), message2.getDataUri())
                    }
                    messagingStyle.addMessage(message)
                }
                messagingStyle.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder())
                return
            }
            Message messageFindLatestIncomingMessage = findLatestIncomingMessage()
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle)
            } else if (messageFindLatestIncomingMessage != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle("")
                if (messageFindLatestIncomingMessage.getPerson() != null) {
                    notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(messageFindLatestIncomingMessage.getPerson().getName())
                }
            }
            if (messageFindLatestIncomingMessage != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentText(this.mConversationTitle != null ? makeMessageLine(messageFindLatestIncomingMessage) : messageFindLatestIncomingMessage.getText())
            }
            if (Build.VERSION.SDK_INT >= 16) {
                SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder()
                Boolean z = this.mConversationTitle != null || hasMessagesWithoutSender()
                for (Int size = this.mMessages.size() - 1; size >= 0; size--) {
                    Message message3 = (Message) this.mMessages.get(size)
                    CharSequence charSequenceMakeMessageLine = z ? makeMessageLine(message3) : message3.getText()
                    if (size != this.mMessages.size() - 1) {
                        spannableStringBuilder.insert(0, (CharSequence) "\n")
                    }
                    spannableStringBuilder.insert(0, charSequenceMakeMessageLine)
                }
                new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText(spannableStringBuilder)
            }
        }

        @Nullable
        fun getConversationTitle() {
            return this.mConversationTitle
        }

        fun getMessages() {
            return this.mMessages
        }

        fun getUser() {
            return this.mUser
        }

        @Deprecated
        fun getUserDisplayName() {
            return this.mUser.getName()
        }

        fun isGroupConversation() {
            if (this.mBuilder != null && this.mBuilder.mContext.getApplicationInfo().targetSdkVersion < 28 && this.mIsGroupConversation == null) {
                return this.mConversationTitle != null
            }
            if (this.mIsGroupConversation != null) {
                return this.mIsGroupConversation.booleanValue()
            }
            return false
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected fun restoreFromCompatExtras(Bundle bundle) {
            this.mMessages.clear()
            if (bundle.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                this.mUser = Person.fromBundle(bundle.getBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER))
            } else {
                this.mUser = new Person.Builder().setName(bundle.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)).build()
            }
            this.mConversationTitle = bundle.getCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE)
            if (this.mConversationTitle == null) {
                this.mConversationTitle = bundle.getCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE)
            }
            Array<Parcelable> parcelableArray = bundle.getParcelableArray(NotificationCompat.EXTRA_MESSAGES)
            if (parcelableArray != null) {
                this.mMessages.addAll(Message.getMessagesFromBundleArray(parcelableArray))
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)) {
                this.mIsGroupConversation = Boolean.valueOf(bundle.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION))
            }
        }

        fun setConversationTitle(@Nullable CharSequence charSequence) {
            this.mConversationTitle = charSequence
            return this
        }

        fun setGroupConversation(Boolean z) {
            this.mIsGroupConversation = Boolean.valueOf(z)
            return this
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface NotificationVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface StreamType {
    }

    abstract class Style {
        CharSequence mBigContentTitle

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected Builder mBuilder
        CharSequence mSummaryText
        Boolean mSummaryTextSet = false

        private fun calculateTopPadding() throws Resources.NotFoundException {
            Resources resources = this.mBuilder.mContext.getResources()
            Int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_top_pad)
            Int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text)
            Float fConstrain = (constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f
            return Math.round((fConstrain * dimensionPixelSize2) + (dimensionPixelSize * (1.0f - fConstrain)))
        }

        private fun constrain(Float f, Float f2, Float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f
        }

        private fun createColoredBitmap(Int i, Int i2, Int i3) throws Resources.NotFoundException {
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(i)
            Int intrinsicWidth = i3 == 0 ? drawable.getIntrinsicWidth() : i3
            if (i3 == 0) {
                i3 = drawable.getIntrinsicHeight()
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, i3, Bitmap.Config.ARGB_8888)
            drawable.setBounds(0, 0, intrinsicWidth, i3)
            if (i2 != 0) {
                drawable.mutate().setColorFilter(PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN))
            }
            drawable.draw(Canvas(bitmapCreateBitmap))
            return bitmapCreateBitmap
        }

        private fun createIconWithBackground(Int i, Int i2, Int i3, Int i4) throws Resources.NotFoundException {
            Int i5 = R.drawable.notification_icon_background
            if (i4 == 0) {
                i4 = 0
            }
            Bitmap bitmapCreateColoredBitmap = createColoredBitmap(i5, i4, i2)
            Canvas canvas = Canvas(bitmapCreateColoredBitmap)
            Drawable drawableMutate = this.mBuilder.mContext.getResources().getDrawable(i).mutate()
            drawableMutate.setFilterBitmap(true)
            Int i6 = (i2 - i3) / 2
            drawableMutate.setBounds(i6, i6, i3 + i6, i3 + i6)
            drawableMutate.setColorFilter(PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP))
            drawableMutate.draw(canvas)
            return bitmapCreateColoredBitmap
        }

        private fun hideNormalContent(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8)
            remoteViews.setViewVisibility(R.id.text2, 8)
            remoteViews.setViewVisibility(R.id.text, 8)
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun addCompatExtras(Bundle bundle) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun applyStandardTemplate(Boolean z, Int i, Boolean z2) throws Resources.NotFoundException {
            Boolean z3
            Boolean z4
            Boolean z5
            Boolean z6
            Resources resources = this.mBuilder.mContext.getResources()
            RemoteViews remoteViews = RemoteViews(this.mBuilder.mContext.getPackageName(), i)
            Boolean z7 = this.mBuilder.getPriority() < -1
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                if (z7) {
                    remoteViews.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg_low)
                    remoteViews.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_low_bg)
                } else {
                    remoteViews.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg)
                    remoteViews.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_bg)
                }
            }
            if (this.mBuilder.mLargeIcon != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    remoteViews.setViewVisibility(R.id.icon, 0)
                    remoteViews.setImageViewBitmap(R.id.icon, this.mBuilder.mLargeIcon)
                } else {
                    remoteViews.setViewVisibility(R.id.icon, 8)
                }
                if (z && this.mBuilder.mNotification.icon != 0) {
                    Int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_right_icon_size)
                    Int dimensionPixelSize2 = dimensionPixelSize - (resources.getDimensionPixelSize(R.dimen.notification_small_icon_background_padding) << 1)
                    if (Build.VERSION.SDK_INT >= 21) {
                        remoteViews.setImageViewBitmap(R.id.right_icon, createIconWithBackground(this.mBuilder.mNotification.icon, dimensionPixelSize, dimensionPixelSize2, this.mBuilder.getColor()))
                    } else {
                        remoteViews.setImageViewBitmap(R.id.right_icon, createColoredBitmap(this.mBuilder.mNotification.icon, -1))
                    }
                    remoteViews.setViewVisibility(R.id.right_icon, 0)
                }
            } else if (z && this.mBuilder.mNotification.icon != 0) {
                remoteViews.setViewVisibility(R.id.icon, 0)
                if (Build.VERSION.SDK_INT >= 21) {
                    remoteViews.setImageViewBitmap(R.id.icon, createIconWithBackground(this.mBuilder.mNotification.icon, resources.getDimensionPixelSize(R.dimen.notification_large_icon_width) - resources.getDimensionPixelSize(R.dimen.notification_big_circle_margin), resources.getDimensionPixelSize(R.dimen.notification_small_icon_size_as_large), this.mBuilder.getColor()))
                } else {
                    remoteViews.setImageViewBitmap(R.id.icon, createColoredBitmap(this.mBuilder.mNotification.icon, -1))
                }
            }
            if (this.mBuilder.mContentTitle != null) {
                remoteViews.setTextViewText(R.id.title, this.mBuilder.mContentTitle)
            }
            if (this.mBuilder.mContentText != null) {
                remoteViews.setTextViewText(R.id.text, this.mBuilder.mContentText)
                z3 = true
            } else {
                z3 = false
            }
            Boolean z8 = Build.VERSION.SDK_INT < 21 && this.mBuilder.mLargeIcon != null
            if (this.mBuilder.mContentInfo != null) {
                remoteViews.setTextViewText(R.id.info, this.mBuilder.mContentInfo)
                remoteViews.setViewVisibility(R.id.info, 0)
                z4 = true
                z5 = true
            } else if (this.mBuilder.mNumber > 0) {
                if (this.mBuilder.mNumber > resources.getInteger(R.integer.status_bar_notification_info_maxnum)) {
                    remoteViews.setTextViewText(R.id.info, resources.getString(R.string.status_bar_notification_info_overflow))
                } else {
                    remoteViews.setTextViewText(R.id.info, NumberFormat.getIntegerInstance().format(this.mBuilder.mNumber))
                }
                remoteViews.setViewVisibility(R.id.info, 0)
                z4 = true
                z5 = true
            } else {
                remoteViews.setViewVisibility(R.id.info, 8)
                z4 = z3
                z5 = z8
            }
            if (this.mBuilder.mSubText == null || Build.VERSION.SDK_INT < 16) {
                z6 = false
            } else {
                remoteViews.setTextViewText(R.id.text, this.mBuilder.mSubText)
                if (this.mBuilder.mContentText != null) {
                    remoteViews.setTextViewText(R.id.text2, this.mBuilder.mContentText)
                    remoteViews.setViewVisibility(R.id.text2, 0)
                    z6 = true
                } else {
                    remoteViews.setViewVisibility(R.id.text2, 8)
                    z6 = false
                }
            }
            if (z6 && Build.VERSION.SDK_INT >= 16) {
                if (z2) {
                    remoteViews.setTextViewTextSize(R.id.text, 0, resources.getDimensionPixelSize(R.dimen.notification_subtext_size))
                }
                remoteViews.setViewPadding(R.id.line1, 0, 0, 0, 0)
            }
            if (this.mBuilder.getWhenIfShowing() != 0) {
                if (!this.mBuilder.mUseChronometer || Build.VERSION.SDK_INT < 16) {
                    remoteViews.setViewVisibility(R.id.time, 0)
                    remoteViews.setLong(R.id.time, "setTime", this.mBuilder.getWhenIfShowing())
                } else {
                    remoteViews.setViewVisibility(R.id.chronometer, 0)
                    remoteViews.setLong(R.id.chronometer, "setBase", this.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()))
                    remoteViews.setBoolean(R.id.chronometer, "setStarted", true)
                }
                z5 = true
            }
            remoteViews.setViewVisibility(R.id.right_side, z5 ? 0 : 8)
            remoteViews.setViewVisibility(R.id.line3, z4 ? 0 : 8)
            return remoteViews
        }

        fun build() {
            if (this.mBuilder != null) {
                return this.mBuilder.build()
            }
            return null
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            hideNormalContent(remoteViews)
            remoteViews.removeAllViews(R.id.notification_main_column)
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone())
            remoteViews.setViewVisibility(R.id.notification_main_column, 0)
            if (Build.VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, calculateTopPadding(), 0, 0)
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun createColoredBitmap(Int i, Int i2) {
            return createColoredBitmap(i, i2, 0)
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected fun restoreFromCompatExtras(Bundle bundle) {
        }

        fun setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this)
                }
            }
        }
    }

    class WearableExtender implements Extender {
        private static val DEFAULT_CONTENT_ICON_GRAVITY = 8388613
        private static val DEFAULT_FLAGS = 1
        private static val DEFAULT_GRAVITY = 80
        private static val EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS"
        private static val FLAG_BIG_PICTURE_AMBIENT = 32
        private static val FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1
        private static val FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16
        private static val FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64
        private static val FLAG_HINT_HIDE_ICON = 2
        private static val FLAG_HINT_SHOW_BACKGROUND_ONLY = 4
        private static val FLAG_START_SCROLL_BOTTOM = 8
        private static val KEY_ACTIONS = "actions"
        private static val KEY_BACKGROUND = "background"
        private static val KEY_BRIDGE_TAG = "bridgeTag"
        private static val KEY_CONTENT_ACTION_INDEX = "contentActionIndex"
        private static val KEY_CONTENT_ICON = "contentIcon"
        private static val KEY_CONTENT_ICON_GRAVITY = "contentIconGravity"
        private static val KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight"
        private static val KEY_CUSTOM_SIZE_PRESET = "customSizePreset"
        private static val KEY_DISMISSAL_ID = "dismissalId"
        private static val KEY_DISPLAY_INTENT = "displayIntent"
        private static val KEY_FLAGS = "flags"
        private static val KEY_GRAVITY = "gravity"
        private static val KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout"
        private static val KEY_PAGES = "pages"
        public static val SCREEN_TIMEOUT_LONG = -1
        public static val SCREEN_TIMEOUT_SHORT = 0
        public static val SIZE_DEFAULT = 0
        public static val SIZE_FULL_SCREEN = 5
        public static val SIZE_LARGE = 4
        public static val SIZE_MEDIUM = 3
        public static val SIZE_SMALL = 2
        public static val SIZE_XSMALL = 1
        public static val UNSET_ACTION_INDEX = -1
        private ArrayList mActions
        private Bitmap mBackground
        private String mBridgeTag
        private Int mContentActionIndex
        private Int mContentIcon
        private Int mContentIconGravity
        private Int mCustomContentHeight
        private Int mCustomSizePreset
        private String mDismissalId
        private PendingIntent mDisplayIntent
        private Int mFlags
        private Int mGravity
        private Int mHintScreenTimeout
        private ArrayList mPages

        constructor() {
            this.mActions = ArrayList()
            this.mFlags = 1
            this.mPages = ArrayList()
            this.mContentIconGravity = 8388613
            this.mContentActionIndex = -1
            this.mCustomSizePreset = 0
            this.mGravity = 80
        }

        constructor(Notification notification) {
            this.mActions = ArrayList()
            this.mFlags = 1
            this.mPages = ArrayList()
            this.mContentIconGravity = 8388613
            this.mContentActionIndex = -1
            this.mCustomSizePreset = 0
            this.mGravity = 80
            Bundle extras = NotificationCompat.getExtras(notification)
            Bundle bundle = extras != null ? extras.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList(KEY_ACTIONS)
                if (Build.VERSION.SDK_INT >= 16 && parcelableArrayList != null) {
                    Array<Action> actionArr = new Action[parcelableArrayList.size()]
                    for (Int i = 0; i < actionArr.length; i++) {
                        if (Build.VERSION.SDK_INT >= 20) {
                            actionArr[i] = NotificationCompat.getActionCompatFromAction((Notification.Action) parcelableArrayList.get(i))
                        } else if (Build.VERSION.SDK_INT >= 16) {
                            actionArr[i] = NotificationCompatJellybean.getActionFromBundle((Bundle) parcelableArrayList.get(i))
                        }
                    }
                    Collections.addAll(this.mActions, actionArr)
                }
                this.mFlags = bundle.getInt(KEY_FLAGS, 1)
                this.mDisplayIntent = (PendingIntent) bundle.getParcelable(KEY_DISPLAY_INTENT)
                Array<Notification> notificationArrayFromBundle = NotificationCompat.getNotificationArrayFromBundle(bundle, KEY_PAGES)
                if (notificationArrayFromBundle != null) {
                    Collections.addAll(this.mPages, notificationArrayFromBundle)
                }
                this.mBackground = (Bitmap) bundle.getParcelable(KEY_BACKGROUND)
                this.mContentIcon = bundle.getInt(KEY_CONTENT_ICON)
                this.mContentIconGravity = bundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613)
                this.mContentActionIndex = bundle.getInt(KEY_CONTENT_ACTION_INDEX, -1)
                this.mCustomSizePreset = bundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0)
                this.mCustomContentHeight = bundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT)
                this.mGravity = bundle.getInt(KEY_GRAVITY, 80)
                this.mHintScreenTimeout = bundle.getInt(KEY_HINT_SCREEN_TIMEOUT)
                this.mDismissalId = bundle.getString(KEY_DISMISSAL_ID)
                this.mBridgeTag = bundle.getString(KEY_BRIDGE_TAG)
            }
        }

        @RequiresApi(20)
        private static Notification.Action getActionFromActionCompat(Action action) {
            Notification.Action.Builder builder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent())
            Bundle bundle = action.getExtras() != null ? Bundle(action.getExtras()) : Bundle()
            bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies())
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies())
            }
            builder.addExtras(bundle)
            Array<RemoteInput> remoteInputs = action.getRemoteInputs()
            if (remoteInputs != null) {
                android.app.Array<RemoteInput> remoteInputArrFromCompat = RemoteInput.fromCompat(remoteInputs)
                for (android.app.RemoteInput remoteInput : remoteInputArrFromCompat) {
                    builder.addRemoteInput(remoteInput)
                }
            }
            return builder.build()
        }

        private fun setFlag(Int i, Boolean z) {
            if (z) {
                this.mFlags |= i
            } else {
                this.mFlags &= i ^ (-1)
            }
        }

        public final WearableExtender addAction(Action action) {
            this.mActions.add(action)
            return this
        }

        public final WearableExtender addActions(List list) {
            this.mActions.addAll(list)
            return this
        }

        public final WearableExtender addPage(Notification notification) {
            this.mPages.add(notification)
            return this
        }

        public final WearableExtender addPages(List list) {
            this.mPages.addAll(list)
            return this
        }

        public final WearableExtender clearActions() {
            this.mActions.clear()
            return this
        }

        public final WearableExtender clearPages() {
            this.mPages.clear()
            return this
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final WearableExtender m1clone() {
            WearableExtender wearableExtender = WearableExtender()
            wearableExtender.mActions = ArrayList(this.mActions)
            wearableExtender.mFlags = this.mFlags
            wearableExtender.mDisplayIntent = this.mDisplayIntent
            wearableExtender.mPages = ArrayList(this.mPages)
            wearableExtender.mBackground = this.mBackground
            wearableExtender.mContentIcon = this.mContentIcon
            wearableExtender.mContentIconGravity = this.mContentIconGravity
            wearableExtender.mContentActionIndex = this.mContentActionIndex
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight
            wearableExtender.mGravity = this.mGravity
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout
            wearableExtender.mDismissalId = this.mDismissalId
            wearableExtender.mBridgeTag = this.mBridgeTag
            return wearableExtender
        }

        @Override // android.support.v4.app.NotificationCompat.Extender
        public final Builder extend(Builder builder) {
            Bundle bundle = Bundle()
            if (!this.mActions.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 16) {
                    ArrayList<? extends Parcelable> arrayList = new ArrayList<>(this.mActions.size())
                    Iterator it = this.mActions.iterator()
                    while (it.hasNext()) {
                        Action action = (Action) it.next()
                        if (Build.VERSION.SDK_INT >= 20) {
                            arrayList.add(getActionFromActionCompat(action))
                        } else if (Build.VERSION.SDK_INT >= 16) {
                            arrayList.add(NotificationCompatJellybean.getBundleForAction(action))
                        }
                    }
                    bundle.putParcelableArrayList(KEY_ACTIONS, arrayList)
                } else {
                    bundle.putParcelableArrayList(KEY_ACTIONS, null)
                }
            }
            if (this.mFlags != 1) {
                bundle.putInt(KEY_FLAGS, this.mFlags)
            }
            if (this.mDisplayIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent)
            }
            if (!this.mPages.isEmpty()) {
                bundle.putParcelableArray(KEY_PAGES, (Array<Parcelable>) this.mPages.toArray(new Notification[this.mPages.size()]))
            }
            if (this.mBackground != null) {
                bundle.putParcelable(KEY_BACKGROUND, this.mBackground)
            }
            if (this.mContentIcon != 0) {
                bundle.putInt(KEY_CONTENT_ICON, this.mContentIcon)
            }
            if (this.mContentIconGravity != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity)
            }
            if (this.mContentActionIndex != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex)
            }
            if (this.mCustomSizePreset != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset)
            }
            if (this.mCustomContentHeight != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight)
            }
            if (this.mGravity != 80) {
                bundle.putInt(KEY_GRAVITY, this.mGravity)
            }
            if (this.mHintScreenTimeout != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout)
            }
            if (this.mDismissalId != null) {
                bundle.putString(KEY_DISMISSAL_ID, this.mDismissalId)
            }
            if (this.mBridgeTag != null) {
                bundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag)
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle)
            return builder
        }

        public final List getActions() {
            return this.mActions
        }

        public final Bitmap getBackground() {
            return this.mBackground
        }

        public final String getBridgeTag() {
            return this.mBridgeTag
        }

        public final Int getContentAction() {
            return this.mContentActionIndex
        }

        @Deprecated
        public final Int getContentIcon() {
            return this.mContentIcon
        }

        @Deprecated
        public final Int getContentIconGravity() {
            return this.mContentIconGravity
        }

        public final Boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0
        }

        @Deprecated
        public final Int getCustomContentHeight() {
            return this.mCustomContentHeight
        }

        @Deprecated
        public final Int getCustomSizePreset() {
            return this.mCustomSizePreset
        }

        public final String getDismissalId() {
            return this.mDismissalId
        }

        public final PendingIntent getDisplayIntent() {
            return this.mDisplayIntent
        }

        @Deprecated
        public final Int getGravity() {
            return this.mGravity
        }

        public final Boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0
        }

        @Deprecated
        public final Boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0
        }

        public final Boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0
        }

        @Deprecated
        public final Boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0
        }

        @Deprecated
        public final Int getHintScreenTimeout() {
            return this.mHintScreenTimeout
        }

        @Deprecated
        public final Boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0
        }

        public final List getPages() {
            return this.mPages
        }

        public final Boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0
        }

        public final WearableExtender setBackground(Bitmap bitmap) {
            this.mBackground = bitmap
            return this
        }

        public final WearableExtender setBridgeTag(String str) {
            this.mBridgeTag = str
            return this
        }

        public final WearableExtender setContentAction(Int i) {
            this.mContentActionIndex = i
            return this
        }

        @Deprecated
        public final WearableExtender setContentIcon(Int i) {
            this.mContentIcon = i
            return this
        }

        @Deprecated
        public final WearableExtender setContentIconGravity(Int i) {
            this.mContentIconGravity = i
            return this
        }

        public final WearableExtender setContentIntentAvailableOffline(Boolean z) {
            setFlag(1, z)
            return this
        }

        @Deprecated
        public final WearableExtender setCustomContentHeight(Int i) {
            this.mCustomContentHeight = i
            return this
        }

        @Deprecated
        public final WearableExtender setCustomSizePreset(Int i) {
            this.mCustomSizePreset = i
            return this
        }

        public final WearableExtender setDismissalId(String str) {
            this.mDismissalId = str
            return this
        }

        public final WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent
            return this
        }

        @Deprecated
        public final WearableExtender setGravity(Int i) {
            this.mGravity = i
            return this
        }

        public final WearableExtender setHintAmbientBigPicture(Boolean z) {
            setFlag(32, z)
            return this
        }

        @Deprecated
        public final WearableExtender setHintAvoidBackgroundClipping(Boolean z) {
            setFlag(16, z)
            return this
        }

        public final WearableExtender setHintContentIntentLaunchesActivity(Boolean z) {
            setFlag(64, z)
            return this
        }

        @Deprecated
        public final WearableExtender setHintHideIcon(Boolean z) {
            setFlag(2, z)
            return this
        }

        @Deprecated
        public final WearableExtender setHintScreenTimeout(Int i) {
            this.mHintScreenTimeout = i
            return this
        }

        @Deprecated
        public final WearableExtender setHintShowBackgroundOnly(Boolean z) {
            setFlag(4, z)
            return this
        }

        public final WearableExtender setStartScrollBottom(Boolean z) {
            setFlag(8, z)
            return this
        }
    }

    @Deprecated
    constructor() {
    }

    fun getAction(Notification notification, Int i) {
        if (Build.VERSION.SDK_INT >= 20) {
            return getActionCompatFromAction(notification.actions[i])
        }
        if (Build.VERSION.SDK_INT >= 19) {
            Notification.Action action = notification.actions[i]
            SparseArray sparseParcelableArray = notification.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS)
            return NotificationCompatJellybean.readAction(action.icon, action.title, action.actionIntent, sparseParcelableArray != null ? (Bundle) sparseParcelableArray.get(i) : null)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getAction(notification, i)
        }
        return null
    }

    @RequiresApi(20)
    static Action getActionCompatFromAction(Notification.Action action) {
        Array<RemoteInput> remoteInputArr
        android.app.Array<RemoteInput> remoteInputs = action.getRemoteInputs()
        if (remoteInputs == null) {
            remoteInputArr = null
        } else {
            Array<RemoteInput> remoteInputArr2 = new RemoteInput[remoteInputs.length]
            for (Int i = 0; i < remoteInputs.length; i++) {
                android.app.RemoteInput remoteInput = remoteInputs[i]
                remoteInputArr2[i] = RemoteInput(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null)
            }
            remoteInputArr = remoteInputArr2
        }
        return Action(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputArr, null, Build.VERSION.SDK_INT >= 24 ? action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies() : action.getExtras().getBoolean("android.support.allowGeneratedReplies"), Build.VERSION.SDK_INT >= 28 ? action.getSemanticAction() : action.getExtras().getInt("android.support.action.semanticAction", 0), action.getExtras().getBoolean("android.support.action.showsUserInterface", true))
    }

    fun getActionCount(Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (notification.actions != null) {
                return notification.actions.length
            }
            return 0
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getActionCount(notification)
        }
        return 0
    }

    fun getBadgeIconType(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType()
        }
        return 0
    }

    fun getCategory(Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.category
        }
        return null
    }

    fun getChannelId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getChannelId()
        }
        return null
    }

    @RequiresApi(19)
    fun getContentTitle(Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TITLE)
    }

    @Nullable
    fun getExtras(Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification)
        }
        return null
    }

    fun getGroup(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getGroup()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY)
        }
        return null
    }

    fun getGroupAlertBehavior(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior()
        }
        return 0
    }

    @RequiresApi(21)
    fun getInvisibleActions(Notification notification) {
        ArrayList arrayList = ArrayList()
        Bundle bundle = notification.extras.getBundle("android.car.EXTENSIONS")
        if (bundle == null) {
            return arrayList
        }
        Bundle bundle2 = bundle.getBundle("invisible_actions")
        if (bundle2 != null) {
            for (Int i = 0; i < bundle2.size(); i++) {
                arrayList.add(NotificationCompatJellybean.getActionFromBundle(bundle2.getBundle(Integer.toString(i))))
            }
        }
        return arrayList
    }

    fun getLocalOnly(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return (notification.flags & 256) != 0
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY)
        }
        return false
    }

    static Array<Notification> getNotificationArrayFromBundle(Bundle bundle, String str) {
        Array<Parcelable> parcelableArray = bundle.getParcelableArray(str)
        if ((parcelableArray is Array<Notification>) || parcelableArray == null) {
            return (Array<Notification>) parcelableArray
        }
        Array<Notification> notificationArr = new Notification[parcelableArray.length]
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= parcelableArray.length) {
                bundle.putParcelableArray(str, notificationArr)
                return notificationArr
            }
            notificationArr[i2] = (Notification) parcelableArray[i2]
            i = i2 + 1
        }
    }

    fun getShortcutId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getShortcutId()
        }
        return null
    }

    fun getSortKey(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getSortKey()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY)
        }
        return null
    }

    fun getTimeoutAfter(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter()
        }
        return 0L
    }

    fun isGroupSummary(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return (notification.flags & 512) != 0
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY)
        }
        return false
    }
}
