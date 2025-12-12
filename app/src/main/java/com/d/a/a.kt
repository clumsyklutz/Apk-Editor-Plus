package com.d.a

import java.lang.annotation.Annotation
import java.lang.reflect.Array
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI
import java.net.URL
import java.util.ArrayList
import java.util.GregorianCalendar
import java.util.HashMap
import java.util.HashSet
import java.util.IdentityHashMap
import java.util.Iterator
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Set
import java.util.TreeMap
import java.util.TreeSet
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

class a {

    /* renamed from: b, reason: collision with root package name */
    private val f718b = HashSet()
    private val c = HashSet()
    private val d = HashSet()
    private val e = HashMap()
    private val f = IdentityHashMap()
    private val g = ConcurrentHashMap()
    private com.a.b.a.e.j h = null
    private Boolean i = true
    private Boolean j = false
    private Boolean k = true
    private j l = b(this)
    private val m = ConcurrentHashMap()
    private Boolean n = true

    /* renamed from: a, reason: collision with root package name */
    private val f717a = o.a()

    constructor() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        d(String.class)
        d(Integer.class)
        d(Long.class)
        d(Boolean.class)
        d(Class.class)
        d(Float.class)
        d(Double.class)
        d(Character.class)
        d(Byte.class)
        d(Short.class)
        d(Void.class)
        d(BigDecimal.class)
        d(BigInteger.class)
        d(URI.class)
        d(URL.class)
        d(UUID.class)
        d(Pattern.class)
        c(TreeSet.class, HashSet.class, HashMap.class, TreeMap.class)
        this.e.put(GregorianCalendar.class, d())
        this.e.put(ArrayList.class, c())
        this.e.put(LinkedList.class, h())
        this.e.put(HashSet.class, g())
        this.e.put(HashMap.class, f())
        this.e.put(TreeMap.class, i())
        this.e.put(ConcurrentHashMap.class, e())
    }

    private fun a(Class cls) {
        List list = (List) this.g.get(cls)
        if (list != null) {
            return list
        }
        LinkedList linkedList = LinkedList()
        a(linkedList, cls.getDeclaredFields())
        Class superclass = cls
        while (true) {
            superclass = superclass.getSuperclass()
            if (superclass == Object.class || superclass == null) {
                break
            }
            a(linkedList, superclass.getDeclaredFields())
        }
        this.g.putIfAbsent(cls, linkedList)
        return linkedList
    }

    private fun a(List list, Array<Field> fieldArr) {
        for (Field field : fieldArr) {
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }
            list.add(field)
        }
    }

    private fun c(Class... clsArr) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= 4) {
                return
            }
            Class cls = clsArr[i2]
            for (Field field : a(cls)) {
                if (Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                    try {
                        Field declaredField = cls.getDeclaredField(field.getName())
                        declaredField.setAccessible(true)
                        this.f.put(declaredField.get(null), true)
                    } catch (IllegalAccessException e) {
                        throw RuntimeException(e)
                    } catch (IllegalArgumentException e2) {
                        throw RuntimeException(e2)
                    } catch (NoSuchFieldException e3) {
                        throw RuntimeException(e3)
                    } catch (SecurityException e4) {
                        throw RuntimeException(e4)
                    }
                }
            }
            i = i2 + 1
        }
    }

    private fun d(Class... clsArr) {
        for (Int i = 0; i <= 0; i++) {
            this.f718b.add(clsArr[0])
        }
    }

    public final Object a(Object obj) {
        if (obj == null) {
            return null
        }
        if (!this.i) {
            return obj
        }
        try {
            return a(obj, IdentityHashMap(16))
        } catch (IllegalAccessException e) {
            throw new com.a.b.b.b("error during cloning of " + obj, e)
        }
    }

    protected final Object a(Object obj, Map map) throws IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Boolean zBooleanValue
        if (obj != null && obj != this) {
            if (this.f.containsKey(obj) || (obj is Enum)) {
                return obj
            }
            Class<?> cls = obj.getClass()
            if (this.d.contains(cls)) {
                return null
            }
            if (this.f718b.contains(cls)) {
                return obj
            }
            Iterator it = this.c.iterator()
            while (it.hasNext()) {
                if (((Class) it.next()).isAssignableFrom(cls)) {
                    return obj
                }
            }
            Boolean bool = (Boolean) this.m.get(cls)
            if (bool != null) {
                zBooleanValue = bool.booleanValue()
            } else {
                Array<Annotation> declaredAnnotations = cls.getDeclaredAnnotations()
                Int length = declaredAnnotations.length
                Int i = 0
                while (true) {
                    if (i >= length) {
                        loop4: for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                            for (Annotation annotation : superclass.getDeclaredAnnotations()) {
                                if (annotation.annotationType() == n.class && ((n) annotation).a()) {
                                    this.m.put(cls, Boolean.TRUE)
                                    zBooleanValue = true
                                    break loop4
                                }
                            }
                        }
                        this.m.put(cls, Boolean.FALSE)
                        zBooleanValue = false
                    } else {
                        if (declaredAnnotations[i].annotationType() == n.class) {
                            this.m.put(cls, Boolean.TRUE)
                            zBooleanValue = true
                            break
                        }
                        i++
                    }
                }
            }
            if (zBooleanValue) {
                return obj
            }
            if ((obj is l) && ((l) obj).a()) {
                return obj
            }
            Object obj2 = map != null ? map.get(obj) : null
            if (obj2 != null) {
                return obj2
            }
            k kVar = (k) this.e.get(obj.getClass())
            Object objA = kVar != null ? kVar.a(obj, this.l, map) : null
            if (objA != null) {
                if (map != null) {
                    map.put(obj, objA)
                }
                return objA
            }
            if (cls.isArray()) {
                Class<?> cls2 = obj.getClass()
                Int length2 = Array.getLength(obj)
                Object objNewInstance = Array.newInstance(cls2.getComponentType(), length2)
                if (map != null) {
                    map.put(obj, objNewInstance)
                }
                for (Int i2 = 0; i2 < length2; i2++) {
                    Object objA2 = Array.get(obj, i2)
                    if (map != null) {
                        objA2 = a(objA2, map)
                    }
                    Array.set(objNewInstance, i2, objA2)
                }
                return objNewInstance
            }
            Object objA3 = this.f717a.a(cls)
            if (map != null) {
                map.put(obj, objA3)
            }
            for (Field field : a((Class) cls)) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object objA4 = field.get(obj)
                    Boolean z = (this.k || !(this.k || field.isSynthetic())) && (this.n || !(this.n || "this$0".equals(field.getName())))
                    if (map != null && z) {
                        objA4 = a(objA4, map)
                    }
                    field.set(objA3, objA4)
                }
            }
            return objA3
        }
        return null
    }

    public final Unit a(Class... clsArr) {
        for (Int i = 0; i < 4; i++) {
            this.f718b.add(clsArr[i])
        }
    }

    public final Unit b(Class... clsArr) {
        for (Int i = 0; i <= 0; i++) {
            this.c.add(clsArr[0])
        }
    }
}
