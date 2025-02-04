package com.coderz.f1.customdialogssample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderz.f1.customdialogs.colordialog.ColorDialog;
import com.coderz.f1.customdialogs.datetimedialogs.DatePickerDialog;
import com.coderz.f1.customdialogs.datetimedialogs.TimePickerDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FragmentManager fm = getSupportFragmentManager();

    final Fragment colorFragment = new ColorDialogDemo();
    final Fragment dateFragment = new DateDialogDemo();
    final Fragment timeFragment = new TimeDialogDemo();

    final String colorTitle = "Color Dialog Demo";
    final String dateTitle = "Date Dialog Demo";
    final String timeTitle = "Time Dialog Demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().replace(R.id.content_main,colorFragment).commit();

        bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(item -> {

            switch (item.getTitle().toString()){
                case colorTitle:
                    fm.beginTransaction().replace(R.id.content_main,colorFragment).commit();
                    return true;
                case dateTitle:
                    fm.beginTransaction().replace(R.id.content_main,dateFragment).commit();
                    return true;
                case timeTitle:
                    fm.beginTransaction().replace(R.id.content_main,timeFragment).commit();
                    return true;
            }

            return false;
        });
    }


}