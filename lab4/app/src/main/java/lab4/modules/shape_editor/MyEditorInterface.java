package lab4.modules.shape_editor;

import android.view.MotionEvent;

import lab4.modules.shape_editor.shapes.Shape;

public interface MyEditorInterface {
    void start(Shape shape);

    boolean onTouchEvent(MotionEvent event);
}