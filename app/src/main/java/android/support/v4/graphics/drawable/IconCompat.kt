package android.support.v4.graphics.drawable

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.support.v4.util.Preconditions
import androidx.core.view.ViewCompat
import android.text.TextUtils
import android.util.Log
import androidx.versionedparcelable.CustomVersionedParcelable
import com.gmail.heagoo.apkeditor.gzd
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.InvocationTargetException
import java.nio.charset.Charset

class IconCompat extends CustomVersionedParcelable {
    private static val ADAPTIVE_ICON_INSET_FACTOR = 0.25f
    private static val AMBIENT_SHADOW_ALPHA = 30
    private static val BLUR_FACTOR = 0.010416667f
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN
    private static val DEFAULT_VIEW_PORT_SCALE = 0.6666667f
    private static val EXTRA_INT1 = "int1"
    private static val EXTRA_INT2 = "int2"
    private static val EXTRA_OBJ = "obj"
    private static val EXTRA_TINT_LIST = "tint_list"
    private static val EXTRA_TINT_MODE = "tint_mode"
    private static val EXTRA_TYPE = "type"
    private static val ICON_DIAMETER_FACTOR = 0.9166667f
    private static val KEY_SHADOW_ALPHA = 61
    private static val KEY_SHADOW_OFFSET_FACTOR = 0.020833334f
    private static val TAG = "IconCompat"
    public static val TYPE_UNKNOWN = -1

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Array<Byte> mData

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Int mInt1

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Int mInt2
    Object mObj1

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Parcelable mParcelable

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ColorStateList mTintList = null
    PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mTintModeStr

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Int mType

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface IconType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    constructor() {
    }

    private constructor(Int i) {
        this.mType = i
    }

    @Nullable
    fun createFromBundle(@NonNull Bundle bundle) {
        Int i = bundle.getInt(EXTRA_TYPE)
        IconCompat iconCompat = IconCompat(i)
        iconCompat.mInt1 = bundle.getInt(EXTRA_INT1)
        iconCompat.mInt2 = bundle.getInt(EXTRA_INT2)
        if (bundle.containsKey(EXTRA_TINT_LIST)) {
            iconCompat.mTintList = (ColorStateList) bundle.getParcelable(EXTRA_TINT_LIST)
        }
        if (bundle.containsKey(EXTRA_TINT_MODE)) {
            iconCompat.mTintMode = PorterDuff.Mode.valueOf(bundle.getString(EXTRA_TINT_MODE))
        }
        switch (i) {
            case -1:
            case 1:
            case 5:
                iconCompat.mObj1 = bundle.getParcelable(EXTRA_OBJ)
                break
            case 0:
            default:
                Log.w(TAG, "Unknown type " + i)
                return null
            case 2:
            case 4:
                iconCompat.mObj1 = bundle.getString(EXTRA_OBJ)
                break
            case 3:
                iconCompat.mObj1 = bundle.getByteArray(EXTRA_OBJ)
                break
        }
        return iconCompat
    }

    @RequiresApi(23)
    @Nullable
    fun createFromIcon(@NonNull Context context, @NonNull Icon icon) {
        Preconditions.checkNotNull(icon)
        switch (getType(icon)) {
            case 2:
                String resPackage = getResPackage(icon)
                try {
                    return createWithResource(getResources(context, resPackage), resPackage, getResId(icon))
                } catch (Resources.NotFoundException e) {
                    throw IllegalArgumentException("Icon resource cannot be found")
                }
            case 3:
            default:
                IconCompat iconCompat = IconCompat(-1)
                iconCompat.mObj1 = icon
                return iconCompat
            case 4:
                return createWithContentUri(getUri(icon))
        }
    }

    @RequiresApi(23)
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun createFromIcon(@NonNull Icon icon) {
        Preconditions.checkNotNull(icon)
        switch (getType(icon)) {
            case 2:
                return createWithResource(null, getResPackage(icon), getResId(icon))
            case 3:
            default:
                IconCompat iconCompat = IconCompat(-1)
                iconCompat.mObj1 = icon
                return iconCompat
            case 4:
                return createWithContentUri(getUri(icon))
        }
    }

    @VisibleForTesting
    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap bitmap, Boolean z) {
        Int iMin = (Int) (DEFAULT_VIEW_PORT_SCALE * Math.min(bitmap.getWidth(), bitmap.getHeight()))
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMin, iMin, Bitmap.Config.ARGB_8888)
        Canvas canvas = Canvas(bitmapCreateBitmap)
        Paint paint = Paint(3)
        Float f = iMin * 0.5f
        Float f2 = ICON_DIAMETER_FACTOR * f
        if (z) {
            Float f3 = BLUR_FACTOR * iMin
            paint.setColor(0)
            paint.setShadowLayer(f3, 0.0f, KEY_SHADOW_OFFSET_FACTOR * iMin, 1023410176)
            canvas.drawCircle(f, f, f2, paint)
            paint.setShadowLayer(f3, 0.0f, 0.0f, 503316480)
            canvas.drawCircle(f, f, f2, paint)
            paint.clearShadowLayer()
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK)
        BitmapShader bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        Matrix matrix = Matrix()
        matrix.setTranslate((-(bitmap.getWidth() - iMin)) / 2, (-(bitmap.getHeight() - iMin)) / 2)
        bitmapShader.setLocalMatrix(matrix)
        paint.setShader(bitmapShader)
        canvas.drawCircle(f, f, f2, paint)
        canvas.setBitmap(null)
        return bitmapCreateBitmap
    }

    fun createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw IllegalArgumentException("Bitmap must not be null.")
        }
        IconCompat iconCompat = IconCompat(5)
        iconCompat.mObj1 = bitmap
        return iconCompat
    }

    fun createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw IllegalArgumentException("Bitmap must not be null.")
        }
        IconCompat iconCompat = IconCompat(1)
        iconCompat.mObj1 = bitmap
        return iconCompat
    }

    fun createWithContentUri(Uri uri) {
        if (uri == null) {
            throw IllegalArgumentException("Uri must not be null.")
        }
        return createWithContentUri(uri.toString())
    }

    fun createWithContentUri(String str) {
        if (str == null) {
            throw IllegalArgumentException("Uri must not be null.")
        }
        IconCompat iconCompat = IconCompat(4)
        iconCompat.mObj1 = str
        return iconCompat
    }

    fun createWithData(Array<Byte> bArr, Int i, Int i2) {
        if (bArr == null) {
            throw IllegalArgumentException("Data must not be null.")
        }
        IconCompat iconCompat = IconCompat(3)
        iconCompat.mObj1 = bArr
        iconCompat.mInt1 = i
        iconCompat.mInt2 = i2
        return iconCompat
    }

    fun createWithResource(Context context, @DrawableRes Int i) {
        if (context == null) {
            throw IllegalArgumentException("Context must not be null.")
        }
        return createWithResource(context.getResources(), context.getPackageName(), i)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    fun createWithResource(Resources resources, String str, @DrawableRes Int i) {
        if (str == null) {
            throw IllegalArgumentException("Package must not be null.")
        }
        if (i == 0) {
            throw IllegalArgumentException("Drawable resource ID must not be 0")
        }
        IconCompat iconCompat = IconCompat(2)
        iconCompat.mInt1 = i
        if (resources != null) {
            try {
                iconCompat.mObj1 = resources.getResourceName(i)
            } catch (Resources.NotFoundException e) {
                throw IllegalArgumentException("Icon resource cannot be found")
            }
        } else {
            iconCompat.mObj1 = str
        }
        return iconCompat
    }

    @DrawableRes
    @RequiresApi(23)
    @IdRes
    private fun getResId(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId()
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue()
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon resource", e)
            return 0
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon resource", e2)
            return 0
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon resource", e3)
            return 0
        }
    }

    @RequiresApi(23)
    @Nullable
    private fun getResPackage(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage()
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0])
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon package", e)
            return null
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon package", e2)
            return null
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon package", e3)
            return null
        }
    }

    private fun getResources(Context context, String str) throws PackageManager.NameNotFoundException {
        if ("android".equals(str)) {
            return Resources.getSystem()
        }
        PackageManager packageManager = context.getPackageManager()
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8192)
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo)
            }
            return null
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, String.format("Unable to find pkg=%s for icon", str), e)
            return null
        }
    }

    @RequiresApi(23)
    private fun getType(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getType()
        }
        try {
            return ((Integer) icon.getClass().getMethod("getType", new Class[0]).invoke(icon, new Object[0])).intValue()
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon type " + icon, e)
            return -1
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon type " + icon, e2)
            return -1
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon type " + icon, e3)
            return -1
        }
    }

    @RequiresApi(23)
    @Nullable
    private fun getUri(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getUri()
        }
        try {
            return (Uri) icon.getClass().getMethod("getUri", new Class[0]).invoke(icon, new Object[0])
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon uri", e)
            return null
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon uri", e2)
            return null
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon uri", e3)
            return null
        }
    }

    private fun loadDrawableInner(Context context) throws FileNotFoundException {
        InputStream inputStreamOpenInputStream
        switch (this.mType) {
            case 1:
                return BitmapDrawable(context.getResources(), (Bitmap) this.mObj1)
            case 2:
                String resPackage = getResPackage()
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName()
                }
                try {
                    return ResourcesCompat.getDrawable(getResources(context, resPackage), this.mInt1, context.getTheme())
                } catch (RuntimeException e) {
                    Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.mInt1), this.mObj1), e)
                    break
                }
            case 3:
                return BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((Array<Byte>) this.mObj1, this.mInt1, this.mInt2))
            case 4:
                Uri uri = Uri.parse((String) this.mObj1)
                String scheme = uri.getScheme()
                if (gzd.COLUMN_CONTENT.equals(scheme) || "file".equals(scheme)) {
                    try {
                        inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri)
                    } catch (Exception e2) {
                        Log.w(TAG, "Unable to load image from URI: " + uri, e2)
                        inputStreamOpenInputStream = null
                    }
                } else {
                    try {
                        inputStreamOpenInputStream = FileInputStream(File((String) this.mObj1))
                    } catch (FileNotFoundException e3) {
                        Log.w(TAG, "Unable to load image from path: " + uri, e3)
                        inputStreamOpenInputStream = null
                    }
                }
                if (inputStreamOpenInputStream != null) {
                    return BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(inputStreamOpenInputStream))
                }
                break
            case 5:
                return BitmapDrawable(context.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, false))
        }
        return null
    }

    private fun typeToString(Int i) {
        switch (i) {
            case 1:
                return "BITMAP"
            case 2:
                return "RESOURCE"
            case 3:
                return "DATA"
            case 4:
                return "URI"
            case 5:
                return "BITMAP_MASKABLE"
            default:
                return "UNKNOWN"
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable, @NonNull Context context) throws PackageManager.NameNotFoundException {
        Bitmap bitmapCreateBitmap
        checkResource(context)
        switch (this.mType) {
            case 1:
                bitmapCreateBitmap = (Bitmap) this.mObj1
                if (drawable != null) {
                    bitmapCreateBitmap = bitmapCreateBitmap.copy(bitmapCreateBitmap.getConfig(), true)
                    break
                }
                break
            case 2:
                try {
                    Context contextCreatePackageContext = context.createPackageContext(getResPackage(), 0)
                    if (drawable != null) {
                        Drawable drawable2 = ContextCompat.getDrawable(contextCreatePackageContext, this.mInt1)
                        if (drawable2.getIntrinsicWidth() <= 0 || drawable2.getIntrinsicHeight() <= 0) {
                            Int launcherLargeIconSize = ((ActivityManager) contextCreatePackageContext.getSystemService("activity")).getLauncherLargeIconSize()
                            bitmapCreateBitmap = Bitmap.createBitmap(launcherLargeIconSize, launcherLargeIconSize, Bitmap.Config.ARGB_8888)
                        } else {
                            bitmapCreateBitmap = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
                        }
                        drawable2.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight())
                        drawable2.draw(Canvas(bitmapCreateBitmap))
                        break
                    } else {
                        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(contextCreatePackageContext, this.mInt1))
                        return
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    throw IllegalArgumentException("Can't find package " + this.mObj1, e)
                }
                break
            case 3:
            case 4:
            default:
                throw IllegalArgumentException("Icon type not supported for intent shortcuts")
            case 5:
                bitmapCreateBitmap = createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, true)
                break
        }
        if (drawable != null) {
            Int width = bitmapCreateBitmap.getWidth()
            Int height = bitmapCreateBitmap.getHeight()
            drawable.setBounds(width / 2, height / 2, width, height)
            drawable.draw(Canvas(bitmapCreateBitmap))
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", bitmapCreateBitmap)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun checkResource(Context context) {
        if (this.mType == 2) {
            String str = (String) this.mObj1
            if (str.contains(":")) {
                String str2 = str.split(":", -1)[1]
                String str3 = str2.split("/", -1)[0]
                String str4 = str2.split("/", -1)[1]
                String str5 = str.split(":", -1)[0]
                Int identifier = getResources(context, str5).getIdentifier(str4, str3, str5)
                if (this.mInt1 != identifier) {
                    Log.i(TAG, "Id has changed for " + str5 + "/" + str4)
                    this.mInt1 = identifier
                }
            }
        }
    }

    @IdRes
    fun getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResId((Icon) this.mObj1)
        }
        if (this.mType != 2) {
            throw IllegalStateException("called getResId() on " + this)
        }
        return this.mInt1
    }

    @NonNull
    fun getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResPackage((Icon) this.mObj1)
        }
        if (this.mType != 2) {
            throw IllegalStateException("called getResPackage() on " + this)
        }
        return ((String) this.mObj1).split(":", -1)[0]
    }

    fun getType() {
        return (this.mType != -1 || Build.VERSION.SDK_INT < 23) ? this.mType : getType((Icon) this.mObj1)
    }

    @NonNull
    fun getUri() {
        return (this.mType != -1 || Build.VERSION.SDK_INT < 23) ? Uri.parse((String) this.mObj1) : getUri((Icon) this.mObj1)
    }

    fun loadDrawable(Context context) throws FileNotFoundException {
        checkResource(context)
        if (Build.VERSION.SDK_INT >= 23) {
            return toIcon().loadDrawable(context)
        }
        Drawable drawableLoadDrawableInner = loadDrawableInner(context)
        if (drawableLoadDrawableInner == null) {
            return drawableLoadDrawableInner
        }
        if (this.mTintList == null && this.mTintMode == DEFAULT_TINT_MODE) {
            return drawableLoadDrawableInner
        }
        drawableLoadDrawableInner.mutate()
        DrawableCompat.setTintList(drawableLoadDrawableInner, this.mTintList)
        DrawableCompat.setTintMode(drawableLoadDrawableInner, this.mTintMode)
        return drawableLoadDrawableInner
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    fun onPostParceling() {
        this.mTintMode = PorterDuff.Mode.valueOf(this.mTintModeStr)
        switch (this.mType) {
            case -1:
                if (this.mParcelable == null) {
                    throw IllegalArgumentException("Invalid icon")
                }
                this.mObj1 = this.mParcelable
                return
            case 0:
            default:
                return
            case 1:
            case 5:
                if (this.mParcelable != null) {
                    this.mObj1 = this.mParcelable
                    return
                }
                this.mObj1 = this.mData
                this.mType = 3
                this.mInt1 = 0
                this.mInt2 = this.mData.length
                return
            case 2:
            case 4:
                this.mObj1 = String(this.mData, Charset.forName("UTF-16"))
                return
            case 3:
                this.mObj1 = this.mData
                return
        }
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    fun onPreParceling(Boolean z) {
        this.mTintModeStr = this.mTintMode.name()
        switch (this.mType) {
            case -1:
                if (z) {
                    throw IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon")
                }
                this.mParcelable = (Parcelable) this.mObj1
                return
            case 0:
            default:
                return
            case 1:
            case 5:
                if (!z) {
                    this.mParcelable = (Parcelable) this.mObj1
                    return
                }
                Bitmap bitmap = (Bitmap) this.mObj1
                ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream)
                this.mData = byteArrayOutputStream.toByteArray()
                return
            case 2:
                this.mData = ((String) this.mObj1).getBytes(Charset.forName("UTF-16"))
                return
            case 3:
                this.mData = (Array<Byte>) this.mObj1
                return
            case 4:
                this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"))
                return
        }
    }

    fun setTint(@ColorInt Int i) {
        return setTintList(ColorStateList.valueOf(i))
    }

    fun setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList
        return this
    }

    fun setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode
        return this
    }

    fun toBundle() {
        Bundle bundle = Bundle()
        switch (this.mType) {
            case -1:
                bundle.putParcelable(EXTRA_OBJ, (Parcelable) this.mObj1)
                break
            case 0:
            default:
                throw IllegalArgumentException("Invalid icon")
            case 1:
            case 5:
                bundle.putParcelable(EXTRA_OBJ, (Bitmap) this.mObj1)
                break
            case 2:
            case 4:
                bundle.putString(EXTRA_OBJ, (String) this.mObj1)
                break
            case 3:
                bundle.putByteArray(EXTRA_OBJ, (Array<Byte>) this.mObj1)
                break
        }
        bundle.putInt(EXTRA_TYPE, this.mType)
        bundle.putInt(EXTRA_INT1, this.mInt1)
        bundle.putInt(EXTRA_INT2, this.mInt2)
        if (this.mTintList != null) {
            bundle.putParcelable(EXTRA_TINT_LIST, this.mTintList)
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            bundle.putString(EXTRA_TINT_MODE, this.mTintMode.name())
        }
        return bundle
    }

    @RequiresApi(23)
    fun toIcon() {
        Icon iconCreateWithContentUri
        switch (this.mType) {
            case -1:
                return (Icon) this.mObj1
            case 0:
            default:
                throw IllegalArgumentException("Unknown type")
            case 1:
                iconCreateWithContentUri = Icon.createWithBitmap((Bitmap) this.mObj1)
                break
            case 2:
                iconCreateWithContentUri = Icon.createWithResource(getResPackage(), this.mInt1)
                break
            case 3:
                iconCreateWithContentUri = Icon.createWithData((Array<Byte>) this.mObj1, this.mInt1, this.mInt2)
                break
            case 4:
                iconCreateWithContentUri = Icon.createWithContentUri((String) this.mObj1)
                break
            case 5:
                if (Build.VERSION.SDK_INT < 26) {
                    iconCreateWithContentUri = Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, false))
                    break
                } else {
                    iconCreateWithContentUri = Icon.createWithAdaptiveBitmap((Bitmap) this.mObj1)
                    break
                }
        }
        if (this.mTintList != null) {
            iconCreateWithContentUri.setTintList(this.mTintList)
        }
        if (this.mTintMode == DEFAULT_TINT_MODE) {
            return iconCreateWithContentUri
        }
        iconCreateWithContentUri.setTintMode(this.mTintMode)
        return iconCreateWithContentUri
    }

    fun toString() {
        if (this.mType == -1) {
            return String.valueOf(this.mObj1)
        }
        StringBuilder sbAppend = StringBuilder("Icon(typ=").append(typeToString(this.mType))
        switch (this.mType) {
            case 1:
            case 5:
                sbAppend.append(" size=").append(((Bitmap) this.mObj1).getWidth()).append("x").append(((Bitmap) this.mObj1).getHeight())
                break
            case 2:
                sbAppend.append(" pkg=").append(getResPackage()).append(" id=").append(String.format("0x%08x", Integer.valueOf(getResId())))
                break
            case 3:
                sbAppend.append(" len=").append(this.mInt1)
                if (this.mInt2 != 0) {
                    sbAppend.append(" off=").append(this.mInt2)
                    break
                }
                break
            case 4:
                sbAppend.append(" uri=").append(this.mObj1)
                break
        }
        if (this.mTintList != null) {
            sbAppend.append(" tint=")
            sbAppend.append(this.mTintList)
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            sbAppend.append(" mode=").append(this.mTintMode)
        }
        sbAppend.append(")")
        return sbAppend.toString()
    }
}
