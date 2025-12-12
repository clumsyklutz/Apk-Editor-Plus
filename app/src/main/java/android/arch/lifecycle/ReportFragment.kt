package android.arch.lifecycle

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.arch.lifecycle.Lifecycle
import android.content.ComponentCallbacks2
import android.os.Bundle
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ReportFragment extends Fragment {
    private static val REPORT_FRAGMENT_TAG = "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag"
    private ActivityInitializationListener mProcessListener

    interface ActivityInitializationListener {
        Unit onCreate()

        Unit onResume()

        Unit onStart()
    }

    private fun dispatch(Lifecycle.Event event) {
        ComponentCallbacks2 activity = getActivity()
        if (activity is LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event)
        } else if (activity is LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle()
            if (lifecycle is LifecycleRegistry) {
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event)
            }
        }
    }

    private fun dispatchCreate(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onCreate()
        }
    }

    private fun dispatchResume(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onResume()
        }
    }

    private fun dispatchStart(ActivityInitializationListener activityInitializationListener) {
        if (activityInitializationListener != null) {
            activityInitializationListener.onStart()
        }
    }

    static ReportFragment get(Activity activity) {
        return (ReportFragment) activity.getFragmentManager().findFragmentByTag(REPORT_FRAGMENT_TAG)
    }

    fun injectIfNeededIn(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager()
        if (fragmentManager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
            fragmentManager.beginTransaction().add(ReportFragment(), REPORT_FRAGMENT_TAG).commit()
            fragmentManager.executePendingTransactions()
        }
    }

    @Override // android.app.Fragment
    fun onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle)
        dispatchCreate(this.mProcessListener)
        dispatch(Lifecycle.Event.ON_CREATE)
    }

    @Override // android.app.Fragment
    fun onDestroy() {
        super.onDestroy()
        dispatch(Lifecycle.Event.ON_DESTROY)
        this.mProcessListener = null
    }

    @Override // android.app.Fragment
    fun onPause() {
        super.onPause()
        dispatch(Lifecycle.Event.ON_PAUSE)
    }

    @Override // android.app.Fragment
    fun onResume() {
        super.onResume()
        dispatchResume(this.mProcessListener)
        dispatch(Lifecycle.Event.ON_RESUME)
    }

    @Override // android.app.Fragment
    fun onStart() {
        super.onStart()
        dispatchStart(this.mProcessListener)
        dispatch(Lifecycle.Event.ON_START)
    }

    @Override // android.app.Fragment
    fun onStop() {
        super.onStop()
        dispatch(Lifecycle.Event.ON_STOP)
    }

    Unit setProcessListener(ActivityInitializationListener activityInitializationListener) {
        this.mProcessListener = activityInitializationListener
    }
}
