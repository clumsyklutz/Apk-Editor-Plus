package android.support.v7.content.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.core.content.ContextCompat
import android.support.v4.content.res.ColorStateListInflaterCompat
import android.support.v7.widget.AppCompatDrawableManager
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import java.util.WeakHashMap

class AppCompatResources {
    private static val LOG_TAG = "AppCompatResources"
    private static val TL_TYPED_VALUE = ThreadLocal()
    private static val sColorStateCaches = WeakHashMap(0)
    private static val sColorStateCacheLock = Object()

    class ColorStateListCacheEntry {
        final Configuration configuration
        final ColorStateList value

        ColorStateListCacheEntry(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration) {
            this.value = colorStateList
            this.configuration = configuration
        }
    }

    private constructor() {
    }

    private fun addColorStateListToCache(@NonNull Context context, @ColorRes Int i, @NonNull ColorStateList colorStateList) {
        synchronized (sColorStateCacheLock) {
            SparseArray sparseArray = (SparseArray) sColorStateCaches.get(context)
            if (sparseArray == null) {
                sparseArray = SparseArray()
                sColorStateCaches.put(context, sparseArray)
            }
            sparseArray.append(i, ColorStateListCacheEntry(colorStateList, context.getResources().getConfiguration()))
        }
    }

    @Nullable
    private fun getCachedColorStateList(@NonNull Context context, @ColorRes Int i) {
        ColorStateListCacheEntry colorStateListCacheEntry
        synchronized (sColorStateCacheLock) {
            SparseArray sparseArray = (SparseArray) sColorStateCaches.get(context)
            if (sparseArray != null && sparseArray.size() > 0 && (colorStateListCacheEntry = (ColorStateListCacheEntry) sparseArray.get(i)) != null) {
                if (colorStateListCacheEntry.configuration.equals(context.getResources().getConfiguration())) {
                    return colorStateListCacheEntry.value
                }
                sparseArray.remove(i)
            }
            return null
        }
    }

    fun getColorStateList(@NonNull Context context, @ColorRes Int i) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i)
        }
        ColorStateList cachedColorStateList = getCachedColorStateList(context, i)
        if (cachedColorStateList != null) {
            return cachedColorStateList
        }
        ColorStateList colorStateListInflateColorStateList = inflateColorStateList(context, i)
        if (colorStateListInflateColorStateList == null) {
            return ContextCompat.getColorStateList(context, i)
        }
        addColorStateListToCache(context, i, colorStateListInflateColorStateList)
        return colorStateListInflateColorStateList
    }

    @Nullable
    fun getDrawable(@NonNull Context context, @DrawableRes Int i) {
        return AppCompatDrawableManager.get().getDrawable(context, i)
    }

    @NonNull
    private fun getTypedValue() {
        TypedValue typedValue = (TypedValue) TL_TYPED_VALUE.get()
        if (typedValue != null) {
            return typedValue
        }
        TypedValue typedValue2 = TypedValue()
        TL_TYPED_VALUE.set(typedValue2)
        return typedValue2
    }

    @Nullable
    private fun inflateColorStateList(Context context, Int i) throws Resources.NotFoundException {
        if (isColorInt(context, i)) {
            return null
        }
        Resources resources = context.getResources()
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), context.getTheme())
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ColorStateList, leaving it to the framework", e)
            return null
        }
    }

    private fun isColorInt(@NonNull Context context, @ColorRes Int i) throws Resources.NotFoundException {
        Resources resources = context.getResources()
        TypedValue typedValue = getTypedValue()
        resources.getValue(i, typedValue, true)
        return typedValue.type >= 28 && typedValue.type <= 31
    }
}
