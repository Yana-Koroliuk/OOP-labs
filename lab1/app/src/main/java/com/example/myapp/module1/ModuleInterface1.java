package com.example.myapp.module1;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

// Інтерфейс діалогового вікна Роботи 1
public interface ModuleInterface1 {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState);
    public String[] getUserSelected();
}
