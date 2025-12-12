package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SlidingDrawer
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import android.widget.ViewAnimator
import com.gmail.heagoo.InterfaceA
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.ui.LayoutObListView
import java.io.IOException
import java.util.ArrayList
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern

class TextEditBigActivity extends gy implements TextWatcher, View.OnClickListener, a {
    private ToggleButton A
    private ToggleButton B
    private ImageView C
    private Boolean D
    private Boolean E
    private TextView F
    private View G
    private View H
    private View I
    private Int J
    private hw K
    private String L
    private Int k
    private FrameLayout l
    private ViewAnimator m
    private SlidingDrawer n
    private EditText o
    private EditText p
    private ImageButton q
    private ImageButton r
    private ImageButton s
    private ImageButton t
    private EditText u
    private ImageView v
    private ImageView w
    private ImageView x
    private ImageView y
    private ImageView z

    constructor() {
        super(true, false)
        this.D = false
        this.E = false
        this.L = ""
    }

    private fun a(Int i, Int i2, Int i3) {
        this.K.a(i, i2, i3)
    }

    private fun a(ImageView imageView, Boolean z) {
        if (z) {
            imageView.getDrawable().setAlpha(255)
            imageView.setClickable(true)
        } else {
            imageView.getDrawable().setAlpha(80)
            imageView.setClickable(false)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(com.gmail.heagoo.common.i iVar) {
        ey(this, hm(this, iVar), R.string.file_saved).show()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Boolean z) {
        if (this.o.getText().toString().equals("")) {
            return false
        }
        if (!b(this.K.a(), this.K.c()) && !b(0, 0)) {
            if (!z) {
                return false
            }
            Toast.makeText(getApplicationContext(), getString(R.string.not_found), 0).show()
            return false
        }
        return true
    }

    private fun b(Int i, Int i2) {
        Matcher matcher
        if (i < 0) {
            i = 0
        }
        Int i3 = this.A.isChecked() ? 10 : 8
        Int i4 = (this.B == null || this.B.isChecked()) ? i3 : i3 | 16
        try {
            List listE = this.K.e()
            String str = (String) listE.get(i)
            Pattern patternCompile = Pattern.compile(this.o.getText().toString(), i4)
            Matcher matcher2 = patternCompile.matcher(str)
            if (matcher2.find(i2)) {
                a(i, matcher2.start(), matcher2.end())
                return true
            }
            do {
                i++
                if (i >= listE.size()) {
                    return false
                }
                matcher = patternCompile.matcher((CharSequence) listE.get(i))
            } while (!matcher.find());
            a(i, matcher.start(), matcher.end())
            return true
        } catch (Exception e) {
            return false
        }
    }

    private fun d(Int i) {
        Byte b2 = 0
        if (this.j.b()) {
            new AlertDialog.Builder(this).setMessage(R.string.save_changes_tip).setPositiveButton(R.string.save, hk(this, i)).setNegativeButton(R.string.donot_save, hj(this, i)).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
        } else {
            this.e += i
            hr(this, b2).execute(new Void[0])
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun e() {
        try {
            String str = this.L
            if (str != null && !"".equals(str)) {
                String string = this.o.getText().toString()
                if ((this.A.isChecked() ? Pattern.compile(string, 10) : Pattern.compile(string)).matcher(str).matches()) {
                    this.r.setEnabled(true)
                    this.s.setEnabled(true)
                    return
                }
            }
        } catch (Exception e) {
        }
        this.r.setEnabled(false)
        this.s.setEnabled(false)
    }

    private fun e(Int i) {
        this.K.a(2, (i <= 40 ? i : 40) >= 4 ? r1 : 4)
    }

    static /* synthetic */ Unit f(TextEditBigActivity textEditBigActivity) {
        if (textEditBigActivity.J > 0) {
            textEditBigActivity.K.c(textEditBigActivity.J - 1)
        }
    }

    static /* synthetic */ Unit g(TextEditBigActivity textEditBigActivity) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textEditBigActivity.K.h()
        layoutParams.setMargins(0, 0, textEditBigActivity.n.getWidth(), layoutParams.bottomMargin)
        textEditBigActivity.K.g()
        textEditBigActivity.C.setImageResource(R.drawable.edit_slide_right)
    }

    static /* synthetic */ Unit h(TextEditBigActivity textEditBigActivity) {
        textEditBigActivity.n.setVisibility(8)
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textEditBigActivity.K.h()
        layoutParams.setMargins(0, 0, 0, layoutParams.bottomMargin)
        textEditBigActivity.K.g()
        textEditBigActivity.C.setImageResource(R.drawable.edit_slide_left)
        textEditBigActivity.m.setDisplayedChild(0)
    }

    @Override // com.gmail.heagoo.apkeditor.gy, com.gmail.heagoo.apkeditor.dn
    public final Unit a(Int i, Int i2) {
        Boolean z
        Boolean z2 = false
        if (this.i == 2) {
            Array<String> strArrSplit = this.K.d().toString().split("\n")
            if (i <= 0) {
                i = 1
            }
            if (i2 > strArrSplit.length) {
                i2 = strArrSplit.length
            }
            if (i <= i2) {
                ArrayList arrayList = ArrayList()
                for (Int i3 = 1; i3 < i; i3++) {
                    arrayList.add(strArrSplit[i3 - 1])
                }
                for (Int i4 = i2 + 1; i4 <= strArrSplit.length; i4++) {
                    arrayList.add(strArrSplit[i4 - 1])
                }
                this.K.a(arrayList)
                Toast.makeText(this, String.format(getString(R.string.n_lines_deleted), Integer.valueOf((i2 - i) + 1)), 1).show()
                z = true
            } else {
                z = false
            }
            z2 = z
        } else if (this.i == 5) {
            Array<String> strArrSplit2 = this.K.d().toString().split("\n")
            if (i <= 0) {
                i = 1
            }
            if (i2 > strArrSplit2.length) {
                i2 = strArrSplit2.length
            }
            if (i <= i2) {
                ArrayList arrayList2 = ArrayList()
                for (Int i5 = 1; i5 < i; i5++) {
                    arrayList2.add(strArrSplit2[i5 - 1])
                }
                Boolean zA = a(strArrSplit2, i, i2, "#")
                if (zA) {
                    for (Int i6 = i; i6 <= i2; i6++) {
                        Int iIndexOf = strArrSplit2[i6 - 1].indexOf(35)
                        if (iIndexOf > 0) {
                            arrayList2.add(strArrSplit2[i6 - 1].substring(0, iIndexOf) + strArrSplit2[i6 - 1].substring(iIndexOf + 1))
                        } else {
                            arrayList2.add(strArrSplit2[i6 - 1].substring(iIndexOf + 1))
                        }
                    }
                } else {
                    for (Int i7 = i; i7 <= i2; i7++) {
                        arrayList2.add("#" + strArrSplit2[i7 - 1])
                    }
                }
                for (Int i8 = i2 + 1; i8 <= strArrSplit2.length; i8++) {
                    arrayList2.add(strArrSplit2[i8 - 1])
                }
                this.K.a(arrayList2)
                Toast.makeText(this, String.format(zA ? getString(R.string.n_lines_uncommented) : getString(R.string.n_lines_commented), Integer.valueOf((i2 - i) + 1)), 1).show()
                z2 = true
            }
        }
        if (z2) {
            this.j.a(true)
            d()
        }
    }

    @Override // com.gmail.heagoo.a
    public final Unit a(Int i, Int i2, String str) {
        this.L = str
        if (i == i2) {
            a(this.x, false)
        } else {
            a(this.x, true)
        }
        if (this.n.isOpened()) {
            e()
        }
    }

    @Override // android.text.TextWatcher
    fun afterTextChanged(Editable editable) {
        this.j.a(true)
        d()
    }

    @Override // com.gmail.heagoo.apkeditor.gr
    public final Unit b(Int i) {
        if (i > 0) {
            this.u.setText("")
            this.K.c(i - 1)
        }
    }

    @Override // android.text.TextWatcher
    fun beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    @Override // com.gmail.heagoo.apkeditor.gy
    protected final Unit c(Int i) {
        Editable editableF
        if (this.K == null || (editableF = this.K.f()) == null) {
            return
        }
        editableF.replace(this.K.b(), this.K.c(), StringBuilder().append(b((String) null).charAt(i)).toString())
    }

    protected final synchronized Unit d() {
        Boolean zB
        com.gmail.heagoo.neweditor.e eVar = this.j
        if (eVar != null && this.E != (zB = eVar.b())) {
            if (zB) {
                a(this.w, true)
            } else {
                a(this.w, false)
            }
            this.w.invalidate()
            this.E = zB
        }
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        if (this.j.b()) {
            new AlertDialog.Builder(this).setMessage(R.string.save_changes_tip).setPositiveButton(R.string.save, ho(this)).setNegativeButton(R.string.donot_save, hn(this)).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
        } else {
            finish()
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int size
        Int iB
        Int iC
        EditText editTextD
        Int id = view.getId()
        if (id == R.id.menu_next) {
            d(1)
            return
        }
        if (id == R.id.menu_previous) {
            d(-1)
            return
        }
        if (id == R.id.moreBtn) {
            a(view)
            return
        }
        if (id == R.id.openFindBtn) {
            if (this.n.getVisibility() == 8) {
                this.n.animateOpen()
                this.n.setVisibility(0)
            } else {
                this.n.close()
                this.n.setVisibility(8)
            }
            String str = this.L
            if (str == null || "".equals(str)) {
                if (this.f1129a != null && "".equals(this.o.getText().toString())) {
                    this.o.setText(this.f1129a)
                }
            } else if (!str.contains("\n")) {
                this.o.setText(str)
            }
            this.o.requestFocus()
            return
        }
        if (id == R.id.findBtn) {
            a(true)
            return
        }
        if (id == R.id.replaceBtn) {
            hw hwVar = this.K
            String string = this.p.getText().toString()
            Int iA = hwVar.a()
            if (iA >= 0 && (iB = hwVar.b()) != (iC = hwVar.c()) && (editTextD = hwVar.d(iA)) != null) {
                editTextD.getEditableText().replace(iB, iC, string)
            }
            a(true)
            return
        }
        if (id != R.id.replaceAllBtn) {
            if (id == R.id.goBtn) {
                String strTrim = this.u.getText().toString().trim()
                if (!strTrim.equals("")) {
                    try {
                        b(Integer.valueOf(strTrim).intValue())
                        return
                    } catch (Exception e) {
                        return
                    }
                }
                hw hwVar2 = this.K
                EditText editTextD2 = hwVar2.d(hwVar2.a())
                if (editTextD2 != null) {
                    editTextD2.getText().insert(editTextD2.getSelectionStart(), "\n")
                    return
                }
                return
            }
            if (id == R.id.saveBtn) {
                a((com.gmail.heagoo.common.i) null)
                return
            }
            if (id == R.id.copyBtn) {
                String str2 = this.L
                if (str2 == null || str2.equals("")) {
                    return
                }
                com.gmail.heagoo.a.c.a.c(this, str2)
                if (str2.contains("\n")) {
                    Toast.makeText(this, R.string.selected_str_copied, 0).show()
                    return
                } else {
                    Toast.makeText(this, String.format(getString(R.string.copied_to_clipboard), str2), 0).show()
                    return
                }
            }
            if (id != R.id.pasteBtn) {
                if (id == R.id.menu_methods) {
                    b(view)
                    return
                }
                return
            }
            String strB = com.gmail.heagoo.a.c.a.b(this)
            if (strB == null) {
                Toast.makeText(this, R.string.clipboard_no_text, 0).show()
                return
            }
            Editable editableF = this.K.f()
            if (editableF != null) {
                editableF.replace(this.K.b(), this.K.c(), strB)
                return
            }
            return
        }
        Int i = this.A.isChecked() ? 10 : 8
        if (this.B != null && !this.B.isChecked()) {
            i |= 16
        }
        try {
            String string2 = this.p.getText().toString()
            List listE = this.K.e()
            Pattern patternCompile = Pattern.compile(this.o.getText().toString(), i)
            ArrayList<hq> arrayList = ArrayList()
            Int i2 = 0
            Int i3 = 0
            while (i2 < listE.size()) {
                String str3 = (String) listE.get(i2)
                Matcher matcher = patternCompile.matcher(str3)
                Int iEnd = 0
                while (matcher.find(iEnd)) {
                    Int iStart = matcher.start()
                    iEnd = matcher.end()
                    arrayList.add(hq(iStart, iEnd, (Byte) 0))
                }
                if (arrayList.isEmpty()) {
                    size = i3
                } else {
                    StringBuilder sb = StringBuilder()
                    Int i4 = 0
                    for (hq hqVar : arrayList) {
                        sb.append(str3.substring(i4, hqVar.f1156a))
                        sb.append(string2)
                        i4 = hqVar.f1157b
                    }
                    sb.append(str3.substring(i4))
                    listE.set(i2, sb.toString())
                    size = arrayList.size() + i3
                    arrayList.clear()
                }
                i2++
                i3 = size
            }
            this.K.i()
            if (i3 > 0) {
                Toast.makeText(getApplicationContext(), String.format(getString(R.string.replace_all_ret), Integer.valueOf(i3)), 0).show()
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.not_found), 0).show()
            }
        } catch (Exception e2) {
        }
    }

    @Override // com.gmail.heagoo.apkeditor.gy, android.app.Activity
    @SuppressLint({"NewApi"})
    fun onCreate(Bundle bundle) {
        super.onCreate(bundle)
        this.F = (TextView) findViewById(R.id.filename)
        this.G = findViewById(R.id.menu_previous)
        this.H = findViewById(R.id.menu_next)
        this.I = findViewById(R.id.menu_methods)
        this.G.setOnClickListener(this)
        this.H.setOnClickListener(this)
        this.I.setOnClickListener(this)
        this.l = (FrameLayout) findViewById(R.id.center_layout)
        this.K = hw(this, (LayoutObListView) findViewById(R.id.text_list))
        this.m = (ViewAnimator) findViewById(R.id.searchAnimator)
        this.n = (SlidingDrawer) findViewById(R.id.sliding_drawer)
        this.o = (EditText) findViewById(R.id.findEdit)
        this.p = (EditText) findViewById(R.id.replaceEdit)
        this.q = (ImageButton) findViewById(R.id.findBtn)
        this.r = (ImageButton) findViewById(R.id.replaceBtn)
        this.s = (ImageButton) findViewById(R.id.replaceAllBtn)
        this.v = (ImageView) findViewById(R.id.openFindBtn)
        this.A = (ToggleButton) findViewById(R.id.checkBoxIgnoreCase)
        this.B = (ToggleButton) findViewById(R.id.checkBoxRegexp)
        this.w = (ImageView) findViewById(R.id.saveBtn)
        this.x = (ImageView) findViewById(R.id.copyBtn)
        this.y = (ImageView) findViewById(R.id.pasteBtn)
        this.z = (ImageView) findViewById(R.id.moreBtn)
        this.t = (ImageButton) findViewById(R.id.goBtn)
        this.u = (EditText) findViewById(R.id.lineNumEdit)
        this.C = (ImageView) findViewById(R.id.panel_button)
        a(this.x, false)
        this.k = SettingEditorActivity.b(this)
        e(this.k)
        this.v.setOnClickListener(this)
        this.q.setOnClickListener(this)
        this.r.setOnClickListener(this)
        this.s.setOnClickListener(this)
        this.w.setOnClickListener(this)
        this.t.setOnClickListener(this)
        this.x.setOnClickListener(this)
        this.y.setOnClickListener(this)
        this.z.setOnClickListener(this)
        this.w.getDrawable().setAlpha(80)
        this.w.setClickable(false)
        this.r.setEnabled(false)
        this.s.setEnabled(false)
        com.gmail.heagoo.neweditor.c cVar = new com.gmail.heagoo.neweditor.c(this)
        this.l.setBackgroundColor(cVar.a())
        this.K.a(cVar.a())
        this.K.b(cVar.b())
        Int iA = cVar.a()
        Color.argb(128, 255 - Color.red(iA), 255 - Color.green(iA), 255 - Color.blue(iA))
        this.K.a((TextWatcher) this)
        this.n.setOnDrawerOpenListener(hf(this))
        this.n.setOnDrawerCloseListener(hg(this))
        this.K.a((a) this)
        this.o.setOnKeyListener(hh(this))
        this.p.setOnKeyListener(hi(this))
        super.a((com.gmail.heagoo.neweditor.s) findViewById(R.id.text_list))
        hr(this, (Byte) 0).execute(new Void[0])
    }

    @Override // android.app.Activity
    fun onPrepareOptionsMenu(Menu menu) {
        return true
    }

    @Override // android.app.Activity
    fun onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle)
    }

    @Override // com.gmail.heagoo.apkeditor.gy, android.app.Activity
    protected fun onResume() {
        super.onResume()
        Int iB = SettingEditorActivity.b(this)
        if (this.k != iB) {
            this.k = iB
            e(iB)
            d()
        }
        if (SettingEditorActivity.e(this)) {
            this.K.a(true)
        } else {
            this.K.a(false)
        }
    }

    @Override // android.app.Activity
    protected fun onSaveInstanceState(Bundle bundle) {
        String str
        super.onSaveInstanceState(bundle)
        bundle.putInt("curFileIndex", this.e)
        bundle.putBoolean("modifySaved", this.g)
        if (this.j == null || !this.j.b()) {
            return
        }
        bundle.putBoolean("docChanged", true)
        try {
            str = com.gmail.heagoo.a.c.a.d(this, "tmp") + com.gmail.heagoo.a.c.a.l(a()) + ".tmp"
        } catch (Exception e) {
            str = a() + ".tmp"
        }
        try {
            this.j.a(str)
            bundle.putString("unsavedFilePath", str)
        } catch (IOException e2) {
        }
    }

    @Override // android.app.Activity
    fun onStop() {
        super.onStop()
    }

    @Override // android.text.TextWatcher
    fun onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }
}
