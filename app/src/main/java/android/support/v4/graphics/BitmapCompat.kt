package android.support.v4.graphics

import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.NonNull

class BitmapCompat {
    private constructor() {
    }

    fun getAllocationByteCount(@NonNull Bitmap bitmap) {
        return Build.VERSION.SDK_INT >= 19 ? bitmap.getAllocationByteCount() : bitmap.getByteCount()
    }

    fun hasMipMap(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 18) {
            return bitmap.hasMipMap()
        }
        return false
    }

    fun setHasMipMap(@NonNull Bitmap bitmap, Boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            bitmap.setHasMipMap(z)
        }
    }
}
