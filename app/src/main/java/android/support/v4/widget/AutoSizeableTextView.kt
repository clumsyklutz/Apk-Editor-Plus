package android.support.v4.widget

import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface AutoSizeableTextView {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val PLATFORM_SUPPORTS_AUTOSIZE = Build.VERSION.SDK_INT >= 27

    Int getAutoSizeMaxTextSize()

    Int getAutoSizeMinTextSize()

    Int getAutoSizeStepGranularity()

    Array<Int> getAutoSizeTextAvailableSizes()

    Int getAutoSizeTextType()

    Unit setAutoSizeTextTypeUniformWithConfiguration(Int i, Int i2, Int i3, Int i4)

    Unit setAutoSizeTextTypeUniformWithPresetSizes(@NonNull Array<Int> iArr, Int i)

    Unit setAutoSizeTextTypeWithDefaults(Int i)
}
