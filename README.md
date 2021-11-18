# Custom Dialogs
## _Custom dialogs at your fingertips_

[![Build Status](readme_images/build.svg)](https://github.com/TheRandomCrafter83/CustomDialogs) 

(Minimum API supported is SDK 28)

Custom Dialogs is an Android Library containing several useful custom dialogs.
- Currently there is only one dialog type, the ColorDialog.
- More to come later.

## Features

- Set the dialog's background color
- Title
- Text color
- Margins
- (To be added) An icon for the dialog

## Tech

Custom Dialogs is an open source project which can be found [Here](https://github.com/TheRandomCrafter83/CustomDialogs)
on GitHub.

## Installation
**settings.gradle** - your settings.gradle file should look similar to the following.

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven { url 'https://jitpack.io' }
    }
}
rootProject.name = "My Application"
include ':app'
```

**build.gradle(App Module)** - then you can add the following in your module level's dependencies section.

```gradle
implementation 'com.github.TheRandomCrafter83:CustomDialogs:1.0' //<--add this implementation to the dependencies section
```

## Example Usage

```java
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coderz.f1.customdialogs.colordialog.ColorDialog;

public class MainActivity extends AppCompatActivity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  Button button = findViewById(R.id.button);
  button.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    ColorDialog cd = new ColorDialog(MainActivity.this, new ColorDialog.DialogResponseListener() {
     @Override
     public void onOkClicked(int color) {
      button.setText(Integer.toString(color));
     }

     @Override
     public void onCancelClicked() {

     }
    });
    cd.setTitle("Choose a color");
    cd.setTabIndex(ColorDialog.TabIndex.PALETTE);
    cd.setInitialColor(Color.RED);
    cd.setMargins(8);
    cd.setBackgroundColor(Color.GRAY);
    cd.setTextColor(Color.YELLOW);
    cd.showDialog();
   }
  });
 }
}
```

## Screenshots
**ColorDialog**
<table>
  <tr>
    <td>Hue Palette</td>
     <td>RGB Sliders</td>
  </tr>
  <tr>
    <td valign="top"><img src="readme_images/colordialog_screenshot1.png"></td>
    <td valign="top"><img src="readme_images/colordialog_screenshot2.png"></td>
  </tr>
 </table>

## License
MIT
