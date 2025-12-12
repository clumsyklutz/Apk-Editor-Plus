package android.support.v4.app

import androidx.lifecycle.ViewModelStore
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log

final class FragmentState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.app.FragmentState.1
        @Override // android.os.Parcelable.Creator
        public final FragmentState createFromParcel(Parcel parcel) {
            return FragmentState(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<FragmentState> newArray(Int i) {
            return new FragmentState[i]
        }
    }
    final Bundle mArguments
    final String mClassName
    final Int mContainerId
    final Boolean mDetached
    final Int mFragmentId
    final Boolean mFromLayout
    final Boolean mHidden
    final Int mIndex
    Fragment mInstance
    final Boolean mRetainInstance
    Bundle mSavedFragmentState
    final String mTag

    FragmentState(Parcel parcel) {
        this.mClassName = parcel.readString()
        this.mIndex = parcel.readInt()
        this.mFromLayout = parcel.readInt() != 0
        this.mFragmentId = parcel.readInt()
        this.mContainerId = parcel.readInt()
        this.mTag = parcel.readString()
        this.mRetainInstance = parcel.readInt() != 0
        this.mDetached = parcel.readInt() != 0
        this.mArguments = parcel.readBundle()
        this.mHidden = parcel.readInt() != 0
        this.mSavedFragmentState = parcel.readBundle()
    }

    FragmentState(Fragment fragment) {
        this.mClassName = fragment.getClass().getName()
        this.mIndex = fragment.mIndex
        this.mFromLayout = fragment.mFromLayout
        this.mFragmentId = fragment.mFragmentId
        this.mContainerId = fragment.mContainerId
        this.mTag = fragment.mTag
        this.mRetainInstance = fragment.mRetainInstance
        this.mDetached = fragment.mDetached
        this.mArguments = fragment.mArguments
        this.mHidden = fragment.mHidden
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    public final Fragment instantiate(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment, FragmentManagerNonConfig fragmentManagerNonConfig, ViewModelStore viewModelStore) {
        if (this.mInstance == null) {
            Context context = fragmentHostCallback.getContext()
            if (this.mArguments != null) {
                this.mArguments.setClassLoader(context.getClassLoader())
            }
            if (fragmentContainer != null) {
                this.mInstance = fragmentContainer.instantiate(context, this.mClassName, this.mArguments)
            } else {
                this.mInstance = Fragment.instantiate(context, this.mClassName, this.mArguments)
            }
            if (this.mSavedFragmentState != null) {
                this.mSavedFragmentState.setClassLoader(context.getClassLoader())
                this.mInstance.mSavedFragmentState = this.mSavedFragmentState
            }
            this.mInstance.setIndex(this.mIndex, fragment)
            this.mInstance.mFromLayout = this.mFromLayout
            this.mInstance.mRestored = true
            this.mInstance.mFragmentId = this.mFragmentId
            this.mInstance.mContainerId = this.mContainerId
            this.mInstance.mTag = this.mTag
            this.mInstance.mRetainInstance = this.mRetainInstance
            this.mInstance.mDetached = this.mDetached
            this.mInstance.mHidden = this.mHidden
            this.mInstance.mFragmentManager = fragmentHostCallback.mFragmentManager
            if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Instantiated fragment " + this.mInstance)
            }
        }
        this.mInstance.mChildNonConfig = fragmentManagerNonConfig
        this.mInstance.mViewModelStore = viewModelStore
        return this.mInstance
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeString(this.mClassName)
        parcel.writeInt(this.mIndex)
        parcel.writeInt(this.mFromLayout ? 1 : 0)
        parcel.writeInt(this.mFragmentId)
        parcel.writeInt(this.mContainerId)
        parcel.writeString(this.mTag)
        parcel.writeInt(this.mRetainInstance ? 1 : 0)
        parcel.writeInt(this.mDetached ? 1 : 0)
        parcel.writeBundle(this.mArguments)
        parcel.writeInt(this.mHidden ? 1 : 0)
        parcel.writeBundle(this.mSavedFragmentState)
    }
}
