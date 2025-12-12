package android.support.v4.view

import android.support.annotation.NonNull
import android.view.View

public interface NestedScrollingParent {
    Int getNestedScrollAxes()

    Boolean onNestedFling(@NonNull View view, Float f, Float f2, Boolean z)

    Boolean onNestedPreFling(@NonNull View view, Float f, Float f2)

    Unit onNestedPreScroll(@NonNull View view, Int i, Int i2, @NonNull Array<Int> iArr)

    Unit onNestedScroll(@NonNull View view, Int i, Int i2, Int i3, Int i4)

    Unit onNestedScrollAccepted(@NonNull View view, @NonNull View view2, Int i)

    Boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, Int i)

    Unit onStopNestedScroll(@NonNull View view)
}
