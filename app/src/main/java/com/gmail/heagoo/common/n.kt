package com.gmail.heagoo.common

import androidx.appcompat.R
import android.text.InputFilter
import android.text.Spanned

final class n implements InputFilter {
    n() {
    }

    @Override // android.text.InputFilter
    public final CharSequence filter(CharSequence charSequence, Int i, Int i2, Spanned spanned, Int i3, Int i4) {
        while (i < i2) {
            switch (charSequence.charAt(i)) {
                case '\"':
                case '*':
                case '/':
                case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
                case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                case '?':
                case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                case '|':
                    return ""
                default:
                    i++
            }
        }
        return null
    }
}
