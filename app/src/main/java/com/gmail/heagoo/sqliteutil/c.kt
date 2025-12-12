package com.gmail.heagoo.sqliteutil

import android.util.Log
import com.gmail.heagoo.common.ccc
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Map

class c implements ccc {

    /* renamed from: b, reason: collision with root package name */
    private static Array<String> f1568b = {"/data/bin/su", "/system/bin/su", "/system/xbin/su"}

    /* renamed from: a, reason: collision with root package name */
    private String f1569a

    /* renamed from: a, reason: collision with other field name */
    private Array<String> f1a = new String[2]

    /* renamed from: b, reason: collision with other field name */
    private String f2b

    private fun a(String str, Array<String> strArr) {
        Int i = 0
        Map<String, String> map = System.getenv()
        Array<String> strArr2 = new String[(strArr != null ? strArr.length : 0) + map.size()]
        Int i2 = 0
        for (Map.Entry<String, String> entry : map.entrySet()) {
            strArr2[i2] = entry.getKey() + "=" + entry.getValue()
            i2++
        }
        if (strArr != null) {
            Int length = strArr.length
            while (i < length) {
                strArr2[i2] = strArr[i]
                i++
                i2++
            }
        }
        return Runtime.getRuntime().exec(str, strArr2)
    }

    private fun a(InputStream inputStream) throws IOException {
        Int i
        Array<Char> cArr = new Char[8192]
        StringBuilder sb = StringBuilder()
        InputStreamReader inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        do {
            i = inputStreamReader.read(cArr, 0, 8192)
            if (i > 0) {
                sb.append(cArr, 0, i)
            }
        } while (i >= 0);
        return sb.toString()
    }

    private fun a(Process process) {
        try {
            process.exitValue()
            return false
        } catch (IllegalThreadStateException e) {
            return true
        }
    }

    private fun b(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (IOException e) {
            }
        }
    }

    private fun b(String str, Array<String> strArr, Integer num, Boolean z) throws InterruptedException, IOException {
        d dVar
        d dVar2
        Log.d("RootCommand", String.format("Running '%s' as root", str))
        Log.d("RootCommand", String.format("num %d , bool %b", num, Boolean.valueOf(z)))
        String str2 = "su"
        for (String str3 : f1568b) {
            if (File(str3).exists()) {
                str2 = str3
            }
        }
        Log.d("RootCommand", "su path: " + str2)
        Process processA = a(str2, strArr)
        try {
            try {
                DataOutputStream dataOutputStream = DataOutputStream(processA.getOutputStream())
                dataOutputStream.writeBytes(str + "\n")
                dataOutputStream.writeBytes("echo \"rc:\" $?\n")
                dataOutputStream.writeBytes("exit\n")
                dataOutputStream.flush()
                InputStream inputStream = processA.getInputStream()
                InputStream errorStream = processA.getErrorStream()
                if (z) {
                    dVar = null
                    dVar2 = null
                } else {
                    d dVar3 = d(inputStream, this.f1a, 0)
                    dVar3.start()
                    d dVar4 = d(errorStream, this.f1a, 1)
                    dVar4.start()
                    Log.d("RootCommand", "After inputStreamReader thread start")
                    dVar = dVar3
                    dVar2 = dVar4
                }
                if (num.intValue() == 1000) {
                    Long jCurrentTimeMillis = System.currentTimeMillis() + num.intValue()
                    Long jCurrentTimeMillis2 = System.currentTimeMillis()
                    Log.d("RootCommand", "currentTimeMillis")
                    Log.d("RootCommand", String.format("init stopTime %d , currentTime %d", Long.valueOf(jCurrentTimeMillis), Long.valueOf(jCurrentTimeMillis2)))
                    while (true) {
                        if (!a(processA)) {
                            break
                        }
                        Long jCurrentTimeMillis3 = System.currentTimeMillis()
                        Array<Object> objArr = new Object[3]
                        objArr[0] = Long.valueOf(jCurrentTimeMillis)
                        objArr[1] = Long.valueOf(jCurrentTimeMillis3)
                        objArr[2] = Boolean.valueOf(jCurrentTimeMillis < jCurrentTimeMillis3)
                        Log.d("RootCommand", String.format("stopTime %d < currentTime %d , %b", objArr))
                        if (jCurrentTimeMillis3 > jCurrentTimeMillis) {
                            Log.d("RootCommand", "Process doesn't seem to stop on it's own, assuming it's hanging")
                            try {
                                dataOutputStream.close()
                                if (processA != null) {
                                    try {
                                        processA.exitValue()
                                    } catch (IllegalThreadStateException e) {
                                        e.printStackTrace()
                                        processA.destroy()
                                    }
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace()
                                throw RuntimeException(e2)
                            }
                        }
                    }
                } else {
                    try {
                        Log.d("RootCommand", "before waitFor")
                        processA.waitFor()
                        Log.d("RootCommand", "after waitFor")
                    } catch (InterruptedException e3) {
                        e3.printStackTrace()
                    }
                }
                if (z) {
                    Log.d("RootCommand", "before inputStreamReader")
                    this.f1a[0] = a(inputStream)
                    this.f1a[1] = a(errorStream)
                    b(inputStream)
                    b(errorStream)
                    Log.d("RootCommand", "after inputStreamReader")
                } else {
                    dVar.a()
                    dVar2.a()
                }
                Log.d("RootCommand", "Process returned with " + processA.exitValue())
                Log.d("RootCommand", "Process stdout was: " + this.f1a[0] + "; stderr: " + this.f1a[1])
                if (processA.exitValue() == 0) {
                    try {
                        dataOutputStream.close()
                        if (processA != null) {
                            try {
                                processA.exitValue()
                            } catch (IllegalThreadStateException e4) {
                                e4.printStackTrace()
                                processA.destroy()
                            }
                        }
                        return true
                    } catch (IOException e5) {
                        throw RuntimeException(e5)
                    }
                }
                try {
                    dataOutputStream.close()
                    if (processA != null) {
                        try {
                            processA.exitValue()
                        } catch (IllegalThreadStateException e6) {
                            e6.printStackTrace()
                            processA.destroy()
                        }
                    }
                    return false
                } catch (IOException e7) {
                    e7.printStackTrace()
                    throw RuntimeException(e7)
                }
            } catch (FileNotFoundException e8) {
                e8.printStackTrace()
                return true
            }
        } catch (IOException e9) {
            e9.printStackTrace()
            Log.d("RootCommand", "Failed to run command: " + e9.getMessage())
            if (processA != null) {
                try {
                    processA.exitValue()
                } catch (IllegalThreadStateException e10) {
                    e10.printStackTrace()
                    processA.destroy()
                }
                return false
            }
            return true
        }
    }

    @Override // com.gmail.heagoo.common.ccc
    public final String a() {
        return this.f1a[0]
    }

    @Override // com.gmail.heagoo.common.ccc
    public final Boolean a(String str, Array<String> strArr, Integer num) {
        return b(str, null, num)
    }

    @Override // com.gmail.heagoo.common.ccc
    public final Boolean a(String str, Array<String> strArr, Integer num, Boolean z) {
        return b(str, null, num, z)
    }

    @Override // com.gmail.heagoo.common.ccc
    public final String b() {
        return this.f1a[1]
    }

    public final Boolean b(String str, Array<String> strArr, Integer num) {
        return b(str, strArr, num, false)
    }
}
