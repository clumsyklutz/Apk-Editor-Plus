package android.support.v4.widget

import android.graphics.Rect
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.appcompat.R
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator

class FocusStrategy {

    public interface BoundsAdapter {
        Unit obtainBounds(Object obj, Rect rect)
    }

    public interface CollectionAdapter {
        Object get(Object obj, Int i)

        Int size(Object obj)
    }

    class SequentialComparator implements Comparator {
        private final BoundsAdapter mAdapter
        private final Boolean mIsLayoutRtl
        private val mTemp1 = Rect()
        private val mTemp2 = Rect()

        SequentialComparator(Boolean z, BoundsAdapter boundsAdapter) {
            this.mIsLayoutRtl = z
            this.mAdapter = boundsAdapter
        }

        @Override // java.util.Comparator
        fun compare(Object obj, Object obj2) {
            Rect rect = this.mTemp1
            Rect rect2 = this.mTemp2
            this.mAdapter.obtainBounds(obj, rect)
            this.mAdapter.obtainBounds(obj2, rect2)
            if (rect.top < rect2.top) {
                return -1
            }
            if (rect.top > rect2.top) {
                return 1
            }
            if (rect.left < rect2.left) {
                return this.mIsLayoutRtl ? 1 : -1
            }
            if (rect.left > rect2.left) {
                return !this.mIsLayoutRtl ? 1 : -1
            }
            if (rect.bottom < rect2.bottom) {
                return -1
            }
            if (rect.bottom > rect2.bottom) {
                return 1
            }
            if (rect.right < rect2.right) {
                return this.mIsLayoutRtl ? 1 : -1
            }
            if (rect.right > rect2.right) {
                return !this.mIsLayoutRtl ? 1 : -1
            }
            return 0
        }
    }

    private constructor() {
    }

    private fun beamBeats(Int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        Boolean zBeamsOverlap = beamsOverlap(i, rect, rect2)
        if (beamsOverlap(i, rect, rect3) || !zBeamsOverlap) {
            return false
        }
        return !isToDirectionOf(i, rect, rect3) || i == 17 || i == 66 || majorAxisDistance(i, rect, rect2) < majorAxisDistanceToFarEdge(i, rect, rect3)
    }

    private fun beamsOverlap(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        switch (i) {
            case 17:
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return rect2.bottom >= rect.top && rect2.top <= rect.bottom
            case 33:
            case 130:
                return rect2.right >= rect.left && rect2.left <= rect.right
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object findNextFocusInAbsoluteDirection(@android.support.annotation.NonNull java.lang.Object r7, @android.support.annotation.NonNull android.support.v4.widget.FocusStrategy.CollectionAdapter r8, @android.support.annotation.NonNull android.support.v4.widget.FocusStrategy.BoundsAdapter r9, @android.support.annotation.Nullable java.lang.Object r10, @android.support.annotation.NonNull android.graphics.Rect r11, Int r12) {
        /*
            r0 = 0
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>(r11)
            switch(r12) {
                case 17: goto L11
                case 33: goto L49
                case 66: goto L3e
                case 130: goto L53
                default: goto L9
            }
        L9:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."
            r0.<init>(r1)
            throw r0
        L11:
            Int r1 = r11.width()
            Int r1 = r1 + 1
            r3.offset(r1, r0)
        L1a:
            r1 = 0
            Int r4 = r8.size(r7)
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>()
            r2 = r0
        L25:
            if (r2 >= r4) goto L5e
            java.lang.Object r0 = r8.get(r7, r2)
            if (r0 == r10) goto L5f
            r9.obtainBounds(r0, r5)
            Boolean r6 = isBetterCandidate(r12, r11, r5, r3)
            if (r6 == 0) goto L5f
            r3.set(r5)
        L39:
            Int r1 = r2 + 1
            r2 = r1
            r1 = r0
            goto L25
        L3e:
            Int r1 = r11.width()
            Int r1 = r1 + 1
            Int r1 = -r1
            r3.offset(r1, r0)
            goto L1a
        L49:
            Int r1 = r11.height()
            Int r1 = r1 + 1
            r3.offset(r0, r1)
            goto L1a
        L53:
            Int r1 = r11.height()
            Int r1 = r1 + 1
            Int r1 = -r1
            r3.offset(r0, r1)
            goto L1a
        L5e:
            return r1
        L5f:
            r0 = r1
            goto L39
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.FocusStrategy.findNextFocusInAbsoluteDirection(java.lang.Object, android.support.v4.widget.FocusStrategy$CollectionAdapter, android.support.v4.widget.FocusStrategy$BoundsAdapter, java.lang.Object, android.graphics.Rect, Int):java.lang.Object")
    }

    fun findNextFocusInRelativeDirection(@NonNull Object obj, @NonNull CollectionAdapter collectionAdapter, @NonNull BoundsAdapter boundsAdapter, @Nullable Object obj2, Int i, Boolean z, Boolean z2) {
        Int size = collectionAdapter.size(obj)
        ArrayList arrayList = ArrayList(size)
        for (Int i2 = 0; i2 < size; i2++) {
            arrayList.add(collectionAdapter.get(obj, i2))
        }
        Collections.sort(arrayList, SequentialComparator(z, boundsAdapter))
        switch (i) {
            case 1:
                return getPreviousFocusable(obj2, arrayList, z2)
            case 2:
                return getNextFocusable(obj2, arrayList, z2)
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.")
        }
    }

    private fun getNextFocusable(Object obj, ArrayList arrayList, Boolean z) {
        Int size = arrayList.size()
        Int iLastIndexOf = (obj == null ? -1 : arrayList.lastIndexOf(obj)) + 1
        if (iLastIndexOf < size) {
            return arrayList.get(iLastIndexOf)
        }
        if (!z || size <= 0) {
            return null
        }
        return arrayList.get(0)
    }

    private fun getPreviousFocusable(Object obj, ArrayList arrayList, Boolean z) {
        Int size = arrayList.size()
        Int iIndexOf = (obj == null ? size : arrayList.indexOf(obj)) - 1
        if (iIndexOf >= 0) {
            return arrayList.get(iIndexOf)
        }
        if (!z || size <= 0) {
            return null
        }
        return arrayList.get(size - 1)
    }

    private fun getWeightedDistanceFor(Int i, Int i2) {
        return (i * 13 * i) + (i2 * i2)
    }

    private fun isBetterCandidate(Int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        if (!isCandidate(rect, rect2, i)) {
            return false
        }
        if (isCandidate(rect, rect3, i) && !beamBeats(i, rect, rect2, rect3)) {
            return !beamBeats(i, rect, rect3, rect2) && getWeightedDistanceFor(majorAxisDistance(i, rect, rect2), minorAxisDistance(i, rect, rect2)) < getWeightedDistanceFor(majorAxisDistance(i, rect, rect3), minorAxisDistance(i, rect, rect3))
        }
        return true
    }

    private fun isCandidate(@NonNull Rect rect, @NonNull Rect rect2, Int i) {
        switch (i) {
            case 17:
                return (rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left
            case 33:
                return (rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return (rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right
            case 130:
                return (rect.top < rect2.top || rect.bottom <= rect2.top) && rect.bottom < rect2.bottom
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    private fun isToDirectionOf(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        switch (i) {
            case 17:
                return rect.left >= rect2.right
            case 33:
                return rect.top >= rect2.bottom
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return rect.right <= rect2.left
            case 130:
                return rect.bottom <= rect2.top
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    private fun majorAxisDistance(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(0, majorAxisDistanceRaw(i, rect, rect2))
    }

    private fun majorAxisDistanceRaw(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        switch (i) {
            case 17:
                return rect.left - rect2.right
            case 33:
                return rect.top - rect2.bottom
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return rect2.left - rect.right
            case 130:
                return rect2.top - rect.bottom
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    private fun majorAxisDistanceToFarEdge(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(1, majorAxisDistanceToFarEdgeRaw(i, rect, rect2))
    }

    private fun majorAxisDistanceToFarEdgeRaw(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        switch (i) {
            case 17:
                return rect.left - rect2.left
            case 33:
                return rect.top - rect2.top
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return rect2.right - rect.right
            case 130:
                return rect2.bottom - rect.bottom
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    private fun minorAxisDistance(Int i, @NonNull Rect rect, @NonNull Rect rect2) {
        switch (i) {
            case 17:
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                return Math.abs((rect.top + (rect.height() / 2)) - (rect2.top + (rect2.height() / 2)))
            case 33:
            case 130:
                return Math.abs((rect.left + (rect.width() / 2)) - (rect2.left + (rect2.width() / 2)))
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }
}
