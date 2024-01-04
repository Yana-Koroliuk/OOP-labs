package com.example.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapp.module1.Module1;
import com.example.myapp.module2.Module2;

// Створення класу MainActivity, що представляє головний екран додатку
public class MainActivity extends AppCompatActivity implements OnTextReceivedCallback{
    // Ініціалізація об'єктів TextView для Роботи 1, 2 програмно,
    // що забезпечить можливість зміни тексту
    @SuppressLint("StaticFieldLeak")
    public TextView textView1, textView2;

    // Створюємо інтерфейс екрану
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Встановлюємо розмітку дизайну
        // Присвоюємо значеня вищеініціалізованим
        // об'єктам за id
        setContentView(R.layout.main_activity);
        textView1 = findViewById(R.id.textWork1);
        textView2 = findViewById(R.id.textWork2);
    }

    // Створюємо меню, за розміткою з файлу R.menu.menu_main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // Створюємо обробник натиску елемента меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.work1) { // для Робота 1
            FragmentManager manager1 = getSupportFragmentManager();
            // Створюємо діалогове вікно та показуємо його
            Module1 module1 = new Module1(this);
            module1.show(manager1, "dialogWork1");
            return true;
        } else if (itemId == R.id.work2) { // для Робота 2
            FragmentManager manager2 = getSupportFragmentManager();
            // Створюємо діалогове вікно та показуємо його
            Module2 module2 = new Module2(this);
            module2.show(manager2, "dialogWork2");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTextReceivedModule1(String text) {
        textView2.setText("");
        textView1.setText(text);
    }

    @Override
    public void onTextReceivedModule2(String text) {
        textView1.setText("");
        textView2.setText(text);
    }
}