package runner;

import model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import thread.impl.TaskImpl;

import java.util.ArrayList;
import java.util.List;

@Component
public class Master implements ApplicationRunner {
    public static List<TaskModel> TASKS = new ArrayList<>();

    @Autowired
    TaskImpl taskImpl;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        System.out.println();
        TASKS = taskImpl.initTasks();
        System.out.println();
    }
}
