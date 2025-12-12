package android.support.v7.widget

import android.view.View
import android.view.ViewParent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

class AppCompatHintHelper {
    private constructor() {
    }

    static InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection != null && editorInfo.hintText == null) {
            ViewParent parent = view.getParent()
            while (true) {
                if (!(parent is View)) {
                    break
                }
                if (parent is WithHint) {
                    editorInfo.hintText = ((WithHint) parent).getHint()
                    break
                }
                parent = parent.getParent()
            }
        }
        return inputConnection
    }
}
