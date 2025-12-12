package android.support.v7.widget

import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View

class TooltipCompat {
    private constructor() {
    }

    fun setTooltipText(@NonNull View view, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(charSequence)
        } else {
            TooltipCompatHandler.setTooltipText(view, charSequence)
        }
    }
}
