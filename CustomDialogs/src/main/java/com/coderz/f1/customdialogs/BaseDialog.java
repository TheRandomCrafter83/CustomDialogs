package com.coderz.f1.customdialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import org.jetbrains.annotations.NotNull;

import utils.Utils;

//this class is only meant to be inherited

public abstract class BaseDialog {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected DialogResponseListener baseListener;
    Context context;
    LinearLayout mainLayout = null;
    TextView txtTitle = null;
    View content = null;
    private String title = "";
    private Drawable dialogIcon;
    @ColorInt
    private int dialogIconTint;
    private int titleBarOrientation;
    private int titleBarGravity;
    private boolean showOkButton;
    private boolean showCancelButton;
    private String okButtonText;
    private String cancelButtonText;
    private boolean showAsBottomSheet;
    @ColorInt
    private int backgroundColor;
    @DrawableRes
    private int dialogBackgroundDrawableResource;
    private Drawable dialogBackgroundDrawable;
    @ColorInt
    private int dialogContentBackgroundColor;
    private Drawable dialogContentBackgroundDrawable;
    @DrawableRes
    private int dialogContentBackgroundResource;
    @ColorInt
    private int textColor;
    private int margins = 8; //Will need context to set actual margins
    private BottomSheetDialog bsd;

    public BaseDialog(@NonNull Context context) {
        this.context = context;
        TypedValue a = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (a.isColorType()) {
                setBackgroundColor(a.data);
            }
        } else if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            setBackgroundColor(a.data);
        }
        a = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, a, true);
        context.getTheme().resolveAttribute(android.R.attr.text, a, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (a.isColorType()) {
                setTextColor(a.data);
            }
        } else if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
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

    public int getDialogIconTint() {
        return dialogIconTint;
    }

    public void setDialogIconTint(int dialogIconTint) {
        this.dialogIconTint = dialogIconTint;
    }

    public int getDialogBackgroundDrawableResource() {
        return dialogBackgroundDrawableResource;
    }

    public void setDialogBackgroundDrawableResource(int dialogBackgroundDrawableResource) {
        this.dialogBackgroundDrawableResource = dialogBackgroundDrawableResource;
    }

    public Drawable getDialogBackgroundDrawable() {
        return dialogBackgroundDrawable;
    }

    public void setDialogBackgroundDrawable(Drawable dialogBackgroundDrawable) {
        this.dialogBackgroundDrawable = dialogBackgroundDrawable;
    }

    public int getDialogContentBackgroundColor() {
        return dialogContentBackgroundColor;
    }

    public void setDialogContentBackgroundColor(int dialogContentBackgroundColor) {
        this.dialogContentBackgroundColor = dialogContentBackgroundColor;
        this.dialogContentBackgroundResource = 0;
    }

    public Drawable getDialogContentBackgroundDrawable() {
        return dialogContentBackgroundDrawable;
    }

    public void setDialogContentBackgroundDrawable(Drawable dialogContentBackgroundDrawable) {
        dialogContentBackgroundColor = 0;
        dialogContentBackgroundResource = 0;
        this.dialogContentBackgroundDrawable = dialogContentBackgroundDrawable;
    }

    public int getDialogContentBackgroundResource() {
        return dialogContentBackgroundResource;

    }

    public void setDialogContentBackgroundResource(int dialogContentBackgroundResource) {
        this.dialogContentBackgroundResource = dialogContentBackgroundResource;
        this.dialogContentBackgroundColor = 0;
    }

    public boolean isShowAsBottomSheet() {
        return showAsBottomSheet;
    }

    public void setShowAsBottomSheet(boolean showAsBottomSheet) {
        this.showAsBottomSheet = showAsBottomSheet;
    }

    protected abstract View getContent(ViewGroup parent);

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

    public int getMargins() {
        return this.margins;
    }

    public void setMargins(int margins) {
        this.margins = margins;
    }

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

    @SuppressLint("ClickableViewAccessibility")
    private void createLayout(View content) {
        Resources r = context.getResources();
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getMargins(), r.getDisplayMetrics());
        mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params;
        if (showAsBottomSheet) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.setMargins(margin, margin, margin, margin);
        mainLayout.setWeightSum(2f);
        mainLayout.setLayoutParams(params);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(getBackgroundColor());

        //Create Titlebar
        LinearLayout titleBar = new LinearLayout(context);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tlp.setMargins(margin, margin, margin, margin);
        titleBar.setLayoutParams(tlp);
        titleBar.setGravity(titleBarGravity);
        titleBar.setOrientation(titleBarOrientation);
        //create titlebar icon
        if (dialogIcon != null) {
            ImageView img = new ImageView(context);
            LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ip.setMargins(0, 0, margin, 0);
            img.setLayoutParams(ip);
            img.setImageDrawable(dialogIcon);
            if (dialogIconTint < 0) {
                img.setImageTintList(new ColorStateList(new int[][]{
                        new int[]{android.R.attr.state_enabled}
                }, new int[]{
                        dialogIconTint
                }));
            }
            titleBar.addView(img);
        }

        //create titlebar text
        txtTitle = new TextView(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.weight = 1;
        txtTitle.setLayoutParams(p);
        txtTitle.setText(getTitle());
        txtTitle.setTextColor(getTextColor());
        txtTitle.setTypeface(txtTitle.getTypeface(), Typeface.BOLD);
        txtTitle.setTextSize(20f);
        titleBar.addView(txtTitle);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.setMargins(margin, margin, margin, margin);
        content.setLayoutParams(lParams);
        mainLayout.addView(titleBar);

        LinearLayout buttonPanel = content.findViewById(R.id.button_panel);
        if (showAsBottomSheet) {
            if (showOkButton || showCancelButton) {
                buttonPanel.setVisibility(View.VISIBLE);
                if (showOkButton) {
                    Button okButton = buttonPanel.findViewById(R.id.ok_button);
                    okButton.setText(okButtonText);
                    okButton.setTextColor(getTextColor());
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bsd.dismiss();
                            baseListener.onOkClicked();
                        }
                    });
                }
                if (showCancelButton) {
                    Button cancelButton = buttonPanel.findViewById(R.id.cancel_button);
                    cancelButton.setText(cancelButtonText);
                    cancelButton.setTextColor(getTextColor());
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bsd.cancel();
                            baseListener.onCancelClicked();
                        }
                    });
                }
            }
        }
        setContentBackgroundDrawable(content);
        mainLayout.addView(content);
    }

    private void setContentBackgroundDrawable(View content){
        if (dialogContentBackgroundDrawable != null){
            content.setBackground(dialogContentBackgroundDrawable);
            return;
        }
        if (dialogContentBackgroundColor !=0){
            content.setBackground(new ColorDrawable(dialogContentBackgroundColor));
            return;
        }
        if (dialogContentBackgroundResource != 0){
            content.setBackground(ResourcesCompat.getDrawable(context.getResources(),dialogContentBackgroundResource,context.getTheme()));
            return;
        }
        content.setBackground(null);
    }

    public void showDialog() {
        if (showAsBottomSheet) {
            bsd = new BottomSheetDialog(context);
            bsd.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    bsd.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
                    bsd.getBehavior().setDraggable(false);
                    bsd.getBehavior().addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            bottomSheet.setClipToOutline(true);
                            bottomSheet.setPadding(0, 0, 0, 0);
                            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                                MaterialShapeDrawable newMaterialShapeDrawable = createMaterialShapeDrawable();
                                ViewCompat.setBackground(bottomSheet, newMaterialShapeDrawable);
                            }
                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });
                }
            });
            View content = getContent(mainLayout);
            createLayout(content);
            bsd.setContentView(mainLayout);
            bsd.create();
            bsd.show();
        } else {
            //show as AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCustomTitle(txtTitle);
            createLayout(getContent(mainLayout));
            builder.setView(mainLayout);
            if (showOkButton) {
                builder.setPositiveButton(okButtonText, (_dialog, _which) -> baseListener.onOkClicked());
            }
            if (showCancelButton) {
                builder.setNegativeButton(cancelButtonText, (_dialog, _which) -> baseListener.onCancelClicked());
            }
            AlertDialog dlg = builder.create();
            dlg.show();
            dlg.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xff000000, getBackgroundColor()));
            dlg.getWindow().getDecorView().setBackground(Utils.getDialogBackgroundDrawable(getBackgroundColor()));
            dlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getTextColor());
            dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getTextColor());
        }
    }

    @NotNull
    private MaterialShapeDrawable createMaterialShapeDrawable() {
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context, 0, R.style.CustomShapeAppearanceBottomSheetDialog).build();
        MaterialShapeDrawable newMaterialShapeDrawable = new MaterialShapeDrawable((shapeAppearanceModel));
        newMaterialShapeDrawable.initializeElevationOverlay(context);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}
        };

        int[] colors = new int[]{
                getBackgroundColor()
        };
        newMaterialShapeDrawable.setFillColor(new ColorStateList(states, colors));
        return newMaterialShapeDrawable;
    }

    public interface DialogResponseListener {
        void onOkClicked();

        void onCancelClicked();
    }


}
