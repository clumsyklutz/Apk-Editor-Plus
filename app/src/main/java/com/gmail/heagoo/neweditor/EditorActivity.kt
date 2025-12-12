package com.gmail.heagoo.neweditor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SlidingDrawer
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import android.widget.ViewAnimator
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class EditorActivity extends Activity implements View.OnClickListener {
    private static Int d = 300
    private static Int e = 300
    private static Int f = 400
    private static Int g = 100
    private EditText A
    private ImageButton B
    private ImageButton C
    private ImageView D
    private ImageView E
    private ImageView F
    private ToggleButton G
    private ToggleButton H
    private LinearLayout I
    private ImageView J
    private ScrollView K
    private String L
    private String M
    private String N
    private Boolean O
    private e P
    private Int R
    private Int U
    private Int V
    private Int W

    /* renamed from: a, reason: collision with root package name */
    protected ObEditText f1488a

    /* renamed from: b, reason: collision with root package name */
    public Int f1489b
    private Int m
    private Boolean r
    private LinearLayout s
    private FrameLayout t
    private EditText u
    private View v
    private ObScrollView w
    private ViewAnimator x
    private SlidingDrawer y
    private EditText z
    private Boolean h = true
    private Boolean i = false
    private Int j = -1
    private Int k = -1
    private Boolean l = true
    private Int n = 50
    private Handler o = Handler()
    private r p = r(this)
    private p q = p(this)
    private Boolean Q = true
    private Boolean S = false
    private Boolean T = false
    protected Boolean c = false

    static /* synthetic */ Int a(EditorActivity editorActivity, Int i) {
        editorActivity.k = -1
        return -1
    }

    private fun a(Int i) {
        Int i2 = i <= 40 ? i : 40
        Int i3 = i2 >= 4 ? i2 : 4
        this.f1488a.setTextSize(2, i3)
        this.u.setTextSize(2, i3)
        this.R = 0
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Boolean z, Int i) {
        if (z && i == this.R) {
            return
        }
        this.Q = z
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.u.getLayoutParams()
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.v.getLayoutParams()
        if (z) {
            this.u.setVisibility(0)
            this.v.setVisibility(0)
            String str = ""
            for (Int i2 = 0; i2 < i; i2++) {
                str = String.valueOf(str) + "9"
            }
            this.R = i
            layoutParams.width = ((Int) this.f1488a.getPaint().measureText(str)) + ((Int) TypedValue.applyDimension(1, 6.0f, getResources().getDisplayMetrics()))
            layoutParams2.width = 1
        } else {
            this.u.setVisibility(8)
            this.v.setVisibility(8)
            layoutParams.width = 0
            layoutParams2.width = 0
            this.R = 0
        }
        this.u.requestLayout()
        this.v.requestLayout()
    }

    static /* synthetic */ Boolean a(EditorActivity editorActivity, Boolean z) {
        editorActivity.i = false
        return false
    }

    static /* synthetic */ Int b(EditorActivity editorActivity, Int i) {
        editorActivity.j = -1
        return -1
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(Boolean z) {
        if (this.z.getText().toString().equals("")) {
            return false
        }
        if (e()) {
            this.f1488a.requestFocus()
            return true
        }
        Int selectionStart = this.f1488a.getSelectionStart()
        Int selectionEnd = this.f1488a.getSelectionEnd()
        this.f1488a.setSelection(0)
        if (e()) {
            this.f1488a.requestFocus()
            return true
        }
        this.f1488a.setSelection(selectionStart, selectionEnd)
        if (!z) {
            return false
        }
        Toast.makeText(getApplicationContext(), getString(this.W), 0).show()
        return false
    }

    private fun d(Boolean z) {
        if (!z) {
            this.f1488a.setMaxWidth(((this.s.getWidth() - this.u.getWidth()) - this.v.getWidth()) - this.y.getWidth())
            return
        }
        Int width = this.s.getWidth()
        Int width2 = this.u.getWidth()
        Int width3 = this.v.getWidth()
        this.f1488a.setMaxWidth(((width - width2) - width3) - this.J.getWidth())
    }

    private fun e() {
        Int i = this.G.isChecked() ? 10 : 8
        if (this.H != null && !this.H.isChecked()) {
            i |= 16
        }
        try {
            Matcher matcher = Pattern.compile(this.z.getText().toString(), i).matcher(this.P.a().toString())
            if (matcher.find(this.f1488a.getSelectionEnd())) {
                this.f1488a.setSelection(matcher.start(), matcher.end())
                return true
            }
            this.f1488a.setSelection(this.f1488a.getSelectionEnd())
            return false
        } catch (Exception e2) {
            return false
        }
    }

    static /* synthetic */ Unit i(EditorActivity editorActivity) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) editorActivity.I.getLayoutParams()
        layoutParams.setMargins(0, 0, editorActivity.y.getWidth(), layoutParams.bottomMargin)
        editorActivity.I.requestLayout()
        if (editorActivity.l) {
            editorActivity.d(true)
            editorActivity.a(false)
        }
        editorActivity.J.setImageResource(R.drawable.edit_slide_right)
    }

    static /* synthetic */ Unit j(EditorActivity editorActivity) {
        editorActivity.y.setVisibility(8)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) editorActivity.I.getLayoutParams()
        layoutParams.setMargins(0, 0, 0, layoutParams.bottomMargin)
        editorActivity.I.requestLayout()
        if (editorActivity.l) {
            editorActivity.d(false)
            editorActivity.a(false)
        }
        editorActivity.J.setImageResource(R.drawable.edit_slide_left)
        editorActivity.x.setDisplayedChild(0)
    }

    static /* synthetic */ Unit l(EditorActivity editorActivity) {
        try {
            Int iMin = Math.min(editorActivity.f1488a.getSelectionStart(), editorActivity.f1488a.getSelectionEnd())
            Int iMax = Math.max(editorActivity.f1488a.getSelectionStart(), editorActivity.f1488a.getSelectionEnd())
            if (iMin != iMax) {
                String string = editorActivity.f1488a.getText().subSequence(iMin, iMax).toString()
                String string2 = editorActivity.z.getText().toString()
                if ((editorActivity.G.isChecked() ? Pattern.compile(string2, 10) : Pattern.compile(string2)).matcher(string).matches()) {
                    editorActivity.C.setEnabled(true)
                    return
                }
            }
        } catch (Exception e2) {
        }
        editorActivity.C.setEnabled(false)
    }

    protected final synchronized Unit a() {
        Boolean zB = this.P.b()
        if (this.T != zB) {
            if (zB) {
                this.E.getDrawable().setAlpha(255)
                this.E.setClickable(true)
            } else {
                this.E.getDrawable().setAlpha(80)
                this.E.setClickable(false)
            }
            this.E.invalidate()
            this.T = zB
        }
    }

    protected final Unit a(Int i, Int i2, Boolean z) {
        if (this.h) {
            if (this.k == -1 || i < this.k) {
                this.k = i
            }
            if (this.j == -1 || i2 > this.j) {
                this.j = i2
            }
            this.i = z
            this.o.removeCallbacks(this.q)
            if (this.r) {
                this.o.postDelayed(this.q, d)
            } else if (z) {
                this.o.postDelayed(this.q, f)
            } else {
                this.o.postDelayed(this.q, g)
            }
        }
    }

    protected final Unit a(Boolean z) {
        if (!this.Q) {
            this.f1489b = this.f1488a.getLineCount()
            return
        }
        this.o.removeCallbacks(this.p)
        if (z) {
            this.o.postDelayed(this.p, e)
        } else {
            this.o.postDelayed(this.p, 0L)
        }
    }

    protected final synchronized Unit b() {
    }

    protected final Unit b(Boolean z) {
        e eVar = this.P
        this.r = true
        this.f1488a.setText(eVar.a())
        this.r = false
        Handler handler = Handler()
        this.f1488a.a(0, 0)
        handler.postDelayed(g(this, eVar), 400L)
        a(true)
        b()
        a()
    }

    protected final Unit c() {
        com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.appdm.util.FileCopyUtil", "copyBack", new Array<Class>{Context.class, String.class, String.class, Boolean.TYPE}, new Array<Object>{this, this.L, this.M, Boolean.valueOf(this.O)})
    }

    protected final Unit d() {
        Intent intent = Intent()
        intent.putExtra("xmlPath", this.L)
        setResult(1, intent)
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id != R.id.openFindBtn) {
            if (id == R.id.findBtn) {
                c(true)
                return
            }
            if (id == R.id.replaceBtn) {
                this.f1488a.getEditableText().replace(this.f1488a.getSelectionStart(), this.f1488a.getSelectionEnd(), this.A.getText())
                c(true)
                return
            } else {
                if (id == R.id.saveBtn) {
                    new com.gmail.heagoo.common.p(this, o(this), this.V).show()
                    return
                }
                return
            }
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        if (Build.VERSION.SDK_INT >= 11) {
            defaultSharedPreferences.getBoolean("hideDocumentList", false)
        }
        if (this.y.getVisibility() == 8) {
            this.y.animateOpen()
            this.y.setVisibility(0)
        } else {
            this.y.close()
            this.y.setVisibility(8)
            this.f1488a.requestFocus()
        }
        Int iMin = Math.min(this.f1488a.getSelectionStart(), this.f1488a.getSelectionEnd())
        Int iMax = Math.max(this.f1488a.getSelectionStart(), this.f1488a.getSelectionEnd())
        if (iMin < iMax) {
            String string = this.f1488a.getText().subSequence(iMin, iMax).toString()
            if (!string.contains("\n")) {
                this.z.setText(string)
            }
        }
        this.z.requestFocus()
    }

    @Override // android.app.Activity
    @SuppressLint({"NewApi"})
    fun onCreate(Bundle bundle) {
        String strSubstring
        Int iLastIndexOf
        super.onCreate(bundle)
        getWindow().requestFeature(1)
        this.l = true
        if (this.l) {
            setContentView(R.layout.editorutil_main)
        } else {
            setContentView(R.layout.editorutil_main)
        }
        try {
            Bundle extras = getIntent().getExtras()
            this.L = extras.getString("filePath")
            this.M = extras.getString("realFilePath")
            this.N = extras.getString("syntaxFileName")
            this.O = extras.getBoolean("isRootMode")
            Array<Int> intArray = extras.getIntArray("resourceIds")
            this.U = intArray[0]
            this.V = intArray[1]
            this.W = intArray[2]
            if (bundle != null) {
                this.c = bundle.getBoolean("modifySaved", false)
                if (this.c) {
                    d()
                }
            }
            this.P = e(this, File(this.L), this.N)
            try {
                this.P.a(this, this.L, this.U)
            } catch (IOException e2) {
                Toast.makeText(this, "Failed to open " + this.L, 1).show()
                e2.printStackTrace()
            }
            TextView textView = (TextView) findViewById(R.id.filename)
            if (this.M == null || (iLastIndexOf = this.M.lastIndexOf(47)) == -1) {
                Int iLastIndexOf2 = this.L.lastIndexOf(47)
                strSubstring = iLastIndexOf2 != -1 ? this.L.substring(iLastIndexOf2 + 1) : this.L
            } else {
                strSubstring = this.M.substring(iLastIndexOf + 1)
            }
            textView.setText(strSubstring)
            findViewById(R.id.editorView)
            this.s = (LinearLayout) findViewById(R.id.editorLayout)
            this.t = (FrameLayout) findViewById(R.id.center_layout)
            this.u = (EditText) findViewById(R.id.lineNumbers)
            this.f1488a = (ObEditText) findViewById(R.id.editor)
            this.v = findViewById(R.id.divider)
            this.w = (ObScrollView) findViewById(R.id.editorScrollview)
            this.x = (ViewAnimator) findViewById(R.id.searchAnimator)
            this.y = (SlidingDrawer) findViewById(R.id.sliding_drawer)
            this.z = (EditText) findViewById(R.id.findEdit)
            this.A = (EditText) findViewById(R.id.replaceEdit)
            this.B = (ImageButton) findViewById(R.id.findBtn)
            this.C = (ImageButton) findViewById(R.id.replaceBtn)
            this.D = (ImageView) findViewById(R.id.openFindBtn)
            this.G = (ToggleButton) findViewById(R.id.checkBoxIgnoreCase)
            this.H = (ToggleButton) findViewById(R.id.checkBoxRegexp)
            this.E = (ImageView) findViewById(R.id.saveBtn)
            this.F = (ImageView) findViewById(R.id.configBtn)
            this.I = this.s
            this.J = (ImageView) findViewById(R.id.panel_button)
            this.C = (ImageButton) findViewById(R.id.replaceBtn)
            this.K = this.w
            this.f1488a.b(this.l)
            this.f1488a.setInputType(721041)
            this.m = 12
            a(this.m)
            String strA = this.P.a()
            if (strA != null) {
                this.f1488a.setText(strA)
                a(0, strA.length(), true)
            }
            this.D.setOnClickListener(this)
            this.B.setOnClickListener(this)
            this.C.setOnClickListener(this)
            this.E.setOnClickListener(this)
            this.F.setOnClickListener(this)
            this.E.getDrawable().setAlpha(80)
            this.E.setClickable(false)
            this.C.setEnabled(false)
            c cVar = c(this)
            this.s.setBackgroundColor(cVar.a())
            this.t.setBackgroundColor(cVar.a())
            this.u.setBackgroundColor(cVar.a())
            this.f1488a.setBackgroundColor(cVar.a())
            this.f1488a.setTextColor(cVar.b())
            this.u.setTextColor(cVar.b())
            this.v.setBackgroundColor(cVar.b())
            ObEditText obEditText = this.f1488a
            Int iA = cVar.a()
            obEditText.a(Color.argb(128, 255 - Color.red(iA), 255 - Color.green(iA), 255 - Color.blue(iA)))
            this.u.setOnLongClickListener(null)
        } catch (Exception e3) {
            Toast.makeText(this, "failed", 0).show()
            finish()
        }
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        this.y.setOnDrawerOpenListener(h(this))
        this.y.setOnDrawerCloseListener(i(this))
        this.f1488a.a(j(this))
        this.f1488a.addTextChangedListener(k(this))
        this.w.a(l(this))
        this.z.setOnKeyListener(m(this))
        this.A.setOnKeyListener(n(this))
        if (this.m != 12) {
            this.m = 12
            a(12)
        }
        a(true, r.a(this.P.a().split("\n").length + 1))
        Handler().postDelayed(f(this), 400L)
    }

    @Override // android.app.Activity
    protected fun onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putBoolean("modifySaved", this.c)
    }

    @Override // android.app.Activity
    protected fun onStart() {
        super.onStart()
    }
}
