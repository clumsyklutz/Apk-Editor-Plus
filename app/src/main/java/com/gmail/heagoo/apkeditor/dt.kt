package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.EmptyStackException
import java.util.Iterator
import java.util.List
import java.util.Stack

final class dt extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, cz, iz {

    /* renamed from: a, reason: collision with root package name */
    private String f969a
    private WeakReference d
    private cz e

    /* renamed from: b, reason: collision with root package name */
    private List f970b = ArrayList()
    private val c = ArrayList()
    private Int f = R.layout.item_manifestline

    dt(Activity activity, String str, cz czVar) throws IOException {
        this.d = WeakReference(activity)
        this.e = czVar
        this.f969a = str
        b()
    }

    fun a(Context context) {
        new AlertDialog.Builder(context).setTitle(R.string.not_available).setMessage(R.string.promote_msg).setPositiveButton(R.string.view_pro_version, dv(context)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
    }

    static /* synthetic */ Unit a(dt dtVar, dj djVar) {
        synchronized (dtVar.c) {
            djVar.f = !djVar.f
            dtVar.d()
        }
        dtVar.notifyDataSetChanged()
    }

    private fun b() throws IOException {
        try {
            BufferedReader bufferedReader = BufferedReader(FileReader(this.f969a))
            Int i = 0
            while (true) {
                String line = bufferedReader.readLine()
                if (line == null) {
                    break
                }
                dj djVar = dj(i, line, "\t")
                this.f970b.add(djVar)
                this.c.add(djVar)
                i++
            }
            bufferedReader.close()
        } catch (Exception e) {
        }
        c()
    }

    static /* synthetic */ Unit b(Context context) {
        String str = context.getPackageName() + ".pro"
        try {
            context.startActivity(Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str)))
        } catch (ActivityNotFoundException e) {
            context.startActivity(Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + str)))
        }
    }

    private fun c() {
        try {
            Stack stack = Stack()
            for (Int i = 0; i < this.c.size(); i++) {
                dj djVar = (dj) this.c.get(i)
                if (djVar.c >= 0) {
                    if (djVar.f963b.endsWith("/>")) {
                        djVar.d = djVar.f962a
                        djVar.e = djVar.f962a
                    } else if (djVar.f963b.startsWith("</")) {
                        dj djVar2 = (dj) stack.pop()
                        djVar2.e = djVar.f962a
                        djVar.d = djVar2.f962a
                        djVar.e = djVar.f962a
                    } else {
                        djVar.d = djVar.f962a
                        stack.push(djVar)
                    }
                }
            }
        } catch (EmptyStackException e) {
            for (Int i2 = 0; i2 < this.c.size(); i2++) {
                ((dj) this.c.get(i2)).c = 0
            }
        }
    }

    private fun d() {
        this.c.clear()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.f970b.size()) {
                return
            }
            dj djVar = (dj) this.f970b.get(i2)
            if (!djVar.g) {
                this.c.add(djVar)
                if (djVar.f && djVar.e > i2) {
                    i2 = djVar.e
                }
            }
            i = i2 + 1
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cz
    public final String a(dj djVar) {
        Boolean z
        Boolean z2
        String strA = djVar.a()
        if ("manifest".equals(strA) || "application".equals(strA)) {
            z = false
        } else if ("activity".equals(strA) || "intent-filter".equals(strA)) {
            Int i = djVar.d
            while (true) {
                Int i2 = i
                if (i2 >= djVar.e) {
                    z2 = false
                    break
                }
                dj djVar2 = (dj) this.f970b.get(i2)
                if ("action".equals(djVar2.a()) && djVar2.f963b.contains("android.intent.action.MAIN")) {
                    z2 = true
                    break
                }
                i = i2 + 1
            }
            z = !z2
        } else {
            z = "action".equals(strA) ? !djVar.f963b.contains("android.intent.action.MAIN") : ("category".equals(strA) && djVar.f963b.contains("android.intent.category.LAUNCHER")) ? false : true
        }
        if (!z) {
            return ((Activity) this.d.get()).getResources().getString(R.string.section_undeletable)
        }
        Int i3 = djVar.d
        Int i4 = djVar.e
        StringBuilder sb = StringBuilder()
        synchronized (this.c) {
            for (Int i5 = 0; i5 < this.f970b.size(); i5++) {
                dj djVar3 = (dj) this.f970b.get(i5)
                if (djVar3.f962a >= i3 && djVar3.f962a <= i4) {
                    djVar3.g = true
                }
                if (!djVar3.g) {
                    sb.append(djVar3.f963b)
                    sb.append('\n')
                }
            }
            d()
        }
        notifyDataSetChanged()
        if (this.e != null) {
            this.e.i(sb.toString())
        }
        return null
    }

    final Unit a() {
        synchronized (this.c) {
            this.f970b.clear()
            this.c.clear()
            b()
        }
        notifyDataSetChanged()
    }

    @Override // com.gmail.heagoo.apkeditor.iz
    public final Unit a(Int i, String str) {
        StringBuilder sb = StringBuilder()
        synchronized (this) {
            for (dj djVar : this.f970b) {
                if (djVar.f962a == i) {
                    djVar.f963b = str
                }
                if (!djVar.g) {
                    sb.append(djVar.f963b)
                    sb.append('\n')
                }
            }
            Iterator it = this.c.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                dj djVar2 = (dj) it.next()
                if (djVar2.f962a == i) {
                    djVar2.f963b = str
                    break
                }
            }
        }
        notifyDataSetChanged()
        if (this.e != null) {
            this.e.i(sb.toString())
        }
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.c.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.c.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        dj djVar
        dw dwVar
        synchronized (this.c) {
            djVar = (dj) this.c.get(i)
        }
        if (view == null) {
            view = LayoutInflater.from((Context) this.d.get()).inflate(this.f, (ViewGroup) null)
            dwVar = dw((Byte) 0)
            dwVar.f975b = (ImageView) view.findViewById(R.id.collapse_icon)
            dwVar.f974a = (TextView) view.findViewById(R.id.line_data)
            view.setTag(dwVar)
        } else {
            dwVar = (dw) view.getTag()
        }
        dwVar.f974a.setText(djVar.f963b)
        if (djVar.c > 0) {
            dwVar.f975b.setVisibility(0)
            ImageView imageView = dwVar.f975b
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(djVar.c * 48, 48, Bitmap.Config.ALPHA_8)
            if (djVar.e != djVar.f962a) {
                Canvas(bitmapCreateBitmap).drawBitmap(!djVar.f ? BitmapFactory.decodeResource(((Activity) this.d.get()).getResources(), R.drawable.arrow_down) : BitmapFactory.decodeResource(((Activity) this.d.get()).getResources(), R.drawable.arrow_right), r4 - 40, 8.0f, Paint())
            }
            imageView.setImageBitmap(bitmapCreateBitmap)
            dwVar.f975b.setOnClickListener(du(this, djVar))
        } else {
            dwVar.f975b.setVisibility(8)
        }
        return view
    }

    @Override // com.gmail.heagoo.apkeditor.cz
    public final Unit i(String str) {
        if (this.e != null) {
            this.e.i(str)
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        dj djVar = null
        try {
            djVar = (dj) this.c.get(i)
        } catch (Exception e) {
        }
        if (djVar != null) {
            iy((Context) this.d.get(), this, djVar.f962a, djVar.f963b).show()
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        dj djVar = null
        try {
            djVar = (dj) this.c.get(i)
        } catch (Exception e) {
        }
        dx((Activity) this.d.get(), this.f969a, djVar, this).show()
        return true
    }
}
