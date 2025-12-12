package com.gmail.heagoo.a.c

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.xml.sax.SAXException

class ResXmlPatcher {
    fun a(File file, String str) throws XPathExpressionException {
        if (str == null || !str.contains("@")) {
            return (String) null
        }
        File file2 = File(file, "/res/values/strings.xml")
        String strReplace = str.replace("@string/", "")
        if (file2.exists()) {
            try {
                Object objEvaluate = XPathFactory.newInstance().newXPath().compile(StringBuffer().append(StringBuffer().append(StringBuffer().append("/resources/string[@name=").append('\"').toString()).append(strReplace).toString()).append("\"]/text()").toString()).evaluate(b(file2), XPathConstants.STRING)
                if (objEvaluate != null) {
                    return (String) objEvaluate
                }
            } catch (IOException e) {
            } catch (ParserConfigurationException e2) {
            } catch (XPathExpressionException e3) {
            } catch (SAXException e4) {
            }
        }
        return (String) null
    }

    fun a(File file) throws XPathExpressionException, IOException {
        String strA
        Boolean z = false
        String strE = ax.e(file)
        String strA2 = ax.a(strE)
        StringBuffer stringBuffer = StringBuffer()
        Matcher matcher = Pattern.compile(StringBuffer().append(StringBuffer().append("<provider([^>]+?)").append(strA2).toString()).append(":authorities=\"@string/([^\"]+)\"").toString()).matcher(strE)
        while (matcher.find()) {
            String strA3 = a(file.getParentFile(), StringBuffer().append("@string/").append(matcher.group(2)).toString())
            if (strA3 != null) {
                if (!z) {
                    z = true
                }
                matcher.appendReplacement(stringBuffer, StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append("<provider").append(matcher.group(1)).toString()).append("").toString()).append(strA2).toString()).append(":authorities=\"").toString()).append(strA3).toString()).append("\"").toString())
            }
        }
        matcher.appendTail(stringBuffer)
        StringBuffer stringBuffer2 = StringBuffer()
        Matcher matcher2 = Pattern.compile(StringBuffer().append(StringBuffer().append("<data([^>]+?)").append(strA2).toString()).append(":scheme=\"@string/([^\"]+)\"").toString()).matcher(stringBuffer.toString())
        if (matcher2.find() && (strA = a(file.getParentFile(), StringBuffer().append("@string/").append(matcher2.group(2)).toString())) != null) {
            if (!z) {
                z = true
            }
            matcher2.appendReplacement(stringBuffer2, StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append("<data").append(matcher2.group(1)).toString()).append("").toString()).append(strA2).toString()).append(":scheme=\"").toString()).append(strA).toString()).append("\"").toString())
        }
        matcher2.appendTail(stringBuffer2)
        if (z) {
            ax.b(file, stringBuffer2.toString())
        }
    }

    private fun b(File file) throws ParserConfigurationException, IOException {
        DocumentBuilder documentBuilderNewDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        FileInputStream fileInputStream = FileInputStream(file)
        try {
            return documentBuilderNewDocumentBuilder.parse(fileInputStream)
        } finally {
            fileInputStream.close()
        }
    }
}
