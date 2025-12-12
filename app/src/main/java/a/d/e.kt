package a.d

import java.io.Serializable

class e implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public final Object f71a

    /* renamed from: b, reason: collision with root package name */
    public Object f72b

    constructor(Object obj, Object obj2) {
        this.f71a = obj
        this.f72b = obj2
    }

    public final Boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        e eVar = (e) obj
        if (this.f71a == eVar.f71a || (this.f71a != null && this.f71a.equals(eVar.f71a))) {
            return this.f72b == eVar.f72b || (this.f72b != null && this.f72b.equals(eVar.f72b))
        }
        return false
    }

    public final Int hashCode() {
        return (((this.f71a != null ? this.f71a.hashCode() : 0) + 213) * 71) + (this.f72b != null ? this.f72b.hashCode() : 0)
    }
}
