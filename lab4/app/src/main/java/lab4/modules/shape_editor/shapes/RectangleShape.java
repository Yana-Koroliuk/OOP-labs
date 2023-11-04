package lab4.modules.shape_editor.shapes;

import android.graphics.Canvas;

import lab4.modules.shape_editor.utils.PaintUtils;


public class RectangleShape extends Shape {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    Canvas canvas;

    public RectangleShape(float startX, float startY, float endX, float endY, Canvas canvas) {
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
        canvas.drawRect(left, top, right, bottom, PaintUtils.innerRectanglePaint);
        canvas.drawRect(left, top, right, bottom, PaintUtils.externalRectanglePaint);
    }
}
