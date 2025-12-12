package android.support.v7.widget

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Rect
import android.support.annotation.RestrictTo
import android.support.v4.view.PointerIconCompat
import androidx.appcompat.R
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TooltipPopup {
    private static val TAG = "TooltipPopup"
    private final View mContentView
    private final Context mContext
    private final TextView mMessageView
    private final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams()
    private val mTmpDisplayFrame = Rect()
    private final Array<Int> mTmpAnchorPos = new Int[2]
    private final Array<Int> mTmpAppPos = new Int[2]

    TooltipPopup(Context context) {
        this.mContext = context
        this.mContentView = LayoutInflater.from(this.mContext).inflate(R.layout.abc_tooltip, (ViewGroup) null)
        this.mMessageView = (TextView) this.mContentView.findViewById(R.id.message)
        this.mLayoutParams.setTitle(getClass().getSimpleName())
        this.mLayoutParams.packageName = this.mContext.getPackageName()
        this.mLayoutParams.type = PointerIconCompat.TYPE_HAND
        this.mLayoutParams.width = -2
        this.mLayoutParams.height = -2
        this.mLayoutParams.format = -3
        this.mLayoutParams.windowAnimations = R.style.Animation_AppCompat_Tooltip
        this.mLayoutParams.flags = 24
    }

    private fun computePosition(View view, Int i, Int i2, Boolean z, WindowManager.LayoutParams layoutParams) throws Resources.NotFoundException {
        Int height
        Int i3
        layoutParams.token = view.getApplicationWindowToken()
        Int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold)
        if (view.getWidth() < dimensionPixelOffset) {
            i = view.getWidth() / 2
        }
        if (view.getHeight() >= dimensionPixelOffset) {
            Int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset)
            height = i2 + dimensionPixelOffset2
            i3 = i2 - dimensionPixelOffset2
        } else {
            height = view.getHeight()
            i3 = 0
        }
        layoutParams.gravity = 49
        Int dimensionPixelOffset3 = this.mContext.getResources().getDimensionPixelOffset(z ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch)
        View appRootView = getAppRootView(view)
        if (appRootView == null) {
            Log.e(TAG, "Cannot find app view")
            return
        }
        appRootView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame)
        if (this.mTmpDisplayFrame.left < 0 && this.mTmpDisplayFrame.top < 0) {
            Resources resources = this.mContext.getResources()
            Int identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
            Int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0
            DisplayMetrics displayMetrics = resources.getDisplayMetrics()
            this.mTmpDisplayFrame.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels)
        }
        appRootView.getLocationOnScreen(this.mTmpAppPos)
        view.getLocationOnScreen(this.mTmpAnchorPos)
        Array<Int> iArr = this.mTmpAnchorPos
        iArr[0] = iArr[0] - this.mTmpAppPos[0]
        Array<Int> iArr2 = this.mTmpAnchorPos
        iArr2[1] = iArr2[1] - this.mTmpAppPos[1]
        layoutParams.x = (this.mTmpAnchorPos[0] + i) - (appRootView.getWidth() / 2)
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
        this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec)
        Int measuredHeight = this.mContentView.getMeasuredHeight()
        Int i4 = ((i3 + this.mTmpAnchorPos[1]) - dimensionPixelOffset3) - measuredHeight
        Int i5 = height + this.mTmpAnchorPos[1] + dimensionPixelOffset3
        if (z) {
            if (i4 < 0) {
                layoutParams.y = i5
                return
            }
        } else if (measuredHeight + i5 <= this.mTmpDisplayFrame.height()) {
            layoutParams.y = i5
            return
        }
        layoutParams.y = i4
    }

    private fun getAppRootView(View view) {
        View rootView = view.getRootView()
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams()
        if ((layoutParams is WindowManager.LayoutParams) && ((WindowManager.LayoutParams) layoutParams).type == 2) {
            return rootView
        }
        for (Context context = view.getContext(); context is ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context is Activity) {
                return ((Activity) context).getWindow().getDecorView()
            }
        }
        return rootView
    }

    Unit hide() {
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mContentView)
        }
    }

    Boolean isShowing() {
        return this.mContentView.getParent() != null
    }

    Unit show(View view, Int i, Int i2, Boolean z, CharSequence charSequence) throws Resources.NotFoundException {
        if (isShowing()) {
            hide()
        }
        this.mMessageView.setText(charSequence)
        computePosition(view, i, i2, z, this.mLayoutParams)
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams)
    }
}
