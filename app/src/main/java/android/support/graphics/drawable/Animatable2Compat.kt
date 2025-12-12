package android.support.graphics.drawable

import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi

public interface Animatable2Compat extends Animatable {

    abstract class AnimationCallback {
        Animatable2.AnimationCallback mPlatformCallback

        @RequiresApi(23)
        Animatable2.AnimationCallback getPlatformCallback() {
            if (this.mPlatformCallback == null) {
                this.mPlatformCallback = new Animatable2.AnimationCallback() { // from class: android.support.graphics.drawable.Animatable2Compat.AnimationCallback.1
                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    fun onAnimationEnd(Drawable drawable) {
                        AnimationCallback.this.onAnimationEnd(drawable)
                    }

                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    fun onAnimationStart(Drawable drawable) {
                        AnimationCallback.this.onAnimationStart(drawable)
                    }
                }
            }
            return this.mPlatformCallback
        }

        fun onAnimationEnd(Drawable drawable) {
        }

        fun onAnimationStart(Drawable drawable) {
        }
    }

    Unit clearAnimationCallbacks()

    Unit registerAnimationCallback(@NonNull AnimationCallback animationCallback)

    Boolean unregisterAnimationCallback(@NonNull AnimationCallback animationCallback)
}
