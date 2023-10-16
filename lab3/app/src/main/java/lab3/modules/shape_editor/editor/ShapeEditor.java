package lab3.modules.shape_editor.editor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.MotionEvent;

import lab3.modules.shape_editor.ShapeObjectsEditor;

@SuppressLint("ViewConstructor")
public class ShapeEditor extends Editor {

    public ShapeObjectsEditor shapeObjectsEditor;

    public ShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        this.shapeObjectsEditor = shapeObjectsEditor;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void processTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float endX;
        float endY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                endX = x;
                endY = y;
                break;
        }
    }
    public void saveShape() {

    }
    public void drawDottedShape(float startX, float startY, float endX, float endY, Canvas canvas) {

    }
}
