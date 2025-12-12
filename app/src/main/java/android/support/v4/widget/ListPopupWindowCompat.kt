package android.support.v4.widget

import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View
import android.widget.ListPopupWindow

class ListPopupWindowCompat {
    private constructor() {
    }

    @Nullable
    public static View.OnTouchListener createDragToOpenListener(@NonNull ListPopupWindow listPopupWindow, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return listPopupWindow.createDragToOpenListener(view)
        }
        return null
    }

    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object obj, View view) {
        return createDragToOpenListener((ListPopupWindow) obj, view)
    }
}
