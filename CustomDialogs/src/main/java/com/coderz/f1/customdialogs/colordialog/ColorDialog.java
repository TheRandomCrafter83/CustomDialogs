package com.coderz.f1.customdialogs.colordialog;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import com.coderz.f1.customdialogs.R;
import com.coderz.f1.customdialogs.colordialog.utils.Utils;

import org.w3c.dom.Text;

public class ColorDialog {


    public interface DialogResponseListener{
        void onOkClicked(@ColorInt int color);
        void onCancelClicked();
    }

    public enum TabIndex{
        PALETTE,
        RGB
    }

    Context context;
    DialogResponseListener listener;

    LinearLayout mainLayout = null;
    TextView txtTitle = null;
    View content = null;
    TextView selColor = null;
    SeekBar sbBrightness = null;
    SeekBar sbSaturation = null;
    SeekBar sbOpacity = null;
    TextView tvOpacity;
    TextView tvSaturation;
    TextView tvBrightness;

    //RGB layout
    SeekBar sbRed;
    SeekBar sbGreen;
    SeekBar sbBlue;
    SeekBar sbAlpha;
    TextView tvPreview2;
    TextView tvAlpha;
    TextView tvRed;
    TextView tvGreen;
    TextView tvBlue;

    //tabs
    LinearLayout tab1;
    LinearLayout tab2;
    TextView tab1_text;
    TextView tab2_text;
    TextView tabSelector1;
    TextView tabSelector2;
    ViewGroup tabContent1;
    ViewGroup tabContent2;

    private String title = "";
    @ColorInt
    private int initialColor = Color.BLACK;
    @ColorInt
    private int backgroundColor;
    @ColorInt
    private int textColor;
    @ColorInt
    private int selectedColor = 0;

    private TabIndex tabIndex = TabIndex.PALETTE;

    int selHue = 0;

    private int margins = 8; //Will need context to set actual margins

    public TabIndex getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(TabIndex tabIndex) {
        this.tabIndex = tabIndex;
    }

    public void setMargins(int margins){this.margins = margins;}

    public int getMargins(){return this.margins;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
        this.selectedColor = initialColor;
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

    public ColorDialog(@NonNull Context context, DialogResponseListener listener){
        this.context = context;
        this.listener = listener;
        //margins = (int) (32 * Resources.getSystem().getDisplayMetrics().density);
        TypedValue a = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground,a,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (a.isColorType()){
            setBackgroundColor(a.data);
            }
        } else if(a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT){
            setBackgroundColor(a.data);
        }
        setTextColor(Utils.getContrast(getBackgroundColor()));
        setTitle(context.getApplicationInfo().loadLabel(context.getPackageManager()).toString());
        setMargins(8);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View createLayout(){
        Resources r = context.getResources();
        int margin =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,getMargins(),r.getDisplayMetrics());

        mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin,margin,margin,margin);
        mainLayout.setLayoutParams(params);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(getBackgroundColor());

        txtTitle = new TextView(context);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tlp.setMargins(margin,margin,margin,margin);
        txtTitle.setLayoutParams(tlp);
        txtTitle.setText(getTitle());
        txtTitle.setTextColor(getTextColor());
        txtTitle.setTypeface(txtTitle.getTypeface(),android.graphics.Typeface.BOLD);
        txtTitle.setTextSize(20f);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        content = inflater.inflate(R.layout.colordialog_color_picker, mainLayout, false);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lParams.setMargins(margin,margin,margin,margin);
        content.setLayoutParams(lParams);
        mainLayout.addView(txtTitle);
        mainLayout.addView(content);

        tab1 = content.findViewById(R.id.tab1);
        tab2 = content.findViewById(R.id.tab2);
        tab1_text = content.findViewById(R.id.tab_text1);
        tab2_text = content.findViewById(R.id.tab_text2);
        tabSelector1 = content.findViewById(R.id.tab_selector1);
        tabSelector2 = content.findViewById(R.id.tab_selector2);
        sbRed = content.findViewById(R.id.sb_red);
        sbGreen = content.findViewById(R.id.sb_green);
        sbBlue = content.findViewById(R.id.sb_blue);
        sbAlpha = content.findViewById(R.id.sb_alpha);
        tvPreview2 = content.findViewById(R.id.tv_preview2);
        tvAlpha = content.findViewById(R.id.tv_alpha);
        tvRed = content.findViewById(R.id.tv_red);
        tvGreen = content.findViewById(R.id.tv_green);
        tvBlue = content.findViewById(R.id.tv_blue);
        tvOpacity = content.findViewById(R.id.tv_opacity);
        tvBrightness = content.findViewById(R.id.tv_brightness);
        tvSaturation = content.findViewById(R.id.tv_saturation);

        tvAlpha.setTextColor(getTextColor());
        tvRed.setTextColor(getTextColor());
        tvGreen.setTextColor(getTextColor());
        tvBlue.setTextColor(getTextColor());
        tvOpacity.setTextColor(getTextColor());
        tvBrightness.setTextColor(getTextColor());
        tvSaturation.setTextColor(getTextColor());


        tab1_text.setTextColor(getTextColor());
        tab2_text.setTextColor(getTextColor());

        tabContent1 = content.findViewById(R.id.palette_layout);
        tabContent2 = content.findViewById(R.id.rgb_layout);

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabIndex(TabIndex.PALETTE);
                setSelectedTab();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabIndex(TabIndex.RGB);
                setSelectedTab();
            }
        });


        ImageView colorPalette = content.findViewById(R.id.iv_color_palette);
        colorPalette.setClipToOutline(true);
        colorPalette.setClickable(true);
        colorPalette.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                v.performClick();
            }
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                ImageView imageView = (ImageView)v;
                imageView.setImageResource(R.drawable.colordialog_huepalette);
                Bitmap bmp = Bitmap.createBitmap(v.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                android.graphics.Canvas canvas = new android.graphics.Canvas(bmp);
                imageView.draw(canvas);
                //Create a Color Object and set it to the selected Color
                int color;
                int eventX = (int)event.getX();
                int eventY = (int)event.getY();
                int x = eventX ;
                int y = eventY;
                if (x < 0){
                    x=0;
                }else if(x > bmp.getWidth()-1){
                    x = bmp.getWidth()-1;
                }
                if(y < 0){
                    y = 0;
                }else if(y > bmp.getHeight()-1){
                    y = bmp.getHeight()-1;
                }
                //Show the color value
                color = bmp.getPixel(x, y);
                int hue;
                float[] hsv = new float[3];
                Color.colorToHSV(color,hsv);
                hue = (int)hsv[0];
                if(Color.alpha(color)>0){
                    imageView.setImageDrawable(Utils.getPalette(context, bmp, x, y));
                    setPreview(hue);
                }
                return true;
            }
            return false;
        });

        selColor = content.findViewById(R.id.tv_preview);
        Utils.setCompoundDrawable(context,selColor,0,R.drawable.colordialog_copy);
        Utils.setCompoundDrawable(context,selColor,2,R.drawable.colordialog_paste);
        selColor.setClickable(true);
        selColor.setOnTouchListener((view, motionEvent) -> {
            int[] textLocation = new int[2];
            selColor.getLocationOnScreen(textLocation);
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if(motionEvent.getRawX() >= textLocation[0] + selColor.getWidth() - selColor.getTotalPaddingRight()){
                    @ColorInt int color = Utils.doPaste(context);
                    finishPaste(color);
                    return true;
                } else if(motionEvent.getRawX() <= textLocation[0] + selColor.getTotalPaddingLeft()) {
                    Utils.doCopy(context,selectedColor);
                    return true;
                }
                view.performClick();
            }
            return false;
        });

        Utils.setCompoundDrawable(context,tvPreview2,0,R.drawable.colordialog_copy);
        Utils.setCompoundDrawable(context,tvPreview2,2,R.drawable.colordialog_paste);
        tvPreview2.setClickable(true);
        tvPreview2.setOnTouchListener((view, motionEvent) -> {
            int[] textLocation = new int[2];
            tvPreview2.getLocationOnScreen(textLocation);
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if(motionEvent.getRawX() >= textLocation[0] + tvPreview2.getWidth() - tvPreview2.getTotalPaddingRight()){
                    @ColorInt int color = Utils.doPaste(context);
                    finishPaste(color);
                    return true;
                } else if(motionEvent.getRawX() <= textLocation[0] + tvPreview2.getTotalPaddingLeft()) {
                    Utils.doCopy(context,selectedColor);
                    return true;
                }
                view.performClick();
            }
            return false;
        });


        sbOpacity = content.findViewById(R.id.sb_opacity);
        sbOpacity.setProgress(Color.alpha(getInitialColor()));
        sbOpacity.setSplitTrack(false);
        sbOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser){
                setPreview(selHue);
            }
        });

        sbBrightness = content.findViewById(R.id.sb_brightness);
        float[]hsv = new float[3];
        Color.colorToHSV(getInitialColor(),hsv);
        float b = hsv[2] * 100;
        int brightness = (int) b ;
        sbBrightness.setProgress(brightness);
        sbBrightness.setSplitTrack(false);
        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser){
                setPreview(selHue);
            }
        });

        sbSaturation = content.findViewById(R.id.sb_saturation);
        int saturation =(int) (hsv[1] * 100) ;
        sbSaturation.setProgress(saturation);
        sbSaturation.setSplitTrack(false);
        sbSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser){
                setPreview(selHue);
            }
        });
        sbAlpha.setSplitTrack(false);
        sbRed.setSplitTrack(false);
        sbGreen.setSplitTrack(false);
        sbBlue.setSplitTrack(false);

        sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r,g,b,a;
                r = sbRed.getProgress();
                g = sbGreen.getProgress();
                b = sbBlue.getProgress();
                a = sbAlpha.getProgress();
                @ColorInt int color = Color.argb(a,r,g,b);
                setPreview2(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r,g,b,a;
                r = sbRed.getProgress();
                g = sbGreen.getProgress();
                b = sbBlue.getProgress();
                a = sbAlpha.getProgress();
                @ColorInt int color = Color.argb(a,r,g,b);
                setPreview2(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r,g,b,a;
                r = sbRed.getProgress();
                g = sbGreen.getProgress();
                b = sbBlue.getProgress();
                a = sbAlpha.getProgress();
                @ColorInt int color = Color.argb(a,r,g,b);
                setPreview2(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r,g,b,a;
                r = sbRed.getProgress();
                g = sbGreen.getProgress();
                b = sbBlue.getProgress();
                a = sbAlpha.getProgress();
                @ColorInt int color = Color.argb(a,r,g,b);
                setPreview2(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setSelectedTab();
        return mainLayout;
    }

    void setSelectedTab(){
        @ColorInt int tmpColor = selectedColor;
        switch(getTabIndex()){
            case PALETTE:
                float[] hsv = new float[3];
                Color.colorToHSV(tmpColor,hsv);
                sbOpacity.setProgress(Color.alpha(tmpColor));
                sbSaturation.setProgress((int)(hsv[1]*100));
                sbBrightness.setProgress((int)(hsv[2]*100));
                tabSelector1.setBackgroundColor(getTextColor());
                tabSelector2.setBackgroundColor(Color.TRANSPARENT);
                tab1_text.setTypeface(tab1_text.getTypeface(), Typeface.BOLD);
                tab2_text.setTypeface(tab2_text.getTypeface(), Typeface.NORMAL);
                tabContent1.setVisibility(View.VISIBLE);
                tabContent2.setVisibility(View.GONE);
                setPreview((int)hsv[0]);
                break;
            case RGB:
                sbAlpha.setProgress(Color.alpha(tmpColor));
                sbRed.setProgress(Color.red(tmpColor));
                sbGreen.setProgress(Color.green(tmpColor));
                sbBlue.setProgress(Color.blue(tmpColor));
                tabSelector2.setBackgroundColor(getTextColor());
                tabSelector1.setBackgroundColor(Color.TRANSPARENT);
                tab2_text.setTypeface(tab2_text.getTypeface(), Typeface.BOLD);
                tab1_text.setTypeface(tab1_text.getTypeface(), Typeface.NORMAL);
                tabContent2.setVisibility(View.VISIBLE);
                tabContent1.setVisibility(View.GONE);
                //setPreview2(tmpColor);
        }
    }

    void finishPaste(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color,hsv);
        float a = Color.alpha(color);
        sbOpacity.setProgress((int)a);
        sbBrightness.setProgress((int)(hsv[2]*100));
        sbSaturation.setProgress((int)(hsv[1]*100));
        sbRed.setProgress(Color.red(color));
        sbGreen.setProgress(Color.green(color));
        sbBlue.setProgress(Color.blue(color));
        sbAlpha.setProgress((int)a);
        setPreview((int)hsv[0]);
        Toast.makeText(context,"Color pasted successfully.",Toast.LENGTH_SHORT).show();
    }

    @ColorInt
    private int getColorFromSeekBars(){
        float[] hsv = new float[3];
        hsv[0] = (float)selHue;
        hsv[1] = (float)sbSaturation.getProgress()/100;
        hsv[2] = (float) sbBrightness.getProgress()/100;
        return Color.HSVToColor(sbOpacity.getProgress(),hsv);
    }

    private void setPreview(int hue) {
        int color;
        selHue = hue;
        float[] hsv = new float[3];
        hsv[0] = (float) hue;
        hsv[1] = (float) sbSaturation.getProgress() / 100;

        hsv[2] = (float) sbBrightness.getProgress() / 100;
        int opacity = sbOpacity.getProgress();
        color = Color.HSVToColor(opacity, hsv);

        float[] hsv2 = new float[3];
        hsv2[0] = (float)hue;
        hsv2[1] = 1f;
        hsv2[2] = 1f;
        int color2 = Color.HSVToColor(hsv2);
        sbOpacity.setProgressDrawable(Utils.getOpacityDrawable(context, color2));
        sbBrightness.setProgressDrawable(Utils.getBrightnessDrawable(color2));
        sbSaturation.setProgressDrawable(Utils.getSaturationDrawable(context, color2));
        selColor.setText(Utils.colorToHTMLColor(color));
        if (sbOpacity.getProgress()>sbOpacity.getMax()/2) {
            selColor.setTextColor(Utils.getContrast(color));
            Utils.makeViewRounded(selColor, color, Utils.getContrast(color));
            Utils.setCompoundDrawableColor(selColor, Utils.getContrast(color));
        }else{
            selColor.setTextColor(Utils.getContrast(getBackgroundColor()));
            Utils.makeViewRounded(selColor, color, Utils.getContrast(getBackgroundColor()));
            Utils.setCompoundDrawableColor(selColor, Utils.getContrast(getBackgroundColor()));
        }
        selectedColor = color;
    }

    private void setPreview2(@ColorInt int color){
        @ColorInt int color2 = Color.argb(255,Color.red(color),Color.green(color),Color.blue(color));
        sbAlpha.setProgressDrawable(Utils.getOpacityDrawable(context,color2));
        tvPreview2.setText(Utils.colorToHTMLColor(color));
        if(sbAlpha.getProgress()>sbAlpha.getMax()/2) {
            tvPreview2.setTextColor(Utils.getContrast(color));
            Utils.makeViewRounded(tvPreview2, color, Utils.getContrast((color)));
            Utils.setCompoundDrawableColor(tvPreview2, Utils.getContrast(color));
        } else {
            tvPreview2.setTextColor(Utils.getContrast(getBackgroundColor()));
            Utils.makeViewRounded(tvPreview2, color, Utils.getContrast((getBackgroundColor())));
            Utils.setCompoundDrawableColor(tvPreview2, Utils.getContrast(getBackgroundColor()));
        }
        selectedColor = color;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCustomTitle(txtTitle);
        builder.setView(createLayout());
        builder.setPositiveButton("Ok", (_dialog, _which) -> listener.onOkClicked(selectedColor));

        builder.setNegativeButton("Cancel", (_dialog, _which) -> listener.onCancelClicked());
        AlertDialog dlg = builder.create();
        dlg.show();
        dlg.getWindow().getDecorView().getBackground().setColorFilter(new android.graphics.LightingColorFilter(0xff000000,getBackgroundColor()));
        dlg.getWindow().getDecorView().setBackground(Utils.getDialogBackgroundDrawable(getBackgroundColor()));
        dlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getTextColor());
        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getTextColor());
    }
}
