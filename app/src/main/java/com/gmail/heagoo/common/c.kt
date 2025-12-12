package com.gmail.heagoo.common

import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class c implements ccc {

    /* renamed from: a, reason: collision with root package name */
    private Array<String> f1449a = new String[2]

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

    @Override // com.gmail.heagoo.common.ccc
    public final String a() {
        return this.f1449a[0]
    }

    public final Boolean a(Object obj, Array<String> strArr, Integer num, Boolean z) {
        Process processExec
        Process process = null
        try {
            try {
                try {
                    if (obj is String) {
                        processExec = Runtime.getRuntime().exec((String) obj, strArr)
                    } else {
                        processExec = Runtime.getRuntime().exec((Array<String>) obj)
                    }
                    InputStream inputStream = processExec.getInputStream()
                    InputStream errorStream = processExec.getErrorStream()
                    d dVar = null
                    d dVar2 = null
                    if (z) {
                        dVar = d(inputStream, this.f1449a, 0)
                        dVar.start()
                        dVar2 = d(errorStream, this.f1449a, 1)
                        dVar2.start()
                    }
                    if (num != null) {
                        Long jCurrentTimeMillis = System.currentTimeMillis() + num.intValue()
                        do {
                            Thread.sleep(20L)
                            if (a(processExec)) {
                            }
                        } while (System.currentTimeMillis() <= jCurrentTimeMillis);
                        Log.w("CommandRunner", "Process doesn't seem to stop on it's own, assuming it's hanging")
                        this.f1449a[1] = "Timeout!"
                        if (processExec != null) {
                            try {
                                processExec.exitValue()
                            } catch (IllegalThreadStateException e) {
                                processExec.destroy()
                            }
                        }
                        return false
                    }
                    processExec.waitFor()
                    if (z) {
                        dVar.a()
                        dVar2.a()
                    } else {
                        this.f1449a[0] = a(inputStream)
                        this.f1449a[1] = a(errorStream)
                        b(inputStream)
                        b(errorStream)
                    }
                    if (processExec.exitValue() != 0) {
                        if (processExec != null) {
                            try {
                                processExec.exitValue()
                            } catch (IllegalThreadStateException e2) {
                                processExec.destroy()
                            }
                        }
                        return false
                    }
                    if (processExec != null) {
                        try {
                            processExec.exitValue()
                        } catch (IllegalThreadStateException e3) {
                            processExec.destroy()
                        }
                    }
                    return true
                } catch (FileNotFoundException e4) {
                    Log.e("DEBUG", "Failed to run command", e4)
                    if (0 != 0) {
                        try {
                            process.exitValue()
                        } catch (IllegalThreadStateException e5) {
                            process.destroy()
                        }
                    }
                    return false
                } catch (InterruptedException e6) {
                    if (0 != 0) {
                        try {
                            process.exitValue()
                        } catch (IllegalThreadStateException e7) {
                            process.destroy()
                        }
                    }
                    return false
                }
            } catch (IOException e8) {
                Log.e("DEBUG", "Failed to run command", e8)
                if (0 != 0) {
                    try {
                        process.exitValue()
                    } catch (IllegalThreadStateException e9) {
                        process.destroy()
                    }
                }
                return false
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    process.exitValue()
                } catch (IllegalThreadStateException e10) {
                    process.destroy()
                }
            }
            throw th
        }
    }

    @Override // com.gmail.heagoo.common.ccc
    public final Boolean a(String str, Array<String> strArr, Integer num) {
        return a((Object) str, (Array<String>) null, num, false)
    }

    @Override // com.gmail.heagoo.common.ccc
    public final Boolean a(Array<String> strArr, Integer num, Boolean z) {
        Process processExec
        InputStream inputStream
        InputStream errorStream
        d dVar
        d dVar2
        Boolean z2 = false
        try {
            try {
                processExec = Runtime.getRuntime().exec(strArr)
                inputStream = processExec.getInputStream()
                errorStream = processExec.getErrorStream()
                if (z) {
                    d dVar3 = d(inputStream, this.f1449a, 0)
                    dVar3.start()
                    d dVar4 = d(errorStream, this.f1449a, 1)
                    dVar4.start()
                    dVar = dVar4
                    dVar2 = dVar3
                } else {
                    dVar = null
                    dVar2 = null
                }
            } catch (InterruptedException e) {
                return false
            }
        } catch (FileNotFoundException e2) {
            e = e2
        } catch (IOException e3) {
            e = e3
        }
        try {
            if (num != null) {
                Long jCurrentTimeMillis = System.currentTimeMillis() + num.intValue()
                do {
                    Thread.sleep(20L)
                    a(processExec)
                } while (System.currentTimeMillis() <= jCurrentTimeMillis);
                Log.w("CommandRunner", "Process doesn't seem to stop on it's own, assuming it's hanging")
                this.f1449a[1] = "Timeout!"
                if (processExec == null) {
                    return false
                }
                try {
                    processExec.exitValue()
                    return false
                } catch (IllegalThreadStateException e4) {
                    processExec.destroy()
                    return false
                }
            }
            processExec.waitFor()
            if (z) {
                dVar2.a()
                dVar.a()
            } else {
                this.f1449a[0] = a(inputStream)
                this.f1449a[1] = a(errorStream)
                b(inputStream)
                b(errorStream)
            }
            if (processExec.exitValue() == 0) {
                if (processExec != null) {
                    try {
                        processExec.exitValue()
                    } catch (IllegalThreadStateException e5) {
                        processExec.destroy()
                    }
                }
                return true
            }
            if (processExec == null) {
                return false
            }
            try {
                processExec.exitValue()
                return false
            } catch (IllegalThreadStateException e6) {
                processExec.destroy()
                return false
            }
        } catch (FileNotFoundException e7) {
            e = e7
            z2 = false
            Log.e("DEBUG", "Failed to run command", e)
            return z2
        } catch (IOException e8) {
            e = e8
            z2 = false
            Log.e("DEBUG", "Failed to run command", e)
            return z2
        }
    }

    @Override // com.gmail.heagoo.common.ccc
    public final String b() {
        return this.f1449a[1]
    }
}
