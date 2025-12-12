package android.support.v7.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.MenuRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.view.AbsSavedState
import androidx.core.view.GravityCompat
import android.support.v4.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat
import android.support.v7.app.ActionBar
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.CollapsibleActionView
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.support.v7.view.menu.SubMenuBuilder
import android.support.v7.widget.ActionMenuView
import android.text.Layout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList
import java.util.List

class Toolbar extends ViewGroup {
    private static val TAG = "Toolbar"
    private MenuPresenter.Callback mActionMenuPresenterCallback
    Int mButtonGravity
    ImageButton mCollapseButtonView
    private CharSequence mCollapseDescription
    private Drawable mCollapseIcon
    private Boolean mCollapsible
    private Int mContentInsetEndWithActions
    private Int mContentInsetStartWithNavigation
    private RtlSpacingHelper mContentInsets
    private Boolean mEatingHover
    private Boolean mEatingTouch
    View mExpandedActionView
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter
    private Int mGravity
    private final ArrayList mHiddenViews
    private ImageView mLogoView
    private Int mMaxButtonHeight
    private MenuBuilder.Callback mMenuBuilderCallback
    private ActionMenuView mMenuView
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener
    private ImageButton mNavButtonView
    OnMenuItemClickListener mOnMenuItemClickListener
    private ActionMenuPresenter mOuterActionMenuPresenter
    private Context mPopupContext
    private Int mPopupTheme
    private final Runnable mShowOverflowMenuRunnable
    private CharSequence mSubtitleText
    private Int mSubtitleTextAppearance
    private Int mSubtitleTextColor
    private TextView mSubtitleTextView
    private final Array<Int> mTempMargins
    private final ArrayList mTempViews
    private Int mTitleMarginBottom
    private Int mTitleMarginEnd
    private Int mTitleMarginStart
    private Int mTitleMarginTop
    private CharSequence mTitleText
    private Int mTitleTextAppearance
    private Int mTitleTextColor
    private TextView mTitleTextView
    private ToolbarWidgetWrapper mWrapper

    class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem
        MenuBuilder mMenu

        ExpandedActionViewMenuPresenter() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView is CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewCollapsed()
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView)
            Toolbar.this.removeView(Toolbar.this.mCollapseButtonView)
            Toolbar.this.mExpandedActionView = null
            Toolbar.this.addChildrenForExpandedActionView()
            this.mCurrentExpandedItem = null
            Toolbar.this.requestLayout()
            menuItemImpl.setActionViewExpanded(false)
            return true
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView()
            ViewParent parent = Toolbar.this.mCollapseButtonView.getParent()
            if (parent != Toolbar.this) {
                if (parent is ViewGroup) {
                    ((ViewGroup) parent).removeView(Toolbar.this.mCollapseButtonView)
                }
                Toolbar.this.addView(Toolbar.this.mCollapseButtonView)
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView()
            this.mCurrentExpandedItem = menuItemImpl
            ViewParent parent2 = Toolbar.this.mExpandedActionView.getParent()
            if (parent2 != Toolbar.this) {
                if (parent2 is ViewGroup) {
                    ((ViewGroup) parent2).removeView(Toolbar.this.mExpandedActionView)
                }
                LayoutParams layoutParamsGenerateDefaultLayoutParams = Toolbar.this.generateDefaultLayoutParams()
                layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (Toolbar.this.mButtonGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall)
                layoutParamsGenerateDefaultLayoutParams.mViewType = 2
                Toolbar.this.mExpandedActionView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams)
                Toolbar.this.addView(Toolbar.this.mExpandedActionView)
            }
            Toolbar.this.removeChildrenForExpandedActionView()
            Toolbar.this.requestLayout()
            menuItemImpl.setActionViewExpanded(true)
            if (Toolbar.this.mExpandedActionView is CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewExpanded()
            }
            return true
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun flagActionItems() {
            return false
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun getId() {
            return 0
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun getMenuView(ViewGroup viewGroup) {
            return null
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun initForMenu(Context context, MenuBuilder menuBuilder) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem)
            }
            this.mMenu = menuBuilder
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun onSaveInstanceState() {
            return null
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun setCallback(MenuPresenter.Callback callback) {
        }

        @Override // android.support.v7.view.menu.MenuPresenter
        fun updateMenuView(Boolean z) {
            Boolean z2 = false
            if (this.mCurrentExpandedItem != null) {
                if (this.mMenu != null) {
                    Int size = this.mMenu.size()
                    Int i = 0
                    while (true) {
                        if (i >= size) {
                            break
                        }
                        if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            z2 = true
                            break
                        }
                        i++
                    }
                }
                if (z2) {
                    return
                }
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem)
            }
        }
    }

    class LayoutParams extends ActionBar.LayoutParams {
        static val CUSTOM = 0
        static val EXPANDED = 2
        static val SYSTEM = 1
        Int mViewType

        constructor(Int i) {
            this(-2, -1, i)
        }

        constructor(Int i, Int i2) {
            super(i, i2)
            this.mViewType = 0
            this.gravity = 8388627
        }

        constructor(Int i, Int i2, Int i3) {
            super(i, i2)
            this.mViewType = 0
            this.gravity = i3
        }

        constructor(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            this.mViewType = 0
        }

        constructor(ActionBar.LayoutParams layoutParams) {
            super(layoutParams)
            this.mViewType = 0
        }

        constructor(LayoutParams layoutParams) {
            super((ActionBar.LayoutParams) layoutParams)
            this.mViewType = 0
            this.mViewType = layoutParams.mViewType
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.mViewType = 0
        }

        constructor(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
            this.mViewType = 0
            copyMarginsFromCompat(marginLayoutParams)
        }

        Unit copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin
            this.topMargin = marginLayoutParams.topMargin
            this.rightMargin = marginLayoutParams.rightMargin
            this.bottomMargin = marginLayoutParams.bottomMargin
        }
    }

    public interface OnMenuItemClickListener {
        Boolean onMenuItemClick(MenuItem menuItem)
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v7.widget.Toolbar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel, null)
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return SavedState(parcel, classLoader)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        Int expandedMenuItemId
        Boolean isOverflowOpen

        constructor(Parcel parcel) {
            this(parcel, null)
        }

        constructor(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader)
            this.expandedMenuItemId = parcel.readInt()
            this.isOverflowOpen = parcel.readInt() != 0
        }

        constructor(Parcelable parcelable) {
            super(parcelable)
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeInt(this.expandedMenuItemId)
            parcel.writeInt(this.isOverflowOpen ? 1 : 0)
        }
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle)
    }

    constructor(Context context, @Nullable AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mGravity = 8388627
        this.mTempViews = ArrayList()
        this.mHiddenViews = ArrayList()
        this.mTempMargins = new Int[2]
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() { // from class: android.support.v7.widget.Toolbar.1
            @Override // android.support.v7.widget.ActionMenuView.OnMenuItemClickListener
            fun onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem)
                }
                return false
            }
        }
        this.mShowOverflowMenuRunnable = Runnable() { // from class: android.support.v7.widget.Toolbar.2
            @Override // java.lang.Runnable
            fun run() {
                Toolbar.this.showOverflowMenu()
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.Toolbar, i, 0)
        this.mTitleTextAppearance = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0)
        this.mSubtitleTextAppearance = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0)
        this.mGravity = tintTypedArrayObtainStyledAttributes.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity)
        this.mButtonGravity = tintTypedArrayObtainStyledAttributes.getInteger(R.styleable.Toolbar_buttonGravity, 48)
        Int dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0)
        dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_titleMargins) ? tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, dimensionPixelOffset) : dimensionPixelOffset
        this.mTitleMarginBottom = dimensionPixelOffset
        this.mTitleMarginTop = dimensionPixelOffset
        this.mTitleMarginEnd = dimensionPixelOffset
        this.mTitleMarginStart = dimensionPixelOffset
        Int dimensionPixelOffset2 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1)
        if (dimensionPixelOffset2 >= 0) {
            this.mTitleMarginStart = dimensionPixelOffset2
        }
        Int dimensionPixelOffset3 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1)
        if (dimensionPixelOffset3 >= 0) {
            this.mTitleMarginEnd = dimensionPixelOffset3
        }
        Int dimensionPixelOffset4 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1)
        if (dimensionPixelOffset4 >= 0) {
            this.mTitleMarginTop = dimensionPixelOffset4
        }
        Int dimensionPixelOffset5 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1)
        if (dimensionPixelOffset5 >= 0) {
            this.mTitleMarginBottom = dimensionPixelOffset5
        }
        this.mMaxButtonHeight = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1)
        Int dimensionPixelOffset6 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE)
        Int dimensionPixelOffset7 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE)
        Int dimensionPixelSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0)
        Int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0)
        ensureContentInsets()
        this.mContentInsets.setAbsolute(dimensionPixelSize, dimensionPixelSize2)
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(dimensionPixelOffset6, dimensionPixelOffset7)
        }
        this.mContentInsetStartWithNavigation = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE)
        this.mContentInsetEndWithActions = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE)
        this.mCollapseIcon = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_collapseIcon)
        this.mCollapseDescription = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_collapseContentDescription)
        CharSequence text = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_title)
        if (!TextUtils.isEmpty(text)) {
            setTitle(text)
        }
        CharSequence text2 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_subtitle)
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2)
        }
        this.mPopupContext = getContext()
        setPopupTheme(tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_popupTheme, 0))
        Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_navigationIcon)
        if (drawable != null) {
            setNavigationIcon(drawable)
        }
        CharSequence text3 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_navigationContentDescription)
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3)
        }
        Drawable drawable2 = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_logo)
        if (drawable2 != null) {
            setLogo(drawable2)
        }
        CharSequence text4 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_logoDescription)
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4)
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(tintTypedArrayObtainStyledAttributes.getColor(R.styleable.Toolbar_titleTextColor, -1))
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(tintTypedArrayObtainStyledAttributes.getColor(R.styleable.Toolbar_subtitleTextColor, -1))
        }
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    private fun addCustomViewsWithGravity(List list, Int i) {
        Boolean z = ViewCompat.getLayoutDirection(this) == 1
        Int childCount = getChildCount()
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this))
        list.clear()
        if (!z) {
            for (Int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2)
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                if (layoutParams.mViewType == 0 && shouldLayout(childAt) && getChildHorizontalGravity(layoutParams.gravity) == absoluteGravity) {
                    list.add(childAt)
                }
            }
            return
        }
        for (Int i3 = childCount - 1; i3 >= 0; i3--) {
            View childAt2 = getChildAt(i3)
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams()
            if (layoutParams2.mViewType == 0 && shouldLayout(childAt2) && getChildHorizontalGravity(layoutParams2.gravity) == absoluteGravity) {
                list.add(childAt2)
            }
        }
    }

    private fun addSystemView(View view, Boolean z) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams()
        LayoutParams layoutParamsGenerateDefaultLayoutParams = layoutParams == null ? generateDefaultLayoutParams() : !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (LayoutParams) layoutParams
        layoutParamsGenerateDefaultLayoutParams.mViewType = 1
        if (!z || this.mExpandedActionView == null) {
            addView(view, layoutParamsGenerateDefaultLayoutParams)
        } else {
            view.setLayoutParams(layoutParamsGenerateDefaultLayoutParams)
            this.mHiddenViews.add(view)
        }
    }

    private fun ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = RtlSpacingHelper()
        }
    }

    private fun ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = AppCompatImageView(getContext())
        }
    }

    private fun ensureMenu() {
        ensureMenuView()
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder) this.mMenuView.getMenu()
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = ExpandedActionViewMenuPresenter()
            }
            this.mMenuView.setExpandedActionViewsExclusive(true)
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext)
        }
    }

    private fun ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = ActionMenuView(getContext())
            this.mMenuView.setPopupTheme(this.mPopupTheme)
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener)
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback)
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams()
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388613 | (this.mButtonGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall)
            this.mMenuView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams)
            addSystemView(this.mMenuView, false)
        }
    }

    private fun ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle)
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams()
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (this.mButtonGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall)
            this.mNavButtonView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams)
        }
    }

    private fun getChildHorizontalGravity(Int i) {
        Int layoutDirection = ViewCompat.getLayoutDirection(this)
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i, layoutDirection) & 7
        switch (absoluteGravity) {
            case 1:
            case 3:
            case 5:
                return absoluteGravity
            case 2:
            case 4:
            default:
                return layoutDirection == 1 ? 5 : 3
        }
    }

    private fun getChildTop(View view, Int i) {
        Int iMax
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int measuredHeight = view.getMeasuredHeight()
        Int i2 = i > 0 ? (measuredHeight - i) / 2 : 0
        switch (getChildVerticalGravity(layoutParams.gravity)) {
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                return getPaddingTop() - i2
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                return (((getHeight() - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin) - i2
            default:
                Int paddingTop = getPaddingTop()
                Int paddingBottom = getPaddingBottom()
                Int height = getHeight()
                Int i3 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2
                if (i3 < layoutParams.topMargin) {
                    iMax = layoutParams.topMargin
                } else {
                    Int i4 = (((height - paddingBottom) - measuredHeight) - i3) - paddingTop
                    iMax = i4 < layoutParams.bottomMargin ? Math.max(0, i3 - (layoutParams.bottomMargin - i4)) : i3
                }
                return iMax + paddingTop
        }
    }

    private fun getChildVerticalGravity(Int i) {
        Int i2 = i & R.styleable.AppCompatTheme_ratingBarStyleSmall
        switch (i2) {
            case 16:
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
            case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                return i2
            default:
                return this.mGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall
        }
    }

    private fun getHorizontalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        return MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams) + MarginLayoutParamsCompat.getMarginStart(marginLayoutParams)
    }

    private fun getMenuInflater() {
        return SupportMenuInflater(getContext())
    }

    private fun getVerticalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        return marginLayoutParams.bottomMargin + marginLayoutParams.topMargin
    }

    private fun getViewListMeasuredWidth(List list, Array<Int> iArr) {
        Int i = iArr[0]
        Int i2 = iArr[1]
        Int size = list.size()
        Int i3 = 0
        Int measuredWidth = 0
        Int iMax = i
        Int iMax2 = i2
        while (i3 < size) {
            View view = (View) list.get(i3)
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
            Int i4 = layoutParams.leftMargin - iMax
            Int i5 = layoutParams.rightMargin - iMax2
            Int iMax3 = Math.max(0, i4)
            Int iMax4 = Math.max(0, i5)
            iMax = Math.max(0, -i4)
            iMax2 = Math.max(0, -i5)
            i3++
            measuredWidth += view.getMeasuredWidth() + iMax3 + iMax4
        }
        return measuredWidth
    }

    private fun isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view)
    }

    private fun isCustomView(View view) {
        return ((LayoutParams) view.getLayoutParams()).mViewType == 0
    }

    private fun layoutChildLeft(View view, Int i, Array<Int> iArr, Int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int i3 = layoutParams.leftMargin - iArr[0]
        Int iMax = Math.max(0, i3) + i
        iArr[0] = Math.max(0, -i3)
        Int childTop = getChildTop(view, i2)
        Int measuredWidth = view.getMeasuredWidth()
        view.layout(iMax, childTop, iMax + measuredWidth, view.getMeasuredHeight() + childTop)
        return layoutParams.rightMargin + measuredWidth + iMax
    }

    private fun layoutChildRight(View view, Int i, Array<Int> iArr, Int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int i3 = layoutParams.rightMargin - iArr[1]
        Int iMax = i - Math.max(0, i3)
        iArr[1] = Math.max(0, -i3)
        Int childTop = getChildTop(view, i2)
        Int measuredWidth = view.getMeasuredWidth()
        view.layout(iMax - measuredWidth, childTop, iMax, view.getMeasuredHeight() + childTop)
        return iMax - (layoutParams.leftMargin + measuredWidth)
    }

    private fun measureChildCollapseMargins(View view, Int i, Int i2, Int i3, Int i4, Array<Int> iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        Int i5 = marginLayoutParams.leftMargin - iArr[0]
        Int i6 = marginLayoutParams.rightMargin - iArr[1]
        Int iMax = Math.max(0, i5) + Math.max(0, i6)
        iArr[0] = Math.max(0, -i5)
        iArr[1] = Math.max(0, -i6)
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + iMax + i2, marginLayoutParams.width), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height))
        return view.getMeasuredWidth() + iMax
    }

    private fun measureChildConstrained(View view, Int i, Int i2, Int i3, Int i4, Int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        Int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width)
        Int childMeasureSpec2 = getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height)
        Int mode = View.MeasureSpec.getMode(childMeasureSpec2)
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5)
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824)
        }
        view.measure(childMeasureSpec, childMeasureSpec2)
    }

    private fun postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable)
        post(this.mShowOverflowMenuRunnable)
    }

    private fun shouldCollapse() {
        if (!this.mCollapsible) {
            return false
        }
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if (shouldLayout(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false
            }
        }
        return true
    }

    private fun shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true
    }

    Unit addChildrenForExpandedActionView() {
        for (Int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            addView((View) this.mHiddenViews.get(size))
        }
        this.mHiddenViews.clear()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved()
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams is LayoutParams)
    }

    fun collapseActionView() {
        MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView()
        }
    }

    fun dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus()
        }
    }

    Unit ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle)
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon)
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription)
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams()
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (this.mButtonGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall)
            layoutParamsGenerateDefaultLayoutParams.mViewType = 2
            this.mCollapseButtonView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams)
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() { // from class: android.support.v7.widget.Toolbar.3
                @Override // android.view.View.OnClickListener
                fun onClick(View view) {
                    Toolbar.this.collapseActionView()
                }
            })
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateDefaultLayoutParams() {
        return LayoutParams(-2, -2)
    }

    @Override // android.view.ViewGroup
    fun generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is LayoutParams ? LayoutParams((LayoutParams) layoutParams) : layoutParams is ActionBar.LayoutParams ? LayoutParams((ActionBar.LayoutParams) layoutParams) : layoutParams is ViewGroup.MarginLayoutParams ? LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : LayoutParams(layoutParams)
    }

    fun getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd()
        }
        return 0
    }

    fun getContentInsetEndWithActions() {
        return this.mContentInsetEndWithActions != Integer.MIN_VALUE ? this.mContentInsetEndWithActions : getContentInsetEnd()
    }

    fun getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft()
        }
        return 0
    }

    fun getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight()
        }
        return 0
    }

    fun getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart()
        }
        return 0
    }

    fun getContentInsetStartWithNavigation() {
        return this.mContentInsetStartWithNavigation != Integer.MIN_VALUE ? this.mContentInsetStartWithNavigation : getContentInsetStart()
    }

    fun getCurrentContentInsetEnd() {
        MenuBuilder menuBuilderPeekMenu
        Boolean z = (this.mMenuView == null || (menuBuilderPeekMenu = this.mMenuView.peekMenu()) == null || !menuBuilderPeekMenu.hasVisibleItems()) ? false : true
        return z ? Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0)) : getContentInsetEnd()
    }

    fun getCurrentContentInsetLeft() {
        return ViewCompat.getLayoutDirection(this) == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart()
    }

    fun getCurrentContentInsetRight() {
        return ViewCompat.getLayoutDirection(this) == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd()
    }

    fun getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0)) : getContentInsetStart()
    }

    fun getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable()
        }
        return null
    }

    fun getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription()
        }
        return null
    }

    fun getMenu() {
        ensureMenu()
        return this.mMenuView.getMenu()
    }

    @Nullable
    fun getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription()
        }
        return null
    }

    @Nullable
    fun getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable()
        }
        return null
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter
    }

    @Nullable
    fun getOverflowIcon() {
        ensureMenu()
        return this.mMenuView.getOverflowIcon()
    }

    Context getPopupContext() {
        return this.mPopupContext
    }

    fun getPopupTheme() {
        return this.mPopupTheme
    }

    fun getSubtitle() {
        return this.mSubtitleText
    }

    fun getTitle() {
        return this.mTitleText
    }

    fun getTitleMarginBottom() {
        return this.mTitleMarginBottom
    }

    fun getTitleMarginEnd() {
        return this.mTitleMarginEnd
    }

    fun getTitleMarginStart() {
        return this.mTitleMarginStart
    }

    fun getTitleMarginTop() {
        return this.mTitleMarginTop
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = ToolbarWidgetWrapper(this, true)
        }
        return this.mWrapper
    }

    fun hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true
    }

    fun hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu()
    }

    fun inflateMenu(@MenuRes Int i) {
        getMenuInflater().inflate(i, getMenu())
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending()
    }

    fun isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isTitleTruncated() {
        Layout layout
        if (this.mTitleTextView == null || (layout = this.mTitleTextView.getLayout()) == null) {
            return false
        }
        Int lineCount = layout.getLineCount()
        for (Int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true
            }
        }
        return false
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(this.mShowOverflowMenuRunnable)
    }

    @Override // android.view.View
    fun onHoverEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 9) {
            this.mEatingHover = false
        }
        if (!this.mEatingHover) {
            Boolean zOnHoverEvent = super.onHoverEvent(motionEvent)
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.mEatingHover = true
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false
        }
        return true
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) throws NoSuchFieldException {
        Int iLayoutChildRight
        Int iLayoutChildLeft
        Int iMin
        Int iMax
        Int measuredHeight
        Int paddingTop
        Int i5
        Int i6
        Int i7
        Int i8
        Int i9
        Int iMax2
        Boolean z2 = ViewCompat.getLayoutDirection(this) == 1
        Int width = getWidth()
        Int height = getHeight()
        Int paddingLeft = getPaddingLeft()
        Int paddingRight = getPaddingRight()
        Int paddingTop2 = getPaddingTop()
        Int paddingBottom = getPaddingBottom()
        Int i10 = width - paddingRight
        Array<Int> iArr = this.mTempMargins
        iArr[1] = 0
        iArr[0] = 0
        Int minimumHeight = ViewCompat.getMinimumHeight(this)
        Int iMin2 = minimumHeight >= 0 ? Math.min(minimumHeight, i4 - i2) : 0
        if (!shouldLayout(this.mNavButtonView)) {
            iLayoutChildRight = i10
            iLayoutChildLeft = paddingLeft
        } else if (z2) {
            iLayoutChildRight = layoutChildRight(this.mNavButtonView, i10, iArr, iMin2)
            iLayoutChildLeft = paddingLeft
        } else {
            iLayoutChildLeft = layoutChildLeft(this.mNavButtonView, paddingLeft, iArr, iMin2)
            iLayoutChildRight = i10
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            if (z2) {
                iLayoutChildRight = layoutChildRight(this.mCollapseButtonView, iLayoutChildRight, iArr, iMin2)
            } else {
                iLayoutChildLeft = layoutChildLeft(this.mCollapseButtonView, iLayoutChildLeft, iArr, iMin2)
            }
        }
        if (shouldLayout(this.mMenuView)) {
            if (z2) {
                iLayoutChildLeft = layoutChildLeft(this.mMenuView, iLayoutChildLeft, iArr, iMin2)
            } else {
                iLayoutChildRight = layoutChildRight(this.mMenuView, iLayoutChildRight, iArr, iMin2)
            }
        }
        Int currentContentInsetLeft = getCurrentContentInsetLeft()
        Int currentContentInsetRight = getCurrentContentInsetRight()
        iArr[0] = Math.max(0, currentContentInsetLeft - iLayoutChildLeft)
        iArr[1] = Math.max(0, currentContentInsetRight - ((width - paddingRight) - iLayoutChildRight))
        Int iMax3 = Math.max(iLayoutChildLeft, currentContentInsetLeft)
        Int iMin3 = Math.min(iLayoutChildRight, (width - paddingRight) - currentContentInsetRight)
        if (shouldLayout(this.mExpandedActionView)) {
            if (z2) {
                iMin3 = layoutChildRight(this.mExpandedActionView, iMin3, iArr, iMin2)
            } else {
                iMax3 = layoutChildLeft(this.mExpandedActionView, iMax3, iArr, iMin2)
            }
        }
        if (!shouldLayout(this.mLogoView)) {
            iMin = iMin3
            iMax = iMax3
        } else if (z2) {
            iMin = layoutChildRight(this.mLogoView, iMin3, iArr, iMin2)
            iMax = iMax3
        } else {
            iMin = iMin3
            iMax = layoutChildLeft(this.mLogoView, iMax3, iArr, iMin2)
        }
        Boolean zShouldLayout = shouldLayout(this.mTitleTextView)
        Boolean zShouldLayout2 = shouldLayout(this.mSubtitleTextView)
        Int measuredHeight2 = 0
        if (zShouldLayout) {
            LayoutParams layoutParams = (LayoutParams) this.mTitleTextView.getLayoutParams()
            measuredHeight2 = layoutParams.bottomMargin + layoutParams.topMargin + this.mTitleTextView.getMeasuredHeight() + 0
        }
        if (zShouldLayout2) {
            LayoutParams layoutParams2 = (LayoutParams) this.mSubtitleTextView.getLayoutParams()
            measuredHeight = layoutParams2.bottomMargin + layoutParams2.topMargin + this.mSubtitleTextView.getMeasuredHeight() + measuredHeight2
        } else {
            measuredHeight = measuredHeight2
        }
        if (zShouldLayout || zShouldLayout2) {
            TextView textView = zShouldLayout ? this.mTitleTextView : this.mSubtitleTextView
            TextView textView2 = zShouldLayout2 ? this.mSubtitleTextView : this.mTitleTextView
            LayoutParams layoutParams3 = (LayoutParams) textView.getLayoutParams()
            LayoutParams layoutParams4 = (LayoutParams) textView2.getLayoutParams()
            Boolean z3 = (zShouldLayout && this.mTitleTextView.getMeasuredWidth() > 0) || (zShouldLayout2 && this.mSubtitleTextView.getMeasuredWidth() > 0)
            switch (this.mGravity & R.styleable.AppCompatTheme_ratingBarStyleSmall) {
                case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                    paddingTop = layoutParams3.topMargin + getPaddingTop() + this.mTitleMarginTop
                    break
                case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                    paddingTop = (((height - paddingBottom) - layoutParams4.bottomMargin) - this.mTitleMarginBottom) - measuredHeight
                    break
                default:
                    Int i11 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2
                    if (i11 < layoutParams3.topMargin + this.mTitleMarginTop) {
                        iMax2 = layoutParams3.topMargin + this.mTitleMarginTop
                    } else {
                        Int i12 = (((height - paddingBottom) - measuredHeight) - i11) - paddingTop2
                        iMax2 = i12 < layoutParams3.bottomMargin + this.mTitleMarginBottom ? Math.max(0, i11 - ((layoutParams4.bottomMargin + this.mTitleMarginBottom) - i12)) : i11
                    }
                    paddingTop = paddingTop2 + iMax2
                    break
            }
            if (z2) {
                Int i13 = (z3 ? this.mTitleMarginStart : 0) - iArr[1]
                Int iMax4 = iMin - Math.max(0, i13)
                iArr[1] = Math.max(0, -i13)
                if (zShouldLayout) {
                    LayoutParams layoutParams5 = (LayoutParams) this.mTitleTextView.getLayoutParams()
                    Int measuredWidth = iMax4 - this.mTitleTextView.getMeasuredWidth()
                    Int measuredHeight3 = this.mTitleTextView.getMeasuredHeight() + paddingTop
                    this.mTitleTextView.layout(measuredWidth, paddingTop, iMax4, measuredHeight3)
                    Int i14 = measuredWidth - this.mTitleMarginEnd
                    paddingTop = measuredHeight3 + layoutParams5.bottomMargin
                    i8 = i14
                } else {
                    i8 = iMax4
                }
                if (zShouldLayout2) {
                    LayoutParams layoutParams6 = (LayoutParams) this.mSubtitleTextView.getLayoutParams()
                    Int i15 = layoutParams6.topMargin + paddingTop
                    this.mSubtitleTextView.layout(iMax4 - this.mSubtitleTextView.getMeasuredWidth(), i15, iMax4, this.mSubtitleTextView.getMeasuredHeight() + i15)
                    Int i16 = iMax4 - this.mTitleMarginEnd
                    Int i17 = layoutParams6.bottomMargin
                    i9 = i16
                } else {
                    i9 = iMax4
                }
                iMin = z3 ? Math.min(i8, i9) : iMax4
            } else {
                Int i18 = (z3 ? this.mTitleMarginStart : 0) - iArr[0]
                iMax += Math.max(0, i18)
                iArr[0] = Math.max(0, -i18)
                if (zShouldLayout) {
                    LayoutParams layoutParams7 = (LayoutParams) this.mTitleTextView.getLayoutParams()
                    Int measuredWidth2 = this.mTitleTextView.getMeasuredWidth() + iMax
                    Int measuredHeight4 = this.mTitleTextView.getMeasuredHeight() + paddingTop
                    this.mTitleTextView.layout(iMax, paddingTop, measuredWidth2, measuredHeight4)
                    Int i19 = measuredWidth2 + this.mTitleMarginEnd
                    Int i20 = layoutParams7.bottomMargin + measuredHeight4
                    i5 = i19
                    i6 = i20
                } else {
                    i5 = iMax
                    i6 = paddingTop
                }
                if (zShouldLayout2) {
                    LayoutParams layoutParams8 = (LayoutParams) this.mSubtitleTextView.getLayoutParams()
                    Int i21 = i6 + layoutParams8.topMargin
                    Int measuredWidth3 = this.mSubtitleTextView.getMeasuredWidth() + iMax
                    this.mSubtitleTextView.layout(iMax, i21, measuredWidth3, this.mSubtitleTextView.getMeasuredHeight() + i21)
                    Int i22 = this.mTitleMarginEnd + measuredWidth3
                    Int i23 = layoutParams8.bottomMargin
                    i7 = i22
                } else {
                    i7 = iMax
                }
                if (z3) {
                    iMax = Math.max(i5, i7)
                }
            }
        }
        addCustomViewsWithGravity(this.mTempViews, 3)
        Int size = this.mTempViews.size()
        Int iLayoutChildLeft2 = iMax
        for (Int i24 = 0; i24 < size; i24++) {
            iLayoutChildLeft2 = layoutChildLeft((View) this.mTempViews.get(i24), iLayoutChildLeft2, iArr, iMin2)
        }
        addCustomViewsWithGravity(this.mTempViews, 5)
        Int size2 = this.mTempViews.size()
        for (Int i25 = 0; i25 < size2; i25++) {
            iMin = layoutChildRight((View) this.mTempViews.get(i25), iMin, iArr, iMin2)
        }
        addCustomViewsWithGravity(this.mTempViews, 1)
        Int viewListMeasuredWidth = getViewListMeasuredWidth(this.mTempViews, iArr)
        Int i26 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (viewListMeasuredWidth / 2)
        Int i27 = viewListMeasuredWidth + i26
        if (i26 < iLayoutChildLeft2) {
            i26 = iLayoutChildLeft2
        } else if (i27 > iMin) {
            i26 -= i27 - iMin
        }
        Int size3 = this.mTempViews.size()
        Int i28 = 0
        Int i29 = i26
        while (i28 < size3) {
            Int iLayoutChildLeft3 = layoutChildLeft((View) this.mTempViews.get(i28), i29, iArr, iMin2)
            i28++
            i29 = iLayoutChildLeft3
        }
        this.mTempViews.clear()
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Char c
        Char c2
        Int iCombineMeasuredStates
        Int iMax
        Int iCombineMeasuredStates2
        Int i3
        Array<Int> iArr = this.mTempMargins
        if (ViewUtils.isLayoutRtl(this)) {
            c = 0
            c2 = 1
        } else {
            c = 1
            c2 = 0
        }
        Int measuredWidth = 0
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, i, 0, i2, 0, this.mMaxButtonHeight)
            measuredWidth = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView)
            Int iMax2 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView))
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState())
            iMax = iMax2
        } else {
            iCombineMeasuredStates = 0
            iMax = 0
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, i, 0, i2, 0, this.mMaxButtonHeight)
            measuredWidth = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView)
            iMax = Math.max(iMax, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView))
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mCollapseButtonView.getMeasuredState())
        }
        Int currentContentInsetStart = getCurrentContentInsetStart()
        Int iMax3 = Math.max(currentContentInsetStart, measuredWidth) + 0
        iArr[c2] = Math.max(0, currentContentInsetStart - measuredWidth)
        Int measuredWidth2 = 0
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, i, iMax3, i2, 0, this.mMaxButtonHeight)
            measuredWidth2 = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView)
            iMax = Math.max(iMax, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView))
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mMenuView.getMeasuredState())
        }
        Int currentContentInsetEnd = getCurrentContentInsetEnd()
        Int iMax4 = iMax3 + Math.max(currentContentInsetEnd, measuredWidth2)
        iArr[c] = Math.max(0, currentContentInsetEnd - measuredWidth2)
        if (shouldLayout(this.mExpandedActionView)) {
            iMax4 += measureChildCollapseMargins(this.mExpandedActionView, i, iMax4, i2, 0, iArr)
            iMax = Math.max(iMax, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView))
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mExpandedActionView.getMeasuredState())
        }
        if (shouldLayout(this.mLogoView)) {
            iMax4 += measureChildCollapseMargins(this.mLogoView, i, iMax4, i2, 0, iArr)
            iMax = Math.max(iMax, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView))
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mLogoView.getMeasuredState())
        }
        Int childCount = getChildCount()
        Int i4 = 0
        Int i5 = iMax
        Int iCombineMeasuredStates3 = iCombineMeasuredStates
        while (i4 < childCount) {
            View childAt = getChildAt(i4)
            if (((LayoutParams) childAt.getLayoutParams()).mViewType == 0 && shouldLayout(childAt)) {
                iMax4 += measureChildCollapseMargins(childAt, i, iMax4, i2, 0, iArr)
                Int iMax5 = Math.max(i5, childAt.getMeasuredHeight() + getVerticalMargins(childAt))
                iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt.getMeasuredState())
                i3 = iMax5
            } else {
                iCombineMeasuredStates2 = iCombineMeasuredStates3
                i3 = i5
            }
            i4++
            iCombineMeasuredStates3 = iCombineMeasuredStates2
            i5 = i3
        }
        Int iMax6 = 0
        Int measuredHeight = 0
        Int i6 = this.mTitleMarginTop + this.mTitleMarginBottom
        Int i7 = this.mTitleMarginStart + this.mTitleMarginEnd
        if (shouldLayout(this.mTitleTextView)) {
            measureChildCollapseMargins(this.mTitleTextView, i, iMax4 + i7, i2, i6, iArr)
            iMax6 = getHorizontalMargins(this.mTitleTextView) + this.mTitleTextView.getMeasuredWidth()
            measuredHeight = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView)
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, this.mTitleTextView.getMeasuredState())
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            iMax6 = Math.max(iMax6, measureChildCollapseMargins(this.mSubtitleTextView, i, iMax4 + i7, i2, i6 + measuredHeight, iArr))
            measuredHeight += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView)
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, this.mSubtitleTextView.getMeasuredState())
        }
        Int iMax7 = Math.max(i5, measuredHeight)
        Int paddingLeft = iMax6 + iMax4 + getPaddingLeft() + getPaddingRight()
        Int paddingTop = iMax7 + getPaddingTop() + getPaddingBottom()
        Int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates3)
        Int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates3 << 16)
        if (shouldCollapse()) {
            iResolveSizeAndState2 = 0
        }
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2)
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        MenuBuilder menuBuilderPeekMenu = this.mMenuView != null ? this.mMenuView.peekMenu() : null
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && menuBuilderPeekMenu != null && (menuItemFindItem = menuBuilderPeekMenu.findItem(savedState.expandedMenuItemId)) != null) {
            menuItemFindItem.expandActionView()
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu()
        }
    }

    @Override // android.view.View
    fun onRtlPropertiesChanged(Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i)
        }
        ensureContentInsets()
        this.mContentInsets.setDirection(i == 1)
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId()
        }
        savedState.isOverflowOpen = isOverflowMenuShowing()
        return savedState
    }

    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 0) {
            this.mEatingTouch = false
        }
        if (!this.mEatingTouch) {
            Boolean zOnTouchEvent = super.onTouchEvent(motionEvent)
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.mEatingTouch = true
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false
        }
        return true
    }

    Unit removeChildrenForExpandedActionView() {
        for (Int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount)
            if (((LayoutParams) childAt.getLayoutParams()).mViewType != 2 && childAt != this.mMenuView) {
                removeViewAt(childCount)
                this.mHiddenViews.add(childAt)
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setCollapsible(Boolean z) {
        this.mCollapsible = z
        requestLayout()
    }

    fun setContentInsetEndWithActions(Int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE
        }
        if (i != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = i
            if (getNavigationIcon() != null) {
                requestLayout()
            }
        }
    }

    fun setContentInsetStartWithNavigation(Int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE
        }
        if (i != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = i
            if (getNavigationIcon() != null) {
                requestLayout()
            }
        }
    }

    fun setContentInsetsAbsolute(Int i, Int i2) {
        ensureContentInsets()
        this.mContentInsets.setAbsolute(i, i2)
    }

    fun setContentInsetsRelative(Int i, Int i2) {
        ensureContentInsets()
        this.mContentInsets.setRelative(i, i2)
    }

    fun setLogo(@DrawableRes Int i) {
        setLogo(AppCompatResources.getDrawable(getContext(), i))
    }

    fun setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView()
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true)
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView)
            this.mHiddenViews.remove(this.mLogoView)
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable)
        }
    }

    fun setLogoDescription(@StringRes Int i) {
        setLogoDescription(getContext().getText(i))
    }

    fun setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureLogoView()
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(charSequence)
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return
        }
        ensureMenuView()
        MenuBuilder menuBuilderPeekMenu = this.mMenuView.peekMenu()
        if (menuBuilderPeekMenu != menuBuilder) {
            if (menuBuilderPeekMenu != null) {
                menuBuilderPeekMenu.removeMenuPresenter(this.mOuterActionMenuPresenter)
                menuBuilderPeekMenu.removeMenuPresenter(this.mExpandedMenuPresenter)
            }
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = ExpandedActionViewMenuPresenter()
            }
            actionMenuPresenter.setExpandedActionViewsExclusive(true)
            if (menuBuilder != null) {
                menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext)
                menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext)
            } else {
                actionMenuPresenter.initForMenu(this.mPopupContext, null)
                this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null)
                actionMenuPresenter.updateMenuView(true)
                this.mExpandedMenuPresenter.updateMenuView(true)
            }
            this.mMenuView.setPopupTheme(this.mPopupTheme)
            this.mMenuView.setPresenter(actionMenuPresenter)
            this.mOuterActionMenuPresenter = actionMenuPresenter
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback
        this.mMenuBuilderCallback = callback2
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(callback, callback2)
        }
    }

    fun setNavigationContentDescription(@StringRes Int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null)
    }

    fun setNavigationContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureNavButtonView()
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(charSequence)
        }
    }

    fun setNavigationIcon(@DrawableRes Int i) {
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), i))
    }

    fun setNavigationIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            ensureNavButtonView()
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true)
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView)
            this.mHiddenViews.remove(this.mNavButtonView)
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(drawable)
        }
    }

    fun setNavigationOnClickListener(View.OnClickListener onClickListener) {
        ensureNavButtonView()
        this.mNavButtonView.setOnClickListener(onClickListener)
    }

    fun setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener
    }

    fun setOverflowIcon(@Nullable Drawable drawable) {
        ensureMenu()
        this.mMenuView.setOverflowIcon(drawable)
    }

    fun setPopupTheme(@StyleRes Int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i
            if (i == 0) {
                this.mPopupContext = getContext()
            } else {
                this.mPopupContext = ContextThemeWrapper(getContext(), i)
            }
        }
    }

    fun setSubtitle(@StringRes Int i) {
        setSubtitle(getContext().getText(i))
    }

    fun setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = getContext()
                this.mSubtitleTextView = AppCompatTextView(context)
                this.mSubtitleTextView.setSingleLine()
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END)
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance)
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor)
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true)
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView)
            this.mHiddenViews.remove(this.mSubtitleTextView)
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence)
        }
        this.mSubtitleText = charSequence
    }

    fun setSubtitleTextAppearance(Context context, @StyleRes Int i) {
        this.mSubtitleTextAppearance = i
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, i)
        }
    }

    fun setSubtitleTextColor(@ColorInt Int i) {
        this.mSubtitleTextColor = i
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(i)
        }
    }

    fun setTitle(@StringRes Int i) {
        setTitle(getContext().getText(i))
    }

    fun setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mTitleTextView == null) {
                Context context = getContext()
                this.mTitleTextView = AppCompatTextView(context)
                this.mTitleTextView.setSingleLine()
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END)
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance)
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor)
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true)
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView)
            this.mHiddenViews.remove(this.mTitleTextView)
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence)
        }
        this.mTitleText = charSequence
    }

    fun setTitleMargin(Int i, Int i2, Int i3, Int i4) {
        this.mTitleMarginStart = i
        this.mTitleMarginTop = i2
        this.mTitleMarginEnd = i3
        this.mTitleMarginBottom = i4
        requestLayout()
    }

    fun setTitleMarginBottom(Int i) {
        this.mTitleMarginBottom = i
        requestLayout()
    }

    fun setTitleMarginEnd(Int i) {
        this.mTitleMarginEnd = i
        requestLayout()
    }

    fun setTitleMarginStart(Int i) {
        this.mTitleMarginStart = i
        requestLayout()
    }

    fun setTitleMarginTop(Int i) {
        this.mTitleMarginTop = i
        requestLayout()
    }

    fun setTitleTextAppearance(Context context, @StyleRes Int i) {
        this.mTitleTextAppearance = i
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, i)
        }
    }

    fun setTitleTextColor(@ColorInt Int i) {
        this.mTitleTextColor = i
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(i)
        }
    }

    fun showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu()
    }
}
