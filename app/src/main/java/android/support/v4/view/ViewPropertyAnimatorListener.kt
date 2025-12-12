package android.support.v4.view

import android.view.View

public interface ViewPropertyAnimatorListener {
    Unit onAnimationCancel(View view)

    Unit onAnimationEnd(View view)

    Unit onAnimationStart(View view)
}
