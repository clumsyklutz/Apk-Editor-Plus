package com.gmail.heagoo.common

import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class l {
    private fun a(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            try {
                outputStream.close()
            } catch (IOException e) {
            }
        }
    }

    fun a(Bitmap bitmap, String str) throws Throwable {
        FileOutputStream fileOutputStream
        Boolean z
        try {
            try {
                fileOutputStream = FileOutputStream(str)
            } catch (Exception e) {
                e = e
                fileOutputStream = null
            } catch (Throwable th) {
                th = th
                fileOutputStream = null
                com.gmail.heagoo.a.c.a.a(fileOutputStream)
                throw th
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                com.gmail.heagoo.a.c.a.a(fileOutputStream)
                z = true
            } catch (Exception e2) {
                e = e2
                e.printStackTrace()
                com.gmail.heagoo.a.c.a.a(fileOutputStream)
                z = false
                return z
            }
            return z
        } catch (Throwable th2) {
            th = th2
            com.gmail.heagoo.a.c.a.a(fileOutputStream)
            throw th
        }
    }

    public final Unit a(Bitmap bitmap, Int i, Int i2, String str) throws Throwable {
        FileOutputStream fileOutputStream
        OutputStream outputStream = null
        Int width = bitmap.getWidth()
        Int height = bitmap.getHeight()
        Matrix matrix = Matrix()
        matrix.postScale(i / width, i2 / height)
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
        try {
            try {
                fileOutputStream = FileOutputStream(str)
            } catch (Exception e) {
                e = e
                fileOutputStream = null
            } catch (Throwable th) {
                th = th
                outputStream = null
                a(outputStream)
                throw th
            }
            try {
                if (str.endsWith(".png")) {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream)
                } else {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
                }
                a(fileOutputStream)
            } catch (Exception e2) {
                e = e2
                e.printStackTrace()
                a(fileOutputStream)
            }
        } catch (Throwable th2) {
            th = th2
            a(outputStream)
            throw th
        }
    }
}
