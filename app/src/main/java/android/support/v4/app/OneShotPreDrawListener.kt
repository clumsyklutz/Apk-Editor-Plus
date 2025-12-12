package android.support.v4.app

import android.view.View
import android.view.ViewTreeObserver

class OneShotPreDrawListener implements View.OnAttachStateChangeListener, ViewTreeObserver.OnPreDrawListener {
    private final Runnable mRunnable
    private final View mView
    private ViewTreeObserver mViewTreeObserver

    private constructor(View view, Runnable runnable) {
        this.mView = view
        this.mViewTreeObserver = view.getViewTreeObserver()
        this.mRunnable = runnable
    }

    fun add(View view, Runnable runnable) {
        OneShotPreDrawListener oneShotPreDrawListener = OneShotPreDrawListener(view, runnable)
        view.getViewTreeObserver().addOnPreDrawListener(oneShotPreDrawListener)
        view.addOnAttachStateChangeListener(oneShotPreDrawListener)
        return oneShotPreDrawListener
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    fun onPreDraw() {
        removeListener()
        this.mRunnable.run()
        return true
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewAttachedToWindow(View view) {
        this.mViewTreeObserver = view.getViewTreeObserver()
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewDetachedFromWindow(View view) {
        removeListener()
    }

    fun removeListener() {
        if (this.mViewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnPreDrawListener(this)
        } else {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this)
        }
        this.mView.removeOnAttachStateChangeListener(this)
    }
}
