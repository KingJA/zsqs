package com.kingja.zsqs.ui.login;

import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.zsqs.R;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.base.IStackActivity;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.event.LoginStatusEvent;
import com.kingja.zsqs.face.FaceSir;
import com.kingja.zsqs.face.faceserver.CompareResult;
import com.kingja.zsqs.face.faceserver.FaceServer;
import com.kingja.zsqs.face.model.DrawInfo;
import com.kingja.zsqs.face.model.FacePreviewInfo;
import com.kingja.zsqs.face.util.ConfigUtil;
import com.kingja.zsqs.face.util.DrawHelper;
import com.kingja.zsqs.face.util.camera.CameraHelper;
import com.kingja.zsqs.face.util.camera.CameraListener;
import com.kingja.zsqs.face.util.face.FaceHelper;
import com.kingja.zsqs.face.util.face.FaceListener;
import com.kingja.zsqs.face.util.face.LivenessType;
import com.kingja.zsqs.face.util.face.RecognizeColor;
import com.kingja.zsqs.face.util.face.RequestFeatureStatus;
import com.kingja.zsqs.face.util.face.RequestLivenessStatus;
import com.kingja.zsqs.face.widget.FaceRectView;
import com.kingja.zsqs.face.widget.FaceSearchResultAdapter;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.LoginInfo;
import com.kingja.zsqs.net.entiy.StringWrap;
import com.kingja.zsqs.service.InitializeService;
import com.kingja.zsqs.utils.CheckUtil;
import com.kingja.zsqs.utils.SpSir;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.BaseDialogFragment;
import com.kingja.zsqs.view.dialog.DialogDoubleFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:TODO
 * Create Time:2020/4/20 0020 下午 3:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginByFaceFragment extends BaseTitleFragment implements ViewTreeObserver.OnGlobalLayoutListener, LoginContract.View {
    @BindView(R.id.single_camera_texture_preview)
    TextureView previewView;
    @BindView(R.id.single_camera_face_rect_view)
    FaceRectView faceRectView;
    @BindView(R.id.single_camera_recycler_view_person)
    RecyclerView recyclerShowFaceInfo;
    Unbinder unbinder;
    @BindView(R.id.ll_inputBar)
    LinearLayout llInputBar;
    Unbinder unbinder1;
    @BindView(R.id.sset_id)
    SuperShapeEditText ssetId;
    private FaceSir faceSir;
    private DialogDoubleFragment goBindDialog;
    private String idcard;
    private DialogDoubleFragment confirmIdcardDialog;
    @Inject
    LoginPresenter loginPresenter;

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_four, R.id.iv_five, R.id.iv_six, R.id.iv_seven,
            R.id.iv_eight, R.id.iv_nine, R.id.iv_zero, R.id.iv_delete, R.id.iv_empty, R.id.iv_confirm})
    void onclick(View v) {

        switch (v.getId()) {
            case R.id.sstv_face_login:
                ((IStackActivity) Objects.requireNonNull(getActivity())).addStack(new LoginByFaceFragment());
                break;
            case R.id.iv_confirm:
                idcard = ssetId.getText().toString().trim();
                if (CheckUtil.checkIdCard(idcard, "身份证号码格式不正确")) {
                    register();
                }
                break;
            case R.id.iv_empty:
                ssetId.setText("");
                break;
            case R.id.iv_delete:
                idcard = ssetId.getText().toString().trim();
                int length = idcard.length();
                if (length > 0) {
                    ssetId.setText(idcard.substring(0, length - 1));
                    ssetId.setSelection(ssetId.getText().length());
                }
                break;
            case R.id.iv_zero:
                input("0");
                break;
            case R.id.iv_one:
                input("1");
                break;
            case R.id.iv_two:
                input("2");
                break;
            case R.id.iv_three:
                input("3");
                break;
            case R.id.iv_four:
                input("4");
                break;
            case R.id.iv_five:
                input("5");
                break;
            case R.id.iv_six:
                input("6");
                break;
            case R.id.iv_seven:
                input("7");
                break;
            case R.id.iv_eight:
                input("8");
                break;
            case R.id.iv_nine:
                input("9");
                break;
        }
    }

    public void input(String number) {
        String content = ssetId.getText().toString().trim();
        ssetId.setText(content + number);
        ssetId.setSelection(ssetId.getText().length());
    }

    @Override
    protected void initVariable() {
        //本地人脸库初始化
        FaceServer.getInstance().init(getActivity());
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        loginPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        compareResultList = new ArrayList<>();
        adapter = new FaceSearchResultAdapter(compareResultList, getActivity());
        recyclerShowFaceInfo.setAdapter(adapter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int spanCount = (int) (dm.widthPixels / (getResources().getDisplayMetrics().density * 100 + 0.5f));
        recyclerShowFaceInfo.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerShowFaceInfo.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void initData() {
        goBindDialog = DialogDoubleFragment.newInstance("未匹配到人脸数据，是否进行人脸绑定", "好的");

    }

    @Override
    public void initNet() {
    }

    @OnClick({R.id.btn_register, R.id.btn_switchCamera})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_switchCamera:
                switchCamera();
                break;
        }
    }

    @Override
    protected String getTitle() {
        return "刷脸登录";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_login_face;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return false;
    }


    private static final String TAG = "RegisterAndRecognize";
    private static final int MAX_DETECT_NUM = 10;
    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static final int WAIT_LIVENESS_INTERVAL = 100;
    /**
     * 失败重试间隔时间（ms）
     */
    private static final long FAIL_RETRY_INTERVAL = 1000;
    private static final int MAX_RETRY_TIME = 6;

    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    /**
     * 优先打开的摄像头，本界面主要用于单目RGB摄像头设备，因此默认打开前置
     */
    private Integer rgbCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private FaceHelper faceHelper;
    private List<CompareResult> compareResultList;
    private FaceSearchResultAdapter adapter;
    /**
     * 活体检测的开关
     */
    private boolean livenessDetect = true;
    /**
     * 注册人脸状态码，准备注册
     */
    private static final int REGISTER_STATUS_READY = 0;
    /**
     * 注册人脸状态码，注册中
     */
    private static final int REGISTER_STATUS_PROCESSING = 1;
    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
    private static final int REGISTER_STATUS_DONE = 2;

    private int registerStatus = REGISTER_STATUS_DONE;
    /**
     * 用于记录人脸识别相关状态
     */
    private ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    /**
     * 用于记录人脸特征提取出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> extractErrorRetryMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体值
     */
    private ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体检测出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> livenessErrorRetryMap = new ConcurrentHashMap<>();

    private CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();
    private CompositeDisposable delayFaceTaskCompositeDisposable = new CompositeDisposable();
    /**
     * 识别阈值
     */
    private static final float SIMILAR_THRESHOLD = 0.8F;

    private void initEngine() {
        faceSir = new FaceSir(getActivity());
        faceSir.init();
    }

    /**
     * 销毁引擎，faceHelper中可能会有特征提取耗时操作仍在执行，加锁防止crash
     */
    private void unInitEngine() {
        if (faceSir != null) {
            faceSir.unInitEngine();
        }
    }


    @Override
    public void onDestroyView() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
        if (getFeatureDelayedDisposables != null) {
            getFeatureDelayedDisposables.clear();
        }
        if (delayFaceTaskCompositeDisposable != null) {
            delayFaceTaskCompositeDisposable.clear();
        }
        if (faceHelper != null) {
            ConfigUtil.setTrackedFaceCount(getActivity(), faceHelper.getTrackedFaceCount());
            faceHelper.release();
            faceHelper = null;
        }
        FaceServer.getInstance().unInit();
        super.onDestroyView();
    }

    private void initCamera() {
        final FaceListener faceListener = new FaceListener() {
            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "FaceListener onFail: " + e.getMessage());
            }

            //请求人脸特征后的回调
            @Override
            public void onFaceFeatureInfoGet(@Nullable final FaceFeature faceFeature, final Integer requestId,
                                             final Integer errorCode) {
                if (faceFeature != null) {
                    searchFace(faceFeature, requestId);
                } else {
                    Log.e(TAG, "特征提取失败: ");
                    retryRecognizeDelayed(requestId);
                }
            }

            @Override
            public void onFaceLivenessInfoGet(@Nullable LivenessInfo livenessInfo, final Integer requestId,
                                              Integer errorCode) {
            }
        };


        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Camera.Size lastPreviewSize = previewSize;
                previewSize = camera.getParameters().getPreviewSize();
                /*初始化绘制助手*/
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(),
                        previewView.getHeight(), displayOrientation
                        , cameraId, isMirror, false, false);
                // 切换相机的时候可能会导致预览尺寸发生变化
                if (faceHelper == null ||
                        lastPreviewSize == null ||
                        lastPreviewSize.width != previewSize.width || lastPreviewSize.height != previewSize.height) {
                    Integer trackedFaceCount = null;
                    // 记录切换时的人脸序号
                    if (faceHelper != null) {
                        trackedFaceCount = faceHelper.getTrackedFaceCount();
                        faceHelper.release();
                    }
                    faceHelper = faceSir.getFaceHelper(previewSize, faceListener, trackedFaceCount);
                }
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                /*清理人脸识别框*/
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                /*从图片中获取人脸*/
                List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);
                if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
                    drawPreviewInfo(facePreviewInfoList);
                }
                registerFace(nv21, facePreviewInfoList);
                clearLeftFace(facePreviewInfoList);

                if (facePreviewInfoList != null && facePreviewInfoList.size() > 0 && previewSize != null) {
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        Integer status = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());
                        /**
                         * 在活体检测开启，在人脸识别状态不为成功或人脸活体状态不为处理中（ANALYZING）且不为处理完成（ALIVE、NOT_ALIVE）时重新进行活体检测
                         */
                        if (livenessDetect && (status == null || status != RequestFeatureStatus.SUCCEED)) {
                            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
                            if (liveness == null
                                    || (liveness != LivenessInfo.ALIVE && liveness != LivenessInfo.NOT_ALIVE && liveness != RequestLivenessStatus.ANALYZING)) {
                                livenessMap.put(facePreviewInfoList.get(i).getTrackId(),
                                        RequestLivenessStatus.ANALYZING);
                                faceHelper.requestFaceLiveness(nv21, facePreviewInfoList.get(i).getFaceInfo(),
                                        previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21,
                                        facePreviewInfoList.get(i).getTrackId(), LivenessType.RGB);
                            }
                        }
                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求特征提取（可根据需要添加其他判断以限制特征提取次数），
                         * 特征提取回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer, Integer)}中回传
                         */
                        if (status == null
                                || status == RequestFeatureStatus.TO_RETRY) {
                            requestFeatureStatusMap.put(facePreviewInfoList.get(i).getTrackId(),
                                    RequestFeatureStatus.SEARCHING);
                            faceHelper.requestFaceFeature(nv21, facePreviewInfoList.get(i).getFaceInfo(),
                                    previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21,
                                    facePreviewInfoList.get(i).getTrackId());
                        }
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };

        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(getActivity().getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(rgbCameraID != null ? rgbCameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }

    private void registerFace(final byte[] nv21, final List<FacePreviewInfo> facePreviewInfoList) {
        if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
            registerStatus = REGISTER_STATUS_PROCESSING;
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) {

                    boolean success = FaceServer.getInstance().registerNv21(getActivity(), nv21.clone(),
                            previewSize.width, previewSize.height,
                            facePreviewInfoList.get(0).getFaceInfo(), idcard);
                    emitter.onNext(success);
                }
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean success) {
                            String result = success ? "register success!" : "register failed!";
                            Log.e(TAG, "onNext: " + result);
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Log.e(TAG, "onError: register failed!");
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void drawPreviewInfo(List<FacePreviewInfo> facePreviewInfoList) {
        List<DrawInfo> drawInfoList = new ArrayList<>();
        for (int i = 0; i < facePreviewInfoList.size(); i++) {
            String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
            Integer recognizeStatus = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());

            // 根据识别结果和活体结果设置颜色
            int color = RecognizeColor.COLOR_UNKNOWN;
            if (recognizeStatus != null) {
                if (recognizeStatus == RequestFeatureStatus.FAILED) {
                    color = RecognizeColor.COLOR_FAILED;
                }
                if (recognizeStatus == RequestFeatureStatus.SUCCEED) {
                    color = RecognizeColor.COLOR_SUCCESS;
                }
            }
            if (liveness != null && liveness == LivenessInfo.NOT_ALIVE) {
                color = RecognizeColor.COLOR_FAILED;
            }

            drawInfoList.add(new DrawInfo(drawHelper.adjustRect(facePreviewInfoList.get(i).getFaceInfo().getRect()),
                    GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, liveness == null ? LivenessInfo.UNKNOWN : liveness, color,
                    name == null ? String.valueOf(facePreviewInfoList.get(i).getTrackId()) : name));
        }
        drawHelper.draw(faceRectView, drawInfoList);
    }


    /**
     * 删除已经离开的人脸
     *
     * @param facePreviewInfoList 人脸和trackId列表
     */
    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        if (compareResultList != null) {
            for (int i = compareResultList.size() - 1; i >= 0; i--) {
                if (!requestFeatureStatusMap.containsKey(compareResultList.get(i).getTrackId())) {
                    compareResultList.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        }
        if (facePreviewInfoList == null || facePreviewInfoList.size() == 0) {
            requestFeatureStatusMap.clear();
            livenessMap.clear();
            livenessErrorRetryMap.clear();
            extractErrorRetryMap.clear();
            if (getFeatureDelayedDisposables != null) {
                getFeatureDelayedDisposables.clear();
            }
            return;
        }
        Enumeration<Integer> keys = requestFeatureStatusMap.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == key) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                requestFeatureStatusMap.remove(key);
                livenessMap.remove(key);
                livenessErrorRetryMap.remove(key);
                extractErrorRetryMap.remove(key);
            }
        }
    }

    private void searchFace(final FaceFeature frFace, final Integer requestId) {
        Observable.create(new ObservableOnSubscribe<CompareResult>() {
            @Override
            public void subscribe(ObservableEmitter<CompareResult> emitter) {
                CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(frFace);
                emitter.onNext(compareResult);

            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompareResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CompareResult compareResult) {
                        if (compareResult == null || compareResult.getUserName() == null) {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            faceHelper.setName(requestId, "VISITOR " + requestId);
                            return;
                        }
                        if (compareResult.getSimilar() > SIMILAR_THRESHOLD) {
                            boolean isAdded = false;
                            if (compareResultList == null) {
                                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                                faceHelper.setName(requestId, "VISITOR " + requestId);
                                return;
                            }
                            for (CompareResult compareResult1 : compareResultList) {
                                if (compareResult1.getTrackId() == requestId) {
                                    isAdded = true;
                                    break;
                                }
                            }
                            if (!isAdded) {
                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
                                if (compareResultList.size() >= MAX_DETECT_NUM) {
                                    compareResultList.remove(0);
                                    adapter.notifyItemRemoved(0);
                                }
                                //添加显示人员时，保存其trackId
                                compareResult.setTrackId(requestId);
                                compareResultList.add(compareResult);
                                adapter.notifyItemInserted(compareResultList.size() - 1);
                            }
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                            faceHelper.setName(requestId, "识别" + compareResult.getUserName());
                            if (confirmIdcardDialog == null || !confirmIdcardDialog.isShowing()) {
                                Log.e(TAG, "getUserName: " + compareResult.getUserName());
                                confirmIdcardDialog = DialogDoubleFragment.newInstance(String.format("您绑定的身份证号码为:\n%s",
                                        compareResult.getUserName()), "确定");
                                confirmIdcardDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        ToastUtil.showText("进行登录操作");
                                        loginPresenter.login(SpSir.getInstance().getString(SpSir.PROJECT_ID),compareResult.getUserName());

                                    }
                                });
                                confirmIdcardDialog.show(getActivity());
                            }
                            Log.e(TAG, "识别成功: ");


                        } else {
                            showBindDialog(requestId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "识别onError: " + e.getMessage());
                        showBindDialog(requestId);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean showBindDialog = true;

    private void showBindDialog(Integer requestId) {
        if (faceHelper != null) {
            faceHelper.setName(requestId, "未绑定");
        }
        Log.e(TAG, "识别: " + "未绑定" + retryTimes);
        if (showBindDialog && (++retryTimes >= MAX_RETRY_TIME)) {
            if (!goBindDialog.isShowing()) {
                goBindDialog.setOnCancelListener(new BaseDialogFragment.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        retryTimes = 0;
                        retryRecognizeDelayed(requestId);
                        Log.e(TAG, "onCancel: " + retryTimes);
                    }
                });
                goBindDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        showBindDialog = false;
                        retryRecognizeDelayed(requestId);
                        Log.e(TAG, "onConfirm: " + retryTimes);
                        llInputBar.setVisibility(View.VISIBLE);
                    }
                });
                goBindDialog.show(getActivity());
            }
        } else {
            retryRecognizeDelayed(requestId);
        }


    }

    private int retryTimes;


    /**
     * 将准备注册的状态置为{@link #REGISTER_STATUS_READY}
     */
    public void register() {
        if (registerStatus == REGISTER_STATUS_DONE) {
            registerStatus = REGISTER_STATUS_READY;
        }
    }

    public void switchCamera() {
        if (cameraHelper != null) {
            boolean success = cameraHelper.switchCamera();
            ToastUtil.showText(success ? "相机切换成功" : "相机切换失败");
        }
    }

    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        initEngine();
        initCamera();
    }

    /**
     * 将map中key对应的value增1回传
     *
     * @param countMap map
     * @param key      key
     * @return 增1后的value
     */
    public int increaseAndGetValue(Map<Integer, Integer> countMap, int key) {
        if (countMap == null) {
            return 0;
        }
        Integer value = countMap.get(key);
        if (value == null) {
            value = 0;
        }
        countMap.put(key, ++value);
        return value;
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行活体检测
     *
     * @param requestId 人脸ID
     */
    private void retryLivenessDetectDelayed(final Integer requestId) {
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        if (livenessDetect) {
                            faceHelper.setName(requestId, Integer.toString(requestId));
                        }
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行人脸识别
     *
     * @param requestId 人脸ID
     */
    private void retryRecognizeDelayed(final Integer requestId) {
        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
                        faceHelper.setName(requestId, Integer.toString(requestId));
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }

    @Override
    public void onLoginSuccess(LoginInfo loginInfo) {
        SpSir.getInstance().putString(SpSir.REALNAME, loginInfo.getRealName());
        SpSir.getInstance().putString(SpSir.MOBILE, loginInfo.getMobilePhone());
        SpSir.getInstance().putString(SpSir.IDCARD, loginInfo.getIdcard());
        ToastUtil.showText("登录成功");
        EventBus.getDefault().post(new LoginStatusEvent(true));
        ((IStackActivity) getActivity()).outStack(this);
        mActivity.startService(new Intent(mActivity, InitializeService.class));
    }
}
