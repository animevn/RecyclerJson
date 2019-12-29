package com.haanhgs.mynote;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.tvSettingSound)
    TextView tvSettingSound;
    @BindView(R.id.cbSettingSound)
    CheckBox cbSettingSound;
    @BindView(R.id.tvSettingAnimation)
    TextView tvSettingAnimation;
    @BindView(R.id.rbnSettingFast)
    RadioButton rbnSettingFast;
    @BindView(R.id.rbnSettingNormal)
    RadioButton rbnSettingNormal;
    @BindView(R.id.rbnSettingSlow)
    RadioButton rbnSettingSlow;
    @BindView(R.id.rgSettingAnimation)
    RadioGroup rgSettingAnimation;

    public static final String SETTINGS = "settings";
    private static final String ANIM = "anim";
    private static final String SOUND = "sound";

    private static final int FAST = 1;
    private static final int NORMAL = 2;
    private static final int SLOW = 3;


    private SharedPreferences.Editor editor;
    private boolean sound = true;
    private int anim = 1;


    @SuppressLint("CommitPrefEdits")
    private void initSaveSettingsSate(){
        SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        editor = prefs.edit();
        sound = prefs.getBoolean(SOUND, sound);
        anim = prefs.getInt(ANIM, anim);
    }

    private void initRadioButtons(){
        switch (anim){
            case FAST:
                rgSettingAnimation.check(R.id.rbnSettingFast);
                break;
            case NORMAL:
                rgSettingAnimation.check(R.id.rbnSettingNormal);
                break;
            case SLOW:
                rgSettingAnimation.check(R.id.rbnSettingSlow);
                break;
        }
    }

    private void handleCheckBoxClick(){
        cbSettingSound.setChecked(sound);
        cbSettingSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sound = !sound;
            editor.putBoolean(SOUND, sound);
        });
    }

    private void handleRadioGroupClick(){
        rgSettingAnimation.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rbnSettingFast:
                    anim = FAST;
                    break;
                case R.id.rbnSettingNormal:
                    anim = NORMAL;
                    break;
                case R.id.rbnSettingSlow:
                    anim = SLOW;
                    break;
            }
            editor.putInt(ANIM, anim);
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initSaveSettingsSate();
        initRadioButtons();
        handleCheckBoxClick();
        handleRadioGroupClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.apply();
    }
}
