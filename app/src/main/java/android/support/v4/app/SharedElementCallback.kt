package android.support.v4.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import java.util.List
import java.util.Map

abstract class SharedElementCallback {
    private static val BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap"
    private static val BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix"
    private static val BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType"
    private static val MAX_IMAGE_SIZE = 1048576
    private Matrix mTempMatrix

    public interface OnSharedElementsReadyListener {
        Unit onSharedElementsReady()
    }

    private fun createDrawableBitmap(Drawable drawable) {
        Int intrinsicWidth = drawable.getIntrinsicWidth()
        Int intrinsicHeight = drawable.getIntrinsicHeight()
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null
        }
        Float fMin = Math.min(1.0f, 1048576.0f / (intrinsicWidth * intrinsicHeight))
        if ((drawable is BitmapDrawable) && fMin == 1.0f) {
            return ((BitmapDrawable) drawable).getBitmap()
        }
        Int i = (Int) (intrinsicWidth * fMin)
        Int i2 = (Int) (intrinsicHeight * fMin)
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888)
        Canvas canvas = Canvas(bitmapCreateBitmap)
        Rect bounds = drawable.getBounds()
        Int i3 = bounds.left
        Int i4 = bounds.top
        Int i5 = bounds.right
        Int i6 = bounds.bottom
        drawable.setBounds(0, 0, i, i2)
        drawable.draw(canvas)
        drawable.setBounds(i3, i4, i5, i6)
        return bitmapCreateBitmap
    }

    fun onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        Bitmap bitmapCreateDrawableBitmap
        if (view is ImageView) {
            ImageView imageView = (ImageView) view
            Drawable drawable = imageView.getDrawable()
            Drawable background = imageView.getBackground()
            if (drawable != null && background == null && (bitmapCreateDrawableBitmap = createDrawableBitmap(drawable)) != null) {
                Bundle bundle = Bundle()
                bundle.putParcelable(BUNDLE_SNAPSHOT_BITMAP, bitmapCreateDrawableBitmap)
                bundle.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView.getScaleType().toString())
                if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
                    Array<Float> fArr = new Float[9]
                    imageView.getImageMatrix().getValues(fArr)
                    bundle.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, fArr)
                }
                return bundle
            }
        }
        Int iRound = Math.round(rectF.width())
        Int iRound2 = Math.round(rectF.height())
        if (iRound <= 0 || iRound2 <= 0) {
            return null
        }
        Float fMin = Math.min(1.0f, 1048576.0f / (iRound * iRound2))
        Int i = (Int) (iRound * fMin)
        Int i2 = (Int) (iRound2 * fMin)
        if (this.mTempMatrix == null) {
            this.mTempMatrix = Matrix()
        }
        this.mTempMatrix.set(matrix)
        this.mTempMatrix.postTranslate(-rectF.left, -rectF.top)
        this.mTempMatrix.postScale(fMin, fMin)
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888)
        Canvas canvas = Canvas(bitmapCreateBitmap)
        canvas.concat(this.mTempMatrix)
        view.draw(canvas)
        return bitmapCreateBitmap
    }

    fun onCreateSnapshotView(Context context, Parcelable parcelable) {
        ImageView imageView
        if (parcelable is Bundle) {
            Bundle bundle = (Bundle) parcelable
            Bitmap bitmap = (Bitmap) bundle.getParcelable(BUNDLE_SNAPSHOT_BITMAP)
            if (bitmap == null) {
                return null
            }
            ImageView imageView2 = ImageView(context)
            imageView2.setImageBitmap(bitmap)
            imageView2.setScaleType(ImageView.ScaleType.valueOf(bundle.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)))
            if (imageView2.getScaleType() == ImageView.ScaleType.MATRIX) {
                Array<Float> floatArray = bundle.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX)
                Matrix matrix = Matrix()
                matrix.setValues(floatArray)
                imageView2.setImageMatrix(matrix)
            }
            imageView = imageView2
        } else if (parcelable is Bitmap) {
            imageView = ImageView(context)
            imageView.setImageBitmap((Bitmap) parcelable)
        } else {
            imageView = null
        }
        return imageView
    }

    fun onMapSharedElements(List list, Map map) {
    }

    fun onRejectSharedElements(List list) {
    }

    fun onSharedElementEnd(List list, List list2, List list3) {
    }

    fun onSharedElementStart(List list, List list2, List list3) {
    }

    fun onSharedElementsArrived(List list, List list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady()
    }
}
