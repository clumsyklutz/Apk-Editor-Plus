package androidx.core.graphics.drawable

import android.content.res.ColorStateList
import android.support.annotation.RestrictTo
import android.support.v4.graphics.drawable.IconCompat
import androidx.versionedparcelable.b

@RestrictTo({RestrictTo.Scope.LIBRARY})
class IconCompatParcelizer {
    fun read(b bVar) {
        IconCompat iconCompat = IconCompat()
        iconCompat.mType = bVar.b(iconCompat.mType, 1)
        iconCompat.mData = bVar.b(iconCompat.mData, 2)
        iconCompat.mParcelable = bVar.b(iconCompat.mParcelable, 3)
        iconCompat.mInt1 = bVar.b(iconCompat.mInt1, 4)
        iconCompat.mInt2 = bVar.b(iconCompat.mInt2, 5)
        iconCompat.mTintList = (ColorStateList) bVar.b(iconCompat.mTintList, 6)
        iconCompat.mTintModeStr = bVar.b(iconCompat.mTintModeStr, 7)
        iconCompat.onPostParceling()
        return iconCompat
    }

    fun write(IconCompat iconCompat, b bVar) {
        iconCompat.onPreParceling(false)
        bVar.a(iconCompat.mType, 1)
        bVar.a(iconCompat.mData, 2)
        bVar.a(iconCompat.mParcelable, 3)
        bVar.a(iconCompat.mInt1, 4)
        bVar.a(iconCompat.mInt2, 5)
        bVar.a(iconCompat.mTintList, 6)
        bVar.a(iconCompat.mTintModeStr, 7)
    }
}
