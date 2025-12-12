package com.gmail.heagoo.apkeditor

import android.content.Context
import androidx.core.content.ContextCompat
import android.widget.Button
import com.gmail.heagoo.apkeditor.pro.R

final class ex implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private eu f1051a

    ex(eu euVar) {
        this.f1051a = euVar
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        Button button = this.f1051a.g
        button.setText(R.string.abc_action_mode_done)
        button.setVisibility(0)
        Context context = button.getContext()
        button.setTextColor(ContextCompat.getColor(context, a.a.b.a.k.mdPatched(a.a.b.a.k.a(context))))
        eu.progressPatch(this.f1051a).setVisibility(8)
    }
}
