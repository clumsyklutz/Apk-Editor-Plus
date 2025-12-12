package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message

final class cl extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ FileListActivity f935a

    cl(FileListActivity fileListActivity) {
        this.f935a = fileListActivity
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                this.f935a.d.a().notifyDataSetChanged()
                break
        }
    }
}
