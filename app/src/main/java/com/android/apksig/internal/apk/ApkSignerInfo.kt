package com.android.apksig.internal.apk

import com.android.apksig.ApkVerificationIssue
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.List

class ApkSignerInfo {
    public Int index
    public List<X509Certificate> certs = ArrayList()
    public List<X509Certificate> certificateLineage = ArrayList()
    public final List<ApkVerificationIssue> mWarnings = ArrayList()
    public final List<ApkVerificationIssue> mErrors = ArrayList()

    fun addError(Int i, Object... objArr) {
        this.mErrors.add(ApkVerificationIssue(i, objArr))
    }

    fun addWarning(Int i, Object... objArr) {
        this.mWarnings.add(ApkVerificationIssue(i, objArr))
    }

    fun containsErrors() {
        return !this.mErrors.isEmpty()
    }

    fun containsWarnings() {
        return !this.mWarnings.isEmpty()
    }

    public List<? extends ApkVerificationIssue> getErrors() {
        return this.mErrors
    }

    public List<? extends ApkVerificationIssue> getWarnings() {
        return this.mWarnings
    }
}
