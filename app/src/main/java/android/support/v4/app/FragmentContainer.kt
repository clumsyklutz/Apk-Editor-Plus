package android.support.v4.app

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.Nullable
import android.view.View

abstract class FragmentContainer {
    fun instantiate(Context context, String str, Bundle bundle) {
        return Fragment.instantiate(context, str, bundle)
    }

    @Nullable
    public abstract View onFindViewById(@IdRes Int i)

    public abstract Boolean onHasView()
}
