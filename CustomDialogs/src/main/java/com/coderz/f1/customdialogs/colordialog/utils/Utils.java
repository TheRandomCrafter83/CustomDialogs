package com.coderz.f1.customdialogs.colordialog.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;

import com.coderz.f1.customdialogs.R;

public class Utils {
    public static Drawable getOpacityDrawable(Context context, int baseColor){
        int[]colors = {Color.TRANSPARENT, baseColor};
        GradientDrawable pgd = new GradientDrawable();
        pgd.setColors(colors);
        pgd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        Drawable[] layers = {getCheckerDrawable(context),pgd};
        return new LayerDrawable(layers);
    }

    public static Drawable getBrightnessDrawable(int baseColor){
        float[]hsv = new float[3];
        Color.colorToHSV(baseColor, hsv);
        hsv[2] = 0;
        int colorStart = Color.HSVToColor(hsv);
        hsv[2] = 1;
        int colorEnd = Color.HSVToColor(hsv);
        int[]colors = {colorStart, colorEnd};
        GradientDrawable pgd = new GradientDrawable();
        pgd.setColors(colors);
        pgd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        return pgd;
    }

    public static Drawable getSaturationDrawable(Context context, int baseColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(baseColor, hsv);
        hsv[1] = 0;
        int startColor = Color.HSVToColor(hsv);
        hsv[1] = 1;
        int endColor = Color.HSVToColor(hsv);
        int[] colors = {startColor, endColor};
        GradientDrawable pgd = new GradientDrawable();
        pgd.setColors(colors);
        pgd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        Drawable[] layers = {getCheckerDrawable(context), pgd};
        return new LayerDrawable(layers);
    }

    private static BitmapDrawable getCheckerDrawable(Context context){
        BitmapDrawable bd = (BitmapDrawable) ResourcesCompat.getDrawable(context.getResources(), R.drawable.colordialog_checkerboard, null);
        bd.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return bd;
    }

    Drawable getDotDrawable(int x, int y){
        ShapeDrawable ret = new ShapeDrawable(new OvalShape());
        ret.setBounds(x,y,x+8,y+8);
        return ret;
    }

    public static Drawable getPalette(Context context, Bitmap buffer, int x, int y){
        BitmapDrawable ret = null;
        Canvas canvas = new Canvas(buffer);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(4F);
        p.setStyle(Paint.Style.STROKE);
        p.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SCREEN));
        canvas.drawCircle((float)x,(float)y,20F,p);
        p.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SCREEN));
        p.setStrokeWidth(2F);
        canvas.drawCircle((float)x,(float)y,19F,p);
        ret = new BitmapDrawable(context.getResources(), buffer);
        return ret;
    }

    public static int getContrast(int color) {
        int y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

    public static boolean isHtmlColor(String s){
        boolean ret = false;
        try{
            Color.parseColor(s);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String colorToHTMLColor(int color){
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        String hex = String.format("#%02x%02x%02x%02x",a, r, g, b);
        return hex.toUpperCase();
    }

    public static void setCompoundDrawable (Context context, final TextView view, final int location, final int resID) {
        Drawable newDrawable = ResourcesCompat.getDrawable(context.getResources(),resID,null);
        Drawable drawableLeft = view.getCompoundDrawables()[0];
        Drawable drawableTop = view.getCompoundDrawables()[1];
        Drawable drawableRight = view.getCompoundDrawables()[2];
        Drawable drawableBottom = view.getCompoundDrawables()[3];
        switch(location){
            case 0: //left
                view.setCompoundDrawablesWithIntrinsicBounds(newDrawable,drawableTop,drawableRight,drawableBottom);
                break;
            case 1: //top
                view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,newDrawable,drawableRight,drawableBottom);
                break;
            case 2: //right
                view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,drawableTop,newDrawable,drawableBottom);
                break;
            case 3: //bottom
                view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,drawableTop,drawableRight,newDrawable);
                break;
        }
    }

    public static void setCompoundDrawableColor (final TextView view, final int color) {
        int[][] states = new int[][] {new int[] {android.R.attr.state_enabled}};
        int[] colors = new int[] {color};
        ColorStateList list = new ColorStateList(states,colors);
        view.setCompoundDrawableTintList(list);
    }

    public static Drawable getDialogBackgroundDrawable(@ColorInt int backgroundColor){
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setCornerRadius(20);
        gd.setColor(backgroundColor);
        gd.setStroke(2,Color.BLACK);
        return gd;
    }

    public static void makeViewRounded (final View view, final int color, final int textColor) {
        TextView tv = (TextView)view;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(20);
        gd.setStroke(1, getContrast(color));
        gd.setColor(color);
        tv.setBackground(gd);
        tv.setTextColor(textColor);
    }

    public static void doCopy(Context context, @ColorInt int selectedColor){
        android.content.ClipboardManager clipboard =(android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData data = android.content.ClipData.newPlainText("Copied Color",Integer.toString(selectedColor));
        clipboard.setPrimaryClip(data);
        Toast.makeText(context,"Color copied to clipboard.",Toast.LENGTH_SHORT).show();
    }

    @ColorInt
    public static int doPaste(Context context){
        @ColorInt int c = 0;
        android.content.ClipboardManager clipboard =(android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        String data = item.getText().toString();
        if (data != null){
            try{
                c = Integer.parseInt(data);
            } catch(Exception e) {
                if (isHtmlColor(data)){
                    c = Color.parseColor(data);
                } else {
                    Toast.makeText(context,"Invalid Color Format",Toast.LENGTH_SHORT).show();
                }
            }
        }
        return c;
    }
}
