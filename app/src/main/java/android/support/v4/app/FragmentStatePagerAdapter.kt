package android.support.v4.app

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static val DEBUG = false
    private static val TAG = "FragmentStatePagerAdapt"
    private final FragmentManager mFragmentManager
    private FragmentTransaction mCurTransaction = null
    private ArrayList mSavedState = ArrayList()
    private ArrayList mFragments = ArrayList()
    private Fragment mCurrentPrimaryItem = null

    constructor(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager
    }

    @Override // android.support.v4.view.PagerAdapter
    fun destroyItem(@NonNull ViewGroup viewGroup, Int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }
        while (this.mSavedState.size() <= i) {
            this.mSavedState.add(null)
        }
        this.mSavedState.set(i, fragment.isAdded() ? this.mFragmentManager.saveFragmentInstanceState(fragment) : null)
        this.mFragments.set(i, null)
        this.mCurTransaction.remove(fragment)
    }

    @Override // android.support.v4.view.PagerAdapter
    fun finishUpdate(@NonNull ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss()
            this.mCurTransaction = null
        }
    }

    public abstract Fragment getItem(Int i)

    @Override // android.support.v4.view.PagerAdapter
    @NonNull
    fun instantiateItem(@NonNull ViewGroup viewGroup, Int i) {
        Fragment.SavedState savedState
        Fragment fragment
        if (this.mFragments.size() > i && (fragment = (Fragment) this.mFragments.get(i)) != null) {
            return fragment
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }
        Fragment item = getItem(i)
        if (this.mSavedState.size() > i && (savedState = (Fragment.SavedState) this.mSavedState.get(i)) != null) {
            item.setInitialSavedState(savedState)
        }
        while (this.mFragments.size() <= i) {
            this.mFragments.add(null)
        }
        item.setMenuVisibility(false)
        item.setUserVisibleHint(false)
        this.mFragments.set(i, item)
        this.mCurTransaction.add(viewGroup.getId(), item)
        return item
    }

    @Override // android.support.v4.view.PagerAdapter
    fun isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return ((Fragment) obj).getView() == view
    }

    @Override // android.support.v4.view.PagerAdapter
    fun restoreState(Parcelable parcelable, ClassLoader classLoader) throws NumberFormatException {
        if (parcelable != null) {
            Bundle bundle = (Bundle) parcelable
            bundle.setClassLoader(classLoader)
            Array<Parcelable> parcelableArray = bundle.getParcelableArray("states")
            this.mSavedState.clear()
            this.mFragments.clear()
            if (parcelableArray != null) {
                for (Parcelable parcelable2 : parcelableArray) {
                    this.mSavedState.add((Fragment.SavedState) parcelable2)
                }
            }
            for (String str : bundle.keySet()) {
                if (str.startsWith("f")) {
                    Int i = Integer.parseInt(str.substring(1))
                    Fragment fragment = this.mFragmentManager.getFragment(bundle, str)
                    if (fragment != null) {
                        while (this.mFragments.size() <= i) {
                            this.mFragments.add(null)
                        }
                        fragment.setMenuVisibility(false)
                        this.mFragments.set(i, fragment)
                    } else {
                        Log.w(TAG, "Bad fragment at key " + str)
                    }
                }
            }
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    fun saveState() {
        Bundle bundle = null
        if (this.mSavedState.size() > 0) {
            bundle = Bundle()
            Fragment.Array<SavedState> savedStateArr = new Fragment.SavedState[this.mSavedState.size()]
            this.mSavedState.toArray(savedStateArr)
            bundle.putParcelableArray("states", savedStateArr)
        }
        Bundle bundle2 = bundle
        for (Int i = 0; i < this.mFragments.size(); i++) {
            Fragment fragment = (Fragment) this.mFragments.get(i)
            if (fragment != null && fragment.isAdded()) {
                if (bundle2 == null) {
                    bundle2 = Bundle()
                }
                this.mFragmentManager.putFragment(bundle2, "f" + i, fragment)
            }
        }
        return bundle2
    }

    @Override // android.support.v4.view.PagerAdapter
    fun setPrimaryItem(@NonNull ViewGroup viewGroup, Int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false)
                this.mCurrentPrimaryItem.setUserVisibleHint(false)
            }
            fragment.setMenuVisibility(true)
            fragment.setUserVisibleHint(true)
            this.mCurrentPrimaryItem = fragment
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    fun startUpdate(@NonNull ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw IllegalStateException("ViewPager with adapter " + this + " requires a view id")
        }
    }
}
