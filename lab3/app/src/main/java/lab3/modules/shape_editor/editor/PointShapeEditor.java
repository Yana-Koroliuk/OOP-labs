package lab3.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;


import lab3.modules.shape_editor.ShapeObjectsEditor;
import lab3.modules.shape_editor.shapes.PointShape;

@SuppressLint("ViewConstructor")
public class PointShapeEditor extends ShapeEditor {

    public PointShapeEditor(ShapeObjectsEditor context) {
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
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                shapeObjectsEditor.isDrawing = false;
                shapeObjectsEditor.endX = x;
                shapeObjectsEditor.endY = y;
                saveShape();
                break;
        }
    }
    @Override
    public void saveShape() {
        PointShape pointShape = new PointShape(shapeObjectsEditor.endX, shapeObjectsEditor.endY, shapeObjectsEditor.canvas);
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = pointShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
}