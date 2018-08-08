package runner;

import entity.TaskEntity;
import kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import service.impl.RedisImpl;
import service.impl.TaskImpl;
import thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class Master extends ThreadPool implements ApplicationRunner {
    private Logger log = LoggerFactory.getLogger(Master.class);
    Properties prop = PropKit.getProp("config.properties");
    private static List<TaskEntity> tasks = new ArrayList<>();
    private static Long taskSize = -1L;
    private boolean waitStatus = false; // 标识是否处于 载入任务等待时间 的间隔中
    private int execInterval = Integer.valueOf(prop.getProperty("task.exec.interval"));
    private long programStartTime;

    @Autowired
    TaskImpl taskImpl;
    @Autowired
    RedisImpl redisImpl;

    @Override
    public void run(ApplicationArguments applicationArguments) {

        programStartTime = System.currentTimeMillis();

        threadPool.scheduleAtFixedRate(() -> {
            // 不在等待间隔中时， 执行检测任务
            if (waitStatus == false) {
                getTaskSize();

                // 任务队列为空
                if (taskSize == 0L) {
                    waitStatus = true;
                    log.info("The last task queue is completed, and now the next task is written.",
                            execInterval);

                    // 首次执行任务 且 任务队列为空时，无需等待即刻执行，否则休眠间隔时间后执行
                    if (System.currentTimeMillis() - programStartTime > 1000 * 60 * 2) {
                        log.info("The last task queue is completed, and the next task will be written in {} minutes.",
                                execInterval);
                        try {
                            Thread.sleep(1000 * 60 * execInterval);
                        } catch (InterruptedException e) {
                        }
                    }
                    waitStatus = false;

                    // 获取mysql任务
                    if (loadLastestTask()) {
                        log.info("Get the task successfully, size: {}", tasks.size());
                    } else {
                        log.info("Get task failure");
                    }

                    //写入任务到redis
                    if (writeSwapTask()) {
                        log.info("The task is written successfully.");
                    } else {
                        log.info("Task write failure.");
                    }
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * 获取任务队列长度
     */
    private void getTaskSize() {
        try {
            taskSize = redisImpl.getDbSize();
        } catch (Exception e) {
            log.error("", e);
            taskSize = -1L; // 标识检测任务列表长度失败
        }
    }

    /**
     * 获取最新任务集合
     * @return true 获取成功 false 失败
     */
    private boolean loadLastestTask() {
        tasks = taskImpl.loadTasks();
        return tasks.size() > 0;
    }

    /**
     * 写入新任务队列
     * @return true 成功 false 失败
     */
    private boolean writeSwapTask() {
        List<TaskEntity> swapRecords = taskImpl.batchConvert(tasks);
        try {
            redisImpl.batchInsert(swapRecords);
            return true;
        } catch (Exception e) {
            log.error("", e);
        } finally {
            tasks.clear();
        }

        return false;
    }
}
