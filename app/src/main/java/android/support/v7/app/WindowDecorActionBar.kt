package android.support.v7.app

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.ViewPropertyAnimatorUpdateListener
import android.support.v7.app.ActionBar
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.ActionBarPolicy
import android.support.v7.view.ActionMode
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.ViewPropertyAnimatorCompatSet
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.view.menu.SubMenuBuilder
import android.support.v7.widget.ActionBarContainer
import android.support.v7.widget.ActionBarContextView
import android.support.v7.widget.ActionBarOverlayLayout
import android.support.v7.widget.DecorToolbar
import android.support.v7.widget.ScrollingTabContainerView
import androidx.appcompat.widget.Toolbar
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.SpinnerAdapter
import java.lang.ref.WeakReference
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    static final /* synthetic */ Boolean $assertionsDisabled
    private static val FADE_IN_DURATION_MS = 200
    private static val FADE_OUT_DURATION_MS = 100
    private static val INVALID_POSITION = -1
    private static val TAG = "WindowDecorActionBar"
    private static final Interpolator sHideInterpolator
    private static final Interpolator sShowInterpolator
    ActionModeImpl mActionMode
    private Activity mActivity
    ActionBarContainer mContainerView
    View mContentView
    Context mContext
    ActionBarContextView mContextView
    ViewPropertyAnimatorCompatSet mCurrentShowAnim
    DecorToolbar mDecorToolbar
    ActionMode mDeferredDestroyActionMode
    ActionMode.Callback mDeferredModeDestroyCallback
    private Dialog mDialog
    private Boolean mDisplayHomeAsUpSet
    private Boolean mHasEmbeddedTabs
    Boolean mHiddenByApp
    Boolean mHiddenBySystem
    Boolean mHideOnContentScroll
    private Boolean mLastMenuVisibility
    ActionBarOverlayLayout mOverlayLayout
    private TabImpl mSelectedTab
    private Boolean mShowHideAnimationEnabled
    private Boolean mShowingForMode
    ScrollingTabContainerView mTabScrollView
    private Context mThemedContext
    private ArrayList mTabs = ArrayList()
    private Int mSavedTabPosition = -1
    private ArrayList mMenuVisibilityListeners = ArrayList()
    private Int mCurWindowVisibility = 0
    Boolean mContentAnimations = true
    private Boolean mNowShowing = true
    val mHideListener = ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.WindowDecorActionBar.1
        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                WindowDecorActionBar.this.mContentView.setTranslationY(0.0f)
                WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f)
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8)
            WindowDecorActionBar.this.mContainerView.setTransitioning(false)
            WindowDecorActionBar.this.mCurrentShowAnim = null
            WindowDecorActionBar.this.completeDeferredDestroyActionMode()
            if (WindowDecorActionBar.this.mOverlayLayout != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.mOverlayLayout)
            }
        }
    }
    val mShowListener = ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.WindowDecorActionBar.2
        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationEnd(View view) {
            WindowDecorActionBar.this.mCurrentShowAnim = null
            WindowDecorActionBar.this.mContainerView.requestLayout()
        }
    }
    val mUpdateListener = ViewPropertyAnimatorUpdateListener() { // from class: android.support.v7.app.WindowDecorActionBar.3
        @Override // android.support.v4.view.ViewPropertyAnimatorUpdateListener
        fun onAnimationUpdate(View view) {
            ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate()
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext
        private ActionMode.Callback mCallback
        private WeakReference mCustomView
        private final MenuBuilder mMenu

        constructor(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context
            this.mCallback = callback
            this.mMenu = MenuBuilder(context).setDefaultShowAsAction(1)
            this.mMenu.setCallback(this)
        }

        fun dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged()
            try {
                return this.mCallback.onCreateActionMode(this, this.mMenu)
            } finally {
                this.mMenu.startDispatchingItemsChanged()
            }
        }

        @Override // android.support.v7.view.ActionMode
        fun finish() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return
            }
            if (WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                this.mCallback.onDestroyActionMode(this)
            } else {
                WindowDecorActionBar.this.mDeferredDestroyActionMode = this
                WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback
            }
            this.mCallback = null
            WindowDecorActionBar.this.animateToMode(false)
            WindowDecorActionBar.this.mContextView.closeMode()
            WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32)
            WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll)
            WindowDecorActionBar.this.mActionMode = null
        }

        @Override // android.support.v7.view.ActionMode
        fun getCustomView() {
            if (this.mCustomView != null) {
                return (View) this.mCustomView.get()
            }
            return null
        }

        @Override // android.support.v7.view.ActionMode
        fun getMenu() {
            return this.mMenu
        }

        @Override // android.support.v7.view.ActionMode
        fun getMenuInflater() {
            return SupportMenuInflater(this.mActionModeContext)
        }

        @Override // android.support.v7.view.ActionMode
        fun getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle()
        }

        @Override // android.support.v7.view.ActionMode
        fun getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle()
        }

        @Override // android.support.v7.view.ActionMode
        fun invalidate() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return
            }
            this.mMenu.stopDispatchingItemsChanged()
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu)
            } finally {
                this.mMenu.startDispatchingItemsChanged()
            }
        }

        @Override // android.support.v7.view.ActionMode
        fun isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional()
        }

        fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        }

        fun onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        fun onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, menuItem)
            }
            return false
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        fun onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return
            }
            invalidate()
            WindowDecorActionBar.this.mContextView.showOverflowMenu()
        }

        fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.mCallback == null) {
                return false
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true
            }
            MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show()
            return true
        }

        @Override // android.support.v7.view.ActionMode
        fun setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view)
            this.mCustomView = WeakReference(view)
        }

        @Override // android.support.v7.view.ActionMode
        fun setSubtitle(Int i) {
            setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(i))
        }

        @Override // android.support.v7.view.ActionMode
        fun setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setSubtitle(charSequence)
        }

        @Override // android.support.v7.view.ActionMode
        fun setTitle(Int i) {
            setTitle(WindowDecorActionBar.this.mContext.getResources().getString(i))
        }

        @Override // android.support.v7.view.ActionMode
        fun setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setTitle(charSequence)
        }

        @Override // android.support.v7.view.ActionMode
        fun setTitleOptionalHint(Boolean z) {
            super.setTitleOptionalHint(z)
            WindowDecorActionBar.this.mContextView.setTitleOptional(z)
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class TabImpl extends ActionBar.Tab {
        private ActionBar.TabListener mCallback
        private CharSequence mContentDesc
        private View mCustomView
        private Drawable mIcon
        private Int mPosition = -1
        private Object mTag
        private CharSequence mText

        constructor() {
        }

        public ActionBar.TabListener getCallback() {
            return this.mCallback
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getContentDescription() {
            return this.mContentDesc
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getCustomView() {
            return this.mCustomView
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getIcon() {
            return this.mIcon
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getPosition() {
            return this.mPosition
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getTag() {
            return this.mTag
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun getText() {
            return this.mText
        }

        @Override // android.support.v7.app.ActionBar.Tab
        fun select() {
            WindowDecorActionBar.this.selectTab(this)
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(Int i) {
            return setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(i))
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition)
            }
            return this
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(Int i) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(i, (ViewGroup) null))
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(View view) {
            this.mCustomView = view
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition)
            }
            return this
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setIcon(Int i) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, i))
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setIcon(Drawable drawable) {
            this.mIcon = drawable
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition)
            }
            return this
        }

        fun setPosition(Int i) {
            this.mPosition = i
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.mCallback = tabListener
            return this
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setTag(Object obj) {
            this.mTag = obj
            return this
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setText(Int i) {
            return setText(WindowDecorActionBar.this.mContext.getResources().getText(i))
        }

        @Override // android.support.v7.app.ActionBar.Tab
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.mText = charSequence
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition)
            }
            return this
        }
    }

    static {
        $assertionsDisabled = !WindowDecorActionBar.class.desiredAssertionStatus()
        sHideInterpolator = AccelerateInterpolator()
        sShowInterpolator = DecelerateInterpolator()
    }

    constructor(Activity activity, Boolean z) {
        this.mActivity = activity
        View decorView = activity.getWindow().getDecorView()
        init(decorView)
        if (z) {
            return
        }
        this.mContentView = decorView.findViewById(R.id.content)
    }

    constructor(Dialog dialog) {
        this.mDialog = dialog
        init(dialog.getWindow().getDecorView())
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    constructor(View view) {
        if (!$assertionsDisabled && !view.isInEditMode()) {
            throw AssertionError()
        }
        init(view)
    }

    static Boolean checkShowingFlags(Boolean z, Boolean z2, Boolean z3) {
        if (z3) {
            return true
        }
        return (z || z2) ? false : true
    }

    private fun cleanupTabs() {
        if (this.mSelectedTab != null) {
            selectTab(null)
        }
        this.mTabs.clear()
        if (this.mTabScrollView != null) {
            this.mTabScrollView.removeAllTabs()
        }
        this.mSavedTabPosition = -1
    }

    private fun configureTab(ActionBar.Tab tab, Int i) {
        TabImpl tabImpl = (TabImpl) tab
        if (tabImpl.getCallback() == null) {
            throw IllegalStateException("Action Bar Tab must have a Callback")
        }
        tabImpl.setPosition(i)
        this.mTabs.add(i, tabImpl)
        Int size = this.mTabs.size()
        for (Int i2 = i + 1; i2 < size; i2++) {
            ((TabImpl) this.mTabs.get(i2)).setPosition(i2)
        }
    }

    private fun ensureTabsExist() {
        if (this.mTabScrollView != null) {
            return
        }
        ScrollingTabContainerView scrollingTabContainerView = ScrollingTabContainerView(this.mContext)
        if (this.mHasEmbeddedTabs) {
            scrollingTabContainerView.setVisibility(0)
            this.mDecorToolbar.setEmbeddedTabView(scrollingTabContainerView)
        } else {
            if (getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0)
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(this.mOverlayLayout)
                }
            } else {
                scrollingTabContainerView.setVisibility(8)
            }
            this.mContainerView.setTabContainer(scrollingTabContainerView)
        }
        this.mTabScrollView = scrollingTabContainerView
    }

    /* JADX WARN: Multi-variable type inference failed */
    private fun getDecorToolbar(View view) {
        if (view is DecorToolbar) {
            return (DecorToolbar) view
        }
        if (view is Toolbar) {
            return ((Toolbar) view).getWrapper()
        }
        throw IllegalStateException("Can't make a decor toolbar out of " + (view != 0 ? view.getClass().getSimpleName() : "null"))
    }

    private fun hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false)
            }
            updateVisibility(false)
        }
    }

    private fun init(View view) {
        this.mOverlayLayout = (ActionBarOverlayLayout) view.findViewById(android.support.v7.appcompat.R.id.decor_content_parent)
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this)
        }
        this.mDecorToolbar = getDecorToolbar(view.findViewById(android.support.v7.appcompat.R.id.action_bar))
        this.mContextView = (ActionBarContextView) view.findViewById(android.support.v7.appcompat.R.id.action_context_bar)
        this.mContainerView = (ActionBarContainer) view.findViewById(android.support.v7.appcompat.R.id.action_bar_container)
        if (this.mDecorToolbar == null || this.mContextView == null || this.mContainerView == null) {
            throw IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout")
        }
        this.mContext = this.mDecorToolbar.getContext()
        Boolean z = (this.mDecorToolbar.getDisplayOptions() & 4) != 0
        if (z) {
            this.mDisplayHomeAsUpSet = true
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext)
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z)
        setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs())
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0)
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true)
        }
        Int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.ActionBar_elevation, 0)
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize)
        }
        typedArrayObtainStyledAttributes.recycle()
    }

    private fun setHasEmbeddedTabs(Boolean z) {
        this.mHasEmbeddedTabs = z
        if (this.mHasEmbeddedTabs) {
            this.mContainerView.setTabContainer(null)
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView)
        } else {
            this.mDecorToolbar.setEmbeddedTabView(null)
            this.mContainerView.setTabContainer(this.mTabScrollView)
        }
        Boolean z2 = getNavigationMode() == 2
        if (this.mTabScrollView != null) {
            if (z2) {
                this.mTabScrollView.setVisibility(0)
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(this.mOverlayLayout)
                }
            } else {
                this.mTabScrollView.setVisibility(8)
            }
        }
        this.mDecorToolbar.setCollapsible(!this.mHasEmbeddedTabs && z2)
        this.mOverlayLayout.setHasNonEmbeddedTabs(!this.mHasEmbeddedTabs && z2)
    }

    private fun shouldAnimateContextView() {
        return ViewCompat.isLaidOut(this.mContainerView)
    }

    private fun showForActionMode() {
        if (this.mShowingForMode) {
            return
        }
        this.mShowingForMode = true
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setShowingForActionMode(true)
        }
        updateVisibility(false)
    }

    private fun updateVisibility(Boolean z) {
        if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (this.mNowShowing) {
                return
            }
            this.mNowShowing = true
            doShow(z)
            return
        }
        if (this.mNowShowing) {
            this.mNowShowing = false
            doHide(z)
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener)
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab) {
        addTab(tab, this.mTabs.isEmpty())
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Int i) {
        addTab(tab, i, this.mTabs.isEmpty())
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Int i, Boolean z) {
        ensureTabsExist()
        this.mTabScrollView.addTab(tab, i, z)
        configureTab(tab, i)
        if (z) {
            selectTab(tab)
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Boolean z) {
        ensureTabsExist()
        this.mTabScrollView.addTab(tab, z)
        configureTab(tab, this.mTabs.size())
        if (z) {
            selectTab(tab)
        }
    }

    fun animateToMode(Boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2
        if (z) {
            showForActionMode()
        } else {
            hideForActionMode()
        }
        if (!shouldAnimateContextView()) {
            if (z) {
                this.mDecorToolbar.setVisibility(4)
                this.mContextView.setVisibility(0)
                return
            } else {
                this.mDecorToolbar.setVisibility(0)
                this.mContextView.setVisibility(8)
                return
            }
        }
        if (z) {
            viewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(4, FADE_OUT_DURATION_MS)
            viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS)
        } else {
            viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS)
            viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS)
        }
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet()
        viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat2, viewPropertyAnimatorCompat)
        viewPropertyAnimatorCompatSet.start()
    }

    @Override // android.support.v7.app.ActionBar
    fun collapseActionView() {
        if (this.mDecorToolbar == null || !this.mDecorToolbar.hasExpandedActionView()) {
            return false
        }
        this.mDecorToolbar.collapseActionView()
        return true
    }

    Unit completeDeferredDestroyActionMode() {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode)
            this.mDeferredDestroyActionMode = null
            this.mDeferredModeDestroyCallback = null
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun dispatchMenuVisibilityChanged(Boolean z) {
        if (z == this.mLastMenuVisibility) {
            return
        }
        this.mLastMenuVisibility = z
        Int size = this.mMenuVisibilityListeners.size()
        for (Int i = 0; i < size; i++) {
            ((ActionBar.OnMenuVisibilityListener) this.mMenuVisibilityListeners.get(i)).onMenuVisibilityChanged(z)
        }
    }

    fun doHide(Boolean z) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel()
        }
        if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !z)) {
            this.mHideListener.onAnimationEnd(null)
            return
        }
        this.mContainerView.setAlpha(1.0f)
        this.mContainerView.setTransitioning(true)
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet()
        Float f = -this.mContainerView.getHeight()
        if (z) {
            this.mContainerView.getLocationInWindow(new Array<Int>{0, 0})
            f -= r2[1]
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.mContainerView).translationY(f)
        viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.mUpdateListener)
        viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompatTranslationY)
        if (this.mContentAnimations && this.mContentView != null) {
            viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.mContentView).translationY(f))
        }
        viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator)
        viewPropertyAnimatorCompatSet.setDuration(250L)
        viewPropertyAnimatorCompatSet.setListener(this.mHideListener)
        this.mCurrentShowAnim = viewPropertyAnimatorCompatSet
        viewPropertyAnimatorCompatSet.start()
    }

    fun doShow(Boolean z) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel()
        }
        this.mContainerView.setVisibility(0)
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || z)) {
            this.mContainerView.setTranslationY(0.0f)
            Float f = -this.mContainerView.getHeight()
            if (z) {
                this.mContainerView.getLocationInWindow(new Array<Int>{0, 0})
                f -= r1[1]
            }
            this.mContainerView.setTranslationY(f)
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = ViewPropertyAnimatorCompatSet()
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.mContainerView).translationY(0.0f)
            viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.mUpdateListener)
            viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompatTranslationY)
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(f)
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.mContentView).translationY(0.0f))
            }
            viewPropertyAnimatorCompatSet.setInterpolator(sShowInterpolator)
            viewPropertyAnimatorCompatSet.setDuration(250L)
            viewPropertyAnimatorCompatSet.setListener(this.mShowListener)
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet
            viewPropertyAnimatorCompatSet.start()
        } else {
            this.mContainerView.setAlpha(1.0f)
            this.mContainerView.setTranslationY(0.0f)
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(0.0f)
            }
            this.mShowListener.onAnimationEnd(null)
        }
        if (this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets(this.mOverlayLayout)
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun enableContentAnimations(Boolean z) {
        this.mContentAnimations = z
    }

    @Override // android.support.v7.app.ActionBar
    fun getCustomView() {
        return this.mDecorToolbar.getCustomView()
    }

    @Override // android.support.v7.app.ActionBar
    fun getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions()
    }

    @Override // android.support.v7.app.ActionBar
    fun getElevation() {
        return ViewCompat.getElevation(this.mContainerView)
    }

    @Override // android.support.v7.app.ActionBar
    fun getHeight() {
        return this.mContainerView.getHeight()
    }

    @Override // android.support.v7.app.ActionBar
    fun getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset()
    }

    @Override // android.support.v7.app.ActionBar
    fun getNavigationItemCount() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownItemCount()
            case 2:
                return this.mTabs.size()
            default:
                return 0
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode()
    }

    @Override // android.support.v7.app.ActionBar
    fun getSelectedNavigationIndex() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 2:
                if (this.mSelectedTab != null) {
                    break
                }
                break
        }
        return -1
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab
    }

    @Override // android.support.v7.app.ActionBar
    fun getSubtitle() {
        return this.mDecorToolbar.getSubtitle()
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getTabAt(Int i) {
        return (ActionBar.Tab) this.mTabs.get(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun getTabCount() {
        return this.mTabs.size()
    }

    @Override // android.support.v7.app.ActionBar
    fun getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = TypedValue()
            this.mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true)
            Int i = typedValue.resourceId
            if (i != 0) {
                this.mThemedContext = ContextThemeWrapper(this.mContext, i)
            } else {
                this.mThemedContext = this.mContext
            }
        }
        return this.mThemedContext
    }

    @Override // android.support.v7.app.ActionBar
    fun getTitle() {
        return this.mDecorToolbar.getTitle()
    }

    fun hasIcon() {
        return this.mDecorToolbar.hasIcon()
    }

    fun hasLogo() {
        return this.mDecorToolbar.hasLogo()
    }

    @Override // android.support.v7.app.ActionBar
    fun hide() {
        if (this.mHiddenByApp) {
            return
        }
        this.mHiddenByApp = true
        updateVisibility(false)
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun hideForSystem() {
        if (this.mHiddenBySystem) {
            return
        }
        this.mHiddenBySystem = true
        updateVisibility(true)
    }

    @Override // android.support.v7.app.ActionBar
    fun isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled()
    }

    @Override // android.support.v7.app.ActionBar
    fun isShowing() {
        Int height = getHeight()
        return this.mNowShowing && (height == 0 || getHideOffset() < height)
    }

    @Override // android.support.v7.app.ActionBar
    fun isTitleTruncated() {
        return this.mDecorToolbar != null && this.mDecorToolbar.isTitleTruncated()
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab newTab() {
        return TabImpl()
    }

    @Override // android.support.v7.app.ActionBar
    fun onConfigurationChanged(Configuration configuration) {
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs())
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun onContentScrollStarted() {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel()
            this.mCurrentShowAnim = null
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun onContentScrollStopped() {
    }

    @Override // android.support.v7.app.ActionBar
    fun onKeyShortcut(Int i, KeyEvent keyEvent) {
        Menu menu
        if (this.mActionMode == null || (menu = this.mActionMode.getMenu()) == null) {
            return false
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1)
        return menu.performShortcut(i, keyEvent, 0)
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun onWindowVisibilityChanged(Int i) {
        this.mCurWindowVisibility = i
    }

    @Override // android.support.v7.app.ActionBar
    fun removeAllTabs() {
        cleanupTabs()
    }

    @Override // android.support.v7.app.ActionBar
    fun removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener)
    }

    @Override // android.support.v7.app.ActionBar
    fun removeTab(ActionBar.Tab tab) {
        removeTabAt(tab.getPosition())
    }

    @Override // android.support.v7.app.ActionBar
    fun removeTabAt(Int i) {
        if (this.mTabScrollView == null) {
            return
        }
        Int position = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition
        this.mTabScrollView.removeTabAt(i)
        TabImpl tabImpl = (TabImpl) this.mTabs.remove(i)
        if (tabImpl != null) {
            tabImpl.setPosition(-1)
        }
        Int size = this.mTabs.size()
        for (Int i2 = i; i2 < size; i2++) {
            ((TabImpl) this.mTabs.get(i2)).setPosition(i2)
        }
        if (position == i) {
            selectTab(this.mTabs.isEmpty() ? null : (TabImpl) this.mTabs.get(Math.max(0, i - 1)))
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup()
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false
        }
        viewGroup.requestFocus()
        return true
    }

    @Override // android.support.v7.app.ActionBar
    fun selectTab(ActionBar.Tab tab) {
        if (getNavigationMode() != 2) {
            this.mSavedTabPosition = tab != null ? tab.getPosition() : -1
            return
        }
        FragmentTransaction fragmentTransactionDisallowAddToBackStack = (!(this.mActivity is FragmentActivity) || this.mDecorToolbar.getViewGroup().isInEditMode()) ? null : ((FragmentActivity) this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack()
        if (this.mSelectedTab != tab) {
            this.mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1)
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack)
            }
            this.mSelectedTab = (TabImpl) tab
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack)
            }
        } else if (this.mSelectedTab != null) {
            this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack)
            this.mTabScrollView.animateToTab(tab.getPosition())
        }
        if (fragmentTransactionDisallowAddToBackStack == null || fragmentTransactionDisallowAddToBackStack.isEmpty()) {
            return
        }
        fragmentTransactionDisallowAddToBackStack.commit()
    }

    @Override // android.support.v7.app.ActionBar
    fun setBackgroundDrawable(Drawable drawable) {
        this.mContainerView.setPrimaryBackground(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(Int i) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(i, this.mDecorToolbar.getViewGroup(), false))
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(View view) {
        this.mDecorToolbar.setCustomView(view)
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams)
        this.mDecorToolbar.setCustomView(view)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDefaultDisplayHomeAsUpEnabled(Boolean z) {
        if (this.mDisplayHomeAsUpSet) {
            return
        }
        setDisplayHomeAsUpEnabled(z)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayHomeAsUpEnabled(Boolean z) {
        setDisplayOptions(z ? 4 : 0, 4)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayOptions(Int i) {
        if ((i & 4) != 0) {
            this.mDisplayHomeAsUpSet = true
        }
        this.mDecorToolbar.setDisplayOptions(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayOptions(Int i, Int i2) {
        Int displayOptions = this.mDecorToolbar.getDisplayOptions()
        if ((i2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true
        }
        this.mDecorToolbar.setDisplayOptions((displayOptions & (i2 ^ (-1))) | (i & i2))
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowCustomEnabled(Boolean z) {
        setDisplayOptions(z ? 16 : 0, 16)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowHomeEnabled(Boolean z) {
        setDisplayOptions(z ? 2 : 0, 2)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowTitleEnabled(Boolean z) {
        setDisplayOptions(z ? 8 : 0, 8)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayUseLogoEnabled(Boolean z) {
        setDisplayOptions(z ? 1 : 0, 1)
    }

    @Override // android.support.v7.app.ActionBar
    fun setElevation(Float f) {
        ViewCompat.setElevation(this.mContainerView, f)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHideOffset(Int i) {
        if (i != 0 && !this.mOverlayLayout.isInOverlayMode()) {
            throw IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset")
        }
        this.mOverlayLayout.setActionBarHideOffset(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHideOnContentScrollEnabled(Boolean z) {
        if (z && !this.mOverlayLayout.isInOverlayMode()) {
            throw IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll")
        }
        this.mHideOnContentScroll = z
        this.mOverlayLayout.setHideOnContentScrollEnabled(z)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeActionContentDescription(Int i) {
        this.mDecorToolbar.setNavigationContentDescription(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeActionContentDescription(CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeAsUpIndicator(Int i) {
        this.mDecorToolbar.setNavigationIcon(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeAsUpIndicator(Drawable drawable) {
        this.mDecorToolbar.setNavigationIcon(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeButtonEnabled(Boolean z) {
        this.mDecorToolbar.setHomeButtonEnabled(z)
    }

    @Override // android.support.v7.app.ActionBar
    fun setIcon(Int i) {
        this.mDecorToolbar.setIcon(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setIcon(Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, NavItemSelectedListener(onNavigationListener))
    }

    @Override // android.support.v7.app.ActionBar
    fun setLogo(Int i) {
        this.mDecorToolbar.setLogo(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setLogo(Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setNavigationMode(Int i) {
        Int navigationMode = this.mDecorToolbar.getNavigationMode()
        switch (navigationMode) {
            case 2:
                this.mSavedTabPosition = getSelectedNavigationIndex()
                selectTab(null)
                this.mTabScrollView.setVisibility(8)
                break
        }
        if (navigationMode != i && !this.mHasEmbeddedTabs && this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets(this.mOverlayLayout)
        }
        this.mDecorToolbar.setNavigationMode(i)
        switch (i) {
            case 2:
                ensureTabsExist()
                this.mTabScrollView.setVisibility(0)
                if (this.mSavedTabPosition != -1) {
                    setSelectedNavigationItem(this.mSavedTabPosition)
                    this.mSavedTabPosition = -1
                    break
                }
                break
        }
        this.mDecorToolbar.setCollapsible(i == 2 && !this.mHasEmbeddedTabs)
        this.mOverlayLayout.setHasNonEmbeddedTabs(i == 2 && !this.mHasEmbeddedTabs)
    }

    @Override // android.support.v7.app.ActionBar
    fun setSelectedNavigationItem(Int i) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(i)
                return
            case 2:
                selectTab((ActionBar.Tab) this.mTabs.get(i))
                return
            default:
                throw IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode")
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun setShowHideAnimationEnabled(Boolean z) {
        this.mShowHideAnimationEnabled = z
        if (z || this.mCurrentShowAnim == null) {
            return
        }
        this.mCurrentShowAnim.cancel()
    }

    @Override // android.support.v7.app.ActionBar
    fun setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setStackedBackgroundDrawable(Drawable drawable) {
        this.mContainerView.setStackedBackground(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setSubtitle(Int i) {
        setSubtitle(this.mContext.getString(i))
    }

    @Override // android.support.v7.app.ActionBar
    fun setSubtitle(CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setTitle(Int i) {
        setTitle(this.mContext.getString(i))
    }

    @Override // android.support.v7.app.ActionBar
    fun setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false
            updateVisibility(false)
        }
    }

    @Override // android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    fun showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false
            updateVisibility(true)
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun startActionMode(ActionMode.Callback callback) {
        if (this.mActionMode != null) {
            this.mActionMode.finish()
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false)
        this.mContextView.killMode()
        ActionModeImpl actionModeImpl = ActionModeImpl(this.mContextView.getContext(), callback)
        if (!actionModeImpl.dispatchOnCreate()) {
            return null
        }
        this.mActionMode = actionModeImpl
        actionModeImpl.invalidate()
        this.mContextView.initForMode(actionModeImpl)
        animateToMode(true)
        this.mContextView.sendAccessibilityEvent(32)
        return actionModeImpl
    }
}
