package service.impl;

import constants.LogConst;
import dao.TaskMapper;
import entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.TaskFoctory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("taskImpl")
public class TaskImpl implements TaskFoctory {
    private Logger log = LoggerFactory.getLogger(TaskImpl.class);
    @Resource
    TaskMapper taskMapper;

    @Override
    public List<TaskEntity> loadTasks() {

        List<TaskEntity> tasks = new ArrayList<>();
        try {
            tasks = taskMapper.getTasks();
        } catch (Exception e) {
            log.error(LogConst.MSG_ERROR_DB, "mysql load task", e.getMessage());
        }
        return tasks;
    }

    @Override
    public List<TaskEntity> batchConvert(List<TaskEntity> tasks) {

        return null;
    }
}
