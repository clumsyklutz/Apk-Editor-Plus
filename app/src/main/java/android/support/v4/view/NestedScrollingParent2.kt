package android.support.v4.view

import android.support.annotation.NonNull
import android.view.View

public interface NestedScrollingParent2 extends NestedScrollingParent {
    Unit onNestedPreScroll(@NonNull View view, Int i, Int i2, @NonNull Array<Int> iArr, Int i3)

    Unit onNestedScroll(@NonNull View view, Int i, Int i2, Int i3, Int i4, Int i5)

    Unit onNestedScrollAccepted(@NonNull View view, @NonNull View view2, Int i, Int i2)

    Boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, Int i, Int i2)

    Unit onStopNestedScroll(@NonNull View view, Int i)
}
