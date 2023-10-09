package lab2.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


import lab2.modules.shape_editor.ShapeObjectsEditor;
import lab2.modules.shape_editor.shapes.EllipseShape;
import lab2.modules.shape_editor.utils.PaintUtils;

@SuppressLint("ViewConstructor")
public class EllipseShapeEditor extends ShapeEditor {
    private EllipseShape ellipseShape;
    public EllipseShapeEditor(ShapeObjectsEditor context) {
        super(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void processTouchEvent(MotionEvent event) {
        super.processTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                shapeObjectsEditor.isDrawing = true;
                ellipseShape = new EllipseShape(x, y, x, y, shapeObjectsEditor.canvas);
                break;
            case MotionEvent.ACTION_MOVE:
                ellipseShape.endX = x;
                ellipseShape.endY = y;
                shapeObjectsEditor.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                shapeObjectsEditor.isDrawing = false;
                saveShape();
                break;
        }
    }
    @Override
    public void saveShape() {
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = ellipseShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
    @Override
    public void drawDottedShape(Canvas canvas) {
        float right = Math.max(ellipseShape.startX, ellipseShape.endX);
        float left = Math.min(ellipseShape.startX, ellipseShape.endX);
        float bottom = Math.max(ellipseShape.startY, ellipseShape.endY);
        float top = Math.min(ellipseShape.startY, ellipseShape.endY);
        canvas.drawOval(left, top, right, bottom, PaintUtils.editingPaint);
    }
}
