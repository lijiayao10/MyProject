package com.cjy.code.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.cjy.code.cache.LaunderThrowable;

public class FutureRenderer {

    private final ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    void renderPage(CharSequence source) throws InterruptedException, ExecutionException {

        final List<ImageInfo> imageInfos = scanForImageInfo(source);

        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {

            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo info : imageInfos) {
                    result.add(info.downloadImage());
                }

                return result;
            }
        };

        //执行下载图片
        Future<List<ImageData>> future = executorService.submit(task);
        //执行处理文件
        renderText(source);

        try {
            //get处理结果 轮询添加图片
            for (ImageData data : future.get()) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e.getCause());
        }

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
