package lab4.modules.shape_editor;
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

import lab4.modules.shape_editor.shapes.CircleEndedLineShape;
import lab4.modules.shape_editor.shapes.CubeShape;
import lab4.modules.shape_editor.shapes.EllipseShape;
import lab4.modules.shape_editor.shapes.LineShape;
import lab4.modules.shape_editor.shapes.PointShape;
import lab4.modules.shape_editor.shapes.RectangleShape;
import lab4.modules.shape_editor.shapes.Shape;
public class MyEditor extends View implements MyEditorInterface {
    // Динамічний масив фігур
    public ArrayList<Shape> showedShapes = new ArrayList<>();
    public boolean isDrawing = false;
    public Paint paint; // Задання стилю під час редагування (пунктир)
    public Bitmap bitmap;
    public Canvas canvas;
    public Shape lastEdited;
    // Оголошення конструктору класу
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
    // Створення полотна
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }
    // Перевизначення методу, що малює об'єкти
    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        for (Shape showedShape : showedShapes) {
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
    // Функція, що додає фігуру до масиву фігур
    public void addShapeToArray() {
        lastEdited.canvas = canvas;
        lastEdited.setPaint();
        showedShapes.add(lastEdited);
        lastEdited = lastEdited.createNewInstance();
        invalidate();
    }
    // Функція редагування введення фігури
    public void startEditing(Shape shape) {
        shape.setEditingProperties(paint);
        shape.show();
    }
    // Функція обробник дотиків
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
                    addShapeToArray();
                }
                break;
        }
        return true;
    }
    // Функція, що стирає останню фігуру
    public void eraseLast() {
        canvas.drawColor(Color.WHITE);
        int length = showedShapes.size();
        if (length > 1) {
            showedShapes.remove(length-1);
            invalidate();
        }
        if (length == 1) {
            showedShapes.clear();
            invalidate();
        }
    }
    // Функція, що стирає всі намальовані фігури
    public void eraseAll() {
        canvas.drawColor(Color.WHITE);
        showedShapes.clear();
        invalidate();
    }
}