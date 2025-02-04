package com.coderz.f1.customdialogs.datetimedialogs;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.coderz.f1.customdialogs.BaseDialog;
import com.coderz.f1.customdialogs.R;
import com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker;
import com.coderz.f1.customdialogs.datetimedialogs.utils.Utils;

import java.util.Calendar;

public class TimePickerDialog  extends BaseDialog implements BaseDialog.DialogResponseListener{

    public interface DialogResponseListener {
        void onOkClicked(TimePickerDialog.ReturnResult result);
        void onCancelClicked();
    }
    public static class ReturnResult{
        private Calendar mSelectedTime;

        public Calendar getSelectedTime() {
            return mSelectedTime;
        }

        public ReturnResult(Calendar selectedTime) {
            this.mSelectedTime = selectedTime;
        }
    }

    public enum TimeFormat{
        FORMAT24, FORMAT12
    }

    Context context;
    DialogResponseListener listener;
    View content;

    NumberPicker hour;
    NumberPicker minute;
    NumberPicker am_pm;

    String[] amPmDisplayStrings = null;

    private TimeFormat mTimeFormat;
    private Calendar mSelectedTime;
    @ColorInt int mSelectionTextColor;

    public TimeFormat getTimeFormat() {
        return mTimeFormat;
    }

    public void setTimeFormat(TimeFormat mTimeFormat) {
        this.mTimeFormat = mTimeFormat;
    }

    public Calendar getSelectedTime() {
        return mSelectedTime;
    }

    public void setSelectedTime(Calendar mSelectedTime) {
        this.mSelectedTime = mSelectedTime;
    }

    public int getSelectionTextColor() {
        return mSelectionTextColor;
    }

    public void setSelectionTextColor(int mSelectionTextColor) {
        this.mSelectionTextColor = mSelectionTextColor;
    }

    public TimePickerDialog(@NonNull Context context,DialogResponseListener listener) {
        super(context);
        //Set some base stuff
        baseListener = this;
        this.context = context;
        this.listener = listener;

        //set default values
        setSelectedTime(Calendar.getInstance());
        setTimeFormat(TimeFormat.FORMAT12);
        int color = ResourcesCompat.getColor(context.getResources(), R.color.design_default_color_primary,context.getTheme());
        setSelectionTextColor(color);
    }

    @Override
    protected View getContent(ViewGroup parent) {
        return content(parent);
    }

    private View content(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        content = inflater.inflate(R.layout.timepickerdialog_main,parent,false);
        hour = content.findViewById(R.id.hour);
        minute = content.findViewById(R.id.minute);
        am_pm = content.findViewById(R.id.am_pm);

        hour.setSelectionTextColor(mSelectionTextColor);
        minute.setSelectionTextColor(mSelectionTextColor);
        am_pm.setSelectionTextColor(mSelectionTextColor);

        switch (mTimeFormat){
            case FORMAT12:
                am_pm.setVisibility(View.VISIBLE);
                hour.setMinValue(1);
                hour.setMaxValue(12);
                break;
            case FORMAT24:
                am_pm.setVisibility(View.GONE);
                hour.setMinValue(00);
                hour.setMaxValue(23);
        }
        am_pm.setMaxValue(1);
        am_pm.setMinValue(0);
        am_pm.setDisplayedValues("am,pm".split(","));

        minute.setMaxValue(59);
        minute.setMinValue(00);

        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        hour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (mTimeFormat){
                    case FORMAT24:
                        mSelectedTime.set(Calendar.HOUR_OF_DAY,newVal);
                        break;
                    case FORMAT12:
                        mSelectedTime.set(Calendar.HOUR,newVal);
                }
            }
        });

        minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mSelectedTime.set(Calendar.MINUTE,newVal);
            }
        });

        am_pm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mSelectedTime.set(Calendar.AM_PM,newVal);
            }
        });

        hour.setValue(mSelectedTime.get(Calendar.HOUR));
        minute.setValue (mSelectedTime.get(Calendar.MINUTE));
        am_pm.setValue(mSelectedTime.get(Calendar.AM_PM));

        return content;
    }


    @Override
    public void onOkClicked() {
        listener.onOkClicked(new ReturnResult(this.mSelectedTime));
    }

    @Override
    public void onCancelClicked() {
        listener.onCancelClicked();
    }

}
