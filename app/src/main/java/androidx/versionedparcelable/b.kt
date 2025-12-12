package androidx.versionedparcelable

import android.os.Parcelable
import android.support.annotation.RestrictTo
import java.lang.reflect.InvocationTargetException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class b {
    private fun a(String str, b bVar) {
        try {
            return (d) Class.forName(str, true, b.class.getClassLoader()).getDeclaredMethod("read", b.class).invoke(null, bVar)
        } catch (ClassNotFoundException e) {
            throw RuntimeException("VersionedParcel encountered ClassNotFoundException", e)
        } catch (IllegalAccessException e2) {
            throw RuntimeException("VersionedParcel encountered IllegalAccessException", e2)
        } catch (NoSuchMethodException e3) {
            throw RuntimeException("VersionedParcel encountered NoSuchMethodException", e3)
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e4.getCause())
            }
            throw RuntimeException("VersionedParcel encountered InvocationTargetException", e4)
        }
    }

    private fun a(Class cls) {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader())
    }

    protected abstract Unit a()

    protected abstract Unit a(Int i)

    public final Unit a(Int i, Int i2) {
        c(i2)
        a(i)
    }

    protected abstract Unit a(Parcelable parcelable)

    public final Unit a(Parcelable parcelable, Int i) {
        c(i)
        a(parcelable)
    }

    protected final Unit a(d dVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (dVar == null) {
            a((String) null)
            return
        }
        try {
            a(a(dVar.getClass()).getName())
            b bVarB = b()
            try {
                a(dVar.getClass()).getDeclaredMethod("write", dVar.getClass(), b.class).invoke(null, dVar, bVarB)
                bVarB.a()
            } catch (ClassNotFoundException e) {
                throw RuntimeException("VersionedParcel encountered ClassNotFoundException", e)
            } catch (IllegalAccessException e2) {
                throw RuntimeException("VersionedParcel encountered IllegalAccessException", e2)
            } catch (NoSuchMethodException e3) {
                throw RuntimeException("VersionedParcel encountered NoSuchMethodException", e3)
            } catch (InvocationTargetException e4) {
                if (!(e4.getCause() instanceof RuntimeException)) {
                    throw RuntimeException("VersionedParcel encountered InvocationTargetException", e4)
                }
                throw ((RuntimeException) e4.getCause())
            }
        } catch (ClassNotFoundException e5) {
            throw RuntimeException(dVar.getClass().getSimpleName() + " does not have a Parcelizer", e5)
        }
    }

    protected abstract Unit a(String str)

    public final Unit a(String str, Int i) {
        c(7)
        a(str)
    }

    protected abstract Unit a(Array<Byte> bArr)

    public final Unit a(Array<Byte> bArr, Int i) {
        c(2)
        a(bArr)
    }

    public final Int b(Int i, Int i2) {
        return !b(i2) ? i : c()
    }

    public final Parcelable b(Parcelable parcelable, Int i) {
        return !b(i) ? parcelable : f()
    }

    protected abstract b b()

    public final String b(String str, Int i) {
        return !b(7) ? str : d()
    }

    protected abstract Boolean b(Int i)

    public final Array<Byte> b(Array<Byte> bArr, Int i) {
        return !b(2) ? bArr : e()
    }

    protected abstract Int c()

    protected abstract Unit c(Int i)

    protected abstract String d()

    protected abstract Array<Byte> e()

    protected abstract Parcelable f()

    protected final d g() {
        String strD = d()
        if (strD == null) {
            return null
        }
        return a(strD, b())
    }
}
