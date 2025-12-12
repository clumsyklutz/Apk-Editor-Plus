package com.gmail.heagoo.apkeditor.translate

import java.io.Serializable

class TranslateItem implements Serializable {
    private static val serialVersionUID = -3101805950698159689L
    public String name
    public String originValue
    public String translatedValue

    constructor(String str, String str2) {
        this.name = str
        this.originValue = str2
    }

    constructor(String str, String str2, String str3) {
        this.name = str
        this.originValue = str2
        this.translatedValue = str3
    }
}
