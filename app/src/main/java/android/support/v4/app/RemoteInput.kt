package android.support.v4.app

import android.app.RemoteInput
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.util.Log
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set

class RemoteInput {
    private static val EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData"
    public static val EXTRA_RESULTS_DATA = "android.remoteinput.resultsData"
    public static val RESULTS_CLIP_LABEL = "android.remoteinput.results"
    private static val TAG = "RemoteInput"
    private final Boolean mAllowFreeFormTextInput
    private final Set mAllowedDataTypes
    private final Array<CharSequence> mChoices
    private final Bundle mExtras
    private final CharSequence mLabel
    private final String mResultKey

    class Builder {
        private Array<CharSequence> mChoices
        private CharSequence mLabel
        private final String mResultKey
        private val mAllowedDataTypes = HashSet()
        private val mExtras = Bundle()
        private Boolean mAllowFreeFormTextInput = true

        constructor(@NonNull String str) {
            if (str == null) {
                throw IllegalArgumentException("Result key can't be null")
            }
            this.mResultKey = str
        }

        @NonNull
        public final Builder addExtras(@NonNull Bundle bundle) {
            if (bundle != null) {
                this.mExtras.putAll(bundle)
            }
            return this
        }

        @NonNull
        public final RemoteInput build() {
            return RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mExtras, this.mAllowedDataTypes)
        }

        @NonNull
        public final Bundle getExtras() {
            return this.mExtras
        }

        @NonNull
        public final Builder setAllowDataType(@NonNull String str, Boolean z) {
            if (z) {
                this.mAllowedDataTypes.add(str)
            } else {
                this.mAllowedDataTypes.remove(str)
            }
            return this
        }

        @NonNull
        public final Builder setAllowFreeFormInput(Boolean z) {
            this.mAllowFreeFormTextInput = z
            return this
        }

        @NonNull
        public final Builder setChoices(@Nullable Array<CharSequence> charSequenceArr) {
            this.mChoices = charSequenceArr
            return this
        }

        @NonNull
        public final Builder setLabel(@Nullable CharSequence charSequence) {
            this.mLabel = charSequence
            return this
        }
    }

    RemoteInput(String str, CharSequence charSequence, Array<CharSequence> charSequenceArr, Boolean z, Bundle bundle, Set set) {
        this.mResultKey = str
        this.mLabel = charSequence
        this.mChoices = charSequenceArr
        this.mAllowFreeFormTextInput = z
        this.mExtras = bundle
        this.mAllowedDataTypes = set
    }

    fun addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map map) {
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addDataResultToIntent(fromCompat(remoteInput), intent, map)
            return
        }
        if (Build.VERSION.SDK_INT < 16) {
            Log.w(TAG, "RemoteInput is only supported from API Level 16")
            return
        }
        Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent)
        Intent intent2 = clipDataIntentFromIntent == null ? Intent() : clipDataIntentFromIntent
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey()
            Uri uri = (Uri) entry.getValue()
            if (str != null) {
                Bundle bundleExtra = intent2.getBundleExtra(getExtraResultsKeyForData(str))
                if (bundleExtra == null) {
                    bundleExtra = Bundle()
                }
                bundleExtra.putString(remoteInput.getResultKey(), uri.toString())
                intent2.putExtra(getExtraResultsKeyForData(str), bundleExtra)
            }
        }
        intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, intent2))
    }

    fun addResultsToIntent(Array<RemoteInput> remoteInputArr, Intent intent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addResultsToIntent(fromCompat(remoteInputArr), intent, bundle)
            return
        }
        if (Build.VERSION.SDK_INT >= 20) {
            Bundle resultsFromIntent = getResultsFromIntent(intent)
            if (resultsFromIntent != null) {
                resultsFromIntent.putAll(bundle)
                bundle = resultsFromIntent
            }
            for (RemoteInput remoteInput : remoteInputArr) {
                Map dataResultsFromIntent = getDataResultsFromIntent(intent, remoteInput.getResultKey())
                android.app.RemoteInput.addResultsToIntent(fromCompat(new Array<RemoteInput>{remoteInput}), intent, bundle)
                if (dataResultsFromIntent != null) {
                    addDataResultToIntent(remoteInput, intent, dataResultsFromIntent)
                }
            }
            return
        }
        if (Build.VERSION.SDK_INT < 16) {
            Log.w(TAG, "RemoteInput is only supported from API Level 16")
            return
        }
        Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent)
        Intent intent2 = clipDataIntentFromIntent == null ? Intent() : clipDataIntentFromIntent
        Bundle bundleExtra = intent2.getBundleExtra(EXTRA_RESULTS_DATA)
        Bundle bundle2 = bundleExtra == null ? Bundle() : bundleExtra
        for (RemoteInput remoteInput2 : remoteInputArr) {
            Object obj = bundle.get(remoteInput2.getResultKey())
            if (obj is CharSequence) {
                bundle2.putCharSequence(remoteInput2.getResultKey(), (CharSequence) obj)
            }
        }
        intent2.putExtra(EXTRA_RESULTS_DATA, bundle2)
        intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, intent2))
    }

    @RequiresApi(20)
    static android.app.RemoteInput fromCompat(RemoteInput remoteInput) {
        return new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build()
    }

    @RequiresApi(20)
    static android.app.Array<RemoteInput> fromCompat(Array<RemoteInput> remoteInputArr) {
        if (remoteInputArr == null) {
            return null
        }
        android.app.Array<RemoteInput> remoteInputArr2 = new android.app.RemoteInput[remoteInputArr.length]
        for (Int i = 0; i < remoteInputArr.length; i++) {
            remoteInputArr2[i] = fromCompat(remoteInputArr[i])
        }
        return remoteInputArr2
    }

    @RequiresApi(16)
    private fun getClipDataIntentFromIntent(Intent intent) {
        ClipData clipData = intent.getClipData()
        if (clipData == null) {
            return null
        }
        ClipDescription description = clipData.getDescription()
        if (description.hasMimeType("text/vnd.android.intent") && description.getLabel().equals(RESULTS_CLIP_LABEL)) {
            return clipData.getItemAt(0).getIntent()
        }
        return null
    }

    fun getDataResultsFromIntent(Intent intent, String str) {
        String string
        if (Build.VERSION.SDK_INT >= 26) {
            return android.app.RemoteInput.getDataResultsFromIntent(intent, str)
        }
        if (Build.VERSION.SDK_INT < 16) {
            Log.w(TAG, "RemoteInput is only supported from API Level 16")
            return null
        }
        Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent)
        if (clipDataIntentFromIntent == null) {
            return null
        }
        HashMap map = HashMap()
        for (String str2 : clipDataIntentFromIntent.getExtras().keySet()) {
            if (str2.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA)) {
                String strSubstring = str2.substring(39)
                if (!strSubstring.isEmpty() && (string = clipDataIntentFromIntent.getBundleExtra(str2).getString(str)) != null && !string.isEmpty()) {
                    map.put(strSubstring, Uri.parse(string))
                }
            }
        }
        if (map.isEmpty()) {
            return null
        }
        return map
    }

    private fun getExtraResultsKeyForData(String str) {
        return EXTRA_DATA_TYPE_RESULTS_DATA + str
    }

    fun getResultsFromIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 20) {
            return android.app.RemoteInput.getResultsFromIntent(intent)
        }
        if (Build.VERSION.SDK_INT < 16) {
            Log.w(TAG, "RemoteInput is only supported from API Level 16")
            return null
        }
        Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent)
        if (clipDataIntentFromIntent != null) {
            return (Bundle) clipDataIntentFromIntent.getExtras().getParcelable(EXTRA_RESULTS_DATA)
        }
        return null
    }

    public final Boolean getAllowFreeFormInput() {
        return this.mAllowFreeFormTextInput
    }

    public final Set getAllowedDataTypes() {
        return this.mAllowedDataTypes
    }

    public final Array<CharSequence> getChoices() {
        return this.mChoices
    }

    public final Bundle getExtras() {
        return this.mExtras
    }

    public final CharSequence getLabel() {
        return this.mLabel
    }

    public final String getResultKey() {
        return this.mResultKey
    }

    public final Boolean isDataOnly() {
        return (getAllowFreeFormInput() || (getChoices() != null && getChoices().length != 0) || getAllowedDataTypes() == null || getAllowedDataTypes().isEmpty()) ? false : true
    }
}
