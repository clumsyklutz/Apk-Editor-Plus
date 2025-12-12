package android.support.v7.widget

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.lang.ref.WeakReference
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TintContextWrapper extends ContextWrapper {
    private static val CACHE_LOCK = Object()
    private static ArrayList sCache
    private final Resources mResources
    private final Resources.Theme mTheme

    private constructor(@NonNull Context context) {
        super(context)
        if (!VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = TintResources(this, context.getResources())
            this.mTheme = null
        } else {
            this.mResources = VectorEnabledTintResources(this, context.getResources())
            this.mTheme = this.mResources.newTheme()
            this.mTheme.setTo(context.getTheme())
        }
    }

    private fun shouldWrap(@NonNull Context context) {
        if ((context is TintContextWrapper) || (context.getResources() instanceof TintResources) || (context.getResources() instanceof VectorEnabledTintResources)) {
            return false
        }
        return Build.VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed()
    }

    fun wrap(@NonNull Context context) {
        if (!shouldWrap(context)) {
            return context
        }
        synchronized (CACHE_LOCK) {
            if (sCache == null) {
                sCache = ArrayList()
            } else {
                for (Int size = sCache.size() - 1; size >= 0; size--) {
                    WeakReference weakReference = (WeakReference) sCache.get(size)
                    if (weakReference == null || weakReference.get() == null) {
                        sCache.remove(size)
                    }
                }
                for (Int size2 = sCache.size() - 1; size2 >= 0; size2--) {
                    WeakReference weakReference2 = (WeakReference) sCache.get(size2)
                    TintContextWrapper tintContextWrapper = weakReference2 != null ? (TintContextWrapper) weakReference2.get() : null
                    if (tintContextWrapper != null && tintContextWrapper.getBaseContext() == context) {
                        return tintContextWrapper
                    }
                }
            }
            TintContextWrapper tintContextWrapper2 = TintContextWrapper(context)
            sCache.add(WeakReference(tintContextWrapper2))
            return tintContextWrapper2
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun getAssets() {
        return this.mResources.getAssets()
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun getResources() {
        return this.mResources
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        return this.mTheme == null ? super.getTheme() : this.mTheme
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun setTheme(Int i) {
        if (this.mTheme == null) {
            super.setTheme(i)
        } else {
            this.mTheme.applyStyle(i, true)
        }
    }
}
