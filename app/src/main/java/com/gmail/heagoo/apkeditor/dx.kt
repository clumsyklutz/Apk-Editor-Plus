package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.Dialog
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

final class dx extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f976a

    /* renamed from: b, reason: collision with root package name */
    private String f977b
    private dj c
    private cz d

    dx(Activity activity, String str, dj djVar, cz czVar) {
        super(activity)
        requestWindowFeature(1)
        this.f976a = WeakReference(activity)
        this.f977b = str
        this.c = djVar
        this.d = czVar
        View viewInflate = ((Activity) this.f976a.get()).getLayoutInflater().inflate(R.layout.dlg_manifestline, (ViewGroup) null, false)
        ((TextView) viewInflate.findViewById(R.id.content)).setText(this.c.f963b)
        TextView textView = (TextView) viewInflate.findViewById(R.id.description)
        textView.setMovementMethod(ScrollingMovementMethod())
        String strA = this.c.a()
        String strA2 = strA != null ? ds.a(activity, strA) : null
        textView.setText(strA2 == null ? "" : strA2)
        Button button = (Button) viewInflate.findViewById(R.id.delete)
        button.setClickable(true)
        button.setOnClickListener(dy(this))
        ((RelativeLayout) viewInflate.findViewById(R.id.extract)).setOnClickListener(dz(this))
        ((RelativeLayout) viewInflate.findViewById(R.id.replace)).setOnClickListener(eb(this))
        ((RelativeLayout) viewInflate.findViewById(R.id.open_in_new_window)).setOnClickListener(ed(this))
        ((Button) viewInflate.findViewById(R.id.close)).setOnClickListener(ee(this))
        setContentView(viewInflate)
    }

    static /* synthetic */ Boolean f(dx dxVar) {
        return true
    }
}
