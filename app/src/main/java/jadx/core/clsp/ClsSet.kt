package jadx.core.clsp

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.RootNode
import jadx.core.utils.exceptions.DecodeException
import jadx.core.utils.exceptions.JadxRuntimeException
import jadx.core.utils.files.FileUtils
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class ClsSet {
    private static val CLST_EXTENSION = ".jcst"
    private static val CLST_FILENAME = "core.jcst"
    private static val CLST_PKG_PATH = ClsSet.class.getPackage().getName().replace('.', '/')
    private static val JADX_CLS_SET_HEADER = "jadx-cst"
    private static val STRING_CHARSET = "US-ASCII"
    private static val VERSION = 1
    private Array<NClass> classes

    private fun getCls(String str, Map map) {
        NClass nClass = (NClass) map.get(str)
        if (nClass == null) {
            map.containsKey(str)
        }
        return nClass
    }

    public static Array<NClass> makeParentsArray(ClassNode classNode, Map map) {
        NClass cls
        ArrayList arrayList = ArrayList(classNode.getInterfaces().size() + 1)
        ArgType superClass = classNode.getSuperClass()
        if (superClass != null && (cls = getCls(superClass.getObject(), map)) != null) {
            arrayList.add(cls)
        }
        Iterator it = classNode.getInterfaces().iterator()
        while (it.hasNext()) {
            NClass cls2 = getCls(((ArgType) it.next()).getObject(), map)
            if (cls2 != null) {
                arrayList.add(cls2)
            }
        }
        return (Array<NClass>) arrayList.toArray(new NClass[arrayList.size()])
    }

    private fun readString(DataInputStream dataInputStream) throws IOException {
        Int i = dataInputStream.readByte()
        Array<Byte> bArr = new Byte[i]
        Int i2 = dataInputStream.read(bArr)
        while (i2 != i) {
            Int i3 = dataInputStream.read(bArr, i2, i - i2)
            if (i3 == -1) {
                throw IOException("String read error")
            }
            i2 += i3
        }
        return String(bArr, STRING_CHARSET)
    }

    private fun writeString(DataOutputStream dataOutputStream, String str) throws IOException {
        Array<Byte> bytes = str.getBytes(STRING_CHARSET)
        dataOutputStream.writeByte(bytes.length)
        dataOutputStream.write(bytes)
    }

    fun addToMap(Map map) {
        for (NClass nClass : this.classes) {
            map.put(nClass.getName(), nClass)
        }
    }

    fun getClassesCount() {
        return this.classes.length
    }

    fun load() {
        InputStream resourceAsStream = getClass().getResourceAsStream(CLST_FILENAME)
        if (resourceAsStream == null) {
            throw JadxRuntimeException("Can't load classpath file: core.jcst")
        }
        try {
            load(resourceAsStream)
        } finally {
            resourceAsStream.close()
        }
    }

    fun load(RootNode rootNode) {
        Int i = 0
        List<ClassNode> classes = rootNode.getClasses(true)
        HashMap map = HashMap(classes.size())
        Int i2 = 0
        for (ClassNode classNode : classes) {
            String rawName = classNode.getRawName()
            if (!classNode.getAccessFlags().isPublic()) {
                map.put(rawName, null)
            } else {
                if (map.put(rawName, NClass(rawName, i2)) != null) {
                    throw JadxRuntimeException("Duplicate class: " + rawName)
                }
                i2++
            }
        }
        this.classes = new NClass[i2]
        for (ClassNode classNode2 : classes) {
            if (classNode2.getAccessFlags().isPublic()) {
                NClass cls = getCls(classNode2.getRawName(), map)
                if (cls == null) {
                    throw JadxRuntimeException("Missing class: " + classNode2)
                }
                cls.setParents(makeParentsArray(classNode2, map))
                this.classes[i] = cls
                i++
            }
        }
    }

    fun load(File file) throws IOException {
        String name = file.getName()
        InputStream fileInputStream = FileInputStream(file)
        try {
            if (name.endsWith(CLST_EXTENSION)) {
                load(fileInputStream)
            } else {
                if (!name.endsWith(".jar")) {
                    throw JadxRuntimeException("Unknown file format: " + name)
                }
                ZipInputStream zipInputStream = ZipInputStream(fileInputStream)
                try {
                    for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                        if (nextEntry.getName().endsWith(CLST_EXTENSION)) {
                            load(zipInputStream)
                        }
                    }
                } finally {
                    zipInputStream.close()
                }
            }
        } finally {
            fileInputStream.close()
        }
    }

    fun load(InputStream inputStream) {
        DataInputStream dataInputStream = DataInputStream(inputStream)
        try {
            Array<Byte> bArr = new Byte[8]
            Int i = dataInputStream.read(bArr)
            Byte b2 = dataInputStream.readByte()
            if (i != 8 || !JADX_CLS_SET_HEADER.equals(String(bArr, STRING_CHARSET)) || b2 != 1) {
                throw DecodeException("Wrong jadx class set header")
            }
            Int i2 = dataInputStream.readInt()
            this.classes = new NClass[i2]
            for (Int i3 = 0; i3 < i2; i3++) {
                this.classes[i3] = NClass(readString(dataInputStream), i3)
            }
            for (Int i4 = 0; i4 < i2; i4++) {
                Int i5 = dataInputStream.readByte()
                Array<NClass> nClassArr = new NClass[i5]
                for (Int i6 = 0; i6 < i5; i6++) {
                    nClassArr[i6] = this.classes[dataInputStream.readInt()]
                }
                this.classes[i4].setParents(nClassArr)
            }
        } finally {
            dataInputStream.close()
        }
    }

    Unit save(File file) {
        FileUtils.makeDirsForFile(file)
        BufferedOutputStream bufferedOutputStream = BufferedOutputStream(FileOutputStream(file))
        try {
            String name = file.getName()
            if (name.endsWith(CLST_EXTENSION)) {
                save(bufferedOutputStream)
            } else {
                if (!name.endsWith(".jar")) {
                    throw JadxRuntimeException("Unknown file format: " + name)
                }
                ZipOutputStream zipOutputStream = ZipOutputStream(bufferedOutputStream)
                try {
                    zipOutputStream.putNextEntry(ZipEntry(CLST_PKG_PATH + "/core.jcst"))
                    save(zipOutputStream)
                } finally {
                    zipOutputStream.close()
                }
            }
        } finally {
            bufferedOutputStream.close()
        }
    }

    fun save(OutputStream outputStream) {
        DataOutputStream dataOutputStream = DataOutputStream(outputStream)
        try {
            dataOutputStream.writeBytes(JADX_CLS_SET_HEADER)
            dataOutputStream.writeByte(1)
            dataOutputStream.writeInt(this.classes.length)
            for (NClass nClass : this.classes) {
                writeString(dataOutputStream, nClass.getName())
            }
            for (NClass nClass2 : this.classes) {
                Array<NClass> parents = nClass2.getParents()
                dataOutputStream.writeByte(parents.length)
                for (NClass nClass3 : parents) {
                    dataOutputStream.writeInt(nClass3.getId())
                }
            }
        } finally {
            dataOutputStream.close()
        }
    }
}
