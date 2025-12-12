package android.support.graphics.drawable

import android.animation.TypeEvaluator
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ArgbEvaluator implements TypeEvaluator {
    private static val sInstance = ArgbEvaluator()

    fun getInstance() {
        return sInstance
    }

    @Override // android.animation.TypeEvaluator
    fun evaluate(Float f, Object obj, Object obj2) {
        Int iIntValue = ((Integer) obj).intValue()
        Float f2 = (iIntValue >>> 24) / 255.0f
        Int iIntValue2 = ((Integer) obj2).intValue()
        Float fPow = (Float) Math.pow(((iIntValue >> 16) & 255) / 255.0f, 2.2d)
        Float fPow2 = (Float) Math.pow(((iIntValue >> 8) & 255) / 255.0f, 2.2d)
        Float fPow3 = (Float) Math.pow((iIntValue & 255) / 255.0f, 2.2d)
        Float fPow4 = (Float) Math.pow(((iIntValue2 >> 16) & 255) / 255.0f, 2.2d)
        Float fPow5 = (Float) Math.pow(((iIntValue2 >> 8) & 255) / 255.0f, 2.2d)
        Float fPow6 = fPow3 + ((((Float) Math.pow((iIntValue2 & 255) / 255.0f, 2.2d)) - fPow3) * f)
        Float fPow7 = ((Float) Math.pow(fPow + ((fPow4 - fPow) * f), 0.45454545454545453d)) * 255.0f
        Float fPow8 = ((Float) Math.pow(fPow2 + ((fPow5 - fPow2) * f), 0.45454545454545453d)) * 255.0f
        return Integer.valueOf(Math.round(((Float) Math.pow(fPow6, 0.45454545454545453d)) * 255.0f) | (Math.round((f2 + ((((iIntValue2 >>> 24) / 255.0f) - f2) * f)) * 255.0f) << 24) | (Math.round(fPow7) << 16) | (Math.round(fPow8) << 8))
    }
}
