package entity;

import java.io.Serializable;

/**
 * 代理信息实体
 */
public class ProxyEntity implements Serializable {

    private String host;
    private Integer port;
    private String agentAccount;
    private String agentPassword;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    public ProxyEntity(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public ProxyEntity(String host, Integer port, String agentAccount, String agentPassword) {
        this.host = host;
        this.port = port;
        this.agentAccount = agentAccount;
        this.agentPassword = agentPassword;
    }
}
