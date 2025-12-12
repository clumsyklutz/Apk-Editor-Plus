package android.support.v4.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.annotation.Nullable

public interface TintableCompoundButton {
    @Nullable
    ColorStateList getSupportButtonTintList()

    @Nullable
    PorterDuff.Mode getSupportButtonTintMode()

    Unit setSupportButtonTintList(@Nullable ColorStateList colorStateList)

    Unit setSupportButtonTintMode(@Nullable PorterDuff.Mode mode)
}
