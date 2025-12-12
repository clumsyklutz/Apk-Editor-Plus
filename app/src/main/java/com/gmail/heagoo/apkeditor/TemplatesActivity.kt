package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.database.SQLException
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

class TemplatesActivity extends Activity implements View.OnClickListener {
    private String TAG
    private gzb adapter
    String content
    Context context
    private gza db
    Button delete_bnt
    EditText editText_content
    EditText editText_title
    private Int position
    Button save_btn
    private TextView snackbar_desc
    private TextView snackbar_title
    Spinner spinner
    List templatesList = ArrayList()
    ListView templates_listView
    TextView textView
    String title

    private fun AB() {
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        ((TextView) viewInflate.findViewById(android.R.id.title)).setText(R.string.templates)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun createTemplates(String str, String str2) {
        gzd text = this.db.getText(this.db.insertTemplates(str, str2))
        if (text != null) {
            this.templatesList.add(0, text)
            this.adapter.notifyDataSetChanged()
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws SQLException {
        switch (view.getId()) {
            case R.id.templates_save /* 2131559006 */:
                if (!this.editText_content.getText().toString().isEmpty()) {
                    this.title = this.editText_title.getText().toString()
                    createTemplates(this.title, this.editText_content.getText().toString())
                    this.editText_title.setText("")
                    this.editText_content.setText("")
                    break
                } else {
                    this.editText_content.setError(getResources().getString(R.string.empty_input_tip))
                    break
                }
            case R.id.templates_delete /* 2131559007 */:
                this.db.deleteTabble()
                this.db.onUpgrade(this.db.getWritableDatabase(), 1, 2)
                this.adapter.clear()
                this.adapter.notifyDataSetChanged()
                Toast.makeText(this, R.string.templates_toast_delete_all, 0).show()
                break
        }
    }

    @Override // android.app.Activity
    fun onContextItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 0:
                AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()
                Log.d(this.TAG, StringBuffer().append("removing item pos=").append(adapterContextMenuInfo.position).toString())
                this.db.deleteTemplates((gzd) this.templatesList.get(adapterContextMenuInfo.position))
                Toast.makeText(this, R.string.templates_toast_delete, 0).show()
                this.position = adapterContextMenuInfo.position
                this.templatesList.remove(adapterContextMenuInfo.position)
                this.adapter.notifyDataSetChanged()
                return true
            case 1:
                AdapterView.AdapterContextMenuInfo adapterContextMenuInfo2 = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()
                Log.d(this.TAG, StringBuffer().append("copy item pos=").append(adapterContextMenuInfo2.position).toString())
                ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", ((gzd) this.templatesList.get(adapterContextMenuInfo2.position)).getContent()))
                Toast.makeText(this, R.string.templates_toast_clipboard, 0).show()
                return true
            default:
                return super.onContextItemSelected(menuItem)
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        getWindow().setSoftInputMode(2)
        setContentView(R.layout.activity_templates)
        AB()
        this.db = gza(this)
        this.templatesList.addAll(this.db.getAllTemplates())
        this.templates_listView = (ListView) findViewById(R.id.templates_list_view)
        this.adapter = gzb(this, this.templatesList)
        this.templates_listView.setAdapter((ListAdapter) this.adapter)
        registerForContextMenu(this.templates_listView)
        this.editText_title = (EditText) findViewById(R.id.templates_edittext_title)
        this.editText_content = (EditText) findViewById(R.id.templates_edittext_content)
        this.save_btn = (Button) findViewById(R.id.templates_save)
        this.delete_bnt = (Button) findViewById(R.id.templates_delete)
        this.save_btn.setOnClickListener(this)
        this.delete_bnt.setOnClickListener(this)
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    fun onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        switch (view.getId()) {
            case R.id.templates_list_view /* 2131559008 */:
                contextMenu.add(0, 0, 0, R.string.remove)
                contextMenu.add(0, 1, 0, android.R.string.copy)
                break
        }
    }

    @Override // android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish()
                return true
            default:
                return false
        }
    }
}
