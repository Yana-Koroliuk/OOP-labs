package lab2.modules.shape_editor.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import lab2.modules.shape_editor.utils.PaintUtils;

public class LineShape extends Shape {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    Canvas canvas;

    public LineShape(float startX, float startY, float endX, float endY, Canvas canvas) {
        super(startX, startY, endX, endY, canvas);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.canvas = canvas;
    }

    @Override
    public void show() {
        float dx = Math.abs(endX - startX);
        float dy = Math.abs(endY - startY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(startX, startY, endX, endY, PaintUtils.linePaint);
        }
    }
}
