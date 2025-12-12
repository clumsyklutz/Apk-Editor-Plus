package android.support.v4.app

import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import androidx.fragment.app.BackStackRecord
import android.support.v4.util.ArrayMap
import androidx.core.view.ViewCompat
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import java.util.Collection
import java.util.List

class FragmentTransition {
    private static final Array<Int> INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8}
    private static final FragmentTransitionImpl PLATFORM_IMPL
    private static final FragmentTransitionImpl SUPPORT_IMPL

    class FragmentContainerTransition {
        public Fragment firstOut
        public Boolean firstOutIsPop
        public BackStackRecord firstOutTransaction
        public Fragment lastIn
        public Boolean lastInIsPop
        public BackStackRecord lastInTransaction

        FragmentContainerTransition() {
        }
    }

    static {
        PLATFORM_IMPL = Build.VERSION.SDK_INT >= 21 ? FragmentTransitionCompat21() : null
        SUPPORT_IMPL = resolveSupportImpl()
    }

    private constructor() {
    }

    private fun addSharedElementsWithMatchingNames(ArrayList arrayList, ArrayMap arrayMap, Collection collection) {
        for (Int size = arrayMap.size() - 1; size >= 0; size--) {
            View view = (View) arrayMap.valueAt(size)
            if (collection.contains(ViewCompat.getTransitionName(view))) {
                arrayList.add(view)
            }
        }
    }

    private fun addToFirstInLastOut(BackStackRecord backStackRecord, BackStackRecord.Op op, SparseArray sparseArray, Boolean z, Boolean z2) throws NoSuchFieldException, Resources.NotFoundException {
        Int i
        Boolean z3
        Boolean z4
        Boolean z5
        Boolean z6
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer2
        Fragment fragment = op.fragment
        if (fragment == null || (i = fragment.mContainerId) == 0) {
            return
        }
        switch (z ? INVERSE_OPS[op.cmd] : op.cmd) {
            case 1:
            case 7:
                z3 = true
                z4 = false
                z5 = false
                z6 = z2 ? fragment.mIsNewlyAdded : (fragment.mAdded || fragment.mHidden) ? false : true
                break
            case 2:
            default:
                z3 = false
                z4 = false
                z5 = false
                z6 = false
                break
            case 3:
            case 6:
                z3 = false
                z4 = z2 ? !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mPostponedAlpha >= 0.0f : fragment.mAdded && !fragment.mHidden
                z5 = true
                z6 = false
                break
            case 4:
                z3 = false
                z4 = z2 ? fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden : fragment.mAdded && !fragment.mHidden
                z5 = true
                z6 = false
                break
            case 5:
                z3 = true
                z4 = false
                z5 = false
                z6 = z2 ? fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded : fragment.mHidden
                break
        }
        FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.get(i)
        if (z6) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransition, sparseArray, i)
            fragmentContainerTransitionEnsureContainer.lastIn = fragment
            fragmentContainerTransitionEnsureContainer.lastInIsPop = z
            fragmentContainerTransitionEnsureContainer.lastInTransaction = backStackRecord
        } else {
            fragmentContainerTransitionEnsureContainer = fragmentContainerTransition
        }
        if (!z2 && z3) {
            if (fragmentContainerTransitionEnsureContainer != null && fragmentContainerTransitionEnsureContainer.firstOut == fragment) {
                fragmentContainerTransitionEnsureContainer.firstOut = null
            }
            FragmentManagerImpl fragmentManagerImpl = backStackRecord.mManager
            if (fragment.mState <= 0 && fragmentManagerImpl.mCurState > 0 && !backStackRecord.mReorderingAllowed) {
                fragmentManagerImpl.makeActive(fragment)
                fragmentManagerImpl.moveToState(fragment, 1, 0, 0, false)
            }
        }
        if (z4 && (fragmentContainerTransitionEnsureContainer == null || fragmentContainerTransitionEnsureContainer.firstOut == null)) {
            fragmentContainerTransitionEnsureContainer2 = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i)
            fragmentContainerTransitionEnsureContainer2.firstOut = fragment
            fragmentContainerTransitionEnsureContainer2.firstOutIsPop = z
            fragmentContainerTransitionEnsureContainer2.firstOutTransaction = backStackRecord
        } else {
            fragmentContainerTransitionEnsureContainer2 = fragmentContainerTransitionEnsureContainer
        }
        if (z2 || !z5 || fragmentContainerTransitionEnsureContainer2 == null || fragmentContainerTransitionEnsureContainer2.lastIn != fragment) {
            return
        }
        fragmentContainerTransitionEnsureContainer2.lastIn = null
    }

    fun calculateFragments(BackStackRecord backStackRecord, SparseArray sparseArray, Boolean z) throws NoSuchFieldException, Resources.NotFoundException {
        Int size = backStackRecord.mOps.size()
        for (Int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, (BackStackRecord.Op) backStackRecord.mOps.get(i), sparseArray, false, z)
        }
    }

    private fun calculateNameOverrides(Int i, ArrayList arrayList, ArrayList arrayList2, Int i2, Int i3) {
        ArrayList arrayList3
        ArrayList arrayList4
        ArrayMap arrayMap = ArrayMap()
        for (Int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i4)
            if (backStackRecord.interactsWith(i)) {
                Boolean zBooleanValue = ((Boolean) arrayList2.get(i4)).booleanValue()
                if (backStackRecord.mSharedElementSourceNames != null) {
                    Int size = backStackRecord.mSharedElementSourceNames.size()
                    if (zBooleanValue) {
                        arrayList3 = backStackRecord.mSharedElementSourceNames
                        arrayList4 = backStackRecord.mSharedElementTargetNames
                    } else {
                        ArrayList arrayList5 = backStackRecord.mSharedElementSourceNames
                        arrayList3 = backStackRecord.mSharedElementTargetNames
                        arrayList4 = arrayList5
                    }
                    for (Int i5 = 0; i5 < size; i5++) {
                        String str = (String) arrayList4.get(i5)
                        String str2 = (String) arrayList3.get(i5)
                        String str3 = (String) arrayMap.remove(str2)
                        if (str3 != null) {
                            arrayMap.put(str, str3)
                        } else {
                            arrayMap.put(str, str2)
                        }
                    }
                }
            }
        }
        return arrayMap
    }

    fun calculatePopFragments(BackStackRecord backStackRecord, SparseArray sparseArray, Boolean z) throws NoSuchFieldException, Resources.NotFoundException {
        if (backStackRecord.mManager.mContainer.onHasView()) {
            for (Int size = backStackRecord.mOps.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(backStackRecord, (BackStackRecord.Op) backStackRecord.mOps.get(size), sparseArray, true, z)
            }
        }
    }

    static Unit callSharedElementStartEnd(Fragment fragment, Fragment fragment2, Boolean z, ArrayMap arrayMap, Boolean z2) {
        SharedElementCallback enterTransitionCallback = z ? fragment2.getEnterTransitionCallback() : fragment.getEnterTransitionCallback()
        if (enterTransitionCallback != null) {
            ArrayList arrayList = ArrayList()
            ArrayList arrayList2 = ArrayList()
            Int size = arrayMap == null ? 0 : arrayMap.size()
            for (Int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i))
                arrayList.add(arrayMap.valueAt(i))
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null)
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null)
            }
        }
    }

    private fun canHandleAll(FragmentTransitionImpl fragmentTransitionImpl, List list) {
        Int size = list.size()
        for (Int i = 0; i < size; i++) {
            if (!fragmentTransitionImpl.canHandle(list.get(i))) {
                return false
            }
        }
        return true
    }

    static ArrayMap captureInSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        ArrayList arrayList
        SharedElementCallback sharedElementCallback
        String strFindKeyForValue
        Fragment fragment = fragmentContainerTransition.lastIn
        View view = fragment.getView()
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear()
            return null
        }
        ArrayMap arrayMap2 = ArrayMap()
        fragmentTransitionImpl.findNamedViews(arrayMap2, view)
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction
        if (fragmentContainerTransition.lastInIsPop) {
            SharedElementCallback exitTransitionCallback = fragment.getExitTransitionCallback()
            arrayList = backStackRecord.mSharedElementSourceNames
            sharedElementCallback = exitTransitionCallback
        } else {
            SharedElementCallback enterTransitionCallback = fragment.getEnterTransitionCallback()
            arrayList = backStackRecord.mSharedElementTargetNames
            sharedElementCallback = enterTransitionCallback
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList)
            arrayMap2.retainAll(arrayMap.values())
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2)
            for (Int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size)
                View view2 = (View) arrayMap2.get(str)
                if (view2 == null) {
                    String strFindKeyForValue2 = findKeyForValue(arrayMap, str)
                    if (strFindKeyForValue2 != null) {
                        arrayMap.remove(strFindKeyForValue2)
                    }
                } else if (!str.equals(ViewCompat.getTransitionName(view2)) && (strFindKeyForValue = findKeyForValue(arrayMap, str)) != null) {
                    arrayMap.put(strFindKeyForValue, ViewCompat.getTransitionName(view2))
                }
            }
        } else {
            retainValues(arrayMap, arrayMap2)
        }
        return arrayMap2
    }

    private fun captureOutSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        ArrayList arrayList
        SharedElementCallback sharedElementCallback
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear()
            return null
        }
        Fragment fragment = fragmentContainerTransition.firstOut
        ArrayMap arrayMap2 = ArrayMap()
        fragmentTransitionImpl.findNamedViews(arrayMap2, fragment.getView())
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction
        if (fragmentContainerTransition.firstOutIsPop) {
            SharedElementCallback enterTransitionCallback = fragment.getEnterTransitionCallback()
            arrayList = backStackRecord.mSharedElementTargetNames
            sharedElementCallback = enterTransitionCallback
        } else {
            SharedElementCallback exitTransitionCallback = fragment.getExitTransitionCallback()
            arrayList = backStackRecord.mSharedElementSourceNames
            sharedElementCallback = exitTransitionCallback
        }
        arrayMap2.retainAll(arrayList)
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2)
            for (Int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size)
                View view = (View) arrayMap2.get(str)
                if (view == null) {
                    arrayMap.remove(str)
                } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                    arrayMap.put(ViewCompat.getTransitionName(view), (String) arrayMap.remove(str))
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet())
        }
        return arrayMap2
    }

    private fun chooseImpl(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = ArrayList()
        if (fragment != null) {
            Object exitTransition = fragment.getExitTransition()
            if (exitTransition != null) {
                arrayList.add(exitTransition)
            }
            Object returnTransition = fragment.getReturnTransition()
            if (returnTransition != null) {
                arrayList.add(returnTransition)
            }
            Object sharedElementReturnTransition = fragment.getSharedElementReturnTransition()
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition)
            }
        }
        if (fragment2 != null) {
            Object enterTransition = fragment2.getEnterTransition()
            if (enterTransition != null) {
                arrayList.add(enterTransition)
            }
            Object reenterTransition = fragment2.getReenterTransition()
            if (reenterTransition != null) {
                arrayList.add(reenterTransition)
            }
            Object sharedElementEnterTransition = fragment2.getSharedElementEnterTransition()
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition)
            }
        }
        if (arrayList.isEmpty()) {
            return null
        }
        if (PLATFORM_IMPL != null && canHandleAll(PLATFORM_IMPL, arrayList)) {
            return PLATFORM_IMPL
        }
        if (SUPPORT_IMPL != null && canHandleAll(SUPPORT_IMPL, arrayList)) {
            return SUPPORT_IMPL
        }
        if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
            return null
        }
        throw IllegalArgumentException("Invalid Transition types")
    }

    static ArrayList configureEnteringExitingViews(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, ArrayList arrayList, View view) {
        ArrayList arrayList2 = null
        if (obj != null) {
            arrayList2 = ArrayList()
            View view2 = fragment.getView()
            if (view2 != null) {
                fragmentTransitionImpl.captureTransitioningViews(arrayList2, view2)
            }
            if (arrayList != null) {
                arrayList2.removeAll(arrayList)
            }
            if (!arrayList2.isEmpty()) {
                arrayList2.add(view)
                fragmentTransitionImpl.addTargets(obj, arrayList2)
            }
        }
        return arrayList2
    }

    private fun configureSharedElementsOrdered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final View view, final ArrayMap arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList arrayList, final ArrayList arrayList2, final Object obj, Object obj2) {
        Object obj3
        final Rect rect
        val fragment = fragmentContainerTransition.lastIn
        val fragment2 = fragmentContainerTransition.firstOut
        if (fragment == null || fragment2 == null) {
            return null
        }
        val z = fragmentContainerTransition.lastInIsPop
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z)
        ArrayMap arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition)
        if (arrayMap.isEmpty()) {
            obj3 = null
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values())
            obj3 = sharedElementTransition
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true)
        if (obj3 != null) {
            rect = Rect()
            fragmentTransitionImpl.setSharedElementTargets(obj3, view, arrayList)
            setOutEpicenter(fragmentTransitionImpl, obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction)
            if (obj != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect)
            }
        } else {
            rect = null
        }
        val obj4 = obj3
        OneShotPreDrawListener.add(viewGroup, Runnable() { // from class: android.support.v4.app.FragmentTransition.4
            @Override // java.lang.Runnable
            public final Unit run() {
                ArrayMap arrayMapCaptureInSharedElements = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, arrayMap, obj4, fragmentContainerTransition)
                if (arrayMapCaptureInSharedElements != null) {
                    arrayList2.addAll(arrayMapCaptureInSharedElements.values())
                    arrayList2.add(view)
                }
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false)
                if (obj4 != null) {
                    fragmentTransitionImpl.swapSharedElementTargets(obj4, arrayList, arrayList2)
                    View inEpicenterView = FragmentTransition.getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z)
                    if (inEpicenterView != null) {
                        fragmentTransitionImpl.getBoundsOnScreen(inEpicenterView, rect)
                    }
                }
            }
        })
        return obj3
    }

    private fun configureSharedElementsReordered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, ArrayList arrayList2, Object obj, Object obj2) {
        Object obj3
        final Rect rect
        final View inEpicenterView
        val fragment = fragmentContainerTransition.lastIn
        val fragment2 = fragmentContainerTransition.firstOut
        if (fragment != null) {
            fragment.getView().setVisibility(0)
        }
        if (fragment == null || fragment2 == null) {
            return null
        }
        val z = fragmentContainerTransition.lastInIsPop
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z)
        ArrayMap arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition)
        val arrayMapCaptureInSharedElements = captureInSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition)
        if (arrayMap.isEmpty()) {
            obj3 = null
            if (arrayMapCaptureOutSharedElements != null) {
                arrayMapCaptureOutSharedElements.clear()
            }
            if (arrayMapCaptureInSharedElements != null) {
                arrayMapCaptureInSharedElements.clear()
            }
        } else {
            addSharedElementsWithMatchingNames(arrayList, arrayMapCaptureOutSharedElements, arrayMap.keySet())
            addSharedElementsWithMatchingNames(arrayList2, arrayMapCaptureInSharedElements, arrayMap.values())
            obj3 = sharedElementTransition
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true)
        if (obj3 != null) {
            arrayList2.add(view)
            fragmentTransitionImpl.setSharedElementTargets(obj3, view, arrayList)
            setOutEpicenter(fragmentTransitionImpl, obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction)
            rect = Rect()
            inEpicenterView = getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z)
            if (inEpicenterView != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect)
            }
        } else {
            rect = null
            inEpicenterView = null
        }
        OneShotPreDrawListener.add(viewGroup, Runnable() { // from class: android.support.v4.app.FragmentTransition.3
            @Override // java.lang.Runnable
            public final Unit run() {
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false)
                if (inEpicenterView != null) {
                    fragmentTransitionImpl.getBoundsOnScreen(inEpicenterView, rect)
                }
            }
        })
        return obj3
    }

    private fun configureTransitionsOrdered(FragmentManagerImpl fragmentManagerImpl, Int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap arrayMap) {
        Fragment fragment
        Fragment fragment2
        FragmentTransitionImpl fragmentTransitionImplChooseImpl
        ViewGroup viewGroup = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i) : null
        if (viewGroup == null || (fragmentTransitionImplChooseImpl = chooseImpl((fragment2 = fragmentContainerTransition.firstOut), (fragment = fragmentContainerTransition.lastIn))) == null) {
            return
        }
        Boolean z = fragmentContainerTransition.lastInIsPop
        Boolean z2 = fragmentContainerTransition.firstOutIsPop
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z)
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2)
        ArrayList arrayList = ArrayList()
        ArrayList arrayList2 = ArrayList()
        Object objConfigureSharedElementsOrdered = configureSharedElementsOrdered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition)
        if (enterTransition == null && objConfigureSharedElementsOrdered == null && exitTransition == null) {
            return
        }
        ArrayList arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayList, view)
        Object obj = (arrayListConfigureEnteringExitingViews == null || arrayListConfigureEnteringExitingViews.isEmpty()) ? null : exitTransition
        fragmentTransitionImplChooseImpl.addTarget(enterTransition, view)
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, obj, objConfigureSharedElementsOrdered, fragment, fragmentContainerTransition.lastInIsPop)
        if (objMergeTransitions != null) {
            ArrayList arrayList3 = ArrayList()
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayList3, obj, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsOrdered, arrayList2)
            scheduleTargetChange(fragmentTransitionImplChooseImpl, viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, obj, arrayListConfigureEnteringExitingViews)
            fragmentTransitionImplChooseImpl.setNameOverridesOrdered(viewGroup, arrayList2, arrayMap)
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions)
            fragmentTransitionImplChooseImpl.scheduleNameReset(viewGroup, arrayList2, arrayMap)
        }
    }

    private fun configureTransitionsReordered(FragmentManagerImpl fragmentManagerImpl, Int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap arrayMap) {
        Fragment fragment
        Fragment fragment2
        FragmentTransitionImpl fragmentTransitionImplChooseImpl
        ViewGroup viewGroup = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i) : null
        if (viewGroup == null || (fragmentTransitionImplChooseImpl = chooseImpl((fragment2 = fragmentContainerTransition.firstOut), (fragment = fragmentContainerTransition.lastIn))) == null) {
            return
        }
        Boolean z = fragmentContainerTransition.lastInIsPop
        Boolean z2 = fragmentContainerTransition.firstOutIsPop
        ArrayList arrayList = ArrayList()
        ArrayList arrayList2 = ArrayList()
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z)
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2)
        Object objConfigureSharedElementsReordered = configureSharedElementsReordered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition)
        if (enterTransition == null && objConfigureSharedElementsReordered == null && exitTransition == null) {
            return
        }
        ArrayList arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayList2, view)
        ArrayList arrayListConfigureEnteringExitingViews2 = configureEnteringExitingViews(fragmentTransitionImplChooseImpl, enterTransition, fragment, arrayList, view)
        setViewVisibility(arrayListConfigureEnteringExitingViews2, 4)
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, exitTransition, objConfigureSharedElementsReordered, fragment, z)
        if (objMergeTransitions != null) {
            replaceHide(fragmentTransitionImplChooseImpl, exitTransition, fragment2, arrayListConfigureEnteringExitingViews)
            ArrayList arrayListPrepareSetNameOverridesReordered = fragmentTransitionImplChooseImpl.prepareSetNameOverridesReordered(arrayList)
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayListConfigureEnteringExitingViews2, exitTransition, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsReordered, arrayList)
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions)
            fragmentTransitionImplChooseImpl.setNameOverridesReordered(viewGroup, arrayList2, arrayList, arrayListPrepareSetNameOverridesReordered, arrayMap)
            setViewVisibility(arrayListConfigureEnteringExitingViews2, 0)
            fragmentTransitionImplChooseImpl.swapSharedElementTargets(objConfigureSharedElementsReordered, arrayList2, arrayList)
        }
    }

    private fun ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray sparseArray, Int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition
        }
        FragmentContainerTransition fragmentContainerTransition2 = FragmentContainerTransition()
        sparseArray.put(i, fragmentContainerTransition2)
        return fragmentContainerTransition2
    }

    private fun findKeyForValue(ArrayMap arrayMap, String str) {
        Int size = arrayMap.size()
        for (Int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return (String) arrayMap.keyAt(i)
            }
        }
        return null
    }

    private fun getEnterTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Boolean z) {
        if (fragment == null) {
            return null
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReenterTransition() : fragment.getEnterTransition())
    }

    private fun getExitTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Boolean z) {
        if (fragment == null) {
            return null
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReturnTransition() : fragment.getExitTransition())
    }

    static View getInEpicenterView(ArrayMap arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, Boolean z) {
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction
        if (obj == null || arrayMap == null || backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return null
        }
        return (View) arrayMap.get(z ? (String) backStackRecord.mSharedElementSourceNames.get(0) : (String) backStackRecord.mSharedElementTargetNames.get(0))
    }

    private fun getSharedElementTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Fragment fragment2, Boolean z) {
        if (fragment == null || fragment2 == null) {
            return null
        }
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition()))
    }

    private fun mergeTransitions(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, Object obj3, Fragment fragment, Boolean z) {
        Boolean allowReturnTransitionOverlap = true
        if (obj != null && obj2 != null && fragment != null) {
            allowReturnTransitionOverlap = z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap()
        }
        return allowReturnTransitionOverlap ? fragmentTransitionImpl.mergeTransitionsTogether(obj2, obj, obj3) : fragmentTransitionImpl.mergeTransitionsInSequence(obj2, obj, obj3)
    }

    private fun replaceHide(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, final ArrayList arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true)
            fragmentTransitionImpl.scheduleHideFragmentView(obj, fragment.getView(), arrayList)
            OneShotPreDrawListener.add(fragment.mContainer, Runnable() { // from class: android.support.v4.app.FragmentTransition.1
                @Override // java.lang.Runnable
                public final Unit run() {
                    FragmentTransition.setViewVisibility(arrayList, 4)
                }
            })
        }
    }

    private fun resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (Exception e) {
            return null
        }
    }

    private fun retainValues(ArrayMap arrayMap, ArrayMap arrayMap2) {
        for (Int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey((String) arrayMap.valueAt(size))) {
                arrayMap.removeAt(size)
            }
        }
    }

    private fun scheduleTargetChange(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList arrayList, final Object obj, final ArrayList arrayList2, final Object obj2, final ArrayList arrayList3) {
        OneShotPreDrawListener.add(viewGroup, Runnable() { // from class: android.support.v4.app.FragmentTransition.2
            @Override // java.lang.Runnable
            public final Unit run() {
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view)
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view))
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList arrayList4 = ArrayList()
                        arrayList4.add(view)
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, arrayList4)
                    }
                    arrayList3.clear()
                    arrayList3.add(view)
                }
            }
        })
    }

    private fun setOutEpicenter(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, ArrayMap arrayMap, Boolean z, BackStackRecord backStackRecord) {
        if (backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return
        }
        View view = (View) arrayMap.get(z ? (String) backStackRecord.mSharedElementTargetNames.get(0) : (String) backStackRecord.mSharedElementSourceNames.get(0))
        fragmentTransitionImpl.setEpicenter(obj, view)
        if (obj2 != null) {
            fragmentTransitionImpl.setEpicenter(obj2, view)
        }
    }

    static Unit setViewVisibility(ArrayList arrayList, Int i) {
        if (arrayList == null) {
            return
        }
        for (Int size = arrayList.size() - 1; size >= 0; size--) {
            ((View) arrayList.get(size)).setVisibility(i)
        }
    }

    static Unit startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList arrayList, ArrayList arrayList2, Int i, Int i2, Boolean z) {
        if (fragmentManagerImpl.mCurState <= 0) {
            return
        }
        SparseArray sparseArray = SparseArray()
        for (Int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i3)
            if (((Boolean) arrayList2.get(i3)).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z)
            } else {
                calculateFragments(backStackRecord, sparseArray, z)
            }
        }
        if (sparseArray.size() != 0) {
            View view = View(fragmentManagerImpl.mHost.getContext())
            Int size = sparseArray.size()
            for (Int i4 = 0; i4 < size; i4++) {
                Int iKeyAt = sparseArray.keyAt(i4)
                ArrayMap arrayMapCalculateNameOverrides = calculateNameOverrides(iKeyAt, arrayList, arrayList2, i, i2)
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4)
                if (z) {
                    configureTransitionsReordered(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides)
                } else {
                    configureTransitionsOrdered(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides)
                }
            }
        }
    }

    static Boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true
    }
}
