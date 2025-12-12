package com.gmail.heagoo.apkeditor.b

import androidx.fragment.app.FragmentTransaction
import androidx.core.view.InputDeviceCompat
import java.util.List

class a {

    /* renamed from: a, reason: collision with root package name */
    Short f867a

    /* renamed from: b, reason: collision with root package name */
    Int f868b
    Int c

    fun a(List list, Int i) {
        Int i2 = 0
        while (true) {
            Int i3 = i2
            if (i3 >= list.size()) {
                return null
            }
            a aVar = (a) list.get(i3)
            if (aVar.f867a == i) {
                return aVar
            }
            i2 = i3 + 1
        }
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("type: ")
        String str = ""
        switch (this.f867a) {
            case 0:
                str = "kDexTypeHeaderItem"
                break
            case 1:
                str = "kDexTypeStringIdItem"
                break
            case 2:
                str = "kDexTypeTypeIdItem"
                break
            case 3:
                str = "kDexTypeProtoIdItem"
                break
            case 4:
                str = "kDexTypeFieldIdItem"
                break
            case 5:
                str = "kDexTypeMethodIdItem"
                break
            case 6:
                str = "kDexTypeClassDefItem"
                break
            case 7:
                str = "kDexTypeCallSiteIdItem"
                break
            case 8:
                str = "kDexTypeMethodHandleItem"
                break
            case 4096:
                str = "kDexTypeMapList"
                break
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /* 4097 */:
                str = "kDexTypeTypeList"
                break
            case InputDeviceCompat.SOURCE_TOUCHSCREEN /* 4098 */:
                str = "kDexTypeAnnotationSetRefList"
                break
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /* 4099 */:
                str = "kDexTypeAnnotationSetItem"
                break
            case 8192:
                str = "kDexTypeClassDataItem"
                break
            case 8193:
                str = "kDexTypeCodeItem"
                break
            case 8194:
                str = "kDexTypeStringDataItem"
                break
            case 8195:
                str = "kDexTypeDebugInfoItem"
                break
            case 8196:
                str = "kDexTypeAnnotationItem"
                break
            case 8197:
                str = "kDexTypeEncodedArrayItem"
                break
            case 8198:
                str = "kDexTypeAnnotationsDirectoryItem"
                break
        }
        sb.append(str)
        sb.append("; ")
        sb.append("size: " + this.f868b + "; ")
        sb.append("offset: " + this.c)
        return sb.toString()
    }
}
