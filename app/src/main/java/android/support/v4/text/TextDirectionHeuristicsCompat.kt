package android.support.v4.text

import java.nio.CharBuffer
import java.util.Locale

class TextDirectionHeuristicsCompat {
    private static val STATE_FALSE = 1
    private static val STATE_TRUE = 0
    private static val STATE_UNKNOWN = 2
    public static val LTR = TextDirectionHeuristicInternal(null, false)
    public static val RTL = TextDirectionHeuristicInternal(null, true)
    public static val FIRSTSTRONG_LTR = TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false)
    public static val FIRSTSTRONG_RTL = TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true)
    public static val ANYRTL_LTR = TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false)
    public static val LOCALE = TextDirectionHeuristicLocale.INSTANCE

    class AnyStrong implements TextDirectionAlgorithm {
        private final Boolean mLookForRtl
        static val INSTANCE_RTL = AnyStrong(true)
        static val INSTANCE_LTR = AnyStrong(false)

        private constructor(Boolean z) {
            this.mLookForRtl = z
        }

        @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        fun checkRtl(CharSequence charSequence, Int i, Int i2) {
            Int i3 = i + i2
            Boolean z = false
            while (i < i3) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(charSequence.charAt(i)))) {
                    case 0:
                        if (!this.mLookForRtl) {
                            z = true
                            break
                        } else {
                            return 0
                        }
                    case 1:
                        if (!this.mLookForRtl) {
                            return 1
                        }
                        z = true
                        break
                }
                i++
            }
            if (z) {
                return !this.mLookForRtl ? 0 : 1
            }
            return 2
        }
    }

    class FirstStrong implements TextDirectionAlgorithm {
        static val INSTANCE = FirstStrong()

        private constructor() {
        }

        @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        fun checkRtl(CharSequence charSequence, Int i, Int i2) {
            Int i3 = i + i2
            Int iIsRtlTextOrFormat = 2
            while (i < i3 && iIsRtlTextOrFormat == 2) {
                iIsRtlTextOrFormat = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(i)))
                i++
            }
            return iIsRtlTextOrFormat
        }
    }

    interface TextDirectionAlgorithm {
        Int checkRtl(CharSequence charSequence, Int i, Int i2)
    }

    abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm

        TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm
        }

        private fun doCheck(CharSequence charSequence, Int i, Int i2) {
            switch (this.mAlgorithm.checkRtl(charSequence, i, i2)) {
                case 0:
                    return true
                case 1:
                    return false
                default:
                    return defaultIsRtl()
            }
        }

        protected abstract Boolean defaultIsRtl()

        @Override // android.support.v4.text.TextDirectionHeuristicCompat
        fun isRtl(CharSequence charSequence, Int i, Int i2) {
            if (charSequence == null || i < 0 || i2 < 0 || charSequence.length() - i2 < i) {
                throw IllegalArgumentException()
            }
            return this.mAlgorithm == null ? defaultIsRtl() : doCheck(charSequence, i, i2)
        }

        @Override // android.support.v4.text.TextDirectionHeuristicCompat
        fun isRtl(Array<Char> cArr, Int i, Int i2) {
            return isRtl(CharBuffer.wrap(cArr), i, i2)
        }
    }

    class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final Boolean mDefaultIsRtl

        TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, Boolean z) {
            super(textDirectionAlgorithm)
            this.mDefaultIsRtl = z
        }

        @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        protected fun defaultIsRtl() {
            return this.mDefaultIsRtl
        }
    }

    class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        static val INSTANCE = TextDirectionHeuristicLocale()

        TextDirectionHeuristicLocale() {
            super(null)
        }

        @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        protected fun defaultIsRtl() {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1
        }
    }

    private constructor() {
    }

    static Int isRtlText(Int i) {
        switch (i) {
            case 0:
                return 1
            case 1:
            case 2:
                return 0
            default:
                return 2
        }
    }

    static Int isRtlTextOrFormat(Int i) {
        switch (i) {
            case 0:
            case 14:
            case 15:
                return 1
            case 1:
            case 2:
            case 16:
            case 17:
                return 0
            default:
                return 2
        }
    }
}
