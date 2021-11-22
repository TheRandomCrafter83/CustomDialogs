package com.coderz.f1.customdialogssample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderz.f1.customdialogs.BaseDialog;
import com.coderz.f1.customdialogs.colordialog.ColorDialog;
import com.coderz.f1.customdialogs.datetimedialogs.DatePickerDialog;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            button_click(view);
        });
    }

    private void button_click(View v){

    }
}