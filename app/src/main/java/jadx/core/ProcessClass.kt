package jadx.core

import jadx.core.codegen.CodeGen
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.ProcessState
import jadx.core.dex.visitors.DepthTraversal
import jadx.core.dex.visitors.IDexTreeVisitor
import jadx.core.utils.ErrorsCounter
import java.util.Iterator
import java.util.List

class ProcessClass {
    private constructor() {
    }

    fun process(ClassNode classNode, List list, CodeGen codeGen) {
        if (codeGen == null && classNode.getState() == ProcessState.PROCESSED) {
            return
        }
        synchronized (classNode) {
            try {
                try {
                    if (classNode.getState() == ProcessState.NOT_LOADED) {
                        classNode.load()
                        classNode.setState(ProcessState.STARTED)
                        Iterator it = list.iterator()
                        while (it.hasNext()) {
                            DepthTraversal.visit((IDexTreeVisitor) it.next(), classNode)
                        }
                        classNode.setState(ProcessState.PROCESSED)
                    }
                    if (classNode.getState() == ProcessState.PROCESSED && codeGen != null) {
                        processDependencies(classNode, list)
                        codeGen.visit(classNode)
                        classNode.setState(ProcessState.GENERATED)
                    }
                    if (classNode.getState() == ProcessState.GENERATED) {
                        classNode.unload()
                        classNode.setState(ProcessState.UNLOADED)
                    }
                } catch (Exception e) {
                    ErrorsCounter.classError(classNode, e.getClass().getSimpleName(), e)
                    if (classNode.getState() == ProcessState.GENERATED) {
                        classNode.unload()
                        classNode.setState(ProcessState.UNLOADED)
                    }
                }
            } catch (Throwable th) {
                if (classNode.getState() == ProcessState.GENERATED) {
                    classNode.unload()
                    classNode.setState(ProcessState.UNLOADED)
                }
                throw th
            }
        }
    }

    static Unit processDependencies(ClassNode classNode, List list) {
        Iterator it = classNode.getDependencies().iterator()
        while (it.hasNext()) {
            process((ClassNode) it.next(), list, null)
        }
    }
}
