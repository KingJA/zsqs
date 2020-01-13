package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/9 0009 下午 4:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResultInfo {

    /**
     * Title : 认定结果
     * Datas : [{"Value":"[{\"Key\":\"牲口房\",\"Value\":\"66.00m²\",\"Type\":0,\"Sort\":0},{\"Key\":\"地下室\",
     * \"Value\":\"77.00m²\",\"Type\":0,\"Sort\":1},{\"Key\":\"简易房\",\"Value\":\"m²\",\"Type\":0,\"Sort\":2},{\"Key
     * \":\"附属用房\",\"Value\":\"m²\",\"Type\":0,\"Sort\":3},{\"Key\":\"简易房面积\",\"Value\":\"0.00m²\",\"Type\":0,
     * \"Sort\":4},{\"Key\":\"总认定合法面\",\"Value\":\"10.00m²\",\"Type\":0,\"Sort\":6},{\"Key\":\"总不认定合法面\",
     * \"Value\":\"0.00m²\",\"Type\":0,\"Sort\":7}]","Type":0},{"Value":"{\"Key\":\"鉴定资料\",\"Value\":[{\"Id\":16398,
     * \"FileUrl\":\"Images/aaaaaa/e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a/49/Appraise/fb66a82d628041759cb46db5fd8a2136
     * .jpg\",\"SmallImgUrl\":\"Images/aaaaaa/e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a/49/Appraise
     * /300X300fb66a82d628041759cb46db5fd8a2136.jpg\",\"FileClassId\":15,\"FileName\":\"u=4289736282,
     * 3577837529&fm=26&gp=0\",\"Sort\":0,\"Type\":0},{\"Id\":15387,\"FileUrl\":\"Images/555555/e6c00411-4fe9-40b8
     * -bfeb-7b4b0c50a19a/49/Appraise/f4a31e6e8d8b42e68c2567cf53e845b2.png\",
     * \"SmallImgUrl\":\"Images/555555/e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a/49/Appraise
     * /300X300f4a31e6e8d8b42e68c2567cf53e845b2.png\",\"FileClassId\":15,\"FileName\":\"微信图片_20190226150451\",
     * \"Sort\":1,\"Type\":0}],\"Type\":1,\"Sort\":8}","Type":1}]
     */

    private String Title;
    private List<DatasBean> Datas;

    public String getTitle() {
        return null == Title ? "" : Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<DatasBean> getDatas() {
        return Datas;
    }

    public void setDatas(List<DatasBean> Datas) {
        this.Datas = Datas;
    }

    public static class DatasBean {
        /**
         * Value : [{"Key":"牲口房","Value":"66.00m²","Type":0,"Sort":0},{"Key":"地下室","Value":"77.00m²","Type":0,
         * "Sort":1},{"Key":"简易房","Value":"m²","Type":0,"Sort":2},{"Key":"附属用房","Value":"m²","Type":0,"Sort":3},{"Key
         * ":"简易房面积","Value":"0.00m²","Type":0,"Sort":4},{"Key":"总认定合法面","Value":"10.00m²","Type":0,"Sort":6},{"Key
         * ":"总不认定合法面","Value":"0.00m²","Type":0,"Sort":7}]
         * Type : 0
         */

        private String Value;
        private int Type;

        public String getValue() {
            return null == Value ? "" : Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }
    }
}
