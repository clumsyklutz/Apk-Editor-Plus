package android.support.v4.graphics.drawable

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.annotation.ColorInt
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface TintAwareDrawable {
    Unit setTint(@ColorInt Int i)

    Unit setTintList(ColorStateList colorStateList)

    Unit setTintMode(PorterDuff.Mode mode)
}
