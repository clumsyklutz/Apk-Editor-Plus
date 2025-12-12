package android.support.v7.widget

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Movie
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import java.io.IOException
import java.io.InputStream
import org.xmlpull.v1.XmlPullParserException

class ResourcesWrapper extends Resources {
    private final Resources mResources

    constructor(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration())
        this.mResources = resources
    }

    @Override // android.content.res.Resources
    fun getAnimation(Int i) {
        return this.mResources.getAnimation(i)
    }

    @Override // android.content.res.Resources
    fun getBoolean(Int i) {
        return this.mResources.getBoolean(i)
    }

    @Override // android.content.res.Resources
    fun getColor(Int i) {
        return this.mResources.getColor(i)
    }

    @Override // android.content.res.Resources
    fun getColorStateList(Int i) {
        return this.mResources.getColorStateList(i)
    }

    @Override // android.content.res.Resources
    fun getConfiguration() {
        return this.mResources.getConfiguration()
    }

    @Override // android.content.res.Resources
    fun getDimension(Int i) {
        return this.mResources.getDimension(i)
    }

    @Override // android.content.res.Resources
    fun getDimensionPixelOffset(Int i) {
        return this.mResources.getDimensionPixelOffset(i)
    }

    @Override // android.content.res.Resources
    fun getDimensionPixelSize(Int i) {
        return this.mResources.getDimensionPixelSize(i)
    }

    @Override // android.content.res.Resources
    fun getDisplayMetrics() {
        return this.mResources.getDisplayMetrics()
    }

    @Override // android.content.res.Resources
    fun getDrawable(Int i) {
        return this.mResources.getDrawable(i)
    }

    @Override // android.content.res.Resources
    @RequiresApi(21)
    fun getDrawable(Int i, Resources.Theme theme) {
        return this.mResources.getDrawable(i, theme)
    }

    @Override // android.content.res.Resources
    @RequiresApi(15)
    fun getDrawableForDensity(Int i, Int i2) {
        return this.mResources.getDrawableForDensity(i, i2)
    }

    @Override // android.content.res.Resources
    @RequiresApi(21)
    fun getDrawableForDensity(Int i, Int i2, Resources.Theme theme) {
        return this.mResources.getDrawableForDensity(i, i2, theme)
    }

    @Override // android.content.res.Resources
    fun getFraction(Int i, Int i2, Int i3) {
        return this.mResources.getFraction(i, i2, i3)
    }

    @Override // android.content.res.Resources
    fun getIdentifier(String str, String str2, String str3) {
        return this.mResources.getIdentifier(str, str2, str3)
    }

    @Override // android.content.res.Resources
    public Array<Int> getIntArray(Int i) {
        return this.mResources.getIntArray(i)
    }

    @Override // android.content.res.Resources
    fun getInteger(Int i) {
        return this.mResources.getInteger(i)
    }

    @Override // android.content.res.Resources
    fun getLayout(Int i) {
        return this.mResources.getLayout(i)
    }

    @Override // android.content.res.Resources
    fun getMovie(Int i) {
        return this.mResources.getMovie(i)
    }

    @Override // android.content.res.Resources
    fun getQuantityString(Int i, Int i2) {
        return this.mResources.getQuantityString(i, i2)
    }

    @Override // android.content.res.Resources
    fun getQuantityString(Int i, Int i2, Object... objArr) {
        return this.mResources.getQuantityString(i, i2, objArr)
    }

    @Override // android.content.res.Resources
    fun getQuantityText(Int i, Int i2) {
        return this.mResources.getQuantityText(i, i2)
    }

    @Override // android.content.res.Resources
    fun getResourceEntryName(Int i) {
        return this.mResources.getResourceEntryName(i)
    }

    @Override // android.content.res.Resources
    fun getResourceName(Int i) {
        return this.mResources.getResourceName(i)
    }

    @Override // android.content.res.Resources
    fun getResourcePackageName(Int i) {
        return this.mResources.getResourcePackageName(i)
    }

    @Override // android.content.res.Resources
    fun getResourceTypeName(Int i) {
        return this.mResources.getResourceTypeName(i)
    }

    @Override // android.content.res.Resources
    fun getString(Int i) {
        return this.mResources.getString(i)
    }

    @Override // android.content.res.Resources
    fun getString(Int i, Object... objArr) {
        return this.mResources.getString(i, objArr)
    }

    @Override // android.content.res.Resources
    public Array<String> getStringArray(Int i) {
        return this.mResources.getStringArray(i)
    }

    @Override // android.content.res.Resources
    fun getText(Int i) {
        return this.mResources.getText(i)
    }

    @Override // android.content.res.Resources
    fun getText(Int i, CharSequence charSequence) {
        return this.mResources.getText(i, charSequence)
    }

    @Override // android.content.res.Resources
    public Array<CharSequence> getTextArray(Int i) {
        return this.mResources.getTextArray(i)
    }

    @Override // android.content.res.Resources
    fun getValue(Int i, TypedValue typedValue, Boolean z) throws Resources.NotFoundException {
        this.mResources.getValue(i, typedValue, z)
    }

    @Override // android.content.res.Resources
    fun getValue(String str, TypedValue typedValue, Boolean z) throws Resources.NotFoundException {
        this.mResources.getValue(str, typedValue, z)
    }

    @Override // android.content.res.Resources
    @RequiresApi(15)
    fun getValueForDensity(Int i, Int i2, TypedValue typedValue, Boolean z) throws Resources.NotFoundException {
        this.mResources.getValueForDensity(i, i2, typedValue, z)
    }

    @Override // android.content.res.Resources
    fun getXml(Int i) {
        return this.mResources.getXml(i)
    }

    @Override // android.content.res.Resources
    fun obtainAttributes(AttributeSet attributeSet, Array<Int> iArr) {
        return this.mResources.obtainAttributes(attributeSet, iArr)
    }

    @Override // android.content.res.Resources
    fun obtainTypedArray(Int i) {
        return this.mResources.obtainTypedArray(i)
    }

    @Override // android.content.res.Resources
    fun openRawResource(Int i) {
        return this.mResources.openRawResource(i)
    }

    @Override // android.content.res.Resources
    fun openRawResource(Int i, TypedValue typedValue) {
        return this.mResources.openRawResource(i, typedValue)
    }

    @Override // android.content.res.Resources
    fun openRawResourceFd(Int i) {
        return this.mResources.openRawResourceFd(i)
    }

    @Override // android.content.res.Resources
    fun parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) throws XmlPullParserException {
        this.mResources.parseBundleExtra(str, attributeSet, bundle)
    }

    @Override // android.content.res.Resources
    fun parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) throws XmlPullParserException, IOException {
        this.mResources.parseBundleExtras(xmlResourceParser, bundle)
    }

    @Override // android.content.res.Resources
    fun updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics)
        if (this.mResources != null) {
            this.mResources.updateConfiguration(configuration, displayMetrics)
        }
    }
}
