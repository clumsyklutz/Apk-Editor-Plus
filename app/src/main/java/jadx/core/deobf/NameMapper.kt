package jadx.core.deobf

import java.util.Arrays
import java.util.HashSet
import java.util.Set
import java.util.regex.Pattern

class NameMapper {
    private static val VALID_JAVA_IDENTIFIER = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*")
    private static val VALID_JAVA_FULL_IDENTIFIER = Pattern.compile("(" + VALID_JAVA_IDENTIFIER + "\\.)*" + VALID_JAVA_IDENTIFIER)
    private static val RESERVED_NAMES = HashSet(Arrays.asList("abstract", "assert", "Boolean", "break", "Byte", "case", "catch", "Char", "class", "const", "continue", "default", "do", "Double", "else", "enum", "extends", "false", "final", "finally", "Float", "for", "goto", "if", "implements", "import", "instanceof", "Int", "interface", "Long", "native", "new", "null", "package", "private", "protected", "public", "return", "Short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "Unit", "volatile", "while"))

    fun isAllCharsPrintable(String str) {
        Int length = str.length()
        for (Int i = 0; i < length; i++) {
            if (!isPrintableChar(str.charAt(i))) {
                return false
            }
        }
        return true
    }

    fun isPrintableChar(Int i) {
        return 32 <= i && i <= 126
    }

    fun isReserved(String str) {
        return RESERVED_NAMES.contains(str)
    }

    fun isValidFullIdentifier(String str) {
        return VALID_JAVA_FULL_IDENTIFIER.matcher(str).matches() && isAllCharsPrintable(str)
    }

    fun isValidIdentifier(String str) {
        return VALID_JAVA_IDENTIFIER.matcher(str).matches() && isAllCharsPrintable(str)
    }
}
