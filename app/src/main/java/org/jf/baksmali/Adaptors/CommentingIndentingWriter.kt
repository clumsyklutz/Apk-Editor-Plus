package org.jf.baksmali.Adaptors

import java.io.IOException
import java.io.Writer
import org.jf.util.IndentingWriter

class CommentingIndentingWriter extends IndentingWriter {
    constructor(Writer writer) {
        super(writer)
    }

    @Override // org.jf.util.IndentingWriter
    fun writeIndent() throws IOException {
        this.writer.write("# ")
        super.writeIndent()
    }
}
