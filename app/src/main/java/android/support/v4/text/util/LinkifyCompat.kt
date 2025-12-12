package android.support.v4.text.util

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.util.PatternsCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.URLSpan
import android.text.util.Linkify
import android.webkit.WebView
import android.widget.TextView
import java.io.UnsupportedEncodingException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.net.URLEncoder
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.Iterator
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

class LinkifyCompat {
    private static final Array<String> EMPTY_STRING = new String[0]
    private static val COMPARATOR = Comparator() { // from class: android.support.v4.text.util.LinkifyCompat.1
        @Override // java.util.Comparator
        public final Int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            if (linkSpec.start < linkSpec2.start) {
                return -1
            }
            if (linkSpec.start <= linkSpec2.start && linkSpec.end >= linkSpec2.end) {
                return linkSpec.end <= linkSpec2.end ? 0 : -1
            }
            return 1
        }
    }

    class LinkSpec {
        Int end
        URLSpan frameworkAddedSpan
        Int start
        String url

        LinkSpec() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface LinkifyMask {
    }

    private constructor() {
    }

    private fun addLinkMovementMethod(@NonNull TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod()
        if ((movementMethod == null || !(movementMethod is LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance())
        }
    }

    fun addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str)
        } else {
            addLinks(textView, pattern, str, (Array<String>) null, (Linkify.MatchFilter) null, (Linkify.TransformFilter) null)
        }
    }

    fun addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str, matchFilter, transformFilter)
        } else {
            addLinks(textView, pattern, str, (Array<String>) null, matchFilter, transformFilter)
        }
    }

    @SuppressLint({"NewApi"})
    fun addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable Array<String> strArr, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str, strArr, matchFilter, transformFilter)
            return
        }
        SpannableString spannableStringValueOf = SpannableString.valueOf(textView.getText())
        if (addLinks(spannableStringValueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.setText(spannableStringValueOf)
            addLinkMovementMethod(textView)
        }
    }

    fun addLinks(@NonNull Spannable spannable, Int i) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, i)
        }
        if (i == 0) {
            return false
        }
        Array<URLSpan> uRLSpanArr = (Array<URLSpan>) spannable.getSpans(0, spannable.length(), URLSpan.class)
        for (Int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length])
        }
        if ((i & 4) != 0) {
            Linkify.addLinks(spannable, 4)
        }
        ArrayList arrayList = ArrayList()
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, PatternsCompat.AUTOLINK_WEB_URL, new Array<String>{"http://", "https://", "rtsp://"}, Linkify.sUrlMatchFilter, null)
        }
        if ((i & 2) != 0) {
            gatherLinks(arrayList, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new Array<String>{"mailto:"}, null, null)
        }
        if ((i & 8) != 0) {
            gatherMapLinks(arrayList, spannable)
        }
        pruneOverlaps(arrayList, spannable)
        if (arrayList.size() == 0) {
            return false
        }
        Iterator it = arrayList.iterator()
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next()
            if (linkSpec.frameworkAddedSpan == null) {
                applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable)
            }
        }
        return true
    }

    fun addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str) {
        return shouldAddLinksFallbackToFramework() ? Linkify.addLinks(spannable, pattern, str) : addLinks(spannable, pattern, str, (Array<String>) null, (Linkify.MatchFilter) null, (Linkify.TransformFilter) null)
    }

    fun addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        return shouldAddLinksFallbackToFramework() ? Linkify.addLinks(spannable, pattern, str, matchFilter, transformFilter) : addLinks(spannable, pattern, str, (Array<String>) null, matchFilter, transformFilter)
    }

    @SuppressLint({"NewApi"})
    fun addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable Array<String> strArr, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter)
        }
        if (str == null) {
            str = ""
        }
        if (strArr == null || strArr.length <= 0) {
            strArr = EMPTY_STRING
        }
        Array<String> strArr2 = new String[strArr.length + 1]
        strArr2[0] = str.toLowerCase(Locale.ROOT)
        for (Int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i]
            strArr2[i + 1] = str2 == null ? "" : str2.toLowerCase(Locale.ROOT)
        }
        Matcher matcher = pattern.matcher(spannable)
        Boolean z = false
        while (matcher.find()) {
            Int iStart = matcher.start()
            Int iEnd = matcher.end()
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, iStart, iEnd) : true) {
                applyLink(makeUrl(matcher.group(0), strArr2, matcher, transformFilter), iStart, iEnd, spannable)
                z = true
            }
        }
        return z
    }

    fun addLinks(@NonNull TextView textView, Int i) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(textView, i)
        }
        if (i == 0) {
            return false
        }
        CharSequence text = textView.getText()
        if (text is Spannable) {
            if (!addLinks((Spannable) text, i)) {
                return false
            }
            addLinkMovementMethod(textView)
            return true
        }
        SpannableString spannableStringValueOf = SpannableString.valueOf(text)
        if (!addLinks(spannableStringValueOf, i)) {
            return false
        }
        addLinkMovementMethod(textView)
        textView.setText(spannableStringValueOf)
        return true
    }

    private fun applyLink(String str, Int i, Int i2, Spannable spannable) {
        spannable.setSpan(URLSpan(str), i, i2, 33)
    }

    private fun findAddress(String str) {
        return Build.VERSION.SDK_INT >= 28 ? WebView.findAddress(str) : FindAddress.findAddress(str)
    }

    private fun gatherLinks(ArrayList arrayList, Spannable spannable, Pattern pattern, Array<String> strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable)
        while (matcher.find()) {
            Int iStart = matcher.start()
            Int iEnd = matcher.end()
            if (matchFilter == null || matchFilter.acceptMatch(spannable, iStart, iEnd)) {
                LinkSpec linkSpec = LinkSpec()
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter)
                linkSpec.start = iStart
                linkSpec.end = iEnd
                arrayList.add(linkSpec)
            }
        }
    }

    private fun gatherMapLinks(ArrayList arrayList, Spannable spannable) {
        Int iIndexOf
        String string = spannable.toString()
        Int i = 0
        while (true) {
            try {
                String strFindAddress = findAddress(string)
                if (strFindAddress == null || (iIndexOf = string.indexOf(strFindAddress)) < 0) {
                    return
                }
                LinkSpec linkSpec = LinkSpec()
                Int length = strFindAddress.length() + iIndexOf
                linkSpec.start = iIndexOf + i
                linkSpec.end = i + length
                string = string.substring(length)
                i += length
                try {
                    linkSpec.url = "geo:0,0?q=" + URLEncoder.encode(strFindAddress, "UTF-8")
                    arrayList.add(linkSpec)
                } catch (UnsupportedEncodingException e) {
                }
            } catch (UnsupportedOperationException e2) {
                return
            }
        }
    }

    private fun makeUrl(@NonNull String str, @NonNull Array<String> strArr, Matcher matcher, @Nullable Linkify.TransformFilter transformFilter) {
        Boolean z = true
        String strTransformUrl = transformFilter != null ? transformFilter.transformUrl(matcher, str) : str
        Int i = 0
        while (true) {
            if (i >= strArr.length) {
                z = false
                break
            }
            if (strTransformUrl.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                if (!strTransformUrl.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                    strTransformUrl = strArr[i] + strTransformUrl.substring(strArr[i].length())
                }
            } else {
                i++
            }
        }
        return (z || strArr.length <= 0) ? strTransformUrl : strArr[0] + strTransformUrl
    }

    private fun pruneOverlaps(ArrayList arrayList, Spannable spannable) {
        Int i = 0
        Array<URLSpan> uRLSpanArr = (Array<URLSpan>) spannable.getSpans(0, spannable.length(), URLSpan.class)
        for (Int i2 = 0; i2 < uRLSpanArr.length; i2++) {
            LinkSpec linkSpec = LinkSpec()
            linkSpec.frameworkAddedSpan = uRLSpanArr[i2]
            linkSpec.start = spannable.getSpanStart(uRLSpanArr[i2])
            linkSpec.end = spannable.getSpanEnd(uRLSpanArr[i2])
            arrayList.add(linkSpec)
        }
        Collections.sort(arrayList, COMPARATOR)
        Int size = arrayList.size()
        while (i < size - 1) {
            LinkSpec linkSpec2 = (LinkSpec) arrayList.get(i)
            LinkSpec linkSpec3 = (LinkSpec) arrayList.get(i + 1)
            if (linkSpec2.start <= linkSpec3.start && linkSpec2.end > linkSpec3.start) {
                Int i3 = (linkSpec3.end > linkSpec2.end && linkSpec2.end - linkSpec2.start <= linkSpec3.end - linkSpec3.start) ? linkSpec2.end - linkSpec2.start < linkSpec3.end - linkSpec3.start ? i : -1 : i + 1
                if (i3 != -1) {
                    Object obj = ((LinkSpec) arrayList.get(i3)).frameworkAddedSpan
                    if (obj != null) {
                        spannable.removeSpan(obj)
                    }
                    arrayList.remove(i3)
                    size--
                }
            }
            i++
        }
    }

    private fun shouldAddLinksFallbackToFramework() {
        return Build.VERSION.SDK_INT >= 28
    }
}
