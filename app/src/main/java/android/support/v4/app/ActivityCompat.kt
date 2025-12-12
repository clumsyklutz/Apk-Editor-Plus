package android.support.v4.app

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v13.view.DragAndDropPermissionsCompat
import androidx.core.app.SharedElementCallback
import androidx.core.content.ContextCompat
import android.support.v4.media.MediaDescriptionCompat
import android.view.DragEvent
import android.view.View
import java.util.List
import java.util.Map

class ActivityCompat extends ContextCompat {
    private static PermissionCompatDelegate sDelegate

    public interface OnRequestPermissionsResultCallback {
        Unit onRequestPermissionsResult(Int i, @NonNull Array<String> strArr, @NonNull Array<Int> iArr)
    }

    public interface PermissionCompatDelegate {
        Boolean onActivityResult(@NonNull Activity activity, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) Int i, Int i2, @Nullable Intent intent)

        Boolean requestPermissions(@NonNull Activity activity, @NonNull Array<String> strArr, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) Int i)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface RequestPermissionsRequestCodeValidator {
        Unit validateRequestPermissionsRequestCode(Int i)
    }

    @RequiresApi(21)
    class SharedElementCallback21Impl extends android.app.SharedElementCallback {
        private final SharedElementCallback mCallback

        SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            this.mCallback = sharedElementCallback
        }

        @Override // android.app.SharedElementCallback
        fun onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            return this.mCallback.onCaptureSharedElementSnapshot(view, matrix, rectF)
        }

        @Override // android.app.SharedElementCallback
        fun onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.mCallback.onCreateSnapshotView(context, parcelable)
        }

        @Override // android.app.SharedElementCallback
        fun onMapSharedElements(List list, Map map) {
            this.mCallback.onMapSharedElements(list, map)
        }

        @Override // android.app.SharedElementCallback
        fun onRejectSharedElements(List list) {
            this.mCallback.onRejectSharedElements(list)
        }

        @Override // android.app.SharedElementCallback
        fun onSharedElementEnd(List list, List list2, List list3) {
            this.mCallback.onSharedElementEnd(list, list2, list3)
        }

        @Override // android.app.SharedElementCallback
        fun onSharedElementStart(List list, List list2, List list3) {
            this.mCallback.onSharedElementStart(list, list2, list3)
        }

        @Override // android.app.SharedElementCallback
        @RequiresApi(23)
        fun onSharedElementsArrived(List list, List list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
            this.mCallback.onSharedElementsArrived(list, list2, new SharedElementCallback.OnSharedElementsReadyListener() { // from class: android.support.v4.app.ActivityCompat.SharedElementCallback21Impl.1
                @Override // android.support.v4.app.SharedElementCallback.OnSharedElementsReadyListener
                fun onSharedElementsReady() {
                    onSharedElementsReadyListener.onSharedElementsReady()
                }
            })
        }
    }

    protected constructor() {
    }

    fun finishAffinity(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity()
        } else {
            activity.finish()
        }
    }

    fun finishAfterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAfterTransition()
        } else {
            activity.finish()
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getPermissionCompatDelegate() {
        return sDelegate
    }

    @Nullable
    fun getReferrer(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 22) {
            return activity.getReferrer()
        }
        Intent intent = activity.getIntent()
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER")
        if (uri != null) {
            return uri
        }
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME")
        if (stringExtra != null) {
            return Uri.parse(stringExtra)
        }
        return null
    }

    @Deprecated
    fun invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu()
        return true
    }

    fun postponeEnterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.postponeEnterTransition()
        }
    }

    @Nullable
    fun requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity, dragEvent)
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun requestPermissions(@NonNull final Activity activity, @NonNull final Array<String> strArr, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) final Int i) {
        if (sDelegate == null || !sDelegate.requestPermissions(activity, strArr, i)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (activity is RequestPermissionsRequestCodeValidator) {
                    ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(i)
                }
                activity.requestPermissions(strArr, i)
            } else if (activity is OnRequestPermissionsResultCallback) {
                Handler(Looper.getMainLooper()).post(Runnable() { // from class: android.support.v4.app.ActivityCompat.1
                    @Override // java.lang.Runnable
                    public final Unit run() {
                        Array<Int> iArr = new Int[strArr.length]
                        PackageManager packageManager = activity.getPackageManager()
                        String packageName = activity.getPackageName()
                        Int length = strArr.length
                        for (Int i2 = 0; i2 < length; i2++) {
                            iArr[i2] = packageManager.checkPermission(strArr[i2], packageName)
                        }
                        ((OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(i, strArr, iArr)
                    }
                })
            }
        }
    }

    @NonNull
    fun requireViewById(@NonNull Activity activity, @IdRes Int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            return activity.requireViewById(i)
        }
        View viewFindViewById = activity.findViewById(i)
        if (viewFindViewById == null) {
            throw IllegalArgumentException("ID does not reference a View inside this Activity")
        }
        return viewFindViewById
    }

    fun setEnterSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback sharedElementCallback) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.setEnterSharedElementCallback(sharedElementCallback != null ? SharedElementCallback21Impl(sharedElementCallback) : null)
        }
    }

    fun setExitSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback sharedElementCallback) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.setExitSharedElementCallback(sharedElementCallback != null ? SharedElementCallback21Impl(sharedElementCallback) : null)
        }
    }

    fun setPermissionCompatDelegate(@Nullable PermissionCompatDelegate permissionCompatDelegate) {
        sDelegate = permissionCompatDelegate
    }

    fun shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(str)
        }
        return false
    }

    fun startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, Int i, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, i, bundle)
        } else {
            activity.startActivityForResult(intent, i)
        }
    }

    fun startIntentSenderForResult(@NonNull Activity activity, @NonNull IntentSender intentSender, Int i, @Nullable Intent intent, Int i2, Int i3, Int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle)
        } else {
            activity.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4)
        }
    }

    fun startPostponedEnterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.startPostponedEnterTransition()
        }
    }
}
