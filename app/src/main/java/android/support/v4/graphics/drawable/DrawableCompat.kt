package android.support.v4.graphics.drawable

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.util.Log
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

class DrawableCompat {
    private static val TAG = "DrawableCompat"
    private static Method sGetLayoutDirectionMethod
    private static Boolean sGetLayoutDirectionMethodFetched
    private static Method sSetLayoutDirectionMethod
    private static Boolean sSetLayoutDirectionMethodFetched

    private constructor() {
    }

    fun applyTheme(@NonNull Drawable drawable, @NonNull Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.applyTheme(theme)
        }
    }

    fun canApplyTheme(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            return drawable.canApplyTheme()
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun clearColorFilter(@NonNull Drawable drawable) {
        DrawableContainer.DrawableContainerState drawableContainerState
        if (Build.VERSION.SDK_INT >= 23 || Build.VERSION.SDK_INT < 21) {
            drawable.clearColorFilter()
            return
        }
        drawable.clearColorFilter()
        if (drawable is InsetDrawable) {
            clearColorFilter(((InsetDrawable) drawable).getDrawable())
            return
        }
        if (drawable is WrappedDrawable) {
            clearColorFilter(((WrappedDrawable) drawable).getWrappedDrawable())
            return
        }
        if (!(drawable is DrawableContainer) || (drawableContainerState = (DrawableContainer.DrawableContainerState) ((DrawableContainer) drawable).getConstantState()) == null) {
            return
        }
        Int childCount = drawableContainerState.getChildCount()
        for (Int i = 0; i < childCount; i++) {
            Drawable child = drawableContainerState.getChild(i)
            if (child != null) {
                clearColorFilter(child)
            }
        }
    }

    fun getAlpha(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            return drawable.getAlpha()
        }
        return 0
    }

    fun getColorFilter(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            return drawable.getColorFilter()
        }
        return null
    }

    fun getLayoutDirection(@NonNull Drawable drawable) throws NoSuchMethodException, SecurityException {
        if (Build.VERSION.SDK_INT >= 23) {
            return drawable.getLayoutDirection()
        }
        if (Build.VERSION.SDK_INT < 17) {
            return 0
        }
        if (!sGetLayoutDirectionMethodFetched) {
            try {
                Method declaredMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0])
                sGetLayoutDirectionMethod = declaredMethod
                declaredMethod.setAccessible(true)
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Failed to retrieve getLayoutDirection() method", e)
            }
            sGetLayoutDirectionMethodFetched = true
        }
        if (sGetLayoutDirectionMethod != null) {
            try {
                return ((Integer) sGetLayoutDirectionMethod.invoke(drawable, new Object[0])).intValue()
            } catch (Exception e2) {
                Log.i(TAG, "Failed to invoke getLayoutDirection() via reflection", e2)
                sGetLayoutDirectionMethod = null
            }
        }
        return 0
    }

    fun inflate(@NonNull Drawable drawable, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme)
        } else {
            drawable.inflate(resources, xmlPullParser, attributeSet)
        }
    }

    fun isAutoMirrored(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            return drawable.isAutoMirrored()
        }
        return false
    }

    @Deprecated
    fun jumpToCurrentState(@NonNull Drawable drawable) {
        drawable.jumpToCurrentState()
    }

    fun setAutoMirrored(@NonNull Drawable drawable, Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            drawable.setAutoMirrored(z)
        }
    }

    fun setHotspot(@NonNull Drawable drawable, Float f, Float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspot(f, f2)
        }
    }

    fun setHotspotBounds(@NonNull Drawable drawable, Int i, Int i2, Int i3, Int i4) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspotBounds(i, i2, i3, i4)
        }
    }

    fun setLayoutDirection(@NonNull Drawable drawable, Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 23) {
            return drawable.setLayoutDirection(i)
        }
        if (Build.VERSION.SDK_INT < 17) {
            return false
        }
        if (!sSetLayoutDirectionMethodFetched) {
            try {
                Method declaredMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", Integer.TYPE)
                sSetLayoutDirectionMethod = declaredMethod
                declaredMethod.setAccessible(true)
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Failed to retrieve setLayoutDirection(Int) method", e)
            }
            sSetLayoutDirectionMethodFetched = true
        }
        if (sSetLayoutDirectionMethod != null) {
            try {
                sSetLayoutDirectionMethod.invoke(drawable, Integer.valueOf(i))
                return true
            } catch (Exception e2) {
                Log.i(TAG, "Failed to invoke setLayoutDirection(Int) via reflection", e2)
                sSetLayoutDirectionMethod = null
            }
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setTint(@NonNull Drawable drawable, @ColorInt Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTint(i)
        } else if (drawable is TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTint(i)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setTintList(@NonNull Drawable drawable, @Nullable ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintList(colorStateList)
        } else if (drawable is TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTintList(colorStateList)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setTintMode(@NonNull Drawable drawable, @NonNull PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintMode(mode)
        } else if (drawable is TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTintMode(mode)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun unwrap(@NonNull Drawable drawable) {
        return drawable is WrappedDrawable ? ((WrappedDrawable) drawable).getWrappedDrawable() : drawable
    }

    fun wrap(@NonNull Drawable drawable) {
        return Build.VERSION.SDK_INT >= 23 ? drawable : Build.VERSION.SDK_INT >= 21 ? !(drawable is TintAwareDrawable) ? WrappedDrawableApi21(drawable) : drawable : !(drawable is TintAwareDrawable) ? WrappedDrawableApi14(drawable) : drawable
    }
}
