package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.os.AsyncTask
import android.view.View
import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern

final class gs extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1119a

    /* renamed from: b, reason: collision with root package name */
    private String f1120b
    private String c
    private View d
    private List e = ArrayList()
    private Int f
    private /* synthetic */ gp g

    gs(gp gpVar, Activity activity, String str, String str2, View view) {
        this.g = gpVar
        this.f = -1
        this.f1119a = WeakReference(activity)
        this.f1120b = str
        this.c = str2
        this.d = view
        if (str.endsWith(".smali")) {
            this.f = 0
        } else if (str.endsWith(".java")) {
            this.f = 1
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private fun a() throws IOException {
        Int i = 0
        BufferedReader bufferedReader = BufferedReader(StringReader(this.c))
        try {
            switch (this.f) {
                case 0:
                    while (true) {
                        String line = bufferedReader.readLine()
                        if (line == null) {
                            break
                        } else {
                            if (line.startsWith(".method ")) {
                                this.e.add(go(i, line.substring(8)))
                            }
                            i++
                        }
                    }
                case 1:
                    while (true) {
                        Int i2 = i
                        String line2 = bufferedReader.readLine()
                        if (line2 == null) {
                            break
                        } else {
                            String strTrim = line2.trim()
                            if (strTrim.length() > 6 && strTrim.charAt(0) == 'p' && (strTrim.charAt(1) == 'u' || strTrim.charAt(1) == 'r')) {
                                Matcher matcher = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])").matcher(strTrim)
                                if (matcher.matches()) {
                                    String strGroup = matcher.group(0)
                                    if (strGroup.endsWith("{")) {
                                        strGroup = strGroup.substring(0, strGroup.length() - 1).trim()
                                    }
                                    this.e.add(go(i2, strGroup))
                                }
                            }
                            i = i2 + 1
                        }
                    }
                    break
            }
            return null
        } catch (IOException e) {
            return null
        }
    }

    @Override // android.os.AsyncTask
    public final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        return a()
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        gp.a(this.g, (Activity) this.f1119a.get(), this.f1120b, this.e)
        this.g.a(this.d)
    }
}
