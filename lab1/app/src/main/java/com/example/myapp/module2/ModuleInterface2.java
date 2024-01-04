package com.example.myapp.module2;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

// Інтерфейс діалогового вікна Роботи 2
public interface ModuleInterface2 {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState);
    public String getUserText();
}
