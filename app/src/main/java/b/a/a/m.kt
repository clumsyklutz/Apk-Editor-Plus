package b.a.a

import androidx.appcompat.R

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class m implements l {
    private final Int u
    private final String v
    private static m e = m("SWITCH_PROTOCOL", 0, R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle, "Switching Protocols")

    /* renamed from: a, reason: collision with root package name */
    public static val f99a = m("OK", 1, 200, "OK")
    private static m f = m("CREATED", 2, 201, "Created")
    private static m g = m("ACCEPTED", 3, 202, "Accepted")
    private static m h = m("NO_CONTENT", 4, 204, "No Content")
    private static m i = m("PARTIAL_CONTENT", 5, 206, "Partial Content")
    private static m j = m("REDIRECT", 6, 301, "Moved Permanently")
    private static m k = m("NOT_MODIFIED", 7, 304, "Not Modified")

    /* renamed from: b, reason: collision with root package name */
    public static val f100b = m("BAD_REQUEST", 8, 400, "Bad Request")
    private static m l = m("UNAUTHORIZED", 9, 401, "Unauthorized")
    private static m m = m("FORBIDDEN", 10, 403, "Forbidden")
    public static val c = m("NOT_FOUND", 11, 404, "Not Found")
    private static m n = m("METHOD_NOT_ALLOWED", 12, 405, "Method Not Allowed")
    private static m o = m("NOT_ACCEPTABLE", 13, 406, "Not Acceptable")
    private static m p = m("REQUEST_TIMEOUT", 14, 408, "Request Timeout")
    private static m q = m("CONFLICT", 15, 409, "Conflict")
    private static m r = m("RANGE_NOT_SATISFIABLE", 16, 416, "Requested Range Not Satisfiable")
    public static val d = m("INTERNAL_ERROR", 17, 500, "Internal Server Error")
    private static m s = m("NOT_IMPLEMENTED", 18, 501, "Not Implemented")
    private static m t = m("UNSUPPORTED_HTTP_VERSION", 19, 505, "HTTP Version Not Supported")

    static {
        Array<m> mVarArr = {e, f99a, f, g, h, i, j, k, f100b, l, m, c, n, o, p, q, r, d, s, t}
    }

    private constructor(String str, Int i2, Int i3, String str2) {
        this.u = i3
        this.v = str2
    }

    @Override // b.a.a.l
    public final String a() {
        return this.u + " " + this.v
    }
}
