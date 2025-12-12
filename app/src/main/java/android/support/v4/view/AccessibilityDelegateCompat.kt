package android.support.v4.view

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeProvider

class AccessibilityDelegateCompat {
    private static final View.AccessibilityDelegate DEFAULT_DELEGATE = new View.AccessibilityDelegate()
    private final View.AccessibilityDelegate mBridge = AccessibilityDelegateAdapter(this)

    final class AccessibilityDelegateAdapter extends View.AccessibilityDelegate {
        private final AccessibilityDelegateCompat mCompat

        AccessibilityDelegateAdapter(AccessibilityDelegateCompat accessibilityDelegateCompat) {
            this.mCompat = accessibilityDelegateCompat
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            return this.mCompat.dispatchPopulateAccessibilityEvent(view, accessibilityEvent)
        }

        @Override // android.view.View.AccessibilityDelegate
        @RequiresApi(16)
        public final AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
            AccessibilityNodeProviderCompat accessibilityNodeProvider = this.mCompat.getAccessibilityNodeProvider(view)
            if (accessibilityNodeProvider != null) {
                return (AccessibilityNodeProvider) accessibilityNodeProvider.getProvider()
            }
            return null
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Unit onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.mCompat.onInitializeAccessibilityEvent(view, accessibilityEvent)
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Unit onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            this.mCompat.onInitializeAccessibilityNodeInfo(view, AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo))
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Unit onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.mCompat.onPopulateAccessibilityEvent(view, accessibilityEvent)
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return this.mCompat.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent)
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Boolean performAccessibilityAction(View view, Int i, Bundle bundle) {
            return this.mCompat.performAccessibilityAction(view, i, bundle)
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Unit sendAccessibilityEvent(View view, Int i) {
            this.mCompat.sendAccessibilityEvent(view, i)
        }

        @Override // android.view.View.AccessibilityDelegate
        public final Unit sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            this.mCompat.sendAccessibilityEventUnchecked(view, accessibilityEvent)
        }
    }

    fun dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return DEFAULT_DELEGATE.dispatchPopulateAccessibilityEvent(view, accessibilityEvent)
    }

    fun getAccessibilityNodeProvider(View view) {
        AccessibilityNodeProvider accessibilityNodeProvider
        if (Build.VERSION.SDK_INT < 16 || (accessibilityNodeProvider = DEFAULT_DELEGATE.getAccessibilityNodeProvider(view)) == null) {
            return null
        }
        return AccessibilityNodeProviderCompat(accessibilityNodeProvider)
    }

    View.AccessibilityDelegate getBridge() {
        return this.mBridge
    }

    fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        DEFAULT_DELEGATE.onInitializeAccessibilityEvent(view, accessibilityEvent)
    }

    fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        DEFAULT_DELEGATE.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.unwrap())
    }

    fun onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        DEFAULT_DELEGATE.onPopulateAccessibilityEvent(view, accessibilityEvent)
    }

    fun onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return DEFAULT_DELEGATE.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent)
    }

    fun performAccessibilityAction(View view, Int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return DEFAULT_DELEGATE.performAccessibilityAction(view, i, bundle)
        }
        return false
    }

    fun sendAccessibilityEvent(View view, Int i) {
        DEFAULT_DELEGATE.sendAccessibilityEvent(view, i)
    }

    fun sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        DEFAULT_DELEGATE.sendAccessibilityEventUnchecked(view, accessibilityEvent)
    }
}
