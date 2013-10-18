package mtu.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class ScribbleView extends View{
	private static final float MINP = 0.25f;
    private static final float MAXP = 0.75f;

    private Paint       mPaint;
    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Path    mPath;
    private Paint   mBitmapPaint;

    public ScribbleView(Context c) {
        super(c);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        mBitmap = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
    }

    private float mX, mY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                invalidate();
                break;
        }
        return true;
    }
}