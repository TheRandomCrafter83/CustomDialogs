package com.coderz.f1.customdialogssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import com.coderz.f1.customdialogs.colordialog.ColorDialog;

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
            ColorDialog colorDialog = new ColorDialog(MainActivity.this , new ColorDialog.DialogResponseListener() {
                @Override
                public void onOkClicked(int color) {
                    textView.setText(String.format(Locale.getDefault(),"%d",color));
                }

                @Override
                public void onCancelClicked() {

                }
            });
            colorDialog.setTextColor(Color.WHITE);
            colorDialog.setBackgroundColor(Color.DKGRAY);
            colorDialog.setInitialColor(Color.RED);
            colorDialog.setTitle("Choose A Color");




            colorDialog.setMargins(8);
            colorDialog.showDialog();
        });
    }
}