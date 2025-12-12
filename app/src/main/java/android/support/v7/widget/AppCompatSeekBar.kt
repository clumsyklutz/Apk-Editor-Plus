package android.support.v7.widget

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.R
import android.util.AttributeSet
import android.widget.SeekBar
import java.lang.reflect.InvocationTargetException

class AppCompatSeekBar extends SeekBar {
    private final AppCompatSeekBarHelper mAppCompatSeekBarHelper

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super(context, attributeSet, i)
        this.mAppCompatSeekBarHelper = AppCompatSeekBarHelper(this)
        this.mAppCompatSeekBarHelper.loadFromAttributes(attributeSet, i)
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        this.mAppCompatSeekBarHelper.drawableStateChanged()
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        this.mAppCompatSeekBarHelper.jumpDrawablesToCurrentState()
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized Unit onDraw(Canvas canvas) {
        super.onDraw(canvas)
        this.mAppCompatSeekBarHelper.drawTickMarks(canvas)
    }
}
