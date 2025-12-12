package com.a.b.b

class b extends RuntimeException {
    constructor() {
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }

    constructor(Throwable th) {
        super(th)
    }
}
