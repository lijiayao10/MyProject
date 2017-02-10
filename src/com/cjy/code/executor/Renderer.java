package com.cjy.code.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.cjy.code.cache.LaunderThrowable;

public class Renderer {

    private final ExecutorService executor;

    public Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) throws InterruptedException, ExecutionException {

        final List<ImageInfo> imageInfos = scanForImageInfo(source);

        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);

        for (final ImageInfo info : imageInfos) {
            completionService.submit(new Callable<Renderer.ImageData>() {

                @Override
                public ImageData call() throws Exception {

                    return info.downloadImage();
                }
            });
        }

        //执行处理文件
        renderText(source);

        try {
            //get处理结果 轮询添加图片
            //            Future<ImageData> data;
            //            while ((data = completionService.take()) != null) {
            //                renderImage(data.get());
            //            }
            for (int i = 0; i < imageInfos.size(); i++) {
                Future<ImageData> data = completionService.take();
                renderImage(data.get());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e.getCause());
        }

    }

    static final long TIME_BUDGET = 1000;

    String renderPageWithAd() throws InterruptedException, ExecutionException, TimeoutException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<String> f = executor.submit(new Work(10000l));

        long timeLeft = endNanos - System.nanoTime();
        return f.get(timeLeft, TimeUnit.NANOSECONDS);

    }

    static class Work implements Callable<String> {

        final long time;

        public Work(long time) {
            this.time = time;
        }

        @Override
        public String call() throws Exception {
            Thread.currentThread().sleep(time);
            return "我睡眠了这么久time:" + time + "";
        }
    }

    class Ad {
    }

    class Page {

    }

    class ImageData {
    }

    interface ImageInfo {
        public ImageData downloadImage();
    }

    static List<ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }

    static void renderText(CharSequence source) {

    }

    static void renderImage(ImageData source) {

    }

}
