package android.support.v4.content.pm

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.graphics.drawable.IconCompat
import android.text.TextUtils
import java.util.Arrays

class ShortcutInfoCompat {
    ComponentName mActivity
    Context mContext
    CharSequence mDisabledMessage
    IconCompat mIcon
    String mId
    Array<Intent> mIntents
    Boolean mIsAlwaysBadged
    CharSequence mLabel
    CharSequence mLongLabel

    class Builder {
        private val mInfo = ShortcutInfoCompat()

        constructor(@NonNull Context context, @NonNull String str) {
            this.mInfo.mContext = context
            this.mInfo.mId = str
        }

        @NonNull
        fun build() {
            if (TextUtils.isEmpty(this.mInfo.mLabel)) {
                throw IllegalArgumentException("Shortcut must have a non-empty label")
            }
            if (this.mInfo.mIntents == null || this.mInfo.mIntents.length == 0) {
                throw IllegalArgumentException("Shortcut must have an intent")
            }
            return this.mInfo
        }

        @NonNull
        fun setActivity(@NonNull ComponentName componentName) {
            this.mInfo.mActivity = componentName
            return this
        }

        fun setAlwaysBadged() {
            this.mInfo.mIsAlwaysBadged = true
            return this
        }

        @NonNull
        fun setDisabledMessage(@NonNull CharSequence charSequence) {
            this.mInfo.mDisabledMessage = charSequence
            return this
        }

        @NonNull
        fun setIcon(IconCompat iconCompat) {
            this.mInfo.mIcon = iconCompat
            return this
        }

        @NonNull
        fun setIntent(@NonNull Intent intent) {
            return setIntents(new Array<Intent>{intent})
        }

        @NonNull
        fun setIntents(@NonNull Array<Intent> intentArr) {
            this.mInfo.mIntents = intentArr
            return this
        }

        @NonNull
        fun setLongLabel(@NonNull CharSequence charSequence) {
            this.mInfo.mLongLabel = charSequence
            return this
        }

        @NonNull
        fun setShortLabel(@NonNull CharSequence charSequence) {
            this.mInfo.mLabel = charSequence
            return this
        }
    }

    ShortcutInfoCompat() {
    }

    Intent addToIntent(Intent intent) throws PackageManager.NameNotFoundException {
        intent.putExtra("android.intent.extra.shortcut.INTENT", this.mIntents[this.mIntents.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString())
        if (this.mIcon != null) {
            Drawable activityIcon = null
            if (this.mIsAlwaysBadged) {
                PackageManager packageManager = this.mContext.getPackageManager()
                if (this.mActivity != null) {
                    try {
                        activityIcon = packageManager.getActivityIcon(this.mActivity)
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
                if (activityIcon == null) {
                    activityIcon = this.mContext.getApplicationInfo().loadIcon(packageManager)
                }
            }
            this.mIcon.addToShortcutIntent(intent, activityIcon, this.mContext)
        }
        return intent
    }

    @Nullable
    fun getActivity() {
        return this.mActivity
    }

    @Nullable
    fun getDisabledMessage() {
        return this.mDisabledMessage
    }

    @NonNull
    fun getId() {
        return this.mId
    }

    @NonNull
    fun getIntent() {
        return this.mIntents[this.mIntents.length - 1]
    }

    @NonNull
    public Array<Intent> getIntents() {
        return (Array<Intent>) Arrays.copyOf(this.mIntents, this.mIntents.length)
    }

    @Nullable
    fun getLongLabel() {
        return this.mLongLabel
    }

    @NonNull
    fun getShortLabel() {
        return this.mLabel
    }

    @RequiresApi(25)
    fun toShortcutInfo() {
        ShortcutInfo.Builder intents = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents)
        if (this.mIcon != null) {
            intents.setIcon(this.mIcon.toIcon())
        }
        if (!TextUtils.isEmpty(this.mLongLabel)) {
            intents.setLongLabel(this.mLongLabel)
        }
        if (!TextUtils.isEmpty(this.mDisabledMessage)) {
            intents.setDisabledMessage(this.mDisabledMessage)
        }
        if (this.mActivity != null) {
            intents.setActivity(this.mActivity)
        }
        return intents.build()
    }
}
