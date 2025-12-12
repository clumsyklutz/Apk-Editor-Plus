package org.apache.commons.io

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Charsets {
    static {
        Charset charset = StandardCharsets.ISO_8859_1
        Charset charset2 = StandardCharsets.US_ASCII
        Charset charset3 = StandardCharsets.UTF_16
        Charset charset4 = StandardCharsets.UTF_16BE
        Charset charset5 = StandardCharsets.UTF_16LE
        Charset charset6 = StandardCharsets.UTF_8
    }

    fun toCharset(String str) {
        return str == null ? Charset.defaultCharset() : Charset.forName(str)
    }

    fun toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset
    }
}
