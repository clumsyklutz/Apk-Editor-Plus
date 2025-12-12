package android.support.multidex

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import dalvik.system.DexFile
import jadx.core.deobf.Deobfuscator
import java.io.File
import java.io.IOException
import java.lang.reflect.Array
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.ListIterator
import java.util.Set
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipFile

class MultiDex {
    private static val MAX_SUPPORTED_SDK_VERSION = 20
    private static val MIN_SDK_VERSION = 4
    static val TAG = "MultiDex"
    private static val VM_WITH_MULTIDEX_VERSION_MAJOR = 2
    private static val VM_WITH_MULTIDEX_VERSION_MINOR = 1
    private static val OLD_SECONDARY_FOLDER_NAME = "secondary-dexes"
    private static val SECONDARY_FOLDER_NAME = "code_cache" + File.separator + OLD_SECONDARY_FOLDER_NAME
    private static val installedApk = HashSet()
    private static val IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"))

    final class V14 {
        private constructor() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun install(ClassLoader classLoader, List list, File file) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
            Object obj = MultiDex.findField(classLoader, "pathList").get(classLoader)
            MultiDex.expandFieldArray(obj, "dexElements", makeDexElements(obj, ArrayList(list), file))
        }

        private static Array<Object> makeDexElements(Object obj, ArrayList arrayList, File file) {
            return (Array<Object>) MultiDex.findMethod(obj, "makeDexElements", ArrayList.class, File.class).invoke(obj, arrayList, file)
        }
    }

    final class V19 {
        private constructor() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun install(ClassLoader classLoader, List list, File file) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
            Array<IOException> iOExceptionArr
            Object obj = MultiDex.findField(classLoader, "pathList").get(classLoader)
            ArrayList arrayList = ArrayList()
            MultiDex.expandFieldArray(obj, "dexElements", makeDexElements(obj, ArrayList(list), file, arrayList))
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator()
                while (it.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", (IOException) it.next())
                }
                Field fieldFindField = MultiDex.findField(classLoader, "dexElementsSuppressedExceptions")
                Array<IOException> iOExceptionArr2 = (Array<IOException>) fieldFindField.get(classLoader)
                if (iOExceptionArr2 == null) {
                    iOExceptionArr = (Array<IOException>) arrayList.toArray(new IOException[arrayList.size()])
                } else {
                    Array<IOException> iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length]
                    arrayList.toArray(iOExceptionArr3)
                    System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length)
                    iOExceptionArr = iOExceptionArr3
                }
                fieldFindField.set(classLoader, iOExceptionArr)
            }
        }

        private static Array<Object> makeDexElements(Object obj, ArrayList arrayList, File file, ArrayList arrayList2) {
            return (Array<Object>) MultiDex.findMethod(obj, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(obj, arrayList, file, arrayList2)
        }
    }

    final class V4 {
        private constructor() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun install(ClassLoader classLoader, List list) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
            Int size = list.size()
            Field fieldFindField = MultiDex.findField(classLoader, "path")
            StringBuilder sb = StringBuilder((String) fieldFindField.get(classLoader))
            Array<String> strArr = new String[size]
            Array<File> fileArr = new File[size]
            Array<ZipFile> zipFileArr = new ZipFile[size]
            Array<DexFile> dexFileArr = new DexFile[size]
            ListIterator listIterator = list.listIterator()
            while (listIterator.hasNext()) {
                File file = (File) listIterator.next()
                String absolutePath = file.getAbsolutePath()
                sb.append(':').append(absolutePath)
                Int iPreviousIndex = listIterator.previousIndex()
                strArr[iPreviousIndex] = absolutePath
                fileArr[iPreviousIndex] = file
                zipFileArr[iPreviousIndex] = ZipFile(file)
                dexFileArr[iPreviousIndex] = DexFile.loadDex(absolutePath, absolutePath + ".dex", 0)
            }
            fieldFindField.set(classLoader, sb.toString())
            MultiDex.expandFieldArray(classLoader, "mPaths", strArr)
            MultiDex.expandFieldArray(classLoader, "mFiles", fileArr)
            MultiDex.expandFieldArray(classLoader, "mZips", zipFileArr)
            MultiDex.expandFieldArray(classLoader, "mDexs", dexFileArr)
        }
    }

    private constructor() {
    }

    private fun checkValidZipFiles(List list) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            if (!MultiDexExtractor.verifyZipFile((File) it.next())) {
                return false
            }
        }
        return true
    }

    private fun clearOldDexDir(Context context) {
        File file = File(context.getFilesDir(), OLD_SECONDARY_FOLDER_NAME)
        if (file.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + file.getPath() + ").")
            Array<File> fileArrListFiles = file.listFiles()
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
            if (file.delete()) {
                Log.i(TAG, "Deleted old secondary dex dir " + file.getPath())
            } else {
                Log.w(TAG, "Failed to delete secondary dex dir " + file.getPath())
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun expandFieldArray(Object obj, String str, Array<Object> objArr) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Field fieldFindField = findField(obj, str)
        Array<Object> objArr2 = (Array<Object>) fieldFindField.get(obj)
        Array<Object> objArr3 = (Array<Object>) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length)
        System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length)
        System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length)
        fieldFindField.set(obj, objArr3)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun findField(Object obj, String str) throws NoSuchFieldException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Field declaredField = superclass.getDeclaredField(str)
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true)
                }
                return declaredField
            } catch (NoSuchFieldException e) {
            }
        }
        throw NoSuchFieldException("Field " + str + " not found in " + obj.getClass())
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun findMethod(Object obj, String str, Class... clsArr) throws NoSuchMethodException, SecurityException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Method declaredMethod = superclass.getDeclaredMethod(str, clsArr)
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true)
                }
                return declaredMethod
            } catch (NoSuchMethodException e) {
            }
        }
        throw NoSuchMethodException("Method " + str + " with parameters " + Arrays.asList(clsArr) + " not found in " + obj.getClass())
    }

    private fun getApplicationInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager()
            String packageName = context.getPackageName()
            if (packageManager == null || packageName == null) {
                return null
            }
            return packageManager.getApplicationInfo(packageName, 128)
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e)
            return null
        }
    }

    fun install(Context context) {
        Log.i(TAG, "install")
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.")
            return
        }
        if (Build.VERSION.SDK_INT < 4) {
            throw RuntimeException("Multi dex installation failed. SDK " + Build.VERSION.SDK_INT + " is unsupported. Min SDK version is 4" + Deobfuscator.CLASS_NAME_SEPARATOR)
        }
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context)
            if (applicationInfo != null) {
                synchronized (installedApk) {
                    String str = applicationInfo.sourceDir
                    if (installedApk.contains(str)) {
                        return
                    }
                    installedApk.add(str)
                    if (Build.VERSION.SDK_INT > 20) {
                        Log.w(TAG, "MultiDex is not guaranteed to work in SDK version " + Build.VERSION.SDK_INT + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"")
                    }
                    try {
                        ClassLoader classLoader = context.getClassLoader()
                        if (classLoader == null) {
                            Log.e(TAG, "Context class loader is null. Must be running in test mode. Skip patching.")
                            return
                        }
                        try {
                            clearOldDexDir(context)
                        } catch (Throwable th) {
                            Log.w(TAG, "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", th)
                        }
                        File file = File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME)
                        List listLoad = MultiDexExtractor.load(context, applicationInfo, file, false)
                        if (checkValidZipFiles(listLoad)) {
                            installSecondaryDexes(classLoader, file, listLoad)
                        } else {
                            Log.w(TAG, "Files were not valid zip files.  Forcing a reload.")
                            List listLoad2 = MultiDexExtractor.load(context, applicationInfo, file, true)
                            if (!checkValidZipFiles(listLoad2)) {
                                throw RuntimeException("Zip files were not valid.")
                            }
                            installSecondaryDexes(classLoader, file, listLoad2)
                        }
                        Log.i(TAG, "install done")
                    } catch (RuntimeException e) {
                        Log.w(TAG, "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e)
                    }
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "Multidex installation failure", e2)
            throw RuntimeException("Multi dex installation failed (" + e2.getMessage() + ").")
        }
    }

    private fun installSecondaryDexes(ClassLoader classLoader, File file, List list) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (list.isEmpty()) {
            return
        }
        if (Build.VERSION.SDK_INT >= 19) {
            V19.install(classLoader, list, file)
        } else if (Build.VERSION.SDK_INT >= 14) {
            V14.install(classLoader, list, file)
        } else {
            V4.install(classLoader, list)
        }
    }

    static Boolean isVMMultidexCapable(String str) throws NumberFormatException {
        Boolean z = false
        if (str != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(str)
            if (matcher.matches()) {
                try {
                    Int i = Integer.parseInt(matcher.group(1))
                    Int i2 = Integer.parseInt(matcher.group(2))
                    if (i > 2 || (i == 2 && i2 > 0)) {
                        z = true
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i(TAG, "VM with version " + str + (z ? " has multidex support" : " does not have multidex support"))
        return z
    }
}
