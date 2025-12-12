package android.support.v4.app

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.BackStackRecord
import android.text.TextUtils
import android.util.Log
import java.util.ArrayList

final class BackStackState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.app.BackStackState.1
        @Override // android.os.Parcelable.Creator
        public final BackStackState createFromParcel(Parcel parcel) {
            return BackStackState(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<BackStackState> newArray(Int i) {
            return new BackStackState[i]
        }
    }
    final Int mBreadCrumbShortTitleRes
    final CharSequence mBreadCrumbShortTitleText
    final Int mBreadCrumbTitleRes
    final CharSequence mBreadCrumbTitleText
    final Int mIndex
    final String mName
    final Array<Int> mOps
    final Boolean mReorderingAllowed
    final ArrayList mSharedElementSourceNames
    final ArrayList mSharedElementTargetNames
    final Int mTransition
    final Int mTransitionStyle

    constructor(Parcel parcel) {
        this.mOps = parcel.createIntArray()
        this.mTransition = parcel.readInt()
        this.mTransitionStyle = parcel.readInt()
        this.mName = parcel.readString()
        this.mIndex = parcel.readInt()
        this.mBreadCrumbTitleRes = parcel.readInt()
        this.mBreadCrumbTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mBreadCrumbShortTitleRes = parcel.readInt()
        this.mBreadCrumbShortTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
        this.mSharedElementSourceNames = parcel.createStringArrayList()
        this.mSharedElementTargetNames = parcel.createStringArrayList()
        this.mReorderingAllowed = parcel.readInt() != 0
    }

    constructor(BackStackRecord backStackRecord) {
        Int size = backStackRecord.mOps.size()
        this.mOps = new Int[size * 6]
        if (!backStackRecord.mAddToBackStack) {
            throw IllegalStateException("Not on back stack")
        }
        Int i = 0
        for (Int i2 = 0; i2 < size; i2++) {
            BackStackRecord.Op op = (BackStackRecord.Op) backStackRecord.mOps.get(i2)
            Int i3 = i + 1
            this.mOps[i] = op.cmd
            Int i4 = i3 + 1
            this.mOps[i3] = op.fragment != null ? op.fragment.mIndex : -1
            Int i5 = i4 + 1
            this.mOps[i4] = op.enterAnim
            Int i6 = i5 + 1
            this.mOps[i5] = op.exitAnim
            Int i7 = i6 + 1
            this.mOps[i6] = op.popEnterAnim
            i = i7 + 1
            this.mOps[i7] = op.popExitAnim
        }
        this.mTransition = backStackRecord.mTransition
        this.mTransitionStyle = backStackRecord.mTransitionStyle
        this.mName = backStackRecord.mName
        this.mIndex = backStackRecord.mIndex
        this.mBreadCrumbTitleRes = backStackRecord.mBreadCrumbTitleRes
        this.mBreadCrumbTitleText = backStackRecord.mBreadCrumbTitleText
        this.mBreadCrumbShortTitleRes = backStackRecord.mBreadCrumbShortTitleRes
        this.mBreadCrumbShortTitleText = backStackRecord.mBreadCrumbShortTitleText
        this.mSharedElementSourceNames = backStackRecord.mSharedElementSourceNames
        this.mSharedElementTargetNames = backStackRecord.mSharedElementTargetNames
        this.mReorderingAllowed = backStackRecord.mReorderingAllowed
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    public final BackStackRecord instantiate(FragmentManagerImpl fragmentManagerImpl) {
        Int i = 0
        BackStackRecord backStackRecord = BackStackRecord(fragmentManagerImpl)
        Int i2 = 0
        while (i < this.mOps.length) {
            BackStackRecord.Op op = new BackStackRecord.Op()
            Int i3 = i + 1
            op.cmd = this.mOps[i]
            if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Instantiate " + backStackRecord + " op #" + i2 + " base fragment #" + this.mOps[i3])
            }
            Int i4 = i3 + 1
            Int i5 = this.mOps[i3]
            if (i5 >= 0) {
                op.fragment = (Fragment) fragmentManagerImpl.mActive.get(i5)
            } else {
                op.fragment = null
            }
            Int i6 = i4 + 1
            op.enterAnim = this.mOps[i4]
            Int i7 = i6 + 1
            op.exitAnim = this.mOps[i6]
            Int i8 = i7 + 1
            op.popEnterAnim = this.mOps[i7]
            op.popExitAnim = this.mOps[i8]
            backStackRecord.mEnterAnim = op.enterAnim
            backStackRecord.mExitAnim = op.exitAnim
            backStackRecord.mPopEnterAnim = op.popEnterAnim
            backStackRecord.mPopExitAnim = op.popExitAnim
            backStackRecord.addOp(op)
            i2++
            i = i8 + 1
        }
        backStackRecord.mTransition = this.mTransition
        backStackRecord.mTransitionStyle = this.mTransitionStyle
        backStackRecord.mName = this.mName
        backStackRecord.mIndex = this.mIndex
        backStackRecord.mAddToBackStack = true
        backStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes
        backStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText
        backStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes
        backStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText
        backStackRecord.mSharedElementSourceNames = this.mSharedElementSourceNames
        backStackRecord.mSharedElementTargetNames = this.mSharedElementTargetNames
        backStackRecord.mReorderingAllowed = this.mReorderingAllowed
        backStackRecord.bumpBackStackNesting(1)
        return backStackRecord
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeIntArray(this.mOps)
        parcel.writeInt(this.mTransition)
        parcel.writeInt(this.mTransitionStyle)
        parcel.writeString(this.mName)
        parcel.writeInt(this.mIndex)
        parcel.writeInt(this.mBreadCrumbTitleRes)
        TextUtils.writeToParcel(this.mBreadCrumbTitleText, parcel, 0)
        parcel.writeInt(this.mBreadCrumbShortTitleRes)
        TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, parcel, 0)
        parcel.writeStringList(this.mSharedElementSourceNames)
        parcel.writeStringList(this.mSharedElementTargetNames)
        parcel.writeInt(this.mReorderingAllowed ? 1 : 0)
    }
}
