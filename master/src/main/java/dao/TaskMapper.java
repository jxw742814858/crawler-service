package dao;

import entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select({"select ti.task_name, si.id as site_id, si.site_name, pri.plate_name, pri.url, pri.url_dom, " +
            "pri.content_dom, pri.list_time_dom, pri.detail_time_dom, pri.time_from_url, ti.task_submit_addresses " +
            "from task_plate_ids tpi " +
            "right join plate_rule_info pri on tpi.plate_id = pri.id " +
            "LEFT join task_info ti on task_id = ti.id " +
            "LEFT join site_info si on pri.site_id = si.id " +
            "order by rand()"})
    List<TaskEntity> getTasks();
}
