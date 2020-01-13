package com.kingja.zsqs.net.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/1/13 0013 上午 10:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementDetail {

    /**
     * progress_house_plan_id : 28
     * progress_house_plan_name : A-1户型
     * house_plan_url : progress_house_plan_image/20191127/b9141b02ccb94035a2e4fbeb3fefa4e442e87ab6.jpg
     * hall : 2
     * room : 4
     * toilet : 2
     * area : 159.05
     * towards : 2
     * towards_name : 南
     * house_plan_renovation_case : [{"renovation_case_id":46,"style":7,
     * "vr_img_url":"renovation_case_vr_image/20190827/a174970804292c09579f14ff8ff89b08d332653f.jpg",
     * "vr_url":"https://www.720yuntu.com/tour/8860c9942cc307f9","hall":2,"room":3,"toilet":2,"style_name":"欧式",
     * "pivot":{"progress_house_plan_id":28,"renovation_case_id":46}},{"renovation_case_id":47,"style":1,
     * "vr_img_url":"renovation_case_vr_image/20190827/f99e30b415717b2e7bf8c038de027663d68959d6.jpg",
     * "vr_url":"https://www.720yuntu.com/tour/6c2023bbb52a4eb4","hall":2,"room":2,"toilet":1,"style_name":"现代",
     * "pivot":{"progress_house_plan_id":28,"renovation_case_id":47}}]
     */

    private int progress_house_plan_id;
    private String progress_house_plan_name;
    private String house_plan_url;
    private int hall;
    private int room;
    private int toilet;
    private String area;
    private int towards;
    private String towards_name;
    private List<HousePlanRenovationCaseBean> house_plan_renovation_case;

    public int getProgress_house_plan_id() {
        return progress_house_plan_id;
    }

    public void setProgress_house_plan_id(int progress_house_plan_id) {
        this.progress_house_plan_id = progress_house_plan_id;
    }

    public String getProgress_house_plan_name() {
        return null == progress_house_plan_name ? "" : progress_house_plan_name;
    }

    public void setProgress_house_plan_name(String progress_house_plan_name) {
        this.progress_house_plan_name = progress_house_plan_name;
    }

    public String getHouse_plan_url() {
        return null == house_plan_url ? "" : house_plan_url;
    }

    public void setHouse_plan_url(String house_plan_url) {
        this.house_plan_url = house_plan_url;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getToilet() {
        return toilet;
    }

    public void setToilet(int toilet) {
        this.toilet = toilet;
    }

    public String getArea() {
        return null == area ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getTowards() {
        return towards;
    }

    public void setTowards(int towards) {
        this.towards = towards;
    }

    public String getTowards_name() {
        return null == towards_name ? "" : towards_name;
    }

    public void setTowards_name(String towards_name) {
        this.towards_name = towards_name;
    }

    public List<HousePlanRenovationCaseBean> getHouse_plan_renovation_case() {
        return house_plan_renovation_case;
    }

    public void setHouse_plan_renovation_case(List<HousePlanRenovationCaseBean> house_plan_renovation_case) {
        this.house_plan_renovation_case = house_plan_renovation_case;
    }

    public static class HousePlanRenovationCaseBean {
        /**
         * renovation_case_id : 46
         * style : 7
         * vr_img_url : renovation_case_vr_image/20190827/a174970804292c09579f14ff8ff89b08d332653f.jpg
         * vr_url : https://www.720yuntu.com/tour/8860c9942cc307f9
         * hall : 2
         * room : 3
         * toilet : 2
         * style_name : 欧式
         * pivot : {"progress_house_plan_id":28,"renovation_case_id":46}
         */

        private int renovation_case_id;
        private int style;
        private String vr_img_url;
        private String vr_url;
        private int hall;
        private int room;
        private int toilet;
        private String style_name;
        private PivotBean pivot;

        public int getRenovation_case_id() {
            return renovation_case_id;
        }

        public void setRenovation_case_id(int renovation_case_id) {
            this.renovation_case_id = renovation_case_id;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getVr_img_url() {
            return null == vr_img_url ? "" : vr_img_url;
        }

        public void setVr_img_url(String vr_img_url) {
            this.vr_img_url = vr_img_url;
        }

        public String getVr_url() {
            return null == vr_url ? "" : vr_url;
        }

        public void setVr_url(String vr_url) {
            this.vr_url = vr_url;
        }

        public int getHall() {
            return hall;
        }

        public void setHall(int hall) {
            this.hall = hall;
        }

        public int getRoom() {
            return room;
        }

        public void setRoom(int room) {
            this.room = room;
        }

        public int getToilet() {
            return toilet;
        }

        public void setToilet(int toilet) {
            this.toilet = toilet;
        }

        public String getStyle_name() {
            return null == style_name ? "" : style_name;
        }

        public void setStyle_name(String style_name) {
            this.style_name = style_name;
        }

        public PivotBean getPivot() {
            return pivot;
        }

        public void setPivot(PivotBean pivot) {
            this.pivot = pivot;
        }

        public static class PivotBean {
            /**
             * progress_house_plan_id : 28
             * renovation_case_id : 46
             */

            private int progress_house_plan_id;
            private int renovation_case_id;

            public int getProgress_house_plan_id() {
                return progress_house_plan_id;
            }

            public void setProgress_house_plan_id(int progress_house_plan_id) {
                this.progress_house_plan_id = progress_house_plan_id;
            }

            public int getRenovation_case_id() {
                return renovation_case_id;
            }

            public void setRenovation_case_id(int renovation_case_id) {
                this.renovation_case_id = renovation_case_id;
            }
        }
    }
}
