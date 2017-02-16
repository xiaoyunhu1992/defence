package com.buaa.hxy.pojo;

public class AttackerEntity {
    private Integer aid;

    private String hostName;

    private String priviledge;


    public Integer getId() {
        return aid;
    }

    public void setId(Integer cid) {
        this.aid = aid;
    }

    public String gethostName() {
        return hostName;
    }

    public void sethostName(String hostName) {
        this.hostName = hostName;
    }

    public String getpriviledge() {
        return priviledge;
    }

    public void setpriviledge(String priviledge) {
        this.priviledge = priviledge;
    }

  
}

