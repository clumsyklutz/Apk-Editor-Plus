package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.core.content.ContextCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.ac.EditTextWithTip
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Closeable
import java.io.IOException
import java.io.RandomAccessFile
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

final class ei extends BaseExpandableListAdapter implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private String f1029a

    /* renamed from: b, reason: collision with root package name */
    private String f1030b
    private WeakReference d
    private WeakReference e
    private Array<Boolean> f
    private Array<Boolean> g
    private Int l
    private Int k = 0
    private Boolean m = false
    private String n = null
    private ArrayList c = ArrayList()
    private val h = HashMap()

    ei(WeakReference weakReference, ExpandableListView expandableListView, String str, List list, String str2) {
        this.d = weakReference
        this.e = WeakReference(expandableListView)
        this.f1029a = str + "/"
        this.f1030b = str2
        Iterator it = list.iterator()
        while (it.hasNext()) {
            this.c.add((String) it.next())
        }
        this.f = new Boolean[list.size()]
        this.g = new Boolean[list.size()]
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    static /* synthetic */ Boolean a(ei eiVar, Boolean z) {
        eiVar.m = true
        return true
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun d(Int i) throws Throwable {
        String str = (String) this.c.get(i)
        try {
            a(str, this.n)
            ((ApkInfoActivity) this.d.get()).a(str, (String) null)
            ((ExpandableListView) this.e.get()).collapseGroup(i)
            b(i)
            Toast.makeText((Context) this.d.get(), String.format(((ApkInfoActivity) this.d.get()).getString(R.string.str_replaced), this.f1030b), 0).show()
        } catch (IOException e) {
            Toast.makeText((Context) this.d.get(), e.getMessage(), 1).show()
        }
    }

    public final ArrayList a() {
        return this.c
    }

    public final Unit a(String str, String str2) throws Throwable {
        RandomAccessFile randomAccessFile
        try {
            randomAccessFile = RandomAccessFile(str, "rw")
            try {
                Array<Byte> bArr = new Byte[(Int) randomAccessFile.length()]
                Int i = 0
                while (true) {
                    Int i2 = randomAccessFile.read(bArr, i, bArr.length - i)
                    if (i2 <= 0) {
                        String strReplace = String(bArr, "UTF-8").replace(this.f1030b, str2)
                        randomAccessFile.setLength(0L)
                        randomAccessFile.write(strReplace.getBytes())
                        a(randomAccessFile)
                        return
                    }
                    i += i2
                }
            } catch (Throwable th) {
                th = th
                a(randomAccessFile)
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            randomAccessFile = null
        }
    }

    final Unit a(String str, List list) {
        synchronized (this.h) {
            this.h.put(str, list)
        }
    }

    public final Boolean a(Int i) {
        Boolean zContainsKey
        String str = (String) this.c.get(i)
        if (str == null) {
            return false
        }
        synchronized (this.h) {
            zContainsKey = this.h.containsKey(str)
        }
        return zContainsKey
    }

    public final String b() {
        return this.f1030b
    }

    public final Unit b(Int i) {
        String str = (String) this.c.get(i)
        if (str != null) {
            synchronized (this.h) {
                this.h.remove(str)
            }
        }
    }

    public final Unit c(Int i) {
        if (i < this.c.size()) {
            this.h.remove((String) this.c.remove(i))
            notifyDataSetChanged()
        }
    }

    @Override // android.widget.ExpandableListAdapter
    public final Object getChild(Int i, Int i2) {
        List list = (List) this.h.get((String) this.c.get(i))
        if (list == null || i2 >= list.size()) {
            return null
        }
        return list.get(i2)
    }

    @Override // android.widget.ExpandableListAdapter
    public final Long getChildId(Int i, Int i2) {
        return (i << 16) + i2
    }

    @Override // android.widget.ExpandableListAdapter
    @SuppressLint({"InflateParams"})
    public final View getChildView(Int i, Int i2, Boolean z, View view, ViewGroup viewGroup) {
        em emVar
        String str
        Int i3
        Int i4 = 0
        eh ehVar = (eh) getChild(i, i2)
        if (view == null) {
            view = ((LayoutInflater) ((ApkInfoActivity) this.d.get()).getSystemService("layout_inflater")).inflate(R.layout.item_matchedline, (ViewGroup) null)
            em emVar2 = em((Byte) 0)
            emVar2.f1035a = (TextView) view.findViewById(R.id.tv_line)
            view.setTag(emVar2)
            emVar = emVar2
        } else {
            emVar = (em) view.getTag()
        }
        if (ehVar != null) {
            Paint paint = Paint()
            paint.setTextSize(emVar.f1035a.getTextSize())
            if (this.k == 0) {
                this.k = emVar.f1035a.getWidth()
                this.l = (Int) paint.measureText(this.f1030b)
            }
            String str2 = ehVar.f1027a + ": "
            Int iMeasureText = (Int) paint.measureText(str2)
            if (this.k < ((Int) paint.measureText(str2 + ehVar.c)) && ehVar.f1028b > 0) {
                Int i5 = ((Int) paint.measureText(ehVar.c.substring(0, ehVar.f1028b))) > ((this.k - iMeasureText) - this.l) / 2 ? ehVar.f1028b - (((r5 * ehVar.f1028b) / r1) - 2) : 0
                i4 = i5 > ehVar.f1028b ? ehVar.f1028b : i5
            }
            Int length = str2.length() + ehVar.f1028b
            if (i4 > 0) {
                String str3 = str2 + "..." + ehVar.c.substring(i4)
                i3 = length - (i4 - 3)
                str = str3
            } else {
                str = str2 + ehVar.c
                i3 = length
            }
            SpannableString spannableString = SpannableString(str)
            spannableString.setSpan(ForegroundColorSpan(-16746497), i3, this.f1030b.length() + i3, 34)
            emVar.f1035a.setText(spannableString)
        }
        return view
    }

    @Override // android.widget.ExpandableListAdapter
    public final Int getChildrenCount(Int i) {
        List list = (List) this.h.get((String) this.c.get(i))
        if (list != null) {
            return list.size()
        }
        return 0
    }

    @Override // android.widget.ExpandableListAdapter
    public final Object getGroup(Int i) {
        return this.c.get(i)
    }

    @Override // android.widget.ExpandableListAdapter
    public final Int getGroupCount() {
        return this.c.size()
    }

    @Override // android.widget.ExpandableListAdapter
    public final Long getGroupId(Int i) {
        return i
    }

    @Override // android.widget.ExpandableListAdapter
    @SuppressLint({"InflateParams"})
    public final View getGroupView(Int i, Boolean z, View view, ViewGroup viewGroup) {
        el elVar
        String strSubstring = ((String) this.c.get(i)).substring(this.f1029a.length())
        if (view == null) {
            view = ((LayoutInflater) ((ApkInfoActivity) this.d.get()).getSystemService("layout_inflater")).inflate(R.layout.item_matchedfile, (ViewGroup) null)
            el elVar2 = el((Byte) 0)
            elVar2.f1033a = (TextView) view.findViewById(R.id.tv_filepath)
            elVar2.c = view.findViewById(R.id.menu_edit)
            elVar2.f1034b = view.findViewById(R.id.menu_replace)
            elVar2.e = (ImageView) view.findViewById(R.id.image_edit)
            elVar2.d = (ImageView) view.findViewById(R.id.image_replace)
            view.setTag(elVar2)
            elVar = elVar2
        } else {
            elVar = (el) view.getTag()
        }
        elVar.e.setImageResource(this.g[i] ? R.drawable.ic_eye_accent : R.drawable.ic_eye)
        elVar.d.setImageResource(this.f[i] ? R.drawable.ic_replace_accent : R.drawable.ic_replace)
        TextView textView = elVar.f1033a
        textView.setTypeface(null, 0)
        textView.setText(strSubstring)
        elVar.c.setTag(Integer.valueOf(i))
        elVar.c.setOnClickListener(this)
        elVar.f1034b.setTag(Integer.valueOf(i))
        elVar.f1034b.setOnClickListener(this)
        return view
    }

    @Override // android.widget.ExpandableListAdapter
    public final Boolean hasStableIds() {
        return true
    }

    @Override // android.widget.ExpandableListAdapter
    public final Boolean isChildSelectable(Int i, Int i2) {
        return true
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) throws Throwable {
        Intent intentA
        Int id = view.getId()
        if (id == R.id.menu_edit) {
            Integer num = (Integer) view.getTag()
            if (num.intValue() < this.c.size()) {
                if (!this.g[num.intValue()]) {
                    this.g[num.intValue()] = true
                    notifyDataSetChanged()
                }
                if (this.c.size() <= 100) {
                    intentA = a.a((Context) this.d.get(), this.c, num.intValue(), ((ApkInfoActivity) this.d.get()).l())
                } else {
                    ApkInfoActivity apkInfoActivity = (ApkInfoActivity) this.d.get()
                    intentA = a.a(apkInfoActivity, (String) this.c.get(num.intValue()), apkInfoActivity.l())
                }
                a.a(intentA, "searchString", this.f1030b)
                ((ApkInfoActivity) this.d.get()).startActivityForResult(intentA, 0)
                return
            }
            return
        }
        if (id == R.id.menu_replace) {
            Integer num2 = (Integer) view.getTag()
            if (!this.f[num2.intValue()]) {
                this.f[num2.intValue()] = true
                notifyDataSetChanged()
            }
            if (num2.intValue() < this.c.size()) {
                if (this.m) {
                    d(num2.intValue())
                    return
                }
                Int iIntValue = num2.intValue()
                Float f = ((ApkInfoActivity) this.d.get()).getResources().getDisplayMetrics().density
                Int i = (Int) ((18.0f * f) + 0.5f)
                Int i2 = (Int) ((20.0f * f) + 0.5f)
                Int i3 = (Int) ((f * 24.0f) + 0.5f)
                AlertDialog.Builder builder = new AlertDialog.Builder((Context) this.d.get())
                builder.setTitle(R.string.replace)
                LinearLayout linearLayout = LinearLayout((Context) this.d.get())
                linearLayout.setOrientation(1)
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2))
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
                layoutParams.setMargins(i3, (Int) ((12.0f * f) + 0.5f), i3, 0)
                TextView textView = TextView((ApkInfoActivity) this.d.get())
                textView.setText(String.format(((ApkInfoActivity) this.d.get()).getString(R.string.str_replace_with), this.f1030b))
                textView.setTextColor(ContextCompat.getColor((ApkInfoActivity) this.d.get(), a.a.b.a.k.mdTextSmall(a.a.b.a.k.a((ApkInfoActivity) this.d.get()))))
                textView.setTextSize(1, 12)
                textView.setLayoutParams(layoutParams)
                linearLayout.addView(textView)
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2)
                layoutParams2.setMargins(i2, 0, i2, 0)
                com.gmail.heagoo.apkeditor.ac.a aVar = new com.gmail.heagoo.apkeditor.ac.a((ApkInfoActivity) this.d.get(), "search_replace_with")
                EditTextWithTip editTextWithTip = EditTextWithTip((ApkInfoActivity) this.d.get())
                editTextWithTip.setTextColor(ContextCompat.getColor((ApkInfoActivity) this.d.get(), a.a.b.a.k.mdTextMedium(a.a.b.a.k.a((ApkInfoActivity) this.d.get()))))
                editTextWithTip.setTextSize(1, 14)
                editTextWithTip.setAdapter(aVar)
                editTextWithTip.setLayoutParams(layoutParams2)
                linearLayout.addView(editTextWithTip)
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2)
                layoutParams3.setMargins(i, 0, i, 0)
                CheckBox checkBox = CheckBox((Context) this.d.get())
                checkBox.setText(R.string.label_replace_with_same_setting)
                checkBox.setLayoutParams(layoutParams3)
                linearLayout.addView(checkBox)
                builder.setView(linearLayout)
                builder.setPositiveButton(android.R.string.ok, ej(this, editTextWithTip, checkBox, aVar, iIntValue))
                builder.setNegativeButton(android.R.string.cancel, ek(this))
                builder.show()
            }
        }
    }
}
