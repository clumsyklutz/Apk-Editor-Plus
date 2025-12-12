package com.gmail.heagoo.apkeditor.util

import android.view.View

final class u implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Int f1333a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ t f1334b

    u(t tVar, Int i) {
        this.f1334b = tVar
        this.f1333a = i
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        for (Int i = 0; i < this.f1334b.c.size(); i++) {
            if (i == this.f1333a) {
                ((v) this.f1334b.c.get(i)).f1336b = true
            } else {
                ((v) this.f1334b.c.get(i)).f1336b = false
            }
        }
        this.f1334b.f1331a.getDialog().dismiss()
    }
}
