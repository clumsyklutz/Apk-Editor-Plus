package android.support.v4.view.accessibility

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List

class AccessibilityNodeInfoCompat {
    public static val ACTION_ACCESSIBILITY_FOCUS = 64
    public static val ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT"
    public static val ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN"
    public static val ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING"
    public static val ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT"
    public static val ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X"
    public static val ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y"
    public static val ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"
    public static val ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT"
    public static val ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT"
    public static val ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT"
    public static val ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE"
    public static val ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128
    public static val ACTION_CLEAR_FOCUS = 2
    public static val ACTION_CLEAR_SELECTION = 8
    public static val ACTION_CLICK = 16
    public static val ACTION_COLLAPSE = 524288
    public static val ACTION_COPY = 16384
    public static val ACTION_CUT = 65536
    public static val ACTION_DISMISS = 1048576
    public static val ACTION_EXPAND = 262144
    public static val ACTION_FOCUS = 1
    public static val ACTION_LONG_CLICK = 32
    public static val ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256
    public static val ACTION_NEXT_HTML_ELEMENT = 1024
    public static val ACTION_PASTE = 32768
    public static val ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512
    public static val ACTION_PREVIOUS_HTML_ELEMENT = 2048
    public static val ACTION_SCROLL_BACKWARD = 8192
    public static val ACTION_SCROLL_FORWARD = 4096
    public static val ACTION_SELECT = 4
    public static val ACTION_SET_SELECTION = 131072
    public static val ACTION_SET_TEXT = 2097152
    private static val BOOLEAN_PROPERTY_IS_HEADING = 2
    private static val BOOLEAN_PROPERTY_IS_SHOWING_HINT = 4
    private static val BOOLEAN_PROPERTY_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY"
    private static val BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 1
    public static val FOCUS_ACCESSIBILITY = 2
    public static val FOCUS_INPUT = 1
    private static val HINT_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY"
    public static val MOVEMENT_GRANULARITY_CHARACTER = 1
    public static val MOVEMENT_GRANULARITY_LINE = 4
    public static val MOVEMENT_GRANULARITY_PAGE = 16
    public static val MOVEMENT_GRANULARITY_PARAGRAPH = 8
    public static val MOVEMENT_GRANULARITY_WORD = 2
    private static val PANE_TITLE_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY"
    private static val ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription"
    private static val TOOLTIP_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY"
    private final AccessibilityNodeInfo mInfo

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Int mParentVirtualDescendantId = -1

    class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK
        public static final AccessibilityActionCompat ACTION_HIDE_TOOLTIP
        public static final AccessibilityActionCompat ACTION_MOVE_WINDOW
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION
        public static final AccessibilityActionCompat ACTION_SCROLL_UP
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN
        public static final AccessibilityActionCompat ACTION_SHOW_TOOLTIP
        final Object mAction
        public static val ACTION_FOCUS = AccessibilityActionCompat(1, null)
        public static val ACTION_CLEAR_FOCUS = AccessibilityActionCompat(2, null)
        public static val ACTION_SELECT = AccessibilityActionCompat(4, null)
        public static val ACTION_CLEAR_SELECTION = AccessibilityActionCompat(8, null)
        public static val ACTION_CLICK = AccessibilityActionCompat(16, null)
        public static val ACTION_LONG_CLICK = AccessibilityActionCompat(32, null)
        public static val ACTION_ACCESSIBILITY_FOCUS = AccessibilityActionCompat(64, null)
        public static val ACTION_CLEAR_ACCESSIBILITY_FOCUS = AccessibilityActionCompat(128, null)
        public static val ACTION_NEXT_AT_MOVEMENT_GRANULARITY = AccessibilityActionCompat(256, null)
        public static val ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = AccessibilityActionCompat(512, null)
        public static val ACTION_NEXT_HTML_ELEMENT = AccessibilityActionCompat(1024, null)
        public static val ACTION_PREVIOUS_HTML_ELEMENT = AccessibilityActionCompat(2048, null)
        public static val ACTION_SCROLL_FORWARD = AccessibilityActionCompat(4096, null)
        public static val ACTION_SCROLL_BACKWARD = AccessibilityActionCompat(8192, null)
        public static val ACTION_COPY = AccessibilityActionCompat(16384, null)
        public static val ACTION_PASTE = AccessibilityActionCompat(32768, null)
        public static val ACTION_CUT = AccessibilityActionCompat(65536, null)
        public static val ACTION_SET_SELECTION = AccessibilityActionCompat(131072, null)
        public static val ACTION_EXPAND = AccessibilityActionCompat(262144, null)
        public static val ACTION_COLLAPSE = AccessibilityActionCompat(524288, null)
        public static val ACTION_DISMISS = AccessibilityActionCompat(1048576, null)
        public static val ACTION_SET_TEXT = AccessibilityActionCompat(2097152, null)

        static {
            ACTION_SHOW_ON_SCREEN = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null)
            ACTION_SCROLL_TO_POSITION = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null)
            ACTION_SCROLL_UP = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null)
            ACTION_SCROLL_LEFT = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null)
            ACTION_SCROLL_DOWN = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null)
            ACTION_SCROLL_RIGHT = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null)
            ACTION_CONTEXT_CLICK = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null)
            ACTION_SET_PROGRESS = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 24 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null)
            ACTION_MOVE_WINDOW = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null)
            ACTION_SHOW_TOOLTIP = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null)
            ACTION_HIDE_TOOLTIP = AccessibilityActionCompat(Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP : null)
        }

        constructor(Int i, CharSequence charSequence) {
            this(Build.VERSION.SDK_INT >= 21 ? new AccessibilityNodeInfo.AccessibilityAction(i, charSequence) : null)
        }

        AccessibilityActionCompat(Object obj) {
            this.mAction = obj
        }

        fun getId() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction) this.mAction).getId()
            }
            return 0
        }

        fun getLabel() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction) this.mAction).getLabel()
            }
            return null
        }
    }

    class CollectionInfoCompat {
        public static val SELECTION_MODE_MULTIPLE = 2
        public static val SELECTION_MODE_NONE = 0
        public static val SELECTION_MODE_SINGLE = 1
        final Object mInfo

        CollectionInfoCompat(Object obj) {
            this.mInfo = obj
        }

        fun obtain(Int i, Int i2, Boolean z) {
            return Build.VERSION.SDK_INT >= 19 ? CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z)) : CollectionInfoCompat(null)
        }

        fun obtain(Int i, Int i2, Boolean z, Int i3) {
            return Build.VERSION.SDK_INT >= 21 ? CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3)) : Build.VERSION.SDK_INT >= 19 ? CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z)) : CollectionInfoCompat(null)
        }

        fun getColumnCount() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getColumnCount()
            }
            return 0
        }

        fun getRowCount() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getRowCount()
            }
            return 0
        }

        fun getSelectionMode() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getSelectionMode()
            }
            return 0
        }

        fun isHierarchical() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).isHierarchical()
            }
            return false
        }
    }

    class CollectionItemInfoCompat {
        final Object mInfo

        CollectionItemInfoCompat(Object obj) {
            this.mInfo = obj
        }

        fun obtain(Int i, Int i2, Int i3, Int i4, Boolean z) {
            return Build.VERSION.SDK_INT >= 19 ? CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z)) : CollectionItemInfoCompat(null)
        }

        fun obtain(Int i, Int i2, Int i3, Int i4, Boolean z, Boolean z2) {
            return Build.VERSION.SDK_INT >= 21 ? CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2)) : Build.VERSION.SDK_INT >= 19 ? CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z)) : CollectionItemInfoCompat(null)
        }

        fun getColumnIndex() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getColumnIndex()
            }
            return 0
        }

        fun getColumnSpan() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getColumnSpan()
            }
            return 0
        }

        fun getRowIndex() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getRowIndex()
            }
            return 0
        }

        fun getRowSpan() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getRowSpan()
            }
            return 0
        }

        fun isHeading() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).isHeading()
            }
            return false
        }

        fun isSelected() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).isSelected()
            }
            return false
        }
    }

    class RangeInfoCompat {
        public static val RANGE_TYPE_FLOAT = 1
        public static val RANGE_TYPE_INT = 0
        public static val RANGE_TYPE_PERCENT = 2
        final Object mInfo

        RangeInfoCompat(Object obj) {
            this.mInfo = obj
        }

        fun obtain(Int i, Float f, Float f2, Float f3) {
            return Build.VERSION.SDK_INT >= 19 ? RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(i, f, f2, f3)) : RangeInfoCompat(null)
        }

        fun getCurrent() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getCurrent()
            }
            return 0.0f
        }

        fun getMax() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getMax()
            }
            return 0.0f
        }

        fun getMin() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getMin()
            }
            return 0.0f
        }

        fun getType() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getType()
            }
            return 0
        }
    }

    private constructor(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo
    }

    @Deprecated
    constructor(Object obj) {
        this.mInfo = (AccessibilityNodeInfo) obj
    }

    private fun getActionSymbolicName(Int i) {
        switch (i) {
            case 1:
                return "ACTION_FOCUS"
            case 2:
                return "ACTION_CLEAR_FOCUS"
            case 4:
                return "ACTION_SELECT"
            case 8:
                return "ACTION_CLEAR_SELECTION"
            case 16:
                return "ACTION_CLICK"
            case 32:
                return "ACTION_LONG_CLICK"
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS"
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS"
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY"
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY"
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT"
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT"
            case 4096:
                return "ACTION_SCROLL_FORWARD"
            case 8192:
                return "ACTION_SCROLL_BACKWARD"
            case 16384:
                return "ACTION_COPY"
            case 32768:
                return "ACTION_PASTE"
            case 65536:
                return "ACTION_CUT"
            case 131072:
                return "ACTION_SET_SELECTION"
            default:
                return "ACTION_UNKNOWN"
        }
    }

    private fun getBooleanProperty(Int i) {
        Bundle extras = getExtras()
        return extras != null && (extras.getInt(BOOLEAN_PROPERTY_KEY, 0) & i) == i
    }

    fun obtain() {
        return wrap(AccessibilityNodeInfo.obtain())
    }

    fun obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return wrap(AccessibilityNodeInfo.obtain(accessibilityNodeInfoCompat.mInfo))
    }

    fun obtain(View view) {
        return wrap(AccessibilityNodeInfo.obtain(view))
    }

    fun obtain(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(AccessibilityNodeInfo.obtain(view, i))
        }
        return null
    }

    private fun setBooleanProperty(Int i, Boolean z) {
        Bundle extras = getExtras()
        if (extras != null) {
            Int i2 = extras.getInt(BOOLEAN_PROPERTY_KEY, 0) & (i ^ (-1))
            if (!z) {
                i = 0
            }
            extras.putInt(BOOLEAN_PROPERTY_KEY, i2 | i)
        }
    }

    fun wrap(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        return AccessibilityNodeInfoCompat(accessibilityNodeInfo)
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return AccessibilityNodeInfoCompat(obj)
        }
        return null
    }

    fun addAction(Int i) {
        this.mInfo.addAction(i)
    }

    fun addAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.mAction)
        }
    }

    fun addChild(View view) {
        this.mInfo.addChild(view)
    }

    fun addChild(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.addChild(view, i)
        }
    }

    fun canOpenPopup() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.canOpenPopup()
        }
        return false
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && getClass() == obj.getClass()) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj
            return this.mInfo == null ? accessibilityNodeInfoCompat.mInfo == null : this.mInfo.equals(accessibilityNodeInfoCompat.mInfo)
        }
        return false
    }

    fun findAccessibilityNodeInfosByText(String str) {
        ArrayList arrayList = ArrayList()
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = this.mInfo.findAccessibilityNodeInfosByText(str)
        Int size = listFindAccessibilityNodeInfosByText.size()
        for (Int i = 0; i < size; i++) {
            arrayList.add(wrap(listFindAccessibilityNodeInfosByText.get(i)))
        }
        return arrayList
    }

    fun findAccessibilityNodeInfosByViewId(String str) {
        if (Build.VERSION.SDK_INT < 18) {
            return Collections.emptyList()
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = this.mInfo.findAccessibilityNodeInfosByViewId(str)
        ArrayList arrayList = ArrayList()
        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByViewId.iterator()
        while (it.hasNext()) {
            arrayList.add(wrap(it.next()))
        }
        return arrayList
    }

    fun findFocus(Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(this.mInfo.findFocus(i))
        }
        return null
    }

    fun focusSearch(Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(this.mInfo.focusSearch(i))
        }
        return null
    }

    fun getActionList() {
        List<AccessibilityNodeInfo.AccessibilityAction> actionList = Build.VERSION.SDK_INT >= 21 ? this.mInfo.getActionList() : null
        if (actionList == null) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList()
        Int size = actionList.size()
        for (Int i = 0; i < size; i++) {
            arrayList.add(AccessibilityActionCompat(actionList.get(i)))
        }
        return arrayList
    }

    fun getActions() {
        return this.mInfo.getActions()
    }

    fun getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect)
    }

    fun getBoundsInScreen(Rect rect) {
        this.mInfo.getBoundsInScreen(rect)
    }

    fun getChild(Int i) {
        return wrapNonNullInstance(this.mInfo.getChild(i))
    }

    fun getChildCount() {
        return this.mInfo.getChildCount()
    }

    fun getClassName() {
        return this.mInfo.getClassName()
    }

    fun getCollectionInfo() {
        AccessibilityNodeInfo.CollectionInfo collectionInfo
        if (Build.VERSION.SDK_INT < 19 || (collectionInfo = this.mInfo.getCollectionInfo()) == null) {
            return null
        }
        return CollectionInfoCompat(collectionInfo)
    }

    fun getCollectionItemInfo() {
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo
        if (Build.VERSION.SDK_INT < 19 || (collectionItemInfo = this.mInfo.getCollectionItemInfo()) == null) {
            return null
        }
        return CollectionItemInfoCompat(collectionItemInfo)
    }

    fun getContentDescription() {
        return this.mInfo.getContentDescription()
    }

    fun getDrawingOrder() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.getDrawingOrder()
        }
        return 0
    }

    fun getError() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getError()
        }
        return null
    }

    fun getExtras() {
        return Build.VERSION.SDK_INT >= 19 ? this.mInfo.getExtras() : Bundle()
    }

    @Nullable
    fun getHintText() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mInfo.getHintText()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(HINT_TEXT_KEY)
        }
        return null
    }

    @Deprecated
    fun getInfo() {
        return this.mInfo
    }

    fun getInputType() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getInputType()
        }
        return 0
    }

    fun getLabelFor() {
        if (Build.VERSION.SDK_INT >= 17) {
            return wrapNonNullInstance(this.mInfo.getLabelFor())
        }
        return null
    }

    fun getLabeledBy() {
        if (Build.VERSION.SDK_INT >= 17) {
            return wrapNonNullInstance(this.mInfo.getLabeledBy())
        }
        return null
    }

    fun getLiveRegion() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getLiveRegion()
        }
        return 0
    }

    fun getMaxTextLength() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getMaxTextLength()
        }
        return -1
    }

    fun getMovementGranularities() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.getMovementGranularities()
        }
        return 0
    }

    fun getPackageName() {
        return this.mInfo.getPackageName()
    }

    @Nullable
    fun getPaneTitle() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getPaneTitle()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(PANE_TITLE_KEY)
        }
        return null
    }

    fun getParent() {
        return wrapNonNullInstance(this.mInfo.getParent())
    }

    fun getRangeInfo() {
        AccessibilityNodeInfo.RangeInfo rangeInfo
        if (Build.VERSION.SDK_INT < 19 || (rangeInfo = this.mInfo.getRangeInfo()) == null) {
            return null
        }
        return RangeInfoCompat(rangeInfo)
    }

    @Nullable
    fun getRoleDescription() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(ROLE_DESCRIPTION_KEY)
        }
        return null
    }

    fun getText() {
        return this.mInfo.getText()
    }

    fun getTextSelectionEnd() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionEnd()
        }
        return -1
    }

    fun getTextSelectionStart() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionStart()
        }
        return -1
    }

    @Nullable
    fun getTooltipText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getTooltipText()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(TOOLTIP_TEXT_KEY)
        }
        return null
    }

    fun getTraversalAfter() {
        if (Build.VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalAfter())
        }
        return null
    }

    fun getTraversalBefore() {
        if (Build.VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalBefore())
        }
        return null
    }

    fun getViewIdResourceName() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getViewIdResourceName()
        }
        return null
    }

    fun getWindow() {
        if (Build.VERSION.SDK_INT >= 21) {
            return AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow())
        }
        return null
    }

    fun getWindowId() {
        return this.mInfo.getWindowId()
    }

    fun hashCode() {
        if (this.mInfo == null) {
            return 0
        }
        return this.mInfo.hashCode()
    }

    fun isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.isAccessibilityFocused()
        }
        return false
    }

    fun isCheckable() {
        return this.mInfo.isCheckable()
    }

    fun isChecked() {
        return this.mInfo.isChecked()
    }

    fun isClickable() {
        return this.mInfo.isClickable()
    }

    fun isContentInvalid() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isContentInvalid()
        }
        return false
    }

    fun isContextClickable() {
        if (Build.VERSION.SDK_INT >= 23) {
            return this.mInfo.isContextClickable()
        }
        return false
    }

    fun isDismissable() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isDismissable()
        }
        return false
    }

    fun isEditable() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.isEditable()
        }
        return false
    }

    fun isEnabled() {
        return this.mInfo.isEnabled()
    }

    fun isFocusable() {
        return this.mInfo.isFocusable()
    }

    fun isFocused() {
        return this.mInfo.isFocused()
    }

    fun isHeading() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.isHeading()
        }
        if (getBooleanProperty(2)) {
            return true
        }
        CollectionItemInfoCompat collectionItemInfo = getCollectionItemInfo()
        return collectionItemInfo != null && collectionItemInfo.isHeading()
    }

    fun isImportantForAccessibility() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.isImportantForAccessibility()
        }
        return true
    }

    fun isLongClickable() {
        return this.mInfo.isLongClickable()
    }

    fun isMultiLine() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isMultiLine()
        }
        return false
    }

    fun isPassword() {
        return this.mInfo.isPassword()
    }

    fun isScreenReaderFocusable() {
        return Build.VERSION.SDK_INT >= 28 ? this.mInfo.isScreenReaderFocusable() : getBooleanProperty(1)
    }

    fun isScrollable() {
        return this.mInfo.isScrollable()
    }

    fun isSelected() {
        return this.mInfo.isSelected()
    }

    fun isShowingHintText() {
        return Build.VERSION.SDK_INT >= 26 ? this.mInfo.isShowingHintText() : getBooleanProperty(4)
    }

    fun isVisibleToUser() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.isVisibleToUser()
        }
        return false
    }

    fun performAction(Int i) {
        return this.mInfo.performAction(i)
    }

    fun performAction(Int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.performAction(i, bundle)
        }
        return false
    }

    fun recycle() {
        this.mInfo.recycle()
    }

    fun refresh() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.refresh()
        }
        return false
    }

    fun removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.mAction)
        }
        return false
    }

    fun removeChild(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(view)
        }
        return false
    }

    fun removeChild(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(view, i)
        }
        return false
    }

    fun setAccessibilityFocused(Boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setAccessibilityFocused(z)
        }
    }

    fun setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect)
    }

    fun setBoundsInScreen(Rect rect) {
        this.mInfo.setBoundsInScreen(rect)
    }

    fun setCanOpenPopup(Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCanOpenPopup(z)
        }
    }

    fun setCheckable(Boolean z) {
        this.mInfo.setCheckable(z)
    }

    fun setChecked(Boolean z) {
        this.mInfo.setChecked(z)
    }

    fun setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence)
    }

    fun setClickable(Boolean z) {
        this.mInfo.setClickable(z)
    }

    fun setCollectionInfo(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionInfo) ((CollectionInfoCompat) obj).mInfo)
        }
    }

    fun setCollectionItemInfo(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionItemInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionItemInfo) ((CollectionItemInfoCompat) obj).mInfo)
        }
    }

    fun setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence)
    }

    fun setContentInvalid(Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setContentInvalid(z)
        }
    }

    fun setContextClickable(Boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mInfo.setContextClickable(z)
        }
    }

    fun setDismissable(Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setDismissable(z)
        }
    }

    fun setDrawingOrder(Int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setDrawingOrder(i)
        }
    }

    fun setEditable(Boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setEditable(z)
        }
    }

    fun setEnabled(Boolean z) {
        this.mInfo.setEnabled(z)
    }

    fun setError(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setError(charSequence)
        }
    }

    fun setFocusable(Boolean z) {
        this.mInfo.setFocusable(z)
    }

    fun setFocused(Boolean z) {
        this.mInfo.setFocused(z)
    }

    fun setHeading(Boolean z) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setHeading(z)
        } else {
            setBooleanProperty(2, z)
        }
    }

    fun setHintText(@Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setHintText(charSequence)
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(HINT_TEXT_KEY, charSequence)
        }
    }

    fun setImportantForAccessibility(Boolean z) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setImportantForAccessibility(z)
        }
    }

    fun setInputType(Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setInputType(i)
        }
    }

    fun setLabelFor(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(view)
        }
    }

    fun setLabelFor(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(view, i)
        }
    }

    fun setLabeledBy(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(view)
        }
    }

    fun setLabeledBy(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(view, i)
        }
    }

    fun setLiveRegion(Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setLiveRegion(i)
        }
    }

    fun setLongClickable(Boolean z) {
        this.mInfo.setLongClickable(z)
    }

    fun setMaxTextLength(Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setMaxTextLength(i)
        }
    }

    fun setMovementGranularities(Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setMovementGranularities(i)
        }
    }

    fun setMultiLine(Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setMultiLine(z)
        }
    }

    fun setPackageName(CharSequence charSequence) {
        this.mInfo.setPackageName(charSequence)
    }

    fun setPaneTitle(@Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setPaneTitle(charSequence)
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(PANE_TITLE_KEY, charSequence)
        }
    }

    fun setParent(View view) {
        this.mInfo.setParent(view)
    }

    fun setParent(View view, Int i) {
        this.mParentVirtualDescendantId = i
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setParent(view, i)
        }
    }

    fun setPassword(Boolean z) {
        this.mInfo.setPassword(z)
    }

    fun setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) rangeInfoCompat.mInfo)
        }
    }

    fun setRoleDescription(@Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, charSequence)
        }
    }

    fun setScreenReaderFocusable(Boolean z) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setScreenReaderFocusable(z)
        } else {
            setBooleanProperty(1, z)
        }
    }

    fun setScrollable(Boolean z) {
        this.mInfo.setScrollable(z)
    }

    fun setSelected(Boolean z) {
        this.mInfo.setSelected(z)
    }

    fun setShowingHintText(Boolean z) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setShowingHintText(z)
        } else {
            setBooleanProperty(4, z)
        }
    }

    fun setSource(View view) {
        this.mInfo.setSource(view)
    }

    fun setSource(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setSource(view, i)
        }
    }

    fun setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence)
    }

    fun setTextSelection(Int i, Int i2) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setTextSelection(i, i2)
        }
    }

    fun setTooltipText(@Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setTooltipText(charSequence)
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(TOOLTIP_TEXT_KEY, charSequence)
        }
    }

    fun setTraversalAfter(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view)
        }
    }

    fun setTraversalAfter(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view, i)
        }
    }

    fun setTraversalBefore(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view)
        }
    }

    fun setTraversalBefore(View view, Int i) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view, i)
        }
    }

    fun setViewIdResourceName(String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setViewIdResourceName(str)
        }
    }

    fun setVisibleToUser(Boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setVisibleToUser(z)
        }
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append(super.toString())
        Rect rect = Rect()
        getBoundsInParent(rect)
        sb.append("; boundsInParent: " + rect)
        getBoundsInScreen(rect)
        sb.append("; boundsInScreen: " + rect)
        sb.append("; packageName: ").append(getPackageName())
        sb.append("; className: ").append(getClassName())
        sb.append("; text: ").append(getText())
        sb.append("; contentDescription: ").append(getContentDescription())
        sb.append("; viewId: ").append(getViewIdResourceName())
        sb.append("; checkable: ").append(isCheckable())
        sb.append("; checked: ").append(isChecked())
        sb.append("; focusable: ").append(isFocusable())
        sb.append("; focused: ").append(isFocused())
        sb.append("; selected: ").append(isSelected())
        sb.append("; clickable: ").append(isClickable())
        sb.append("; longClickable: ").append(isLongClickable())
        sb.append("; enabled: ").append(isEnabled())
        sb.append("; password: ").append(isPassword())
        sb.append("; scrollable: " + isScrollable())
        sb.append("; [")
        Int actions = getActions()
        while (actions != 0) {
            Int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions)
            actions &= iNumberOfTrailingZeros ^ (-1)
            sb.append(getActionSymbolicName(iNumberOfTrailingZeros))
            if (actions != 0) {
                sb.append(", ")
            }
        }
        sb.append("]")
        return sb.toString()
    }

    fun unwrap() {
        return this.mInfo
    }
}
