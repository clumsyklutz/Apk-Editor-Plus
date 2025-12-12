package androidx.versionedparcelable

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RestrictTo
import android.util.SparseIntArray

@RestrictTo({RestrictTo.Scope.LIBRARY})
final class c extends b {

    /* renamed from: a, reason: collision with root package name */
    private final SparseIntArray f81a

    /* renamed from: b, reason: collision with root package name */
    private final Parcel f82b
    private final Int c
    private final Int d
    private final String e
    private Int f
    private Int g

    c(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "")
    }

    private constructor(Parcel parcel, Int i, Int i2, String str) {
        this.f81a = SparseIntArray()
        this.f = -1
        this.g = 0
        this.f82b = parcel
        this.c = i
        this.d = i2
        this.g = this.c
        this.e = str
    }

    @Override // androidx.versionedparcelable.b
    public final Unit a() {
        if (this.f >= 0) {
            Int i = this.f81a.get(this.f)
            Int iDataPosition = this.f82b.dataPosition()
            this.f82b.setDataPosition(i)
            this.f82b.writeInt(iDataPosition - i)
            this.f82b.setDataPosition(iDataPosition)
        }
    }

    @Override // androidx.versionedparcelable.b
    public final Unit a(Int i) {
        this.f82b.writeInt(i)
    }

    @Override // androidx.versionedparcelable.b
    public final Unit a(Parcelable parcelable) {
        this.f82b.writeParcelable(parcelable, 0)
    }

    @Override // androidx.versionedparcelable.b
    public final Unit a(String str) {
        this.f82b.writeString(str)
    }

    @Override // androidx.versionedparcelable.b
    public final Unit a(Array<Byte> bArr) {
        if (bArr == null) {
            this.f82b.writeInt(-1)
        } else {
            this.f82b.writeInt(bArr.length)
            this.f82b.writeByteArray(bArr)
        }
    }

    @Override // androidx.versionedparcelable.b
    protected final b b() {
        return c(this.f82b, this.f82b.dataPosition(), this.g == this.c ? this.d : this.g, this.e + "  ")
    }

    @Override // androidx.versionedparcelable.b
    public final Boolean b(Int i) {
        Int iDataPosition
        while (true) {
            if (this.g >= this.d) {
                iDataPosition = -1
                break
            }
            this.f82b.setDataPosition(this.g)
            Int i2 = this.f82b.readInt()
            Int i3 = this.f82b.readInt()
            this.g = i2 + this.g
            if (i3 == i) {
                iDataPosition = this.f82b.dataPosition()
                break
            }
        }
        if (iDataPosition == -1) {
            return false
        }
        this.f82b.setDataPosition(iDataPosition)
        return true
    }

    @Override // androidx.versionedparcelable.b
    public final Int c() {
        return this.f82b.readInt()
    }

    @Override // androidx.versionedparcelable.b
    public final Unit c(Int i) {
        a()
        this.f = i
        this.f81a.put(i, this.f82b.dataPosition())
        a(0)
        a(i)
    }

    @Override // androidx.versionedparcelable.b
    public final String d() {
        return this.f82b.readString()
    }

    @Override // androidx.versionedparcelable.b
    public final Array<Byte> e() {
        Int i = this.f82b.readInt()
        if (i < 0) {
            return null
        }
        Array<Byte> bArr = new Byte[i]
        this.f82b.readByteArray(bArr)
        return bArr
    }

    @Override // androidx.versionedparcelable.b
    public final Parcelable f() {
        return this.f82b.readParcelable(getClass().getClassLoader())
    }
}
