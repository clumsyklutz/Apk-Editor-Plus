package jadx.core.xmlgen

import androidx.core.view.InputDeviceCompat
import com.gmail.heagoo.apkeditor.gzd
import jadx.api.ResourcesLoader
import jadx.core.codegen.CodeWriter
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.RootNode
import jadx.core.utils.StringUtils
import jadx.core.utils.exceptions.JadxRuntimeException
import jadx.core.xmlgen.entry.ValuesParser
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field
import java.util.HashMap
import java.util.Iterator
import java.util.Map
import org.d.b
import org.d.c

class BinaryXMLParser extends CommonBinaryParser {
    private static val ANDROID_R_STYLE_CLS = "android.R$style"
    private static val ATTR_NEW_LINE = false
    private static val LOG = c.a(BinaryXMLParser.class)
    private final ManifestAttributes attributes
    private Boolean firstElement
    private final Map resNames
    private Array<String> strings
    private ValuesParser valuesParser
    private CodeWriter writer
    private String nsPrefix = "ERROR"
    private String nsURI = "ERROR"
    private String currentTag = "ERROR"
    private Boolean wasOneLiner = false
    private val styleMap = HashMap()
    private val localStyleMap = HashMap()

    constructor(RootNode rootNode) {
        try {
            try {
                for (Field field : Class.forName(ANDROID_R_STYLE_CLS).getFields()) {
                    this.styleMap.put(Integer.valueOf(field.getInt(field.getType())), field.getName())
                }
            } catch (Throwable th) {
                LOG.b("R class loading failed", th)
            }
            Iterator it = rootNode.getDexNodes().iterator()
            while (it.hasNext()) {
                for (Map.Entry entry : ((DexNode) it.next()).getConstFields().entrySet()) {
                    Object key = entry.getKey()
                    FieldNode fieldNode = (FieldNode) entry.getValue()
                    if (fieldNode.getType().equals(ArgType.INT) && (key is Integer)) {
                        this.localStyleMap.put((Integer) key, fieldNode)
                    }
                }
            }
            this.resNames = rootNode.getResourcesNames()
            this.attributes = ManifestAttributes()
            this.attributes.parseAll()
        } catch (Exception e) {
            throw JadxRuntimeException("BinaryXMLParser init error", e)
        }
    }

    private fun decodeAttribute(Int i, Int i2, Int i3) {
        if (i2 != 1) {
            String strDecodeValue = this.valuesParser.decodeValue(i2, i3)
            CodeWriter codeWriter = this.writer
            if (strDecodeValue == null) {
                strDecodeValue = "null"
            }
            codeWriter.add(strDecodeValue)
            return
        }
        String str = (String) this.styleMap.get(Integer.valueOf(i3))
        if (str != null) {
            this.writer.add("@*")
            if (i != -1) {
                this.writer.add(this.nsPrefix).add(':')
            }
            this.writer.add("style/").add(str.replaceAll("_", Deobfuscator.CLASS_NAME_SEPARATOR))
            return
        }
        FieldNode fieldNode = (FieldNode) this.localStyleMap.get(Integer.valueOf(i3))
        if (fieldNode != null) {
            String lowerCase = fieldNode.getParentClass().getShortName().toLowerCase()
            this.writer.add("@")
            if (gzd.COLUMN_ID.equals(lowerCase)) {
                this.writer.add('+')
            }
            this.writer.add(lowerCase).add("/").add(fieldNode.getName())
            return
        }
        String str2 = (String) this.resNames.get(Integer.valueOf(i3))
        if (str2 != null) {
            this.writer.add("@").add(str2)
        } else {
            this.writer.add("0x").add(Integer.toHexString(i3))
        }
    }

    private fun isBinaryXml() throws IOException {
        this.is.mark(4)
        Int int16 = this.is.readInt16()
        Int int162 = this.is.readInt16()
        if (int16 == 3 && int162 == 8) {
            return true
        }
        this.is.reset()
        return false
    }

    private fun parseAttribute(Int i, Boolean z) throws IOException {
        Int int32 = this.is.readInt32()
        Int int322 = this.is.readInt32()
        this.is.readInt32()
        if (this.is.readInt16() != 8) {
            die("attrValSize != 0x08 not supported")
        }
        if (this.is.readInt8() != 0) {
            die("res0 is not 0")
        }
        Int int8 = this.is.readInt8()
        Int int323 = this.is.readInt32()
        String str = this.strings[int322]
        if (z) {
            this.writer.startLine().addIndent()
        } else {
            this.writer.add(' ')
        }
        if (int32 != -1) {
            this.writer.add(this.nsPrefix).add(':')
        }
        this.writer.add(str).add("=\"")
        String strDecode = this.attributes.decode(str, int323)
        if (strDecode != null) {
            this.writer.add(strDecode)
        } else {
            decodeAttribute(int32, int8, int323)
        }
        this.writer.add('\"')
    }

    private fun parseCData() throws IOException {
        if (this.is.readInt16() != 16) {
            die("CDATA header is not 0x10")
        }
        if (this.is.readInt32() != 28) {
            die("CDATA header chunk is not 0x1C")
        }
        Int int32 = this.is.readInt32()
        this.is.skip(4L)
        String str = this.strings[this.is.readInt32()]
        this.writer.startLine().addIndent()
        this.writer.attachSourceLine(int32)
        this.writer.add(StringUtils.escapeXML(str.trim()))
        this.is.skip(this.is.readInt16() - 2)
    }

    private fun parseElement() throws IOException {
        if (this.firstElement) {
            this.firstElement = false
        } else {
            this.writer.incIndent()
        }
        if (this.is.readInt16() != 16) {
            die("ELEMENT HEADER SIZE is not 0x10")
        }
        this.is.readInt32()
        Int int32 = this.is.readInt32()
        this.is.readInt32()
        this.is.readInt32()
        Int int322 = this.is.readInt32()
        if (!this.wasOneLiner && !"ERROR".equals(this.currentTag) && !this.currentTag.equals(this.strings[int322])) {
            this.writer.add(">")
        }
        this.wasOneLiner = false
        this.currentTag = this.strings[int322]
        this.writer.startLine("<").add(this.currentTag)
        this.writer.attachSourceLine(int32)
        if (this.is.readInt16() != 20) {
            die("startNS's attributeStart is not 0x14")
        }
        if (this.is.readInt16() != 20) {
            die("startNS's attributeSize is not 0x14")
        }
        Int int16 = this.is.readInt16()
        this.is.readInt16()
        this.is.readInt16()
        this.is.readInt16()
        if ("manifest".equals(this.currentTag) || this.writer.getIndent() == 0) {
            this.writer.add(" xmlns:android=\"").add(this.nsURI).add("\"")
        }
        for (Int i = 0; i < int16; i++) {
            parseAttribute(i, false)
        }
    }

    private fun parseElementEnd() throws IOException {
        if (this.is.readInt16() != 16) {
            die("ELEMENT END header is not 0x10")
        }
        if (this.is.readInt32() != 24) {
            die("ELEMENT END header chunk is not 0x18 big")
        }
        Int int32 = this.is.readInt32()
        this.is.readInt32()
        Int int322 = this.is.readInt32()
        Int int323 = this.is.readInt32()
        if (this.currentTag.equals(this.strings[int323])) {
            this.writer.add(" />")
            this.wasOneLiner = true
        } else {
            this.writer.startLine("</")
            this.writer.attachSourceLine(int32)
            if (int322 != -1) {
                this.writer.add(this.strings[int322]).add(':')
            }
            this.writer.add(this.strings[int323]).add(">")
        }
        if (this.writer.getIndent() != 0) {
            this.writer.decIndent()
        }
    }

    private fun parseNameSpace() throws IOException {
        if (this.is.readInt16() != 16) {
            die("NAMESPACE header is not 0x0010")
        }
        if (this.is.readInt32() != 24) {
            die("NAMESPACE header chunk is not 0x18 big")
        }
        this.is.readInt32()
        this.is.readInt32()
        this.nsPrefix = this.strings[this.is.readInt32()]
        this.nsURI = this.strings[this.is.readInt32()]
    }

    private fun parseNameSpaceEnd() throws IOException {
        if (this.is.readInt16() != 16) {
            die("NAMESPACE header is not 0x0010")
        }
        if (this.is.readInt32() != 24) {
            die("NAMESPACE header chunk is not 0x18 big")
        }
        this.is.readInt32()
        this.is.readInt32()
        this.nsPrefix = this.strings[this.is.readInt32()]
        this.nsURI = this.strings[this.is.readInt32()]
    }

    private fun parseResourceMap() {
        if (this.is.readInt16() != 8) {
            die("Header size of resmap is not 8!")
        }
        Array<Int> iArr = new Int[(this.is.readInt32() - 8) / 4]
        for (Int i = 0; i < iArr.length; i++) {
            iArr[i] = this.is.readInt32()
        }
    }

    Unit decode() throws IOException {
        Int int32 = this.is.readInt32()
        while (this.is.getPos() < int32) {
            Int int16 = this.is.readInt16()
            switch (int16) {
                case 0:
                    break
                case 1:
                    this.strings = parseStringPoolNoType()
                    this.valuesParser = ValuesParser(this.strings, this.resNames)
                    break
                case 256:
                    parseNameSpace()
                    break
                case InputDeviceCompat.SOURCE_KEYBOARD /* 257 */:
                    parseNameSpaceEnd()
                    break
                case 258:
                    parseElement()
                    break
                case 259:
                    parseElementEnd()
                    break
                case 260:
                    parseCData()
                    break
                case 384:
                    parseResourceMap()
                    break
                default:
                    die("Type: 0x" + Integer.toHexString(int16) + " not yet implemented")
                    break
            }
        }
    }

    public synchronized CodeWriter parse(InputStream inputStream) {
        CodeWriter codeWriterLoadToCodeWriter
        this.is = ParserStream(inputStream)
        if (isBinaryXml()) {
            this.writer = CodeWriter()
            this.writer.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
            this.firstElement = true
            decode()
            this.writer.finish()
            codeWriterLoadToCodeWriter = this.writer
        } else {
            codeWriterLoadToCodeWriter = ResourcesLoader.loadToCodeWriter(inputStream)
        }
        return codeWriterLoadToCodeWriter
    }
}
