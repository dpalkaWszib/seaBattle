package pl.dawidpalka;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static long getNextNumber(){
        return counter.incrementAndGet();
    }
}
