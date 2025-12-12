package android.support.v4.app

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import androidx.fragment.app.Fragment
import android.view.View
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.List

abstract class FragmentManager {
    public static val POP_BACK_STACK_INCLUSIVE = 1

    public interface BackStackEntry {
        @Nullable
        CharSequence getBreadCrumbShortTitle()

        @StringRes
        Int getBreadCrumbShortTitleRes()

        @Nullable
        CharSequence getBreadCrumbTitle()

        @StringRes
        Int getBreadCrumbTitleRes()

        Int getId()

        @Nullable
        String getName()
    }

    abstract class FragmentLifecycleCallbacks {
        fun onFragmentActivityCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        fun onFragmentAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        fun onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        fun onFragmentDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentDetached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentPaused(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentPreAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        fun onFragmentPreCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        fun onFragmentResumed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentSaveInstanceState(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Bundle bundle) {
        }

        fun onFragmentStarted(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentStopped(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        fun onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle) {
        }

        fun onFragmentViewDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }
    }

    public interface OnBackStackChangedListener {
        Unit onBackStackChanged()
    }

    fun enableDebugLogging(Boolean z) {
        FragmentManagerImpl.DEBUG = z
    }

    public abstract Unit addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener)

    @NonNull
    public abstract FragmentTransaction beginTransaction()

    public abstract Unit dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr)

    public abstract Boolean executePendingTransactions()

    @Nullable
    public abstract Fragment findFragmentById(@IdRes Int i)

    @Nullable
    public abstract Fragment findFragmentByTag(@Nullable String str)

    @NonNull
    public abstract BackStackEntry getBackStackEntryAt(Int i)

    public abstract Int getBackStackEntryCount()

    @Nullable
    public abstract Fragment getFragment(@NonNull Bundle bundle, @NonNull String str)

    @NonNull
    public abstract List getFragments()

    @Nullable
    public abstract Fragment getPrimaryNavigationFragment()

    public abstract Boolean isDestroyed()

    public abstract Boolean isStateSaved()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Deprecated
    fun openTransaction() {
        return beginTransaction()
    }

    public abstract Unit popBackStack()

    public abstract Unit popBackStack(Int i, Int i2)

    public abstract Unit popBackStack(@Nullable String str, Int i)

    public abstract Boolean popBackStackImmediate()

    public abstract Boolean popBackStackImmediate(Int i, Int i2)

    public abstract Boolean popBackStackImmediate(@Nullable String str, Int i)

    public abstract Unit putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment)

    public abstract Unit registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, Boolean z)

    public abstract Unit removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener)

    @Nullable
    public abstract Fragment.SavedState saveFragmentInstanceState(Fragment fragment)

    public abstract Unit unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks)
}
