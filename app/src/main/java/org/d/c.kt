package org.d

import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.URL
import java.util.ArrayList
import java.util.Arrays
import java.util.Enumeration
import java.util.Iterator
import java.util.LinkedHashSet
import java.util.Set
import java.util.concurrent.LinkedBlockingQueue
import org.d.a.d
import org.d.b.e
import org.d.b.f
import org.d.b.g
import org.slf4j.impl.StaticLoggerBinder

class c {

    /* renamed from: a, reason: collision with root package name */
    private static volatile Int f1616a = 0

    /* renamed from: b, reason: collision with root package name */
    private static f f1617b = f()
    private static org.d.b.c c = new org.d.b.c()
    private static Boolean d = g.b("slf4j.detectLoggerNameMismatch")
    private static final Array<String> e = {"1.6", "1.7"}
    private static String f = "org/slf4j/impl/StaticLoggerBinder.class"

    private constructor() {
    }

    fun a(Class cls) {
        Class clsA
        b bVarA = a(cls.getName())
        if (d && (clsA = g.a()) != null) {
            if (!clsA.isAssignableFrom(cls)) {
                g.c(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", bVarA.a(), clsA.getName()))
                g.c("See http://www.slf4j.org/codes.html#loggerNameMismatch for an explanation")
            }
        }
        return bVarA
    }

    private fun a(String str) {
        return e().a(str)
    }

    private fun a() {
        synchronized (f1617b) {
            f1617b.c()
            for (e eVar : f1617b.a()) {
                eVar.a(a(eVar.a()))
            }
        }
    }

    private fun a(Throwable th) {
        f1616a = 2
        g.a("Failed to instantiate SLF4J LoggerFactory", th)
    }

    private fun a(Set set) {
        return set.size() > 1
    }

    private fun b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LinkedBlockingQueue linkedBlockingQueueB = f1617b.b()
        Int size = linkedBlockingQueueB.size()
        Int i = 0
        ArrayList<d> arrayList = ArrayList(128)
        while (linkedBlockingQueueB.drainTo(arrayList, 128) != 0) {
            Int i2 = i
            for (d dVar : arrayList) {
                if (dVar != null) {
                    e eVarA = dVar.a()
                    String strA = eVarA.a()
                    if (eVarA.c()) {
                        throw IllegalStateException("Delegate logger cannot be null at this state.")
                    }
                    if (!eVarA.d()) {
                        if (eVarA.b()) {
                            eVarA.a(dVar)
                        } else {
                            g.c(strA)
                        }
                    }
                }
                Int i3 = i2 + 1
                if (i2 == 0) {
                    if (dVar.a().b()) {
                        g.c("A number (" + size + ") of logging calls during the initialization phase have been intercepted and are")
                        g.c("now being replayed. These are subject to the filtering rules of the underlying logging system.")
                        g.c("See also http://www.slf4j.org/codes.html#replay")
                        i2 = i3
                    } else if (!dVar.a().d()) {
                        g.c("The following set of substitute loggers may have been accessed")
                        g.c("during the initialization phase. Logging calls during this")
                        g.c("phase were not honored. However, subsequent logging calls to these")
                        g.c("loggers will work as normally expected.")
                        g.c("See also http://www.slf4j.org/codes.html#substituteLogger")
                    }
                }
                i2 = i3
            }
            arrayList.clear()
            i = i2
        }
    }

    private static final Unit c() {
        Boolean z = false
        try {
            String str = StaticLoggerBinder.REQUESTED_API_VERSION
            Array<String> strArr = e
            for (Int i = 0; i < 2; i++) {
                if (str.startsWith(strArr[i])) {
                    z = true
                }
            }
            if (z) {
                return
            }
            g.c("The requested version " + str + " by your slf4j binding is not compatible with " + Arrays.asList(e).toString())
            g.c("See http://www.slf4j.org/codes.html#version_mismatch for further details.")
        } catch (NoSuchFieldError e2) {
        } catch (Throwable th) {
            g.a("Unexpected problem occured during version sanity check", th)
        }
    }

    private fun d() {
        LinkedHashSet linkedHashSet = LinkedHashSet()
        try {
            ClassLoader classLoader = c.class.getClassLoader()
            Enumeration<URL> systemResources = classLoader == null ? ClassLoader.getSystemResources(f) : classLoader.getResources(f)
            while (systemResources.hasMoreElements()) {
                linkedHashSet.add(systemResources.nextElement())
            }
        } catch (IOException e2) {
            g.a("Error getting resources from path", e2)
        }
        return linkedHashSet
    }

    private fun e() {
        Boolean zContains
        Boolean z = true
        if (f1616a == 0) {
            synchronized (c.class) {
                if (f1616a == 0) {
                    f1616a = 1
                    Set set = null
                    if ("http://www.android.com/" == 0) {
                        zContains = false
                    } else {
                        try {
                            try {
                                zContains = "http://www.android.com/".toLowerCase().contains("android")
                            } catch (NoClassDefFoundError e2) {
                                String message = e2.getMessage()
                                if (message == null || (!message.contains("org/slf4j/impl/StaticLoggerBinder") && !message.contains("org.slf4j.impl.StaticLoggerBinder"))) {
                                    z = false
                                }
                                if (!z) {
                                    a(e2)
                                    throw e2
                                }
                                f1616a = 4
                                g.c("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".")
                                g.c("Defaulting to no-operation (NOP) logger implementation")
                                g.c("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.")
                            } catch (NoSuchMethodError e3) {
                                String message2 = e3.getMessage()
                                if (message2 != null && message2.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
                                    f1616a = 2
                                    g.c("slf4j-api 1.6.x (or later) is incompatible with this binding.")
                                    g.c("Your binding is version 1.5.5 or earlier.")
                                    g.c("Upgrade your binding to version 1.6.x.")
                                }
                                throw e3
                            }
                        } catch (Exception e4) {
                            a(e4)
                            throw IllegalStateException("Unexpected initialization failure", e4)
                        }
                    }
                    if (!zContains) {
                        Set setD = d()
                        if (a(setD)) {
                            g.c("Class path contains multiple SLF4J bindings.")
                            Iterator it = setD.iterator()
                            while (it.hasNext()) {
                                g.c("Found binding in [" + ((URL) it.next()) + "]")
                            }
                            g.c("See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.")
                        }
                        set = setD
                    }
                    StaticLoggerBinder.getSingleton()
                    f1616a = 3
                    if (set != null && a(set)) {
                        g.c("Actual binding is of type [" + StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr() + "]")
                    }
                    a()
                    b()
                    f1617b.d()
                    if (f1616a == 3) {
                        c()
                    }
                }
            }
        }
        switch (f1616a) {
            case 1:
                return f1617b
            case 2:
                throw IllegalStateException("org.slf4j.LoggerFactory could not be successfully initialized. See also http://www.slf4j.org/codes.html#unsuccessfulInit")
            case 3:
                return StaticLoggerBinder.getSingleton().getLoggerFactory()
            case 4:
                return c
            default:
                throw IllegalStateException("Unreachable code")
        }
    }
}
