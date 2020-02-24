package com.aptitudemaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Users loggedIn;
    public static TextView  usernametextnsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences=this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        loggedIn=new Users(sharedPreferences.getLong("id",0),sharedPreferences.getLong("pin",1234),sharedPreferences.getString("name",""),sharedPreferences.getFloat("score",0));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_quiz, R.id.nav_leaderboard,
                R.id.nav_changepin, R.id.nav_share, R.id.nav_send,R.id.nav_tutorial)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



         TextView userIdtextView=headerView.findViewById(R.id.UserID);
         usernametextnsView= headerView.findViewById(R.id.Name);
         DecimalFormat df = new DecimalFormat("###.##");
         userIdtextView.setText(Long.toString(loggedIn.getId()));
         usernametextnsView.setText(loggedIn.getName()+"\nScore:"+df.format(loggedIn.score));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("log",false).apply();

                sharedPreferences.edit().putString("name","").apply();
                sharedPreferences.edit().putLong("id",0).apply();
                sharedPreferences.edit().putFloat("score",0).apply();
                sharedPreferences.edit().putLong("pin",0).apply();
                Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
