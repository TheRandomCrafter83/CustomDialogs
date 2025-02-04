package com.coderz.f1.customdialogs.datetimedialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.coderz.f1.customdialogs.BaseDialog;
import com.coderz.f1.customdialogs.R;
import com.coderz.f1.customdialogs.datetimedialogs.utils.Utils;


import java.util.Calendar;

public class DatePickerDialog extends BaseDialog implements BaseDialog.DialogResponseListener{
    public interface DialogResponseListener {
        void onOkClicked(DatePickerDialog.ReturnResult result);
        void onCancelClicked();
    }
    public static class ReturnResult{
        private Calendar mSelectedDate;

        public Calendar getSelectedDate() {
            return mSelectedDate;
        }

        public ReturnResult(Calendar selectedDate) {
            this.mSelectedDate = selectedDate;
        }
    }
    public enum MonthFormat{
        Numerical,
        ShortName,
        FullName
    }
    Context context;
    DatePickerDialog.DialogResponseListener listener;
    View content;

    NumberPicker month;
    NumberPicker day;
    NumberPicker year;
    String[] displayNames = null;

    //properties
    private MonthFormat mMonthFormat;
    private int mStartYear;
    private int mNumberOfYears;
    private Calendar mSelectedDate;

    @ColorInt private int mSelectionTextColor;


    public int getSelectionTextColor() {
        return mSelectionTextColor;
    }

    public void setSelectionTextColor(int mSelectionBarTextColor) {
        this.mSelectionTextColor = mSelectionBarTextColor;
    }

    public Calendar getSelectedDate() {
        return mSelectedDate;
    }

    public void setSelectedDate(Calendar mSelectedDate) {
        this.mSelectedDate = mSelectedDate;
    }

    public int getStartYear() {
        return mStartYear;
    }

    public void setStartYear(int mStartYear) {
        this.mStartYear = mStartYear;
    }

    public int getNumberOfYears() {
        return mNumberOfYears;
    }

    public void setNumberOfYears(int mNumberOfYears) {
        this.mNumberOfYears = mNumberOfYears;
    }

    public MonthFormat getMonthFormat() {
        return mMonthFormat;
    }

    public void setMonthFormat(MonthFormat mMonthFormat) {
        this.mMonthFormat = mMonthFormat;
    }

    public DatePickerDialog(@NonNull Context context, DatePickerDialog.DialogResponseListener listener) {
        super(context);
        baseListener = this;
        this.context = context;
        this.listener = listener;
        //set the default values
        setSelectedDate(Calendar.getInstance());
        setStartYear(this.mSelectedDate.get(Calendar.YEAR));
        setNumberOfYears(8);
        setMonthFormat(MonthFormat.FullName);
        int color = ResourcesCompat.getColor(context.getResources(),R.color.design_default_color_primary,context.getTheme());
        setSelectionTextColor(color);
    }

    //NOTE TO SELF: all dialog variations WILL need its own Result interface
    //NOTE TO SELF: Try overlaying multiple number pickers for selected bar text color

    @Override
    protected View getContent(ViewGroup parent) {
        return content(parent);
    }

    private View content(ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        content = inflater.inflate(R.layout.datepickerdialog_main,parent,false);

        month = content.findViewById(R.id.month1);
        day = content.findViewById(R.id.day1);
        year = content.findViewById(R.id.year1);
        month.setMinValue(1);
        month.setMaxValue(12);

        month.setSelectionTextColor(this.mSelectionTextColor);
        day.setSelectionTextColor(this.mSelectionTextColor);
        year.setSelectionTextColor(this.getSelectionTextColor());

        switch(this.mMonthFormat){
            case FullName:
                displayNames = context.getString(R.string.month_names).split(",");
                break;
            case Numerical:
                displayNames = context.getString(R.string.month_numbers).split(",");
                break;
            case ShortName:
                displayNames = context.getString(R.string.month_abbreviations).split(",");
        }
        month.setDisplayedValues(displayNames);
        month.setOnValueChangedListener(new com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker picker, int oldVal, int newVal) {
                loadDays();
                mSelectedDate.set(Calendar.MONTH,month.getValue()-1);
            }
        });
        month.setValue(this.mSelectedDate.get(Calendar.MONTH)+1);
        year.setMinValue(this.mStartYear);
        year.setMaxValue(this.mStartYear + this.mNumberOfYears);
        year.setOnValueChangedListener(new com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.coderz.f1.customdialogs.datetimedialogs.custom_views.NumberPicker picker, int oldVal, int newVal) {
                loadDays();
                mSelectedDate.set(Calendar.YEAR,year.getValue());
            }
        });
        year.setValue(this.mSelectedDate.get(Calendar.YEAR));
        loadDays();
        day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mSelectedDate.set(Calendar.DATE,newVal);
            }
        });

        day.setValue(this.mSelectedDate.get(Calendar.DATE));
        return content;
    }

    void loadDays(){
        day.setMinValue(1);
        if (year.getValue() % 4 == 0 && month.getValue() == 2){
            day.setMaxValue(29);
            return;
        }
        if (month.getValue()==2){
            day.setMaxValue(28);
            return;
        }
        if (month.getValue()==4||month.getValue()==6||month.getValue()==9||month.getValue()==11){
            day.setMaxValue(30);
            return;
        }
        day.setMaxValue(31);
    }

    @Override
    public void onOkClicked() {
        listener.onOkClicked(new ReturnResult(getSelectedDate()));
    }

    @Override
    public void onCancelClicked() {
        listener.onCancelClicked();
    }
}
