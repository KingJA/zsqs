package com.kingja.zsqs.net.api;


import com.kingja.zsqs.net.entiy.BannerItem;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.HomeConfig;
import com.kingja.zsqs.net.entiy.HouseItem;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.net.entiy.PlacementDetail;
import com.kingja.zsqs.net.entiy.PlacementItem;
import com.kingja.zsqs.net.entiy.ProjectBaseInfo;
import com.kingja.zsqs.net.entiy.ProjectDetail;
import com.kingja.zsqs.net.entiy.ProjectIdResult;
import com.kingja.zsqs.net.entiy.ResultInfo;
import com.kingja.zsqs.net.entiy.UpdateResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 项目名称：和Api相关联
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/12 16:32
 * 修改备注：
 */
public interface ApiService {

    /*项目概况*/
    @GET("Project/GetProjectInfo")
    Observable<HttpResult<ProjectDetail>> getDecorationDetail(@Query("projectId") String projectId);

    /*项目文件*/
    @GET("Project/GetProjectFile")
    Observable<HttpResult<FileInfo>> getFileInfo(@Query("projectId") String projectId, @Query("fileType") int fileType);

    /*房产文件*/
    @GET("House/GetMyHouseFiles")
    Observable<HttpResult<FileInfo>> getHouseFileInfo(@Query("projectId") String projectId,
                                                      @Query("houseId") String houseId,
                                                      @Query("fileType") int fileType,
                                                      @Query("BuildingType") int buildingType);

    /*结果页面*/
    @GET("House/GetMyHouseInfo")
    Observable<HttpResult<ResultInfo>> getResultInfo(@Query("projectId") String projectId,
                                                     @Query("HouseId") String HouseId,
                                                     @Query("queryType") int queryType,
                                                     @Query("BuildingType") int buildingType);

    /*登录*/
    @GET("House/GetMyHousePersonInfo")
    Observable<HttpResult<LoginInfo>> login(@Query("projectId") String projectId, @Query("idcard") String idcard,
                                            @Query("sceneAddress") String sceneAddress, @Query("deviceCode") String deviceCode);

    /*登录*/
    @GET("House/GetMyHouseList")
    Observable<HttpResult<List<HouseItem>>> getHouseList(@Query("projectId") String projectId,
                                                         @Query("idcard") String idcard);


    /*==================================房屋拆迁网=========================================*/
    /*安置户型列表*/
    @Headers("urlname:fwcq")
    @GET("progress_house_plan/list")
    Observable<HttpResult<List<PlacementItem>>> getPlacementList(@Query("fwzs_project_id") String projectId);

    /*安置户型详情*/
    @Headers("urlname:fwcq")
    @GET("progress_house_plan/{progress_house_plan_id}")
    Observable<HttpResult<PlacementDetail>> getPlacementDetail(@Path("progress_house_plan_id") int progressId);

    /*装修预约*/
    @Headers("urlname:fwcq")
    @POST("renovation_reserve/add")
    Observable<HttpResult<Boolean>> decorateAppoint(@Body RequestBody requestBody);

    /*装修报价*/
    @Headers("urlname:fwcq")
    @POST("renovation_offer/offer")
    Observable<HttpResult<String>> decorateOffer(@Body RequestBody requestBody);

    /*获取配置*/
    @Headers("urlname:fwcq")
    @GET("common/public_config")
    Observable<HttpResult<HomeConfig>> getHomeConfig(@Query("device_code") String deviceCode);

    /*轮播图*/
    @Headers("urlname:fwcq")
    @GET("slide/get_slide")
    Observable<HttpResult<List<BannerItem>>> getBanner(@Query("device_code") String deviceCode);

    /*获取项目基本信息*/
    @FormUrlEncoded
    @POST("/project/CheckProjectId")
    Observable<HttpResult<ProjectBaseInfo>> getProjectInfo(@Field("projectId") String projectId);


    /*检查更新*/
    @Headers("urlname:fwcq")
    @FormUrlEncoded
    @POST("common/tv_update")
    Observable<HttpResult<UpdateResult>> checkUpdate(@Field("version") String versionCode);


    /*获取房屋征收项目id*/
    @Headers("urlname:fwcq")
    @GET("common/get_fwzs_project_id")
    Observable<HttpResult<ProjectIdResult>> getProjectId(@Query("device_code") String deviceCode);


}
