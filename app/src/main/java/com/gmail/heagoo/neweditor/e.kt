package com.gmail.heagoo.neweditor

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.style.CharacterStyle
import android.widget.EditText
import java.io.BufferedInputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Serializable
import java.util.ArrayList
import java.util.Vector
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.XMLReader

class e implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private static Int f1512a = 64

    /* renamed from: b, reason: collision with root package name */
    private transient ArrayList f1513b
    private Boolean d
    private Int e
    private Int f
    private File g
    private transient af m
    private c n
    private Boolean c = false
    private Vector h = Vector()
    private Int i = 0
    private Int j = 0
    private String k = null
    private Vector l = Vector()

    constructor(Context context, File file, String str) throws SAXException {
        String str2
        this.g = file
        this.n = c(context)
        if (str == null) {
            String name = file.getName()
            Int iLastIndexOf = name.lastIndexOf(46)
            if (iLastIndexOf != -1) {
                String strSubstring = name.substring(iLastIndexOf + 1)
                str2 = (strSubstring.equals("htm") ? "html" : strSubstring) + ".xml"
            } else {
                str2 = "txt.xml"
            }
        } else {
            str2 = str
        }
        ah ahVar = ah()
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader()
            try {
                InputSource inputSource = InputSource(BufferedInputStream(context.getAssets().open("syntax/" + str2)))
                xMLReader.setContentHandler(ahVar)
                xMLReader.setDTDHandler(ahVar)
                xMLReader.setEntityResolver(ahVar)
                xMLReader.setErrorHandler(ahVar)
                xMLReader.parse(inputSource)
                this.m = ahVar.a()
            } catch (Exception e) {
                e.printStackTrace()
            } catch (Throwable th) {
            }
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace()
        } catch (SAXException e3) {
            e3.printStackTrace()
        }
    }

    private fun a(Int i) {
        if (this.f1513b == null) {
            this.f1513b = ArrayList()
        }
        if (i >= this.f1513b.size()) {
            return null
        }
        return (ag) this.f1513b.get(i)
    }

    private fun a(Int i, ag agVar) {
        if (i >= 0) {
            if (this.f1513b == null) {
                this.f1513b = ArrayList()
            }
            while (i >= this.f1513b.size()) {
                this.f1513b.add(null)
            }
            this.f1513b.set(i, agVar)
        }
    }

    private fun a(Spannable spannable, Int i, Int i2) {
        for (Object obj : spannable.getSpans(i, i2, ac.class)) {
            spannable.removeSpan(obj)
        }
    }

    private fun a(Spannable spannable, CharacterStyle characterStyle, Int i, Int i2, Int i3) {
        spannable.setSpan(characterStyle, i, i2, 33)
    }

    private fun c() {
        return this.g != null ? this.g.getName() : "untitled"
    }

    public final String a() {
        return this.k
    }

    public final Unit a(Context context) throws IOException {
        a(this.g.getAbsolutePath())
    }

    public final Unit a(Context context, String str, Int i) throws IOException {
        Int i2
        File file = File(str)
        FileInputStream fileInputStream = FileInputStream(file)
        Long length = file.length()
        if (length > 4194304) {
            throw IOException(context.getString(i))
        }
        Array<Byte> bArr = new Byte[(Int) length]
        Int i3 = 0
        while (i3 < bArr.length && (i2 = fileInputStream.read(bArr, i3, bArr.length - i3)) >= 0) {
            i3 += i2
        }
        this.k = String(bArr, "UTF-8")
        this.c = false
        fileInputStream.close()
    }

    public final Unit a(EditText editText) {
        ag agVarA
        try {
            if (this.m == null) {
                this.d = false
                return
            }
            Editable text = editText.getText()
            Array<String> strArrSplit = text.toString().split("\\n")
            z zVar = z()
            ag agVar = null
            Int i = 0
            Int i2 = 0
            while (i < strArrSplit.length) {
                a(text, i2, strArrSplit[i].length() + i2)
                try {
                    d dVar = d()
                    zVar.f1537a = strArrSplit[i].toCharArray()
                    zVar.c = 0
                    zVar.f1538b = strArrSplit[i].length()
                    agVarA = this.m.a(agVar, dVar, zVar)
                    try {
                        for (Token tokenA = com.gmail.heagoo.a.c.a.a(dVar.a()); tokenA != null; tokenA = tokenA.next) {
                            if (tokenA.length > 0 && tokenA.id != 0) {
                                a(text, ac(this.n.a(tokenA)), tokenA.offset + i2, tokenA.length + tokenA.offset + i2, 33)
                            }
                        }
                    } catch (Exception e) {
                        e = e
                        e.printStackTrace()
                        a(i, agVar)
                        Int length = i2 + strArrSplit[i].length() + 1
                        i++
                        i2 = length
                        agVar = agVarA
                    }
                } catch (Exception e2) {
                    e = e2
                    agVarA = agVar
                }
                a(i, agVar)
                Int length2 = i2 + strArrSplit[i].length() + 1
                i++
                i2 = length2
                agVar = agVarA
            }
        } catch (Error e3) {
            e3.printStackTrace()
        }
    }

    public final Unit a(EditText editText, Int i, Int i2, Int i3, Int i4, Boolean z) {
        ag agVarA
        ag agVarA2
        d dVar
        try {
            if (this.m == null) {
                this.d = false
                return
            }
            Editable text = editText.getText()
            Array<String> strArrSplit = text.toString().split("\\n")
            z zVar = z()
            ag agVar = null
            Int i5 = 0
            Int i6 = 0
            while (i5 < strArrSplit.length) {
                if (i5 >= this.f && i5 <= this.e && (i5 < i3 || i5 > i4)) {
                    a(editText.getText(), i6, strArrSplit[i5].length() + i6)
                }
                if ((!z || i > strArrSplit[i5].length() + i6 || i2 < i6) && ((z || i5 < i3 || i5 > i4) && (a(i5) == agVar || i5 < i3 || i5 > i4))) {
                    agVarA = a(i5 + 1)
                } else {
                    a(text, i6, strArrSplit[i5].length() + i6)
                    try {
                        dVar = d()
                        zVar.f1537a = strArrSplit[i5].toCharArray()
                        zVar.c = 0
                        zVar.f1538b = strArrSplit[i5].length()
                        agVarA2 = this.m.a(agVar, dVar, zVar)
                    } catch (Exception e) {
                        e = e
                        agVarA2 = agVar
                    }
                    try {
                        for (Token tokenA = com.gmail.heagoo.a.c.a.a(dVar.a()); tokenA != null; tokenA = tokenA.next) {
                            if (i5 >= i3 && i5 <= i4 && tokenA.length > 0 && tokenA.id != 0) {
                                a(text, ac(this.n.a(tokenA)), tokenA.offset + i6, tokenA.length + tokenA.offset + i6, 33)
                            }
                        }
                        agVarA = agVarA2
                    } catch (Exception e2) {
                        e = e2
                        e.printStackTrace()
                        agVarA = agVarA2
                        a(i5, agVar)
                        Int length = i6 + strArrSplit[i5].length() + 1
                        i5++
                        i6 = length
                        agVar = agVarA
                    }
                    a(i5, agVar)
                }
                Int length2 = i6 + strArrSplit[i5].length() + 1
                i5++
                i6 = length2
                agVar = agVarA
            }
            this.f = i3
            this.e = i4
        } catch (Error e3) {
            e3.printStackTrace()
        }
    }

    public final Unit a(CharSequence charSequence, Int i, Int i2, Int i3) {
        try {
            String str = this.k
            this.k = charSequence.toString()
            a aVar = a(i, str.substring(i, i + i2), this.k.substring(i, i + i3))
            if (this.l.size() <= 0 || !((a) this.l.lastElement()).a(aVar)) {
                this.l.addElement(aVar)
            } else {
                ((a) this.l.lastElement()).b(aVar)
            }
            while (f1512a != 0 && this.l.size() > f1512a) {
                this.l.remove(0)
            }
            this.h.clear()
            this.c = true
        } catch (Exception e) {
        }
    }

    public final Unit a(String str) throws IOException {
        FileOutputStream fileOutputStream = FileOutputStream(File(str))
        OutputStreamWriter outputStreamWriter = OutputStreamWriter(fileOutputStream, "UTF-8")
        BufferedWriter bufferedWriter = BufferedWriter(outputStreamWriter)
        bufferedWriter.write(this.k)
        bufferedWriter.flush()
        outputStreamWriter.close()
        fileOutputStream.close()
    }

    public final Unit a(Boolean z) {
        this.c = z
    }

    public final Boolean b() {
        return this.c
    }

    public final String toString() {
        return this.c ? c() + " *" : c()
    }
}
