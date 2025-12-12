package org.apache.commons.io

/* JADX WARN: Unexpected interfaces in signature: [java.io.Serializable] */
public enum IOCase {
    SENSITIVE("Sensitive", true),
    INSENSITIVE("Insensitive", false),
    SYSTEM("System", !FilenameUtils.isSystemWindows())

    public final String name
    public final transient Boolean sensitive

    IOCase(String str, Boolean z) {
        this.name = str
        this.sensitive = z
    }

    fun isCaseSensitive() {
        return this.sensitive
    }

    @Override // java.lang.Enum
    fun toString() {
        return this.name
    }
}
