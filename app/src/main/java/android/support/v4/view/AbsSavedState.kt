package android.support.v4.view

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable

abstract class AbsSavedState implements Parcelable {
    private final Parcelable mSuperState
    public static val EMPTY_STATE = AbsSavedState() { // from class: android.support.v4.view.AbsSavedState.1
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v4.view.AbsSavedState.2
        @Override // android.os.Parcelable.Creator
        public final AbsSavedState createFromParcel(Parcel parcel) {
            return createFromParcel(parcel, (ClassLoader) null)
        }

        @Override // android.os.Parcelable.ClassLoaderCreator
        public final AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            if (parcel.readParcelable(classLoader) != null) {
                throw IllegalStateException("superState must be null")
            }
            return AbsSavedState.EMPTY_STATE
        }

        @Override // android.os.Parcelable.Creator
        public final Array<AbsSavedState> newArray(Int i) {
            return new AbsSavedState[i]
        }
    }

    private constructor() {
        this.mSuperState = null
    }

    protected constructor(@NonNull Parcel parcel) {
        this(parcel, null)
    }

    protected constructor(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
        Parcelable parcelable = parcel.readParcelable(classLoader)
        this.mSuperState = parcelable == null ? EMPTY_STATE : parcelable
    }

    protected constructor(@NonNull Parcelable parcelable) {
        if (parcelable == null) {
            throw IllegalArgumentException("superState must not be null")
        }
        this.mSuperState = parcelable == EMPTY_STATE ? null : parcelable
    }

    @Override // android.os.Parcelable
    fun describeContents() {
        return 0
    }

    @Nullable
    public final Parcelable getSuperState() {
        return this.mSuperState
    }

    @Override // android.os.Parcelable
    fun writeToParcel(Parcel parcel, Int i) {
        parcel.writeParcelable(this.mSuperState, i)
    }
}
