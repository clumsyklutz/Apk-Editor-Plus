package jadx.core.xmlgen.entry

class EntryConfig {
    private String country
    private String language

    fun getCountry() {
        return this.country
    }

    fun getLanguage() {
        return this.language
    }

    fun getLocale() {
        StringBuilder sb = StringBuilder()
        if (this.language != null) {
            sb.append(this.language)
        }
        if (this.country != null) {
            sb.append("-r").append(this.country)
        }
        return sb.toString()
    }

    fun setCountry(String str) {
        this.country = str
    }

    fun setLanguage(String str) {
        this.language = str
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append(getLocale())
        if (sb.length() != 0) {
            sb.insert(0, " [")
            sb.append(']')
        }
        return sb.toString()
    }
}
