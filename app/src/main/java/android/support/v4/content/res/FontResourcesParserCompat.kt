package android.support.v4.content.res

import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.ArrayRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.compat.R
import android.support.v4.provider.FontRequest
import android.util.Base64
import android.util.TypedValue
import android.util.Xml
import java.io.IOException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class FontResourcesParserCompat {
    private static val DEFAULT_TIMEOUT_MILLIS = 500
    public static val FETCH_STRATEGY_ASYNC = 1
    public static val FETCH_STRATEGY_BLOCKING = 0
    public static val INFINITE_TIMEOUT_VALUE = -1
    private static val ITALIC = 1
    private static val NORMAL_WEIGHT = 400

    public interface FamilyResourceEntry {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FetchStrategy {
    }

    class FontFamilyFilesResourceEntry implements FamilyResourceEntry {

        @NonNull
        private final Array<FontFileResourceEntry> mEntries

        constructor(@NonNull Array<FontFileResourceEntry> fontFileResourceEntryArr) {
            this.mEntries = fontFileResourceEntryArr
        }

        @NonNull
        public final Array<FontFileResourceEntry> getEntries() {
            return this.mEntries
        }
    }

    class FontFileResourceEntry {

        @NonNull
        private final String mFileName
        private Boolean mItalic
        private Int mResourceId
        private Int mTtcIndex
        private String mVariationSettings
        private Int mWeight

        constructor(@NonNull String str, Int i, Boolean z, @Nullable String str2, Int i2, Int i3) {
            this.mFileName = str
            this.mWeight = i
            this.mItalic = z
            this.mVariationSettings = str2
            this.mTtcIndex = i2
            this.mResourceId = i3
        }

        @NonNull
        public final String getFileName() {
            return this.mFileName
        }

        public final Int getResourceId() {
            return this.mResourceId
        }

        public final Int getTtcIndex() {
            return this.mTtcIndex
        }

        @Nullable
        public final String getVariationSettings() {
            return this.mVariationSettings
        }

        public final Int getWeight() {
            return this.mWeight
        }

        public final Boolean isItalic() {
            return this.mItalic
        }
    }

    class ProviderResourceEntry implements FamilyResourceEntry {

        @NonNull
        private final FontRequest mRequest
        private final Int mStrategy
        private final Int mTimeoutMs

        constructor(@NonNull FontRequest fontRequest, Int i, Int i2) {
            this.mRequest = fontRequest
            this.mStrategy = i
            this.mTimeoutMs = i2
        }

        public final Int getFetchStrategy() {
            return this.mStrategy
        }

        @NonNull
        public final FontRequest getRequest() {
            return this.mRequest
        }

        public final Int getTimeout() {
            return this.mTimeoutMs
        }
    }

    private constructor() {
    }

    private fun getType(TypedArray typedArray, Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return typedArray.getType(i)
        }
        TypedValue typedValue = TypedValue()
        typedArray.getValue(i, typedValue)
        return typedValue.type
    }

    @Nullable
    fun parse(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        Int next
        do {
            next = xmlPullParser.next()
            if (next == 2) {
                break
            }
        } while (next != 1);
        if (next != 2) {
            throw XmlPullParserException("No start tag found")
        }
        return readFamilies(xmlPullParser, resources)
    }

    fun readCerts(Resources resources, @ArrayRes Int i) throws Resources.NotFoundException {
        if (i == 0) {
            return Collections.emptyList()
        }
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(i)
        try {
            if (typedArrayObtainTypedArray.length() == 0) {
                return Collections.emptyList()
            }
            ArrayList arrayList = ArrayList()
            if (getType(typedArrayObtainTypedArray, 0) == 1) {
                for (Int i2 = 0; i2 < typedArrayObtainTypedArray.length(); i2++) {
                    Int resourceId = typedArrayObtainTypedArray.getResourceId(i2, 0)
                    if (resourceId != 0) {
                        arrayList.add(toByteArrayList(resources.getStringArray(resourceId)))
                    }
                }
            } else {
                arrayList.add(toByteArrayList(resources.getStringArray(i)))
            }
            return arrayList
        } finally {
            typedArrayObtainTypedArray.recycle()
        }
    }

    @Nullable
    private fun readFamilies(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "font-family")
        if (xmlPullParser.getName().equals("font-family")) {
            return readFamily(xmlPullParser, resources)
        }
        skip(xmlPullParser)
        return null
    }

    @Nullable
    private fun readFamily(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.FontFamily)
        String string = typedArrayObtainAttributes.getString(R.styleable.FontFamily_fontProviderAuthority)
        String string2 = typedArrayObtainAttributes.getString(R.styleable.FontFamily_fontProviderPackage)
        String string3 = typedArrayObtainAttributes.getString(R.styleable.FontFamily_fontProviderQuery)
        Int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.FontFamily_fontProviderCerts, 0)
        Int integer = typedArrayObtainAttributes.getInteger(R.styleable.FontFamily_fontProviderFetchStrategy, 1)
        Int integer2 = typedArrayObtainAttributes.getInteger(R.styleable.FontFamily_fontProviderFetchTimeout, DEFAULT_TIMEOUT_MILLIS)
        typedArrayObtainAttributes.recycle()
        if (string != null && string2 != null && string3 != null) {
            while (xmlPullParser.next() != 3) {
                skip(xmlPullParser)
            }
            return ProviderResourceEntry(FontRequest(string, string2, string3, readCerts(resources, resourceId)), integer, integer2)
        }
        ArrayList arrayList = ArrayList()
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    arrayList.add(readFont(xmlPullParser, resources))
                } else {
                    skip(xmlPullParser)
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null
        }
        return FontFamilyFilesResourceEntry((Array<FontFileResourceEntry>) arrayList.toArray(new FontFileResourceEntry[arrayList.size()]))
    }

    private fun readFont(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.FontFamilyFont)
        Int i = typedArrayObtainAttributes.getInt(typedArrayObtainAttributes.hasValue(R.styleable.FontFamilyFont_fontWeight) ? R.styleable.FontFamilyFont_fontWeight : R.styleable.FontFamilyFont_android_fontWeight, NORMAL_WEIGHT)
        Boolean z = 1 == typedArrayObtainAttributes.getInt(typedArrayObtainAttributes.hasValue(R.styleable.FontFamilyFont_fontStyle) ? R.styleable.FontFamilyFont_fontStyle : R.styleable.FontFamilyFont_android_fontStyle, 0)
        Int i2 = typedArrayObtainAttributes.hasValue(R.styleable.FontFamilyFont_ttcIndex) ? R.styleable.FontFamilyFont_ttcIndex : R.styleable.FontFamilyFont_android_ttcIndex
        String string = typedArrayObtainAttributes.getString(typedArrayObtainAttributes.hasValue(R.styleable.FontFamilyFont_fontVariationSettings) ? R.styleable.FontFamilyFont_fontVariationSettings : R.styleable.FontFamilyFont_android_fontVariationSettings)
        Int i3 = typedArrayObtainAttributes.getInt(i2, 0)
        Int i4 = typedArrayObtainAttributes.hasValue(R.styleable.FontFamilyFont_font) ? R.styleable.FontFamilyFont_font : R.styleable.FontFamilyFont_android_font
        Int resourceId = typedArrayObtainAttributes.getResourceId(i4, 0)
        String string2 = typedArrayObtainAttributes.getString(i4)
        typedArrayObtainAttributes.recycle()
        while (xmlPullParser.next() != 3) {
            skip(xmlPullParser)
        }
        return FontFileResourceEntry(string2, i, z, string, i3, resourceId)
    }

    private fun skip(XmlPullParser xmlPullParser) {
        Int i = 1
        while (i > 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++
                    break
                case 3:
                    i--
                    break
            }
        }
    }

    private fun toByteArrayList(Array<String> strArr) {
        ArrayList arrayList = ArrayList()
        for (String str : strArr) {
            arrayList.add(Base64.decode(str, 0))
        }
        return arrayList
    }
}
