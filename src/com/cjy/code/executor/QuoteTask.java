package com.cjy.code.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.cjy.code.executor.QuoteTask.TravelQuote;

public class QuoteTask implements Callable<TravelQuote> {

    private final TravelCompany company;

    private final TravelInfo    travelInfo;

    private ExecutorService     executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    static class TravelQuote {

    }

    TravelQuote getFailureQuote(Throwable throwable) {
        return null;
    }

    TravelQuote getTimeoutQuote(Exception e) {
        return null;
    }

    static class TravelCompany {
        public TravelQuote solicitQuote(TravelInfo travelInfo) {
            return null;
        }
    }

    static class TravelInfo {
    }

    @Override
    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companys,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit)
                                                           throws InterruptedException {
        List<QuoteTask> quoteTasks = new ArrayList<>();

        for (TravelCompany company : companys) {
            quoteTasks.add(new QuoteTask(company, travelInfo));
        }

        List<TravelQuote> quotes = new ArrayList<>(quoteTasks.size());

        List<Future<TravelQuote>> futures = executorService.invokeAll(quoteTasks, time, unit);

        Iterator<QuoteTask> iterator = quoteTasks.iterator();

        for (Future<TravelQuote> f : futures) {
            QuoteTask task = iterator.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));

                System.out.println("报错:" + e.getCause());

            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
                System.out.println("超时的线程+" + e);
            }
        }

        Collections.sort(quotes, ranking);
        return quotes;
    }
}
