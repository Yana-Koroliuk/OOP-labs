package com.example.myapp.module1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapp.OnTextReceivedCallback;
import com.example.myapp.R;


// Клас, що описує далогове вікно Роботи 1
public class Module1 extends DialogFragment implements ModuleInterface1 {
    // Опис діалогового вікна в методі зворотнього виклику
    public OnTextReceivedCallback callback;
    public Module1(OnTextReceivedCallback callback) {
        this.callback = callback;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Resources res = getResources();
        final String[] groups = res.getStringArray(R.array.list_of_groups); // Список груп факультету
        final String[] selected = {""}; // Змінна, що зберігатиме обрану користувачем групу
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.headline1)
                .setPositiveButton(R.string.action_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onTextReceivedModule1(selected[0]);

                    }
                }) // Встановлення функції, що обробляє натиснення кнопки "Так"
                .setNegativeButton(R.string.action_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }) // Встановлення функції, що обробляє натиснення кнопки "Відміна"
                .setSingleChoiceItems(R.array.list_of_groups, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected[0] = groups[i];
                    }
                }); // Встановлення функції, що обробляє вибір групи користувача
        return builder.create();
    }

    @Override
    public String[] getUserSelected() {
        return null;
    }
}