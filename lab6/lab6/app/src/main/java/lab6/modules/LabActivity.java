package lab6.modules;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
public class LabActivity extends AppCompatActivity {
    private ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent1 = getIntent();
        ArrayList<String> array = intent1.getStringArrayListExtra("array");
        if (array != null) {
            data = array;
        }
        Button generateButton = findViewById(R.id.button1);
        generateButton.setOnClickListener(v -> {
            EditText inputN = findViewById(R.id.edit_text_1);
            EditText inputMin = findViewById(R.id.edit_text_2);
            EditText inputMax = findViewById(R.id.edit_text_3);
            int n;
            float min;
            float max;
            try {
                try{
                    n = Integer.parseInt(String.valueOf(inputN.getText()));
                    if (n <= 0) {
                        Toast toast = Toast.makeText(LabActivity.this,
                                "Перевірте корекність введених даних:n має бути більше нуля",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(LabActivity.this, "Перевірте корекність введених даних:" +
                            "n має бути цілим числом", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                try {
                    min =
                            Float.parseFloat(String.valueOf(inputMin.getText()));
                    max =
                            Float.parseFloat(String.valueOf(inputMax.getText()));
                    if (min > max) {
                        Toast toast = Toast.makeText(LabActivity.this,
                                "Перевірте корекність введених даних: min має бути менше за max",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(LabActivity.this, "Перевірте корекність введених даних" +
                            "чи запвнено всі поля числами?",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                Intent intent2 = new Intent();
                intent2.setClassName("lab6.object2", "lab6.object2.Object2Activity");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("n", n);
                intent2.putExtra("min", min);
                intent2.putExtra("max", max);
                startActivity(intent2);
            } catch (Exception e) {
                Toast toast = Toast.makeText(LabActivity.this, "Перевірте корекність введених даних:" +
                "чи запвнено всі поля числами?", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        Button graphicsButton = findViewById(R.id.button2);
        graphicsButton.setOnClickListener(v -> {
            if (data == null) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard.hasPrimaryClip() && Objects.requireNonNull(clipboard.getPrimaryClip()).getItemCount() > 0) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    String listString = item.getText().toString();
                    data = new ArrayList<>(Arrays.asList(listString.split(", ")));
                }
            }

            if (data != null && !data.isEmpty()) {
                Intent intent3 = new Intent();
                intent3.setClassName("lab6.object3", "lab6.object3.Object3Activity");
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.putStringArrayListExtra("array", data);
                startActivity(intent3);
            } else {
                Toast.makeText(LabActivity.this, "Вам слід попередньо згенерувати та записати вектор", Toast.LENGTH_SHORT).show();
            }
        });
    }
}