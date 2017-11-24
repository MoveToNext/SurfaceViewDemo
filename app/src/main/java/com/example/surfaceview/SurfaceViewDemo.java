package com.example.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * <pre>
 * PackageName:  com.example.surfaceview
 * Description:
 * Created by :  Liu
 * date:         2017/11/24 下午9:03
 * </pre>
 */
public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private boolean mIsDraw;
    private Canvas mCanvas;
    private int x;
    private int y;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    public SurfaceViewDemo(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 50}, 0));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDraw = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDraw = false;
    }

    //runnable 的run 方法中，我们可以执行绘制的操作
    @Override
    public void run() {
        while (mIsDraw) {
            draw();
            x++;
            y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + 400);
            mPath.lineTo(x, y);
        }
    }

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        //draw something
        if (mCanvas != null) {
            mCanvas.drawPath(mPath, mPaint);
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
