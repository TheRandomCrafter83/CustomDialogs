package utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public class Utils {
    public static Drawable getDialogBackgroundDrawable(@ColorInt int backgroundColor){
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setCornerRadius(20);
        gd.setColor(backgroundColor);
        gd.setStroke(2, Color.BLACK);
        return gd;
    }
}
