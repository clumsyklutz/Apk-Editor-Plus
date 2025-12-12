package android.support.v7.widget

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.MenuRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StyleRes
import androidx.appcompat.R
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.view.menu.ShowableListMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.PopupWindow

class PopupMenu {
    private final View mAnchor
    private final Context mContext
    private View.OnTouchListener mDragListener
    private final MenuBuilder mMenu
    OnMenuItemClickListener mMenuItemClickListener
    OnDismissListener mOnDismissListener
    final MenuPopupHelper mPopup

    public interface OnDismissListener {
        Unit onDismiss(PopupMenu popupMenu)
    }

    public interface OnMenuItemClickListener {
        Boolean onMenuItemClick(MenuItem menuItem)
    }

    constructor(@NonNull Context context, @NonNull View view) {
        this(context, view, 0)
    }

    constructor(@NonNull Context context, @NonNull View view, Int i) {
        this(context, view, i, R.attr.popupMenuStyle, 0)
    }

    constructor(@NonNull Context context, @NonNull View view, Int i, @AttrRes Int i2, @StyleRes Int i3) {
        this.mContext = context
        this.mAnchor = view
        this.mMenu = MenuBuilder(context)
        this.mMenu.setCallback(new MenuBuilder.Callback() { // from class: android.support.v7.widget.PopupMenu.1
            @Override // android.support.v7.view.menu.MenuBuilder.Callback
            fun onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                if (PopupMenu.this.mMenuItemClickListener != null) {
                    return PopupMenu.this.mMenuItemClickListener.onMenuItemClick(menuItem)
                }
                return false
            }

            @Override // android.support.v7.view.menu.MenuBuilder.Callback
            fun onMenuModeChange(MenuBuilder menuBuilder) {
            }
        })
        this.mPopup = MenuPopupHelper(context, this.mMenu, view, false, i2, i3)
        this.mPopup.setGravity(i)
        this.mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: android.support.v7.widget.PopupMenu.2
            @Override // android.widget.PopupWindow.OnDismissListener
            fun onDismiss() {
                if (PopupMenu.this.mOnDismissListener != null) {
                    PopupMenu.this.mOnDismissListener.onDismiss(PopupMenu.this)
                }
            }
        })
    }

    fun dismiss() {
        this.mPopup.dismiss()
    }

    @NonNull
    public View.OnTouchListener getDragToOpenListener() {
        if (this.mDragListener == null) {
            this.mDragListener = ForwardingListener(this.mAnchor) { // from class: android.support.v7.widget.PopupMenu.3
                @Override // android.support.v7.widget.ForwardingListener
                fun getPopup() {
                    return PopupMenu.this.mPopup.getPopup()
                }

                @Override // android.support.v7.widget.ForwardingListener
                protected fun onForwardingStarted() {
                    PopupMenu.this.show()
                    return true
                }

                @Override // android.support.v7.widget.ForwardingListener
                protected fun onForwardingStopped() {
                    PopupMenu.this.dismiss()
                    return true
                }
            }
        }
        return this.mDragListener
    }

    fun getGravity() {
        return this.mPopup.getGravity()
    }

    @NonNull
    fun getMenu() {
        return this.mMenu
    }

    @NonNull
    fun getMenuInflater() {
        return SupportMenuInflater(this.mContext)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    ListView getMenuListView() {
        if (this.mPopup.isShowing()) {
            return this.mPopup.getListView()
        }
        return null
    }

    fun inflate(@MenuRes Int i) {
        getMenuInflater().inflate(i, this.mMenu)
    }

    fun setGravity(Int i) {
        this.mPopup.setGravity(i)
    }

    fun setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener
    }

    fun setOnMenuItemClickListener(@Nullable OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItemClickListener = onMenuItemClickListener
    }

    fun show() {
        this.mPopup.show()
    }
}
