package com.koroliuk.painter.editor.shapes;

public class Line extends Shape {


    public Line() {
        super();
    }

    @Override
    public void draw() {
        canvas.drawLine(startX, startY, endX, endY, pStroke);
    }

    @Override
    public Shape createNextEmpty() {
        return new Line();
    }
}
