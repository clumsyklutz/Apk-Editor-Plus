package android.support.v4.media.app

import android.app.Notification
import android.app.PendingIntent
import android.media.session.MediaSession
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.mediacompat.R
import android.support.v4.app.BundleCompat
import android.support.v4.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import android.support.v4.media.session.MediaSessionCompat
import android.widget.RemoteViews

class NotificationCompat {

    class DecoratedMediaCustomViewStyle extends MediaStyle {
        private fun setBackgroundColor(RemoteViews remoteViews) {
            remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", this.mBuilder.getColor() != 0 ? this.mBuilder.getColor() : this.mBuilder.mContext.getResources().getColor(R.color.notification_material_background_media_default_color))
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.DecoratedMediaCustomViewStyle()))
            } else {
                super.apply(notificationBuilderWithBuilderAccessor)
            }
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle
        Int getBigContentViewLayoutResource(Int i) {
            return i <= 3 ? R.layout.notification_template_big_media_narrow_custom : R.layout.notification_template_big_media_custom
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle
        Int getContentViewLayoutResource() {
            return this.mBuilder.getContentView() != null ? R.layout.notification_template_media_custom : super.getContentViewLayoutResource()
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViewsGenerateBigContentView = null
            if (Build.VERSION.SDK_INT < 24) {
                RemoteViews bigContentView = this.mBuilder.getBigContentView() != null ? this.mBuilder.getBigContentView() : this.mBuilder.getContentView()
                if (bigContentView != null) {
                    remoteViewsGenerateBigContentView = generateBigContentView()
                    buildIntoRemoteViews(remoteViewsGenerateBigContentView, bigContentView)
                    if (Build.VERSION.SDK_INT >= 21) {
                        setBackgroundColor(remoteViewsGenerateBigContentView)
                    }
                }
            }
            return remoteViewsGenerateBigContentView
        }

        @Override // android.support.v4.media.app.NotificationCompat.MediaStyle, android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null
            }
            Boolean z = this.mBuilder.getContentView() != null
            if (Build.VERSION.SDK_INT >= 21) {
                if (z || this.mBuilder.getBigContentView() != null) {
                    RemoteViews remoteViewsGenerateContentView = generateContentView()
                    if (z) {
                        buildIntoRemoteViews(remoteViewsGenerateContentView, this.mBuilder.getContentView())
                    }
                    setBackgroundColor(remoteViewsGenerateContentView)
                    return remoteViewsGenerateContentView
                }
            } else {
                RemoteViews remoteViewsGenerateContentView2 = generateContentView()
                if (z) {
                    buildIntoRemoteViews(remoteViewsGenerateContentView2, this.mBuilder.getContentView())
                    return remoteViewsGenerateContentView2
                }
            }
            return null
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViewsGenerateBigContentView = null
            if (Build.VERSION.SDK_INT < 24) {
                RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView() != null ? this.mBuilder.getHeadsUpContentView() : this.mBuilder.getContentView()
                if (headsUpContentView != null) {
                    remoteViewsGenerateBigContentView = generateBigContentView()
                    buildIntoRemoteViews(remoteViewsGenerateBigContentView, headsUpContentView)
                    if (Build.VERSION.SDK_INT >= 21) {
                        setBackgroundColor(remoteViewsGenerateBigContentView)
                    }
                }
            }
            return remoteViewsGenerateBigContentView
        }
    }

    class MediaStyle extends NotificationCompat.Style {
        private static val MAX_MEDIA_BUTTONS = 5
        private static val MAX_MEDIA_BUTTONS_IN_COMPACT = 3
        Array<Int> mActionsToShowInCompact = null
        PendingIntent mCancelButtonIntent
        Boolean mShowCancelButton
        MediaSessionCompat.Token mToken

        constructor() {
        }

        constructor(NotificationCompat.Builder builder) {
            setBuilder(builder)
        }

        private fun generateMediaActionButton(NotificationCompat.Action action) {
            Boolean z = action.getActionIntent() == null
            RemoteViews remoteViews = RemoteViews(this.mBuilder.mContext.getPackageName(), R.layout.notification_media_action)
            remoteViews.setImageViewResource(R.id.action0, action.getIcon())
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action0, action.getActionIntent())
            }
            if (Build.VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action0, action.getTitle())
            }
            return remoteViews
        }

        public static MediaSessionCompat.Token getMediaSession(Notification notification) {
            Bundle extras = android.support.v4.app.NotificationCompat.getExtras(notification)
            if (extras != null) {
                if (Build.VERSION.SDK_INT >= 21) {
                    Parcelable parcelable = extras.getParcelable(android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION)
                    if (parcelable != null) {
                        return MediaSessionCompat.Token.fromToken(parcelable)
                    }
                } else {
                    IBinder binder = BundleCompat.getBinder(extras, android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION)
                    if (binder != null) {
                        Parcel parcelObtain = Parcel.obtain()
                        parcelObtain.writeStrongBinder(binder)
                        parcelObtain.setDataPosition(0)
                        MediaSessionCompat.Token token = (MediaSessionCompat.Token) MediaSessionCompat.Token.CREATOR.createFromParcel(parcelObtain)
                        parcelObtain.recycle()
                        return token
                    }
                }
            }
            return null
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(fillInMediaStyle(new Notification.MediaStyle()))
            } else if (this.mShowCancelButton) {
                notificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true)
            }
        }

        @RequiresApi(21)
        Notification.MediaStyle fillInMediaStyle(Notification.MediaStyle mediaStyle) {
            if (this.mActionsToShowInCompact != null) {
                mediaStyle.setShowActionsInCompactView(this.mActionsToShowInCompact)
            }
            if (this.mToken != null) {
                mediaStyle.setMediaSession((MediaSession.Token) this.mToken.getToken())
            }
            return mediaStyle
        }

        RemoteViews generateBigContentView() {
            Int iMin = Math.min(this.mBuilder.mActions.size(), 5)
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, getBigContentViewLayoutResource(iMin), false)
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions)
            if (iMin > 0) {
                for (Int i = 0; i < iMin; i++) {
                    remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton((NotificationCompat.Action) this.mBuilder.mActions.get(i)))
                }
            }
            if (this.mShowCancelButton) {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0)
                remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha))
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.mCancelButtonIntent)
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8)
            }
            return remoteViewsApplyStandardTemplate
        }

        RemoteViews generateContentView() {
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, getContentViewLayoutResource(), true)
            Int size = this.mBuilder.mActions.size()
            Int iMin = this.mActionsToShowInCompact == null ? 0 : Math.min(this.mActionsToShowInCompact.length, 3)
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions)
            if (iMin > 0) {
                for (Int i = 0; i < iMin; i++) {
                    if (i >= size) {
                        throw IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i), Integer.valueOf(size - 1)))
                    }
                    remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton((NotificationCompat.Action) this.mBuilder.mActions.get(this.mActionsToShowInCompact[i])))
                }
            }
            if (this.mShowCancelButton) {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 8)
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0)
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.mCancelButtonIntent)
                remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha))
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 0)
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8)
            }
            return remoteViewsApplyStandardTemplate
        }

        Int getBigContentViewLayoutResource(Int i) {
            return i <= 3 ? R.layout.notification_template_big_media_narrow : R.layout.notification_template_big_media
        }

        Int getContentViewLayoutResource() {
            return R.layout.notification_template_media
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null
            }
            return generateBigContentView()
        }

        @Override // android.support.v4.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null
            }
            return generateContentView()
        }

        fun setCancelButtonIntent(PendingIntent pendingIntent) {
            this.mCancelButtonIntent = pendingIntent
            return this
        }

        fun setMediaSession(MediaSessionCompat.Token token) {
            this.mToken = token
            return this
        }

        fun setShowActionsInCompactView(Int... iArr) {
            this.mActionsToShowInCompact = iArr
            return this
        }

        fun setShowCancelButton(Boolean z) {
            if (Build.VERSION.SDK_INT < 21) {
                this.mShowCancelButton = z
            }
            return this
        }
    }

    private constructor() {
    }
}
