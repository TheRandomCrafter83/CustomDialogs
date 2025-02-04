package com.coderz.f1.customdialogssample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.coderz.f1.customdialogs.colordialog.ColorDialog;

public class ColorDialogDemo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content =inflater.inflate(R.layout.fragment_color_dialog_demo, container, false);
        TextView tv = content.findViewById(R.id.tester);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDialog cd = new ColorDialog(inflater.getContext(), new ColorDialog.DialogResponseListener() {
                    @Override
                    public void onOkClicked(ColorDialog.ReturnResult result) {

                    }

                    @Override
                    public void onCancelClicked() {

                    }
                });
                cd.setBackgroundColor(Color.RED);
                cd.setShowAsBottomSheet(true);
                cd.setDialogContentBackgroundColor(Color.BLUE);
                cd.showDialog();

            }
        });

        return content;
    }
}
