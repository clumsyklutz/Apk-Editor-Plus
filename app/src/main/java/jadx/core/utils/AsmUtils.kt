package jadx.core.utils

import java.io.File
import java.io.FileInputStream
import org.b.a.a

class AsmUtils {
    private constructor() {
    }

    fun getNameFromClassFile(File file) throws Throwable {
        FileInputStream fileInputStream
        try {
            fileInputStream = FileInputStream(file)
            try {
                String strA = a(fileInputStream).a()
                fileInputStream.close()
                return strA
            } catch (Throwable th) {
                th = th
                if (fileInputStream != null) {
                    fileInputStream.close()
                }
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            fileInputStream = null
        }
    }
}
