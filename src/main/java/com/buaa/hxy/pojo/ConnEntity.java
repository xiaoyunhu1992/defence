package com.buaa.hxy.pojo;

public class ConnEntity {
    private Integer cid;

    private String sourceName;

    private String desName;

    private String protocol;
    private String port;

    public Integer getId() {
        return cid;
    }

    public void setId(Integer cid) {
        this.cid = cid;
    }

    public String getsourceName() {
        return sourceName;
    }

    public void setsourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getdesName() {
        return desName;
    }

    public void setdesName(String desName) {
        this.desName = desName;
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
}

