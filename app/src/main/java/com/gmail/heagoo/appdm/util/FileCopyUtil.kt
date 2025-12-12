package com.gmail.heagoo.appdm.util

import android.content.Context
import com.gmail.heagoo.common.ccc
import java.io.File

class FileCopyUtil {
    fun copyBack(Context context, String str, String str2, Boolean z) throws Exception {
        ccc cccVarCreateCommandRunner = createCommandRunner(z)
        File file = File(context.getFilesDir(), "mycp")
        if (!cccVarCreateCommandRunner.a(String.format((file.exists() ? file.getPath() : "cp") + " %s \"%s\"", str, str2), (Array<String>) null, (Integer) 5000)) {
            throw Exception("Can not write file to " + str2)
        }
    }

    private fun createCommandRunner(Boolean z) {
        return z ? new com.gmail.heagoo.sqliteutil.c() : new com.gmail.heagoo.common.c()
    }
}
