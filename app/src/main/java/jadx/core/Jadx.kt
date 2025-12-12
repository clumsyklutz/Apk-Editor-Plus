package jadx.core

import jadx.api.IJadxArgs
import jadx.core.dex.visitors.ClassModifier
import jadx.core.dex.visitors.CodeShrinker
import jadx.core.dex.visitors.ConstInlineVisitor
import jadx.core.dex.visitors.DebugInfoVisitor
import jadx.core.dex.visitors.DependencyCollector
import jadx.core.dex.visitors.DotGraphVisitor
import jadx.core.dex.visitors.EnumVisitor
import jadx.core.dex.visitors.ExtractFieldInit
import jadx.core.dex.visitors.FallbackModeVisitor
import jadx.core.dex.visitors.MethodInlineVisitor
import jadx.core.dex.visitors.ModVisitor
import jadx.core.dex.visitors.PrepareForCodeGen
import jadx.core.dex.visitors.ReSugarCode
import jadx.core.dex.visitors.RenameVisitor
import jadx.core.dex.visitors.SimplifyVisitor
import jadx.core.dex.visitors.blocksmaker.BlockExceptionHandler
import jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract
import jadx.core.dex.visitors.blocksmaker.BlockFinish
import jadx.core.dex.visitors.blocksmaker.BlockProcessor
import jadx.core.dex.visitors.blocksmaker.BlockSplitter
import jadx.core.dex.visitors.regions.CheckRegions
import jadx.core.dex.visitors.regions.IfRegionVisitor
import jadx.core.dex.visitors.regions.LoopRegionVisitor
import jadx.core.dex.visitors.regions.ProcessVariables
import jadx.core.dex.visitors.regions.RegionMakerVisitor
import jadx.core.dex.visitors.regions.ReturnVisitor
import jadx.core.dex.visitors.ssa.EliminatePhiNodes
import jadx.core.dex.visitors.ssa.SSATransform
import jadx.core.dex.visitors.typeinference.FinishTypeInference
import jadx.core.dex.visitors.typeinference.TypeInference
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.ArrayList
import java.util.Enumeration
import java.util.List
import java.util.jar.Manifest

class Jadx {
    fun getPassesList(IJadxArgs iJadxArgs, File file) {
        ArrayList arrayList = ArrayList()
        if (iJadxArgs.isFallbackMode()) {
            arrayList.add(FallbackModeVisitor())
        } else {
            arrayList.add(BlockSplitter())
            arrayList.add(BlockProcessor())
            arrayList.add(BlockExceptionHandler())
            arrayList.add(BlockFinallyExtract())
            arrayList.add(BlockFinish())
            arrayList.add(SSATransform())
            arrayList.add(DebugInfoVisitor())
            arrayList.add(TypeInference())
            if (iJadxArgs.isRawCFGOutput()) {
                arrayList.add(DotGraphVisitor.dumpRaw(file))
            }
            arrayList.add(ConstInlineVisitor())
            arrayList.add(FinishTypeInference())
            arrayList.add(EliminatePhiNodes())
            arrayList.add(ModVisitor())
            arrayList.add(CodeShrinker())
            arrayList.add(ReSugarCode())
            if (iJadxArgs.isCFGOutput()) {
                arrayList.add(DotGraphVisitor.dump(file))
            }
            arrayList.add(RegionMakerVisitor())
            arrayList.add(IfRegionVisitor())
            arrayList.add(ReturnVisitor())
            arrayList.add(CodeShrinker())
            arrayList.add(SimplifyVisitor())
            arrayList.add(CheckRegions())
            if (iJadxArgs.isCFGOutput()) {
                arrayList.add(DotGraphVisitor.dumpRegions(file))
            }
            arrayList.add(MethodInlineVisitor())
            arrayList.add(ExtractFieldInit())
            arrayList.add(ClassModifier())
            arrayList.add(EnumVisitor())
            arrayList.add(PrepareForCodeGen())
            arrayList.add(LoopRegionVisitor())
            arrayList.add(ProcessVariables())
            arrayList.add(DependencyCollector())
            arrayList.add(RenameVisitor())
        }
        return arrayList
    }

    fun getVersion() throws IOException {
        try {
            ClassLoader classLoader = Jadx.class.getClassLoader()
            if (classLoader != null) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/MANIFEST.MF")
                while (resources.hasMoreElements()) {
                    String value = Manifest(resources.nextElement().openStream()).getMainAttributes().getValue("jadx-version")
                    if (value != null) {
                        return value
                    }
                }
            }
        } catch (Exception e) {
        }
        return "dev"
    }
}
