package lab6.object3;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lab6.object3.drawer.Drawer;
public class Object3Activity extends AppCompatActivity {
    public Drawer drawer;
    public ArrayList<String> numArray;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip() && Objects.requireNonNull(clipboard.getPrimaryClip()).getItemCount() > 0) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String listString = item.getText().toString();
            numArray = new ArrayList<>(Arrays.asList(listString.split(", ")));
        } else {
            Intent intent3 = getIntent();
            numArray = intent3.getStringArrayListExtra("array");
        }
        if (numArray != null) {
            List<Float> data = new ArrayList<>();
            for (String num : numArray) {
                data.add(Float.parseFloat(num));
            }
            drawer = new Drawer(this, data);
            setContentView(drawer);
        } else {
            setContentView(R.layout.main);
            TextView textView = findViewById(R.id.text);
            textView.setText("Не задано вхідних даних");
            Button goButton = findViewById(R.id.button);
            goButton.setOnClickListener(v -> {
                Intent intent1 = new Intent("koroliuk.yana.intent.action.lab6");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (!item.isChecked()) item.setChecked(true);
        if (id == R.id.go_back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}