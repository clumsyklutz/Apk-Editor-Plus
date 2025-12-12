package android.support.v4.text

import android.text.SpannableStringBuilder
import com.gmail.heagoo.neweditor.Token
import java.util.Locale

class BidiFormatter {
    private static val DEFAULT_FLAGS = 2
    private static val DIR_LTR = -1
    private static val DIR_RTL = 1
    private static val DIR_UNKNOWN = 0
    private static val EMPTY_STRING = ""
    private static val FLAG_STEREO_RESET = 2
    private static val LRE = 8234
    private static val PDF = 8236
    private static val RLE = 8235
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat
    private final Int mFlags
    private final Boolean mIsRtlContext
    static val DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR
    private static val LRM = 8206
    private static val LRM_STRING = Character.toString(LRM)
    private static val RLM = 8207
    private static val RLM_STRING = Character.toString(RLM)
    static val DEFAULT_LTR_INSTANCE = BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC)
    static val DEFAULT_RTL_INSTANCE = BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC)

    class Builder {
        private Int mFlags
        private Boolean mIsRtlContext
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat

        constructor() {
            initialize(BidiFormatter.isRtlLocale(Locale.getDefault()))
        }

        constructor(Locale locale) {
            initialize(BidiFormatter.isRtlLocale(locale))
        }

        constructor(Boolean z) {
            initialize(z)
        }

        private fun getDefaultInstanceFromContext(Boolean z) {
            return z ? BidiFormatter.DEFAULT_RTL_INSTANCE : BidiFormatter.DEFAULT_LTR_INSTANCE
        }

        private fun initialize(Boolean z) {
            this.mIsRtlContext = z
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC
            this.mFlags = 2
        }

        public final BidiFormatter build() {
            return (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) ? getDefaultInstanceFromContext(this.mIsRtlContext) : BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat)
        }

        public final Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat
            return this
        }

        public final Builder stereoReset(Boolean z) {
            if (z) {
                this.mFlags |= 2
            } else {
                this.mFlags &= -3
            }
            return this
        }
    }

    class DirectionalityEstimator {
        private Int charIndex
        private final Boolean isHtml
        private Char lastChar
        private final Int length
        private final CharSequence text
        private static val DIR_TYPE_CACHE_SIZE = 1792
        private static final Array<Byte> DIR_TYPE_CACHE = new Byte[DIR_TYPE_CACHE_SIZE]

        static {
            for (Int i = 0; i < DIR_TYPE_CACHE_SIZE; i++) {
                DIR_TYPE_CACHE[i] = Character.getDirectionality(i)
            }
        }

        DirectionalityEstimator(CharSequence charSequence, Boolean z) {
            this.text = charSequence
            this.isHtml = z
            this.length = charSequence.length()
        }

        private fun getCachedDirectionality(Char c) {
            return c < DIR_TYPE_CACHE_SIZE ? DIR_TYPE_CACHE[c] : Character.getDirectionality(c)
        }

        private fun skipEntityBackward() {
            Int i = this.charIndex
            while (this.charIndex > 0) {
                CharSequence charSequence = this.text
                Int i2 = this.charIndex - 1
                this.charIndex = i2
                this.lastChar = charSequence.charAt(i2)
                if (this.lastChar == '&') {
                    return (Byte) 12
                }
                if (this.lastChar == ';') {
                    break
                }
            }
            this.charIndex = i
            this.lastChar = ';'
            return Token.LITERAL1
        }

        private fun skipEntityForward() {
            while (this.charIndex < this.length) {
                CharSequence charSequence = this.text
                Int i = this.charIndex
                this.charIndex = i + 1
                Char cCharAt = charSequence.charAt(i)
                this.lastChar = cCharAt
                if (cCharAt == ';') {
                    return (Byte) 12
                }
            }
            return (Byte) 12
        }

        private fun skipTagBackward() {
            Int i = this.charIndex
            while (this.charIndex > 0) {
                CharSequence charSequence = this.text
                Int i2 = this.charIndex - 1
                this.charIndex = i2
                this.lastChar = charSequence.charAt(i2)
                if (this.lastChar != '<') {
                    if (this.lastChar == '>') {
                        break
                    }
                    if (this.lastChar == '\"' || this.lastChar == '\'') {
                        Char c = this.lastChar
                        while (this.charIndex > 0) {
                            CharSequence charSequence2 = this.text
                            Int i3 = this.charIndex - 1
                            this.charIndex = i3
                            Char cCharAt = charSequence2.charAt(i3)
                            this.lastChar = cCharAt
                            if (cCharAt == c) {
                                break
                            }
                        }
                    }
                } else {
                    return (Byte) 12
                }
            }
            this.charIndex = i
            this.lastChar = '>'
            return Token.LITERAL1
        }

        private fun skipTagForward() {
            Int i = this.charIndex
            while (this.charIndex < this.length) {
                CharSequence charSequence = this.text
                Int i2 = this.charIndex
                this.charIndex = i2 + 1
                this.lastChar = charSequence.charAt(i2)
                if (this.lastChar == '>') {
                    return (Byte) 12
                }
                if (this.lastChar == '\"' || this.lastChar == '\'') {
                    Char c = this.lastChar
                    while (this.charIndex < this.length) {
                        CharSequence charSequence2 = this.text
                        Int i3 = this.charIndex
                        this.charIndex = i3 + 1
                        Char cCharAt = charSequence2.charAt(i3)
                        this.lastChar = cCharAt
                        if (cCharAt == c) {
                            break
                        }
                    }
                }
            }
            this.charIndex = i
            this.lastChar = '<'
            return Token.LITERAL1
        }

        Byte dirTypeBackward() {
            this.lastChar = this.text.charAt(this.charIndex - 1)
            if (Character.isLowSurrogate(this.lastChar)) {
                Int iCodePointBefore = Character.codePointBefore(this.text, this.charIndex)
                this.charIndex -= Character.charCount(iCodePointBefore)
                return Character.getDirectionality(iCodePointBefore)
            }
            this.charIndex--
            Byte cachedDirectionality = getCachedDirectionality(this.lastChar)
            return this.isHtml ? this.lastChar == '>' ? skipTagBackward() : this.lastChar == ';' ? skipEntityBackward() : cachedDirectionality : cachedDirectionality
        }

        Byte dirTypeForward() {
            this.lastChar = this.text.charAt(this.charIndex)
            if (Character.isHighSurrogate(this.lastChar)) {
                Int iCodePointAt = Character.codePointAt(this.text, this.charIndex)
                this.charIndex += Character.charCount(iCodePointAt)
                return Character.getDirectionality(iCodePointAt)
            }
            this.charIndex++
            Byte cachedDirectionality = getCachedDirectionality(this.lastChar)
            return this.isHtml ? this.lastChar == '<' ? skipTagForward() : this.lastChar == '&' ? skipEntityForward() : cachedDirectionality : cachedDirectionality
        }

        Int getEntryDir() {
            this.charIndex = 0
            Int i = 0
            Int i2 = 0
            Int i3 = 0
            while (this.charIndex < this.length && i == 0) {
                switch (dirTypeForward()) {
                    case 0:
                        if (i3 != 0) {
                            i = i3
                            break
                        } else {
                            return -1
                        }
                    case 1:
                    case 2:
                        if (i3 != 0) {
                            i = i3
                            break
                        } else {
                            return 1
                        }
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    default:
                        i = i3
                        break
                    case 9:
                        break
                    case 14:
                    case 15:
                        i3++
                        i2 = -1
                        break
                    case 16:
                    case 17:
                        i3++
                        i2 = 1
                        break
                    case 18:
                        i3--
                        i2 = 0
                        break
                }
            }
            if (i == 0) {
                return 0
            }
            if (i2 != 0) {
                return i2
            }
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 14:
                    case 15:
                        if (i == i3) {
                            return -1
                        }
                        i3--
                        break
                    case 16:
                    case 17:
                        if (i != i3) {
                            i3--
                            break
                        } else {
                            return 1
                        }
                    case 18:
                        i3++
                        break
                }
            }
            return 0
        }

        Int getExitDir() {
            this.charIndex = this.length
            Int i = 0
            Int i2 = 0
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 0:
                        if (i2 != 0) {
                            if (i != 0) {
                                break
                            } else {
                                i = i2
                                break
                            }
                        } else {
                            return -1
                        }
                    case 1:
                    case 2:
                        if (i2 != 0) {
                            if (i != 0) {
                                break
                            } else {
                                i = i2
                                break
                            }
                        } else {
                            return 1
                        }
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    default:
                        if (i != 0) {
                            break
                        } else {
                            i = i2
                            break
                        }
                    case 9:
                        break
                    case 14:
                    case 15:
                        if (i != i2) {
                            i2--
                            break
                        } else {
                            return -1
                        }
                    case 16:
                    case 17:
                        if (i != i2) {
                            i2--
                            break
                        } else {
                            return 1
                        }
                    case 18:
                        i2++
                        break
                }
            }
            return 0
        }
    }

    BidiFormatter(Boolean z, Int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = z
        this.mFlags = i
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat
    }

    private fun getEntryDir(CharSequence charSequence) {
        return DirectionalityEstimator(charSequence, false).getEntryDir()
    }

    private fun getExitDir(CharSequence charSequence) {
        return DirectionalityEstimator(charSequence, false).getExitDir()
    }

    fun getInstance() {
        return Builder().build()
    }

    fun getInstance(Locale locale) {
        return Builder(locale).build()
    }

    fun getInstance(Boolean z) {
        return Builder(z).build()
    }

    static Boolean isRtlLocale(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1
    }

    private fun markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        Boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length())
        return (this.mIsRtlContext || !(zIsRtl || getExitDir(charSequence) == 1)) ? (!this.mIsRtlContext || (zIsRtl && getExitDir(charSequence) != -1)) ? "" : RLM_STRING : LRM_STRING
    }

    private fun markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        Boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length())
        return (this.mIsRtlContext || !(zIsRtl || getEntryDir(charSequence) == 1)) ? (!this.mIsRtlContext || (zIsRtl && getEntryDir(charSequence) != -1)) ? "" : RLM_STRING : LRM_STRING
    }

    public final Boolean getStereoReset() {
        return (this.mFlags & 2) != 0
    }

    public final Boolean isRtl(CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length())
    }

    public final Boolean isRtl(String str) {
        return isRtl((CharSequence) str)
    }

    public final Boolean isRtlContext() {
        return this.mIsRtlContext
    }

    public final CharSequence unicodeWrap(CharSequence charSequence) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, true)
    }

    public final CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(charSequence, textDirectionHeuristicCompat, true)
    }

    public final CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, Boolean z) {
        if (charSequence == null) {
            return null
        }
        Boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length())
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder()
        if (getStereoReset() && z) {
            spannableStringBuilder.append((CharSequence) markBefore(charSequence, zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR))
        }
        if (zIsRtl != this.mIsRtlContext) {
            spannableStringBuilder.append(zIsRtl ? RLE : LRE)
            spannableStringBuilder.append(charSequence)
            spannableStringBuilder.append(PDF)
        } else {
            spannableStringBuilder.append(charSequence)
        }
        if (z) {
            spannableStringBuilder.append((CharSequence) markAfter(charSequence, zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR))
        }
        return spannableStringBuilder
    }

    public final CharSequence unicodeWrap(CharSequence charSequence, Boolean z) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, z)
    }

    public final String unicodeWrap(String str) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, true)
    }

    public final String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true)
    }

    public final String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, Boolean z) {
        if (str == null) {
            return null
        }
        return unicodeWrap((CharSequence) str, textDirectionHeuristicCompat, z).toString()
    }

    public final String unicodeWrap(String str, Boolean z) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, z)
    }
}
