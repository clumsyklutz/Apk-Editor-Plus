package com.android.apksig.internal.apk

import com.android.apksig.ApkVerificationIssue
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class ApkSigResult {
    public final Int signatureSchemeVersion
    public Boolean verified
    public final List<ApkSignerInfo> mSigners = ArrayList()
    public final List<ApkVerificationIssue> mWarnings = ArrayList()
    public final List<ApkVerificationIssue> mErrors = ArrayList()

    constructor(Int i) {
        this.signatureSchemeVersion = i
    }

    fun addError(Int i, Object... objArr) {
        this.mErrors.add(ApkVerificationIssue(i, objArr))
    }

    fun addWarning(Int i, Object... objArr) {
        this.mWarnings.add(ApkVerificationIssue(i, objArr))
    }

    fun containsErrors() {
        if (!this.mErrors.isEmpty()) {
            return true
        }
        if (this.mSigners.isEmpty()) {
            return false
        }
        Iterator<ApkSignerInfo> it = this.mSigners.iterator()
        while (it.hasNext()) {
            if (it.next().containsErrors()) {
                return true
            }
        }
        return false
    }

    fun containsWarnings() {
        if (!this.mWarnings.isEmpty()) {
            return true
        }
        if (this.mSigners.isEmpty()) {
            return false
        }
        Iterator<ApkSignerInfo> it = this.mSigners.iterator()
        while (it.hasNext()) {
            if (it.next().containsWarnings()) {
                return true
            }
        }
        return false
    }

    public List<? extends ApkVerificationIssue> getErrors() {
        return this.mErrors
    }

    public List<? extends ApkVerificationIssue> getWarnings() {
        return this.mWarnings
    }
}
