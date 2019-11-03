package ug.co.globalautosystems.speakapp_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import ug.co.globalautosystems.speakapp_admin.adapters.CustomAdapter;

public class ProfileActivity extends AppCompatActivity {

   //Arrays of String
    ListView simplest;
    String countryList[] = {"Amos: 07703904647","Moris:0782303329","Rogers:0756144791","Timothy:0770456782","Victor"};

    int flags[]={R.drawable.timothy,R.drawable.timothy,R.drawable.timothy,R.drawable.timothy,R.drawable.timothy};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        simplest = findViewById(R.id.simpleListView);
        ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity__list_view,R.id.second_List,countryList);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),countryList,flags);
        simplest.setAdapter(arrayAdapter);

    }
}
