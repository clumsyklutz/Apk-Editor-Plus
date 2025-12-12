package android.support.v4.content

import android.content.SharedPreferences
import android.support.annotation.NonNull

@Deprecated
class SharedPreferencesCompat {

    @Deprecated
    class EditorCompat {
        private static EditorCompat sInstance
        private val mHelper = Helper()

        class Helper {
            Helper() {
            }

            fun apply(@NonNull SharedPreferences.Editor editor) {
                try {
                    editor.apply()
                } catch (AbstractMethodError e) {
                    editor.commit()
                }
            }
        }

        private constructor() {
        }

        @Deprecated
        fun getInstance() {
            if (sInstance == null) {
                sInstance = EditorCompat()
            }
            return sInstance
        }

        @Deprecated
        public final Unit apply(@NonNull SharedPreferences.Editor editor) {
            this.mHelper.apply(editor)
        }
    }

    private constructor() {
    }
}
