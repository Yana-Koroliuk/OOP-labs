package com.koroliuk.painter.editor.shapes;

public class Brush extends Shape {

    public Brush() {
        super();
    }

    @Override
    public void draw() {
        canvas.drawLine(startX, startY, endX, endY, pStroke);
    }

    @Override
    public Shape createNextEmpty() {
        return new Brush();
    }
}
