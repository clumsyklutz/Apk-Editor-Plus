package android.support.v4.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.widget.ImageView

class ImageViewCompat {
    private constructor() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    fun getImageTintList(@NonNull ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return imageView.getImageTintList()
        }
        if (imageView is TintableImageSourceView) {
            return ((TintableImageSourceView) imageView).getSupportImageTintList()
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return imageView.getImageTintMode()
        }
        if (imageView is TintableImageSourceView) {
            return ((TintableImageSourceView) imageView).getSupportImageTintMode()
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT < 21) {
            if (imageView is TintableImageSourceView) {
                ((TintableImageSourceView) imageView).setSupportImageTintList(colorStateList)
                return
            }
            return
        }
        imageView.setImageTintList(colorStateList)
        if (Build.VERSION.SDK_INT == 21) {
            Drawable drawable = imageView.getDrawable()
            Boolean z = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true
            if (drawable == null || !z) {
                return
            }
            if (drawable.isStateful()) {
                drawable.setState(imageView.getDrawableState())
            }
            imageView.setImageDrawable(drawable)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT < 21) {
            if (imageView is TintableImageSourceView) {
                ((TintableImageSourceView) imageView).setSupportImageTintMode(mode)
                return
            }
            return
        }
        imageView.setImageTintMode(mode)
        if (Build.VERSION.SDK_INT == 21) {
            Drawable drawable = imageView.getDrawable()
            Boolean z = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true
            if (drawable == null || !z) {
                return
            }
            if (drawable.isStateful()) {
                drawable.setState(imageView.getDrawableState())
            }
            imageView.setImageDrawable(drawable)
        }
    }
}
