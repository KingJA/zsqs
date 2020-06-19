package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/6/18 0018 下午 3:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectItem {

    /**
     * ProjectId : 25babe5f-fa3c-42f2-a19d-11082fc3f81f
     * ProjectName : 1
     * Address : 2121
     * TotalBuilding : 0
     */

    private String ProjectId;
    private String ProjectName;
    private String Address;
    private int TotalBuilding;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String ProjectId) {
        this.ProjectId = ProjectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getTotalBuilding() {
        return TotalBuilding;
    }

    public void setTotalBuilding(int TotalBuilding) {
        this.TotalBuilding = TotalBuilding;
    }
}
