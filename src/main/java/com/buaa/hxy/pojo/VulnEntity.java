package com.buaa.hxy.pojo;

public class VulnEntity {
    private Integer vid;

    private String hostName;

    private String VulID;

    private String ServiceName;

    public Integer getId() {
        return vid;
    }

    public void setId(Integer cid) {
        this.vid = vid;
    }

    public String gethostName() {
        return hostName;
    }

    public void sethostName(String hostName) {
        this.hostName = hostName;
    }

    public String getVulID() {
        return VulID;
    }

    public void setVulID(String VulID) {
        this.VulID = VulID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

}

