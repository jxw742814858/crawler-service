package entity;

import java.io.Serializable;

/**
 * redis任务记录模型
 */
public class TaskEntity implements Serializable {

    private String taskName;
    private Integer siteId;
    private String siteName;
    private String plateName;
    private String url;
    private String urlDom;
    private String contentDom;
    private String listTimeDom;
    private String detailTimeDom;
    private Boolean timeFromUrl;
    private String taskSumbitAddresses;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlDom() {
        return urlDom;
    }

    public void setUrlDom(String urlDom) {
        this.urlDom = urlDom;
    }

    public String getContentDom() {
        return contentDom;
    }

    public void setContentDom(String contentDom) {
        this.contentDom = contentDom;
    }

    public String getListTimeDom() {
        return listTimeDom;
    }

    public void setListTimeDom(String listTimeDom) {
        this.listTimeDom = listTimeDom;
    }

    public String getDetailTimeDom() {
        return detailTimeDom;
    }

    public void setDetailTimeDom(String detailTimeDom) {
        this.detailTimeDom = detailTimeDom;
    }

    public Boolean getTimeFromUrl() {
        return timeFromUrl;
    }

    public void setTimeFromUrl(Boolean timeFromUrl) {
        this.timeFromUrl = timeFromUrl;
    }

    public String getTaskSumbitAddresses() {
        return taskSumbitAddresses;
    }

    public void setTaskSumbitAddresses(String taskSumbitAddresses) {
        this.taskSumbitAddresses = taskSumbitAddresses;
    }

    public TaskEntity(String taskName, Integer siteId, String siteName, String plateName, String url, String urlDom,
                      String contentDom, String listTimeDom, String detailTimeDom, Boolean timeFromUrl,
                      String taskSumbitAddresses) {
        this.taskName = taskName;
        this.siteId = siteId;
        this.siteName = siteName;
        this.plateName = plateName;
        this.url = url;
        this.urlDom = urlDom;
        this.contentDom = contentDom;
        this.listTimeDom = listTimeDom;
        this.detailTimeDom = detailTimeDom;
        this.timeFromUrl = timeFromUrl;
        this.taskSumbitAddresses = taskSumbitAddresses;
    }
}
