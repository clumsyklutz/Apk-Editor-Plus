package android.support.v7.view

import android.support.annotation.RestrictTo
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.view.View
import android.view.animation.Interpolator
import java.util.ArrayList
import java.util.Iterator

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ViewPropertyAnimatorCompatSet {
    private Interpolator mInterpolator
    private Boolean mIsStarted
    ViewPropertyAnimatorListener mListener
    private Long mDuration = -1
    private val mProxyListener = ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.view.ViewPropertyAnimatorCompatSet.1
        private Boolean mProxyStarted = false
        private Int mProxyEndCount = 0

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationEnd(View view) {
            Int i = this.mProxyEndCount + 1
            this.mProxyEndCount = i
            if (i == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
                if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                    ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null)
                }
                onEnd()
            }
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationStart(View view) {
            if (this.mProxyStarted) {
                return
            }
            this.mProxyStarted = true
            if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null)
            }
        }

        Unit onEnd() {
            this.mProxyEndCount = 0
            this.mProxyStarted = false
            ViewPropertyAnimatorCompatSet.this.onAnimationsEnded()
        }
    }
    val mAnimators = ArrayList()

    fun cancel() {
        if (this.mIsStarted) {
            Iterator it = this.mAnimators.iterator()
            while (it.hasNext()) {
                ((ViewPropertyAnimatorCompat) it.next()).cancel()
            }
            this.mIsStarted = false
        }
    }

    Unit onAnimationsEnded() {
        this.mIsStarted = false
    }

    fun play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (!this.mIsStarted) {
            this.mAnimators.add(viewPropertyAnimatorCompat)
        }
        return this
    }

    fun playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        this.mAnimators.add(viewPropertyAnimatorCompat)
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration())
        this.mAnimators.add(viewPropertyAnimatorCompat2)
        return this
    }

    fun setDuration(Long j) {
        if (!this.mIsStarted) {
            this.mDuration = j
        }
        return this
    }

    fun setInterpolator(Interpolator interpolator) {
        if (!this.mIsStarted) {
            this.mInterpolator = interpolator
        }
        return this
    }

    fun setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (!this.mIsStarted) {
            this.mListener = viewPropertyAnimatorListener
        }
        return this
    }

    fun start() {
        if (this.mIsStarted) {
            return
        }
        Iterator it = this.mAnimators.iterator()
        while (it.hasNext()) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) it.next()
            if (this.mDuration >= 0) {
                viewPropertyAnimatorCompat.setDuration(this.mDuration)
            }
            if (this.mInterpolator != null) {
                viewPropertyAnimatorCompat.setInterpolator(this.mInterpolator)
            }
            if (this.mListener != null) {
                viewPropertyAnimatorCompat.setListener(this.mProxyListener)
            }
            viewPropertyAnimatorCompat.start()
        }
        this.mIsStarted = true
    }
}
