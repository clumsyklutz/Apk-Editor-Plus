package android.support.v4.graphics.drawable

import android.support.annotation.RestrictTo
import androidx.versionedparcelable.b

@RestrictTo({RestrictTo.Scope.LIBRARY})
class IconCompatParcelizer extends androidx.core.graphics.drawable.IconCompatParcelizer {
    fun read(b bVar) {
        return androidx.core.graphics.drawable.IconCompatParcelizer.read(bVar)
    }

    fun write(IconCompat iconCompat, b bVar) {
        androidx.core.graphics.drawable.IconCompatParcelizer.write(iconCompat, bVar)
    }
}
