package lab5.modules.shape_editor.shapes;

import android.graphics.Color;
import android.graphics.Paint;

public class PointShape extends Shape {
    Paint pointPaint;

    public PointShape() {
        setPaint();
    }

    @Override
    public void show() {
        if (endX >= 4 || endY >= 4) {
            canvas.drawPoint(endX, endY, pointPaint);
        }
    }

    @Override
    public void setPaint() {
        pointPaint = new Paint(Paint.DITHER_FLAG);
        pointPaint.setAntiAlias(true);
        pointPaint.setDither(true);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(8);
    }

    @Override
    public Shape createNextEmpty() {
        return new PointShape();
    }

    @Override
    public void setEditingPaint(Paint paint) {
    }
}