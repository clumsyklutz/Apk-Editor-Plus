package android.support.v4.app

import android.support.annotation.AnimRes
import android.support.annotation.AnimatorRes
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.view.View

abstract class FragmentTransaction {
    public static val TRANSIT_ENTER_MASK = 4096
    public static val TRANSIT_EXIT_MASK = 8192
    public static val TRANSIT_FRAGMENT_CLOSE = 8194
    public static val TRANSIT_FRAGMENT_FADE = 4099
    public static val TRANSIT_FRAGMENT_OPEN = 4097
    public static val TRANSIT_NONE = 0
    public static val TRANSIT_UNSET = -1

    @NonNull
    public abstract FragmentTransaction add(@IdRes Int i, @NonNull Fragment fragment)

    @NonNull
    public abstract FragmentTransaction add(@IdRes Int i, @NonNull Fragment fragment, @Nullable String str)

    @NonNull
    public abstract FragmentTransaction add(@NonNull Fragment fragment, @Nullable String str)

    @NonNull
    public abstract FragmentTransaction addSharedElement(@NonNull View view, @NonNull String str)

    @NonNull
    public abstract FragmentTransaction addToBackStack(@Nullable String str)

    @NonNull
    public abstract FragmentTransaction attach(@NonNull Fragment fragment)

    public abstract Int commit()

    public abstract Int commitAllowingStateLoss()

    public abstract Unit commitNow()

    public abstract Unit commitNowAllowingStateLoss()

    @NonNull
    public abstract FragmentTransaction detach(@NonNull Fragment fragment)

    @NonNull
    public abstract FragmentTransaction disallowAddToBackStack()

    @NonNull
    public abstract FragmentTransaction hide(@NonNull Fragment fragment)

    public abstract Boolean isAddToBackStackAllowed()

    public abstract Boolean isEmpty()

    @NonNull
    public abstract FragmentTransaction remove(@NonNull Fragment fragment)

    @NonNull
    public abstract FragmentTransaction replace(@IdRes Int i, @NonNull Fragment fragment)

    @NonNull
    public abstract FragmentTransaction replace(@IdRes Int i, @NonNull Fragment fragment, @Nullable String str)

    @NonNull
    public abstract FragmentTransaction runOnCommit(@NonNull Runnable runnable)

    @Deprecated
    public abstract FragmentTransaction setAllowOptimization(Boolean z)

    @NonNull
    public abstract FragmentTransaction setBreadCrumbShortTitle(@StringRes Int i)

    @NonNull
    public abstract FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence charSequence)

    @NonNull
    public abstract FragmentTransaction setBreadCrumbTitle(@StringRes Int i)

    @NonNull
    public abstract FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence charSequence)

    @NonNull
    public abstract FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes Int i, @AnimRes @AnimatorRes Int i2)

    @NonNull
    public abstract FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes Int i, @AnimRes @AnimatorRes Int i2, @AnimRes @AnimatorRes Int i3, @AnimRes @AnimatorRes Int i4)

    @NonNull
    public abstract FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment)

    @NonNull
    public abstract FragmentTransaction setReorderingAllowed(Boolean z)

    @NonNull
    public abstract FragmentTransaction setTransition(Int i)

    @NonNull
    public abstract FragmentTransaction setTransitionStyle(@StyleRes Int i)

    @NonNull
    public abstract FragmentTransaction show(@NonNull Fragment fragment)
}
