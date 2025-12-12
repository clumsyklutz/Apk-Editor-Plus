package jadx.core.xmlgen

import com.gmail.heagoo.apkeditor.gzd
import jadx.core.codegen.CodeWriter
import jadx.core.utils.StringUtils
import jadx.core.xmlgen.entry.RawNamedValue
import jadx.core.xmlgen.entry.ResourceEntry
import jadx.core.xmlgen.entry.ValuesParser
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import org.d.b
import org.d.c

class ResXmlGen {
    private static val LOG = c.a(ResXmlGen.class)
    private static val SKIP_RES_TYPES = HashSet(Arrays.asList("layout", "mipmap", gzd.COLUMN_ID))
    private final ResourceStorage resStorage
    private final ValuesParser vp

    constructor(ResourceStorage resourceStorage, ValuesParser valuesParser) {
        this.resStorage = resourceStorage
        this.vp = valuesParser
    }

    private fun addItem(CodeWriter codeWriter, RawNamedValue rawNamedValue) {
        String str
        String str2 = null
        Int nameRef = rawNamedValue.getNameRef()
        if (ParserConstants.isResInternalId(nameRef)) {
            String str3 = (String) ParserConstants.PLURALS_MAP.get(Integer.valueOf(nameRef))
            if (str3 != null) {
                str = "quantity"
                str2 = str3
            } else {
                str = null
                str2 = str3
            }
        } else {
            str = null
        }
        addSimpleValue(codeWriter, "item", str, str2, this.vp.decodeValue(rawNamedValue.getRawValue()))
    }

    private fun addSimpleValue(CodeWriter codeWriter, String str, String str2, String str3, String str4) {
        codeWriter.startLine()
        codeWriter.add('<').add(str)
        if (str2 != null && str3 != null) {
            codeWriter.add(' ').add(str2).add("=\"").add(str3).add('\"')
        }
        codeWriter.add('>')
        codeWriter.add(StringUtils.escapeResValue(str4))
        codeWriter.add("</").add(str).add('>')
    }

    private fun addValue(CodeWriter codeWriter, ResourceEntry resourceEntry) {
        if (resourceEntry.getSimpleValue() != null) {
            addSimpleValue(codeWriter, resourceEntry.getTypeName(), "name", resourceEntry.getKeyName(), this.vp.decodeValue(resourceEntry.getSimpleValue()))
            return
        }
        codeWriter.startLine()
        codeWriter.add('<').add(resourceEntry.getTypeName()).add(' ')
        codeWriter.add("name=\"").add(resourceEntry.getKeyName()).add("\">")
        codeWriter.incIndent()
        Iterator it = resourceEntry.getNamedValues().iterator()
        while (it.hasNext()) {
            addItem(codeWriter, (RawNamedValue) it.next())
        }
        codeWriter.decIndent()
        codeWriter.startLine().add("</").add(resourceEntry.getTypeName()).add('>')
    }

    private fun getFileName(ResourceEntry resourceEntry) {
        StringBuilder sb = StringBuilder()
        String locale = resourceEntry.getConfig().getLocale()
        sb.append("res/values")
        if (!locale.isEmpty()) {
            sb.append('-').append(locale)
        }
        sb.append('/')
        sb.append(resourceEntry.getTypeName())
        if (!resourceEntry.getTypeName().endsWith("s")) {
            sb.append('s')
        }
        sb.append(".xml")
        return sb.toString()
    }

    fun makeResourcesXml() {
        HashMap map = HashMap()
        for (ResourceEntry resourceEntry : this.resStorage.getResources()) {
            if (!SKIP_RES_TYPES.contains(resourceEntry.getTypeName())) {
                String fileName = getFileName(resourceEntry)
                CodeWriter codeWriter = (CodeWriter) map.get(fileName)
                if (codeWriter == null) {
                    codeWriter = CodeWriter()
                    codeWriter.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                    codeWriter.startLine("<resources>")
                    codeWriter.incIndent()
                    map.put(fileName, codeWriter)
                }
                addValue(codeWriter, resourceEntry)
            }
        }
        ArrayList arrayList = ArrayList(map.size())
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey()
            CodeWriter codeWriter2 = (CodeWriter) entry.getValue()
            codeWriter2.decIndent()
            codeWriter2.startLine("</resources>")
            codeWriter2.finish()
            arrayList.add(ResContainer.singleFile(str, codeWriter2))
        }
        Collections.sort(arrayList)
        return arrayList
    }
}
