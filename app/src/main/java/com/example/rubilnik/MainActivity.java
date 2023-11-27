package com.example.rubilnik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SetUserNameDialog.activityContains_getStringFromDialog {

    TextView usernameTextView;

    Button usernameButton;
    ImageButton editUsernameImageButton;

    LinearLayout settingsLinLay;
    LinearLayout settingsGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        settingsLinLay = findViewById(R.id.settings_lin_lay);

        ArrayList<View> settingsModules = new ArrayList<>();

        ArrayList<View> settingViews1 = new ArrayList<>();
        settingViews1.add(new Switch(this));
        settingViews1.add(new Switch(this));
        settingViews1.add(new Switch(this));
        settingViews1.add(new Switch(this));
        settingViews1.add(new Switch(this));
        ArrayList<View> settingViews2 = new ArrayList<>();
        settingViews2.add(new Switch(this));
        ArrayList<View> settingViews3 = new ArrayList<>();
        settingViews3.add(new Switch(this));
        settingViews3.add(new Switch(this));
        CheckBox checkBox = new CheckBox(this);
        checkBox.setPadding(MyTools.dpToPx(this,40),0,0,0);
        settingViews3.add(checkBox);

        settingsGroup = MyTools.customListFormat(settingViews1,this,true, MyTools.dpToPx(this, 2),true);
        settingsModules.add(settingsGroup);
        settingsGroup = MyTools.customListFormat(settingViews2,this,true, MyTools.dpToPx(this, 2),true);
        settingsModules.add(settingsGroup);
        settingsGroup = MyTools.customListFormat(settingViews3,this,true,MyTools.dpToPx(this, 2),true);
        settingsModules.add(settingsGroup);

        settingsGroup = MyTools.customListFormat(settingsModules,this,false,MyTools.dpToPx(this, 10),false);

        settingsLinLay.addView(settingsGroup);






        usernameButton = findViewById(R.id.usernameButton);

        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetUserNameDialog setUserNameDialog = new SetUserNameDialog();
                setUserNameDialog.show(getSupportFragmentManager(),"Rename");
            }
        });
    }

    @Override
    public void getStringFromDialog(String value) {
        usernameButton.setText(value);
    }
}