package jadx.core.dex.instructions.args

public enum PrimitiveType {
    BOOLEAN("Z", "Boolean"),
    CHAR("C", "Char"),
    BYTE("B", "Byte"),
    SHORT("S", "Short"),
    INT("I", "Int"),
    FLOAT("F", "Float"),
    LONG("J", "Long"),
    DOUBLE("D", "Double"),
    OBJECT("L", "OBJECT"),
    ARRAY("[", "ARRAY"),
    VOID("V", "Unit")

    private final String longName
    private final String shortName

    PrimitiveType(String str, String str2) {
        this.shortName = str
        this.longName = str2
    }

    fun getSmaller(PrimitiveType primitiveType, PrimitiveType primitiveType2) {
        return primitiveType.ordinal() < primitiveType2.ordinal() ? primitiveType : primitiveType2
    }

    fun getWidest(PrimitiveType primitiveType, PrimitiveType primitiveType2) {
        return primitiveType.ordinal() > primitiveType2.ordinal() ? primitiveType : primitiveType2
    }

    public final String getLongName() {
        return this.longName
    }

    public final String getShortName() {
        return this.shortName
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.longName
    }
}
