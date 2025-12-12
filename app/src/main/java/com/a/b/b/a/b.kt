package com.a.b.b.a

import com.a.b.a.b.ah
import java.io.File

final class b implements com.a.b.a.d.h {
    b() {
    }

    @Override // com.a.b.a.d.h
    public final Unit a(File file) {
        if (a.d.verbose) {
            com.a.b.b.a.f253a.println("processing archive " + file + "...")
        }
    }

    @Override // com.a.b.a.d.h
    public final Unit a(Exception exc) {
        if (exc is i) {
            throw ((i) exc)
        }
        if (exc is ah) {
            com.a.b.b.a.f254b.println("\nEXCEPTION FROM SIMULATION:")
            com.a.b.b.a.f254b.println(exc.getMessage() + "\n")
            com.a.b.b.a.f254b.println(((ah) exc).a())
        } else {
            com.a.b.b.a.f254b.println("\nUNEXPECTED TOP-LEVEL EXCEPTION:")
            exc.printStackTrace(com.a.b.b.a.f254b)
        }
        a.c.incrementAndGet()
    }

    @Override // com.a.b.a.d.h
    public final Boolean a(String str, Long j, Array<Byte> bArr) {
        return a.b(str, j, bArr)
    }
}
