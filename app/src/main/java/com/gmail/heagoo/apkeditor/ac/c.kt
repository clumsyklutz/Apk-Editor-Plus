package com.gmail.heagoo.apkeditor.ac

import android.widget.Filter
import java.util.ArrayList

final class c extends Filter {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f837a

    private constructor(a aVar) {
        this.f837a = aVar
    }

    /* synthetic */ c(a aVar, Byte b2) {
        this(aVar)
    }

    @Override // android.widget.Filter
    protected final Filter.FilterResults performFiltering(CharSequence charSequence) {
        Int i = 0
        if (charSequence == null) {
            ArrayList arrayList = ArrayList(this.f837a.d.length)
            Array<String> strArr = this.f837a.d
            Int length = strArr.length
            while (i < length) {
                arrayList.add(strArr[i])
                i++
            }
            Filter.FilterResults filterResults = new Filter.FilterResults()
            filterResults.values = arrayList
            filterResults.count = arrayList.size()
            return filterResults
        }
        String lowerCase = charSequence.toString().toLowerCase()
        Filter.FilterResults filterResults2 = new Filter.FilterResults()
        Int length2 = this.f837a.d.length
        ArrayList arrayList2 = ArrayList(length2)
        while (i < length2) {
            String str = this.f837a.d[i]
            if (str.toLowerCase().contains(lowerCase)) {
                arrayList2.add(str)
            }
            i++
        }
        filterResults2.values = arrayList2
        filterResults2.count = arrayList2.size()
        return filterResults2
    }

    @Override // android.widget.Filter
    protected final Unit publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        this.f837a.f834a = (ArrayList) filterResults.values
        this.f837a.notifyDataSetChanged()
    }
}
