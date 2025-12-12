package android.support.v7.view

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.support.annotation.StyleRes
import androidx.appcompat.R
import android.view.LayoutInflater

class ContextThemeWrapper extends ContextWrapper {
    private LayoutInflater mInflater
    private Configuration mOverrideConfiguration
    private Resources mResources
    private Resources.Theme mTheme
    private Int mThemeResource

    constructor() {
        super(null)
    }

    constructor(Context context, @StyleRes Int i) {
        super(context)
        this.mThemeResource = i
    }

    constructor(Context context, Resources.Theme theme) {
        super(context)
        this.mTheme = theme
    }

    private fun getResourcesInternal() {
        if (this.mResources == null) {
            if (this.mOverrideConfiguration == null) {
                this.mResources = super.getResources()
            } else if (Build.VERSION.SDK_INT >= 17) {
                this.mResources = createConfigurationContext(this.mOverrideConfiguration).getResources()
            }
        }
        return this.mResources
    }

    private fun initializeTheme() {
        Boolean z = this.mTheme == null
        if (z) {
            this.mTheme = getResources().newTheme()
            Resources.Theme theme = getBaseContext().getTheme()
            if (theme != null) {
                this.mTheme.setTo(theme)
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, z)
    }

    fun applyOverrideConfiguration(Configuration configuration) {
        if (this.mResources != null) {
            throw IllegalStateException("getResources() or getAssets() has already been called")
        }
        if (this.mOverrideConfiguration != null) {
            throw IllegalStateException("Override configuration has already been set")
        }
        this.mOverrideConfiguration = Configuration(configuration)
    }

    @Override // android.content.ContextWrapper
    protected fun attachBaseContext(Context context) {
        super.attachBaseContext(context)
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun getAssets() {
        return getResources().getAssets()
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun getResources() {
        return getResourcesInternal()
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str)
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this)
        }
        return this.mInflater
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        if (this.mTheme != null) {
            return this.mTheme
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = R.style.Theme_AppCompat_Light
        }
        initializeTheme()
        return this.mTheme
    }

    fun getThemeResId() {
        return this.mThemeResource
    }

    protected fun onApplyThemeResource(Resources.Theme theme, Int i, Boolean z) {
        theme.applyStyle(i, true)
    }

    @Override // android.content.ContextWrapper, android.content.Context
    fun setTheme(Int i) {
        if (this.mThemeResource != i) {
            this.mThemeResource = i
            initializeTheme()
        }
    }
}
