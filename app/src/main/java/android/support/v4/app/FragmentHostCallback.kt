package android.support.v4.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.util.Preconditions
import android.view.LayoutInflater
import android.view.View
import java.io.FileDescriptor
import java.io.PrintWriter

abstract class FragmentHostCallback extends FragmentContainer {

    @Nullable
    private final Activity mActivity

    @NonNull
    private final Context mContext
    final FragmentManagerImpl mFragmentManager

    @NonNull
    private final Handler mHandler
    private final Int mWindowAnimations

    FragmentHostCallback(@Nullable Activity activity, @NonNull Context context, @NonNull Handler handler, Int i) {
        this.mFragmentManager = FragmentManagerImpl()
        this.mActivity = activity
        this.mContext = (Context) Preconditions.checkNotNull(context, "context == null")
        this.mHandler = (Handler) Preconditions.checkNotNull(handler, "handler == null")
        this.mWindowAnimations = i
    }

    constructor(@NonNull Context context, @NonNull Handler handler, Int i) {
        this(context is Activity ? (Activity) context : null, context, handler, i)
    }

    FragmentHostCallback(@NonNull FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, fragmentActivity.mHandler, 0)
    }

    @Nullable
    Activity getActivity() {
        return this.mActivity
    }

    @NonNull
    Context getContext() {
        return this.mContext
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager
    }

    @NonNull
    Handler getHandler() {
        return this.mHandler
    }

    Unit onAttachFragment(Fragment fragment) {
    }

    fun onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
    }

    @Override // android.support.v4.app.FragmentContainer
    @Nullable
    fun onFindViewById(Int i) {
        return null
    }

    @Nullable
    public abstract Object onGetHost()

    @NonNull
    fun onGetLayoutInflater() {
        return LayoutInflater.from(this.mContext)
    }

    fun onGetWindowAnimations() {
        return this.mWindowAnimations
    }

    @Override // android.support.v4.app.FragmentContainer
    fun onHasView() {
        return true
    }

    fun onHasWindowAnimations() {
        return true
    }

    fun onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull Array<String> strArr, Int i) {
    }

    fun onShouldSaveFragmentState(Fragment fragment) {
        return true
    }

    fun onShouldShowRequestPermissionRationale(@NonNull String str) {
        return false
    }

    fun onStartActivityFromFragment(Fragment fragment, Intent intent, Int i) {
        onStartActivityFromFragment(fragment, intent, i, null)
    }

    fun onStartActivityFromFragment(Fragment fragment, Intent intent, Int i, @Nullable Bundle bundle) {
        if (i != -1) {
            throw IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host")
        }
        this.mContext.startActivity(intent)
    }

    fun onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, Bundle bundle) {
        if (i != -1) {
            throw IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host")
        }
        ActivityCompat.startIntentSenderForResult(this.mActivity, intentSender, i, intent, i2, i3, i4, bundle)
    }

    fun onSupportInvalidateOptionsMenu() {
    }
}
