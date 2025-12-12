package android.support.v4.content.pm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.pm.ShortcutManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import android.text.TextUtils
import java.util.Iterator

class ShortcutManagerCompat {

    @VisibleForTesting
    static val ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"

    @VisibleForTesting
    static val INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT"

    private constructor() {
    }

    @NonNull
    fun createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent intentCreateShortcutResultIntent = Build.VERSION.SDK_INT >= 26 ? ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo()) : null
        if (intentCreateShortcutResultIntent == null) {
            intentCreateShortcutResultIntent = Intent()
        }
        return shortcutInfoCompat.addToIntent(intentCreateShortcutResultIntent)
    }

    fun isRequestPinShortcutSupported(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported()
        }
        if (ContextCompat.checkSelfPermission(context, INSTALL_SHORTCUT_PERMISSION) != 0) {
            return false
        }
        Iterator<ResolveInfo> it = context.getPackageManager().queryBroadcastReceivers(Intent(ACTION_INSTALL_SHORTCUT), 0).iterator()
        while (it.hasNext()) {
            String str = it.next().activityInfo.permission
            if (TextUtils.isEmpty(str) || INSTALL_SHORTCUT_PERMISSION.equals(str)) {
                return true
            }
        }
        return false
    }

    fun requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable final IntentSender intentSender) throws PackageManager.NameNotFoundException {
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender)
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false
        }
        Intent intentAddToIntent = shortcutInfoCompat.addToIntent(Intent(ACTION_INSTALL_SHORTCUT))
        if (intentSender == null) {
            context.sendBroadcast(intentAddToIntent)
            return true
        }
        context.sendOrderedBroadcast(intentAddToIntent, null, BroadcastReceiver() { // from class: android.support.v4.content.pm.ShortcutManagerCompat.1
            @Override // android.content.BroadcastReceiver
            public final Unit onReceive(Context context2, Intent intent) throws IntentSender.SendIntentException {
                try {
                    intentSender.sendIntent(context2, 0, null, null, null)
                } catch (IntentSender.SendIntentException e) {
                }
            }
        }, null, -1, null, null)
        return true
    }
}
