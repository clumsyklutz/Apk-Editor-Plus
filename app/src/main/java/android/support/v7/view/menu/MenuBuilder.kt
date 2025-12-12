package android.support.v7.view.menu

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import androidx.core.content.ContextCompat
import androidx.core.internal.view.SupportMenu
import android.support.v4.view.ActionProvider
import android.support.v4.view.ViewConfigurationCompat
import android.util.SparseArray
import android.view.ContextMenu
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.view.ViewConfiguration
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.concurrent.CopyOnWriteArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuBuilder implements SupportMenu {
    private static val ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates"
    private static val EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview"
    private static val PRESENTER_KEY = "android:menu:presenters"
    private static val TAG = "MenuBuilder"
    private static final Array<Int> sCategoryToOrder = {1, 4, 5, 3, 2, 0}
    private Callback mCallback
    private final Context mContext
    private ContextMenu.ContextMenuInfo mCurrentMenuInfo
    private MenuItemImpl mExpandedItem
    private SparseArray mFrozenViewStates
    Drawable mHeaderIcon
    CharSequence mHeaderTitle
    View mHeaderView
    private Boolean mOverrideVisibleItems
    private Boolean mQwertyMode
    private final Resources mResources
    private Boolean mShortcutsVisible
    private Int mDefaultShowAsAction = 0
    private Boolean mPreventDispatchingItemsChanged = false
    private Boolean mItemsChangedWhileDispatchPrevented = false
    private Boolean mStructureChangedWhileDispatchPrevented = false
    private Boolean mOptionalIconsVisible = false
    private Boolean mIsClosing = false
    private ArrayList mTempShortcutItemList = ArrayList()
    private CopyOnWriteArrayList mPresenters = CopyOnWriteArrayList()
    private Boolean mGroupDividerEnabled = false
    private ArrayList mItems = ArrayList()
    private ArrayList mVisibleItems = ArrayList()
    private Boolean mIsVisibleItemsStale = true
    private ArrayList mActionItems = ArrayList()
    private ArrayList mNonActionItems = ArrayList()
    private Boolean mIsActionItemsStale = true

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface Callback {
        Boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem)

        Unit onMenuModeChange(MenuBuilder menuBuilder)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface ItemInvoker {
        Boolean invokeItem(MenuItemImpl menuItemImpl)
    }

    constructor(Context context) {
        this.mContext = context
        this.mResources = context.getResources()
        setShortcutsVisibleInner(true)
    }

    private fun createNewMenuItem(Int i, Int i2, Int i3, Int i4, CharSequence charSequence, Int i5) {
        return MenuItemImpl(this, i, i2, i3, i4, charSequence, i5)
    }

    private fun dispatchPresenterUpdate(Boolean z) {
        if (this.mPresenters.isEmpty()) {
            return
        }
        stopDispatchingItemsChanged()
        Iterator it = this.mPresenters.iterator()
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference)
            } else {
                menuPresenter.updateMenuView(z)
            }
        }
        startDispatchingItemsChanged()
    }

    private fun dispatchRestoreInstanceState(Bundle bundle) {
        Parcelable parcelable
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(PRESENTER_KEY)
        if (sparseParcelableArray == null || this.mPresenters.isEmpty()) {
            return
        }
        Iterator it = this.mPresenters.iterator()
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference)
            } else {
                Int id = menuPresenter.getId()
                if (id > 0 && (parcelable = (Parcelable) sparseParcelableArray.get(id)) != null) {
                    menuPresenter.onRestoreInstanceState(parcelable)
                }
            }
        }
    }

    private fun dispatchSaveInstanceState(Bundle bundle) {
        Parcelable parcelableOnSaveInstanceState
        if (this.mPresenters.isEmpty()) {
            return
        }
        SparseArray<? extends Parcelable> sparseArray = new SparseArray<>()
        Iterator it = this.mPresenters.iterator()
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference)
            } else {
                Int id = menuPresenter.getId()
                if (id > 0 && (parcelableOnSaveInstanceState = menuPresenter.onSaveInstanceState()) != null) {
                    sparseArray.put(id, parcelableOnSaveInstanceState)
                }
            }
        }
        bundle.putSparseParcelableArray(PRESENTER_KEY, sparseArray)
    }

    private fun dispatchSubMenuSelected(SubMenuBuilder subMenuBuilder, MenuPresenter menuPresenter) {
        if (this.mPresenters.isEmpty()) {
            return false
        }
        Boolean zOnSubMenuSelected = menuPresenter != null ? menuPresenter.onSubMenuSelected(subMenuBuilder) : false
        Iterator it = this.mPresenters.iterator()
        Boolean zOnSubMenuSelected2 = zOnSubMenuSelected
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter2 = (MenuPresenter) weakReference.get()
            if (menuPresenter2 == null) {
                this.mPresenters.remove(weakReference)
            } else {
                zOnSubMenuSelected2 = !zOnSubMenuSelected2 ? menuPresenter2.onSubMenuSelected(subMenuBuilder) : zOnSubMenuSelected2
            }
        }
        return zOnSubMenuSelected2
    }

    private fun findInsertIndex(ArrayList arrayList, Int i) {
        for (Int size = arrayList.size() - 1; size >= 0; size--) {
            if (((MenuItemImpl) arrayList.get(size)).getOrdering() <= i) {
                return size + 1
            }
        }
        return 0
    }

    private fun getOrdering(Int i) {
        Int i2 = i >> 16
        if (i2 < 0 || i2 >= sCategoryToOrder.length) {
            throw IllegalArgumentException("order does not contain a valid category.")
        }
        return (sCategoryToOrder[i2] << 16) | (65535 & i)
    }

    private fun removeItemAtInt(Int i, Boolean z) {
        if (i < 0 || i >= this.mItems.size()) {
            return
        }
        this.mItems.remove(i)
        if (z) {
            onItemsChanged(true)
        }
    }

    private fun setHeaderInternal(Int i, CharSequence charSequence, Int i2, Drawable drawable, View view) {
        Resources resources = getResources()
        if (view != null) {
            this.mHeaderView = view
            this.mHeaderTitle = null
            this.mHeaderIcon = null
        } else {
            if (i > 0) {
                this.mHeaderTitle = resources.getText(i)
            } else if (charSequence != null) {
                this.mHeaderTitle = charSequence
            }
            if (i2 > 0) {
                this.mHeaderIcon = ContextCompat.getDrawable(getContext(), i2)
            } else if (drawable != null) {
                this.mHeaderIcon = drawable
            }
            this.mHeaderView = null
        }
        onItemsChanged(false)
    }

    private fun setShortcutsVisibleInner(Boolean z) {
        this.mShortcutsVisible = z && this.mResources.getConfiguration().keyboard != 1 && ViewConfigurationCompat.shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration.get(this.mContext), this.mContext)
    }

    @Override // android.view.Menu
    fun add(Int i) {
        return addInternal(0, 0, 0, this.mResources.getString(i))
    }

    @Override // android.view.Menu
    fun add(Int i, Int i2, Int i3, Int i4) {
        return addInternal(i, i2, i3, this.mResources.getString(i4))
    }

    @Override // android.view.Menu
    fun add(Int i, Int i2, Int i3, CharSequence charSequence) {
        return addInternal(i, i2, i3, charSequence)
    }

    @Override // android.view.Menu
    fun add(CharSequence charSequence) {
        return addInternal(0, 0, 0, charSequence)
    }

    @Override // android.view.Menu
    fun addIntentOptions(Int i, Int i2, Int i3, ComponentName componentName, Array<Intent> intentArr, Intent intent, Int i4, Array<MenuItem> menuItemArr) {
        PackageManager packageManager = this.mContext.getPackageManager()
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0)
        Int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0
        if ((i4 & 1) == 0) {
            removeGroup(i)
        }
        for (Int i5 = 0; i5 < size; i5++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i5)
            Intent intent2 = Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex])
            intent2.setComponent(ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name))
            MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2)
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3
            }
        }
        return size
    }

    protected fun addInternal(Int i, Int i2, Int i3, CharSequence charSequence) {
        Int ordering = getOrdering(i3)
        MenuItemImpl menuItemImplCreateNewMenuItem = createNewMenuItem(i, i2, i3, ordering, charSequence, this.mDefaultShowAsAction)
        if (this.mCurrentMenuInfo != null) {
            menuItemImplCreateNewMenuItem.setMenuInfo(this.mCurrentMenuInfo)
        }
        this.mItems.add(findInsertIndex(this.mItems, ordering), menuItemImplCreateNewMenuItem)
        onItemsChanged(true)
        return menuItemImplCreateNewMenuItem
    }

    fun addMenuPresenter(MenuPresenter menuPresenter) {
        addMenuPresenter(menuPresenter, this.mContext)
    }

    fun addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.mPresenters.add(WeakReference(menuPresenter))
        menuPresenter.initForMenu(context, this)
        this.mIsActionItemsStale = true
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i) {
        return addSubMenu(0, 0, 0, this.mResources.getString(i))
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i, Int i2, Int i3, Int i4) {
        return addSubMenu(i, i2, i3, this.mResources.getString(i4))
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i, Int i2, Int i3, CharSequence charSequence) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) addInternal(i, i2, i3, charSequence)
        SubMenuBuilder subMenuBuilder = SubMenuBuilder(this.mContext, this, menuItemImpl)
        menuItemImpl.setSubMenu(subMenuBuilder)
        return subMenuBuilder
    }

    @Override // android.view.Menu
    fun addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence)
    }

    fun changeMenuMode() {
        if (this.mCallback != null) {
            this.mCallback.onMenuModeChange(this)
        }
    }

    @Override // android.view.Menu
    fun clear() {
        if (this.mExpandedItem != null) {
            collapseItemActionView(this.mExpandedItem)
        }
        this.mItems.clear()
        onItemsChanged(true)
    }

    fun clearAll() {
        this.mPreventDispatchingItemsChanged = true
        clear()
        clearHeader()
        this.mPresenters.clear()
        this.mPreventDispatchingItemsChanged = false
        this.mItemsChangedWhileDispatchPrevented = false
        this.mStructureChangedWhileDispatchPrevented = false
        onItemsChanged(true)
    }

    fun clearHeader() {
        this.mHeaderIcon = null
        this.mHeaderTitle = null
        this.mHeaderView = null
        onItemsChanged(false)
    }

    @Override // android.view.Menu
    fun close() {
        close(true)
    }

    public final Unit close(Boolean z) {
        if (this.mIsClosing) {
            return
        }
        this.mIsClosing = true
        Iterator it = this.mPresenters.iterator()
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference)
            } else {
                menuPresenter.onCloseMenu(this, z)
            }
        }
        this.mIsClosing = false
    }

    fun collapseItemActionView(MenuItemImpl menuItemImpl) {
        Boolean zCollapseItemActionView = false
        if (!this.mPresenters.isEmpty() && this.mExpandedItem == menuItemImpl) {
            stopDispatchingItemsChanged()
            Iterator it = this.mPresenters.iterator()
            Boolean z = false
            while (true) {
                if (!it.hasNext()) {
                    zCollapseItemActionView = z
                    break
                }
                WeakReference weakReference = (WeakReference) it.next()
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
                if (menuPresenter != null) {
                    zCollapseItemActionView = menuPresenter.collapseItemActionView(this, menuItemImpl)
                    if (zCollapseItemActionView) {
                        break
                    }
                    z = zCollapseItemActionView
                } else {
                    this.mPresenters.remove(weakReference)
                }
            }
            startDispatchingItemsChanged()
            if (zCollapseItemActionView) {
                this.mExpandedItem = null
            }
        }
        return zCollapseItemActionView
    }

    Boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback != null && this.mCallback.onMenuItemSelected(menuBuilder, menuItem)
    }

    fun expandItemActionView(MenuItemImpl menuItemImpl) {
        Boolean zExpandItemActionView = false
        if (!this.mPresenters.isEmpty()) {
            stopDispatchingItemsChanged()
            Iterator it = this.mPresenters.iterator()
            Boolean z = false
            while (true) {
                if (!it.hasNext()) {
                    zExpandItemActionView = z
                    break
                }
                WeakReference weakReference = (WeakReference) it.next()
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
                if (menuPresenter != null) {
                    zExpandItemActionView = menuPresenter.expandItemActionView(this, menuItemImpl)
                    if (zExpandItemActionView) {
                        break
                    }
                    z = zExpandItemActionView
                } else {
                    this.mPresenters.remove(weakReference)
                }
            }
            startDispatchingItemsChanged()
            if (zExpandItemActionView) {
                this.mExpandedItem = menuItemImpl
            }
        }
        return zExpandItemActionView
    }

    fun findGroupIndex(Int i) {
        return findGroupIndex(i, 0)
    }

    fun findGroupIndex(Int i, Int i2) {
        Int size = size()
        if (i2 < 0) {
            i2 = 0
        }
        for (Int i3 = i2; i3 < size; i3++) {
            if (((MenuItemImpl) this.mItems.get(i3)).getGroupId() == i) {
                return i3
            }
        }
        return -1
    }

    @Override // android.view.Menu
    fun findItem(Int i) {
        MenuItem menuItemFindItem
        Int size = size()
        for (Int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i2)
            if (menuItemImpl.getItemId() == i) {
                return menuItemImpl
            }
            if (menuItemImpl.hasSubMenu() && (menuItemFindItem = menuItemImpl.getSubMenu().findItem(i)) != null) {
                return menuItemFindItem
            }
        }
        return null
    }

    fun findItemIndex(Int i) {
        Int size = size()
        for (Int i2 = 0; i2 < size; i2++) {
            if (((MenuItemImpl) this.mItems.get(i2)).getItemId() == i) {
                return i2
            }
        }
        return -1
    }

    MenuItemImpl findItemWithShortcutForKey(Int i, KeyEvent keyEvent) {
        ArrayList arrayList = this.mTempShortcutItemList
        arrayList.clear()
        findItemsWithShortcutForKey(arrayList, i, keyEvent)
        if (arrayList.isEmpty()) {
            return null
        }
        Int metaState = keyEvent.getMetaState()
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData()
        keyEvent.getKeyData(keyData)
        Int size = arrayList.size()
        if (size == 1) {
            return (MenuItemImpl) arrayList.get(0)
        }
        Boolean zIsQwertyMode = isQwertyMode()
        for (Int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i2)
            Char alphabeticShortcut = zIsQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut()
            if (alphabeticShortcut == keyData.meta[0] && (metaState & 2) == 0) {
                return menuItemImpl
            }
            if (alphabeticShortcut == keyData.meta[2] && (metaState & 2) != 0) {
                return menuItemImpl
            }
            if (zIsQwertyMode && alphabeticShortcut == '\b' && i == 67) {
                return menuItemImpl
            }
        }
        return null
    }

    Unit findItemsWithShortcutForKey(List list, Int i, KeyEvent keyEvent) {
        Boolean zIsQwertyMode = isQwertyMode()
        Int modifiers = keyEvent.getModifiers()
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData()
        if (keyEvent.getKeyData(keyData) || i == 67) {
            Int size = this.mItems.size()
            for (Int i2 = 0; i2 < size; i2++) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i2)
                if (menuItemImpl.hasSubMenu()) {
                    ((MenuBuilder) menuItemImpl.getSubMenu()).findItemsWithShortcutForKey(list, i, keyEvent)
                }
                Char alphabeticShortcut = zIsQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut()
                if (((modifiers & SupportMenu.SUPPORTED_MODIFIERS_MASK) == ((zIsQwertyMode ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers()) & SupportMenu.SUPPORTED_MODIFIERS_MASK)) && alphabeticShortcut != 0 && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (zIsQwertyMode && alphabeticShortcut == '\b' && i == 67)) && menuItemImpl.isEnabled())) {
                    list.add(menuItemImpl)
                }
            }
        }
    }

    fun flagActionItems() {
        ArrayList visibleItems = getVisibleItems()
        if (this.mIsActionItemsStale) {
            Iterator it = this.mPresenters.iterator()
            Boolean zFlagActionItems = false
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next()
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get()
                if (menuPresenter == null) {
                    this.mPresenters.remove(weakReference)
                } else {
                    zFlagActionItems = menuPresenter.flagActionItems() | zFlagActionItems
                }
            }
            if (zFlagActionItems) {
                this.mActionItems.clear()
                this.mNonActionItems.clear()
                Int size = visibleItems.size()
                for (Int i = 0; i < size; i++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i)
                    if (menuItemImpl.isActionButton()) {
                        this.mActionItems.add(menuItemImpl)
                    } else {
                        this.mNonActionItems.add(menuItemImpl)
                    }
                }
            } else {
                this.mActionItems.clear()
                this.mNonActionItems.clear()
                this.mNonActionItems.addAll(getVisibleItems())
            }
            this.mIsActionItemsStale = false
        }
    }

    fun getActionItems() {
        flagActionItems()
        return this.mActionItems
    }

    protected fun getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY
    }

    fun getContext() {
        return this.mContext
    }

    fun getExpandedItem() {
        return this.mExpandedItem
    }

    fun getHeaderIcon() {
        return this.mHeaderIcon
    }

    fun getHeaderTitle() {
        return this.mHeaderTitle
    }

    fun getHeaderView() {
        return this.mHeaderView
    }

    @Override // android.view.Menu
    fun getItem(Int i) {
        return (MenuItem) this.mItems.get(i)
    }

    fun getNonActionItems() {
        flagActionItems()
        return this.mNonActionItems
    }

    Boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible
    }

    Resources getResources() {
        return this.mResources
    }

    fun getRootMenu() {
        return this
    }

    @NonNull
    fun getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems
        }
        this.mVisibleItems.clear()
        Int size = this.mItems.size()
        for (Int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i)
            if (menuItemImpl.isVisible()) {
                this.mVisibleItems.add(menuItemImpl)
            }
        }
        this.mIsVisibleItemsStale = false
        this.mIsActionItemsStale = true
        return this.mVisibleItems
    }

    @Override // android.view.Menu
    fun hasVisibleItems() {
        if (this.mOverrideVisibleItems) {
            return true
        }
        Int size = size()
        for (Int i = 0; i < size; i++) {
            if (((MenuItemImpl) this.mItems.get(i)).isVisible()) {
                return true
            }
        }
        return false
    }

    fun isGroupDividerEnabled() {
        return this.mGroupDividerEnabled
    }

    Boolean isQwertyMode() {
        return this.mQwertyMode
    }

    @Override // android.view.Menu
    fun isShortcutKey(Int i, KeyEvent keyEvent) {
        return findItemWithShortcutForKey(i, keyEvent) != null
    }

    fun isShortcutsVisible() {
        return this.mShortcutsVisible
    }

    Unit onItemActionRequestChanged(MenuItemImpl menuItemImpl) {
        this.mIsActionItemsStale = true
        onItemsChanged(true)
    }

    Unit onItemVisibleChanged(MenuItemImpl menuItemImpl) {
        this.mIsVisibleItemsStale = true
        onItemsChanged(true)
    }

    fun onItemsChanged(Boolean z) {
        if (this.mPreventDispatchingItemsChanged) {
            this.mItemsChangedWhileDispatchPrevented = true
            if (z) {
                this.mStructureChangedWhileDispatchPrevented = true
                return
            }
            return
        }
        if (z) {
            this.mIsVisibleItemsStale = true
            this.mIsActionItemsStale = true
        }
        dispatchPresenterUpdate(z)
    }

    @Override // android.view.Menu
    fun performIdentifierAction(Int i, Int i2) {
        return performItemAction(findItem(i), i2)
    }

    fun performItemAction(MenuItem menuItem, Int i) {
        return performItemAction(menuItem, null, i)
    }

    fun performItemAction(MenuItem menuItem, MenuPresenter menuPresenter, Int i) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) menuItem
        if (menuItemImpl == null || !menuItemImpl.isEnabled()) {
            return false
        }
        Boolean zInvoke = menuItemImpl.invoke()
        ActionProvider supportActionProvider = menuItemImpl.getSupportActionProvider()
        Boolean z = supportActionProvider != null && supportActionProvider.hasSubMenu()
        if (menuItemImpl.hasCollapsibleActionView()) {
            Boolean zExpandActionView = menuItemImpl.expandActionView() | zInvoke
            if (!zExpandActionView) {
                return zExpandActionView
            }
            close(true)
            return zExpandActionView
        }
        if (!menuItemImpl.hasSubMenu() && !z) {
            if ((i & 1) == 0) {
                close(true)
            }
            return zInvoke
        }
        if ((i & 4) == 0) {
            close(false)
        }
        if (!menuItemImpl.hasSubMenu()) {
            menuItemImpl.setSubMenu(SubMenuBuilder(getContext(), this, menuItemImpl))
        }
        SubMenuBuilder subMenuBuilder = (SubMenuBuilder) menuItemImpl.getSubMenu()
        if (z) {
            supportActionProvider.onPrepareSubMenu(subMenuBuilder)
        }
        Boolean zDispatchSubMenuSelected = dispatchSubMenuSelected(subMenuBuilder, menuPresenter) | zInvoke
        if (zDispatchSubMenuSelected) {
            return zDispatchSubMenuSelected
        }
        close(true)
        return zDispatchSubMenuSelected
    }

    @Override // android.view.Menu
    fun performShortcut(Int i, KeyEvent keyEvent, Int i2) {
        MenuItemImpl menuItemImplFindItemWithShortcutForKey = findItemWithShortcutForKey(i, keyEvent)
        Boolean zPerformItemAction = menuItemImplFindItemWithShortcutForKey != null ? performItemAction(menuItemImplFindItemWithShortcutForKey, i2) : false
        if ((i2 & 2) != 0) {
            close(true)
        }
        return zPerformItemAction
    }

    @Override // android.view.Menu
    fun removeGroup(Int i) {
        Int iFindGroupIndex = findGroupIndex(i)
        if (iFindGroupIndex >= 0) {
            Int size = this.mItems.size() - iFindGroupIndex
            Int i2 = 0
            while (true) {
                Int i3 = i2 + 1
                if (i2 >= size || ((MenuItemImpl) this.mItems.get(iFindGroupIndex)).getGroupId() != i) {
                    break
                }
                removeItemAtInt(iFindGroupIndex, false)
                i2 = i3
            }
            onItemsChanged(true)
        }
    }

    @Override // android.view.Menu
    fun removeItem(Int i) {
        removeItemAtInt(findItemIndex(i), true)
    }

    fun removeItemAt(Int i) {
        removeItemAtInt(i, true)
    }

    fun removeMenuPresenter(MenuPresenter menuPresenter) {
        Iterator it = this.mPresenters.iterator()
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next()
            MenuPresenter menuPresenter2 = (MenuPresenter) weakReference.get()
            if (menuPresenter2 == null || menuPresenter2 == menuPresenter) {
                this.mPresenters.remove(weakReference)
            }
        }
    }

    fun restoreActionViewStates(Bundle bundle) {
        MenuItem menuItemFindItem
        if (bundle == null) {
            return
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(getActionViewStatesKey())
        Int size = size()
        for (Int i = 0; i < size; i++) {
            MenuItem item = getItem(i)
            View actionView = item.getActionView()
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray)
            }
            if (item.hasSubMenu()) {
                ((SubMenuBuilder) item.getSubMenu()).restoreActionViewStates(bundle)
            }
        }
        Int i2 = bundle.getInt(EXPANDED_ACTION_VIEW_ID)
        if (i2 <= 0 || (menuItemFindItem = findItem(i2)) == null) {
            return
        }
        menuItemFindItem.expandActionView()
    }

    fun restorePresenterStates(Bundle bundle) {
        dispatchRestoreInstanceState(bundle)
    }

    fun saveActionViewStates(Bundle bundle) {
        Int size = size()
        Int i = 0
        SparseArray<? extends Parcelable> sparseArray = null
        while (i < size) {
            MenuItem item = getItem(i)
            View actionView = item.getActionView()
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>()
                }
                actionView.saveHierarchyState(sparseArray)
                if (item.isActionViewExpanded()) {
                    bundle.putInt(EXPANDED_ACTION_VIEW_ID, item.getItemId())
                }
            }
            SparseArray<? extends Parcelable> sparseArray2 = sparseArray
            if (item.hasSubMenu()) {
                ((SubMenuBuilder) item.getSubMenu()).saveActionViewStates(bundle)
            }
            i++
            sparseArray = sparseArray2
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray)
        }
    }

    fun savePresenterStates(Bundle bundle) {
        dispatchSaveInstanceState(bundle)
    }

    fun setCallback(Callback callback) {
        this.mCallback = callback
    }

    fun setCurrentMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mCurrentMenuInfo = contextMenuInfo
    }

    fun setDefaultShowAsAction(Int i) {
        this.mDefaultShowAsAction = i
        return this
    }

    Unit setExclusiveItemChecked(MenuItem menuItem) {
        Int groupId = menuItem.getGroupId()
        Int size = this.mItems.size()
        stopDispatchingItemsChanged()
        for (Int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i)
            if (menuItemImpl.getGroupId() == groupId && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
                menuItemImpl.setCheckedInt(menuItemImpl == menuItem)
            }
        }
        startDispatchingItemsChanged()
    }

    @Override // android.view.Menu
    fun setGroupCheckable(Int i, Boolean z, Boolean z2) {
        Int size = this.mItems.size()
        for (Int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i2)
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setExclusiveCheckable(z2)
                menuItemImpl.setCheckable(z)
            }
        }
    }

    @Override // android.support.v4.internal.view.SupportMenu, android.view.Menu
    fun setGroupDividerEnabled(Boolean z) {
        this.mGroupDividerEnabled = z
    }

    @Override // android.view.Menu
    fun setGroupEnabled(Int i, Boolean z) {
        Int size = this.mItems.size()
        for (Int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i2)
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setEnabled(z)
            }
        }
    }

    @Override // android.view.Menu
    fun setGroupVisible(Int i, Boolean z) {
        Int size = this.mItems.size()
        Int i2 = 0
        Boolean z2 = false
        while (i2 < size) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i2)
            i2++
            z2 = (menuItemImpl.getGroupId() == i && menuItemImpl.setVisibleInt(z)) ? true : z2
        }
        if (z2) {
            onItemsChanged(true)
        }
    }

    protected fun setHeaderIconInt(Int i) {
        setHeaderInternal(0, null, i, null, null)
        return this
    }

    protected fun setHeaderIconInt(Drawable drawable) {
        setHeaderInternal(0, null, 0, drawable, null)
        return this
    }

    protected fun setHeaderTitleInt(Int i) {
        setHeaderInternal(i, null, 0, null, null)
        return this
    }

    protected fun setHeaderTitleInt(CharSequence charSequence) {
        setHeaderInternal(0, charSequence, 0, null, null)
        return this
    }

    protected fun setHeaderViewInt(View view) {
        setHeaderInternal(0, null, 0, null, view)
        return this
    }

    fun setOptionalIconsVisible(Boolean z) {
        this.mOptionalIconsVisible = z
    }

    fun setOverrideVisibleItems(Boolean z) {
        this.mOverrideVisibleItems = z
    }

    @Override // android.view.Menu
    fun setQwertyMode(Boolean z) {
        this.mQwertyMode = z
        onItemsChanged(false)
    }

    fun setShortcutsVisible(Boolean z) {
        if (this.mShortcutsVisible == z) {
            return
        }
        setShortcutsVisibleInner(z)
        onItemsChanged(false)
    }

    @Override // android.view.Menu
    fun size() {
        return this.mItems.size()
    }

    fun startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false
            onItemsChanged(this.mStructureChangedWhileDispatchPrevented)
        }
    }

    fun stopDispatchingItemsChanged() {
        if (this.mPreventDispatchingItemsChanged) {
            return
        }
        this.mPreventDispatchingItemsChanged = true
        this.mItemsChangedWhileDispatchPrevented = false
        this.mStructureChangedWhileDispatchPrevented = false
    }
}
