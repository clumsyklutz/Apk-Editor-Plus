package com.gmail.heagoo.apkeditor.se

import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.cn
import com.gmail.heagoo.apkeditor.cu
import com.gmail.heagoo.apkeditor.pro.R
import java.io.IOException
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set

class c extends BaseAdapter implements MediaPlayer.OnCompletionListener, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, cu {

    /* renamed from: a, reason: collision with root package name */
    private Activity f1256a

    /* renamed from: b, reason: collision with root package name */
    private z f1257b
    private List c
    private String f
    private MediaPlayer g
    private String i
    private Set d = HashSet()
    private Map e = HashMap()
    private Int h = -1
    private Int j = R.layout.item_zipfile

    constructor(Activity activity, z zVar) {
        this.f1256a = activity
        this.f1257b = zVar
        this.c = zVar.d
    }

    private fun a(String str) {
        return str.substring(str.lastIndexOf(47) + 1)
    }

    private fun a(Int i) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        String str = (String) this.c.get(i)
        if (this.g == null) {
            this.f = com.gmail.heagoo.a.c.a.d(this.f1256a, "tmp")
            this.g = MediaPlayer()
            this.g.setOnCompletionListener(this)
        } else {
            this.g.reset()
        }
        if (!this.d.contains(str)) {
            a.a.b.a.a.x.b(this.f1257b.b(), str, this.f + a(str))
            this.d.add(str)
        }
        if (str.equals(this.i)) {
            this.g.stop()
            this.i = null
            this.h = -1
            return
        }
        String str2 = (String) this.e.get(str)
        if (str2 != null) {
            this.g.setDataSource(str2)
        } else {
            this.g.setDataSource(this.f + a(str))
        }
        this.g.prepare()
        this.g.start()
        this.i = str
        this.h = i
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Int i) {
        cn(this.f1256a, this, "", (String) this.c.get(i), this.f1256a.getString(R.string.select_file_replace)).show()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(Int i) {
        String str = (String) this.c.get(i)
        u.a(this.f1256a, this.f1257b.b(), str)
    }

    public final Unit a() {
        if (this.g != null) {
            if (this.g.isPlaying()) {
                this.g.stop()
            }
            this.g.release()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        this.e.put(str2, str)
        ((SimpleEditActivity) this.f1256a).a()
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return z.a(str)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    public final Map b() {
        return this.e
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
        t tVar
        String str = (String) this.c.get(i)
        if (view == null) {
            view = LayoutInflater.from(this.f1256a).inflate(this.j, (ViewGroup) null)
            t tVar2 = t()
            tVar2.f1278a = (ImageView) view.findViewById(R.id.file_icon)
            tVar2.f1279b = (TextView) view.findViewById(R.id.filename)
            tVar2.c = (TextView) view.findViewById(R.id.detail1)
            tVar2.d = view.findViewById(R.id.menu_edit)
            tVar2.e = view.findViewById(R.id.menu_save)
            view.setTag(tVar2)
            tVar = tVar2
        } else {
            tVar = (t) view.getTag()
        }
        Int iLastIndexOf = str.lastIndexOf(47)
        String strSubstring = str.substring(iLastIndexOf + 1)
        String strSubstring2 = str.substring(0, iLastIndexOf + 1)
        tVar.f1278a.setId(i)
        tVar.f1278a.setOnClickListener(this)
        tVar.d.setId(this.c.size() + i)
        tVar.d.setOnClickListener(this)
        tVar.e.setId((this.c.size() * 2) + i)
        tVar.e.setOnClickListener(this)
        if (i != this.h) {
            tVar.f1278a.setImageResource(R.drawable.play)
        } else {
            tVar.f1278a.setImageResource(R.drawable.pause)
        }
        tVar.f1279b.setText(strSubstring)
        tVar.c.setText(strSubstring2)
        return view
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id < this.c.size()) {
            try {
                a(id)
                notifyDataSetChanged()
                return
            } catch (Exception e) {
                e.printStackTrace()
                return
            }
        }
        if (id < this.c.size() * 2) {
            b(id - this.c.size())
        } else if (id < this.c.size() * 3) {
            c(id - (this.c.size() * 2))
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final Unit onCompletion(MediaPlayer mediaPlayer) {
        this.i = null
        this.h = -1
        notifyDataSetChanged()
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        try {
            a(i)
            notifyDataSetChanged()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        adapterView.setOnCreateContextMenuListener(d(this, i))
        return false
    }
}
