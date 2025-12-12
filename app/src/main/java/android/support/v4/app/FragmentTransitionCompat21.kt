package android.support.v4.app

import android.graphics.Rect
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import java.util.List

@RequiresApi(21)
class FragmentTransitionCompat21 extends FragmentTransitionImpl {
    FragmentTransitionCompat21() {
    }

    private fun hasSimpleTarget(Transition transition) {
        return (isNullOrEmpty(transition.getTargetIds()) && isNullOrEmpty(transition.getTargetNames()) && isNullOrEmpty(transition.getTargetTypes())) ? false : true
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun addTarget(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).addTarget(view)
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun addTargets(Object obj, ArrayList arrayList) {
        Transition transition = (Transition) obj
        if (transition == null) {
            return
        }
        if (transition is TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition
            Int transitionCount = transitionSet.getTransitionCount()
            for (Int i = 0; i < transitionCount; i++) {
                addTargets(transitionSet.getTransitionAt(i), arrayList)
            }
            return
        }
        if (hasSimpleTarget(transition) || !isNullOrEmpty(transition.getTargets())) {
            return
        }
        Int size = arrayList.size()
        for (Int i2 = 0; i2 < size; i2++) {
            transition.addTarget((View) arrayList.get(i2))
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj)
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun canHandle(Object obj) {
        return obj is Transition
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun cloneTransition(Object obj) {
        if (obj != null) {
            return ((Transition) obj).clone()
        }
        return null
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun mergeTransitionsInSequence(Object obj, Object obj2, Object obj3) {
        Transition ordering = null
        Transition transition = (Transition) obj
        Transition transition2 = (Transition) obj2
        Transition transition3 = (Transition) obj3
        if (transition != null && transition2 != null) {
            ordering = TransitionSet().addTransition(transition).addTransition(transition2).setOrdering(1)
        } else if (transition != null) {
            ordering = transition
        } else if (transition2 != null) {
            ordering = transition2
        }
        if (transition3 == null) {
            return ordering
        }
        TransitionSet transitionSet = TransitionSet()
        if (ordering != null) {
            transitionSet.addTransition(ordering)
        }
        transitionSet.addTransition(transition3)
        return transitionSet
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun mergeTransitionsTogether(Object obj, Object obj2, Object obj3) {
        TransitionSet transitionSet = TransitionSet()
        if (obj != null) {
            transitionSet.addTransition((Transition) obj)
        }
        if (obj2 != null) {
            transitionSet.addTransition((Transition) obj2)
        }
        if (obj3 != null) {
            transitionSet.addTransition((Transition) obj3)
        }
        return transitionSet
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun removeTarget(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).removeTarget(view)
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun replaceTargets(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        List<View> targets
        Transition transition = (Transition) obj
        if (transition is TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition
            Int transitionCount = transitionSet.getTransitionCount()
            for (Int i = 0; i < transitionCount; i++) {
                replaceTargets(transitionSet.getTransitionAt(i), arrayList, arrayList2)
            }
            return
        }
        if (hasSimpleTarget(transition) || (targets = transition.getTargets()) == null || targets.size() != arrayList.size() || !targets.containsAll(arrayList)) {
            return
        }
        Int size = arrayList2 == null ? 0 : arrayList2.size()
        for (Int i2 = 0; i2 < size; i2++) {
            transition.addTarget((View) arrayList2.get(i2))
        }
        for (Int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            transition.removeTarget((View) arrayList.get(size2))
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun scheduleHideFragmentView(Object obj, final View view, final ArrayList arrayList) {
        ((Transition) obj).addListener(new Transition.TransitionListener() { // from class: android.support.v4.app.FragmentTransitionCompat21.2
            @Override // android.transition.Transition.TransitionListener
            fun onTransitionCancel(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionEnd(Transition transition) {
                transition.removeListener(this)
                view.setVisibility(8)
                Int size = arrayList.size()
                for (Int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0)
                }
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionPause(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionResume(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionStart(Transition transition) {
            }
        })
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun scheduleRemoveTargets(Object obj, final Object obj2, final ArrayList arrayList, final Object obj3, final ArrayList arrayList2, final Object obj4, final ArrayList arrayList3) {
        ((Transition) obj).addListener(new Transition.TransitionListener() { // from class: android.support.v4.app.FragmentTransitionCompat21.3
            @Override // android.transition.Transition.TransitionListener
            fun onTransitionCancel(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionEnd(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionPause(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionResume(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            fun onTransitionStart(Transition transition) {
                if (obj2 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj2, arrayList, null)
                }
                if (obj3 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj3, arrayList2, null)
                }
                if (obj4 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj4, arrayList3, null)
                }
            }
        })
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun setEpicenter(Object obj, final Rect rect) {
        if (obj != null) {
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() { // from class: android.support.v4.app.FragmentTransitionCompat21.4
                @Override // android.transition.Transition.EpicenterCallback
                fun onGetEpicenter(Transition transition) {
                    if (rect == null || rect.isEmpty()) {
                        return null
                    }
                    return rect
                }
            })
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun setEpicenter(Object obj, View view) {
        if (view != null) {
            val rect = Rect()
            getBoundsOnScreen(view, rect)
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() { // from class: android.support.v4.app.FragmentTransitionCompat21.1
                @Override // android.transition.Transition.EpicenterCallback
                fun onGetEpicenter(Transition transition) {
                    return rect
                }
            })
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun setSharedElementTargets(Object obj, View view, ArrayList arrayList) {
        TransitionSet transitionSet = (TransitionSet) obj
        List<View> targets = transitionSet.getTargets()
        targets.clear()
        Int size = arrayList.size()
        for (Int i = 0; i < size; i++) {
            bfsAddViewChildren(targets, (View) arrayList.get(i))
        }
        targets.add(view)
        arrayList.add(view)
        addTargets(transitionSet, arrayList)
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun swapSharedElementTargets(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        TransitionSet transitionSet = (TransitionSet) obj
        if (transitionSet != null) {
            transitionSet.getTargets().clear()
            transitionSet.getTargets().addAll(arrayList2)
            replaceTargets(transitionSet, arrayList, arrayList2)
        }
    }

    @Override // android.support.v4.app.FragmentTransitionImpl
    fun wrapTransitionInSet(Object obj) {
        if (obj == null) {
            return null
        }
        TransitionSet transitionSet = TransitionSet()
        transitionSet.addTransition((Transition) obj)
        return transitionSet
    }
}
