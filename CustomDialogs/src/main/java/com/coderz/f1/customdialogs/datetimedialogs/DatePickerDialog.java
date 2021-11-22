package com.coderz.f1.customdialogs.datetimedialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.coderz.f1.customdialogs.BaseDialog;
import com.coderz.f1.customdialogs.R;

public class DatePickerDialog extends BaseDialog implements BaseDialog.DialogResponseListener{
    Context context;
    DialogResponseListener listener;
    public DatePickerDialog(@NonNull Context context, DialogResponseListener listener) {
        super(context);
        baseListener = this;
        this.context = context;
        this.listener = listener;
    }

    //NOTE TO SELF: all dialog variations WILL need its own Result interface

    @Override
    protected View getContent(ViewGroup parent) {
        return content(parent);
    }

    private View content(ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View ret = inflater.inflate(R.layout.datepickerdialog_main,parent,false);

        return ret;
    }

    @Override
    public void onOkClicked() {

    }

    @Override
    public void onCancelClicked() {

    }
}
