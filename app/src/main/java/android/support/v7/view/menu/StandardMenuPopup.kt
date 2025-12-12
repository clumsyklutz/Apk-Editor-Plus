package android.support.v7.view.menu

import android.content.Context
import android.content.res.Resources
import android.os.Parcelable
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.widget.MenuPopupWindow
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView

final class StandardMenuPopup extends MenuPopup implements MenuPresenter, View.OnKeyListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
    private static val ITEM_LAYOUT = R.layout.abc_popup_menu_item_layout
    private final MenuAdapter mAdapter
    private View mAnchorView
    private Int mContentWidth
    private final Context mContext
    private Boolean mHasContentWidth
    private final MenuBuilder mMenu
    private PopupWindow.OnDismissListener mOnDismissListener
    private final Boolean mOverflowOnly
    final MenuPopupWindow mPopup
    private final Int mPopupMaxWidth
    private final Int mPopupStyleAttr
    private final Int mPopupStyleRes
    private MenuPresenter.Callback mPresenterCallback
    private Boolean mShowTitle
    View mShownAnchorView
    ViewTreeObserver mTreeObserver
    private Boolean mWasDismissed
    final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.support.v7.view.menu.StandardMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        fun onGlobalLayout() {
            if (!StandardMenuPopup.this.isShowing() || StandardMenuPopup.this.mPopup.isModal()) {
                return
            }
            View view = StandardMenuPopup.this.mShownAnchorView
            if (view == null || !view.isShown()) {
                StandardMenuPopup.this.dismiss()
            } else {
                StandardMenuPopup.this.mPopup.show()
            }
        }
    }
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: android.support.v7.view.menu.StandardMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        fun onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        fun onViewDetachedFromWindow(View view) {
            if (StandardMenuPopup.this.mTreeObserver != null) {
                if (!StandardMenuPopup.this.mTreeObserver.isAlive()) {
                    StandardMenuPopup.this.mTreeObserver = view.getViewTreeObserver()
                }
                StandardMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(StandardMenuPopup.this.mGlobalLayoutListener)
            }
            view.removeOnAttachStateChangeListener(this)
        }
    }
    private Int mDropDownGravity = 0

    constructor(Context context, MenuBuilder menuBuilder, View view, Int i, Int i2, Boolean z) {
        this.mContext = context
        this.mMenu = menuBuilder
        this.mOverflowOnly = z
        this.mAdapter = MenuAdapter(menuBuilder, LayoutInflater.from(context), this.mOverflowOnly, ITEM_LAYOUT)
        this.mPopupStyleAttr = i
        this.mPopupStyleRes = i2
        Resources resources = context.getResources()
        this.mPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth))
        this.mAnchorView = view
        this.mPopup = MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes)
        menuBuilder.addMenuPresenter(this, context)
    }

    private fun tryShow() {
        if (isShowing()) {
            return true
        }
        if (this.mWasDismissed || this.mAnchorView == null) {
            return false
        }
        this.mShownAnchorView = this.mAnchorView
        this.mPopup.setOnDismissListener(this)
        this.mPopup.setOnItemClickListener(this)
        this.mPopup.setModal(true)
        View view = this.mShownAnchorView
        Boolean z = this.mTreeObserver == null
        this.mTreeObserver = view.getViewTreeObserver()
        if (z) {
            this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener)
        }
        view.addOnAttachStateChangeListener(this.mAttachStateChangeListener)
        this.mPopup.setAnchorView(view)
        this.mPopup.setDropDownGravity(this.mDropDownGravity)
        if (!this.mHasContentWidth) {
            this.mContentWidth = measureIndividualMenuWidth(this.mAdapter, null, this.mContext, this.mPopupMaxWidth)
            this.mHasContentWidth = true
        }
        this.mPopup.setContentWidth(this.mContentWidth)
        this.mPopup.setInputMethodMode(2)
        this.mPopup.setEpicenterBounds(getEpicenterBounds())
        this.mPopup.show()
        ListView listView = this.mPopup.getListView()
        listView.setOnKeyListener(this)
        if (this.mShowTitle && this.mMenu.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false)
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title)
            if (textView != null) {
                textView.setText(this.mMenu.getHeaderTitle())
            }
            frameLayout.setEnabled(false)
            listView.addHeaderView(frameLayout, null, false)
        }
        this.mPopup.setAdapter(this.mAdapter)
        this.mPopup.show()
        return true
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit addMenu(MenuBuilder menuBuilder) {
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Unit dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss()
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Boolean flagActionItems() {
        return false
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final ListView getListView() {
        return this.mPopup.getListView()
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Boolean isShowing() {
        return !this.mWasDismissed && this.mPopup.isShowing()
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        if (menuBuilder != this.mMenu) {
            return
        }
        dismiss()
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onCloseMenu(menuBuilder, z)
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final Unit onDismiss() {
        this.mWasDismissed = true
        this.mMenu.close()
        if (this.mTreeObserver != null) {
            if (!this.mTreeObserver.isAlive()) {
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver()
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener)
            this.mTreeObserver = null
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener)
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss()
        }
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false
        }
        dismiss()
        return true
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        return null
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = MenuPopupHelper(this.mContext, subMenuBuilder, this.mShownAnchorView, this.mOverflowOnly, this.mPopupStyleAttr, this.mPopupStyleRes)
            menuPopupHelper.setPresenterCallback(this.mPresenterCallback)
            menuPopupHelper.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(subMenuBuilder))
            menuPopupHelper.setOnDismissListener(this.mOnDismissListener)
            this.mOnDismissListener = null
            this.mMenu.close(false)
            Int horizontalOffset = this.mPopup.getHorizontalOffset()
            Int verticalOffset = this.mPopup.getVerticalOffset()
            if ((Gravity.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7) == 5) {
                horizontalOffset += this.mAnchorView.getWidth()
            }
            if (menuPopupHelper.tryShow(horizontalOffset, verticalOffset)) {
                if (this.mPresenterCallback != null) {
                    this.mPresenterCallback.onOpenSubMenu(subMenuBuilder)
                }
                return true
            }
        }
        return false
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setAnchorView(View view) {
        this.mAnchorView = view
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setForceShowIcon(Boolean z) {
        this.mAdapter.setForceShowIcon(z)
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setGravity(Int i) {
        this.mDropDownGravity = i
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setHorizontalOffset(Int i) {
        this.mPopup.setHorizontalOffset(i)
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setShowTitle(Boolean z) {
        this.mShowTitle = z
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setVerticalOffset(Int i) {
        this.mPopup.setVerticalOffset(i)
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Unit show() {
        if (!tryShow()) {
            throw IllegalStateException("StandardMenuPopup cannot be used without an anchor")
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit updateMenuView(Boolean z) {
        this.mHasContentWidth = false
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged()
        }
    }
}
