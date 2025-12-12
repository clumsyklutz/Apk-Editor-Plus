package com.a.b.b.a

import com.a.a.s
import com.a.b.a.d.k
import com.a.b.a.d.p
import com.a.b.c.c.j
import com.a.b.c.c.r
import com.a.b.c.c.v
import com.a.b.f.c.y
import jadx.core.codegen.CodeWriter
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Writer
import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.TreeMap
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

class a {

    /* renamed from: a, reason: collision with root package name */
    private static final Attributes.Name f255a

    /* renamed from: b, reason: collision with root package name */
    private static final Array<String> f256b
    private static AtomicInteger c
    private static c d
    private static r e
    private static TreeMap f
    private static final List g
    private static ExecutorService h
    private static List i
    private static volatile Boolean j
    private static Long k
    private static Set l
    private static List m
    private static OutputStreamWriter n
    private static /* synthetic */ Boolean o

    static {
        o = !a.class.desiredAssertionStatus()
        f255a = new Attributes.Name("Created-By")
        f256b = new Array<String>{"accessibility", "crypto", "imageio", "management", "naming", "net", "print", "rmi", "security", "sip", "sound", "sql", "swing", "transaction", "xml"}
        c = AtomicInteger(0)
        g = ArrayList()
        k = 0L
        l = null
        m = ArrayList()
        n = null
    }

    private constructor() {
    }

    fun a(c cVar) throws IOException {
        OutputStream outputStream
        File file
        Array<Byte> bArrH
        Int iE
        Array<Byte> byteArray = null
        c.set(0)
        g.clear()
        d = cVar
        cVar.makeOptionsObjects()
        if (d.humanOutName != null) {
            OutputStream outputStreamE = e(d.humanOutName)
            n = OutputStreamWriter(outputStreamE)
            outputStream = outputStreamE
        } else {
            outputStream = null
        }
        try {
            if (d.multiDex) {
                iE = e()
            } else {
                if (!d.incremental) {
                    file = null
                } else if (d.outName == null) {
                    System.err.println("error: no incremental output name specified")
                    iE = -1
                } else {
                    File file2 = File(d.outName)
                    if (file2.exists()) {
                        k = file2.lastModified()
                    }
                    file = file2
                }
                if (!f()) {
                    iE = 1
                } else if (!d.incremental || j) {
                    if (e.a() && d.humanOutName == null) {
                        bArrH = null
                    } else {
                        bArrH = h()
                        if (bArrH == null) {
                            iE = 2
                        }
                    }
                    if (d.incremental) {
                        com.a.a.i iVar = bArrH != null ? new com.a.a.i(bArrH) : null
                        com.a.a.i iVar2 = file.exists() ? new com.a.a.i(file) : null
                        if (iVar != null || iVar2 != null) {
                            com.a.a.i iVarA = iVar == null ? iVar2 : iVar2 == null ? iVar : new com.a.b.e.b(iVar, iVar2, com.a.b.e.a.f454a).a()
                            ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
                            iVarA.a(byteArrayOutputStream)
                            byteArray = byteArrayOutputStream.toByteArray()
                        }
                    } else {
                        byteArray = bArrH
                    }
                    Array<Byte> bArrA = a(byteArray)
                    if (d.jarOutput) {
                        e = null
                        if (bArrA != null) {
                            f.put("classes.dex", bArrA)
                        }
                        if (!d(d.outName)) {
                            iE = 3
                        }
                    } else if (bArrA != null && d.outName != null) {
                        OutputStream outputStreamE2 = e(d.outName)
                        outputStreamE2.write(bArrA)
                        a(outputStreamE2)
                    }
                    iE = 0
                } else {
                    iE = 0
                }
            }
            return iE
        } finally {
            a(outputStream)
        }
    }

    fun a() {
        return d.multiDex ? "The list of classes given in --main-dex-list is too big and does not fit in the main dex." : "You may try using --multi-dex option."
    }

    private fun a(Int i2) {
        return i2 == 0 ? "classes.dex" : "classes" + (i2 + 1) + ".dex"
    }

    private fun a(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            return
        }
        outputStream.flush()
        if (outputStream != System.out) {
            outputStream.close()
        }
    }

    private fun a(String str, com.a.b.a.d.i iVar) {
        Byte b2 = 0
        com.a.b.a.d.d dVar = new com.a.b.a.d.d(str, false, iVar, b())
        if (d.numThreads > 1) {
            i.add(h.submit(h(dVar, b2)))
        } else if (dVar.a()) {
            j = true
        }
    }

    private fun a(String str, Array<Byte> bArr) {
        Boolean z
        if (!d.coreLibrary) {
            if (str.startsWith("java/")) {
                z = true
            } else if (str.startsWith("javax/")) {
                Int iIndexOf = str.indexOf(47, 6)
                if (iIndexOf == -1) {
                    z = true
                } else {
                    z = Arrays.binarySearch(f256b, str.substring(6, iIndexOf)) >= 0
                }
            } else {
                z = false
            }
            if (z) {
                com.a.b.b.a.f254b.println("\ntrouble processing \"" + str + "\":\n\nIll-advised or mistaken usage of a core class (java.* or javax.*)\nwhen not building a core library.\n\nThis is often due to inadvertently including a core library file\nin your application's project, when using an IDE (such as\nEclipse). If you are sure you're not intentionally defining a\ncore class, then this is the most likely explanation of what's\ngoing on.\n\nHowever, you might actually be trying to define a class in a core\nnamespace, the source of which you may have taken, for example,\nfrom a non-Android virtual machine project. This will most\nassuredly not work. At a minimum, it jeopardizes the\ncompatibility of your app with future versions of the platform.\nIt is also often of questionable legality.\n\nIf you really intend to build a core library -- which is only\nappropriate as part of creating a full virtual machine\ndistribution, as opposed to compiling an application -- then use\nthe \"--core-library\" option to suppress this error message.\n\nIf you go ahead and use \"--core-library\" but are in fact\nbuilding an application, then be forewarned that your application\nwill still fail to build or run, at some point. Please be\nprepared for angry customers who find, for example, that your\napplication ceases to function once they upgrade their operating\nsystem. You will be to blame for this problem.\n\nIf you are legitimately using some code that happens to be in a\ncore package, then the easiest safe alternative you have is to\nrepackage that code. That is, move the classes in question into\nyour own package namespace. This means that they will never be in\nconflict with core system classes. JarJar is a tool that may help\nyou in this endeavor. If you find that you cannot do this, then\nthat is an indication that the path you are on will ultimately\nlead to pain, suffering, grief, and lamentation.\n")
                c.incrementAndGet()
                throw i((Byte) 0)
            }
        }
        k kVar = k(bArr, str, d.cfOptions$4cfd32cd.c)
        kVar.a((com.a.b.a.d.b) p.f248a)
        kVar.c()
        Int size = e.n().a().size()
        Int size2 = e.m().a().size()
        Int iA = kVar.g().a()
        Int iD_ = size + iA + kVar.j().d_() + 2
        Int iD_2 = iA + size2 + kVar.i().d_() + 9
        if (d.multiDex && e.i().a().size() > 0 && (iD_ > d.maxNumberOfIdxPerDex || iD_2 > d.maxNumberOfIdxPerDex)) {
            r rVar = e
            g()
            if (!o && (rVar.n().a().size() > size + 2 || rVar.m().a().size() > size2 + 9)) {
                throw AssertionError()
            }
        }
        try {
            j jVarA = com.a.b.c.a.a.a(kVar, bArr, d.cfOptions$4cfd32cd, d.dexOptions, e)
            synchronized (e) {
                e.a(jVarA)
            }
            return true
        } catch (com.a.b.a.e.i e2) {
            com.a.b.b.a.f254b.println("\ntrouble processing:")
            if (d.debug) {
                e2.printStackTrace(com.a.b.b.a.f254b)
            } else {
                e2.a(com.a.b.b.a.f254b)
            }
            c.incrementAndGet()
            return false
        }
    }

    static /* synthetic */ Boolean a(Boolean z) {
        j = true
        return true
    }

    private static Array<Byte> a(Array<Byte> bArr) {
        for (Array<Byte> bArr2 : g) {
            bArr = bArr == null ? bArr2 : new com.a.b.e.b(new com.a.a.i(bArr), new com.a.a.i(bArr2), com.a.b.e.a.f455b).a().d()
        }
        return bArr
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(String str, Long j2, Array<Byte> bArr) {
        Boolean zEndsWith = str.endsWith(".class")
        Boolean zEquals = str.equals("classes.dex")
        Boolean z = f != null
        if (!zEndsWith && !zEquals && !z) {
            if (!d.verbose) {
                return false
            }
            com.a.b.b.a.f253a.println("ignored resource " + str)
            return false
        }
        if (d.verbose) {
            com.a.b.b.a.f253a.println("processing " + str + "...")
        }
        String strF = f(str)
        if (zEndsWith) {
            if (z && d.keepClassesInJar) {
                synchronized (f) {
                    f.put(strF, bArr)
                }
            }
            if (j2 < k) {
                return true
            }
            return a(strF, bArr)
        }
        if (zEquals) {
            synchronized (g) {
                g.add(bArr)
            }
            return true
        }
        synchronized (f) {
            f.put(strF, bArr)
        }
        return true
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(String str) throws Throwable {
        BufferedReader bufferedReader
        HashSet hashSet = HashSet()
        try {
            bufferedReader = BufferedReader(FileReader(str))
            while (true) {
                try {
                    String line = bufferedReader.readLine()
                    if (line == null) {
                        bufferedReader.close()
                        return hashSet
                    }
                    hashSet.add(f(line))
                } catch (Throwable th) {
                    th = th
                    if (bufferedReader != null) {
                        bufferedReader.close()
                    }
                    throw th
                }
            }
        } catch (Throwable th2) {
            th = th2
            bufferedReader = null
        }
    }

    private fun d(String str) {
        Manifest manifest
        Attributes attributes
        try {
            Array<Byte> bArr = (Array<Byte>) f.get("META-INF/MANIFEST.MF")
            if (bArr == null) {
                Manifest manifest2 = Manifest()
                Attributes mainAttributes = manifest2.getMainAttributes()
                mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0")
                manifest = manifest2
                attributes = mainAttributes
            } else {
                Manifest manifest3 = Manifest(ByteArrayInputStream(bArr))
                Attributes mainAttributes2 = manifest3.getMainAttributes()
                f.remove("META-INF/MANIFEST.MF")
                manifest = manifest3
                attributes = mainAttributes2
            }
            String value = attributes.getValue(f255a)
            attributes.put(f255a, (value == null ? "" : value + " + ") + "dx 1.10")
            attributes.putValue("Dex-Location", "classes.dex")
            OutputStream outputStreamE = e(str)
            JarOutputStream jarOutputStream = JarOutputStream(outputStreamE, manifest)
            try {
                for (Map.Entry entry : f.entrySet()) {
                    String str2 = (String) entry.getKey()
                    Array<Byte> bArr2 = (Array<Byte>) entry.getValue()
                    JarEntry jarEntry = JarEntry(str2)
                    Int length = bArr2.length
                    if (d.verbose) {
                        com.a.b.b.a.f253a.println("writing " + str2 + "; size " + length + "...")
                    }
                    jarEntry.setSize(length)
                    jarOutputStream.putNextEntry(jarEntry)
                    jarOutputStream.write(bArr2)
                    jarOutputStream.closeEntry()
                }
                return true
            } finally {
                jarOutputStream.finish()
                jarOutputStream.flush()
                a(outputStreamE)
            }
        } catch (Exception e2) {
            if (d.debug) {
                com.a.b.b.a.f254b.println("\ntrouble writing output:")
                e2.printStackTrace(com.a.b.b.a.f254b)
            } else {
                com.a.b.b.a.f254b.println("\ntrouble writing output: " + e2.getMessage())
            }
            return false
        }
    }

    private fun e() throws IOException {
        if (!o && d.incremental) {
            throw AssertionError()
        }
        if (!o && d.numThreads != 1) {
            throw AssertionError()
        }
        if (d.mainDexListFile != null) {
            l = c(d.mainDexListFile)
        }
        if (!f()) {
            return 1
        }
        if (!g.isEmpty()) {
            throw s("Library dex files are not supported in multi-dex mode")
        }
        if (e != null) {
            m.add(h())
            e = null
        }
        if (d.jarOutput) {
            for (Int i2 = 0; i2 < m.size(); i2++) {
                f.put(a(i2), m.get(i2))
            }
            return !d(d.outName) ? 3 : 0
        }
        if (d.outName == null) {
            return 0
        }
        File file = File(d.outName)
        if (!o && !file.isDirectory()) {
            throw AssertionError()
        }
        for (Int i3 = 0; i3 < m.size(); i3++) {
            FileOutputStream fileOutputStream = FileOutputStream(File(file, a(i3)))
            try {
                fileOutputStream.write((Array<Byte>) m.get(i3))
                a(fileOutputStream)
            } catch (Throwable th) {
                a(fileOutputStream)
                throw th
            }
        }
        return 0
    }

    private fun e(String str) {
        return (str.equals("-") || str.startsWith("-.")) ? System.out : FileOutputStream(str)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun f(String str) {
        if (File.separatorChar == '\\') {
            str = str.replace('\\', '/')
        }
        Int iLastIndexOf = str.lastIndexOf("/./")
        return iLastIndexOf != -1 ? str.substring(iLastIndexOf + 3) : str.startsWith("./") ? str.substring(2) : str
    }

    private fun f() throws ExecutionException, InterruptedException {
        g()
        if (d.jarOutput) {
            f = TreeMap()
        }
        j = false
        Array<String> strArr = d.fileNames
        if (d.numThreads > 1) {
            h = Executors.newFixedThreadPool(d.numThreads)
            i = ArrayList()
        }
        try {
            if (d.mainDexListFile != null) {
                com.a.b.a.d.i fVar = d.strictNameCheck ? f((Byte) 0) : e()
                for (String str : strArr) {
                    a(str, fVar)
                }
                if (m.size() > 0) {
                    throw s("Too many classes in --main-dex-list, main dex capacity exceeded")
                }
                if (d.minimalMainDex) {
                    g()
                }
                for (String str2 : strArr) {
                    a(str2, g(fVar, (Byte) 0))
                }
            } else {
                for (String str3 : strArr) {
                    a(str3, com.a.b.a.d.d.f236a)
                }
            }
        } catch (i e2) {
        }
        if (d.numThreads > 1) {
            try {
                h.shutdown()
                if (!h.awaitTermination(600L, TimeUnit.SECONDS)) {
                    throw RuntimeException("Timed out waiting for threads.")
                }
                try {
                    Iterator it = i.iterator()
                    while (it.hasNext()) {
                        ((Future) it.next()).get()
                    }
                } catch (InterruptedException e3) {
                    throw AssertionError(e3)
                } catch (ExecutionException e4) {
                    if (e4.getCause() instanceof Error) {
                        throw ((Error) e4.getCause())
                    }
                    throw AssertionError(e4.getCause())
                }
            } catch (InterruptedException e5) {
                h.shutdownNow()
                throw RuntimeException("A thread has been interrupted.")
            }
        }
        Int i2 = c.get()
        if (i2 != 0) {
            com.a.b.b.a.f254b.println(i2 + " error" + (i2 == 1 ? "" : "s") + "; aborting")
            return false
        }
        if (d.incremental && !j) {
            return true
        }
        if (!j && !d.emptyOk) {
            com.a.b.b.a.f254b.println("no classfiles specified")
            return false
        }
        if (d.optimize && d.statistics) {
            com.a.b.c.a.c.a(com.a.b.b.a.f253a)
        }
        return true
    }

    private fun g() {
        if (e != null) {
            m.add(h())
        }
        e = r(d.dexOptions)
        if (d.dumpWidth != 0) {
            e.a(d.dumpWidth)
        }
    }

    private static Array<Byte> h() throws IOException {
        Array<Byte> bArrA
        try {
            try {
                if (d.methodToDump != null) {
                    e.a((Writer) null, false)
                    r rVar = e
                    String str = d.methodToDump
                    OutputStreamWriter outputStreamWriter = n
                    Boolean zEndsWith = str.endsWith("*")
                    Int iLastIndexOf = str.lastIndexOf(46)
                    if (iLastIndexOf <= 0 || iLastIndexOf == str.length() - 1) {
                        com.a.b.b.a.f254b.println("bogus fully-qualified method name: " + str)
                        bArrA = null
                    } else {
                        String strReplace = str.substring(0, iLastIndexOf).replace('.', '/')
                        String strSubstring = str.substring(iLastIndexOf + 1)
                        j jVarA = rVar.a(strReplace)
                        if (jVarA == null) {
                            com.a.b.b.a.f254b.println("no such class: " + strReplace)
                            bArrA = null
                        } else {
                            String strSubstring2 = zEndsWith ? strSubstring.substring(0, strSubstring.length() - 1) : strSubstring
                            ArrayList arrayListG = jVarA.g()
                            TreeMap treeMap = TreeMap()
                            Iterator it = arrayListG.iterator()
                            while (it.hasNext()) {
                                v vVar = (v) it.next()
                                String strJ = vVar.a().j()
                                if ((zEndsWith && strJ.startsWith(strSubstring2)) || (!zEndsWith && strJ.equals(strSubstring2))) {
                                    treeMap.put(vVar.c().l(), vVar)
                                }
                            }
                            if (treeMap.size() == 0) {
                                com.a.b.b.a.f254b.println("no such method: " + str)
                                bArrA = null
                            } else {
                                PrintWriter printWriter = PrintWriter(outputStreamWriter)
                                for (v vVar2 : treeMap.values()) {
                                    vVar2.a(printWriter, d.verboseDump)
                                    y yVarF = jVarA.f()
                                    if (yVarF != null) {
                                        printWriter.println("  source file: " + yVarF.i())
                                    }
                                    com.a.b.f.a.c cVarA = jVarA.a(vVar2.c())
                                    com.a.b.f.a.d dVarB = jVarA.b(vVar2.c())
                                    if (cVarA != null) {
                                        printWriter.println("  method annotations:")
                                        Iterator it2 = cVarA.d().iterator()
                                        while (it2.hasNext()) {
                                            printWriter.println(CodeWriter.INDENT + ((com.a.b.f.a.a) it2.next()))
                                        }
                                    }
                                    if (dVarB != null) {
                                        printWriter.println("  parameter annotations:")
                                        Int iD_ = dVarB.d_()
                                        for (Int i2 = 0; i2 < iD_; i2++) {
                                            printWriter.println("    parameter " + i2)
                                            Iterator it3 = dVarB.a(i2).d().iterator()
                                            while (it3.hasNext()) {
                                                printWriter.println("      " + ((com.a.b.f.a.a) it3.next()))
                                            }
                                        }
                                    }
                                }
                                printWriter.flush()
                                bArrA = null
                            }
                        }
                    }
                } else {
                    bArrA = e.a(n, d.verboseDump)
                }
                if (d.statistics) {
                    com.a.b.b.a.f253a.println(e.r().a())
                }
            } finally {
                if (n != null) {
                    n.flush()
                }
            }
        } catch (Exception e2) {
            if (d.debug) {
                com.a.b.b.a.f254b.println("\ntrouble writing output:")
                e2.printStackTrace(com.a.b.b.a.f254b)
            } else {
                com.a.b.b.a.f254b.println("\ntrouble writing output: " + e2.getMessage())
            }
            return null
        }
    }
}
