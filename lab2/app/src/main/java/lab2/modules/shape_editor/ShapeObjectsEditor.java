package lab2.modules.shape_editor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import lab2.modules.shape_editor.editor.ShapeEditor;
import lab2.modules.shape_editor.shapes.Shape;
import lab2.modules.shape_editor.utils.PaintUtils;

public class ShapeObjectsEditor extends View implements ShapeObjectsEditorInterface {
    public ShapeEditor currentEditor;
    public Shape[] showedShapes = new Shape[111];
    public int index = 0;
    public boolean isDrawing = false;
    public Bitmap bitmap;
    public Canvas canvas;
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    public ShapeObjectsEditor(Context context) {
        super(context);
    }

    public ShapeObjectsEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeObjectsEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, PaintUtils.editingPaint);
        for (Shape showedShape : showedShapes) {
            if (showedShape != null) {
                showedShape.show();
            }
        }

        if (isDrawing) {
            currentEditor.drawDottedShape(startX, startY, endX, endY, canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (currentEditor != null) {
            currentEditor.processTouchEvent(event);
        }
        return true;
    }

    public void eraseLast() {
        canvas.drawColor(Color.WHITE);
        boolean isAllNull = true;
        for (Shape shape: showedShapes) {
            if (shape != null) {
                isAllNull = false;
                break;
            }
        }
        if (!isAllNull) {
            decrement();
            showedShapes[index] = null;
            invalidate();
        }
    }

    public void increment() {
        if (index+1 > 110) {
            System.arraycopy(showedShapes, 1, showedShapes, 0, 110);
            showedShapes[110] = null;
        } else {
            index++;
        }
    }

    public void decrement() {
        if (index-1 >= 0) {
            index--;
        }
    }

}