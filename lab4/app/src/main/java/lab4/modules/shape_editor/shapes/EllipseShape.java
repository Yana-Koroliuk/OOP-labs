package lab4.modules.shape_editor.shapes;
import android.graphics.Color;
import android.graphics.Paint;

// клас, що задає еліпс, як фігуру
public class EllipseShape extends Shape implements EllipseShapeInterface {
    public Paint ellipsePaint;
    public EllipseShape() {
        setPaint();
    }
    @Override
    public void show() {
        showEllipse(startX, startY, endX, endY);
    }
    @Override
    public void setPaint() {
        setEllipsePaint();
    }
    @Override
    public void setEllipsePaint() {
        ellipsePaint = new Paint(Paint.DITHER_FLAG);
        ellipsePaint.setAntiAlias(true);
        ellipsePaint.setDither(true);
        ellipsePaint.setStyle(Paint.Style.STROKE);
        ellipsePaint.setColor(Color.BLACK);
        ellipsePaint.setStrokeJoin(Paint.Join.ROUND);
        ellipsePaint.setStrokeCap(Paint.Cap.ROUND);
        ellipsePaint.setStrokeWidth(5);
    }
    @Override
    public void showEllipse(float startX, float startY, float endX, float endY) {
        float rStartX = 2 * startX - endX;
        float rStartY = 2 * startY - endY;
        float right = Math.max(rStartX, endX);
        float left = Math.min(rStartX, endX);
        float bottom = Math.max(rStartY, endY);
        float top = Math.min(rStartY, endY);
        canvas.drawOval(left, top, right, bottom, ellipsePaint);
    }
    @Override
    public Shape createNewInstance() {
        return new EllipseShape();
    }
    @Override
    public void setEditingPaint(Paint paint) {
        ellipsePaint = paint;
    }
}