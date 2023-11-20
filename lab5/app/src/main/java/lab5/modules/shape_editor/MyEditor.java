package lab5.modules.shape_editor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lab5.modules.CallBack;
import lab5.modules.shape_editor.shapes.Shape;

public class MyEditor extends View implements MyEditorInterface {
    public List<Shape> showedShapes = new ArrayList<>();
    public boolean isDrawing = false;
    public Paint paint;
    public Bitmap bitmap;
    public Canvas canvas;
    public Shape lastEdited;
    public MyEditorSingleton myEditorContext;

    public MyEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
        paint.setPathEffect(new DashPathEffect(new float[]{60, 40}, 0));
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
        canvas.drawBitmap(bitmap, 0, 0, paint);
        for (Shape showedShape : showedShapes) {
            showedShape.canvas = canvas;
            showedShape.show();
        }
        if (isDrawing) {
            lastEdited.canvas = canvas;
            startEditing(lastEdited);
        }
    }

    @Override
    public void start(Shape shape) {
        lastEdited = shape;
    }

    public void addShapeToArray(CallBack callback) {
        lastEdited.canvas = canvas;
        lastEdited.setPaint();
        showedShapes.add(lastEdited);
        callback.addCallBack(lastEdited);
        lastEdited = lastEdited.createNextEmpty();
        invalidate();
    }

    public void startEditing(Shape shape) {
        shape.setEditingPaint(paint);
        shape.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                if (lastEdited != null) {
                    lastEdited.startX = x;
                    lastEdited.startY = y;
                    lastEdited.endX = x;
                    lastEdited.endY = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastEdited != null) {
                    lastEdited.endX = x;
                    lastEdited.endY = y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                if (lastEdited != null) {
                    addShapeToArray(myEditorContext.getCallback());
                }
                break;
        }
        return true;
    }

    public void eraseLast() {
        canvas.drawColor(Color.WHITE);
        int length = showedShapes.size();
        if (length > 1) {
            myEditorContext.getCallback().deleteCallBack(showedShapes.size());
            showedShapes.remove(length-1);
            invalidate();
        }
        if (length == 1) {
            myEditorContext.getCallback().deleteCallBack(showedShapes.size());
            showedShapes.clear();
            invalidate();
        }
    }

    public void eraseByIndex(int index) {
        canvas.drawColor(Color.WHITE);
        showedShapes.remove(index-1);
        invalidate();
    }

    public void eraseAll() {
        canvas.drawColor(Color.WHITE);
        for (int i = showedShapes.size(); i > 0; i--) {
            myEditorContext.getCallback().deleteCallBack(i);
        }
        showedShapes.clear();
        invalidate();
    }
}