package android.support.v4.app

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.loader.content.Loader
import java.io.FileDescriptor
import java.io.PrintWriter

abstract class LoaderManager {

    public interface LoaderCallbacks {
        @NonNull
        @MainThread
        Loader onCreateLoader(Int i, @Nullable Bundle bundle)

        @MainThread
        Unit onLoadFinished(@NonNull Loader loader, Object obj)

        @MainThread
        Unit onLoaderReset(@NonNull Loader loader)
    }

    fun enableDebugLogging(Boolean z) {
        LoaderManagerImpl.DEBUG = z
    }

    @NonNull
    fun getInstance(@NonNull LifecycleOwner lifecycleOwner) {
        return LoaderManagerImpl(lifecycleOwner, ((ViewModelStoreOwner) lifecycleOwner).getViewModelStore())
    }

    @MainThread
    public abstract Unit destroyLoader(Int i)

    @Deprecated
    public abstract Unit dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr)

    @Nullable
    public abstract Loader getLoader(Int i)

    fun hasRunningLoaders() {
        return false
    }

    @NonNull
    @MainThread
    public abstract Loader initLoader(Int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks loaderCallbacks)

    public abstract Unit markForRedelivery()

    @NonNull
    @MainThread
    public abstract Loader restartLoader(Int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks loaderCallbacks)
}
