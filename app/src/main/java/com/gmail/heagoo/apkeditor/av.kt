package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import com.gmail.heagoo.apkeditor.ac.EditTextWithTip
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.b.a
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.Set
import java.util.zip.ZipFile

final class av implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ApkInfoExActivity f860a

    av(ApkInfoExActivity apkInfoExActivity) {
        this.f860a = apkInfoExActivity
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.app.AlertDialog a(java.lang.String r12, com.gmail.heagoo.b.a r13, Int r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 313
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.av.a(java.lang.String, com.gmail.heagoo.b.a, Int):android.app.AlertDialog")
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.io.Closeable, java.io.FileOutputStream, java.io.OutputStream] */
    protected final Unit a(String str, a aVar, String str2, String str3, Int i) {
        InputStream inputStream
        ZipFile zipFile
        InputStream inputStream2
        ZipFile zipFile2
        InputStream fileInputStream
        InputStream inputStream3 = null
        Boolean z = false
        if (this.f860a.m) {
            File(str + "/" + aVar.f1424a).renameTo(File(str + "/" + str3))
            return
        }
        if (!aVar.c && !ApkInfoExActivity.b(aVar.f1424a)) {
            z = true
        }
        try {
            if (z) {
                inputStream = FileInputStream(str + "/" + aVar.f1424a)
                zipFile = null
            } else {
                ZipFile zipFile3 = ZipFile(this.f860a.f757a)
                try {
                    inputStream = zipFile3.getInputStream(zipFile3.getEntry(str2))
                    zipFile = zipFile3
                } catch (Exception e) {
                    zipFile2 = zipFile3
                    inputStream2 = null
                    try {
                        Toast.makeText(this.f860a, R.string.str_rename_failed, 0).show()
                        a(inputStream2)
                        a(inputStream3)
                        a(zipFile2)
                    } catch (Throwable th) {
                        zipFile = zipFile2
                        inputStream = inputStream3
                        inputStream3 = inputStream2
                        th = th
                        a(inputStream3)
                        a(inputStream)
                        a(zipFile)
                        throw th
                    }
                } catch (Throwable th2) {
                    zipFile = zipFile3
                    th = th2
                    inputStream = null
                    a(inputStream3)
                    a(inputStream)
                    a(zipFile)
                    throw th
                }
            }
            try {
                String str4 = com.gmail.heagoo.a.c.a.d(this.f860a, "tmp") + com.gmail.heagoo.common.s.a(6)
                FileOutputStream fileOutputStream = FileOutputStream(str4)
                try {
                    com.gmail.heagoo.a.c.a.b(inputStream, (OutputStream) fileOutputStream)
                    a((Closeable) fileOutputStream)
                    a(inputStream)
                    a(zipFile)
                    this.f860a.e.b(str, aVar.f1424a, aVar.c)
                    ArrayList arrayList = ArrayList()
                    arrayList.add(Integer.valueOf(i))
                    this.f860a.e.b(arrayList)
                    String str5 = str + "/" + str3
                    try {
                        fileInputStream = FileInputStream(str4)
                    } catch (Exception e2) {
                        e = e2
                    } catch (Throwable th3) {
                        th = th3
                        fileInputStream = null
                    }
                    try {
                        this.f860a.e.a(str, this.f860a.e.a(str5, fileInputStream))
                        Toast.makeText(this.f860a, R.string.file_renamed, 0).show()
                        a(fileInputStream)
                    } catch (Exception e3) {
                        e = e3
                        inputStream3 = fileInputStream
                        try {
                            e.printStackTrace()
                            Toast.makeText(this.f860a, R.string.str_rename_failed, 0).show()
                            a(inputStream3)
                        } catch (Throwable th4) {
                            th = th4
                            fileInputStream = inputStream3
                            a(fileInputStream)
                            throw th
                        }
                    } catch (Throwable th5) {
                        th = th5
                        a(fileInputStream)
                        throw th
                    }
                } catch (Exception e4) {
                    inputStream3 = inputStream
                    zipFile2 = zipFile
                    inputStream2 = fileOutputStream
                    Toast.makeText(this.f860a, R.string.str_rename_failed, 0).show()
                    a(inputStream2)
                    a(inputStream3)
                    a(zipFile2)
                } catch (Throwable th6) {
                    inputStream3 = fileOutputStream
                    th = th6
                    a(inputStream3)
                    a(inputStream)
                    a(zipFile)
                    throw th
                }
            } catch (Exception e5) {
                inputStream2 = null
                inputStream3 = inputStream
                zipFile2 = zipFile
            } catch (Throwable th7) {
                th = th7
            }
        } catch (Exception e6) {
            inputStream2 = null
            zipFile2 = null
        } catch (Throwable th8) {
            th = th8
            inputStream = null
            zipFile = null
        }
    }

    protected final Unit a(String str, Boolean z, Boolean z2) {
        Set setE = this.f860a.e.e()
        if (setE.isEmpty()) {
            return
        }
        ArrayList arrayList = ArrayList()
        String strA = this.f860a.e.a(arrayList)
        ArrayList arrayList2 = ArrayList()
        ArrayList arrayList3 = ArrayList(setE.size())
        arrayList3.addAll(setE)
        Iterator it = arrayList3.iterator()
        while (it.hasNext()) {
            arrayList2.add(((a) arrayList.get(((Integer) it.next()).intValue())).f1424a)
        }
        this.f860a.a(str, strA, arrayList2, z, !z2)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.menu_home) {
            this.f860a.e.c(this.f860a.f758b)
            this.f860a.f.a(this.f860a.f758b)
            return
        }
        if (id == R.id.menu_done) {
            this.f860a.e.a(false)
            return
        }
        if (id == R.id.menu_select) {
            Set setE = this.f860a.e.e()
            Int count = this.f860a.e.getCount()
            ArrayList arrayList = ArrayList(count)
            this.f860a.e.a(arrayList)
            if (setE.size() != ("..".equals(((a) arrayList.get(0)).f1424a) ? count - 1 : count)) {
                this.f860a.e.a(true)
                return
            } else {
                this.f860a.e.a(false)
                return
            }
        }
        if (id == R.id.menu_addfile) {
            this.f860a.f()
            return
        }
        if (id == R.id.menu_addfolder) {
            this.f860a.g()
            return
        }
        if (id == R.id.menu_searchoptions) {
            this.f860a.n()
            return
        }
        if (id == R.id.menu_caseinsensitive) {
            this.f860a.o()
            return
        }
        if (id == R.drawable.ic_res_extract) {
            Set setE2 = this.f860a.e.e()
            if (setE2.isEmpty()) {
                return
            }
            ArrayList arrayList2 = ArrayList()
            arrayList2.addAll(setE2)
            Collections.sort(arrayList2)
            this.f860a.a((List) arrayList2)
            return
        }
        if (id == R.drawable.ic_res_replace) {
            Set setE3 = this.f860a.e.e()
            if (setE3.isEmpty()) {
                return
            }
            Int iIntValue = ((Integer) setE3.iterator().next()).intValue()
            ArrayList arrayList3 = ArrayList()
            this.f860a.e.a(arrayList3)
            if (((a) arrayList3.get(iIntValue)).f1425b) {
                this.f860a.b(iIntValue)
                return
            } else {
                this.f860a.a(iIntValue)
                return
            }
        }
        if (id != R.drawable.ic_res_search) {
            if (id == R.drawable.ic_res_delete) {
                Set setE4 = this.f860a.e.e()
                if (setE4.isEmpty()) {
                    return
                }
                ArrayList arrayList4 = ArrayList()
                arrayList4.addAll(setE4)
                Collections.sort(arrayList4)
                this.f860a.e.c(arrayList4)
                return
            }
            if (id == R.drawable.ic_res_detail) {
                Set setE5 = this.f860a.e.e()
                if (setE5.isEmpty()) {
                    return
                }
                Int iIntValue2 = ((Integer) setE5.iterator().next()).intValue()
                ArrayList arrayList5 = ArrayList()
                a(this.f860a.e.a(arrayList5), (a) arrayList5.get(iIntValue2), iIntValue2).show()
                return
            }
            return
        }
        Float f = this.f860a.getResources().getDisplayMetrics().density
        Int i = (Int) ((8.0f * f) + 0.5f)
        Int i2 = (Int) ((18.0f * f) + 0.5f)
        Int i3 = (Int) ((f * 20.0f) + 0.5f)
        AlertDialog.Builder builder = new AlertDialog.Builder(this.f860a)
        builder.setTitle(R.string.search)
        com.gmail.heagoo.apkeditor.ac.a aVar = new com.gmail.heagoo.apkeditor.ac.a(this.f860a, "res_keywords")
        LinearLayout linearLayout = LinearLayout(this.f860a)
        linearLayout.setOrientation(1)
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2))
        EditTextWithTip editTextWithTip = EditTextWithTip(this.f860a)
        editTextWithTip.setHint(R.string.pls_input_keyword)
        editTextWithTip.setTextColor(ContextCompat.getColor(this.f860a, a.a.b.a.k.mdTextMedium(a.a.b.a.k.a(this.f860a))))
        editTextWithTip.setTextSize(1, 14)
        editTextWithTip.setAdapter(aVar)
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
        layoutParams.setMargins(i3, i, i3, 0)
        editTextWithTip.setLayoutParams(layoutParams)
        linearLayout.addView(editTextWithTip)
        CheckBox checkBox = CheckBox(this.f860a)
        checkBox.setText(R.string.case_insensitive)
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2)
        layoutParams2.setMargins(i2, 0, i2, 0)
        checkBox.setLayoutParams(layoutParams2)
        linearLayout.addView(checkBox)
        CheckBox checkBox2 = CheckBox(this.f860a)
        checkBox2.setText(R.string.search_file_names)
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2)
        layoutParams3.setMargins(i2, 0, i2, 0)
        checkBox2.setLayoutParams(layoutParams3)
        linearLayout.addView(checkBox2)
        builder.setView(linearLayout)
        builder.setPositiveButton(android.R.string.ok, aw(this, editTextWithTip, checkBox2, checkBox))
        builder.setNegativeButton(android.R.string.cancel, ax(this))
        builder.show()
    }
}
