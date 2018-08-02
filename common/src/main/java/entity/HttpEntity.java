package entity;

import java.io.Serializable;

/**
 * http请求返回消息实体
 */
public class HttpEntity implements Serializable {

    private String html;
    private Integer respCode;

    public String getHtml() {

        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public HttpEntity(String html, Integer respCode) {
        this.html = html;
        this.respCode = respCode;
    }
}
