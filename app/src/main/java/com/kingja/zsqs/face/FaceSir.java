package com.kingja.zsqs.face;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.kingja.zsqs.base.App;
import com.kingja.zsqs.face.util.ConfigUtil;
import com.kingja.zsqs.face.util.face.FaceHelper;
import com.kingja.zsqs.face.util.face.FaceListener;

/**
 * Description:TODO
 * Create Time:2020/4/20 0020 下午 4:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FaceSir {
    private String TAG=getClass().getSimpleName();
    private Context context;
    /**
     * VIDEO模式人脸检测引擎，用于预览帧人脸追踪
     */
    private FaceEngine ftEngine;
    /**
     * 用于特征提取的引擎
     */
    private FaceEngine frEngine;
    /**
     * IMAGE模式活体检测引擎，用于预览帧人脸活体检测
     */
    private FaceEngine flEngine;

    private int ftInitCode = -1;
    private int frInitCode = -1;
    private int flInitCode = -1;
    private static final int MAX_DETECT_NUM = 10;
    public FaceSir(Context context) {
        this.context = context;
    }

    public void init() {
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

        Log.i(TAG, "initEngine:  init: " + ftInitCode);

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

    public void unInitEngine() {
        if (ftInitCode == ErrorInfo.MOK && ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i(TAG, "unInitEngine: " + ftUnInitCode);
            }
        }
        if (frInitCode == ErrorInfo.MOK && frEngine != null) {
            synchronized (frEngine) {
                int frUnInitCode = frEngine.unInit();
                Log.i(TAG, "unInitEngine: " + frUnInitCode);
            }
        }
        if (flInitCode == ErrorInfo.MOK && flEngine != null) {
            synchronized (flEngine) {
                int flUnInitCode = flEngine.unInit();
                Log.i(TAG, "unInitEngine: " + flUnInitCode);
            }
        }
    }

  public  FaceHelper   getFaceHelper(Camera.Size previewSize, FaceListener faceListener,Integer trackedFaceCount) {
        return new FaceHelper.Builder()
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
