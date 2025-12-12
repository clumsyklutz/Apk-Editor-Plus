package jadx.core.dex.nodes.parser

import com.a.a.o
import com.gmail.heagoo.a.c.a
import jadx.core.dex.attributes.annotations.Annotation
import jadx.core.dex.attributes.annotations.AnnotationsList
import jadx.core.dex.attributes.annotations.MethodParameters
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.exceptions.DecodeException
import java.util.ArrayList
import java.util.LinkedHashMap

class AnnotationsParser {
    private static final Annotation.Array<Visibility> VISIBILITIES = {Annotation.Visibility.BUILD, Annotation.Visibility.RUNTIME, Annotation.Visibility.SYSTEM}
    private final ClassNode cls
    private final DexNode dex

    constructor(ClassNode classNode) {
        this.cls = classNode
        this.dex = classNode.dex()
    }

    fun readAnnotation(DexNode dexNode, o oVar, Boolean z) throws DecodeException {
        EncValueParser encValueParser = EncValueParser(dexNode, oVar)
        Annotation.Visibility visibility = z ? VISIBILITIES[oVar.d()] : null
        Int iB = a.b(oVar)
        Int iB2 = a.b(oVar)
        LinkedHashMap linkedHashMap = LinkedHashMap(iB2)
        for (Int i = 0; i < iB2; i++) {
            linkedHashMap.put(dexNode.getString(a.b(oVar)), encValueParser.parseValue())
        }
        ArgType type = dexNode.getType(iB)
        Annotation annotation = Annotation(visibility, type, linkedHashMap)
        if (type.isObject()) {
            return annotation
        }
        throw DecodeException("Incorrect type for annotation: " + annotation)
    }

    private fun readAnnotationSet(Int i) {
        o oVarOpenSection
        Int iB
        if (i != 0 && (iB = (oVarOpenSection = this.dex.openSection(i)).b()) != 0) {
            ArrayList arrayList = ArrayList(iB)
            for (Int i2 = 0; i2 < iB; i2++) {
                arrayList.add(readAnnotation(this.dex, this.dex.openSection(oVarOpenSection.b()), true))
            }
            return AnnotationsList(arrayList)
        }
        return AnnotationsList.EMPTY
    }

    fun parse(Int i) {
        o oVarOpenSection = this.dex.openSection(i)
        Int iB = oVarOpenSection.b()
        Int iB2 = oVarOpenSection.b()
        Int iB3 = oVarOpenSection.b()
        Int iB4 = oVarOpenSection.b()
        if (iB != 0) {
            this.cls.addAttr(readAnnotationSet(iB))
        }
        for (Int i2 = 0; i2 < iB2; i2++) {
            this.cls.searchFieldById(oVarOpenSection.b()).addAttr(readAnnotationSet(oVarOpenSection.b()))
        }
        for (Int i3 = 0; i3 < iB3; i3++) {
            this.cls.searchMethodById(oVarOpenSection.b()).addAttr(readAnnotationSet(oVarOpenSection.b()))
        }
        for (Int i4 = 0; i4 < iB4; i4++) {
            MethodNode methodNodeSearchMethodById = this.cls.searchMethodById(oVarOpenSection.b())
            o oVarOpenSection2 = this.dex.openSection(oVarOpenSection.b())
            Int iB5 = oVarOpenSection2.b()
            MethodParameters methodParameters = MethodParameters(iB5)
            for (Int i5 = 0; i5 < iB5; i5++) {
                methodParameters.getParamList().add(readAnnotationSet(oVarOpenSection2.b()))
            }
            methodNodeSearchMethodById.addAttr(methodParameters)
        }
    }
}
