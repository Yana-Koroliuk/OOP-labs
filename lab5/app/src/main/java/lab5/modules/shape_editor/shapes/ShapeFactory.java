package lab5.modules.shape_editor.shapes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ShapeFactory {

    private static final Map<String, Supplier<Shape>> shapeMap = new HashMap<>();

    static {
        shapeMap.put("Точка", PointShape::new);
        shapeMap.put("Лінія", LineShape::new);
        shapeMap.put("Прямокутник", RectangleShape::new);
        shapeMap.put("Еліпс", EllipseShape::new);
        shapeMap.put("Лінія з кружечками", CircleEndedLineShape::new);
        shapeMap.put("Куб", CubeShape::new);
    }

    public static Shape createShapeByName(String shapeName) {
        Supplier<Shape> shapeSupplier = shapeMap.get(shapeName);
        if (shapeSupplier != null) {
            return shapeSupplier.get();
        }
        return null;
    }
}

