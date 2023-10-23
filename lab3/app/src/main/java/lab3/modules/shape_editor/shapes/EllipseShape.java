package lab3.modules.shape_editor.shapes;

import android.graphics.Canvas;

import lab3.modules.shape_editor.utils.PaintUtils;


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
        float rStartX = 2 * startX - endX;
        float rStartY = 2 * startY - endY;
        float right = Math.max(rStartX, endX);
        float left = Math.min(rStartX, endX);
        float bottom = Math.max(rStartY, endY);
        float top = Math.min(rStartY, endY);
        canvas.drawOval(left, top, right, bottom, PaintUtils.externalEllipsePaint);
    }
}
