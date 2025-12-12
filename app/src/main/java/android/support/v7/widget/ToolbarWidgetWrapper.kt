package android.support.v7.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.menu.ActionMenuItem
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPresenter
import androidx.appcompat.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ToolbarWidgetWrapper implements DecorToolbar {
    private static val AFFECTS_LOGO_MASK = 3
    private static val DEFAULT_FADE_DURATION_MS = 200
    private static val TAG = "ToolbarWidgetWrapper"
    private ActionMenuPresenter mActionMenuPresenter
    private View mCustomView
    private Int mDefaultNavigationContentDescription
    private Drawable mDefaultNavigationIcon
    private Int mDisplayOpts
    private CharSequence mHomeDescription
    private Drawable mIcon
    private Drawable mLogo
    Boolean mMenuPrepared
    private Drawable mNavIcon
    private Int mNavigationMode
    private Spinner mSpinner
    private CharSequence mSubtitle
    private View mTabView
    CharSequence mTitle
    private Boolean mTitleSet
    Toolbar mToolbar
    Window.Callback mWindowCallback

    constructor(Toolbar toolbar, Boolean z) {
        this(toolbar, z, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_material)
    }

    constructor(Toolbar toolbar, Boolean z, Int i, Int i2) {
        this.mNavigationMode = 0
        this.mDefaultNavigationContentDescription = 0
        this.mToolbar = toolbar
        this.mTitle = toolbar.getTitle()
        this.mSubtitle = toolbar.getSubtitle()
        this.mTitleSet = this.mTitle != null
        this.mNavIcon = toolbar.getNavigationIcon()
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(toolbar.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0)
        this.mDefaultNavigationIcon = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_homeAsUpIndicator)
        if (z) {
            CharSequence text = tintTypedArrayObtainStyledAttributes.getText(R.styleable.ActionBar_title)
            if (!TextUtils.isEmpty(text)) {
                setTitle(text)
            }
            CharSequence text2 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.ActionBar_subtitle)
            if (!TextUtils.isEmpty(text2)) {
                setSubtitle(text2)
            }
            Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_logo)
            if (drawable != null) {
                setLogo(drawable)
            }
            Drawable drawable2 = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_icon)
            if (drawable2 != null) {
                setIcon(drawable2)
            }
            if (this.mNavIcon == null && this.mDefaultNavigationIcon != null) {
                setNavigationIcon(this.mDefaultNavigationIcon)
            }
            setDisplayOptions(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.ActionBar_displayOptions, 0))
            Int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_customNavigationLayout, 0)
            if (resourceId != 0) {
                setCustomView(LayoutInflater.from(this.mToolbar.getContext()).inflate(resourceId, (ViewGroup) this.mToolbar, false))
                setDisplayOptions(this.mDisplayOpts | 16)
            }
            Int layoutDimension = tintTypedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0)
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams()
                layoutParams.height = layoutDimension
                this.mToolbar.setLayoutParams(layoutParams)
            }
            Int dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1)
            Int dimensionPixelOffset2 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1)
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(dimensionPixelOffset, 0), Math.max(dimensionPixelOffset2, 0))
            }
            Int resourceId2 = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_titleTextStyle, 0)
            if (resourceId2 != 0) {
                this.mToolbar.setTitleTextAppearance(this.mToolbar.getContext(), resourceId2)
            }
            Int resourceId3 = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0)
            if (resourceId3 != 0) {
                this.mToolbar.setSubtitleTextAppearance(this.mToolbar.getContext(), resourceId3)
            }
            Int resourceId4 = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionBar_popupTheme, 0)
            if (resourceId4 != 0) {
                this.mToolbar.setPopupTheme(resourceId4)
            }
        } else {
            this.mDisplayOpts = detectDisplayOptions()
        }
        tintTypedArrayObtainStyledAttributes.recycle()
        setDefaultNavigationContentDescription(i)
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription()
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: android.support.v7.widget.ToolbarWidgetWrapper.1
            final ActionMenuItem mNavItem

            {
                this.mNavItem = ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, android.R.id.home, 0, 0, ToolbarWidgetWrapper.this.mTitle)
            }

            @Override // android.view.View.OnClickListener
            fun onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback == null || !ToolbarWidgetWrapper.this.mMenuPrepared) {
                    return
                }
                ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem)
            }
        })
    }

    private fun detectDisplayOptions() {
        if (this.mToolbar.getNavigationIcon() == null) {
            return 11
        }
        this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon()
        return 15
    }

    private fun ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle)
            this.mSpinner.setLayoutParams(new Toolbar.LayoutParams(-2, -2, 8388627))
        }
    }

    private fun setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence)
        }
    }

    private fun updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            if (TextUtils.isEmpty(this.mHomeDescription)) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription)
            } else {
                this.mToolbar.setNavigationContentDescription(this.mHomeDescription)
            }
        }
    }

    private fun updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) != 0) {
            this.mToolbar.setNavigationIcon(this.mNavIcon != null ? this.mNavIcon : this.mDefaultNavigationIcon)
        } else {
            this.mToolbar.setNavigationIcon((Drawable) null)
        }
    }

    private fun updateToolbarLogo() {
        Drawable drawable = null
        if ((this.mDisplayOpts & 2) != 0) {
            drawable = ((this.mDisplayOpts & 1) == 0 || this.mLogo == null) ? this.mIcon : this.mLogo
        }
        this.mToolbar.setLogo(drawable)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun animateToVisibility(Int i) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = setupAnimatorToVisibility(i, DEFAULT_FADE_DURATION_MS)
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.start()
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun collapseActionView() {
        this.mToolbar.collapseActionView()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getContext() {
        return this.mToolbar.getContext()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getCustomView() {
        return this.mCustomView
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getDisplayOptions() {
        return this.mDisplayOpts
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getDropdownItemCount() {
        if (this.mSpinner != null) {
            return this.mSpinner.getCount()
        }
        return 0
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getDropdownSelectedPosition() {
        if (this.mSpinner != null) {
            return this.mSpinner.getSelectedItemPosition()
        }
        return 0
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getHeight() {
        return this.mToolbar.getHeight()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getMenu() {
        return this.mToolbar.getMenu()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getNavigationMode() {
        return this.mNavigationMode
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getSubtitle() {
        return this.mToolbar.getSubtitle()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getTitle() {
        return this.mToolbar.getTitle()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getViewGroup() {
        return this.mToolbar
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun getVisibility() {
        return this.mToolbar.getVisibility()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun hasEmbeddedTabs() {
        return this.mTabView != null
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun hasIcon() {
        return this.mIcon != null
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun hasLogo() {
        return this.mLogo != null
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun initIndeterminateProgress() {
        Log.i(TAG, "Progress display unsupported")
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun initProgress() {
        Log.i(TAG, "Progress display unsupported")
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun isTitleTruncated() {
        return this.mToolbar.isTitleTruncated()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun restoreHierarchyState(SparseArray sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun saveHierarchyState(SparseArray sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setBackgroundDrawable(Drawable drawable) {
        ViewCompat.setBackground(this.mToolbar, drawable)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setCollapsible(Boolean z) {
        this.mToolbar.setCollapsible(z)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setCustomView(View view) {
        if (this.mCustomView != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.removeView(this.mCustomView)
        }
        this.mCustomView = view
        if (view == null || (this.mDisplayOpts & 16) == 0) {
            return
        }
        this.mToolbar.addView(this.mCustomView)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setDefaultNavigationContentDescription(Int i) {
        if (i == this.mDefaultNavigationContentDescription) {
            return
        }
        this.mDefaultNavigationContentDescription = i
        if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
            setNavigationContentDescription(this.mDefaultNavigationContentDescription)
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setDefaultNavigationIcon(Drawable drawable) {
        if (this.mDefaultNavigationIcon != drawable) {
            this.mDefaultNavigationIcon = drawable
            updateNavigationIcon()
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setDisplayOptions(Int i) {
        Int i2 = this.mDisplayOpts ^ i
        this.mDisplayOpts = i
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility()
                }
                updateNavigationIcon()
            }
            if ((i2 & 3) != 0) {
                updateToolbarLogo()
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle)
                    this.mToolbar.setSubtitle(this.mSubtitle)
                } else {
                    this.mToolbar.setTitle((CharSequence) null)
                    this.mToolbar.setSubtitle((CharSequence) null)
                }
            }
            if ((i2 & 16) == 0 || this.mCustomView == null) {
                return
            }
            if ((i & 16) != 0) {
                this.mToolbar.addView(this.mCustomView)
            } else {
                this.mToolbar.removeView(this.mCustomView)
            }
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ensureSpinner()
        this.mSpinner.setAdapter(spinnerAdapter)
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setDropdownSelectedPosition(Int i) {
        if (this.mSpinner == null) {
            throw IllegalStateException("Can't set dropdown selected position without an adapter")
        }
        this.mSpinner.setSelection(i)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView)
        }
        this.mTabView = scrollingTabContainerView
        if (scrollingTabContainerView == null || this.mNavigationMode != 2) {
            return
        }
        this.mToolbar.addView(this.mTabView, 0)
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams()
        layoutParams.width = -2
        layoutParams.height = -2
        layoutParams.gravity = 8388691
        scrollingTabContainerView.setAllowCollapse(true)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setHomeButtonEnabled(Boolean z) {
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setIcon(Int i) {
        setIcon(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setIcon(Drawable drawable) {
        this.mIcon = drawable
        updateToolbarLogo()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setLogo(Int i) {
        setLogo(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setLogo(Drawable drawable) {
        this.mLogo = drawable
        updateToolbarLogo()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setMenu(Menu menu, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = ActionMenuPresenter(this.mToolbar.getContext())
            this.mActionMenuPresenter.setId(R.id.action_menu_presenter)
        }
        this.mActionMenuPresenter.setCallback(callback)
        this.mToolbar.setMenu((MenuBuilder) menu, this.mActionMenuPresenter)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setMenuPrepared() {
        this.mMenuPrepared = true
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setNavigationContentDescription(Int i) {
        setNavigationContentDescription(i == 0 ? null : getContext().getString(i))
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence
        updateHomeAccessibility()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setNavigationIcon(Int i) {
        setNavigationIcon(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setNavigationIcon(Drawable drawable) {
        this.mNavIcon = drawable
        updateNavigationIcon()
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setNavigationMode(Int i) {
        Int i2 = this.mNavigationMode
        if (i != i2) {
            switch (i2) {
                case 1:
                    if (this.mSpinner != null && this.mSpinner.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mSpinner)
                        break
                    }
                    break
                case 2:
                    if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mTabView)
                        break
                    }
                    break
            }
            this.mNavigationMode = i
            switch (i) {
                case 0:
                    return
                case 1:
                    ensureSpinner()
                    this.mToolbar.addView(this.mSpinner, 0)
                    return
                case 2:
                    if (this.mTabView != null) {
                        this.mToolbar.addView(this.mTabView, 0)
                        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams()
                        layoutParams.width = -2
                        layoutParams.height = -2
                        layoutParams.gravity = 8388691
                        return
                    }
                    return
                default:
                    throw IllegalArgumentException("Invalid navigation mode " + i)
            }
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence)
        }
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setTitle(CharSequence charSequence) {
        this.mTitleSet = true
        setTitleInt(charSequence)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setVisibility(Int i) {
        this.mToolbar.setVisibility(i)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setWindowTitle(CharSequence charSequence) {
        if (this.mTitleSet) {
            return
        }
        setTitleInt(charSequence)
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun setupAnimatorToVisibility(final Int i, Long j) {
        return ViewCompat.animate(this.mToolbar).alpha(i == 0 ? 1.0f : 0.0f).setDuration(j).setListener(ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.widget.ToolbarWidgetWrapper.2
            private Boolean mCanceled = false

            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
            fun onAnimationCancel(View view) {
                this.mCanceled = true
            }

            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
            fun onAnimationEnd(View view) {
                if (this.mCanceled) {
                    return
                }
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(i)
            }

            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
            fun onAnimationStart(View view) {
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(0)
            }
        })
    }

    @Override // android.support.v7.widget.DecorToolbar
    fun showOverflowMenu() {
        return this.mToolbar.showOverflowMenu()
    }
}
