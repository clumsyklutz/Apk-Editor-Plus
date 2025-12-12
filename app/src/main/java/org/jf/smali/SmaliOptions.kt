package org.jf.smali

class SmaliOptions {
    public Int apiLevel = 15
    public String outputDexFile = "out.dex"
    public Int jobs = Runtime.getRuntime().availableProcessors()
    public Boolean allowOdexOpcodes = false
    public Boolean verboseErrors = false
}
