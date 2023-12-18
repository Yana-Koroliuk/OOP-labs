package lab6.object3.drawer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import java.util.List;
@SuppressLint("ViewConstructor")
public class Drawer extends View {
    private final float[] yArray; // масив значень
    private final float[] xArray; // масив аргументів
    private final Paint paint;
    private final float xMax;
    private final float yMax;
    private final float xMin;
    private float yMin;
    private final float cdntOX; // кординати осі ОХ та ОУ за
    private final float cdntOY; // координатною площиною екрану
    private final int n;
    // Конструктор класу
    public Drawer(Context context, List<Float> data) {
        super(context);
        // Обробка тестового формату вхідних даних
        n = data.size();
        yArray = new float[n];
        xArray = new float[n];
        for (int i = 0; i < data.size(); i++) {
            yArray[i] = data.get(i);
            xArray[i] = i;
        }
        xMin = 0;
        xMax = n-1;
        yMin = getArrayMin(yArray);
        if (yMin > 0) {
            yMin = 0;
        }
        // Можлива зміна значень для побудови графіку,
        // що зручний для перегляду
        yMax = getArrayMax(yArray);
        cdntOY = xMin;
        if (yMin >= 0)
            cdntOX = yMin;
        else if (yMin < 0 && yMax >= 0)
            cdntOX = 0;
        else
            cdntOX = yMax;
        paint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        float h = getHeight();
        float w = getWidth();
        // Знаходження координат для значень вектора
        int[] realCdntsX = getRealArrayCdnts(w, xMin, xMax, xArray);
        int[] realCdntsY = getRealArrayCdnts(h, yMin, yMax, yArray);
        // Знаходження величин, які визначають положення осей (типувідступів)
        int dOX = getRealCdnts(h, yMin, yMax, cdntOX);
        int dOY = getRealCdnts(w, xMin, xMax, cdntOY);
        paint.setStrokeWidth(5);
        // З'єднання точок лініями
        paint.setColor(Color.parseColor("#ad270c"));
        for (int i = 0; i < n - 1; i++) {
            canvas.drawLine(realCdntsX[i], h-realCdntsY[i], realCdntsX[i+1],
                    h-realCdntsY[i+1], paint);
        }
        paint.setColor(Color.BLACK);
        // Малювання осей
        canvas.drawLine(0, h-dOX, w, h-dOX, paint);
        canvas.drawLine(dOY, 0, dOY, h, paint);
        // Малювання стрілочок
        drawArrow(canvas, dOY, 0, dOY+10, 35, dOY-10, 35);
        drawArrow(canvas, w, h-dOX, w-35, h-dOX+10, w-35, h-dOX-10);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        // Малювання підписів значень до осей
        float textNumI;
        for (int i = 1; i <= 10; i++) {
            textNumI = Math.round(10*(xMin +(i-1)*(xMax - xMin)/10))/10;
            if (textNumI != 0) {
                canvas.drawText(String.valueOf(textNumI), (float)
                        getRealCdnts(w, xMin, xMax, textNumI), h-dOX+30, paint);
            }
            textNumI = Math.round(10*(yMin +(i-1)*(yMax - yMin)/10))/10;
            if (textNumI != 0) {
                canvas.drawText(String.valueOf(textNumI), dOY-20, h-(float)
                        getRealCdnts(h, yMin, yMax, textNumI), paint);
            }
        }
        canvas.drawText(String.valueOf(0.0), dOY-20, h-(float)
                getRealCdnts(h, yMin, yMax, 0.0f)-20, paint);
        canvas.drawText(String.valueOf(xMax), (float) getRealCdnts(w, xMin,
                xMax, xMax), h-dOX+30, paint);
        canvas.drawText(String.valueOf(yMax), dOY-20, h-(float)
                getRealCdnts(h, yMin, yMax, yMax), paint);
    }
    // Функція, що малює стілку
    private void drawArrow(Canvas canvas, float x1, float y1, float x2,
                           float y2, float x3, float y3) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path path1 = new Path();
        path1.moveTo(x1, y1);
        path1.lineTo(x2, y2);
        path1.lineTo(x3, y3);
        path1.lineTo(x1, y1);
        path1.close();
        canvas.drawPath(path1, paint);
        paint.setStyle(Paint.Style.FILL);
    }
    // Функція, що знаходить координати масиву певних величин на екрані
    private int[] getRealArrayCdnts(float dimension, float min, float max, float[] values) {
        int[] cdntsArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            cdntsArray[i] =
                    (int)(0.1f*dimension+((values[i]-min)/(max-min))*0.8f*dimension);
        }
        return cdntsArray;
    }
    // Функція, що знаходить координати певної величини на екрані
    private int getRealCdnts(float dimension, float min, float max, float value) {
        int cdnt;
        cdnt = (int)(0.1f*dimension+((value-min)/(max-min))*0.8f*dimension);
        return cdnt;
    }
    // Функція знаходження максимального значення в масиві
    private float getArrayMax(float[] arr) {
        float arrayMax = arr[0];
        for (float value : arr)
            if (value > arrayMax)
                arrayMax = value;
        return arrayMax;
    }
    // Функція знаходження мінімального значення в масиві
    private float getArrayMin(float[] arr) {
        float arrayMin = arr[0];
        for (float value : arr)
            if (value < arrayMin)
                arrayMin = value;
        return arrayMin;
    }
}