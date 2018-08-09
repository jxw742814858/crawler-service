package thread;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPool {
    public static ScheduledExecutorService threadPool;

    public ThreadPool() {
        threadPool = new ScheduledThreadPoolExecutor(10);
    }
}
