package android.support.v4.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import android.view.animation.Animation
import android.widget.ImageView

class CircleImageView extends ImageView {
    private static val FILL_SHADOW_COLOR = 1023410176
    private static val KEY_SHADOW_COLOR = 503316480
    private static val SHADOW_ELEVATION = 4
    private static val SHADOW_RADIUS = 3.5f
    private static val X_OFFSET = 0.0f
    private static val Y_OFFSET = 1.75f
    private Animation.AnimationListener mListener
    Int mShadowRadius

    class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient
        private Paint mShadowPaint = Paint()

        OvalShadow(Int i) {
            CircleImageView.this.mShadowRadius = i
            updateRadialGradient((Int) rect().width())
        }

        private fun updateRadialGradient(Int i) {
            this.mRadialGradient = RadialGradient(i / 2, i / 2, CircleImageView.this.mShadowRadius, new Array<Int>{CircleImageView.FILL_SHADOW_COLOR, 0}, (Array<Float>) null, Shader.TileMode.CLAMP)
            this.mShadowPaint.setShader(this.mRadialGradient)
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        fun draw(Canvas canvas, Paint paint) {
            Int width = CircleImageView.this.getWidth()
            Int height = CircleImageView.this.getHeight()
            canvas.drawCircle(width / 2, height / 2, width / 2, this.mShadowPaint)
            canvas.drawCircle(width / 2, height / 2, (width / 2) - CircleImageView.this.mShadowRadius, paint)
        }

        @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        protected fun onResize(Float f, Float f2) {
            super.onResize(f, f2)
            updateRadialGradient((Int) f)
        }
    }

    CircleImageView(Context context, Int i) {
        ShapeDrawable shapeDrawable
        super(context)
        Float f = getContext().getResources().getDisplayMetrics().density
        Int i2 = (Int) (Y_OFFSET * f)
        Int i3 = (Int) (0.0f * f)
        this.mShadowRadius = (Int) (SHADOW_RADIUS * f)
        if (elevationSupported()) {
            shapeDrawable = ShapeDrawable(OvalShape())
            ViewCompat.setElevation(this, f * 4.0f)
        } else {
            shapeDrawable = ShapeDrawable(OvalShadow(this.mShadowRadius))
            setLayerType(1, shapeDrawable.getPaint())
            shapeDrawable.getPaint().setShadowLayer(this.mShadowRadius, i3, i2, KEY_SHADOW_COLOR)
            Int i4 = this.mShadowRadius
            setPadding(i4, i4, i4, i4)
        }
        shapeDrawable.getPaint().setColor(i)
        ViewCompat.setBackground(this, shapeDrawable)
    }

    private fun elevationSupported() {
        return Build.VERSION.SDK_INT >= 21
    }

    @Override // android.view.View
    fun onAnimationEnd() {
        super.onAnimationEnd()
        if (this.mListener != null) {
            this.mListener.onAnimationEnd(getAnimation())
        }
    }

    @Override // android.view.View
    fun onAnimationStart() {
        super.onAnimationStart()
        if (this.mListener != null) {
            this.mListener.onAnimationStart(getAnimation())
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
        if (elevationSupported()) {
            return
        }
        setMeasuredDimension(getMeasuredWidth() + (this.mShadowRadius << 1), getMeasuredHeight() + (this.mShadowRadius << 1))
    }

    fun setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener
    }

    @Override // android.view.View
    fun setBackgroundColor(Int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i)
        }
    }

    fun setBackgroundColorRes(Int i) {
        setBackgroundColor(ContextCompat.getColor(getContext(), i))
    }
}
