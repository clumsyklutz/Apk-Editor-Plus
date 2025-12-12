package android.arch.lifecycle

import android.app.Application
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import java.lang.reflect.InvocationTargetException

class ViewModelProvider {
    private static val DEFAULT_KEY = "android.arch.lifecycle.ViewModelProvider.DefaultKey"
    private final Factory mFactory
    private final ViewModelStore mViewModelStore

    class AndroidViewModelFactory extends NewInstanceFactory {
        private static AndroidViewModelFactory sInstance
        private Application mApplication

        constructor(@NonNull Application application) {
            this.mApplication = application
        }

        @NonNull
        fun getInstance(@NonNull Application application) {
            if (sInstance == null) {
                sInstance = AndroidViewModelFactory(application)
            }
            return sInstance
        }

        @Override // android.arch.lifecycle.ViewModelProvider.NewInstanceFactory, android.arch.lifecycle.ViewModelProvider.Factory
        @NonNull
        fun create(@NonNull Class cls) {
            if (!AndroidViewModel.class.isAssignableFrom(cls)) {
                return super.create(cls)
            }
            try {
                return (ViewModel) cls.getConstructor(Application.class).newInstance(this.mApplication)
            } catch (IllegalAccessException e) {
                throw RuntimeException("Cannot create an instance of " + cls, e)
            } catch (InstantiationException e2) {
                throw RuntimeException("Cannot create an instance of " + cls, e2)
            } catch (NoSuchMethodException e3) {
                throw RuntimeException("Cannot create an instance of " + cls, e3)
            } catch (InvocationTargetException e4) {
                throw RuntimeException("Cannot create an instance of " + cls, e4)
            }
        }
    }

    public interface Factory {
        @NonNull
        ViewModel create(@NonNull Class cls)
    }

    class NewInstanceFactory implements Factory {
        @Override // android.arch.lifecycle.ViewModelProvider.Factory
        @NonNull
        fun create(@NonNull Class cls) {
            try {
                return (ViewModel) cls.newInstance()
            } catch (IllegalAccessException e) {
                throw RuntimeException("Cannot create an instance of " + cls, e)
            } catch (InstantiationException e2) {
                throw RuntimeException("Cannot create an instance of " + cls, e2)
            }
        }
    }

    constructor(@NonNull ViewModelStore viewModelStore, @NonNull Factory factory) {
        this.mFactory = factory
        this.mViewModelStore = viewModelStore
    }

    constructor(@NonNull ViewModelStoreOwner viewModelStoreOwner, @NonNull Factory factory) {
        this(viewModelStoreOwner.getViewModelStore(), factory)
    }

    @NonNull
    @MainThread
    fun get(@NonNull Class cls) {
        String canonicalName = cls.getCanonicalName()
        if (canonicalName == null) {
            throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
        }
        return get("android.arch.lifecycle.ViewModelProvider.DefaultKey:" + canonicalName, cls)
    }

    @NonNull
    @MainThread
    fun get(@NonNull String str, @NonNull Class cls) {
        ViewModel viewModel = this.mViewModelStore.get(str)
        if (cls.isInstance(viewModel)) {
            return viewModel
        }
        ViewModel viewModelCreate = this.mFactory.create(cls)
        this.mViewModelStore.put(str, viewModelCreate)
        return viewModelCreate
    }
}
