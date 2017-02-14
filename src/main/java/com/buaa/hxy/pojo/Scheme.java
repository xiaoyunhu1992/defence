package com.buaa.hxy.pojo;

public class Scheme {
    private Integer sid;

    private String component;

    private Float risk;

    private Float cost;

    public Integer getId() {
        return sid;
    }

    public void setId(Integer sid) {
        this.sid = sid;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public Float getRisk() {
        return risk;
    }

    public void setRisk(Float risk) {
        this.risk = risk;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}

