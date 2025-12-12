package android.support.v7.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.PorterDuff
import android.support.annotation.LayoutRes
import android.support.annotation.RestrictTo
import androidx.core.internal.view.SupportMenu
import android.support.v4.view.ActionProvider
import android.support.v4.view.MenuItemCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.view.menu.MenuItemWrapperICS
import android.support.v7.widget.DrawableUtils
import android.util.AttributeSet
import android.util.Log
import android.util.Xml
import android.view.InflateException
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import java.io.IOException
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SupportMenuInflater extends MenuInflater {
    static final Array<Class> ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE
    static final Array<Class> ACTION_VIEW_CONSTRUCTOR_SIGNATURE
    static val LOG_TAG = "SupportMenuInflater"
    static val NO_ID = 0
    private static val XML_GROUP = "group"
    private static val XML_ITEM = "item"
    private static val XML_MENU = "menu"
    final Array<Object> mActionProviderConstructorArguments
    final Array<Object> mActionViewConstructorArguments
    Context mContext
    private Object mRealOwner

    class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        private static final Array<Class> PARAM_TYPES = {MenuItem.class}
        private Method mMethod
        private Object mRealOwner

        constructor(Object obj, String str) {
            this.mRealOwner = obj
            Class<?> cls = obj.getClass()
            try {
                this.mMethod = cls.getMethod(str, PARAM_TYPES)
            } catch (Exception e) {
                InflateException inflateException = InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName())
                inflateException.initCause(e)
                throw inflateException
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        fun onMenuItemClick(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, menuItem)).booleanValue()
                }
                this.mMethod.invoke(this.mRealOwner, menuItem)
                return true
            } catch (Exception e) {
                throw RuntimeException(e)
            }
        }
    }

    class MenuState {
        private static val defaultGroupId = 0
        private static val defaultItemCategory = 0
        private static val defaultItemCheckable = 0
        private static val defaultItemChecked = false
        private static val defaultItemEnabled = true
        private static val defaultItemId = 0
        private static val defaultItemOrder = 0
        private static val defaultItemVisible = true
        private Int groupCategory
        private Int groupCheckable
        private Boolean groupEnabled
        private Int groupId
        private Int groupOrder
        private Boolean groupVisible
        ActionProvider itemActionProvider
        private String itemActionProviderClassName
        private String itemActionViewClassName
        private Int itemActionViewLayout
        private Boolean itemAdded
        private Int itemAlphabeticModifiers
        private Char itemAlphabeticShortcut
        private Int itemCategoryOrder
        private Int itemCheckable
        private Boolean itemChecked
        private CharSequence itemContentDescription
        private Boolean itemEnabled
        private Int itemIconResId
        private ColorStateList itemIconTintList = null
        private PorterDuff.Mode itemIconTintMode = null
        private Int itemId
        private String itemListenerMethodName
        private Int itemNumericModifiers
        private Char itemNumericShortcut
        private Int itemShowAsAction
        private CharSequence itemTitle
        private CharSequence itemTitleCondensed
        private CharSequence itemTooltipText
        private Boolean itemVisible
        private Menu menu

        constructor(Menu menu) {
            this.menu = menu
            resetGroup()
        }

        private fun getShortcut(String str) {
            if (str == null) {
                return (Char) 0
            }
            return str.charAt(0)
        }

        private fun newInstance(String str, Array<Class> clsArr, Array<Object> objArr) throws NoSuchMethodException, SecurityException {
            try {
                Constructor<?> constructor = SupportMenuInflater.this.mContext.getClassLoader().loadClass(str).getConstructor(clsArr)
                constructor.setAccessible(true)
                return constructor.newInstance(objArr)
            } catch (Exception e) {
                Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e)
                return null
            }
        }

        private fun setItem(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Boolean z = true
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable > 0).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId)
            if (this.itemShowAsAction >= 0) {
                menuItem.setShowAsAction(this.itemShowAsAction)
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw IllegalStateException("The android:onClick attribute cannot be used within a restricted context")
                }
                menuItem.setOnMenuItemClickListener(InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName))
            }
            if (this.itemCheckable >= 2) {
                if (menuItem is MenuItemImpl) {
                    ((MenuItemImpl) menuItem).setExclusiveCheckable(true)
                } else if (menuItem is MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) menuItem).setExclusiveCheckable(true)
                }
            }
            if (this.itemActionViewClassName != null) {
                menuItem.setActionView((View) newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments))
            } else {
                z = false
            }
            if (this.itemActionViewLayout > 0) {
                if (z) {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.")
                } else {
                    menuItem.setActionView(this.itemActionViewLayout)
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider(menuItem, this.itemActionProvider)
            }
            MenuItemCompat.setContentDescription(menuItem, this.itemContentDescription)
            MenuItemCompat.setTooltipText(menuItem, this.itemTooltipText)
            MenuItemCompat.setAlphabeticShortcut(menuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers)
            MenuItemCompat.setNumericShortcut(menuItem, this.itemNumericShortcut, this.itemNumericModifiers)
            if (this.itemIconTintMode != null) {
                MenuItemCompat.setIconTintMode(menuItem, this.itemIconTintMode)
            }
            if (this.itemIconTintList != null) {
                MenuItemCompat.setIconTintList(menuItem, this.itemIconTintList)
            }
        }

        fun addItem() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.itemAdded = true
            setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle))
        }

        fun addSubMenuItem() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.itemAdded = true
            SubMenu subMenuAddSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle)
            setItem(subMenuAddSubMenu.getItem())
            return subMenuAddSubMenu
        }

        fun hasAddedItem() {
            return this.itemAdded
        }

        fun readGroup(AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup)
            this.groupId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuGroup_android_id, 0)
            this.groupCategory = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_menuCategory, 0)
            this.groupOrder = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_orderInCategory, 0)
            this.groupCheckable = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0)
            this.groupVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_visible, true)
            this.groupEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_enabled, true)
            typedArrayObtainStyledAttributes.recycle()
        }

        fun readItem(AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuItem)
            this.itemId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_id, 0)
            this.itemCategoryOrder = (typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) & SupportMenu.CATEGORY_MASK) | (typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder) & SupportMenu.USER_MASK)
            this.itemTitle = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_title)
            this.itemTitleCondensed = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_titleCondensed)
            this.itemIconResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_icon, 0)
            this.itemAlphabeticShortcut = getShortcut(typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_alphabeticShortcut))
            this.itemAlphabeticModifiers = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096)
            this.itemNumericShortcut = getShortcut(typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_numericShortcut))
            this.itemNumericModifiers = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_numericModifiers, 4096)
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_android_checkable)) {
                this.itemCheckable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0
            } else {
                this.itemCheckable = this.groupCheckable
            }
            this.itemChecked = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checked, false)
            this.itemVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible)
            this.itemEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled)
            this.itemShowAsAction = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_showAsAction, -1)
            this.itemListenerMethodName = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_onClick)
            this.itemActionViewLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_actionLayout, 0)
            this.itemActionViewClassName = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionViewClass)
            this.itemActionProviderClassName = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionProviderClass)
            Boolean z = this.itemActionProviderClassName != null
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments)
            } else {
                if (z) {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.")
                }
                this.itemActionProvider = null
            }
            this.itemContentDescription = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_contentDescription)
            this.itemTooltipText = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_tooltipText)
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTintMode)) {
                this.itemIconTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode)
            } else {
                this.itemIconTintMode = null
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTint)) {
                this.itemIconTintList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.MenuItem_iconTint)
            } else {
                this.itemIconTintList = null
            }
            typedArrayObtainStyledAttributes.recycle()
            this.itemAdded = false
        }

        fun resetGroup() {
            this.groupId = 0
            this.groupCategory = 0
            this.groupOrder = 0
            this.groupCheckable = 0
            this.groupVisible = true
            this.groupEnabled = true
        }
    }

    static {
        Array<Class> clsArr = {Context.class}
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = clsArr
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = clsArr
    }

    constructor(Context context) {
        super(context)
        this.mContext = context
        this.mActionViewConstructorArguments = new Array<Object>{context}
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments
    }

    private fun findRealOwner(Object obj) {
        Object baseContext = obj
        while (!(baseContext is Activity) && (baseContext is ContextWrapper)) {
            baseContext = ((ContextWrapper) baseContext).getBaseContext()
        }
        return baseContext
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        switch(r3) {
            case 1: goto L57
            case 2: goto L18
            case 3: goto L29
            default: goto L11
        }
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
    
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:
    
        r9 = r3
        r3 = r11.next()
        r5 = r9
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
    
        if (r5 != false) goto L11
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004f, code lost:
    
        r3 = r11.getName()
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0059, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_GROUP) == false) goto L22
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
    
        r7.readGroup(r12)
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_ITEM) == false) goto L25
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        r7.readItem(r12)
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0073, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_MENU) == false) goto L28
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0075, code lost:
    
        parseMenu(r11, r12, r7.addSubMenuItem())
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007e, code lost:
    
        r2 = r3
        r3 = true
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0081, code lost:
    
        r3 = r11.getName()
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0085, code lost:
    
        if (r5 == false) goto L34
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:
    
        if (r3.equals(r2) == false) goto L34
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008d, code lost:
    
        r2 = null
        r3 = false
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0096, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_GROUP) == false) goto L37
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0098, code lost:
    
        r7.resetGroup()
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a3, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_ITEM) == false) goto L47
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
    
        if (r7.hasAddedItem() != false) goto L11
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ad, code lost:
    
        if (r7.itemActionProvider == null) goto L46
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b5, code lost:
    
        if (r7.itemActionProvider.hasSubMenu() == false) goto L46
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b7, code lost:
    
        r7.addSubMenuItem()
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bd, code lost:
    
        r7.addItem()
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c9, code lost:
    
        if (r3.equals(android.support.v7.view.SupportMenuInflater.XML_MENU) == false) goto L11
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00cb, code lost:
    
        r0 = true
        r3 = r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d6, code lost:
    
        throw new java.lang.RuntimeException("Unexpected end of document")
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d7, code lost:
    
        return
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:
    
        r2 = null
        r5 = false
        r3 = r0
        r0 = false
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0023, code lost:
    
        if (r0 != false) goto L56
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun parseMenu(org.xmlpull.v1.XmlPullParser r11, android.util.AttributeSet r12, android.view.Menu r13) throws org.xmlpull.v1.XmlPullParserException, java.lang.IllegalAccessException, java.io.IOException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r10 = this
            r4 = 0
            r1 = 1
            r6 = 0
            android.support.v7.view.SupportMenuInflater$MenuState r7 = new android.support.v7.view.SupportMenuInflater$MenuState
            r7.<init>(r13)
            Int r0 = r11.getEventType()
        Lc:
            r2 = 2
            if (r0 != r2) goto L46
            java.lang.String r0 = r11.getName()
            java.lang.String r2 = "menu"
            Boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L31
            Int r0 = r11.next()
        L1f:
            r2 = r4
            r5 = r6
            r3 = r0
            r0 = r6
        L23:
            if (r0 != 0) goto Ld7
            switch(r3) {
                case 1: goto Lcf
                case 2: goto L4d
                case 3: goto L81
                default: goto L28
            }
        L28:
            r3 = r5
        L29:
            Int r5 = r11.next()
            r9 = r3
            r3 = r5
            r5 = r9
            goto L23
        L31:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Expecting menu, got "
            r2.<init>(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L46:
            Int r0 = r11.next()
            if (r0 != r1) goto Lc
            goto L1f
        L4d:
            if (r5 != 0) goto L28
            java.lang.String r3 = r11.getName()
            java.lang.String r8 = "group"
            Boolean r8 = r3.equals(r8)
            if (r8 == 0) goto L60
            r7.readGroup(r12)
            r3 = r5
            goto L29
        L60:
            java.lang.String r8 = "item"
            Boolean r8 = r3.equals(r8)
            if (r8 == 0) goto L6d
            r7.readItem(r12)
            r3 = r5
            goto L29
        L6d:
            java.lang.String r8 = "menu"
            Boolean r8 = r3.equals(r8)
            if (r8 == 0) goto L7e
            android.view.SubMenu r3 = r7.addSubMenuItem()
            r10.parseMenu(r11, r12, r3)
            r3 = r5
            goto L29
        L7e:
            r2 = r3
            r3 = r1
            goto L29
        L81:
            java.lang.String r3 = r11.getName()
            if (r5 == 0) goto L90
            Boolean r8 = r3.equals(r2)
            if (r8 == 0) goto L90
            r2 = r4
            r3 = r6
            goto L29
        L90:
            java.lang.String r8 = "group"
            Boolean r8 = r3.equals(r8)
            if (r8 == 0) goto L9d
            r7.resetGroup()
            r3 = r5
            goto L29
        L9d:
            java.lang.String r8 = "item"
            Boolean r8 = r3.equals(r8)
            if (r8 == 0) goto Lc3
            Boolean r3 = r7.hasAddedItem()
            if (r3 != 0) goto L28
            android.support.v4.view.ActionProvider r3 = r7.itemActionProvider
            if (r3 == 0) goto Lbd
            android.support.v4.view.ActionProvider r3 = r7.itemActionProvider
            Boolean r3 = r3.hasSubMenu()
            if (r3 == 0) goto Lbd
            r7.addSubMenuItem()
            r3 = r5
            goto L29
        Lbd:
            r7.addItem()
            r3 = r5
            goto L29
        Lc3:
            java.lang.String r8 = "menu"
            Boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L28
            r0 = r1
            r3 = r5
            goto L29
        Lcf:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Unexpected end of document"
            r0.<init>(r1)
            throw r0
        Ld7:
            return
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.view.SupportMenuInflater.parseMenu(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.Menu):Unit")
    }

    Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext)
        }
        return this.mRealOwner
    }

    @Override // android.view.MenuInflater
    fun inflate(@LayoutRes Int i, Menu menu) {
        if (!(menu is SupportMenu)) {
            super.inflate(i, menu)
            return
        }
        XmlResourceParser layout = null
        try {
            try {
                layout = this.mContext.getResources().getLayout(i)
                parseMenu(layout, Xml.asAttributeSet(layout), menu)
            } catch (IOException e) {
                throw InflateException("Error inflating menu XML", e)
            } catch (XmlPullParserException e2) {
                throw InflateException("Error inflating menu XML", e2)
            }
        } finally {
            if (layout != null) {
                layout.close()
            }
        }
    }
}
