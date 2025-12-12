package android.support.v4.view

import android.support.annotation.Nullable

public interface NestedScrollingChild {
    Boolean dispatchNestedFling(Float f, Float f2, Boolean z)

    Boolean dispatchNestedPreFling(Float f, Float f2)

    Boolean dispatchNestedPreScroll(Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2)

    Boolean dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr)

    Boolean hasNestedScrollingParent()

    Boolean isNestedScrollingEnabled()

    Unit setNestedScrollingEnabled(Boolean z)

    Boolean startNestedScroll(Int i)

    Unit stopNestedScroll()
}
