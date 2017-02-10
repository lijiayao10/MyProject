package com.cjy.code.cache;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2016/1/7.
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();

    private final Computable<A, V>            c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {

                FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                });

                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException c) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Memoizer<Integer, String> memoizer = new Memoizer<>(new Computable<Integer, String>() {

            @Override
            public String compute(Integer arg) throws InterruptedException {
                int i = 0;
                while (i++ < 10000000)
                    ;

                Random random = new Random();
                return random.nextInt(5000) + "";
            }
        });

        for (int i = 0; i < 200; i++) {
            long time = System.currentTimeMillis();
            String value = memoizer.compute(1);
            System.out.println("cache:" + value);

            System.out.println("time:" + (System.currentTimeMillis() - time));
        }
    }

}
