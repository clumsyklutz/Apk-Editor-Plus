package android.support.v4.widget

import android.support.annotation.NonNull
import android.widget.ListView

class ListViewAutoScrollHelper extends AutoScrollHelper {
    private final ListView mTarget

    constructor(@NonNull ListView listView) {
        super(listView)
        this.mTarget = listView
    }

    @Override // android.support.v4.widget.AutoScrollHelper
    fun canTargetScrollHorizontally(Int i) {
        return false
    }

    @Override // android.support.v4.widget.AutoScrollHelper
    fun canTargetScrollVertically(Int i) {
        ListView listView = this.mTarget
        Int count = listView.getCount()
        if (count == 0) {
            return false
        }
        Int childCount = listView.getChildCount()
        Int firstVisiblePosition = listView.getFirstVisiblePosition()
        Int i2 = firstVisiblePosition + childCount
        if (i > 0) {
            if (i2 >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                return false
            }
        } else {
            if (i >= 0) {
                return false
            }
            if (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) {
                return false
            }
        }
        return true
    }

    @Override // android.support.v4.widget.AutoScrollHelper
    fun scrollTargetBy(Int i, Int i2) {
        ListViewCompat.scrollListBy(this.mTarget, i2)
    }
}
