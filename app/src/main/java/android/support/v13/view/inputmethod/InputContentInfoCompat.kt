package android.support.v13.view.inputmethod

import android.content.ClipDescription
import android.net.Uri
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.view.inputmethod.InputContentInfo

class InputContentInfoCompat {
    private final InputContentInfoCompatImpl mImpl

    @RequiresApi(25)
    final class InputContentInfoCompatApi25Impl implements InputContentInfoCompatImpl {

        @NonNull
        final InputContentInfo mObject

        InputContentInfoCompatApi25Impl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.mObject = InputContentInfo(uri, clipDescription, uri2)
        }

        InputContentInfoCompatApi25Impl(@NonNull Object obj) {
            this.mObject = (InputContentInfo) obj
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @NonNull
        public final Uri getContentUri() {
            return this.mObject.getContentUri()
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @NonNull
        public final ClipDescription getDescription() {
            return this.mObject.getDescription()
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @Nullable
        public final Object getInputContentInfo() {
            return this.mObject
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @Nullable
        public final Uri getLinkUri() {
            return this.mObject.getLinkUri()
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        public final Unit releasePermission() {
            this.mObject.releasePermission()
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        public final Unit requestPermission() {
            this.mObject.requestPermission()
        }
    }

    final class InputContentInfoCompatBaseImpl implements InputContentInfoCompatImpl {

        @NonNull
        private final Uri mContentUri

        @NonNull
        private final ClipDescription mDescription

        @Nullable
        private final Uri mLinkUri

        InputContentInfoCompatBaseImpl(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.mContentUri = uri
            this.mDescription = clipDescription
            this.mLinkUri = uri2
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @NonNull
        public final Uri getContentUri() {
            return this.mContentUri
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @NonNull
        public final ClipDescription getDescription() {
            return this.mDescription
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @Nullable
        public final Object getInputContentInfo() {
            return null
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        @Nullable
        public final Uri getLinkUri() {
            return this.mLinkUri
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        public final Unit releasePermission() {
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.InputContentInfoCompatImpl
        public final Unit requestPermission() {
        }
    }

    interface InputContentInfoCompatImpl {
        @NonNull
        Uri getContentUri()

        @NonNull
        ClipDescription getDescription()

        @Nullable
        Object getInputContentInfo()

        @Nullable
        Uri getLinkUri()

        Unit releasePermission()

        Unit requestPermission()
    }

    constructor(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        if (Build.VERSION.SDK_INT >= 25) {
            this.mImpl = InputContentInfoCompatApi25Impl(uri, clipDescription, uri2)
        } else {
            this.mImpl = InputContentInfoCompatBaseImpl(uri, clipDescription, uri2)
        }
    }

    private constructor(@NonNull InputContentInfoCompatImpl inputContentInfoCompatImpl) {
        this.mImpl = inputContentInfoCompatImpl
    }

    @Nullable
    fun wrap(@Nullable Object obj) {
        if (obj != null && Build.VERSION.SDK_INT >= 25) {
            return InputContentInfoCompat(InputContentInfoCompatApi25Impl(obj))
        }
        return null
    }

    @NonNull
    public final Uri getContentUri() {
        return this.mImpl.getContentUri()
    }

    @NonNull
    public final ClipDescription getDescription() {
        return this.mImpl.getDescription()
    }

    @Nullable
    public final Uri getLinkUri() {
        return this.mImpl.getLinkUri()
    }

    public final Unit releasePermission() {
        this.mImpl.releasePermission()
    }

    public final Unit requestPermission() {
        this.mImpl.requestPermission()
    }

    @Nullable
    public final Object unwrap() {
        return this.mImpl.getInputContentInfo()
    }
}
