package com.kingja.zsqs.threepart.face.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kingja.zsqs.threepart.face.model.DrawInfo;
import com.kingja.zsqs.threepart.face.util.DrawHelper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用于显示人脸信息的控件
 */
public class FaceRectView extends View {
    private CopyOnWriteArrayList<DrawInfo> drawInfoList = new CopyOnWriteArrayList<>();

    // 画笔，复用
    private Paint paint;

    // 默认人脸框厚度
    private static final int DEFAULT_FACE_RECT_THICKNESS = 6;

    public FaceRectView(Context context) {
        this(context, null);
    }

    public FaceRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawInfoList != null && drawInfoList.size() > 0) {
            for (int i = 0; i < drawInfoList.size(); i++) {
                DrawHelper.drawFaceRect(canvas, drawInfoList.get(i), DEFAULT_FACE_RECT_THICKNESS, paint);
            }
        }
    }

    public void clearFaceInfo() {
        drawInfoList.clear();
        postInvalidate();
    }

    public void addFaceInfo(DrawInfo faceInfo) {
        drawInfoList.add(faceInfo);
        postInvalidate();
    }

    public void addFaceInfo(List<DrawInfo> faceInfoList) {
        drawInfoList.addAll(faceInfoList);
        postInvalidate();
    }
}