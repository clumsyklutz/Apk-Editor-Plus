package a.c

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.LinkedHashMap
import java.util.LinkedHashSet

class f extends a {
    private File c

    constructor(File file) throws e {
        if (!file.isDirectory()) {
            throw e("File must be a directory: " + file)
        }
        this.c = file
    }

    private fun c() {
        this.f61a = LinkedHashSet()
        this.f62b = LinkedHashMap()
        Array<File> fileArrListFiles = this.c.listFiles()
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isFile()) {
                    this.f61a.add(file.getName())
                } else {
                    try {
                        this.f62b.put(file.getName(), f(file))
                    } catch (e e) {
                    }
                }
            }
        }
    }

    private fun j(String str) {
        return this.c.getPath() + '/' + str
    }

    @Override // a.c.a
    protected final Unit a() {
        c()
    }

    @Override // a.c.a
    protected final Unit b() {
        c()
    }

    @Override // a.c.a
    protected final InputStream f(String str) throws e {
        try {
            return FileInputStream(j(str))
        } catch (FileNotFoundException e) {
            throw e(e)
        }
    }

    @Override // a.c.a
    protected final OutputStream g(String str) throws e {
        try {
            return FileOutputStream(j(str))
        } catch (FileNotFoundException e) {
            throw e(e)
        }
    }

    @Override // a.c.d
    fun getCompressionLevel(String str) {
        return 0
    }

    @Override // a.c.d
    fun getSize(String str) {
        return 0L
    }

    @Override // a.c.a
    protected final a h(String str) {
        File file = File(j(str))
        file.mkdir()
        return f(file)
    }

    @Override // a.c.a
    protected final Unit i(String str) {
        File(j(str)).delete()
    }
}
