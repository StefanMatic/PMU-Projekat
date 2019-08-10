package com.example.lenovo.pmuprojekat.Main.View;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.R;

public class SettingsFieldSelection extends AppCompatActivity {
    private ImageView fieldView;
    private ImageButton fieldLeft, fieldRight;
    private Button fieldSelectionButton;

    private int fieldCounter;

    private Bitmap[] arrows;
    private BitmapDrawable[] arrowsDrawable;

    private Bitmap field;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_settings_field_selection);

        fieldCounter = 0;

        fieldView = findViewById(R.id.FieldPickerView);
        fieldLeft = findViewById(R.id.FieldLeft);
        fieldRight = findViewById(R.id.FieldRight);

        arrows = AppConstants.getBitmapBank().getArrows();
        arrowsDrawable = new BitmapDrawable[2];
        arrowsDrawable[0] = new BitmapDrawable(this.getResources(), arrows[0]);
        arrowsDrawable[1] = new BitmapDrawable(this.getResources(), arrows[1]);

        fieldLeft.setBackground(arrowsDrawable[0]);
        fieldRight.setBackground(arrowsDrawable[1]);
        getAndSetField();

        fieldSelectionButton = findViewById(R.id.fieldSelection);

        Bitmap buttonBackgroundImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_background);
        buttonBackgroundImage = AppConstants.getBitmapBank().resizePicture(buttonBackgroundImage,
                (int)(AppConstants.SCREEN_WIDTH*0.3),
                (int) (AppConstants.SCREEN_HEIGHT*0.10));
        Drawable background = new BitmapDrawable(this.getResources(), buttonBackgroundImage);
        fieldSelectionButton.setBackground(background);
    }

    public void changeFieldLeft(View view) {
        if (fieldCounter == 0) {
            fieldCounter = AppConstants.getBitmapBank().getNumberOfFields() - 1;
        } else
            fieldCounter--;

        getAndSetField();
    }

    public void changeFieldRight(View view) {
        if (fieldCounter == AppConstants.getBitmapBank().getNumberOfFields() - 1)
            fieldCounter = 0;
        else
            fieldCounter++;

        getAndSetField();
    }

    private void getAndSetField() {
        field = AppConstants.getBitmapBank().getField(fieldCounter);
        field = AppConstants.getBitmapBank().resizePicture(field,
                AppConstants.SCREEN_WIDTH / 10 * 6,
                AppConstants.SCREEN_HEIGHT / 5 * 3);

        fieldView.setImageBitmap(field);
    }

    public void selectField(View view) {
        //postavljamo izabrani field u AppConstants i vracamo se na stranicu koja ju je pozvala
        AppConstants.getBitmapBank().setFiled(field);
        finish();
    }
}
