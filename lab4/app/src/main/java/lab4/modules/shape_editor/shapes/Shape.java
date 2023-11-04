package lab4.modules.shape_editor.shapes;
import android.graphics.Canvas;
import android.graphics.Paint;

import lab4.modules.shape_editor.Type;
// абстрактний клас, що визначає загальні
// поля та методи фігури
public abstract class Shape {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public Canvas canvas;
    public Type type;
    public abstract void show();
    public abstract void setPaint();
    public abstract Shape createNewInstance();
    public abstract void setEditingProperties(Paint paint);
}