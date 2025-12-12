package android.support.v4.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log
import android.widget.CompoundButton
import java.lang.reflect.Field

class CompoundButtonCompat {
    private static val TAG = "CompoundButtonCompat"
    private static Field sButtonDrawableField
    private static Boolean sButtonDrawableFieldFetched

    private constructor() {
    }

    @Nullable
    fun getButtonDrawable(@NonNull CompoundButton compoundButton) throws NoSuchFieldException {
        if (Build.VERSION.SDK_INT >= 23) {
            return compoundButton.getButtonDrawable()
        }
        if (!sButtonDrawableFieldFetched) {
            try {
                Field declaredField = CompoundButton.class.getDeclaredField("mButtonDrawable")
                sButtonDrawableField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
                Log.i(TAG, "Failed to retrieve mButtonDrawable field", e)
            }
            sButtonDrawableFieldFetched = true
        }
        if (sButtonDrawableField != null) {
            try {
                return (Drawable) sButtonDrawableField.get(compoundButton)
            } catch (IllegalAccessException e2) {
                Log.i(TAG, "Failed to get button drawable via reflection", e2)
                sButtonDrawableField = null
            }
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    fun getButtonTintList(@NonNull CompoundButton compoundButton) {
        if (Build.VERSION.SDK_INT >= 21) {
            return compoundButton.getButtonTintList()
        }
        if (compoundButton is TintableCompoundButton) {
            return ((TintableCompoundButton) compoundButton).getSupportButtonTintList()
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static PorterDuff.Mode getButtonTintMode(@NonNull CompoundButton compoundButton) {
        if (Build.VERSION.SDK_INT >= 21) {
            return compoundButton.getButtonTintMode()
        }
        if (compoundButton is TintableCompoundButton) {
            return ((TintableCompoundButton) compoundButton).getSupportButtonTintMode()
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setButtonTintList(@NonNull CompoundButton compoundButton, @Nullable ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            compoundButton.setButtonTintList(colorStateList)
        } else if (compoundButton is TintableCompoundButton) {
            ((TintableCompoundButton) compoundButton).setSupportButtonTintList(colorStateList)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setButtonTintMode(@NonNull CompoundButton compoundButton, @Nullable PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            compoundButton.setButtonTintMode(mode)
        } else if (compoundButton is TintableCompoundButton) {
            ((TintableCompoundButton) compoundButton).setSupportButtonTintMode(mode)
        }
    }
}
