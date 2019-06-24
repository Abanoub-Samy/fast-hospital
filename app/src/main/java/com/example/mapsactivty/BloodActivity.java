package com.example.mapsactivty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class BloodActivity extends AppCompatActivity {

    Spinner spinner;
    Button search;
    private MenuItem item;

    private String TAG = BloodActivity.class.getSimpleName();


    ArrayList<String> id_hos = new ArrayList<>();
    ArrayList<String> name_hos = new ArrayList<>();
    ArrayList<String> lat_hos = new ArrayList<>();
    ArrayList<String> lon_hos = new ArrayList<>();
    ArrayList<String> phone_hos = new ArrayList<>();
    ArrayList<String> blood = new ArrayList<>();


    String text;

    private GoogleMap map;
    Location current = new Location("current");
    Location used = new Location("used");
    Location real_used = new Location("real_used");
    double result000 = 1000000000 ;


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        spinner = findViewById(R.id.spinner);
        search = findViewById(R.id.search);

        id_hos = getIntent().getStringArrayListExtra("id");
        name_hos = getIntent().getStringArrayListExtra("name");
        phone_hos = getIntent().getStringArrayListExtra("phone");
        lat_hos = getIntent().getStringArrayListExtra("lat");
        lon_hos = getIntent().getStringArrayListExtra("lon");
        blood = getIntent().getStringArrayListExtra("blood");

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        getLocationPermission();


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.item = item;
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            current.setLatitude(currentLocation.getLatitude());
                            current.setLongitude(currentLocation.getLongitude());

                            Log.d(TAG, "lat : " + currentLocation.getLatitude() + "lon : " + currentLocation.getLongitude());

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            //Toast.makeText(NearestHospitalActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                getDeviceLocation();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    public void go(View view) {
        text = spinner.getSelectedItem().toString();

        for (int x = 0; x < blood.size(); x++) {

            if (blood.get(x).contains(text)) {
                //array.add(x);
                Log.e(TAG,"name is " +name_hos.get(x));
                Log.e(TAG, "id is " +id_hos.get(x));
                Log.e(TAG, "weight is " +blood.get(x));
//                used.setLatitude(Double.parseDouble(lat_hos.get(x)));
//                used.setLongitude(Double.parseDouble(lon_hos.get(x)));
//               double result=current.distanceTo(used);
//                if(result<result000){
//                    real_used.setLatitude(Double.parseDouble(lat_hos.get(x)));
//                    real_used.setLongitude(Double.parseDouble(lon_hos.get(x)));
//                    result000=result;
//                }


            }
        }
        String c1 = String.valueOf(current.getLatitude());
        String c2 = String.valueOf(current.getLongitude());
        String r1 = "30.471370";
        String r2 = "30.930548";
        Intent intent = new Intent(getBaseContext(),MapsActivity.class);
        intent.putExtra("current_lat",c1);
        intent.putExtra("current_lon",c2);
        intent.putExtra("real_lat",r1/*real_used.getLatitude()*/);
        intent.putExtra("real_lon",r2/*real_used.getLongitude()*/);
        startActivity(intent);
    }
}
