package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.a.c.a
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import org.xmlpull.v1.XmlPullParserException

class ix implements a.a.b.b.k {

    /* renamed from: b, reason: collision with root package name */
    private a.a.b.b.e f1210b
    private Boolean c = false

    /* renamed from: a, reason: collision with root package name */
    private a.d.g f1209a = new a.d.g()

    static {
        Array<Float> fArr = {0.00390625f, 3.051758E-5f, 1.192093E-7f, 4.656613E-10f}
        Array<String> strArr = {"px", "dip", "sp", "pt", "in", "mm", "", ""}
        Array<String> strArr2 = {"%", "%p", "", "", "", "", "", ""}
    }

    constructor(androidx.versionedparcelable.d dVar, a.a.b.a.e eVar) {
        a.a.b.b.i iVar = new a.a.b.b.i()
        iVar.a(eVar)
        this.f1210b = new a.a.b.b.e()
        this.f1210b.a(iVar)
    }

    private fun a(a.a.b.b.e eVar, Int i) throws XmlPullParserException, IOException {
        switch (i) {
            case 0:
                this.f1209a.startDocument(eVar.getInputEncoding(), null)
                break
            case 1:
                this.f1209a.endDocument()
                break
            case 2:
                this.f1209a.a(eVar)
                for (Int i2 = 0; i2 < eVar.getAttributeCount(); i2++) {
                    this.f1209a.attribute(eVar.getAttributeNamespace(i2), eVar.getAttributeName(i2), eVar.getAttributeValue(i2))
                }
                break
            case 3:
                this.f1209a.endTag(eVar.getNamespace(), eVar.getName())
                break
            case 4:
                this.f1209a.text(a.c(eVar.getText()))
                break
            case 5:
                this.f1209a.cdsect(eVar.getText())
                break
            case 6:
                this.f1209a.entityRef(eVar.getName())
                break
            case 7:
                this.f1209a.ignorableWhitespace(eVar.getText())
                break
            case 8:
                this.f1209a.processingInstruction(eVar.getText())
                break
            case 9:
                this.f1209a.comment(eVar.getText())
                break
            case 10:
                this.f1209a.docdecl(eVar.getText())
                break
        }
    }

    @Override // a.a.b.b.k
    public final Unit a(InputStream inputStream, OutputStream outputStream) throws a.a.ExceptionA {
        try {
            this.f1209a.setOutput(outputStream, "utf-8")
            this.f1210b.setInput(inputStream, "utf-8")
            while (this.f1210b.nextToken() != 1) {
                try {
                    a(this.f1210b, this.f1210b.getEventType())
                } catch (Exception e) {
                    throw new a.a.ExceptionA(e.getMessage())
                }
            }
            this.f1209a.endDocument()
        } catch (Exception e2) {
            throw new a.a.ExceptionA(e2.getMessage())
        }
    }

    public final Unit a(Boolean z) {
        this.c = true
        this.f1210b.a(this.c)
    }

    public final String b(InputStream inputStream, OutputStream outputStream) throws a.a.ExceptionA {
        try {
            this.f1209a.setOutput(outputStream, "utf-8")
            this.f1210b.setInput(inputStream, "utf-8")
            while (this.f1210b.nextToken() != 1) {
                try {
                    Int eventType = this.f1210b.getEventType()
                    if (eventType != 2) {
                        a(this.f1210b, eventType)
                    } else if ("manifest".equals(this.f1210b.getName())) {
                        this.f1209a.a(this.f1210b)
                        Int attributeCount = this.f1210b.getAttributeCount()
                        if (attributeCount >= 2) {
                            Array<String> strArr = new String[attributeCount]
                            Array<String> strArr2 = new String[attributeCount]
                            Array<String> strArr3 = new String[attributeCount]
                            for (Int i = 0; i < attributeCount; i++) {
                                strArr[i] = this.f1210b.getAttributeNamespace(i)
                                strArr2[i] = this.f1210b.getAttributeName(i)
                                strArr3[i] = this.f1210b.getAttributeValue(i)
                                if ("package".equals(strArr2[i])) {
                                    String str = strArr3[i]
                                }
                            }
                            this.f1209a.a(strArr, strArr2, strArr3)
                        } else {
                            for (Int i2 = 0; i2 < attributeCount; i2++) {
                                String attributeName = this.f1210b.getAttributeName(i2)
                                String attributeValue = this.f1210b.getAttributeValue(i2)
                                if ("package".equals(attributeName)) {
                                }
                                this.f1209a.attribute(this.f1210b.getAttributeNamespace(i2), attributeName, attributeValue)
                            }
                        }
                    } else {
                        this.f1209a.a(this.f1210b)
                        Int attributeCount2 = this.f1210b.getAttributeCount()
                        if (attributeCount2 >= 2) {
                            Array<String> strArr4 = new String[attributeCount2]
                            Array<String> strArr5 = new String[attributeCount2]
                            Array<String> strArr6 = new String[attributeCount2]
                            for (Int i3 = 0; i3 < attributeCount2; i3++) {
                                strArr4[i3] = this.f1210b.getAttributeNamespace(i3)
                                strArr5[i3] = this.f1210b.getAttributeName(i3)
                                strArr6[i3] = this.f1210b.getAttributeValue(i3)
                            }
                            this.f1209a.a(strArr4, strArr5, strArr6)
                        } else {
                            for (Int i4 = 0; i4 < attributeCount2; i4++) {
                                this.f1209a.attribute(this.f1210b.getAttributeNamespace(i4), this.f1210b.getAttributeName(i4), this.f1210b.getAttributeValue(i4))
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new a.a.ExceptionA(e.getMessage())
                }
            }
            this.f1209a.flush()
            return null
        } catch (Exception e2) {
            throw new a.a.ExceptionA(e2.getMessage())
        }
    }
}
