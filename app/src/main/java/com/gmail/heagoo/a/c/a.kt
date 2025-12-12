package com.gmail.heagoo.a.c

import a.a.b.a.f
import a.d.e
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.FileProvider
import androidx.appcompat.R
import android.widget.Toast
import com.a.a.s
import com.a.b.a.b.ah
import com.a.b.a.b.m
import com.a.b.a.b.v
import com.a.b.f.c.g
import com.a.b.f.c.h
import com.a.b.f.c.i
import com.a.b.f.c.j
import com.a.b.f.c.n
import com.a.b.f.c.p
import com.a.b.f.c.t
import com.a.b.f.c.x
import com.gmail.heagoo.apkeditor.MainActivity
import com.gmail.heagoo.apkeditor.SettingActivity
import com.gmail.heagoo.apkeditor.SettingEditorActivity
import com.gmail.heagoo.apkeditor.TextEditBigActivity
import com.gmail.heagoo.apkeditor.TextEditNormalActivity
import com.gmail.heagoo.apkeditor.bv
import com.gmail.heagoo.apkeditor.installer.Installer
import com.gmail.heagoo.neweditor.Token
import com.gmail.heagoo.neweditor.z
import jadx.core.codegen.CodeWriter
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.io.RandomAccessFile
import java.io.UTFDataFormatException
import java.lang.Character
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.zip.ZipFile

class a {
    private static HashSet f
    private static HashSet g
    private static Boolean h

    /* renamed from: a, reason: collision with root package name */
    private val f743a = HashMap()

    /* renamed from: b, reason: collision with root package name */
    private val f744b = HashMap()
    private val c = LinkedHashSet()
    private val d = LinkedHashSet()
    private Context e

    constructor() {
        LinkedHashMap()
        new a.a.b.c.b()
    }

    constructor(Context context, Boolean z) {
        LinkedHashMap()
        new a.a.b.c.b()
        this.e = context
    }

    fun A(Int i) {
        Array<Char> cArr = new Char[5]
        if (i < 0) {
            cArr[0] = '-'
            i = -i
        } else {
            cArr[0] = '+'
        }
        for (Int i2 = 0; i2 < 4; i2++) {
            cArr[4 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun B(Int i) {
        Array<Char> cArr = new Char[3]
        if (i < 0) {
            cArr[0] = '-'
            i = -i
        } else {
            cArr[0] = '+'
        }
        for (Int i2 = 0; i2 < 2; i2++) {
            cArr[2 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun a(Int i, Int i2) {
        if (i == i2) {
            return 0
        }
        return (((Long) i) & 4294967295L) < (((Long) i2) & 4294967295L) ? -1 : 1
    }

    fun a(com.a.a.ClassA.b bVar) {
        Int iD
        Int i = 0
        Int i2 = -1
        Int i3 = 0
        do {
            iD = bVar.d() & 255
            i3 |= (iD & 127) << (i * 7)
            i2 <<= 7
            i++
            if ((iD & 128) != 128) {
                break
            }
        } while (i < 5);
        if ((iD & 128) == 128) {
            throw s("invalid LEB128 sequence")
        }
        return ((i2 >> 1) & i3) != 0 ? i3 | i2 : i3
    }

    fun a(com.a.a.ClassA.b bVar, Int i) {
        Int iD = 0
        for (Int i2 = i; i2 >= 0; i2--) {
            iD = (iD >>> 8) | ((bVar.d() & 255) << 24)
        }
        return iD >> ((3 - i) << 3)
    }

    fun a(com.a.a.ClassA.b bVar, Int i, Boolean z) {
        Int iD = 0
        if (z) {
            while (i >= 0) {
                iD = (iD >>> 8) | ((bVar.d() & 255) << 24)
                i--
            }
            return iD
        }
        for (Int i2 = i; i2 >= 0; i2--) {
            iD = (iD >>> 8) | ((bVar.d() & 255) << 24)
        }
        return iD >>> ((3 - i) << 3)
    }

    fun a(Short s, Short s2) {
        if (s == s2) {
            return 0
        }
        return (s & 65535) < (65535 & s2) ? -1 : 1
    }

    fun a(Array<Int> iArr) {
        return iArr.length << 5
    }

    private fun a(String str, Int i) {
        Char cCharAt
        Int i2 = 0
        ArrayList arrayList = ArrayList()
        ArrayList arrayList2 = ArrayList()
        if (str == null) {
            return e(arrayList, arrayList2)
        }
        Int length = str.length()
        while (true) {
            Int iIndexOf = str.indexOf(37, i2)
            if (iIndexOf != -1) {
                Int i3 = iIndexOf + 1
                if (i3 != length) {
                    i2 = i3 + 1
                    Char cCharAt2 = str.charAt(i3)
                    if (cCharAt2 != '%') {
                        if (cCharAt2 >= '0' && cCharAt2 <= '9' && i2 < length) {
                            do {
                                Int i4 = i2
                                i2 = i4 + 1
                                cCharAt = str.charAt(i4)
                                if (cCharAt < '0' || cCharAt > '9') {
                                    break
                                }
                            } while (i2 < length);
                            if (cCharAt == '$') {
                                arrayList2.add(Integer.valueOf(iIndexOf))
                            }
                        }
                        arrayList.add(Integer.valueOf(iIndexOf))
                        if (arrayList.size() >= 4) {
                            break
                        }
                    }
                } else {
                    arrayList.add(Integer.valueOf(iIndexOf))
                    break
                }
            } else {
                break
            }
        }
        return e(arrayList, arrayList2)
    }

    fun a(Context context, String str, String str2) {
        Intent intent = f(context, str) ? Intent(context, (Class<?>) TextEditBigActivity.class) : Intent(context, (Class<?>) TextEditNormalActivity.class)
        a(intent, "xmlPath", str)
        if (str2 != null) {
            a(intent, "apkPath", str2)
        }
        return intent
    }

    fun a(Context context, ArrayList arrayList, Int i, String str) {
        Intent intent = f(context, (String) arrayList.get(i)) ? Intent(context, (Class<?>) TextEditBigActivity.class) : Intent(context, (Class<?>) TextEditNormalActivity.class)
        a(intent, "fileList", arrayList)
        a(intent, "curFileIndex", i)
        if (str != null) {
            a(intent, "apkPath", str)
        }
        return intent
    }

    fun a(Intent intent, String str, Int i) {
        Bundle bundle = Bundle()
        bundle.putInt(str, i)
        intent.putExtras(bundle)
        return bundle
    }

    fun a(Intent intent, String str, String str2) {
        Bundle bundle = Bundle()
        bundle.putString(str, str2)
        intent.putExtras(bundle)
        return bundle
    }

    fun a(Intent intent, String str, ArrayList arrayList) {
        Bundle bundle = Bundle()
        bundle.putStringArrayList(str, arrayList)
        intent.putExtras(bundle)
        return bundle
    }

    fun a(Intent intent, String str, Boolean z) {
        Bundle bundle = Bundle()
        bundle.putBoolean(str, z)
        intent.putExtras(bundle)
        return bundle
    }

    fun a(m mVar, m mVar2) {
        if (mVar == mVar2) {
            return mVar
        }
        Int iB = mVar.b()
        if (mVar2.b() != iB) {
            throw ah("mismatched stack depths")
        }
        m mVarA = null
        for (Int i = 0; i < iB; i++) {
            com.a.b.f.d.d dVarA = mVar.a(i)
            com.a.b.f.d.d dVarA2 = mVar2.a(i)
            com.a.b.f.d.d dVarA3 = a(dVarA, dVarA2)
            if (dVarA3 != dVarA) {
                if (mVarA == null) {
                    mVarA = mVar.a()
                }
                if (dVarA3 == null) {
                    throw ah("incompatible: " + dVarA + ", " + dVarA2)
                }
                try {
                    mVarA.a(i, dVarA3)
                } catch (ah e) {
                    e.a("...while merging stack[" + v(i) + "]")
                    throw e
                }
                e.a("...while merging stack[" + v(i) + "]")
                throw e
            }
        }
        if (mVarA == null) {
            return mVar
        }
        mVarA.b_()
        return mVarA
    }

    fun a(v vVar, v vVar2) {
        if (vVar == vVar2) {
            return vVar
        }
        Int iF = vVar.f()
        if (vVar2.f() != iF) {
            throw ah("mismatched maxLocals values")
        }
        v vVarE = null
        for (Int i = 0; i < iF; i++) {
            com.a.b.f.d.d dVarC = vVar.c(i)
            com.a.b.f.d.d dVarA = a(dVarC, vVar2.c(i))
            if (dVarA != dVarC) {
                if (vVarE == null) {
                    vVarE = vVar.a()
                }
                if (dVarA == null) {
                    vVarE.b(i)
                } else {
                    vVarE.a(i, dVarA)
                }
            }
        }
        if (vVarE == null) {
            return vVar
        }
        vVarE.b_()
        return vVarE
    }

    public static com.a.b.f.c.a a(com.a.b.f.d.c cVar) {
        switch (cVar.c()) {
            case 1:
                return g.f537a
            case 2:
                return h.f539a
            case 3:
                return i.f540a
            case 4:
                return j.f541a
            case 5:
                return com.a.b.f.c.m.f544a
            case 6:
                return n.f547b
            case 7:
                return t.f552a
            case 8:
                return x.f558a
            case 9:
                return p.f549a
            default:
                throw UnsupportedOperationException("no zero for type: " + cVar.d())
        }
    }

    public static com.a.b.f.d.d a(com.a.b.f.d.d dVar, com.a.b.f.d.d dVar2) {
        if (dVar == null || dVar.equals(dVar2)) {
            return dVar
        }
        if (dVar2 == null) {
            return null
        }
        com.a.b.f.d.c cVarA = dVar.a()
        com.a.b.f.d.c cVarA2 = dVar2.a()
        if (cVarA == cVarA2) {
            return cVarA
        }
        if (!cVarA.n() || !cVarA2.n()) {
            if (cVarA.l() && cVarA2.l()) {
                return com.a.b.f.d.c.f
            }
            return null
        }
        if (cVarA == com.a.b.f.d.c.j) {
            return cVarA2
        }
        if (cVarA2 == com.a.b.f.d.c.j) {
            return cVarA
        }
        if (!cVarA.o() || !cVarA2.o()) {
            return com.a.b.f.d.c.n
        }
        com.a.b.f.d.d dVarA = a(cVarA.s(), cVarA2.s())
        return dVarA == null ? com.a.b.f.d.c.n : ((com.a.b.f.d.c) dVarA).r()
    }

    fun a(Token token) {
        if (token == null) {
            return null
        }
        Token token2 = token
        for (Token token3 = token.next; token3 != null; token3 = token3.next) {
            if (token3.id == token2.id && token3.offset == token2.offset + token2.length) {
                token2.length += token3.length
            } else {
                token2.next = token3
                token2 = token3
            }
        }
        token2.next = null
        return token
    }

    fun a(String str, Boolean z) {
        String strSubstring
        Int i
        Int iLastIndexOf = str.lastIndexOf(47)
        String strSubstring2 = str.substring(0, iLastIndexOf + 1)
        String strSubstring3 = str.substring(iLastIndexOf + 1)
        String strSubstring4 = ""
        Int iLastIndexOf2 = strSubstring3.lastIndexOf(46)
        if (iLastIndexOf2 != -1) {
            strSubstring = strSubstring3.substring(0, iLastIndexOf2)
            strSubstring4 = strSubstring3.substring(iLastIndexOf2)
            i = 1
        } else {
            strSubstring = strSubstring3
            i = 1
        }
        while (true) {
            File file = File(strSubstring2 + strSubstring + i + strSubstring4)
            if (!file.exists()) {
                return file
            }
            i++
        }
    }

    fun a(String str, String str2, Object obj, Array<Class> clsArr, Array<Object> objArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(obj, objArr)
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
            return null
        } catch (IllegalAccessException e2) {
            e2.printStackTrace()
            return null
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace()
            return null
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace()
            return null
        } catch (SecurityException e5) {
            e5.printStackTrace()
            return null
        } catch (InvocationTargetException e6) {
            e6.printStackTrace()
            return null
        }
    }

    fun a(String str, String str2, Array<Class> clsArr, Array<Object> objArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(null, objArr)
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
            return null
        } catch (IllegalAccessException e2) {
            e2.printStackTrace()
            return null
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace()
            return null
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace()
            return null
        } catch (SecurityException e5) {
            e5.printStackTrace()
            return null
        } catch (InvocationTargetException e6) {
            e6.printStackTrace()
            return null
        }
    }

    fun a(String str, Array<Class> clsArr, Array<Object> objArr) {
        try {
            return Class.forName(str).getConstructor(clsArr).newInstance(objArr)
        } catch (Exception e) {
            return null
        }
    }

    private fun a(Int i, Int i2, Int i3) {
        StringBuffer stringBuffer = StringBuffer(80)
        Int i4 = (i2 ^ (-1)) & i
        Int i5 = i & i2
        if ((i5 & 1) != 0) {
            stringBuffer.append("|public")
        }
        if ((i5 & 2) != 0) {
            stringBuffer.append("|private")
        }
        if ((i5 & 4) != 0) {
            stringBuffer.append("|protected")
        }
        if ((i5 & 8) != 0) {
            stringBuffer.append("|static")
        }
        if ((i5 & 16) != 0) {
            stringBuffer.append("|final")
        }
        if ((i5 & 32) != 0) {
            if (i3 == 1) {
                stringBuffer.append("|super")
            } else {
                stringBuffer.append("|synchronized")
            }
        }
        if ((i5 & 64) != 0) {
            if (i3 == 3) {
                stringBuffer.append("|bridge")
            } else {
                stringBuffer.append("|volatile")
            }
        }
        if ((i5 & 128) != 0) {
            if (i3 == 3) {
                stringBuffer.append("|varargs")
            } else {
                stringBuffer.append("|transient")
            }
        }
        if ((i5 & 256) != 0) {
            stringBuffer.append("|native")
        }
        if ((i5 & 512) != 0) {
            stringBuffer.append("|interface")
        }
        if ((i5 & 1024) != 0) {
            stringBuffer.append("|abstract")
        }
        if ((i5 & 2048) != 0) {
            stringBuffer.append("|strictfp")
        }
        if ((i5 & 4096) != 0) {
            stringBuffer.append("|synthetic")
        }
        if ((i5 & 8192) != 0) {
            stringBuffer.append("|annotation")
        }
        if ((i5 & 16384) != 0) {
            stringBuffer.append("|enum")
        }
        if ((65536 & i5) != 0) {
            stringBuffer.append("|constructor")
        }
        if ((i5 & 131072) != 0) {
            stringBuffer.append("|declared_synchronized")
        }
        if (i4 != 0 || stringBuffer.length() == 0) {
            stringBuffer.append('|')
            stringBuffer.append(v(i4))
        }
        return stringBuffer.substring(1)
    }

    fun a(Long j) {
        Array<Char> cArr = new Char[16]
        for (Int i = 0; i < 16; i++) {
            cArr[15 - i] = Character.forDigit(((Int) j) & 15, 16)
            j >>= 4
        }
        return String(cArr)
    }

    fun a(Context context, Uri uri, String str, Array<String> strArr) {
        Cursor cursor = null
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new Array<String>{"_data"}, str, strArr, null)
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"))
                        if (cursorQuery == null) {
                            return string
                        }
                        cursorQuery.close()
                        return string
                    }
                } catch (Throwable th) {
                    th = th
                    cursor = cursorQuery
                    if (cursor != null) {
                        cursor.close()
                    }
                    throw th
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close()
            }
            return null
        } catch (Throwable th2) {
            th = th2
        }
    }

    fun a(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getString(str)
        }
        return null
    }

    fun a(com.a.a.ClassA.b bVar, Array<Char> cArr) throws UTFDataFormatException {
        Int i = 0
        while (true) {
            Char cD = (Char) (bVar.d() & 255)
            if (cD == 0) {
                return String(cArr, 0, i)
            }
            cArr[i] = cD
            if (cD < 128) {
                i++
            } else if ((cD & 224) == 192) {
                Int iD = bVar.d() & 255
                if ((iD & 192) != 128) {
                    throw UTFDataFormatException("bad second Byte")
                }
                cArr[i] = (Char) (((cD & 31) << 6) | (iD & 63))
                i++
            } else {
                if ((cD & 240) != 224) {
                    throw UTFDataFormatException("bad Byte")
                }
                Int iD2 = bVar.d() & 255
                Int iD3 = bVar.d() & 255
                if ((iD2 & 192) != 128 || (iD3 & 192) != 128) {
                    break
                }
                cArr[i] = (Char) (((cD & 15) << 12) | ((iD2 & 63) << 6) | (iD3 & 63))
                i++
            }
        }
        throw UTFDataFormatException("bad second or third Byte")
    }

    fun a(InputStream inputStream) throws IOException {
        StringBuilder sb = StringBuilder()
        BufferedReader bufferedReader = BufferedReader(InputStreamReader(inputStream))
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            sb.append(line)
            sb.append("\n")
        }
        return sb.toString()
    }

    fun a(String str) {
        return str.replace("&", "&amp;").replace("<", "&lt;")
    }

    fun a(String str, Array<String> strArr) {
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < strArr.length; i++) {
            if (i == strArr.length - 1) {
                sb.append(strArr[i])
            } else {
                sb.append(strArr[i]).append(str)
            }
        }
        return sb.toString()
    }

    fun a(Array<Byte> bArr) {
        StringBuffer stringBuffer = StringBuffer()
        for (Int i = 0; i < bArr.length; i += 3) {
            stringBuffer.append(a(bArr, i))
        }
        return stringBuffer.toString()
    }

    fun a(Array<Byte> bArr, Int i, Int i2, Int i3, Int i4, Int i5) {
        Int i6 = i + i2
        if ((i | i2 | i6) < 0 || i6 > bArr.length) {
            throw IndexOutOfBoundsException("arr.length " + bArr.length + "; " + i + "..!" + i6)
        }
        if (i3 < 0) {
            throw IllegalArgumentException("outOffset < 0")
        }
        if (i2 == 0) {
            return ""
        }
        StringBuffer stringBuffer = StringBuffer((i2 << 2) + 6)
        Int i7 = 0
        while (i2 > 0) {
            if (i7 == 0) {
                stringBuffer.append(u(i3))
                stringBuffer.append(": ")
            } else if ((i7 & 1) == 0) {
                stringBuffer.append(' ')
            }
            stringBuffer.append(x(bArr[i]))
            i3++
            i++
            i7++
            if (i7 == i4) {
                stringBuffer.append('\n')
                i7 = 0
            }
            i2--
        }
        if (i7 != 0) {
            stringBuffer.append('\n')
        }
        return stringBuffer.toString()
    }

    fun a(com.gmail.heagoo.a.c.d dVar, File file, String str) throws com.gmail.heagoo.a.c.e {
        try {
            if (dVar.a(str)) {
                a(File(file, str))
                dVar.d(str).a(File(file, str))
            } else {
                File file2 = File(file, str)
                file2.getParentFile().mkdirs()
                a(dVar.b(str), FileOutputStream(file2))
            }
        } catch (IOException e) {
            throw new com.gmail.heagoo.a.c.e("File copy error: " + str, e)
        } catch (Exception e2) {
            throw new com.gmail.heagoo.a.c.e("File copy error: " + str, e2)
        }
    }

    fun a(Activity activity, String str, Int i) {
        Intent intentE = e(activity, str)
        if (intentE != null) {
            activity.startActivityForResult(intentE, i)
        }
    }

    fun a(Context context, String str) throws Throwable {
        Intent intent
        FileOutputStream fileOutputStream
        FileInputStream fileInputStream = null
        Uri uriFromFile = null
        fileInputStream = null
        if (Build.VERSION.SDK_INT >= 24) {
            intent = Intent("android.intent.action.INSTALL_PACKAGE")
            intent.setFlags(1)
            File externalFilesDir = context.getExternalFilesDir("apk")
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdir()
            }
            File file = File(externalFilesDir, "gen.apk")
            try {
                FileInputStream fileInputStream2 = FileInputStream(File(str))
                try {
                    fileOutputStream = FileOutputStream(file)
                    try {
                        b(fileInputStream2, fileOutputStream)
                        a((Closeable) fileInputStream2)
                        a(fileOutputStream)
                        try {
                            uriFromFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file)
                        } catch (Throwable th) {
                        }
                    } catch (Exception e) {
                        e = e
                        fileInputStream = fileInputStream2
                        try {
                            Toast.makeText(context, "Internal error: " + e.getMessage(), 1)
                            a((Closeable) fileInputStream)
                            a(fileOutputStream)
                            return
                        } catch (Throwable th2) {
                            th = th2
                            a((Closeable) fileInputStream)
                            a(fileOutputStream)
                            throw th
                        }
                    } catch (Throwable th3) {
                        th = th3
                        fileInputStream = fileInputStream2
                        a((Closeable) fileInputStream)
                        a(fileOutputStream)
                        throw th
                    }
                } catch (Exception e2) {
                    e = e2
                    fileOutputStream = null
                    fileInputStream = fileInputStream2
                } catch (Throwable th4) {
                    th = th4
                    fileOutputStream = null
                    fileInputStream = fileInputStream2
                }
            } catch (Exception e3) {
                e = e3
                fileOutputStream = null
            } catch (Throwable th5) {
                th = th5
                fileOutputStream = null
            }
        } else {
            intent = Intent("android.intent.action.VIEW")
            uriFromFile = Uri.fromFile(File(str))
        }
        if (uriFromFile != null) {
            try {
                intent.putExtra("android.intent.extra.NOT_UNKNOWN_SOURCE", true)
                intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive")
                intent.setFlags(268468224)
                intent.addFlags(1)
                context.startActivity(intent)
            } catch (Throwable th6) {
                th6.printStackTrace()
            }
        }
    }

    fun a(Context context, String str, String str2, Map<String, String> map, Map<String, String> map2, Set<String> set) {
        String str3
        String str4
        String absolutePath
        String absolutePath2
        if (isNullOrEmpty(map) && isNullOrEmpty(map2) && isNullOrEmpty(set)) {
            str4 = null
            str3 = str
        } else {
            str3 = str2 + ".signed"
            Int length = 0
            StringBuilder sb = StringBuilder()
            Char c = '\n'
            if (!isNullOrEmpty(map2)) {
                for (Map.Entry<String, String> entry : map2.entrySet()) {
                    String key = entry.getKey()
                    String value = entry.getValue()
                    sb.append(key)
                    sb.append('\n')
                    sb.append(value)
                    sb.append('\n')
                    length += key.getBytes().length + value.getBytes().length + 2
                }
            }
            StringBuilder sb2 = StringBuilder()
            Int length2 = 0
            if (!isNullOrEmpty(set)) {
                for (String str5 : set) {
                    sb2.append(str5)
                    sb2.append('\n')
                    length2 += str5.getBytes().length + 1
                }
            }
            StringBuilder sb3 = StringBuilder()
            Int length3 = 0
            if (!isNullOrEmpty(map)) {
                for (Map.Entry<String, String> entry2 : map.entrySet()) {
                    String key2 = entry2.getKey()
                    String value2 = entry2.getValue()
                    sb3.append(key2)
                    sb3.append(c)
                    sb3.append(value2)
                    sb3.append(c)
                    length3 += key2.getBytes().length + value2.getBytes().length + 2
                    c = '\n'
                }
            }
            MainActivity.md(str3, str, sb.toString(), length, sb2.toString(), length2, sb3.toString(), length3)
            str4 = str3
        }
        String strC = SettingActivity.c(context)
        if (strC.charAt(0) == 'c' && strC.charAt(1) == 'u') {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            String string = defaultSharedPreferences.getString("PublicKeyPath", "")
            absolutePath2 = defaultSharedPreferences.getString("PrivateKeyPath", "")
            absolutePath = string
        } else {
            File file = File(context.getFilesDir(), "signing")
            File file2 = File(file, strC + ".x509.pem")
            File file3 = File(file, strC + ".pk8")
            if (file2.exists() && file3.exists()) {
                absolutePath = file2.getAbsolutePath()
                absolutePath2 = file3.getAbsolutePath()
            } else {
                file.mkdir()
                try {
                    AssetManager assets = context.getAssets()
                    a(assets.open(strC + ".x509.pem"), FileOutputStream(file2))
                    a(assets.open(strC + ".pk8"), FileOutputStream(file3))
                } catch (IOException e) {
                    e.printStackTrace()
                }
                if (!file2.exists() || !file3.exists()) {
                    return
                }
                absolutePath = file2.getAbsolutePath()
                absolutePath2 = file3.getAbsolutePath()
            }
        }
        Array<Boolean> zArrEnabledSignatureVersions = SettingActivity.enabledSignatureVersions(context)
        b.a(absolutePath, absolutePath2, str3, str2, zArrEnabledSignatureVersions[0], zArrEnabledSignatureVersions[1], zArrEnabledSignatureVersions[2], zArrEnabledSignatureVersions[3])
        if (str4 != null) {
            File file4 = File(str4)
            if (file4.exists()) {
                file4.delete()
            }
        }
    }

    fun a(Intent intent, String str, Map map) {
        Bundle bundle = Bundle()
        ArrayList<String> arrayList = new ArrayList<>()
        ArrayList<String> arrayList2 = new ArrayList<>()
        for (String str2 : map.keySet()) {
            String str3 = (String) map.get(str2)
            arrayList.add(str2)
            arrayList2.add(str3)
        }
        bundle.putStringArrayList(str + "_keys", arrayList)
        bundle.putStringArrayList(str + "_values", arrayList2)
        intent.putExtras(bundle)
    }

    fun a(com.a.a.ClassA.c cVar, Int i) {
        for (Int i2 = i >>> 7; i2 != 0; i2 >>>= 7) {
            cVar.d((Byte) ((i & 127) | 128))
            i = i2
        }
        cVar.d((Byte) (i & 127))
    }

    fun a(com.a.a.ClassA.c cVar, Int i, Long j) {
        Int iNumberOfLeadingZeros = ((65 - Long.numberOfLeadingZeros((j >> 63) ^ j)) + 7) >> 3
        cVar.d(((iNumberOfLeadingZeros - 1) << 5) | i)
        while (iNumberOfLeadingZeros > 0) {
            cVar.d((Byte) j)
            j >>= 8
            iNumberOfLeadingZeros--
        }
    }

    private fun a(BufferedWriter bufferedWriter, Int i, String str) {
        for (Int i2 = 0; i2 < i; i2++) {
            bufferedWriter.write(CodeWriter.INDENT)
        }
        bufferedWriter.write("&lt;/<span class=\"end-tag\">" + str + "</span>&gt;")
    }

    private fun a(BufferedWriter bufferedWriter, Int i, String str, String str2) {
        bufferedWriter.write("\n")
        for (Int i2 = 0; i2 < i; i2++) {
            bufferedWriter.write(CodeWriter.INDENT)
        }
        bufferedWriter.write("<span class=\"attribute-name\">" + str + "</span>=<a class=\"attribute-value\">" + str2 + "</a>")
    }

    private fun a(BufferedWriter bufferedWriter, String str) {
        bufferedWriter.write(str.replaceAll("<", "&lt;").replace(">", "&gt;"))
    }

    fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    fun a(File file) {
        if (file.exists()) {
            Array<File> fileArrListFiles = file.listFiles()
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        a(file2)
                    } else {
                        file2.delete()
                    }
                }
            }
            file.delete()
        }
    }

    fun a(File file, File file2) throws Throwable {
        FileInputStream fileInputStream
        FileOutputStream fileOutputStream
        FileOutputStream fileOutputStream2 = null
        Array<File> fileArrListFiles = file2.listFiles()
        if (fileArrListFiles != null) {
            for (File file3 : fileArrListFiles) {
                if (file3.isFile()) {
                    try {
                        fileInputStream = FileInputStream(file3)
                        try {
                            fileOutputStream = FileOutputStream(File(file, file3.getName()))
                        } catch (Exception e) {
                            fileOutputStream = null
                        } catch (Throwable th) {
                            th = th
                        }
                    } catch (Exception e2) {
                        fileOutputStream = null
                        fileInputStream = null
                    } catch (Throwable th2) {
                        th = th2
                        fileInputStream = null
                    }
                    try {
                        b(fileInputStream, fileOutputStream)
                        a((Closeable) fileInputStream)
                        a(fileOutputStream)
                    } catch (Exception e3) {
                        a((Closeable) fileInputStream)
                        a(fileOutputStream)
                    } catch (Throwable th3) {
                        fileOutputStream2 = fileOutputStream
                        th = th3
                        a((Closeable) fileInputStream)
                        a(fileOutputStream2)
                        throw th
                    }
                } else if (file3.isDirectory()) {
                    File file4 = File(file, file3.getName())
                    file4.mkdir()
                    a(file4, file3)
                }
            }
        }
    }

    fun a(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            Array<Byte> bArr = new Byte[1024]
            Int i = inputStream.read(bArr)
            while (i > 0) {
                outputStream.write(bArr, 0, i)
                i = inputStream.read(bArr)
            }
        } finally {
            try {
                inputStream.close()
                outputStream.close()
            } catch (IOException e) {
            }
        }
    }

    fun a(InputStream inputStream, Array<Byte> bArr) {
        Int i
        Int i2 = 0
        while (i2 < bArr.length && (i = inputStream.read(bArr, i2, bArr.length - i2)) != -1) {
            i2 += i
        }
    }

    fun a(String str, String str2) {
        if (h) {
            return
        }
        if (str != null && str2 != null) {
            throw RuntimeException("optimize and don't optimize lists  are mutually exclusive.")
        }
        if (str != null) {
            f = m(str)
        }
        if (str2 != null) {
            g = m(str2)
        }
        h = true
    }

    fun a(List list, String str) throws IOException {
        if (list.isEmpty()) {
            return
        }
        BufferedWriter bufferedWriter = BufferedWriter(FileWriter(File(str)))
        bufferedWriter.write("<html><head>")
        bufferedWriter.write("<title>1.xml</title>")
        bufferedWriter.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"viewsource.css\">")
        bufferedWriter.write("</head>")
        bufferedWriter.write("<body id=\"viewsource\" class=\"wrap\" style=\"-moz-tab-size: 4\">")
        bufferedWriter.write("<pre id=\"line1\">")
        String str2 = (String) list.get(0)
        if (str2 != null) {
            bufferedWriter.write(str2.replaceAll("<", "&lt;").replace(">", "&gt;"))
        }
        Int i = 1
        Int i2 = 2
        Int i3 = 0
        while (true) {
            Int i4 = i
            if (i4 >= list.size()) {
                bufferedWriter.close()
                return
            }
            String strTrim = ((String) list.get(i4)).trim()
            bufferedWriter.write("\n<span id=\"line" + i2 + "\"></span>")
            Boolean z = false
            if (strTrim.length() >= 2 && strTrim.charAt(0) == '<') {
                Char cCharAt = strTrim.charAt(1)
                if ((cCharAt >= 'a' && cCharAt <= 'z') || ((cCharAt >= 'A' && cCharAt <= 'Z') || cCharAt == '_')) {
                    Boolean z2 = false
                    Boolean z3 = false
                    if (strTrim.endsWith(">")) {
                        z2 = true
                        if (strTrim.endsWith("/>")) {
                            z3 = true
                            strTrim = strTrim.substring(0, strTrim.length() - 2)
                        } else {
                            strTrim = strTrim.substring(0, strTrim.length() - 1)
                        }
                    }
                    Array<String> strArrSplit = strTrim.split(" ")
                    b(bufferedWriter, i3, strArrSplit[0].substring(1))
                    for (Int i5 = 1; i5 < strArrSplit.length; i5++) {
                        Int iIndexOf = strArrSplit[i5].indexOf("=")
                        if (iIndexOf != -1) {
                            a(bufferedWriter, i3 + 1, strArrSplit[i5].substring(0, iIndexOf), strArrSplit[i5].substring(iIndexOf + 1))
                        } else {
                            a(bufferedWriter, strArrSplit[i5])
                        }
                    }
                    if (z3) {
                        a(bufferedWriter, "/>")
                    } else if (z2) {
                        a(bufferedWriter, ">")
                    }
                    if (!z3) {
                        i3++
                    }
                    z = true
                } else if (cCharAt == '/') {
                    i3--
                    a(bufferedWriter, i3, strTrim.substring(2, strTrim.length() - 1))
                    z = true
                }
            }
            if (!z) {
                for (Int i6 = 0; i6 < i3; i6++) {
                    bufferedWriter.write(CodeWriter.INDENT)
                }
                bufferedWriter.write(strTrim.replaceAll("<", "&lt;").replace(">", "&gt;"))
            }
            i2++
            i = i4 + 1
        }
    }

    fun a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    fun a(Array<Byte> bArr, Int i, String str) {
        Int i2
        Int length = str.length()
        Int i3 = 0
        while (i3 < length) {
            Char cCharAt = str.charAt(i3)
            if (cCharAt != 0 && cCharAt <= 127) {
                i2 = i + 1
                bArr[i] = (Byte) cCharAt
            } else if (cCharAt <= 2047) {
                Int i4 = i + 1
                bArr[i] = (Byte) (((cCharAt >> 6) & 31) | 192)
                i2 = i4 + 1
                bArr[i4] = (Byte) ((cCharAt & '?') | 128)
            } else {
                Int i5 = i + 1
                bArr[i] = (Byte) (((cCharAt >> '\f') & 15) | 224)
                Int i6 = i5 + 1
                bArr[i5] = (Byte) (((cCharAt >> 6) & 63) | 128)
                i2 = i6 + 1
                bArr[i6] = (Byte) ((cCharAt & '?') | 128)
            }
            i3++
            i = i2
        }
    }

    fun a(Array<Int> iArr, Int i, Boolean z) {
        Int i2 = i >> 5
        iArr[i2] = (1 << (i & 31)) | iArr[i2]
    }

    fun a(Array<Int> iArr, Array<Int> iArr2) {
        for (Int i = 0; i < iArr2.length; i++) {
            iArr[i] = iArr[i] | iArr2[i]
        }
    }

    private fun a(Char c) {
        Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(c)
        return (Character.isISOControl(c) || c == 65535 || unicodeBlockOf == null || unicodeBlockOf == Character.UnicodeBlock.SPECIALS) ? false : true
    }

    fun a(Context context, String str, Boolean z) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, false)
    }

    fun a(String str, Object obj) {
        ObjectOutputStream objectOutputStream
        Boolean z
        ObjectOutputStream objectOutputStream2 = null
        try {
            try {
                objectOutputStream = ObjectOutputStream(FileOutputStream(File(str)))
            } catch (IOException e) {
                e = e
                objectOutputStream = null
            } catch (Throwable th) {
                th = th
                a(objectOutputStream2)
                throw th
            }
            try {
                objectOutputStream.writeObject(obj)
                objectOutputStream.flush()
                a(objectOutputStream)
                z = true
            } catch (IOException e2) {
                e = e2
                e.printStackTrace()
                a(objectOutputStream)
                z = false
                return z
            }
            return z
        } catch (Throwable th2) {
            th = th2
            objectOutputStream2 = objectOutputStream
            a(objectOutputStream2)
            throw th
        }
    }

    fun a(Boolean z, z zVar, Int i, Array<Char> cArr) {
        Int length = i + cArr.length
        if (length > zVar.c + zVar.f1538b) {
            return false
        }
        Array<Char> cArr2 = zVar.f1537a
        Int i2 = 0
        while (i < length) {
            Char upperCase = cArr2[i]
            Char upperCase2 = cArr[i2]
            if (z) {
                upperCase = Character.toUpperCase(upperCase)
                upperCase2 = Character.toUpperCase(upperCase2)
            }
            if (upperCase != upperCase2) {
                return false
            }
            i++
            i2++
        }
        return true
    }

    fun a(Array<Int> iArr, Int i) {
        return (iArr[i >> 5] & (1 << (i & 31))) != 0
    }

    fun a(Array<Int> iArr, Int i, Int i2) {
        Int iD = d(iArr, i)
        return iD >= 0 && iD < i2
    }

    private static Array<Char> a(Array<Byte> bArr, Int i) {
        Int length = (bArr.length - i) - 1
        Int i2 = length >= 2 ? 2 : length
        Int i3 = 0
        for (Int i4 = 0; i4 <= i2; i4++) {
            Int i5 = bArr[i + i4]
            if (i5 < 0) {
                i5 += 256
            }
            i3 += i5 << ((2 - i4) * 8)
        }
        Array<Char> cArr = new Char[4]
        for (Int i6 = 0; i6 < 4; i6++) {
            Int i7 = (i3 >>> ((3 - i6) * 6)) & 63
            cArr[i6] = (i7 < 0 || i7 > 25) ? (i7 < 26 || i7 > 51) ? (i7 < 52 || i7 > 61) ? i7 == 62 ? '+' : i7 == 63 ? '/' : '?' : (Char) ((i7 - 52) + 48) : (Char) ((i7 - 26) + 97) : (Char) (i7 + 65)
        }
        if (length <= 0) {
            cArr[2] = '='
        }
        if (length < 2) {
            cArr[3] = '='
        }
        return cArr
    }

    /* JADX WARN: Multi-variable type inference failed */
    private a.a.b.a.Array<e> a(com.gmail.heagoo.InterfaceA aVar, Boolean z) throws IOException, a.a.ExceptionA {
        ByteArrayInputStream byteArrayInputStream
        try {
            File file = File(this.e.getFilesDir().getAbsolutePath() + "/bin/resources.arsc")
            FileInputStream fileInputStream = FileInputStream(file)
            try {
                Array<Byte> bArr = new Byte[(Int) file.length()]
                a(fileInputStream, bArr)
                byteArrayInputStream = ByteArrayInputStream(bArr)
            } catch (Throwable th) {
                byteArrayInputStream = 0
            }
            a.a.b.a.Array<e> eVarArrA = a.a.b.b.a.a(byteArrayInputStream != 0 ? byteArrayInputStream : fileInputStream, false, true, aVar, null, false).a()
            if (byteArrayInputStream != 0) {
                try {
                    byteArrayInputStream.close()
                } catch (Exception e) {
                }
            }
            try {
                fileInputStream.close()
            } catch (Exception e2) {
            }
            return eVarArrA
        } catch (IOException e3) {
            throw new a.a.ExceptionA("Could not load resources.arsc", e3)
        }
    }

    fun aa(Context context, String str) {
        File file = File(str)
        if (!file.exists() && !file.isFile()) {
            Toast.makeText(context, "File " + file.getAbsolutePath() + " don't exist", 1).show()
            return
        }
        if (file.canRead()) {
            Installer(context).installApkFiles(file)
            return
        }
        Toast.makeText(context, "File " + file.getAbsolutePath() + " don't open", 1).show()
    }

    fun b(com.a.a.ClassA.b bVar) {
        Int iD
        Int i = 0
        Int i2 = 0
        do {
            iD = bVar.d() & 255
            i2 |= (iD & 127) << (i * 7)
            i++
            if ((iD & 128) != 128) {
                break
            }
        } while (i < 5);
        if ((iD & 128) == 128) {
            throw s("invalid LEB128 sequence")
        }
        return i2
    }

    fun b(Array<Byte> bArr) {
        if (bArr.length != 8 || bArr[0] != 100 || bArr[1] != 101 || bArr[2] != 120 || bArr[3] != 10 || bArr[7] != 0) {
            return -1
        }
        String string = StringBuilder().append((Char) bArr[4]).append((Char) bArr[5]).append((Char) bArr[6]).toString()
        if (string.equals("036")) {
            return 14
        }
        return string.equals("035") ? 13 : -1
    }

    fun b(com.a.a.ClassA.b bVar, Int i) {
        Long jD = 0
        for (Int i2 = i; i2 >= 0; i2--) {
            jD = (jD >>> 8) | ((bVar.d() & 255) << 56)
        }
        return jD >> ((7 - i) << 3)
    }

    fun b(com.a.a.ClassA.b bVar, Int i, Boolean z) {
        Long jD = 0
        while (i >= 0) {
            jD = (jD >>> 8) | ((bVar.d() & 255) << 56)
            i--
        }
        return jD
    }

    fun b(Intent intent, String str, Boolean z) {
        Bundle bundle = Bundle()
        bundle.putBoolean(str, z)
        intent.putExtras(bundle)
        return bundle
    }

    fun b(Long j) {
        Array<Char> cArr = new Char[17]
        if (j < 0) {
            cArr[0] = '-'
            j = -j
        } else {
            cArr[0] = '+'
        }
        for (Int i = 0; i < 16; i++) {
            cArr[16 - i] = Character.forDigit(((Int) j) & 15, 16)
            j >>= 4
        }
        return String(cArr)
    }

    fun b(Context context) {
        ClipData primaryClip = ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip()
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null
        }
        return primaryClip.getItemAt(0).coerceToText(context).toString()
    }

    fun b(String str) {
        if (str == null || str.isEmpty()) {
            return str
        }
        Array<Char> charArray = str.toCharArray()
        StringBuilder sb = StringBuilder(str.length() + 10)
        switch (charArray[0]) {
            case '#':
            case '?':
            case '@':
                sb.append('\\')
                break
        }
        for (Char c : charArray) {
            switch (c) {
                case '\n':
                    sb.append("\\n")
                    continue
                case '\"':
                    sb.append("&quot;")
                    continue
                case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                    sb.append('\\')
                    break
                default:
                    if (!a(c)) {
                        sb.append(String.format("\\u%04x", Integer.valueOf(c)))
                    }
                    break
            }
            sb.append(c)
        }
        return sb.toString()
    }

    fun b(Context context, String str, Boolean z) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editorEdit.putBoolean(str, true)
        editorEdit.commit()
    }

    fun b(com.a.a.ClassA.c cVar, Int i) {
        Int i2 = i >> 7
        Int i3 = (Integer.MIN_VALUE & i) == 0 ? 0 : -1
        Boolean z = true
        Int i4 = i2
        while (z) {
            z = (i4 == i3 && (i4 & 1) == ((i >> 6) & 1)) ? false : true
            cVar.d((Byte) ((z ? 128 : 0) | (i & 127)))
            i = i4
            i4 >>= 7
        }
    }

    fun b(com.a.a.ClassA.c cVar, Int i, Long j) {
        Int iNumberOfLeadingZeros = 64 - Long.numberOfLeadingZeros(j)
        if (iNumberOfLeadingZeros == 0) {
            iNumberOfLeadingZeros = 1
        }
        Int i2 = (iNumberOfLeadingZeros + 7) >> 3
        cVar.d(((i2 - 1) << 5) | i)
        while (i2 > 0) {
            cVar.d((Byte) j)
            j >>= 8
            i2--
        }
    }

    private fun b(BufferedWriter bufferedWriter, Int i, String str) {
        for (Int i2 = 0; i2 < i; i2++) {
            bufferedWriter.write(CodeWriter.INDENT)
        }
        bufferedWriter.write("&lt;<span class=\"start-tag\">" + str + "</span>")
    }

    fun b(InputStream inputStream, OutputStream outputStream) throws IOException {
        Array<Byte> bArr = new Byte[4096]
        while (true) {
            Int i = inputStream.read(bArr)
            if (i == -1) {
                return
            } else {
                outputStream.write(bArr, 0, i)
            }
        }
    }

    fun b(String str, String str2) {
        FileOutputStream fileOutputStream
        try {
            fileOutputStream = FileOutputStream(str)
            try {
                fileOutputStream.write(str2.getBytes())
                a(fileOutputStream)
            } catch (Throwable th) {
                th = th
                a(fileOutputStream)
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            fileOutputStream = null
        }
    }

    fun b(Array<Int> iArr, Int i) {
        Int i2 = i >> 5
        iArr[i2] = (1 << (i & 31)) | iArr[i2]
    }

    fun b(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getBoolean(str, false)
        }
        return false
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:
    
        if (r1.l() == false) goto L19
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
    
        if (r0.l() == false) goto L19
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:?, code lost:
    
        return true
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:?, code lost:
    
        return false
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun b(com.a.b.f.d.d r9, com.a.b.f.d.d r10) {
        /*
            r7 = 10
            r5 = 9
            r3 = 0
            r2 = 1
        L6:
            com.a.b.f.d.c r6 = r9.a()
            com.a.b.f.d.c r4 = r10.a()
            Boolean r0 = r6.equals(r4)
            if (r0 == 0) goto L16
            r0 = r2
        L15:
            return r0
        L16:
            Int r1 = r6.c()
            Int r0 = r4.c()
            if (r1 != r7) goto L8b
            com.a.b.f.d.c r1 = com.a.b.f.d.c.n
            r6 = r5
        L23:
            if (r0 != r7) goto L87
            com.a.b.f.d.c r0 = com.a.b.f.d.c.n
            r4 = r5
        L28:
            if (r6 != r5) goto L2c
            if (r4 == r5) goto L3c
        L2c:
            Boolean r1 = r1.l()
            if (r1 == 0) goto L3a
            Boolean r0 = r0.l()
            if (r0 == 0) goto L3a
            r0 = r2
            goto L15
        L3a:
            r0 = r3
            goto L15
        L3c:
            com.a.b.f.d.c r4 = com.a.b.f.d.c.j
            if (r1 != r4) goto L42
            r0 = r3
            goto L15
        L42:
            com.a.b.f.d.c r4 = com.a.b.f.d.c.j
            if (r0 != r4) goto L48
            r0 = r2
            goto L15
        L48:
            com.a.b.f.d.c r4 = com.a.b.f.d.c.n
            if (r1 != r4) goto L4e
            r0 = r2
            goto L15
        L4e:
            Boolean r4 = r1.o()
            if (r4 == 0) goto L73
            Boolean r4 = r0.o()
            if (r4 != 0) goto L5c
            r0 = r3
            goto L15
        L5c:
            com.a.b.f.d.c r1 = r1.s()
            com.a.b.f.d.c r0 = r0.s()
            Boolean r4 = r1.o()
            if (r4 == 0) goto L70
            Boolean r4 = r0.o()
            if (r4 != 0) goto L5c
        L70:
            r10 = r0
            r9 = r1
            goto L6
        L73:
            Boolean r0 = r0.o()
            if (r0 == 0) goto L85
            com.a.b.f.d.c r0 = com.a.b.f.d.c.o
            if (r1 == r0) goto L81
            com.a.b.f.d.c r0 = com.a.b.f.d.c.m
            if (r1 != r0) goto L83
        L81:
            r0 = r2
            goto L15
        L83:
            r0 = r3
            goto L15
        L85:
            r0 = r2
            goto L15
        L87:
            r8 = r0
            r0 = r4
            r4 = r8
            goto L28
        L8b:
            r8 = r1
            r1 = r6
            r6 = r8
            goto L23
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.a.c.a.b(com.a.b.f.d.d, com.a.b.f.d.d):Boolean")
    }

    private fun b(String str, Array<String> strArr) {
        for (String str2 : strArr) {
            if (str.endsWith(str2)) {
                return true
            }
        }
        return false
    }

    fun b(Array<Int> iArr) {
        for (Int i : iArr) {
            if (i != 0) {
                return false
            }
        }
        return true
    }

    public static Array<Byte> b(File file) throws IOException {
        if (!file.exists()) {
            throw RuntimeException(file + ": file not found")
        }
        if (!file.isFile()) {
            throw RuntimeException(file + ": not a file")
        }
        if (!file.canRead()) {
            throw RuntimeException(file + ": file not readable")
        }
        Long length = file.length()
        Int i = (Int) length
        if (i != length) {
            throw RuntimeException(file + ": file too Long")
        }
        Array<Byte> bArr = new Byte[i]
        try {
            FileInputStream fileInputStream = FileInputStream(file)
            Int i2 = 0
            while (i > 0) {
                Int i3 = fileInputStream.read(bArr, i2, i)
                if (i3 == -1) {
                    throw RuntimeException(file + ": unexpected EOF")
                }
                i2 += i3
                i -= i3
            }
            fileInputStream.close()
            return bArr
        } catch (IOException e) {
            throw RuntimeException(file + ": trouble reading", e)
        }
    }

    fun c(Array<Int> iArr) {
        Int iBitCount = 0
        for (Int i : iArr) {
            iBitCount += Integer.bitCount(i)
        }
        return iBitCount
    }

    fun c(Int i) {
        return i >= 14 ? "dex\n036\u0000" : "dex\n035\u0000"
    }

    fun c(String str) {
        Boolean z
        Boolean z2
        if (str == null || str.isEmpty()) {
            return str
        }
        Array<Char> charArray = str.toCharArray()
        StringBuilder sb = StringBuilder(str.length() + 16)
        switch (charArray[0]) {
            case '#':
            case '?':
            case '@':
                sb.append('\\')
                break
        }
        Int length = charArray.length
        Int i = 0
        Boolean z3 = true
        Boolean z4 = false
        Int length2 = 0
        Boolean z5 = false
        while (i < length) {
            Char c = charArray[i]
            if (z5) {
                if (c == '>') {
                    length2 = sb.length() + 1
                    z5 = false
                    z2 = z3
                    z = false
                } else {
                    Boolean z6 = z3
                    z = z4
                    z2 = z6
                }
            } else if (c == ' ') {
                if (z3) {
                    z4 = true
                }
                z = z4
                z2 = true
            } else {
                switch (c) {
                    case '\n':
                    case '\'':
                        z2 = false
                        z = true
                        break
                    case '\"':
                        sb.append('\\')
                        z = z4
                        z2 = false
                        break
                    case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                        if (!z4) {
                            z = z4
                            z5 = true
                            z2 = false
                            break
                        } else {
                            sb.insert(length2, '\"').append('\"')
                            z = z4
                            z5 = true
                            z2 = false
                            break
                        }
                    case R.styleable.AppCompatTheme_colorSwitchThumbNormal /* 92 */:
                        sb.append('\\')
                        z = z4
                        z2 = false
                        break
                    default:
                        if (!a(c)) {
                            if (sb.length() + 1 != str.length() || c != 0) {
                                sb.append(String.format("\\u%04x", Integer.valueOf(c)))
                                z = z4
                                z2 = false
                                break
                            } else {
                                z = z4
                                z2 = false
                                break
                            }
                        } else {
                            z = z4
                            z2 = false
                            break
                        }
                }
                i++
                Boolean z7 = z2
                z4 = z
                z3 = z7
            }
            sb.append(c)
            i++
            Boolean z72 = z2
            z4 = z
            z3 = z72
        }
        if (z4 || z3) {
            sb.insert(length2, '\"').append('\"')
        }
        return sb.toString()
    }

    fun c(Array<Byte> bArr) {
        StringBuilder sb = StringBuilder("")
        if (bArr == null || bArr.length <= 0) {
            return null
        }
        for (Byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255)
            if (hexString.length() < 2) {
                sb.append(0)
            }
            sb.append(hexString)
        }
        return sb.toString()
    }

    fun c(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getStringArrayList(str)
        }
        return null
    }

    fun c(Context context, String str) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("", str))
    }

    fun c(com.a.a.ClassA.c cVar, Int i, Long j) {
        Int iNumberOfTrailingZeros = 64 - Long.numberOfTrailingZeros(j)
        if (iNumberOfTrailingZeros == 0) {
            iNumberOfTrailingZeros = 1
        }
        Int i2 = (iNumberOfTrailingZeros + 7) >> 3
        Long j2 = j >> (64 - (i2 << 3))
        cVar.d(((i2 - 1) << 5) | i)
        while (i2 > 0) {
            cVar.d((Byte) j2)
            j2 >>= 8
            i2--
        }
    }

    fun c(Array<Int> iArr, Int i) {
        Int i2 = i >> 5
        iArr[i2] = ((1 << (i & 31)) ^ (-1)) & iArr[i2]
    }

    fun c(String str, String str2) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1)
        }
        if (str2.endsWith("/")) {
            str2 = str2.substring(0, str2.length() - 1)
        }
        Array<String> strArrSplit = str.split("/")
        Array<String> strArrSplit2 = str2.split("/")
        if (strArrSplit.length >= strArrSplit2.length) {
            return false
        }
        for (Int i = 0; i < strArrSplit.length; i++) {
            if (!strArrSplit[i].equals(strArrSplit2[i])) {
                return false
            }
        }
        return true
    }

    fun d(Int i) {
        Int i2 = i >> 7
        Int i3 = 0
        while (i2 != 0) {
            i2 >>= 7
            i3++
        }
        return i3 + 1
    }

    fun d(Array<Int> iArr, Int i) {
        Int length = iArr.length
        Int i2 = i & 31
        for (Int i3 = i >> 5; i3 < length; i3++) {
            Int i4 = iArr[i3]
            if (i4 != 0) {
                Int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros((((1 << i2) - 1) ^ (-1)) & i4)
                if (iNumberOfTrailingZeros == 32) {
                    iNumberOfTrailingZeros = -1
                }
                if (iNumberOfTrailingZeros >= 0) {
                    return (i3 << 5) + iNumberOfTrailingZeros
                }
            }
            i2 = 0
        }
        return -1
    }

    fun d(Context context, String str) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            throw Exception("Can not find sd card.")
        }
        String str2 = Environment.getExternalStorageDirectory().getPath() + ("/ApkEditor/" + str + "/")
        File file = File(str2)
        if (!file.exists()) {
            file.mkdirs()
        }
        return str2
    }

    fun d(String str, String str2) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1)
        }
        if (str2.endsWith("/")) {
            str2 = str2.substring(0, str2.length() - 1)
        }
        Array<String> strArrSplit = str.split("/")
        Array<String> strArrSplit2 = str2.split("/")
        if (strArrSplit.length < strArrSplit2.length) {
            return strArrSplit2[strArrSplit.length]
        }
        return null
    }

    fun d(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            HashMap map = HashMap()
            ArrayList<String> stringArrayList = extras.getStringArrayList(str + "_keys")
            ArrayList<String> stringArrayList2 = extras.getStringArrayList(str + "_values")
            if (stringArrayList != null && stringArrayList2 != null) {
                for (Int i = 0; i < stringArrayList.size(); i++) {
                    map.put(stringArrayList.get(i), stringArrayList2.get(i))
                }
                return map
            }
        }
        return null
    }

    fun d(String str) {
        e eVarA = a(str, 4)
        if (!((List) eVarA.f71a).isEmpty()) {
            if (((List) eVarA.f72b).size() + ((List) eVarA.f71a).size() > 1) {
                return true
            }
        }
        return false
    }

    fun e(Context context, String str) {
        Uri uriForFile
        Intent intent = null
        try {
            uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", File(str))
        } catch (Throwable th) {
            uriForFile = null
        }
        if (uriForFile != null) {
            if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeImage))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "image/*")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeWebText))) {
                intent = Intent("android.intent.action.VIEW")
                intent.setDataAndType(uriForFile, "text/html")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypePackage))) {
                intent = Intent()
                intent.setAction("android.intent.action.VIEW")
                intent.setDataAndType(uriForFile, "application/vnd.android.package-archive")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeAudio))) {
                intent = Intent("android.intent.action.VIEW")
                intent.putExtra("oneshot", 0)
                intent.putExtra("configchange", 0)
                intent.setDataAndType(uriForFile, "audio/*")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeVideo))) {
                intent = Intent("android.intent.action.VIEW")
                intent.putExtra("oneshot", 0)
                intent.putExtra("configchange", 0)
                intent.setDataAndType(uriForFile, "video/*")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeText))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "text/plain")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypePdf))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "application/pdf")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeWord))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "application/msword")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypeExcel))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "application/vnd.ms-excel")
            } else if (b(str, context.getResources().getStringArray(com.gmail.heagoo.apkeditor.pro.R.array.fileTypePPT))) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(uriForFile, "application/vnd.ms-powerpoint")
            } else {
                intent = Intent("android.intent.action.VIEW")
                intent.setDataAndType(uriForFile, "*/*")
            }
            intent.setFlags(3)
        }
        return intent
    }

    fun e(String str, String str2) throws NoSuchFieldException {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2)
            declaredField.setAccessible(true)
            return declaredField.get(null)
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
            return null
        } catch (IllegalAccessException e2) {
            e2.printStackTrace()
            return null
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace()
            return null
        } catch (NoSuchFieldException e4) {
            e4.printStackTrace()
            return null
        } catch (SecurityException e5) {
            e5.printStackTrace()
            return null
        }
    }

    fun e(String str) {
        Int i = 0
        e eVarA = a(str, 4)
        if (((List) eVarA.f71a).isEmpty()) {
            return str
        }
        if (((List) eVarA.f72b).size() + ((List) eVarA.f71a).size() < 2) {
            return str
        }
        List list = (List) eVarA.f71a
        StringBuilder sb = StringBuilder()
        Iterator it = list.iterator()
        Int iIntValue = 0
        while (it.hasNext()) {
            Integer numValueOf = Integer.valueOf(((Integer) it.next()).intValue() + 1)
            Int i2 = i + 1
            sb.append(str.substring(iIntValue, numValueOf.intValue())).append(i2).append('$')
            iIntValue = numValueOf.intValue()
            i = i2
        }
        sb.append(str.substring(iIntValue))
        return sb.toString()
    }

    fun e(Int i) {
        if (i < -1) {
            return false
        }
        if (i == -1) {
            return true
        }
        Int i2 = i & 255
        if (i2 == 0 || i2 == 255) {
            return true
        }
        return (65280 & i) == 0
    }

    fun f(Int i) {
        Int i2 = i & 255
        return (i2 == 0 || i2 == 255) ? i : i2
    }

    private fun f(Context context, String str) throws Resources.NotFoundException {
        File file = File(str)
        Boolean z = file.exists() && file.length() > ((Long) (SettingEditorActivity.d(context) << 10))
        if (z) {
            Toast.makeText(context, com.gmail.heagoo.apkeditor.pro.R.string.use_bfe_tip, 0)
        }
        return z
    }

    public static Array<Byte> f(String str) throws UTFDataFormatException {
        Long j = 0
        Int length = str.length()
        for (Int i = 0; i < length; i++) {
            Char cCharAt = str.charAt(i)
            j = (cCharAt == 0 || cCharAt > 127) ? cCharAt <= 2047 ? j + 2 : j + 3 : j + 1
            if (j > 65535) {
                throw UTFDataFormatException("String more than 65535 UTF bytes Long")
            }
        }
        Array<Byte> bArr = new Byte[(Int) j]
        a(bArr, 0, str)
        return bArr
    }

    fun g(Int i) {
        return a(i, 30257, 1)
    }

    fun g(String str) {
        return str.endsWith(".zip") || str.endsWith(".jar") || str.endsWith(".apk")
    }

    fun h(Int i) {
        return a(i, 30239, 1)
    }

    fun h(String str) {
        return f != null ? f.contains(str) : g == null || !g.contains(str)
    }

    fun i(Int i) {
        return a(i, 20703, 2)
    }

    fun i(String str) throws Throwable {
        RandomAccessFile randomAccessFile
        Array<Byte> bArr = new Byte[4096]
        try {
            randomAccessFile = RandomAccessFile(str, "rw")
            Int i = 0
            while (true) {
                try {
                    Int i2 = randomAccessFile.read(bArr)
                    if (i2 == -1) {
                        randomAccessFile.close()
                        randomAccessFile.close()
                        return
                    }
                    for (Int i3 = 0; i3 < i2; i3++) {
                        bArr[i3] = (Byte) (bArr[i3] ^ 85)
                    }
                    randomAccessFile.seek(i)
                    randomAccessFile.write(bArr, 0, i2)
                    i += i2
                } catch (Throwable th) {
                    th = th
                    if (randomAccessFile != null) {
                        randomAccessFile.close()
                    }
                    throw th
                }
            }
        } catch (Throwable th2) {
            th = th2
            randomAccessFile = null
        }
    }

    fun isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty()
    }

    fun isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty()
    }

    fun j(String str) throws Throwable {
        ObjectInputStream objectInputStream
        Throwable th
        Object object = null
        if (str != null) {
            try {
                objectInputStream = ObjectInputStream(FileInputStream(File(str)))
            } catch (Exception e) {
                e = e
                objectInputStream = null
            } catch (Throwable th2) {
                objectInputStream = null
                th = th2
                a((Closeable) objectInputStream)
                throw th
            }
            try {
                try {
                    object = objectInputStream.readObject()
                    a((Closeable) objectInputStream)
                } catch (Throwable th3) {
                    th = th3
                    a((Closeable) objectInputStream)
                    throw th
                }
            } catch (Exception e2) {
                e = e2
                e.printStackTrace()
                a((Closeable) objectInputStream)
                return object
            }
        }
        return object
    }

    fun j(Int i) {
        return a(i, 204287, 3)
    }

    fun k(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(str.getBytes())
            Array<Byte> bArrDigest = messageDigest.digest()
            Array<Char> cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}
            Array<Char> cArr2 = new Char[bArrDigest.length << 1]
            Int i = 0
            for (Byte b2 : bArrDigest) {
                Int i2 = i + 1
                cArr2[i] = cArr[(b2 >>> 4) & 15]
                i = i2 + 1
                cArr2[i2] = cArr[b2 & Token.LITERAL3]
            }
            return String(cArr2)
        } catch (NoSuchAlgorithmException e) {
            return null
        }
    }

    fun k(Int i) {
        return (i & 2) != 0
    }

    fun l(String str) {
        if (str != null) {
            return str.substring(str.lastIndexOf(47) + 1)
        }
        return null
    }

    fun l(Int i) {
        return (i & 8) != 0
    }

    private fun m(String str) throws IOException {
        HashSet hashSet = HashSet()
        try {
            FileReader fileReader = FileReader(str)
            BufferedReader bufferedReader = BufferedReader(fileReader)
            while (true) {
                String line = bufferedReader.readLine()
                if (line == null) {
                    fileReader.close()
                    return hashSet
                }
                hashSet.add(line)
            }
        } catch (IOException e) {
            throw RuntimeException("Error with optimize list: " + str, e)
        }
    }

    fun m(Int i) {
        return (i & 32) != 0
    }

    fun n(Int i) {
        return (i & 1024) != 0
    }

    fun o(Int i) {
        return (i & 256) != 0
    }

    fun p(Int i) {
        return (i & 8192) != 0
    }

    fun q(Int i) {
        switch (i) {
            case 1:
                return "nop"
            case 2:
                return "move"
            case 3:
                return "move-param"
            case 4:
                return "move-exception"
            case 5:
                return "const"
            case 6:
                return "goto"
            case 7:
                return "if-eq"
            case 8:
                return "if-ne"
            case 9:
                return "if-lt"
            case 10:
                return "if-ge"
            case 11:
                return "if-le"
            case 12:
                return "if-gt"
            case 13:
                return "switch"
            case 14:
                return "add"
            case 15:
                return "sub"
            case 16:
                return "mul"
            case 17:
                return "div"
            case 18:
                return "rem"
            case 19:
                return "neg"
            case 20:
                return "and"
            case 21:
                return "or"
            case 22:
                return "xor"
            case 23:
                return "shl"
            case 24:
                return "shr"
            case 25:
                return "ushr"
            case 26:
                return "not"
            case 27:
                return "cmpl"
            case 28:
                return "cmpg"
            case 29:
                return "conv"
            case 30:
                return "to-Byte"
            case 31:
                return "to-Char"
            case 32:
                return "to-Short"
            case 33:
                return "return"
            case 34:
                return "array-length"
            case 35:
                return "throw"
            case 36:
                return "monitor-enter"
            case 37:
                return "monitor-exit"
            case 38:
                return "aget"
            case 39:
                return "aput"
            case 40:
                return "new-instance"
            case 41:
                return "new-array"
            case 42:
                return "filled-new-array"
            case 43:
                return "check-cast"
            case 44:
                return "instance-of"
            case 45:
                return "get-field"
            case 46:
                return "get-static"
            case 47:
                return "put-field"
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /* 48 */:
                return "put-static"
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                return "invoke-static"
            case 50:
                return "invoke-virtual"
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
                return "invoke-super"
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                return "invoke-direct"
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
                return "invoke-interface"
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
            default:
                return "unknown-" + x(i)
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
                return "move-result"
            case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
                return "move-result-pseudo"
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
                return "fill-array-data"
        }
    }

    fun r(Int i) {
        switch (i) {
            case 7:
            case 8:
                return i
            case 9:
                return 12
            case 10:
                return 11
            case 11:
                return 10
            case 12:
                return 9
            default:
                throw RuntimeException("Unrecognized IF regop: " + i)
        }
    }

    public static Array<Int> s(Int i) {
        return new Int[(i + 31) >> 5]
    }

    fun t(Int i) {
        Array<Char> cArr = new Char[8]
        for (Int i2 = 0; i2 < 8; i2++) {
            cArr[7 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun u(Int i) {
        Array<Char> cArr = new Char[6]
        for (Int i2 = 0; i2 < 6; i2++) {
            cArr[5 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun v(Int i) {
        Array<Char> cArr = new Char[4]
        for (Int i2 = 0; i2 < 4; i2++) {
            cArr[3 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun w(Int i) {
        return i == ((Char) i) ? v(i) : t(i)
    }

    fun x(Int i) {
        Array<Char> cArr = new Char[2]
        for (Int i2 = 0; i2 < 2; i2++) {
            cArr[1 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun y(Int i) {
        return String(new Array<Char>{Character.forDigit(i & 15, 16)})
    }

    fun z(Int i) {
        Array<Char> cArr = new Char[9]
        if (i < 0) {
            cArr[0] = '-'
            i = -i
        } else {
            cArr[0] = '+'
        }
        for (Int i2 = 0; i2 < 8; i2++) {
            cArr[8 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    public a.a.b.a.e a(com.gmail.heagoo.InterfaceA aVar, Int i) throws a.a.ExceptionA, IOException {
        a.a.b.a.e eVarB
        StringBuilder("*************************************id=").append(i)
        if (i != 1 || (eVarB = bv.a().b()) == null) {
            Long jCurrentTimeMillis = System.currentTimeMillis()
            a.a.b.a.Array<e> eVarArrA = a(aVar, true)
            if (eVarArrA.length != 1) {
                throw new a.a.ExceptionA("Arsc files with zero or multiple packages")
            }
            eVarB = eVarArrA[0]
            if (eVarB.e() != i) {
                throw new a.a.ExceptionA("Expected pkg of id: " + String.valueOf(i) + ", got: " + eVarB.e())
            }
            aVar.a(eVarB, false)
            StringBuilder("Loaded. Time=").append(System.currentTimeMillis() - jCurrentTimeMillis)
            bv.a().a(eVarB)
        } else {
            aVar.a(eVarB, false)
        }
        return eVarB
    }

    fun a(Int i) {
        if ((i >> 24) == 0) {
            i |= 33554432
        }
        return a(new a.a.b.a.d(i))
    }

    fun a(com.gmail.heagoo.a.c.d dVar) {
        a.a.b.a.e eVarB = b(dVar.f27a)
        if (eVarB != null) {
            return eVarB.b(dVar)
        }
        return null
    }

    fun a() {
        return this.c
    }

    fun a(a.a.b.a.e eVar, Boolean z) throws a.a.ExceptionA {
        Integer numValueOf = Integer.valueOf(eVar.e())
        if (this.f743a.containsKey(numValueOf)) {
            throw new a.a.ExceptionA("Multiple packages: id=" + numValueOf.toString())
        }
        String strF = eVar.f()
        if (this.f744b.containsKey(strF)) {
            throw new a.a.ExceptionA("Multiple packages: name=" + strF)
        }
        this.f743a.put(numValueOf, eVar)
        this.f744b.put(strF, eVar)
        if (z) {
            this.c.add(eVar)
        } else {
            this.d.add(eVar)
        }
    }

    public a.a.b.a.e b(Int i) {
        a.a.b.a.e eVar = (a.a.b.a.e) this.f743a.get(Integer.valueOf(i))
        if (eVar != null) {
            return eVar
        }
        if (this.f743a.get(1) == null) {
            try {
                return a(this, i)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        return null
    }
}
