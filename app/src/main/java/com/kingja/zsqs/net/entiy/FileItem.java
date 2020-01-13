package com.kingja.zsqs.net.entiy;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 上午 11:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FileItem {
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
