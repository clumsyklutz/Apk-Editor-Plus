package android.support.v7.view

import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.view.ActionMode
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import java.util.List

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class WindowCallbackWrapper implements Window.Callback {
    final Window.Callback mWrapped

    constructor(Window.Callback callback) {
        if (callback == null) {
            throw IllegalArgumentException("Window callback may not be null")
        }
        this.mWrapped = callback
    }

    @Override // android.view.Window.Callback
    fun dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchGenericMotionEvent(motionEvent)
    }

    @Override // android.view.Window.Callback
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyEvent(keyEvent)
    }

    @Override // android.view.Window.Callback
    fun dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyShortcutEvent(keyEvent)
    }

    @Override // android.view.Window.Callback
    fun dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.mWrapped.dispatchPopulateAccessibilityEvent(accessibilityEvent)
    }

    @Override // android.view.Window.Callback
    fun dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTouchEvent(motionEvent)
    }

    @Override // android.view.Window.Callback
    fun dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTrackballEvent(motionEvent)
    }

    @Override // android.view.Window.Callback
    fun onActionModeFinished(android.view.ActionMode actionMode) {
        this.mWrapped.onActionModeFinished(actionMode)
    }

    @Override // android.view.Window.Callback
    fun onActionModeStarted(android.view.ActionMode actionMode) {
        this.mWrapped.onActionModeStarted(actionMode)
    }

    @Override // android.view.Window.Callback
    fun onAttachedToWindow() {
        this.mWrapped.onAttachedToWindow()
    }

    @Override // android.view.Window.Callback
    fun onContentChanged() {
        this.mWrapped.onContentChanged()
    }

    @Override // android.view.Window.Callback
    fun onCreatePanelMenu(Int i, Menu menu) {
        return this.mWrapped.onCreatePanelMenu(i, menu)
    }

    @Override // android.view.Window.Callback
    fun onCreatePanelView(Int i) {
        return this.mWrapped.onCreatePanelView(i)
    }

    @Override // android.view.Window.Callback
    fun onDetachedFromWindow() {
        this.mWrapped.onDetachedFromWindow()
    }

    @Override // android.view.Window.Callback
    fun onMenuItemSelected(Int i, MenuItem menuItem) {
        return this.mWrapped.onMenuItemSelected(i, menuItem)
    }

    @Override // android.view.Window.Callback
    fun onMenuOpened(Int i, Menu menu) {
        return this.mWrapped.onMenuOpened(i, menu)
    }

    @Override // android.view.Window.Callback
    fun onPanelClosed(Int i, Menu menu) {
        this.mWrapped.onPanelClosed(i, menu)
    }

    @Override // android.view.Window.Callback
    @RequiresApi(26)
    fun onPointerCaptureChanged(Boolean z) {
        this.mWrapped.onPointerCaptureChanged(z)
    }

    @Override // android.view.Window.Callback
    fun onPreparePanel(Int i, View view, Menu menu) {
        return this.mWrapped.onPreparePanel(i, view, menu)
    }

    @Override // android.view.Window.Callback
    @RequiresApi(24)
    fun onProvideKeyboardShortcuts(List list, Menu menu, Int i) {
        this.mWrapped.onProvideKeyboardShortcuts(list, menu, i)
    }

    @Override // android.view.Window.Callback
    fun onSearchRequested() {
        return this.mWrapped.onSearchRequested()
    }

    @Override // android.view.Window.Callback
    @RequiresApi(23)
    fun onSearchRequested(SearchEvent searchEvent) {
        return this.mWrapped.onSearchRequested(searchEvent)
    }

    @Override // android.view.Window.Callback
    fun onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.mWrapped.onWindowAttributesChanged(layoutParams)
    }

    @Override // android.view.Window.Callback
    fun onWindowFocusChanged(Boolean z) {
        this.mWrapped.onWindowFocusChanged(z)
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.mWrapped.onWindowStartingActionMode(callback)
    }

    @Override // android.view.Window.Callback
    @RequiresApi(23)
    public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, Int i) {
        return this.mWrapped.onWindowStartingActionMode(callback, i)
    }
}
