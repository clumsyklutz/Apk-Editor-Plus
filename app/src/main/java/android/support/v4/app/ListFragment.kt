package android.support.v4.app

import android.R
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView

class ListFragment extends Fragment {
    static val INTERNAL_EMPTY_ID = 16711681
    static val INTERNAL_LIST_CONTAINER_ID = 16711683
    static val INTERNAL_PROGRESS_CONTAINER_ID = 16711682
    ListAdapter mAdapter
    CharSequence mEmptyText
    View mEmptyView
    ListView mList
    View mListContainer
    Boolean mListShown
    View mProgressContainer
    TextView mStandardEmptyView
    private val mHandler = Handler()
    private val mRequestFocus = Runnable() { // from class: android.support.v4.app.ListFragment.1
        @Override // java.lang.Runnable
        fun run() {
            ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList)
        }
    }
    private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() { // from class: android.support.v4.app.ListFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
            ListFragment.this.onListItemClick((ListView) adapterView, view, i, j)
        }
    }

    private fun ensureList() {
        if (this.mList != null) {
            return
        }
        View view = getView()
        if (view == null) {
            throw IllegalStateException("Content view not yet created")
        }
        if (view is ListView) {
            this.mList = (ListView) view
        } else {
            this.mStandardEmptyView = (TextView) view.findViewById(INTERNAL_EMPTY_ID)
            if (this.mStandardEmptyView == null) {
                this.mEmptyView = view.findViewById(R.id.empty)
            } else {
                this.mStandardEmptyView.setVisibility(8)
            }
            this.mProgressContainer = view.findViewById(INTERNAL_PROGRESS_CONTAINER_ID)
            this.mListContainer = view.findViewById(INTERNAL_LIST_CONTAINER_ID)
            View viewFindViewById = view.findViewById(R.id.list)
            if (!(viewFindViewById is ListView)) {
                if (viewFindViewById != null) {
                    throw RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class")
                }
                throw RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'")
            }
            this.mList = (ListView) viewFindViewById
            if (this.mEmptyView != null) {
                this.mList.setEmptyView(this.mEmptyView)
            } else if (this.mEmptyText != null) {
                this.mStandardEmptyView.setText(this.mEmptyText)
                this.mList.setEmptyView(this.mStandardEmptyView)
            }
        }
        this.mListShown = true
        this.mList.setOnItemClickListener(this.mOnClickListener)
        if (this.mAdapter != null) {
            ListAdapter listAdapter = this.mAdapter
            this.mAdapter = null
            setListAdapter(listAdapter)
        } else if (this.mProgressContainer != null) {
            setListShown(false, false)
        }
        this.mHandler.post(this.mRequestFocus)
    }

    private fun setListShown(Boolean z, Boolean z2) {
        ensureList()
        if (this.mProgressContainer == null) {
            throw IllegalStateException("Can't be used with a custom content view")
        }
        if (this.mListShown == z) {
            return
        }
        this.mListShown = z
        if (z) {
            if (z2) {
                this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out))
                this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in))
            } else {
                this.mProgressContainer.clearAnimation()
                this.mListContainer.clearAnimation()
            }
            this.mProgressContainer.setVisibility(8)
            this.mListContainer.setVisibility(0)
            return
        }
        if (z2) {
            this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in))
            this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out))
        } else {
            this.mProgressContainer.clearAnimation()
            this.mListContainer.clearAnimation()
        }
        this.mProgressContainer.setVisibility(0)
        this.mListContainer.setVisibility(8)
    }

    fun getListAdapter() {
        return this.mAdapter
    }

    fun getListView() {
        ensureList()
        return this.mList
    }

    fun getSelectedItemId() {
        ensureList()
        return this.mList.getSelectedItemId()
    }

    fun getSelectedItemPosition() {
        ensureList()
        return this.mList.getSelectedItemPosition()
    }

    @Override // android.support.v4.app.Fragment
    fun onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = getContext()
        FrameLayout frameLayout = FrameLayout(context)
        LinearLayout linearLayout = LinearLayout(context)
        linearLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID)
        linearLayout.setOrientation(1)
        linearLayout.setVisibility(8)
        linearLayout.setGravity(17)
        linearLayout.addView(ProgressBar(context, null, R.attr.progressBarStyleLarge), new FrameLayout.LayoutParams(-2, -2))
        frameLayout.addView(linearLayout, new FrameLayout.LayoutParams(-1, -1))
        FrameLayout frameLayout2 = FrameLayout(context)
        frameLayout2.setId(INTERNAL_LIST_CONTAINER_ID)
        TextView textView = TextView(context)
        textView.setId(INTERNAL_EMPTY_ID)
        textView.setGravity(17)
        frameLayout2.addView(textView, new FrameLayout.LayoutParams(-1, -1))
        ListView listView = ListView(context)
        listView.setId(R.id.list)
        listView.setDrawSelectorOnTop(false)
        frameLayout2.addView(listView, new FrameLayout.LayoutParams(-1, -1))
        frameLayout.addView(frameLayout2, new FrameLayout.LayoutParams(-1, -1))
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1))
        return frameLayout
    }

    @Override // android.support.v4.app.Fragment
    fun onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus)
        this.mList = null
        this.mListShown = false
        this.mListContainer = null
        this.mProgressContainer = null
        this.mEmptyView = null
        this.mStandardEmptyView = null
        super.onDestroyView()
    }

    fun onListItemClick(ListView listView, View view, Int i, Long j) {
    }

    @Override // android.support.v4.app.Fragment
    fun onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle)
        ensureList()
    }

    fun setEmptyText(CharSequence charSequence) {
        ensureList()
        if (this.mStandardEmptyView == null) {
            throw IllegalStateException("Can't be used with a custom content view")
        }
        this.mStandardEmptyView.setText(charSequence)
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView)
        }
        this.mEmptyText = charSequence
    }

    fun setListAdapter(ListAdapter listAdapter) {
        Boolean z = this.mAdapter != null
        this.mAdapter = listAdapter
        if (this.mList != null) {
            this.mList.setAdapter(listAdapter)
            if (this.mListShown || z) {
                return
            }
            setListShown(true, getView().getWindowToken() != null)
        }
    }

    fun setListShown(Boolean z) {
        setListShown(z, true)
    }

    fun setListShownNoAnimation(Boolean z) {
        setListShown(z, false)
    }

    fun setSelection(Int i) {
        ensureList()
        this.mList.setSelection(i)
    }
}
