package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gmail.heagoo.apkeditor.pro.R

class b extends Dialog implements View.OnClickListener {
    constructor(Activity activity) {
        super(activity)
        requestWindowFeature(1)
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.dlg_about_translate_plugin, (ViewGroup) null)
        ((Button) viewInflate.findViewById(R.id.btn_close)).setOnClickListener(this)
        setContentView(viewInflate)
        getWindow().setLayout((activity.getResources().getDisplayMetrics().widthPixels * 7) / 8, -2)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (view.getId() == R.id.btn_close) {
            dismiss()
        }
    }
}
