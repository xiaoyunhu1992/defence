package com.buaa.hxy.pojo;

public class ServiceEntity {
    private Integer sid;

    private String hostName;
    private String ServiceName;
    private String version;
    private String protocol;
    private String port;
    private String priviledge;


    public Integer getId() {
        return sid;
    }

    public void setId(Integer cid) {
        this.sid = sid;
    }

    public String gethostName() {
        return hostName;
    }

    public void sethostName(String hostName) {
        this.hostName = hostName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getprotocol() {
        return protocol;
    }

    public void setprotocol(String protocol) {
        this.protocol = protocol;
    }
    public String getport() {
        return port;
    }

    public void setport(String port) {
        this.port = port;
    }
    public String getpriviledge() {
        return priviledge;
    }

    public void setpriviledge(String priviledge) {
        this.priviledge = priviledge;
    }
}

