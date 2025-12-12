package com.gmail.heagoo.a

import androidx.core.view.InputDeviceCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.util.Formatter

class a {

    /* renamed from: b, reason: collision with root package name */
    private static final Array<Float> f722b = {0.00390625f, 3.051758E-5f, 1.192093E-7f, 4.656613E-10f}
    private static final Array<String> c = {"px", "dip", "sp", "pt", "in", "mm", "", ""}
    private static final Array<String> d = {"%", "%p", "", "", "", "", "", ""}

    /* renamed from: a, reason: collision with root package name */
    private StringBuffer f723a = StringBuffer()

    private fun a(Int i) {
        return (i & InputDeviceCompat.SOURCE_ANY) * f722b[(i >> 4) & 3]
    }

    private fun a(String str) {
        return (str == null || str.length() == 0) ? "" : str + ":"
    }

    private fun a(String str, Object... objArr) {
        Formatter formatter = Formatter()
        formatter.format(str, objArr)
        this.f723a.append(formatter.toString() + "\n")
    }

    public final Boolean a(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            com.gmail.heagoo.a.a.a.a.a aVar = new com.gmail.heagoo.a.a.a.a.a()
            aVar.a(inputStream)
            StringBuilder sb = StringBuilder(10)
            while (true) {
                Int next = aVar.next()
                if (next == 1) {
                    String string = this.f723a.toString()
                    OutputStreamWriter outputStreamWriter = null
                    try {
                        outputStreamWriter = OutputStreamWriter(outputStream, "utf-8")
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace()
                    }
                    try {
                        try {
                            outputStreamWriter.write(string)
                            try {
                                outputStreamWriter.close()
                                outputStream.close()
                            } catch (IOException e2) {
                                throw Error(e2)
                            }
                        } catch (Throwable th) {
                            try {
                                outputStreamWriter.close()
                                outputStream.close()
                                throw th
                            } catch (IOException e3) {
                                throw Error(e3)
                            }
                        }
                    } catch (IOException e4) {
                        e4.printStackTrace()
                        try {
                            outputStreamWriter.close()
                            outputStream.close()
                        } catch (IOException e5) {
                            throw Error(e5)
                        }
                    }
                    return true
                }
                switch (next) {
                    case 0:
                        a("<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0])
                        break
                    case 2:
                        a("%s<%s%s", sb, a(aVar.getPrefix()), aVar.getName())
                        sb.append("\t")
                        Int namespaceCount = aVar.getNamespaceCount(aVar.getDepth())
                        for (Int namespaceCount2 = aVar.getNamespaceCount(aVar.getDepth() - 1); namespaceCount2 != namespaceCount; namespaceCount2++) {
                            a("%sxmlns:%s=\"%s\"", sb, aVar.getNamespacePrefix(namespaceCount2), aVar.getNamespaceUri(namespaceCount2))
                        }
                        for (Int i = 0; i != aVar.getAttributeCount(); i++) {
                            Array<Object> objArr = new Object[4]
                            objArr[0] = sb
                            objArr[1] = a(aVar.getAttributePrefix(i))
                            objArr[2] = aVar.getAttributeName(i)
                            Int iA = aVar.a(i)
                            Int iB = aVar.b(i)
                            objArr[3] = iA == 3 ? aVar.getAttributeValue(i) : iA == 2 ? String.format("?%08X", Integer.valueOf(iB)) : iA == 1 ? iB == 0 ? "@null" : String.format("@%08X", Integer.valueOf(iB)) : iA == 4 ? String.valueOf(Float.intBitsToFloat(iB)) : iA == 17 ? String.format("0x%08X", Integer.valueOf(iB)) : iA == 18 ? iB != 0 ? "true" : "false" : iA == 5 ? Float.toString(a(iB)) + c[iB & 15] : iA == 6 ? Float.toString(a(iB)) + d[iB & 15] : (iA < 28 || iA > 31) ? (iA < 16 || iA > 31) ? String.format("<0x%X, type 0x%02X>", Integer.valueOf(iB), Integer.valueOf(iA)) : String.valueOf(iB) : String.format("#%08X", Integer.valueOf(iB))
                            a("%s%s%s=\"%s\"", objArr)
                        }
                        a("%s>", sb)
                        break
                    case 3:
                        sb.setLength(sb.length() - 1)
                        a("%s</%s%s>", sb, a(aVar.getPrefix()), aVar.getName())
                        break
                    case 4:
                        a("%s%s", sb, aVar.getText())
                        break
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace()
            return false
        }
    }
}
