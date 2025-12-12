package android.support.v4.hardware.display

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.Display
import android.view.WindowManager
import java.util.WeakHashMap

class DisplayManagerCompat {
    public static val DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION"
    private static val sInstances = WeakHashMap()
    private final Context mContext

    private constructor(Context context) {
        this.mContext = context
    }

    @NonNull
    fun getInstance(@NonNull Context context) {
        DisplayManagerCompat displayManagerCompat
        synchronized (sInstances) {
            displayManagerCompat = (DisplayManagerCompat) sInstances.get(context)
            if (displayManagerCompat == null) {
                displayManagerCompat = DisplayManagerCompat(context)
                sInstances.put(context, displayManagerCompat)
            }
        }
        return displayManagerCompat
    }

    @Nullable
    public final Display getDisplay(Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            return ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i)
        }
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay()
        if (defaultDisplay.getDisplayId() != i) {
            return null
        }
        return defaultDisplay
    }

    @NonNull
    public final Array<Display> getDisplays() {
        return Build.VERSION.SDK_INT >= 17 ? ((DisplayManager) this.mContext.getSystemService("display")).getDisplays() : new Array<Display>{((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay()}
    }

    @NonNull
    public final Array<Display> getDisplays(@Nullable String str) {
        return Build.VERSION.SDK_INT >= 17 ? ((DisplayManager) this.mContext.getSystemService("display")).getDisplays(str) : str == null ? new Display[0] : new Array<Display>{((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay()}
    }
}
