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
                shapeObjectsEditor.startX = x;
                shapeObjectsEditor.startY = y;
                shapeObjectsEditor.endX = x;
                shapeObjectsEditor.endY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                shapeObjectsEditor.endX = x;
                shapeObjectsEditor.endY = y;
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
        EllipseShape ellipseShape = new EllipseShape(shapeObjectsEditor.startX, shapeObjectsEditor.startY,
                shapeObjectsEditor.endX, shapeObjectsEditor.endY, shapeObjectsEditor.canvas);
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = ellipseShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
    @Override
    public void drawDottedShape(float startX, float startY, float endX, float endY, Canvas canvas) {
        float right = Math.max(startX, endX);
        float left = Math.min(startX, endX);
        float bottom = Math.max(startY, endY);
        float top = Math.min(startY, endY);
        canvas.drawOval(left, top, right, bottom, PaintUtils.editingPaint);
    }
}