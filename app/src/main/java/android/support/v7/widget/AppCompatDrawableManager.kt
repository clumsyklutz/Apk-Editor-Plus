package android.support.v7.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.graphics.drawable.VectorDrawableCompat
import androidx.core.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.util.ArrayMap
import android.support.v4.util.LongSparseArray
import android.support.v4.util.LruCache
import androidx.collection.SparseArrayCompat
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.util.Xml
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.WeakHashMap
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class AppCompatDrawableManager {
    private static val DEBUG = false
    private static AppCompatDrawableManager INSTANCE = null
    private static val PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable"
    private static val SKIP_DRAWABLE_TAG = "appcompat_skip_skip"
    private static val TAG = "AppCompatDrawableManag"
    private ArrayMap mDelegates
    private val mDrawableCaches = WeakHashMap(0)
    private Boolean mHasCheckedVectorDrawableSetup
    private SparseArrayCompat mKnownDrawableIdTags
    private WeakHashMap mTintLists
    private TypedValue mTypedValue
    private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN
    private static val COLOR_FILTER_CACHE = ColorFilterLruCache(6)
    private static final Array<Int> COLORFILTER_TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha}
    private static final Array<Int> TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha}
    private static final Array<Int> COLORFILTER_COLOR_CONTROL_ACTIVATED = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light}
    private static final Array<Int> COLORFILTER_COLOR_BACKGROUND_MULTIPLY = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult}
    private static final Array<Int> TINT_COLOR_CONTROL_STATE_LIST = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material}
    private static final Array<Int> TINT_CHECKABLE_BUTTON_LIST = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material}

    @RequiresApi(11)
    class AsldcInflateDelegate implements InflateDelegate {
        AsldcInflateDelegate() {
        }

        @Override // android.support.v7.widget.AppCompatDrawableManager.InflateDelegate
        fun createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return AnimatedStateListDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme)
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e)
                return null
            }
        }
    }

    class AvdcInflateDelegate implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        @Override // android.support.v7.widget.AppCompatDrawableManager.InflateDelegate
        fun createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme)
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e)
                return null
            }
        }
    }

    class ColorFilterLruCache extends LruCache {
        constructor(Int i) {
            super(i)
        }

        private fun generateCacheKey(Int i, PorterDuff.Mode mode) {
            return ((i + 31) * 31) + mode.hashCode()
        }

        PorterDuffColorFilter get(Int i, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(i, mode)))
        }

        PorterDuffColorFilter put(Int i, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(i, mode)), porterDuffColorFilter)
        }
    }

    interface InflateDelegate {
        Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme)
    }

    class VdcInflateDelegate implements InflateDelegate {
        VdcInflateDelegate() {
        }

        @Override // android.support.v7.widget.AppCompatDrawableManager.InflateDelegate
        fun createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme)
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e)
                return null
            }
        }
    }

    private fun addDelegate(@NonNull String str, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates == null) {
            this.mDelegates = ArrayMap()
        }
        this.mDelegates.put(str, inflateDelegate)
    }

    private synchronized Boolean addDrawableToCache(@NonNull Context context, Long j, @NonNull Drawable drawable) {
        Boolean z
        Drawable.ConstantState constantState = drawable.getConstantState()
        if (constantState != null) {
            LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context)
            if (longSparseArray == null) {
                longSparseArray = LongSparseArray()
                this.mDrawableCaches.put(context, longSparseArray)
            }
            longSparseArray.put(j, WeakReference(constantState))
            z = true
        } else {
            z = false
        }
        return z
    }

    private fun addTintListToCache(@NonNull Context context, @DrawableRes Int i, @NonNull ColorStateList colorStateList) {
        if (this.mTintLists == null) {
            this.mTintLists = WeakHashMap()
        }
        SparseArrayCompat sparseArrayCompat = (SparseArrayCompat) this.mTintLists.get(context)
        if (sparseArrayCompat == null) {
            sparseArrayCompat = SparseArrayCompat()
            this.mTintLists.put(context, sparseArrayCompat)
        }
        sparseArrayCompat.append(i, colorStateList)
    }

    private fun arrayContains(Array<Int> iArr, Int i) {
        for (Int i2 : iArr) {
            if (i2 == i) {
                return true
            }
        }
        return false
    }

    private fun checkVectorDrawableSetup(@NonNull Context context) {
        if (this.mHasCheckedVectorDrawableSetup) {
            return
        }
        this.mHasCheckedVectorDrawableSetup = true
        Drawable drawable = getDrawable(context, R.drawable.abc_vector_test)
        if (drawable == null || !isVectorDrawable(drawable)) {
            this.mHasCheckedVectorDrawableSetup = false
            throw IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.")
        }
    }

    private fun createBorderlessButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, 0)
    }

    private fun createButtonColorStateList(@NonNull Context context, @ColorInt Int i) {
        Int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight)
        return ColorStateList(new Array<Int>[]{ThemeUtils.DISABLED_STATE_SET, ThemeUtils.PRESSED_STATE_SET, ThemeUtils.FOCUSED_STATE_SET, ThemeUtils.EMPTY_STATE_SET}, new Array<Int>{ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal), ColorUtils.compositeColors(themeAttrColor, i), ColorUtils.compositeColors(themeAttrColor, i), i})
    }

    private fun createCacheKey(TypedValue typedValue) {
        return (typedValue.assetCookie << 32) | typedValue.data
    }

    private fun createColoredButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent))
    }

    private fun createDefaultButtonColorStateList(@NonNull Context context) {
        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal))
    }

    private fun createDrawableIfNeeded(@NonNull Context context, @DrawableRes Int i) throws Resources.NotFoundException {
        if (this.mTypedValue == null) {
            this.mTypedValue = TypedValue()
        }
        TypedValue typedValue = this.mTypedValue
        context.getResources().getValue(i, typedValue, true)
        Long jCreateCacheKey = createCacheKey(typedValue)
        Drawable cachedDrawable = getCachedDrawable(context, jCreateCacheKey)
        if (cachedDrawable == null) {
            if (i == R.drawable.abc_cab_background_top_material) {
                cachedDrawable = LayerDrawable(new Array<Drawable>{getDrawable(context, R.drawable.abc_cab_background_internal_bg), getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)})
            }
            if (cachedDrawable != null) {
                cachedDrawable.setChangingConfigurations(typedValue.changingConfigurations)
                addDrawableToCache(context, jCreateCacheKey, cachedDrawable)
            }
        }
        return cachedDrawable
    }

    private fun createSwitchThumbColorStateList(Context context) {
        Array<Int>[] iArr = new Int[3][]
        Array<Int> iArr2 = new Int[3]
        ColorStateList themeAttrColorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal)
        if (themeAttrColorStateList == null || !themeAttrColorStateList.isStateful()) {
            iArr[0] = ThemeUtils.DISABLED_STATE_SET
            iArr2[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal)
            iArr[1] = ThemeUtils.CHECKED_STATE_SET
            iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated)
            iArr[2] = ThemeUtils.EMPTY_STATE_SET
            iArr2[2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal)
        } else {
            iArr[0] = ThemeUtils.DISABLED_STATE_SET
            iArr2[0] = themeAttrColorStateList.getColorForState(iArr[0], 0)
            iArr[1] = ThemeUtils.CHECKED_STATE_SET
            iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated)
            iArr[2] = ThemeUtils.EMPTY_STATE_SET
            iArr2[2] = themeAttrColorStateList.getDefaultColor()
        }
        return ColorStateList(iArr, iArr2)
    }

    private fun createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, Array<Int> iArr) {
        if (colorStateList == null || mode == null) {
            return null
        }
        return getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode)
    }

    public static synchronized AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager()
            INSTANCE = appCompatDrawableManager
            installDefaultInflateDelegates(appCompatDrawableManager)
        }
        return INSTANCE
    }

    private synchronized Drawable getCachedDrawable(@NonNull Context context, Long j) {
        WeakReference weakReference
        Drawable drawableNewDrawable
        LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context)
        if (longSparseArray == null || (weakReference = (WeakReference) longSparseArray.get(j)) == null) {
            drawableNewDrawable = null
        } else {
            Drawable.ConstantState constantState = (Drawable.ConstantState) weakReference.get()
            if (constantState != null) {
                drawableNewDrawable = constantState.newDrawable(context.getResources())
            } else {
                longSparseArray.delete(j)
                drawableNewDrawable = null
            }
        }
        return drawableNewDrawable
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(Int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter
        porterDuffColorFilter = COLOR_FILTER_CACHE.get(i, mode)
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = PorterDuffColorFilter(i, mode)
            COLOR_FILTER_CACHE.put(i, mode, porterDuffColorFilter)
        }
        return porterDuffColorFilter
    }

    private fun getTintListFromCache(@NonNull Context context, @DrawableRes Int i) {
        SparseArrayCompat sparseArrayCompat
        if (this.mTintLists != null && (sparseArrayCompat = (SparseArrayCompat) this.mTintLists.get(context)) != null) {
            return (ColorStateList) sparseArrayCompat.get(i)
        }
        return null
    }

    static PorterDuff.Mode getTintMode(Int i) {
        if (i == R.drawable.abc_switch_thumb_material) {
            return PorterDuff.Mode.MULTIPLY
        }
        return null
    }

    private fun installDefaultInflateDelegates(@NonNull AppCompatDrawableManager appCompatDrawableManager) {
        if (Build.VERSION.SDK_INT < 24) {
            appCompatDrawableManager.addDelegate("vector", VdcInflateDelegate())
            appCompatDrawableManager.addDelegate("animated-vector", AvdcInflateDelegate())
            appCompatDrawableManager.addDelegate("animated-selector", AsldcInflateDelegate())
        }
    }

    private fun isVectorDrawable(@NonNull Drawable drawable) {
        return (drawable is VectorDrawableCompat) || PLATFORM_VD_CLAZZ.equals(drawable.getClass().getName())
    }

    private fun loadDrawableFromDelegates(@NonNull Context context, @DrawableRes Int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Drawable drawable
        Int next
        if (this.mDelegates == null || this.mDelegates.isEmpty()) {
            return null
        }
        if (this.mKnownDrawableIdTags != null) {
            String str = (String) this.mKnownDrawableIdTags.get(i)
            if (SKIP_DRAWABLE_TAG.equals(str) || (str != null && this.mDelegates.get(str) == null)) {
                return null
            }
        } else {
            this.mKnownDrawableIdTags = SparseArrayCompat()
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = TypedValue()
        }
        TypedValue typedValue = this.mTypedValue
        Resources resources = context.getResources()
        resources.getValue(i, typedValue, true)
        Long jCreateCacheKey = createCacheKey(typedValue)
        Drawable cachedDrawable = getCachedDrawable(context, jCreateCacheKey)
        if (cachedDrawable != null) {
            return cachedDrawable
        }
        if (typedValue.string == null || !typedValue.string.toString().endsWith(".xml")) {
            drawable = cachedDrawable
        } else {
            try {
                XmlResourceParser xml = resources.getXml(i)
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml)
                do {
                    next = xml.next()
                    if (next == 2) {
                        break
                    }
                } while (next != 1);
                if (next != 2) {
                    throw XmlPullParserException("No start tag found")
                }
                String name = xml.getName()
                this.mKnownDrawableIdTags.append(i, name)
                InflateDelegate inflateDelegate = (InflateDelegate) this.mDelegates.get(name)
                if (inflateDelegate != null) {
                    cachedDrawable = inflateDelegate.createFromXmlInner(context, xml, attributeSetAsAttributeSet, context.getTheme())
                }
                if (cachedDrawable != null) {
                    cachedDrawable.setChangingConfigurations(typedValue.changingConfigurations)
                    addDrawableToCache(context, jCreateCacheKey, cachedDrawable)
                }
                drawable = cachedDrawable
            } catch (Exception e) {
                Log.e(TAG, "Exception while inflating drawable", e)
            }
        }
        if (drawable != null) {
            return drawable
        }
        this.mKnownDrawableIdTags.append(i, SKIP_DRAWABLE_TAG)
        return drawable
    }

    private fun removeDelegate(@NonNull String str, @NonNull InflateDelegate inflateDelegate) {
        if (this.mDelegates == null || this.mDelegates.get(str) != inflateDelegate) {
            return
        }
        this.mDelegates.remove(str)
    }

    private fun setPorterDuffColorFilter(Drawable drawable, Int i, PorterDuff.Mode mode) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable = drawable.mutate()
        }
        if (mode == null) {
            mode = DEFAULT_MODE
        }
        drawable.setColorFilter(getPorterDuffColorFilter(i, mode))
    }

    private fun tintDrawable(@NonNull Context context, @DrawableRes Int i, Boolean z, @NonNull Drawable drawable) {
        ColorStateList tintList = getTintList(context, i)
        if (tintList != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate()
            }
            Drawable drawableWrap = DrawableCompat.wrap(drawable)
            DrawableCompat.setTintList(drawableWrap, tintList)
            PorterDuff.Mode tintMode = getTintMode(i)
            if (tintMode == null) {
                return drawableWrap
            }
            DrawableCompat.setTintMode(drawableWrap, tintMode)
            return drawableWrap
        }
        if (i == R.drawable.abc_seekbar_track_material) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable
            setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(android.R.id.background), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE)
            setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE)
            setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(android.R.id.progress), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE)
            return drawable
        }
        if (i != R.drawable.abc_ratingbar_material && i != R.drawable.abc_ratingbar_indicator_material && i != R.drawable.abc_ratingbar_small_material) {
            if (tintDrawableUsingColorFilter(context, i, drawable) || !z) {
                return drawable
            }
            return null
        }
        LayerDrawable layerDrawable2 = (LayerDrawable) drawable
        setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(android.R.id.background), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE)
        setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(android.R.id.secondaryProgress), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE)
        setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(android.R.id.progress), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE)
        return drawable
    }

    static Unit tintDrawable(Drawable drawable, TintInfo tintInfo, Array<Int> iArr) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable) && drawable.mutate() != drawable) {
            Log.d(TAG, "Mutated drawable is not the same instance as the input.")
            return
        }
        if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
            drawable.setColorFilter(createTintFilter(tintInfo.mHasTintList ? tintInfo.mTintList : null, tintInfo.mHasTintMode ? tintInfo.mTintMode : DEFAULT_MODE, iArr))
        } else {
            drawable.clearColorFilter()
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable.invalidateSelf()
        }
    }

    static Boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes Int i, @NonNull Drawable drawable) {
        Int iRound
        Int i2
        PorterDuff.Mode mode
        Boolean z
        PorterDuff.Mode mode2 = DEFAULT_MODE
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, i)) {
            i2 = R.attr.colorControlNormal
            mode = mode2
            z = true
            iRound = -1
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, i)) {
            i2 = R.attr.colorControlActivated
            mode = mode2
            z = true
            iRound = -1
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, i)) {
            z = true
            mode = PorterDuff.Mode.MULTIPLY
            i2 = 16842801
            iRound = -1
        } else if (i == R.drawable.abc_list_divider_mtrl_alpha) {
            i2 = android.R.attr.colorForeground
            iRound = Math.round(40.8f)
            mode = mode2
            z = true
        } else if (i == R.drawable.abc_dialog_material_background) {
            i2 = 16842801
            mode = mode2
            z = true
            iRound = -1
        } else {
            iRound = -1
            i2 = 0
            mode = mode2
            z = false
        }
        if (!z) {
            return false
        }
        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable = drawable.mutate()
        }
        drawable.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, i2), mode))
        if (iRound == -1) {
            return true
        }
        drawable.setAlpha(iRound)
        return true
    }

    public final synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes Int i) {
        return getDrawable(context, i, false)
    }

    final synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes Int i, Boolean z) {
        Drawable drawableLoadDrawableFromDelegates
        checkVectorDrawableSetup(context)
        drawableLoadDrawableFromDelegates = loadDrawableFromDelegates(context, i)
        if (drawableLoadDrawableFromDelegates == null) {
            drawableLoadDrawableFromDelegates = createDrawableIfNeeded(context, i)
        }
        if (drawableLoadDrawableFromDelegates == null) {
            drawableLoadDrawableFromDelegates = ContextCompat.getDrawable(context, i)
        }
        if (drawableLoadDrawableFromDelegates != null) {
            drawableLoadDrawableFromDelegates = tintDrawable(context, i, z, drawableLoadDrawableFromDelegates)
        }
        if (drawableLoadDrawableFromDelegates != null) {
            DrawableUtils.fixDrawable(drawableLoadDrawableFromDelegates)
        }
        return drawableLoadDrawableFromDelegates
    }

    final synchronized ColorStateList getTintList(@NonNull Context context, @DrawableRes Int i) {
        ColorStateList tintListFromCache
        tintListFromCache = getTintListFromCache(context, i)
        if (tintListFromCache == null) {
            if (i == R.drawable.abc_edit_text_material) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext)
            } else if (i == R.drawable.abc_switch_track_mtrl_alpha) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track)
            } else if (i == R.drawable.abc_switch_thumb_material) {
                tintListFromCache = createSwitchThumbColorStateList(context)
            } else if (i == R.drawable.abc_btn_default_mtrl_shape) {
                tintListFromCache = createDefaultButtonColorStateList(context)
            } else if (i == R.drawable.abc_btn_borderless_material) {
                tintListFromCache = createBorderlessButtonColorStateList(context)
            } else if (i == R.drawable.abc_btn_colored_material) {
                tintListFromCache = createColoredButtonColorStateList(context)
            } else if (i == R.drawable.abc_spinner_mtrl_am_alpha || i == R.drawable.abc_spinner_textfield_background_material) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner)
            } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, i)) {
                tintListFromCache = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal)
            } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, i)) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_default)
            } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, i)) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable)
            } else if (i == R.drawable.abc_seekbar_thumb_material) {
                tintListFromCache = AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb)
            }
            if (tintListFromCache != null) {
                addTintListToCache(context, i, tintListFromCache)
            }
        }
        return tintListFromCache
    }

    public final synchronized Unit onConfigurationChanged(@NonNull Context context) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context)
        if (longSparseArray != null) {
            longSparseArray.clear()
        }
    }

    final synchronized Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes Int i) {
        Drawable drawableLoadDrawableFromDelegates
        drawableLoadDrawableFromDelegates = loadDrawableFromDelegates(context, i)
        if (drawableLoadDrawableFromDelegates == null) {
            drawableLoadDrawableFromDelegates = vectorEnabledTintResources.superGetDrawable(i)
        }
        return drawableLoadDrawableFromDelegates != null ? tintDrawable(context, i, false, drawableLoadDrawableFromDelegates) : null
    }
}
