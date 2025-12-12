package android.support.v4.view.accessibility

import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityRecord

class AccessibilityEventCompat {
    public static val CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4
    public static val CONTENT_CHANGE_TYPE_SUBTREE = 1
    public static val CONTENT_CHANGE_TYPE_TEXT = 2
    public static val CONTENT_CHANGE_TYPE_UNDEFINED = 0
    public static val TYPES_ALL_MASK = -1
    public static val TYPE_ANNOUNCEMENT = 16384
    public static val TYPE_ASSIST_READING_CONTEXT = 16777216
    public static val TYPE_GESTURE_DETECTION_END = 524288
    public static val TYPE_GESTURE_DETECTION_START = 262144

    @Deprecated
    public static val TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024

    @Deprecated
    public static val TYPE_TOUCH_EXPLORATION_GESTURE_START = 512
    public static val TYPE_TOUCH_INTERACTION_END = 2097152
    public static val TYPE_TOUCH_INTERACTION_START = 1048576
    public static val TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768
    public static val TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536
    public static val TYPE_VIEW_CONTEXT_CLICKED = 8388608

    @Deprecated
    public static val TYPE_VIEW_HOVER_ENTER = 128

    @Deprecated
    public static val TYPE_VIEW_HOVER_EXIT = 256

    @Deprecated
    public static val TYPE_VIEW_SCROLLED = 4096

    @Deprecated
    public static val TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192
    public static val TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072
    public static val TYPE_WINDOWS_CHANGED = 4194304

    @Deprecated
    public static val TYPE_WINDOW_CONTENT_CHANGED = 2048

    private constructor() {
    }

    @Deprecated
    fun appendRecord(AccessibilityEvent accessibilityEvent, AccessibilityRecordCompat accessibilityRecordCompat) {
        accessibilityEvent.appendRecord((AccessibilityRecord) accessibilityRecordCompat.getImpl())
    }

    @Deprecated
    fun asRecord(AccessibilityEvent accessibilityEvent) {
        return AccessibilityRecordCompat(accessibilityEvent)
    }

    fun getAction(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return accessibilityEvent.getAction()
        }
        return 0
    }

    fun getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 19) {
            return accessibilityEvent.getContentChangeTypes()
        }
        return 0
    }

    fun getMovementGranularity(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return accessibilityEvent.getMovementGranularity()
        }
        return 0
    }

    @Deprecated
    fun getRecord(AccessibilityEvent accessibilityEvent, Int i) {
        return AccessibilityRecordCompat(accessibilityEvent.getRecord(i))
    }

    @Deprecated
    fun getRecordCount(AccessibilityEvent accessibilityEvent) {
        return accessibilityEvent.getRecordCount()
    }

    fun setAction(AccessibilityEvent accessibilityEvent, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            accessibilityEvent.setAction(i)
        }
    }

    fun setContentChangeTypes(AccessibilityEvent accessibilityEvent, Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            accessibilityEvent.setContentChangeTypes(i)
        }
    }

    fun setMovementGranularity(AccessibilityEvent accessibilityEvent, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            accessibilityEvent.setMovementGranularity(i)
        }
    }
}
