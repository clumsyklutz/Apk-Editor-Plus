package jadx.api

import java.io.File

public interface IJadxArgs {
    Int getDeobfuscationMaxLength()

    Int getDeobfuscationMinLength()

    File getOutDir()

    Int getThreadsCount()

    Boolean isCFGOutput()

    Boolean isDeobfuscationForceSave()

    Boolean isDeobfuscationOn()

    Boolean isFallbackMode()

    Boolean isRawCFGOutput()

    Boolean isShowInconsistentCode()

    Boolean isSkipResources()

    Boolean isSkipSources()

    Boolean isVerbose()

    Boolean useSourceNameAsClassAlias()
}
