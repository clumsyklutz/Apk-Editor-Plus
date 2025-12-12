package android.support.v7.view.menu

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.AttrRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StyleRes
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.widget.MenuItemHoverListener
import android.support.v7.widget.MenuPopupWindow
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.HeaderViewListAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Iterator
import java.util.List

final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener {
    static val HORIZ_POSITION_LEFT = 0
    static val HORIZ_POSITION_RIGHT = 1
    private static val ITEM_LAYOUT = R.layout.abc_cascading_menu_item_layout
    static val SUBMENU_TIMEOUT_MS = 200
    private View mAnchorView
    private final Context mContext
    private Boolean mHasXOffset
    private Boolean mHasYOffset
    private final Int mMenuMaxWidth
    private PopupWindow.OnDismissListener mOnDismissListener
    private final Boolean mOverflowOnly
    private final Int mPopupStyleAttr
    private final Int mPopupStyleRes
    private MenuPresenter.Callback mPresenterCallback
    Boolean mShouldCloseImmediately
    private Boolean mShowTitle
    View mShownAnchorView
    final Handler mSubMenuHoverHandler
    ViewTreeObserver mTreeObserver
    private Int mXOffset
    private Int mYOffset
    private val mPendingMenus = ArrayList()
    val mShowingMenus = ArrayList()
    final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.support.v7.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        fun onGlobalLayout() {
            if (!CascadingMenuPopup.this.isShowing() || CascadingMenuPopup.this.mShowingMenus.size() <= 0 || ((CascadingMenuInfo) CascadingMenuPopup.this.mShowingMenus.get(0)).window.isModal()) {
                return
            }
            View view = CascadingMenuPopup.this.mShownAnchorView
            if (view == null || !view.isShown()) {
                CascadingMenuPopup.this.dismiss()
                return
            }
            Iterator it = CascadingMenuPopup.this.mShowingMenus.iterator()
            while (it.hasNext()) {
                ((CascadingMenuInfo) it.next()).window.show()
            }
        }
    }
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: android.support.v7.view.menu.CascadingMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        fun onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        fun onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.mTreeObserver != null) {
                if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver()
                }
                CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener)
            }
            view.removeOnAttachStateChangeListener(this)
        }
    }
    private val mMenuItemHoverListener = MenuItemHoverListener() { // from class: android.support.v7.view.menu.CascadingMenuPopup.3
        @Override // android.support.v7.widget.MenuItemHoverListener
        fun onItemHoverEnter(@NonNull final MenuBuilder menuBuilder, @NonNull final MenuItem menuItem) {
            Int i
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(null)
            Int i2 = 0
            Int size = CascadingMenuPopup.this.mShowingMenus.size()
            while (true) {
                if (i2 >= size) {
                    i = -1
                    break
                } else {
                    if (menuBuilder == ((CascadingMenuInfo) CascadingMenuPopup.this.mShowingMenus.get(i2)).menu) {
                        i = i2
                        break
                    }
                    i2++
                }
            }
            if (i == -1) {
                return
            }
            Int i3 = i + 1
            val cascadingMenuInfo = i3 < CascadingMenuPopup.this.mShowingMenus.size() ? (CascadingMenuInfo) CascadingMenuPopup.this.mShowingMenus.get(i3) : null
            CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime(Runnable() { // from class: android.support.v7.view.menu.CascadingMenuPopup.3.1
                @Override // java.lang.Runnable
                fun run() {
                    if (cascadingMenuInfo != null) {
                        CascadingMenuPopup.this.mShouldCloseImmediately = true
                        cascadingMenuInfo.menu.close(false)
                        CascadingMenuPopup.this.mShouldCloseImmediately = false
                    }
                    if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                        menuBuilder.performItemAction(menuItem, 4)
                    }
                }
            }, menuBuilder, SystemClock.uptimeMillis() + 200)
        }

        @Override // android.support.v7.widget.MenuItemHoverListener
        fun onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder)
        }
    }
    private Int mRawDropDownGravity = 0
    private Int mDropDownGravity = 0
    private Boolean mForceShowIcon = false
    private Int mLastPosition = getInitialMenuPosition()

    class CascadingMenuInfo {
        public final MenuBuilder menu
        public final Int position
        public final MenuPopupWindow window

        constructor(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, Int i) {
            this.window = menuPopupWindow
            this.menu = menuBuilder
            this.position = i
        }

        fun getListView() {
            return this.window.getListView()
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HorizPosition {
    }

    constructor(@NonNull Context context, @NonNull View view, @AttrRes Int i, @StyleRes Int i2, Boolean z) {
        this.mContext = context
        this.mAnchorView = view
        this.mPopupStyleAttr = i
        this.mPopupStyleRes = i2
        this.mOverflowOnly = z
        Resources resources = context.getResources()
        this.mMenuMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth))
        this.mSubMenuHoverHandler = Handler()
    }

    private fun createPopupWindow() {
        MenuPopupWindow menuPopupWindow = MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes)
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener)
        menuPopupWindow.setOnItemClickListener(this)
        menuPopupWindow.setOnDismissListener(this)
        menuPopupWindow.setAnchorView(this.mAnchorView)
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity)
        menuPopupWindow.setModal(true)
        menuPopupWindow.setInputMethodMode(2)
        return menuPopupWindow
    }

    private fun findIndexOfAddedMenu(@NonNull MenuBuilder menuBuilder) {
        Int size = this.mShowingMenus.size()
        for (Int i = 0; i < size; i++) {
            if (menuBuilder == ((CascadingMenuInfo) this.mShowingMenus.get(i)).menu) {
                return i
            }
        }
        return -1
    }

    private fun findMenuItemForSubmenu(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        Int size = menuBuilder.size()
        for (Int i = 0; i < size; i++) {
            MenuItem item = menuBuilder.getItem(i)
            if (item.hasSubMenu() && menuBuilder2 == item.getSubMenu()) {
                return item
            }
        }
        return null
    }

    @Nullable
    private fun findParentViewForSubmenu(@NonNull CascadingMenuInfo cascadingMenuInfo, @NonNull MenuBuilder menuBuilder) {
        MenuAdapter menuAdapter
        Int headersCount
        Int i
        Int i2 = 0
        MenuItem menuItemFindMenuItemForSubmenu = findMenuItemForSubmenu(cascadingMenuInfo.menu, menuBuilder)
        if (menuItemFindMenuItemForSubmenu == null) {
            return null
        }
        ListView listView = cascadingMenuInfo.getListView()
        ListAdapter adapter = listView.getAdapter()
        if (adapter is HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter
            headersCount = headerViewListAdapter.getHeadersCount()
            menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter()
        } else {
            menuAdapter = (MenuAdapter) adapter
            headersCount = 0
        }
        Int count = menuAdapter.getCount()
        while (true) {
            if (i2 >= count) {
                i = -1
                break
            }
            if (menuItemFindMenuItemForSubmenu == menuAdapter.getItem(i2)) {
                i = i2
                break
            }
            i2++
        }
        if (i == -1) {
            return null
        }
        Int firstVisiblePosition = (i + headersCount) - listView.getFirstVisiblePosition()
        if (firstVisiblePosition < 0 || firstVisiblePosition >= listView.getChildCount()) {
            return null
        }
        return listView.getChildAt(firstVisiblePosition)
    }

    private fun getInitialMenuPosition() {
        return ViewCompat.getLayoutDirection(this.mAnchorView) == 1 ? 0 : 1
    }

    private fun getNextMenuPosition(Int i) {
        ListView listView = ((CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1)).getListView()
        Array<Int> iArr = new Int[2]
        listView.getLocationOnScreen(iArr)
        Rect rect = Rect()
        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect)
        if (this.mLastPosition == 1) {
            return (listView.getWidth() + iArr[0]) + i > rect.right ? 0 : 1
        }
        return iArr[0] - i < 0 ? 1 : 0
    }

    private fun showMenu(@NonNull MenuBuilder menuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        View viewFindParentViewForSubmenu
        CascadingMenuInfo cascadingMenuInfo
        Int i
        Int i2
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext)
        MenuAdapter menuAdapter = MenuAdapter(menuBuilder, layoutInflaterFrom, this.mOverflowOnly, ITEM_LAYOUT)
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter.setForceShowIcon(true)
        } else if (isShowing()) {
            menuAdapter.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(menuBuilder))
        }
        Int iMeasureIndividualMenuWidth = measureIndividualMenuWidth(menuAdapter, null, this.mContext, this.mMenuMaxWidth)
        MenuPopupWindow menuPopupWindowCreatePopupWindow = createPopupWindow()
        menuPopupWindowCreatePopupWindow.setAdapter(menuAdapter)
        menuPopupWindowCreatePopupWindow.setContentWidth(iMeasureIndividualMenuWidth)
        menuPopupWindowCreatePopupWindow.setDropDownGravity(this.mDropDownGravity)
        if (this.mShowingMenus.size() > 0) {
            CascadingMenuInfo cascadingMenuInfo2 = (CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1)
            viewFindParentViewForSubmenu = findParentViewForSubmenu(cascadingMenuInfo2, menuBuilder)
            cascadingMenuInfo = cascadingMenuInfo2
        } else {
            viewFindParentViewForSubmenu = null
            cascadingMenuInfo = null
        }
        if (viewFindParentViewForSubmenu != null) {
            menuPopupWindowCreatePopupWindow.setTouchModal(false)
            menuPopupWindowCreatePopupWindow.setEnterTransition(null)
            Int nextMenuPosition = getNextMenuPosition(iMeasureIndividualMenuWidth)
            Boolean z = nextMenuPosition == 1
            this.mLastPosition = nextMenuPosition
            if (Build.VERSION.SDK_INT >= 26) {
                menuPopupWindowCreatePopupWindow.setAnchorView(viewFindParentViewForSubmenu)
                i2 = 0
                i = 0
            } else {
                Array<Int> iArr = new Int[2]
                this.mAnchorView.getLocationOnScreen(iArr)
                Array<Int> iArr2 = new Int[2]
                viewFindParentViewForSubmenu.getLocationOnScreen(iArr2)
                if ((this.mDropDownGravity & 7) == 5) {
                    iArr[0] = iArr[0] + this.mAnchorView.getWidth()
                    iArr2[0] = iArr2[0] + viewFindParentViewForSubmenu.getWidth()
                }
                i = iArr2[0] - iArr[0]
                i2 = iArr2[1] - iArr[1]
            }
            menuPopupWindowCreatePopupWindow.setHorizontalOffset((this.mDropDownGravity & 5) == 5 ? z ? i + iMeasureIndividualMenuWidth : i - viewFindParentViewForSubmenu.getWidth() : z ? viewFindParentViewForSubmenu.getWidth() + i : i - iMeasureIndividualMenuWidth)
            menuPopupWindowCreatePopupWindow.setOverlapAnchor(true)
            menuPopupWindowCreatePopupWindow.setVerticalOffset(i2)
        } else {
            if (this.mHasXOffset) {
                menuPopupWindowCreatePopupWindow.setHorizontalOffset(this.mXOffset)
            }
            if (this.mHasYOffset) {
                menuPopupWindowCreatePopupWindow.setVerticalOffset(this.mYOffset)
            }
            menuPopupWindowCreatePopupWindow.setEpicenterBounds(getEpicenterBounds())
        }
        this.mShowingMenus.add(CascadingMenuInfo(menuPopupWindowCreatePopupWindow, menuBuilder, this.mLastPosition))
        menuPopupWindowCreatePopupWindow.show()
        ListView listView = menuPopupWindowCreatePopupWindow.getListView()
        listView.setOnKeyListener(this)
        if (cascadingMenuInfo == null && this.mShowTitle && menuBuilder.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false)
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title)
            frameLayout.setEnabled(false)
            textView.setText(menuBuilder.getHeaderTitle())
            listView.addHeaderView(frameLayout, null, false)
            menuPopupWindowCreatePopupWindow.show()
        }
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit addMenu(MenuBuilder menuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        menuBuilder.addMenuPresenter(this, this.mContext)
        if (isShowing()) {
            showMenu(menuBuilder)
        } else {
            this.mPendingMenus.add(menuBuilder)
        }
    }

    @Override // android.support.v7.view.menu.MenuPopup
    protected final Boolean closeMenuOnSubMenuOpened() {
        return false
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Unit dismiss() {
        Int size = this.mShowingMenus.size()
        if (size > 0) {
            Array<CascadingMenuInfo> cascadingMenuInfoArr = (Array<CascadingMenuInfo>) this.mShowingMenus.toArray(new CascadingMenuInfo[size])
            for (Int i = size - 1; i >= 0; i--) {
                CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[i]
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss()
                }
            }
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Boolean flagActionItems() {
        return false
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final ListView getListView() {
        if (this.mShowingMenus.isEmpty()) {
            return null
        }
        return ((CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1)).getListView()
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Boolean isShowing() {
        return this.mShowingMenus.size() > 0 && ((CascadingMenuInfo) this.mShowingMenus.get(0)).window.isShowing()
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        Int iFindIndexOfAddedMenu = findIndexOfAddedMenu(menuBuilder)
        if (iFindIndexOfAddedMenu < 0) {
            return
        }
        Int i = iFindIndexOfAddedMenu + 1
        if (i < this.mShowingMenus.size()) {
            ((CascadingMenuInfo) this.mShowingMenus.get(i)).menu.close(false)
        }
        CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.remove(iFindIndexOfAddedMenu)
        cascadingMenuInfo.menu.removeMenuPresenter(this)
        if (this.mShouldCloseImmediately) {
            cascadingMenuInfo.window.setExitTransition(null)
            cascadingMenuInfo.window.setAnimationStyle(0)
        }
        cascadingMenuInfo.window.dismiss()
        Int size = this.mShowingMenus.size()
        if (size > 0) {
            this.mLastPosition = ((CascadingMenuInfo) this.mShowingMenus.get(size - 1)).position
        } else {
            this.mLastPosition = getInitialMenuPosition()
        }
        if (size != 0) {
            if (z) {
                ((CascadingMenuInfo) this.mShowingMenus.get(0)).menu.close(false)
                return
            }
            return
        }
        dismiss()
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onCloseMenu(menuBuilder, true)
        }
        if (this.mTreeObserver != null) {
            if (this.mTreeObserver.isAlive()) {
                this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener)
            }
            this.mTreeObserver = null
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener)
        this.mOnDismissListener.onDismiss()
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final Unit onDismiss() {
        CascadingMenuInfo cascadingMenuInfo
        Int size = this.mShowingMenus.size()
        Int i = 0
        while (true) {
            if (i >= size) {
                cascadingMenuInfo = null
                break
            }
            cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.get(i)
            if (!cascadingMenuInfo.window.isShowing()) {
                break
            } else {
                i++
            }
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false)
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
    public final Boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                cascadingMenuInfo.getListView().requestFocus()
                return true
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false
        }
        addMenu(subMenuBuilder)
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onOpenSubMenu(subMenuBuilder)
        }
        return true
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setAnchorView(@NonNull View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView))
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setForceShowIcon(Boolean z) {
        this.mForceShowIcon = z
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setGravity(Int i) {
        if (this.mRawDropDownGravity != i) {
            this.mRawDropDownGravity = i
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this.mAnchorView))
        }
    }

    @Override // android.support.v7.view.menu.MenuPopup
    public final Unit setHorizontalOffset(Int i) {
        this.mHasXOffset = true
        this.mXOffset = i
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
        this.mHasYOffset = true
        this.mYOffset = i
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public final Unit show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (isShowing()) {
            return
        }
        Iterator it = this.mPendingMenus.iterator()
        while (it.hasNext()) {
            showMenu((MenuBuilder) it.next())
        }
        this.mPendingMenus.clear()
        this.mShownAnchorView = this.mAnchorView
        if (this.mShownAnchorView != null) {
            Boolean z = this.mTreeObserver == null
            this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver()
            if (z) {
                this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener)
            }
            this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener)
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    public final Unit updateMenuView(Boolean z) {
        Iterator it = this.mShowingMenus.iterator()
        while (it.hasNext()) {
            toMenuAdapter(((CascadingMenuInfo) it.next()).getListView().getAdapter()).notifyDataSetChanged()
        }
    }
}
