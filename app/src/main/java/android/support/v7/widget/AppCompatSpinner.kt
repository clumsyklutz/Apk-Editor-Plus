package android.support.v7.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.database.DataSetObserver
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.TintableBackgroundView
import androidx.core.view.ViewCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.SpinnerAdapter
import java.lang.reflect.InvocationTargetException

class AppCompatSpinner extends Spinner implements TintableBackgroundView {
    private static final Array<Int> ATTRS_ANDROID_SPINNERMODE = {R.attr.spinnerMode}
    private static val MAX_ITEMS_MEASURED = 15
    private static val MODE_DIALOG = 0
    private static val MODE_DROPDOWN = 1
    private static val MODE_THEME = -1
    private static val TAG = "AppCompatSpinner"
    private final AppCompatBackgroundHelper mBackgroundTintHelper
    Int mDropDownWidth
    private ForwardingListener mForwardingListener
    DropdownPopup mPopup
    private final Context mPopupContext
    private final Boolean mPopupSet
    private SpinnerAdapter mTempAdapter
    final Rect mTempRect

    class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter
        private ListAdapter mListAdapter

        constructor(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            this.mAdapter = spinnerAdapter
            if (spinnerAdapter is ListAdapter) {
                this.mListAdapter = (ListAdapter) spinnerAdapter
            }
            if (theme != null) {
                if (Build.VERSION.SDK_INT >= 23 && (spinnerAdapter is android.widget.ThemedSpinnerAdapter)) {
                    android.widget.ThemedSpinnerAdapter themedSpinnerAdapter = (android.widget.ThemedSpinnerAdapter) spinnerAdapter
                    if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                        themedSpinnerAdapter.setDropDownViewTheme(theme)
                        return
                    }
                    return
                }
                if (spinnerAdapter is ThemedSpinnerAdapter) {
                    ThemedSpinnerAdapter themedSpinnerAdapter2 = (ThemedSpinnerAdapter) spinnerAdapter
                    if (themedSpinnerAdapter2.getDropDownViewTheme() == null) {
                        themedSpinnerAdapter2.setDropDownViewTheme(theme)
                    }
                }
            }
        }

        @Override // android.widget.ListAdapter
        fun areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled()
            }
            return true
        }

        @Override // android.widget.Adapter
        fun getCount() {
            if (this.mAdapter == null) {
                return 0
            }
            return this.mAdapter.getCount()
        }

        @Override // android.widget.SpinnerAdapter
        fun getDropDownView(Int i, View view, ViewGroup viewGroup) {
            if (this.mAdapter == null) {
                return null
            }
            return this.mAdapter.getDropDownView(i, view, viewGroup)
        }

        @Override // android.widget.Adapter
        fun getItem(Int i) {
            if (this.mAdapter == null) {
                return null
            }
            return this.mAdapter.getItem(i)
        }

        @Override // android.widget.Adapter
        fun getItemId(Int i) {
            if (this.mAdapter == null) {
                return -1L
            }
            return this.mAdapter.getItemId(i)
        }

        @Override // android.widget.Adapter
        fun getItemViewType(Int i) {
            return 0
        }

        @Override // android.widget.Adapter
        fun getView(Int i, View view, ViewGroup viewGroup) {
            return getDropDownView(i, view, viewGroup)
        }

        @Override // android.widget.Adapter
        fun getViewTypeCount() {
            return 1
        }

        @Override // android.widget.Adapter
        fun hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds()
        }

        @Override // android.widget.Adapter
        fun isEmpty() {
            return getCount() == 0
        }

        @Override // android.widget.ListAdapter
        fun isEnabled(Int i) {
            ListAdapter listAdapter = this.mListAdapter
            if (listAdapter != null) {
                return listAdapter.isEnabled(i)
            }
            return true
        }

        @Override // android.widget.Adapter
        fun registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver)
            }
        }

        @Override // android.widget.Adapter
        fun unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver)
            }
        }
    }

    class DropdownPopup extends ListPopupWindow {
        ListAdapter mAdapter
        private CharSequence mHintText
        private final Rect mVisibleRect

        constructor(Context context, AttributeSet attributeSet, Int i) {
            super(context, attributeSet, i)
            this.mVisibleRect = Rect()
            setAnchorView(AppCompatSpinner.this)
            setModal(true)
            setPromptPosition(0)
            setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.support.v7.widget.AppCompatSpinner.DropdownPopup.1
                @Override // android.widget.AdapterView.OnItemClickListener
                fun onItemClick(AdapterView adapterView, View view, Int i2, Long j) {
                    AppCompatSpinner.this.setSelection(i2)
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(view, i2, DropdownPopup.this.mAdapter.getItemId(i2))
                    }
                    DropdownPopup.this.dismiss()
                }
            })
        }

        Unit computeContentWidth() {
            Int i
            Drawable background = getBackground()
            if (background != null) {
                background.getPadding(AppCompatSpinner.this.mTempRect)
                i = ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left
            } else {
                Rect rect = AppCompatSpinner.this.mTempRect
                AppCompatSpinner.this.mTempRect.right = 0
                rect.left = 0
                i = 0
            }
            Int paddingLeft = AppCompatSpinner.this.getPaddingLeft()
            Int paddingRight = AppCompatSpinner.this.getPaddingRight()
            Int width = AppCompatSpinner.this.getWidth()
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                Int iCompatMeasureContentWidth = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground())
                Int i2 = (AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left) - AppCompatSpinner.this.mTempRect.right
                if (iCompatMeasureContentWidth <= i2) {
                    i2 = iCompatMeasureContentWidth
                }
                setContentWidth(Math.max(i2, (width - paddingLeft) - paddingRight))
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                setContentWidth((width - paddingLeft) - paddingRight)
            } else {
                setContentWidth(AppCompatSpinner.this.mDropDownWidth)
            }
            setHorizontalOffset(ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? ((width - paddingRight) - getWidth()) + i : i + paddingLeft)
        }

        fun getHintText() {
            return this.mHintText
        }

        Boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect)
        }

        @Override // android.support.v7.widget.ListPopupWindow
        fun setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter)
            this.mAdapter = listAdapter
        }

        fun setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence
        }

        @Override // android.support.v7.widget.ListPopupWindow, android.support.v7.view.menu.ShowableListMenu
        fun show() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            ViewTreeObserver viewTreeObserver
            Boolean zIsShowing = isShowing()
            computeContentWidth()
            setInputMethodMode(2)
            super.show()
            getListView().setChoiceMode(1)
            setSelection(AppCompatSpinner.this.getSelectedItemPosition())
            if (zIsShowing || (viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) == null) {
                return
            }
            final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.support.v7.widget.AppCompatSpinner.DropdownPopup.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                fun onGlobalLayout() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                    if (!DropdownPopup.this.isVisibleToUser(AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss()
                    } else {
                        DropdownPopup.this.computeContentWidth()
                        DropdownPopup.super.show()
                    }
                }
            }
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
            setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: android.support.v7.widget.AppCompatSpinner.DropdownPopup.3
                @Override // android.widget.PopupWindow.OnDismissListener
                fun onDismiss() {
                    ViewTreeObserver viewTreeObserver2 = AppCompatSpinner.this.getViewTreeObserver()
                    if (viewTreeObserver2 != null) {
                        viewTreeObserver2.removeGlobalOnLayoutListener(onGlobalLayoutListener)
                    }
                }
            })
        }
    }

    constructor(Context context) {
        this(context, (AttributeSet) null)
    }

    constructor(Context context, Int i) {
        this(context, null, android.support.v7.appcompat.R.attr.spinnerStyle, i)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.support.v7.appcompat.R.attr.spinnerStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        this(context, attributeSet, i, -1)
    }

    constructor(Context context, AttributeSet attributeSet, Int i, Int i2) {
        this(context, attributeSet, i, i2, null)
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x009d  */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.support.v7.widget.AppCompatSpinner] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    constructor(android.content.Context r9, android.util.AttributeSet r10, Int r11, Int r12, android.content.res.Resources.Theme r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, Int, Int, android.content.res.Resources$Theme):Unit")
    }

    Int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        View view
        if (spinnerAdapter == null) {
            return 0
        }
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0)
        Int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0)
        Int iMax = Math.max(0, getSelectedItemPosition())
        Int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15)
        Int iMax2 = Math.max(0, iMax - (15 - (iMin - iMax)))
        View view2 = null
        Int iMax3 = 0
        Int i = 0
        while (iMax2 < iMin) {
            Int itemViewType = spinnerAdapter.getItemViewType(iMax2)
            if (itemViewType != i) {
                view = null
            } else {
                itemViewType = i
                view = view2
            }
            view2 = spinnerAdapter.getView(iMax2, view, this)
            if (view2.getLayoutParams() == null) {
                view2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2))
            }
            view2.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
            iMax3 = Math.max(iMax3, view2.getMeasuredWidth())
            iMax2++
            i = itemViewType
        }
        if (drawable == null) {
            return iMax3
        }
        drawable.getPadding(this.mTempRect)
        return this.mTempRect.left + this.mTempRect.right + iMax3
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint()
        }
    }

    @Override // android.widget.Spinner
    fun getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset()
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset()
        }
        return 0
    }

    @Override // android.widget.Spinner
    fun getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset()
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset()
        }
        return 0
    }

    @Override // android.widget.Spinner
    fun getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth()
        }
        return 0
    }

    @Override // android.widget.Spinner
    fun getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground()
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground()
        }
        return null
    }

    @Override // android.widget.Spinner
    fun getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getPopupContext()
        }
        return null
    }

    @Override // android.widget.Spinner
    fun getPrompt() {
        return this.mPopup != null ? this.mPopup.getHintText() : super.getPrompt()
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList()
        }
        return null
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode()
        }
        return null
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (this.mPopup == null || !this.mPopup.isShowing()) {
            return
        }
        this.mPopup.dismiss()
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
        if (this.mPopup == null || View.MeasureSpec.getMode(i) != Integer.MIN_VALUE) {
            return
        }
        setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(i)), getMeasuredHeight())
    }

    @Override // android.widget.Spinner, android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        if (this.mForwardingListener == null || !this.mForwardingListener.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent)
        }
        return true
    }

    @Override // android.widget.Spinner, android.view.View
    fun performClick() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.mPopup == null) {
            return super.performClick()
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show()
        }
        return true
    }

    @Override // android.widget.AdapterView
    fun setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter
            return
        }
        super.setAdapter(spinnerAdapter)
        if (this.mPopup != null) {
            this.mPopup.setAdapter(DropDownAdapter(spinnerAdapter, (this.mPopupContext == null ? getContext() : this.mPopupContext).getTheme()))
        }
    }

    @Override // android.view.View
    fun setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable)
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable)
        }
    }

    @Override // android.view.View
    fun setBackgroundResource(@DrawableRes Int i) {
        super.setBackgroundResource(i)
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(i)
        }
    }

    @Override // android.widget.Spinner
    fun setDropDownHorizontalOffset(Int i) {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(i)
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(i)
        }
    }

    @Override // android.widget.Spinner
    fun setDropDownVerticalOffset(Int i) {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(i)
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(i)
        }
    }

    @Override // android.widget.Spinner
    fun setDropDownWidth(Int i) {
        if (this.mPopup != null) {
            this.mDropDownWidth = i
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(i)
        }
    }

    @Override // android.widget.Spinner
    fun setPopupBackgroundDrawable(Drawable drawable) {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(drawable)
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(drawable)
        }
    }

    @Override // android.widget.Spinner
    fun setPopupBackgroundResource(@DrawableRes Int i) {
        setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), i))
    }

    @Override // android.widget.Spinner
    fun setPrompt(CharSequence charSequence) {
        if (this.mPopup != null) {
            this.mPopup.setPromptText(charSequence)
        } else {
            super.setPrompt(charSequence)
        }
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList)
        }
    }

    @Override // android.support.v4.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode)
        }
    }
}
