package android.support.v4.view.accessibility

import android.os.Build
import android.os.Parcelable
import android.support.annotation.NonNull
import android.view.View
import android.view.accessibility.AccessibilityRecord
import java.util.List

class AccessibilityRecordCompat {
    private final AccessibilityRecord mRecord

    @Deprecated
    constructor(Object obj) {
        this.mRecord = (AccessibilityRecord) obj
    }

    fun getMaxScrollX(AccessibilityRecord accessibilityRecord) {
        if (Build.VERSION.SDK_INT >= 15) {
            return accessibilityRecord.getMaxScrollX()
        }
        return 0
    }

    fun getMaxScrollY(AccessibilityRecord accessibilityRecord) {
        if (Build.VERSION.SDK_INT >= 15) {
            return accessibilityRecord.getMaxScrollY()
        }
        return 0
    }

    @Deprecated
    fun obtain() {
        return AccessibilityRecordCompat(AccessibilityRecord.obtain())
    }

    @Deprecated
    fun obtain(AccessibilityRecordCompat accessibilityRecordCompat) {
        return AccessibilityRecordCompat(AccessibilityRecord.obtain(accessibilityRecordCompat.mRecord))
    }

    fun setMaxScrollX(AccessibilityRecord accessibilityRecord, Int i) {
        if (Build.VERSION.SDK_INT >= 15) {
            accessibilityRecord.setMaxScrollX(i)
        }
    }

    fun setMaxScrollY(AccessibilityRecord accessibilityRecord, Int i) {
        if (Build.VERSION.SDK_INT >= 15) {
            accessibilityRecord.setMaxScrollY(i)
        }
    }

    fun setSource(@NonNull AccessibilityRecord accessibilityRecord, View view, Int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            accessibilityRecord.setSource(view, i)
        }
    }

    @Deprecated
    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && getClass() == obj.getClass()) {
            AccessibilityRecordCompat accessibilityRecordCompat = (AccessibilityRecordCompat) obj
            return this.mRecord == null ? accessibilityRecordCompat.mRecord == null : this.mRecord.equals(accessibilityRecordCompat.mRecord)
        }
        return false
    }

    @Deprecated
    fun getAddedCount() {
        return this.mRecord.getAddedCount()
    }

    @Deprecated
    fun getBeforeText() {
        return this.mRecord.getBeforeText()
    }

    @Deprecated
    fun getClassName() {
        return this.mRecord.getClassName()
    }

    @Deprecated
    fun getContentDescription() {
        return this.mRecord.getContentDescription()
    }

    @Deprecated
    fun getCurrentItemIndex() {
        return this.mRecord.getCurrentItemIndex()
    }

    @Deprecated
    fun getFromIndex() {
        return this.mRecord.getFromIndex()
    }

    @Deprecated
    fun getImpl() {
        return this.mRecord
    }

    @Deprecated
    fun getItemCount() {
        return this.mRecord.getItemCount()
    }

    @Deprecated
    fun getMaxScrollX() {
        return getMaxScrollX(this.mRecord)
    }

    @Deprecated
    fun getMaxScrollY() {
        return getMaxScrollY(this.mRecord)
    }

    @Deprecated
    fun getParcelableData() {
        return this.mRecord.getParcelableData()
    }

    @Deprecated
    fun getRemovedCount() {
        return this.mRecord.getRemovedCount()
    }

    @Deprecated
    fun getScrollX() {
        return this.mRecord.getScrollX()
    }

    @Deprecated
    fun getScrollY() {
        return this.mRecord.getScrollY()
    }

    @Deprecated
    fun getSource() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(this.mRecord.getSource())
    }

    @Deprecated
    fun getText() {
        return this.mRecord.getText()
    }

    @Deprecated
    fun getToIndex() {
        return this.mRecord.getToIndex()
    }

    @Deprecated
    fun getWindowId() {
        return this.mRecord.getWindowId()
    }

    @Deprecated
    fun hashCode() {
        if (this.mRecord == null) {
            return 0
        }
        return this.mRecord.hashCode()
    }

    @Deprecated
    fun isChecked() {
        return this.mRecord.isChecked()
    }

    @Deprecated
    fun isEnabled() {
        return this.mRecord.isEnabled()
    }

    @Deprecated
    fun isFullScreen() {
        return this.mRecord.isFullScreen()
    }

    @Deprecated
    fun isPassword() {
        return this.mRecord.isPassword()
    }

    @Deprecated
    fun isScrollable() {
        return this.mRecord.isScrollable()
    }

    @Deprecated
    fun recycle() {
        this.mRecord.recycle()
    }

    @Deprecated
    fun setAddedCount(Int i) {
        this.mRecord.setAddedCount(i)
    }

    @Deprecated
    fun setBeforeText(CharSequence charSequence) {
        this.mRecord.setBeforeText(charSequence)
    }

    @Deprecated
    fun setChecked(Boolean z) {
        this.mRecord.setChecked(z)
    }

    @Deprecated
    fun setClassName(CharSequence charSequence) {
        this.mRecord.setClassName(charSequence)
    }

    @Deprecated
    fun setContentDescription(CharSequence charSequence) {
        this.mRecord.setContentDescription(charSequence)
    }

    @Deprecated
    fun setCurrentItemIndex(Int i) {
        this.mRecord.setCurrentItemIndex(i)
    }

    @Deprecated
    fun setEnabled(Boolean z) {
        this.mRecord.setEnabled(z)
    }

    @Deprecated
    fun setFromIndex(Int i) {
        this.mRecord.setFromIndex(i)
    }

    @Deprecated
    fun setFullScreen(Boolean z) {
        this.mRecord.setFullScreen(z)
    }

    @Deprecated
    fun setItemCount(Int i) {
        this.mRecord.setItemCount(i)
    }

    @Deprecated
    fun setMaxScrollX(Int i) {
        setMaxScrollX(this.mRecord, i)
    }

    @Deprecated
    fun setMaxScrollY(Int i) {
        setMaxScrollY(this.mRecord, i)
    }

    @Deprecated
    fun setParcelableData(Parcelable parcelable) {
        this.mRecord.setParcelableData(parcelable)
    }

    @Deprecated
    fun setPassword(Boolean z) {
        this.mRecord.setPassword(z)
    }

    @Deprecated
    fun setRemovedCount(Int i) {
        this.mRecord.setRemovedCount(i)
    }

    @Deprecated
    fun setScrollX(Int i) {
        this.mRecord.setScrollX(i)
    }

    @Deprecated
    fun setScrollY(Int i) {
        this.mRecord.setScrollY(i)
    }

    @Deprecated
    fun setScrollable(Boolean z) {
        this.mRecord.setScrollable(z)
    }

    @Deprecated
    fun setSource(View view) {
        this.mRecord.setSource(view)
    }

    @Deprecated
    fun setSource(View view, Int i) {
        setSource(this.mRecord, view, i)
    }

    @Deprecated
    fun setToIndex(Int i) {
        this.mRecord.setToIndex(i)
    }
}
