package android.support.v7.text

import android.content.Context
import android.graphics.Rect
import android.support.annotation.RestrictTo
import android.text.method.TransformationMethod
import android.view.View
import java.util.Locale

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class AllCapsTransformationMethod implements TransformationMethod {
    private Locale mLocale

    constructor(Context context) {
        this.mLocale = context.getResources().getConfiguration().locale
    }

    @Override // android.text.method.TransformationMethod
    fun getTransformation(CharSequence charSequence, View view) {
        if (charSequence != null) {
            return charSequence.toString().toUpperCase(this.mLocale)
        }
        return null
    }

    @Override // android.text.method.TransformationMethod
    fun onFocusChanged(View view, CharSequence charSequence, Boolean z, Int i, Rect rect) {
    }
}
