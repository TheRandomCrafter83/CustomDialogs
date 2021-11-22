package com.coderz.f1.customdialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import utils.Utils;

//this class is only meant to be inherited

public abstract class BaseDialog {
    public interface DialogResponseListener{
        void onOkClicked();
        void onCancelClicked();
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    Context context;
    protected DialogResponseListener baseListener;

    LinearLayout mainLayout = null;
    TextView txtTitle = null;
    View content = null;

    private String title = "";
    private Drawable dialogIcon;
    private int titleBarOrientation;
    private int titleBarGravity;
    private boolean showOkButton;
    private boolean showCancelButton;
    private String okButtonText;
    private String cancelButtonText;
    @ColorInt
    private int backgroundColor;
    @ColorInt
    private int textColor;

    protected abstract View getContent(ViewGroup parent);

    private int margins = 8; //Will need context to set actual margins

    public String getOkButtonText() {
        return okButtonText;
    }

    public void setOkButtonText(String okButtonText) {
        this.okButtonText = okButtonText;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    public boolean isShowOkButton() {
        return showOkButton;
    }

    public void setShowOkButton(boolean showOkButton) {
        this.showOkButton = showOkButton;
    }

    public boolean isShowCancelButton() {
        return showCancelButton;
    }

    public void setShowCancelButton(boolean showCancelButton) {
        this.showCancelButton = showCancelButton;
    }

    public void setMargins(int margins){this.margins = margins;}

    public int getMargins(){return this.margins;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDialogIcon() {
        return dialogIcon;
    }

    public void setDialogIcon(Drawable dialogIcon) {
        this.dialogIcon = dialogIcon;
    }

    public int getTitleBarOrientation() {
        return titleBarOrientation;
    }

    public void setTitleBarOrientation(int titleBarOrientation) {
        this.titleBarOrientation = titleBarOrientation;
    }

    public int getTitleBarGravity() {
        return titleBarGravity;
    }

    public void setTitleBarGravity(int titleBarGravity) {
        this.titleBarGravity = titleBarGravity;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public BaseDialog(@NonNull Context context){
        this.context = context;
//        this.baseListener = new DialogResponseListener() {
//            @Override
//            public void onOkClicked() {
//
//            }
//
//            @Override
//            public void onCancelClicked() {
//
//            }
//        };
        TypedValue a = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground,a,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (a.isColorType()){
                setBackgroundColor(a.data);
            }
        } else if(a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT){
            setBackgroundColor(a.data);
        }
        a = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground,a,true);
        context.getTheme().resolveAttribute(android.R.attr.text,a,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(a.isColorType()){
                setTextColor(a.data);
            }
        } else if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT){
            setTextColor(a.data);
        }
        setTitle(context.getApplicationInfo().loadLabel(context.getPackageManager()).toString());
        setTitleBarOrientation(HORIZONTAL);
        setTitleBarGravity(Gravity.CENTER_VERTICAL);
        setShowCancelButton(true);
        setShowOkButton(true);
        setOkButtonText("Ok");
        setCancelButtonText("Cancel");
        setMargins(8);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createLayout(View content){
        Resources r = context.getResources();
        int margin =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,getMargins(),r.getDisplayMetrics());

        mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin,margin,margin,margin);
        mainLayout.setLayoutParams(params);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(getBackgroundColor());

        //Create Titlebar
        LinearLayout titleBar = new LinearLayout(context);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tlp.setMargins(margin,margin,margin,margin);
        titleBar.setLayoutParams(tlp);
        titleBar.setGravity(titleBarGravity);
        titleBar.setOrientation(titleBarOrientation);
        //create titlebar icon
        if (dialogIcon !=null){
            ImageView img = new ImageView(context);
            LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ip.setMargins(0,0,margin,0);
            img.setLayoutParams(ip);
            img.setImageDrawable(dialogIcon);
            titleBar.addView(img);
        }

        //create titlebar text
        txtTitle = new TextView(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.weight = 1;
        txtTitle.setLayoutParams(p);
        txtTitle.setText(getTitle());
        txtTitle.setTextColor(getTextColor());
        txtTitle.setTypeface(txtTitle.getTypeface(),android.graphics.Typeface.BOLD);
        txtTitle.setTextSize(20f);
        titleBar.addView(txtTitle);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lParams.setMargins(margin,margin,margin,margin);
        content.setLayoutParams(lParams);
        mainLayout.addView(titleBar);
        mainLayout.addView(content);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCustomTitle(txtTitle);
        createLayout(getContent(mainLayout));
        builder.setView(mainLayout);
        if (showOkButton){builder.setPositiveButton(okButtonText, (_dialog, _which) -> baseListener.onOkClicked());}
        if (showCancelButton){builder.setNegativeButton(cancelButtonText, (_dialog, _which) -> baseListener.onCancelClicked());}
        AlertDialog dlg = builder.create();
        dlg.show();
        dlg.getWindow().getDecorView().getBackground().setColorFilter(new android.graphics.LightingColorFilter(0xff000000,getBackgroundColor()));
        dlg.getWindow().getDecorView().setBackground(Utils.getDialogBackgroundDrawable(getBackgroundColor()));
        dlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getTextColor());
        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getTextColor());


        Dialog dlg2 = new Dialog(context);

    }


}
