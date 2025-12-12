package ru.zeratul.preference

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.DialogPreference
import android.support.v4.provider.FontsContractCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ColorPreference extends DialogPreference {

    /* renamed from: a, reason: collision with root package name */
    private a f1618a

    /* renamed from: b, reason: collision with root package name */
    private Integer f1619b

    constructor(Context context) {
        this(context, (AttributeSet) null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    private fun a() {
        return getSharedPreferences().contains(getKey()) ? Integer.valueOf(getPersistedInt(-7829368)) : this.f1619b
    }

    private fun b(Integer num) {
        if (num == null) {
            c()
        } else {
            persistInt(num.intValue())
        }
        notifyChanged()
    }

    private fun c() {
        if (shouldPersist()) {
            getSharedPreferences().edit().remove(getKey()).commit()
        }
    }

    private fun d(String str) {
        if (str.charAt(0) != '#' || str.length() > "#argb".length()) {
            return str
        }
        String string = "#"
        for (Int i = 1; i < str.length(); i++) {
            string = StringBuffer().append(StringBuffer().append(string).append(str.charAt(i)).toString()).append(str.charAt(i)).toString()
        }
        return string
    }

    @Override // android.preference.Preference
    protected fun onBindView(View view) {
        Integer numA = a()
        setSummary("#" + String.format("%02X%02X%02X%02X", Integer(Color.alpha(numA.intValue())), Integer(Color.red(numA.intValue())), Integer(Color.green(numA.intValue())), Integer(Color.blue(numA.intValue()))))
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.widget_frame)
        viewGroup.setVisibility(0)
        viewGroup.removeAllViews()
        LayoutInflater.from(getContext()).inflate(isEnabled() ? com.gmail.heagoo.apkeditor.pro.R.layout.cpv_thumb : com.gmail.heagoo.apkeditor.pro.R.layout.cpv_thumb_disabled, viewGroup)
        View viewFindViewById = viewGroup.findViewById(com.gmail.heagoo.apkeditor.pro.R.id.image)
        if (numA == null) {
            numA = this.f1619b
        }
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(numA == null ? 8 : 0)
            View viewFindViewById2 = viewFindViewById.findViewById(com.gmail.heagoo.apkeditor.pro.R.id.result_image)
            Drawable drawableMutate = viewFindViewById2.getBackground().mutate()
            drawableMutate.setTint(numA != null ? numA.intValue() : 0)
            viewFindViewById2.setBackground(drawableMutate)
        }
        super.onBindView(view)
    }

    @Override // android.preference.DialogPreference, android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        switch (i) {
            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR /* -3 */:
                if (callChangeListener((Object) null)) {
                    b((Integer) null)
                    break
                }
                break
            case -2:
                dialogInterface.dismiss()
                break
            case -1:
                Int i2 = this.f1618a.i()
                if (callChangeListener(Integer(i2))) {
                    b(Integer(i2))
                    break
                }
                break
        }
    }

    @Override // android.preference.Preference
    protected fun onGetDefaultValue(TypedArray typedArray, Int i) {
        if (typedArray.peekValue(i) == null || typedArray.peekValue(i).type != 3) {
            this.f1619b = Integer.valueOf(typedArray.getColor(i, -7829368))
        } else {
            this.f1619b = Integer.valueOf(Color.parseColor(d(typedArray.getString(i))))
        }
        return this.f1619b
    }

    @Override // android.preference.DialogPreference
    protected fun onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder)
        this.f1618a = a(getContext())
        this.f1618a.h(getPersistedInt(this.f1619b == null ? -7829368 : this.f1619b.intValue()))
        builder.setTitle((CharSequence) null)
        builder.setView(this.f1618a)
        builder.setPositiveButton(com.gmail.heagoo.apkeditor.pro.R.string.set, this)
        builder.setNegativeButton(R.string.cancel, this)
        builder.setNeutralButton(com.gmail.heagoo.apkeditor.pro.R.string.reset, this)
    }

    @Override // android.preference.Preference
    protected fun onSetInitialValue(Boolean z, Object obj) {
        b(z ? a() : this.f1619b)
    }

    @Override // android.preference.DialogPreference
    protected fun showDialog(Bundle bundle) {
        super.showDialog(bundle)
        getDialog().getWindow().setSoftInputMode(2)
    }
}
