package ug.co.globalautosystems.speakapp_admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    private static final int MY_CONST = 200;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    String selectedTab = "cases";
    Fragment fragment;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        FirebaseApp.initializeApp(context);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_CONST);

        } if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_CONST);

        }
        actionBar = getSupportActionBar();
        actionBar.setTitle("Reported Cases");
        fragment = new CasesFragment();
        loadFragment(fragment);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.store:
                        actionBar.setTitle("Reported Cases");
                        fragment = new CasesFragment();
                        selectedTab = "cases";
                        loadFragment(fragment);
                        return true;
                    case R.id.orders:
                        actionBar.setTitle("Policies");
                        fragment = new PoliciesFragment();
                        selectedTab = "policies";
                        loadFragment(fragment);
                        return true;
                    case R.id.categories:
                        actionBar.setTitle("App Statistics");
                        fragment = new StatsFragment();
                        selectedTab = "stats";
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framecontainer, fragment);

//        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_profile){

            Intent profileIntent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(profileIntent);
        }
        if(id == R.id.help){
            Intent helpIntent = new Intent(MainActivity.this,HelpActivity.class);
            startActivity(helpIntent);
        }
        if(id == R.id.settings){
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
