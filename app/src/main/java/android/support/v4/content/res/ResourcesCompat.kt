package android.support.v4.content.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.FontRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.util.Preconditions
import android.util.TypedValue

class ResourcesCompat {
    private static val TAG = "ResourcesCompat"

    abstract class FontCallback {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final Unit callbackFailAsync(final Int i, @Nullable Handler handler) {
            if (handler == null) {
                handler = Handler(Looper.getMainLooper())
            }
            handler.post(Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.2
                @Override // java.lang.Runnable
                fun run() {
                    FontCallback.this.onFontRetrievalFailed(i)
                }
            })
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final Unit callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
            if (handler == null) {
                handler = Handler(Looper.getMainLooper())
            }
            handler.post(Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.1
                @Override // java.lang.Runnable
                fun run() {
                    FontCallback.this.onFontRetrieved(typeface)
                }
            })
        }

        public abstract Unit onFontRetrievalFailed(Int i)

        public abstract Unit onFontRetrieved(@NonNull Typeface typeface)
    }

    private constructor() {
    }

    @ColorInt
    fun getColor(@NonNull Resources resources, @ColorRes Int i, @Nullable Resources.Theme theme) {
        return Build.VERSION.SDK_INT >= 23 ? resources.getColor(i, theme) : resources.getColor(i)
    }

    @Nullable
    fun getColorStateList(@NonNull Resources resources, @ColorRes Int i, @Nullable Resources.Theme theme) {
        return Build.VERSION.SDK_INT >= 23 ? resources.getColorStateList(i, theme) : resources.getColorStateList(i)
    }

    @Nullable
    fun getDrawable(@NonNull Resources resources, @DrawableRes Int i, @Nullable Resources.Theme theme) {
        return Build.VERSION.SDK_INT >= 21 ? resources.getDrawable(i, theme) : resources.getDrawable(i)
    }

    @Nullable
    fun getDrawableForDensity(@NonNull Resources resources, @DrawableRes Int i, Int i2, @Nullable Resources.Theme theme) {
        return Build.VERSION.SDK_INT >= 21 ? resources.getDrawableForDensity(i, i2, theme) : Build.VERSION.SDK_INT >= 15 ? resources.getDrawableForDensity(i, i2) : resources.getDrawable(i)
    }

    @Nullable
    fun getFont(@NonNull Context context, @FontRes Int i) {
        if (context.isRestricted()) {
            return null
        }
        return loadFont(context, i, TypedValue(), 0, null, null, false)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getFont(@NonNull Context context, @FontRes Int i, TypedValue typedValue, Int i2, @Nullable FontCallback fontCallback) {
        if (context.isRestricted()) {
            return null
        }
        return loadFont(context, i, typedValue, i2, fontCallback, null, true)
    }

    fun getFont(@NonNull Context context, @FontRes Int i, @NonNull FontCallback fontCallback, @Nullable Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback)
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler)
        } else {
            loadFont(context, i, TypedValue(), 0, fontCallback, handler, false)
        }
    }

    private fun loadFont(@NonNull Context context, Int i, TypedValue typedValue, Int i2, @Nullable FontCallback fontCallback, @Nullable Handler handler, Boolean z) throws Resources.NotFoundException {
        Resources resources = context.getResources()
        resources.getValue(i, typedValue, true)
        Typeface typefaceLoadFont = loadFont(context, resources, typedValue, i, i2, fontCallback, handler, z)
        if (typefaceLoadFont == null && fontCallback == null) {
            throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.")
        }
        return typefaceLoadFont
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Typeface loadFont(@android.support.annotation.NonNull android.content.Context r9, android.content.res.Resources r10, android.util.TypedValue r11, Int r12, Int r13, @android.support.annotation.Nullable android.support.v4.content.res.ResourcesCompat.FontCallback r14, @android.support.annotation.Nullable android.os.Handler r15, Boolean r16) {
        /*
            java.lang.CharSequence r0 = r11.string
            if (r0 != 0) goto L35
            android.content.res.Resources$NotFoundException r0 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Resource \""
            r1.<init>(r2)
            java.lang.String r2 = r10.getResourceName(r12)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "\" ("
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r12)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ") is not a Font: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L35:
            java.lang.CharSequence r0 = r11.string
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "res/"
            Boolean r0 = r8.startsWith(r0)
            if (r0 != 0) goto L4b
            if (r14 == 0) goto L49
            r0 = -3
            r14.callbackFailAsync(r0, r15)
        L49:
            r0 = 0
        L4a:
            return r0
        L4b:
            android.graphics.Typeface r0 = android.support.v4.graphics.TypefaceCompat.findFromCache(r10, r12, r13)
            if (r0 == 0) goto L57
            if (r14 == 0) goto L4a
            r14.callbackSuccessAsync(r0, r15)
            goto L4a
        L57:
            java.lang.String r0 = r8.toLowerCase()     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            java.lang.String r1 = ".xml"
            Boolean r0 = r0.endsWith(r1)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            if (r0 == 0) goto L89
            android.content.res.XmlResourceParser r0 = r10.getXml(r12)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            android.support.v4.content.res.FontResourcesParserCompat$FamilyResourceEntry r1 = android.support.v4.content.res.FontResourcesParserCompat.parse(r0, r10)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            if (r1 != 0) goto L7c
            java.lang.String r0 = "ResourcesCompat"
            java.lang.String r1 = "Failed to find font-family tag"
            android.util.Log.e(r0, r1)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            if (r14 == 0) goto L7a
            r0 = -3
            r14.callbackFailAsync(r0, r15)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
        L7a:
            r0 = 0
            goto L4a
        L7c:
            r0 = r9
            r2 = r10
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            android.graphics.Typeface r0 = android.support.v4.graphics.TypefaceCompat.createFromResourcesFamilyXml(r0, r1, r2, r3, r4, r5, r6, r7)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            goto L4a
        L89:
            android.graphics.Typeface r0 = android.support.v4.graphics.TypefaceCompat.createFromResourcesFontFile(r9, r10, r12, r8, r13)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            if (r14 == 0) goto L4a
            if (r0 == 0) goto Lb2
            r14.callbackSuccessAsync(r0, r15)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            goto L4a
        L95:
            r0 = move-exception
            java.lang.String r1 = "ResourcesCompat"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to parse xml resource "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r8)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2, r0)
        Laa:
            if (r14 == 0) goto Lb0
            r0 = -3
            r14.callbackFailAsync(r0, r15)
        Lb0:
            r0 = 0
            goto L4a
        Lb2:
            r1 = -3
            r14.callbackFailAsync(r1, r15)     // Catch: org.xmlpull.v1.XmlPullParserException -> L95 java.io.IOException -> Lb7
            goto L4a
        Lb7:
            r0 = move-exception
            java.lang.String r1 = "ResourcesCompat"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to read xml resource "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r8)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2, r0)
            goto Laa
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.content.res.ResourcesCompat.loadFont(android.content.Context, android.content.res.Resources, android.util.TypedValue, Int, Int, android.support.v4.content.res.ResourcesCompat$FontCallback, android.os.Handler, Boolean):android.graphics.Typeface")
    }
}
