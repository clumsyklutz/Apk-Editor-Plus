package android.support.v7.widget

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v4.view.ActionProvider
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.appcompat.R
import android.support.v7.view.menu.ShowableListMenu
import android.support.v7.widget.ActivityChooserModel
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import java.lang.reflect.InvocationTargetException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
    private static val LOG_TAG = "ActivityChooserView"
    private final View mActivityChooserContent
    private final Drawable mActivityChooserContentBackground
    final ActivityChooserViewAdapter mAdapter
    private final Callbacks mCallbacks
    private Int mDefaultActionButtonContentDescription
    final FrameLayout mDefaultActivityButton
    private final ImageView mDefaultActivityButtonImage
    final FrameLayout mExpandActivityOverflowButton
    private final ImageView mExpandActivityOverflowButtonImage
    Int mInitialActivityCount
    private Boolean mIsAttachedToWindow
    Boolean mIsSelectingDefaultActivity
    private final Int mListPopupMaxWidth
    private ListPopupWindow mListPopupWindow
    final DataSetObserver mModelDataSetObserver
    PopupWindow.OnDismissListener mOnDismissListener
    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener
    ActionProvider mProvider

    class ActivityChooserViewAdapter extends BaseAdapter {
        private static val ITEM_VIEW_TYPE_ACTIVITY = 0
        private static val ITEM_VIEW_TYPE_COUNT = 3
        private static val ITEM_VIEW_TYPE_FOOTER = 1
        public static val MAX_ACTIVITY_COUNT_DEFAULT = 4
        public static val MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE
        private ActivityChooserModel mDataModel
        private Boolean mHighlightDefaultActivity
        private Int mMaxActivityCount = 4
        private Boolean mShowDefaultActivity
        private Boolean mShowFooterView

        ActivityChooserViewAdapter() {
        }

        fun getActivityCount() {
            return this.mDataModel.getActivityCount()
        }

        @Override // android.widget.Adapter
        fun getCount() {
            Int activityCount = this.mDataModel.getActivityCount()
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                activityCount--
            }
            Int iMin = Math.min(activityCount, this.mMaxActivityCount)
            return this.mShowFooterView ? iMin + 1 : iMin
        }

        fun getDataModel() {
            return this.mDataModel
        }

        fun getDefaultActivity() {
            return this.mDataModel.getDefaultActivity()
        }

        fun getHistorySize() {
            return this.mDataModel.getHistorySize()
        }

        @Override // android.widget.Adapter
        fun getItem(Int i) {
            switch (getItemViewType(i)) {
                case 0:
                    if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                        i++
                    }
                    return this.mDataModel.getActivity(i)
                case 1:
                    return null
                default:
                    throw IllegalArgumentException()
            }
        }

        @Override // android.widget.Adapter
        fun getItemId(Int i) {
            return i
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        fun getItemViewType(Int i) {
            return (this.mShowFooterView && i == getCount() + (-1)) ? 1 : 0
        }

        fun getShowDefaultActivity() {
            return this.mShowDefaultActivity
        }

        @Override // android.widget.Adapter
        fun getView(Int i, View view, ViewGroup viewGroup) {
            switch (getItemViewType(i)) {
                case 0:
                    if (view == null || view.getId() != R.id.list_item) {
                        view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false)
                    }
                    PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager()
                    ImageView imageView = (ImageView) view.findViewById(R.id.icon)
                    ResolveInfo resolveInfo = (ResolveInfo) getItem(i)
                    imageView.setImageDrawable(resolveInfo.loadIcon(packageManager))
                    ((TextView) view.findViewById(R.id.title)).setText(resolveInfo.loadLabel(packageManager))
                    if (this.mShowDefaultActivity && i == 0 && this.mHighlightDefaultActivity) {
                        view.setActivated(true)
                        return view
                    }
                    view.setActivated(false)
                    return view
                case 1:
                    if (view != null && view.getId() == 1) {
                        return view
                    }
                    View viewInflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false)
                    viewInflate.setId(1)
                    ((TextView) viewInflate.findViewById(R.id.title)).setText(ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all))
                    return viewInflate
                default:
                    throw IllegalArgumentException()
            }
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        fun getViewTypeCount() {
            return 3
        }

        fun measureContentWidth() {
            Int i = this.mMaxActivityCount
            this.mMaxActivityCount = MAX_ACTIVITY_COUNT_UNLIMITED
            Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
            Int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0)
            Int count = getCount()
            View view = null
            Int iMax = 0
            for (Int i2 = 0; i2 < count; i2++) {
                view = getView(i2, view, null)
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
                iMax = Math.max(iMax, view.getMeasuredWidth())
            }
            this.mMaxActivityCount = i
            return iMax
        }

        fun setDataModel(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel dataModel = ActivityChooserView.this.mAdapter.getDataModel()
            if (dataModel != null && ActivityChooserView.this.isShown()) {
                dataModel.unregisterObserver(ActivityChooserView.this.mModelDataSetObserver)
            }
            this.mDataModel = activityChooserModel
            if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver(ActivityChooserView.this.mModelDataSetObserver)
            }
            notifyDataSetChanged()
        }

        fun setMaxActivityCount(Int i) {
            if (this.mMaxActivityCount != i) {
                this.mMaxActivityCount = i
                notifyDataSetChanged()
            }
        }

        fun setShowDefaultActivity(Boolean z, Boolean z2) {
            if (this.mShowDefaultActivity == z && this.mHighlightDefaultActivity == z2) {
                return
            }
            this.mShowDefaultActivity = z
            this.mHighlightDefaultActivity = z2
            notifyDataSetChanged()
        }

        fun setShowFooterView(Boolean z) {
            if (this.mShowFooterView != z) {
                this.mShowFooterView = z
                notifyDataSetChanged()
            }
        }
    }

    class Callbacks implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
        Callbacks() {
        }

        private fun notifyOnDismissListener() {
            if (ActivityChooserView.this.mOnDismissListener != null) {
                ActivityChooserView.this.mOnDismissListener.onDismiss()
            }
        }

        @Override // android.view.View.OnClickListener
        fun onClick(View view) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (view != ActivityChooserView.this.mDefaultActivityButton) {
                if (view != ActivityChooserView.this.mExpandActivityOverflowButton) {
                    throw IllegalArgumentException()
                }
                ActivityChooserView.this.mIsSelectingDefaultActivity = false
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount)
                return
            }
            ActivityChooserView.this.dismissPopup()
            Intent intentChooseActivity = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(ActivityChooserView.this.mAdapter.getDefaultActivity()))
            if (intentChooseActivity != null) {
                intentChooseActivity.addFlags(524288)
                ActivityChooserView.this.getContext().startActivity(intentChooseActivity)
            }
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        fun onDismiss() {
            notifyOnDismissListener()
            if (ActivityChooserView.this.mProvider != null) {
                ActivityChooserView.this.mProvider.subUiVisibilityChanged(false)
            }
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        fun onItemClick(AdapterView adapterView, View view, Int i, Long j) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            switch (((ActivityChooserViewAdapter) adapterView.getAdapter()).getItemViewType(i)) {
                case 0:
                    ActivityChooserView.this.dismissPopup()
                    if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
                        if (i > 0) {
                            ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(i)
                            return
                        }
                        return
                    }
                    if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                        i++
                    }
                    Intent intentChooseActivity = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(i)
                    if (intentChooseActivity != null) {
                        intentChooseActivity.addFlags(524288)
                        ActivityChooserView.this.getContext().startActivity(intentChooseActivity)
                        return
                    }
                    return
                case 1:
                    ActivityChooserView.this.showPopupUnchecked(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
                    return
                default:
                    throw IllegalArgumentException()
            }
        }

        @Override // android.view.View.OnLongClickListener
        fun onLongClick(View view) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (view != ActivityChooserView.this.mDefaultActivityButton) {
                throw IllegalArgumentException()
            }
            if (ActivityChooserView.this.mAdapter.getCount() > 0) {
                ActivityChooserView.this.mIsSelectingDefaultActivity = true
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount)
            }
            return true
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class InnerLayout extends LinearLayout {
        private static final Array<Int> TINT_ATTRS = {android.R.attr.background}

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, TINT_ATTRS)
            setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0))
            tintTypedArrayObtainStyledAttributes.recycle()
        }
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mModelDataSetObserver = DataSetObserver() { // from class: android.support.v7.widget.ActivityChooserView.1
            @Override // android.database.DataSetObserver
            fun onChanged() {
                super.onChanged()
                ActivityChooserView.this.mAdapter.notifyDataSetChanged()
            }

            @Override // android.database.DataSetObserver
            fun onInvalidated() {
                super.onInvalidated()
                ActivityChooserView.this.mAdapter.notifyDataSetInvalidated()
            }
        }
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.support.v7.widget.ActivityChooserView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            fun onGlobalLayout() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (ActivityChooserView.this.isShowingPopup()) {
                    if (!ActivityChooserView.this.isShown()) {
                        ActivityChooserView.this.getListPopupWindow().dismiss()
                        return
                    }
                    ActivityChooserView.this.getListPopupWindow().show()
                    if (ActivityChooserView.this.mProvider != null) {
                        ActivityChooserView.this.mProvider.subUiVisibilityChanged(true)
                    }
                }
            }
        }
        this.mInitialActivityCount = 4
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActivityChooserView, i, 0)
        this.mInitialActivityCount = typedArrayObtainStyledAttributes.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4)
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable)
        typedArrayObtainStyledAttributes.recycle()
        LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, (ViewGroup) this, true)
        this.mCallbacks = Callbacks()
        this.mActivityChooserContent = findViewById(R.id.activity_chooser_view_content)
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground()
        this.mDefaultActivityButton = (FrameLayout) findViewById(R.id.default_activity_button)
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks)
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks)
        this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(R.id.image)
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.expand_activities_button)
        frameLayout.setOnClickListener(this.mCallbacks)
        frameLayout.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: android.support.v7.widget.ActivityChooserView.3
            @Override // android.view.View.AccessibilityDelegate
            fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo)
                AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCanOpenPopup(true)
            }
        })
        frameLayout.setOnTouchListener(ForwardingListener(frameLayout) { // from class: android.support.v7.widget.ActivityChooserView.4
            @Override // android.support.v7.widget.ForwardingListener
            fun getPopup() {
                return ActivityChooserView.this.getListPopupWindow()
            }

            @Override // android.support.v7.widget.ForwardingListener
            protected fun onForwardingStarted() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                ActivityChooserView.this.showPopup()
                return true
            }

            @Override // android.support.v7.widget.ForwardingListener
            protected fun onForwardingStopped() {
                ActivityChooserView.this.dismissPopup()
                return true
            }
        })
        this.mExpandActivityOverflowButton = frameLayout
        this.mExpandActivityOverflowButtonImage = (ImageView) frameLayout.findViewById(R.id.image)
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable)
        this.mAdapter = ActivityChooserViewAdapter()
        this.mAdapter.registerDataSetObserver(DataSetObserver() { // from class: android.support.v7.widget.ActivityChooserView.5
            @Override // android.database.DataSetObserver
            fun onChanged() {
                super.onChanged()
                ActivityChooserView.this.updateAppearance()
            }
        })
        Resources resources = context.getResources()
        this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth))
    }

    fun dismissPopup() {
        if (!isShowingPopup()) {
            return true
        }
        getListPopupWindow().dismiss()
        ViewTreeObserver viewTreeObserver = getViewTreeObserver()
        if (!viewTreeObserver.isAlive()) {
            return true
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener)
        return true
    }

    fun getDataModel() {
        return this.mAdapter.getDataModel()
    }

    ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = ListPopupWindow(getContext())
            this.mListPopupWindow.setAdapter(this.mAdapter)
            this.mListPopupWindow.setAnchorView(this)
            this.mListPopupWindow.setModal(true)
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks)
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks)
        }
        return this.mListPopupWindow
    }

    fun isShowingPopup() {
        return getListPopupWindow().isShowing()
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ActivityChooserModel dataModel = this.mAdapter.getDataModel()
        if (dataModel != null) {
            dataModel.registerObserver(this.mModelDataSetObserver)
        }
        this.mIsAttachedToWindow = true
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ActivityChooserModel dataModel = this.mAdapter.getDataModel()
        if (dataModel != null) {
            dataModel.unregisterObserver(this.mModelDataSetObserver)
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver()
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener)
        }
        if (isShowingPopup()) {
            dismissPopup()
        }
        this.mIsAttachedToWindow = false
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        this.mActivityChooserContent.layout(0, 0, i3 - i, i4 - i2)
        if (isShowingPopup()) {
            return
        }
        dismissPopup()
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        View view = this.mActivityChooserContent
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824)
        }
        measureChild(view, i, i2)
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight())
    }

    @Override // android.support.v7.widget.ActivityChooserModel.ActivityChooserModelClient
    fun setActivityChooserModel(ActivityChooserModel activityChooserModel) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.mAdapter.setDataModel(activityChooserModel)
        if (isShowingPopup()) {
            dismissPopup()
            showPopup()
        }
    }

    fun setDefaultActionButtonContentDescription(Int i) {
        this.mDefaultActionButtonContentDescription = i
    }

    fun setExpandActivityOverflowButtonContentDescription(Int i) {
        this.mExpandActivityOverflowButtonImage.setContentDescription(getContext().getString(i))
    }

    fun setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable)
    }

    fun setInitialActivityCount(Int i) {
        this.mInitialActivityCount = i
    }

    fun setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setProvider(ActionProvider actionProvider) {
        this.mProvider = actionProvider
    }

    fun showPopup() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (isShowingPopup() || !this.mIsAttachedToWindow) {
            return false
        }
        this.mIsSelectingDefaultActivity = false
        showPopupUnchecked(this.mInitialActivityCount)
        return true
    }

    Unit showPopupUnchecked(Int i) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.mAdapter.getDataModel() == null) {
            throw IllegalStateException("No data model. Did you call #setDataModel?")
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener)
        Boolean z = this.mDefaultActivityButton.getVisibility() == 0
        Int activityCount = this.mAdapter.getActivityCount()
        Int i2 = z ? 1 : 0
        if (i == Integer.MAX_VALUE || activityCount <= i2 + i) {
            this.mAdapter.setShowFooterView(false)
            this.mAdapter.setMaxActivityCount(i)
        } else {
            this.mAdapter.setShowFooterView(true)
            this.mAdapter.setMaxActivityCount(i - 1)
        }
        ListPopupWindow listPopupWindow = getListPopupWindow()
        if (listPopupWindow.isShowing()) {
            return
        }
        if (this.mIsSelectingDefaultActivity || !z) {
            this.mAdapter.setShowDefaultActivity(true, z)
        } else {
            this.mAdapter.setShowDefaultActivity(false, false)
        }
        listPopupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth))
        listPopupWindow.show()
        if (this.mProvider != null) {
            this.mProvider.subUiVisibilityChanged(true)
        }
        listPopupWindow.getListView().setContentDescription(getContext().getString(R.string.abc_activitychooserview_choose_application))
        listPopupWindow.getListView().setSelector(ColorDrawable(0))
    }

    Unit updateAppearance() {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true)
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false)
        }
        Int activityCount = this.mAdapter.getActivityCount()
        Int historySize = this.mAdapter.getHistorySize()
        if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
            this.mDefaultActivityButton.setVisibility(0)
            ResolveInfo defaultActivity = this.mAdapter.getDefaultActivity()
            PackageManager packageManager = getContext().getPackageManager()
            this.mDefaultActivityButtonImage.setImageDrawable(defaultActivity.loadIcon(packageManager))
            if (this.mDefaultActionButtonContentDescription != 0) {
                this.mDefaultActivityButton.setContentDescription(getContext().getString(this.mDefaultActionButtonContentDescription, defaultActivity.loadLabel(packageManager)))
            }
        } else {
            this.mDefaultActivityButton.setVisibility(8)
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground)
        } else {
            this.mActivityChooserContent.setBackgroundDrawable(null)
        }
    }
}
