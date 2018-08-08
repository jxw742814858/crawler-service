package service;

import entity.TaskEntity;

import java.util.List;

public interface TaskFoctory {

    /**
     * 获取mysql任务列表
     * @return
     */
    List<TaskEntity> loadTasks();

    List<TaskEntity> batchConvert(List<TaskEntity> tasks);
}
