package a.c

class h extends e {
    constructor() {
    }

    constructor(String str) {
        super("'" + str + "' does not exist.")
    }

    constructor(String str, Throwable th) {
        super("'" + str + "' does not exist.", th)
    }
}
