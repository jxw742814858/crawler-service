package dao;

import model.TaskModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select({"select task_name, task_describe, task_get_addresses, task_submit_addresses from task_info"})
    List<TaskModel> getTasks();
}
