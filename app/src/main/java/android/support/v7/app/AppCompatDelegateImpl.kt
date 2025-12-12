package android.support.v7.app

import android.app.Activity
import android.app.Dialog
import android.app.UiModeManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.VisibleForTesting
import android.support.v4.app.NavUtils
import android.support.v4.view.KeyEventDispatcher
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.OnApplyWindowInsetsListener
import android.support.v4.view.PointerIconCompat
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.WindowInsetsCompat
import android.support.v4.widget.PopupWindowCompat
import android.support.v7.app.ActionBarDrawerToggle
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.ActionMode
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.view.StandaloneActionMode
import android.support.v7.view.SupportActionModeWrapper
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.WindowCallbackWrapper
import android.support.v7.view.menu.ListMenuPresenter
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.ActionBarContextView
import android.support.v7.widget.AppCompatDrawableManager
import android.support.v7.widget.ContentFrameLayout
import android.support.v7.widget.DecorContentParent
import android.support.v7.widget.FitWindowsViewGroup
import android.support.v7.widget.TintTypedArray
import androidx.appcompat.widget.Toolbar
import android.support.v7.widget.VectorEnabledTintResources
import android.support.v7.widget.ViewStubCompat
import android.support.v7.widget.ViewUtils
import android.text.TextUtils
import android.util.AndroidRuntimeException
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.ActionMode
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewParent
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import java.lang.Thread
import java.lang.reflect.InvocationTargetException
import java.util.List
import org.xmlpull.v1.XmlPullParser

class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    private static val DEBUG = false
    static val EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info."
    private static final Boolean IS_PRE_LOLLIPOP
    private static val KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode"
    private static Boolean sInstalledExceptionHandler
    private static final Array<Int> sWindowBackgroundStyleable
    ActionBar mActionBar
    private ActionMenuPresenterCallback mActionMenuPresenterCallback
    ActionMode mActionMode
    PopupWindow mActionModePopup
    ActionBarContextView mActionModeView
    final AppCompatCallback mAppCompatCallback
    private AppCompatViewInflater mAppCompatViewInflater
    final Window.Callback mAppCompatWindowCallback
    private Boolean mApplyDayNightCalled
    private AutoNightModeManager mAutoNightModeManager
    private Boolean mClosingActionMenu
    final Context mContext
    private DecorContentParent mDecorContentParent
    private Boolean mEnableDefaultActionBarUp
    private Boolean mFeatureIndeterminateProgress
    private Boolean mFeatureProgress
    Boolean mHasActionBar
    Int mInvalidatePanelMenuFeatures
    Boolean mInvalidatePanelMenuPosted
    Boolean mIsDestroyed
    Boolean mIsFloating
    private Boolean mLongPressBackDown
    MenuInflater mMenuInflater
    final Window.Callback mOriginalWindowCallback
    Boolean mOverlayActionBar
    Boolean mOverlayActionMode
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback
    private Array<PanelFeatureState> mPanels
    private PanelFeatureState mPreparedPanel
    Runnable mShowActionModePopup
    private View mStatusGuard
    private ViewGroup mSubDecor
    private Boolean mSubDecorInstalled
    private Rect mTempRect1
    private Rect mTempRect2
    private CharSequence mTitle
    private TextView mTitleView
    final Window mWindow
    Boolean mWindowNoTitle
    ViewPropertyAnimatorCompat mFadeAnim = null
    private Boolean mHandleNativeActionModes = true
    private Int mLocalNightMode = -100
    private val mInvalidatePanelMenuRunnable = Runnable() { // from class: android.support.v7.app.AppCompatDelegateImpl.2
        @Override // java.lang.Runnable
        fun run() {
            if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                AppCompatDelegateImpl.this.doInvalidatePanelMenu(0)
            }
            if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
                AppCompatDelegateImpl.this.doInvalidatePanelMenu(108)
            }
            AppCompatDelegateImpl.this.mInvalidatePanelMenuPosted = false
            AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures = 0
        }
    }

    class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.getActionBarThemedContext()
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getThemeUpIndicator() {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new Array<Int>{R.attr.homeAsUpIndicator})
            Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(0)
            tintTypedArrayObtainStyledAttributes.recycle()
            return drawable
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar()
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarDescription(Int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar()
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i)
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarUpIndicator(Drawable drawable, Int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar()
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable)
                supportActionBar.setHomeActionContentDescription(i)
            }
        }
    }

    final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder)
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback = AppCompatDelegateImpl.this.getWindowCallback()
            if (windowCallback == null) {
                return true
            }
            windowCallback.onMenuOpened(108, menuBuilder)
            return true
        }
    }

    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped

        constructor(ActionMode.Callback callback) {
            this.mWrapped = callback
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem)
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu)
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode)
            if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup)
            }
            if (AppCompatDelegateImpl.this.mActionModeView != null) {
                AppCompatDelegateImpl.this.endOnGoingFadeAnimation()
                AppCompatDelegateImpl.this.mFadeAnim = ViewCompat.animate(AppCompatDelegateImpl.this.mActionModeView).alpha(0.0f)
                AppCompatDelegateImpl.this.mFadeAnim.setListener(ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                    fun onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8)
                        if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                            AppCompatDelegateImpl.this.mActionModePopup.dismiss()
                        } else if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent())
                        }
                        AppCompatDelegateImpl.this.mActionModeView.removeAllViews()
                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null)
                        AppCompatDelegateImpl.this.mFadeAnim = null
                    }
                })
            }
            if (AppCompatDelegateImpl.this.mAppCompatCallback != null) {
                AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode)
            }
            AppCompatDelegateImpl.this.mActionMode = null
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu)
        }
    }

    class AppCompatWindowCallback extends WindowCallbackWrapper {
        AppCompatWindowCallback(Window.Callback callback) {
            super(callback)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onContentChanged() {
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onCreatePanelMenu(Int i, Menu menu) {
            if (i != 0 || (menu is MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu)
            }
            return false
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onMenuOpened(Int i, Menu menu) {
            super.onMenuOpened(i, menu)
            AppCompatDelegateImpl.this.onMenuOpened(i)
            return true
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onPanelClosed(Int i, Menu menu) {
            super.onPanelClosed(i, menu)
            AppCompatDelegateImpl.this.onPanelClosed(i)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onPreparePanel(Int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu is MenuBuilder ? (MenuBuilder) menu : null
            if (i == 0 && menuBuilder == null) {
                return false
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true)
            }
            Boolean zOnPreparePanel = super.onPreparePanel(i, view, menu)
            if (menuBuilder == null) {
                return zOnPreparePanel
            }
            menuBuilder.setOverrideVisibleItems(false)
            return zOnPreparePanel
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(24)
        fun onProvideKeyboardShortcuts(List list, Menu menu, Int i) {
            PanelFeatureState panelState = AppCompatDelegateImpl.this.getPanelState(0, true)
            if (panelState == null || panelState.menu == null) {
                super.onProvideKeyboardShortcuts(list, menu, i)
            } else {
                super.onProvideKeyboardShortcuts(list, panelState.menu, i)
            }
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null
            }
            return AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() ? startAsSupportActionMode(callback) : super.onWindowStartingActionMode(callback)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, Int i) {
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                switch (i) {
                    case 0:
                        return startAsSupportActionMode(callback)
                }
            }
            return super.onWindowStartingActionMode(callback, i)
        }

        final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, callback)
            android.support.v7.view.ActionMode actionModeStartSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper)
            if (actionModeStartSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(actionModeStartSupportActionMode)
            }
            return null
        }
    }

    @VisibleForTesting
    final class AutoNightModeManager {
        private BroadcastReceiver mAutoTimeChangeReceiver
        private IntentFilter mAutoTimeChangeReceiverFilter
        private Boolean mIsNight
        private TwilightManager mTwilightManager

        AutoNightModeManager(TwilightManager twilightManager) {
            this.mTwilightManager = twilightManager
            this.mIsNight = twilightManager.isNight()
        }

        final Unit cleanup() {
            if (this.mAutoTimeChangeReceiver != null) {
                AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver)
                this.mAutoTimeChangeReceiver = null
            }
        }

        final Unit dispatchTimeChanged() {
            Boolean zIsNight = this.mTwilightManager.isNight()
            if (zIsNight != this.mIsNight) {
                this.mIsNight = zIsNight
                AppCompatDelegateImpl.this.applyDayNight()
            }
        }

        final Int getApplyableNightMode() {
            this.mIsNight = this.mTwilightManager.isNight()
            return this.mIsNight ? 2 : 1
        }

        final Unit setup() {
            cleanup()
            if (this.mAutoTimeChangeReceiver == null) {
                this.mAutoTimeChangeReceiver = BroadcastReceiver() { // from class: android.support.v7.app.AppCompatDelegateImpl.AutoNightModeManager.1
                    @Override // android.content.BroadcastReceiver
                    fun onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.dispatchTimeChanged()
                    }
                }
            }
            if (this.mAutoTimeChangeReceiverFilter == null) {
                this.mAutoTimeChangeReceiverFilter = IntentFilter()
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET")
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED")
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK")
            }
            AppCompatDelegateImpl.this.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter)
        }
    }

    class ListMenuDecorView extends ContentFrameLayout {
        constructor(Context context) {
            super(context)
        }

        private fun isOutOfBounds(Int i, Int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5
        }

        @Override // android.view.ViewGroup, android.view.View
        fun dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)
        }

        @Override // android.view.ViewGroup
        fun onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !isOutOfBounds((Int) motionEvent.getX(), (Int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent)
            }
            AppCompatDelegateImpl.this.closePanel(0)
            return true
        }

        @Override // android.view.View
        fun setBackgroundResource(Int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i))
        }
    }

    class PanelFeatureState {
        Int background
        View createdPanelView
        ViewGroup decorView
        Int featureId
        Bundle frozenActionViewState
        Bundle frozenMenuState
        Int gravity
        Boolean isHandled
        Boolean isOpen
        Boolean isPrepared
        ListMenuPresenter listMenuPresenter
        Context listPresenterContext
        MenuBuilder menu
        public Boolean qwertyMode
        Boolean refreshDecorView = false
        Boolean refreshMenuContent
        View shownPanelView
        Boolean wasLastOpen
        Int windowAnimations
        Int x
        Int y

        class SavedState implements Parcelable {
            public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v7.app.AppCompatDelegateImpl.PanelFeatureState.SavedState.1
                @Override // android.os.Parcelable.Creator
                public final SavedState createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel, null)
                }

                @Override // android.os.Parcelable.ClassLoaderCreator
                public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.readFromParcel(parcel, classLoader)
                }

                @Override // android.os.Parcelable.Creator
                public final Array<SavedState> newArray(Int i) {
                    return new SavedState[i]
                }
            }
            Int featureId
            Boolean isOpen
            Bundle menuState

            SavedState() {
            }

            static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = SavedState()
                savedState.featureId = parcel.readInt()
                savedState.isOpen = parcel.readInt() == 1
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle(classLoader)
                }
                return savedState
            }

            @Override // android.os.Parcelable
            fun describeContents() {
                return 0
            }

            @Override // android.os.Parcelable
            fun writeToParcel(Parcel parcel, Int i) {
                parcel.writeInt(this.featureId)
                parcel.writeInt(this.isOpen ? 1 : 0)
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState)
                }
            }
        }

        PanelFeatureState(Int i) {
            this.featureId = i
        }

        final Unit applyFrozenState() {
            if (this.menu == null || this.frozenMenuState == null) {
                return
            }
            this.menu.restorePresenterStates(this.frozenMenuState)
            this.frozenMenuState = null
        }

        public final Unit clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter)
            }
            this.listMenuPresenter = null
        }

        final MenuView getListMenuView(MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout)
                this.listMenuPresenter.setCallback(callback)
                this.menu.addMenuPresenter(this.listMenuPresenter)
            }
            return this.listMenuPresenter.getMenuView(this.decorView)
        }

        public final Boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false
            }
            return this.createdPanelView != null || this.listMenuPresenter.getAdapter().getCount() > 0
        }

        final Unit onRestoreInstanceState(Parcelable parcelable) {
            SavedState savedState = (SavedState) parcelable
            this.featureId = savedState.featureId
            this.wasLastOpen = savedState.isOpen
            this.frozenMenuState = savedState.menuState
            this.shownPanelView = null
            this.decorView = null
        }

        final Parcelable onSaveInstanceState() {
            SavedState savedState = SavedState()
            savedState.featureId = this.featureId
            savedState.isOpen = this.isOpen
            if (this.menu != null) {
                savedState.menuState = Bundle()
                this.menu.savePresenterStates(savedState.menuState)
            }
            return savedState
        }

        final Unit setMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == this.menu) {
                return
            }
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter)
            }
            this.menu = menuBuilder
            if (menuBuilder == null || this.listMenuPresenter == null) {
                return
            }
            menuBuilder.addMenuPresenter(this.listMenuPresenter)
        }

        final Unit setStyle(Context context) {
            TypedValue typedValue = TypedValue()
            Resources.Theme themeNewTheme = context.getResources().newTheme()
            themeNewTheme.setTo(context.getTheme())
            themeNewTheme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true)
            if (typedValue.resourceId != 0) {
                themeNewTheme.applyStyle(typedValue.resourceId, true)
            }
            themeNewTheme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true)
            if (typedValue.resourceId != 0) {
                themeNewTheme.applyStyle(typedValue.resourceId, true)
            } else {
                themeNewTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true)
            }
            ContextThemeWrapper contextThemeWrapper = ContextThemeWrapper(context, 0)
            contextThemeWrapper.getTheme().setTo(themeNewTheme)
            this.listPresenterContext = contextThemeWrapper
            TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme)
            this.background = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0)
            this.windowAnimations = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0)
            typedArrayObtainStyledAttributes.recycle()
        }
    }

    final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu()
            Boolean z2 = rootMenu != menuBuilder
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this
            if (z2) {
                menuBuilder = rootMenu
            }
            PanelFeatureState panelFeatureStateFindMenuPanel = appCompatDelegateImpl.findMenuPanel(menuBuilder)
            if (panelFeatureStateFindMenuPanel != null) {
                if (!z2) {
                    AppCompatDelegateImpl.this.closePanel(panelFeatureStateFindMenuPanel, z)
                } else {
                    AppCompatDelegateImpl.this.callOnPanelClosed(panelFeatureStateFindMenuPanel.featureId, panelFeatureStateFindMenuPanel, rootMenu)
                    AppCompatDelegateImpl.this.closePanel(panelFeatureStateFindMenuPanel, true)
                }
            }
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback
            if (menuBuilder != null || !AppCompatDelegateImpl.this.mHasActionBar || (windowCallback = AppCompatDelegateImpl.this.getWindowCallback()) == null || AppCompatDelegateImpl.this.mIsDestroyed) {
                return true
            }
            windowCallback.onMenuOpened(108, menuBuilder)
            return true
        }
    }

    static {
        IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21
        sWindowBackgroundStyleable = new Array<Int>{android.R.attr.windowBackground}
        if (!IS_PRE_LOLLIPOP || sInstalledExceptionHandler) {
            return
        }
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: android.support.v7.app.AppCompatDelegateImpl.1
            private fun shouldWrapException(Throwable th) {
                String message
                if (!(th is Resources.NotFoundException) || (message = th.getMessage()) == null) {
                    return false
                }
                return message.contains("drawable") || message.contains("Drawable")
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final Unit uncaughtException(Thread thread, Throwable th) {
                if (!shouldWrapException(th)) {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th)
                    return
                }
                Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + AppCompatDelegateImpl.EXCEPTION_HANDLER_MESSAGE_SUFFIX)
                notFoundException.initCause(th.getCause())
                notFoundException.setStackTrace(th.getStackTrace())
                defaultUncaughtExceptionHandler.uncaughtException(thread, notFoundException)
            }
        })
        sInstalledExceptionHandler = true
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this.mContext = context
        this.mWindow = window
        this.mAppCompatCallback = appCompatCallback
        this.mOriginalWindowCallback = this.mWindow.getCallback()
        if (this.mOriginalWindowCallback is AppCompatWindowCallback) {
            throw IllegalStateException("AppCompat has already installed itself into the Window")
        }
        this.mAppCompatWindowCallback = AppCompatWindowCallback(this.mOriginalWindowCallback)
        this.mWindow.setCallback(this.mAppCompatWindowCallback)
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, sWindowBackgroundStyleable)
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0)
        if (drawableIfKnown != null) {
            this.mWindow.setBackgroundDrawable(drawableIfKnown)
        }
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    private fun applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.mSubDecor.findViewById(android.R.id.content)
        View decorView = this.mWindow.getDecorView()
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom())
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme)
        typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor())
        typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor())
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor())
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor())
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor())
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor())
        }
        typedArrayObtainStyledAttributes.recycle()
        contentFrameLayout.requestLayout()
    }

    private fun createSubDecor() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewGroup viewGroup
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme)
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle()
            throw IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.")
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1)
        } else if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108)
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109)
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10)
        }
        this.mIsFloating = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false)
        typedArrayObtainStyledAttributes.recycle()
        this.mWindow.getDecorView()
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext)
        if (this.mWindowNoTitle) {
            ViewGroup viewGroup2 = this.mOverlayActionMode ? (ViewGroup) layoutInflaterFrom.inflate(R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(R.layout.abc_screen_simple, (ViewGroup) null)
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.setOnApplyWindowInsetsListener(viewGroup2, OnApplyWindowInsetsListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.3
                    @Override // android.support.v4.view.OnApplyWindowInsetsListener
                    fun onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        Int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop()
                        Int iUpdateStatusGuard = AppCompatDelegateImpl.this.updateStatusGuard(systemWindowInsetTop)
                        if (systemWindowInsetTop != iUpdateStatusGuard) {
                            windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), iUpdateStatusGuard, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom())
                        }
                        return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat)
                    }
                })
                viewGroup = viewGroup2
            } else {
                ((FitWindowsViewGroup) viewGroup2).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.4
                    @Override // android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener
                    fun onFitSystemWindows(Rect rect) {
                        rect.top = AppCompatDelegateImpl.this.updateStatusGuard(rect.top)
                    }
                })
                viewGroup = viewGroup2
            }
        } else if (this.mIsFloating) {
            ViewGroup viewGroup3 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.abc_dialog_title_material, (ViewGroup) null)
            this.mOverlayActionBar = false
            this.mHasActionBar = false
            viewGroup = viewGroup3
        } else if (this.mHasActionBar) {
            TypedValue typedValue = TypedValue()
            this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true)
            ViewGroup viewGroup4 = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? ContextThemeWrapper(this.mContext, typedValue.resourceId) : this.mContext).inflate(R.layout.abc_screen_toolbar, (ViewGroup) null)
            this.mDecorContentParent = (DecorContentParent) viewGroup4.findViewById(R.id.decor_content_parent)
            this.mDecorContentParent.setWindowCallback(getWindowCallback())
            if (this.mOverlayActionBar) {
                this.mDecorContentParent.initFeature(109)
            }
            if (this.mFeatureProgress) {
                this.mDecorContentParent.initFeature(2)
            }
            if (this.mFeatureIndeterminateProgress) {
                this.mDecorContentParent.initFeature(5)
            }
            viewGroup = viewGroup4
        } else {
            viewGroup = null
        }
        if (viewGroup == null) {
            throw IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }")
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(R.id.title)
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup)
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R.id.action_bar_activity_content)
        ViewGroup viewGroup5 = (ViewGroup) this.mWindow.findViewById(android.R.id.content)
        if (viewGroup5 != null) {
            while (viewGroup5.getChildCount() > 0) {
                View childAt = viewGroup5.getChildAt(0)
                viewGroup5.removeViewAt(0)
                contentFrameLayout.addView(childAt)
            }
            viewGroup5.setId(-1)
            contentFrameLayout.setId(android.R.id.content)
            if (viewGroup5 is FrameLayout) {
                ((FrameLayout) viewGroup5).setForeground(null)
            }
        }
        this.mWindow.setContentView(viewGroup)
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.5
            @Override // android.support.v7.widget.ContentFrameLayout.OnAttachListener
            fun onAttachedFromWindow() {
            }

            @Override // android.support.v7.widget.ContentFrameLayout.OnAttachListener
            fun onDetachedFromWindow() {
                AppCompatDelegateImpl.this.dismissPopups()
            }
        })
        return viewGroup
    }

    private fun ensureAutoNightModeManager() {
        if (this.mAutoNightModeManager == null) {
            this.mAutoNightModeManager = AutoNightModeManager(TwilightManager.getInstance(this.mContext))
        }
    }

    private fun ensureSubDecor() {
        if (this.mSubDecorInstalled) {
            return
        }
        this.mSubDecor = createSubDecor()
        CharSequence title = getTitle()
        if (!TextUtils.isEmpty(title)) {
            if (this.mDecorContentParent != null) {
                this.mDecorContentParent.setWindowTitle(title)
            } else if (peekSupportActionBar() != null) {
                peekSupportActionBar().setWindowTitle(title)
            } else if (this.mTitleView != null) {
                this.mTitleView.setText(title)
            }
        }
        applyFixedSizeWindow()
        onSubDecorInstalled(this.mSubDecor)
        this.mSubDecorInstalled = true
        PanelFeatureState panelState = getPanelState(0, false)
        if (this.mIsDestroyed) {
            return
        }
        if (panelState == null || panelState.menu == null) {
            invalidatePanelMenu(108)
        }
    }

    private fun getNightMode() {
        return this.mLocalNightMode != -100 ? this.mLocalNightMode : getDefaultNightMode()
    }

    private fun initWindowDecorActionBar() {
        ensureSubDecor()
        if (this.mHasActionBar && this.mActionBar == null) {
            if (this.mOriginalWindowCallback is Activity) {
                this.mActionBar = WindowDecorActionBar((Activity) this.mOriginalWindowCallback, this.mOverlayActionBar)
            } else if (this.mOriginalWindowCallback is Dialog) {
                this.mActionBar = WindowDecorActionBar((Dialog) this.mOriginalWindowCallback)
            }
            if (this.mActionBar != null) {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp)
            }
        }
    }

    private fun initializePanelContent(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView
            return true
        }
        if (panelFeatureState.menu == null) {
            return false
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = PanelMenuPresenterCallback()
        }
        panelFeatureState.shownPanelView = (View) panelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback)
        return panelFeatureState.shownPanelView != null
    }

    private fun initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.setStyle(getActionBarThemedContext())
        panelFeatureState.decorView = ListMenuDecorView(panelFeatureState.listPresenterContext)
        panelFeatureState.gravity = 81
        return true
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun initializePanelMenu(android.support.v7.app.AppCompatDelegateImpl.PanelFeatureState r7) {
        /*
            r6 = this
            r5 = 1
            android.content.Context r1 = r6.mContext
            Int r0 = r7.featureId
            if (r0 == 0) goto Ld
            Int r0 = r7.featureId
            r2 = 108(0x6c, Float:1.51E-43)
            if (r0 != r2) goto L71
        Ld:
            android.support.v7.widget.DecorContentParent r0 = r6.mDecorContentParent
            if (r0 == 0) goto L71
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            android.content.res.Resources$Theme r3 = r1.getTheme()
            Int r0 = android.support.v7.appcompat.R.attr.actionBarTheme
            r3.resolveAttribute(r0, r2, r5)
            r0 = 0
            Int r4 = r2.resourceId
            if (r4 == 0) goto L6b
            android.content.res.Resources r0 = r1.getResources()
            android.content.res.Resources$Theme r0 = r0.newTheme()
            r0.setTo(r3)
            Int r4 = r2.resourceId
            r0.applyStyle(r4, r5)
            Int r4 = android.support.v7.appcompat.R.attr.actionBarWidgetTheme
            r0.resolveAttribute(r4, r2, r5)
        L39:
            Int r4 = r2.resourceId
            if (r4 == 0) goto L4f
            if (r0 != 0) goto L4a
            android.content.res.Resources r0 = r1.getResources()
            android.content.res.Resources$Theme r0 = r0.newTheme()
            r0.setTo(r3)
        L4a:
            Int r2 = r2.resourceId
            r0.applyStyle(r2, r5)
        L4f:
            r2 = r0
            if (r2 == 0) goto L71
            android.support.v7.view.ContextThemeWrapper r0 = new android.support.v7.view.ContextThemeWrapper
            r3 = 0
            r0.<init>(r1, r3)
            android.content.res.Resources$Theme r1 = r0.getTheme()
            r1.setTo(r2)
        L5f:
            android.support.v7.view.menu.MenuBuilder r1 = new android.support.v7.view.menu.MenuBuilder
            r1.<init>(r0)
            r1.setCallback(r6)
            r7.setMenu(r1)
            return r5
        L6b:
            Int r4 = android.support.v7.appcompat.R.attr.actionBarWidgetTheme
            r3.resolveAttribute(r4, r2, r5)
            goto L39
        L71:
            r0 = r1
            goto L5f
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImpl.initializePanelMenu(android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState):Boolean")
    }

    private fun invalidatePanelMenu(Int i) {
        this.mInvalidatePanelMenuFeatures |= 1 << i
        if (this.mInvalidatePanelMenuPosted) {
            return
        }
        ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable)
        this.mInvalidatePanelMenuPosted = true
    }

    private fun onKeyDownPanel(Int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState panelState = getPanelState(i, true)
            if (!panelState.isOpen) {
                return preparePanel(panelState, keyEvent)
            }
        }
        return false
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun onKeyUpPanel(Int r5, android.view.KeyEvent r6) {
        /*
            r4 = this
            r2 = 1
            r1 = 0
            android.support.v7.view.ActionMode r0 = r4.mActionMode
            if (r0 == 0) goto L8
            r0 = r1
        L7:
            return r0
        L8:
            android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState r3 = r4.getPanelState(r5, r2)
            if (r5 != 0) goto L58
            android.support.v7.widget.DecorContentParent r0 = r4.mDecorContentParent
            if (r0 == 0) goto L58
            android.support.v7.widget.DecorContentParent r0 = r4.mDecorContentParent
            Boolean r0 = r0.canShowOverflowMenu()
            if (r0 == 0) goto L58
            android.content.Context r0 = r4.mContext
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            Boolean r0 = r0.hasPermanentMenuKey()
            if (r0 != 0) goto L58
            android.support.v7.widget.DecorContentParent r0 = r4.mDecorContentParent
            Boolean r0 = r0.isOverflowMenuShowing()
            if (r0 != 0) goto L51
            Boolean r0 = r4.mIsDestroyed
            if (r0 != 0) goto L83
            Boolean r0 = r4.preparePanel(r3, r6)
            if (r0 == 0) goto L83
            android.support.v7.widget.DecorContentParent r0 = r4.mDecorContentParent
            Boolean r2 = r0.showOverflowMenu()
        L3e:
            if (r2 == 0) goto L4f
            android.content.Context r0 = r4.mContext
            java.lang.String r3 = "audio"
            java.lang.Object r0 = r0.getSystemService(r3)
            android.media.AudioManager r0 = (android.media.AudioManager) r0
            if (r0 == 0) goto L7b
            r0.playSoundEffect(r1)
        L4f:
            r0 = r2
            goto L7
        L51:
            android.support.v7.widget.DecorContentParent r0 = r4.mDecorContentParent
            Boolean r2 = r0.hideOverflowMenu()
            goto L3e
        L58:
            Boolean r0 = r3.isOpen
            if (r0 != 0) goto L60
            Boolean r0 = r3.isHandled
            if (r0 == 0) goto L67
        L60:
            Boolean r0 = r3.isOpen
            r4.closePanel(r3, r2)
            r2 = r0
            goto L3e
        L67:
            Boolean r0 = r3.isPrepared
            if (r0 == 0) goto L83
            Boolean r0 = r3.refreshMenuContent
            if (r0 == 0) goto L85
            r3.isPrepared = r1
            Boolean r0 = r4.preparePanel(r3, r6)
        L75:
            if (r0 == 0) goto L83
            r4.openPanel(r3, r6)
            goto L3e
        L7b:
            java.lang.String r0 = "AppCompatDelegate"
            java.lang.String r1 = "Couldn't get audio manager"
            android.util.Log.w(r0, r1)
            goto L4f
        L83:
            r2 = r1
            goto L3e
        L85:
            r0 = r2
            goto L75
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImpl.onKeyUpPanel(Int, android.view.KeyEvent):Boolean")
    }

    private fun openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        ViewGroup.LayoutParams layoutParams
        Int i = -1
        if (panelFeatureState.isOpen || this.mIsDestroyed) {
            return
        }
        if (panelFeatureState.featureId == 0) {
            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                return
            }
        }
        Window.Callback windowCallback = getWindowCallback()
        if (windowCallback != null && !windowCallback.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true)
            return
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window")
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return
        }
        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
            if (panelFeatureState.decorView == null) {
                if (!initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                    return
                }
            } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews()
            }
            if (!initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                return
            }
            ViewGroup.LayoutParams layoutParams2 = panelFeatureState.shownPanelView.getLayoutParams()
            ViewGroup.LayoutParams layoutParams3 = layoutParams2 == null ? new ViewGroup.LayoutParams(-2, -2) : layoutParams2
            panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background)
            ViewParent parent = panelFeatureState.shownPanelView.getParent()
            if (parent != null && (parent is ViewGroup)) {
                ((ViewGroup) parent).removeView(panelFeatureState.shownPanelView)
            }
            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams3)
            if (!panelFeatureState.shownPanelView.hasFocus()) {
                panelFeatureState.shownPanelView.requestFocus()
            }
            i = -2
        } else if (panelFeatureState.createdPanelView == null || (layoutParams = panelFeatureState.createdPanelView.getLayoutParams()) == null || layoutParams.width != -1) {
            i = -2
        }
        panelFeatureState.isHandled = false
        WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams(i, -2, panelFeatureState.x, panelFeatureState.y, PointerIconCompat.TYPE_HAND, 8519680, -3)
        layoutParams4.gravity = panelFeatureState.gravity
        layoutParams4.windowAnimations = panelFeatureState.windowAnimations
        windowManager.addView(panelFeatureState.decorView, layoutParams4)
        panelFeatureState.isOpen = true
    }

    private fun performPanelShortcut(PanelFeatureState panelFeatureState, Int i, KeyEvent keyEvent, Int i2) {
        Boolean zPerformShortcut = false
        if (!keyEvent.isSystem()) {
            if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && panelFeatureState.menu != null) {
                zPerformShortcut = panelFeatureState.menu.performShortcut(i, keyEvent, i2)
            }
            if (zPerformShortcut && (i2 & 1) == 0 && this.mDecorContentParent == null) {
                closePanel(panelFeatureState, true)
            }
        }
        return zPerformShortcut
    }

    private fun preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        if (this.mIsDestroyed) {
            return false
        }
        if (panelFeatureState.isPrepared) {
            return true
        }
        if (this.mPreparedPanel != null && this.mPreparedPanel != panelFeatureState) {
            closePanel(this.mPreparedPanel, false)
        }
        Window.Callback windowCallback = getWindowCallback()
        if (windowCallback != null) {
            panelFeatureState.createdPanelView = windowCallback.onCreatePanelView(panelFeatureState.featureId)
        }
        Boolean z = panelFeatureState.featureId == 0 || panelFeatureState.featureId == 108
        if (z && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared()
        }
        if (panelFeatureState.createdPanelView == null && (!z || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
            if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent) {
                if (panelFeatureState.menu == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.menu == null)) {
                    return false
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = ActionMenuPresenterCallback()
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback)
                }
                panelFeatureState.menu.stopDispatchingItemsChanged()
                if (!windowCallback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    panelFeatureState.setMenu(null)
                    if (!z || this.mDecorContentParent == null) {
                        return false
                    }
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback)
                    return false
                }
                panelFeatureState.refreshMenuContent = false
            }
            panelFeatureState.menu.stopDispatchingItemsChanged()
            if (panelFeatureState.frozenActionViewState != null) {
                panelFeatureState.menu.restoreActionViewStates(panelFeatureState.frozenActionViewState)
                panelFeatureState.frozenActionViewState = null
            }
            if (!windowCallback.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback)
                }
                panelFeatureState.menu.startDispatchingItemsChanged()
                return false
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode)
            panelFeatureState.menu.startDispatchingItemsChanged()
        }
        panelFeatureState.isPrepared = true
        panelFeatureState.isHandled = false
        this.mPreparedPanel = panelFeatureState
        return true
    }

    private fun reopenMenu(MenuBuilder menuBuilder, Boolean z) {
        if (this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState panelState = getPanelState(0, true)
            panelState.refreshDecorView = true
            closePanel(panelState, false)
            openPanel(panelState, null)
            return
        }
        Window.Callback windowCallback = getWindowCallback()
        if (this.mDecorContentParent.isOverflowMenuShowing() && z) {
            this.mDecorContentParent.hideOverflowMenu()
            if (this.mIsDestroyed) {
                return
            }
            windowCallback.onPanelClosed(108, getPanelState(0, true).menu)
            return
        }
        if (windowCallback == null || this.mIsDestroyed) {
            return
        }
        if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable)
            this.mInvalidatePanelMenuRunnable.run()
        }
        PanelFeatureState panelState2 = getPanelState(0, true)
        if (panelState2.menu == null || panelState2.refreshMenuContent || !windowCallback.onPreparePanel(0, panelState2.createdPanelView, panelState2.menu)) {
            return
        }
        windowCallback.onMenuOpened(108, panelState2.menu)
        this.mDecorContentParent.showOverflowMenu()
    }

    private fun sanitizeWindowFeatureId(Int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.")
            return 108
        }
        if (i != 9) {
            return i
        }
        Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.")
        return 109
    }

    private fun shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false
        }
        View decorView = this.mWindow.getDecorView()
        for (ViewParent parent = viewParent; parent != null; parent = parent.getParent()) {
            if (parent == decorView || !(parent is View) || ViewCompat.isAttachedToWindow((View) parent)) {
                return false
            }
        }
        return true
    }

    private fun shouldRecreateOnNightModeChange() {
        if (!this.mApplyDayNightCalled || !(this.mContext is Activity)) {
            return false
        }
        try {
            return (this.mContext.getPackageManager().getActivityInfo(ComponentName(this.mContext, this.mContext.getClass()), 0).configChanges & 512) == 0
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e)
            return true
        }
    }

    private fun throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw AndroidRuntimeException("Window feature must be requested before adding content")
        }
    }

    private fun updateForNightMode(Int i) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Resources resources = this.mContext.getResources()
        Configuration configuration = resources.getConfiguration()
        Int i2 = configuration.uiMode & 48
        Int i3 = i == 2 ? 32 : 16
        if (i2 == i3) {
            return false
        }
        if (shouldRecreateOnNightModeChange()) {
            ((Activity) this.mContext).recreate()
        } else {
            Configuration configuration2 = Configuration(configuration)
            DisplayMetrics displayMetrics = resources.getDisplayMetrics()
            configuration2.uiMode = i3 | (configuration2.uiMode & (-49))
            resources.updateConfiguration(configuration2, displayMetrics)
            if (Build.VERSION.SDK_INT < 26) {
                ResourcesFlusher.flush(resources)
            }
        }
        return true
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor()
        ((ViewGroup) this.mSubDecor.findViewById(android.R.id.content)).addView(view, layoutParams)
        this.mOriginalWindowCallback.onContentChanged()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun applyDayNight() {
        Int nightMode = getNightMode()
        Int iMapNightMode = mapNightMode(nightMode)
        Boolean zUpdateForNightMode = iMapNightMode != -1 ? updateForNightMode(iMapNightMode) : false
        if (nightMode == 0) {
            ensureAutoNightModeManager()
            this.mAutoNightModeManager.setup()
        }
        this.mApplyDayNightCalled = true
        return zUpdateForNightMode
    }

    Unit callOnPanelClosed(Int i, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i >= 0 && i < this.mPanels.length) {
                panelFeatureState = this.mPanels[i]
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.menu
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !this.mIsDestroyed) {
            this.mOriginalWindowCallback.onPanelClosed(i, menu)
        }
    }

    Unit checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return
        }
        this.mClosingActionMenu = true
        this.mDecorContentParent.dismissPopups()
        Window.Callback windowCallback = getWindowCallback()
        if (windowCallback != null && !this.mIsDestroyed) {
            windowCallback.onPanelClosed(108, menuBuilder)
        }
        this.mClosingActionMenu = false
    }

    Unit closePanel(Int i) {
        closePanel(getPanelState(i, true), true)
    }

    Unit closePanel(PanelFeatureState panelFeatureState, Boolean z) {
        if (z && panelFeatureState.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(panelFeatureState.menu)
            return
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window")
        if (windowManager != null && panelFeatureState.isOpen && panelFeatureState.decorView != null) {
            windowManager.removeView(panelFeatureState.decorView)
            if (z) {
                callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null)
            }
        }
        panelFeatureState.isPrepared = false
        panelFeatureState.isHandled = false
        panelFeatureState.isOpen = false
        panelFeatureState.shownPanelView = null
        panelFeatureState.refreshDecorView = true
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.app.AppCompatDelegate
    fun createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        Boolean zShouldInheritContext
        if (this.mAppCompatViewInflater == null) {
            String string = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass)
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                this.mAppCompatViewInflater = AppCompatViewInflater()
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th)
                    this.mAppCompatViewInflater = AppCompatViewInflater()
                }
            }
        }
        if (IS_PRE_LOLLIPOP) {
            zShouldInheritContext = attributeSet is XmlPullParser ? ((XmlPullParser) attributeSet).getDepth() > 1 : shouldInheritContext((ViewParent) view)
        } else {
            zShouldInheritContext = false
        }
        return this.mAppCompatViewInflater.createView(view, str, context, attributeSet, zShouldInheritContext, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed())
    }

    Unit dismissPopups() {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups()
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup)
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss()
                } catch (IllegalArgumentException e) {
                }
            }
            this.mActionModePopup = null
        }
        endOnGoingFadeAnimation()
        PanelFeatureState panelState = getPanelState(0, false)
        if (panelState == null || panelState.menu == null) {
            return
        }
        panelState.menu.close()
    }

    Boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView
        if (((this.mOriginalWindowCallback is KeyEventDispatcher.Component) || (this.mOriginalWindowCallback is AppCompatDialog)) && (decorView = this.mWindow.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return true
        }
        if (keyEvent.getKeyCode() == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(keyEvent)) {
            return true
        }
        Int keyCode = keyEvent.getKeyCode()
        return keyEvent.getAction() == 0 ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent)
    }

    Unit doInvalidatePanelMenu(Int i) {
        PanelFeatureState panelState
        PanelFeatureState panelState2 = getPanelState(i, true)
        if (panelState2.menu != null) {
            Bundle bundle = Bundle()
            panelState2.menu.saveActionViewStates(bundle)
            if (bundle.size() > 0) {
                panelState2.frozenActionViewState = bundle
            }
            panelState2.menu.stopDispatchingItemsChanged()
            panelState2.menu.clear()
        }
        panelState2.refreshMenuContent = true
        panelState2.refreshDecorView = true
        if ((i != 108 && i != 0) || this.mDecorContentParent == null || (panelState = getPanelState(0, false)) == null) {
            return
        }
        panelState.isPrepared = false
        preparePanel(panelState, null)
    }

    Unit endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.cancel()
        }
    }

    PanelFeatureState findMenuPanel(Menu menu) {
        Array<PanelFeatureState> panelFeatureStateArr = this.mPanels
        Int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0
        for (Int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i]
            if (panelFeatureState != null && panelFeatureState.menu == menu) {
                return panelFeatureState
            }
        }
        return null
    }

    @Override // android.support.v7.app.AppCompatDelegate
    @Nullable
    fun findViewById(@IdRes Int i) {
        ensureSubDecor()
        return this.mWindow.findViewById(i)
    }

    final Context getActionBarThemedContext() {
        ActionBar supportActionBar = getSupportActionBar()
        Context themedContext = supportActionBar != null ? supportActionBar.getThemedContext() : null
        return themedContext == null ? this.mContext : themedContext
    }

    @VisibleForTesting
    final AutoNightModeManager getAutoNightModeManager() {
        ensureAutoNightModeManager()
        return this.mAutoNightModeManager
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return ActionBarDrawableToggleImpl()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar()
            this.mMenuInflater = SupportMenuInflater(this.mActionBar != null ? this.mActionBar.getThemedContext() : this.mContext)
        }
        return this.mMenuInflater
    }

    protected fun getPanelState(Int i, Boolean z) {
        Array<PanelFeatureState> panelFeatureStateArr = this.mPanels
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            Array<PanelFeatureState> panelFeatureStateArr2 = new PanelFeatureState[i + 1]
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length)
            }
            this.mPanels = panelFeatureStateArr2
            panelFeatureStateArr = panelFeatureStateArr2
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i]
        if (panelFeatureState != null) {
            return panelFeatureState
        }
        PanelFeatureState panelFeatureState2 = PanelFeatureState(i)
        panelFeatureStateArr[i] = panelFeatureState2
        return panelFeatureState2
    }

    ViewGroup getSubDecor() {
        return this.mSubDecor
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun getSupportActionBar() {
        initWindowDecorActionBar()
        return this.mActionBar
    }

    final CharSequence getTitle() {
        return this.mOriginalWindowCallback is Activity ? ((Activity) this.mOriginalWindowCallback).getTitle() : this.mTitle
    }

    final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun hasWindowFeature(Int i) {
        Boolean z
        switch (sanitizeWindowFeatureId(i)) {
            case 1:
                z = this.mWindowNoTitle
                break
            case 2:
                z = this.mFeatureProgress
                break
            case 5:
                z = this.mFeatureIndeterminateProgress
                break
            case 10:
                z = this.mOverlayActionMode
                break
            case 108:
                z = this.mHasActionBar
                break
            case 109:
                z = this.mOverlayActionBar
                break
            default:
                z = false
                break
        }
        return z || this.mWindow.hasFeature(i)
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun installViewFactory() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext)
        if (layoutInflaterFrom.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflaterFrom, this)
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImpl) {
                return
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's")
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar == null || !supportActionBar.invalidateOptionsMenu()) {
            invalidatePanelMenu(0)
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes
    }

    Int mapNightMode(Int i) {
        switch (i) {
            case -100:
                return -1
            case 0:
                if (Build.VERSION.SDK_INT >= 23 && ((UiModeManager) this.mContext.getSystemService(UiModeManager.class)).getNightMode() == 0) {
                    return -1
                }
                ensureAutoNightModeManager()
                return this.mAutoNightModeManager.getApplyableNightMode()
            default:
                return i
        }
    }

    Boolean onBackPressed() {
        if (this.mActionMode != null) {
            this.mActionMode.finish()
            return true
        }
        ActionBar supportActionBar = getSupportActionBar()
        return supportActionBar != null && supportActionBar.collapseActionView()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onConfigurationChanged(Configuration configuration) {
        ActionBar supportActionBar
        if (this.mHasActionBar && this.mSubDecorInstalled && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.onConfigurationChanged(configuration)
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext)
        applyDayNight()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onCreate(Bundle bundle) {
        String parentActivityName
        if (this.mOriginalWindowCallback is Activity) {
            try {
                parentActivityName = NavUtils.getParentActivityName((Activity) this.mOriginalWindowCallback)
            } catch (IllegalArgumentException e) {
                parentActivityName = null
            }
            if (parentActivityName != null) {
                ActionBar actionBarPeekSupportActionBar = peekSupportActionBar()
                if (actionBarPeekSupportActionBar == null) {
                    this.mEnableDefaultActionBarUp = true
                } else {
                    actionBarPeekSupportActionBar.setDefaultDisplayHomeAsUpEnabled(true)
                }
            }
        }
        if (bundle == null || this.mLocalNightMode != -100) {
            return
        }
        this.mLocalNightMode = bundle.getInt(KEY_LOCAL_NIGHT_MODE, -100)
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return createView(view, str, context, attributeSet)
    }

    @Override // android.view.LayoutInflater.Factory
    fun onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet)
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onDestroy() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable)
        }
        this.mIsDestroyed = true
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy()
        }
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup()
        }
    }

    Boolean onKeyDown(Int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                this.mLongPressBackDown = (keyEvent.getFlags() & 128) != 0
                break
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
                onKeyDownPanel(0, keyEvent)
                return true
        }
        return false
    }

    Boolean onKeyShortcut(Int i, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar != null && supportActionBar.onKeyShortcut(i, keyEvent)) {
            return true
        }
        if (this.mPreparedPanel != null && performPanelShortcut(this.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mPreparedPanel == null) {
                return true
            }
            this.mPreparedPanel.isHandled = true
            return true
        }
        if (this.mPreparedPanel == null) {
            PanelFeatureState panelState = getPanelState(0, true)
            preparePanel(panelState, keyEvent)
            Boolean zPerformPanelShortcut = performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent, 1)
            panelState.isPrepared = false
            if (zPerformPanelShortcut) {
                return true
            }
        }
        return false
    }

    Boolean onKeyUp(Int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                Boolean z = this.mLongPressBackDown
                this.mLongPressBackDown = false
                PanelFeatureState panelState = getPanelState(0, false)
                if (panelState != null && panelState.isOpen) {
                    if (z) {
                        return true
                    }
                    closePanel(panelState, true)
                    return true
                }
                if (onBackPressed()) {
                    return true
                }
                break
            case R.styleable.AppCompatTheme_panelMenuListWidth /* 82 */:
                onKeyUpPanel(0, keyEvent)
                return true
        }
        return false
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    fun onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState panelFeatureStateFindMenuPanel
        Window.Callback windowCallback = getWindowCallback()
        if (windowCallback == null || this.mIsDestroyed || (panelFeatureStateFindMenuPanel = findMenuPanel(menuBuilder.getRootMenu())) == null) {
            return false
        }
        return windowCallback.onMenuItemSelected(panelFeatureStateFindMenuPanel.featureId, menuItem)
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    fun onMenuModeChange(MenuBuilder menuBuilder) {
        reopenMenu(menuBuilder, true)
    }

    Unit onMenuOpened(Int i) {
        ActionBar supportActionBar
        if (i != 108 || (supportActionBar = getSupportActionBar()) == null) {
            return
        }
        supportActionBar.dispatchMenuVisibilityChanged(true)
    }

    Unit onPanelClosed(Int i) {
        if (i == 108) {
            ActionBar supportActionBar = getSupportActionBar()
            if (supportActionBar != null) {
                supportActionBar.dispatchMenuVisibilityChanged(false)
                return
            }
            return
        }
        if (i == 0) {
            PanelFeatureState panelState = getPanelState(i, true)
            if (panelState.isOpen) {
                closePanel(panelState, false)
            }
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onPostCreate(Bundle bundle) {
        ensureSubDecor()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onPostResume() {
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true)
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onSaveInstanceState(Bundle bundle) {
        if (this.mLocalNightMode != -100) {
            bundle.putInt(KEY_LOCAL_NIGHT_MODE, this.mLocalNightMode)
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onStart() {
        applyDayNight()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun onStop() {
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false)
        }
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup()
        }
    }

    Unit onSubDecorInstalled(ViewGroup viewGroup) {
    }

    final ActionBar peekSupportActionBar() {
        return this.mActionBar
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun requestWindowFeature(Int i) {
        Int iSanitizeWindowFeatureId = sanitizeWindowFeatureId(i)
        if (this.mWindowNoTitle && iSanitizeWindowFeatureId == 108) {
            return false
        }
        if (this.mHasActionBar && iSanitizeWindowFeatureId == 1) {
            this.mHasActionBar = false
        }
        switch (iSanitizeWindowFeatureId) {
            case 1:
                throwFeatureRequestIfSubDecorInstalled()
                this.mWindowNoTitle = true
                return true
            case 2:
                throwFeatureRequestIfSubDecorInstalled()
                this.mFeatureProgress = true
                return true
            case 5:
                throwFeatureRequestIfSubDecorInstalled()
                this.mFeatureIndeterminateProgress = true
                return true
            case 10:
                throwFeatureRequestIfSubDecorInstalled()
                this.mOverlayActionMode = true
                return true
            case 108:
                throwFeatureRequestIfSubDecorInstalled()
                this.mHasActionBar = true
                return true
            case 109:
                throwFeatureRequestIfSubDecorInstalled()
                this.mOverlayActionBar = true
                return true
            default:
                return this.mWindow.requestFeature(iSanitizeWindowFeatureId)
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setContentView(Int i) {
        ensureSubDecor()
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(android.R.id.content)
        viewGroup.removeAllViews()
        LayoutInflater.from(this.mContext).inflate(i, viewGroup)
        this.mOriginalWindowCallback.onContentChanged()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setContentView(View view) {
        ensureSubDecor()
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(android.R.id.content)
        viewGroup.removeAllViews()
        viewGroup.addView(view)
        this.mOriginalWindowCallback.onContentChanged()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor()
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(android.R.id.content)
        viewGroup.removeAllViews()
        viewGroup.addView(view, layoutParams)
        this.mOriginalWindowCallback.onContentChanged()
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setHandleNativeActionModesEnabled(Boolean z) {
        this.mHandleNativeActionModes = z
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setLocalNightMode(Int i) {
        switch (i) {
            case -1:
            case 0:
            case 1:
            case 2:
                if (this.mLocalNightMode != i) {
                    this.mLocalNightMode = i
                    if (this.mApplyDayNightCalled) {
                        applyDayNight()
                        break
                    }
                }
                break
            default:
                Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode")
                break
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    fun setSupportActionBar(Toolbar toolbar) {
        if (this.mOriginalWindowCallback is Activity) {
            ActionBar supportActionBar = getSupportActionBar()
            if (supportActionBar is WindowDecorActionBar) {
                throw IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.")
            }
            this.mMenuInflater = null
            if (supportActionBar != null) {
                supportActionBar.onDestroy()
            }
            if (toolbar != null) {
                ToolbarActionBar toolbarActionBar = ToolbarActionBar(toolbar, ((Activity) this.mOriginalWindowCallback).getTitle(), this.mAppCompatWindowCallback)
                this.mActionBar = toolbarActionBar
                this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback())
            } else {
                this.mActionBar = null
                this.mWindow.setCallback(this.mAppCompatWindowCallback)
            }
            invalidateOptionsMenu()
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public final Unit setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(charSequence)
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle(charSequence)
        } else if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence)
        }
    }

    final Boolean shouldAnimateActionModeView() {
        return this.mSubDecorInstalled && this.mSubDecor != null && ViewCompat.isLaidOut(this.mSubDecor)
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public android.support.v7.view.ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        if (callback == null) {
            throw IllegalArgumentException("ActionMode callback can not be null.")
        }
        if (this.mActionMode != null) {
            this.mActionMode.finish()
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = ActionModeCallbackWrapperV9(callback)
        ActionBar supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            this.mActionMode = supportActionBar.startActionMode(actionModeCallbackWrapperV9)
            if (this.mActionMode != null && this.mAppCompatCallback != null) {
                this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode)
            }
        }
        if (this.mActionMode == null) {
            this.mActionMode = startSupportActionModeFromWindow(actionModeCallbackWrapperV9)
        }
        return this.mActionMode
    }

    android.support.v7.view.ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback callback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        android.support.v7.view.ActionMode actionModeOnWindowStartingSupportActionMode
        Context contextThemeWrapper
        endOnGoingFadeAnimation()
        if (this.mActionMode != null) {
            this.mActionMode.finish()
        }
        if (!(callback is ActionModeCallbackWrapperV9)) {
            callback = ActionModeCallbackWrapperV9(callback)
        }
        if (this.mAppCompatCallback == null || this.mIsDestroyed) {
            actionModeOnWindowStartingSupportActionMode = null
        } else {
            try {
                actionModeOnWindowStartingSupportActionMode = this.mAppCompatCallback.onWindowStartingSupportActionMode(callback)
            } catch (AbstractMethodError e) {
            }
        }
        if (actionModeOnWindowStartingSupportActionMode != null) {
            this.mActionMode = actionModeOnWindowStartingSupportActionMode
        } else {
            if (this.mActionModeView == null) {
                if (this.mIsFloating) {
                    TypedValue typedValue = TypedValue()
                    Resources.Theme theme = this.mContext.getTheme()
                    theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true)
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = this.mContext.getResources().newTheme()
                        themeNewTheme.setTo(theme)
                        themeNewTheme.applyStyle(typedValue.resourceId, true)
                        contextThemeWrapper = ContextThemeWrapper(this.mContext, 0)
                        contextThemeWrapper.getTheme().setTo(themeNewTheme)
                    } else {
                        contextThemeWrapper = this.mContext
                    }
                    this.mActionModeView = ActionBarContextView(contextThemeWrapper)
                    this.mActionModePopup = PopupWindow(contextThemeWrapper, (AttributeSet) null, R.attr.actionModePopupWindowStyle)
                    PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2)
                    this.mActionModePopup.setContentView(this.mActionModeView)
                    this.mActionModePopup.setWidth(-1)
                    contextThemeWrapper.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)
                    this.mActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, contextThemeWrapper.getResources().getDisplayMetrics()))
                    this.mActionModePopup.setHeight(-2)
                    this.mShowActionModePopup = Runnable() { // from class: android.support.v7.app.AppCompatDelegateImpl.6
                        @Override // java.lang.Runnable
                        fun run() {
                            AppCompatDelegateImpl.this.mActionModePopup.showAtLocation(AppCompatDelegateImpl.this.mActionModeView, 55, 0, 0)
                            AppCompatDelegateImpl.this.endOnGoingFadeAnimation()
                            if (!AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f)
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0)
                            } else {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f)
                                AppCompatDelegateImpl.this.mFadeAnim = ViewCompat.animate(AppCompatDelegateImpl.this.mActionModeView).alpha(1.0f)
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.6.1
                                    @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                                    fun onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f)
                                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null)
                                        AppCompatDelegateImpl.this.mFadeAnim = null
                                    }

                                    @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                                    fun onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(0)
                                    }
                                })
                            }
                        }
                    }
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.mSubDecor.findViewById(R.id.action_mode_bar_stub)
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()))
                        this.mActionModeView = (ActionBarContextView) viewStubCompat.inflate()
                    }
                }
            }
            if (this.mActionModeView != null) {
                endOnGoingFadeAnimation()
                this.mActionModeView.killMode()
                StandaloneActionMode standaloneActionMode = StandaloneActionMode(this.mActionModeView.getContext(), this.mActionModeView, callback, this.mActionModePopup == null)
                if (callback.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                    standaloneActionMode.invalidate()
                    this.mActionModeView.initForMode(standaloneActionMode)
                    this.mActionMode = standaloneActionMode
                    if (shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f)
                        this.mFadeAnim = ViewCompat.animate(this.mActionModeView).alpha(1.0f)
                        this.mFadeAnim.setListener(ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.7
                            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                            fun onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f)
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(null)
                                AppCompatDelegateImpl.this.mFadeAnim = null
                            }

                            @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                            fun onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0)
                                AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32)
                                if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent())
                                }
                            }
                        })
                    } else {
                        this.mActionModeView.setAlpha(1.0f)
                        this.mActionModeView.setVisibility(0)
                        this.mActionModeView.sendAccessibilityEvent(32)
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent())
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup)
                    }
                } else {
                    this.mActionMode = null
                }
            }
        }
        if (this.mActionMode != null && this.mAppCompatCallback != null) {
            this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode)
        }
        return this.mActionMode
    }

    Int updateStatusGuard(Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Boolean z
        Boolean z2
        Boolean z3
        if (this.mActionModeView == null || !(this.mActionModeView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mActionModeView.getLayoutParams()
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = Rect()
                    this.mTempRect2 = Rect()
                }
                Rect rect = this.mTempRect1
                Rect rect2 = this.mTempRect2
                rect.set(0, i, 0, 0)
                ViewUtils.computeFitSystemWindows(this.mSubDecor, rect, rect2)
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i
                    if (this.mStatusGuard == null) {
                        this.mStatusGuard = View(this.mContext)
                        this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard))
                        this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, i))
                        z3 = true
                    } else {
                        ViewGroup.LayoutParams layoutParams = this.mStatusGuard.getLayoutParams()
                        if (layoutParams.height != i) {
                            layoutParams.height = i
                            this.mStatusGuard.setLayoutParams(layoutParams)
                        }
                        z3 = true
                    }
                } else {
                    z3 = false
                }
                z = this.mStatusGuard != null
                if (!this.mOverlayActionMode && z) {
                    i = 0
                }
                Boolean z4 = z3
                z2 = z
                z = z4
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0
                z2 = false
            } else {
                z = false
                z2 = false
            }
            if (z) {
                this.mActionModeView.setLayoutParams(marginLayoutParams)
            }
            z = z2
        }
        if (this.mStatusGuard != null) {
            this.mStatusGuard.setVisibility(z ? 0 : 8)
        }
        return i
    }
}
