package android.support.multidex

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import java.io.BufferedOutputStream
import java.io.Closeable
import java.io.File
import java.io.FileFilter
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

final class MultiDexExtractor {
    private static val BUFFER_SIZE = 16384
    private static val DEX_PREFIX = "classes"
    private static val DEX_SUFFIX = ".dex"
    private static val EXTRACTED_NAME_EXT = ".classes"
    private static val EXTRACTED_SUFFIX = ".zip"
    private static val KEY_CRC = "crc"
    private static val KEY_DEX_NUMBER = "dex.number"
    private static val KEY_TIME_STAMP = "timestamp"
    private static val MAX_EXTRACT_ATTEMPTS = 3
    private static val NO_VALUE = -1
    private static val PREFS_FILE = "multidex.version"
    private static val TAG = "MultiDex"
    private static Method sApplyMethod

    static {
        try {
            sApplyMethod = SharedPreferences.Editor.class.getMethod("apply", new Class[0])
        } catch (NoSuchMethodException e) {
            sApplyMethod = null
        }
    }

    MultiDexExtractor() {
    }

    private fun apply(SharedPreferences.Editor editor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor, new Object[0])
                return
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e2) {
            }
        }
        editor.commit()
    }

    private fun closeQuietly(Closeable closeable) throws IOException {
        try {
            closeable.close()
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e)
        }
    }

    private fun extract(ZipFile zipFile, ZipEntry zipEntry, File file, String str) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry)
        File fileCreateTempFile = File.createTempFile(str, EXTRACTED_SUFFIX, file.getParentFile())
        Log.i(TAG, "Extracting " + fileCreateTempFile.getPath())
        try {
            ZipOutputStream zipOutputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(fileCreateTempFile)))
            try {
                ZipEntry zipEntry2 = ZipEntry("classes.dex")
                zipEntry2.setTime(zipEntry.getTime())
                zipOutputStream.putNextEntry(zipEntry2)
                Array<Byte> bArr = new Byte[16384]
                for (Int i = inputStream.read(bArr); i != -1; i = inputStream.read(bArr)) {
                    zipOutputStream.write(bArr, 0, i)
                }
                zipOutputStream.closeEntry()
                zipOutputStream.close()
                Log.i(TAG, "Renaming to " + file.getPath())
                if (!fileCreateTempFile.renameTo(file)) {
                    throw IOException("Failed to rename \"" + fileCreateTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"")
                }
            } catch (Throwable th) {
                zipOutputStream.close()
                throw th
            }
        } finally {
            closeQuietly(inputStream)
            fileCreateTempFile.delete()
        }
    }

    private fun getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < 11 ? 0 : 4)
    }

    private fun getTimeStamp(File file) {
        Long jLastModified = file.lastModified()
        return jLastModified == -1 ? jLastModified - 1 : jLastModified
    }

    private fun getZipCrc(File file) throws IOException {
        Long zipCrc = ZipUtil.getZipCrc(file)
        return zipCrc == -1 ? zipCrc - 1 : zipCrc
    }

    private fun isModified(Context context, File file, Long j) {
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context)
        return (multiDexPreferences.getLong(KEY_TIME_STAMP, -1L) == getTimeStamp(file) && multiDexPreferences.getLong(KEY_CRC, -1L) == j) ? false : true
    }

    static List load(Context context, ApplicationInfo applicationInfo, File file, Boolean z) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        List listPerformExtractions
        Log.i(TAG, "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + z + ")")
        File file2 = File(applicationInfo.sourceDir)
        Long zipCrc = getZipCrc(file2)
        if (z || isModified(context, file2, zipCrc)) {
            Log.i(TAG, "Detected that extraction must be performed.")
            listPerformExtractions = performExtractions(file2, file)
            putStoredApkInfo(context, getTimeStamp(file2), zipCrc, listPerformExtractions.size() + 1)
        } else {
            try {
                listPerformExtractions = loadExistingExtractions(context, file2, file)
            } catch (IOException e) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e)
                listPerformExtractions = performExtractions(file2, file)
                putStoredApkInfo(context, getTimeStamp(file2), zipCrc, listPerformExtractions.size() + 1)
            }
        }
        Log.i(TAG, "load found " + listPerformExtractions.size() + " secondary dex files")
        return listPerformExtractions
    }

    private fun loadExistingExtractions(Context context, File file, File file2) throws IOException {
        Log.i(TAG, "loading existing secondary dex files")
        String str = file.getName() + EXTRACTED_NAME_EXT
        Int i = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1)
        ArrayList arrayList = ArrayList(i)
        for (Int i2 = 2; i2 <= i; i2++) {
            File file3 = File(file2, str + i2 + EXTRACTED_SUFFIX)
            if (!file3.isFile()) {
                throw IOException("Missing extracted secondary dex file '" + file3.getPath() + "'")
            }
            arrayList.add(file3)
            if (!verifyZipFile(file3)) {
                Log.i(TAG, "Invalid zip file: " + file3)
                throw IOException("Invalid ZIP file.")
            }
        }
        return arrayList
    }

    private fun mkdirChecked(File file) throws IOException {
        file.mkdir()
        if (file.isDirectory()) {
            return
        }
        File parentFile = file.getParentFile()
        if (parentFile == null) {
            Log.e(TAG, "Failed to create dir " + file.getPath() + ". Parent file is null.")
        } else {
            Log.e(TAG, "Failed to create dir " + file.getPath() + ". parent file is a dir " + parentFile.isDirectory() + ", a file " + parentFile.isFile() + ", exists " + parentFile.exists() + ", readable " + parentFile.canRead() + ", writable " + parentFile.canWrite())
        }
        throw IOException("Failed to create cache directory " + file.getPath())
    }

    private fun performExtractions(File file, File file2) throws IOException {
        String str = file.getName() + EXTRACTED_NAME_EXT
        prepareDexDir(file2, str)
        ArrayList arrayList = ArrayList()
        ZipFile zipFile = ZipFile(file)
        try {
            ZipEntry entry = zipFile.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX)
            Int i = 2
            while (entry != null) {
                File file3 = File(file2, str + i + EXTRACTED_SUFFIX)
                arrayList.add(file3)
                Log.i(TAG, "Extraction is needed for file " + file3)
                Boolean z = false
                Int i2 = 0
                while (i2 < 3 && !z) {
                    Int i3 = i2 + 1
                    extract(zipFile, entry, file3, str)
                    Boolean zVerifyZipFile = verifyZipFile(file3)
                    Log.i(TAG, "Extraction " + (zVerifyZipFile ? "success" : "failed") + " - length " + file3.getAbsolutePath() + ": " + file3.length())
                    if (!zVerifyZipFile) {
                        file3.delete()
                        if (file3.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + file3.getPath() + "'")
                            z = zVerifyZipFile
                            i2 = i3
                        }
                    }
                    z = zVerifyZipFile
                    i2 = i3
                }
                if (!z) {
                    throw IOException("Could not create zip file " + file3.getAbsolutePath() + " for secondary dex (" + i + ")")
                }
                Int i4 = i + 1
                entry = zipFile.getEntry(DEX_PREFIX + i4 + DEX_SUFFIX)
                i = i4
            }
            return arrayList
        } finally {
            try {
                zipFile.close()
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e)
            }
        }
    }

    private fun prepareDexDir(File file, final String str) throws IOException {
        mkdirChecked(file.getParentFile())
        mkdirChecked(file)
        Array<File> fileArrListFiles = file.listFiles(FileFilter() { // from class: android.support.multidex.MultiDexExtractor.1
            @Override // java.io.FileFilter
            public final Boolean accept(File file2) {
                return !file2.getName().startsWith(str)
            }
        })
        if (fileArrListFiles == null) {
            Log.w(TAG, "Failed to list secondary dex dir content (" + file.getPath() + ").")
            return
        }
        for (File file2 : fileArrListFiles) {
            Log.i(TAG, "Trying to delete old file " + file2.getPath() + " of size " + file2.length())
            if (file2.delete()) {
                Log.i(TAG, "Deleted old file " + file2.getPath())
            } else {
                Log.w(TAG, "Failed to delete old file " + file2.getPath())
            }
        }
    }

    private fun putStoredApkInfo(Context context, Long j, Long j2, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SharedPreferences.Editor editorEdit = getMultiDexPreferences(context).edit()
        editorEdit.putLong(KEY_TIME_STAMP, j)
        editorEdit.putLong(KEY_CRC, j2)
        editorEdit.putInt(KEY_DEX_NUMBER, i)
        apply(editorEdit)
    }

    static Boolean verifyZipFile(File file) throws IOException {
        try {
            try {
            } catch (ZipException e) {
                Log.w(TAG, "File " + file.getAbsolutePath() + " is not a valid zip file.", e)
            }
        } catch (IOException e2) {
            Log.w(TAG, "Got an IOException trying to open zip file: " + file.getAbsolutePath(), e2)
        }
        try {
            ZipFile(file).close()
            return true
        } catch (IOException e3) {
            Log.w(TAG, "Failed to close zip file: " + file.getAbsolutePath())
            return false
        }
    }
}
