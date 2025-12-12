package android.support.v7.widget

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.View
import android.widget.RatingBar

class AppCompatRatingBar extends RatingBar {
    private final AppCompatProgressBarHelper mAppCompatProgressBarHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.ratingBarStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mAppCompatProgressBarHelper = AppCompatProgressBarHelper(this)
        this.mAppCompatProgressBarHelper.loadFromAttributes(attributeSet, i)
    }

    @Override // android.widget.RatingBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized Unit onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
        Bitmap sampleTime = this.mAppCompatProgressBarHelper.getSampleTime()
        if (sampleTime != null) {
            setMeasuredDimension(View.resolveSizeAndState(sampleTime.getWidth() * getNumStars(), i, 0), getMeasuredHeight())
        }
    }
}
