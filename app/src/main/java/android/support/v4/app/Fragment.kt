package android.support.v4.app

import android.animation.Animator
import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelStore
import android.arch.lifecycle.ViewModelStoreOwner
import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import android.support.v4.util.DebugUtils
import android.support.v4.util.SimpleArrayMap
import android.support.v4.view.LayoutInflaterCompat
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.InvocationTargetException

class Fragment implements LifecycleOwner, ViewModelStoreOwner, ComponentCallbacks, View.OnCreateContextMenuListener {
    static val ACTIVITY_CREATED = 2
    static val CREATED = 1
    static val INITIALIZING = 0
    static val RESUMED = 4
    static val STARTED = 3
    Boolean mAdded
    AnimationInfo mAnimationInfo
    Bundle mArguments
    Int mBackStackNesting
    Boolean mCalled
    FragmentManagerImpl mChildFragmentManager
    FragmentManagerNonConfig mChildNonConfig
    ViewGroup mContainer
    Int mContainerId
    Boolean mDeferStart
    Boolean mDetached
    Int mFragmentId
    FragmentManagerImpl mFragmentManager
    Boolean mFromLayout
    Boolean mHasMenu
    Boolean mHidden
    Boolean mHiddenChanged
    FragmentHostCallback mHost
    Boolean mInLayout
    View mInnerView
    Boolean mIsCreated
    Boolean mIsNewlyAdded
    LayoutInflater mLayoutInflater
    Fragment mParentFragment
    Boolean mPerformedCreateView
    Float mPostponedAlpha
    Boolean mRemoving
    Boolean mRestored
    Boolean mRetainInstance
    Boolean mRetaining
    Bundle mSavedFragmentState

    @Nullable
    Boolean mSavedUserVisibleHint
    SparseArray mSavedViewState
    String mTag
    Fragment mTarget
    Int mTargetRequestCode
    View mView
    LifecycleOwner mViewLifecycleOwner
    LifecycleRegistry mViewLifecycleRegistry
    ViewModelStore mViewModelStore
    String mWho
    private static val sClassMap = SimpleArrayMap()
    static val USE_DEFAULT_TRANSITION = Object()
    Int mState = 0
    Int mIndex = -1
    Int mTargetIndex = -1
    Boolean mMenuVisible = true
    Boolean mUserVisibleHint = true
    LifecycleRegistry mLifecycleRegistry = LifecycleRegistry(this)
    MutableLiveData mViewLifecycleOwnerLiveData = MutableLiveData()

    class AnimationInfo {
        Boolean mAllowEnterTransitionOverlap
        Boolean mAllowReturnTransitionOverlap
        View mAnimatingAway
        Animator mAnimator
        Boolean mEnterTransitionPostponed
        Boolean mIsHideReplaced
        Int mNextAnim
        Int mNextTransition
        Int mNextTransitionStyle
        OnStartEnterTransitionListener mStartEnterTransitionListener
        Int mStateAfterAnimating
        Object mEnterTransition = null
        Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION
        Object mExitTransition = null
        Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION
        Object mSharedElementEnterTransition = null
        Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION
        SharedElementCallback mEnterTransitionCallback = null
        SharedElementCallback mExitTransitionCallback = null

        AnimationInfo() {
        }
    }

    class InstantiationException extends RuntimeException {
        constructor(String str, Exception exc) {
            super(str, exc)
        }
    }

    interface OnStartEnterTransitionListener {
        Unit onStartEnterTransition()

        Unit startListening()
    }

    class SavedState implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v4.app.Fragment.SavedState.1
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
        final Bundle mState

        SavedState(Bundle bundle) {
            this.mState = bundle
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            this.mState = parcel.readBundle()
            if (classLoader == null || this.mState == null) {
                return
            }
            this.mState.setClassLoader(classLoader)
        }

        @Override // android.os.Parcelable
        fun describeContents() {
            return 0
        }

        @Override // android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            parcel.writeBundle(this.mState)
        }
    }

    private fun ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = AnimationInfo()
        }
        return this.mAnimationInfo
    }

    fun instantiate(Context context, String str) {
        return instantiate(context, str, null)
    }

    fun instantiate(Context context, String str, @Nullable Bundle bundle) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = (Class) sClassMap.get(str)
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str)
                sClassMap.put(str, clsLoadClass)
            }
            Fragment fragment = (Fragment) clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0])
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader())
                fragment.setArguments(bundle)
            }
            return fragment
        } catch (ClassNotFoundException e) {
            throw InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e)
        } catch (IllegalAccessException e2) {
            throw InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2)
        } catch (java.lang.InstantiationException e3) {
            throw InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e3)
        } catch (NoSuchMethodException e4) {
            throw InstantiationException("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e4)
        } catch (InvocationTargetException e5) {
            throw InstantiationException("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e5)
        }
    }

    static Boolean isSupportFragmentClass(Context context, String str) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = (Class) sClassMap.get(str)
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str)
                sClassMap.put(str, clsLoadClass)
            }
            return Fragment.class.isAssignableFrom(clsLoadClass)
        } catch (ClassNotFoundException e) {
            return false
        }
    }

    Unit callStartTransitionListener() {
        OnStartEnterTransitionListener onStartEnterTransitionListener = null
        if (this.mAnimationInfo != null) {
            this.mAnimationInfo.mEnterTransitionPostponed = false
            OnStartEnterTransitionListener onStartEnterTransitionListener2 = this.mAnimationInfo.mStartEnterTransitionListener
            this.mAnimationInfo.mStartEnterTransitionListener = null
            onStartEnterTransitionListener = onStartEnterTransitionListener2
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.onStartEnterTransition()
        }
    }

    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        printWriter.print(str)
        printWriter.print("mFragmentId=#")
        printWriter.print(Integer.toHexString(this.mFragmentId))
        printWriter.print(" mContainerId=#")
        printWriter.print(Integer.toHexString(this.mContainerId))
        printWriter.print(" mTag=")
        printWriter.println(this.mTag)
        printWriter.print(str)
        printWriter.print("mState=")
        printWriter.print(this.mState)
        printWriter.print(" mIndex=")
        printWriter.print(this.mIndex)
        printWriter.print(" mWho=")
        printWriter.print(this.mWho)
        printWriter.print(" mBackStackNesting=")
        printWriter.println(this.mBackStackNesting)
        printWriter.print(str)
        printWriter.print("mAdded=")
        printWriter.print(this.mAdded)
        printWriter.print(" mRemoving=")
        printWriter.print(this.mRemoving)
        printWriter.print(" mFromLayout=")
        printWriter.print(this.mFromLayout)
        printWriter.print(" mInLayout=")
        printWriter.println(this.mInLayout)
        printWriter.print(str)
        printWriter.print("mHidden=")
        printWriter.print(this.mHidden)
        printWriter.print(" mDetached=")
        printWriter.print(this.mDetached)
        printWriter.print(" mMenuVisible=")
        printWriter.print(this.mMenuVisible)
        printWriter.print(" mHasMenu=")
        printWriter.println(this.mHasMenu)
        printWriter.print(str)
        printWriter.print("mRetainInstance=")
        printWriter.print(this.mRetainInstance)
        printWriter.print(" mRetaining=")
        printWriter.print(this.mRetaining)
        printWriter.print(" mUserVisibleHint=")
        printWriter.println(this.mUserVisibleHint)
        if (this.mFragmentManager != null) {
            printWriter.print(str)
            printWriter.print("mFragmentManager=")
            printWriter.println(this.mFragmentManager)
        }
        if (this.mHost != null) {
            printWriter.print(str)
            printWriter.print("mHost=")
            printWriter.println(this.mHost)
        }
        if (this.mParentFragment != null) {
            printWriter.print(str)
            printWriter.print("mParentFragment=")
            printWriter.println(this.mParentFragment)
        }
        if (this.mArguments != null) {
            printWriter.print(str)
            printWriter.print("mArguments=")
            printWriter.println(this.mArguments)
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(str)
            printWriter.print("mSavedFragmentState=")
            printWriter.println(this.mSavedFragmentState)
        }
        if (this.mSavedViewState != null) {
            printWriter.print(str)
            printWriter.print("mSavedViewState=")
            printWriter.println(this.mSavedViewState)
        }
        if (this.mTarget != null) {
            printWriter.print(str)
            printWriter.print("mTarget=")
            printWriter.print(this.mTarget)
            printWriter.print(" mTargetRequestCode=")
            printWriter.println(this.mTargetRequestCode)
        }
        if (getNextAnim() != 0) {
            printWriter.print(str)
            printWriter.print("mNextAnim=")
            printWriter.println(getNextAnim())
        }
        if (this.mContainer != null) {
            printWriter.print(str)
            printWriter.print("mContainer=")
            printWriter.println(this.mContainer)
        }
        if (this.mView != null) {
            printWriter.print(str)
            printWriter.print("mView=")
            printWriter.println(this.mView)
        }
        if (this.mInnerView != null) {
            printWriter.print(str)
            printWriter.print("mInnerView=")
            printWriter.println(this.mView)
        }
        if (getAnimatingAway() != null) {
            printWriter.print(str)
            printWriter.print("mAnimatingAway=")
            printWriter.println(getAnimatingAway())
            printWriter.print(str)
            printWriter.print("mStateAfterAnimating=")
            printWriter.println(getStateAfterAnimating())
        }
        if (getContext() != null) {
            LoaderManager.getInstance(this).dump(str, fileDescriptor, printWriter, strArr)
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(str)
            printWriter.println("Child " + this.mChildFragmentManager + ":")
            this.mChildFragmentManager.dump(str + "  ", fileDescriptor, printWriter, strArr)
        }
    }

    public final Boolean equals(Object obj) {
        return super.equals(obj)
    }

    Fragment findFragmentByWho(String str) {
        if (str.equals(this.mWho)) {
            return this
        }
        if (this.mChildFragmentManager != null) {
            return this.mChildFragmentManager.findFragmentByWho(str)
        }
        return null
    }

    @Nullable
    public final FragmentActivity getActivity() {
        if (this.mHost == null) {
            return null
        }
        return (FragmentActivity) this.mHost.getActivity()
    }

    fun getAllowEnterTransitionOverlap() {
        if (this.mAnimationInfo == null || this.mAnimationInfo.mAllowEnterTransitionOverlap == null) {
            return true
        }
        return this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue()
    }

    fun getAllowReturnTransitionOverlap() {
        if (this.mAnimationInfo == null || this.mAnimationInfo.mAllowReturnTransitionOverlap == null) {
            return true
        }
        return this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue()
    }

    View getAnimatingAway() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mAnimatingAway
    }

    Animator getAnimator() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mAnimator
    }

    @Nullable
    public final Bundle getArguments() {
        return this.mArguments
    }

    @NonNull
    public final FragmentManager getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager()
            if (this.mState >= 4) {
                this.mChildFragmentManager.dispatchResume()
            } else if (this.mState >= 3) {
                this.mChildFragmentManager.dispatchStart()
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated()
            } else if (this.mState > 0) {
                this.mChildFragmentManager.dispatchCreate()
            }
        }
        return this.mChildFragmentManager
    }

    @Nullable
    fun getContext() {
        if (this.mHost == null) {
            return null
        }
        return this.mHost.getContext()
    }

    @Nullable
    fun getEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mEnterTransition
    }

    SharedElementCallback getEnterTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mEnterTransitionCallback
    }

    @Nullable
    fun getExitTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mExitTransition
    }

    SharedElementCallback getExitTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mExitTransitionCallback
    }

    @Nullable
    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager
    }

    @Nullable
    public final Object getHost() {
        if (this.mHost == null) {
            return null
        }
        return this.mHost.onGetHost()
    }

    public final Int getId() {
        return this.mFragmentId
    }

    public final LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater == null ? performGetLayoutInflater(null) : this.mLayoutInflater
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Deprecated
    fun getLayoutInflater(@Nullable Bundle bundle) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (this.mHost == null) {
            throw IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.")
        }
        LayoutInflater layoutInflaterOnGetLayoutInflater = this.mHost.onGetLayoutInflater()
        getChildFragmentManager()
        LayoutInflaterCompat.setFactory2(layoutInflaterOnGetLayoutInflater, this.mChildFragmentManager.getLayoutInflaterFactory())
        return layoutInflaterOnGetLayoutInflater
    }

    @Override // android.arch.lifecycle.LifecycleOwner
    fun getLifecycle() {
        return this.mLifecycleRegistry
    }

    @Deprecated
    fun getLoaderManager() {
        return LoaderManager.getInstance(this)
    }

    Int getNextAnim() {
        if (this.mAnimationInfo == null) {
            return 0
        }
        return this.mAnimationInfo.mNextAnim
    }

    Int getNextTransition() {
        if (this.mAnimationInfo == null) {
            return 0
        }
        return this.mAnimationInfo.mNextTransition
    }

    Int getNextTransitionStyle() {
        if (this.mAnimationInfo == null) {
            return 0
        }
        return this.mAnimationInfo.mNextTransitionStyle
    }

    @Nullable
    public final Fragment getParentFragment() {
        return this.mParentFragment
    }

    fun getReenterTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mReenterTransition == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mAnimationInfo.mReenterTransition
    }

    @NonNull
    public final Resources getResources() {
        return requireContext().getResources()
    }

    public final Boolean getRetainInstance() {
        return this.mRetainInstance
    }

    @Nullable
    fun getReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mReturnTransition == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mAnimationInfo.mReturnTransition
    }

    @Nullable
    fun getSharedElementEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mSharedElementEnterTransition
    }

    @Nullable
    fun getSharedElementReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null
        }
        return this.mAnimationInfo.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : this.mAnimationInfo.mSharedElementReturnTransition
    }

    Int getStateAfterAnimating() {
        if (this.mAnimationInfo == null) {
            return 0
        }
        return this.mAnimationInfo.mStateAfterAnimating
    }

    @NonNull
    public final String getString(@StringRes Int i) {
        return getResources().getString(i)
    }

    @NonNull
    public final String getString(@StringRes Int i, Object... objArr) {
        return getResources().getString(i, objArr)
    }

    @Nullable
    public final String getTag() {
        return this.mTag
    }

    @Nullable
    public final Fragment getTargetFragment() {
        return this.mTarget
    }

    public final Int getTargetRequestCode() {
        return this.mTargetRequestCode
    }

    @NonNull
    public final CharSequence getText(@StringRes Int i) {
        return getResources().getText(i)
    }

    fun getUserVisibleHint() {
        return this.mUserVisibleHint
    }

    @Nullable
    fun getView() {
        return this.mView
    }

    @NonNull
    @MainThread
    fun getViewLifecycleOwner() {
        if (this.mViewLifecycleOwner == null) {
            throw IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()")
        }
        return this.mViewLifecycleOwner
    }

    @NonNull
    fun getViewLifecycleOwnerLiveData() {
        return this.mViewLifecycleOwnerLiveData
    }

    @Override // android.arch.lifecycle.ViewModelStoreOwner
    @NonNull
    fun getViewModelStore() {
        if (getContext() == null) {
            throw IllegalStateException("Can't access ViewModels from detached fragment")
        }
        if (this.mViewModelStore == null) {
            this.mViewModelStore = ViewModelStore()
        }
        return this.mViewModelStore
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final Boolean hasOptionsMenu() {
        return this.mHasMenu
    }

    public final Int hashCode() {
        return super.hashCode()
    }

    Unit initState() {
        this.mIndex = -1
        this.mWho = null
        this.mAdded = false
        this.mRemoving = false
        this.mFromLayout = false
        this.mInLayout = false
        this.mRestored = false
        this.mBackStackNesting = 0
        this.mFragmentManager = null
        this.mChildFragmentManager = null
        this.mHost = null
        this.mFragmentId = 0
        this.mContainerId = 0
        this.mTag = null
        this.mHidden = false
        this.mDetached = false
        this.mRetaining = false
    }

    Unit instantiateChildFragmentManager() {
        if (this.mHost == null) {
            throw IllegalStateException("Fragment has not been attached yet.")
        }
        this.mChildFragmentManager = FragmentManagerImpl()
        this.mChildFragmentManager.attachController(this.mHost, FragmentContainer() { // from class: android.support.v4.app.Fragment.2
            @Override // android.support.v4.app.FragmentContainer
            fun instantiate(Context context, String str, Bundle bundle) {
                return Fragment.this.mHost.instantiate(context, str, bundle)
            }

            @Override // android.support.v4.app.FragmentContainer
            @Nullable
            fun onFindViewById(Int i) {
                if (Fragment.this.mView == null) {
                    throw IllegalStateException("Fragment does not have a view")
                }
                return Fragment.this.mView.findViewById(i)
            }

            @Override // android.support.v4.app.FragmentContainer
            fun onHasView() {
                return Fragment.this.mView != null
            }
        }, this)
    }

    public final Boolean isAdded() {
        return this.mHost != null && this.mAdded
    }

    public final Boolean isDetached() {
        return this.mDetached
    }

    public final Boolean isHidden() {
        return this.mHidden
    }

    Boolean isHideReplaced() {
        if (this.mAnimationInfo == null) {
            return false
        }
        return this.mAnimationInfo.mIsHideReplaced
    }

    final Boolean isInBackStack() {
        return this.mBackStackNesting > 0
    }

    public final Boolean isInLayout() {
        return this.mInLayout
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final Boolean isMenuVisible() {
        return this.mMenuVisible
    }

    Boolean isPostponed() {
        if (this.mAnimationInfo == null) {
            return false
        }
        return this.mAnimationInfo.mEnterTransitionPostponed
    }

    public final Boolean isRemoving() {
        return this.mRemoving
    }

    public final Boolean isResumed() {
        return this.mState >= 4
    }

    public final Boolean isStateSaved() {
        if (this.mFragmentManager == null) {
            return false
        }
        return this.mFragmentManager.isStateSaved()
    }

    public final Boolean isVisible() {
        return (!isAdded() || isHidden() || this.mView == null || this.mView.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true
    }

    Unit noteStateNotSaved() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
        }
    }

    @CallSuper
    fun onActivityCreated(@Nullable Bundle bundle) {
        this.mCalled = true
    }

    fun onActivityResult(Int i, Int i2, Intent intent) {
    }

    @CallSuper
    @Deprecated
    fun onAttach(Activity activity) {
        this.mCalled = true
    }

    @CallSuper
    fun onAttach(Context context) {
        this.mCalled = true
        Activity activity = this.mHost == null ? null : this.mHost.getActivity()
        if (activity != null) {
            this.mCalled = false
            onAttach(activity)
        }
    }

    fun onAttachFragment(Fragment fragment) {
    }

    @Override // android.content.ComponentCallbacks
    @CallSuper
    fun onConfigurationChanged(Configuration configuration) {
        this.mCalled = true
    }

    fun onContextItemSelected(MenuItem menuItem) {
        return false
    }

    @CallSuper
    fun onCreate(@Nullable Bundle bundle) {
        this.mCalled = true
        restoreChildFragmentState(bundle)
        if (this.mChildFragmentManager == null || this.mChildFragmentManager.isStateAtLeast(1)) {
            return
        }
        this.mChildFragmentManager.dispatchCreate()
    }

    fun onCreateAnimation(Int i, Boolean z, Int i2) {
        return null
    }

    fun onCreateAnimator(Int i, Boolean z, Int i2) {
        return null
    }

    @Override // android.view.View.OnCreateContextMenuListener
    fun onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo)
    }

    fun onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    @Nullable
    fun onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return null
    }

    @CallSuper
    fun onDestroy() {
        this.mCalled = true
        FragmentActivity activity = getActivity()
        Boolean z = activity != null && activity.isChangingConfigurations()
        if (this.mViewModelStore == null || z) {
            return
        }
        this.mViewModelStore.clear()
    }

    fun onDestroyOptionsMenu() {
    }

    @CallSuper
    fun onDestroyView() {
        this.mCalled = true
    }

    @CallSuper
    fun onDetach() {
        this.mCalled = true
    }

    @NonNull
    fun onGetLayoutInflater(@Nullable Bundle bundle) {
        return getLayoutInflater(bundle)
    }

    fun onHiddenChanged(Boolean z) {
    }

    @CallSuper
    @Deprecated
    fun onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true
    }

    @CallSuper
    fun onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true
        Activity activity = this.mHost == null ? null : this.mHost.getActivity()
        if (activity != null) {
            this.mCalled = false
            onInflate(activity, attributeSet, bundle)
        }
    }

    @Override // android.content.ComponentCallbacks
    @CallSuper
    fun onLowMemory() {
        this.mCalled = true
    }

    fun onMultiWindowModeChanged(Boolean z) {
    }

    fun onOptionsItemSelected(MenuItem menuItem) {
        return false
    }

    fun onOptionsMenuClosed(Menu menu) {
    }

    @CallSuper
    fun onPause() {
        this.mCalled = true
    }

    fun onPictureInPictureModeChanged(Boolean z) {
    }

    fun onPrepareOptionsMenu(Menu menu) {
    }

    fun onRequestPermissionsResult(Int i, @NonNull Array<String> strArr, @NonNull Array<Int> iArr) {
    }

    @CallSuper
    fun onResume() {
        this.mCalled = true
    }

    fun onSaveInstanceState(@NonNull Bundle bundle) {
    }

    @CallSuper
    fun onStart() {
        this.mCalled = true
    }

    @CallSuper
    fun onStop() {
        this.mCalled = true
    }

    fun onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    }

    @CallSuper
    fun onViewStateRestored(@Nullable Bundle bundle) {
        this.mCalled = true
    }

    @Nullable
    FragmentManager peekChildFragmentManager() {
        return this.mChildFragmentManager
    }

    Unit performActivityCreated(Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
        }
        this.mState = 2
        this.mCalled = false
        onActivityCreated(bundle)
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()")
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated()
        }
    }

    Unit performConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchConfigurationChanged(configuration)
        }
    }

    Boolean performContextItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (onContextItemSelected(menuItem)) {
                return true
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(menuItem)) {
                return true
            }
        }
        return false
    }

    Unit performCreate(Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
        }
        this.mState = 1
        this.mCalled = false
        onCreate(bundle)
        this.mIsCreated = true
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()")
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    Boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Boolean z = false
        if (this.mHidden) {
            return false
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true
            onCreateOptionsMenu(menu, menuInflater)
        }
        return this.mChildFragmentManager != null ? z | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater) : z
    }

    Unit performCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
        }
        this.mPerformedCreateView = true
        this.mViewLifecycleOwner = LifecycleOwner() { // from class: android.support.v4.app.Fragment.3
            @Override // android.arch.lifecycle.LifecycleOwner
            fun getLifecycle() {
                if (Fragment.this.mViewLifecycleRegistry == null) {
                    Fragment.this.mViewLifecycleRegistry = LifecycleRegistry(Fragment.this.mViewLifecycleOwner)
                }
                return Fragment.this.mViewLifecycleRegistry
            }
        }
        this.mViewLifecycleRegistry = null
        this.mView = onCreateView(layoutInflater, viewGroup, bundle)
        if (this.mView != null) {
            this.mViewLifecycleOwner.getLifecycle()
            this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner)
        } else {
            if (this.mViewLifecycleRegistry != null) {
                throw IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null")
            }
            this.mViewLifecycleOwner = null
        }
    }

    Unit performDestroy() {
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroy()
        }
        this.mState = 0
        this.mCalled = false
        this.mIsCreated = false
        onDestroy()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()")
        }
        this.mChildFragmentManager = null
    }

    Unit performDestroyView() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroyView()
        }
        this.mState = 1
        this.mCalled = false
        onDestroyView()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()")
        }
        LoaderManager.getInstance(this).markForRedelivery()
        this.mPerformedCreateView = false
    }

    Unit performDetach() {
        this.mCalled = false
        onDetach()
        this.mLayoutInflater = null
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()")
        }
        if (this.mChildFragmentManager != null) {
            if (!this.mRetaining) {
                throw IllegalStateException("Child FragmentManager of " + this + " was not  destroyed and this fragment is not retaining instance")
            }
            this.mChildFragmentManager.dispatchDestroy()
            this.mChildFragmentManager = null
        }
    }

    @NonNull
    LayoutInflater performGetLayoutInflater(@Nullable Bundle bundle) {
        this.mLayoutInflater = onGetLayoutInflater(bundle)
        return this.mLayoutInflater
    }

    Unit performLowMemory() {
        onLowMemory()
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchLowMemory()
        }
    }

    Unit performMultiWindowModeChanged(Boolean z) {
        onMultiWindowModeChanged(z)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchMultiWindowModeChanged(z)
        }
    }

    Boolean performOptionsItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(menuItem)) {
                return true
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(menuItem)) {
                return true
            }
        }
        return false
    }

    Unit performOptionsMenuClosed(Menu menu) {
        if (this.mHidden) {
            return
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onOptionsMenuClosed(menu)
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchOptionsMenuClosed(menu)
        }
    }

    Unit performPause() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPause()
        }
        this.mState = 3
        this.mCalled = false
        onPause()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()")
        }
    }

    Unit performPictureInPictureModeChanged(Boolean z) {
        onPictureInPictureModeChanged(z)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPictureInPictureModeChanged(z)
        }
    }

    Boolean performPrepareOptionsMenu(Menu menu) {
        Boolean z = false
        if (this.mHidden) {
            return false
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true
            onPrepareOptionsMenu(menu)
        }
        return this.mChildFragmentManager != null ? z | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu) : z
    }

    Unit performResume() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
            this.mChildFragmentManager.execPendingActions()
        }
        this.mState = 4
        this.mCalled = false
        onResume()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()")
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume()
            this.mChildFragmentManager.execPendingActions()
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    Unit performSaveInstanceState(Bundle bundle) {
        Parcelable parcelableSaveAllState
        onSaveInstanceState(bundle)
        if (this.mChildFragmentManager == null || (parcelableSaveAllState = this.mChildFragmentManager.saveAllState()) == null) {
            return
        }
        bundle.putParcelable("android:support:fragments", parcelableSaveAllState)
    }

    Unit performStart() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved()
            this.mChildFragmentManager.execPendingActions()
        }
        this.mState = 3
        this.mCalled = false
        onStart()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()")
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStart()
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    Unit performStop() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStop()
        }
        this.mState = 2
        this.mCalled = false
        onStop()
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()")
        }
    }

    fun postponeEnterTransition() {
        ensureAnimationInfo().mEnterTransitionPostponed = true
    }

    fun registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this)
    }

    public final Unit requestPermissions(@NonNull Array<String> strArr, Int i) {
        if (this.mHost == null) {
            throw IllegalStateException("Fragment " + this + " not attached to Activity")
        }
        this.mHost.onRequestPermissionsFromFragment(this, strArr, i)
    }

    @NonNull
    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity()
        if (activity == null) {
            throw IllegalStateException("Fragment " + this + " not attached to an activity.")
        }
        return activity
    }

    @NonNull
    public final Context requireContext() {
        Context context = getContext()
        if (context == null) {
            throw IllegalStateException("Fragment " + this + " not attached to a context.")
        }
        return context
    }

    @NonNull
    public final FragmentManager requireFragmentManager() {
        FragmentManager fragmentManager = getFragmentManager()
        if (fragmentManager == null) {
            throw IllegalStateException("Fragment " + this + " not associated with a fragment manager.")
        }
        return fragmentManager
    }

    @NonNull
    public final Object requireHost() {
        Object host = getHost()
        if (host == null) {
            throw IllegalStateException("Fragment " + this + " not attached to a host.")
        }
        return host
    }

    Unit restoreChildFragmentState(@Nullable Bundle bundle) {
        Parcelable parcelable
        if (bundle == null || (parcelable = bundle.getParcelable("android:support:fragments")) == null) {
            return
        }
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager()
        }
        this.mChildFragmentManager.restoreAllState(parcelable, this.mChildNonConfig)
        this.mChildNonConfig = null
        this.mChildFragmentManager.dispatchCreate()
    }

    final Unit restoreViewState(Bundle bundle) {
        if (this.mSavedViewState != null) {
            this.mInnerView.restoreHierarchyState(this.mSavedViewState)
            this.mSavedViewState = null
        }
        this.mCalled = false
        onViewStateRestored(bundle)
        if (!this.mCalled) {
            throw SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()")
        }
        if (this.mView != null) {
            this.mViewLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }
    }

    fun setAllowEnterTransitionOverlap(Boolean z) {
        ensureAnimationInfo().mAllowEnterTransitionOverlap = Boolean.valueOf(z)
    }

    fun setAllowReturnTransitionOverlap(Boolean z) {
        ensureAnimationInfo().mAllowReturnTransitionOverlap = Boolean.valueOf(z)
    }

    Unit setAnimatingAway(View view) {
        ensureAnimationInfo().mAnimatingAway = view
    }

    Unit setAnimator(Animator animator) {
        ensureAnimationInfo().mAnimator = animator
    }

    fun setArguments(@Nullable Bundle bundle) {
        if (this.mIndex >= 0 && isStateSaved()) {
            throw IllegalStateException("Fragment already active and state has been saved")
        }
        this.mArguments = bundle
    }

    fun setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback
    }

    fun setEnterTransition(@Nullable Object obj) {
        ensureAnimationInfo().mEnterTransition = obj
    }

    fun setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback
    }

    fun setExitTransition(@Nullable Object obj) {
        ensureAnimationInfo().mExitTransition = obj
    }

    fun setHasOptionsMenu(Boolean z) {
        if (this.mHasMenu != z) {
            this.mHasMenu = z
            if (!isAdded() || isHidden()) {
                return
            }
            this.mHost.onSupportInvalidateOptionsMenu()
        }
    }

    Unit setHideReplaced(Boolean z) {
        ensureAnimationInfo().mIsHideReplaced = z
    }

    final Unit setIndex(Int i, Fragment fragment) {
        this.mIndex = i
        if (fragment != null) {
            this.mWho = fragment.mWho + ":" + this.mIndex
        } else {
            this.mWho = "android:fragment:" + this.mIndex
        }
    }

    fun setInitialSavedState(@Nullable SavedState savedState) {
        if (this.mIndex >= 0) {
            throw IllegalStateException("Fragment already active")
        }
        this.mSavedFragmentState = (savedState == null || savedState.mState == null) ? null : savedState.mState
    }

    fun setMenuVisibility(Boolean z) {
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu()
            }
        }
    }

    Unit setNextAnim(Int i) {
        if (this.mAnimationInfo == null && i == 0) {
            return
        }
        ensureAnimationInfo().mNextAnim = i
    }

    Unit setNextTransition(Int i, Int i2) {
        if (this.mAnimationInfo == null && i == 0 && i2 == 0) {
            return
        }
        ensureAnimationInfo()
        this.mAnimationInfo.mNextTransition = i
        this.mAnimationInfo.mNextTransitionStyle = i2
    }

    Unit setOnStartEnterTransitionListener(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        ensureAnimationInfo()
        if (onStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener) {
            return
        }
        if (onStartEnterTransitionListener != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            throw IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this)
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = onStartEnterTransitionListener
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.startListening()
        }
    }

    fun setReenterTransition(@Nullable Object obj) {
        ensureAnimationInfo().mReenterTransition = obj
    }

    fun setRetainInstance(Boolean z) {
        this.mRetainInstance = z
    }

    fun setReturnTransition(@Nullable Object obj) {
        ensureAnimationInfo().mReturnTransition = obj
    }

    fun setSharedElementEnterTransition(@Nullable Object obj) {
        ensureAnimationInfo().mSharedElementEnterTransition = obj
    }

    fun setSharedElementReturnTransition(@Nullable Object obj) {
        ensureAnimationInfo().mSharedElementReturnTransition = obj
    }

    Unit setStateAfterAnimating(Int i) {
        ensureAnimationInfo().mStateAfterAnimating = i
    }

    fun setTargetFragment(@Nullable Fragment fragment, Int i) {
        FragmentManager fragmentManager = getFragmentManager()
        FragmentManager fragmentManager2 = fragment != null ? fragment.getFragmentManager() : null
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment")
        }
        for (Fragment targetFragment = fragment; targetFragment != null; targetFragment = targetFragment.getTargetFragment()) {
            if (targetFragment == this) {
                throw IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle")
            }
        }
        this.mTarget = fragment
        this.mTargetRequestCode = i
    }

    fun setUserVisibleHint(Boolean z) {
        if (!this.mUserVisibleHint && z && this.mState < 3 && this.mFragmentManager != null && isAdded() && this.mIsCreated) {
            this.mFragmentManager.performPendingDeferredStart(this)
        }
        this.mUserVisibleHint = z
        this.mDeferStart = this.mState < 3 && !z
        if (this.mSavedFragmentState != null) {
            this.mSavedUserVisibleHint = Boolean.valueOf(z)
        }
    }

    fun shouldShowRequestPermissionRationale(@NonNull String str) {
        if (this.mHost != null) {
            return this.mHost.onShouldShowRequestPermissionRationale(str)
        }
        return false
    }

    fun startActivity(Intent intent) {
        startActivity(intent, null)
    }

    fun startActivity(Intent intent, @Nullable Bundle bundle) {
        if (this.mHost == null) {
            throw IllegalStateException("Fragment " + this + " not attached to Activity")
        }
        this.mHost.onStartActivityFromFragment(this, intent, -1, bundle)
    }

    fun startActivityForResult(Intent intent, Int i) {
        startActivityForResult(intent, i, null)
    }

    fun startActivityForResult(Intent intent, Int i, @Nullable Bundle bundle) {
        if (this.mHost == null) {
            throw IllegalStateException("Fragment " + this + " not attached to Activity")
        }
        this.mHost.onStartActivityFromFragment(this, intent, i, bundle)
    }

    fun startIntentSenderForResult(IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, Bundle bundle) {
        if (this.mHost == null) {
            throw IllegalStateException("Fragment " + this + " not attached to Activity")
        }
        this.mHost.onStartIntentSenderFromFragment(this, intentSender, i, intent, i2, i3, i4, bundle)
    }

    fun startPostponedEnterTransition() {
        if (this.mFragmentManager == null || this.mFragmentManager.mHost == null) {
            ensureAnimationInfo().mEnterTransitionPostponed = false
        } else if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
            this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(Runnable() { // from class: android.support.v4.app.Fragment.1
                @Override // java.lang.Runnable
                fun run() {
                    Fragment.this.callStartTransitionListener()
                }
            })
        } else {
            callStartTransitionListener()
        }
    }

    fun toString() {
        StringBuilder sb = StringBuilder(128)
        DebugUtils.buildShortClassTag(this, sb)
        if (this.mIndex >= 0) {
            sb.append(" #")
            sb.append(this.mIndex)
        }
        if (this.mFragmentId != 0) {
            sb.append(" id=0x")
            sb.append(Integer.toHexString(this.mFragmentId))
        }
        if (this.mTag != null) {
            sb.append(" ")
            sb.append(this.mTag)
        }
        sb.append('}')
        return sb.toString()
    }

    fun unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null)
    }
}
