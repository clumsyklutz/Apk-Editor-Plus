package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.common.a
import java.util.LinkedList
import java.util.List

final class cm extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private a f936a = null

    /* renamed from: b, reason: collision with root package name */
    private Boolean f937b = false
    private List c = LinkedList()
    private /* synthetic */ FileListActivity d

    cm(FileListActivity fileListActivity) {
        this.d = fileListActivity
    }

    public final Unit a() {
        this.f937b = true
        synchronized (this.c) {
            this.c.notify()
        }
    }

    public final Unit a(String str) {
        synchronized (this.c) {
            this.c.add(str)
            this.c.notify()
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        String str
        com.gmail.heagoo.common.b bVarA
        this.f936a = a()
        while (!this.f937b) {
            synchronized (this.c) {
                if (this.c.isEmpty()) {
                    try {
                        this.c.wait()
                    } catch (InterruptedException e) {
                    }
                }
                str = !this.c.isEmpty() ? (String) this.c.remove(0) : null
            }
            if (str != null) {
                try {
                    bVarA = a.a(this.d, str)
                } catch (Throwable th) {
                    bVarA = null
                }
                if (bVarA != null) {
                    this.d.k.put(str, bVarA)
                    FileListActivity.g(this.d)
                }
            }
        }
    }
}
