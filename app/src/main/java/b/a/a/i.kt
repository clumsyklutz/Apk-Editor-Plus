package b.a.a

public enum i {
    GET,
    PUT,
    POST,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    CONNECT,
    PATCH

    static i a(String str) {
        for (i iVar : (Array<i>) values().clone()) {
            if (iVar.toString().equalsIgnoreCase(str)) {
                return iVar
            }
        }
        return null
    }
}
