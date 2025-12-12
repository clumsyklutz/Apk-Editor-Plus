package android.support.v4.view.accessibility

import android.graphics.Rect
import android.os.Build
import android.view.accessibility.AccessibilityWindowInfo

class AccessibilityWindowInfoCompat {
    public static val TYPE_ACCESSIBILITY_OVERLAY = 4
    public static val TYPE_APPLICATION = 1
    public static val TYPE_INPUT_METHOD = 2
    public static val TYPE_SPLIT_SCREEN_DIVIDER = 5
    public static val TYPE_SYSTEM = 3
    private static val UNDEFINED = -1
    private Object mInfo

    private constructor(Object obj) {
        this.mInfo = obj
    }

    fun obtain() {
        if (Build.VERSION.SDK_INT >= 21) {
            return wrapNonNullInstance(AccessibilityWindowInfo.obtain())
        }
        return null
    }

    fun obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (Build.VERSION.SDK_INT < 21 || accessibilityWindowInfoCompat == null) {
            return null
        }
        return wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.mInfo))
    }

    private fun typeToString(Int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION"
            case 2:
                return "TYPE_INPUT_METHOD"
            case 3:
                return "TYPE_SYSTEM"
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY"
            default:
                return "<UNKNOWN>"
        }
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return AccessibilityWindowInfoCompat(obj)
        }
        return null
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && getClass() == obj.getClass()) {
            AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj
            return this.mInfo == null ? accessibilityWindowInfoCompat.mInfo == null : this.mInfo.equals(accessibilityWindowInfoCompat.mInfo)
        }
        return false
    }

    fun getAnchor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getAnchor())
        }
        return null
    }

    fun getBoundsInScreen(Rect rect) {
        if (Build.VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).getBoundsInScreen(rect)
        }
    }

    fun getChild(Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getChild(i))
        }
        return null
    }

    fun getChildCount() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).getChildCount()
        }
        return 0
    }

    fun getId() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).getId()
        }
        return -1
    }

    fun getLayer() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).getLayer()
        }
        return -1
    }

    fun getParent() {
        if (Build.VERSION.SDK_INT >= 21) {
            return wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getParent())
        }
        return null
    }

    fun getRoot() {
        if (Build.VERSION.SDK_INT >= 21) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getRoot())
        }
        return null
    }

    fun getTitle() {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((AccessibilityWindowInfo) this.mInfo).getTitle()
        }
        return null
    }

    fun getType() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).getType()
        }
        return -1
    }

    fun hashCode() {
        if (this.mInfo == null) {
            return 0
        }
        return this.mInfo.hashCode()
    }

    fun isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).isAccessibilityFocused()
        }
        return true
    }

    fun isActive() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).isActive()
        }
        return true
    }

    fun isFocused() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.mInfo).isFocused()
        }
        return true
    }

    fun recycle() {
        if (Build.VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).recycle()
        }
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        Rect rect = Rect()
        getBoundsInScreen(rect)
        sb.append("AccessibilityWindowInfo[")
        sb.append("id=").append(getId())
        sb.append(", type=").append(typeToString(getType()))
        sb.append(", layer=").append(getLayer())
        sb.append(", bounds=").append(rect)
        sb.append(", focused=").append(isFocused())
        sb.append(", active=").append(isActive())
        sb.append(", hasParent=").append(getParent() != null)
        sb.append(", hasChildren=").append(getChildCount() > 0)
        sb.append(']')
        return sb.toString()
    }
}
