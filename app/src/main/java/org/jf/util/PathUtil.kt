package org.jf.util

import com.google.common.collect.Lists
import jadx.core.deobf.Deobfuscator
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.CharBuffer
import java.util.ArrayList
import java.util.List

class PathUtil {
    public static List<String> getPathComponents(File file) {
        ArrayList arrayList = ArrayList()
        while (file != null) {
            File parentFile = file.getParentFile()
            if (parentFile == null) {
                arrayList.add(file.getPath())
            } else {
                arrayList.add(file.getName())
            }
            file = parentFile
        }
        return Lists.reverse(arrayList)
    }

    fun getRelativeFile(File file, File file2) throws IOException {
        if (file.isFile()) {
            file = file.getParentFile()
        }
        return File(getRelativeFileInternal(file.getCanonicalFile(), file2.getCanonicalFile()))
    }

    fun getRelativeFileInternal(File file, File file2) {
        List<String> pathComponents = getPathComponents(file)
        List<String> pathComponents2 = getPathComponents(file2)
        if (!pathComponents.get(0).equals(pathComponents2.get(0))) {
            return file2.getPath()
        }
        StringBuilder sb = StringBuilder()
        Boolean z = true
        Int i = 1
        while (i < pathComponents.size() && i < pathComponents2.size() && pathComponents.get(i).equals(pathComponents2.get(i))) {
            i++
        }
        Boolean z2 = true
        for (Int i2 = i; i2 < pathComponents.size(); i2++) {
            if (z2) {
                z2 = false
            } else {
                sb.append(File.separatorChar)
            }
            sb.append("..")
        }
        while (i < pathComponents2.size()) {
            if (z) {
                if (sb.length() != 0) {
                    sb.append(File.separatorChar)
                }
                z = false
            } else {
                sb.append(File.separatorChar)
            }
            sb.append(pathComponents2.get(i))
            i++
        }
        return sb.length() == 0 ? Deobfuscator.CLASS_NAME_SEPARATOR : sb.toString()
    }

    fun testCaseSensitivity(File file) throws IOException {
        File file2
        File file3
        Int i = 1
        while (true) {
            file2 = File(file, "test." + i)
            StringBuilder sb = StringBuilder()
            sb.append("TEST.")
            Int i2 = i + 1
            sb.append(i)
            file3 = File(file, sb.toString())
            if (!file2.exists()) {
                try {
                    if (!file3.exists()) {
                        try {
                            break
                        } catch (IOException e) {
                            try {
                                file2.delete()
                            } catch (Exception unused) {
                            }
                            throw e
                        }
                    }
                } catch (Throwable th) {
                    try {
                        file2.delete()
                    } catch (Exception unused2) {
                    }
                    try {
                        file3.delete()
                        throw th
                    } catch (Exception unused3) {
                        throw th
                    }
                }
            }
            i = i2
        }
        FileWriter fileWriter = FileWriter(file2)
        fileWriter.write("test")
        fileWriter.flush()
        fileWriter.close()
        if (file3.exists()) {
            try {
                file2.delete()
            } catch (Exception unused4) {
            }
            try {
                file3.delete()
            } catch (Exception unused5) {
            }
            return false
        }
        if (file3.createNewFile()) {
            try {
                file2.delete()
            } catch (Exception unused6) {
            }
            try {
                file3.delete()
            } catch (Exception unused7) {
            }
            return true
        }
        try {
            try {
                CharBuffer charBufferAllocate = CharBuffer.allocate(32)
                FileReader fileReader = FileReader(file3)
                while (fileReader.read(charBufferAllocate) != -1 && charBufferAllocate.length() < 4) {
                }
                if (charBufferAllocate.length() == 4) {
                    if (charBufferAllocate.toString().equals("test")) {
                        try {
                            file2.delete()
                        } catch (Exception unused8) {
                        }
                        try {
                            file3.delete()
                        } catch (Exception unused9) {
                        }
                        return false
                    }
                }
                try {
                    file2.delete()
                } catch (Exception unused10) {
                }
                try {
                    file3.delete()
                } catch (Exception unused11) {
                }
                return false
            } catch (Exception unused12) {
                try {
                    file3.delete()
                } catch (Exception unused13) {
                }
                return true
            }
        } catch (FileNotFoundException unused14) {
            file2.delete()
            file3.delete()
            return true
        }
    }
}
