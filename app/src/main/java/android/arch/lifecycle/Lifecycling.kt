package android.arch.lifecycle

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import jadx.core.deobf.Deobfuscator
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class Lifecycling {
    private static val GENERATED_CALLBACK = 2
    private static val REFLECTIVE_CALLBACK = 1
    private static Map sCallbackCache = HashMap()
    private static Map sClassToAdapters = HashMap()

    private fun createGeneratedAdapter(Constructor constructor, Object obj) {
        try {
            return (GeneratedAdapter) constructor.newInstance(obj)
        } catch (IllegalAccessException e) {
            throw RuntimeException(e)
        } catch (InstantiationException e2) {
            throw RuntimeException(e2)
        } catch (InvocationTargetException e3) {
            throw RuntimeException(e3)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private fun generatedConstructor(Class cls) throws NoSuchMethodException, SecurityException {
        try {
            Package r1 = cls.getPackage()
            String canonicalName = cls.getCanonicalName()
            String name = r1 != null ? r1.getName() : ""
            if (!name.isEmpty()) {
                canonicalName = canonicalName.substring(name.length() + 1)
            }
            String adapterName = getAdapterName(canonicalName)
            if (!name.isEmpty()) {
                adapterName = name + Deobfuscator.CLASS_NAME_SEPARATOR + adapterName
            }
            Constructor declaredConstructor = Class.forName(adapterName).getDeclaredConstructor(cls)
            if (declaredConstructor.isAccessible()) {
                return declaredConstructor
            }
            declaredConstructor.setAccessible(true)
            return declaredConstructor
        } catch (ClassNotFoundException e) {
            return null
        } catch (NoSuchMethodException e2) {
            throw RuntimeException(e2)
        }
    }

    fun getAdapterName(String str) {
        return str.replace(Deobfuscator.CLASS_NAME_SEPARATOR, "_") + "_LifecycleAdapter"
    }

    @NonNull
    static GenericLifecycleObserver getCallback(Object obj) {
        Int i = 0
        if (obj is FullLifecycleObserver) {
            return FullLifecycleObserverAdapter((FullLifecycleObserver) obj)
        }
        if (obj is GenericLifecycleObserver) {
            return (GenericLifecycleObserver) obj
        }
        Class<?> cls = obj.getClass()
        if (getObserverConstructorType(cls) != 2) {
            return ReflectiveGenericLifecycleObserver(obj)
        }
        List list = (List) sClassToAdapters.get(cls)
        if (list.size() == 1) {
            return SingleGeneratedAdapterObserver(createGeneratedAdapter((Constructor) list.get(0), obj))
        }
        Array<GeneratedAdapter> generatedAdapterArr = new GeneratedAdapter[list.size()]
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return CompositeGeneratedAdaptersObserver(generatedAdapterArr)
            }
            generatedAdapterArr[i2] = createGeneratedAdapter((Constructor) list.get(i2), obj)
            i = i2 + 1
        }
    }

    private fun getObserverConstructorType(Class cls) throws NoSuchMethodException, SecurityException {
        if (sCallbackCache.containsKey(cls)) {
            return ((Integer) sCallbackCache.get(cls)).intValue()
        }
        Int iResolveObserverCallbackType = resolveObserverCallbackType(cls)
        sCallbackCache.put(cls, Integer.valueOf(iResolveObserverCallbackType))
        return iResolveObserverCallbackType
    }

    private fun isLifecycleParent(Class cls) {
        return cls != null && LifecycleObserver.class.isAssignableFrom(cls)
    }

    private fun resolveObserverCallbackType(Class cls) throws NoSuchMethodException, SecurityException {
        ArrayList arrayList
        if (cls.getCanonicalName() == null) {
            return 1
        }
        Constructor constructorGeneratedConstructor = generatedConstructor(cls)
        if (constructorGeneratedConstructor != null) {
            sClassToAdapters.put(cls, Collections.singletonList(constructorGeneratedConstructor))
            return 2
        }
        if (ClassesInfoCache.sInstance.hasLifecycleMethods(cls)) {
            return 1
        }
        Class superclass = cls.getSuperclass()
        ArrayList arrayList2 = null
        if (isLifecycleParent(superclass)) {
            if (getObserverConstructorType(superclass) == 1) {
                return 1
            }
            arrayList2 = ArrayList((Collection) sClassToAdapters.get(superclass))
        }
        Class<?>[] interfaces = cls.getInterfaces()
        Int length = interfaces.length
        Int i = 0
        while (i < length) {
            Class<?> cls2 = interfaces[i]
            if (!isLifecycleParent(cls2)) {
                arrayList = arrayList2
            } else {
                if (getObserverConstructorType(cls2) == 1) {
                    return 1
                }
                arrayList = arrayList2 == null ? ArrayList() : arrayList2
                arrayList.addAll((Collection) sClassToAdapters.get(cls2))
            }
            i++
            arrayList2 = arrayList
        }
        if (arrayList2 == null) {
            return 1
        }
        sClassToAdapters.put(cls, arrayList2)
        return 2
    }
}
