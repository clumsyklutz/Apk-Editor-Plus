package android.support.v7.app

import android.support.annotation.Nullable
import android.support.v7.view.ActionMode

public interface AppCompatCallback {
    Unit onSupportActionModeFinished(ActionMode actionMode)

    Unit onSupportActionModeStarted(ActionMode actionMode)

    @Nullable
    ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback)
}
