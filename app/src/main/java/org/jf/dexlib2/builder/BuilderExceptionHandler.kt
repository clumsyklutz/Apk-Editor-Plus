package org.jf.dexlib2.builder

import org.jf.dexlib2.base.BaseExceptionHandler
import org.jf.dexlib2.iface.reference.TypeReference

abstract class BuilderExceptionHandler extends BaseExceptionHandler {
    public final Label handler

    constructor(Label label) {
        this.handler = label
    }

    fun newExceptionHandler(Label label) {
        return BuilderExceptionHandler(label) { // from class: org.jf.dexlib2.builder.BuilderExceptionHandler.2
            @Override // org.jf.dexlib2.iface.ExceptionHandler
            fun getExceptionType() {
                return null
            }

            @Override // org.jf.dexlib2.iface.ExceptionHandler
            fun getHandlerCodeAddress() {
                return this.handler.getCodeAddress()
            }
        }
    }

    fun newExceptionHandler(final TypeReference typeReference, Label label) {
        return typeReference == null ? newExceptionHandler(label) : BuilderExceptionHandler(label) { // from class: org.jf.dexlib2.builder.BuilderExceptionHandler.1
            @Override // org.jf.dexlib2.iface.ExceptionHandler
            fun getExceptionType() {
                return typeReference.getType()
            }

            @Override // org.jf.dexlib2.base.BaseExceptionHandler, org.jf.dexlib2.iface.ExceptionHandler
            fun getExceptionTypeReference() {
                return typeReference
            }

            @Override // org.jf.dexlib2.iface.ExceptionHandler
            fun getHandlerCodeAddress() {
                return this.handler.getCodeAddress()
            }
        }
    }
}
