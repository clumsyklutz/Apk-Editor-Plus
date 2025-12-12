package android.support.v7.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff

class TintInfo {
    public Boolean mHasTintList
    public Boolean mHasTintMode
    public ColorStateList mTintList
    public PorterDuff.Mode mTintMode

    TintInfo() {
    }

    Unit clear() {
        this.mTintList = null
        this.mHasTintList = false
        this.mTintMode = null
        this.mHasTintMode = false
    }
}
