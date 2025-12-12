package android.support.v4.text

import android.os.Build
import android.support.annotation.GuardedBy
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.UiThread
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.os.TraceCompat
import android.support.v4.util.ObjectsCompat
import android.support.v4.util.Preconditions
import android.support.v7.widget.ActivityChooserView
import android.text.Layout
import android.text.PrecomputedText
import android.text.Spannable
import android.text.SpannableString
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.MetricAffectingSpan
import java.util.ArrayList
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

class PrecomputedTextCompat implements Spannable {
    private static val LINE_FEED = '\n'

    @NonNull
    private final Array<Int> mParagraphEnds

    @NonNull
    private final Params mParams

    @NonNull
    private final Spannable mText

    @Nullable
    private final PrecomputedText mWrapped
    private static val sLock = Object()

    @GuardedBy("sLock")
    @NonNull
    private static Executor sExecutor = null

    class Params {
        private final Int mBreakStrategy
        private final Int mHyphenationFrequency

        @NonNull
        private final TextPaint mPaint

        @Nullable
        private final TextDirectionHeuristic mTextDir
        final PrecomputedText.Params mWrapped

        class Builder {
            private Int mBreakStrategy
            private Int mHyphenationFrequency

            @NonNull
            private final TextPaint mPaint
            private TextDirectionHeuristic mTextDir

            constructor(@NonNull TextPaint textPaint) {
                this.mPaint = textPaint
                if (Build.VERSION.SDK_INT >= 23) {
                    this.mBreakStrategy = 1
                    this.mHyphenationFrequency = 1
                } else {
                    this.mHyphenationFrequency = 0
                    this.mBreakStrategy = 0
                }
                if (Build.VERSION.SDK_INT >= 18) {
                    this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR
                } else {
                    this.mTextDir = null
                }
            }

            @NonNull
            fun build() {
                return Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency)
            }

            @RequiresApi(23)
            fun setBreakStrategy(Int i) {
                this.mBreakStrategy = i
                return this
            }

            @RequiresApi(23)
            fun setHyphenationFrequency(Int i) {
                this.mHyphenationFrequency = i
                return this
            }

            @RequiresApi(18)
            fun setTextDirection(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
                this.mTextDir = textDirectionHeuristic
                return this
            }
        }

        @RequiresApi(28)
        constructor(@NonNull PrecomputedText.Params params) {
            this.mPaint = params.getTextPaint()
            this.mTextDir = params.getTextDirection()
            this.mBreakStrategy = params.getBreakStrategy()
            this.mHyphenationFrequency = params.getHyphenationFrequency()
            this.mWrapped = params
        }

        Params(@NonNull TextPaint textPaint, @NonNull TextDirectionHeuristic textDirectionHeuristic, Int i, Int i2) {
            if (Build.VERSION.SDK_INT >= 28) {
                this.mWrapped = new PrecomputedText.Params.Builder(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build()
            } else {
                this.mWrapped = null
            }
            this.mPaint = textPaint
            this.mTextDir = textDirectionHeuristic
            this.mBreakStrategy = i
            this.mHyphenationFrequency = i2
        }

        public final Boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true
            }
            if (obj == null || !(obj is Params)) {
                return false
            }
            Params params = (Params) obj
            if (this.mWrapped != null) {
                return this.mWrapped.equals(params.mWrapped)
            }
            if (Build.VERSION.SDK_INT < 23 || (this.mBreakStrategy == params.getBreakStrategy() && this.mHyphenationFrequency == params.getHyphenationFrequency())) {
                if ((Build.VERSION.SDK_INT < 18 || this.mTextDir == params.getTextDirection()) && this.mPaint.getTextSize() == params.getTextPaint().getTextSize() && this.mPaint.getTextScaleX() == params.getTextPaint().getTextScaleX() && this.mPaint.getTextSkewX() == params.getTextPaint().getTextSkewX()) {
                    if ((Build.VERSION.SDK_INT < 21 || (this.mPaint.getLetterSpacing() == params.getTextPaint().getLetterSpacing() && TextUtils.equals(this.mPaint.getFontFeatureSettings(), params.getTextPaint().getFontFeatureSettings()))) && this.mPaint.getFlags() == params.getTextPaint().getFlags()) {
                        if (Build.VERSION.SDK_INT >= 24) {
                            if (!this.mPaint.getTextLocales().equals(params.getTextPaint().getTextLocales())) {
                                return false
                            }
                        } else if (Build.VERSION.SDK_INT >= 17 && !this.mPaint.getTextLocale().equals(params.getTextPaint().getTextLocale())) {
                            return false
                        }
                        return this.mPaint.getTypeface() == null ? params.getTextPaint().getTypeface() == null : this.mPaint.getTypeface().equals(params.getTextPaint().getTypeface())
                    }
                    return false
                }
                return false
            }
            return false
        }

        @RequiresApi(23)
        public final Int getBreakStrategy() {
            return this.mBreakStrategy
        }

        @RequiresApi(23)
        public final Int getHyphenationFrequency() {
            return this.mHyphenationFrequency
        }

        @RequiresApi(18)
        @Nullable
        public final TextDirectionHeuristic getTextDirection() {
            return this.mTextDir
        }

        @NonNull
        public final TextPaint getTextPaint() {
            return this.mPaint
        }

        public final Int hashCode() {
            if (Build.VERSION.SDK_INT >= 24) {
                return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency))
            }
            if (Build.VERSION.SDK_INT >= 21) {
                return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency))
            }
            if (Build.VERSION.SDK_INT < 18 && Build.VERSION.SDK_INT < 17) {
                return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency))
            }
            return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency))
        }

        public final String toString() {
            StringBuilder sb = StringBuilder("{")
            sb.append("textSize=" + this.mPaint.getTextSize())
            sb.append(", textScaleX=" + this.mPaint.getTextScaleX())
            sb.append(", textSkewX=" + this.mPaint.getTextSkewX())
            if (Build.VERSION.SDK_INT >= 21) {
                sb.append(", letterSpacing=" + this.mPaint.getLetterSpacing())
                sb.append(", elegantTextHeight=" + this.mPaint.isElegantTextHeight())
            }
            if (Build.VERSION.SDK_INT >= 24) {
                sb.append(", textLocale=" + this.mPaint.getTextLocales())
            } else if (Build.VERSION.SDK_INT >= 17) {
                sb.append(", textLocale=" + this.mPaint.getTextLocale())
            }
            sb.append(", typeface=" + this.mPaint.getTypeface())
            if (Build.VERSION.SDK_INT >= 26) {
                sb.append(", variationSettings=" + this.mPaint.getFontVariationSettings())
            }
            sb.append(", textDir=" + this.mTextDir)
            sb.append(", breakStrategy=" + this.mBreakStrategy)
            sb.append(", hyphenationFrequency=" + this.mHyphenationFrequency)
            sb.append("}")
            return sb.toString()
        }
    }

    class PrecomputedTextFutureTask extends FutureTask {

        class PrecomputedTextCallback implements Callable {
            private Params mParams
            private CharSequence mText

            PrecomputedTextCallback(@NonNull Params params, @NonNull CharSequence charSequence) {
                this.mParams = params
                this.mText = charSequence
            }

            @Override // java.util.concurrent.Callable
            fun call() {
                return PrecomputedTextCompat.create(this.mText, this.mParams)
            }
        }

        PrecomputedTextFutureTask(@NonNull Params params, @NonNull CharSequence charSequence) {
            super(PrecomputedTextCallback(params, charSequence))
        }
    }

    @RequiresApi(28)
    private constructor(@NonNull PrecomputedText precomputedText, @NonNull Params params) {
        this.mText = precomputedText
        this.mParams = params
        this.mParagraphEnds = null
        this.mWrapped = precomputedText
    }

    private constructor(@NonNull CharSequence charSequence, @NonNull Params params, @NonNull Array<Int> iArr) {
        this.mText = SpannableString(charSequence)
        this.mParams = params
        this.mParagraphEnds = iArr
        this.mWrapped = null
    }

    fun create(@NonNull CharSequence charSequence, @NonNull Params params) {
        PrecomputedTextCompat precomputedTextCompat
        Preconditions.checkNotNull(charSequence)
        Preconditions.checkNotNull(params)
        try {
            TraceCompat.beginSection("PrecomputedText")
            if (Build.VERSION.SDK_INT < 28 || params.mWrapped == null) {
                ArrayList arrayList = ArrayList()
                Int length = charSequence.length()
                Int i = 0
                while (i < length) {
                    Int iIndexOf = TextUtils.indexOf(charSequence, LINE_FEED, i, length)
                    i = iIndexOf < 0 ? length : iIndexOf + 1
                    arrayList.add(Integer.valueOf(i))
                }
                Array<Int> iArr = new Int[arrayList.size()]
                for (Int i2 = 0; i2 < arrayList.size(); i2++) {
                    iArr[i2] = ((Integer) arrayList.get(i2)).intValue()
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), params.getTextPaint(), ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED).setBreakStrategy(params.getBreakStrategy()).setHyphenationFrequency(params.getHyphenationFrequency()).setTextDirection(params.getTextDirection()).build()
                } else if (Build.VERSION.SDK_INT >= 21) {
                    StaticLayout(charSequence, params.getTextPaint(), ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
                }
                precomputedTextCompat = PrecomputedTextCompat(charSequence, params, iArr)
            } else {
                precomputedTextCompat = PrecomputedTextCompat(PrecomputedText.create(charSequence, params.mWrapped), params)
            }
            return precomputedTextCompat
        } finally {
            TraceCompat.endSection()
        }
    }

    private fun findParaIndex(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) Int i) {
        for (Int i2 = 0; i2 < this.mParagraphEnds.length; i2++) {
            if (i < this.mParagraphEnds[i2]) {
                return i2
            }
        }
        throw IndexOutOfBoundsException("pos must be less than " + this.mParagraphEnds[this.mParagraphEnds.length - 1] + ", gave " + i)
    }

    @UiThread
    fun getTextFuture(@NonNull CharSequence charSequence, @NonNull Params params, @Nullable Executor executor) {
        PrecomputedTextFutureTask precomputedTextFutureTask = PrecomputedTextFutureTask(params, charSequence)
        if (executor == null) {
            synchronized (sLock) {
                if (sExecutor == null) {
                    sExecutor = Executors.newFixedThreadPool(1)
                }
                executor = sExecutor
            }
        }
        executor.execute(precomputedTextFutureTask)
        return precomputedTextFutureTask
    }

    @Override // java.lang.CharSequence
    fun charAt(Int i) {
        return this.mText.charAt(i)
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    fun getParagraphCount() {
        return Build.VERSION.SDK_INT >= 28 ? this.mWrapped.getParagraphCount() : this.mParagraphEnds.length
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    fun getParagraphEnd(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) Int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex")
        return Build.VERSION.SDK_INT >= 28 ? this.mWrapped.getParagraphEnd(i) : this.mParagraphEnds[i]
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    fun getParagraphStart(@IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) Int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex")
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mWrapped.getParagraphStart(i)
        }
        if (i != 0) {
            return this.mParagraphEnds[i - 1]
        }
        return 0
    }

    @NonNull
    fun getParams() {
        return this.mParams
    }

    @RequiresApi(28)
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getPrecomputedText() {
        if (this.mText is PrecomputedText) {
            return (PrecomputedText) this.mText
        }
        return null
    }

    @Override // android.text.Spanned
    fun getSpanEnd(Object obj) {
        return this.mText.getSpanEnd(obj)
    }

    @Override // android.text.Spanned
    fun getSpanFlags(Object obj) {
        return this.mText.getSpanFlags(obj)
    }

    @Override // android.text.Spanned
    fun getSpanStart(Object obj) {
        return this.mText.getSpanStart(obj)
    }

    @Override // android.text.Spanned
    public Array<Object> getSpans(Int i, Int i2, Class cls) {
        return Build.VERSION.SDK_INT >= 28 ? this.mWrapped.getSpans(i, i2, cls) : this.mText.getSpans(i, i2, cls)
    }

    @Override // java.lang.CharSequence
    fun length() {
        return this.mText.length()
    }

    @Override // android.text.Spanned
    fun nextSpanTransition(Int i, Int i2, Class cls) {
        return this.mText.nextSpanTransition(i, i2, cls)
    }

    @Override // android.text.Spannable
    fun removeSpan(Object obj) {
        if (obj is MetricAffectingSpan) {
            throw IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.")
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mWrapped.removeSpan(obj)
        } else {
            this.mText.removeSpan(obj)
        }
    }

    @Override // android.text.Spannable
    fun setSpan(Object obj, Int i, Int i2, Int i3) {
        if (obj is MetricAffectingSpan) {
            throw IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.")
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mWrapped.setSpan(obj, i, i2, i3)
        } else {
            this.mText.setSpan(obj, i, i2, i3)
        }
    }

    @Override // java.lang.CharSequence
    fun subSequence(Int i, Int i2) {
        return this.mText.subSequence(i, i2)
    }

    @Override // java.lang.CharSequence
    fun toString() {
        return this.mText.toString()
    }
}
