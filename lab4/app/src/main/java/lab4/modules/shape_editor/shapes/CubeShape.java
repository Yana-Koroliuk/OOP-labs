package lab4.modules.shape_editor.shapes;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import static lab4.modules.shape_editor.Type.CUBE;
// клас, що задає куб як фігуру
public class CubeShape extends Shape implements RectangleShapeInterface,
        LineShapeInterface{
    public Paint linePaint;
    public Paint rectPaint;
    public CubeShape() {
        setPaint();
        type = CUBE;
    }
    @Override
    public void show() {
        float side_length = (float) Math.sqrt(Math.pow(startX-endX,
                2)+Math.pow(startY-endY, 2))/1.86f;
        float right = Math.max(startX, endX);
        float left = Math.min(startX, endX);
        float bottom = Math.max(startY, endY);
        float top = Math.min(startY, endY);
        showRect(left, bottom-side_length, left+side_length, bottom);
        showRect(right-side_length, top, right, top+side_length);
        showLine(left, bottom-side_length, right-side_length, top);
        showLine(left+side_length, bottom-side_length, right, top);
        showLine(left+side_length, bottom, right, top+side_length);
        showLine(left, bottom, right-side_length, top+side_length);
    }
    @Override
    public void setPaint() {
        setLinePaint();
        setRectPaint();
    }
    @Override
    public void setLinePaint() {
        linePaint = new Paint(Paint.DITHER_FLAG);
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(5);
    }
    @Override
    public void showLine(float startX, float startY, float endX, float endY) {
        Path linePath = new Path();
        linePath.moveTo(startX, startY);
        linePath.lineTo(endX, endY);
        canvas.drawPath(linePath, linePaint);
    }
    @Override
    public void setRectPaint() {
        rectPaint = new Paint(Paint.DITHER_FLAG);
        rectPaint.setAntiAlias(true);
        rectPaint.setDither(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStrokeJoin(Paint.Join.ROUND);
        rectPaint.setStrokeCap(Paint.Cap.ROUND);
        rectPaint.setStrokeWidth(5);
    }
    @Override
    public void showRect(float left, float top, float right, float bottom) {
        canvas.drawRect(left, top , right, bottom, rectPaint);
    }
    @Override
    public Shape createNewInstance() {
        return new CubeShape();
    }
    @Override
    public void setEditingProperties(Paint paint) {
        linePaint = paint;
        rectPaint = paint;
    }
}