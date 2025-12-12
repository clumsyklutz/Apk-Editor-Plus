package android.support.v4.graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.CancellationSignal
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.content.res.FontResourcesParserCompat
import android.support.v4.provider.FontsContractCompat
import android.support.v7.widget.ActivityChooserView
import java.io.File
import java.io.IOException
import java.io.InputStream

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatBaseImpl {
    private static val CACHE_FILE_PREFIX = "cached_font_"
    private static val TAG = "TypefaceCompatBaseImpl"

    interface StyleExtractor {
        Int getWeight(Object obj)

        Boolean isItalic(Object obj)
    }

    TypefaceCompatBaseImpl() {
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Int i) {
        return (FontResourcesParserCompat.FontFileResourceEntry) findBestFont(fontFamilyFilesResourceEntry.getEntries(), i, StyleExtractor() { // from class: android.support.v4.graphics.TypefaceCompatBaseImpl.2
            @Override // android.support.v4.graphics.TypefaceCompatBaseImpl.StyleExtractor
            fun getWeight(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.getWeight()
            }

            @Override // android.support.v4.graphics.TypefaceCompatBaseImpl.StyleExtractor
            fun isItalic(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.isItalic()
            }
        })
    }

    private fun findBestFont(Array<Object> objArr, Int i, StyleExtractor styleExtractor) {
        Object obj
        Int i2 = (i & 1) == 0 ? 400 : 700
        Boolean z = (i & 2) != 0
        Object obj2 = null
        Int i3 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        Int length = objArr.length
        Int i4 = 0
        while (i4 < length) {
            Object obj3 = objArr[i4]
            Int iAbs = (styleExtractor.isItalic(obj3) == z ? 0 : 1) + (Math.abs(styleExtractor.getWeight(obj3) - i2) << 1)
            if (obj2 == null || i3 > iAbs) {
                i3 = iAbs
                obj = obj3
            } else {
                obj = obj2
            }
            i4++
            obj2 = obj
        }
        return obj2
    }

    @Nullable
    fun createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, Int i) {
        FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntryFindBestEntry = findBestEntry(fontFamilyFilesResourceEntry, i)
        if (fontFileResourceEntryFindBestEntry == null) {
            return null
        }
        return TypefaceCompat.createFromResourcesFontFile(context, resources, fontFileResourceEntryFindBestEntry.getResourceId(), fontFileResourceEntryFindBestEntry.getFileName(), i)
    }

    fun createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.Array<FontInfo> fontInfoArr, Int i) throws Throwable {
        InputStream inputStreamOpenInputStream
        Throwable th
        Typeface typefaceCreateFromInputStream = null
        if (fontInfoArr.length > 0) {
            try {
                inputStreamOpenInputStream = context.getContentResolver().openInputStream(findBestInfo(fontInfoArr, i).getUri())
                try {
                    typefaceCreateFromInputStream = createFromInputStream(context, inputStreamOpenInputStream)
                    TypefaceCompatUtil.closeQuietly(inputStreamOpenInputStream)
                } catch (IOException e) {
                    TypefaceCompatUtil.closeQuietly(inputStreamOpenInputStream)
                    return typefaceCreateFromInputStream
                } catch (Throwable th2) {
                    th = th2
                    TypefaceCompatUtil.closeQuietly(inputStreamOpenInputStream)
                    throw th
                }
            } catch (IOException e2) {
                inputStreamOpenInputStream = null
            } catch (Throwable th3) {
                inputStreamOpenInputStream = null
                th = th3
            }
        }
        return typefaceCreateFromInputStream
    }

    protected fun createFromInputStream(Context context, InputStream inputStream) {
        Typeface typefaceCreateFromFile = null
        File tempFile = TypefaceCompatUtil.getTempFile(context)
        if (tempFile != null) {
            try {
                if (TypefaceCompatUtil.copyToFile(tempFile, inputStream)) {
                    typefaceCreateFromFile = Typeface.createFromFile(tempFile.getPath())
                }
            } catch (RuntimeException e) {
            } finally {
                tempFile.delete()
            }
        }
        return typefaceCreateFromFile
    }

    @Nullable
    fun createFromResourcesFontFile(Context context, Resources resources, Int i, String str, Int i2) {
        Typeface typefaceCreateFromFile = null
        File tempFile = TypefaceCompatUtil.getTempFile(context)
        if (tempFile != null) {
            try {
                if (TypefaceCompatUtil.copyToFile(tempFile, resources, i)) {
                    typefaceCreateFromFile = Typeface.createFromFile(tempFile.getPath())
                }
            } catch (RuntimeException e) {
            } finally {
                tempFile.delete()
            }
        }
        return typefaceCreateFromFile
    }

    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.Array<FontInfo> fontInfoArr, Int i) {
        return (FontsContractCompat.FontInfo) findBestFont(fontInfoArr, i, StyleExtractor() { // from class: android.support.v4.graphics.TypefaceCompatBaseImpl.1
            @Override // android.support.v4.graphics.TypefaceCompatBaseImpl.StyleExtractor
            fun getWeight(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.getWeight()
            }

            @Override // android.support.v4.graphics.TypefaceCompatBaseImpl.StyleExtractor
            fun isItalic(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.isItalic()
            }
        })
    }
}
