package com.codersofblvkn.criminaltagging.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codersofblvkn.criminaltagging.Fragments.DetectFragment;
import com.codersofblvkn.criminaltagging.Fragments.ManualEntryFragment;
import com.codersofblvkn.criminaltagging.Fragments.MapsFragment;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.FCMTask;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.codersofblvkn.criminaltagging.Utils.ServerKey;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigation;
    // --Commented out by Inspection (01-07-2020 09:41 PM):private static final String TAG = "ALERT";

    public final static String AUTH_KEY_FCM = new ServerKey().getSERVER_KEY();
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    // --Commented out by Inspection (01-07-2020 09:41 PM):private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE =1002;
    // --// --Comm// --Commented out by Inspection (01-07-2020 09:41 PM):ented out by Inspection (01-07-2020 09:41 PM):Commented out by Inspection (01// --Commented out by Inspection (01-07-2020 09:41 PM):-07-2020 09:41 PM):final private String contentType = "application/json";
    Toolbar toolbar;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    private FirebaseAuth mAuth;

    String language;
    private  Locale locale;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        language=sp.getString("language","en");
        locale = new Locale(language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        alert = findViewById(R.id.alert);
//        alert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new FCMTask().execute();
//            }
//        });

//        bottomNavigation = findViewById(R.id.bottom_navigation);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.navigation_detect:
//                                openFragment(DetectFragment.newInstance());
//                                return true;
//                            case R.id.navigation_me:
//                                openFragment(ManualEntryFragment.newInstance("", ""));
//                                return true;
//                            case R.id.navigation_about:
//                                openFragment(MapsFragment.newInstance());
//                                return true;
//                        }
//                        return false;
//                    }
//                };

//        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(DetectFragment.newInstance());
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.taskbar_menu, menu);
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==R.id.logout)
//        {
//            mAuth.signOut();
//            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }
//        else if(item.getItemId()==R.id.criminal_alert)
//        {
//            LayoutInflater li = LayoutInflater.from(getApplicationContext());
//            View promptsView = li.inflate(R.layout.custom_dialog, null);
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    MainActivity.this);
//
//            // set alert_dialog.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//
//            final EditText userInput = promptsView.findViewById(R.id.dialoged);
//
//            // set dialog message
//            alertDialogBuilder
//                    .setCancelable(false)
//                    .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // get user input and set it to result
//                            // edit text
//                            String cid=userInput.getText().toString();
//                            if(!cid.equals(""))
//                            {
//                                if(InternetConnection.checkConnection(getApplicationContext()))
//                                {
//                                    new FCMTask(getApplicationContext()).execute(getString(R.string.criminalnotification)+ cid);
//                                }
//                                else
//                                {
//                                    Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
//                                }
//                            }
////                            Toast.makeText(getApplicationContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .setNegativeButton(getString(R.string.cancel),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//
//            // create alert dialog
//            AlertDialog alertDialog = alertDialogBuilder.create();
//
//            // show it
//            alertDialog.show();
//        }
//        else if(item.getItemId()==R.id.settings)
//        {
//            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(intent);
//        }
//        else if(item.getItemId()==R.id.ic_track_locate)
//        {
//            SharedPreferences sharedPref = getSharedPreferences("alertcred",MODE_PRIVATE);
//            String defaultValue = "-1";
//            String cidData = sharedPref.getString(getString(R.string.preference_cid), defaultValue);
//            if(cidData==defaultValue)
//            {
//                Toast.makeText(getApplicationContext(),"No alerts currently",Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Intent intent=new Intent(MainActivity.this, NotificationActivity.class);
//                intent.putExtra("cid",cidData);
//                startActivity(intent);
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent=null;
        switch (item.getItemId())
        {

            case R.id.logout:
                mAuth.signOut();
                intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.criminal_alert:
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.custom_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView.findViewById(R.id.dialoged);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String cid=userInput.getText().toString();
                                if(!cid.equals(""))
                                {
                                    if(InternetConnection.checkConnection(getApplicationContext()))
                                    {
                                        new FCMTask(getApplicationContext()).execute(getString(R.string.criminalnotification)+ cid);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                                    }
                                }
//                            Toast.makeText(getApplicationContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                break;
            case R.id.ic_track_locate:
                SharedPreferences sharedPref = getSharedPreferences("alertcred",MODE_PRIVATE);
                String defaultValue = "-1";
                String cidData = sharedPref.getString(getString(R.string.preference_cid), defaultValue);
                if(cidData==defaultValue)
                {
                    Toast.makeText(getApplicationContext(),"No alerts currently",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent = new Intent(MainActivity.this, NotificationActivity.class);
                    intent.putExtra("cid",cidData);
                    startActivity(intent);
                }
                break;
            case R.id.settings:
                intent=new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_detect:
                openFragment(DetectFragment.newInstance());
                break;
            case R.id.navigation_me:
                openFragment(ManualEntryFragment.newInstance("", ""));
                break;
            case R.id.navigation_about:
                openFragment(MapsFragment.newInstance());
                break;
            case R.id.navigation_violence:
                intent = new Intent(MainActivity.this, ViolenceDashboardActivity.class);
                startActivity(intent);
                break;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        // refresh your views here
        Locale.setDefault(locale);
        config.locale = locale;
        super.onConfigurationChanged(newConfig);

    }
}