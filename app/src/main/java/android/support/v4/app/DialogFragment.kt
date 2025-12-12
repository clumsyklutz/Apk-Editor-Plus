package android.support.v4.app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import android.view.LayoutInflater
import android.view.View

class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    private static val SAVED_BACK_STACK_ID = "android:backStackId"
    private static val SAVED_CANCELABLE = "android:cancelable"
    private static val SAVED_DIALOG_STATE_TAG = "android:savedDialogState"
    private static val SAVED_SHOWS_DIALOG = "android:showsDialog"
    private static val SAVED_STYLE = "android:style"
    private static val SAVED_THEME = "android:theme"
    public static val STYLE_NORMAL = 0
    public static val STYLE_NO_FRAME = 2
    public static val STYLE_NO_INPUT = 3
    public static val STYLE_NO_TITLE = 1
    Dialog mDialog
    Boolean mDismissed
    Boolean mShownByMe
    Boolean mViewDestroyed
    Int mStyle = 0
    Int mTheme = 0
    Boolean mCancelable = true
    Boolean mShowsDialog = true
    Int mBackStackId = -1

    fun dismiss() {
        dismissInternal(false)
    }

    fun dismissAllowingStateLoss() {
        dismissInternal(true)
    }

    Unit dismissInternal(Boolean z) {
        if (this.mDismissed) {
            return
        }
        this.mDismissed = true
        this.mShownByMe = false
        if (this.mDialog != null) {
            this.mDialog.dismiss()
        }
        this.mViewDestroyed = true
        if (this.mBackStackId >= 0) {
            getFragmentManager().popBackStack(this.mBackStackId, 1)
            this.mBackStackId = -1
            return
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManager().beginTransaction()
        fragmentTransactionBeginTransaction.remove(this)
        if (z) {
            fragmentTransactionBeginTransaction.commitAllowingStateLoss()
        } else {
            fragmentTransactionBeginTransaction.commit()
        }
    }

    fun getDialog() {
        return this.mDialog
    }

    fun getShowsDialog() {
        return this.mShowsDialog
    }

    @StyleRes
    fun getTheme() {
        return this.mTheme
    }

    fun isCancelable() {
        return this.mCancelable
    }

    @Override // android.support.v4.app.Fragment
    fun onActivityCreated(@Nullable Bundle bundle) {
        Bundle bundle2
        super.onActivityCreated(bundle)
        if (this.mShowsDialog) {
            View view = getView()
            if (view != null) {
                if (view.getParent() != null) {
                    throw IllegalStateException("DialogFragment can not be attached to a container view")
                }
                this.mDialog.setContentView(view)
            }
            FragmentActivity activity = getActivity()
            if (activity != null) {
                this.mDialog.setOwnerActivity(activity)
            }
            this.mDialog.setCancelable(this.mCancelable)
            this.mDialog.setOnCancelListener(this)
            this.mDialog.setOnDismissListener(this)
            if (bundle == null || (bundle2 = bundle.getBundle(SAVED_DIALOG_STATE_TAG)) == null) {
                return
            }
            this.mDialog.onRestoreInstanceState(bundle2)
        }
    }

    @Override // android.support.v4.app.Fragment
    fun onAttach(Context context) {
        super.onAttach(context)
        if (this.mShownByMe) {
            return
        }
        this.mDismissed = false
    }

    @Override // android.content.DialogInterface.OnCancelListener
    fun onCancel(DialogInterface dialogInterface) {
    }

    @Override // android.support.v4.app.Fragment
    fun onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle)
        this.mShowsDialog = this.mContainerId == 0
        if (bundle != null) {
            this.mStyle = bundle.getInt(SAVED_STYLE, 0)
            this.mTheme = bundle.getInt(SAVED_THEME, 0)
            this.mCancelable = bundle.getBoolean(SAVED_CANCELABLE, true)
            this.mShowsDialog = bundle.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog)
            this.mBackStackId = bundle.getInt(SAVED_BACK_STACK_ID, -1)
        }
    }

    @NonNull
    fun onCreateDialog(@Nullable Bundle bundle) {
        return Dialog(getActivity(), getTheme())
    }

    @Override // android.support.v4.app.Fragment
    fun onDestroyView() {
        super.onDestroyView()
        if (this.mDialog != null) {
            this.mViewDestroyed = true
            this.mDialog.dismiss()
            this.mDialog = null
        }
    }

    @Override // android.support.v4.app.Fragment
    fun onDetach() {
        super.onDetach()
        if (this.mShownByMe || this.mDismissed) {
            return
        }
        this.mDismissed = true
    }

    @Override // android.content.DialogInterface.OnDismissListener
    fun onDismiss(DialogInterface dialogInterface) {
        if (this.mViewDestroyed) {
            return
        }
        dismissInternal(true)
    }

    @Override // android.support.v4.app.Fragment
    @NonNull
    fun onGetLayoutInflater(@Nullable Bundle bundle) {
        if (!this.mShowsDialog) {
            return super.onGetLayoutInflater(bundle)
        }
        this.mDialog = onCreateDialog(bundle)
        if (this.mDialog == null) {
            return (LayoutInflater) this.mHost.getContext().getSystemService("layout_inflater")
        }
        setupDialog(this.mDialog, this.mStyle)
        return (LayoutInflater) this.mDialog.getContext().getSystemService("layout_inflater")
    }

    @Override // android.support.v4.app.Fragment
    fun onSaveInstanceState(@NonNull Bundle bundle) {
        Bundle bundleOnSaveInstanceState
        super.onSaveInstanceState(bundle)
        if (this.mDialog != null && (bundleOnSaveInstanceState = this.mDialog.onSaveInstanceState()) != null) {
            bundle.putBundle(SAVED_DIALOG_STATE_TAG, bundleOnSaveInstanceState)
        }
        if (this.mStyle != 0) {
            bundle.putInt(SAVED_STYLE, this.mStyle)
        }
        if (this.mTheme != 0) {
            bundle.putInt(SAVED_THEME, this.mTheme)
        }
        if (!this.mCancelable) {
            bundle.putBoolean(SAVED_CANCELABLE, this.mCancelable)
        }
        if (!this.mShowsDialog) {
            bundle.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog)
        }
        if (this.mBackStackId != -1) {
            bundle.putInt(SAVED_BACK_STACK_ID, this.mBackStackId)
        }
    }

    @Override // android.support.v4.app.Fragment
    fun onStart() {
        super.onStart()
        if (this.mDialog != null) {
            this.mViewDestroyed = false
            this.mDialog.show()
        }
    }

    @Override // android.support.v4.app.Fragment
    fun onStop() {
        super.onStop()
        if (this.mDialog != null) {
            this.mDialog.hide()
        }
    }

    fun setCancelable(Boolean z) {
        this.mCancelable = z
        if (this.mDialog != null) {
            this.mDialog.setCancelable(z)
        }
    }

    fun setShowsDialog(Boolean z) {
        this.mShowsDialog = z
    }

    fun setStyle(Int i, @StyleRes Int i2) {
        this.mStyle = i
        if (this.mStyle == 2 || this.mStyle == 3) {
            this.mTheme = 16973913
        }
        if (i2 != 0) {
            this.mTheme = i2
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setupDialog(Dialog dialog, Int i) {
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
        dialog.requestWindowFeature(1)
    }

    fun show(FragmentTransaction fragmentTransaction, String str) {
        this.mDismissed = false
        this.mShownByMe = true
        fragmentTransaction.add(this, str)
        this.mViewDestroyed = false
        this.mBackStackId = fragmentTransaction.commit()
        return this.mBackStackId
    }

    fun show(FragmentManager fragmentManager, String str) {
        this.mDismissed = false
        this.mShownByMe = true
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction()
        fragmentTransactionBeginTransaction.add(this, str)
        fragmentTransactionBeginTransaction.commit()
    }

    fun showNow(FragmentManager fragmentManager, String str) {
        this.mDismissed = false
        this.mShownByMe = true
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction()
        fragmentTransactionBeginTransaction.add(this, str)
        fragmentTransactionBeginTransaction.commitNow()
    }
}
