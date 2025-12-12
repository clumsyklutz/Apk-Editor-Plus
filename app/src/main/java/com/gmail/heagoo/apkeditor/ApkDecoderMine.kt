package com.gmail.heagoo.apkeditor

import android.content.Context
import android.support.v4.os.EnvironmentCompat
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.a
import jadx.core.deobf.Deobfuscator
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.EOFException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStreamWriter
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import java.util.regex.Pattern
import org.apache.commons.io.FilenameUtils
import org.xmlpull.v1.XmlSerializer

class ApkDecoderMine implements androidx.versionedparcelable.d {

    /* renamed from: a, reason: collision with root package name */
    private a f755a

    /* renamed from: b, reason: collision with root package name */
    private ix f756b
    private Array<Byte> f
    private Array<Byte> g
    private Boolean i
    private static final Array<String> j = {"classes.dex", ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME, "resources.arsc"}
    private static final Array<String> k = {"res", "r", "R", "lib", "libs", "assets", "kotlin"}
    private static val NO_COMPRESS_PATTERN = Pattern.compile("(jpg|jpeg|png|gif|wav|mp2|mp3|ogg|aac|mpg|mpeg|mid|midi|smf|jet|rtttl|imy|xmf|mp4|m4a|m4v|3gp|3gpp|3g2|3gpp2|amr|awb|wma|wmv|webm|webp|mkv)$")
    private a.a.b.b.l c = new a.a.b.b.l()
    private String d = ""
    private Boolean e = false
    private Map h = HashMap()

    constructor(a aVar, Array<Byte> bArr, Array<Byte> bArr2, Array<Byte> bArr3) {
        Boolean z = false
        this.f755a = aVar
        this.f = bArr
        this.g = bArr2
        if (bArr2 == null && bArr == null) {
            z = true
        }
        this.i = z
        Set setA = this.f755a.a()
        this.f756b = ix(this, (setA == null || setA.isEmpty()) ? null : (a.a.b.a.e) setA.iterator().next())
        this.c.a("xml", this.f756b)
        this.c.a("9path", new a.a.b.b.g())
        this.c.a("raw", new a.a.b.b.j())
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:50:0x0127
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:280)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:79)
        */
    private fun a(a.a.b.c.a r17, a.c.d r18) {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.ApkDecoderMine.a(a.a.b.c.a, a.c.d):Unit")
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(a.c.d r6, java.lang.String r7, a.c.d r8, java.lang.String r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            r5 = this
            r2 = 0
            r0 = 1
            java.lang.String r1 = "xml"
            Boolean r1 = r1.equals(r10)
            if (r1 == 0) goto L25
            java.lang.String r1 = ".xml"
            Boolean r1 = r9.endsWith(r1)
            if (r1 != 0) goto L25
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r9)
            java.lang.String r3 = ".xml"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r9 = r1.toString()
        L25:
            r1 = 0
            Boolean r3 = r5.i     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 != 0) goto Lbf
            java.lang.String r3 = "raw"
            Boolean r3 = r3.equals(r10)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 == 0) goto Lbf
            java.lang.String r3 = ""
            java.lang.String r4 = r5.d     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            Boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 != 0) goto Lbf
            java.lang.String r3 = ".jpg"
            Boolean r3 = r9.endsWith(r3)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 == 0) goto L90
            java.io.OutputStream r1 = r8.c(r9)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            Array<Byte> r3 = r5.g     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            r1.write(r3)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
        L4d:
            if (r0 != 0) goto L5c
            java.io.InputStream r2 = r6.b(r7)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.io.OutputStream r1 = r8.c(r9)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            a.a.b.b.l r0 = r5.c     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            r0.a(r2, r1, r10)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
        L5c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.String r3 = "res/"
            r0.<init>(r3)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            r3.<init>()     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.String r4 = r5.d     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            Boolean r4 = r0.equals(r3)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            if (r4 != 0) goto L89
            java.util.Map r4 = r5.h     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            r4.put(r0, r3)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
        L89:
            a(r2)
            a(r1)
            return
        L90:
            java.lang.String r3 = ".png"
            Boolean r3 = r9.endsWith(r3)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 == 0) goto Lbf
            java.lang.String r3 = ".9.png"
            Boolean r3 = r9.endsWith(r3)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            if (r3 != 0) goto Lbf
            java.io.OutputStream r1 = r8.c(r9)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbc
            Array<Byte> r3 = r5.f     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            r1.write(r3)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lb1
            goto L4d
        Laa:
            r0 = move-exception
        Lab:
            a.a.a r3 = new a.a.a     // Catch: java.lang.Throwable -> Lb1
            r3.<init>(r0)     // Catch: java.lang.Throwable -> Lb1
            throw r3     // Catch: java.lang.Throwable -> Lb1
        Lb1:
            r0 = move-exception
        Lb2:
            a(r2)
            a(r1)
            throw r0
        Lb9:
            r0 = move-exception
            r1 = r2
            goto Lb2
        Lbc:
            r0 = move-exception
            r1 = r2
            goto Lab
        Lbf:
            r0 = r1
            r1 = r2
            goto L4d
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.ApkDecoderMine.a(a.c.d, java.lang.String, a.c.d, java.lang.String, java.lang.String):Unit")
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(File file) throws IOException, a.a.a {
        for (a.a.b.a.e eVar : this.f755a.a()) {
            if (this.e) {
                return
            }
            try {
                File file2 = File(file, "values/public.xml")
                if (!file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs()
                }
                FileOutputStream fileOutputStream = FileOutputStream(file2)
                OutputStreamWriter outputStreamWriter = OutputStreamWriter(fileOutputStream)
                outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                outputStreamWriter.write("<resources>\n")
                for (a.a.b.a.f fVar : eVar.a()) {
                    outputStreamWriter.write(String.format("<public type=\"%s\" name=\"%s\" id=\"0x%08x\" />\n", fVar.h().a(), fVar.f(), Integer.valueOf(fVar.e().f28b)))
                }
                outputStreamWriter.write("</resources>")
                outputStreamWriter.close()
                fileOutputStream.close()
            } catch (IOException e) {
                throw new a.a.ExceptionA("Could not generate public.xml file", e)
            }
        }
    }

    private fun a(String str) {
        Array<String> strArr = j
        for (Int i = 0; i < 3; i++) {
            if (strArr[i].equals(str)) {
                return true
            }
        }
        Array<String> strArr2 = k
        for (Int i2 = 0; i2 < 7; i2++) {
            if (str.startsWith(strArr2[i2] + "/")) {
                return true
            }
        }
        return str.endsWith(".dex") && !str.contains("/")
    }

    private fun b(a.a.b.c.a aVar, File file) throws a.a.ExceptionA {
        File file2 = File(file, EnvironmentCompat.MEDIA_UNKNOWN)
        try {
            a.c.d dVarA = aVar.a()
            Collection collectionValues = this.h.values()
            for (String str : dVarA.a(true)) {
                if (!a(str) && !collectionValues.contains(str)) {
                    dVarA.a(file2, str)
                }
            }
        } catch (a.c.e e) {
            throw new a.a.ExceptionA(e)
        }
    }

    private fun b(File file) throws IllegalStateException, IOException, IllegalArgumentException {
        for (a.a.b.a.e eVar : this.f755a.a()) {
            if (this.e) {
                return
            }
            for (a.a.b.a.j jVar : eVar.c()) {
                if (!this.e) {
                    String strA = jVar.a()
                    Int iLastIndexOf = strA.lastIndexOf(47)
                    if (iLastIndexOf != -1) {
                        File file2 = File(file, strA.substring(0, iLastIndexOf))
                        if (!file2.exists()) {
                            file2.mkdirs()
                        }
                    }
                    XmlSerializer hVar = new a.d.h()
                    FileOutputStream fileOutputStream = FileOutputStream(File(file, strA))
                    OutputStreamWriter outputStreamWriter = OutputStreamWriter(fileOutputStream)
                    hVar.setOutput(outputStreamWriter)
                    outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                    outputStreamWriter.write("<resources>\n")
                    for (a.a.b.a.g gVar : jVar.b()) {
                        if (!jVar.a(gVar)) {
                            Object objD = gVar.d()
                            if (objD is a.a.b.d.a) {
                                try {
                                    ((a.a.b.d.a) objD).a(hVar, gVar)
                                } catch (Throwable th) {
                                    th.printStackTrace()
                                }
                            }
                            hVar.flush()
                            outputStreamWriter.write("\n")
                        }
                    }
                    outputStreamWriter.write("</resources>\n")
                    outputStreamWriter.close()
                    fileOutputStream.close()
                }
            }
        }
    }

    private fun c(a.a.b.c.a aVar, File file) throws a.a.ExceptionA {
        try {
            a.c.d dVarA = aVar.a()
            if (dVarA.a("assets")) {
                dVarA.a(file, "assets")
            }
            if (dVarA.a("lib")) {
                dVarA.a(file, "lib")
            }
            if (dVarA.a("libs")) {
                dVarA.a(file, "libs")
            }
            if (dVarA.a("kotlin")) {
                dVarA.a(file, "kotlin")
            }
            for (String str : dVarA.a(false)) {
                if (str.endsWith(".dex")) {
                    dVarA.a(file, str)
                }
            }
        } catch (a.c.e e) {
            throw new a.a.ExceptionA(e)
        }
    }

    private fun d(a.a.b.c.a aVar, File file) throws Throwable {
        InputStream inputStreamB
        FileOutputStream fileOutputStream
        FileOutputStream fileOutputStream2 = null
        try {
            inputStreamB = aVar.a().b(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)
            try {
                fileOutputStream = FileOutputStream(File(file, ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME))
            } catch (Exception e) {
                e = e
                fileOutputStream = null
            } catch (Throwable th) {
                th = th
                try {
                    inputStreamB.close()
                } catch (Exception e2) {
                }
                try {
                    fileOutputStream2.close()
                    throw th
                } catch (Exception e3) {
                    throw th
                }
            }
        } catch (Exception e4) {
            e = e4
            fileOutputStream = null
            inputStreamB = null
        } catch (Throwable th2) {
            th = th2
            inputStreamB = null
        }
        try {
            try {
                this.f756b.b(inputStreamB, fileOutputStream)
                try {
                    inputStreamB.close()
                } catch (Exception e5) {
                }
                try {
                    fileOutputStream.close()
                } catch (Exception e6) {
                }
            } catch (Throwable th3) {
                th = th3
                fileOutputStream2 = fileOutputStream
                inputStreamB.close()
                fileOutputStream2.close()
                throw th
            }
        } catch (Exception e7) {
            e = e7
            e.printStackTrace()
            try {
                inputStreamB.close()
            } catch (Exception e8) {
            }
            try {
                fileOutputStream.close()
            } catch (Exception e9) {
            }
            return null
        }
        return null
    }

    private fun extension(String str, a.c.d dVar) throws IOException {
        Int iLastIndexOf = str.lastIndexOf(Deobfuscator.CLASS_NAME_SEPARATOR)
        if (iLastIndexOf != -1 && !this.d.equalsIgnoreCase("r/")) {
            return str.endsWith(".9.png") ? ".9.png" : str.substring(iLastIndexOf).toLowerCase()
        }
        Array<Byte> bArr = new Byte[4]
        String lowerCase = iLastIndexOf > 0 ? str.substring(iLastIndexOf).toLowerCase() : ""
        try {
            InputStream inputStreamB = dVar.b(str)
            if (inputStreamB.read(bArr) != bArr.length) {
                return lowerCase
            }
            inputStreamB.close()
            return (bArr[0] == 3 && bArr[1] == 0 && bArr[2] == 8 && bArr[3] == 0) ? ".xml" : ((bArr[0] & 255) == 255 && (bArr[1] & 255) == 216 && (bArr[2] & 255) == 255 && (bArr[3] & 255) == 224) ? ".jpg" : (bArr[0] == 82 && bArr[1] == 73 && bArr[2] == 70 && bArr[3] == 70) ? ".webp" : ((bArr[0] & 255) == 137 && bArr[1] == 80 && bArr[2] == 78 && bArr[3] == 71) ? is9patch(dVar, str) ? ".9.png" : ".png" : lowerCase
        } catch (a.c.e | IOException e) {
            e.printStackTrace()
            return lowerCase
        }
    }

    private fun find9patchChunk(a.d.f fVar) {
        try {
            fVar.skipBytes(8)
            while (true) {
                Int i = fVar.readInt()
                if (fVar.readInt() == 1852855395) {
                    return true
                }
                fVar.skipBytes(i + 4)
            }
        } catch (EOFException e) {
            return false
        }
    }

    private fun is9patch(a.c.d dVar, String str) throws IOException {
        try {
            InputStream inputStreamB = dVar.b(str)
            ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
            a.b(inputStreamB, byteArrayOutputStream)
            Array<Byte> byteArray = byteArrayOutputStream.toByteArray()
            inputStreamB.close()
            if (byteArray.length == 0) {
                return false
            }
            return find9patchChunk(new a.d.f(ByteArrayInputStream(byteArray)))
        } catch (IOException e) {
            return false
        }
    }

    private fun isRes(String str) {
        Array<String> strArr = k
        for (Int i = 0; i < 3; i++) {
            if (str.startsWith(strArr[i] + "/")) {
                return true
            }
        }
        return false
    }

    private fun isStd(String str) {
        Array<String> strArr = j
        for (Int i = 0; i < 3; i++) {
            if (strArr[i].equals(str)) {
                return true
            }
        }
        return str.endsWith(".dex")
    }

    public final Map a() {
        return this.h
    }

    public final Unit a(a.a.b.c.a aVar, File file) {
        if (!aVar.a().a("res")) {
            this.f756b.a(true)
        }
        if (!this.e) {
            d(aVar, file)
        }
        try {
            com.gmail.heagoo.common.h.a(File(file.getPath() + "/res"))
        } catch (Exception e) {
        }
        StringBuilder().append(file.getPath()).append("/res deleted!")
        File file2 = File(file, "res")
        if (!file2.exists()) {
            file2.mkdirs()
        }
        if (!this.e) {
            a(aVar, new a.c.f(file2))
        }
        if (!this.e) {
            b(file2)
        }
        if (!this.e) {
            a(file2)
            recordUncompressedFiles(aVar)
        }
        if (this.i) {
            if (!this.e) {
                c(aVar, file)
            }
            if (this.e) {
                return
            }
            b(aVar, file)
        }
    }

    public final Unit b() {
        this.e = true
    }

    fun recordUncompressedFiles(a.a.b.c.a aVar) {
        String extension
        HashSet hashSet = HashSet()
        HashSet hashSet2 = HashSet()
        HashMap map = HashMap()
        for (Map.Entry entry : this.h.entrySet()) {
            map.put((String) entry.getValue(), (String) entry.getKey())
        }
        try {
            a.c.d dVarA = aVar.a()
            for (String str : dVarA.a(true)) {
                if (dVarA.getCompressionLevel(str) == 0 && dVarA.getSize(str) > 0) {
                    String str2 = (String) map.get(str)
                    if (str2 != null) {
                        extension = FilenameUtils.getExtension(str2)
                        str = str2
                    } else {
                        extension = FilenameUtils.getExtension(str)
                    }
                    if (!NO_COMPRESS_PATTERN.matcher(extension).find()) {
                        if (extension.isEmpty()) {
                        }
                        if (!isStd(str)) {
                            if (isRes(str)) {
                                hashSet.add(str)
                            } else {
                                hashSet2.add(str)
                            }
                        }
                    }
                }
            }
            if (hashSet.isEmpty() && hashSet2.isEmpty()) {
                return
            }
            String strD = a.d((Context) null, "tmp")
            if (!hashSet.isEmpty()) {
                a.a(strD + "aaptUncString", hashSet)
            }
            if (hashSet2.isEmpty()) {
                return
            }
            a.a(strD + "mdUncString", hashSet2)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
