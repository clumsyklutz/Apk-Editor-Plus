package android.support.v4.app

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TabHost
import android.widget.TabWidget
import java.util.ArrayList

class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private Boolean mAttached
    private Int mContainerId
    private Context mContext
    private FragmentManager mFragmentManager
    private TabInfo mLastTab
    private TabHost.OnTabChangeListener mOnTabChangeListener
    private FrameLayout mRealTabContent
    private final ArrayList mTabs

    class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext

        constructor(Context context) {
            this.mContext = context
        }

        @Override // android.widget.TabHost.TabContentFactory
        fun createTabContent(String str) {
            View view = View(this.mContext)
            view.setMinimumWidth(0)
            view.setMinimumHeight(0)
            return view
        }
    }

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.app.FragmentTabHost.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        String curTab

        SavedState(Parcel parcel) {
            super(parcel)
            this.curTab = parcel.readString()
        }

        SavedState(Parcelable parcelable) {
            super(parcelable)
        }

        fun toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}"
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeString(this.curTab)
        }
    }

    final class TabInfo {

        @Nullable
        final Bundle args

        @NonNull
        final Class clss
        Fragment fragment

        @NonNull
        final String tag

        TabInfo(@NonNull String str, @NonNull Class cls, @Nullable Bundle bundle) {
            this.tag = str
            this.clss = cls
            this.args = bundle
        }
    }

    constructor(Context context) {
        super(context, null)
        this.mTabs = ArrayList()
        initFragmentTabHost(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mTabs = ArrayList()
        initFragmentTabHost(context, attributeSet)
    }

    @Nullable
    private fun doTabChanged(@Nullable String str, @Nullable FragmentTransaction fragmentTransaction) {
        TabInfo tabInfoForTag = getTabInfoForTag(str)
        if (this.mLastTab != tabInfoForTag) {
            if (fragmentTransaction == null) {
                fragmentTransaction = this.mFragmentManager.beginTransaction()
            }
            if (this.mLastTab != null && this.mLastTab.fragment != null) {
                fragmentTransaction.detach(this.mLastTab.fragment)
            }
            if (tabInfoForTag != null) {
                if (tabInfoForTag.fragment == null) {
                    tabInfoForTag.fragment = Fragment.instantiate(this.mContext, tabInfoForTag.clss.getName(), tabInfoForTag.args)
                    fragmentTransaction.add(this.mContainerId, tabInfoForTag.fragment, tabInfoForTag.tag)
                } else {
                    fragmentTransaction.attach(tabInfoForTag.fragment)
                }
            }
            this.mLastTab = tabInfoForTag
        }
        return fragmentTransaction
    }

    private fun ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId)
            if (this.mRealTabContent == null) {
                throw IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId)
            }
        }
    }

    private fun ensureHierarchy(Context context) {
        if (findViewById(R.id.tabs) == null) {
            LinearLayout linearLayout = LinearLayout(context)
            linearLayout.setOrientation(1)
            addView(linearLayout, new FrameLayout.LayoutParams(-1, -1))
            TabWidget tabWidget = TabWidget(context)
            tabWidget.setId(R.id.tabs)
            tabWidget.setOrientation(0)
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f))
            FrameLayout frameLayout = FrameLayout(context)
            frameLayout.setId(R.id.tabcontent)
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, 0.0f))
            FrameLayout frameLayout2 = FrameLayout(context)
            this.mRealTabContent = frameLayout2
            this.mRealTabContent.setId(this.mContainerId)
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f))
        }
    }

    @Nullable
    private fun getTabInfoForTag(String str) {
        Int size = this.mTabs.size()
        for (Int i = 0; i < size; i++) {
            TabInfo tabInfo = (TabInfo) this.mTabs.get(i)
            if (tabInfo.tag.equals(str)) {
                return tabInfo
            }
        }
        return null
    }

    private fun initFragmentTabHost(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new Array<Int>{R.attr.inflatedId}, 0, 0)
        this.mContainerId = typedArrayObtainStyledAttributes.getResourceId(0, 0)
        typedArrayObtainStyledAttributes.recycle()
        super.setOnTabChangedListener(this)
    }

    fun addTab(@NonNull TabHost.TabSpec tabSpec, @NonNull Class cls, @Nullable Bundle bundle) {
        tabSpec.setContent(DummyTabFactory(this.mContext))
        String tag = tabSpec.getTag()
        TabInfo tabInfo = TabInfo(tag, cls, bundle)
        if (this.mAttached) {
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tag)
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction()
                fragmentTransactionBeginTransaction.detach(tabInfo.fragment)
                fragmentTransactionBeginTransaction.commit()
            }
        }
        this.mTabs.add(tabInfo)
        addTab(tabSpec)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        String currentTabTag = getCurrentTabTag()
        FragmentTransaction fragmentTransactionBeginTransaction = null
        Int size = this.mTabs.size()
        for (Int i = 0; i < size; i++) {
            TabInfo tabInfo = (TabInfo) this.mTabs.get(i)
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag)
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                if (tabInfo.tag.equals(currentTabTag)) {
                    this.mLastTab = tabInfo
                } else {
                    if (fragmentTransactionBeginTransaction == null) {
                        fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction()
                    }
                    fragmentTransactionBeginTransaction.detach(tabInfo.fragment)
                }
            }
        }
        this.mAttached = true
        FragmentTransaction fragmentTransactionDoTabChanged = doTabChanged(currentTabTag, fragmentTransactionBeginTransaction)
        if (fragmentTransactionDoTabChanged != null) {
            fragmentTransactionDoTabChanged.commit()
            this.mFragmentManager.executePendingTransactions()
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.mAttached = false
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        setCurrentTabByTag(savedState.curTab)
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        savedState.curTab = getCurrentTabTag()
        return savedState
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    fun onTabChanged(String str) {
        FragmentTransaction fragmentTransactionDoTabChanged
        if (this.mAttached && (fragmentTransactionDoTabChanged = doTabChanged(str, null)) != null) {
            fragmentTransactionDoTabChanged.commit()
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(str)
        }
    }

    @Override // android.widget.TabHost
    fun setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener
    }

    @Override // android.widget.TabHost
    @Deprecated
    fun setup() {
        throw IllegalStateException("Must call setup() that takes a Context and FragmentManager")
    }

    fun setup(Context context, FragmentManager fragmentManager) {
        ensureHierarchy(context)
        super.setup()
        this.mContext = context
        this.mFragmentManager = fragmentManager
        ensureContent()
    }

    fun setup(Context context, FragmentManager fragmentManager, Int i) {
        ensureHierarchy(context)
        super.setup()
        this.mContext = context
        this.mFragmentManager = fragmentManager
        this.mContainerId = i
        ensureContent()
        this.mRealTabContent.setId(i)
        if (getId() == -1) {
            setId(R.id.tabhost)
        }
    }
}
