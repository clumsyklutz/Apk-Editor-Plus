package android.support.v7.app

import android.content.res.Resources
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.util.Log
import android.util.LongSparseArray
import java.lang.reflect.Field
import java.util.Map

class ResourcesFlusher {
    private static val TAG = "ResourcesFlusher"
    private static Field sDrawableCacheField
    private static Boolean sDrawableCacheFieldFetched
    private static Field sResourcesImplField
    private static Boolean sResourcesImplFieldFetched
    private static Class sThemedResourceCacheClazz
    private static Boolean sThemedResourceCacheClazzFetched
    private static Field sThemedResourceCache_mUnthemedEntriesField
    private static Boolean sThemedResourceCache_mUnthemedEntriesFieldFetched

    private constructor() {
    }

    static Unit flush(@NonNull Resources resources) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 28) {
            return
        }
        if (Build.VERSION.SDK_INT >= 24) {
            flushNougats(resources)
        } else if (Build.VERSION.SDK_INT >= 23) {
            flushMarshmallows(resources)
        } else if (Build.VERSION.SDK_INT >= 21) {
            flushLollipops(resources)
        }
    }

    @RequiresApi(21)
    private fun flushLollipops(@NonNull Resources resources) throws NoSuchFieldException {
        Map map
        if (!sDrawableCacheFieldFetched) {
            try {
                Field declaredField = Resources.class.getDeclaredField("mDrawableCache")
                sDrawableCacheField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "Could not retrieve Resources#mDrawableCache field", e)
            }
            sDrawableCacheFieldFetched = true
        }
        if (sDrawableCacheField != null) {
            try {
                map = (Map) sDrawableCacheField.get(resources)
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e2)
                map = null
            }
            if (map != null) {
                map.clear()
            }
        }
    }

    @RequiresApi(23)
    private fun flushMarshmallows(@NonNull Resources resources) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Object obj
        if (!sDrawableCacheFieldFetched) {
            try {
                Field declaredField = Resources.class.getDeclaredField("mDrawableCache")
                sDrawableCacheField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "Could not retrieve Resources#mDrawableCache field", e)
            }
            sDrawableCacheFieldFetched = true
        }
        if (sDrawableCacheField != null) {
            try {
                obj = sDrawableCacheField.get(resources)
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e2)
            }
        } else {
            obj = null
        }
        if (obj == null) {
            return
        }
        flushThemedResourcesCache(obj)
    }

    @RequiresApi(24)
    private fun flushNougats(@NonNull Resources resources) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Object obj
        Object obj2
        if (!sResourcesImplFieldFetched) {
            try {
                Field declaredField = Resources.class.getDeclaredField("mResourcesImpl")
                sResourcesImplField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "Could not retrieve Resources#mResourcesImpl field", e)
            }
            sResourcesImplFieldFetched = true
        }
        if (sResourcesImplField == null) {
            return
        }
        try {
            obj = sResourcesImplField.get(resources)
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "Could not retrieve value from Resources#mResourcesImpl", e2)
            obj = null
        }
        if (obj != null) {
            if (!sDrawableCacheFieldFetched) {
                try {
                    Field declaredField2 = obj.getClass().getDeclaredField("mDrawableCache")
                    sDrawableCacheField = declaredField2
                    declaredField2.setAccessible(true)
                } catch (NoSuchFieldException e3) {
                    Log.e(TAG, "Could not retrieve ResourcesImpl#mDrawableCache field", e3)
                }
                sDrawableCacheFieldFetched = true
            }
            if (sDrawableCacheField != null) {
                try {
                    obj2 = sDrawableCacheField.get(obj)
                } catch (IllegalAccessException e4) {
                    Log.e(TAG, "Could not retrieve value from ResourcesImpl#mDrawableCache", e4)
                }
            } else {
                obj2 = null
            }
            if (obj2 != null) {
                flushThemedResourcesCache(obj2)
            }
        }
    }

    @RequiresApi(16)
    private fun flushThemedResourcesCache(@NonNull Object obj) throws NoSuchFieldException {
        LongSparseArray longSparseArray
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache")
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Could not find ThemedResourceCache class", e)
            }
            sThemedResourceCacheClazzFetched = true
        }
        if (sThemedResourceCacheClazz == null) {
            return
        }
        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                Field declaredField = sThemedResourceCacheClazz.getDeclaredField("mUnthemedEntries")
                sThemedResourceCache_mUnthemedEntriesField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e2) {
                Log.e(TAG, "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e2)
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true
        }
        if (sThemedResourceCache_mUnthemedEntriesField != null) {
            try {
                longSparseArray = (LongSparseArray) sThemedResourceCache_mUnthemedEntriesField.get(obj)
            } catch (IllegalAccessException e3) {
                Log.e(TAG, "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e3)
                longSparseArray = null
            }
            if (longSparseArray != null) {
                longSparseArray.clear()
            }
        }
    }
}
