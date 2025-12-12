package com.google.common.io

import com.google.common.base.Preconditions
import java.io.File

class Files {
    static {
        Object() { // from class: com.google.common.io.Files.2
        }
    }

    fun getNameWithoutExtension(String str) {
        Preconditions.checkNotNull(str)
        String name = File(str).getName()
        Int iLastIndexOf = name.lastIndexOf(46)
        return iLastIndexOf == -1 ? name : name.substring(0, iLastIndexOf)
    }
}
