package com.a.b.b.a

import java.io.File
import java.io.IOException
import java.util.Arrays
import java.util.Set

class c {
    private static val INCREMENTAL_OPTION = "--incremental"
    private static val INPUT_LIST_OPTION = "--input-list"
    private static val MAIN_DEX_LIST_OPTION = "--main-dex-list"
    private static val MINIMAL_MAIN_DEX_OPTION = "--minimal-main-dex"
    private static val MULTI_DEX_OPTION = "--multi-dex"
    private static val NUM_THREADS_OPTION = "--num-threads"
    public com.a.b.c.a.a cfOptions$4cfd32cd
    public com.a.b.c.a dexOptions
    public Array<String> fileNames
    public Boolean statistics
    public Boolean debug = false
    public Boolean verbose = false
    public Boolean verboseDump = false
    public Boolean coreLibrary = false
    public String methodToDump = null
    public Int dumpWidth = 0
    public String outName = null
    public String humanOutName = null
    public Boolean strictNameCheck = true
    public Boolean emptyOk = false
    public Boolean jarOutput = false
    public Boolean keepClassesInJar = false
    public Int positionInfo = 2
    public Boolean localInfo = true
    public Boolean incremental = false
    public Boolean forceJumbo = false
    public Boolean optimize = true
    public String optimizeListFile = null
    public String dontOptimizeListFile = null
    public Int numThreads = 1
    public Boolean multiDex = false
    public String mainDexListFile = null
    public Boolean minimalMainDex = false
    private Set inputList = null
    private Int maxNumberOfIdxPerDex = 65536

    /* JADX INFO: Access modifiers changed from: private */
    fun makeOptionsObjects() {
        this.cfOptions$4cfd32cd = new com.a.b.c.a.a()
        this.cfOptions$4cfd32cd.f264a = this.positionInfo
        this.cfOptions$4cfd32cd.f265b = this.localInfo
        this.cfOptions$4cfd32cd.c = this.strictNameCheck
        this.cfOptions$4cfd32cd.d = this.optimize
        this.cfOptions$4cfd32cd.e = this.optimizeListFile
        this.cfOptions$4cfd32cd.f = this.dontOptimizeListFile
        this.cfOptions$4cfd32cd.g = this.statistics
        this.cfOptions$4cfd32cd.h = com.a.b.b.a.f254b
        this.dexOptions = new com.a.b.c.a()
        this.dexOptions.c = this.forceJumbo
    }

    fun parse(Array<String> strArr) {
        d dVar = d(strArr)
        Boolean z = false
        Boolean z2 = false
        while (dVar.c()) {
            if (dVar.a("--debug")) {
                this.debug = true
            } else if (dVar.a("--verbose")) {
                this.verbose = true
            } else if (dVar.a("--verbose-dump")) {
                this.verboseDump = true
            } else if (dVar.a("--no-files")) {
                this.emptyOk = true
            } else if (dVar.a("--no-optimize")) {
                this.optimize = false
            } else if (dVar.a("--no-strict")) {
                this.strictNameCheck = false
            } else if (dVar.a("--core-library")) {
                this.coreLibrary = true
            } else if (dVar.a("--statistics")) {
                this.statistics = true
            } else if (dVar.a("--optimize-list=")) {
                if (this.dontOptimizeListFile != null) {
                    System.err.println("--optimize-list and --no-optimize-list are incompatible.")
                    throw new com.a.b.b.b()
                }
                this.optimize = true
                this.optimizeListFile = dVar.b()
            } else if (dVar.a("--no-optimize-list=")) {
                if (this.dontOptimizeListFile != null) {
                    System.err.println("--optimize-list and --no-optimize-list are incompatible.")
                    throw new com.a.b.b.b()
                }
                this.optimize = true
                this.dontOptimizeListFile = dVar.b()
            } else if (dVar.a("--keep-classes")) {
                this.keepClassesInJar = true
            } else if (dVar.a("--output=")) {
                this.outName = dVar.b()
                if (File(this.outName).isDirectory()) {
                    this.jarOutput = false
                    z2 = true
                } else if (com.gmail.heagoo.a.c.a.g(this.outName)) {
                    this.jarOutput = true
                } else {
                    if (!this.outName.endsWith(".dex") && !this.outName.equals("-")) {
                        System.err.println("unknown output extension: " + this.outName)
                        throw new com.a.b.b.b()
                    }
                    this.jarOutput = false
                    z = true
                }
            } else if (dVar.a("--dump-to=")) {
                this.humanOutName = dVar.b()
            } else if (dVar.a("--dump-width=")) {
                this.dumpWidth = Integer.parseInt(dVar.b())
            } else if (dVar.a("--dump-method=")) {
                this.methodToDump = dVar.b()
                this.jarOutput = false
            } else if (dVar.a("--positions=")) {
                String strIntern = dVar.b().intern()
                if (strIntern == "none") {
                    this.positionInfo = 1
                } else if (strIntern == "important") {
                    this.positionInfo = 3
                } else {
                    if (strIntern != "lines") {
                        System.err.println("unknown positions option: " + strIntern)
                        throw new com.a.b.b.b()
                    }
                    this.positionInfo = 2
                }
            } else if (dVar.a("--no-locals")) {
                this.localInfo = false
            } else if (dVar.a("--num-threads=")) {
                this.numThreads = Integer.parseInt(dVar.b())
            } else if (dVar.a(INCREMENTAL_OPTION)) {
                this.incremental = true
            } else if (dVar.a("--force-jumbo")) {
                this.forceJumbo = true
            } else if (dVar.a(MULTI_DEX_OPTION)) {
                this.multiDex = true
            } else if (dVar.a("--main-dex-list=")) {
                this.mainDexListFile = dVar.b()
            } else if (dVar.a(MINIMAL_MAIN_DEX_OPTION)) {
                this.minimalMainDex = true
            } else if (dVar.a("--set-max-idx-number=")) {
                this.maxNumberOfIdxPerDex = Integer.parseInt(dVar.b())
            } else {
                if (!dVar.a("--input-list=")) {
                    System.err.println("unknown option: " + dVar.a())
                    throw new com.a.b.b.b()
                }
                File file = File(dVar.b())
                try {
                    this.inputList = a.c(file.getAbsolutePath())
                } catch (IOException e) {
                    System.err.println("Unable to read input list file: " + file.getName())
                    throw new com.a.b.b.b()
                }
            }
        }
        this.fileNames = dVar.d()
        if (this.inputList != null && !this.inputList.isEmpty()) {
            this.inputList.addAll(Arrays.asList(this.fileNames))
            this.fileNames = (Array<String>) this.inputList.toArray(new String[this.inputList.size()])
        }
        if (this.fileNames.length == 0) {
            if (!this.emptyOk) {
                System.err.println("no input files specified")
                throw new com.a.b.b.b()
            }
        } else if (this.emptyOk) {
            System.out.println("ignoring input files")
        }
        if (this.humanOutName == null && this.methodToDump != null) {
            this.humanOutName = "-"
        }
        if (this.mainDexListFile != null && !this.multiDex) {
            System.err.println("--main-dex-list is only supported in combination with --multi-dex")
            throw new com.a.b.b.b()
        }
        if (this.minimalMainDex && (this.mainDexListFile == null || !this.multiDex)) {
            System.err.println("--minimal-main-dex is only supported in combination with --multi-dex and --main-dex-list")
            throw new com.a.b.b.b()
        }
        if (this.multiDex && this.numThreads != 1) {
            System.out.println("--num-threads is ignored when used with --multi-dex")
            this.numThreads = 1
        }
        if (this.multiDex && this.incremental) {
            System.err.println("--incremental is not supported with --multi-dex")
            throw new com.a.b.b.b()
        }
        if (this.multiDex && z) {
            System.err.println("Unsupported output \"" + this.outName + "\". --multi-dex supports only archive or directory output")
            throw new com.a.b.b.b()
        }
        if (z2 && !this.multiDex) {
            this.outName = File(this.outName, "classes.dex").getPath()
        }
        makeOptionsObjects()
    }
}
