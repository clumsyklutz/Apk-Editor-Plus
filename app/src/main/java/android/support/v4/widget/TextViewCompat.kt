package android.support.v4.widget

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.icu.text.DecimalFormatSymbols
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.text.PrecomputedTextCompat
import android.support.v4.util.Preconditions
import android.text.Editable
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.List

class TextViewCompat {
    public static val AUTO_SIZE_TEXT_TYPE_NONE = 0
    public static val AUTO_SIZE_TEXT_TYPE_UNIFORM = 1
    private static val LINES = 1
    private static val LOG_TAG = "TextViewCompat"
    private static Field sMaxModeField
    private static Boolean sMaxModeFieldFetched
    private static Field sMaximumField
    private static Boolean sMaximumFieldFetched
    private static Field sMinModeField
    private static Boolean sMinModeFieldFetched
    private static Field sMinimumField
    private static Boolean sMinimumFieldFetched

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AutoSizeTextType {
    }

    @RequiresApi(26)
    class OreoCallback implements ActionMode.Callback {
        private static val MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100
        private final ActionMode.Callback mCallback
        private Boolean mCanUseMenuBuilderReferences
        private Boolean mInitializedMenuBuilderReferences = false
        private Class mMenuBuilderClass
        private Method mMenuBuilderRemoveItemAtMethod
        private final TextView mTextView

        OreoCallback(ActionMode.Callback callback, TextView textView) {
            this.mCallback = callback
            this.mTextView = textView
        }

        private fun createProcessTextIntent() {
            return Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain")
        }

        private fun createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo, TextView textView) {
            return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", !isEditable(textView)).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
        }

        private fun getSupportedActivities(Context context, PackageManager packageManager) {
            ArrayList arrayList = ArrayList()
            if (!(context is Activity)) {
                return arrayList
            }
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(resolveInfo, context)) {
                    arrayList.add(resolveInfo)
                }
            }
            return arrayList
        }

        private fun isEditable(TextView textView) {
            return (textView is Editable) && textView.onCheckIsTextEditor() && textView.isEnabled()
        }

        private fun isSupportedActivity(ResolveInfo resolveInfo, Context context) {
            if (context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                return true
            }
            if (resolveInfo.activityInfo.exported) {
                return resolveInfo.activityInfo.permission == null || context.checkSelfPermission(resolveInfo.activityInfo.permission) == 0
            }
            return false
        }

        private fun recomputeProcessTextMenuItems(Menu menu) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Context context = this.mTextView.getContext()
            PackageManager packageManager = context.getPackageManager()
            if (!this.mInitializedMenuBuilderReferences) {
                this.mInitializedMenuBuilderReferences = true
                try {
                    this.mMenuBuilderClass = Class.forName("com.android.internal.view.menu.MenuBuilder")
                    this.mMenuBuilderRemoveItemAtMethod = this.mMenuBuilderClass.getDeclaredMethod("removeItemAt", Integer.TYPE)
                    this.mCanUseMenuBuilderReferences = true
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    this.mMenuBuilderClass = null
                    this.mMenuBuilderRemoveItemAtMethod = null
                    this.mCanUseMenuBuilderReferences = false
                }
            }
            try {
                Method declaredMethod = (this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance(menu)) ? this.mMenuBuilderRemoveItemAtMethod : menu.getClass().getDeclaredMethod("removeItemAt", Integer.TYPE)
                for (Int size = menu.size() - 1; size >= 0; size--) {
                    MenuItem item = menu.getItem(size)
                    if (item.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(item.getIntent().getAction())) {
                        declaredMethod.invoke(menu, Integer.valueOf(size))
                    }
                }
                List supportedActivities = getSupportedActivities(context, packageManager)
                for (Int i = 0; i < supportedActivities.size(); i++) {
                    ResolveInfo resolveInfo = (ResolveInfo) supportedActivities.get(i)
                    menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo, this.mTextView)).setShowAsAction(1)
                }
            } catch (IllegalAccessException e2) {
            } catch (NoSuchMethodException e3) {
            } catch (InvocationTargetException e4) {
            }
        }

        @Override // android.view.ActionMode.Callback
        fun onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mCallback.onActionItemClicked(actionMode, menuItem)
        }

        @Override // android.view.ActionMode.Callback
        fun onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mCallback.onCreateActionMode(actionMode, menu)
        }

        @Override // android.view.ActionMode.Callback
        fun onDestroyActionMode(ActionMode actionMode) {
            this.mCallback.onDestroyActionMode(actionMode)
        }

        @Override // android.view.ActionMode.Callback
        fun onPrepareActionMode(ActionMode actionMode, Menu menu) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            recomputeProcessTextMenuItems(menu)
            return this.mCallback.onPrepareActionMode(actionMode, menu)
        }
    }

    private constructor() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getAutoSizeMaxTextSize(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMaxTextSize()
        }
        if (textView is AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize()
        }
        return -1
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getAutoSizeMinTextSize(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMinTextSize()
        }
        if (textView is AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize()
        }
        return -1
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getAutoSizeStepGranularity(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeStepGranularity()
        }
        if (textView is AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity()
        }
        return -1
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public static Array<Int> getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return Build.VERSION.SDK_INT >= 27 ? textView.getAutoSizeTextAvailableSizes() : textView is AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes() : new Int[0]
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getAutoSizeTextType(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextType()
        }
        if (textView is AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextType()
        }
        return 0
    }

    @NonNull
    public static Array<Drawable> getCompoundDrawablesRelative(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 18) {
            return textView.getCompoundDrawablesRelative()
        }
        if (Build.VERSION.SDK_INT < 17) {
            return textView.getCompoundDrawables()
        }
        Boolean z = textView.getLayoutDirection() == 1
        Array<Drawable> compoundDrawables = textView.getCompoundDrawables()
        if (z) {
            Drawable drawable = compoundDrawables[2]
            Drawable drawable2 = compoundDrawables[0]
            compoundDrawables[0] = drawable
            compoundDrawables[2] = drawable2
        }
        return compoundDrawables
    }

    fun getFirstBaselineToTopHeight(@NonNull TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top
    }

    fun getLastBaselineToBottomHeight(@NonNull TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom
    }

    fun getMaxLines(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMaxLines()
        }
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode")
            sMaxModeFieldFetched = true
        }
        if (sMaxModeField != null && retrieveIntFromField(sMaxModeField, textView) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum")
                sMaximumFieldFetched = true
            }
            if (sMaximumField != null) {
                return retrieveIntFromField(sMaximumField, textView)
            }
        }
        return -1
    }

    fun getMinLines(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMinLines()
        }
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode")
            sMinModeFieldFetched = true
        }
        if (sMinModeField != null && retrieveIntFromField(sMinModeField, textView) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum")
                sMinimumFieldFetched = true
            }
            if (sMinimumField != null) {
                return retrieveIntFromField(sMinimumField, textView)
            }
        }
        return -1
    }

    @RequiresApi(18)
    private fun getTextDirection(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6
        }
        return textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL ? 7 : 1
    }

    @RequiresApi(18)
    private fun getTextDirectionHeuristic(@NonNull TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR
        }
        if (Build.VERSION.SDK_INT >= 28 && (textView.getInputType() & 15) == 3) {
            Byte directionality = Character.getDirectionality(DecimalFormatSymbols.getInstance(textView.getTextLocale()).getDigitStrings()[0].codePointAt(0))
            return (directionality == 1 || directionality == 2) ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR
        }
        Boolean z = textView.getLayoutDirection() == 1
        switch (textView.getTextDirection()) {
            case 2:
                return TextDirectionHeuristics.ANYRTL_LTR
            case 3:
                return TextDirectionHeuristics.LTR
            case 4:
                return TextDirectionHeuristics.RTL
            case 5:
                return TextDirectionHeuristics.LOCALE
            case 6:
                return TextDirectionHeuristics.FIRSTSTRONG_LTR
            case 7:
                return TextDirectionHeuristics.FIRSTSTRONG_RTL
            default:
                return z ? TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR
        }
    }

    @NonNull
    public static PrecomputedTextCompat.Params getTextMetricsParams(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 28) {
            return new PrecomputedTextCompat.Params(textView.getTextMetricsParams())
        }
        PrecomputedTextCompat.Params.Builder builder = new PrecomputedTextCompat.Params.Builder(TextPaint(textView.getPaint()))
        if (Build.VERSION.SDK_INT >= 23) {
            builder.setBreakStrategy(textView.getBreakStrategy())
            builder.setHyphenationFrequency(textView.getHyphenationFrequency())
        }
        if (Build.VERSION.SDK_INT >= 18) {
            builder.setTextDirection(getTextDirectionHeuristic(textView))
        }
        return builder.build()
    }

    private fun retrieveField(String str) throws NoSuchFieldException {
        Field declaredField = null
        try {
            declaredField = TextView.class.getDeclaredField(str)
            declaredField.setAccessible(true)
            return declaredField
        } catch (NoSuchFieldException e) {
            Log.e(LOG_TAG, "Could not retrieve " + str + " field.")
            return declaredField
        }
    }

    private fun retrieveIntFromField(Field field, TextView textView) {
        try {
            return field.getInt(textView)
        } catch (IllegalAccessException e) {
            Log.d(LOG_TAG, "Could not retrieve value of " + field.getName() + " field.")
            return -1
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, Int i, Int i2, Int i3, Int i4) {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4)
        } else if (textView is AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull Array<Int> iArr, Int i) {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i)
        } else if (textView is AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, Int i) {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeWithDefaults(i)
        } else if (textView is AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i)
        }
    }

    fun setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4)
            return
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4)
            return
        }
        Boolean z = textView.getLayoutDirection() == 1
        Drawable drawable5 = z ? drawable3 : drawable
        if (!z) {
            drawable = drawable3
        }
        textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4)
    }

    fun setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes Int i, @DrawableRes Int i2, @DrawableRes Int i3, @DrawableRes Int i4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4)
            return
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4)
            return
        }
        Boolean z = textView.getLayoutDirection() == 1
        Int i5 = z ? i3 : i
        if (!z) {
            i = i3
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(i5, i2, i, i4)
    }

    fun setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4)
            return
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4)
            return
        }
        Boolean z = textView.getLayoutDirection() == 1
        Drawable drawable5 = z ? drawable3 : drawable
        if (!z) {
            drawable = drawable3
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4)
    }

    fun setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull ActionMode.Callback callback) {
        textView.setCustomSelectionActionModeCallback(wrapCustomSelectionActionModeCallback(textView, callback))
    }

    fun setFirstBaselineToTopHeight(@NonNull TextView textView, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        Preconditions.checkArgumentNonnegative(i)
        if (Build.VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(i)
            return
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt()
        Int i2 = (Build.VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) ? fontMetricsInt.top : fontMetricsInt.ascent
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i - (-i2), textView.getPaddingRight(), textView.getPaddingBottom())
        }
    }

    fun setLastBaselineToBottomHeight(@NonNull TextView textView, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        Preconditions.checkArgumentNonnegative(i)
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt()
        Int i2 = (Build.VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) ? fontMetricsInt.bottom : fontMetricsInt.descent
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2)
        }
    }

    fun setLineHeight(@NonNull TextView textView, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) @Px Int i) {
        Preconditions.checkArgumentNonnegative(i)
        if (i != textView.getPaint().getFontMetricsInt(null)) {
            textView.setLineSpacing(i - r0, 1.0f)
        }
    }

    fun setPrecomputedText(@NonNull TextView textView, @NonNull PrecomputedTextCompat precomputedTextCompat) {
        if (Build.VERSION.SDK_INT >= 28) {
            textView.setText(precomputedTextCompat.getPrecomputedText())
        } else {
            if (!getTextMetricsParams(textView).equals(precomputedTextCompat.getParams())) {
                throw IllegalArgumentException("Given text can not be applied to TextView.")
            }
            textView.setText(precomputedTextCompat)
        }
    }

    fun setTextAppearance(@NonNull TextView textView, @StyleRes Int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(i)
        } else {
            textView.setTextAppearance(textView.getContext(), i)
        }
    }

    fun setTextMetricsParams(@NonNull TextView textView, @NonNull PrecomputedTextCompat.Params params) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setTextDirection(getTextDirection(params.getTextDirection()))
        }
        if (Build.VERSION.SDK_INT >= 23) {
            textView.getPaint().set(params.getTextPaint())
            textView.setBreakStrategy(params.getBreakStrategy())
            textView.setHyphenationFrequency(params.getHyphenationFrequency())
        } else {
            Float textScaleX = params.getTextPaint().getTextScaleX()
            textView.getPaint().set(params.getTextPaint())
            if (textScaleX == textView.getTextScaleX()) {
                textView.setTextScaleX((textScaleX / 2.0f) + 1.0f)
            }
            textView.setTextScaleX(textScaleX)
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static ActionMode.Callback wrapCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull ActionMode.Callback callback) {
        return (Build.VERSION.SDK_INT < 26 || Build.VERSION.SDK_INT > 27 || (callback is OreoCallback)) ? callback : OreoCallback(callback, textView)
    }
}
