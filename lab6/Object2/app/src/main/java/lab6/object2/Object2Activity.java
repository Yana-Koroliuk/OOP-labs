package lab6.object2;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;
public class Object2Activity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            finish();
        });
        Button recordButton = findViewById(R.id.button);
        Intent intent2 = getIntent();
        final int n = intent2.getIntExtra("n", -1);
        final float min = intent2.getFloatExtra("min", 0);
        final float max = intent2.getFloatExtra("max", 0);
        if (n == -1) {
            TextView textView = findViewById(R.id.text);
            textView.setText(" Не задано вхідні дані" +
                    "\n для генерації вектора");
            recordButton.setOnClickListener(v -> {
                Toast toast = Toast.makeText(Object2Activity.this, "Задайте спочатку вхідні данні в Lab6", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            });
        }
        else {
            List<Float> list = new ArrayList<>();
            ArrayList<String> transferList = new ArrayList<>();
            int count = n;
            while (count > 0) {
                list.add((float) ((max-min)*Math.random()+min));
                count--;
            }
            TextView textView = findViewById(R.id.text);
            textView.setText("Згенерований вектор:");
            StringBuilder numbersString = new StringBuilder();
            for (Float num : list) {
                String[] numString = String.valueOf(num).split("\\.");
                numbersString.append(numString[0]).append(".");
                StringBuilder afterDot = new StringBuilder(numString[1]);
                numbersString.append(afterDot.delete(2, afterDot.length()));
                numbersString.append(", ");
                transferList.add(numString[0]+"."+ afterDot.delete(2, afterDot.length()));
            }
            numbersString.delete(numbersString.length()-2, numbersString.length());
            TextView numbersView = findViewById(R.id.numbers);
            numbersView.setText(numbersString);
            recordButton.setOnClickListener(v -> {
                String listString = String.join(", ", transferList);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("generated_vector", listString);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Вектор збережено у буфер обміну", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}
