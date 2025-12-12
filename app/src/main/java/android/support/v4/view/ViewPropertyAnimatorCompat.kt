package android.support.v4.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.view.animation.Interpolator
import java.lang.ref.WeakReference

class ViewPropertyAnimatorCompat {
    static val LISTENER_TAG_ID = 2113929216
    private static val TAG = "ViewAnimatorCompat"
    private WeakReference mView
    Runnable mStartAction = null
    Runnable mEndAction = null
    Int mOldLayerType = -1

    class ViewPropertyAnimatorListenerApi14 implements ViewPropertyAnimatorListener {
        Boolean mAnimEndCalled
        ViewPropertyAnimatorCompat mVpa

        ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.mVpa = viewPropertyAnimatorCompat
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationCancel(View view) {
            Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID)
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag is ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationCancel(view)
            }
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationEnd(View view) {
            if (this.mVpa.mOldLayerType >= 0) {
                view.setLayerType(this.mVpa.mOldLayerType, null)
                this.mVpa.mOldLayerType = -1
            }
            if (Build.VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
                if (this.mVpa.mEndAction != null) {
                    Runnable runnable = this.mVpa.mEndAction
                    this.mVpa.mEndAction = null
                    runnable.run()
                }
                Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID)
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag is ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd(view)
                }
                this.mAnimEndCalled = true
            }
        }

        @Override // android.support.v4.view.ViewPropertyAnimatorListener
        fun onAnimationStart(View view) {
            this.mAnimEndCalled = false
            if (this.mVpa.mOldLayerType >= 0) {
                view.setLayerType(2, null)
            }
            if (this.mVpa.mStartAction != null) {
                Runnable runnable = this.mVpa.mStartAction
                this.mVpa.mStartAction = null
                runnable.run()
            }
            Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID)
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag is ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationStart(view)
            }
        }
    }

    ViewPropertyAnimatorCompat(View view) {
        this.mView = WeakReference(view)
    }

    private fun setListenerInternal(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view.animate().setListener(AnimatorListenerAdapter() { // from class: android.support.v4.view.ViewPropertyAnimatorCompat.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                fun onAnimationCancel(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationCancel(view)
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                fun onAnimationEnd(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationEnd(view)
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                fun onAnimationStart(Animator animator) {
                    viewPropertyAnimatorListener.onAnimationStart(view)
                }
            })
        } else {
            view.animate().setListener(null)
        }
    }

    public final ViewPropertyAnimatorCompat alpha(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().alpha(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat alphaBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().alphaBy(f)
        }
        return this
    }

    public final Unit cancel() {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().cancel()
        }
    }

    public final Long getDuration() {
        View view = (View) this.mView.get()
        if (view != null) {
            return view.animate().getDuration()
        }
        return 0L
    }

    public final Interpolator getInterpolator() {
        View view = (View) this.mView.get()
        if (view == null || Build.VERSION.SDK_INT < 18) {
            return null
        }
        return (Interpolator) view.animate().getInterpolator()
    }

    public final Long getStartDelay() {
        View view = (View) this.mView.get()
        if (view != null) {
            return view.animate().getStartDelay()
        }
        return 0L
    }

    public final ViewPropertyAnimatorCompat rotation(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotation(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat rotationBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotationBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat rotationX(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotationX(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat rotationXBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotationXBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat rotationY(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotationY(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat rotationYBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().rotationYBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat scaleX(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().scaleX(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat scaleXBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().scaleXBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat scaleY(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().scaleY(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat scaleYBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().scaleYBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat setDuration(Long j) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().setDuration(j)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().setInterpolator(interpolator)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view = (View) this.mView.get()
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                setListenerInternal(view, viewPropertyAnimatorListener)
            } else {
                view.setTag(LISTENER_TAG_ID, viewPropertyAnimatorListener)
                setListenerInternal(view, ViewPropertyAnimatorListenerApi14(this))
            }
        }
        return this
    }

    public final ViewPropertyAnimatorCompat setStartDelay(Long j) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().setStartDelay(j)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat setUpdateListener(final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        val view = (View) this.mView.get()
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            view.animate().setUpdateListener(viewPropertyAnimatorUpdateListener != null ? new ValueAnimator.AnimatorUpdateListener() { // from class: android.support.v4.view.ViewPropertyAnimatorCompat.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                fun onAnimationUpdate(ValueAnimator valueAnimator) {
                    viewPropertyAnimatorUpdateListener.onAnimationUpdate(view)
                }
            } : null)
        }
        return this
    }

    public final Unit start() {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().start()
        }
    }

    public final ViewPropertyAnimatorCompat translationX(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().translationX(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat translationXBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().translationXBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat translationY(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().translationY(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat translationYBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().translationYBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat translationZ(Float f) {
        View view = (View) this.mView.get()
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().translationZ(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat translationZBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().translationZBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        View view = (View) this.mView.get()
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withEndAction(runnable)
            } else {
                setListenerInternal(view, ViewPropertyAnimatorListenerApi14(this))
                this.mEndAction = runnable
            }
        }
        return this
    }

    public final ViewPropertyAnimatorCompat withLayer() {
        View view = (View) this.mView.get()
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withLayer()
            } else {
                this.mOldLayerType = view.getLayerType()
                setListenerInternal(view, ViewPropertyAnimatorListenerApi14(this))
            }
        }
        return this
    }

    public final ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        View view = (View) this.mView.get()
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.animate().withStartAction(runnable)
            } else {
                setListenerInternal(view, ViewPropertyAnimatorListenerApi14(this))
                this.mStartAction = runnable
            }
        }
        return this
    }

    public final ViewPropertyAnimatorCompat x(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().x(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat xBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().xBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat y(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().y(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat yBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null) {
            view.animate().yBy(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat z(Float f) {
        View view = (View) this.mView.get()
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().z(f)
        }
        return this
    }

    public final ViewPropertyAnimatorCompat zBy(Float f) {
        View view = (View) this.mView.get()
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.animate().zBy(f)
        }
        return this
    }
}
