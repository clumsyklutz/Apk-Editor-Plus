package android.support.v13.view.inputmethod

import android.content.ClipDescription
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.view.inputmethod.InputContentInfo

class InputConnectionCompat {
    private static val COMMIT_CONTENT_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT"
    private static val COMMIT_CONTENT_CONTENT_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI"
    private static val COMMIT_CONTENT_DESCRIPTION_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION"
    private static val COMMIT_CONTENT_FLAGS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS"
    private static val COMMIT_CONTENT_LINK_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI"
    private static val COMMIT_CONTENT_OPTS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS"
    private static val COMMIT_CONTENT_RESULT_RECEIVER = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER"
    public static val INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1

    public interface OnCommitContentListener {
        Boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, Int i, Bundle bundle)
    }

    @Deprecated
    constructor() {
    }

    fun commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfoCompat, Int i, @Nullable Bundle bundle) {
        Boolean z
        ClipDescription description = inputContentInfoCompat.getDescription()
        Array<String> contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo)
        Int length = contentMimeTypes.length
        Int i2 = 0
        while (true) {
            if (i2 >= length) {
                z = false
                break
            }
            if (description.hasMimeType(contentMimeTypes[i2])) {
                z = true
                break
            }
            i2++
        }
        if (!z) {
            return false
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle)
        }
        Bundle bundle2 = Bundle()
        bundle2.putParcelable(COMMIT_CONTENT_CONTENT_URI_KEY, inputContentInfoCompat.getContentUri())
        bundle2.putParcelable(COMMIT_CONTENT_DESCRIPTION_KEY, inputContentInfoCompat.getDescription())
        bundle2.putParcelable(COMMIT_CONTENT_LINK_URI_KEY, inputContentInfoCompat.getLinkUri())
        bundle2.putInt(COMMIT_CONTENT_FLAGS_KEY, i)
        bundle2.putParcelable(COMMIT_CONTENT_OPTS_KEY, bundle)
        return inputConnection.performPrivateCommand(COMMIT_CONTENT_ACTION, bundle2)
    }

    @NonNull
    fun createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull final OnCommitContentListener onCommitContentListener) {
        Boolean z = false
        if (inputConnection == null) {
            throw IllegalArgumentException("inputConnection must be non-null")
        }
        if (editorInfo == null) {
            throw IllegalArgumentException("editorInfo must be non-null")
        }
        if (onCommitContentListener == null) {
            throw IllegalArgumentException("onCommitContentListener must be non-null")
        }
        return Build.VERSION.SDK_INT >= 25 ? InputConnectionWrapper(inputConnection, z) { // from class: android.support.v13.view.inputmethod.InputConnectionCompat.1
            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public final Boolean commitContent(InputContentInfo inputContentInfo, Int i, Bundle bundle) {
                if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                    return true
                }
                return super.commitContent(inputContentInfo, i, bundle)
            }
        } : EditorInfoCompat.getContentMimeTypes(editorInfo).length != 0 ? InputConnectionWrapper(inputConnection, z) { // from class: android.support.v13.view.inputmethod.InputConnectionCompat.2
            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public final Boolean performPrivateCommand(String str, Bundle bundle) {
                if (InputConnectionCompat.handlePerformPrivateCommand(str, bundle, onCommitContentListener)) {
                    return true
                }
                return super.performPrivateCommand(str, bundle)
            }
        } : inputConnection
    }

    static Boolean handlePerformPrivateCommand(@Nullable String str, @NonNull Bundle bundle, @NonNull OnCommitContentListener onCommitContentListener) throws Throwable {
        ResultReceiver resultReceiver
        if (!TextUtils.equals(COMMIT_CONTENT_ACTION, str) || bundle == null) {
            return false
        }
        try {
            ResultReceiver resultReceiver2 = (ResultReceiver) bundle.getParcelable(COMMIT_CONTENT_RESULT_RECEIVER)
            try {
                Boolean zOnCommitContent = onCommitContentListener.onCommitContent(InputContentInfoCompat((Uri) bundle.getParcelable(COMMIT_CONTENT_CONTENT_URI_KEY), (ClipDescription) bundle.getParcelable(COMMIT_CONTENT_DESCRIPTION_KEY), (Uri) bundle.getParcelable(COMMIT_CONTENT_LINK_URI_KEY)), bundle.getInt(COMMIT_CONTENT_FLAGS_KEY), (Bundle) bundle.getParcelable(COMMIT_CONTENT_OPTS_KEY))
                if (resultReceiver2 != null) {
                    resultReceiver2.send(zOnCommitContent ? 1 : 0, null)
                }
                return zOnCommitContent
            } catch (Throwable th) {
                resultReceiver = resultReceiver2
                th = th
                if (resultReceiver != null) {
                    resultReceiver.send(0, null)
                }
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            resultReceiver = null
        }
    }
}
