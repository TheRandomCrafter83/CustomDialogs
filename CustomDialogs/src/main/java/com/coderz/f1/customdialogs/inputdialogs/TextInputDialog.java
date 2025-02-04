package com.coderz.f1.customdialogs.inputdialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.coderz.f1.customdialogs.R;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.coderz.f1.customdialogs.BaseDialog;
import com.coderz.f1.customdialogs.databinding.TextinputdialogMainBinding;
import com.coderz.f1.customdialogs.datetimedialogs.DatePickerDialog;

import java.util.Calendar;

public class TextInputDialog extends BaseDialog implements BaseDialog.DialogResponseListener{
    public interface DialogResponseListener {
        void onOkClicked(TextInputDialog.ReturnResult result);
        void onCancelClicked();
    }
    public static class ReturnResult{
        private String mEnteredText;

        public String getEnteredText() {
            return mEnteredText;
        }

        public ReturnResult(String enteredText) {
            this.mEnteredText = enteredText;
        }
    }

    private final Context context;
    private TextinputdialogMainBinding binding;
    private final DialogResponseListener listener;

    public TextInputDialog(@NonNull Context context, DialogResponseListener listener) {
        super(context);
        this.context = context;
        baseListener = this;
        this.listener = listener;
    }

    @Override
    protected View getContent(ViewGroup parent) {
        return content(parent);
    }

    private ConstraintLayout content(ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = TextinputdialogMainBinding.inflate(inflater,parent,false);

        return binding.getRoot();
    }

    @Override
    public void onOkClicked() {
        listener.onOkClicked(new ReturnResult(binding.textViewUserInput.getText().toString()));
    }

    @Override
    public void onCancelClicked() {

    }
}
