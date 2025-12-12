package android.support.v7.view.menu

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.ForwardingListener
import android.support.v7.widget.TooltipCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionMenuItemView extends AppCompatTextView implements MenuView.ItemView, ActionMenuView.ActionMenuChildView, View.OnClickListener {
    private static val MAX_ICON_SIZE = 32
    private static val TAG = "ActionMenuItemView"
    private Boolean mAllowTextWithIcon
    private Boolean mExpandedFormat
    private ForwardingListener mForwardingListener
    private Drawable mIcon
    MenuItemImpl mItemData
    MenuBuilder.ItemInvoker mItemInvoker
    private Int mMaxIconSize
    private Int mMinWidth
    PopupCallback mPopupCallback
    private Int mSavedPaddingLeft
    private CharSequence mTitle

    class ActionMenuItemForwardingListener extends ForwardingListener {
        constructor() {
            super(ActionMenuItemView.this)
        }

        @Override // android.support.v7.widget.ForwardingListener
        fun getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup()
            }
            return null
        }

        @Override // android.support.v7.widget.ForwardingListener
        protected fun onForwardingStarted() {
            ShowableListMenu popup
            return ActionMenuItemView.this.mItemInvoker != null && ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData) && (popup = getPopup()) != null && popup.isShowing()
        }
    }

    abstract class PopupCallback {
        public abstract ShowableListMenu getPopup()
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        Resources resources = context.getResources()
        this.mAllowTextWithIcon = shouldAllowTextWithIcon()
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, i, 0)
        this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0)
        typedArrayObtainStyledAttributes.recycle()
        this.mMaxIconSize = (Int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f)
        setOnClickListener(this)
        this.mSavedPaddingLeft = -1
        setSaveEnabled(false)
    }

    private fun shouldAllowTextWithIcon() {
        Configuration configuration = getContext().getResources().getConfiguration()
        Int i = configuration.screenWidthDp
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2
    }

    private fun updateTextButtonVisibility() {
        Boolean z = false
        Boolean z2 = !TextUtils.isEmpty(this.mTitle)
        if (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat))) {
            z = true
        }
        Boolean z3 = z2 & z
        setText(z3 ? this.mTitle : null)
        CharSequence contentDescription = this.mItemData.getContentDescription()
        if (TextUtils.isEmpty(contentDescription)) {
            setContentDescription(z3 ? null : this.mItemData.getTitle())
        } else {
            setContentDescription(contentDescription)
        }
        CharSequence tooltipText = this.mItemData.getTooltipText()
        if (TextUtils.isEmpty(tooltipText)) {
            TooltipCompat.setTooltipText(this, z3 ? null : this.mItemData.getTitle())
        } else {
            TooltipCompat.setTooltipText(this, tooltipText)
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun getItemData() {
        return this.mItemData
    }

    fun hasText() {
        return !TextUtils.isEmpty(getText())
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun initialize(MenuItemImpl menuItemImpl, Int i) {
        this.mItemData = menuItemImpl
        setIcon(menuItemImpl.getIcon())
        setTitle(menuItemImpl.getTitleForItemView(this))
        setId(menuItemImpl.getItemId())
        setVisibility(menuItemImpl.isVisible() ? 0 : 8)
        setEnabled(menuItemImpl.isEnabled())
        if (menuItemImpl.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = ActionMenuItemForwardingListener()
        }
    }

    @Override // android.support.v7.widget.ActionMenuView.ActionMenuChildView
    fun needsDividerAfter() {
        return hasText()
    }

    @Override // android.support.v7.widget.ActionMenuView.ActionMenuChildView
    fun needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData)
        }
    }

    @Override // android.widget.TextView, android.view.View
    fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        this.mAllowTextWithIcon = shouldAllowTextWithIcon()
        updateTextButtonVisibility()
    }

    @Override // android.support.v7.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Boolean zHasText = hasText()
        if (zHasText && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom())
        }
        super.onMeasure(i, i2)
        Int mode = View.MeasureSpec.getMode(i)
        Int size = View.MeasureSpec.getSize(i)
        Int measuredWidth = getMeasuredWidth()
        Int iMin = mode == Integer.MIN_VALUE ? Math.min(size, this.mMinWidth) : this.mMinWidth
        if (mode != 1073741824 && this.mMinWidth > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2)
        }
        if (zHasText || this.mIcon == null) {
            return
        }
        super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom())
    }

    @Override // android.widget.TextView, android.view.View
    fun onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null)
    }

    @Override // android.widget.TextView, android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        if (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch(this, motionEvent)) {
            return true
        }
        return super.onTouchEvent(motionEvent)
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun prefersCondensedTitle() {
        return true
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setCheckable(Boolean z) {
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setChecked(Boolean z) {
    }

    fun setExpandedFormat(Boolean z) {
        if (this.mExpandedFormat != z) {
            this.mExpandedFormat = z
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged()
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setIcon(Drawable drawable) {
        this.mIcon = drawable
        if (drawable != null) {
            Int intrinsicWidth = drawable.getIntrinsicWidth()
            Int intrinsicHeight = drawable.getIntrinsicHeight()
            if (intrinsicWidth > this.mMaxIconSize) {
                Float f = this.mMaxIconSize / intrinsicWidth
                intrinsicWidth = this.mMaxIconSize
                intrinsicHeight = (Int) (intrinsicHeight * f)
            }
            if (intrinsicHeight > this.mMaxIconSize) {
                Float f2 = this.mMaxIconSize / intrinsicHeight
                intrinsicHeight = this.mMaxIconSize
                intrinsicWidth = (Int) (intrinsicWidth * f2)
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        setCompoundDrawables(drawable, null, null, null)
        updateTextButtonVisibility()
    }

    fun setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker
    }

    @Override // android.widget.TextView, android.view.View
    fun setPadding(Int i, Int i2, Int i3, Int i4) {
        this.mSavedPaddingLeft = i
        super.setPadding(i, i2, i3, i4)
    }

    fun setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setShortcut(Boolean z, Char c) {
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        updateTextButtonVisibility()
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun showsIcon() {
        return true
    }
}
