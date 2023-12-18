package com.koroliuk.painter.editor.shapes;

import android.graphics.Color;
import android.graphics.Paint;

public class Erasor extends Shape {

    public Erasor() {
        super();
    }

    @Override
    public void draw() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(startX, startY, 50, paint);
    }

    @Override
    public Shape createNextEmpty() {
        return new Erasor();
    }
}