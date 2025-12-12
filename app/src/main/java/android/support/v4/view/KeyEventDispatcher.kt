package android.support.v4.view

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.view.KeyEvent
import android.view.View
import android.view.Window
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class KeyEventDispatcher {
    private static Boolean sActionBarFieldsFetched = false
    private static Method sActionBarOnMenuKeyMethod = null
    private static Boolean sDialogFieldsFetched = false
    private static Field sDialogKeyListenerField = null

    public interface Component {
        Boolean superDispatchKeyEvent(KeyEvent keyEvent)
    }

    private constructor() {
    }

    private fun actionBarOnMenuKeyEventPre28(ActionBar actionBar, KeyEvent keyEvent) {
        if (!sActionBarFieldsFetched) {
            try {
                sActionBarOnMenuKeyMethod = actionBar.getClass().getMethod("onMenuKeyEvent", KeyEvent.class)
            } catch (NoSuchMethodException e) {
            }
            sActionBarFieldsFetched = true
        }
        if (sActionBarOnMenuKeyMethod != null) {
            try {
                return ((Boolean) sActionBarOnMenuKeyMethod.invoke(actionBar, keyEvent)).booleanValue()
            } catch (IllegalAccessException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
        return false
    }

    private fun activitySuperDispatchKeyEventPre28(Activity activity, KeyEvent keyEvent) {
        activity.onUserInteraction()
        Window window = activity.getWindow()
        if (window.hasFeature(8)) {
            ActionBar actionBar = activity.getActionBar()
            if (keyEvent.getKeyCode() == 82 && actionBar != null && actionBarOnMenuKeyEventPre28(actionBar, keyEvent)) {
                return true
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true
        }
        View decorView = window.getDecorView()
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true
        }
        return keyEvent.dispatch(activity, decorView != null ? decorView.getKeyDispatcherState() : null, activity)
    }

    private fun dialogSuperDispatchKeyEventPre28(Dialog dialog, KeyEvent keyEvent) throws NoSuchFieldException {
        DialogInterface.OnKeyListener dialogKeyListenerPre28 = getDialogKeyListenerPre28(dialog)
        if (dialogKeyListenerPre28 != null && dialogKeyListenerPre28.onKey(dialog, keyEvent.getKeyCode(), keyEvent)) {
            return true
        }
        Window window = dialog.getWindow()
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true
        }
        View decorView = window.getDecorView()
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true
        }
        return keyEvent.dispatch(dialog, decorView != null ? decorView.getKeyDispatcherState() : null, dialog)
    }

    fun dispatchBeforeHierarchy(@NonNull View view, @NonNull KeyEvent keyEvent) {
        return ViewCompat.dispatchUnhandledKeyEventBeforeHierarchy(view, keyEvent)
    }

    fun dispatchKeyEvent(@NonNull Component component, @Nullable View view, @Nullable Window.Callback callback, @NonNull KeyEvent keyEvent) {
        if (component == null) {
            return false
        }
        return Build.VERSION.SDK_INT >= 28 ? component.superDispatchKeyEvent(keyEvent) : callback is Activity ? activitySuperDispatchKeyEventPre28((Activity) callback, keyEvent) : callback is Dialog ? dialogSuperDispatchKeyEventPre28((Dialog) callback, keyEvent) : (view != null && ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, keyEvent)) || component.superDispatchKeyEvent(keyEvent)
    }

    private static DialogInterface.OnKeyListener getDialogKeyListenerPre28(Dialog dialog) throws NoSuchFieldException {
        if (!sDialogFieldsFetched) {
            try {
                Field declaredField = Dialog.class.getDeclaredField("mOnKeyListener")
                sDialogKeyListenerField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
            }
            sDialogFieldsFetched = true
        }
        if (sDialogKeyListenerField != null) {
            try {
                return (DialogInterface.OnKeyListener) sDialogKeyListenerField.get(dialog)
            } catch (IllegalAccessException e2) {
            }
        }
        return null
    }
}
