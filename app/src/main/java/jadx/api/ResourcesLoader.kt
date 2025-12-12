package jadx.api

import jadx.api.ResourceFile
import jadx.core.codegen.CodeWriter
import jadx.core.utils.Utils
import jadx.core.utils.exceptions.JadxException
import jadx.core.utils.files.InputFile
import jadx.core.xmlgen.ResContainer
import jadx.core.xmlgen.ResTableParser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Enumeration
import java.util.Iterator
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class ResourcesLoader {
    private static val LOAD_SIZE_LIMIT = 10485760
    private static val READ_BUFFER_SIZE = 8192
    private final JadxDecompiler jadxRef

    public interface ResourceDecoder {
        ResContainer decode(Long j, InputStream inputStream)
    }

    ResourcesLoader(JadxDecompiler jadxDecompiler) {
        this.jadxRef = jadxDecompiler
    }

    private fun addEntry(List list, File file, ZipEntry zipEntry) {
        if (zipEntry.isDirectory()) {
            return
        }
        String name = zipEntry.getName()
        ResourceFile resourceFile = ResourceFile(this.jadxRef, name, ResourceType.getFileType(name))
        resourceFile.setZipRef(new ResourceFile.ZipRef(file, name))
        list.add(resourceFile)
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0055 A[Catch: Exception -> 0x0073, TRY_LEAVE, TryCatch #5 {Exception -> 0x0073, blocks: (B:16:0x0050, B:18:0x0055), top: B:38:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static jadx.core.xmlgen.ResContainer decodeStream(jadx.api.ResourceFile r8, jadx.api.ResourcesLoader.ResourceDecoder r9) throws java.lang.Throwable {
        /*
            r0 = 0
            jadx.api.ResourceFile$ZipRef r3 = r8.getZipRef()
            if (r3 != 0) goto L8
        L7:
            return r0
        L8:
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L80
            java.io.File r1 = r3.getZipFile()     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L80
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L80
            java.lang.String r1 = r3.getEntryName()     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.util.zip.ZipEntry r4 = r2.getEntry(r1)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            if (r4 != 0) goto L59
            java.io.IOException r1 = new java.io.IOException     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.lang.String r5 = "Zip entry not found: "
            r4.<init>(r5)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.lang.StringBuilder r4 = r4.append(r3)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            r1.<init>(r4)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            throw r1     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
        L30:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
        L34:
            jadx.core.utils.exceptions.JadxException r4 = new jadx.core.utils.exceptions.JadxException     // Catch: java.lang.Throwable -> L4d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4d
            java.lang.String r6 = "Error decode: "
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r3 = r3.getEntryName()     // Catch: java.lang.Throwable -> L4d
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L4d
            r4.<init>(r3, r0)     // Catch: java.lang.Throwable -> L4d
            throw r4     // Catch: java.lang.Throwable -> L4d
        L4d:
            r0 = move-exception
        L4e:
            if (r2 == 0) goto L53
            r2.close()     // Catch: java.lang.Exception -> L73
        L53:
            if (r1 == 0) goto L58
            r1.close()     // Catch: java.lang.Exception -> L73
        L58:
            throw r0
        L59:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            java.io.InputStream r5 = r2.getInputStream(r4)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            r1.<init>(r5)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L7b
            Long r4 = r4.getSize()     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L86
            jadx.core.xmlgen.ResContainer r0 = r9.decode(r4, r1)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L86
            r2.close()     // Catch: java.lang.Exception -> L71
            r1.close()     // Catch: java.lang.Exception -> L71
            goto L7
        L71:
            r1 = move-exception
            goto L7
        L73:
            r1 = move-exception
            goto L58
        L75:
            r1 = move-exception
            r2 = r0
            r7 = r0
            r0 = r1
            r1 = r7
            goto L4e
        L7b:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L4e
        L80:
            r1 = move-exception
            r2 = r0
            r7 = r0
            r0 = r1
            r1 = r7
            goto L34
        L86:
            r0 = move-exception
            goto L34
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.api.ResourcesLoader.decodeStream(jadx.api.ResourceFile, jadx.api.ResourcesLoader$ResourceDecoder):jadx.core.xmlgen.ResContainer")
    }

    static ResContainer loadContent(final JadxDecompiler jadxDecompiler, final ResourceFile resourceFile) {
        try {
            return decodeStream(resourceFile, ResourceDecoder() { // from class: jadx.api.ResourcesLoader.1
                @Override // jadx.api.ResourcesLoader.ResourceDecoder
                public final ResContainer decode(Long j, InputStream inputStream) {
                    return j > 10485760 ? ResContainer.singleFile(resourceFile.getName(), CodeWriter().add("File too big, size: " + String.format("%.2f KB", Double.valueOf(j / 1024.0d)))) : ResourcesLoader.loadContent(jadxDecompiler, resourceFile, inputStream)
                }
            })
        } catch (JadxException e) {
            CodeWriter codeWriter = CodeWriter()
            codeWriter.add("Error decode ").add(resourceFile.getType().toString().toLowerCase())
            codeWriter.startLine(Utils.getStackTrace(e.getCause()))
            return ResContainer.singleFile(resourceFile.getName(), codeWriter)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun loadContent(JadxDecompiler jadxDecompiler, ResourceFile resourceFile, InputStream inputStream) {
        switch (resourceFile.getType()) {
            case MANIFEST:
            case XML:
                return ResContainer.singleFile(resourceFile.getName(), jadxDecompiler.getXmlParser().parse(inputStream))
            case ARSC:
                return ResTableParser().decodeFiles(inputStream)
            default:
                return ResContainer.singleFile(resourceFile.getName(), loadToCodeWriter(inputStream))
        }
    }

    private fun loadFile(List list, File file) throws Throwable {
        ZipFile zipFile
        Throwable th
        if (file == null) {
            return
        }
        ZipFile zipFile2 = null
        try {
            zipFile = ZipFile(file)
        } catch (IOException e) {
        } catch (Throwable th2) {
            zipFile = null
            th = th2
        }
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries()
            while (enumerationEntries.hasMoreElements()) {
                addEntry(list, file, enumerationEntries.nextElement())
            }
            try {
                zipFile.close()
            } catch (Exception e2) {
            }
        } catch (IOException e3) {
            zipFile2 = zipFile
            if (zipFile2 != null) {
                try {
                    zipFile2.close()
                } catch (Exception e4) {
                }
            }
        } catch (Throwable th3) {
            th = th3
            if (zipFile != null) {
                try {
                    zipFile.close()
                } catch (Exception e5) {
                }
            }
            throw th
        }
    }

    fun loadToCodeWriter(InputStream inputStream) throws IOException {
        CodeWriter codeWriter = CodeWriter()
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream(8192)
        Array<Byte> bArr = new Byte[8192]
        while (true) {
            try {
                Int i = inputStream.read(bArr)
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i)
                } else {
                    try {
                        break
                    } catch (Exception e) {
                    }
                }
            } finally {
                try {
                    inputStream.close()
                } catch (Exception e2) {
                }
            }
        }
        codeWriter.add(byteArrayOutputStream.toString("UTF-8"))
        return codeWriter
    }

    final List load(List list) throws Throwable {
        ArrayList arrayList = ArrayList(list.size())
        Iterator it = list.iterator()
        while (it.hasNext()) {
            loadFile(arrayList, ((InputFile) it.next()).getFile())
        }
        return arrayList
    }
}
