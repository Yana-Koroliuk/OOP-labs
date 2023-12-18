package com.koroliuk.painter.editor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.koroliuk.painter.editor.shapes.Shape;
import com.koroliuk.painter.scrolling.MyHorizontalScrollView;
import com.koroliuk.painter.scrolling.MyScrollView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class PainterView extends View {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public MyScrollView scrollView;
    public MyHorizontalScrollView horizontalScrollView;
    public boolean isDrawing;
    public Bitmap mainBitmap;
    public Canvas canvas;
    public Paint paintStroke;
    public Paint paintFill;
    public String backgroundColor;
    public Shape lastEdited;
    public boolean isFilled;
    public int width;
    public int selectedType;
    public boolean selectedTool;
    public List<Bitmap> bitmapsList;
    public int bitmapIndex;

    public PainterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmapIndex = -1;
        width = 10;
        bitmapsList = new ArrayList<>();
        backgroundColor = "#FFFFFF";
        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(10);
        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(Color.TRANSPARENT);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mainBitmap == null || mainBitmap.getWidth() != w || mainBitmap.getHeight() != h) {
            Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            new Canvas(newBitmap).drawColor(Color.parseColor(backgroundColor));
            if (mainBitmap != null) {
                new Canvas(newBitmap).drawBitmap(mainBitmap, 0, 0, null);
            }
            mainBitmap = newBitmap;
        }
        canvas = new Canvas(mainBitmap);
        addToUndoList(mainBitmap);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mainBitmap != null) {
            canvas.drawColor(Color.parseColor(backgroundColor));
            canvas.drawBitmap(mainBitmap, 0, 0, null);
            if (isDrawing) {
                if (selectedTool) {
                    lastEdited.canvas = this.canvas;
                } else {
                    lastEdited.canvas = canvas;
                }
                lastEdited.draw();
            }
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "DrawAllocation"})
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scrollView.isEnableScrolling()) {
            return true;
        }
        deleteAllFromIndex(bitmapIndex);
        lastEdited.pStroke = new Paint(paintStroke);
        paintStroke.setStrokeWidth(width);
        lastEdited.pFill = new Paint(paintFill);
        float x = event.getX();
        float y = event.getY();
        if (selectedTool) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (lastEdited != null) {
                        lastEdited.startX = x;
                        lastEdited.startY = y;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (lastEdited != null) {
                        lastEdited.endX = x;
                        lastEdited.endY = y;
                        addToDrawen();
                        lastEdited.startX = x;
                        lastEdited.startY = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (lastEdited != null) {
                        lastEdited.endX = x;
                        lastEdited.endY = y;
                        addToDrawen();
                    }
                    break;
            }
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (lastEdited != null) {
                        isDrawing = true;
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
                    if (lastEdited != null) {
                        isDrawing = false;
                        addToDrawen();
                    }
                    break;
            }
        }
        return true;
    }
    public void start(Shape shape) {
        lastEdited = shape;
    }
    public void addToDrawen() {
        lastEdited.canvas = canvas;
        lastEdited.draw();
        addToUndoList(mainBitmap);
        start(lastEdited.createNextEmpty());
        invalidate();
    }

    public void addToUndoList(Bitmap bitmap) {
        if (bitmapIndex < bitmapsList.size() - 1) {
            cleanupBitmapsFromIndex(bitmapIndex + 1);
        }
        if (bitmapsList.size() >= 1000) {
            Bitmap toRecycle = bitmapsList.remove(0);
            toRecycle.recycle();
        }
        bitmapsList.add(bitmap.copy(bitmap.getConfig(), true));
        bitmapIndex = bitmapsList.size() - 1;
    }
    public void deleteAllFromIndex(int index) {
        while (bitmapsList.size() > index + 1) {
            Bitmap toRecycle = bitmapsList.remove(bitmapsList.size() - 1);
            if (toRecycle != null && !toRecycle.isRecycled()) {
                toRecycle.recycle();
            }
        }
    }

    public void setUndoBitmap() {
        if (bitmapIndex > 0) {
            cleanupBitmap(mainBitmap);
            mainBitmap = bitmapsList.get(--bitmapIndex).copy(Bitmap.Config.ARGB_8888, true);
            canvas = new Canvas(mainBitmap);
            invalidate();
        }
    }

    public void setRedoBitmap() {
        if (bitmapIndex < bitmapsList.size() - 1) {
            cleanupBitmap(mainBitmap);
            mainBitmap = bitmapsList.get(++bitmapIndex).copy(Bitmap.Config.ARGB_8888, true);
            canvas = new Canvas(mainBitmap);
            invalidate();
        }
    }
    public void zoomIn() {
        rescaleBitmap(0.8f);
    }

    public void zoomOut() {
        rescaleBitmap(1.2f);
    }

    private void rescaleBitmap(float scaleFactor) {
        if (mainBitmap != null) {
            int newWidth = (int) (mainBitmap.getWidth() * scaleFactor);
            int newHeight = (int) (mainBitmap.getHeight() * scaleFactor);
            Bitmap rescaledBitmap = Bitmap.createScaledBitmap(mainBitmap, newWidth, newHeight, true);
            cleanupBitmap(mainBitmap);
            mainBitmap = rescaledBitmap;
            canvas = new Canvas(mainBitmap);
            invalidate();
        }
    }
    public Uri saveFileAsPNG(String name) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
        Uri fileUri = null;
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(fullPath, name + ".png");
            if (file.exists())
                file.delete();
            file.createNewFile();
            try (OutputStream fOut = new FileOutputStream(file)) {
                mainBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fileUri = Uri.fromFile(file);
            }
            Toast.makeText(context, "Файл збережено: " + fullPath + name + ".png", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Помилка при збереженні файла: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return fileUri;
    }
    public void cleanupBitmaps() {
        cleanupBitmap(mainBitmap);
        cleanupBitmapsFromIndex(0);
    }

    private void cleanupBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void cleanupBitmapsFromIndex(int index) {
        while (index < bitmapsList.size()) {
            cleanupBitmap(bitmapsList.remove(index));
        }
    }
    public Bitmap getBitmap() {
        return mainBitmap;
    }

}
