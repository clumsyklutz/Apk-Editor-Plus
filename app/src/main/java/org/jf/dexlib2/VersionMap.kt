package org.jf.dexlib2

class VersionMap {
    fun mapApiToArtVersion(Int i) {
        if (i < 19) {
            return -1
        }
        switch (i) {
            case 19:
            case 20:
                return 7
            case 21:
                return 39
            case 22:
                return 45
            case 23:
                return 64
            case 24:
            case 25:
                return 79
            case 26:
                return 124
            case 27:
                return 131
            case 28:
                return 138
            case 29:
                return 170
            default:
                return 178
        }
    }

    fun mapApiToDexVersion(Int i) {
        if (i <= 23) {
            return 35
        }
        if (i <= 25) {
            return 37
        }
        return i <= 27 ? 38 : 39
    }

    fun mapArtVersionToApi(Int i) {
        if (i >= 170) {
            return 29
        }
        if (i >= 138) {
            return 28
        }
        if (i >= 131) {
            return 27
        }
        if (i >= 124) {
            return 26
        }
        if (i >= 79) {
            return 24
        }
        if (i >= 64) {
            return 23
        }
        if (i >= 45) {
            return 22
        }
        return i >= 39 ? 21 : 19
    }

    fun mapDexVersionToApi(Int i) {
        switch (i) {
            case 35:
                return 23
            case 36:
            default:
                return -1
            case 37:
                return 25
            case 38:
                return 27
            case 39:
                return 28
        }
    }
}
