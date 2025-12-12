package com.gmail.heagoo.sqliteutil.a

import a.a.b.a.k
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import android.view.MotionEvent
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.util.ArrayList
import java.util.List

class b implements View.OnClickListener, View.OnTouchListener {

    /* renamed from: a, reason: collision with root package name */
    private Context f1565a

    /* renamed from: b, reason: collision with root package name */
    private TableLayout f1566b
    private ArrayList c
    private List d
    private TableRow e
    private com.gmail.heagoo.sqliteutil.a f = null
    private c g
    private TableLayout.LayoutParams h
    private TableRow.LayoutParams i
    private Array<TableRow> j
    private Array<View> k
    private Boolean l
    private Int m
    private Int n
    private Int o
    private Int p
    private Int q
    private Int r

    constructor(Context context, com.gmail.heagoo.sqliteutil.a aVar, TableLayout tableLayout, c cVar, Boolean z) {
        this.m = -1
        this.n = -13421773
        this.o = -1445124
        this.p = -3355444
        this.q = -1
        this.r = -8409217
        this.f1565a = context
        this.f1566b = tableLayout
        this.g = cVar
        if (z) {
            this.m = -13421773
            this.n = -3355444
            this.o = ViewCompat.MEASURED_STATE_MASK
            this.p = -8355712
            this.q = -1
            this.r = -8409217
        }
    }

    public final Unit a() {
        this.h = new TableLayout.LayoutParams(-2, -2)
        this.i = new TableRow.LayoutParams(-2, -2)
        this.i.setMargins(8, 0, 8, 0)
        Int size = this.d.size()
        Int size2 = this.c.size()
        this.j = new TableRow[size]
        Array<TextView> textViewArr = new TextView[size2]
        this.k = new View[size]
        for (Int i = 0; i < size; i++) {
            List list = (List) this.d.get(i)
            this.j[i] = TableRow(this.f1565a)
            this.j[i].setId(i)
            for (Int i2 = 0; i2 < size2; i2++) {
                textViewArr[i2] = TextView(this.f1565a)
                textViewArr[i2].setTextSize(1, 12.0f)
                textViewArr[i2].setTextColor(ContextCompat.getColor(this.f1565a, k.mdTextMedium(k.a(this.f1565a))))
                textViewArr[i2].setText((CharSequence) list.get(i2))
            }
            for (Int i3 = 0; i3 < size2; i3++) {
                this.j[i].addView(textViewArr[i3], i3, this.i)
            }
            this.k[i] = View(this.f1565a)
            this.k[i].setBackgroundColor(ContextCompat.getColor(this.f1565a, k.mdTextSmall(k.a(this.f1565a))))
        }
    }

    public final Unit a(ArrayList arrayList) {
        this.c = arrayList
    }

    public final Unit a(List list) {
        this.d = list
    }

    public final Unit b() {
        this.f1566b.removeAllViews()
        this.l = true
        this.e = TableRow(this.f1565a)
        for (Int i = 0; i < this.c.size(); i++) {
            TextView textView = TextView(this.f1565a)
            textView.setTextSize(1, 12.0f)
            textView.setTextColor(ContextCompat.getColor(this.f1565a, k.mdTextMedium(k.a(this.f1565a))))
            textView.setText((CharSequence) this.c.get(i))
            this.e.addView(textView, this.i)
        }
        this.e.setBackgroundColor(ContextCompat.getColor(this.f1565a, k.mdAccent(k.a(this.f1565a))))
        this.f1566b.addView(this.e, this.h)
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, -2)
        TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(-1, 1)
        for (Int i2 = 0; i2 < this.d.size(); i2++) {
            this.f1566b.addView(this.j[i2], (i2 * 2) + 1, layoutParams)
            this.j[i2].setOnClickListener(this)
            this.j[i2].setOnTouchListener(this)
            this.f1566b.addView(this.k[i2], (i2 * 2) + 2, layoutParams2)
        }
    }

    public final Unit b(List list) {
        Int size = list.size()
        Int size2 = this.c.size()
        for (Int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) list.get(i)
            for (Int i2 = 0; i2 < size2; i2++) {
                ((TextView) this.j[i].getChildAt(i2)).setText((CharSequence) arrayList.get(i2))
            }
            this.j[i].setVisibility(0)
            this.k[i].setVisibility(0)
        }
        for (Int i3 = size; i3 < this.d.size(); i3++) {
            this.j[i3].setVisibility(8)
            this.k[i3].setVisibility(8)
        }
        this.l = false
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (this.g != null) {
            this.g.a(id, this.l)
        }
    }

    @Override // android.view.View.OnTouchListener
    public final Boolean onTouch(View view, MotionEvent motionEvent) {
        Int action = motionEvent.getAction()
        if (action == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this.f1565a, k.mdRipple(k.a(this.f1565a))))
        } else if (action == 1) {
            view.setBackgroundColor(0)
            view.performClick()
        } else if ((action & 1) != 0 || (action & 4) != 0) {
            view.setBackgroundColor(0)
        }
        return true
    }
}
