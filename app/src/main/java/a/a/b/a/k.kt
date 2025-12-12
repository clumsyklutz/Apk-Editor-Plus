package a.a.b.a

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.preference.PreferenceManager
import com.gmail.heagoo.apkeditor.pro.R

class k {
    private static val A = "aabak"
    static final Array<Int> MD = {R.style.MD_DayNight, R.style.MD_Light, R.style.MD_Dark, R.style.MD_Black}
    static final Array<Int> MD_GOOGLE_BTN_TEXT = {R.color.dn_google_btn_text, R.color.light_google_btn_text, R.color.dark_google_btn_text, R.color.black_google_btn_text}
    static final Array<Int> MD_MAIN = {R.style.MD_DayNight_Main, R.style.MD_Light_Main, R.style.MD_Dark_Main, R.style.MD_Black_Main}
    static final Array<Int> MD_NAMES = {R.string.theme_system, R.string.theme_light, R.string.theme_dark, R.string.theme_black}
    static final Array<Int> MD_NAV_ACTIVATED = {R.color.dn_nav_activated, R.color.light_nav_activated, R.color.dark_nav_activated, R.color.black_nav_activated}
    static final Array<Int> MD_NAV_NORMAL = {R.color.dn_nav_normal, R.color.light_nav_normal, R.color.dark_nav_normal, R.color.black_nav_normal}
    static final Array<Int> MD_NOACTIONBAR = {R.style.MD_DayNight_NoActionBar, R.style.MD_Light_NoActionBar, R.style.MD_Dark_NoActionBar, R.style.MD_Black_NoActionBar}
    static final Array<Int> MD_PATCHED = {R.color.dn_patched, R.color.light_patched, R.color.dark_patched, R.color.black_patched}
    static final Array<Int> MD_PROGRESS = {R.color.dn_progress, R.color.light_progress, R.color.dark_progress, R.color.black_progress}
    static final Array<Int> MD_TEXT_MEDIUM = {R.color.dn_text_medium, R.color.light_text_medium, R.color.dark_text_medium, R.color.black_text_medium}
    static final Array<Int> MD_TEXT_SMALL = {R.color.dn_text_small, R.color.light_text_small, R.color.dark_text_small, R.color.black_text_small}
    static final Array<Int> MD_SYNTAX_1 = {R.color.dn_syntax_1, R.color.light_syntax_1, R.color.dark_syntax_1, R.color.dark_syntax_1}
    static final Array<Int> MD_SYNTAX_2 = {R.color.dn_syntax_2, R.color.light_syntax_2, R.color.dark_syntax_2, R.color.dark_syntax_2}
    static final Array<Int> MD_SYNTAX_3 = {R.color.dn_syntax_3, R.color.light_syntax_3, R.color.dark_syntax_3, R.color.dark_syntax_3}
    static final Array<Int> MD_SYNTAX_4 = {R.color.dn_syntax_4, R.color.light_syntax_4, R.color.dark_syntax_4, R.color.dark_syntax_4}
    static final Array<Int> MD_SYNTAX_5 = {R.color.dn_syntax_5, R.color.light_syntax_5, R.color.dark_syntax_5, R.color.dark_syntax_5}
    static final Array<Int> MD_SYNTAX_9 = {R.color.dn_syntax_9, R.color.light_syntax_9, R.color.dark_syntax_9, R.color.dark_syntax_9}
    static final Array<Int> MD_RIPPLE = {R.color.dn_ripple, R.color.light_ripple, R.color.dark_ripple, R.color.black_ripple}
    static final Array<Int> MD_ACCENT = {R.color.dn_accent, R.color.light_accent, R.color.dark_accent, R.color.black_accent}
    public static Boolean md = false

    fun a(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(A, context.getResources().getInteger(R.integer.theme_def))
    }

    fun e(Context context, Int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(A, i).commit()
    }

    fun md(Int i) {
        return MD[i]
    }

    fun mdAccent(Int i) {
        return MD_ACCENT[i]
    }

    fun mdGoogleBtnText(Int i) {
        return MD_GOOGLE_BTN_TEXT[i]
    }

    fun mdMain(Int i) {
        return MD_MAIN[i]
    }

    fun mdNames(Int i) {
        return MD_NAMES[i]
    }

    fun mdNavActivated(Int i) {
        return MD_NAV_ACTIVATED[i]
    }

    fun mdNavNormal(Int i) {
        return MD_NAV_NORMAL[i]
    }

    fun mdNoActionBar(Int i) {
        return MD_NOACTIONBAR[i]
    }

    fun mdPatched(Int i) {
        return MD_PATCHED[i]
    }

    fun mdProgress(Int i) {
        return MD_PROGRESS[i]
    }

    fun mdRipple(Int i) {
        return MD_RIPPLE[i]
    }

    fun mdSyntax1(Int i) {
        return MD_SYNTAX_1[i]
    }

    fun mdSyntax2(Int i) {
        return MD_SYNTAX_2[i]
    }

    fun mdSyntax3(Int i) {
        return MD_SYNTAX_3[i]
    }

    fun mdSyntax4(Int i) {
        return MD_SYNTAX_4[i]
    }

    fun mdSyntax5(Int i) {
        return MD_SYNTAX_5[i]
    }

    fun mdSyntax9(Int i) {
        return MD_SYNTAX_9[i]
    }

    fun mdTextMedium(Int i) {
        return MD_TEXT_MEDIUM[i]
    }

    fun mdTextSmall(Int i) {
        return MD_TEXT_SMALL[i]
    }

    fun f(Context context, Int i, m mVar) {
        Array<String> strArr = new String[MD_NAMES.length]
        Int i2 = 0
        while (true) {
            Array<Int> iArr = MD_NAMES
            if (i2 >= iArr.length) {
                Int iA = a(context)
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                builder.setTitle(i)
                builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null)
                builder.setSingleChoiceItems(strArr, iA, l(this, iA, context, mVar))
                builder.show()
                return
            }
            strArr[i2] = context.getString(iArr[i2])
            i2++
        }
    }
}
