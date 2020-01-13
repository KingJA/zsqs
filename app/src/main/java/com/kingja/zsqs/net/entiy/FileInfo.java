package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 上午 9:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FileInfo {

    /**
     * Title : 项目公示文件
     * ProjectFileList : [{"Id":172,"FileUrl":"Images/project/ededcd9a-1436-4e76-91cc-3dae98106202/41
     * /eb086e29960e42b88465c98389540af4.png","SmallImgUrl":"Images/project/ededcd9a-1436-4e76-91cc-3dae98106202/41
     * /300X300eb086e29960e42b88465c98389540af4.png","FileClassId":41,"FileName":"54733_27456","Sort":0,"Type":0},{
     * "Id":167,"FileUrl":"Images/project/ededcd9a-1436-4e76-91cc-3dae98106202/41/7173cc85a5b34c2c819bb2080f818d48
     * .pdf","SmallImgUrl":"","FileClassId":41,"FileName":"公示文件pdf","Sort":1,"Type":1}]
     */

    private String Title;
    private List<FileItem> ProjectFileList;

    public String getTitle() {
        return null == Title ? "" : Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<FileItem> getProjectFileList() {
        return ProjectFileList;
    }

    public void setProjectFileList(List<FileItem> ProjectFileList) {
        this.ProjectFileList = ProjectFileList;
    }

}
