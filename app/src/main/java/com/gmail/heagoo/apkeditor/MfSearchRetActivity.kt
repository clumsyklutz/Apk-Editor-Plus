package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Iterator

class MfSearchRetActivity extends Activity implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private String f771a

    /* renamed from: b, reason: collision with root package name */
    private ArrayList f772b
    private ArrayList c
    private ArrayList d = ArrayList()

    private fun a(String str) {
        Char cCharAt
        StringBuffer stringBuffer = StringBuffer()
        for (Int i = 0; i < str.length() && ((cCharAt = str.charAt(i)) == ' ' || cCharAt == '\t'); i++) {
            stringBuffer.append(cCharAt)
        }
        return stringBuffer.toString()
    }

    private fun a() throws IOException {
        try {
            FileOutputStream fileOutputStream = FileOutputStream(this.f771a + ".tmp")
            FileInputStream fileInputStream = FileInputStream(this.f771a)
            BufferedReader bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            ArrayList arrayList = ArrayList()
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                arrayList.add(line)
            }
            for (Int i = 0; i < this.f772b.size(); i++) {
                Int iIntValue = ((Integer) this.f772b.get(i)).intValue() - 1
                arrayList.set(iIntValue, a((String) arrayList.get(iIntValue)) + ((String) this.c.get(i)).trim())
            }
            Iterator it = arrayList.iterator()
            while (it.hasNext()) {
                fileOutputStream.write(((String) it.next()).getBytes())
                fileOutputStream.write(10)
            }
            bufferedReader.close()
            fileInputStream.close()
            fileOutputStream.close()
            File(this.f771a + ".tmp").renameTo(File(this.f771a))
            return true
        } catch (Exception e) {
            e.printStackTrace()
            Toast.makeText(this, e.getMessage(), 0).show()
            return false
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_close) {
            finish()
            return
        }
        if (id == R.id.btn_save) {
            Boolean z = false
            for (Int i = 0; i < this.d.size(); i++) {
                String string = ((EditText) this.d.get(i)).getText().toString()
                if (!((String) this.c.get(i)).equals(string)) {
                    this.c.set(i, string)
                    z = true
                }
            }
            if (!z) {
                Toast.makeText(this, R.string.no_change_detected, 0).show()
            } else if (a()) {
                Toast.makeText(this, R.string.succeed, 0).show()
                setResult(1)
                finish()
            }
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_mf_searchret)
        Bundle extras = getIntent().getExtras()
        this.f771a = extras.getString("xmlPath")
        this.f772b = extras.getIntegerArrayList("lineIndexs")
        this.c = extras.getStringArrayList("lineContents")
        ((TextView) findViewById(R.id.title)).setText(String.format(getResources().getString(R.string.mf_search_ret), Integer.valueOf(this.f772b.size())))
        ((Button) findViewById(R.id.btn_save)).setOnClickListener(this)
        findViewById(R.id.btn_close).setOnClickListener(this)
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.result_layout)
        for (Int i = 0; i < this.c.size(); i++) {
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.item_mf_search, (ViewGroup) null)
            EditText editText = (EditText) viewInflate.findViewById(R.id.edittext_mf)
            editText.setText((CharSequence) this.c.get(i))
            linearLayout.addView(viewInflate, 0)
            this.d.add(editText)
        }
    }
}
