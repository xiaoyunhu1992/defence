package com.buaa.hxy.pojo;

public class HostEntity {
    private Integer hid;

    private String hostName;

    private String IP;

    private String MASK;

    public Integer getId() {
        return hid;
    }

    public void setId(Integer cid) {
        this.hid = hid;
    }

    public String gethostName() {
        return hostName;
    }

    public void sethostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getMASK() {
        return MASK;
    }

    public void setMASK(String MASK) {
        this.MASK = MASK;
    }
  
}

