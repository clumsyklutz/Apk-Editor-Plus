package com.google.common.collect

class Hashing {
    fun closedTableSize(Int i, Double d) {
        Int iMax = Math.max(i, 2)
        Int iHighestOneBit = Integer.highestOneBit(iMax)
        Double d2 = iHighestOneBit
        Double.isNaN(d2)
        if (iMax <= ((Int) (d * d2))) {
            return iHighestOneBit
        }
        Int i2 = iHighestOneBit << 1
        if (i2 > 0) {
            return i2
        }
        return 1073741824
    }

    fun smear(Int i) {
        return (Int) (Integer.rotateLeft((Int) (i * (-862048943)), 15) * 461845907)
    }

    fun smearedHash(Object obj) {
        return smear(obj == null ? 0 : obj.hashCode())
    }
}
