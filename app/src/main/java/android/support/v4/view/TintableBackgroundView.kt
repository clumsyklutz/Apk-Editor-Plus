package android.support.v4.view

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.annotation.Nullable

public interface TintableBackgroundView {
    @Nullable
    ColorStateList getSupportBackgroundTintList()

    @Nullable
    PorterDuff.Mode getSupportBackgroundTintMode()

    Unit setSupportBackgroundTintList(@Nullable ColorStateList colorStateList)

    Unit setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode)
}
