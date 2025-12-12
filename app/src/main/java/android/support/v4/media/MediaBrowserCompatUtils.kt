package android.support.v4.media

import android.os.Bundle
import android.support.annotation.RestrictTo
import android.support.v7.widget.ActivityChooserView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MediaBrowserCompatUtils {
    fun areSameOptions(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true
        }
        return bundle == null ? bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1 : bundle2 == null ? bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) && bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)
    }

    fun hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        Int i
        Int i2
        Int i3
        Int i4 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        Int i5 = bundle == null ? -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1)
        Int i6 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1)
        Int i7 = bundle == null ? -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)
        Int i8 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)
        if (i5 == -1 || i7 == -1) {
            i = Integer.MAX_VALUE
            i2 = 0
        } else {
            Int i9 = i5 * i7
            Int i10 = (i9 + i7) - 1
            i2 = i9
            i = i10
        }
        if (i6 == -1 || i8 == -1) {
            i3 = 0
        } else {
            i3 = i8 * i6
            i4 = (i3 + i8) - 1
        }
        if (i2 > i3 || i3 > i) {
            return i2 <= i4 && i4 <= i
        }
        return true
    }
}
