package com.kingja.zsqs.constant;


/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/13 11:10
 * 修改备注：
 */
public class Constants {
    public static final String BASE_FWCQ_URL = "http://tv.chinafwcq.com/";
    //    public static final String BASE_FILE_URL = "http://192.168.0.105:8080/";
    public static final String BASE_FILE_URL = "http://api.fwzspt.cn/";
//    public static final String BASE_URL = "http://192.168.0.65:8085/";//测试
    public static final String BASE_URL = "http://pqs-api.fwzspt.cn/";//测试
    public static final String BASE_FWCQ_IMG_URL = "https://img.yalangke.vip/";
    public static final String APPLICATION_NAME = "YLK";
    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final String LOG_FILENAME = "Logs";
    public static final String DIR_HIL = "JDP";
//    public static final String PROJECT_ID = "BF49A831-1CF3-44C0-9739-EA0C5578F94F";
//    public static final String PROJECT_ID = "BF49A831-1CF3-44C0-9739-EA0C5578F94F";

//    public static final String PROJECT_ID = "582b4797-12f6-4cf2-8fbb-698854b471a1";

    public static final String HOUSEID = "e08f8072-d87b-41a1-9e73-19d52eb80406";
    public static final String IDCARD = "330324195508241355";
    /*face*/
    public static final String APP_ID = "GjAUf3uRfxxGj6JLZZJFtVu6fXPuZ21hEN4TarcQRr3";
    public static final String SDK_KEY = "6Tu9i4bV7BKCk2LmCkMGBC33f6Rn6eXRGjbNRAYA3qJW";

    public interface NETWORK {
        int CONNECTTIMEOUT = 30;
        int WRITETIMEOUT = 40;
        int READTIMEOUT = 40;
    }

    public interface HOUSE_SELECT_TYPE {
        int NONE = 0;
        int ONE = 1;
        int MUL = 2;
    }

    public interface REQUIRE_ROUTE {
        int ATTENTION_LIST = 0;
        int DATA_SHOW = 1;
    }

    public interface REQUEST_CODE {
        int COMMON_SEARCH = 100;
    }

    public interface TIME_MILLISECOND {
        int BANNER = 10 * 1000;
        int DIALOG_CLOSE = 60;
        int FRAGMENT_CLOSE = 120;
        int PREVIEW_CLOSE = 120;
        int BOOT_PAGE_GO = 3000;
    }

    public interface Extra {
        String RESULT_TYPE = "RESULT_TYPE";
        String FILE_TYPE = "FILE_TYPE";
        String ORDER_TYPE = "ORDER_TYPE";
        String ORDER_STATUS = "ORDER_STATUS";
        String TYPE = "TYPE";
        String URL = "URL";
        String TITLE = "TITLE";
        String ID = "ID";
        String ZSID = "ZSID";
        String PROGRESS = "PROGRESS";
        String NEEDTOKEN = "NEEDTOKEN";
        String SEARCH_KEY = "SEARCH_KEY";
        String KEYWORD = "KEYWORD";
        String PROJECTID = "PROJECTID";
        String PROGRESSID = "PROGRESSID";
        String HOUSEID = "HOUSEID";
        String PRICE = "PRICE";
        String CONTENT = "CONTENT";
        String CONFIRMTEXT = "CONFIRMTEXT";
        String FILE_LIST = "FILE_LIST";
        String POSITION = "POSITION";
        String STRING = "STRING";
        String BYTEARRAY = "BYTEARRAY";
    }

    public interface SP_KEY {
        String SEARCH_GROUPBUY = "SEARCH_GROUPBUY";
        String SEARCH_COMPANY = "SEARCH_COMPANY";
        String SEARCH_FENGSHUI = "SEARCH_FENGSHUI";
    }

    //1:待处理  2:服务中 3.待评价  不传为全部
    public interface OrderType {
        Integer TYPE_ALL = 0;
        Integer TYPE_TODO = 1;
        Integer TYPE_SERVICE = 2;
        Integer TYPE_UNEVALUATED = 3;
    }

    //1接受2拒绝
    public interface OrderOperate {
        Integer ACCEPT = 1;
        Integer REFUSE = 2;
    }

    //订单状态 1发起2专家接受3专家拒绝4专家完成5用户取消
    public interface OrderStatus {
        int PUBLISH = 1;
        int ACCEPT = 2;
        int REFUSE = 3;
        int FINISH = 4;
        int CANCEL = 5;
    }

    public interface IntBoolean {
        int NO = 0;
        int YES = 1;
    }

    public interface InformationType {
        int NOTIFICATION = 2;
        int INTRODUCE = 1;
    }

    public interface InformationId {
        int DEC_STRATEGY = 47;
    }

    public interface ImageType {
        String VIDEO = "视频";
        String VR = "VR";
        String IMG = "图片";
    }

    public interface MODEL_TYPE {
        int KVS = 0;
        int IMAGES = 1;
        int TAOS = 2;
    }

    public interface FILE_TYPE {
        int IMG = 0;
        int PDF = 1;
    }

    public interface CODE_FILETYPE {
        int ZHENGSHOUJUEDING = 42;
        int BUCHANGFANGAN = 11;
        int GONGSHIGONGGAO = 41;
    }

    public interface CODE_HOUSEFILETYPE {
        int BUCHANGKUANFAFANG = 0;
        int FANGWUJIESUANDAN = 1;
    }

    public interface CODE_RESULTTYPE {
        int DIAOCHA = 0;
        int RENDING = 1;
        int PINGGU = 2;
        int XIEYI = 3;
    }

    public interface RouterCode {
        int XIANGMUGAIKUANG = 0;
        int ZHENGSHOUJUEDING = 1;
        int BUCHANGFANGAN = 2;
        int GONGSHIGONGGAO = 3;
        int DIAOCHAJIEGUO = 4;
        int RENDINGJIEGUO = 5;
        int PINGGUJIEGUO = 6;
        int FANGWUXIEYI = 7;
        int BUCHAGNKUANFAFANG = 8;
        int FANGWUJIESUANDAN = 9;
        int ANZHIHUXING = 10;
    }

}
