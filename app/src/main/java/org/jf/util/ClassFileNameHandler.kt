package org.jf.util

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import jadx.core.deobf.Deobfuscator
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.IntBuffer
import java.util.Collection
import java.util.HashMap
import java.util.Map
import java.util.regex.Pattern

class ClassFileNameHandler {
    public static Pattern reservedFileNameRegex = Pattern.compile("^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(\\..*)?$", 2)
    public String fileExtension
    public Int forcedCaseSensitivity = -1
    public Boolean modifyWindowsReservedFilenames = isWindows()
    public DirectoryEntry top

    class DirectoryEntry extends FileSystemEntry {
        public Int caseSensitivity
        public final Multimap<String, FileSystemEntry> children
        public File file
        public final Map<String, Integer> lastSuffixMap
        public final Map<String, FileSystemEntry> physicalToEntry

        constructor(File file) {
            super(null, file.getName())
            this.file = null
            this.caseSensitivity = ClassFileNameHandler.this.forcedCaseSensitivity
            this.children = ArrayListMultimap.create()
            this.physicalToEntry = HashMap()
            this.lastSuffixMap = HashMap()
            this.file = file
            this.physicalName = file.getName()
        }

        constructor(DirectoryEntry directoryEntry, String str) {
            super(directoryEntry, str)
            this.file = null
            this.caseSensitivity = ClassFileNameHandler.this.forcedCaseSensitivity
            this.children = ArrayListMultimap.create()
            this.physicalToEntry = HashMap()
            this.lastSuffixMap = HashMap()
        }

        public synchronized FileSystemEntry addChild(FileSystemEntry fileSystemEntry) throws IOException {
            String normalizedName = fileSystemEntry.getNormalizedName(false)
            Collection<FileSystemEntry> collection = this.children.get(normalizedName)
            if (fileSystemEntry is DirectoryEntry) {
                for (FileSystemEntry fileSystemEntry2 : collection) {
                    if (fileSystemEntry2.logicalName.equals(fileSystemEntry.logicalName)) {
                        return fileSystemEntry2
                    }
                }
            }
            Integer num = this.lastSuffixMap.get(normalizedName)
            if (num == null) {
                num = -1
            }
            Int iIntValue = num.intValue()
            do {
                iIntValue++
            } while (this.physicalToEntry.containsKey(File(this.file, fileSystemEntry.getPhysicalNameWithSuffix(iIntValue)).getCanonicalFile().getName()));
            fileSystemEntry.setSuffix(iIntValue)
            this.lastSuffixMap.put(normalizedName, Integer.valueOf(iIntValue))
            this.physicalToEntry.put(fileSystemEntry.getPhysicalName(), fileSystemEntry)
            collection.add(fileSystemEntry)
            return fileSystemEntry
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        fun createIfNeeded() throws IOException {
            String physicalName = getPhysicalName()
            if (this.parent == null || physicalName == null) {
                return
            }
            File canonicalFile = File(this.parent.file, physicalName).getCanonicalFile()
            this.file = canonicalFile
            canonicalFile.mkdirs()
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        fun getPhysicalNameWithSuffix(Int i) {
            if (i <= 0) {
                return getNormalizedName(true)
            }
            return getNormalizedName(true) + Deobfuscator.CLASS_NAME_SEPARATOR + i
        }

        fun isCaseSensitive() {
            File file
            if (getPhysicalName() == null || (file = this.file) == null) {
                throw IllegalStateException("Must call setSuffix() first")
            }
            Int i = this.caseSensitivity
            Int i2 = 1
            if (i != -1) {
                return i == 1
            }
            if (file.exists() && file.isFile() && !file.delete()) {
                throw ExceptionWithContext("Can't delete %s to make it into a directory", file.getAbsolutePath())
            }
            if (!file.exists() && !file.mkdirs()) {
                throw ExceptionWithContext("Couldn't create directory %s", file.getAbsolutePath())
            }
            try {
                Boolean zTestCaseSensitivity = PathUtil.testCaseSensitivity(file)
                if (!zTestCaseSensitivity) {
                    i2 = 0
                }
                this.caseSensitivity = i2
                return zTestCaseSensitivity
            } catch (IOException unused) {
                return false
            }
        }
    }

    class FileEntry extends FileSystemEntry {
        constructor(ClassFileNameHandler classFileNameHandler, DirectoryEntry directoryEntry, String str) {
            super(directoryEntry, str)
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        fun createIfNeeded() throws IOException {
            String physicalName = getPhysicalName()
            if (this.parent == null || physicalName == null) {
                return
            }
            File(this.parent.file, physicalName).getCanonicalFile().createNewFile()
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        fun getPhysicalNameWithSuffix(Int i) {
            if (i <= 0) {
                return getNormalizedName(true)
            }
            return ClassFileNameHandler.addSuffixBeforeExtension(getNormalizedName(true), '.' + Integer.toString(i))
        }
    }

    abstract class FileSystemEntry {
        public final String logicalName
        public final DirectoryEntry parent
        public String physicalName

        constructor(DirectoryEntry directoryEntry, String str) {
            this.physicalName = null
            this.parent = directoryEntry
            this.logicalName = str
        }

        public abstract Unit createIfNeeded() throws IOException

        fun getNormalizedName(Boolean z) {
            DirectoryEntry directoryEntry
            String strAddSuffixBeforeExtension = this.logicalName
            if (!z && (directoryEntry = this.parent) != null && !directoryEntry.isCaseSensitive()) {
                strAddSuffixBeforeExtension = strAddSuffixBeforeExtension.toLowerCase()
            }
            if (ClassFileNameHandler.this.modifyWindowsReservedFilenames && ClassFileNameHandler.isReservedFileName(strAddSuffixBeforeExtension)) {
                strAddSuffixBeforeExtension = ClassFileNameHandler.addSuffixBeforeExtension(strAddSuffixBeforeExtension, "#")
            }
            Int iUtf8Length = ClassFileNameHandler.utf8Length(strAddSuffixBeforeExtension)
            return iUtf8Length > ClassFileNameHandler.this.getMaxFilenameLength() ? ClassFileNameHandler.shortenPathComponent(strAddSuffixBeforeExtension, iUtf8Length - ClassFileNameHandler.this.getMaxFilenameLength()) : strAddSuffixBeforeExtension
        }

        fun getPhysicalName() {
            return this.physicalName
        }

        public abstract String getPhysicalNameWithSuffix(Int i)

        fun setSuffix(Int i) throws IOException {
            if (i < 0 || i > 99999) {
                throw IllegalArgumentException("suffix must be in [0, 100000)")
            }
            if (this.physicalName != null) {
                throw IllegalStateException("The suffix can only be set once")
            }
            this.physicalName = File(this.parent.file, getPhysicalNameWithSuffix(i)).getCanonicalFile().getName()
            createIfNeeded()
        }
    }

    constructor(File file, String str) {
        this.top = DirectoryEntry(file)
        this.fileExtension = str
    }

    fun addSuffixBeforeExtension(String str, String str2) {
        Int iLastIndexOf = str.lastIndexOf(46)
        StringBuilder sb = StringBuilder(str.length() + str2.length() + 1)
        if (iLastIndexOf < 0) {
            sb.append(str)
            sb.append(str2)
        } else {
            sb.append(str.subSequence(0, iLastIndexOf))
            sb.append(str2)
            sb.append(str.subSequence(iLastIndexOf, str.length()))
        }
        return sb.toString()
    }

    fun isReservedFileName(String str) {
        return reservedFileNameRegex.matcher(str).matches()
    }

    fun isWindows() {
        return System.getProperty("os.name").startsWith("Windows")
    }

    fun shortenPathComponent(String str, Int i) {
        Int i2 = i + 1
        try {
            IntBuffer intBufferAsIntBuffer = ByteBuffer.wrap(str.getBytes("UTF-32BE")).asIntBuffer()
            Int iLimit = intBufferAsIntBuffer.limit()
            Array<Int> iArr = new Int[iLimit]
            intBufferAsIntBuffer.get(iArr)
            Int i3 = iLimit / 2
            Int i4 = i3 + 1
            Int iUtf8Length = utf8Length(iArr[i3])
            if (iLimit % 2 == 0 && iUtf8Length < i2) {
                iUtf8Length += utf8Length(iArr[i4])
                i4++
            }
            while (iUtf8Length < i2 && (i3 > 0 || i4 < iLimit)) {
                if (i3 > 0) {
                    i3--
                    iUtf8Length += utf8Length(iArr[i3])
                }
                if (iUtf8Length < i2 && i4 < iLimit) {
                    iUtf8Length += utf8Length(iArr[i4])
                    i4++
                }
            }
            StringBuilder sb = StringBuilder()
            for (Int i5 = 0; i5 < i3; i5++) {
                sb.appendCodePoint(iArr[i5])
            }
            sb.append('#')
            while (i4 < iLimit) {
                sb.appendCodePoint(iArr[i4])
                i4++
            }
            return sb.toString()
        } catch (UnsupportedEncodingException e) {
            throw RuntimeException(e)
        }
    }

    fun utf8Length(Int i) {
        if (i < 128) {
            return 1
        }
        if (i < 2048) {
            return 2
        }
        return i < 65536 ? 3 : 4
    }

    fun utf8Length(String str) {
        Int iCharCount = 0
        Int iUtf8Length = 0
        while (iCharCount < str.length()) {
            Int iCodePointAt = str.codePointAt(iCharCount)
            iUtf8Length += utf8Length(iCodePointAt)
            iCharCount += Character.charCount(iCodePointAt)
        }
        return iUtf8Length
    }

    public final File addUniqueChild(DirectoryEntry directoryEntry, Array<String> strArr, Int i) throws IOException {
        if (i != strArr.length - 1) {
            return addUniqueChild((DirectoryEntry) directoryEntry.addChild(DirectoryEntry(directoryEntry, strArr[i])), strArr, i + 1)
        }
        FileEntry fileEntry = FileEntry(directoryEntry, strArr[i] + this.fileExtension)
        directoryEntry.addChild(fileEntry)
        return File(directoryEntry.file, fileEntry.getPhysicalName())
    }

    public final Int getMaxFilenameLength() {
        return 249
    }

    fun getUniqueFilenameForClass(String str) throws IOException {
        if (str.charAt(0) != 'L' || str.charAt(str.length() - 1) != ';') {
            throw RuntimeException("Not a valid dalvik class name")
        }
        Int i = 1
        for (Int i2 = 1; i2 < str.length() - 1; i2++) {
            if (str.charAt(i2) == '/') {
                i++
            }
        }
        Array<String> strArr = new String[i]
        Int i3 = 1
        Int i4 = 1
        Int i5 = 0
        while (i3 < str.length() - 1) {
            if (str.charAt(i3) == '/') {
                if (i3 - i4 == 0) {
                    throw RuntimeException("Not a valid dalvik class name")
                }
                strArr[i5] = str.substring(i4, i3)
                i3++
                i4 = i3
                i5++
            }
            i3++
        }
        if (i4 >= str.length() - 1) {
            throw RuntimeException("Not a valid dalvik class name")
        }
        strArr[i5] = str.substring(i4, str.length() - 1)
        return addUniqueChild(this.top, strArr, 0)
    }
}
