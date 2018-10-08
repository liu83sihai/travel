package com.dce.business.entity.user;

public class UserParentDo {
    private Integer id;

    private Integer userid;

    private Integer parentid;

    private Integer distance;

    private String position;

    private Boolean network;

    private Byte lrDistrict;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getNetwork() {
        return network;
    }

    public void setNetwork(Boolean network) {
        this.network = network;
    }

    public Byte getLrDistrict() {
        return lrDistrict;
    }

    public void setLrDistrict(Byte lrDistrict) {
        this.lrDistrict = lrDistrict;
    }
}