package com.gmail.heagoo.a.c

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Xml
import com.gmail.heagoo.apkeditor.gzd
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.InvocationTargetException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Enumeration
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlSerializer

@SuppressLint({"NewApi"})
class ax {
    private static HashSet f
    private static HashSet g
    private static Boolean h

    /* renamed from: a, reason: collision with root package name */
    private val f745a = HashMap()

    /* renamed from: b, reason: collision with root package name */
    private val f746b = HashMap()
    private val c = LinkedHashSet()
    private val d = LinkedHashSet()
    private Context e

    constructor() {
        LinkedHashMap()
        new a.a.b.c.b()
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004a A[Catch: Exception -> 0x0063, TRY_LEAVE, TryCatch #10 {Exception -> 0x0063, blocks: (B:22:0x0045, B:24:0x004a), top: B:60:0x0045 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.io.File r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            r1 = 0
            r0 = r1
            java.io.FileInputStream r0 = (java.io.FileInputStream) r0
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1
            r2 = 0
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch: java.lang.OutOfMemoryError -> L3e java.lang.Exception -> L50 java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.OutOfMemoryError -> L3e java.lang.Exception -> L50 java.lang.Throwable -> L67
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.OutOfMemoryError -> L3e java.lang.Exception -> L50 java.lang.Throwable -> L67
            r4.<init>(r6)     // Catch: java.lang.OutOfMemoryError -> L3e java.lang.Exception -> L50 java.lang.Throwable -> L67
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L6e java.lang.OutOfMemoryError -> L75
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L6e java.lang.OutOfMemoryError -> L75
            r0.<init>(r4, r7)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L6e java.lang.OutOfMemoryError -> L75
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L6e java.lang.OutOfMemoryError -> L75
            r0 = r2
        L1c:
            java.lang.String r1 = r3.readLine()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L71 java.lang.OutOfMemoryError -> L78
            if (r1 != 0) goto L31
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L71 java.lang.OutOfMemoryError -> L78
            if (r4 == 0) goto L2b
            r4.close()     // Catch: java.lang.Throwable -> L6a java.lang.OutOfMemoryError -> L78 java.lang.Exception -> L7c
        L2b:
            if (r3 == 0) goto L30
            r3.close()     // Catch: java.lang.Throwable -> L6a java.lang.OutOfMemoryError -> L78 java.lang.Exception -> L7c
        L30:
            return r0
        L31:
            if (r0 <= 0) goto L38
            java.lang.String r2 = "\n"
            r5.append(r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L71 java.lang.OutOfMemoryError -> L78
        L38:
            r5.append(r1)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L71 java.lang.OutOfMemoryError -> L78
            Int r0 = r0 + 1
            goto L1c
        L3e:
            r2 = move-exception
            r4 = r0
        L40:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L56
        L43:
            if (r4 == 0) goto L48
            r4.close()     // Catch: java.lang.Exception -> L63
        L48:
            if (r1 == 0) goto L4d
            r1.close()     // Catch: java.lang.Exception -> L63
        L4d:
            java.lang.String r0 = ""
            goto L30
        L50:
            r2 = move-exception
            r4 = r0
        L52:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L56
            goto L43
        L56:
            r0 = move-exception
            r2 = r0
        L58:
            if (r4 == 0) goto L5d
            r4.close()     // Catch: java.lang.Exception -> L65
        L5d:
            if (r1 == 0) goto L62
            r1.close()     // Catch: java.lang.Exception -> L65
        L62:
            throw r2
        L63:
            r0 = move-exception
            goto L4d
        L65:
            r0 = move-exception
            goto L62
        L67:
            r2 = move-exception
            r4 = r0
            goto L58
        L6a:
            r0 = move-exception
            r2 = r0
            r1 = r3
            goto L58
        L6e:
            r0 = move-exception
            r2 = r0
            goto L52
        L71:
            r0 = move-exception
            r2 = r0
            r1 = r3
            goto L52
        L75:
            r0 = move-exception
            r2 = r0
            goto L40
        L78:
            r0 = move-exception
            r2 = r0
            r1 = r3
            goto L40
        L7c:
            r1 = move-exception
            goto L30
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.a.c.ax.a(java.io.File, java.lang.String):java.lang.String")
    }

    fun a(String str) {
        Matcher matcher = Pattern.compile("\\sxmlns:(\\w+)=\"http://schemas.android.com/apk/res/android\"").matcher(str)
        return matcher.find() ? matcher.group(1) : "android"
    }

    fun a(File file, String str, String str2) throws IOException {
        try {
            BufferedWriter bufferedWriter = BufferedWriter(OutputStreamWriter(FileOutputStream(file), str2))
            bufferedWriter.write(str)
            bufferedWriter.close()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    fun a_001(Intent intent, String str, String str2) {
        Bundle bundle = Bundle()
        bundle.putString(str, str2)
        intent.putExtras(bundle)
        return bundle
    }

    fun a_002(Intent intent, String str, Int i) {
        Bundle bundle = Bundle()
        bundle.putInt(str, i)
        intent.putExtras(bundle)
        return bundle
    }

    fun a_003(InputStream inputStream, OutputStream outputStream) throws IOException {
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

    fun a_004(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser()
        xmlPullParserNewPullParser.setInput(inputStream, null)
        Array<String> strArr = new String[1]
        Int eventType = xmlPullParserNewPullParser.getEventType()
        while (eventType != 2) {
            if (eventType == 3) {
                throw XmlPullParserException("Unexpected end tag at: " + xmlPullParserNewPullParser.getName())
            }
            if (eventType == 4) {
                throw XmlPullParserException("Unexpected text: " + xmlPullParserNewPullParser.getText())
            }
            eventType = xmlPullParserNewPullParser.next()
            if (eventType == 1) {
                throw XmlPullParserException("Unexpected end of document")
            }
        }
        return (HashMap) a_023(xmlPullParserNewPullParser, strArr)
    }

    fun a_005(Map map, OutputStream outputStream) throws IllegalStateException, IOException, IllegalArgumentException {
        com.gmail.heagoo.appdm.util.d dVar = new com.gmail.heagoo.appdm.util.d()
        dVar.setOutput(outputStream, "utf-8")
        dVar.startDocument(null, true)
        dVar.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true)
        a_016(map, null, dVar)
        dVar.endDocument()
    }

    fun a_008(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getString(str)
        }
        return null
    }

    fun a_010(String str, String str2, Array<Class> clsArr, Array<Object> objArr) {
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

    fun a_014(Array<Byte> bArr, Int i, Int i2) {
        StringBuilder sb = StringBuilder("")
        if (bArr == null || bArr.length <= 0) {
            return null
        }
        String hexString = Integer.toHexString(bArr[0] & 255)
        if (hexString.length() < 2) {
            sb.append(0)
        }
        sb.append(hexString)
        for (Int i3 = 1; i3 < i2 + 0; i3++) {
            sb.append(" ")
            String hexString2 = Integer.toHexString(bArr[i3] & 255)
            if (hexString2.length() < 2) {
                sb.append(0)
            }
            sb.append(hexString2)
        }
        return sb.toString()
    }

    fun a_015(Intent intent, String str, ArrayList arrayList) {
        Bundle bundle = Bundle()
        bundle.putStringArrayList(str, arrayList)
        intent.putExtras(bundle)
        return bundle
    }

    fun a_016(Map map, String str, XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        if (map == null) {
            xmlSerializer.startTag(null, "null")
            xmlSerializer.endTag(null, "null")
            return
        }
        xmlSerializer.startTag(null, "map")
        if (str != null) {
            xmlSerializer.attribute(null, "name", str)
        }
        for (Map.Entry entry : map.entrySet()) {
            a_017(entry.getValue(), (String) entry.getKey(), xmlSerializer)
        }
        xmlSerializer.endTag(null, "map")
    }

    fun a_017(Object obj, String str, XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        String str2
        if (obj == null) {
            xmlSerializer.startTag(null, "null")
            if (str != null) {
                xmlSerializer.attribute(null, "name", str)
            }
            xmlSerializer.endTag(null, "null")
            return
        }
        if (obj is String) {
            xmlSerializer.startTag(null, "string")
            if (str != null) {
                xmlSerializer.attribute(null, "name", str)
            }
            xmlSerializer.text(obj.toString())
            xmlSerializer.endTag(null, "string")
            return
        }
        if (obj is Integer) {
            str2 = "Int"
        } else if (obj is Long) {
            str2 = "Long"
        } else if (obj is Float) {
            str2 = "Float"
        } else if (obj is Double) {
            str2 = "Double"
        } else {
            if (!(obj is Boolean)) {
                if (!(obj is Array<Byte>)) {
                    if (obj is Array<Int>) {
                        a_019((Array<Int>) obj, str, xmlSerializer)
                        return
                    }
                    if (obj is Map) {
                        a_016((Map) obj, str, xmlSerializer)
                        return
                    }
                    if (obj is List) {
                        a_018((List) obj, str, xmlSerializer)
                        return
                    }
                    if (obj is Set) {
                        a_020((Set) obj, str, xmlSerializer)
                        return
                    }
                    if (!(obj is CharSequence)) {
                        throw RuntimeException("writeValueXml: unable to write value " + obj)
                    }
                    xmlSerializer.startTag(null, "string")
                    if (str != null) {
                        xmlSerializer.attribute(null, "name", str)
                    }
                    xmlSerializer.text(obj.toString())
                    xmlSerializer.endTag(null, "string")
                    return
                }
                Array<Byte> bArr = (Array<Byte>) obj
                if (bArr == null) {
                    xmlSerializer.startTag(null, "null")
                    xmlSerializer.endTag(null, "null")
                    return
                }
                xmlSerializer.startTag(null, "Byte-array")
                if (str != null) {
                    xmlSerializer.attribute(null, "name", str)
                }
                xmlSerializer.attribute(null, "num", Integer.toString(bArr.length))
                StringBuilder sb = StringBuilder(bArr.length << 1)
                for (Byte b2 : bArr) {
                    Int i = b2 >> 4
                    sb.append(i >= 10 ? (i + 97) - 10 : i + 48)
                    Int i2 = b2 & 255
                    sb.append(i2 >= 10 ? (i2 + 97) - 10 : i2 + 48)
                }
                xmlSerializer.text(sb.toString())
                xmlSerializer.endTag(null, "Byte-array")
                return
            }
            str2 = "Boolean"
        }
        xmlSerializer.startTag(null, str2)
        if (str != null) {
            xmlSerializer.attribute(null, "name", str)
        }
        xmlSerializer.attribute(null, "value", obj.toString())
        xmlSerializer.endTag(null, str2)
    }

    fun a_018(List list, String str, XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        if (list == null) {
            xmlSerializer.startTag(null, "null")
            xmlSerializer.endTag(null, "null")
            return
        }
        xmlSerializer.startTag(null, "list")
        if (str != null) {
            xmlSerializer.attribute(null, "name", str)
        }
        Int size = list.size()
        for (Int i = 0; i < size; i++) {
            a_017(list.get(i), null, xmlSerializer)
        }
        xmlSerializer.endTag(null, "list")
    }

    fun a_019(Array<Int> iArr, String str, XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        if (iArr == null) {
            xmlSerializer.startTag(null, "null")
            xmlSerializer.endTag(null, "null")
            return
        }
        xmlSerializer.startTag(null, "Int-array")
        if (str != null) {
            xmlSerializer.attribute(null, "name", str)
        }
        xmlSerializer.attribute(null, "num", Integer.toString(iArr.length))
        for (Int i : iArr) {
            xmlSerializer.startTag(null, "item")
            xmlSerializer.attribute(null, "value", Integer.toString(i))
            xmlSerializer.endTag(null, "item")
        }
        xmlSerializer.endTag(null, "Int-array")
    }

    fun a_020(Set set, String str, XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        if (set == null) {
            xmlSerializer.startTag(null, "null")
            xmlSerializer.endTag(null, "null")
            return
        }
        xmlSerializer.startTag(null, "set")
        if (str != null) {
            xmlSerializer.attribute(null, "name", str)
        }
        Iterator it = set.iterator()
        while (it.hasNext()) {
            a_017(it.next(), null, xmlSerializer)
        }
        xmlSerializer.endTag(null, "set")
    }

    private fun a_023(XmlPullParser xmlPullParser, Array<String> strArr) throws XmlPullParserException, IOException, NumberFormatException {
        Int next
        Object objValueOf = null
        String attributeValue = xmlPullParser.getAttributeValue(null, "name")
        String name = xmlPullParser.getName()
        if (!name.equals("null")) {
            if (name.equals("string")) {
                String str = ""
                while (true) {
                    Int next2 = xmlPullParser.next()
                    if (next2 == 1) {
                        throw XmlPullParserException("Unexpected end of document in <string>")
                    }
                    if (next2 == 3) {
                        if (!xmlPullParser.getName().equals("string")) {
                            throw XmlPullParserException("Unexpected end tag in <string>: " + xmlPullParser.getName())
                        }
                        strArr[0] = attributeValue
                        return str
                    }
                    if (next2 == 4) {
                        str = str + xmlPullParser.getText()
                    } else if (next2 == 2) {
                        throw XmlPullParserException("Unexpected start tag in <string>: " + xmlPullParser.getName())
                    }
                }
            } else if (name.equals("Int")) {
                objValueOf = Integer.valueOf(Integer.parseInt(xmlPullParser.getAttributeValue(null, "value")))
            } else if (name.equals("Long")) {
                objValueOf = Long.valueOf(xmlPullParser.getAttributeValue(null, "value"))
            } else if (name.equals("Float")) {
                objValueOf = Float(xmlPullParser.getAttributeValue(null, "value"))
            } else if (name.equals("Double")) {
                objValueOf = Double(xmlPullParser.getAttributeValue(null, "value"))
            } else {
                if (!name.equals("Boolean")) {
                    if (name.equals("Int-array")) {
                        xmlPullParser.next()
                        Array<Int> iArrA_024 = a_024(xmlPullParser, "Int-array")
                        strArr[0] = attributeValue
                        return iArrA_024
                    }
                    if (name.equals("map")) {
                        xmlPullParser.next()
                        HashMap mapA_025 = a_025(xmlPullParser, "map", strArr)
                        strArr[0] = attributeValue
                        return mapA_025
                    }
                    if (name.equals("list")) {
                        xmlPullParser.next()
                        ArrayList arrayListB_026 = b_026(xmlPullParser, "list", strArr)
                        strArr[0] = attributeValue
                        return arrayListB_026
                    }
                    if (!name.equals("set")) {
                        throw XmlPullParserException("Unknown tag: " + name)
                    }
                    xmlPullParser.next()
                    HashSet hashSetC_027 = c_027(xmlPullParser, "set", strArr)
                    strArr[0] = attributeValue
                    return hashSetC_027
                }
                objValueOf = Boolean.valueOf(xmlPullParser.getAttributeValue(null, "value"))
            }
        }
        do {
            next = xmlPullParser.next()
            if (next == 1) {
                throw XmlPullParserException("Unexpected end of document in <" + name + ">")
            }
            if (next == 3) {
                if (!xmlPullParser.getName().equals(name)) {
                    throw XmlPullParserException("Unexpected end tag in <" + name + ">: " + xmlPullParser.getName())
                }
                strArr[0] = attributeValue
                return objValueOf
            }
            if (next == 4) {
                throw XmlPullParserException("Unexpected text in <" + name + ">: " + xmlPullParser.getName())
            }
        } while (next != 2);
        throw XmlPullParserException("Unexpected start tag in <" + name + ">: " + xmlPullParser.getName())
    }

    public static Array<Int> a_024(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        try {
            Array<Int> iArr = new Int[Integer.parseInt(xmlPullParser.getAttributeValue(null, "num"))]
            Int i = 0
            Int eventType = xmlPullParser.getEventType()
            do {
                if (eventType == 2) {
                    if (!xmlPullParser.getName().equals("item")) {
                        throw XmlPullParserException("Expected item tag at: " + xmlPullParser.getName())
                    }
                    try {
                        iArr[i] = Integer.parseInt(xmlPullParser.getAttributeValue(null, "value"))
                    } catch (NullPointerException e) {
                        throw XmlPullParserException("Need value attribute in item")
                    } catch (NumberFormatException e2) {
                        throw XmlPullParserException("Not a number in value attribute in item")
                    }
                } else if (eventType == 3) {
                    if (xmlPullParser.getName().equals(str)) {
                        return iArr
                    }
                    if (!xmlPullParser.getName().equals("item")) {
                        throw XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName())
                    }
                    i++
                }
                eventType = xmlPullParser.next()
            } while (eventType != 1);
            throw XmlPullParserException("Document ended before " + str + " end tag")
        } catch (NullPointerException e3) {
            throw XmlPullParserException("Need num attribute in Byte-array")
        } catch (NumberFormatException e4) {
            throw XmlPullParserException("Not a number in num attribute in Byte-array")
        }
    }

    fun a_025(XmlPullParser xmlPullParser, String str, Array<String> strArr) throws XmlPullParserException, IOException, NumberFormatException {
        HashMap map = HashMap()
        Int eventType = xmlPullParser.getEventType()
        do {
            if (eventType == 2) {
                Object objA_023 = a_023(xmlPullParser, strArr)
                if (strArr[0] == null) {
                    throw XmlPullParserException("Map value without name attribute: " + xmlPullParser.getName())
                }
                map.put(strArr[0], objA_023)
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return map
                }
                throw XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName())
            }
            eventType = xmlPullParser.next()
        } while (eventType != 1);
        throw XmlPullParserException("Document ended before " + str + " end tag")
    }

    fun b(File file, String str) throws IOException {
        a(file, str, "UTF-8")
    }

    fun b_007(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getBoolean(str, false)
        }
        return false
    }

    fun b_011(Context context, String str) {
        Intent intent
        if (b_022(str, context.getResources().getStringArray(R.array.fileTypeImage))) {
            File file = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file), "image/*")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeWebText))) {
            File file2 = File(str)
            Uri uriBuild = Uri.parse(file2.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme(gzd.COLUMN_CONTENT).encodedPath(file2.toString()).build()
            intent = Intent("android.intent.action.VIEW")
            intent.setDataAndType(uriBuild, "text/html")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypePackage))) {
            File file3 = File(str)
            intent = Intent()
            intent.setAction("android.intent.action.VIEW")
            intent.setDataAndType(Uri.fromFile(file3), "application/vnd.android.package-archive")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeAudio))) {
            File file4 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.putExtra("oneshot", 0)
            intent.putExtra("configchange", 0)
            intent.setDataAndType(Uri.fromFile(file4), "audio/*")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeVideo))) {
            File file5 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.putExtra("oneshot", 0)
            intent.putExtra("configchange", 0)
            intent.setDataAndType(Uri.fromFile(file5), "video/*")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeText))) {
            File file6 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file6), "text/plain")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypePdf))) {
            File file7 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file7), "application/pdf")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeWord))) {
            File file8 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file8), "application/msword")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypeExcel))) {
            File file9 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file9), "application/vnd.ms-excel")
        } else if (b_022(str, context.getResources().getStringArray(R.array.fileTypePPT))) {
            File file10 = File(str)
            intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.setDataAndType(Uri.fromFile(file10), "application/vnd.ms-powerpoint")
        } else {
            intent = Intent("android.intent.action.VIEW")
            intent.setDataAndType(Uri.fromFile(File(str)), "*/*")
        }
        context.startActivity(intent)
    }

    fun b_012(Intent intent, String str, Boolean z) {
        Bundle bundle = Bundle()
        bundle.putBoolean(str, z)
        intent.putExtras(bundle)
        return bundle
    }

    private fun b_022(String str, Array<String> strArr) {
        for (String str2 : strArr) {
            if (str.endsWith(str2)) {
                return true
            }
        }
        return false
    }

    fun b_026(XmlPullParser xmlPullParser, String str, Array<String> strArr) throws XmlPullParserException, IOException {
        ArrayList arrayList = ArrayList()
        Int eventType = xmlPullParser.getEventType()
        do {
            if (eventType == 2) {
                arrayList.add(a_023(xmlPullParser, strArr))
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return arrayList
                }
                throw XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName())
            }
            eventType = xmlPullParser.next()
        } while (eventType != 1);
        throw XmlPullParserException("Document ended before " + str + " end tag")
    }

    fun c_006(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getInt(str, 0)
        }
        return 0
    }

    fun c_027(XmlPullParser xmlPullParser, String str, Array<String> strArr) throws XmlPullParserException, IOException {
        HashSet hashSet = HashSet()
        Int eventType = xmlPullParser.getEventType()
        do {
            if (eventType == 2) {
                hashSet.add(a_023(xmlPullParser, strArr))
            } else if (eventType == 3) {
                if (xmlPullParser.getName().equals(str)) {
                    return hashSet
                }
                throw XmlPullParserException("Expected " + str + " end tag at: " + xmlPullParser.getName())
            }
            eventType = xmlPullParser.next()
        } while (eventType != 1);
        throw XmlPullParserException("Document ended before " + str + " end tag")
    }

    fun d_013(Intent intent, String str) {
        Bundle extras = intent.getExtras()
        if (extras != null) {
            return extras.getStringArrayList(str)
        }
        return null
    }

    fun e(File file) {
        return a(file, "UTF-8")
    }

    fun i_009(String str) throws IOException {
        try {
            ZipFile zipFile = ZipFile(str)
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement()
                if (!zipEntryNextElement.isDirectory()) {
                    String name = zipEntryNextElement.getName()
                    if (name.endsWith(".RSA") || name.endsWith(".rsa") || name.endsWith(".DSA") || name.endsWith(".dsa")) {
                        InputStream inputStream = zipFile.getInputStream(zipEntryNextElement)
                        try {
                            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(inputStream)).getSubjectDN().toString().replace("C=", "Country=").replaceAll("O=", "Organization=").replaceAll("OU=", "Organization Unit=").replaceAll("ST=", "State/Province=").replaceAll("CN=", "Common Name=").replaceAll("L=", "Locality=").replaceAll("SN=", "Surname=").replaceAll("GN=", "Given Name=")
                        } catch (Exception e) {
                        } finally {
                            inputStream.close()
                        }
                    }
                }
            }
            zipFile.close()
        } catch (Exception e2) {
            e2.printStackTrace()
        }
        return null
    }

    fun millisToDHMS(Long j) {
        Long days = TimeUnit.MILLISECONDS.toDays(j)
        Long hours = TimeUnit.MILLISECONDS.toHours(j) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(j))
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(j) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j))
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j))
        Long millis = TimeUnit.MILLISECONDS.toMillis(j) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(j))
        return days == 0 ? String.format("%02dh:%02dm:%02ds.%03dms", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(millis)) : String.format("%dd %02dh:%02dm:%02ds.%03dms", Long.valueOf(days), Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(millis))
    }
}
