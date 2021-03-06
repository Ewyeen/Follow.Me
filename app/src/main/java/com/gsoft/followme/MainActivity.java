package com.gsoft.followme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textloc;
    private LocationManager locationManager;
    private LocationListener listener;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.buttongetloc);
        textloc = (TextView) findViewById(R.id.showloc);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aprimaps = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(aprimaps);

            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textloc.setText(location.getLatitude() + " - " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent locsource = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(locsource);
            }
        };
        configureButton();
    }

        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 9:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
        }
    }

    private void configureButton() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,9);
            }
            return;
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                locationManager.requestLocationUpdates("gps", 0, 5, listener);
            }
        });
    }




}
