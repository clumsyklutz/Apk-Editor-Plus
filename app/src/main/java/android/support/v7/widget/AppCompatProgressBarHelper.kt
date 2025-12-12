package android.support.v7.widget

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.support.v4.graphics.drawable.WrappedDrawable
import android.util.AttributeSet
import android.widget.ProgressBar

class AppCompatProgressBarHelper {
    private static final Array<Int> TINT_ATTRS = {R.attr.indeterminateDrawable, R.attr.progressDrawable}
    private Bitmap mSampleTile
    private final ProgressBar mView

    AppCompatProgressBarHelper(ProgressBar progressBar) {
        this.mView = progressBar
    }

    private fun getDrawableShape() {
        return RoundRectShape(new Array<Float>{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null)
    }

    /* JADX WARN: Multi-variable type inference failed */
    private fun tileify(Drawable drawable, Boolean z) {
        if (drawable is WrappedDrawable) {
            Drawable wrappedDrawable = ((WrappedDrawable) drawable).getWrappedDrawable()
            if (wrappedDrawable == null) {
                return drawable
            }
            ((WrappedDrawable) drawable).setWrappedDrawable(tileify(wrappedDrawable, z))
            return drawable
        }
        if (!(drawable is LayerDrawable)) {
            if (!(drawable is BitmapDrawable)) {
                return drawable
            }
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable
            Bitmap bitmap = bitmapDrawable.getBitmap()
            if (this.mSampleTile == null) {
                this.mSampleTile = bitmap
            }
            ShapeDrawable shapeDrawable = ShapeDrawable(getDrawableShape())
            shapeDrawable.getPaint().setShader(BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP))
            shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter())
            return z ? ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable
        Int numberOfLayers = layerDrawable.getNumberOfLayers()
        Array<Drawable> drawableArr = new Drawable[numberOfLayers]
        for (Int i = 0; i < numberOfLayers; i++) {
            Int id = layerDrawable.getId(i)
            drawableArr[i] = tileify(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303)
        }
        LayerDrawable layerDrawable2 = LayerDrawable(drawableArr)
        for (Int i2 = 0; i2 < numberOfLayers; i2++) {
            layerDrawable2.setId(i2, layerDrawable.getId(i2))
        }
        return layerDrawable2
    }

    private fun tileifyIndeterminate(Drawable drawable) {
        if (!(drawable is AnimationDrawable)) {
            return drawable
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable
        Int numberOfFrames = animationDrawable.getNumberOfFrames()
        AnimationDrawable animationDrawable2 = AnimationDrawable()
        animationDrawable2.setOneShot(animationDrawable.isOneShot())
        for (Int i = 0; i < numberOfFrames; i++) {
            Drawable drawableTileify = tileify(animationDrawable.getFrame(i), true)
            drawableTileify.setLevel(10000)
            animationDrawable2.addFrame(drawableTileify, animationDrawable.getDuration(i))
        }
        animationDrawable2.setLevel(10000)
        return animationDrawable2
    }

    Bitmap getSampleTime() {
        return this.mSampleTile
    }

    Unit loadFromAttributes(AttributeSet attributeSet, Int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, TINT_ATTRS, i, 0)
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0)
        if (drawableIfKnown != null) {
            this.mView.setIndeterminateDrawable(tileifyIndeterminate(drawableIfKnown))
        }
        Drawable drawableIfKnown2 = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(1)
        if (drawableIfKnown2 != null) {
            this.mView.setProgressDrawable(tileify(drawableIfKnown2, false))
        }
        tintTypedArrayObtainStyledAttributes.recycle()
    }
}
