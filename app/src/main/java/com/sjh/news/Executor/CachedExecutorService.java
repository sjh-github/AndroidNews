package com.sjh.news.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 25235 on 2018/7/11.
 */

public class CachedExecutorService {
    public static ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
}
