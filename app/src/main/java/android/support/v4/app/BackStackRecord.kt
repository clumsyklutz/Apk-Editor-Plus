package android.support.v4.app

import android.support.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.support.v4.app.FragmentManagerImpl
import android.support.v4.util.LogWriter
import androidx.core.view.ViewCompat
import android.util.Log
import android.view.View
import jadx.core.codegen.CodeWriter
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.Modifier
import java.util.ArrayList

final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManagerImpl.OpGenerator {
    static val OP_ADD = 1
    static val OP_ATTACH = 7
    static val OP_DETACH = 6
    static val OP_HIDE = 4
    static val OP_NULL = 0
    static val OP_REMOVE = 3
    static val OP_REPLACE = 2
    static val OP_SET_PRIMARY_NAV = 8
    static val OP_SHOW = 5
    static val OP_UNSET_PRIMARY_NAV = 9
    static val TAG = "FragmentManager"
    Boolean mAddToBackStack
    Int mBreadCrumbShortTitleRes
    CharSequence mBreadCrumbShortTitleText
    Int mBreadCrumbTitleRes
    CharSequence mBreadCrumbTitleText
    ArrayList mCommitRunnables
    Boolean mCommitted
    Int mEnterAnim
    Int mExitAnim
    final FragmentManagerImpl mManager

    @Nullable
    String mName
    Int mPopEnterAnim
    Int mPopExitAnim
    ArrayList mSharedElementSourceNames
    ArrayList mSharedElementTargetNames
    Int mTransition
    Int mTransitionStyle
    ArrayList mOps = ArrayList()
    Boolean mAllowAddToBackStack = true
    Int mIndex = -1
    Boolean mReorderingAllowed = false

    final class Op {
        Int cmd
        Int enterAnim
        Int exitAnim
        Fragment fragment
        Int popEnterAnim
        Int popExitAnim

        Op() {
        }

        Op(Int i, Fragment fragment) {
            this.cmd = i
            this.fragment = fragment
        }
    }

    constructor(FragmentManagerImpl fragmentManagerImpl) {
        this.mManager = fragmentManagerImpl
    }

    private fun doAddOp(Int i, Fragment fragment, @Nullable String str, Int i2) {
        Class<?> cls = fragment.getClass()
        Int modifiers = cls.getModifiers()
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.")
        }
        fragment.mFragmentManager = this.mManager
        if (str != null) {
            if (fragment.mTag != null && !str.equals(fragment.mTag)) {
                throw IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str)
            }
            fragment.mTag = str
        }
        if (i != 0) {
            if (i == -1) {
                throw IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id")
            }
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != i) {
                throw IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i)
            }
            fragment.mFragmentId = i
            fragment.mContainerId = i
        }
        addOp(Op(i2, fragment))
    }

    private fun isFragmentPostponed(Op op) {
        Fragment fragment = op.fragment
        return (fragment == null || !fragment.mAdded || fragment.mView == null || fragment.mDetached || fragment.mHidden || !fragment.isPostponed()) ? false : true
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction add(Int i, Fragment fragment) {
        doAddOp(i, fragment, null, 1)
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction add(Int i, Fragment fragment, @Nullable String str) {
        doAddOp(i, fragment, str, 1)
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction add(Fragment fragment, @Nullable String str) {
        doAddOp(0, fragment, str, 1)
        return this
    }

    final Unit addOp(Op op) {
        this.mOps.add(op)
        op.enterAnim = this.mEnterAnim
        op.exitAnim = this.mExitAnim
        op.popEnterAnim = this.mPopEnterAnim
        op.popExitAnim = this.mPopExitAnim
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction addSharedElement(View view, String str) {
        if (FragmentTransition.supportsTransition()) {
            String transitionName = ViewCompat.getTransitionName(view)
            if (transitionName == null) {
                throw IllegalArgumentException("Unique transitionNames are required for all sharedElements")
            }
            if (this.mSharedElementSourceNames == null) {
                this.mSharedElementSourceNames = ArrayList()
                this.mSharedElementTargetNames = ArrayList()
            } else {
                if (this.mSharedElementTargetNames.contains(str)) {
                    throw IllegalArgumentException("A shared element with the target name '" + str + "' has already been added to the transaction.")
                }
                if (this.mSharedElementSourceNames.contains(transitionName)) {
                    throw IllegalArgumentException("A shared element with the source name '" + transitionName + " has already been added to the transaction.")
                }
            }
            this.mSharedElementSourceNames.add(transitionName)
            this.mSharedElementTargetNames.add(str)
        }
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction addToBackStack(@Nullable String str) {
        if (!this.mAllowAddToBackStack) {
            throw IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.")
        }
        this.mAddToBackStack = true
        this.mName = str
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction attach(Fragment fragment) {
        addOp(Op(7, fragment))
        return this
    }

    final Unit bumpBackStackNesting(Int i) {
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                Log.v(TAG, "Bump nesting in " + this + " by " + i)
            }
            Int size = this.mOps.size()
            for (Int i2 = 0; i2 < size; i2++) {
                Op op = (Op) this.mOps.get(i2)
                if (op.fragment != null) {
                    op.fragment.mBackStackNesting += i
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v(TAG, "Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting)
                    }
                }
            }
        }
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Int commit() {
        return commitInternal(false)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Int commitAllowingStateLoss() {
        return commitInternal(true)
    }

    final Int commitInternal(Boolean z) {
        if (this.mCommitted) {
            throw IllegalStateException("commit already called")
        }
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "Commit: " + this)
            PrintWriter printWriter = PrintWriter(LogWriter(TAG))
            dump("  ", null, printWriter, null)
            printWriter.close()
        }
        this.mCommitted = true
        if (this.mAddToBackStack) {
            this.mIndex = this.mManager.allocBackStackIndex(this)
        } else {
            this.mIndex = -1
        }
        this.mManager.enqueueAction(this, z)
        return this.mIndex
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Unit commitNow() {
        disallowAddToBackStack()
        this.mManager.execSingleAction(this, false)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Unit commitNowAllowingStateLoss() {
        disallowAddToBackStack()
        this.mManager.execSingleAction(this, true)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction detach(Fragment fragment) {
        addOp(Op(6, fragment))
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction disallowAddToBackStack() {
        if (this.mAddToBackStack) {
            throw IllegalStateException("This transaction is already being added to the back stack")
        }
        this.mAllowAddToBackStack = false
        return this
    }

    public final Unit dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, Array<String> strArr) {
        dump(str, printWriter, true)
    }

    public final Unit dump(String str, PrintWriter printWriter, Boolean z) {
        String str2
        if (z) {
            printWriter.print(str)
            printWriter.print("mName=")
            printWriter.print(this.mName)
            printWriter.print(" mIndex=")
            printWriter.print(this.mIndex)
            printWriter.print(" mCommitted=")
            printWriter.println(this.mCommitted)
            if (this.mTransition != 0) {
                printWriter.print(str)
                printWriter.print("mTransition=#")
                printWriter.print(Integer.toHexString(this.mTransition))
                printWriter.print(" mTransitionStyle=#")
                printWriter.println(Integer.toHexString(this.mTransitionStyle))
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(str)
                printWriter.print("mEnterAnim=#")
                printWriter.print(Integer.toHexString(this.mEnterAnim))
                printWriter.print(" mExitAnim=#")
                printWriter.println(Integer.toHexString(this.mExitAnim))
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(str)
                printWriter.print("mPopEnterAnim=#")
                printWriter.print(Integer.toHexString(this.mPopEnterAnim))
                printWriter.print(" mPopExitAnim=#")
                printWriter.println(Integer.toHexString(this.mPopExitAnim))
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(str)
                printWriter.print("mBreadCrumbTitleRes=#")
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes))
                printWriter.print(" mBreadCrumbTitleText=")
                printWriter.println(this.mBreadCrumbTitleText)
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(str)
                printWriter.print("mBreadCrumbShortTitleRes=#")
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes))
                printWriter.print(" mBreadCrumbShortTitleText=")
                printWriter.println(this.mBreadCrumbShortTitleText)
            }
        }
        if (this.mOps.isEmpty()) {
            return
        }
        printWriter.print(str)
        printWriter.println("Operations:")
        StringBuilder().append(str).append(CodeWriter.INDENT)
        Int size = this.mOps.size()
        for (Int i = 0; i < size; i++) {
            Op op = (Op) this.mOps.get(i)
            switch (op.cmd) {
                case 0:
                    str2 = "NULL"
                    break
                case 1:
                    str2 = "ADD"
                    break
                case 2:
                    str2 = "REPLACE"
                    break
                case 3:
                    str2 = "REMOVE"
                    break
                case 4:
                    str2 = "HIDE"
                    break
                case 5:
                    str2 = "SHOW"
                    break
                case 6:
                    str2 = "DETACH"
                    break
                case 7:
                    str2 = "ATTACH"
                    break
                case 8:
                    str2 = "SET_PRIMARY_NAV"
                    break
                case 9:
                    str2 = "UNSET_PRIMARY_NAV"
                    break
                default:
                    str2 = "cmd=" + op.cmd
                    break
            }
            printWriter.print(str)
            printWriter.print("  Op #")
            printWriter.print(i)
            printWriter.print(": ")
            printWriter.print(str2)
            printWriter.print(" ")
            printWriter.println(op.fragment)
            if (z) {
                if (op.enterAnim != 0 || op.exitAnim != 0) {
                    printWriter.print(str)
                    printWriter.print("enterAnim=#")
                    printWriter.print(Integer.toHexString(op.enterAnim))
                    printWriter.print(" exitAnim=#")
                    printWriter.println(Integer.toHexString(op.exitAnim))
                }
                if (op.popEnterAnim != 0 || op.popExitAnim != 0) {
                    printWriter.print(str)
                    printWriter.print("popEnterAnim=#")
                    printWriter.print(Integer.toHexString(op.popEnterAnim))
                    printWriter.print(" popExitAnim=#")
                    printWriter.println(Integer.toHexString(op.popExitAnim))
                }
            }
        }
    }

    final Unit executeOps() {
        Int size = this.mOps.size()
        for (Int i = 0; i < size; i++) {
            Op op = (Op) this.mOps.get(i)
            Fragment fragment = op.fragment
            if (fragment != null) {
                fragment.setNextTransition(this.mTransition, this.mTransitionStyle)
            }
            switch (op.cmd) {
                case 1:
                    fragment.setNextAnim(op.enterAnim)
                    this.mManager.addFragment(fragment, false)
                    break
                case 2:
                default:
                    throw IllegalArgumentException("Unknown cmd: " + op.cmd)
                case 3:
                    fragment.setNextAnim(op.exitAnim)
                    this.mManager.removeFragment(fragment)
                    break
                case 4:
                    fragment.setNextAnim(op.exitAnim)
                    this.mManager.hideFragment(fragment)
                    break
                case 5:
                    fragment.setNextAnim(op.enterAnim)
                    this.mManager.showFragment(fragment)
                    break
                case 6:
                    fragment.setNextAnim(op.exitAnim)
                    this.mManager.detachFragment(fragment)
                    break
                case 7:
                    fragment.setNextAnim(op.enterAnim)
                    this.mManager.attachFragment(fragment)
                    break
                case 8:
                    this.mManager.setPrimaryNavigationFragment(fragment)
                    break
                case 9:
                    this.mManager.setPrimaryNavigationFragment(null)
                    break
            }
            if (!this.mReorderingAllowed && op.cmd != 1 && fragment != null) {
                this.mManager.moveFragmentToExpectedState(fragment)
            }
        }
        if (this.mReorderingAllowed) {
            return
        }
        this.mManager.moveToState(this.mManager.mCurState, true)
    }

    final Unit executePopOps(Boolean z) {
        for (Int size = this.mOps.size() - 1; size >= 0; size--) {
            Op op = (Op) this.mOps.get(size)
            Fragment fragment = op.fragment
            if (fragment != null) {
                fragment.setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle)
            }
            switch (op.cmd) {
                case 1:
                    fragment.setNextAnim(op.popExitAnim)
                    this.mManager.removeFragment(fragment)
                    break
                case 2:
                default:
                    throw IllegalArgumentException("Unknown cmd: " + op.cmd)
                case 3:
                    fragment.setNextAnim(op.popEnterAnim)
                    this.mManager.addFragment(fragment, false)
                    break
                case 4:
                    fragment.setNextAnim(op.popEnterAnim)
                    this.mManager.showFragment(fragment)
                    break
                case 5:
                    fragment.setNextAnim(op.popExitAnim)
                    this.mManager.hideFragment(fragment)
                    break
                case 6:
                    fragment.setNextAnim(op.popEnterAnim)
                    this.mManager.attachFragment(fragment)
                    break
                case 7:
                    fragment.setNextAnim(op.popExitAnim)
                    this.mManager.detachFragment(fragment)
                    break
                case 8:
                    this.mManager.setPrimaryNavigationFragment(null)
                    break
                case 9:
                    this.mManager.setPrimaryNavigationFragment(fragment)
                    break
            }
            if (!this.mReorderingAllowed && op.cmd != 3 && fragment != null) {
                this.mManager.moveFragmentToExpectedState(fragment)
            }
        }
        if (this.mReorderingAllowed || !z) {
            return
        }
        this.mManager.moveToState(this.mManager.mCurState, true)
    }

    final Fragment expandOps(ArrayList arrayList, Fragment fragment) {
        Boolean z
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mOps.size()) {
                return fragment
            }
            Op op = (Op) this.mOps.get(i2)
            switch (op.cmd) {
                case 1:
                case 7:
                    arrayList.add(op.fragment)
                    break
                case 2:
                    Fragment fragment2 = op.fragment
                    Int i3 = fragment2.mContainerId
                    Boolean z2 = false
                    Int size = arrayList.size() - 1
                    Fragment fragment3 = fragment
                    Int i4 = i2
                    while (size >= 0) {
                        Fragment fragment4 = (Fragment) arrayList.get(size)
                        if (fragment4.mContainerId != i3) {
                            z = z2
                        } else if (fragment4 == fragment2) {
                            z = true
                        } else {
                            if (fragment4 == fragment3) {
                                this.mOps.add(i4, Op(9, fragment4))
                                i4++
                                fragment3 = null
                            }
                            Op op2 = Op(3, fragment4)
                            op2.enterAnim = op.enterAnim
                            op2.popEnterAnim = op.popEnterAnim
                            op2.exitAnim = op.exitAnim
                            op2.popExitAnim = op.popExitAnim
                            this.mOps.add(i4, op2)
                            arrayList.remove(fragment4)
                            i4++
                            z = z2
                        }
                        size--
                        z2 = z
                    }
                    if (!z2) {
                        op.cmd = 1
                        arrayList.add(fragment2)
                        i2 = i4
                        fragment = fragment3
                        break
                    } else {
                        this.mOps.remove(i4)
                        i2 = i4 - 1
                        fragment = fragment3
                        break
                    }
                case 3:
                case 6:
                    arrayList.remove(op.fragment)
                    if (op.fragment != fragment) {
                        break
                    } else {
                        this.mOps.add(i2, Op(9, op.fragment))
                        i2++
                        fragment = null
                        break
                    }
                case 8:
                    this.mOps.add(i2, Op(9, fragment))
                    i2++
                    fragment = op.fragment
                    break
            }
            i = i2 + 1
        }
    }

    @Override // android.support.v4.app.FragmentManagerImpl.OpGenerator
    public final Boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "Run: " + this)
        }
        arrayList.add(this)
        arrayList2.add(false)
        if (!this.mAddToBackStack) {
            return true
        }
        this.mManager.addBackStackState(this)
        return true
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    @Nullable
    public final CharSequence getBreadCrumbShortTitle() {
        return this.mBreadCrumbShortTitleRes != 0 ? this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes) : this.mBreadCrumbShortTitleText
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    public final Int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    @Nullable
    public final CharSequence getBreadCrumbTitle() {
        return this.mBreadCrumbTitleRes != 0 ? this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes) : this.mBreadCrumbTitleText
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    public final Int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    public final Int getId() {
        return this.mIndex
    }

    @Override // android.support.v4.app.FragmentManager.BackStackEntry
    @Nullable
    public final String getName() {
        return this.mName
    }

    public final Int getTransition() {
        return this.mTransition
    }

    public final Int getTransitionStyle() {
        return this.mTransitionStyle
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction hide(Fragment fragment) {
        addOp(Op(4, fragment))
        return this
    }

    final Boolean interactsWith(Int i) {
        Int size = this.mOps.size()
        for (Int i2 = 0; i2 < size; i2++) {
            Op op = (Op) this.mOps.get(i2)
            Int i3 = op.fragment != null ? op.fragment.mContainerId : 0
            if (i3 != 0 && i3 == i) {
                return true
            }
        }
        return false
    }

    final Boolean interactsWith(ArrayList arrayList, Int i, Int i2) {
        Int i3
        if (i2 == i) {
            return false
        }
        Int size = this.mOps.size()
        Int i4 = -1
        Int i5 = 0
        while (i5 < size) {
            Op op = (Op) this.mOps.get(i5)
            Int i6 = op.fragment != null ? op.fragment.mContainerId : 0
            if (i6 == 0 || i6 == i4) {
                i3 = i4
            } else {
                for (Int i7 = i; i7 < i2; i7++) {
                    BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i7)
                    Int size2 = backStackRecord.mOps.size()
                    for (Int i8 = 0; i8 < size2; i8++) {
                        Op op2 = (Op) backStackRecord.mOps.get(i8)
                        if ((op2.fragment != null ? op2.fragment.mContainerId : 0) == i6) {
                            return true
                        }
                    }
                }
                i3 = i6
            }
            i5++
            i4 = i3
        }
        return false
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final Boolean isEmpty() {
        return this.mOps.isEmpty()
    }

    final Boolean isPostponed() {
        for (Int i = 0; i < this.mOps.size(); i++) {
            if (isFragmentPostponed((Op) this.mOps.get(i))) {
                return true
            }
        }
        return false
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction remove(Fragment fragment) {
        addOp(Op(3, fragment))
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction replace(Int i, Fragment fragment) {
        return replace(i, fragment, null)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction replace(Int i, Fragment fragment, @Nullable String str) {
        if (i == 0) {
            throw IllegalArgumentException("Must use non-zero containerViewId")
        }
        doAddOp(i, fragment, str, 2)
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction runOnCommit(Runnable runnable) {
        if (runnable == null) {
            throw IllegalArgumentException("runnable cannot be null")
        }
        disallowAddToBackStack()
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = ArrayList()
        }
        this.mCommitRunnables.add(runnable)
        return this
    }

    public final Unit runOnCommitRunnables() {
        if (this.mCommitRunnables != null) {
            Int size = this.mCommitRunnables.size()
            for (Int i = 0; i < size; i++) {
                ((Runnable) this.mCommitRunnables.get(i)).run()
            }
            this.mCommitRunnables = null
        }
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setAllowOptimization(Boolean z) {
        return setReorderingAllowed(z)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setBreadCrumbShortTitle(Int i) {
        this.mBreadCrumbShortTitleRes = i
        this.mBreadCrumbShortTitleText = null
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0
        this.mBreadCrumbShortTitleText = charSequence
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setBreadCrumbTitle(Int i) {
        this.mBreadCrumbTitleRes = i
        this.mBreadCrumbTitleText = null
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0
        this.mBreadCrumbTitleText = charSequence
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setCustomAnimations(Int i, Int i2) {
        return setCustomAnimations(i, i2, 0, 0)
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setCustomAnimations(Int i, Int i2, Int i3, Int i4) {
        this.mEnterAnim = i
        this.mExitAnim = i2
        this.mPopEnterAnim = i3
        this.mPopExitAnim = i4
        return this
    }

    final Unit setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mOps.size()) {
                return
            }
            Op op = (Op) this.mOps.get(i2)
            if (isFragmentPostponed(op)) {
                op.fragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener)
            }
            i = i2 + 1
        }
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        addOp(Op(8, fragment))
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setReorderingAllowed(Boolean z) {
        this.mReorderingAllowed = z
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setTransition(Int i) {
        this.mTransition = i
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction setTransitionStyle(Int i) {
        this.mTransitionStyle = i
        return this
    }

    @Override // android.support.v4.app.FragmentTransaction
    public final FragmentTransaction show(Fragment fragment) {
        addOp(Op(5, fragment))
        return this
    }

    public final String toString() {
        StringBuilder sb = StringBuilder(128)
        sb.append("BackStackEntry{")
        sb.append(Integer.toHexString(System.identityHashCode(this)))
        if (this.mIndex >= 0) {
            sb.append(" #")
            sb.append(this.mIndex)
        }
        if (this.mName != null) {
            sb.append(" ")
            sb.append(this.mName)
        }
        sb.append("}")
        return sb.toString()
    }

    final Fragment trackAddedFragmentsInPop(ArrayList arrayList, Fragment fragment) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mOps.size()) {
                return fragment
            }
            Op op = (Op) this.mOps.get(i2)
            switch (op.cmd) {
                case 1:
                case 7:
                    arrayList.remove(op.fragment)
                    break
                case 3:
                case 6:
                    arrayList.add(op.fragment)
                    break
                case 8:
                    fragment = null
                    break
                case 9:
                    fragment = op.fragment
                    break
            }
            i = i2 + 1
        }
    }
}
