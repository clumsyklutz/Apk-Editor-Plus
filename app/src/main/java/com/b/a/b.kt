package com.b.a

import android.text.InputFilter
import android.text.Spanned

final class b implements InputFilter {
    b(a aVar) {
    }

    @Override // android.text.InputFilter
    public final CharSequence filter(CharSequence charSequence, Int i, Int i2, Spanned spanned, Int i3, Int i4) {
        while (i < i2) {
            Char cCharAt = charSequence.charAt(i)
            if (!((cCharAt < '0' || cCharAt > '9') ? (cCharAt < 'a' || cCharAt > 'f') ? cCharAt >= 'A' && cCharAt <= 'F' : true : true)) {
                return ""
            }
            i++
        }
        return null
    }
}
