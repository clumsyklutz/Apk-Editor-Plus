package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SlidingDrawer
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import android.widget.ViewAnimator
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.neweditor.ObEditText
import com.gmail.heagoo.neweditor.ObScrollView
import java.io.IOException
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

class TextEditNormalActivity extends gy implements View.OnClickListener, com.gmail.heagoo.neweditor.ad {
    private static Int n = 300
    private static Int o = 300
    private static Int p = 400
    private static Int q = 100
    private FrameLayout A
    private EditText B
    private HorizontalScrollView C
    private View D
    private ObScrollView E
    private ViewAnimator F
    private SlidingDrawer G
    private EditText H
    private EditText I
    private ImageButton J
    private ImageButton K
    private ImageButton L
    private ImageButton M
    private EditText N
    private ImageView O
    private ImageView P
    private ImageView Q
    private ImageView R
    private ImageView S
    private ToggleButton T
    private ToggleButton U
    private LinearLayout V
    private ImageView W
    private io X
    private Boolean Y
    private Int Z
    private Boolean aa
    private Boolean ab
    private TextView ac
    private View ad
    private View ae
    private View af
    private Int ag
    private Boolean ah
    private Boolean ai
    iq k
    protected ObEditText l
    public Int m
    private Boolean r
    private Boolean s
    private Int t
    private Int u
    private Int v
    private Int w
    private Handler x
    private im y
    private LinearLayout z

    constructor() {
        super(false, true)
        this.r = true
        this.s = false
        this.t = -1
        this.u = -1
        this.w = 20
        this.x = Handler()
        this.k = iq(this)
        this.y = im(this)
        this.Y = true
        this.aa = false
        this.ab = false
        this.ah = true
        this.ai = false
    }

    static /* synthetic */ Int a(TextEditNormalActivity textEditNormalActivity, Int i) {
        textEditNormalActivity.u = -1
        return -1
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

    static /* synthetic */ Unit a(TextEditNormalActivity textEditNormalActivity, Boolean z, Int i) {
        if (z && i == textEditNormalActivity.Z) {
            return
        }
        textEditNormalActivity.Y = z
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textEditNormalActivity.B.getLayoutParams()
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textEditNormalActivity.D.getLayoutParams()
        if (z) {
            if (SettingEditorActivity.e(textEditNormalActivity)) {
                textEditNormalActivity.B.setVisibility(0)
                textEditNormalActivity.D.setVisibility(0)
            }
            String str = ""
            for (Int i2 = 0; i2 < i; i2++) {
                str = str + "9"
            }
            textEditNormalActivity.Z = i
            layoutParams.width = ((Int) textEditNormalActivity.l.getPaint().measureText(str)) + ((Int) TypedValue.applyDimension(1, 6.0f, textEditNormalActivity.getResources().getDisplayMetrics()))
            layoutParams2.width = 1
        } else {
            textEditNormalActivity.B.setVisibility(8)
            textEditNormalActivity.D.setVisibility(8)
            layoutParams.width = 0
            layoutParams2.width = 0
            textEditNormalActivity.Z = 0
        }
        textEditNormalActivity.B.requestLayout()
        textEditNormalActivity.D.requestLayout()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(com.gmail.heagoo.common.i iVar) {
        ey(this, ik(this, iVar), R.string.file_saved).show()
    }

    static /* synthetic */ Boolean a(TextEditNormalActivity textEditNormalActivity, Boolean z) {
        textEditNormalActivity.s = false
        return false
    }

    static /* synthetic */ Int b(TextEditNormalActivity textEditNormalActivity, Int i) {
        textEditNormalActivity.t = -1
        return -1
    }

    static /* synthetic */ Boolean b(TextEditNormalActivity textEditNormalActivity, Boolean z) {
        textEditNormalActivity.ah = false
        return false
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Boolean z) {
        if (this.H.getText().toString().equals("")) {
            return false
        }
        if (f()) {
            this.l.requestFocus()
            return true
        }
        Int selectionStart = this.l.getSelectionStart()
        Int selectionEnd = this.l.getSelectionEnd()
        this.l.setSelection(0)
        if (f()) {
            this.l.requestFocus()
            return true
        }
        this.l.setSelection(selectionStart, selectionEnd)
        if (!z) {
            return false
        }
        Toast.makeText(getApplicationContext(), getString(R.string.not_found), 0).show()
        return false
    }

    private fun c(Boolean z) {
        if (!z) {
            this.l.setMaxWidth(((this.z.getWidth() - this.B.getWidth()) - this.D.getWidth()) - this.G.getWidth())
            return
        }
        Int width = this.z.getWidth()
        Int width2 = this.B.getWidth()
        this.l.setMaxWidth(((width - width2) - this.D.getWidth()) - this.W.getWidth())
    }

    static /* synthetic */ Boolean c(TextEditNormalActivity textEditNormalActivity, Boolean z) {
        textEditNormalActivity.ai = true
        return true
    }

    private fun d(Int i) {
        Byte b2 = 0
        if (this.j.b()) {
            new AlertDialog.Builder(this).setMessage(R.string.save_changes_tip).setPositiveButton(R.string.save, ii(this, i)).setNegativeButton(R.string.donot_save, ih(this, i)).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
        } else {
            this.e += i
            ip(this, b2).execute(new Void[0])
        }
    }

    private fun e(Int i) {
        Int i2 = i <= 40 ? i : 40
        Int i3 = i2 >= 4 ? i2 : 4
        this.l.setTextSize(2, i3)
        this.B.setTextSize(2, i3)
        this.Z = 0
    }

    private fun f() {
        Int i = this.T.isChecked() ? 10 : 8
        if (this.U != null && !this.U.isChecked()) {
            i |= 16
        }
        try {
            Matcher matcher = Pattern.compile(this.H.getText().toString(), i).matcher(this.j.a())
            if (matcher.find(this.l.getSelectionEnd())) {
                this.l.setSelection(matcher.start(), matcher.end())
                return true
            }
            this.l.setSelection(this.l.getSelectionEnd())
            return false
        } catch (Exception e) {
            return false
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun g() {
        try {
            Int iMin = Math.min(this.l.getSelectionStart(), this.l.getSelectionEnd())
            Int iMax = Math.max(this.l.getSelectionStart(), this.l.getSelectionEnd())
            if (iMin != iMax) {
                String string = this.l.getText().subSequence(iMin, iMax).toString()
                String string2 = this.H.getText().toString()
                if ((this.T.isChecked() ? Pattern.compile(string2, 10) : Pattern.compile(string2)).matcher(string).matches()) {
                    this.K.setEnabled(true)
                    this.L.setEnabled(true)
                    return
                }
            }
        } catch (Exception e) {
        }
        this.K.setEnabled(false)
        this.L.setEnabled(false)
    }

    fun g(String str) {
        Array<String> strArrSplit = str.split("/")
        return strArrSplit.length > 2 && strArrSplit[strArrSplit.length + (-2)].startsWith("values")
    }

    static /* synthetic */ Boolean m(TextEditNormalActivity textEditNormalActivity) {
        return false
    }

    static /* synthetic */ Unit n(TextEditNormalActivity textEditNormalActivity) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textEditNormalActivity.V.getLayoutParams()
        layoutParams.setMargins(0, 0, textEditNormalActivity.G.getWidth(), layoutParams.bottomMargin)
        textEditNormalActivity.V.requestLayout()
        if (textEditNormalActivity.f) {
            textEditNormalActivity.c(true)
            textEditNormalActivity.a(false)
        }
        textEditNormalActivity.W.setImageResource(R.drawable.edit_slide_right)
    }

    static /* synthetic */ Unit o(TextEditNormalActivity textEditNormalActivity) {
        textEditNormalActivity.G.setVisibility(8)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textEditNormalActivity.V.getLayoutParams()
        layoutParams.setMargins(0, 0, 0, layoutParams.bottomMargin)
        textEditNormalActivity.V.requestLayout()
        if (textEditNormalActivity.f) {
            textEditNormalActivity.c(false)
            textEditNormalActivity.a(false)
        }
        textEditNormalActivity.W.setImageResource(R.drawable.edit_slide_left)
        textEditNormalActivity.F.setDisplayedChild(0)
    }

    static /* synthetic */ Unit r(TextEditNormalActivity textEditNormalActivity) {
        View currentFocus = textEditNormalActivity.getCurrentFocus()
        if (currentFocus != null) {
            ((InputMethodManager) textEditNormalActivity.getSystemService("input_method")).showSoftInput(currentFocus, 0)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.gy, com.gmail.heagoo.apkeditor.dn
    public final Unit a(Int i, Int i2) {
        Boolean z = false
        if (this.i == 2) {
            Array<String> strArrSplit = this.l.getText().toString().split("\n")
            if (i <= 0) {
                i = 1
            }
            if (i2 > strArrSplit.length) {
                i2 = strArrSplit.length
            }
            if (i <= i2) {
                Int length = 0
                for (Int i3 = 1; i3 < i; i3++) {
                    length += strArrSplit[i3 - 1].length() + 1
                }
                Int length2 = length
                for (Int i4 = i; i4 <= i2; i4++) {
                    length2 += strArrSplit[i4 - 1].length() + 1
                }
                this.l.getText().replace(length, length2, "")
                Toast.makeText(this, String.format(getString(R.string.n_lines_deleted), Integer.valueOf((i2 - i) + 1)), 1).show()
                z = true
            }
        } else if (this.i == 5) {
            Array<String> strArrSplit2 = this.l.getText().toString().split("\n")
            if (i <= 0) {
                i = 1
            }
            if (i2 > strArrSplit2.length) {
                i2 = strArrSplit2.length
            }
            if (i <= i2) {
                StringBuilder sb = StringBuilder()
                Int length3 = 0
                for (Int i5 = 1; i5 < i; i5++) {
                    length3 += strArrSplit2[i5 - 1].length() + 1
                }
                Int length4 = length3
                for (Int i6 = i; i6 <= i2; i6++) {
                    length4 += strArrSplit2[i6 - 1].length() + 1
                }
                Boolean zA = a(strArrSplit2, i, i2, "#")
                if (zA) {
                    for (Int i7 = i; i7 <= i2; i7++) {
                        Int iIndexOf = strArrSplit2[i7 - 1].indexOf(35)
                        if (iIndexOf > 0) {
                            sb.append(strArrSplit2[i7 - 1].substring(0, iIndexOf))
                        }
                        sb.append(strArrSplit2[i7 - 1].substring(iIndexOf + 1))
                        sb.append("\n")
                    }
                } else {
                    for (Int i8 = i; i8 <= i2; i8++) {
                        sb.append("#")
                        sb.append(strArrSplit2[i8 - 1])
                        sb.append("\n")
                    }
                }
                this.l.getText().replace(length3, length4, sb.toString())
                Toast.makeText(this, String.format(zA ? getString(R.string.n_lines_uncommented) : getString(R.string.n_lines_commented), Integer.valueOf((i2 - i) + 1)), 1).show()
                z = true
            }
        }
        if (z) {
            this.j.a(true)
            d()
        }
    }

    protected final Unit a(Int i, Int i2, Boolean z) {
        if (this.r) {
            if (this.u == -1 || i < this.u) {
                this.u = i
            }
            if (this.t == -1 || i2 > this.t) {
                this.t = i2
            }
            this.s = z
            this.x.removeCallbacks(this.y)
            if (z) {
                this.x.postDelayed(this.y, p)
            } else {
                this.x.postDelayed(this.y, q)
            }
        }
    }

    protected final Unit a(Boolean z) {
        if (this.ag > 0) {
            Array<String> strArrSplit = this.l.getText().toString().split("\n")
            Int length = this.ag > strArrSplit.length ? strArrSplit.length : this.ag
            Int length2 = 0
            for (Int i = 0; i < length - 1; i++) {
                length2 += strArrSplit[i].length() + 1
            }
            this.l.setSelection(length2)
            this.l.requestFocus()
            View currentFocus = getCurrentFocus()
            if (currentFocus != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0)
            }
            this.ag = -1
        }
        if (!this.Y) {
            if (this.C != null && String.valueOf(this.m).length() != String.valueOf(this.l.getLineCount()).length()) {
                this.C.requestLayout()
            }
            this.m = this.l.getLineCount()
            return
        }
        this.x.removeCallbacks(this.k)
        if (z) {
            this.x.postDelayed(this.k, o)
        } else {
            this.x.postDelayed(this.k, 0L)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.gr
    public final Unit b(Int i) {
        if (i > 0) {
            Array<String> strArrSplit = this.l.getText().toString().split("\n")
            if (i > strArrSplit.length) {
                i = strArrSplit.length
            }
            Int length = 0
            for (Int i2 = 0; i2 < i - 1; i2++) {
                length += strArrSplit[i2].length() + 1
            }
            this.N.setText("")
            this.l.setSelection(length)
            this.l.requestFocus()
        }
    }

    @Override // com.gmail.heagoo.neweditor.ad
    public final Unit b(Int i, Int i2) {
        if (i == i2) {
            a(this.Q, false)
        } else {
            a(this.Q, true)
        }
        if (this.G.isOpened()) {
            g()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.gy
    protected final Unit c(Int i) {
        if (this.l != null) {
            this.l.getText().replace(this.l.getSelectionStart(), this.l.getSelectionEnd(), StringBuilder().append(b((String) null).charAt(i)).toString())
            this.l.requestFocus()
        }
    }

    protected final synchronized Unit d() {
        Boolean zB
        com.gmail.heagoo.neweditor.e eVar = this.j
        if (eVar != null && this.ab != (zB = eVar.b())) {
            if (zB) {
                a(this.P, true)
            } else {
                a(this.P, false)
            }
            this.P.invalidate()
            this.ab = zB
        }
    }

    protected final synchronized Unit e() {
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        if (this.j == null || this.j.b()) {
            new AlertDialog.Builder(this).setMessage(R.string.save_changes_tip).setPositiveButton(R.string.save, ia(this)).setNegativeButton(R.string.donot_save, hz(this)).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
        } else {
            finish()
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int length = 0
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
            if (this.G.getVisibility() == 8) {
                this.G.animateOpen()
                this.G.setVisibility(0)
            } else {
                this.G.close()
                this.G.setVisibility(8)
                this.l.requestFocus()
            }
            Int iMin = Math.min(this.l.getSelectionStart(), this.l.getSelectionEnd())
            Int iMax = Math.max(this.l.getSelectionStart(), this.l.getSelectionEnd())
            if (iMin < iMax) {
                String string = this.l.getText().subSequence(iMin, iMax).toString()
                if (!string.contains("\n")) {
                    this.H.setText(string)
                }
            } else if (this.f1129a != null && "".equals(this.H.getText().toString())) {
                this.H.setText(this.f1129a)
            }
            this.H.requestFocus()
            return
        }
        if (id == R.id.findBtn) {
            b(true)
            return
        }
        if (id == R.id.replaceBtn) {
            this.l.getEditableText().replace(this.l.getSelectionStart(), this.l.getSelectionEnd(), this.I.getText())
            b(true)
            return
        }
        if (id == R.id.replaceAllBtn) {
            Int i = this.T.isChecked() ? 10 : 8
            if (this.U != null && !this.U.isChecked()) {
                i |= 16
            }
            try {
                Matcher matcher = Pattern.compile(this.H.getText().toString(), i).matcher(this.j.a())
                ArrayList arrayList = ArrayList()
                Int iEnd = 0
                while (matcher.find(iEnd)) {
                    Int iStart = matcher.start()
                    iEnd = matcher.end()
                    arrayList.add(il(iStart, iEnd, (Byte) 0))
                }
                if (arrayList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_found), 0).show()
                    return
                }
                String string2 = this.I.getText().toString()
                Int i2 = 0
                while (i2 < arrayList.size()) {
                    il ilVar = (il) arrayList.get(i2)
                    this.l.getEditableText().replace(ilVar.f1190a + length, ilVar.f1191b + length, string2)
                    i2++
                    length = (string2.length() - (ilVar.f1191b - ilVar.f1190a)) + length
                }
                Toast.makeText(getApplicationContext(), String.format(getString(R.string.replace_all_ret), Integer.valueOf(arrayList.size())), 0).show()
                return
            } catch (Exception e) {
                return
            }
        }
        if (id == R.id.goBtn) {
            String strTrim = this.N.getText().toString().trim()
            if (strTrim.equals("")) {
                this.l.getText().insert(this.l.getSelectionStart(), "\n")
                this.l.requestFocus()
                return
            } else {
                try {
                    b(Integer.valueOf(strTrim).intValue())
                    return
                } catch (Exception e2) {
                    return
                }
            }
        }
        if (id == R.id.saveBtn) {
            a((com.gmail.heagoo.common.i) null)
            return
        }
        if (id == R.id.copyBtn) {
            String strSubstring = this.l.getText().toString().substring(this.l.getSelectionStart(), this.l.getSelectionEnd())
            if (strSubstring == null || strSubstring.equals("")) {
                return
            }
            a.c(this, strSubstring)
            if (strSubstring.contains("\n")) {
                Toast.makeText(this, R.string.selected_str_copied, 0).show()
                return
            } else {
                Toast.makeText(this, String.format(getString(R.string.copied_to_clipboard), strSubstring), 0).show()
                return
            }
        }
        if (id != R.id.pasteBtn) {
            if (id == R.id.menu_methods) {
                b(view)
            }
        } else {
            String strB = a.b(this)
            if (strB == null) {
                Toast.makeText(this, R.string.clipboard_no_text, 0).show()
            } else {
                this.l.getText().replace(this.l.getSelectionStart(), this.l.getSelectionEnd(), strB)
                this.l.requestFocus()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.gy, android.app.Activity
    @SuppressLint({"NewApi"})
    fun onCreate(Bundle bundle) {
        Byte b2 = 0
        super.onCreate(bundle)
        this.ac = (TextView) findViewById(R.id.filename)
        this.ad = findViewById(R.id.menu_previous)
        this.ae = findViewById(R.id.menu_next)
        this.af = findViewById(R.id.menu_methods)
        this.ad.setOnClickListener(this)
        this.ae.setOnClickListener(this)
        this.af.setOnClickListener(this)
        this.z = (LinearLayout) findViewById(R.id.editorLayout)
        this.A = (FrameLayout) findViewById(R.id.center_layout)
        this.B = (EditText) findViewById(R.id.lineNumbers)
        this.B.setFilters(new InputFilter[0])
        this.l = (ObEditText) findViewById(R.id.editor)
        this.l.setFilters(new InputFilter[0])
        this.D = findViewById(R.id.divider)
        this.E = (ObScrollView) findViewById(R.id.editorScrollview)
        this.C = (HorizontalScrollView) findViewById(R.id.hScrollView)
        this.F = (ViewAnimator) findViewById(R.id.searchAnimator)
        this.G = (SlidingDrawer) findViewById(R.id.sliding_drawer)
        this.H = (EditText) findViewById(R.id.findEdit)
        this.I = (EditText) findViewById(R.id.replaceEdit)
        this.J = (ImageButton) findViewById(R.id.findBtn)
        this.K = (ImageButton) findViewById(R.id.replaceBtn)
        this.L = (ImageButton) findViewById(R.id.replaceAllBtn)
        this.O = (ImageView) findViewById(R.id.openFindBtn)
        this.T = (ToggleButton) findViewById(R.id.checkBoxIgnoreCase)
        this.U = (ToggleButton) findViewById(R.id.checkBoxRegexp)
        this.P = (ImageView) findViewById(R.id.saveBtn)
        this.Q = (ImageView) findViewById(R.id.copyBtn)
        this.R = (ImageView) findViewById(R.id.pasteBtn)
        this.S = (ImageView) findViewById(R.id.moreBtn)
        this.M = (ImageButton) findViewById(R.id.goBtn)
        this.N = (EditText) findViewById(R.id.lineNumEdit)
        this.V = this.z
        this.W = (ImageView) findViewById(R.id.panel_button)
        a(this.Q, false)
        this.l.b(this.f)
        this.l.setInputType(720897)
        this.v = SettingEditorActivity.b(this)
        e(this.v)
        this.O.setOnClickListener(this)
        this.J.setOnClickListener(this)
        this.K.setOnClickListener(this)
        this.L.setOnClickListener(this)
        this.P.setOnClickListener(this)
        this.M.setOnClickListener(this)
        this.Q.setOnClickListener(this)
        this.R.setOnClickListener(this)
        this.S.setOnClickListener(this)
        this.P.getDrawable().setAlpha(80)
        this.P.setClickable(false)
        this.K.setEnabled(false)
        this.L.setEnabled(false)
        com.gmail.heagoo.neweditor.c cVar = new com.gmail.heagoo.neweditor.c(this)
        this.z.setBackgroundColor(cVar.a())
        this.A.setBackgroundColor(cVar.a())
        this.B.setBackgroundColor(cVar.a())
        this.l.setBackgroundColor(cVar.a())
        this.l.setTextColor(cVar.b())
        this.B.setTextColor(cVar.b())
        this.D.setBackgroundColor(cVar.b())
        ObEditText obEditText = this.l
        Int iA = cVar.a()
        obEditText.a(Color.argb(128, 255 - Color.red(iA), 255 - Color.green(iA), 255 - Color.blue(iA)))
        this.B.setOnLongClickListener(null)
        this.G.setOnDrawerOpenListener(hy(this))
        this.G.setOnDrawerCloseListener(ic(this))
        this.l.a(this)
        this.X = io(this, b2)
        this.l.addTextChangedListener(this.X)
        this.E.a(id(this))
        this.H.setOnKeyListener(ie(this))
        this.I.setOnKeyListener(Cif(this))
        this.l.setOnTouchListener(ig(this))
        super.a((com.gmail.heagoo.neweditor.s) this.l)
        ip(this, b2).execute(new Void[0])
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
        if (this.v != iB) {
            this.v = iB
            e(iB)
            a(true)
            a(-1, -1, false)
            d()
        }
        if (!SettingEditorActivity.e(this)) {
            this.B.setVisibility(8)
            this.D.setVisibility(8)
            return
        }
        this.B.setVisibility(0)
        this.D.setVisibility(0)
        if (this.ai) {
            a(false)
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
            str = a.d(this, "tmp") + a.l(a()) + ".tmp"
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
}
