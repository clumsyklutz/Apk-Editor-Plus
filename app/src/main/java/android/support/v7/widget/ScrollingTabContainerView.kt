package android.support.v7.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v7.app.ActionBar
import androidx.appcompat.R
import android.support.v7.view.ActionBarPolicy
import android.support.v7.widget.LinearLayoutCompat
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.ViewPropertyAnimator
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ScrollingTabContainerView extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {
    private static val FADE_DURATION = 200
    private static val TAG = "ScrollingTabContainerView"
    private static val sAlphaInterpolator = DecelerateInterpolator()
    private Boolean mAllowCollapse
    private Int mContentHeight
    Int mMaxTabWidth
    private Int mSelectedTabIndex
    Int mStackedTabMaxWidth
    private TabClickListener mTabClickListener
    LinearLayoutCompat mTabLayout
    Runnable mTabSelector
    private Spinner mTabSpinner
    protected final VisibilityAnimListener mVisAnimListener
    protected ViewPropertyAnimator mVisibilityAnim

    class TabAdapter extends BaseAdapter {
        TabAdapter() {
        }

        @Override // android.widget.Adapter
        fun getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount()
        }

        @Override // android.widget.Adapter
        fun getItem(Int i) {
            return ((TabView) ScrollingTabContainerView.this.mTabLayout.getChildAt(i)).getTab()
        }

        @Override // android.widget.Adapter
        fun getItemId(Int i) {
            return i
        }

        @Override // android.widget.Adapter
        fun getView(Int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.createTabView((ActionBar.Tab) getItem(i), true)
            }
            ((TabView) view).bindTab((ActionBar.Tab) getItem(i))
            return view
        }
    }

    class TabClickListener implements View.OnClickListener {
        TabClickListener() {
        }

        @Override // android.view.View.OnClickListener
        fun onClick(View view) {
            ((TabView) view).getTab().select()
            Int childCount = ScrollingTabContainerView.this.mTabLayout.getChildCount()
            for (Int i = 0; i < childCount; i++) {
                View childAt = ScrollingTabContainerView.this.mTabLayout.getChildAt(i)
                childAt.setSelected(childAt == view)
            }
        }
    }

    class TabView extends LinearLayout {
        private final Array<Int> BG_ATTRS
        private View mCustomView
        private ImageView mIconView
        private ActionBar.Tab mTab
        private TextView mTextView

        constructor(Context context, ActionBar.Tab tab, Boolean z) {
            super(context, null, R.attr.actionBarTabStyle)
            this.BG_ATTRS = new Array<Int>{android.R.attr.background}
            this.mTab = tab
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, null, this.BG_ATTRS, R.attr.actionBarTabStyle, 0)
            if (tintTypedArrayObtainStyledAttributes.hasValue(0)) {
                setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0))
            }
            tintTypedArrayObtainStyledAttributes.recycle()
            if (z) {
                setGravity(8388627)
            }
            update()
        }

        fun bindTab(ActionBar.Tab tab) {
            this.mTab = tab
            update()
        }

        public ActionBar.Tab getTab() {
            return this.mTab
        }

        @Override // android.view.View
        fun onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent)
            accessibilityEvent.setClassName(ActionBar.Tab.class.getName())
        }

        @Override // android.view.View
        fun onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo)
            accessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName())
        }

        @Override // android.widget.LinearLayout, android.view.View
        fun onMeasure(Int i, Int i2) {
            super.onMeasure(i, i2)
            if (ScrollingTabContainerView.this.mMaxTabWidth <= 0 || getMeasuredWidth() <= ScrollingTabContainerView.this.mMaxTabWidth) {
                return
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), i2)
        }

        @Override // android.view.View
        fun setSelected(Boolean z) {
            Boolean z2 = isSelected() != z
            super.setSelected(z)
            if (z2 && z) {
                sendAccessibilityEvent(4)
            }
        }

        fun update() {
            ActionBar.Tab tab = this.mTab
            View customView = tab.getCustomView()
            if (customView != null) {
                ViewParent parent = customView.getParent()
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView)
                    }
                    addView(customView)
                }
                this.mCustomView = customView
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8)
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8)
                    this.mIconView.setImageDrawable(null)
                    return
                }
                return
            }
            if (this.mCustomView != null) {
                removeView(this.mCustomView)
                this.mCustomView = null
            }
            Drawable icon = tab.getIcon()
            CharSequence text = tab.getText()
            if (icon != null) {
                if (this.mIconView == null) {
                    AppCompatImageView appCompatImageView = AppCompatImageView(getContext())
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2)
                    layoutParams.gravity = 16
                    appCompatImageView.setLayoutParams(layoutParams)
                    addView(appCompatImageView, 0)
                    this.mIconView = appCompatImageView
                }
                this.mIconView.setImageDrawable(icon)
                this.mIconView.setVisibility(0)
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8)
                this.mIconView.setImageDrawable(null)
            }
            Boolean z = !TextUtils.isEmpty(text)
            if (z) {
                if (this.mTextView == null) {
                    AppCompatTextView appCompatTextView = AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle)
                    appCompatTextView.setEllipsize(TextUtils.TruncateAt.END)
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2)
                    layoutParams2.gravity = 16
                    appCompatTextView.setLayoutParams(layoutParams2)
                    addView(appCompatTextView)
                    this.mTextView = appCompatTextView
                }
                this.mTextView.setText(text)
                this.mTextView.setVisibility(0)
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8)
                this.mTextView.setText((CharSequence) null)
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription(tab.getContentDescription())
            }
            TooltipCompat.setTooltipText(this, z ? null : tab.getContentDescription())
        }
    }

    class VisibilityAnimListener extends AnimatorListenerAdapter {
        private Boolean mCanceled = false
        private Int mFinalVisibility

        protected constructor() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        fun onAnimationCancel(Animator animator) {
            this.mCanceled = true
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        fun onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                return
            }
            ScrollingTabContainerView.this.mVisibilityAnim = null
            ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility)
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        fun onAnimationStart(Animator animator) {
            ScrollingTabContainerView.this.setVisibility(0)
            this.mCanceled = false
        }

        fun withFinalVisibility(ViewPropertyAnimator viewPropertyAnimator, Int i) {
            this.mFinalVisibility = i
            ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimator
            return this
        }
    }

    constructor(Context context) {
        super(context)
        this.mVisAnimListener = VisibilityAnimListener()
        setHorizontalScrollBarEnabled(false)
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context)
        setContentHeight(actionBarPolicy.getTabContainerHeight())
        this.mStackedTabMaxWidth = actionBarPolicy.getStackedTabMaxWidth()
        this.mTabLayout = createTabLayout()
        addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1))
    }

    private fun createSpinner() {
        AppCompatSpinner appCompatSpinner = AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle)
        appCompatSpinner.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1))
        appCompatSpinner.setOnItemSelectedListener(this)
        return appCompatSpinner
    }

    private fun createTabLayout() {
        LinearLayoutCompat linearLayoutCompat = LinearLayoutCompat(getContext(), null, R.attr.actionBarTabBarStyle)
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true)
        linearLayoutCompat.setGravity(17)
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1))
        return linearLayoutCompat
    }

    private fun isCollapsed() {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this
    }

    private fun performCollapse() {
        if (isCollapsed()) {
            return
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = createSpinner()
        }
        removeView(this.mTabLayout)
        addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1))
        if (this.mTabSpinner.getAdapter() == null) {
            this.mTabSpinner.setAdapter((SpinnerAdapter) TabAdapter())
        }
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector)
            this.mTabSelector = null
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex)
    }

    private fun performExpand() {
        if (isCollapsed()) {
            removeView(this.mTabSpinner)
            addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1))
            setTabSelected(this.mTabSpinner.getSelectedItemPosition())
        }
        return false
    }

    fun addTab(ActionBar.Tab tab, Int i, Boolean z) {
        TabView tabViewCreateTabView = createTabView(tab, false)
        this.mTabLayout.addView(tabViewCreateTabView, i, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f))
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged()
        }
        if (z) {
            tabViewCreateTabView.setSelected(true)
        }
        if (this.mAllowCollapse) {
            requestLayout()
        }
    }

    fun addTab(ActionBar.Tab tab, Boolean z) {
        TabView tabViewCreateTabView = createTabView(tab, false)
        this.mTabLayout.addView(tabViewCreateTabView, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f))
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged()
        }
        if (z) {
            tabViewCreateTabView.setSelected(true)
        }
        if (this.mAllowCollapse) {
            requestLayout()
        }
    }

    fun animateToTab(Int i) {
        val childAt = this.mTabLayout.getChildAt(i)
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector)
        }
        this.mTabSelector = Runnable() { // from class: android.support.v7.widget.ScrollingTabContainerView.1
            @Override // java.lang.Runnable
            fun run() {
                ScrollingTabContainerView.this.smoothScrollTo(childAt.getLeft() - ((ScrollingTabContainerView.this.getWidth() - childAt.getWidth()) / 2), 0)
                ScrollingTabContainerView.this.mTabSelector = null
            }
        }
        post(this.mTabSelector)
    }

    fun animateToVisibility(Int i) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel()
        }
        if (i != 0) {
            ViewPropertyAnimator viewPropertyAnimatorAlpha = animate().alpha(0.0f)
            viewPropertyAnimatorAlpha.setDuration(200L)
            viewPropertyAnimatorAlpha.setInterpolator(sAlphaInterpolator)
            viewPropertyAnimatorAlpha.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorAlpha, i))
            viewPropertyAnimatorAlpha.start()
            return
        }
        if (getVisibility() != 0) {
            setAlpha(0.0f)
        }
        ViewPropertyAnimator viewPropertyAnimatorAlpha2 = animate().alpha(1.0f)
        viewPropertyAnimatorAlpha2.setDuration(200L)
        viewPropertyAnimatorAlpha2.setInterpolator(sAlphaInterpolator)
        viewPropertyAnimatorAlpha2.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorAlpha2, i))
        viewPropertyAnimatorAlpha2.start()
    }

    TabView createTabView(ActionBar.Tab tab, Boolean z) {
        TabView tabView = TabView(getContext(), tab, z)
        if (z) {
            tabView.setBackgroundDrawable(null)
            tabView.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight))
        } else {
            tabView.setFocusable(true)
            if (this.mTabClickListener == null) {
                this.mTabClickListener = TabClickListener()
            }
            tabView.setOnClickListener(this.mTabClickListener)
        }
        return tabView
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (this.mTabSelector != null) {
            post(this.mTabSelector)
        }
    }

    @Override // android.view.View
    protected fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(getContext())
        setContentHeight(actionBarPolicy.getTabContainerHeight())
        this.mStackedTabMaxWidth = actionBarPolicy.getStackedTabMaxWidth()
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector)
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        ((TabView) view).getTab().select()
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    fun onMeasure(Int i, Int i2) {
        Int mode = View.MeasureSpec.getMode(i)
        Boolean z = mode == 1073741824
        setFillViewport(z)
        Int childCount = this.mTabLayout.getChildCount()
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1
        } else {
            if (childCount > 2) {
                this.mMaxTabWidth = (Int) (View.MeasureSpec.getSize(i) * 0.4f)
            } else {
                this.mMaxTabWidth = View.MeasureSpec.getSize(i) / 2
            }
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth)
        }
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824)
        if (!z && this.mAllowCollapse) {
            this.mTabLayout.measure(0, iMakeMeasureSpec)
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(i)) {
                performCollapse()
            } else {
                performExpand()
            }
        } else {
            performExpand()
        }
        Int measuredWidth = getMeasuredWidth()
        super.onMeasure(i, iMakeMeasureSpec)
        Int measuredWidth2 = getMeasuredWidth()
        if (!z || measuredWidth == measuredWidth2) {
            return
        }
        setTabSelected(this.mSelectedTabIndex)
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onNothingSelected(AdapterView adapterView) {
    }

    fun removeAllTabs() {
        this.mTabLayout.removeAllViews()
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged()
        }
        if (this.mAllowCollapse) {
            requestLayout()
        }
    }

    fun removeTabAt(Int i) {
        this.mTabLayout.removeViewAt(i)
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged()
        }
        if (this.mAllowCollapse) {
            requestLayout()
        }
    }

    fun setAllowCollapse(Boolean z) {
        this.mAllowCollapse = z
    }

    fun setContentHeight(Int i) {
        this.mContentHeight = i
        requestLayout()
    }

    fun setTabSelected(Int i) {
        this.mSelectedTabIndex = i
        Int childCount = this.mTabLayout.getChildCount()
        Int i2 = 0
        while (i2 < childCount) {
            View childAt = this.mTabLayout.getChildAt(i2)
            Boolean z = i2 == i
            childAt.setSelected(z)
            if (z) {
                animateToTab(i)
            }
            i2++
        }
        if (this.mTabSpinner == null || i < 0) {
            return
        }
        this.mTabSpinner.setSelection(i)
    }

    fun updateTab(Int i) {
        ((TabView) this.mTabLayout.getChildAt(i)).update()
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged()
        }
        if (this.mAllowCollapse) {
            requestLayout()
        }
    }
}
