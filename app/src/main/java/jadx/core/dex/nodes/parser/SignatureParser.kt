package jadx.core.dex.nodes.parser

import androidx.appcompat.R
import jadx.core.Consts
import jadx.core.dex.attributes.IAttributeNode
import jadx.core.dex.attributes.annotations.Annotation
import jadx.core.dex.instructions.args.ArgType
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Collections
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.LinkedList
import java.util.List
import java.util.Map
import org.d.b
import org.d.c

class SignatureParser {
    private static val LOG = c.a(SignatureParser.class)
    private static val STOP_CHAR = 0
    private final Int end
    private final String sign
    private Int pos = -1
    private Int mark = 0

    constructor(String str) {
        this.sign = str
        this.end = this.sign.length()
    }

    private fun consume(Char c) {
        Char next = next()
        if (c != next) {
            throw JadxRuntimeException("Consume wrong Char: '" + next + "' != '" + c + "', sign: " + debugString())
        }
    }

    private fun consumeExtendsTypesList() {
        Boolean zLookAhead
        List listEmptyList = Collections.emptyList()
        do {
            ArgType argTypeConsumeType = consumeType()
            if (!argTypeConsumeType.equals(ArgType.OBJECT)) {
                if (listEmptyList.isEmpty()) {
                    listEmptyList = LinkedList()
                }
                listEmptyList.add(argTypeConsumeType)
            }
            zLookAhead = lookAhead(':')
            if (zLookAhead) {
                consume(':')
            }
        } while (zLookAhead);
        return listEmptyList
    }

    private Array<ArgType> consumeGenericArgs() {
        ArgType argTypeConsumeType
        LinkedList linkedList = LinkedList()
        do {
            if (lookAhead('*')) {
                next()
                argTypeConsumeType = ArgType.wildcard()
            } else if (lookAhead('+')) {
                next()
                argTypeConsumeType = ArgType.wildcard(consumeType(), 1)
            } else if (lookAhead('-')) {
                next()
                argTypeConsumeType = ArgType.wildcard(consumeType(), -1)
            } else {
                argTypeConsumeType = consumeType()
            }
            if (argTypeConsumeType != null) {
                linkedList.add(argTypeConsumeType)
            }
            if (argTypeConsumeType == null) {
                break
            }
        } while (!lookAhead('>'));
        return (Array<ArgType>) linkedList.toArray(new ArgType[linkedList.size()])
    }

    private fun consumeObjectType(Boolean z) {
        Char next
        mark()
        do {
            next = next()
            if (next != 0) {
                if (next == '<') {
                    break
                }
            } else {
                return null
            }
        } while (next != ';');
        if (next == ';') {
            return ArgType.object(z ? slice().replace('/', '.') : inclusiveSlice())
        }
        String strSlice = slice()
        if (!z) {
            strSlice = strSlice + ";"
        }
        Array<ArgType> argTypeArrConsumeGenericArgs = consumeGenericArgs()
        consume('>')
        ArgType argTypeGeneric = ArgType.generic(strSlice, argTypeArrConsumeGenericArgs)
        if (!lookAhead('.')) {
            consume(';')
            return argTypeGeneric
        }
        consume('.')
        next()
        ArgType argTypeConsumeObjectType = consumeObjectType(true)
        return ArgType.genericInner(argTypeGeneric, argTypeConsumeObjectType.getObject(), argTypeConsumeObjectType.getGenericTypes())
    }

    private fun consumeUntil(Char c) {
        mark()
        if (forwardTo(c)) {
            return slice()
        }
        return null
    }

    private fun debugString() {
        return this.sign + " at position " + this.pos + " ('" + this.sign.charAt(this.pos) + "')"
    }

    private fun forwardTo(Char c) {
        Char next
        Int i = this.pos
        do {
            next = next()
            if (next == 0) {
                this.pos = i
                return false
            }
        } while (next != c);
        return true
    }

    fun fromNode(IAttributeNode iAttributeNode) {
        Annotation annotation = iAttributeNode.getAnnotation(Consts.DALVIK_SIGNATURE)
        if (annotation == null) {
            return null
        }
        return SignatureParser(mergeSignature((List) annotation.getDefaultValue()))
    }

    private fun inclusiveSlice() {
        return this.mark >= this.pos ? "" : this.sign.substring(this.mark, this.pos + 1)
    }

    private fun lookAhead(Char c) {
        Int i = this.pos + 1
        return i < this.end && this.sign.charAt(i) == c
    }

    private fun mark() {
        this.mark = this.pos
    }

    private fun mergeSignature(List list) {
        if (list.size() == 1) {
            return (String) list.get(0)
        }
        StringBuilder sb = StringBuilder()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            sb.append((String) it.next())
        }
        return sb.toString()
    }

    private fun next() {
        this.pos++
        if (this.pos >= this.end) {
            return (Char) 0
        }
        return this.sign.charAt(this.pos)
    }

    private fun slice() {
        return this.mark >= this.pos ? "" : this.sign.substring(this.mark, this.pos)
    }

    private fun tryConsume(Char c) {
        if (!lookAhead(c)) {
            return false
        }
        next()
        return true
    }

    fun consumeGenericMap() {
        if (!lookAhead('<')) {
            return Collections.emptyMap()
        }
        LinkedHashMap linkedHashMap = LinkedHashMap(2)
        consume('<')
        while (!lookAhead('>') && next() != 0) {
            String strConsumeUntil = consumeUntil(':')
            if (strConsumeUntil == null) {
                LOG.d("Can't parse generic map: {}", this.sign)
                return Collections.emptyMap()
            }
            tryConsume(':')
            linkedHashMap.put(ArgType.genericType(strConsumeUntil), consumeExtendsTypesList())
        }
        consume('>')
        return linkedHashMap
    }

    fun consumeMethodArgs() {
        consume('(')
        if (lookAhead(')')) {
            consume(')')
            return Collections.emptyList()
        }
        LinkedList linkedList = LinkedList()
        do {
            linkedList.add(consumeType())
        } while (!lookAhead(')'));
        consume(')')
        return linkedList
    }

    fun consumeType() {
        Char next = next()
        mark()
        switch (next) {
            case 0:
                return null
            case R.styleable.AppCompatTheme_dropDownListViewStyle /* 76 */:
                ArgType argTypeConsumeObjectType = consumeObjectType(false)
                if (argTypeConsumeObjectType != null) {
                    return argTypeConsumeObjectType
                }
                break
            case R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /* 84 */:
                next()
                mark()
                if (forwardTo(';')) {
                    return ArgType.genericType(slice())
                }
                break
            case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                return ArgType.array(consumeType())
            default:
                ArgType argType = ArgType.parse(next)
                if (argType != null) {
                    return argType
                }
                break
        }
        throw JadxRuntimeException("Can't parse type: " + debugString())
    }

    fun toString() {
        return this.pos == -1 ? this.sign : this.sign.substring(0, this.mark) + '{' + this.sign.substring(this.mark, this.pos) + '}' + this.sign.substring(this.pos)
    }
}
