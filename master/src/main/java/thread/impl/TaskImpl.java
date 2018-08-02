package thread.impl;

import dao.TaskMapper;
import model.TaskModel;
import org.springframework.stereotype.Service;
import thread.Foctory;

import javax.annotation.Resource;
import java.util.List;

@Service("taskImpl")
public class TaskImpl implements Foctory {
    @Resource
    TaskMapper taskMapper;

    @Override
    public List<TaskModel> initTasks() {
        List<TaskModel> tasks = taskMapper.getTasks();
        System.out.println();
        return tasks;
    }
}
