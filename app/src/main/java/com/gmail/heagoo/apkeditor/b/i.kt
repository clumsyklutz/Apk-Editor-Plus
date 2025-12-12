package com.gmail.heagoo.apkeditor.b

class i extends Exception {
    constructor(String str, Object... objArr) {
        super(String.format(str, objArr))
    }
}
