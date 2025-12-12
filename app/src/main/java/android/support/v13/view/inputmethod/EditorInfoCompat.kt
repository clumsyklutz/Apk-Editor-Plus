package android.support.v13.view.inputmethod

import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.inputmethod.EditorInfo

class EditorInfoCompat {
    private static val CONTENT_MIME_TYPES_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES"
    private static final Array<String> EMPTY_STRING_ARRAY = new String[0]
    public static val IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE
    public static val IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216

    @Deprecated
    constructor() {
    }

    @NonNull
    public static Array<String> getContentMimeTypes(EditorInfo editorInfo) {
        Array<String> stringArray
        if (Build.VERSION.SDK_INT < 25) {
            return (editorInfo.extras == null || (stringArray = editorInfo.extras.getStringArray(CONTENT_MIME_TYPES_KEY)) == null) ? EMPTY_STRING_ARRAY : stringArray
        }
        Array<String> strArr = editorInfo.contentMimeTypes
        return strArr != null ? strArr : EMPTY_STRING_ARRAY
    }

    fun setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable Array<String> strArr) {
        if (Build.VERSION.SDK_INT >= 25) {
            editorInfo.contentMimeTypes = strArr
            return
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = Bundle()
        }
        editorInfo.extras.putStringArray(CONTENT_MIME_TYPES_KEY, strArr)
    }
}
