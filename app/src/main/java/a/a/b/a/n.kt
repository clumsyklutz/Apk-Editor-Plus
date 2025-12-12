package a.a.b.a

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.preference.PreferenceManager
import com.gmail.heagoo.apkeditor.pro.R

class n {
    private static val A = "aaban"
    static final Array<Int> B = {R.layout.activity_main, R.layout.activity_main_cards}
    static final Array<Int> C = {R.string.layout_buttons, R.string.layout_cards}

    fun b(Int i) {
        return B[i]
    }

    fun c(Int i) {
        return C[i]
    }

    fun d(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(A, context.getResources().getInteger(R.integer.layout_def))
    }

    fun e(Context context, Int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(A, i).commit()
    }

    fun f(Context context, Int i, p pVar) {
        Array<String> strArr = new String[C.length]
        Int i2 = 0
        while (true) {
            Array<Int> iArr = C
            if (i2 >= iArr.length) {
                Int iD = d(context)
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                builder.setTitle(i)
                builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null)
                builder.setSingleChoiceItems(strArr, iD, o(this, iD, context, pVar))
                builder.show()
                return
            }
            strArr[i2] = context.getString(iArr[i2])
            i2++
        }
    }
}
