package model;

import java.io.Serializable;

/**
 * 任务数据实体
 */
public class TaskModel implements Serializable {

    private String taskName;
    private String taskDescribe;
    private String taskGetAddresses;
    private String taskSubmitAddresses;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe;
    }

    public String getTaskGetAddresses() {
        return taskGetAddresses;
    }

    public void setTaskGetAddresses(String taskGetAddresses) {
        this.taskGetAddresses = taskGetAddresses;
    }

    public String getTaskSubmitAddresses() {
        return taskSubmitAddresses;
    }

    public void setTaskSubmitAddresses(String taskSubmitAddresses) {
        this.taskSubmitAddresses = taskSubmitAddresses;
    }

    public TaskModel(String taskName, String taskDescribe, String taskGetAddresses, String taskSubmitAddresses) {
        this.taskName = taskName;
        this.taskDescribe = taskDescribe;
        this.taskGetAddresses = taskGetAddresses;
        this.taskSubmitAddresses = taskSubmitAddresses;
    }
}
