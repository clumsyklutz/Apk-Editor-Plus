package android.support.v7.view.menu

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.TintTypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    private static val TAG = "ListMenuItemView"
    private Drawable mBackground
    private CheckBox mCheckBox
    private LinearLayout mContent
    private Boolean mForceShowIcon
    private ImageView mGroupDivider
    private Boolean mHasListDivider
    private ImageView mIconView
    private LayoutInflater mInflater
    private MenuItemImpl mItemData
    private Int mMenuType
    private Boolean mPreserveIconSpacing
    private RadioButton mRadioButton
    private TextView mShortcutView
    private Drawable mSubMenuArrow
    private ImageView mSubMenuArrowView
    private Int mTextAppearance
    private Context mTextAppearanceContext
    private TextView mTitleView

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listMenuViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet)
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.MenuView, i, 0)
        this.mBackground = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.MenuView_android_itemBackground)
        this.mTextAppearance = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuView_android_itemTextAppearance, -1)
        this.mPreserveIconSpacing = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuView_preserveIconSpacing, false)
        this.mTextAppearanceContext = context
        this.mSubMenuArrow = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.MenuView_subMenuArrow)
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new Array<Int>{android.R.attr.divider}, R.attr.dropDownListViewStyle, 0)
        this.mHasListDivider = typedArrayObtainStyledAttributes.hasValue(0)
        tintTypedArrayObtainStyledAttributes.recycle()
        typedArrayObtainStyledAttributes.recycle()
    }

    private fun addContentView(View view) {
        addContentView(view, -1)
    }

    private fun addContentView(View view, Int i) {
        if (this.mContent != null) {
            this.mContent.addView(view, i)
        } else {
            addView(view, i)
        }
    }

    private fun getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext())
        }
        return this.mInflater
    }

    private fun insertCheckBox() {
        this.mCheckBox = (CheckBox) getInflater().inflate(R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false)
        addContentView(this.mCheckBox)
    }

    private fun insertIconView() {
        this.mIconView = (ImageView) getInflater().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false)
        addContentView(this.mIconView, 0)
    }

    private fun insertRadioButton() {
        this.mRadioButton = (RadioButton) getInflater().inflate(R.layout.abc_list_menu_item_radio, (ViewGroup) this, false)
        addContentView(this.mRadioButton)
    }

    private fun setSubMenuArrowVisible(Boolean z) {
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setVisibility(z ? 0 : 8)
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    fun adjustListItemSelectionBounds(Rect rect) {
        if (this.mGroupDivider == null || this.mGroupDivider.getVisibility() != 0) {
            return
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams()
        rect.top = layoutParams.bottomMargin + this.mGroupDivider.getHeight() + layoutParams.topMargin + rect.top
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun getItemData() {
        return this.mItemData
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun initialize(MenuItemImpl menuItemImpl, Int i) {
        this.mItemData = menuItemImpl
        this.mMenuType = i
        setVisibility(menuItemImpl.isVisible() ? 0 : 8)
        setTitle(menuItemImpl.getTitleForItemView(this))
        setCheckable(menuItemImpl.isCheckable())
        setShortcut(menuItemImpl.shouldShowShortcut(), menuItemImpl.getShortcut())
        setIcon(menuItemImpl.getIcon())
        setEnabled(menuItemImpl.isEnabled())
        setSubMenuArrowVisible(menuItemImpl.hasSubMenu())
        setContentDescription(menuItemImpl.getContentDescription())
    }

    @Override // android.view.View
    protected fun onFinishInflate() {
        super.onFinishInflate()
        ViewCompat.setBackground(this, this.mBackground)
        this.mTitleView = (TextView) findViewById(R.id.title)
        if (this.mTextAppearance != -1) {
            this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance)
        }
        this.mShortcutView = (TextView) findViewById(R.id.shortcut)
        this.mSubMenuArrowView = (ImageView) findViewById(R.id.submenuarrow)
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setImageDrawable(this.mSubMenuArrow)
        }
        this.mGroupDivider = (ImageView) findViewById(R.id.group_divider)
        this.mContent = (LinearLayout) findViewById(R.id.content)
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams()
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams()
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height
            }
        }
        super.onMeasure(i, i2)
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun prefersCondensedTitle() {
        return false
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setCheckable(Boolean z) {
        CompoundButton compoundButton
        CompoundButton compoundButton2
        if (!z && this.mRadioButton == null && this.mCheckBox == null) {
            return
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton()
            }
            compoundButton = this.mRadioButton
            compoundButton2 = this.mCheckBox
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox()
            }
            compoundButton = this.mCheckBox
            compoundButton2 = this.mRadioButton
        }
        if (!z) {
            if (this.mCheckBox != null) {
                this.mCheckBox.setVisibility(8)
            }
            if (this.mRadioButton != null) {
                this.mRadioButton.setVisibility(8)
                return
            }
            return
        }
        compoundButton.setChecked(this.mItemData.isChecked())
        if (compoundButton.getVisibility() != 0) {
            compoundButton.setVisibility(0)
        }
        if (compoundButton2 == null || compoundButton2.getVisibility() == 8) {
            return
        }
        compoundButton2.setVisibility(8)
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setChecked(Boolean z) {
        CompoundButton compoundButton
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton()
            }
            compoundButton = this.mRadioButton
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox()
            }
            compoundButton = this.mCheckBox
        }
        compoundButton.setChecked(z)
    }

    fun setForceShowIcon(Boolean z) {
        this.mForceShowIcon = z
        this.mPreserveIconSpacing = z
    }

    fun setGroupDividerEnabled(Boolean z) {
        if (this.mGroupDivider != null) {
            this.mGroupDivider.setVisibility((this.mHasListDivider || !z) ? 8 : 0)
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setIcon(Drawable drawable) {
        Boolean z = this.mItemData.shouldShowIcon() || this.mForceShowIcon
        if (z || this.mPreserveIconSpacing) {
            if (this.mIconView == null && drawable == null && !this.mPreserveIconSpacing) {
                return
            }
            if (this.mIconView == null) {
                insertIconView()
            }
            if (drawable == null && !this.mPreserveIconSpacing) {
                this.mIconView.setVisibility(8)
                return
            }
            ImageView imageView = this.mIconView
            if (!z) {
                drawable = null
            }
            imageView.setImageDrawable(drawable)
            if (this.mIconView.getVisibility() != 0) {
                this.mIconView.setVisibility(0)
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setShortcut(Boolean z, Char c) {
        Int i = (z && this.mItemData.shouldShowShortcut()) ? 0 : 8
        if (i == 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel())
        }
        if (this.mShortcutView.getVisibility() != i) {
            this.mShortcutView.setVisibility(i)
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun setTitle(CharSequence charSequence) {
        if (charSequence == null) {
            if (this.mTitleView.getVisibility() != 8) {
                this.mTitleView.setVisibility(8)
            }
        } else {
            this.mTitleView.setText(charSequence)
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0)
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuView.ItemView
    fun showsIcon() {
        return this.mForceShowIcon
    }
}
