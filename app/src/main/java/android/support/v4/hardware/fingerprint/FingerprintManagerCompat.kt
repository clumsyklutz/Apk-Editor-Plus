package android.support.v4.hardware.fingerprint

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Handler
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import android.support.v4.os.CancellationSignal
import java.security.Signature
import javax.crypto.Cipher
import javax.crypto.Mac

class FingerprintManagerCompat {
    private final Context mContext

    abstract class AuthenticationCallback {
        fun onAuthenticationError(Int i, CharSequence charSequence) {
        }

        fun onAuthenticationFailed() {
        }

        fun onAuthenticationHelp(Int i, CharSequence charSequence) {
        }

        fun onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    class AuthenticationResult {
        private final CryptoObject mCryptoObject

        constructor(CryptoObject cryptoObject) {
            this.mCryptoObject = cryptoObject
        }

        public final CryptoObject getCryptoObject() {
            return this.mCryptoObject
        }
    }

    class CryptoObject {
        private final Cipher mCipher
        private final Mac mMac
        private final Signature mSignature

        constructor(@NonNull Signature signature) {
            this.mSignature = signature
            this.mCipher = null
            this.mMac = null
        }

        constructor(@NonNull Cipher cipher) {
            this.mCipher = cipher
            this.mSignature = null
            this.mMac = null
        }

        constructor(@NonNull Mac mac) {
            this.mMac = mac
            this.mCipher = null
            this.mSignature = null
        }

        @Nullable
        fun getCipher() {
            return this.mCipher
        }

        @Nullable
        fun getMac() {
            return this.mMac
        }

        @Nullable
        fun getSignature() {
            return this.mSignature
        }
    }

    private constructor(Context context) {
        this.mContext = context
    }

    @NonNull
    fun from(@NonNull Context context) {
        return FingerprintManagerCompat(context)
    }

    @RequiresApi(23)
    @Nullable
    private fun getFingerprintManagerOrNull(@NonNull Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class)
        }
        return null
    }

    @RequiresApi(23)
    static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null
        }
        if (cryptoObject.getCipher() != null) {
            return CryptoObject(cryptoObject.getCipher())
        }
        if (cryptoObject.getSignature() != null) {
            return CryptoObject(cryptoObject.getSignature())
        }
        if (cryptoObject.getMac() != null) {
            return CryptoObject(cryptoObject.getMac())
        }
        return null
    }

    @RequiresApi(23)
    private static FingerprintManager.AuthenticationCallback wrapCallback(final AuthenticationCallback authenticationCallback) {
        return new FingerprintManager.AuthenticationCallback() { // from class: android.support.v4.hardware.fingerprint.FingerprintManagerCompat.1
            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final Unit onAuthenticationError(Int i, CharSequence charSequence) {
                authenticationCallback.onAuthenticationError(i, charSequence)
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final Unit onAuthenticationFailed() {
                authenticationCallback.onAuthenticationFailed()
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final Unit onAuthenticationHelp(Int i, CharSequence charSequence) {
                authenticationCallback.onAuthenticationHelp(i, charSequence)
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final Unit onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                authenticationCallback.onAuthenticationSucceeded(AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(authenticationResult.getCryptoObject())))
            }
        }
    }

    @RequiresApi(23)
    private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getCipher())
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getSignature())
        }
        if (cryptoObject.getMac() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getMac())
        }
        return null
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public final Unit authenticate(@Nullable CryptoObject cryptoObject, Int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        FingerprintManager fingerprintManagerOrNull
        if (Build.VERSION.SDK_INT < 23 || (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) == null) {
            return
        }
        fingerprintManagerOrNull.authenticate(wrapCryptoObject(cryptoObject), cancellationSignal != null ? (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, i, wrapCallback(authenticationCallback), handler)
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public final Boolean hasEnrolledFingerprints() {
        FingerprintManager fingerprintManagerOrNull
        return Build.VERSION.SDK_INT >= 23 && (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) != null && fingerprintManagerOrNull.hasEnrolledFingerprints()
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public final Boolean isHardwareDetected() {
        FingerprintManager fingerprintManagerOrNull
        return Build.VERSION.SDK_INT >= 23 && (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) != null && fingerprintManagerOrNull.isHardwareDetected()
    }
}
