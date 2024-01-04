package com.example.myapp.module2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapp.MainActivity;
import com.example.myapp.OnTextReceivedCallback;
import com.example.myapp.R;


// Клас, що описує діалогове вікно Роботи 2
public class Module2 extends DialogFragment implements ModuleInterface2 {
    // Опис діалогового вікна в методі зворотнього виклику
    public OnTextReceivedCallback callback;
    public Module2(OnTextReceivedCallback callback) {
        this.callback = callback;
    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.module2, null);
        builder.setTitle(R.string.headline2).setView(view);
        // Створення обробника тексту, що вводиться користувачем
        final EditText editText = (EditText) view.findViewById(R.id.textWork2);
        builder.setPositiveButton(R.string.action_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = editText.getText().toString();
                callback.onTextReceivedModule2(s);
            }
        }); // Встановлення функції, що обробляє натиск кнопки "Так"
        builder.setNegativeButton(R.string.action_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }); // Встановлення функції, що обробляє натиск кнопки "Відміна"
        return builder.create();
    }

    @Override
    public String getUserText() {
        return null;
    }
}