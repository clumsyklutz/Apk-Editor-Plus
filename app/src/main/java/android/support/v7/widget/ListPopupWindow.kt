package android.support.v7.widget

import android.content.Context
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.annotation.AttrRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import android.support.v4.view.PointerIconCompat
import androidx.core.view.ViewCompat
import android.support.v4.widget.PopupWindowCompat
import androidx.appcompat.R
import android.support.v7.view.menu.ShowableListMenu
import android.support.v7.widget.ActivityChooserView
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.PopupWindow
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ListPopupWindow implements ShowableListMenu {
    private static val DEBUG = false
    static val EXPAND_LIST_TIMEOUT = 250
    public static val INPUT_METHOD_FROM_FOCUSABLE = 0
    public static val INPUT_METHOD_NEEDED = 1
    public static val INPUT_METHOD_NOT_NEEDED = 2
    public static val MATCH_PARENT = -1
    public static val POSITION_PROMPT_ABOVE = 0
    public static val POSITION_PROMPT_BELOW = 1
    private static val TAG = "ListPopupWindow"
    public static val WRAP_CONTENT = -2
    private static Method sClipToWindowEnabledMethod
    private static Method sGetMaxAvailableHeightMethod
    private static Method sSetEpicenterBoundsMethod
    private ListAdapter mAdapter
    private Context mContext
    private Boolean mDropDownAlwaysVisible
    private View mDropDownAnchorView
    private Int mDropDownGravity
    private Int mDropDownHeight
    private Int mDropDownHorizontalOffset
    DropDownListView mDropDownList
    private Drawable mDropDownListHighlight
    private Int mDropDownVerticalOffset
    private Boolean mDropDownVerticalOffsetSet
    private Int mDropDownWidth
    private Int mDropDownWindowLayoutType
    private Rect mEpicenterBounds
    private Boolean mForceIgnoreOutsideTouch
    final Handler mHandler
    private final ListSelectorHider mHideSelector
    private Boolean mIsAnimatedFromAnchor
    private AdapterView.OnItemClickListener mItemClickListener
    private AdapterView.OnItemSelectedListener mItemSelectedListener
    Int mListItemExpandMaximum
    private Boolean mModal
    private DataSetObserver mObserver
    private Boolean mOverlapAnchor
    private Boolean mOverlapAnchorSet
    PopupWindow mPopup
    private Int mPromptPosition
    private View mPromptView
    final ResizePopupRunnable mResizePopupRunnable
    private final PopupScrollListener mScrollListener
    private Runnable mShowDropDownRunnable
    private final Rect mTempRect
    private final PopupTouchInterceptor mTouchInterceptor

    class ListSelectorHider implements Runnable {
        ListSelectorHider() {
        }

        @Override // java.lang.Runnable
        fun run() {
            ListPopupWindow.this.clearListSelection()
        }
    }

    class PopupDataSetObserver extends DataSetObserver {
        PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        fun onChanged() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show()
            }
        }

        @Override // android.database.DataSetObserver
        fun onInvalidated() {
            ListPopupWindow.this.dismiss()
        }
    }

    class PopupScrollListener implements AbsListView.OnScrollListener {
        PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        fun onScroll(AbsListView absListView, Int i, Int i2, Int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        fun onScrollStateChanged(AbsListView absListView, Int i) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (i != 1 || ListPopupWindow.this.isInputMethodNotNeeded() || ListPopupWindow.this.mPopup.getContentView() == null) {
                return
            }
            ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable)
            ListPopupWindow.this.mResizePopupRunnable.run()
        }
    }

    class PopupTouchInterceptor implements View.OnTouchListener {
        PopupTouchInterceptor() {
        }

        @Override // android.view.View.OnTouchListener
        fun onTouch(View view, MotionEvent motionEvent) {
            Int action = motionEvent.getAction()
            Int x = (Int) motionEvent.getX()
            Int y = (Int) motionEvent.getY()
            if (action == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && x >= 0 && x < ListPopupWindow.this.mPopup.getWidth() && y >= 0 && y < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250L)
                return false
            }
            if (action != 1) {
                return false
            }
            ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable)
            return false
        }
    }

    class ResizePopupRunnable implements Runnable {
        ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        fun run() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (ListPopupWindow.this.mDropDownList == null || !ViewCompat.isAttachedToWindow(ListPopupWindow.this.mDropDownList) || ListPopupWindow.this.mDropDownList.getCount() <= ListPopupWindow.this.mDropDownList.getChildCount() || ListPopupWindow.this.mDropDownList.getChildCount() > ListPopupWindow.this.mListItemExpandMaximum) {
                return
            }
            ListPopupWindow.this.mPopup.setInputMethodMode(2)
            ListPopupWindow.this.show()
        }
    }

    static {
        try {
            sClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE)
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.")
        }
        try {
            sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE)
        } catch (NoSuchMethodException e2) {
            Log.i(TAG, "Could not find method getMaxAvailableHeight(View, Int, Boolean) on PopupWindow. Oh well.")
        }
        try {
            sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class)
        } catch (NoSuchMethodException e3) {
            Log.i(TAG, "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.")
        }
    }

    constructor(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes Int i) {
        this(context, attributeSet, i, 0)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes Int i, @StyleRes Int i2) {
        this.mDropDownHeight = -2
        this.mDropDownWidth = -2
        this.mDropDownWindowLayoutType = PointerIconCompat.TYPE_HAND
        this.mIsAnimatedFromAnchor = true
        this.mDropDownGravity = 0
        this.mDropDownAlwaysVisible = false
        this.mForceIgnoreOutsideTouch = false
        this.mListItemExpandMaximum = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        this.mPromptPosition = 0
        this.mResizePopupRunnable = ResizePopupRunnable()
        this.mTouchInterceptor = PopupTouchInterceptor()
        this.mScrollListener = PopupScrollListener()
        this.mHideSelector = ListSelectorHider()
        this.mTempRect = Rect()
        this.mContext = context
        this.mHandler = Handler(context.getMainLooper())
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2)
        this.mDropDownHorizontalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0)
        this.mDropDownVerticalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0)
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true
        }
        typedArrayObtainStyledAttributes.recycle()
        this.mPopup = AppCompatPopupWindow(context, attributeSet, i, i2)
        this.mPopup.setInputMethodMode(1)
    }

    private fun buildDropDown() {
        Int measuredHeight
        Int i
        Int iMakeMeasureSpec
        View view
        Int measuredHeight2
        Int i2
        Int i3
        if (this.mDropDownList == null) {
            Context context = this.mContext
            this.mShowDropDownRunnable = Runnable() { // from class: android.support.v7.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                fun run() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                    View anchorView = ListPopupWindow.this.getAnchorView()
                    if (anchorView == null || anchorView.getWindowToken() == null) {
                        return
                    }
                    ListPopupWindow.this.show()
                }
            }
            this.mDropDownList = createDropDownListView(context, !this.mModal)
            if (this.mDropDownListHighlight != null) {
                this.mDropDownList.setSelector(this.mDropDownListHighlight)
            }
            this.mDropDownList.setAdapter(this.mAdapter)
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener)
            this.mDropDownList.setFocusable(true)
            this.mDropDownList.setFocusableInTouchMode(true)
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: android.support.v7.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                fun onItemSelected(AdapterView adapterView, View view2, Int i4, Long j) {
                    DropDownListView dropDownListView
                    if (i4 == -1 || (dropDownListView = ListPopupWindow.this.mDropDownList) == null) {
                        return
                    }
                    dropDownListView.setListSelectionHidden(false)
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                fun onNothingSelected(AdapterView adapterView) {
                }
            })
            this.mDropDownList.setOnScrollListener(this.mScrollListener)
            if (this.mItemSelectedListener != null) {
                this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener)
            }
            View view2 = this.mDropDownList
            View view3 = this.mPromptView
            if (view3 != null) {
                LinearLayout linearLayout = LinearLayout(context)
                linearLayout.setOrientation(1)
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f)
                switch (this.mPromptPosition) {
                    case 0:
                        linearLayout.addView(view3)
                        linearLayout.addView(view2, layoutParams)
                        break
                    case 1:
                        linearLayout.addView(view2, layoutParams)
                        linearLayout.addView(view3)
                        break
                    default:
                        Log.e(TAG, "Invalid hint position " + this.mPromptPosition)
                        break
                }
                if (this.mDropDownWidth >= 0) {
                    i3 = this.mDropDownWidth
                    i2 = Integer.MIN_VALUE
                } else {
                    i2 = 0
                    i3 = 0
                }
                view3.measure(View.MeasureSpec.makeMeasureSpec(i3, i2), 0)
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view3.getLayoutParams()
                measuredHeight2 = layoutParams2.bottomMargin + view3.getMeasuredHeight() + layoutParams2.topMargin
                view = linearLayout
            } else {
                view = view2
                measuredHeight2 = 0
            }
            this.mPopup.setContentView(view)
            measuredHeight = measuredHeight2
        } else {
            this.mPopup.getContentView()
            View view4 = this.mPromptView
            if (view4 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view4.getLayoutParams()
                measuredHeight = layoutParams3.bottomMargin + view4.getMeasuredHeight() + layoutParams3.topMargin
            } else {
                measuredHeight = 0
            }
        }
        Drawable background = this.mPopup.getBackground()
        if (background != null) {
            background.getPadding(this.mTempRect)
            Int i4 = this.mTempRect.top + this.mTempRect.bottom
            if (this.mDropDownVerticalOffsetSet) {
                i = i4
            } else {
                this.mDropDownVerticalOffset = -this.mTempRect.top
                i = i4
            }
        } else {
            this.mTempRect.setEmpty()
            i = 0
        }
        Int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.mPopup.getInputMethodMode() == 2)
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return maxAvailableHeight + i
        }
        switch (this.mDropDownWidth) {
            case -2:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE)
                break
            case -1:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824)
                break
            default:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824)
                break
        }
        Int iMeasureHeightOfChildrenCompat = this.mDropDownList.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1)
        if (iMeasureHeightOfChildrenCompat > 0) {
            measuredHeight += this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom() + i
        }
        return iMeasureHeightOfChildrenCompat + measuredHeight
    }

    private fun getMaxAvailableHeight(View view, Int i, Boolean z) {
        if (sGetMaxAvailableHeightMethod != null) {
            try {
                return ((Integer) sGetMaxAvailableHeightMethod.invoke(this.mPopup, view, Integer.valueOf(i), Boolean.valueOf(z))).intValue()
            } catch (Exception e) {
                Log.i(TAG, "Could not call getMaxAvailableHeightMethod(View, Int, Boolean) on PopupWindow. Using the public version.")
            }
        }
        return this.mPopup.getMaxAvailableHeight(view, i)
    }

    private fun isConfirmKey(Int i) {
        return i == 66 || i == 23
    }

    private fun removePromptView() {
        if (this.mPromptView != null) {
            ViewParent parent = this.mPromptView.getParent()
            if (parent is ViewGroup) {
                ((ViewGroup) parent).removeView(this.mPromptView)
            }
        }
    }

    private fun setPopupClipToScreenEnabled(Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sClipToWindowEnabledMethod != null) {
            try {
                sClipToWindowEnabledMethod.invoke(this.mPopup, Boolean.valueOf(z))
            } catch (Exception e) {
                Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.")
            }
        }
    }

    fun clearListSelection() {
        DropDownListView dropDownListView = this.mDropDownList
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true)
            dropDownListView.requestLayout()
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return ForwardingListener(view) { // from class: android.support.v7.widget.ListPopupWindow.1
            @Override // android.support.v7.widget.ForwardingListener
            fun getPopup() {
                return ListPopupWindow.this
            }
        }
    }

    @NonNull
    DropDownListView createDropDownListView(Context context, Boolean z) {
        return DropDownListView(context, z)
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    fun dismiss() {
        this.mPopup.dismiss()
        removePromptView()
        this.mPopup.setContentView(null)
        this.mDropDownList = null
        this.mHandler.removeCallbacks(this.mResizePopupRunnable)
    }

    @Nullable
    fun getAnchorView() {
        return this.mDropDownAnchorView
    }

    @StyleRes
    fun getAnimationStyle() {
        return this.mPopup.getAnimationStyle()
    }

    @Nullable
    fun getBackground() {
        return this.mPopup.getBackground()
    }

    fun getHeight() {
        return this.mDropDownHeight
    }

    fun getHorizontalOffset() {
        return this.mDropDownHorizontalOffset
    }

    fun getInputMethodMode() {
        return this.mPopup.getInputMethodMode()
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    @Nullable
    fun getListView() {
        return this.mDropDownList
    }

    fun getPromptPosition() {
        return this.mPromptPosition
    }

    @Nullable
    fun getSelectedItem() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItem()
        }
        return null
    }

    fun getSelectedItemId() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItemId()
        }
        return Long.MIN_VALUE
    }

    fun getSelectedItemPosition() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItemPosition()
        }
        return -1
    }

    @Nullable
    fun getSelectedView() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedView()
        }
        return null
    }

    fun getSoftInputMode() {
        return this.mPopup.getSoftInputMode()
    }

    fun getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) {
            return this.mDropDownVerticalOffset
        }
        return 0
    }

    fun getWidth() {
        return this.mDropDownWidth
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible
    }

    fun isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2
    }

    fun isModal() {
        return this.mModal
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    fun isShowing() {
        return this.mPopup.isShowing()
    }

    fun onKeyDown(Int i, @NonNull KeyEvent keyEvent) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (isShowing() && i != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !isConfirmKey(i))) {
            Int selectedItemPosition = this.mDropDownList.getSelectedItemPosition()
            Boolean z = !this.mPopup.isAboveAnchor()
            ListAdapter listAdapter = this.mAdapter
            Int i2 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
            Int count = Integer.MIN_VALUE
            if (listAdapter != null) {
                Boolean zAreAllItemsEnabled = listAdapter.areAllItemsEnabled()
                Int iLookForSelectablePosition = zAreAllItemsEnabled ? 0 : this.mDropDownList.lookForSelectablePosition(0, true)
                count = zAreAllItemsEnabled ? listAdapter.getCount() - 1 : this.mDropDownList.lookForSelectablePosition(listAdapter.getCount() - 1, false)
                i2 = iLookForSelectablePosition
            }
            if ((z && i == 19 && selectedItemPosition <= i2) || (!z && i == 20 && selectedItemPosition >= count)) {
                clearListSelection()
                this.mPopup.setInputMethodMode(1)
                show()
                return true
            }
            this.mDropDownList.setListSelectionHidden(false)
            if (this.mDropDownList.onKeyDown(i, keyEvent)) {
                this.mPopup.setInputMethodMode(2)
                this.mDropDownList.requestFocusFromTouch()
                show()
                switch (i) {
                    case 19:
                    case 20:
                    case 23:
                    case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                        break
                }
                return true
            }
            if (z && i == 20) {
                if (selectedItemPosition == count) {
                    return true
                }
            } else if (!z && i == 19 && selectedItemPosition == i2) {
                return true
            }
        }
        return false
    }

    fun onKeyPreIme(Int i, @NonNull KeyEvent keyEvent) {
        if (i == 4 && isShowing()) {
            View view = this.mDropDownAnchorView
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState keyDispatcherState = view.getKeyDispatcherState()
                if (keyDispatcherState == null) {
                    return true
                }
                keyDispatcherState.startTracking(keyEvent, this)
                return true
            }
            if (keyEvent.getAction() == 1) {
                KeyEvent.DispatcherState keyDispatcherState2 = view.getKeyDispatcherState()
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.handleUpEvent(keyEvent)
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    dismiss()
                    return true
                }
            }
        }
        return false
    }

    fun onKeyUp(Int i, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.mDropDownList.getSelectedItemPosition() < 0) {
            return false
        }
        Boolean zOnKeyUp = this.mDropDownList.onKeyUp(i, keyEvent)
        if (!zOnKeyUp || !isConfirmKey(i)) {
            return zOnKeyUp
        }
        dismiss()
        return zOnKeyUp
    }

    fun performItemClick(Int i) {
        if (!isShowing()) {
            return false
        }
        if (this.mItemClickListener != null) {
            DropDownListView dropDownListView = this.mDropDownList
            this.mItemClickListener.onItemClick(dropDownListView, dropDownListView.getChildAt(i - dropDownListView.getFirstVisiblePosition()), i, dropDownListView.getAdapter().getItemId(i))
        }
        return true
    }

    fun postShow() {
        this.mHandler.post(this.mShowDropDownRunnable)
    }

    fun setAdapter(@Nullable ListAdapter listAdapter) {
        if (this.mObserver == null) {
            this.mObserver = PopupDataSetObserver()
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver)
        }
        this.mAdapter = listAdapter
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver)
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter)
        }
    }

    fun setAnchorView(@Nullable View view) {
        this.mDropDownAnchorView = view
    }

    fun setAnimationStyle(@StyleRes Int i) {
        this.mPopup.setAnimationStyle(i)
    }

    fun setBackgroundDrawable(@Nullable Drawable drawable) {
        this.mPopup.setBackgroundDrawable(drawable)
    }

    fun setContentWidth(Int i) {
        Drawable background = this.mPopup.getBackground()
        if (background == null) {
            setWidth(i)
        } else {
            background.getPadding(this.mTempRect)
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + i
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setDropDownAlwaysVisible(Boolean z) {
        this.mDropDownAlwaysVisible = z
    }

    fun setDropDownGravity(Int i) {
        this.mDropDownGravity = i
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setForceIgnoreOutsideTouch(Boolean z) {
        this.mForceIgnoreOutsideTouch = z
    }

    fun setHeight(Int i) {
        if (i < 0 && -2 != i && -1 != i) {
            throw IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.")
        }
        this.mDropDownHeight = i
    }

    fun setHorizontalOffset(Int i) {
        this.mDropDownHorizontalOffset = i
    }

    fun setInputMethodMode(Int i) {
        this.mPopup.setInputMethodMode(i)
    }

    Unit setListItemExpandMax(Int i) {
        this.mListItemExpandMaximum = i
    }

    fun setListSelector(Drawable drawable) {
        this.mDropDownListHighlight = drawable
    }

    fun setModal(Boolean z) {
        this.mModal = z
        this.mPopup.setFocusable(z)
    }

    fun setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOnDismissListener(onDismissListener)
    }

    fun setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener
    }

    fun setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setOverlapAnchor(Boolean z) {
        this.mOverlapAnchorSet = true
        this.mOverlapAnchor = z
    }

    fun setPromptPosition(Int i) {
        this.mPromptPosition = i
    }

    fun setPromptView(@Nullable View view) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Boolean zIsShowing = isShowing()
        if (zIsShowing) {
            removePromptView()
        }
        this.mPromptView = view
        if (zIsShowing) {
            show()
        }
    }

    fun setSelection(Int i) {
        DropDownListView dropDownListView = this.mDropDownList
        if (!isShowing() || dropDownListView == null) {
            return
        }
        dropDownListView.setListSelectionHidden(false)
        dropDownListView.setSelection(i)
        if (dropDownListView.getChoiceMode() != 0) {
            dropDownListView.setItemChecked(i, true)
        }
    }

    fun setSoftInputMode(Int i) {
        this.mPopup.setSoftInputMode(i)
    }

    fun setVerticalOffset(Int i) {
        this.mDropDownVerticalOffset = i
        this.mDropDownVerticalOffsetSet = true
    }

    fun setWidth(Int i) {
        this.mDropDownWidth = i
    }

    fun setWindowLayoutType(Int i) {
        this.mDropDownWindowLayoutType = i
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    fun show() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int i
        Boolean z = false
        Int iBuildDropDown = buildDropDown()
        Boolean zIsInputMethodNotNeeded = isInputMethodNotNeeded()
        PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType)
        if (!this.mPopup.isShowing()) {
            Int width = this.mDropDownWidth == -1 ? -1 : this.mDropDownWidth == -2 ? getAnchorView().getWidth() : this.mDropDownWidth
            if (this.mDropDownHeight == -1) {
                iBuildDropDown = -1
            } else if (this.mDropDownHeight != -2) {
                iBuildDropDown = this.mDropDownHeight
            }
            this.mPopup.setWidth(width)
            this.mPopup.setHeight(iBuildDropDown)
            setPopupClipToScreenEnabled(true)
            this.mPopup.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true)
            this.mPopup.setTouchInterceptor(this.mTouchInterceptor)
            if (this.mOverlapAnchorSet) {
                PopupWindowCompat.setOverlapAnchor(this.mPopup, this.mOverlapAnchor)
            }
            if (sSetEpicenterBoundsMethod != null) {
                try {
                    sSetEpicenterBoundsMethod.invoke(this.mPopup, this.mEpicenterBounds)
                } catch (Exception e) {
                    Log.e(TAG, "Could not invoke setEpicenterBounds on PopupWindow", e)
                }
            }
            PopupWindowCompat.showAsDropDown(this.mPopup, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity)
            this.mDropDownList.setSelection(-1)
            if (!this.mModal || this.mDropDownList.isInTouchMode()) {
                clearListSelection()
            }
            if (this.mModal) {
                return
            }
            this.mHandler.post(this.mHideSelector)
            return
        }
        if (ViewCompat.isAttachedToWindow(getAnchorView())) {
            Int width2 = this.mDropDownWidth == -1 ? -1 : this.mDropDownWidth == -2 ? getAnchorView().getWidth() : this.mDropDownWidth
            if (this.mDropDownHeight == -1) {
                if (!zIsInputMethodNotNeeded) {
                    iBuildDropDown = -1
                }
                if (zIsInputMethodNotNeeded) {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0)
                    this.mPopup.setHeight(0)
                    i = iBuildDropDown
                } else {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0)
                    this.mPopup.setHeight(-1)
                    i = iBuildDropDown
                }
            } else {
                i = this.mDropDownHeight == -2 ? iBuildDropDown : this.mDropDownHeight
            }
            PopupWindow popupWindow = this.mPopup
            if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
                z = true
            }
            popupWindow.setOutsideTouchable(z)
            PopupWindow popupWindow2 = this.mPopup
            View anchorView = getAnchorView()
            Int i2 = this.mDropDownHorizontalOffset
            Int i3 = this.mDropDownVerticalOffset
            if (width2 < 0) {
                width2 = -1
            }
            popupWindow2.update(anchorView, i2, i3, width2, i >= 0 ? i : -1)
        }
    }
}
