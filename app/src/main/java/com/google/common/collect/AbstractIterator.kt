package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.NoSuchElementException

abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    public T next
    public State state = State.NOT_READY

    /* renamed from: com.google.common.collect.AbstractIterator$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$google$common$collect$AbstractIterator$State

        static {
            Array<Int> iArr = new Int[State.values().length]
            $SwitchMap$com$google$common$collect$AbstractIterator$State = iArr
            try {
                iArr[State.DONE.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$collect$AbstractIterator$State[State.READY.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    public abstract T computeNext()

    public final T endOfData() {
        this.state = State.DONE
        return null
    }

    @Override // java.util.Iterator
    public final Boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED)
        Int i = AnonymousClass1.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()]
        if (i == 1) {
            return false
        }
        if (i != 2) {
            return tryToComputeNext()
        }
        return true
    }

    @Override // java.util.Iterator
    public final T next() {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        this.state = State.NOT_READY
        T t = (T) NullnessCasts.uncheckedCastNullableTToT(this.next)
        this.next = null
        return t
    }

    public final Boolean tryToComputeNext() {
        this.state = State.FAILED
        this.next = computeNext()
        if (this.state == State.DONE) {
            return false
        }
        this.state = State.READY
        return true
    }
}
