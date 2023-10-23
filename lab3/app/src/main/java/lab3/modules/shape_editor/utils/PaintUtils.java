package lab3.modules.shape_editor.utils;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class PaintUtils {
    public static Paint editingPaint = createEditingPaint();
    public static Paint pointPaint = createPointPaint();
    public static Paint linePaint = createLinePoint();
    public static Paint innerRectanglePaint = createInnerRectanglePaint();
    public static Paint externalRectanglePaint = createExternalRectanglePaint();
    public static Paint externalEllipsePaint = createExternalEllipsePaint();
    private static Paint createEditingPaint() {
        Paint paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
        return paint;
    }
    private static Paint createPointPaint() {
        Paint mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        return mPaint;
    }
    private static Paint createLinePoint() {
        Paint mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        return mPaint;
    }
    private static Paint createInnerRectanglePaint() {
        Paint mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        return mPaint;
    }

    private static Paint createExternalRectanglePaint() {
        Paint mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        return mPaint;
    }

    private static Paint createExternalEllipsePaint() {
        Paint mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        return mPaint;
    }
}
