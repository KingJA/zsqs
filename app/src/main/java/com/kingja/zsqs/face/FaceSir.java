package com.kingja.zsqs.face;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.TextureView;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.kingja.zsqs.base.App;
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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
 * Create Time:2020/4/20 0020 下午 4:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FaceSir {
    private String TAG = getClass().getSimpleName();
    private Activity context;
    /*VIDEO模式人脸检测引擎，用于预览帧人脸追踪*/
    private FaceEngine ftEngine;
    /*用于特征提取的引擎*/
    private FaceEngine frEngine;
    /*IMAGE模式活体检测引擎，用于预览帧人脸活体检测*/
    private FaceEngine flEngine;
    private int ftInitCode = -1;
    private int frInitCode = -1;
    private int flInitCode = -1;
    private static final int MAX_DETECT_NUM = 10;
    private FaceHelper faceHelper;
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    /*用于记录人脸识别相关状态*/
    private ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    /*活体检测的开关*/
    private boolean livenessDetect = true;
    /*用于存储活体值*/
    private ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    /*识别阈值*/
    private static final float SIMILAR_THRESHOLD = 0.8F;
    private boolean isDestory;
    /*用于存储活体检测出错重试次数*/
    private ConcurrentHashMap<Integer, Integer> livenessErrorRetryMap = new ConcurrentHashMap<>();
    private List<CompareResult> compareResultList = new ArrayList<>();
    /*用于记录人脸特征提取出错重试次数*/
    private ConcurrentHashMap<Integer, Integer> extractErrorRetryMap = new ConcurrentHashMap<>();
    private CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();
    private CompositeDisposable delayFaceTaskCompositeDisposable = new CompositeDisposable();
    /*注册人脸状态码，准备注册*/
    private static final int REGISTER_STATUS_READY = 0;
    /*注册人脸状态码，注册中*/
    private static final int REGISTER_STATUS_PROCESSING = 1;
    /*注册人脸状态码，注册结束（无论成功失败）*/
    private static final int REGISTER_STATUS_DONE = 2;
    private int registerStatus = REGISTER_STATUS_DONE;
    private FaceRectView faceRectView;
    private TextureView previewView;
    private OnSearchFaceListener onSearchFaceListener;
    private String username;

    /**
     * 1.初始化人脸特征byte[]，对应的注册名放入faceRegisterInfoList中
     * 2.初始化引擎
     * 3.初始化相机
     */

    public FaceSir(Activity context) {
        this.context = context;
    }

    public void initFaces() {
        FaceServer.getInstance().init(context);
    }

    public void initEngineAndCamera(FaceRectView faceRectView, TextureView previewView,
                                    OnSearchFaceListener onSearchFaceListener) {
        this.faceRectView = faceRectView;
        this.previewView = previewView;
        this.onSearchFaceListener = onSearchFaceListener;
        initEngine();
        initCamera();
    }

    private void initEngine() {
        ftEngine = new FaceEngine();
        ftInitCode = ftEngine.init(context, DetectMode.ASF_DETECT_MODE_VIDEO,
                DetectFaceOrientPriority.ASF_OP_ALL_OUT,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT);
        frEngine = new FaceEngine();
        frInitCode = frEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE,
                DetectFaceOrientPriority.ASF_OP_ALL_OUT,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION);
        flEngine = new FaceEngine();
        flInitCode = flEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE,
                DetectFaceOrientPriority.ASF_OP_ALL_OUT,
                16, MAX_DETECT_NUM, FaceEngine.ASF_LIVENESS);
        if (ftInitCode != ErrorInfo.MOK) {
            Log.i(TAG, "initEngine: ftEngine error");
        }
        if (frInitCode != ErrorInfo.MOK) {
            Log.i(TAG, "initEngine: frEngine error");
        }
        if (flInitCode != ErrorInfo.MOK) {
            Log.i(TAG, "initEngine: flEngine error");
        }
    }

    public void register(String username) {
        this.username = username;
        if (registerStatus == REGISTER_STATUS_DONE) {
            registerStatus = REGISTER_STATUS_READY;
        }
    }

    public void onDestory() {
        isDestory = true;
        if (ftInitCode == ErrorInfo.MOK && ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i(TAG, "onDestory: " + ftUnInitCode);
            }
        }
        if (frInitCode == ErrorInfo.MOK && frEngine != null) {
            synchronized (frEngine) {
                int frUnInitCode = frEngine.unInit();
                Log.i(TAG, "onDestory: " + frUnInitCode);
            }
        }
        if (flInitCode == ErrorInfo.MOK && flEngine != null) {
            synchronized (flEngine) {
                int flUnInitCode = flEngine.unInit();
                Log.i(TAG, "onDestory: " + flUnInitCode);
            }
        }
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        if (getFeatureDelayedDisposables != null) {
            getFeatureDelayedDisposables.clear();
        }
        if (delayFaceTaskCompositeDisposable != null) {
            delayFaceTaskCompositeDisposable.clear();
        }
        if (faceHelper != null) {
            ConfigUtil.setTrackedFaceCount(context, faceHelper.getTrackedFaceCount());
            faceHelper.release();
            faceHelper = null;
        }
        FaceServer.getInstance().unInit();
    }


    private void initCamera() {
        final FaceListener faceListener = new FaceListener() {
            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "FaceListener onFail: " + e.getMessage());
            }

            @Override
            public void onFaceFeatureInfoGet(@Nullable final FaceFeature faceFeature, final Integer requestId,
                                             final Integer errorCode) {
                Log.e(TAG, "onFaceFeatureInfoGet: " );
                if (faceFeature != null) {
                    searchFace(faceFeature, requestId);
                } else {
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
                        previewView.getHeight(), displayOrientation, cameraId, isMirror, false, false);
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
                    faceHelper = new FaceHelper.Builder()
                            .ftEngine(ftEngine)
                            .frEngine(frEngine)
                            .flEngine(flEngine)
                            .frQueueSize(MAX_DETECT_NUM)
                            .flQueueSize(MAX_DETECT_NUM)
                            .previewSize(previewSize)
                            .faceListener(faceListener)
                            .trackedFaceCount(trackedFaceCount == null ?
                                    ConfigUtil.getTrackedFaceCount(App.getContext()) : trackedFaceCount)
                            .build();
                }
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                if (!doDiscern) {
                    return;
                }
                /*清理人脸识别框*/
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                /*从图片中获取人脸*/
                List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);
                /*获取人脸识别框*/
                if (faceRectView != null && drawHelper != null) {
                    drawPreviewInfo(faceRectView, facePreviewInfoList);
                }
                registerFace(nv21, facePreviewInfoList);
                clearLeftFace(facePreviewInfoList);

                if (facePreviewInfoList.size() > 0 && previewSize != null) {
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
                        if (status == null || status == RequestFeatureStatus.TO_RETRY) {
                            Log.e(TAG, "请求特征>>>>>>>: " + facePreviewInfoList.get(i).getTrackId());
                            /*人脸识别状态-搜索中*/
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
                .rotation(context.getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }


    private void searchFace(final FaceFeature frFace, final Integer requestId) {
        Observable.create(new ObservableOnSubscribe<CompareResult>() {
            @Override
            public void subscribe(ObservableEmitter<CompareResult> emitter) {
                CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(frFace);
                Log.e(TAG, "compareResult: " + compareResult);
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
                        if (isDestory) {
                            return;
                        }

                        if (compareResult == null || compareResult.getUserName() == null) {
                            Log.e(TAG, "compareResult == null || compareResult.getUserName() == null: ");
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            faceHelper.setName(requestId, "VISITOR " + requestId);
                            return;
                        }
                        if (compareResult.getSimilar() > SIMILAR_THRESHOLD) {
                            Log.e(TAG, "compareResult.getSimilar() > SIMILAR_THRESHOLD: ");
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
//                                    adapter.notifyItemRemoved(0);
                                }
                                //添加显示人员时，保存其trackId
                                compareResult.setTrackId(requestId);
                                compareResultList.add(compareResult);
//                                adapter.notifyItemInserted(compareResultList.size() - 1);
                            }
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                            faceHelper.setName(requestId, compareResult.getUserName());

                            Log.e(TAG, "识别成功: ");
                            if (onSearchFaceListener != null) {
                                onSearchFaceListener.onSearchFaceSuccess(requestId,compareResult.getUserName());
                            }

                        } else {
                            Log.e(TAG, "识别失败: ");
                            if (faceHelper != null) {
                                faceHelper.setName(requestId, "未匹配");
                            }
                            if (++retryTimes >= MAX_RETRY_TIME) {
                                if (listenSearchFail && onSearchFaceListener != null) {
                                    onSearchFaceListener.onSearchFail(requestId);
                                }else{
                                    retryRecognizeDelayed(requestId);
                                }

                            } else {
                                retryRecognizeDelayed(requestId);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "识别onError: " + e.getMessage());
                        if (faceHelper != null) {
                            faceHelper.setName(requestId, "未匹配");
                        }
                        if (++retryTimes >= MAX_RETRY_TIME) {
                            if (listenSearchFail && onSearchFaceListener != null) {
                                onSearchFaceListener.onSearchFail(requestId);
                            }else{
                                retryRecognizeDelayed(requestId);
                            }

                        } else {
                            retryRecognizeDelayed(requestId);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private int retryTimes;
    private static final int MAX_RETRY_TIME = 3;

    public void searchFail(int requestId, String username) {
        if (faceHelper != null) {
            faceHelper.setName(requestId, "未匹配");
        }
        if (++retryTimes >= MAX_RETRY_TIME) {

        } else {
            retryRecognizeDelayed(requestId);
        }

    }

    public interface OnSearchFaceListener {
        void onSearchFaceSuccess(int requestId, String username);

        void onSearchFail(int requestId);
    }


    private void drawPreviewInfo(FaceRectView faceRectView, List<FacePreviewInfo> facePreviewInfoList) {
        List<DrawInfo> drawInfoList = new ArrayList<>();
        for (int i = 0; i < facePreviewInfoList.size(); i++) {
            String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
            Integer recognizeStatus = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());

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

    private static final long FAIL_RETRY_INTERVAL = 1000;
    private boolean listenSearchFail = true;

    public void stopListenSearchFail() {
        listenSearchFail = false;
    }

    public void research(int requestId) {
        retryTimes = 0;
        retryRecognizeDelayed(requestId);
    }

    public void retryRecognizeDelayed(final Integer requestId) {
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
                        if (isDestory) {
                            return;
                        }
                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
                        faceHelper.setName(requestId, Integer.toString(requestId));
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }


    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        if (compareResultList != null) {
            for (int i = compareResultList.size() - 1; i >= 0; i--) {
                if (!requestFeatureStatusMap.containsKey(compareResultList.get(i).getTrackId())) {
                    compareResultList.remove(i);
//                    adapter.notifyItemRemoved(i);
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


    private void registerFace(final byte[] nv21, final List<FacePreviewInfo> facePreviewInfoList) {

        if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
            registerStatus = REGISTER_STATUS_PROCESSING;
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) {
                    boolean success = FaceServer.getInstance().registerNv21(context, nv21.clone(),
                            previewSize.width, previewSize.height,
                            facePreviewInfoList.get(0).getFaceInfo(), username);
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
                            Log.e(TAG, "注册:" + (success ? "成功" : "失败"));
//                            if (success) {
//                                compareResultList.clear();
//                            }
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Log.e(TAG, "注册失败:" + e.getMessage());
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private boolean doDiscern = true;

    public void startDiscern() {
        doDiscern = true;
    }

    public void stopDiscern() {
        doDiscern = false;
    }
}
