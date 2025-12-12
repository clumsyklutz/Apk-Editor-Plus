package android.support.v7.widget

import android.graphics.Rect
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface FitWindowsViewGroup {

    public interface OnFitSystemWindowsListener {
        Unit onFitSystemWindows(Rect rect)
    }

    Unit setOnFitSystemWindowsListener(OnFitSystemWindowsListener onFitSystemWindowsListener)
}
