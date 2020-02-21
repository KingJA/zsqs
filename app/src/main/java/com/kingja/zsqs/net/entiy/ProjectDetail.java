package com.kingja.zsqs.net.entiy;

import java.nio.file.attribute.FileTime;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/8 0008 下午 2:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ProjectDetail {

    /**
     * CityName : 温州市
     * StreetName : 松台街道
     * AreaRange : 121213
     * AreaName : 鹿城区
     * Implementor : 温州测试实施单位
     * Agent : 温州民生拆迁事务所有限公司
     * Mapper : 温州测试测绘单位
     * Identifier : 温州年鉴公司
     * Evaluator : 温州评估单位
     * TotalBuildingCount : 54
     * HouseTotalBuildingCount : 30
     * EntTotalBuildingCount : 24
     * TotalBuildingArea : 12098.48
     * HouseTotalBuildingArea : 1687.08
     * EntTotalBuildingArea : 10411.4
     * RedLineFile : {"Id":107,"FileUrl":"Images/project/e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a/43
     * /780fc2a32d3f4d2eb091431d3c891dd9.png","SmallImgUrl":null,"FileClassId":43,"FileName":"cc2","Sort":0,"Type":0}
     * ProjectId : e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a
     * ProjectName : 培训用
     * Address : 河通桥测试
     */

    private String CityName;
    private String StreetName;
    private String AreaRange;
    private String AreaName;
    private String Implementor;
    private String Agent;
    private String Mapper;
    private String Identifier;
    private String Evaluator;
    private int TotalBuildingCount;
    private int HouseTotalBuildingCount;
    private int EntTotalBuildingCount;
    private double TotalBuildingArea;
    private double HouseTotalBuildingArea;
    private double EntTotalBuildingArea;
    private List<FileItem> RedLineFile;
    private String ProjectId;
    private String ProjectName;
    private String Address;

    public String getCityName() {
        return null == CityName ? "" : CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getStreetName() {
        return null == StreetName ? "" : StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getAreaRange() {
        return null == AreaRange ? "" : AreaRange;
    }

    public void setAreaRange(String areaRange) {
        AreaRange = areaRange;
    }

    public String getAreaName() {
        return null == AreaName ? "" : AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getImplementor() {
        return null == Implementor ? "" : Implementor;
    }

    public void setImplementor(String implementor) {
        Implementor = implementor;
    }

    public String getAgent() {
        return null == Agent ? "" : Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getMapper() {
        return null == Mapper ? "" : Mapper;
    }

    public void setMapper(String mapper) {
        Mapper = mapper;
    }

    public String getIdentifier() {
        return null == Identifier ? "" : Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getEvaluator() {
        return null == Evaluator ? "" : Evaluator;
    }

    public void setEvaluator(String evaluator) {
        Evaluator = evaluator;
    }

    public int getTotalBuildingCount() {
        return TotalBuildingCount;
    }

    public void setTotalBuildingCount(int totalBuildingCount) {
        TotalBuildingCount = totalBuildingCount;
    }

    public int getHouseTotalBuildingCount() {
        return HouseTotalBuildingCount;
    }

    public void setHouseTotalBuildingCount(int houseTotalBuildingCount) {
        HouseTotalBuildingCount = houseTotalBuildingCount;
    }

    public int getEntTotalBuildingCount() {
        return EntTotalBuildingCount;
    }

    public void setEntTotalBuildingCount(int entTotalBuildingCount) {
        EntTotalBuildingCount = entTotalBuildingCount;
    }

    public double getTotalBuildingArea() {
        return TotalBuildingArea;
    }

    public void setTotalBuildingArea(double totalBuildingArea) {
        TotalBuildingArea = totalBuildingArea;
    }

    public double getHouseTotalBuildingArea() {
        return HouseTotalBuildingArea;
    }

    public void setHouseTotalBuildingArea(double houseTotalBuildingArea) {
        HouseTotalBuildingArea = houseTotalBuildingArea;
    }

    public double getEntTotalBuildingArea() {
        return EntTotalBuildingArea;
    }

    public void setEntTotalBuildingArea(double entTotalBuildingArea) {
        EntTotalBuildingArea = entTotalBuildingArea;
    }

    public List<FileItem> getRedLineFile() {
        return RedLineFile;
    }

    public void setRedLineFile(List<FileItem> redLineFile) {
        RedLineFile = redLineFile;
    }

    public String getProjectId() {
        return null == ProjectId ? "" : ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return null == ProjectName ? "" : ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getAddress() {
        return null == Address ? "" : Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public static class RedLineFileBean {
        /**
         * Id : 107
         * FileUrl : Images/project/e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a/43/780fc2a32d3f4d2eb091431d3c891dd9.png
         * SmallImgUrl : null
         * FileClassId : 43
         * FileName : cc2
         * Sort : 0
         * Type : 0
         */

        private int Id;
        private String FileUrl;
        private String SmallImgUrl;
        private int FileClassId;
        private String FileName;
        private int Sort;
        private int Type;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getFileUrl() {
            return null == FileUrl ? "" : FileUrl;
        }

        public void setFileUrl(String fileUrl) {
            FileUrl = fileUrl;
        }

        public String getSmallImgUrl() {
            return null == SmallImgUrl ? "" : SmallImgUrl;
        }

        public void setSmallImgUrl(String smallImgUrl) {
            SmallImgUrl = smallImgUrl;
        }

        public int getFileClassId() {
            return FileClassId;
        }

        public void setFileClassId(int fileClassId) {
            FileClassId = fileClassId;
        }

        public String getFileName() {
            return null == FileName ? "" : FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int sort) {
            Sort = sort;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }
    }
}
