package android.support.v7.view

import android.support.annotation.RestrictTo
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

abstract class ActionMode {
    private Object mTag
    private Boolean mTitleOptionalHint

    public interface Callback {
        Boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)

        Boolean onCreateActionMode(ActionMode actionMode, Menu menu)

        Unit onDestroyActionMode(ActionMode actionMode)

        Boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
    }

    public abstract Unit finish()

    public abstract View getCustomView()

    public abstract Menu getMenu()

    public abstract MenuInflater getMenuInflater()

    public abstract CharSequence getSubtitle()

    fun getTag() {
        return this.mTag
    }

    public abstract CharSequence getTitle()

    fun getTitleOptionalHint() {
        return this.mTitleOptionalHint
    }

    public abstract Unit invalidate()

    fun isTitleOptional() {
        return false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isUiFocusable() {
        return true
    }

    public abstract Unit setCustomView(View view)

    public abstract Unit setSubtitle(Int i)

    public abstract Unit setSubtitle(CharSequence charSequence)

    fun setTag(Object obj) {
        this.mTag = obj
    }

    public abstract Unit setTitle(Int i)

    public abstract Unit setTitle(CharSequence charSequence)

    fun setTitleOptionalHint(Boolean z) {
        this.mTitleOptionalHint = z
    }
}
