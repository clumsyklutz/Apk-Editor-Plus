package android.support.v4.graphics.drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.graphics.BitmapCompat
import androidx.core.view.GravityCompat
import android.util.Log
import java.io.InputStream

class RoundedBitmapDrawableFactory {
    private static val TAG = "RoundedBitmapDrawableFa"

    class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
            super(resources, bitmap)
        }

        @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
        Unit gravityCompatApply(Int i, Int i2, Int i3, Rect rect, Rect rect2) {
            GravityCompat.apply(i, i2, i3, rect, rect2, 0)
        }

        @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
        fun hasMipMap() {
            return this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap)
        }

        @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
        fun setMipMap(Boolean z) {
            if (this.mBitmap != null) {
                BitmapCompat.setHasMipMap(this.mBitmap, z)
                invalidateSelf()
            }
        }
    }

    private constructor() {
    }

    @NonNull
    fun create(@NonNull Resources resources, @Nullable Bitmap bitmap) {
        return Build.VERSION.SDK_INT >= 21 ? RoundedBitmapDrawable21(resources, bitmap) : DefaultRoundedBitmapDrawable(resources, bitmap)
    }

    @NonNull
    fun create(@NonNull Resources resources, @NonNull InputStream inputStream) {
        RoundedBitmapDrawable roundedBitmapDrawableCreate = create(resources, BitmapFactory.decodeStream(inputStream))
        if (roundedBitmapDrawableCreate.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + inputStream)
        }
        return roundedBitmapDrawableCreate
    }

    @NonNull
    fun create(@NonNull Resources resources, @NonNull String str) {
        RoundedBitmapDrawable roundedBitmapDrawableCreate = create(resources, BitmapFactory.decodeFile(str))
        if (roundedBitmapDrawableCreate.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + str)
        }
        return roundedBitmapDrawableCreate
    }
}
