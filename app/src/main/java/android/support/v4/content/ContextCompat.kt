package android.support.v4.content

import android.accessibilityservice.AccessibilityService
import android.accounts.AccountManager
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.DownloadManager
import android.app.KeyguardManager
import android.app.NotificationManager
import android.app.SearchManager
import android.app.UiModeManager
import android.app.WallpaperManager
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.DropBoxManager
import android.os.PowerManager
import android.os.Process
import android.os.UserManager
import android.os.Vibrator
import android.os.storage.StorageManager
import android.print.PrintManager
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.core.app.NotificationCompat
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import java.io.File
import java.util.HashMap

class ContextCompat {
    private static val TAG = "ContextCompat"
    private static val sLock = Object()
    private static TypedValue sTempValue

    final class LegacyServiceMapHolder {
        static val SERVICES = HashMap()

        static {
            if (Build.VERSION.SDK_INT > 22) {
                SERVICES.put(SubscriptionManager.class, "telephony_subscription_service")
                SERVICES.put(UsageStatsManager.class, "usagestats")
            }
            if (Build.VERSION.SDK_INT > 21) {
                SERVICES.put(AppWidgetManager.class, "appwidget")
                SERVICES.put(BatteryManager.class, "batterymanager")
                SERVICES.put(CameraManager.class, "camera")
                SERVICES.put(JobScheduler.class, "jobscheduler")
                SERVICES.put(LauncherApps.class, "launcherapps")
                SERVICES.put(MediaProjectionManager.class, "media_projection")
                SERVICES.put(MediaSessionManager.class, "media_session")
                SERVICES.put(RestrictionsManager.class, "restrictions")
                SERVICES.put(TelecomManager.class, "telecom")
                SERVICES.put(TvInputManager.class, "tv_input")
            }
            if (Build.VERSION.SDK_INT > 19) {
                SERVICES.put(AppOpsManager.class, "appops")
                SERVICES.put(CaptioningManager.class, "captioning")
                SERVICES.put(ConsumerIrManager.class, "consumer_ir")
                SERVICES.put(PrintManager.class, "print")
            }
            if (Build.VERSION.SDK_INT > 18) {
                SERVICES.put(BluetoothManager.class, "bluetooth")
            }
            if (Build.VERSION.SDK_INT > 17) {
                SERVICES.put(DisplayManager.class, "display")
                SERVICES.put(UserManager.class, "user")
            }
            if (Build.VERSION.SDK_INT > 16) {
                SERVICES.put(InputManager.class, "input")
                SERVICES.put(MediaRouter.class, "media_router")
                SERVICES.put(NsdManager.class, "servicediscovery")
            }
            SERVICES.put(AccessibilityService.class, "accessibility")
            SERVICES.put(AccountManager.class, "account")
            SERVICES.put(ActivityManager.class, "activity")
            SERVICES.put(AlarmManager.class, NotificationCompat.CATEGORY_ALARM)
            SERVICES.put(AudioManager.class, "audio")
            SERVICES.put(ClipboardManager.class, "clipboard")
            SERVICES.put(ConnectivityManager.class, "connectivity")
            SERVICES.put(DevicePolicyManager.class, "device_policy")
            SERVICES.put(DownloadManager.class, "download")
            SERVICES.put(DropBoxManager.class, "dropbox")
            SERVICES.put(InputMethodManager.class, "input_method")
            SERVICES.put(KeyguardManager.class, "keyguard")
            SERVICES.put(LayoutInflater.class, "layout_inflater")
            SERVICES.put(LocationManager.class, "location")
            SERVICES.put(NfcManager.class, "nfc")
            SERVICES.put(NotificationManager.class, "notification")
            SERVICES.put(PowerManager.class, "power")
            SERVICES.put(SearchManager.class, "search")
            SERVICES.put(SensorManager.class, "sensor")
            SERVICES.put(StorageManager.class, "storage")
            SERVICES.put(TelephonyManager.class, "phone")
            SERVICES.put(TextServicesManager.class, "textservices")
            SERVICES.put(UiModeManager.class, "uimode")
            SERVICES.put(UsbManager.class, "usb")
            SERVICES.put(Vibrator.class, "vibrator")
            SERVICES.put(WallpaperManager.class, "wallpaper")
            SERVICES.put(WifiP2pManager.class, "wifip2p")
            SERVICES.put(WifiManager.class, "wifi")
            SERVICES.put(WindowManager.class, "window")
        }

        private constructor() {
        }
    }

    protected constructor() {
    }

    private fun buildPath(File file, String... strArr) {
        Int length = strArr.length
        Int i = 0
        File file2 = file
        while (i < length) {
            String str = strArr[i]
            i++
            file2 = file2 == null ? File(str) : str != null ? File(file2, str) : file2
        }
        return file2
    }

    fun checkSelfPermission(@NonNull Context context, @NonNull String str) {
        if (str == null) {
            throw IllegalArgumentException("permission is null")
        }
        return context.checkPermission(str, Process.myPid(), Process.myUid())
    }

    @Nullable
    fun createDeviceProtectedStorageContext(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext()
        }
        return null
    }

    private static synchronized File createFilesDir(File file) {
        if (!file.exists() && !file.mkdirs() && !file.exists()) {
            Log.w(TAG, "Unable to create files subdir " + file.getPath())
            file = null
        }
        return file
    }

    fun getCodeCacheDir(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 21 ? context.getCodeCacheDir() : createFilesDir(File(context.getApplicationInfo().dataDir, "code_cache"))
    }

    @ColorInt
    fun getColor(@NonNull Context context, @ColorRes Int i) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColor(i) : context.getResources().getColor(i)
    }

    @Nullable
    fun getColorStateList(@NonNull Context context, @ColorRes Int i) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColorStateList(i) : context.getResources().getColorStateList(i)
    }

    @Nullable
    fun getDataDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getDataDir()
        }
        String str = context.getApplicationInfo().dataDir
        if (str != null) {
            return File(str)
        }
        return null
    }

    @Nullable
    fun getDrawable(@NonNull Context context, @DrawableRes Int i) {
        Int i2
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(i)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return context.getResources().getDrawable(i)
        }
        synchronized (sLock) {
            if (sTempValue == null) {
                sTempValue = TypedValue()
            }
            context.getResources().getValue(i, sTempValue, true)
            i2 = sTempValue.resourceId
        }
        return context.getResources().getDrawable(i2)
    }

    @NonNull
    public static Array<File> getExternalCacheDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? context.getExternalCacheDirs() : new Array<File>{context.getExternalCacheDir()}
    }

    @NonNull
    public static Array<File> getExternalFilesDirs(@NonNull Context context, @Nullable String str) {
        return Build.VERSION.SDK_INT >= 19 ? context.getExternalFilesDirs(str) : new Array<File>{context.getExternalFilesDir(str)}
    }

    @Nullable
    fun getNoBackupFilesDir(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 21 ? context.getNoBackupFilesDir() : createFilesDir(File(context.getApplicationInfo().dataDir, "no_backup"))
    }

    @NonNull
    public static Array<File> getObbDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? context.getObbDirs() : new Array<File>{context.getObbDir()}
    }

    @Nullable
    fun getSystemService(@NonNull Context context, @NonNull Class cls) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getSystemService(cls)
        }
        String systemServiceName = getSystemServiceName(context, cls)
        if (systemServiceName != null) {
            return context.getSystemService(systemServiceName)
        }
        return null
    }

    @Nullable
    fun getSystemServiceName(@NonNull Context context, @NonNull Class cls) {
        return Build.VERSION.SDK_INT >= 23 ? context.getSystemServiceName(cls) : (String) LegacyServiceMapHolder.SERVICES.get(cls)
    }

    fun isDeviceProtectedStorage(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.isDeviceProtectedStorage()
        }
        return false
    }

    fun startActivities(@NonNull Context context, @NonNull Array<Intent> intentArr) {
        return startActivities(context, intentArr, null)
    }

    fun startActivities(@NonNull Context context, @NonNull Array<Intent> intentArr, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivities(intentArr, bundle)
            return true
        }
        context.startActivities(intentArr)
        return true
    }

    fun startActivity(@NonNull Context context, @NonNull Intent intent, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivity(intent, bundle)
        } else {
            context.startActivity(intent)
        }
    }

    fun startForegroundService(@NonNull Context context, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}
