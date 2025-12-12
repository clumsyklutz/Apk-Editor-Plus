package org.jf.dexlib2

import com.google.common.collect.Maps
import java.util.EnumMap
import java.util.HashMap

class Opcodes {
    public final Int api
    public final Int artVersion
    public final EnumMap<Opcode, Short> opcodeValues
    public final HashMap<String, Opcode> opcodesByName
    public final Array<Opcode> opcodesByValue = new Opcode[256]

    constructor(Int i, Int i2) {
        if (i >= 21) {
            this.api = i
            this.artVersion = VersionMap.mapApiToArtVersion(i)
        } else if (i2 < 0 || i2 >= 39) {
            this.api = i
            this.artVersion = i2
        } else {
            this.api = VersionMap.mapArtVersionToApi(i2)
            this.artVersion = i2
        }
        this.opcodeValues = new EnumMap<>(Opcode.class)
        this.opcodesByName = Maps.newHashMap()
        Int i3 = isArt() ? this.artVersion : this.api
        for (Opcode opcode : Opcode.values()) {
            Short sh = (isArt() ? opcode.artVersionToValueMap : opcode.apiToValueMap).get(Integer.valueOf(i3))
            if (sh != null) {
                if (!opcode.format.isPayloadFormat) {
                    this.opcodesByValue[sh.shortValue()] = opcode
                }
                this.opcodeValues.put((EnumMap<Opcode, Short>) opcode, (Opcode) sh)
                this.opcodesByName.put(opcode.name.toLowerCase(), opcode)
            }
        }
    }

    fun forApi(Int i) {
        return Opcodes(i, -1)
    }

    fun forArtVersion(Int i) {
        return Opcodes(-1, i)
    }

    fun forDexVersion(Int i) {
        Int iMapDexVersionToApi = VersionMap.mapDexVersionToApi(i)
        if (iMapDexVersionToApi != -1) {
            return Opcodes(iMapDexVersionToApi, -1)
        }
        throw RuntimeException("Unsupported dex version " + i)
    }

    fun getOpcodeByName(String str) {
        return this.opcodesByName.get(str.toLowerCase())
    }

    fun getOpcodeByValue(Int i) {
        if (i == 256) {
            return Opcode.PACKED_SWITCH_PAYLOAD
        }
        if (i == 512) {
            return Opcode.SPARSE_SWITCH_PAYLOAD
        }
        if (i == 768) {
            return Opcode.ARRAY_PAYLOAD
        }
        if (i < 0) {
            return null
        }
        Array<Opcode> opcodeArr = this.opcodesByValue
        if (i < opcodeArr.length) {
            return opcodeArr[i]
        }
        return null
    }

    fun getOpcodeValue(Opcode opcode) {
        return this.opcodeValues.get(opcode)
    }

    fun isArt() {
        return this.artVersion != -1
    }
}
