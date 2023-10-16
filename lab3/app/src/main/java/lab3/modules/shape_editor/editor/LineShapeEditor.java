package lab3.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


import lab3.modules.shape_editor.ShapeObjectsEditor;
import lab3.modules.shape_editor.shapes.LineShape;
import lab3.modules.shape_editor.utils.PaintUtils;

@SuppressLint("ViewConstructor")
public class LineShapeEditor extends ShapeEditor {

    public LineShapeEditor(ShapeObjectsEditor context) {
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
    public void saveShape() {
        LineShape lineShape = new LineShape(shapeObjectsEditor.startX, shapeObjectsEditor.startY,
                shapeObjectsEditor.endX, shapeObjectsEditor.endY, shapeObjectsEditor.canvas);
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = lineShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
    @Override
    public void drawDottedShape(float startX, float startY, float endX, float endY, Canvas canvas) {
        float dx = Math.abs(endX - startX);
        float dy = Math.abs(endY - startY);
        if (dx >= 4 || dy >= 4) {
            canvas.drawLine(startX, startY, endX, endY, PaintUtils.editingPaint);
        }
    }
}