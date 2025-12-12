package org.jf.baksmali.Adaptors

import androidx.appcompat.R
import java.io.IOException
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.formatter.BaksmaliWriter

class RegisterFormatter {
    public final BaksmaliOptions options
    public final Int parameterRegisterCount
    public final Int registerCount

    constructor(BaksmaliOptions baksmaliOptions, Int i, Int i2) {
        this.options = baksmaliOptions
        this.registerCount = i
        this.parameterRegisterCount = i2
    }

    fun writeRegisterRange(BaksmaliWriter baksmaliWriter, Int i, Int i2) throws IOException {
        if (!this.options.parameterRegisters || i < this.registerCount - this.parameterRegisterCount) {
            baksmaliWriter.write("{v")
            baksmaliWriter.writeSignedIntAsDec(i)
            baksmaliWriter.write(" .. v")
            baksmaliWriter.writeSignedIntAsDec(i2)
            baksmaliWriter.write(125)
            return
        }
        baksmaliWriter.write("{p")
        baksmaliWriter.writeSignedIntAsDec(i - (this.registerCount - this.parameterRegisterCount))
        baksmaliWriter.write(" .. p")
        baksmaliWriter.writeSignedIntAsDec(i2 - (this.registerCount - this.parameterRegisterCount))
        baksmaliWriter.write(125)
    }

    fun writeTo(BaksmaliWriter baksmaliWriter, Int i) throws IOException {
        if (!this.options.parameterRegisters || i < this.registerCount - this.parameterRegisterCount) {
            baksmaliWriter.write(R.styleable.AppCompatTheme_tooltipForegroundColor)
            baksmaliWriter.writeSignedIntAsDec(i)
        } else {
            baksmaliWriter.write(R.styleable.AppCompatTheme_ratingBarStyleSmall)
            baksmaliWriter.writeSignedIntAsDec(i - (this.registerCount - this.parameterRegisterCount))
        }
    }
}
