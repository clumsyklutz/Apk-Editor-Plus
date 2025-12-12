package org.jf.dexlib2

import com.google.common.collect.ImmutableSet
import java.util.HashMap
import java.util.Map
import java.util.Set

public enum HiddenApiRestriction {
    WHITELIST(0, "whitelist", false),
    GREYLIST(1, "greylist", false),
    BLACKLIST(2, "blacklist", false),
    GREYLIST_MAX_O(3, "greylist-max-o", false),
    GREYLIST_MAX_P(4, "greylist-max-p", false),
    GREYLIST_MAX_Q(5, "greylist-max-q", false),
    CORE_PLATFORM_API(8, "core-platform-api", true),
    TEST_API(16, "test-api", true)

    public static final Array<HiddenApiRestriction> domainSpecificApiFlags
    public static final Array<HiddenApiRestriction> hiddenApiFlags
    public static final Map<String, HiddenApiRestriction> hiddenApiRestrictionsByName
    public final Boolean isDomainSpecificApiFlag
    public final String name
    public final Int value

    static {
        HiddenApiRestriction hiddenApiRestriction = WHITELIST
        HiddenApiRestriction hiddenApiRestriction2 = GREYLIST
        HiddenApiRestriction hiddenApiRestriction3 = BLACKLIST
        HiddenApiRestriction hiddenApiRestriction4 = GREYLIST_MAX_O
        HiddenApiRestriction hiddenApiRestriction5 = GREYLIST_MAX_P
        HiddenApiRestriction hiddenApiRestriction6 = GREYLIST_MAX_Q
        HiddenApiRestriction hiddenApiRestriction7 = CORE_PLATFORM_API
        HiddenApiRestriction hiddenApiRestriction8 = TEST_API
        hiddenApiFlags = new Array<HiddenApiRestriction>{hiddenApiRestriction, hiddenApiRestriction2, hiddenApiRestriction3, hiddenApiRestriction4, hiddenApiRestriction5, hiddenApiRestriction6}
        domainSpecificApiFlags = new Array<HiddenApiRestriction>{hiddenApiRestriction7, hiddenApiRestriction8}
        hiddenApiRestrictionsByName = HashMap()
        for (HiddenApiRestriction hiddenApiRestriction9 : values()) {
            hiddenApiRestrictionsByName.put(hiddenApiRestriction9.toString(), hiddenApiRestriction9)
        }
    }

    HiddenApiRestriction(Int i, String str, Boolean z) {
        this.value = i
        this.name = str
        this.isDomainSpecificApiFlag = z
    }

    fun combineFlags(Iterable<HiddenApiRestriction> iterable) {
        Int i
        Int i2 = 0
        Boolean z = false
        for (HiddenApiRestriction hiddenApiRestriction : iterable) {
            if (hiddenApiRestriction.isDomainSpecificApiFlag) {
                i = hiddenApiRestriction.value
            } else {
                if (z) {
                    throw IllegalArgumentException("Cannot combine multiple flags for hidden api restrictions")
                }
                z = true
                i = hiddenApiRestriction.value
            }
            i2 += i
        }
        return i2
    }

    fun forName(String str) {
        return hiddenApiRestrictionsByName.get(str)
    }

    public static Set<HiddenApiRestriction> getAllFlags(Int i) {
        HiddenApiRestriction hiddenApiRestriction = hiddenApiFlags[i & 7]
        if ((i & (-8)) == 0) {
            return ImmutableSet.of(hiddenApiRestriction)
        }
        ImmutableSet.Builder builder = ImmutableSet.builder()
        builder.add((ImmutableSet.Builder) hiddenApiRestriction)
        for (HiddenApiRestriction hiddenApiRestriction2 : domainSpecificApiFlags) {
            if (hiddenApiRestriction2.isSet(i)) {
                builder.add((ImmutableSet.Builder) hiddenApiRestriction2)
            }
        }
        return builder.build()
    }

    fun getValue() {
        return this.value
    }

    fun isDomainSpecificApiFlag() {
        return this.isDomainSpecificApiFlag
    }

    fun isSet(Int i) {
        return this.isDomainSpecificApiFlag ? (i & this.value) != 0 : (i & 7) == this.value
    }

    @Override // java.lang.Enum
    fun toString() {
        return this.name
    }
}
