package android.support.v4.view

import android.support.annotation.Nullable

public interface NestedScrollingChild2 extends NestedScrollingChild {
    Boolean dispatchNestedPreScroll(Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2, Int i3)

    Boolean dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr, Int i5)

    Boolean hasNestedScrollingParent(Int i)

    Boolean startNestedScroll(Int i, Int i2)

    Unit stopNestedScroll(Int i)
}
