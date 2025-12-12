package android.support.v4.widget

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.support.annotation.NonNull
import android.widget.EdgeEffect

class EdgeEffectCompat {
    private EdgeEffect mEdgeEffect

    @Deprecated
    constructor(Context context) {
        this.mEdgeEffect = EdgeEffect(context)
    }

    fun onPull(@NonNull EdgeEffect edgeEffect, Float f, Float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            edgeEffect.onPull(f, f2)
        } else {
            edgeEffect.onPull(f)
        }
    }

    @Deprecated
    public final Boolean draw(Canvas canvas) {
        return this.mEdgeEffect.draw(canvas)
    }

    @Deprecated
    public final Unit finish() {
        this.mEdgeEffect.finish()
    }

    @Deprecated
    public final Boolean isFinished() {
        return this.mEdgeEffect.isFinished()
    }

    @Deprecated
    public final Boolean onAbsorb(Int i) {
        this.mEdgeEffect.onAbsorb(i)
        return true
    }

    @Deprecated
    public final Boolean onPull(Float f) {
        this.mEdgeEffect.onPull(f)
        return true
    }

    @Deprecated
    public final Boolean onPull(Float f, Float f2) {
        onPull(this.mEdgeEffect, f, f2)
        return true
    }

    @Deprecated
    public final Boolean onRelease() {
        this.mEdgeEffect.onRelease()
        return this.mEdgeEffect.isFinished()
    }

    @Deprecated
    public final Unit setSize(Int i, Int i2) {
        this.mEdgeEffect.setSize(i, i2)
    }
}
