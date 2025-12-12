package android.support.v4.app

import android.arch.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import android.os.Bundle
import android.os.Looper
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import android.support.v4.util.DebugUtils
import androidx.collection.SparseArrayCompat
import android.util.Log
import jadx.core.codegen.CodeWriter
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.Modifier

class LoaderManagerImpl extends LoaderManager {
    static Boolean DEBUG = false
    static val TAG = "LoaderManager"

    @NonNull
    private final LifecycleOwner mLifecycleOwner

    @NonNull
    private final LoaderViewModel mLoaderViewModel

    class LoaderInfo extends MutableLiveData implements Loader.OnLoadCompleteListener {

        @Nullable
        private final Bundle mArgs
        private final Int mId
        private LifecycleOwner mLifecycleOwner

        @NonNull
        private final Loader mLoader
        private LoaderObserver mObserver
        private Loader mPriorLoader

        LoaderInfo(Int i, @Nullable Bundle bundle, @NonNull Loader loader, @Nullable Loader loader2) {
            this.mId = i
            this.mArgs = bundle
            this.mLoader = loader
            this.mPriorLoader = loader2
            this.mLoader.registerListener(i, this)
        }

        @MainThread
        Loader destroy(Boolean z) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Destroying: " + this)
            }
            this.mLoader.cancelLoad()
            this.mLoader.abandon()
            LoaderObserver loaderObserver = this.mObserver
            if (loaderObserver != null) {
                removeObserver(loaderObserver)
                if (z) {
                    loaderObserver.reset()
                }
            }
            this.mLoader.unregisterListener(this)
            if ((loaderObserver == null || loaderObserver.hasDeliveredData()) && !z) {
                return this.mLoader
            }
            this.mLoader.reset()
            return this.mPriorLoader
        }

        fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
            printWriter.print(str)
            printWriter.print("mId=")
            printWriter.print(this.mId)
            printWriter.print(" mArgs=")
            printWriter.println(this.mArgs)
            printWriter.print(str)
            printWriter.print("mLoader=")
            printWriter.println(this.mLoader)
            this.mLoader.dump(str + "  ", fileDescriptor, printWriter, strArr)
            if (this.mObserver != null) {
                printWriter.print(str)
                printWriter.print("mCallbacks=")
                printWriter.println(this.mObserver)
                this.mObserver.dump(str + "  ", printWriter)
            }
            printWriter.print(str)
            printWriter.print("mData=")
            printWriter.println(getLoader().dataToString(getValue()))
            printWriter.print(str)
            printWriter.print("mStarted=")
            printWriter.println(hasActiveObservers())
        }

        @NonNull
        Loader getLoader() {
            return this.mLoader
        }

        Boolean isCallbackWaitingForData() {
            return (!hasActiveObservers() || this.mObserver == null || this.mObserver.hasDeliveredData()) ? false : true
        }

        Unit markForRedelivery() {
            LifecycleOwner lifecycleOwner = this.mLifecycleOwner
            LoaderObserver loaderObserver = this.mObserver
            if (lifecycleOwner == null || loaderObserver == null) {
                return
            }
            super.removeObserver(loaderObserver)
            observe(lifecycleOwner, loaderObserver)
        }

        @Override // android.arch.lifecycle.LiveData
        protected fun onActive() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Starting: " + this)
            }
            this.mLoader.startLoading()
        }

        @Override // android.arch.lifecycle.LiveData
        protected fun onInactive() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Stopping: " + this)
            }
            this.mLoader.stopLoading()
        }

        @Override // android.support.v4.content.Loader.OnLoadCompleteListener
        fun onLoadComplete(@NonNull Loader loader, @Nullable Object obj) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "onLoadComplete: " + this)
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                setValue(obj)
                return
            }
            if (LoaderManagerImpl.DEBUG) {
                Log.w(LoaderManagerImpl.TAG, "onLoadComplete was incorrectly called on a background thread")
            }
            postValue(obj)
        }

        @Override // android.arch.lifecycle.LiveData
        fun removeObserver(@NonNull Observer observer) {
            super.removeObserver(observer)
            this.mLifecycleOwner = null
            this.mObserver = null
        }

        @NonNull
        @MainThread
        Loader setCallback(@NonNull LifecycleOwner lifecycleOwner, @NonNull LoaderManager.LoaderCallbacks loaderCallbacks) {
            LoaderObserver loaderObserver = LoaderObserver(this.mLoader, loaderCallbacks)
            observe(lifecycleOwner, loaderObserver)
            if (this.mObserver != null) {
                removeObserver(this.mObserver)
            }
            this.mLifecycleOwner = lifecycleOwner
            this.mObserver = loaderObserver
            return this.mLoader
        }

        @Override // android.arch.lifecycle.MutableLiveData, android.arch.lifecycle.LiveData
        fun setValue(Object obj) {
            super.setValue(obj)
            if (this.mPriorLoader != null) {
                this.mPriorLoader.reset()
                this.mPriorLoader = null
            }
        }

        fun toString() {
            StringBuilder sb = StringBuilder(64)
            sb.append("LoaderInfo{")
            sb.append(Integer.toHexString(System.identityHashCode(this)))
            sb.append(" #")
            sb.append(this.mId)
            sb.append(" : ")
            DebugUtils.buildShortClassTag(this.mLoader, sb)
            sb.append("}}")
            return sb.toString()
        }
    }

    class LoaderObserver implements Observer {

        @NonNull
        private final LoaderManager.LoaderCallbacks mCallback
        private Boolean mDeliveredData = false

        @NonNull
        private final Loader mLoader

        LoaderObserver(@NonNull Loader loader, @NonNull LoaderManager.LoaderCallbacks loaderCallbacks) {
            this.mLoader = loader
            this.mCallback = loaderCallbacks
        }

        fun dump(String str, PrintWriter printWriter) {
            printWriter.print(str)
            printWriter.print("mDeliveredData=")
            printWriter.println(this.mDeliveredData)
        }

        Boolean hasDeliveredData() {
            return this.mDeliveredData
        }

        @Override // android.arch.lifecycle.Observer
        fun onChanged(@Nullable Object obj) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  onLoadFinished in " + this.mLoader + ": " + this.mLoader.dataToString(obj))
            }
            this.mCallback.onLoadFinished(this.mLoader, obj)
            this.mDeliveredData = true
        }

        @MainThread
        Unit reset() {
            if (this.mDeliveredData) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Resetting: " + this.mLoader)
                }
                this.mCallback.onLoaderReset(this.mLoader)
            }
        }

        fun toString() {
            return this.mCallback.toString()
        }
    }

    class LoaderViewModel extends ViewModel {
        private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() { // from class: android.support.v4.app.LoaderManagerImpl.LoaderViewModel.1
            @Override // android.arch.lifecycle.ViewModelProvider.Factory
            @NonNull
            public final ViewModel create(@NonNull Class cls) {
                return LoaderViewModel()
            }
        }
        private SparseArrayCompat mLoaders = SparseArrayCompat()
        private Boolean mCreatingLoader = false

        LoaderViewModel() {
        }

        @NonNull
        static LoaderViewModel getInstance(ViewModelStore viewModelStore) {
            return (LoaderViewModel) ViewModelProvider(viewModelStore, FACTORY).get(LoaderViewModel.class)
        }

        fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
            if (this.mLoaders.size() <= 0) {
                return
            }
            printWriter.print(str)
            printWriter.println("Loaders:")
            String str2 = str + CodeWriter.INDENT
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 >= this.mLoaders.size()) {
                    return
                }
                LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.valueAt(i2)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(this.mLoaders.keyAt(i2))
                printWriter.print(": ")
                printWriter.println(loaderInfo.toString())
                loaderInfo.dump(str2, fileDescriptor, printWriter, strArr)
                i = i2 + 1
            }
        }

        Unit finishCreatingLoader() {
            this.mCreatingLoader = false
        }

        LoaderInfo getLoader(Int i) {
            return (LoaderInfo) this.mLoaders.get(i)
        }

        Boolean hasRunningLoaders() {
            Int size = this.mLoaders.size()
            for (Int i = 0; i < size; i++) {
                if (((LoaderInfo) this.mLoaders.valueAt(i)).isCallbackWaitingForData()) {
                    return true
                }
            }
            return false
        }

        Boolean isCreatingLoader() {
            return this.mCreatingLoader
        }

        Unit markForRedelivery() {
            Int size = this.mLoaders.size()
            for (Int i = 0; i < size; i++) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).markForRedelivery()
            }
        }

        @Override // android.arch.lifecycle.ViewModel
        protected fun onCleared() {
            super.onCleared()
            Int size = this.mLoaders.size()
            for (Int i = 0; i < size; i++) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).destroy(true)
            }
            this.mLoaders.clear()
        }

        Unit putLoader(Int i, @NonNull LoaderInfo loaderInfo) {
            this.mLoaders.put(i, loaderInfo)
        }

        Unit removeLoader(Int i) {
            this.mLoaders.remove(i)
        }

        Unit startCreatingLoader() {
            this.mCreatingLoader = true
        }
    }

    LoaderManagerImpl(@NonNull LifecycleOwner lifecycleOwner, @NonNull ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner
        this.mLoaderViewModel = LoaderViewModel.getInstance(viewModelStore)
    }

    @NonNull
    @MainThread
    private fun createAndInstallLoader(Int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks loaderCallbacks, @Nullable Loader loader) {
        try {
            this.mLoaderViewModel.startCreatingLoader()
            Loader loaderOnCreateLoader = loaderCallbacks.onCreateLoader(i, bundle)
            if (loaderOnCreateLoader == null) {
                throw IllegalArgumentException("Object returned from onCreateLoader must not be null")
            }
            if (loaderOnCreateLoader.getClass().isMemberClass() && !Modifier.isStatic(loaderOnCreateLoader.getClass().getModifiers())) {
                throw IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + loaderOnCreateLoader)
            }
            LoaderInfo loaderInfo = LoaderInfo(i, bundle, loaderOnCreateLoader, loader)
            if (DEBUG) {
                Log.v(TAG, "  Created new loader " + loaderInfo)
            }
            this.mLoaderViewModel.putLoader(i, loaderInfo)
            this.mLoaderViewModel.finishCreatingLoader()
            return loaderInfo.setCallback(this.mLifecycleOwner, loaderCallbacks)
        } catch (Throwable th) {
            this.mLoaderViewModel.finishCreatingLoader()
            throw th
        }
    }

    @Override // android.support.v4.app.LoaderManager
    @MainThread
    fun destroyLoader(Int i) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw IllegalStateException("Called while creating a loader")
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalStateException("destroyLoader must be called on the main thread")
        }
        if (DEBUG) {
            Log.v(TAG, "destroyLoader in " + this + " of " + i)
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(i)
        if (loader != null) {
            loader.destroy(true)
            this.mLoaderViewModel.removeLoader(i)
        }
    }

    @Override // android.support.v4.app.LoaderManager
    @Deprecated
    fun dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        this.mLoaderViewModel.dump(str, fileDescriptor, printWriter, strArr)
    }

    @Override // android.support.v4.app.LoaderManager
    @Nullable
    fun getLoader(Int i) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw IllegalStateException("Called while creating a loader")
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(i)
        if (loader != null) {
            return loader.getLoader()
        }
        return null
    }

    @Override // android.support.v4.app.LoaderManager
    fun hasRunningLoaders() {
        return this.mLoaderViewModel.hasRunningLoaders()
    }

    @Override // android.support.v4.app.LoaderManager
    @NonNull
    @MainThread
    fun initLoader(Int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks loaderCallbacks) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw IllegalStateException("Called while creating a loader")
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalStateException("initLoader must be called on the main thread")
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(i)
        if (DEBUG) {
            Log.v(TAG, "initLoader in " + this + ": args=" + bundle)
        }
        if (loader == null) {
            return createAndInstallLoader(i, bundle, loaderCallbacks, null)
        }
        if (DEBUG) {
            Log.v(TAG, "  Re-using existing loader " + loader)
        }
        return loader.setCallback(this.mLifecycleOwner, loaderCallbacks)
    }

    @Override // android.support.v4.app.LoaderManager
    fun markForRedelivery() {
        this.mLoaderViewModel.markForRedelivery()
    }

    @Override // android.support.v4.app.LoaderManager
    @NonNull
    @MainThread
    fun restartLoader(Int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks loaderCallbacks) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw IllegalStateException("Called while creating a loader")
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalStateException("restartLoader must be called on the main thread")
        }
        if (DEBUG) {
            Log.v(TAG, "restartLoader in " + this + ": args=" + bundle)
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(i)
        return createAndInstallLoader(i, bundle, loaderCallbacks, loader != null ? loader.destroy(false) : null)
    }

    fun toString() {
        StringBuilder sb = StringBuilder(128)
        sb.append("LoaderManager{")
        sb.append(Integer.toHexString(System.identityHashCode(this)))
        sb.append(" in ")
        DebugUtils.buildShortClassTag(this.mLifecycleOwner, sb)
        sb.append("}}")
        return sb.toString()
    }
}
