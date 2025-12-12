package android.support.v4.content

import android.content.Intent
import android.os.Build
import android.support.annotation.NonNull

class IntentCompat {
    public static val CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER"
    public static val EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT"
    public static val EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK"

    private constructor() {
    }

    @NonNull
    fun makeMainSelectorActivity(@NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 15) {
            return Intent.makeMainSelectorActivity(str, str2)
        }
        Intent intent = Intent(str)
        intent.addCategory(str2)
        return intent
    }
}
