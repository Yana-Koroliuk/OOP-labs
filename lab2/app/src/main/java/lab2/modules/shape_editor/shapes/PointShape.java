package lab2.modules.shape_editor.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import lab2.modules.shape_editor.utils.PaintUtils;

// клас, що визначає точку
public class PointShape extends Shape {

    public float x;

    public float y;
    public PointShape(float x1, float y1, Canvas canvas) {
        super(x1, y1, x1, y1, canvas);
        this.x = x1;
        this.y = y1;
        this.canvas = canvas;
    }

    @Override
    public void show() {
        if (x >= TOUCH_TOLERANCE || y >= TOUCH_TOLERANCE) {
            canvas.drawPoint(x, y, PaintUtils.pointPaint);
        }
    }
}
