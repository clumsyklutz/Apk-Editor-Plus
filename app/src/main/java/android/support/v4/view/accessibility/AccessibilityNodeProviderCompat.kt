package android.support.v4.view.accessibility

import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeProvider
import java.util.ArrayList
import java.util.List

class AccessibilityNodeProviderCompat {
    public static val HOST_VIEW_ID = -1
    private final Object mProvider

    @RequiresApi(16)
    class AccessibilityNodeProviderApi16 extends AccessibilityNodeProvider {
        final AccessibilityNodeProviderCompat mCompat

        AccessibilityNodeProviderApi16(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            this.mCompat = accessibilityNodeProviderCompat
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        fun createAccessibilityNodeInfo(Int i) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatCreateAccessibilityNodeInfo = this.mCompat.createAccessibilityNodeInfo(i)
            if (accessibilityNodeInfoCompatCreateAccessibilityNodeInfo == null) {
                return null
            }
            return accessibilityNodeInfoCompatCreateAccessibilityNodeInfo.unwrap()
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        fun findAccessibilityNodeInfosByText(String str, Int i) {
            List listFindAccessibilityNodeInfosByText = this.mCompat.findAccessibilityNodeInfosByText(str, i)
            if (listFindAccessibilityNodeInfosByText == null) {
                return null
            }
            ArrayList arrayList = ArrayList()
            Int size = listFindAccessibilityNodeInfosByText.size()
            for (Int i2 = 0; i2 < size; i2++) {
                arrayList.add(((AccessibilityNodeInfoCompat) listFindAccessibilityNodeInfosByText.get(i2)).unwrap())
            }
            return arrayList
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        fun performAction(Int i, Int i2, Bundle bundle) {
            return this.mCompat.performAction(i, i2, bundle)
        }
    }

    @RequiresApi(19)
    class AccessibilityNodeProviderApi19 extends AccessibilityNodeProviderApi16 {
        AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            super(accessibilityNodeProviderCompat)
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        fun findFocus(Int i) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatFindFocus = this.mCompat.findFocus(i)
            if (accessibilityNodeInfoCompatFindFocus == null) {
                return null
            }
            return accessibilityNodeInfoCompatFindFocus.unwrap()
        }
    }

    constructor() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mProvider = AccessibilityNodeProviderApi19(this)
        } else if (Build.VERSION.SDK_INT >= 16) {
            this.mProvider = AccessibilityNodeProviderApi16(this)
        } else {
            this.mProvider = null
        }
    }

    constructor(Object obj) {
        this.mProvider = obj
    }

    @Nullable
    fun createAccessibilityNodeInfo(Int i) {
        return null
    }

    @Nullable
    fun findAccessibilityNodeInfosByText(String str, Int i) {
        return null
    }

    @Nullable
    fun findFocus(Int i) {
        return null
    }

    fun getProvider() {
        return this.mProvider
    }

    fun performAction(Int i, Int i2, Bundle bundle) {
        return false
    }
}
