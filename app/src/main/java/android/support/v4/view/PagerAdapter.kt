package android.support.v4.view

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View
import android.view.ViewGroup

abstract class PagerAdapter {
    public static val POSITION_NONE = -2
    public static val POSITION_UNCHANGED = -1
    private val mObservable = DataSetObservable()
    private DataSetObserver mViewPagerObserver

    @Deprecated
    fun destroyItem(@NonNull View view, Int i, @NonNull Object obj) {
        throw UnsupportedOperationException("Required method destroyItem was not overridden")
    }

    fun destroyItem(@NonNull ViewGroup viewGroup, Int i, @NonNull Object obj) {
        destroyItem((View) viewGroup, i, obj)
    }

    @Deprecated
    fun finishUpdate(@NonNull View view) {
    }

    fun finishUpdate(@NonNull ViewGroup viewGroup) {
        finishUpdate((View) viewGroup)
    }

    public abstract Int getCount()

    fun getItemPosition(@NonNull Object obj) {
        return -1
    }

    @Nullable
    fun getPageTitle(Int i) {
        return null
    }

    fun getPageWidth(Int i) {
        return 1.0f
    }

    @NonNull
    @Deprecated
    fun instantiateItem(@NonNull View view, Int i) {
        throw UnsupportedOperationException("Required method instantiateItem was not overridden")
    }

    @NonNull
    fun instantiateItem(@NonNull ViewGroup viewGroup, Int i) {
        return instantiateItem((View) viewGroup, i)
    }

    public abstract Boolean isViewFromObject(@NonNull View view, @NonNull Object obj)

    fun notifyDataSetChanged() {
        synchronized (this) {
            if (this.mViewPagerObserver != null) {
                this.mViewPagerObserver.onChanged()
            }
        }
        this.mObservable.notifyChanged()
    }

    fun registerDataSetObserver(@NonNull DataSetObserver dataSetObserver) {
        this.mObservable.registerObserver(dataSetObserver)
    }

    fun restoreState(@Nullable Parcelable parcelable, @Nullable ClassLoader classLoader) {
    }

    @Nullable
    fun saveState() {
        return null
    }

    @Deprecated
    fun setPrimaryItem(@NonNull View view, Int i, @NonNull Object obj) {
    }

    fun setPrimaryItem(@NonNull ViewGroup viewGroup, Int i, @NonNull Object obj) {
        setPrimaryItem((View) viewGroup, i, obj)
    }

    Unit setViewPagerObserver(DataSetObserver dataSetObserver) {
        synchronized (this) {
            this.mViewPagerObserver = dataSetObserver
        }
    }

    @Deprecated
    fun startUpdate(@NonNull View view) {
    }

    fun startUpdate(@NonNull ViewGroup viewGroup) {
        startUpdate((View) viewGroup)
    }

    fun unregisterDataSetObserver(@NonNull DataSetObserver dataSetObserver) {
        this.mObservable.unregisterObserver(dataSetObserver)
    }
}
