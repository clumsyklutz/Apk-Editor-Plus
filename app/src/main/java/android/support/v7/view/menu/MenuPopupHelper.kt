package android.support.v7.view.menu

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.R
import android.support.v7.view.menu.MenuPresenter
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import android.widget.PopupWindow

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuPopupHelper implements MenuHelper {
    private static val TOUCH_EPICENTER_SIZE_DP = 48
    private View mAnchorView
    private final Context mContext
    private Int mDropDownGravity
    private Boolean mForceShowIcon
    private final PopupWindow.OnDismissListener mInternalOnDismissListener
    private final MenuBuilder mMenu
    private PopupWindow.OnDismissListener mOnDismissListener
    private final Boolean mOverflowOnly
    private MenuPopup mPopup
    private final Int mPopupStyleAttr
    private final Int mPopupStyleRes
    private MenuPresenter.Callback mPresenterCallback

    constructor(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0)
    }

    constructor(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0)
    }

    constructor(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, Boolean z, @AttrRes Int i) {
        this(context, menuBuilder, view, z, i, 0)
    }

    constructor(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, Boolean z, @AttrRes Int i, @StyleRes Int i2) {
        this.mDropDownGravity = GravityCompat.START
        this.mInternalOnDismissListener = new PopupWindow.OnDismissListener() { // from class: android.support.v7.view.menu.MenuPopupHelper.1
            @Override // android.widget.PopupWindow.OnDismissListener
            fun onDismiss() {
                MenuPopupHelper.this.onDismiss()
            }
        }
        this.mContext = context
        this.mMenu = menuBuilder
        this.mAnchorView = view
        this.mOverflowOnly = z
        this.mPopupStyleAttr = i
        this.mPopupStyleRes = i2
    }

    @NonNull
    private fun createPopup() {
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay()
        Point point = Point()
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point)
        } else {
            defaultDisplay.getSize(point)
        }
        MenuPopup cascadingMenuPopup = Math.min(point.x, point.y) >= this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width) ? CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly) : StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly)
        cascadingMenuPopup.addMenu(this.mMenu)
        cascadingMenuPopup.setOnDismissListener(this.mInternalOnDismissListener)
        cascadingMenuPopup.setAnchorView(this.mAnchorView)
        cascadingMenuPopup.setCallback(this.mPresenterCallback)
        cascadingMenuPopup.setForceShowIcon(this.mForceShowIcon)
        cascadingMenuPopup.setGravity(this.mDropDownGravity)
        return cascadingMenuPopup
    }

    private fun showPopup(Int i, Int i2, Boolean z, Boolean z2) {
        MenuPopup popup = getPopup()
        popup.setShowTitle(z2)
        if (z) {
            if ((GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7) == 5) {
                i -= this.mAnchorView.getWidth()
            }
            popup.setHorizontalOffset(i)
            popup.setVerticalOffset(i2)
            Int i3 = (Int) ((this.mContext.getResources().getDisplayMetrics().density * 48.0f) / 2.0f)
            popup.setEpicenterBounds(Rect(i - i3, i2 - i3, i + i3, i3 + i2))
        }
        popup.show()
    }

    @Override // android.support.v7.view.menu.MenuHelper
    fun dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss()
        }
    }

    fun getGravity() {
        return this.mDropDownGravity
    }

    fun getListView() {
        return getPopup().getListView()
    }

    @NonNull
    fun getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup()
        }
        return this.mPopup
    }

    fun isShowing() {
        return this.mPopup != null && this.mPopup.isShowing()
    }

    protected fun onDismiss() {
        this.mPopup = null
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss()
        }
    }

    fun setAnchorView(@NonNull View view) {
        this.mAnchorView = view
    }

    fun setForceShowIcon(Boolean z) {
        this.mForceShowIcon = z
        if (this.mPopup != null) {
            this.mPopup.setForceShowIcon(z)
        }
    }

    fun setGravity(Int i) {
        this.mDropDownGravity = i
    }

    fun setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener
    }

    @Override // android.support.v7.view.menu.MenuHelper
    fun setPresenterCallback(@Nullable MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback
        if (this.mPopup != null) {
            this.mPopup.setCallback(callback)
        }
    }

    fun show() {
        if (!tryShow()) {
            throw IllegalStateException("MenuPopupHelper cannot be used without an anchor")
        }
    }

    fun show(Int i, Int i2) {
        if (!tryShow(i, i2)) {
            throw IllegalStateException("MenuPopupHelper cannot be used without an anchor")
        }
    }

    fun tryShow() {
        if (isShowing()) {
            return true
        }
        if (this.mAnchorView == null) {
            return false
        }
        showPopup(0, 0, false, false)
        return true
    }

    fun tryShow(Int i, Int i2) {
        if (isShowing()) {
            return true
        }
        if (this.mAnchorView == null) {
            return false
        }
        showPopup(i, i2, true, true)
        return true
    }
}
