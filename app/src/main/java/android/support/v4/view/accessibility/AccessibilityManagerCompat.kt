package android.support.v4.view.accessibility

import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.view.accessibility.AccessibilityManager
import java.util.List

class AccessibilityManagerCompat {

    @Deprecated
    public interface AccessibilityStateChangeListener {
        @Deprecated
        Unit onAccessibilityStateChanged(Boolean z)
    }

    @Deprecated
    abstract class AccessibilityStateChangeListenerCompat implements AccessibilityStateChangeListener {
    }

    class AccessibilityStateChangeListenerWrapper implements AccessibilityManager.AccessibilityStateChangeListener {
        AccessibilityStateChangeListener mListener

        AccessibilityStateChangeListenerWrapper(@NonNull AccessibilityStateChangeListener accessibilityStateChangeListener) {
            this.mListener = accessibilityStateChangeListener
        }

        fun equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false
            }
            return this.mListener.equals(((AccessibilityStateChangeListenerWrapper) obj).mListener)
        }

        fun hashCode() {
            return this.mListener.hashCode()
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        fun onAccessibilityStateChanged(Boolean z) {
            this.mListener.onAccessibilityStateChanged(z)
        }
    }

    public interface TouchExplorationStateChangeListener {
        Unit onTouchExplorationStateChanged(Boolean z)
    }

    @RequiresApi(19)
    class TouchExplorationStateChangeListenerWrapper implements AccessibilityManager.TouchExplorationStateChangeListener {
        final TouchExplorationStateChangeListener mListener

        TouchExplorationStateChangeListenerWrapper(@NonNull TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            this.mListener = touchExplorationStateChangeListener
        }

        fun equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false
            }
            return this.mListener.equals(((TouchExplorationStateChangeListenerWrapper) obj).mListener)
        }

        fun hashCode() {
            return this.mListener.hashCode()
        }

        @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
        fun onTouchExplorationStateChanged(Boolean z) {
            this.mListener.onTouchExplorationStateChanged(z)
        }
    }

    private constructor() {
    }

    @Deprecated
    fun addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
        if (accessibilityStateChangeListener == null) {
            return false
        }
        return accessibilityManager.addAccessibilityStateChangeListener(AccessibilityStateChangeListenerWrapper(accessibilityStateChangeListener))
    }

    fun addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        if (Build.VERSION.SDK_INT < 19 || touchExplorationStateChangeListener == null) {
            return false
        }
        return accessibilityManager.addTouchExplorationStateChangeListener(TouchExplorationStateChangeListenerWrapper(touchExplorationStateChangeListener))
    }

    @Deprecated
    fun getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, Int i) {
        return accessibilityManager.getEnabledAccessibilityServiceList(i)
    }

    @Deprecated
    fun getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        return accessibilityManager.getInstalledAccessibilityServiceList()
    }

    @Deprecated
    fun isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        return accessibilityManager.isTouchExplorationEnabled()
    }

    @Deprecated
    fun removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
        if (accessibilityStateChangeListener == null) {
            return false
        }
        return accessibilityManager.removeAccessibilityStateChangeListener(AccessibilityStateChangeListenerWrapper(accessibilityStateChangeListener))
    }

    fun removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        if (Build.VERSION.SDK_INT < 19 || touchExplorationStateChangeListener == null) {
            return false
        }
        return accessibilityManager.removeTouchExplorationStateChangeListener(TouchExplorationStateChangeListenerWrapper(touchExplorationStateChangeListener))
    }
}
