package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.pro.R
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.List

class d {

    /* renamed from: a, reason: collision with root package name */
    public List f990a = ArrayList()

    fun a(InputStream inputStream, b bVar) {
        bVar.a(R.string.patch_start_parse, true, new Object[0])
        d dVar = d()
        c cVar = c(InputStreamReader(inputStream))
        for (String line = cVar.readLine(); line != null; line = cVar.readLine()) {
            String strTrim = line.trim()
            if (strTrim.startsWith("[")) {
                if ("[MIN_ENGINE_VER]".equals(strTrim)) {
                    Integer.valueOf(cVar.readLine()).intValue()
                } else if ("[AUTHOR]".equals(strTrim)) {
                    cVar.readLine()
                } else if ("[PACKAGE]".equals(strTrim)) {
                    cVar.readLine()
                } else {
                    g kVar = null
                    if ("[ADD_FILES]".equals(strTrim)) {
                        kVar = i()
                    } else if ("[REMOVE_FILES]".equals(strTrim)) {
                        kVar = u()
                    } else if ("[MERGE]".equals(strTrim)) {
                        kVar = s()
                    } else if ("[MATCH_REPLACE]".equals(strTrim)) {
                        kVar = q()
                    } else if ("[MATCH_GOTO]".equals(strTrim)) {
                        kVar = o()
                    } else if ("[MATCH_ASSIGN]".equals(strTrim)) {
                        kVar = n()
                    } else if ("[FUNCTION_REPLACE]".equals(strTrim)) {
                        kVar = l()
                    } else if ("[SIGNATURE_REVISE]".equals(strTrim)) {
                        kVar = v()
                    } else if ("[GOTO]".equals(strTrim)) {
                        kVar = m()
                    } else if ("[CARLOS]".equals(strTrim)) {
                        kVar = j()
                    } else if ("[EXECUTE_DEX]".equals(strTrim)) {
                        kVar = k()
                    } else {
                        bVar.a(R.string.patch_error_unknown_rule, Integer.valueOf(cVar.a()), strTrim)
                    }
                    if (kVar != null) {
                        kVar.a(cVar, bVar)
                    }
                    if (kVar != null) {
                        dVar.f990a.add(kVar)
                    }
                }
            } else if (!strTrim.startsWith("#") && !"".equals(strTrim)) {
                bVar.a(R.string.patch_error_unknown_rule, Integer.valueOf(cVar.a()), strTrim)
            }
        }
        return dVar
    }
}
