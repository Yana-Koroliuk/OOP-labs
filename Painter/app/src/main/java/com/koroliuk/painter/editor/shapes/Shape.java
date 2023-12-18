package com.koroliuk.painter.editor.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {

    public Paint pStroke;
    public Paint pFill;
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public Canvas canvas;
    protected Shape() {}

    public abstract void draw();
    public abstract Shape createNextEmpty();

}
