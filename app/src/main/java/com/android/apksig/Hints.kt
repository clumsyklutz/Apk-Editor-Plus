package com.android.apksig

import android.annotation.TargetApi
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern

class Hints {

    public static final class ByteRange {
        public final Long end
        public final Long start

        constructor(Long j, Long j2) {
            this.start = j
            this.end = j2
        }
    }

    public static final class PatternWithRange {
        public final Long offset
        public final Pattern pattern
        public final Long size

        constructor(String str) {
            this.pattern = Pattern.compile(str)
            this.offset = 0L
            this.size = Long.MAX_VALUE
        }

        constructor(String str, Long j, Long j2) {
            this.pattern = Pattern.compile(str)
            this.offset = j
            this.size = j2
        }

        fun ClampToAbsoluteByteRange(ByteRange byteRange) {
            Long j = byteRange.end
            Long j2 = byteRange.start
            Long j3 = j - j2
            Long j4 = this.offset
            if (j3 < j4) {
                return null
            }
            Long j5 = j2 + j4
            return ByteRange(j5, Math.min(j - j5, this.size) + j5)
        }

        fun matcher(CharSequence charSequence) {
            return this.pattern.matcher(charSequence)
        }
    }

    fun clampToInt(Long j) {
        return (Int) Math.max(0L, Math.min(j, 2147483647L))
    }

    @TargetApi(19)
    public static Array<Byte> encodeByteRangeList(List<ByteRange> list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream(list.size() * 8)
        DataOutputStream dataOutputStream = DataOutputStream(byteArrayOutputStream)
        try {
            for (ByteRange byteRange : list) {
                dataOutputStream.writeInt(clampToInt(byteRange.start))
                dataOutputStream.writeInt(clampToInt(byteRange.end - byteRange.start))
            }
            return byteArrayOutputStream.toByteArray()
        } catch (IOException e) {
            AssertionError assertionError = AssertionError("impossible")
            assertionError.initCause(e)
            throw assertionError
        }
    }

    public static ArrayList<PatternWithRange> parsePinPatterns(Array<Byte> bArr) throws NumberFormatException {
        ArrayList<PatternWithRange> arrayList = new ArrayList<>()
        try {
            for (String str : String(bArr, "UTF-8").split("\n")) {
                String strReplaceFirst = str.replaceFirst("#.*", "")
                Array<String> strArrSplit = strReplaceFirst.split(" ")
                if (strArrSplit.length == 1) {
                    arrayList.add(PatternWithRange(strArrSplit[0]))
                } else {
                    if (strArrSplit.length != 3) {
                        throw AssertionError("bad pin pattern line " + strReplaceFirst)
                    }
                    Long j = Long.parseLong(strArrSplit[1])
                    arrayList.add(PatternWithRange(strArrSplit[0], j, Long.parseLong(strArrSplit[2]) - j))
                }
            }
            return arrayList
        } catch (UnsupportedEncodingException e) {
            throw RuntimeException("UTF-8 must be supported", e)
        }
    }
}
