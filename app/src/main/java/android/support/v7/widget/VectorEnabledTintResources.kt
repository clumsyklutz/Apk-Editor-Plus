package android.support.v7.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.lang.ref.WeakReference

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class VectorEnabledTintResources extends Resources {
    public static val MAX_SDK_WHERE_REQUIRED = 20
    private static Boolean sCompatVectorFromResourcesEnabled = false
    private final WeakReference mContextRef

    constructor(@NonNull Context context, @NonNull Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration())
        this.mContextRef = WeakReference(context)
    }

    fun isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled
    }

    fun setCompatVectorFromResourcesEnabled(Boolean z) {
        sCompatVectorFromResourcesEnabled = z
    }

    fun shouldBeUsed() {
        return isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20
    }

    @Override // android.content.res.Resources
    fun getDrawable(Int i) {
        Context context = (Context) this.mContextRef.get()
        return context != null ? AppCompatDrawableManager.get().onDrawableLoadedFromResources(context, this, i) : super.getDrawable(i)
    }

    final Drawable superGetDrawable(Int i) {
        return super.getDrawable(i)
    }
}
