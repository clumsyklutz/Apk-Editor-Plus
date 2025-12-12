package android.support.v4.accessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable

class AccessibilityServiceInfoCompat {
    public static val CAPABILITY_CAN_FILTER_KEY_EVENTS = 8
    public static val CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4
    public static val CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2
    public static val CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1
    public static val FEEDBACK_ALL_MASK = -1
    public static val FEEDBACK_BRAILLE = 32
    public static val FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2
    public static val FLAG_REPORT_VIEW_IDS = 16
    public static val FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8
    public static val FLAG_REQUEST_FILTER_KEY_EVENTS = 32
    public static val FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4

    private constructor() {
    }

    @NonNull
    fun capabilityToString(Int i) {
        switch (i) {
            case 1:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT"
            case 2:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION"
            case 3:
            case 5:
            case 6:
            case 7:
            default:
                return "UNKNOWN"
            case 4:
                return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY"
            case 8:
                return "CAPABILITY_CAN_FILTER_KEY_EVENTS"
        }
    }

    @NonNull
    fun feedbackTypeToString(Int i) {
        StringBuilder sb = StringBuilder()
        sb.append("[")
        while (i > 0) {
            Int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i)
            i &= iNumberOfTrailingZeros ^ (-1)
            if (sb.length() > 1) {
                sb.append(", ")
            }
            switch (iNumberOfTrailingZeros) {
                case 1:
                    sb.append("FEEDBACK_SPOKEN")
                    break
                case 2:
                    sb.append("FEEDBACK_HAPTIC")
                    break
                case 4:
                    sb.append("FEEDBACK_AUDIBLE")
                    break
                case 8:
                    sb.append("FEEDBACK_VISUAL")
                    break
                case 16:
                    sb.append("FEEDBACK_GENERIC")
                    break
            }
        }
        sb.append("]")
        return sb.toString()
    }

    @Nullable
    fun flagToString(Int i) {
        switch (i) {
            case 1:
                return "DEFAULT"
            case 2:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS"
            case 4:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE"
            case 8:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY"
            case 16:
                return "FLAG_REPORT_VIEW_IDS"
            case 32:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS"
            default:
                return null
        }
    }

    fun getCapabilities(@NonNull AccessibilityServiceInfo accessibilityServiceInfo) {
        return Build.VERSION.SDK_INT >= 18 ? accessibilityServiceInfo.getCapabilities() : accessibilityServiceInfo.getCanRetrieveWindowContent() ? 1 : 0
    }

    @Nullable
    fun loadDescription(@NonNull AccessibilityServiceInfo accessibilityServiceInfo, @NonNull PackageManager packageManager) {
        return Build.VERSION.SDK_INT >= 16 ? accessibilityServiceInfo.loadDescription(packageManager) : accessibilityServiceInfo.getDescription()
    }
}
