package android.support.v4.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RestrictTo
import android.view.PointerIcon

class PointerIconCompat {
    public static val TYPE_ALIAS = 1010
    public static val TYPE_ALL_SCROLL = 1013
    public static val TYPE_ARROW = 1000
    public static val TYPE_CELL = 1006
    public static val TYPE_CONTEXT_MENU = 1001
    public static val TYPE_COPY = 1011
    public static val TYPE_CROSSHAIR = 1007
    public static val TYPE_DEFAULT = 1000
    public static val TYPE_GRAB = 1020
    public static val TYPE_GRABBING = 1021
    public static val TYPE_HAND = 1002
    public static val TYPE_HELP = 1003
    public static val TYPE_HORIZONTAL_DOUBLE_ARROW = 1014
    public static val TYPE_NO_DROP = 1012
    public static val TYPE_NULL = 0
    public static val TYPE_TEXT = 1008
    public static val TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW = 1017
    public static val TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW = 1016
    public static val TYPE_VERTICAL_DOUBLE_ARROW = 1015
    public static val TYPE_VERTICAL_TEXT = 1009
    public static val TYPE_WAIT = 1004
    public static val TYPE_ZOOM_IN = 1018
    public static val TYPE_ZOOM_OUT = 1019
    private Object mPointerIcon

    private constructor(Object obj) {
        this.mPointerIcon = obj
    }

    fun create(Bitmap bitmap, Float f, Float f2) {
        return Build.VERSION.SDK_INT >= 24 ? PointerIconCompat(PointerIcon.create(bitmap, f, f2)) : PointerIconCompat(null)
    }

    fun getSystemIcon(Context context, Int i) {
        return Build.VERSION.SDK_INT >= 24 ? PointerIconCompat(PointerIcon.getSystemIcon(context, i)) : PointerIconCompat(null)
    }

    fun load(Resources resources, Int i) {
        return Build.VERSION.SDK_INT >= 24 ? PointerIconCompat(PointerIcon.load(resources, i)) : PointerIconCompat(null)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final Object getPointerIcon() {
        return this.mPointerIcon
    }
}
