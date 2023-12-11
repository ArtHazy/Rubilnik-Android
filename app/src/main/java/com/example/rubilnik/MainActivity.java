package com.example.rubilnik;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rubilnik.screens.first.FirstFragment;
import com.example.rubilnik.screens.second.SecondFragment;
import com.example.rubilnik.screens.third.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;


public class MainActivity extends AppCompatActivity {

    TextView usernameTextView;

    Button usernameButton;
    Button connectButton;
    ImageButton editUsernameImageButton;

    LinearLayout settingsLinLay;
    LinearLayout settingsGroup;
    Context context;

    EditText editText;

    NavController navController;
    BottomNavigationView bottomNavigationView;

//    private ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        navController = Navigation.findNavController(this, R.id.nav_controller_view_tag);



//        NavigationView navigationView = findViewById(R.id.na);
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Fragment()).commit();
//            navigationView.setCheckedItem(R.id.item1);
//        }

//        //NAVIGATION
        bottomNavigationView = findViewById(R.id.menuBottom);
        replaceFragment(new FirstFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.item1)
                replaceFragment(new FirstFragment());
            else if (id == R.id.item2)
                replaceFragment(new SecondFragment());
            else if (id == R.id.item3)
                replaceFragment(new ThirdFragment());
            return true;
        });

//        NavController navController = NavHostFragment.findNavController(this);
//        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.list_fragment);
//
//        viewModel = new ViewModelProvider(backStackEntry).get(ListViewModel.class);
//        viewModel.getFilteredList().observe(getViewLifecycleOwner(), list -> {
//            // Update the list UI.
//        }
    }


//        //CODE
//        context = this;
//        Fragment mainFragment = getFragmentManager();
//        usernameButton = (Button) getFragmentManager().findViewById(R.id.usernameButton);
//        connectButton = findViewById(R.id.connectRoomButton);
//
//        usernameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SetUserNameDialog setUserNameDialog = new SetUserNameDialog();
//                setUserNameDialog.show(getSupportFragmentManager(),"Rename");
//            }
//        });
//
//
//        connectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Http http = new Http(context);
//                http.execute();
//            }
//        });
//
//        editText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
//            @Override
//            public void afterTextChanged(Editable et) {
//                String s=et.toString();
//                if(!s.equals(s.toUpperCase()))
//                {
//                    s=s.toUpperCase();
//                    editText.setText(s);
//                    editText.setSelection(editText.length()); //fix reverse texting
//                }
//            }
//        });

//    }

//    @Override
//    public void getStringFromDialog(String value) {
//        usernameButton.setText(value);
//    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


//        setContentView(R.layout.profile);



//        settingsLinLay = findViewById(R.id.settings_lin_lay);

//        ArrayList<View> settingsModules = new ArrayList<>();

//        ArrayList<View> settingViews1 = new ArrayList<>();
//        settingViews1.add(new Switch(this));
//        settingViews1.add(new Switch(this));
//        settingViews1.add(new Switch(this));
//        settingViews1.add(new Switch(this));
//        settingViews1.add(new Switch(this));
//        ArrayList<View> settingViews2 = new ArrayList<>();
//        settingViews2.add(new Switch(this));
//        ArrayList<View> settingViews3 = new ArrayList<>();
//        settingViews3.add(new Switch(this));
//        settingViews3.add(new Switch(this));
//        CheckBox checkBox = new CheckBox(this);
//        checkBox.setPadding(MyTools.dpToPx(this,40),0,0,0);
//        settingViews3.add(checkBox);

//        settingsGroup = MyTools.customListFormat(settingViews1,this,true, MyTools.dpToPx(this, 2),true);
//        settingsModules.add(settingsGroup);
//        settingsGroup = MyTools.customListFormat(settingViews2,this,true, MyTools.dpToPx(this, 2),true);
//        settingsModules.add(settingsGroup);
//        settingsGroup = MyTools.customListFormat(settingViews3,this,true,MyTools.dpToPx(this, 2),true);
//        settingsModules.add(settingsGroup);
//
//        settingsGroup = MyTools.customListFormat(settingsModules,this,false,MyTools.dpToPx(this, 10),false);
//
//        settingsLinLay.addView(settingsGroup);

}