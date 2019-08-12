package com.example.lenovo.pmuprojekat.Main.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.R;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private NumberPicker speedPicker, timePicker, goalPicker;
    private Switch timeSwitch, goalSwitch;
    private Button fieldSelect, saveChanges, resetSettings;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_settings);

        //dohvatanje svih elemenata i postavljanje pocetnog stanja
        speedPicker = findViewById(R.id.speedPicker);
        speedPicker.setMaxValue(3);
        speedPicker.setMinValue(1);
        speedPicker.setValue(AppConstants.CURRENT_SPEED);

        goalPicker = findViewById(R.id.matchGoalPicker);
        goalPicker.setMinValue(AppConstants.MIN_GAME_GOALS);
        goalPicker.setMaxValue(AppConstants.MAX_GAME_GOALS);
        goalPicker.setEnabled(AppConstants.isPlayOnGoals());
        goalPicker.setValue(AppConstants.CURRENT_GOALS);

        timePicker = findViewById(R.id.matchTimePicker);
        timePicker.setMinValue(AppConstants.MIN_GAME_TIME);
        timePicker.setMaxValue(AppConstants.MAX_GAME_TIME);
        timePicker.setEnabled(!AppConstants.isPlayOnGoals());
        timePicker.setValue(AppConstants.CURRENT_TIME);

        timeSwitch = findViewById(R.id.timeSwitch);
        goalSwitch = findViewById(R.id.goalSwitch);
        timeSwitch.setChecked(!AppConstants.isPlayOnGoals());
        goalSwitch.setChecked(AppConstants.isPlayOnGoals());

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                timeSwitch.setChecked(isChecked);
                timePicker.setEnabled(isChecked);
                goalSwitch.setChecked(!isChecked);
                goalPicker.setEnabled(!isChecked);
            }
        });

        goalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                goalSwitch.setChecked(isChecked);
                goalPicker.setEnabled(isChecked);
                timeSwitch.setChecked(!isChecked);
                timePicker.setEnabled(!isChecked);
            }
        });

        fieldSelect = findViewById(R.id.fieldSelectButton);
        saveChanges = findViewById(R.id.saveChangesButton);
        resetSettings = findViewById(R.id.resetSettingsButton);

        Bitmap buttonBackgroundImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_background);
        buttonBackgroundImage = AppConstants.getBitmapBank().resizePicture(buttonBackgroundImage,
                (int) (AppConstants.SCREEN_WIDTH * 0.3),
                (int) (AppConstants.SCREEN_HEIGHT * 0.05));
        Drawable background = new BitmapDrawable(this.getResources(), buttonBackgroundImage);

        fieldSelect.setBackground(background);
        saveChanges.setBackground(background);
        resetSettings.setBackground(background);

        Field selectorWheelPaintSpeed = null;
        Field selectorWheelPaintTime = null;
        Field selectorWheelPaintGoals = null;
        try {
            selectorWheelPaintSpeed = speedPicker.getClass()
                    .getDeclaredField("mSelectorWheelPaint");
            selectorWheelPaintTime = timePicker.getClass()
                    .getDeclaredField("mSelectorWheelPaint");
            selectorWheelPaintGoals = goalPicker.getClass()
                    .getDeclaredField("mSelectorWheelPaint");

            selectorWheelPaintSpeed.setAccessible(true);
            selectorWheelPaintTime.setAccessible(true);
            selectorWheelPaintGoals.setAccessible(true);

            ((Paint) selectorWheelPaintSpeed.get(speedPicker)).setColor(Color.WHITE);
            ((Paint) selectorWheelPaintTime.get(timePicker)).setColor(Color.WHITE);
            ((Paint) selectorWheelPaintGoals.get(goalPicker)).setColor(Color.WHITE);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public void resetAllSettings(View view) {
        AppConstants.setCurrentGoals(AppConstants.DEFAULT_GOALS);
        AppConstants.setCurrentSpeed(AppConstants.DEFAULT_SPEED);
        AppConstants.setCurrentTime(AppConstants.DEFAULT_TIME);
        AppConstants.getBitmapBank().setFiled(AppConstants.getBitmapBank().getField(AppConstants.DEFAULT_FIELDS_INDEX));

        speedPicker.setValue(AppConstants.DEFAULT_SPEED);

        goalSwitch.setChecked(true);
        goalPicker.setEnabled(true);
        goalPicker.setValue(AppConstants.DEFAULT_GOALS);

        timeSwitch.setChecked(false);
        timePicker.setEnabled(false);
        timePicker.setValue(AppConstants.DEFAULT_TIME);

    }

    public void selectField(View view) {
        Intent intent = new Intent(this, SettingsFieldSelection.class);
        startActivity(intent);
    }

    public void saveAllChanges(View view) {
        //postavljanje nove przine
        AppConstants.setCurrentSpeed(speedPicker.getValue());

        //postvaljanje uslove igre u zavisnosti od odabranih opcija
        if (goalSwitch.isChecked()) {
            AppConstants.setPlayOnGoals(true);
            AppConstants.setCurrentGoals(goalPicker.getValue());
        } else {
            AppConstants.setPlayOnGoals(false);
            AppConstants.setCurrentTime(timePicker.getValue());
        }

        //nakon postavljanja novih vrednosti, napustamo activity
        finish();
    }
}
