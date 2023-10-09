package lab2.modules.shape_editor.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import lab2.modules.shape_editor.utils.PaintUtils;


public class EllipseShape extends Shape {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    Canvas canvas;

    public EllipseShape(float startX, float startY, float endX, float endY, Canvas canvas) {
        super(startX, startY, endX, endY, canvas);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.canvas = canvas;
    }

    @Override
    public void show() {
        float right = Math.max(startX, endX);
        float left = Math.min(startX, endX);
        float bottom = Math.max(startY, endY);
        float top = Math.min(startY, endY);
        canvas.drawOval(left, top, right, bottom, PaintUtils.innerEllipsePaint);
        canvas.drawOval(left, top, right, bottom, PaintUtils.externalEllipsePaint);
    }
}
