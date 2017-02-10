package com.cjy.code.lock;

import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {

    private static class IntPair {
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> values = new AtomicReference<CasNumberRange.IntPair>(
            new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int lower) {
        while (true) {
            IntPair oldIntPair = values.get();
            IntPair newIntPair = new IntPair(lower, oldIntPair.upper);

            if (values.compareAndSet(oldIntPair, newIntPair)) {
                return;
            }
        }
    }

    public void setUpper(int upper) {
        while (true) {
            IntPair oldIntPair = values.get();
            IntPair newIntPair = new IntPair(oldIntPair.lower, upper);

            if (values.compareAndSet(oldIntPair, newIntPair)) {
                return;
            }
        }
    }
}
