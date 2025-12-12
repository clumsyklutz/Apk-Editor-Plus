package android.support.v4.app

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelStore
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.support.v4.util.ArraySet
import android.support.v4.util.DebugUtils
import android.support.v4.util.LogWriter
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import jadx.core.codegen.CodeWriter
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.concurrent.CopyOnWriteArrayList

final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
    static val ANIM_DUR = 220
    public static val ANIM_STYLE_CLOSE_ENTER = 3
    public static val ANIM_STYLE_CLOSE_EXIT = 4
    public static val ANIM_STYLE_FADE_ENTER = 5
    public static val ANIM_STYLE_FADE_EXIT = 6
    public static val ANIM_STYLE_OPEN_ENTER = 1
    public static val ANIM_STYLE_OPEN_EXIT = 2
    static val TAG = "FragmentManager"
    static val TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state"
    static val TARGET_STATE_TAG = "android:target_state"
    static val USER_VISIBLE_HINT_TAG = "android:user_visible_hint"
    static val VIEW_STATE_TAG = "android:view_state"
    SparseArray mActive
    ArrayList mAvailBackStackIndices
    ArrayList mBackStack
    ArrayList mBackStackChangeListeners
    ArrayList mBackStackIndices
    FragmentContainer mContainer
    ArrayList mCreatedMenus
    Boolean mDestroyed
    Boolean mExecutingActions
    Boolean mHavePendingDeferredStart
    FragmentHostCallback mHost
    Boolean mNeedMenuInvalidate
    String mNoTransactionsBecause
    Fragment mParent
    ArrayList mPendingActions
    ArrayList mPostponedTransactions

    @Nullable
    Fragment mPrimaryNav
    FragmentManagerNonConfig mSavedNonConfig
    Boolean mStateSaved
    Boolean mStopped
    ArrayList mTmpAddedFragments
    ArrayList mTmpIsPop
    ArrayList mTmpRecords
    static Boolean DEBUG = false
    static Field sAnimationListenerField = null
    static val DECELERATE_QUINT = DecelerateInterpolator(2.5f)
    static val DECELERATE_CUBIC = DecelerateInterpolator(1.5f)
    static val ACCELERATE_QUINT = AccelerateInterpolator(2.5f)
    static val ACCELERATE_CUBIC = AccelerateInterpolator(1.5f)
    Int mNextFragmentIndex = 0
    val mAdded = ArrayList()
    private val mLifecycleCallbacks = CopyOnWriteArrayList()
    Int mCurState = 0
    Bundle mStateBundle = null
    SparseArray mStateArray = null
    Runnable mExecCommit = Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.1
        @Override // java.lang.Runnable
        fun run() {
            FragmentManagerImpl.this.execPendingActions()
        }
    }

    class AnimateOnHWLayerIfNeededListener extends AnimationListenerWrapper {
        View mView

        AnimateOnHWLayerIfNeededListener(View view, Animation.AnimationListener animationListener) {
            super(animationListener)
            this.mView = view
        }

        @Override // android.support.v4.app.FragmentManagerImpl.AnimationListenerWrapper, android.view.animation.Animation.AnimationListener
        @CallSuper
        fun onAnimationEnd(Animation animation) {
            if (ViewCompat.isAttachedToWindow(this.mView) || Build.VERSION.SDK_INT >= 24) {
                this.mView.post(Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.1
                    @Override // java.lang.Runnable
                    fun run() {
                        AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null)
                    }
                })
            } else {
                this.mView.setLayerType(0, null)
            }
            super.onAnimationEnd(animation)
        }
    }

    class AnimationListenerWrapper implements Animation.AnimationListener {
        private final Animation.AnimationListener mWrapped

        AnimationListenerWrapper(Animation.AnimationListener animationListener) {
            this.mWrapped = animationListener
        }

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        fun onAnimationEnd(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationEnd(animation)
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        fun onAnimationRepeat(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationRepeat(animation)
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        fun onAnimationStart(Animation animation) {
            if (this.mWrapped != null) {
                this.mWrapped.onAnimationStart(animation)
            }
        }
    }

    class AnimationOrAnimator {
        public final Animation animation
        public final Animator animator

        AnimationOrAnimator(Animator animator) {
            this.animation = null
            this.animator = animator
            if (animator == null) {
                throw IllegalStateException("Animator cannot be null")
            }
        }

        AnimationOrAnimator(Animation animation) {
            this.animation = animation
            this.animator = null
            if (animation == null) {
                throw IllegalStateException("Animation cannot be null")
            }
        }
    }

    class AnimatorOnHWLayerIfNeededListener extends AnimatorListenerAdapter {
        View mView

        AnimatorOnHWLayerIfNeededListener(View view) {
            this.mView = view
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        fun onAnimationEnd(Animator animator) {
            this.mView.setLayerType(0, null)
            animator.removeListener(this)
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        fun onAnimationStart(Animator animator) {
            this.mView.setLayerType(2, null)
        }
    }

    class EndViewTransitionAnimator extends AnimationSet implements Runnable {
        private Boolean mAnimating
        private final View mChild
        private Boolean mEnded
        private final ViewGroup mParent
        private Boolean mTransitionEnded

        EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
            super(false)
            this.mAnimating = true
            this.mParent = viewGroup
            this.mChild = view
            addAnimation(animation)
            this.mParent.post(this)
        }

        @Override // android.view.animation.AnimationSet, android.view.animation.Animation
        fun getTransformation(Long j, Transformation transformation) {
            this.mAnimating = true
            if (this.mEnded) {
                return !this.mTransitionEnded
            }
            if (super.getTransformation(j, transformation)) {
                return true
            }
            this.mEnded = true
            OneShotPreDrawListener.add(this.mParent, this)
            return true
        }

        @Override // android.view.animation.Animation
        fun getTransformation(Long j, Transformation transformation, Float f) {
            this.mAnimating = true
            if (this.mEnded) {
                return !this.mTransitionEnded
            }
            if (super.getTransformation(j, transformation, f)) {
                return true
            }
            this.mEnded = true
            OneShotPreDrawListener.add(this.mParent, this)
            return true
        }

        @Override // java.lang.Runnable
        fun run() {
            if (this.mEnded || !this.mAnimating) {
                this.mParent.endViewTransition(this.mChild)
                this.mTransitionEnded = true
            } else {
                this.mAnimating = false
                this.mParent.post(this)
            }
        }
    }

    final class FragmentLifecycleCallbacksHolder {
        final FragmentManager.FragmentLifecycleCallbacks mCallback
        final Boolean mRecursive

        FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, Boolean z) {
            this.mCallback = fragmentLifecycleCallbacks
            this.mRecursive = z
        }
    }

    class FragmentTag {
        public static final Array<Int> Fragment = {R.attr.name, R.attr.id, R.attr.tag}
        public static val Fragment_id = 1
        public static val Fragment_name = 0
        public static val Fragment_tag = 2

        private constructor() {
        }
    }

    interface OpGenerator {
        Boolean generateOps(ArrayList arrayList, ArrayList arrayList2)
    }

    class PopBackStackState implements OpGenerator {
        final Int mFlags
        final Int mId
        final String mName

        PopBackStackState(String str, Int i, Int i2) {
            this.mName = str
            this.mId = i
            this.mFlags = i2
        }

        @Override // android.support.v4.app.FragmentManagerImpl.OpGenerator
        fun generateOps(ArrayList arrayList, ArrayList arrayList2) {
            FragmentManager fragmentManagerPeekChildFragmentManager
            if (FragmentManagerImpl.this.mPrimaryNav == null || this.mId >= 0 || this.mName != null || (fragmentManagerPeekChildFragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager()) == null || !fragmentManagerPeekChildFragmentManager.popBackStackImmediate()) {
                return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags)
            }
            return false
        }
    }

    class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        final Boolean mIsBack
        private Int mNumPostponed
        final BackStackRecord mRecord

        StartEnterTransitionListener(BackStackRecord backStackRecord, Boolean z) {
            this.mIsBack = z
            this.mRecord = backStackRecord
        }

        fun cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false)
        }

        fun completeTransaction() {
            Boolean z = this.mNumPostponed > 0
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager
            Int size = fragmentManagerImpl.mAdded.size()
            for (Int i = 0; i < size; i++) {
                Fragment fragment = (Fragment) fragmentManagerImpl.mAdded.get(i)
                fragment.setOnStartEnterTransitionListener(null)
                if (z && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition()
                }
            }
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, z ? false : true, true)
        }

        fun isReady() {
            return this.mNumPostponed == 0
        }

        @Override // android.support.v4.app.Fragment.OnStartEnterTransitionListener
        fun onStartEnterTransition() {
            this.mNumPostponed--
            if (this.mNumPostponed != 0) {
                return
            }
            this.mRecord.mManager.scheduleCommit()
        }

        @Override // android.support.v4.app.Fragment.OnStartEnterTransitionListener
        fun startListening() {
            this.mNumPostponed++
        }
    }

    FragmentManagerImpl() {
    }

    private fun addAddedFragments(ArraySet arraySet) throws NoSuchFieldException, Resources.NotFoundException {
        if (this.mCurState <= 0) {
            return
        }
        Int iMin = Math.min(this.mCurState, 3)
        Int size = this.mAdded.size()
        for (Int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment.mState < iMin) {
                moveToState(fragment, iMin, fragment.getNextAnim(), fragment.getNextTransition(), false)
                if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                    arraySet.add(fragment)
                }
            }
        }
    }

    private fun animateRemoveFragment(@NonNull final Fragment fragment, @NonNull AnimationOrAnimator animationOrAnimator, Int i) throws NoSuchFieldException {
        val view = fragment.mView
        val viewGroup = fragment.mContainer
        viewGroup.startViewTransition(view)
        fragment.setStateAfterAnimating(i)
        if (animationOrAnimator.animation != null) {
            EndViewTransitionAnimator endViewTransitionAnimator = EndViewTransitionAnimator(animationOrAnimator.animation, viewGroup, view)
            fragment.setAnimatingAway(fragment.mView)
            endViewTransitionAnimator.setAnimationListener(AnimationListenerWrapper(getAnimationListener(endViewTransitionAnimator)) { // from class: android.support.v4.app.FragmentManagerImpl.2
                @Override // android.support.v4.app.FragmentManagerImpl.AnimationListenerWrapper, android.view.animation.Animation.AnimationListener
                fun onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation)
                    viewGroup.post(Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.2.1
                        @Override // java.lang.Runnable
                        fun run() throws NoSuchFieldException, Resources.NotFoundException {
                            if (fragment.getAnimatingAway() != null) {
                                fragment.setAnimatingAway(null)
                                FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false)
                            }
                        }
                    })
                }
            })
            setHWLayerAnimListenerIfAlpha(view, animationOrAnimator)
            fragment.mView.startAnimation(endViewTransitionAnimator)
            return
        }
        Animator animator = animationOrAnimator.animator
        fragment.setAnimator(animationOrAnimator.animator)
        animator.addListener(AnimatorListenerAdapter() { // from class: android.support.v4.app.FragmentManagerImpl.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            fun onAnimationEnd(Animator animator2) throws NoSuchFieldException, Resources.NotFoundException {
                viewGroup.endViewTransition(view)
                Animator animator3 = fragment.getAnimator()
                fragment.setAnimator(null)
                if (animator3 == null || viewGroup.indexOfChild(view) >= 0) {
                    return
                }
                FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false)
            }
        })
        animator.setTarget(fragment.mView)
        setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator)
        animator.start()
    }

    private fun burpActive() {
        if (this.mActive != null) {
            for (Int size = this.mActive.size() - 1; size >= 0; size--) {
                if (this.mActive.valueAt(size) == null) {
                    this.mActive.delete(this.mActive.keyAt(size))
                }
            }
        }
    }

    private fun checkStateLoss() {
        if (isStateSaved()) {
            throw IllegalStateException("Can not perform this action after onSaveInstanceState")
        }
        if (this.mNoTransactionsBecause != null) {
            throw IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause)
        }
    }

    private fun cleanupExec() {
        this.mExecutingActions = false
        this.mTmpIsPop.clear()
        this.mTmpRecords.clear()
    }

    private fun dispatchStateChange(Int i) {
        try {
            this.mExecutingActions = true
            moveToState(i, false)
            this.mExecutingActions = false
            execPendingActions()
        } catch (Throwable th) {
            this.mExecutingActions = false
            throw th
        }
    }

    private fun endAnimatingAwayFragments() throws NoSuchFieldException, Resources.NotFoundException {
        Int size = this.mActive == null ? 0 : this.mActive.size()
        for (Int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) this.mActive.valueAt(i)
            if (fragment != null) {
                if (fragment.getAnimatingAway() != null) {
                    Int stateAfterAnimating = fragment.getStateAfterAnimating()
                    View animatingAway = fragment.getAnimatingAway()
                    Animation animation = animatingAway.getAnimation()
                    if (animation != null) {
                        animation.cancel()
                        animatingAway.clearAnimation()
                    }
                    fragment.setAnimatingAway(null)
                    moveToState(fragment, stateAfterAnimating, 0, 0, false)
                } else if (fragment.getAnimator() != null) {
                    fragment.getAnimator().end()
                }
            }
        }
    }

    private fun ensureExecReady(Boolean z) {
        if (this.mExecutingActions) {
            throw IllegalStateException("FragmentManager is already executing transactions")
        }
        if (this.mHost == null) {
            throw IllegalStateException("Fragment host has been destroyed")
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw IllegalStateException("Must be called from main thread of fragment host")
        }
        if (!z) {
            checkStateLoss()
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = ArrayList()
            this.mTmpIsPop = ArrayList()
        }
        this.mExecutingActions = true
        try {
            executePostponedTransaction(null, null)
        } finally {
            this.mExecutingActions = false
        }
    }

    private fun executeOps(ArrayList arrayList, ArrayList arrayList2, Int i, Int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i)
            if (((Boolean) arrayList2.get(i)).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1)
                backStackRecord.executePopOps(i == i2 + (-1))
            } else {
                backStackRecord.bumpBackStackNesting(1)
                backStackRecord.executeOps()
            }
            i++
        }
    }

    private fun executeOpsTogether(ArrayList arrayList, ArrayList arrayList2, Int i, Int i2) throws NoSuchFieldException, Resources.NotFoundException {
        Int iPostponePostponableTransactions
        Boolean z = ((BackStackRecord) arrayList.get(i)).mReorderingAllowed
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = ArrayList()
        } else {
            this.mTmpAddedFragments.clear()
        }
        this.mTmpAddedFragments.addAll(this.mAdded)
        Int i3 = i
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment()
        Boolean z2 = false
        while (i3 < i2) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i3)
            Fragment fragmentExpandOps = !((Boolean) arrayList2.get(i3)).booleanValue() ? backStackRecord.expandOps(this.mTmpAddedFragments, primaryNavigationFragment) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, primaryNavigationFragment)
            i3++
            primaryNavigationFragment = fragmentExpandOps
            z2 = z2 || backStackRecord.mAddToBackStack
        }
        this.mTmpAddedFragments.clear()
        if (!z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i2, false)
        }
        executeOps(arrayList, arrayList2, i, i2)
        if (z) {
            ArraySet arraySet = ArraySet()
            addAddedFragments(arraySet)
            iPostponePostponableTransactions = postponePostponableTransactions(arrayList, arrayList2, i, i2, arraySet)
            makeRemovedFragmentsInvisible(arraySet)
        } else {
            iPostponePostponableTransactions = i2
        }
        if (iPostponePostponableTransactions != i && z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, iPostponePostponableTransactions, true)
            moveToState(this.mCurState, true)
        }
        while (i < i2) {
            BackStackRecord backStackRecord2 = (BackStackRecord) arrayList.get(i)
            if (((Boolean) arrayList2.get(i)).booleanValue() && backStackRecord2.mIndex >= 0) {
                freeBackStackIndex(backStackRecord2.mIndex)
                backStackRecord2.mIndex = -1
            }
            backStackRecord2.runOnCommitRunnables()
            i++
        }
        if (z2) {
            reportBackStackChanged()
        }
    }

    private fun executePostponedTransaction(ArrayList arrayList, ArrayList arrayList2) {
        Int iIndexOf
        Int iIndexOf2
        Int i = 0
        Int size = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size()
        while (i < size) {
            StartEnterTransitionListener startEnterTransitionListener = (StartEnterTransitionListener) this.mPostponedTransactions.get(i)
            if (arrayList != null && !startEnterTransitionListener.mIsBack && (iIndexOf2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && ((Boolean) arrayList2.get(iIndexOf2)).booleanValue()) {
                startEnterTransitionListener.cancelTransaction()
            } else if (startEnterTransitionListener.isReady() || (arrayList != null && startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size()))) {
                this.mPostponedTransactions.remove(i)
                i--
                size--
                if (arrayList == null || startEnterTransitionListener.mIsBack || (iIndexOf = arrayList.indexOf(startEnterTransitionListener.mRecord)) == -1 || !((Boolean) arrayList2.get(iIndexOf)).booleanValue()) {
                    startEnterTransitionListener.completeTransaction()
                } else {
                    startEnterTransitionListener.cancelTransaction()
                }
            }
            i++
            size = size
        }
    }

    private fun findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer
        View view = fragment.mView
        if (viewGroup == null || view == null) {
            return null
        }
        for (Int iIndexOf = this.mAdded.indexOf(fragment) - 1; iIndexOf >= 0; iIndexOf--) {
            Fragment fragment2 = (Fragment) this.mAdded.get(iIndexOf)
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return fragment2
            }
        }
        return null
    }

    private fun forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                ((StartEnterTransitionListener) this.mPostponedTransactions.remove(0)).completeTransaction()
            }
        }
    }

    private fun generateOpsForPendingActions(ArrayList arrayList, ArrayList arrayList2) {
        synchronized (this) {
            if (this.mPendingActions == null || this.mPendingActions.size() == 0) {
                return false
            }
            Int size = this.mPendingActions.size()
            Boolean zGenerateOps = false
            for (Int i = 0; i < size; i++) {
                zGenerateOps |= ((OpGenerator) this.mPendingActions.get(i)).generateOps(arrayList, arrayList2)
            }
            this.mPendingActions.clear()
            this.mHost.getHandler().removeCallbacks(this.mExecCommit)
            return zGenerateOps
        }
    }

    private static Animation.AnimationListener getAnimationListener(Animation animation) throws NoSuchFieldException {
        try {
            if (sAnimationListenerField == null) {
                Field declaredField = Animation.class.getDeclaredField("mListener")
                sAnimationListenerField = declaredField
                declaredField.setAccessible(true)
            }
            return (Animation.AnimationListener) sAnimationListenerField.get(animation)
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Cannot access Animation's mListener field", e)
            return null
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "No field with the name mListener is found in Animation class", e2)
            return null
        }
    }

    static AnimationOrAnimator makeFadeAnimation(Context context, Float f, Float f2) {
        AlphaAnimation alphaAnimation = AlphaAnimation(f, f2)
        alphaAnimation.setInterpolator(DECELERATE_CUBIC)
        alphaAnimation.setDuration(220L)
        return AnimationOrAnimator(alphaAnimation)
    }

    static AnimationOrAnimator makeOpenCloseAnimation(Context context, Float f, Float f2, Float f3, Float f4) {
        AnimationSet animationSet = AnimationSet(false)
        ScaleAnimation scaleAnimation = ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f)
        scaleAnimation.setInterpolator(DECELERATE_QUINT)
        scaleAnimation.setDuration(220L)
        animationSet.addAnimation(scaleAnimation)
        AlphaAnimation alphaAnimation = AlphaAnimation(f3, f4)
        alphaAnimation.setInterpolator(DECELERATE_CUBIC)
        alphaAnimation.setDuration(220L)
        animationSet.addAnimation(alphaAnimation)
        return AnimationOrAnimator(animationSet)
    }

    private fun makeRemovedFragmentsInvisible(ArraySet arraySet) {
        Int size = arraySet.size()
        for (Int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) arraySet.valueAt(i)
            if (!fragment.mAdded) {
                View view = fragment.getView()
                fragment.mPostponedAlpha = view.getAlpha()
                view.setAlpha(0.0f)
            }
        }
    }

    static Boolean modifiesAlpha(Animator animator) {
        if (animator == null) {
            return false
        }
        if (animator is ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true
                }
            }
            return false
        }
        if (!(animator is AnimatorSet)) {
            return false
        }
        ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations()
        for (Int i = 0; i < childAnimations.size(); i++) {
            if (modifiesAlpha(childAnimations.get(i))) {
                return true
            }
        }
        return false
    }

    static Boolean modifiesAlpha(AnimationOrAnimator animationOrAnimator) {
        if (animationOrAnimator.animation is AlphaAnimation) {
            return true
        }
        if (!(animationOrAnimator.animation is AnimationSet)) {
            return modifiesAlpha(animationOrAnimator.animator)
        }
        List<Animation> animations = ((AnimationSet) animationOrAnimator.animation).getAnimations()
        for (Int i = 0; i < animations.size(); i++) {
            if (animations.get(i) instanceof AlphaAnimation) {
                return true
            }
        }
        return false
    }

    private fun popBackStackImmediate(String str, Int i, Int i2) {
        FragmentManager fragmentManagerPeekChildFragmentManager
        execPendingActions()
        ensureExecReady(true)
        if (this.mPrimaryNav != null && i < 0 && str == null && (fragmentManagerPeekChildFragmentManager = this.mPrimaryNav.peekChildFragmentManager()) != null && fragmentManagerPeekChildFragmentManager.popBackStackImmediate()) {
            return true
        }
        Boolean zPopBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2)
        if (zPopBackStackState) {
            this.mExecutingActions = true
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop)
            } finally {
                cleanupExec()
            }
        }
        doPendingDeferredStart()
        burpActive()
        return zPopBackStackState
    }

    private fun postponePostponableTransactions(ArrayList arrayList, ArrayList arrayList2, Int i, Int i2, ArraySet arraySet) throws NoSuchFieldException, Resources.NotFoundException {
        Int i3
        Int i4 = i2 - 1
        Int i5 = i2
        while (i4 >= i) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i4)
            Boolean zBooleanValue = ((Boolean) arrayList2.get(i4)).booleanValue()
            if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i4 + 1, i2)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = ArrayList()
                }
                StartEnterTransitionListener startEnterTransitionListener = StartEnterTransitionListener(backStackRecord, zBooleanValue)
                this.mPostponedTransactions.add(startEnterTransitionListener)
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener)
                if (zBooleanValue) {
                    backStackRecord.executeOps()
                } else {
                    backStackRecord.executePopOps(false)
                }
                Int i6 = i5 - 1
                if (i4 != i6) {
                    arrayList.remove(i4)
                    arrayList.add(i6, backStackRecord)
                }
                addAddedFragments(arraySet)
                i3 = i6
            } else {
                i3 = i5
            }
            i4--
            i5 = i3
        }
        return i5
    }

    private fun removeRedundantOperationsAndExecute(ArrayList arrayList, ArrayList arrayList2) throws NoSuchFieldException, Resources.NotFoundException {
        Int i
        Int i2 = 0
        if (arrayList == null || arrayList.isEmpty()) {
            return
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            throw IllegalStateException("Internal error with the back stack records")
        }
        executePostponedTransaction(arrayList, arrayList2)
        Int size = arrayList.size()
        Int i3 = 0
        while (i2 < size) {
            if (((BackStackRecord) arrayList.get(i2)).mReorderingAllowed) {
                i = i2
            } else {
                if (i3 != i2) {
                    executeOpsTogether(arrayList, arrayList2, i3, i2)
                }
                Int i4 = i2 + 1
                if (((Boolean) arrayList2.get(i2)).booleanValue()) {
                    while (i4 < size && ((Boolean) arrayList2.get(i4)).booleanValue() && !((BackStackRecord) arrayList.get(i4)).mReorderingAllowed) {
                        i4++
                    }
                }
                Int i5 = i4
                executeOpsTogether(arrayList, arrayList2, i2, i5)
                i3 = i5
                i = i5 - 1
            }
            i2 = i + 1
        }
        if (i3 != size) {
            executeOpsTogether(arrayList, arrayList2, i3, size)
        }
    }

    fun reverseTransit(Int i) {
        switch (i) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /* 4097 */:
                return 8194
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /* 4099 */:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE
            case 8194:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            default:
                return 0
        }
    }

    private fun setHWLayerAnimListenerIfAlpha(View view, AnimationOrAnimator animationOrAnimator) throws NoSuchFieldException {
        if (view == null || animationOrAnimator == null || !shouldRunOnHWLayer(view, animationOrAnimator)) {
            return
        }
        if (animationOrAnimator.animator != null) {
            animationOrAnimator.animator.addListener(AnimatorOnHWLayerIfNeededListener(view))
            return
        }
        Animation.AnimationListener animationListener = getAnimationListener(animationOrAnimator.animation)
        view.setLayerType(2, null)
        animationOrAnimator.animation.setAnimationListener(AnimateOnHWLayerIfNeededListener(view, animationListener))
    }

    private fun setRetaining(FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (fragmentManagerNonConfig == null) {
            return
        }
        List fragments = fragmentManagerNonConfig.getFragments()
        if (fragments != null) {
            Iterator it = fragments.iterator()
            while (it.hasNext()) {
                ((Fragment) it.next()).mRetaining = true
            }
        }
        List childNonConfigs = fragmentManagerNonConfig.getChildNonConfigs()
        if (childNonConfigs != null) {
            Iterator it2 = childNonConfigs.iterator()
            while (it2.hasNext()) {
                setRetaining((FragmentManagerNonConfig) it2.next())
            }
        }
    }

    static Boolean shouldRunOnHWLayer(View view, AnimationOrAnimator animationOrAnimator) {
        return view != null && animationOrAnimator != null && Build.VERSION.SDK_INT >= 19 && view.getLayerType() == 0 && ViewCompat.hasOverlappingRendering(view) && modifiesAlpha(animationOrAnimator)
    }

    private fun throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage())
        Log.e(TAG, "Activity state:")
        PrintWriter printWriter = PrintWriter(LogWriter(TAG))
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, printWriter, new String[0])
                throw runtimeException
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e)
                throw runtimeException
            }
        }
        try {
            dump("  ", null, printWriter, new String[0])
            throw runtimeException
        } catch (Exception e2) {
            Log.e(TAG, "Failed dumping state", e2)
            throw runtimeException
        }
    }

    fun transitToStyleIndex(Int i, Boolean z) {
        switch (i) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /* 4097 */:
                return z ? 1 : 2
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /* 4099 */:
                return z ? 5 : 6
            case 8194:
                return z ? 3 : 4
            default:
                return -1
        }
    }

    final Unit addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = ArrayList()
        }
        this.mBackStack.add(backStackRecord)
    }

    public final Unit addFragment(Fragment fragment, Boolean z) {
        if (DEBUG) {
            Log.v(TAG, "add: " + fragment)
        }
        makeActive(fragment)
        if (fragment.mDetached) {
            return
        }
        if (this.mAdded.contains(fragment)) {
            throw IllegalStateException("Fragment already added: " + fragment)
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment)
        }
        fragment.mAdded = true
        fragment.mRemoving = false
        if (fragment.mView == null) {
            fragment.mHiddenChanged = false
        }
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true
        }
        if (z) {
            moveToState(fragment)
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = ArrayList()
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener)
    }

    public final Int allocBackStackIndex(BackStackRecord backStackRecord) {
        Int size
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = ArrayList()
                }
                size = this.mBackStackIndices.size()
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + size + " to " + backStackRecord)
                }
                this.mBackStackIndices.add(backStackRecord)
            } else {
                size = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue()
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + size + " with " + backStackRecord)
                }
                this.mBackStackIndices.set(size, backStackRecord)
            }
        }
        return size
    }

    public final Unit attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost != null) {
            throw IllegalStateException("Already attached")
        }
        this.mHost = fragmentHostCallback
        this.mContainer = fragmentContainer
        this.mParent = fragment
    }

    public final Unit attachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "attach: " + fragment)
        }
        if (fragment.mDetached) {
            fragment.mDetached = false
            if (fragment.mAdded) {
                return
            }
            if (this.mAdded.contains(fragment)) {
                throw IllegalStateException("Fragment already added: " + fragment)
            }
            if (DEBUG) {
                Log.v(TAG, "add from attach: " + fragment)
            }
            synchronized (this.mAdded) {
                this.mAdded.add(fragment)
            }
            fragment.mAdded = true
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true
            }
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public final FragmentTransaction beginTransaction() {
        return BackStackRecord(this)
    }

    final Unit completeExecute(BackStackRecord backStackRecord, Boolean z, Boolean z2, Boolean z3) {
        if (z) {
            backStackRecord.executePopOps(z3)
        } else {
            backStackRecord.executeOps()
        }
        ArrayList arrayList = ArrayList(1)
        ArrayList arrayList2 = ArrayList(1)
        arrayList.add(backStackRecord)
        arrayList2.add(Boolean.valueOf(z))
        if (z2) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, 0, 1, true)
        }
        if (z3) {
            moveToState(this.mCurState, true)
        }
        if (this.mActive != null) {
            Int size = this.mActive.size()
            for (Int i = 0; i < size; i++) {
                Fragment fragment = (Fragment) this.mActive.valueAt(i)
                if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && backStackRecord.interactsWith(fragment.mContainerId)) {
                    if (fragment.mPostponedAlpha > 0.0f) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha)
                    }
                    if (z3) {
                        fragment.mPostponedAlpha = 0.0f
                    } else {
                        fragment.mPostponedAlpha = -1.0f
                        fragment.mIsNewlyAdded = false
                    }
                }
            }
        }
    }

    final Unit completeShowHideFragment(final Fragment fragment) throws NoSuchFieldException, Resources.NotFoundException {
        if (fragment.mView != null) {
            AnimationOrAnimator animationOrAnimatorLoadAnimation = loadAnimation(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle())
            if (animationOrAnimatorLoadAnimation == null || animationOrAnimatorLoadAnimation.animator == null) {
                if (animationOrAnimatorLoadAnimation != null) {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimatorLoadAnimation)
                    fragment.mView.startAnimation(animationOrAnimatorLoadAnimation.animation)
                    animationOrAnimatorLoadAnimation.animation.start()
                }
                fragment.mView.setVisibility((!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8)
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false)
                }
            } else {
                animationOrAnimatorLoadAnimation.animator.setTarget(fragment.mView)
                if (!fragment.mHidden) {
                    fragment.mView.setVisibility(0)
                } else if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false)
                } else {
                    val viewGroup = fragment.mContainer
                    val view = fragment.mView
                    viewGroup.startViewTransition(view)
                    animationOrAnimatorLoadAnimation.animator.addListener(AnimatorListenerAdapter() { // from class: android.support.v4.app.FragmentManagerImpl.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        fun onAnimationEnd(Animator animator) {
                            viewGroup.endViewTransition(view)
                            animator.removeListener(this)
                            if (fragment.mView != null) {
                                fragment.mView.setVisibility(8)
                            }
                        }
                    })
                }
                setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimatorLoadAnimation)
                animationOrAnimatorLoadAnimation.animator.start()
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true
        }
        fragment.mHiddenChanged = false
        fragment.onHiddenChanged(fragment.mHidden)
    }

    public final Unit detachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "detach: " + fragment)
        }
        if (fragment.mDetached) {
            return
        }
        fragment.mDetached = true
        if (fragment.mAdded) {
            if (DEBUG) {
                Log.v(TAG, "remove from detach: " + fragment)
            }
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment)
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true
            }
            fragment.mAdded = false
        }
    }

    public final Unit dispatchActivityCreated() {
        this.mStateSaved = false
        this.mStopped = false
        dispatchStateChange(2)
    }

    public final Unit dispatchConfigurationChanged(Configuration configuration) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mAdded.size()) {
                return
            }
            Fragment fragment = (Fragment) this.mAdded.get(i2)
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration)
            }
            i = i2 + 1
        }
    }

    public final Boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mCurState <= 0) {
            return false
        }
        for (Int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true
            }
        }
        return false
    }

    public final Unit dispatchCreate() {
        this.mStateSaved = false
        this.mStopped = false
        dispatchStateChange(1)
    }

    public final Boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mCurState <= 0) {
            return false
        }
        ArrayList arrayList = null
        Int i = 0
        Boolean z = false
        while (i < this.mAdded.size()) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                z = true
                if (arrayList == null) {
                    arrayList = ArrayList()
                }
                arrayList.add(fragment)
            }
            i++
            z = z
        }
        if (this.mCreatedMenus != null) {
            for (Int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment fragment2 = (Fragment) this.mCreatedMenus.get(i2)
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu()
                }
            }
        }
        this.mCreatedMenus = arrayList
        return z
    }

    public final Unit dispatchDestroy() {
        this.mDestroyed = true
        execPendingActions()
        dispatchStateChange(0)
        this.mHost = null
        this.mContainer = null
        this.mParent = null
    }

    public final Unit dispatchDestroyView() {
        dispatchStateChange(1)
    }

    public final Unit dispatchLowMemory() {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mAdded.size()) {
                return
            }
            Fragment fragment = (Fragment) this.mAdded.get(i2)
            if (fragment != null) {
                fragment.performLowMemory()
            }
            i = i2 + 1
        }
    }

    public final Unit dispatchMultiWindowModeChanged(Boolean z) {
        for (Int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.mAdded.get(size)
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z)
            }
        }
    }

    final Unit dispatchOnFragmentActivityCreated(@NonNull Fragment fragment, @Nullable Bundle bundle, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentActivityCreated(fragment, bundle, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentActivityCreated(this, fragment, bundle)
            }
        }
    }

    final Unit dispatchOnFragmentAttached(@NonNull Fragment fragment, @NonNull Context context, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentAttached(fragment, context, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentAttached(this, fragment, context)
            }
        }
    }

    final Unit dispatchOnFragmentCreated(@NonNull Fragment fragment, @Nullable Bundle bundle, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentCreated(fragment, bundle, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentCreated(this, fragment, bundle)
            }
        }
    }

    final Unit dispatchOnFragmentDestroyed(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDestroyed(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentDestroyed(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentDetached(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDetached(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentDetached(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentPaused(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPaused(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentPaused(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentPreAttached(@NonNull Fragment fragment, @NonNull Context context, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreAttached(fragment, context, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentPreAttached(this, fragment, context)
            }
        }
    }

    final Unit dispatchOnFragmentPreCreated(@NonNull Fragment fragment, @Nullable Bundle bundle, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreCreated(fragment, bundle, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentPreCreated(this, fragment, bundle)
            }
        }
    }

    final Unit dispatchOnFragmentResumed(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentResumed(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentResumed(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentSaveInstanceState(@NonNull Fragment fragment, @NonNull Bundle bundle, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentSaveInstanceState(fragment, bundle, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentSaveInstanceState(this, fragment, bundle)
            }
        }
    }

    final Unit dispatchOnFragmentStarted(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStarted(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentStarted(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentStopped(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStopped(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentStopped(this, fragment)
            }
        }
    }

    final Unit dispatchOnFragmentViewCreated(@NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewCreated(fragment, view, bundle, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewCreated(this, fragment, view, bundle)
            }
        }
    }

    final Unit dispatchOnFragmentViewDestroyed(@NonNull Fragment fragment, Boolean z) {
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager()
            if (fragmentManager is FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewDestroyed(fragment, true)
            }
        }
        Iterator it = this.mLifecycleCallbacks.iterator()
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next()
            if (!z || fragmentLifecycleCallbacksHolder.mRecursive) {
                fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewDestroyed(this, fragment)
            }
        }
    }

    public final Boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState <= 0) {
            return false
        }
        for (Int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true
            }
        }
        return false
    }

    public final Unit dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState <= 0) {
            return
        }
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mAdded.size()) {
                return
            }
            Fragment fragment = (Fragment) this.mAdded.get(i2)
            if (fragment != null) {
                fragment.performOptionsMenuClosed(menu)
            }
            i = i2 + 1
        }
    }

    public final Unit dispatchPause() {
        dispatchStateChange(3)
    }

    public final Unit dispatchPictureInPictureModeChanged(Boolean z) {
        for (Int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.mAdded.get(size)
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z)
            }
        }
    }

    public final Boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mCurState <= 0) {
            return false
        }
        Boolean z = false
        for (Int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z = true
            }
        }
        return z
    }

    public final Unit dispatchResume() {
        this.mStateSaved = false
        this.mStopped = false
        dispatchStateChange(4)
    }

    public final Unit dispatchStart() {
        this.mStateSaved = false
        this.mStopped = false
        dispatchStateChange(3)
    }

    public final Unit dispatchStop() {
        this.mStopped = true
        dispatchStateChange(2)
    }

    final Unit doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false
            startPendingDeferredFragments()
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        Int size
        Int size2
        Int size3
        Int size4
        Int size5
        String str2 = str + CodeWriter.INDENT
        if (this.mActive != null && (size5 = this.mActive.size()) > 0) {
            printWriter.print(str)
            printWriter.print("Active Fragments in ")
            printWriter.print(Integer.toHexString(System.identityHashCode(this)))
            printWriter.println(":")
            for (Int i = 0; i < size5; i++) {
                Fragment fragment = (Fragment) this.mActive.valueAt(i)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(i)
                printWriter.print(": ")
                printWriter.println(fragment)
                if (fragment != null) {
                    fragment.dump(str2, fileDescriptor, printWriter, strArr)
                }
            }
        }
        Int size6 = this.mAdded.size()
        if (size6 > 0) {
            printWriter.print(str)
            printWriter.println("Added Fragments:")
            for (Int i2 = 0; i2 < size6; i2++) {
                Fragment fragment2 = (Fragment) this.mAdded.get(i2)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(i2)
                printWriter.print(": ")
                printWriter.println(fragment2.toString())
            }
        }
        if (this.mCreatedMenus != null && (size4 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(str)
            printWriter.println("Fragments Created Menus:")
            for (Int i3 = 0; i3 < size4; i3++) {
                Fragment fragment3 = (Fragment) this.mCreatedMenus.get(i3)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(i3)
                printWriter.print(": ")
                printWriter.println(fragment3.toString())
            }
        }
        if (this.mBackStack != null && (size3 = this.mBackStack.size()) > 0) {
            printWriter.print(str)
            printWriter.println("Back Stack:")
            for (Int i4 = 0; i4 < size3; i4++) {
                BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(i4)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(i4)
                printWriter.print(": ")
                printWriter.println(backStackRecord.toString())
                backStackRecord.dump(str2, fileDescriptor, printWriter, strArr)
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str)
                printWriter.println("Back Stack Indices:")
                for (Int i5 = 0; i5 < size2; i5++) {
                    Object obj = (BackStackRecord) this.mBackStackIndices.get(i5)
                    printWriter.print(str)
                    printWriter.print("  #")
                    printWriter.print(i5)
                    printWriter.print(": ")
                    printWriter.println(obj)
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str)
                printWriter.print("mAvailBackStackIndices: ")
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()))
            }
        }
        if (this.mPendingActions != null && (size = this.mPendingActions.size()) > 0) {
            printWriter.print(str)
            printWriter.println("Pending Actions:")
            for (Int i6 = 0; i6 < size; i6++) {
                Object obj2 = (OpGenerator) this.mPendingActions.get(i6)
                printWriter.print(str)
                printWriter.print("  #")
                printWriter.print(i6)
                printWriter.print(": ")
                printWriter.println(obj2)
            }
        }
        printWriter.print(str)
        printWriter.println("FragmentManager misc state:")
        printWriter.print(str)
        printWriter.print("  mHost=")
        printWriter.println(this.mHost)
        printWriter.print(str)
        printWriter.print("  mContainer=")
        printWriter.println(this.mContainer)
        if (this.mParent != null) {
            printWriter.print(str)
            printWriter.print("  mParent=")
            printWriter.println(this.mParent)
        }
        printWriter.print(str)
        printWriter.print("  mCurState=")
        printWriter.print(this.mCurState)
        printWriter.print(" mStateSaved=")
        printWriter.print(this.mStateSaved)
        printWriter.print(" mStopped=")
        printWriter.print(this.mStopped)
        printWriter.print(" mDestroyed=")
        printWriter.println(this.mDestroyed)
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str)
            printWriter.print("  mNeedMenuInvalidate=")
            printWriter.println(this.mNeedMenuInvalidate)
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str)
            printWriter.print("  mNoTransactionsBecause=")
            printWriter.println(this.mNoTransactionsBecause)
        }
    }

    public final Unit enqueueAction(OpGenerator opGenerator, Boolean z) {
        if (!z) {
            checkStateLoss()
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                if (!z) {
                    throw IllegalStateException("Activity has been destroyed")
                }
            } else {
                if (this.mPendingActions == null) {
                    this.mPendingActions = ArrayList()
                }
                this.mPendingActions.add(opGenerator)
                scheduleCommit()
            }
        }
    }

    final Unit ensureInflatedFragmentView(Fragment fragment) {
        if (!fragment.mFromLayout || fragment.mPerformedCreateView) {
            return
        }
        fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState)
        if (fragment.mView == null) {
            fragment.mInnerView = null
            return
        }
        fragment.mInnerView = fragment.mView
        fragment.mView.setSaveFromParentEnabled(false)
        if (fragment.mHidden) {
            fragment.mView.setVisibility(8)
        }
        fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState)
        dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false)
    }

    public final Boolean execPendingActions() {
        ensureExecReady(true)
        Boolean z = false
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop)
                cleanupExec()
                z = true
            } catch (Throwable th) {
                cleanupExec()
                throw th
            }
        }
        doPendingDeferredStart()
        burpActive()
        return z
    }

    public final Unit execSingleAction(OpGenerator opGenerator, Boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return
        }
        ensureExecReady(z)
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop)
            } finally {
                cleanupExec()
            }
        }
        doPendingDeferredStart()
        burpActive()
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean executePendingTransactions() {
        Boolean zExecPendingActions = execPendingActions()
        forcePostponedTransactions()
        return zExecPendingActions
    }

    @Override // android.support.v4.app.FragmentManager
    @Nullable
    public final Fragment findFragmentById(Int i) {
        for (Int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.mAdded.get(size)
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment
            }
        }
        if (this.mActive != null) {
            for (Int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.mActive.valueAt(size2)
                if (fragment2 != null && fragment2.mFragmentId == i) {
                    return fragment2
                }
            }
        }
        return null
    }

    @Override // android.support.v4.app.FragmentManager
    @Nullable
    public final Fragment findFragmentByTag(@Nullable String str) {
        if (str != null) {
            for (Int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.mAdded.get(size)
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment
                }
            }
        }
        if (this.mActive != null && str != null) {
            for (Int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.mActive.valueAt(size2)
                if (fragment2 != null && str.equals(fragment2.mTag)) {
                    return fragment2
                }
            }
        }
        return null
    }

    public final Fragment findFragmentByWho(String str) {
        Fragment fragmentFindFragmentByWho
        if (this.mActive != null && str != null) {
            for (Int size = this.mActive.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.mActive.valueAt(size)
                if (fragment != null && (fragmentFindFragmentByWho = fragment.findFragmentByWho(str)) != null) {
                    return fragmentFindFragmentByWho
                }
            }
        }
        return null
    }

    public final Unit freeBackStackIndex(Int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, null)
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = ArrayList()
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + i)
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(i))
        }
    }

    final Int getActiveFragmentCount() {
        if (this.mActive == null) {
            return 0
        }
        return this.mActive.size()
    }

    final List getActiveFragments() {
        if (this.mActive == null) {
            return null
        }
        Int size = this.mActive.size()
        ArrayList arrayList = ArrayList(size)
        for (Int i = 0; i < size; i++) {
            arrayList.add(this.mActive.valueAt(i))
        }
        return arrayList
    }

    @Override // android.support.v4.app.FragmentManager
    public final FragmentManager.BackStackEntry getBackStackEntryAt(Int i) {
        return (FragmentManager.BackStackEntry) this.mBackStack.get(i)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size()
        }
        return 0
    }

    @Override // android.support.v4.app.FragmentManager
    @Nullable
    public final Fragment getFragment(Bundle bundle, String str) {
        Int i = bundle.getInt(str, -1)
        if (i == -1) {
            return null
        }
        Fragment fragment = (Fragment) this.mActive.get(i)
        if (fragment != null) {
            return fragment
        }
        throwException(IllegalStateException("Fragment no longer exists for key " + str + ": index " + i))
        return fragment
    }

    @Override // android.support.v4.app.FragmentManager
    public final List getFragments() {
        List list
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList()
        }
        synchronized (this.mAdded) {
            list = (List) this.mAdded.clone()
        }
        return list
    }

    final LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this
    }

    @Override // android.support.v4.app.FragmentManager
    @Nullable
    public final Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav
    }

    public final Unit hideFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "hide: " + fragment)
        }
        if (fragment.mHidden) {
            return
        }
        fragment.mHidden = true
        fragment.mHiddenChanged = fragment.mHiddenChanged ? false : true
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean isDestroyed() {
        return this.mDestroyed
    }

    final Boolean isStateAtLeast(Int i) {
        return this.mCurState >= i
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean isStateSaved() {
        return this.mStateSaved || this.mStopped
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final android.support.v4.app.FragmentManagerImpl.AnimationOrAnimator loadAnimation(android.support.v4.app.Fragment r10, Int r11, Boolean r12, Int r13) throws android.content.res.Resources.NotFoundException {
        /*
            r9 = this
            r8 = 1064933786(0x3f79999a, Float:0.975)
            r1 = 0
            r7 = 0
            r6 = 1065353216(0x3f800000, Float:1.0)
            Int r3 = r10.getNextAnim()
            android.view.animation.Animation r2 = r10.onCreateAnimation(r11, r12, r3)
            if (r2 == 0) goto L17
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = new android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator
            r0.<init>(r2)
        L16:
            return r0
        L17:
            android.animation.Animator r2 = r10.onCreateAnimator(r11, r12, r3)
            if (r2 == 0) goto L23
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = new android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator
            r0.<init>(r2)
            goto L16
        L23:
            if (r3 == 0) goto L7e
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.content.res.Resources r0 = r0.getResources()
            java.lang.String r0 = r0.getResourceTypeName(r3)
            java.lang.String r2 = "anim"
            Boolean r4 = r2.equals(r0)
            r2 = 0
            if (r4 == 0) goto L6a
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost     // Catch: android.content.res.Resources.NotFoundException -> L4e java.lang.RuntimeException -> L69
            android.content.Context r0 = r0.getContext()     // Catch: android.content.res.Resources.NotFoundException -> L4e java.lang.RuntimeException -> L69
            android.view.animation.Animation r5 = android.view.animation.AnimationUtils.loadAnimation(r0, r3)     // Catch: android.content.res.Resources.NotFoundException -> L4e java.lang.RuntimeException -> L69
            if (r5 == 0) goto L50
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = new android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator     // Catch: android.content.res.Resources.NotFoundException -> L4e java.lang.RuntimeException -> L69
            r0.<init>(r5)     // Catch: android.content.res.Resources.NotFoundException -> L4e java.lang.RuntimeException -> L69
            goto L16
        L4e:
            r0 = move-exception
            throw r0
        L50:
            r0 = 1
        L51:
            if (r0 != 0) goto L7e
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost     // Catch: java.lang.RuntimeException -> L65
            android.content.Context r0 = r0.getContext()     // Catch: java.lang.RuntimeException -> L65
            android.animation.Animator r2 = android.animation.AnimatorInflater.loadAnimator(r0, r3)     // Catch: java.lang.RuntimeException -> L65
            if (r2 == 0) goto L7e
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = new android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator     // Catch: java.lang.RuntimeException -> L65
            r0.<init>(r2)     // Catch: java.lang.RuntimeException -> L65
            goto L16
        L65:
            r0 = move-exception
            if (r4 == 0) goto L6c
            throw r0
        L69:
            r0 = move-exception
        L6a:
            r0 = r2
            goto L51
        L6c:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.view.animation.Animation r2 = android.view.animation.AnimationUtils.loadAnimation(r0, r3)
            if (r2 == 0) goto L7e
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = new android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator
            r0.<init>(r2)
            goto L16
        L7e:
            if (r11 != 0) goto L82
            r0 = r1
            goto L16
        L82:
            Int r0 = transitToStyleIndex(r11, r12)
            if (r0 >= 0) goto L8a
            r0 = r1
            goto L16
        L8a:
            switch(r0) {
                case 1: goto La2
                case 2: goto Lb0
                case 3: goto Lbc
                case 4: goto Lc8
                case 5: goto Ld7
                case 6: goto Le3
                default: goto L8d
            }
        L8d:
            if (r13 != 0) goto L9d
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            Boolean r0 = r0.onHasWindowAnimations()
            if (r0 == 0) goto L9d
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            Int r13 = r0.onGetWindowAnimations()
        L9d:
            if (r13 != 0) goto Lef
            r0 = r1
            goto L16
        La2:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            r1 = 1066401792(0x3f900000, Float:1.125)
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeOpenCloseAnimation(r0, r1, r6, r7, r6)
            goto L16
        Lb0:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeOpenCloseAnimation(r0, r6, r8, r6, r7)
            goto L16
        Lbc:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeOpenCloseAnimation(r0, r8, r6, r7, r6)
            goto L16
        Lc8:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            r1 = 1065982362(0x3f89999a, Float:1.075)
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeOpenCloseAnimation(r0, r6, r1, r6, r7)
            goto L16
        Ld7:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeFadeAnimation(r0, r7, r6)
            goto L16
        Le3:
            android.support.v4.app.FragmentHostCallback r0 = r9.mHost
            android.content.Context r0 = r0.getContext()
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = makeFadeAnimation(r0, r6, r7)
            goto L16
        Lef:
            r0 = r1
            goto L16
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.loadAnimation(android.support.v4.app.Fragment, Int, Boolean, Int):android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator")
    }

    final Unit makeActive(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            return
        }
        Int i = this.mNextFragmentIndex
        this.mNextFragmentIndex = i + 1
        fragment.setIndex(i, this.mParent)
        if (this.mActive == null) {
            this.mActive = SparseArray()
        }
        this.mActive.put(fragment.mIndex, fragment)
        if (DEBUG) {
            Log.v(TAG, "Allocated fragment index " + fragment)
        }
    }

    final Unit makeInactive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            return
        }
        if (DEBUG) {
            Log.v(TAG, "Freeing fragment index " + fragment)
        }
        this.mActive.put(fragment.mIndex, null)
        fragment.initState()
    }

    final Unit moveFragmentToExpectedState(Fragment fragment) {
        if (fragment == null) {
            return
        }
        Int iMin = this.mCurState
        if (fragment.mRemoving) {
            iMin = fragment.isInBackStack() ? Math.min(iMin, 1) : Math.min(iMin, 0)
        }
        moveToState(fragment, iMin, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false)
        if (fragment.mView != null) {
            Fragment fragmentFindFragmentUnder = findFragmentUnder(fragment)
            if (fragmentFindFragmentUnder != null) {
                View view = fragmentFindFragmentUnder.mView
                ViewGroup viewGroup = fragment.mContainer
                Int iIndexOfChild = viewGroup.indexOfChild(view)
                Int iIndexOfChild2 = viewGroup.indexOfChild(fragment.mView)
                if (iIndexOfChild2 < iIndexOfChild) {
                    viewGroup.removeViewAt(iIndexOfChild2)
                    viewGroup.addView(fragment.mView, iIndexOfChild)
                }
            }
            if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha)
                }
                fragment.mPostponedAlpha = 0.0f
                fragment.mIsNewlyAdded = false
                AnimationOrAnimator animationOrAnimatorLoadAnimation = loadAnimation(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle())
                if (animationOrAnimatorLoadAnimation != null) {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimatorLoadAnimation)
                    if (animationOrAnimatorLoadAnimation.animation != null) {
                        fragment.mView.startAnimation(animationOrAnimatorLoadAnimation.animation)
                    } else {
                        animationOrAnimatorLoadAnimation.animator.setTarget(fragment.mView)
                        animationOrAnimatorLoadAnimation.animator.start()
                    }
                }
            }
        }
        if (fragment.mHiddenChanged) {
            completeShowHideFragment(fragment)
        }
    }

    final Unit moveToState(Int i, Boolean z) {
        if (this.mHost == null && i != 0) {
            throw IllegalStateException("No activity")
        }
        if (z || i != this.mCurState) {
            this.mCurState = i
            if (this.mActive != null) {
                Int size = this.mAdded.size()
                for (Int i2 = 0; i2 < size; i2++) {
                    moveFragmentToExpectedState((Fragment) this.mAdded.get(i2))
                }
                Int size2 = this.mActive.size()
                for (Int i3 = 0; i3 < size2; i3++) {
                    Fragment fragment = (Fragment) this.mActive.valueAt(i3)
                    if (fragment != null && ((fragment.mRemoving || fragment.mDetached) && !fragment.mIsNewlyAdded)) {
                        moveFragmentToExpectedState(fragment)
                    }
                }
                startPendingDeferredFragments()
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 4) {
                    this.mHost.onSupportInvalidateOptionsMenu()
                    this.mNeedMenuInvalidate = false
                }
            }
        }
    }

    final Unit moveToState(Fragment fragment) throws NoSuchFieldException, Resources.NotFoundException {
        moveToState(fragment, this.mCurState, 0, 0, false)
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    final Unit moveToState(android.support.v4.app.Fragment r11, Int r12, Int r13, Int r14, Boolean r15) throws java.lang.NoSuchFieldException, android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 1134
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, Int, Int, Int, Boolean):Unit")
    }

    public final Unit noteStateNotSaved() {
        this.mSavedNonConfig = null
        this.mStateSaved = false
        this.mStopped = false
        Int size = this.mAdded.size()
        for (Int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) this.mAdded.get(i)
            if (fragment != null) {
                fragment.noteStateNotSaved()
            }
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) throws NoSuchFieldException, Resources.NotFoundException {
        Fragment fragment
        if (!"fragment".equals(str)) {
            return null
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class")
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment)
        String string = attributeValue == null ? typedArrayObtainStyledAttributes.getString(0) : attributeValue
        Int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1)
        String string2 = typedArrayObtainStyledAttributes.getString(2)
        typedArrayObtainStyledAttributes.recycle()
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), string)) {
            return null
        }
        Int id = view != null ? view.getId() : 0
        if (id == -1 && resourceId == -1 && string2 == null) {
            throw IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + string)
        }
        Fragment fragmentFindFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null
        if (fragmentFindFragmentById == null && string2 != null) {
            fragmentFindFragmentById = findFragmentByTag(string2)
        }
        if (fragmentFindFragmentById == null && id != -1) {
            fragmentFindFragmentById = findFragmentById(id)
        }
        if (DEBUG) {
            Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + string + " existing=" + fragmentFindFragmentById)
        }
        if (fragmentFindFragmentById == null) {
            Fragment fragmentInstantiate = this.mContainer.instantiate(context, string, null)
            fragmentInstantiate.mFromLayout = true
            fragmentInstantiate.mFragmentId = resourceId != 0 ? resourceId : id
            fragmentInstantiate.mContainerId = id
            fragmentInstantiate.mTag = string2
            fragmentInstantiate.mInLayout = true
            fragmentInstantiate.mFragmentManager = this
            fragmentInstantiate.mHost = this.mHost
            fragmentInstantiate.onInflate(this.mHost.getContext(), attributeSet, fragmentInstantiate.mSavedFragmentState)
            addFragment(fragmentInstantiate, true)
            fragment = fragmentInstantiate
        } else {
            if (fragmentFindFragmentById.mInLayout) {
                throw IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + string)
            }
            fragmentFindFragmentById.mInLayout = true
            fragmentFindFragmentById.mHost = this.mHost
            if (!fragmentFindFragmentById.mRetaining) {
                fragmentFindFragmentById.onInflate(this.mHost.getContext(), attributeSet, fragmentFindFragmentById.mSavedFragmentState)
            }
            fragment = fragmentFindFragmentById
        }
        if (this.mCurState > 0 || !fragment.mFromLayout) {
            moveToState(fragment)
        } else {
            moveToState(fragment, 1, 0, 0, false)
        }
        if (fragment.mView == null) {
            throw IllegalStateException("Fragment " + string + " did not create a view.")
        }
        if (resourceId != 0) {
            fragment.mView.setId(resourceId)
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(string2)
        }
        return fragment.mView
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet)
    }

    public final Unit performPendingDeferredStart(Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true
            } else {
                fragment.mDeferStart = false
                moveToState(fragment, this.mCurState, 0, 0, false)
            }
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit popBackStack() {
        enqueueAction(PopBackStackState(null, -1, 0), false)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit popBackStack(Int i, Int i2) {
        if (i < 0) {
            throw IllegalArgumentException("Bad id: " + i)
        }
        enqueueAction(PopBackStackState(null, i, i2), false)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit popBackStack(@Nullable String str, Int i) {
        enqueueAction(PopBackStackState(str, -1, i), false)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean popBackStackImmediate() {
        checkStateLoss()
        return popBackStackImmediate(null, -1, 0)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean popBackStackImmediate(Int i, Int i2) {
        checkStateLoss()
        execPendingActions()
        if (i < 0) {
            throw IllegalArgumentException("Bad id: " + i)
        }
        return popBackStackImmediate(null, i, i2)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Boolean popBackStackImmediate(@Nullable String str, Int i) {
        checkStateLoss()
        return popBackStackImmediate(str, -1, i)
    }

    final Boolean popBackStackState(ArrayList arrayList, ArrayList arrayList2, String str, Int i, Int i2) {
        if (this.mBackStack == null) {
            return false
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            Int size = this.mBackStack.size() - 1
            if (size < 0) {
                return false
            }
            arrayList.add(this.mBackStack.remove(size))
            arrayList2.add(true)
        } else {
            Int i3 = -1
            if (str != null || i >= 0) {
                Int size2 = this.mBackStack.size() - 1
                while (size2 >= 0) {
                    BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(size2)
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break
                    }
                    size2--
                }
                if (size2 < 0) {
                    return false
                }
                if ((i2 & 1) != 0) {
                    size2--
                    while (size2 >= 0) {
                        BackStackRecord backStackRecord2 = (BackStackRecord) this.mBackStack.get(size2)
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                            break
                        }
                        size2--
                    }
                }
                i3 = size2
            }
            if (i3 == this.mBackStack.size() - 1) {
                return false
            }
            for (Int size3 = this.mBackStack.size() - 1; size3 > i3; size3--) {
                arrayList.add(this.mBackStack.remove(size3))
                arrayList2.add(true)
            }
        }
        return true
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit putFragment(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"))
        }
        bundle.putInt(str, fragment.mIndex)
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, Boolean z) {
        this.mLifecycleCallbacks.add(FragmentLifecycleCallbacksHolder(fragmentLifecycleCallbacks, z))
    }

    public final Unit removeFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting)
        }
        Boolean z = !fragment.isInBackStack()
        if (!fragment.mDetached || z) {
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment)
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true
            }
            fragment.mAdded = false
            fragment.mRemoving = true
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener)
        }
    }

    final Unit reportBackStackChanged() {
        if (this.mBackStackChangeListeners == null) {
            return
        }
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mBackStackChangeListeners.size()) {
                return
            }
            ((FragmentManager.OnBackStackChangedListener) this.mBackStackChangeListeners.get(i2)).onBackStackChanged()
            i = i2 + 1
        }
    }

    final Unit restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        List list
        List list2
        if (parcelable == null) {
            return
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable
        if (fragmentManagerState.mActive != null) {
            if (fragmentManagerNonConfig != null) {
                List fragments = fragmentManagerNonConfig.getFragments()
                List childNonConfigs = fragmentManagerNonConfig.getChildNonConfigs()
                List viewModelStores = fragmentManagerNonConfig.getViewModelStores()
                Int size = fragments != null ? fragments.size() : 0
                for (Int i = 0; i < size; i++) {
                    Fragment fragment = (Fragment) fragments.get(i)
                    if (DEBUG) {
                        Log.v(TAG, "restoreAllState: re-attaching retained " + fragment)
                    }
                    Int i2 = 0
                    while (i2 < fragmentManagerState.mActive.length && fragmentManagerState.mActive[i2].mIndex != fragment.mIndex) {
                        i2++
                    }
                    if (i2 == fragmentManagerState.mActive.length) {
                        throwException(IllegalStateException("Could not find active fragment with index " + fragment.mIndex))
                    }
                    FragmentState fragmentState = fragmentManagerState.mActive[i2]
                    fragmentState.mInstance = fragment
                    fragment.mSavedViewState = null
                    fragment.mBackStackNesting = 0
                    fragment.mInLayout = false
                    fragment.mAdded = false
                    fragment.mTarget = null
                    if (fragmentState.mSavedFragmentState != null) {
                        fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader())
                        fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG)
                        fragment.mSavedFragmentState = fragmentState.mSavedFragmentState
                    }
                }
                list = viewModelStores
                list2 = childNonConfigs
            } else {
                list = null
                list2 = null
            }
            this.mActive = SparseArray(fragmentManagerState.mActive.length)
            Int i3 = 0
            while (i3 < fragmentManagerState.mActive.length) {
                FragmentState fragmentState2 = fragmentManagerState.mActive[i3]
                if (fragmentState2 != null) {
                    Fragment fragmentInstantiate = fragmentState2.instantiate(this.mHost, this.mContainer, this.mParent, (list2 == null || i3 >= list2.size()) ? null : (FragmentManagerNonConfig) list2.get(i3), (list == null || i3 >= list.size()) ? null : (ViewModelStore) list.get(i3))
                    if (DEBUG) {
                        Log.v(TAG, "restoreAllState: active #" + i3 + ": " + fragmentInstantiate)
                    }
                    this.mActive.put(fragmentInstantiate.mIndex, fragmentInstantiate)
                    fragmentState2.mInstance = null
                }
                i3++
            }
            if (fragmentManagerNonConfig != null) {
                List fragments2 = fragmentManagerNonConfig.getFragments()
                Int size2 = fragments2 != null ? fragments2.size() : 0
                for (Int i4 = 0; i4 < size2; i4++) {
                    Fragment fragment2 = (Fragment) fragments2.get(i4)
                    if (fragment2.mTargetIndex >= 0) {
                        fragment2.mTarget = (Fragment) this.mActive.get(fragment2.mTargetIndex)
                        if (fragment2.mTarget == null) {
                            Log.w(TAG, "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.mTargetIndex)
                        }
                    }
                }
            }
            this.mAdded.clear()
            if (fragmentManagerState.mAdded != null) {
                for (Int i5 = 0; i5 < fragmentManagerState.mAdded.length; i5++) {
                    Fragment fragment3 = (Fragment) this.mActive.get(fragmentManagerState.mAdded[i5])
                    if (fragment3 == null) {
                        throwException(IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[i5]))
                    }
                    fragment3.mAdded = true
                    if (DEBUG) {
                        Log.v(TAG, "restoreAllState: added #" + i5 + ": " + fragment3)
                    }
                    if (this.mAdded.contains(fragment3)) {
                        throw IllegalStateException("Already added!")
                    }
                    synchronized (this.mAdded) {
                        this.mAdded.add(fragment3)
                    }
                }
            }
            if (fragmentManagerState.mBackStack != null) {
                this.mBackStack = ArrayList(fragmentManagerState.mBackStack.length)
                for (Int i6 = 0; i6 < fragmentManagerState.mBackStack.length; i6++) {
                    BackStackRecord backStackRecordInstantiate = fragmentManagerState.mBackStack[i6].instantiate(this)
                    if (DEBUG) {
                        Log.v(TAG, "restoreAllState: back stack #" + i6 + " (index " + backStackRecordInstantiate.mIndex + "): " + backStackRecordInstantiate)
                        PrintWriter printWriter = PrintWriter(LogWriter(TAG))
                        backStackRecordInstantiate.dump("  ", printWriter, false)
                        printWriter.close()
                    }
                    this.mBackStack.add(backStackRecordInstantiate)
                    if (backStackRecordInstantiate.mIndex >= 0) {
                        setBackStackIndex(backStackRecordInstantiate.mIndex, backStackRecordInstantiate)
                    }
                }
            } else {
                this.mBackStack = null
            }
            if (fragmentManagerState.mPrimaryNavActiveIndex >= 0) {
                this.mPrimaryNav = (Fragment) this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex)
            }
            this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex
        }
    }

    final FragmentManagerNonConfig retainNonConfig() {
        setRetaining(this.mSavedNonConfig)
        return this.mSavedNonConfig
    }

    final Parcelable saveAllState() throws NoSuchFieldException, Resources.NotFoundException {
        Array<Int> iArr
        Int size
        Boolean z
        Array<BackStackState> backStackStateArr = null
        forcePostponedTransactions()
        endAnimatingAwayFragments()
        execPendingActions()
        this.mStateSaved = true
        this.mSavedNonConfig = null
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null
        }
        Int size2 = this.mActive.size()
        Array<FragmentState> fragmentStateArr = new FragmentState[size2]
        Int i = 0
        Boolean z2 = false
        while (i < size2) {
            Fragment fragment = (Fragment) this.mActive.valueAt(i)
            if (fragment != null) {
                if (fragment.mIndex < 0) {
                    throwException(IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.mIndex))
                }
                FragmentState fragmentState = FragmentState(fragment)
                fragmentStateArr[i] = fragmentState
                if (fragment.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState = fragment.mSavedFragmentState
                } else {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState(fragment)
                    if (fragment.mTarget != null) {
                        if (fragment.mTarget.mIndex < 0) {
                            throwException(IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.mTarget))
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = Bundle()
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, fragment.mTarget)
                        if (fragment.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, fragment.mTargetRequestCode)
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + fragment + ": " + fragmentState.mSavedFragmentState)
                }
                z = true
            } else {
                z = z2
            }
            i++
            z2 = z
        }
        if (!z2) {
            if (!DEBUG) {
                return null
            }
            Log.v(TAG, "saveAllState: no fragments!")
            return null
        }
        Int size3 = this.mAdded.size()
        if (size3 > 0) {
            iArr = new Int[size3]
            for (Int i2 = 0; i2 < size3; i2++) {
                iArr[i2] = ((Fragment) this.mAdded.get(i2)).mIndex
                if (iArr[i2] < 0) {
                    throwException(IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + iArr[i2]))
                }
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2))
                }
            }
        } else {
            iArr = null
        }
        if (this.mBackStack != null && (size = this.mBackStack.size()) > 0) {
            backStackStateArr = new BackStackState[size]
            for (Int i3 = 0; i3 < size; i3++) {
                backStackStateArr[i3] = BackStackState((BackStackRecord) this.mBackStack.get(i3))
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3))
                }
            }
        }
        FragmentManagerState fragmentManagerState = FragmentManagerState()
        fragmentManagerState.mActive = fragmentStateArr
        fragmentManagerState.mAdded = iArr
        fragmentManagerState.mBackStack = backStackStateArr
        if (this.mPrimaryNav != null) {
            fragmentManagerState.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex
        }
        fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex
        saveNonConfig()
        return fragmentManagerState
    }

    final Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle
        if (this.mStateBundle == null) {
            this.mStateBundle = Bundle()
        }
        fragment.performSaveInstanceState(this.mStateBundle)
        dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false)
        if (this.mStateBundle.isEmpty()) {
            bundle = null
        } else {
            bundle = this.mStateBundle
            this.mStateBundle = null
        }
        if (fragment.mView != null) {
            saveFragmentViewState(fragment)
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState)
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint)
        }
        return bundle
    }

    @Override // android.support.v4.app.FragmentManager
    @Nullable
    public final Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Bundle bundleSaveFragmentBasicState
        if (fragment.mIndex < 0) {
            throwException(IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"))
        }
        if (fragment.mState <= 0 || (bundleSaveFragmentBasicState = saveFragmentBasicState(fragment)) == null) {
            return null
        }
        return new Fragment.SavedState(bundleSaveFragmentBasicState)
    }

    final Unit saveFragmentViewState(Fragment fragment) {
        if (fragment.mInnerView == null) {
            return
        }
        if (this.mStateArray == null) {
            this.mStateArray = SparseArray()
        } else {
            this.mStateArray.clear()
        }
        fragment.mInnerView.saveHierarchyState(this.mStateArray)
        if (this.mStateArray.size() > 0) {
            fragment.mSavedViewState = this.mStateArray
            this.mStateArray = null
        }
    }

    final Unit saveNonConfig() {
        ArrayList arrayList
        ArrayList arrayList2
        ArrayList arrayList3
        FragmentManagerNonConfig fragmentManagerNonConfig
        if (this.mActive != null) {
            Int i = 0
            arrayList = null
            arrayList2 = null
            arrayList3 = null
            while (i < this.mActive.size()) {
                Fragment fragment = (Fragment) this.mActive.valueAt(i)
                if (fragment != null) {
                    if (fragment.mRetainInstance) {
                        if (arrayList3 == null) {
                            arrayList3 = ArrayList()
                        }
                        arrayList3.add(fragment)
                        fragment.mTargetIndex = fragment.mTarget != null ? fragment.mTarget.mIndex : -1
                        if (DEBUG) {
                            Log.v(TAG, "retainNonConfig: keeping retained " + fragment)
                        }
                    }
                    if (fragment.mChildFragmentManager != null) {
                        fragment.mChildFragmentManager.saveNonConfig()
                        fragmentManagerNonConfig = fragment.mChildFragmentManager.mSavedNonConfig
                    } else {
                        fragmentManagerNonConfig = fragment.mChildNonConfig
                    }
                    if (arrayList2 == null && fragmentManagerNonConfig != null) {
                        arrayList2 = ArrayList(this.mActive.size())
                        for (Int i2 = 0; i2 < i; i2++) {
                            arrayList2.add(null)
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(fragmentManagerNonConfig)
                    }
                    if (arrayList == null && fragment.mViewModelStore != null) {
                        arrayList = ArrayList(this.mActive.size())
                        for (Int i3 = 0; i3 < i; i3++) {
                            arrayList.add(null)
                        }
                    }
                    if (arrayList != null) {
                        arrayList.add(fragment.mViewModelStore)
                    }
                }
                i++
                arrayList3 = arrayList3
                arrayList2 = arrayList2
                arrayList = arrayList
            }
        } else {
            arrayList = null
            arrayList2 = null
            arrayList3 = null
        }
        if (arrayList3 == null && arrayList2 == null && arrayList == null) {
            this.mSavedNonConfig = null
        } else {
            this.mSavedNonConfig = FragmentManagerNonConfig(arrayList3, arrayList2, arrayList)
        }
    }

    final Unit scheduleCommit() {
        synchronized (this) {
            Boolean z = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true
            Boolean z2 = this.mPendingActions != null && this.mPendingActions.size() == 1
            if (z || z2) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit)
                this.mHost.getHandler().post(this.mExecCommit)
            }
        }
    }

    public final Unit setBackStackIndex(Int i, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = ArrayList()
            }
            Int size = this.mBackStackIndices.size()
            if (i < size) {
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + i + " to " + backStackRecord)
                }
                this.mBackStackIndices.set(i, backStackRecord)
            } else {
                while (size < i) {
                    this.mBackStackIndices.add(null)
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = ArrayList()
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Adding available back stack index " + size)
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(size))
                    size++
                }
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + i + " with " + backStackRecord)
                }
                this.mBackStackIndices.add(backStackRecord)
            }
        }
    }

    public final Unit setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null && (this.mActive.get(fragment.mIndex) != fragment || (fragment.mHost != null && fragment.getFragmentManager() != this))) {
            throw IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this)
        }
        this.mPrimaryNav = fragment
    }

    public final Unit showFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "show: " + fragment)
        }
        if (fragment.mHidden) {
            fragment.mHidden = false
            fragment.mHiddenChanged = fragment.mHiddenChanged ? false : true
        }
    }

    final Unit startPendingDeferredFragments() {
        if (this.mActive == null) {
            return
        }
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mActive.size()) {
                return
            }
            Fragment fragment = (Fragment) this.mActive.valueAt(i2)
            if (fragment != null) {
                performPendingDeferredStart(fragment)
            }
            i = i2 + 1
        }
    }

    public final String toString() {
        StringBuilder sb = StringBuilder(128)
        sb.append("FragmentManager{")
        sb.append(Integer.toHexString(System.identityHashCode(this)))
        sb.append(" in ")
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, sb)
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb)
        }
        sb.append("}}")
        return sb.toString()
    }

    @Override // android.support.v4.app.FragmentManager
    public final Unit unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.mLifecycleCallbacks) {
            Int size = this.mLifecycleCallbacks.size()
            Int i = 0
            while (true) {
                if (i >= size) {
                    break
                }
                if (((FragmentLifecycleCallbacksHolder) this.mLifecycleCallbacks.get(i)).mCallback == fragmentLifecycleCallbacks) {
                    this.mLifecycleCallbacks.remove(i)
                    break
                }
                i++
            }
        }
    }
}
