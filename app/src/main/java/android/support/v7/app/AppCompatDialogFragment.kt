package android.support.v7.app

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.RestrictTo
import androidx.fragment.app.DialogFragment

class AppCompatDialogFragment extends DialogFragment {
    @Override // android.support.v4.app.DialogFragment
    fun onCreateDialog(Bundle bundle) {
        return AppCompatDialog(getContext(), getTheme())
    }

    @Override // android.support.v4.app.DialogFragment
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setupDialog(Dialog dialog, Int i) {
        if (!(dialog is AppCompatDialog)) {
            super.setupDialog(dialog, i)
            return
        }
        AppCompatDialog appCompatDialog = (AppCompatDialog) dialog
        switch (i) {
            case 1:
            case 2:
                break
            case 3:
                dialog.getWindow().addFlags(24)
                break
            default:
                return
        }
        appCompatDialog.supportRequestWindowFeature(1)
    }
}
