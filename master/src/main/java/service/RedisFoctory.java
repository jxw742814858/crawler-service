package service;

import entity.TaskEntity;

import java.util.List;

public interface RedisFoctory {

    Long getDbSize() throws Exception;

    void flushDb() throws Exception;

    void batchInsert(List<TaskEntity> tasks) throws Exception;

    void cyclicInsert(List<TaskEntity> tasks) throws Exception;
}
