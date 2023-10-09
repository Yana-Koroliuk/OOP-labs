package lab2.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


import lab2.modules.shape_editor.ShapeObjectsEditor;
import lab2.modules.shape_editor.shapes.RectangleShape;
import lab2.modules.shape_editor.utils.PaintUtils;

@SuppressLint("ViewConstructor")
public class RectangleShapeEditor extends ShapeEditor {
    private RectangleShape rectangleShape;
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
                rectangleShape = new RectangleShape(x, y, x, y, shapeObjectsEditor.canvas);
                break;
            case MotionEvent.ACTION_MOVE:
                rectangleShape.endX = x;
                rectangleShape.endY = y;
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
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = rectangleShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }

    @Override
    public void drawDottedShape(Canvas canvas) {
        float rStartX = 2 * rectangleShape.startX - rectangleShape.endX;
        float rStartY = 2 * rectangleShape.startY - rectangleShape.endY;
        float right = Math.max(rStartX, rectangleShape.endX);
        float left = Math.min(rStartX, rectangleShape.endX);
        float bottom = Math.max(rStartY, rectangleShape.endY);
        float top = Math.min(rStartY, rectangleShape.endY);
        canvas.drawRect(left, top, right, bottom, PaintUtils.editingPaint);
    }
}
