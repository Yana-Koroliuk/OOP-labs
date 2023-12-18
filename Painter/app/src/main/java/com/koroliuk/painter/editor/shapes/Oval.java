package com.koroliuk.painter.editor.shapes;

import android.os.Build;
import androidx.annotation.RequiresApi;

public class Oval extends Shape {


    public Oval() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw() {
        float rStartX = 2 * startX - endX;
        float rStartY = 2 * startY - endY;
        float right = Math.max(rStartX, endX);
        float left = Math.min(rStartX, endX);
        float bottom = Math.max(rStartY, endY);
        float top = Math.min(rStartY, endY);
        canvas.drawOval(left, top , right, bottom, pStroke);
        if (pFill != null) {
            canvas.drawOval(left, top, right, bottom, pFill);
        }
    }

    @Override
    public Shape createNextEmpty() {
        return new Oval();
    }
}
