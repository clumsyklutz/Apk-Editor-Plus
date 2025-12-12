package android.support.v4.app

import android.app.Notification
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RestrictTo
import androidx.core.app.NotificationCompat
import android.text.TextUtils
import android.util.SparseArray
import android.widget.RemoteViews
import java.util.ArrayList
import java.util.Iterator
import java.util.List

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView
    private final Notification.Builder mBuilder
    private final NotificationCompat.Builder mBuilderCompat
    private RemoteViews mContentView
    private Int mGroupAlertBehavior
    private RemoteViews mHeadsUpContentView
    private val mActionExtrasList = ArrayList()
    private val mExtras = Bundle()

    NotificationCompatBuilder(NotificationCompat.Builder builder) {
        this.mBuilderCompat = builder
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder = new Notification.Builder(builder.mContext, builder.mChannelId)
        } else {
            this.mBuilder = new Notification.Builder(builder.mContext)
        }
        Notification notification = builder.mNotification
        this.mBuilder.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.mTickerView).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.mContentTitle).setContentText(builder.mContentText).setContentInfo(builder.mContentInfo).setContentIntent(builder.mContentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.mFullScreenIntent, (notification.flags & 128) != 0).setLargeIcon(builder.mLargeIcon).setNumber(builder.mNumber).setProgress(builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate)
        if (Build.VERSION.SDK_INT < 21) {
            this.mBuilder.setSound(notification.sound, notification.audioStreamType)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.mBuilder.setSubText(builder.mSubText).setUsesChronometer(builder.mUseChronometer).setPriority(builder.mPriority)
            Iterator it = builder.mActions.iterator()
            while (it.hasNext()) {
                addAction((NotificationCompat.Action) it.next())
            }
            if (builder.mExtras != null) {
                this.mExtras.putAll(builder.mExtras)
            }
            if (Build.VERSION.SDK_INT < 20) {
                if (builder.mLocalOnly) {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true)
                }
                if (builder.mGroupKey != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, builder.mGroupKey)
                    if (builder.mGroupSummary) {
                        this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true)
                    } else {
                        this.mExtras.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true)
                    }
                }
                if (builder.mSortKey != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, builder.mSortKey)
                }
            }
            this.mContentView = builder.mContentView
            this.mBigContentView = builder.mBigContentView
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.mBuilder.setShowWhen(builder.mShowWhen)
            if (Build.VERSION.SDK_INT < 21 && builder.mPeople != null && !builder.mPeople.isEmpty()) {
                this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (Array<String>) builder.mPeople.toArray(new String[builder.mPeople.size()]))
            }
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setLocalOnly(builder.mLocalOnly).setGroup(builder.mGroupKey).setGroupSummary(builder.mGroupSummary).setSortKey(builder.mSortKey)
            this.mGroupAlertBehavior = builder.mGroupAlertBehavior
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setCategory(builder.mCategory).setColor(builder.mColor).setVisibility(builder.mVisibility).setPublicVersion(builder.mPublicVersion).setSound(notification.sound, notification.audioAttributes)
            Iterator it2 = builder.mPeople.iterator()
            while (it2.hasNext()) {
                this.mBuilder.addPerson((String) it2.next())
            }
            this.mHeadsUpContentView = builder.mHeadsUpContentView
            if (builder.mInvisibleActions.size() > 0) {
                Bundle bundle = builder.getExtras().getBundle("android.car.EXTENSIONS")
                Bundle bundle2 = bundle == null ? Bundle() : bundle
                Bundle bundle3 = Bundle()
                for (Int i = 0; i < builder.mInvisibleActions.size(); i++) {
                    bundle3.putBundle(Integer.toString(i), NotificationCompatJellybean.getBundleForAction((NotificationCompat.Action) builder.mInvisibleActions.get(i)))
                }
                bundle2.putBundle("invisible_actions", bundle3)
                builder.getExtras().putBundle("android.car.EXTENSIONS", bundle2)
                this.mExtras.putBundle("android.car.EXTENSIONS", bundle2)
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.mBuilder.setExtras(builder.mExtras).setRemoteInputHistory(builder.mRemoteInputHistory)
            if (builder.mContentView != null) {
                this.mBuilder.setCustomContentView(builder.mContentView)
            }
            if (builder.mBigContentView != null) {
                this.mBuilder.setCustomBigContentView(builder.mBigContentView)
            }
            if (builder.mHeadsUpContentView != null) {
                this.mBuilder.setCustomHeadsUpContentView(builder.mHeadsUpContentView)
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder.setBadgeIconType(builder.mBadgeIcon).setShortcutId(builder.mShortcutId).setTimeoutAfter(builder.mTimeout).setGroupAlertBehavior(builder.mGroupAlertBehavior)
            if (builder.mColorizedSet) {
                this.mBuilder.setColorized(builder.mColorized)
            }
            if (TextUtils.isEmpty(builder.mChannelId)) {
                return
            }
            this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null)
        }
    }

    private fun addAction(NotificationCompat.Action action) {
        if (Build.VERSION.SDK_INT < 20) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, action))
                return
            }
            return
        }
        Notification.Action.Builder builder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent())
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                builder.addRemoteInput(remoteInput)
            }
        }
        Bundle bundle = action.getExtras() != null ? Bundle(action.getExtras()) : Bundle()
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies())
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies())
        }
        bundle.putInt("android.support.action.semanticAction", action.getSemanticAction())
        if (Build.VERSION.SDK_INT >= 28) {
            builder.setSemanticAction(action.getSemanticAction())
        }
        bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface())
        builder.addExtras(bundle)
        this.mBuilder.addAction(builder.build())
    }

    private fun removeSoundAndVibration(Notification notification) {
        notification.sound = null
        notification.vibrate = null
        notification.defaults &= -2
        notification.defaults &= -3
    }

    fun build() {
        Bundle extras
        RemoteViews remoteViewsMakeHeadsUpContentView
        RemoteViews remoteViewsMakeBigContentView
        NotificationCompat.Style style = this.mBuilderCompat.mStyle
        if (style != null) {
            style.apply(this)
        }
        RemoteViews remoteViewsMakeContentView = style != null ? style.makeContentView(this) : null
        Notification notificationBuildInternal = buildInternal()
        if (remoteViewsMakeContentView != null) {
            notificationBuildInternal.contentView = remoteViewsMakeContentView
        } else if (this.mBuilderCompat.mContentView != null) {
            notificationBuildInternal.contentView = this.mBuilderCompat.mContentView
        }
        if (Build.VERSION.SDK_INT >= 16 && style != null && (remoteViewsMakeBigContentView = style.makeBigContentView(this)) != null) {
            notificationBuildInternal.bigContentView = remoteViewsMakeBigContentView
        }
        if (Build.VERSION.SDK_INT >= 21 && style != null && (remoteViewsMakeHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this)) != null) {
            notificationBuildInternal.headsUpContentView = remoteViewsMakeHeadsUpContentView
        }
        if (Build.VERSION.SDK_INT >= 16 && style != null && (extras = NotificationCompat.getExtras(notificationBuildInternal)) != null) {
            style.addCompatExtras(extras)
        }
        return notificationBuildInternal
    }

    protected fun buildInternal() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mBuilder.build()
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Notification notificationBuild = this.mBuilder.build()
            if (this.mGroupAlertBehavior == 0) {
                return notificationBuild
            }
            if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notificationBuild)
            }
            if (notificationBuild.getGroup() == null || (notificationBuild.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notificationBuild
            }
            removeSoundAndVibration(notificationBuild)
            return notificationBuild
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setExtras(this.mExtras)
            Notification notificationBuild2 = this.mBuilder.build()
            if (this.mContentView != null) {
                notificationBuild2.contentView = this.mContentView
            }
            if (this.mBigContentView != null) {
                notificationBuild2.bigContentView = this.mBigContentView
            }
            if (this.mHeadsUpContentView != null) {
                notificationBuild2.headsUpContentView = this.mHeadsUpContentView
            }
            if (this.mGroupAlertBehavior == 0) {
                return notificationBuild2
            }
            if (notificationBuild2.getGroup() != null && (notificationBuild2.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notificationBuild2)
            }
            if (notificationBuild2.getGroup() == null || (notificationBuild2.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notificationBuild2
            }
            removeSoundAndVibration(notificationBuild2)
            return notificationBuild2
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setExtras(this.mExtras)
            Notification notificationBuild3 = this.mBuilder.build()
            if (this.mContentView != null) {
                notificationBuild3.contentView = this.mContentView
            }
            if (this.mBigContentView != null) {
                notificationBuild3.bigContentView = this.mBigContentView
            }
            if (this.mGroupAlertBehavior == 0) {
                return notificationBuild3
            }
            if (notificationBuild3.getGroup() != null && (notificationBuild3.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notificationBuild3)
            }
            if (notificationBuild3.getGroup() == null || (notificationBuild3.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notificationBuild3
            }
            removeSoundAndVibration(notificationBuild3)
            return notificationBuild3
        }
        if (Build.VERSION.SDK_INT >= 19) {
            SparseArray<? extends Parcelable> sparseArrayBuildActionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList)
            if (sparseArrayBuildActionExtrasMap != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, sparseArrayBuildActionExtrasMap)
            }
            this.mBuilder.setExtras(this.mExtras)
            Notification notificationBuild4 = this.mBuilder.build()
            if (this.mContentView != null) {
                notificationBuild4.contentView = this.mContentView
            }
            if (this.mBigContentView == null) {
                return notificationBuild4
            }
            notificationBuild4.bigContentView = this.mBigContentView
            return notificationBuild4
        }
        if (Build.VERSION.SDK_INT < 16) {
            return this.mBuilder.getNotification()
        }
        Notification notificationBuild5 = this.mBuilder.build()
        Bundle extras = NotificationCompat.getExtras(notificationBuild5)
        Bundle bundle = Bundle(this.mExtras)
        for (String str : this.mExtras.keySet()) {
            if (extras.containsKey(str)) {
                bundle.remove(str)
            }
        }
        extras.putAll(bundle)
        SparseArray<? extends Parcelable> sparseArrayBuildActionExtrasMap2 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList)
        if (sparseArrayBuildActionExtrasMap2 != null) {
            NotificationCompat.getExtras(notificationBuild5).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, sparseArrayBuildActionExtrasMap2)
        }
        if (this.mContentView != null) {
            notificationBuild5.contentView = this.mContentView
        }
        if (this.mBigContentView != null) {
            notificationBuild5.bigContentView = this.mBigContentView
        }
        return notificationBuild5
    }

    @Override // android.support.v4.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder
    }
}
