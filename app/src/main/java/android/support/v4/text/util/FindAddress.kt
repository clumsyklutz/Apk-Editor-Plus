package android.support.v4.text.util

import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import java.util.Locale
import java.util.regex.MatchResult
import java.util.regex.Matcher
import java.util.regex.Pattern

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class FindAddress {
    private static val HOUSE_COMPONENT = "(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)"
    private static val HOUSE_END = "(?=[,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)"
    private static val HOUSE_POST_DELIM = ",\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029"
    private static val HOUSE_PRE_DELIM = ":,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029"
    private static val MAX_ADDRESS_LINES = 5
    private static val MAX_ADDRESS_WORDS = 14
    private static val MAX_LOCATION_NAME_DISTANCE = 5
    private static val MIN_ADDRESS_WORDS = 4
    private static val NL = "\n\u000b\f\r\u0085\u2028\u2029"
    private static val SP = "\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000"
    private static val WORD_DELIM = ",*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029"
    private static val WORD_END = "(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)"
    private static val WS = "\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029"
    private static val kMaxAddressNameWordLength = 25
    private static final Array<ZipRange> sStateZipCodeRanges = {ZipRange(99, 99, -1, -1), ZipRange(35, 36, -1, -1), ZipRange(71, 72, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(85, 86, -1, -1), ZipRange(90, 96, -1, -1), ZipRange(80, 81, -1, -1), ZipRange(6, 6, -1, -1), ZipRange(20, 20, -1, -1), ZipRange(19, 19, -1, -1), ZipRange(32, 34, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(30, 31, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(50, 52, -1, -1), ZipRange(83, 83, -1, -1), ZipRange(60, 62, -1, -1), ZipRange(46, 47, -1, -1), ZipRange(66, 67, 73, -1), ZipRange(40, 42, -1, -1), ZipRange(70, 71, -1, -1), ZipRange(1, 2, -1, -1), ZipRange(20, 21, -1, -1), ZipRange(3, 4, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(48, 49, -1, -1), ZipRange(55, 56, -1, -1), ZipRange(63, 65, -1, -1), ZipRange(96, 96, -1, -1), ZipRange(38, 39, -1, -1), ZipRange(55, 56, -1, -1), ZipRange(27, 28, -1, -1), ZipRange(58, 58, -1, -1), ZipRange(68, 69, -1, -1), ZipRange(3, 4, -1, -1), ZipRange(7, 8, -1, -1), ZipRange(87, 88, 86, -1), ZipRange(88, 89, 96, -1), ZipRange(10, 14, 0, 6), ZipRange(43, 45, -1, -1), ZipRange(73, 74, -1, -1), ZipRange(97, 97, -1, -1), ZipRange(15, 19, -1, -1), ZipRange(6, 6, 0, 9), ZipRange(96, 96, -1, -1), ZipRange(2, 2, -1, -1), ZipRange(29, 29, -1, -1), ZipRange(57, 57, -1, -1), ZipRange(37, 38, -1, -1), ZipRange(75, 79, 87, 88), ZipRange(84, 84, -1, -1), ZipRange(22, 24, 20, -1), ZipRange(6, 9, -1, -1), ZipRange(5, 5, -1, -1), ZipRange(98, 99, -1, -1), ZipRange(53, 54, -1, -1), ZipRange(24, 26, -1, -1), ZipRange(82, 83, -1, -1)}
    private static val sWordRe = Pattern.compile("[^,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]+(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2)
    private static val sHouseNumberRe = Pattern.compile("(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2)
    private static val sStateRe = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+states[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mariana[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(nd|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(ne|nebraska)|(nh|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+hampshire)|(nj|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+jersey)|(nm|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mexico)|(nv|nevada)|(ny|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+rico)|(pw|palau)|(ri|rhode[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+island)|(sc|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(sd|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+virginia)|(wy|wyoming))(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2)
    private static val sLocationNameRe = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2)
    private static val sSuffixedNumberRe = Pattern.compile("(\\d+)(st|nd|rd|th)", 2)
    private static val sZipCodeRe = Pattern.compile("(?:\\d{5}(?:-\\d{4})?)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2)

    class ZipRange {
        Int mException1
        Int mException2
        Int mHigh
        Int mLow

        ZipRange(Int i, Int i2, Int i3, Int i4) {
            this.mLow = i
            this.mHigh = i2
            this.mException1 = i3
            this.mException2 = i3
        }

        Boolean matches(String str) throws NumberFormatException {
            Int i = Integer.parseInt(str.substring(0, 2))
            return (this.mLow <= i && i <= this.mHigh) || i == this.mException1 || i == this.mException2
        }
    }

    private constructor() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x009a, code lost:
    
        if (r2 > 0) goto L83
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x009c, code lost:
    
        if (r4 <= 0) goto L56
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ed, code lost:
    
        r4 = r0
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f1, code lost:
    
        r0 = r3
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:?, code lost:
    
        return r2
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:?, code lost:
    
        return -r4
     */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ef A[PHI: r1
  0x00ef: PHI (r1v5 Boolean) = (r1v3 Boolean), (r1v3 Boolean), (r1v3 Boolean), (r1v3 Boolean), (r1v2 Boolean) binds: [B:40:0x00a0, B:42:0x00a3, B:44:0x00a9, B:53:0x00df, B:28:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun attemptMatch(java.lang.String r13, java.util.regex.MatchResult r14) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.text.util.FindAddress.attemptMatch(java.lang.String, java.util.regex.MatchResult):Int")
    }

    private fun checkHouseNumber(String str) throws NumberFormatException {
        Int i = 0
        for (Int i2 = 0; i2 < str.length(); i2++) {
            if (Character.isDigit(str.charAt(i2))) {
                i++
            }
        }
        if (i > 5) {
            return false
        }
        Matcher matcher = sSuffixedNumberRe.matcher(str)
        if (!matcher.find()) {
            return true
        }
        Int i3 = Integer.parseInt(matcher.group(1))
        if (i3 == 0) {
            return false
        }
        String lowerCase = matcher.group(2).toLowerCase(Locale.getDefault())
        switch (i3 % 10) {
            case 1:
                break
            case 2:
                break
            case 3:
                break
        }
        return false
    }

    static String findAddress(String str) {
        Matcher matcher = sHouseNumberRe.matcher(str)
        Int iEnd = 0
        while (matcher.find(iEnd)) {
            if (checkHouseNumber(matcher.group(0))) {
                Int iStart = matcher.start()
                Int iAttemptMatch = attemptMatch(str, matcher)
                if (iAttemptMatch > 0) {
                    return str.substring(iStart, iAttemptMatch)
                }
                iEnd = -iAttemptMatch
            } else {
                iEnd = matcher.end()
            }
        }
        return null
    }

    @VisibleForTesting
    fun isValidLocationName(String str) {
        return sLocationNameRe.matcher(str).matches()
    }

    @VisibleForTesting
    fun isValidZipCode(String str) {
        return sZipCodeRe.matcher(str).matches()
    }

    @VisibleForTesting
    fun isValidZipCode(String str, String str2) {
        return isValidZipCode(str, matchState(str2, 0))
    }

    private fun isValidZipCode(String str, MatchResult matchResult) {
        Int i
        if (matchResult == null) {
            return false
        }
        Int iGroupCount = matchResult.groupCount()
        while (true) {
            if (iGroupCount <= 0) {
                i = iGroupCount
                break
            }
            i = iGroupCount - 1
            if (matchResult.group(iGroupCount) != null) {
                break
            }
            iGroupCount = i
        }
        return sZipCodeRe.matcher(str).matches() && sStateZipCodeRanges[i].matches(str)
    }

    @VisibleForTesting
    fun matchHouseNumber(String str, Int i) {
        if (i > 0 && HOUSE_PRE_DELIM.indexOf(str.charAt(i - 1)) == -1) {
            return null
        }
        Matcher matcherRegion = sHouseNumberRe.matcher(str).region(i, str.length())
        if (!matcherRegion.lookingAt()) {
            return null
        }
        MatchResult matchResult = matcherRegion.toMatchResult()
        if (checkHouseNumber(matchResult.group(0))) {
            return matchResult
        }
        return null
    }

    @VisibleForTesting
    fun matchState(String str, Int i) {
        if (i > 0 && WORD_DELIM.indexOf(str.charAt(i - 1)) == -1) {
            return null
        }
        Matcher matcherRegion = sStateRe.matcher(str).region(i, str.length())
        if (matcherRegion.lookingAt()) {
            return matcherRegion.toMatchResult()
        }
        return null
    }
}
