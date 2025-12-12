package android.support.v4.app

import android.app.Activity
import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import androidx.core.util.Pair
import android.view.View

class ActivityOptionsCompat {
    public static val EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time"
    public static val EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages"

    @RequiresApi(16)
    class ActivityOptionsCompatImpl extends ActivityOptionsCompat {
        private final ActivityOptions mActivityOptions

        ActivityOptionsCompatImpl(ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        fun getLaunchBounds() {
            if (Build.VERSION.SDK_INT < 24) {
                return null
            }
            return this.mActivityOptions.getLaunchBounds()
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        fun requestUsageTimeReport(PendingIntent pendingIntent) {
            if (Build.VERSION.SDK_INT >= 23) {
                this.mActivityOptions.requestUsageTimeReport(pendingIntent)
            }
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        fun setLaunchBounds(@Nullable Rect rect) {
            return Build.VERSION.SDK_INT < 24 ? this : ActivityOptionsCompatImpl(this.mActivityOptions.setLaunchBounds(rect))
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        fun toBundle() {
            return this.mActivityOptions.toBundle()
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        fun update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat is ActivityOptionsCompatImpl) {
                this.mActivityOptions.update(((ActivityOptionsCompatImpl) activityOptionsCompat).mActivityOptions)
            }
        }
    }

    protected constructor() {
    }

    @NonNull
    fun makeBasic() {
        return Build.VERSION.SDK_INT >= 23 ? ActivityOptionsCompatImpl(ActivityOptions.makeBasic()) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeClipRevealAnimation(@NonNull View view, Int i, Int i2, Int i3, Int i4) {
        return Build.VERSION.SDK_INT >= 23 ? ActivityOptionsCompatImpl(ActivityOptions.makeClipRevealAnimation(view, i, i2, i3, i4)) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeCustomAnimation(@NonNull Context context, Int i, Int i2) {
        return Build.VERSION.SDK_INT >= 16 ? ActivityOptionsCompatImpl(ActivityOptions.makeCustomAnimation(context, i, i2)) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeScaleUpAnimation(@NonNull View view, Int i, Int i2, Int i3, Int i4) {
        return Build.VERSION.SDK_INT >= 16 ? ActivityOptionsCompatImpl(ActivityOptions.makeScaleUpAnimation(view, i, i2, i3, i4)) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeSceneTransitionAnimation(@NonNull Activity activity, @NonNull View view, @NonNull String str) {
        return Build.VERSION.SDK_INT >= 21 ? ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, view, str)) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeSceneTransitionAnimation(@NonNull Activity activity, Pair... pairArr) {
        if (Build.VERSION.SDK_INT < 21) {
            return ActivityOptionsCompat()
        }
        android.util.Array<Pair> pairArr2 = null
        if (pairArr != null) {
            android.util.Array<Pair> pairArr3 = new android.util.Pair[pairArr.length]
            for (Int i = 0; i < pairArr.length; i++) {
                pairArr3[i] = android.util.Pair.create(pairArr[i].first, pairArr[i].second)
            }
            pairArr2 = pairArr3
        }
        return ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, pairArr2))
    }

    @NonNull
    fun makeTaskLaunchBehind() {
        return Build.VERSION.SDK_INT >= 21 ? ActivityOptionsCompatImpl(ActivityOptions.makeTaskLaunchBehind()) : ActivityOptionsCompat()
    }

    @NonNull
    fun makeThumbnailScaleUpAnimation(@NonNull View view, @NonNull Bitmap bitmap, Int i, Int i2) {
        return Build.VERSION.SDK_INT >= 16 ? ActivityOptionsCompatImpl(ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, i, i2)) : ActivityOptionsCompat()
    }

    @Nullable
    fun getLaunchBounds() {
        return null
    }

    fun requestUsageTimeReport(@NonNull PendingIntent pendingIntent) {
    }

    @NonNull
    fun setLaunchBounds(@Nullable Rect rect) {
        return this
    }

    @Nullable
    fun toBundle() {
        return null
    }

    fun update(@NonNull ActivityOptionsCompat activityOptionsCompat) {
    }
}
