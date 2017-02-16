package com.buaa.hxy.pojo;

public class SafeEventEntity {
    private Integer eid;

    private String hostName;
    private String serviceName;
    private String priviledge;


    public Integer getId() {
        return eid;
    }

    public void setId(Integer eid) {
        this.eid = eid;
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
    public String getserviceName() {
        return serviceName;
    }

    public void setserviceName(String serviceName) {
        this.serviceName = serviceName;
    }

  
  
}

