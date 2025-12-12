package jadx.api

import android.support.v7.widget.ActivityChooserView
import java.io.File

class JadxArgs implements IJadxArgs {
    private File outDir = File("jadx-output")
    private Int threadsCount = Math.max(1, Runtime.getRuntime().availableProcessors() - 1)
    private Boolean cfgOutput = false
    private Boolean rawCFGOutput = false
    private Boolean isVerbose = false
    private Boolean fallbackMode = false
    private Boolean showInconsistentCode = false
    private Boolean isSkipResources = false
    private Boolean isSkipSources = false
    private Boolean isDeobfuscationOn = false
    private Boolean isDeobfuscationForceSave = false
    private Boolean useSourceNameAsClassAlias = false
    private Int deobfuscationMinLength = 0
    private Int deobfuscationMaxLength = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED

    @Override // jadx.api.IJadxArgs
    fun getDeobfuscationMaxLength() {
        return this.deobfuscationMaxLength
    }

    @Override // jadx.api.IJadxArgs
    fun getDeobfuscationMinLength() {
        return this.deobfuscationMinLength
    }

    @Override // jadx.api.IJadxArgs
    fun getOutDir() {
        return this.outDir
    }

    @Override // jadx.api.IJadxArgs
    fun getThreadsCount() {
        return this.threadsCount
    }

    @Override // jadx.api.IJadxArgs
    fun isCFGOutput() {
        return this.cfgOutput
    }

    @Override // jadx.api.IJadxArgs
    fun isDeobfuscationForceSave() {
        return this.isDeobfuscationForceSave
    }

    @Override // jadx.api.IJadxArgs
    fun isDeobfuscationOn() {
        return this.isDeobfuscationOn
    }

    @Override // jadx.api.IJadxArgs
    fun isFallbackMode() {
        return this.fallbackMode
    }

    @Override // jadx.api.IJadxArgs
    fun isRawCFGOutput() {
        return this.rawCFGOutput
    }

    @Override // jadx.api.IJadxArgs
    fun isShowInconsistentCode() {
        return this.showInconsistentCode
    }

    @Override // jadx.api.IJadxArgs
    fun isSkipResources() {
        return this.isSkipResources
    }

    @Override // jadx.api.IJadxArgs
    fun isSkipSources() {
        return this.isSkipSources
    }

    @Override // jadx.api.IJadxArgs
    fun isVerbose() {
        return this.isVerbose
    }

    fun setCfgOutput(Boolean z) {
        this.cfgOutput = z
    }

    fun setDeobfuscationForceSave(Boolean z) {
        this.isDeobfuscationForceSave = z
    }

    fun setDeobfuscationMaxLength(Int i) {
        this.deobfuscationMaxLength = i
    }

    fun setDeobfuscationMinLength(Int i) {
        this.deobfuscationMinLength = i
    }

    fun setDeobfuscationOn(Boolean z) {
        this.isDeobfuscationOn = z
    }

    fun setFallbackMode(Boolean z) {
        this.fallbackMode = z
    }

    fun setOutDir(File file) {
        this.outDir = file
    }

    fun setRawCFGOutput(Boolean z) {
        this.rawCFGOutput = z
    }

    fun setShowInconsistentCode(Boolean z) {
        this.showInconsistentCode = z
    }

    fun setSkipResources(Boolean z) {
        this.isSkipResources = z
    }

    fun setSkipSources(Boolean z) {
        this.isSkipSources = z
    }

    fun setThreadsCount(Int i) {
        this.threadsCount = i
    }

    fun setUseSourceNameAsClassAlias(Boolean z) {
        this.useSourceNameAsClassAlias = z
    }

    fun setVerbose(Boolean z) {
        this.isVerbose = z
    }

    @Override // jadx.api.IJadxArgs
    fun useSourceNameAsClassAlias() {
        return this.useSourceNameAsClassAlias
    }
}
