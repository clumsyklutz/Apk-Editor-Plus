package android.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.support.annotation.Nullable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map

class ClassesInfoCache {
    private static val CALL_TYPE_NO_ARG = 0
    private static val CALL_TYPE_PROVIDER = 1
    private static val CALL_TYPE_PROVIDER_WITH_EVENT = 2
    static ClassesInfoCache sInstance = ClassesInfoCache()
    private val mCallbackMap = HashMap()
    private val mHasLifecycleMethods = HashMap()

    class CallbackInfo {
        val mEventToHandlers = HashMap()
        final Map mHandlerToEvent

        CallbackInfo(Map map) {
            this.mHandlerToEvent = map
            for (Map.Entry entry : map.entrySet()) {
                Lifecycle.Event event = (Lifecycle.Event) entry.getValue()
                List arrayList = (List) this.mEventToHandlers.get(event)
                if (arrayList == null) {
                    arrayList = ArrayList()
                    this.mEventToHandlers.put(event, arrayList)
                }
                arrayList.add(entry.getKey())
            }
        }

        private fun invokeMethodsForEvent(List list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (list != null) {
                for (Int size = list.size() - 1; size >= 0; size--) {
                    ((MethodReference) list.get(size)).invokeCallback(lifecycleOwner, event, obj)
                }
            }
        }

        Unit invokeCallbacks(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            invokeMethodsForEvent((List) this.mEventToHandlers.get(event), lifecycleOwner, event, obj)
            invokeMethodsForEvent((List) this.mEventToHandlers.get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj)
        }
    }

    class MethodReference {
        final Int mCallType
        final Method mMethod

        MethodReference(Int i, Method method) {
            this.mCallType = i
            this.mMethod = method
            this.mMethod.setAccessible(true)
        }

        fun equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false
            }
            MethodReference methodReference = (MethodReference) obj
            return this.mCallType == methodReference.mCallType && this.mMethod.getName().equals(methodReference.mMethod.getName())
        }

        fun hashCode() {
            return (this.mCallType * 31) + this.mMethod.getName().hashCode()
        }

        Unit invokeCallback(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                switch (this.mCallType) {
                    case 0:
                        this.mMethod.invoke(obj, new Object[0])
                        return
                    case 1:
                        this.mMethod.invoke(obj, lifecycleOwner)
                        return
                    case 2:
                        this.mMethod.invoke(obj, lifecycleOwner, event)
                        return
                    default:
                        return
                }
            } catch (IllegalAccessException e) {
                throw RuntimeException(e)
            } catch (InvocationTargetException e2) {
                throw RuntimeException("Failed to call observer method", e2.getCause())
            }
        }
    }

    ClassesInfoCache() {
    }

    private fun createInfo(Class cls, @Nullable Array<Method> methodArr) {
        Boolean z
        Int i
        CallbackInfo info
        Class superclass = cls.getSuperclass()
        HashMap map = HashMap()
        if (superclass != null && (info = getInfo(superclass)) != null) {
            map.putAll(info.mHandlerToEvent)
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Map.Entry entry : getInfo(cls2).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(map, (MethodReference) entry.getKey(), (Lifecycle.Event) entry.getValue(), cls)
            }
        }
        if (methodArr == null) {
            methodArr = getDeclaredMethods(cls)
        }
        Int length = methodArr.length
        Int i2 = 0
        Boolean z2 = false
        while (i2 < length) {
            Method method = methodArr[i2]
            OnLifecycleEvent onLifecycleEvent = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class)
            if (onLifecycleEvent != null) {
                Class<?>[] parameterTypes = method.getParameterTypes()
                if (parameterTypes.length <= 0) {
                    i = 0
                } else {
                    if (!parameterTypes[0].isAssignableFrom(LifecycleOwner.class)) {
                        throw IllegalArgumentException("invalid parameter type. Must be one and is LifecycleOwner")
                    }
                    i = 1
                }
                Lifecycle.Event eventValue = onLifecycleEvent.value()
                if (parameterTypes.length > 1) {
                    if (!parameterTypes[1].isAssignableFrom(Lifecycle.Event.class)) {
                        throw IllegalArgumentException("invalid parameter type. second arg must be an event")
                    }
                    if (eventValue != Lifecycle.Event.ON_ANY) {
                        throw IllegalArgumentException("Second arg is supported only for ON_ANY value")
                    }
                    i = 2
                }
                if (parameterTypes.length > 2) {
                    throw IllegalArgumentException("cannot have more than 2 params")
                }
                verifyAndPutHandler(map, MethodReference(i, method), eventValue, cls)
                z = true
            } else {
                z = z2
            }
            i2++
            z2 = z
        }
        CallbackInfo callbackInfo = CallbackInfo(map)
        this.mCallbackMap.put(cls, callbackInfo)
        this.mHasLifecycleMethods.put(cls, Boolean.valueOf(z2))
        return callbackInfo
    }

    private Array<Method> getDeclaredMethods(Class cls) {
        try {
            return cls.getDeclaredMethods()
        } catch (NoClassDefFoundError e) {
            throw IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e)
        }
    }

    private fun verifyAndPutHandler(Map map, MethodReference methodReference, Lifecycle.Event event, Class cls) {
        Lifecycle.Event event2 = (Lifecycle.Event) map.get(methodReference)
        if (event2 != null && event != event2) {
            throw IllegalArgumentException("Method " + methodReference.mMethod.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + event2 + ", new value " + event)
        }
        if (event2 == null) {
            map.put(methodReference, event)
        }
    }

    CallbackInfo getInfo(Class cls) {
        CallbackInfo callbackInfo = (CallbackInfo) this.mCallbackMap.get(cls)
        return callbackInfo != null ? callbackInfo : createInfo(cls, null)
    }

    Boolean hasLifecycleMethods(Class cls) {
        if (this.mHasLifecycleMethods.containsKey(cls)) {
            return ((Boolean) this.mHasLifecycleMethods.get(cls)).booleanValue()
        }
        Array<Method> declaredMethods = getDeclaredMethods(cls)
        for (Method method : declaredMethods) {
            if (((OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class)) != null) {
                createInfo(cls, declaredMethods)
                return true
            }
        }
        this.mHasLifecycleMethods.put(cls, false)
        return false
    }
}
