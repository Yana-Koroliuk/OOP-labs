package com.koroliuk.painter.editor.shapes;

public class Rect extends Shape {


    public Rect() {
        super();
    }

    @Override
    public void draw() {
        float right = Math.max(startX, endX);
        float left = Math.min(startX, endX);
        float bottom = Math.max(startY, endY);
        float top = Math.min(startY, endY);
        canvas.drawRect(left, top , right, bottom, pStroke);
        if (pFill != null) {
            canvas.drawRect(left, top, right, bottom, pFill);
        }
    }

    @Override
    public Shape createNextEmpty() {
        return new Rect();
    }
}
