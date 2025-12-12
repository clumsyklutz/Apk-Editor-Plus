package com.gmail.heagoo.apkeditor

class gzd {
    public static val COLUMN_CONTENT = "content"
    public static val COLUMN_ID = "id"
    public static val COLUMN_TITLE = "title"
    public static val CREATE_TABLE = "CREATE TABLE templates(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT)"
    public static val TABLE_NAME = "templates"
    private String content
    private Int id
    private String title

    constructor() {
    }

    constructor(Int i, String str, String str2) {
        this.id = i
        this.title = str
        this.content = str2
    }

    fun getContent() {
        return this.content
    }

    fun getId() {
        return this.id
    }

    fun getTitle() {
        return this.title
    }

    fun setContent(String str) {
        this.content = str
    }

    fun setId(Int i) {
        this.id = i
    }

    fun setTitle(String str) {
        this.title = str
    }
}
