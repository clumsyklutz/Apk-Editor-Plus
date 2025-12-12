package org.antlr.runtime

class CommonTokenStream extends BufferedTokenStream {
    public Int channel

    constructor(TokenSource tokenSource) {
        super(tokenSource)
        this.channel = 0
    }

    fun LB(Int i) {
        if (i != 0) {
            Int iSkipOffTokenChannelsReverse = this.p
            if (iSkipOffTokenChannelsReverse - i >= 0) {
                for (Int i2 = 1; i2 <= i; i2++) {
                    iSkipOffTokenChannelsReverse = skipOffTokenChannelsReverse(iSkipOffTokenChannelsReverse - 1)
                }
                if (iSkipOffTokenChannelsReverse < 0) {
                    return null
                }
                return this.tokens.get(iSkipOffTokenChannelsReverse)
            }
        }
        return null
    }

    @Override // org.antlr.runtime.BufferedTokenStream, org.antlr.runtime.TokenStream
    fun LT(Int i) {
        if (this.p == -1) {
            setup()
        }
        if (i == 0) {
            return null
        }
        if (i < 0) {
            return LB(-i)
        }
        Int iSkipOffTokenChannels = this.p
        for (Int i2 = 1; i2 < i; i2++) {
            iSkipOffTokenChannels = skipOffTokenChannels(iSkipOffTokenChannels + 1)
        }
        if (iSkipOffTokenChannels > this.range) {
            this.range = iSkipOffTokenChannels
        }
        return this.tokens.get(iSkipOffTokenChannels)
    }

    @Override // org.antlr.runtime.IntStream
    fun consume() {
        if (this.p == -1) {
            setup()
        }
        Int i = this.p + 1
        this.p = i
        sync(i)
        while (this.tokens.get(this.p).getChannel() != this.channel) {
            Int i2 = this.p + 1
            this.p = i2
            sync(i2)
        }
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    fun setup() {
        Int i = 0
        this.p = 0
        sync(0)
        while (this.tokens.get(i).getChannel() != this.channel) {
            i++
            sync(i)
        }
        this.p = i
    }

    fun skipOffTokenChannels(Int i) {
        sync(i)
        while (this.tokens.get(i).getChannel() != this.channel) {
            i++
            sync(i)
        }
        return i
    }

    fun skipOffTokenChannelsReverse(Int i) {
        while (i >= 0 && this.tokens.get(i).getChannel() != this.channel) {
            i--
        }
        return i
    }
}
