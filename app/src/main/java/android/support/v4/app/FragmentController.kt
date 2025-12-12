package android.support.v4.app

import android.content.Context
import android.content.res.Configuration
import android.os.Parcelable
import android.support.annotation.Nullable
import android.support.v4.util.SimpleArrayMap
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.List

class FragmentController {
    private final FragmentHostCallback mHost

    private constructor(FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback
    }

    fun createController(FragmentHostCallback fragmentHostCallback) {
        return FragmentController(fragmentHostCallback)
    }

    fun attachHost(Fragment fragment) {
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, fragment)
    }

    fun dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated()
    }

    fun dispatchConfigurationChanged(Configuration configuration) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(configuration)
    }

    fun dispatchContextItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(menuItem)
    }

    fun dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate()
    }

    fun dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater)
    }

    fun dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy()
    }

    fun dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView()
    }

    fun dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory()
    }

    fun dispatchMultiWindowModeChanged(Boolean z) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(z)
    }

    fun dispatchOptionsItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(menuItem)
    }

    fun dispatchOptionsMenuClosed(Menu menu) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu)
    }

    fun dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause()
    }

    fun dispatchPictureInPictureModeChanged(Boolean z) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(z)
    }

    fun dispatchPrepareOptionsMenu(Menu menu) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu)
    }

    @Deprecated
    fun dispatchReallyStop() {
    }

    fun dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume()
    }

    fun dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart()
    }

    fun dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop()
    }

    @Deprecated
    fun doLoaderDestroy() {
    }

    @Deprecated
    fun doLoaderRetain() {
    }

    @Deprecated
    fun doLoaderStart() {
    }

    @Deprecated
    fun doLoaderStop(Boolean z) {
    }

    @Deprecated
    fun dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
    }

    fun execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions()
    }

    @Nullable
    fun findFragmentByWho(String str) {
        return this.mHost.mFragmentManager.findFragmentByWho(str)
    }

    fun getActiveFragments(List list) {
        return this.mHost.mFragmentManager.getActiveFragments()
    }

    fun getActiveFragmentsCount() {
        return this.mHost.mFragmentManager.getActiveFragmentCount()
    }

    fun getSupportFragmentManager() {
        return this.mHost.getFragmentManagerImpl()
    }

    @Deprecated
    fun getSupportLoaderManager() {
        throw UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.")
    }

    fun noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved()
    }

    fun onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return this.mHost.mFragmentManager.onCreateView(view, str, context, attributeSet)
    }

    @Deprecated
    fun reportLoaderStart() {
    }

    fun restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, fragmentManagerNonConfig)
    }

    @Deprecated
    fun restoreAllState(Parcelable parcelable, List list) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, FragmentManagerNonConfig(list, null, null))
    }

    @Deprecated
    fun restoreLoaderNonConfig(SimpleArrayMap simpleArrayMap) {
    }

    @Deprecated
    fun retainLoaderNonConfig() {
        return null
    }

    fun retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig()
    }

    @Deprecated
    fun retainNonConfig() {
        FragmentManagerNonConfig fragmentManagerNonConfigRetainNonConfig = this.mHost.mFragmentManager.retainNonConfig()
        if (fragmentManagerNonConfigRetainNonConfig != null) {
            return fragmentManagerNonConfigRetainNonConfig.getFragments()
        }
        return null
    }

    fun saveAllState() {
        return this.mHost.mFragmentManager.saveAllState()
    }
}
