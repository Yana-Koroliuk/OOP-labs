package lab2.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;


import lab2.modules.shape_editor.ShapeObjectsEditor;
import lab2.modules.shape_editor.shapes.PointShape;

@SuppressLint("ViewConstructor")
public class PointShapeEditor extends ShapeEditor {

    private PointShape pointShape;
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
                pointShape = new PointShape(x, y, shapeObjectsEditor.canvas);
                saveShape();
                break;
        }
    }
    @Override
    public void saveShape() {
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = pointShape;
        shapeObjectsEditor.increment();
        shapeObjectsEditor.canvas.drawColor(Color.WHITE);
        shapeObjectsEditor.invalidate();
    }
}
