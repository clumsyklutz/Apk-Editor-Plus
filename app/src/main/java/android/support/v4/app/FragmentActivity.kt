package android.support.v4.app

import android.arch.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import android.arch.lifecycle.ViewModelStoreOwner
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.core.internal.view.SupportMenu
import androidx.collection.SparseArrayCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import java.io.FileDescriptor
import java.io.PrintWriter

class FragmentActivity extends SupportActivity implements ViewModelStoreOwner, ActivityCompat.OnRequestPermissionsResultCallback, ActivityCompat.RequestPermissionsRequestCodeValidator {
    static val ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies"
    static val FRAGMENTS_TAG = "android:support:fragments"
    static val MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534
    static val MSG_RESUME_PENDING = 2
    static val NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index"
    static val REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who"
    private static val TAG = "FragmentActivity"
    Boolean mCreated
    Int mNextCandidateRequestIndex
    SparseArrayCompat mPendingFragmentActivityResults
    Boolean mRequestedPermissionsFromFragment
    Boolean mResumed
    Boolean mStartedActivityFromFragment
    Boolean mStartedIntentSenderFromFragment
    private ViewModelStore mViewModelStore
    val mHandler = Handler() { // from class: android.support.v4.app.FragmentActivity.1
        @Override // android.os.Handler
        fun handleMessage(Message message) {
            switch (message.what) {
                case 2:
                    FragmentActivity.this.onResumeFragments()
                    FragmentActivity.this.mFragments.execPendingActions()
                    break
                default:
                    super.handleMessage(message)
                    break
            }
        }
    }
    val mFragments = FragmentController.createController(HostCallbacks())
    Boolean mStopped = true

    class HostCallbacks extends FragmentHostCallback {
        constructor() {
            super(FragmentActivity.this)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onAttachFragment(Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
            FragmentActivity.this.dump(str, fileDescriptor, printWriter, strArr)
        }

        @Override // android.support.v4.app.FragmentHostCallback, android.support.v4.app.FragmentContainer
        @Nullable
        fun onFindViewById(Int i) {
            return FragmentActivity.this.findViewById(i)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onGetHost() {
            return FragmentActivity.this
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow()
            if (window == null) {
                return 0
            }
            return window.getAttributes().windowAnimations
        }

        @Override // android.support.v4.app.FragmentHostCallback, android.support.v4.app.FragmentContainer
        fun onHasView() {
            Window window = FragmentActivity.this.getWindow()
            return (window == null || window.peekDecorView() == null) ? false : true
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onHasWindowAnimations() {
            return FragmentActivity.this.getWindow() != null
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull Array<String> strArr, Int i) {
            FragmentActivity.this.requestPermissionsFromFragment(fragment, strArr, i)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onShouldSaveFragmentState(Fragment fragment) {
            return !FragmentActivity.this.isFinishing()
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onShouldShowRequestPermissionRationale(@NonNull String str) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, str)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onStartActivityFromFragment(Fragment fragment, Intent intent, Int i) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, i)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onStartActivityFromFragment(Fragment fragment, Intent intent, Int i, @Nullable Bundle bundle) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, i, bundle)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, Bundle bundle) {
            FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle)
        }

        @Override // android.support.v4.app.FragmentHostCallback
        fun onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu()
        }
    }

    final class NonConfigurationInstances {
        Object custom
        FragmentManagerNonConfig fragments
        ViewModelStore viewModelStore

        NonConfigurationInstances() {
        }
    }

    private fun allocateRequestIndex(Fragment fragment) {
        if (this.mPendingFragmentActivityResults.size() >= MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS) {
            throw IllegalStateException("Too many pending Fragment activity results.")
        }
        while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0) {
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS
        }
        Int i = this.mNextCandidateRequestIndex
        this.mPendingFragmentActivityResults.put(i, fragment.mWho)
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS
        return i
    }

    static Unit checkForValidRequestCode(Int i) {
        if (((-65536) & i) != 0) {
            throw IllegalArgumentException("Can only use lower 16 bits for requestCode")
        }
    }

    private fun markFragmentsCreated() {
        while (markState(getSupportFragmentManager(), Lifecycle.State.CREATED)) {
        }
    }

    private fun markState(FragmentManager fragmentManager, Lifecycle.State state) {
        Boolean zMarkState = false
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null) {
                if (fragment.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    fragment.mLifecycleRegistry.markState(state)
                    zMarkState = true
                }
                FragmentManager fragmentManagerPeekChildFragmentManager = fragment.peekChildFragmentManager()
                zMarkState = fragmentManagerPeekChildFragmentManager != null ? markState(fragmentManagerPeekChildFragmentManager, state) | zMarkState : zMarkState
            }
        }
        return zMarkState
    }

    final View dispatchFragmentsOnCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return this.mFragments.onCreateView(view, str, context, attributeSet)
    }

    @Override // android.app.Activity
    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr)
        printWriter.print(str)
        printWriter.print("Local FragmentActivity ")
        printWriter.print(Integer.toHexString(System.identityHashCode(this)))
        printWriter.println(" State:")
        String str2 = str + "  "
        printWriter.print(str2)
        printWriter.print("mCreated=")
        printWriter.print(this.mCreated)
        printWriter.print(" mResumed=")
        printWriter.print(this.mResumed)
        printWriter.print(" mStopped=")
        printWriter.print(this.mStopped)
        if (getApplication() != null) {
            LoaderManager.getInstance(this).dump(str2, fileDescriptor, printWriter, strArr)
        }
        this.mFragments.getSupportFragmentManager().dump(str, fileDescriptor, printWriter, strArr)
    }

    fun getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance()
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.custom
        }
        return null
    }

    @Override // android.support.v4.app.SupportActivity, android.arch.lifecycle.LifecycleOwner
    fun getLifecycle() {
        return super.getLifecycle()
    }

    fun getSupportFragmentManager() {
        return this.mFragments.getSupportFragmentManager()
    }

    @Deprecated
    fun getSupportLoaderManager() {
        return LoaderManager.getInstance(this)
    }

    @Override // android.arch.lifecycle.ViewModelStoreOwner
    @NonNull
    fun getViewModelStore() {
        if (getApplication() == null) {
            throw IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.")
        }
        if (this.mViewModelStore == null) {
            NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance()
            if (nonConfigurationInstances != null) {
                this.mViewModelStore = nonConfigurationInstances.viewModelStore
            }
            if (this.mViewModelStore == null) {
                this.mViewModelStore = ViewModelStore()
            }
        }
        return this.mViewModelStore
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, @Nullable Intent intent) {
        this.mFragments.noteStateNotSaved()
        Int i3 = i >> 16
        if (i3 == 0) {
            ActivityCompat.PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate()
            if (permissionCompatDelegate == null || !permissionCompatDelegate.onActivityResult(this, i, i2, intent)) {
                super.onActivityResult(i, i2, intent)
                return
            }
            return
        }
        Int i4 = i3 - 1
        String str = (String) this.mPendingFragmentActivityResults.get(i4)
        this.mPendingFragmentActivityResults.remove(i4)
        if (str == null) {
            Log.w(TAG, "Activity result delivered for unknown Fragment.")
            return
        }
        Fragment fragmentFindFragmentByWho = this.mFragments.findFragmentByWho(str)
        if (fragmentFindFragmentByWho == null) {
            Log.w(TAG, "Activity result no fragment exists for who: " + str)
        } else {
            fragmentFindFragmentByWho.onActivityResult(65535 & i, i2, intent)
        }
    }

    fun onAttachFragment(Fragment fragment) {
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        FragmentManager supportFragmentManager = this.mFragments.getSupportFragmentManager()
        Boolean zIsStateSaved = supportFragmentManager.isStateSaved()
        if (!zIsStateSaved || Build.VERSION.SDK_INT > 25) {
            if (zIsStateSaved || !supportFragmentManager.popBackStackImmediate()) {
                super.onBackPressed()
            }
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        this.mFragments.noteStateNotSaved()
        this.mFragments.dispatchConfigurationChanged(configuration)
    }

    @Override // android.support.v4.app.SupportActivity, android.app.Activity
    protected fun onCreate(@Nullable Bundle bundle) {
        this.mFragments.attachHost(null)
        super.onCreate(bundle)
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance()
        if (nonConfigurationInstances != null && nonConfigurationInstances.viewModelStore != null && this.mViewModelStore == null) {
            this.mViewModelStore = nonConfigurationInstances.viewModelStore
        }
        if (bundle != null) {
            this.mFragments.restoreAllState(bundle.getParcelable(FRAGMENTS_TAG), nonConfigurationInstances != null ? nonConfigurationInstances.fragments : null)
            if (bundle.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = bundle.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG)
                Array<Int> intArray = bundle.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG)
                Array<String> stringArray = bundle.getStringArray(REQUEST_FRAGMENT_WHO_TAG)
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w(TAG, "Invalid requestCode mapping in savedInstanceState.")
                } else {
                    this.mPendingFragmentActivityResults = SparseArrayCompat(intArray.length)
                    for (Int i = 0; i < intArray.length; i++) {
                        this.mPendingFragmentActivityResults.put(intArray[i], stringArray[i])
                    }
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = SparseArrayCompat()
            this.mNextCandidateRequestIndex = 0
        }
        this.mFragments.dispatchCreate()
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onCreatePanelMenu(Int i, Menu menu) {
        return i == 0 ? super.onCreatePanelMenu(i, menu) | this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater()) : super.onCreatePanelMenu(i, menu)
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    fun onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewDispatchFragmentsOnCreateView = dispatchFragmentsOnCreateView(view, str, context, attributeSet)
        return viewDispatchFragmentsOnCreateView == null ? super.onCreateView(view, str, context, attributeSet) : viewDispatchFragmentsOnCreateView
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    fun onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewDispatchFragmentsOnCreateView = dispatchFragmentsOnCreateView(null, str, context, attributeSet)
        return viewDispatchFragmentsOnCreateView == null ? super.onCreateView(str, context, attributeSet) : viewDispatchFragmentsOnCreateView
    }

    @Override // android.app.Activity
    protected fun onDestroy() {
        super.onDestroy()
        if (this.mViewModelStore != null && !isChangingConfigurations()) {
            this.mViewModelStore.clear()
        }
        this.mFragments.dispatchDestroy()
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    fun onLowMemory() {
        super.onLowMemory()
        this.mFragments.dispatchLowMemory()
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onMenuItemSelected(Int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true
        }
        switch (i) {
            case 0:
                return this.mFragments.dispatchOptionsItemSelected(menuItem)
            case 6:
                return this.mFragments.dispatchContextItemSelected(menuItem)
            default:
                return false
        }
    }

    @Override // android.app.Activity
    @CallSuper
    fun onMultiWindowModeChanged(Boolean z) {
        this.mFragments.dispatchMultiWindowModeChanged(z)
    }

    @Override // android.app.Activity
    protected fun onNewIntent(Intent intent) {
        super.onNewIntent(intent)
        this.mFragments.noteStateNotSaved()
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onPanelClosed(Int i, Menu menu) {
        switch (i) {
            case 0:
                this.mFragments.dispatchOptionsMenuClosed(menu)
                break
        }
        super.onPanelClosed(i, menu)
    }

    @Override // android.app.Activity
    protected fun onPause() {
        super.onPause()
        this.mResumed = false
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2)
            onResumeFragments()
        }
        this.mFragments.dispatchPause()
    }

    @Override // android.app.Activity
    @CallSuper
    fun onPictureInPictureModeChanged(Boolean z) {
        this.mFragments.dispatchPictureInPictureModeChanged(z)
    }

    @Override // android.app.Activity
    protected fun onPostResume() {
        super.onPostResume()
        this.mHandler.removeMessages(2)
        onResumeFragments()
        this.mFragments.execPendingActions()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected fun onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu)
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onPreparePanel(Int i, View view, Menu menu) {
        return (i != 0 || menu == null) ? super.onPreparePanel(i, view, menu) : onPrepareOptionsPanel(view, menu) | this.mFragments.dispatchPrepareOptionsMenu(menu)
    }

    @Override // android.app.Activity, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    fun onRequestPermissionsResult(Int i, @NonNull Array<String> strArr, @NonNull Array<Int> iArr) {
        this.mFragments.noteStateNotSaved()
        Int i2 = (i >> 16) & SupportMenu.USER_MASK
        if (i2 != 0) {
            Int i3 = i2 - 1
            String str = (String) this.mPendingFragmentActivityResults.get(i3)
            this.mPendingFragmentActivityResults.remove(i3)
            if (str == null) {
                Log.w(TAG, "Activity result delivered for unknown Fragment.")
                return
            }
            Fragment fragmentFindFragmentByWho = this.mFragments.findFragmentByWho(str)
            if (fragmentFindFragmentByWho == null) {
                Log.w(TAG, "Activity result no fragment exists for who: " + str)
            } else {
                fragmentFindFragmentByWho.onRequestPermissionsResult(i & SupportMenu.USER_MASK, strArr, iArr)
            }
        }
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        this.mHandler.sendEmptyMessage(2)
        this.mResumed = true
        this.mFragments.execPendingActions()
    }

    protected fun onResumeFragments() {
        this.mFragments.dispatchResume()
    }

    fun onRetainCustomNonConfigurationInstance() {
        return null
    }

    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        Object objOnRetainCustomNonConfigurationInstance = onRetainCustomNonConfigurationInstance()
        FragmentManagerNonConfig fragmentManagerNonConfigRetainNestedNonConfig = this.mFragments.retainNestedNonConfig()
        if (fragmentManagerNonConfigRetainNestedNonConfig == null && this.mViewModelStore == null && objOnRetainCustomNonConfigurationInstance == null) {
            return null
        }
        NonConfigurationInstances nonConfigurationInstances = NonConfigurationInstances()
        nonConfigurationInstances.custom = objOnRetainCustomNonConfigurationInstance
        nonConfigurationInstances.viewModelStore = this.mViewModelStore
        nonConfigurationInstances.fragments = fragmentManagerNonConfigRetainNestedNonConfig
        return nonConfigurationInstances
    }

    @Override // android.support.v4.app.SupportActivity, android.app.Activity
    protected fun onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle)
        markFragmentsCreated()
        Parcelable parcelableSaveAllState = this.mFragments.saveAllState()
        if (parcelableSaveAllState != null) {
            bundle.putParcelable(FRAGMENTS_TAG, parcelableSaveAllState)
        }
        if (this.mPendingFragmentActivityResults.size() <= 0) {
            return
        }
        bundle.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex)
        Array<Int> iArr = new Int[this.mPendingFragmentActivityResults.size()]
        Array<String> strArr = new String[this.mPendingFragmentActivityResults.size()]
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mPendingFragmentActivityResults.size()) {
                bundle.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, iArr)
                bundle.putStringArray(REQUEST_FRAGMENT_WHO_TAG, strArr)
                return
            } else {
                iArr[i2] = this.mPendingFragmentActivityResults.keyAt(i2)
                strArr[i2] = (String) this.mPendingFragmentActivityResults.valueAt(i2)
                i = i2 + 1
            }
        }
    }

    @Override // android.app.Activity
    protected fun onStart() {
        super.onStart()
        this.mStopped = false
        if (!this.mCreated) {
            this.mCreated = true
            this.mFragments.dispatchActivityCreated()
        }
        this.mFragments.noteStateNotSaved()
        this.mFragments.execPendingActions()
        this.mFragments.dispatchStart()
    }

    @Override // android.app.Activity
    fun onStateNotSaved() {
        this.mFragments.noteStateNotSaved()
    }

    @Override // android.app.Activity
    protected fun onStop() {
        super.onStop()
        this.mStopped = true
        markFragmentsCreated()
        this.mFragments.dispatchStop()
    }

    Unit requestPermissionsFromFragment(Fragment fragment, Array<String> strArr, Int i) {
        if (i == -1) {
            ActivityCompat.requestPermissions(this, strArr, i)
            return
        }
        checkForValidRequestCode(i)
        try {
            this.mRequestedPermissionsFromFragment = true
            ActivityCompat.requestPermissions(this, strArr, ((allocateRequestIndex(fragment) + 1) << 16) + (65535 & i))
        } finally {
            this.mRequestedPermissionsFromFragment = false
        }
    }

    fun setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback)
    }

    fun setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback)
    }

    @Override // android.app.Activity
    fun startActivityForResult(Intent intent, Int i) {
        if (!this.mStartedActivityFromFragment && i != -1) {
            checkForValidRequestCode(i)
        }
        super.startActivityForResult(intent, i)
    }

    @Override // android.app.Activity
    fun startActivityForResult(Intent intent, Int i, @Nullable Bundle bundle) {
        if (!this.mStartedActivityFromFragment && i != -1) {
            checkForValidRequestCode(i)
        }
        super.startActivityForResult(intent, i, bundle)
    }

    fun startActivityFromFragment(Fragment fragment, Intent intent, Int i) {
        startActivityFromFragment(fragment, intent, i, (Bundle) null)
    }

    fun startActivityFromFragment(Fragment fragment, Intent intent, Int i, @Nullable Bundle bundle) {
        this.mStartedActivityFromFragment = true
        try {
            if (i == -1) {
                ActivityCompat.startActivityForResult(this, intent, -1, bundle)
            } else {
                checkForValidRequestCode(i)
                ActivityCompat.startActivityForResult(this, intent, ((allocateRequestIndex(fragment) + 1) << 16) + (65535 & i), bundle)
                this.mStartedActivityFromFragment = false
            }
        } finally {
            this.mStartedActivityFromFragment = false
        }
    }

    @Override // android.app.Activity
    fun startIntentSenderForResult(IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4) {
        if (!this.mStartedIntentSenderFromFragment && i != -1) {
            checkForValidRequestCode(i)
        }
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4)
    }

    @Override // android.app.Activity
    fun startIntentSenderForResult(IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, Bundle bundle) {
        if (!this.mStartedIntentSenderFromFragment && i != -1) {
            checkForValidRequestCode(i)
        }
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle)
    }

    fun startIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, Bundle bundle) {
        this.mStartedIntentSenderFromFragment = true
        try {
            if (i == -1) {
                ActivityCompat.startIntentSenderForResult(this, intentSender, i, intent, i2, i3, i4, bundle)
            } else {
                checkForValidRequestCode(i)
                ActivityCompat.startIntentSenderForResult(this, intentSender, ((allocateRequestIndex(fragment) + 1) << 16) + (65535 & i), intent, i2, i3, i4, bundle)
                this.mStartedIntentSenderFromFragment = false
            }
        } finally {
            this.mStartedIntentSenderFromFragment = false
        }
    }

    fun supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this)
    }

    @Deprecated
    fun supportInvalidateOptionsMenu() {
        invalidateOptionsMenu()
    }

    fun supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this)
    }

    fun supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this)
    }

    @Override // android.support.v4.app.ActivityCompat.RequestPermissionsRequestCodeValidator
    public final Unit validateRequestPermissionsRequestCode(Int i) {
        if (this.mRequestedPermissionsFromFragment || i == -1) {
            return
        }
        checkForValidRequestCode(i)
    }
}
