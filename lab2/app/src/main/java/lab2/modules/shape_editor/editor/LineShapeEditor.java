package lab2.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


import lab2.modules.shape_editor.ShapeObjectsEditor;
import lab2.modules.shape_editor.shapes.LineShape;
import lab2.modules.shape_editor.utils.PaintUtils;

@SuppressLint("ViewConstructor")
public class LineShapeEditor extends ShapeEditor {
    private LineShape lineShape;
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
                lineShape = new LineShape(x, y, x, y, shapeObjectsEditor.canvas);
                break;
            case MotionEvent.ACTION_MOVE:
                lineShape.endX = x;
                lineShape.endY = y;
                shapeObjectsEditor.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                shapeObjectsEditor.isDrawing = false;
                saveShape();
                break;
        }
    }
    public void saveShape() {
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = lineShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
    @Override
    public void drawDottedShape(Canvas canvas) {
        float dx = Math.abs(lineShape.endX - lineShape.startX);
        float dy = Math.abs(lineShape.endY - lineShape.startY);
        if (dx >= 4 || dy >= 4) {
            canvas.drawLine(lineShape.startX, lineShape.startY, lineShape.endX, lineShape.endY, PaintUtils.editingPaint);
        }
    }
}







