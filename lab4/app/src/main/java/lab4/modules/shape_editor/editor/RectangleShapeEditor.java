package lab4.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


import lab4.modules.shape_editor.ShapeObjectsEditor;
import lab4.modules.shape_editor.shapes.RectangleShape;
import lab4.modules.shape_editor.utils.PaintUtils;

@SuppressLint("ViewConstructor")
public class RectangleShapeEditor extends ShapeEditor {
    public RectangleShapeEditor(ShapeObjectsEditor context) {
        super(context);
    }

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
        RectangleShape rectangleShape = new RectangleShape(shapeObjectsEditor.startX,
                shapeObjectsEditor.startY, shapeObjectsEditor.endX, shapeObjectsEditor.endY, shapeObjectsEditor.canvas);
        shapeObjectsEditor.showedShapes.add(rectangleShape);
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }

    @Override
    public void drawDottedShape(float startX, float startY, float endX, float endY, Canvas canvas) {
        float right = Math.max(startX, endX);
        float left = Math.min(startX, endX);
        float bottom = Math.max(startY, endY);
        float top = Math.min(startY, endY);
        canvas.drawRect(left, top, right, bottom, PaintUtils.editingPaint);
    }
}