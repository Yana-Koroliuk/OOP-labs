package lab3.modules.shape_editor.shapes;

import android.graphics.Canvas;

public abstract class Shape {
    float startX;
    float startY;
    float endX;
    float endY;
    Canvas canvas;
    final float TOUCH_TOLERANCE = 4;

    Shape(float startX, float startY, float endX, float endY, Canvas canvas) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.canvas = canvas;
    }

    public abstract void show();
}
