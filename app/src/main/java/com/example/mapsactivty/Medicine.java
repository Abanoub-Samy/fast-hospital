package com.example.mapsactivty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapsactivty.internet.HttpHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Medicine extends AppCompatActivity {

    Button search;
    EditText editText;
    private MenuItem item;
    private String TAG = Medicine.class.getSimpleName();
    ArrayList<HashMap<String, String>> pokemonList;

    ArrayList<String> id_hos = new ArrayList<>();
    ArrayList<String> name_hos = new ArrayList<>();
    ArrayList<String> lat_hos = new ArrayList<>();
    ArrayList<String> lon_hos = new ArrayList<>();
    ArrayList<String> phone_hos = new ArrayList<>();
    ArrayList<String> medicine = new ArrayList<>();
    ArrayList<String> blood = new ArrayList<>();
    ArrayList<String> room = new ArrayList<>();
    ArrayList<Integer> array = new ArrayList<>();

    String text;

    private GoogleMap map;


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        search = findViewById(R.id.search);
        editText = findViewById(R.id.editText1);
        new Getsearch().execute();


//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                text = editText.getText().toString();
//
//
//                //array(name_hos);
//
//
//                    for(int x=0 ; x < medicine.size();x++){
//                        //Log.e(TAG, "done "+medicine.get(x));
//                        if(medicine.get(x).contains(text)){
//                           array.add(x);
//                           Log.e(TAG, name_hos.get(x));
//                        }
//
//                        }
//
//                    for(int w=0;w<array.size();w++){
//                        name_hos.get(array.get(w));
//                        //Log.e(TAG, name_hos.get(w));
//                        //Toast.makeText(Medicine.this, "is : "+ name_hos.get(w) , Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void go(View view) {

        text = editText.getText().toString();


        for (int x = 0; x < medicine.size(); x++) {
            //Log.e(TAG, "done "+medicine.get(x));
            if (medicine.get(x).contains(text)) {
                array.add(x);
                Log.e(TAG, name_hos.get(x));
                Log.e(TAG, id_hos.get(x));
                Log.e(TAG, medicine.get(x));
            }

        }

        for (int w = 0; w < array.size(); w++) {
            name_hos.get(array.get(w));
            //Log.e(TAG, name_hos.get(w));
            //Toast.makeText(Medicine.this, "is : "+ name_hos.get(w) , Toast.LENGTH_SHORT).show();
        }
        getLocationPermission();


    }


    private class Getsearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // TODO: make a request to the URL
            String url = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
            String jsonString = "";
            try {
                jsonString = sh.makeHttpRequest(createUrl(url));
            } catch (IOException e) {
                return null;
            }

            if (jsonString != null) {
                try {

                    //TODO: Create a new JSONObject
                    JSONObject jsonObj = new JSONObject(jsonString);

                    // TODO: Get the JSON Array node
                    JSONArray pokemons = jsonObj.getJSONArray("pokemon");

                    // looping through all Contacts
                    int z = 0;
                    for (int i = 0; i < pokemons.length(); i++) {
                        //TODO: get the JSONObject
                        JSONObject c = pokemons.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
//                        String lat = c.getString("");
//                        String lon = c.getString("");
//                        String phone = c.getString("");
                        String med = c.getString("weight");

                        name_hos.add(z, name);
//                            lat_hos.add(z,lat);
//                            lon_hos.add(z,lon);
//                            phone_hos.add(z,phone);
                        id_hos.add(z, id);
                        medicine.add(z, med);
                        z++;
                        //Log.e(TAG, "Json parsing error: "+medicine.get(i));


//                             HashMap<String, String> pokemon = new HashMap<>();
                        // add each child node to HashMap key => value
//                            pokemon.put("name", name);
//
                        // adding a pokemon to our pokemon list
//                            pokemonList.add(pokemon);


                        // tmp hash map for a single pokemon

                    }
                } catch (final JSONException e) {

                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Medicine.this, "", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }


            return null;
        }

        private URL createUrl(String stringUrl) {
            URL url;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }


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

}


