package com.buaa.hxy.pojo;

public class LperEntity {
    private Integer lid;

    private String serviceName;

    private String port;
    private String protocol;
    private String sp;
    private String version;
    private String vulID;
    private String orp;
    private String tarp;


    public Integer getId() {
        return lid;
    }

    public void setId(Integer lid) {
        this.lid = lid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setserviceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getport() {
        return port;
    }

    public void setport(String port) {
        this.port = port;
    }
    public String getprotocol() {
        return protocol;
    }

    public void setprotocol(String protocol) {
        this.protocol = protocol;
    }
    public String getsp() {
        return sp;
    }

    public void setsp(String sp) {
        this.sp = sp;
    }
    
    public String getversion() {
        return version;
    }

    public void setversion(String version) {
        this.version = version;
    }
    public String getvulID() {
        return vulID;
    }

    public void setvulID(String vulID) {
        this.vulID = vulID;
    }
    public String getorp() {
        return orp;
    }

    public void setorp(String orp) {
        this.orp = orp;
    }
    public String getarp() {
        return tarp;
    }

    public void settarp(String tarp) {
        this.tarp = tarp;
    }
}

