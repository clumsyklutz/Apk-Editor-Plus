package jadx.core.xmlgen

import jadx.core.utils.exceptions.JadxException
import java.io.IOException
import java.io.InputStream
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map
import javax.xml.parsers.DocumentBuilderFactory
import org.d.b
import org.d.c
import org.w3c.dom.DOMException
import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.w3c.dom.NodeList

class ManifestAttributes {
    private static val ATTR_XML = "/android/attrs.xml"
    private static val LOG = c.a(ManifestAttributes.class)
    private static val MANIFEST_ATTR_XML = "/android/attrs_manifest.xml"
    private val attrMap = HashMap()

    class MAttr {
        private final MAttrType type
        private val values = LinkedHashMap()

        constructor(MAttrType mAttrType) {
            this.type = mAttrType
        }

        fun getType() {
            return this.type
        }

        fun getValues() {
            return this.values
        }

        fun toString() {
            return "[" + this.type + ", " + this.values + "]"
        }
    }

    enum MAttrType {
        ENUM,
        FLAG
    }

    private fun loadXML(String str) throws IOException {
        InputStream inputStream = null
        try {
            InputStream resourceAsStream = ManifestAttributes.class.getResourceAsStream(str)
            if (resourceAsStream == null) {
                throw JadxException(str + " not found in classpath")
            }
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resourceAsStream)
            if (resourceAsStream != null) {
                resourceAsStream.close()
            }
            return document
        } catch (Throwable th) {
            if (0 != 0) {
                inputStream.close()
            }
            throw th
        }
    }

    private fun parse(Document document) throws DOMException {
        NodeList childNodes = document.getChildNodes()
        for (Int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i)
            if (nodeItem.getNodeType() == 1 && nodeItem.hasChildNodes()) {
                parseAttrList(nodeItem.getChildNodes())
            }
        }
    }

    private fun parseAttrList(NodeList nodeList) throws DOMException {
        String nodeValue
        for (Int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i)
            if (nodeItem.getNodeType() == 1 && nodeItem.hasAttributes() && nodeItem.hasChildNodes()) {
                NamedNodeMap attributes = nodeItem.getAttributes()
                Int i2 = 0
                while (true) {
                    if (i2 >= attributes.getLength()) {
                        nodeValue = null
                        break
                    }
                    Node nodeItem2 = attributes.item(i2)
                    if (nodeItem2.getNodeName().equals("name")) {
                        nodeValue = nodeItem2.getNodeValue()
                        break
                    }
                    i2++
                }
                if (nodeValue == null || !nodeItem.getNodeName().equals("attr")) {
                    parseAttrList(nodeItem.getChildNodes())
                } else {
                    parseValues(nodeValue, nodeItem.getChildNodes())
                }
            }
        }
    }

    private fun parseValues(String str, NodeList nodeList) throws DOMException {
        Node namedItem
        MAttr mAttr = null
        for (Int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i)
            if (nodeItem.getNodeType() == 1 && nodeItem.hasAttributes()) {
                if (mAttr == null) {
                    if (nodeItem.getNodeName().equals("enum")) {
                        mAttr = MAttr(MAttrType.ENUM)
                    } else if (nodeItem.getNodeName().equals("flag")) {
                        mAttr = MAttr(MAttrType.FLAG)
                    }
                    if (mAttr == null) {
                        return
                    } else {
                        this.attrMap.put(str, mAttr)
                    }
                }
                NamedNodeMap attributes = nodeItem.getAttributes()
                Node namedItem2 = attributes.getNamedItem("name")
                if (namedItem2 != null && (namedItem = attributes.getNamedItem("value")) != null) {
                    try {
                        String nodeValue = namedItem.getNodeValue()
                        mAttr.getValues().put(Long.valueOf(nodeValue.startsWith("0x") ? Long.parseLong(nodeValue.substring(2), 16) : Long.parseLong(nodeValue)), namedItem2.getNodeValue())
                    } catch (NumberFormatException e) {
                        LOG.a("Failed parse manifest number", (Throwable) e)
                    }
                }
            }
        }
    }

    fun decode(String str, Long j) {
        MAttr mAttr = (MAttr) this.attrMap.get(str)
        if (mAttr == null) {
            return null
        }
        if (mAttr.getType() == MAttrType.ENUM) {
            String str2 = (String) mAttr.getValues().get(Long.valueOf(j))
            if (str2 != null) {
                return str2
            }
        } else if (mAttr.getType() == MAttrType.FLAG) {
            StringBuilder sb = StringBuilder()
            for (Map.Entry entry : mAttr.getValues().entrySet()) {
                if ((((Long) entry.getKey()).longValue() & j) != 0) {
                    sb.append((String) entry.getValue()).append('|')
                }
            }
            if (sb.length() != 0) {
                return sb.deleteCharAt(sb.length() - 1).toString()
            }
        }
        return "UNKNOWN_DATA_0x" + Long.toHexString(j)
    }

    fun parseAll() throws DOMException {
        parse(loadXML(ATTR_XML))
        parse(loadXML(MANIFEST_ATTR_XML))
        LOG.a("Loaded android attributes count: {}", Integer.valueOf(this.attrMap.size()))
    }
}
