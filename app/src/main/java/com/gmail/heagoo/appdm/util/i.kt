package com.gmail.heagoo.appdm.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.media.session.PlaybackStateCompat
import java.io.File

class i {

    /* renamed from: a, reason: collision with root package name */
    private static String f1416a = null

    @SuppressLint({"DefaultLocale"})
    fun a(Long j) {
        return j >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED ? String.format("%.2f M", Float.valueOf(((j * 1.0f) / 1024.0f) / 1024.0f)) : j >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? String.format("%.2f K", Float.valueOf((j * 1.0f) / 1024.0f)) : j + " B"
    }

    fun a(Context context) {
        c(context)
        String str = Environment.getExternalStorageDirectory().getPath() + "/" + f1416a + "/backups"
        File file = File(str)
        if (!file.exists()) {
            file.mkdirs()
        }
        return str
    }

    fun a() {
        return Environment.getExternalStorageState().equals("mounted")
    }

    fun b(Context context) {
        c(context)
        String str = Environment.getExternalStorageDirectory().getPath() + "/" + f1416a + "/temp"
        File file = File(str)
        if (!file.exists()) {
            file.mkdirs()
        }
        return str
    }

    private fun c(Context context) {
        if (f1416a != null) {
            return
        }
        try {
            f1416a = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("heagoo.sdcard_folder")
        } catch (PackageManager.NameNotFoundException e) {
        }
    }
}
