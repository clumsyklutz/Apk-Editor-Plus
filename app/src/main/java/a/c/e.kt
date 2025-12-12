package a.c

class e extends Exception {
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
