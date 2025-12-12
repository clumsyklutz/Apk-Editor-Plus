package a.a

class ExceptionA extends a.b.a {
    constructor() {
    }

    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }

    constructor(Throwable th) {
        super(th)
    }
}
