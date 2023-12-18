package com.koroliuk.painter.editor.shapes;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ShapeFactory {

    private static final Map<String, Supplier<Shape>> shapeMap = new HashMap<>();

    static {
        shapeMap.put("Пензлик", Brush::new);
        shapeMap.put("Лінія", Line::new);
        shapeMap.put("Прямокутник", Rect::new);
        shapeMap.put("Овал", Oval::new);
        shapeMap.put("Куб", Cube::new);
        shapeMap.put("Гумка", Erasor::new);

    }

    public static Shape createShapeByName(String shapeName) {
        Supplier<Shape> shapeSupplier = shapeMap.get(shapeName);
        if (shapeSupplier != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return shapeSupplier.get();
            }
        }
        return null;
    }
}

