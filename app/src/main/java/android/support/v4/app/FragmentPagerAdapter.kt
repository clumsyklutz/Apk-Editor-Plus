package android.support.v4.app

import android.os.Parcelable
import android.support.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup

abstract class FragmentPagerAdapter extends PagerAdapter {
    private static val DEBUG = false
    private static val TAG = "FragmentPagerAdapter"
    private FragmentTransaction mCurTransaction = null
    private Fragment mCurrentPrimaryItem = null
    private final FragmentManager mFragmentManager

    constructor(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager
    }

    private fun makeFragmentName(Int i, Long j) {
        return "android:switcher:" + i + ":" + j
    }

    @Override // android.support.v4.view.PagerAdapter
    fun destroyItem(@NonNull ViewGroup viewGroup, Int i, @NonNull Object obj) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }
        this.mCurTransaction.detach((Fragment) obj)
    }

    @Override // android.support.v4.view.PagerAdapter
    fun finishUpdate(@NonNull ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss()
            this.mCurTransaction = null
        }
    }

    public abstract Fragment getItem(Int i)

    fun getItemId(Int i) {
        return i
    }

    @Override // android.support.v4.view.PagerAdapter
    @NonNull
    fun instantiateItem(@NonNull ViewGroup viewGroup, Int i) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }
        Long itemId = getItemId(i)
        Fragment fragmentFindFragmentByTag = this.mFragmentManager.findFragmentByTag(makeFragmentName(viewGroup.getId(), itemId))
        if (fragmentFindFragmentByTag != null) {
            this.mCurTransaction.attach(fragmentFindFragmentByTag)
        } else {
            fragmentFindFragmentByTag = getItem(i)
            this.mCurTransaction.add(viewGroup.getId(), fragmentFindFragmentByTag, makeFragmentName(viewGroup.getId(), itemId))
        }
        if (fragmentFindFragmentByTag != this.mCurrentPrimaryItem) {
            fragmentFindFragmentByTag.setMenuVisibility(false)
            fragmentFindFragmentByTag.setUserVisibleHint(false)
        }
        return fragmentFindFragmentByTag
    }

    @Override // android.support.v4.view.PagerAdapter
    fun isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return ((Fragment) obj).getView() == view
    }

    @Override // android.support.v4.view.PagerAdapter
    fun restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override // android.support.v4.view.PagerAdapter
    fun saveState() {
        return null
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
