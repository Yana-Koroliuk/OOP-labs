package lab5.modules.shape_editor.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Shape {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public Canvas canvas;

    public abstract void show();

    public abstract void setPaint();

    public abstract Shape createNextEmpty();

    public abstract void setEditingPaint(Paint paint);
    public abstract void setChoosePaint(Paint paintStroke, Paint paintFill);
    public abstract String getNameOfShape();
}