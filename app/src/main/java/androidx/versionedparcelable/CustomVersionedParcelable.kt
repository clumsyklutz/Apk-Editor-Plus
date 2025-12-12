package androidx.versionedparcelable

import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class CustomVersionedParcelable implements d {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun onPostParceling() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun onPreParceling(Boolean z) {
    }
}
