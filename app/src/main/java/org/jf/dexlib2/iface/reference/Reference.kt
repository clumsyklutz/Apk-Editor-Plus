package org.jf.dexlib2.iface.reference

public interface Reference {

    public static class InvalidReferenceException extends Exception {
        public final String invalidReferenceRepresentation

        constructor(String str) {
            super("Invalid reference")
            this.invalidReferenceRepresentation = str
        }

        constructor(String str, Throwable th) {
            super(th)
            this.invalidReferenceRepresentation = str
        }

        fun getInvalidReferenceRepresentation() {
            return this.invalidReferenceRepresentation
        }
    }

    Unit validateReference() throws InvalidReferenceException
}
