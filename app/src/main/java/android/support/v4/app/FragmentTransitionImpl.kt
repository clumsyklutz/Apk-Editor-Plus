package android.support.v4.app

import android.graphics.Rect
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewGroupCompat
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import java.util.List
import java.util.Map

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class FragmentTransitionImpl {
    protected static Unit bfsAddViewChildren(List list, View view) {
        Int size = list.size()
        if (containedBeforeIndex(list, view, size)) {
            return
        }
        list.add(view)
        for (Int i = size; i < list.size(); i++) {
            View view2 = (View) list.get(i)
            if (view2 is ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2
                Int childCount = viewGroup.getChildCount()
                for (Int i2 = 0; i2 < childCount; i2++) {
                    View childAt = viewGroup.getChildAt(i2)
                    if (!containedBeforeIndex(list, childAt, size)) {
                        list.add(childAt)
                    }
                }
            }
        }
    }

    private fun containedBeforeIndex(List list, View view, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            if (list.get(i2) == view) {
                return true
            }
        }
        return false
    }

    static String findKeyForValue(Map map, String str) {
        for (Map.Entry entry : map.entrySet()) {
            if (str.equals(entry.getValue())) {
                return (String) entry.getKey()
            }
        }
        return null
    }

    protected static Boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty()
    }

    public abstract Unit addTarget(Object obj, View view)

    public abstract Unit addTargets(Object obj, ArrayList arrayList)

    public abstract Unit beginDelayedTransition(ViewGroup viewGroup, Object obj)

    public abstract Boolean canHandle(Object obj)

    Unit captureTransitioningViews(ArrayList arrayList, View view) {
        if (view.getVisibility() == 0) {
            if (!(view is ViewGroup)) {
                arrayList.add(view)
                return
            }
            ViewGroup viewGroup = (ViewGroup) view
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                arrayList.add(viewGroup)
                return
            }
            Int childCount = viewGroup.getChildCount()
            for (Int i = 0; i < childCount; i++) {
                captureTransitioningViews(arrayList, viewGroup.getChildAt(i))
            }
        }
    }

    public abstract Object cloneTransition(Object obj)

    Unit findNamedViews(Map map, View view) {
        if (view.getVisibility() == 0) {
            String transitionName = ViewCompat.getTransitionName(view)
            if (transitionName != null) {
                map.put(transitionName, view)
            }
            if (view is ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view
                Int childCount = viewGroup.getChildCount()
                for (Int i = 0; i < childCount; i++) {
                    findNamedViews(map, viewGroup.getChildAt(i))
                }
            }
        }
    }

    protected fun getBoundsOnScreen(View view, Rect rect) {
        Array<Int> iArr = new Int[2]
        view.getLocationOnScreen(iArr)
        rect.set(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight())
    }

    public abstract Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3)

    public abstract Object mergeTransitionsTogether(Object obj, Object obj2, Object obj3)

    ArrayList prepareSetNameOverridesReordered(ArrayList arrayList) {
        ArrayList arrayList2 = ArrayList()
        Int size = arrayList.size()
        for (Int i = 0; i < size; i++) {
            View view = (View) arrayList.get(i)
            arrayList2.add(ViewCompat.getTransitionName(view))
            ViewCompat.setTransitionName(view, null)
        }
        return arrayList2
    }

    public abstract Unit removeTarget(Object obj, View view)

    public abstract Unit replaceTargets(Object obj, ArrayList arrayList, ArrayList arrayList2)

    public abstract Unit scheduleHideFragmentView(Object obj, View view, ArrayList arrayList)

    Unit scheduleNameReset(ViewGroup viewGroup, final ArrayList arrayList, final Map map) {
        OneShotPreDrawListener.add(viewGroup, Runnable() { // from class: android.support.v4.app.FragmentTransitionImpl.3
            @Override // java.lang.Runnable
            fun run() {
                Int size = arrayList.size()
                for (Int i = 0; i < size; i++) {
                    View view = (View) arrayList.get(i)
                    ViewCompat.setTransitionName(view, (String) map.get(ViewCompat.getTransitionName(view)))
                }
            }
        })
    }

    public abstract Unit scheduleRemoveTargets(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2, Object obj4, ArrayList arrayList3)

    public abstract Unit setEpicenter(Object obj, Rect rect)

    public abstract Unit setEpicenter(Object obj, View view)

    Unit setNameOverridesOrdered(View view, final ArrayList arrayList, final Map map) {
        OneShotPreDrawListener.add(view, Runnable() { // from class: android.support.v4.app.FragmentTransitionImpl.2
            @Override // java.lang.Runnable
            fun run() {
                Int size = arrayList.size()
                for (Int i = 0; i < size; i++) {
                    View view2 = (View) arrayList.get(i)
                    String transitionName = ViewCompat.getTransitionName(view2)
                    if (transitionName != null) {
                        ViewCompat.setTransitionName(view2, FragmentTransitionImpl.findKeyForValue(map, transitionName))
                    }
                }
            }
        })
    }

    Unit setNameOverridesReordered(View view, final ArrayList arrayList, final ArrayList arrayList2, final ArrayList arrayList3, Map map) {
        val size = arrayList2.size()
        val arrayList4 = ArrayList()
        for (Int i = 0; i < size; i++) {
            View view2 = (View) arrayList.get(i)
            String transitionName = ViewCompat.getTransitionName(view2)
            arrayList4.add(transitionName)
            if (transitionName != null) {
                ViewCompat.setTransitionName(view2, null)
                String str = (String) map.get(transitionName)
                Int i2 = 0
                while (true) {
                    if (i2 >= size) {
                        break
                    }
                    if (str.equals(arrayList3.get(i2))) {
                        ViewCompat.setTransitionName((View) arrayList2.get(i2), transitionName)
                        break
                    }
                    i2++
                }
            }
        }
        OneShotPreDrawListener.add(view, Runnable() { // from class: android.support.v4.app.FragmentTransitionImpl.1
            @Override // java.lang.Runnable
            fun run() {
                Int i3 = 0
                while (true) {
                    Int i4 = i3
                    if (i4 >= size) {
                        return
                    }
                    ViewCompat.setTransitionName((View) arrayList2.get(i4), (String) arrayList3.get(i4))
                    ViewCompat.setTransitionName((View) arrayList.get(i4), (String) arrayList4.get(i4))
                    i3 = i4 + 1
                }
            }
        })
    }

    public abstract Unit setSharedElementTargets(Object obj, View view, ArrayList arrayList)

    public abstract Unit swapSharedElementTargets(Object obj, ArrayList arrayList, ArrayList arrayList2)

    public abstract Object wrapTransitionInSet(Object obj)
}
