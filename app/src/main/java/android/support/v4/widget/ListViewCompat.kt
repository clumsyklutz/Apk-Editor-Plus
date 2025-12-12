package android.support.v4.widget

import android.os.Build
import android.support.annotation.NonNull
import android.view.View
import android.widget.ListView

class ListViewCompat {
    private constructor() {
    }

    fun canScrollList(@NonNull ListView listView, Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            return listView.canScrollList(i)
        }
        Int childCount = listView.getChildCount()
        if (childCount == 0) {
            return false
        }
        Int firstVisiblePosition = listView.getFirstVisiblePosition()
        if (i > 0) {
            return childCount + firstVisiblePosition < listView.getCount() || listView.getChildAt(childCount + (-1)).getBottom() > listView.getHeight() - listView.getListPaddingBottom()
        }
        return firstVisiblePosition > 0 || listView.getChildAt(0).getTop() < listView.getListPaddingTop()
    }

    fun scrollListBy(@NonNull ListView listView, Int i) {
        View childAt
        if (Build.VERSION.SDK_INT >= 19) {
            listView.scrollListBy(i)
            return
        }
        Int firstVisiblePosition = listView.getFirstVisiblePosition()
        if (firstVisiblePosition == -1 || (childAt = listView.getChildAt(0)) == null) {
            return
        }
        listView.setSelectionFromTop(firstVisiblePosition, childAt.getTop() - i)
    }
}
