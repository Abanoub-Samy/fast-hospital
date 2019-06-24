package com.example.mapsactivty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mapsactivty.internet.HttpHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NearestHospitalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap map = null;
    private static final String TAG = "NearestHospitalActivity";
    Intent myIntent ;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    ArrayList<String> id_hos = new ArrayList<>();
    ArrayList<String> name_hos = new ArrayList<>();
    ArrayList<String> lat_hos = new ArrayList<>();
    ArrayList<String> lon_hos = new ArrayList<>();
    ArrayList<String> phone_hos = new ArrayList<>();
    ArrayList<String> medicine = new ArrayList<>();
    ArrayList<String> blood = new ArrayList<>();
    ArrayList<String> room = new ArrayList<>();


    //vars
    private Boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearesthospital);




        getLocationPermission();
        new Getsearch().execute();




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent myIntent = new Intent(getBaseContext(),   Maps.class);
//                startActivity(myIntent);
                Log.d("dooo",name_hos.get(2));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.room) {
             myIntent = new Intent(getBaseContext(), Room.class);
            myIntent.putStringArrayListExtra("name",name_hos);
            myIntent.putStringArrayListExtra("id",id_hos);
            myIntent.putStringArrayListExtra("phone",phone_hos);
            myIntent.putStringArrayListExtra("lat",lat_hos);
            myIntent.putStringArrayListExtra("lon",lon_hos);
            myIntent.putStringArrayListExtra("room",room);
            startActivity(myIntent);
        } else if (id == R.id.medicine) {
             myIntent = new Intent(getBaseContext(), Medicine.class);
            myIntent.putStringArrayListExtra("name",name_hos);
            myIntent.putStringArrayListExtra("id",id_hos);
            myIntent.putStringArrayListExtra("phone",phone_hos);
            myIntent.putStringArrayListExtra("lat",lat_hos);
            myIntent.putStringArrayListExtra("lon",lon_hos);
            myIntent.putStringArrayListExtra("med",medicine);
            startActivity(myIntent);
        } else if (id == R.id.Blood) {
             myIntent = new Intent(getBaseContext(), BloodActivity.class);
            myIntent.putStringArrayListExtra("name",name_hos);
            myIntent.putStringArrayListExtra("id",id_hos);
            myIntent.putStringArrayListExtra("phone",phone_hos);
            myIntent.putStringArrayListExtra("lat",lat_hos);
            myIntent.putStringArrayListExtra("lon",lon_hos);
            myIntent.putStringArrayListExtra("blood",blood);
            startActivity(myIntent);
        } else if (id == R.id.nav_share) {
             myIntent = new Intent(getBaseContext(), MapsActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        map = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LatLng hos1 = new LatLng(30.577106,31.013911);
            MarkerOptions hos11 = new MarkerOptions();
            hos11.position(hos1);
            hos11.title("معهد الكبد القومي");
            hos11.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos2 = new LatLng(30.576903,31.014392);
            MarkerOptions hos22 = new MarkerOptions();
            hos22.position(hos2);
            hos22.title("European Hospital");
            hos22.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos3 = new LatLng(30.576548,31.012085);
            MarkerOptions hos33 = new MarkerOptions();
            hos33.position(hos3);
            hos33.title(" مستشفى شبين الكوم الجامعي");
            hos33.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos4 = new LatLng(30.577193,31.012755);
            MarkerOptions hos44 = new MarkerOptions();
            hos44.position(hos4);
            hos44.title("المستشفى الجامعي التخصصي بشبين الكوم ");
            hos44.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos5 = new LatLng(30.574637,31.012004);
            MarkerOptions hos55 = new MarkerOptions();
            hos55.position(hos5);
            hos55.title("مستشفى شبين الكوم التعليمي ");
            hos55.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos6 = new LatLng(30.559011,31.012239);
            MarkerOptions hos66 = new MarkerOptions();
            hos66.position(hos6);
            hos66.title("مستشفى الرمد ");
            hos66.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos7 = new LatLng(30.564018,31.012307);
            MarkerOptions hos77 = new MarkerOptions();
            hos77.position(hos7);
            hos77.title("مستشفى الرمد، جمال عبد الناصر");
            hos77.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos8 = new LatLng(30.560251,31.014352);
            MarkerOptions hos88 = new MarkerOptions();
            hos88.position(hos8);
            hos88.title("مستشفى المواساة الاسلامى التخصصى ");
            hos88.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hos9 = new LatLng(30.544408,31.048101);
            MarkerOptions hos99 = new MarkerOptions();
            hos99.position(hos9);
            hos99.title("مستشفي الصدر ميت خلف");
            hos99.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));


            LatLng hoss1 = new LatLng(30.544631,31.048172);
            MarkerOptions hoss11 = new MarkerOptions();
            hoss11.position(hoss1);
            hoss11.title("مستشفى الصحه النفسيه وعلاج الإدمان بشبين الكوم ");
            hoss11.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss2 = new LatLng(30.472071,30.927375);
            MarkerOptions hoss22 = new MarkerOptions();
            hoss22.position(hoss2);
            hoss22.title("مستشفى منوف العام و ملحقاتها ");
            hoss22.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss3 = new LatLng(30.471305,30.926797);
            MarkerOptions hoss33 = new MarkerOptions();
            hoss33.position(hoss3);
            hoss33.title("مستشفى الصدر");
            hoss33.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss4 = new LatLng(30.471009,30.925668);
            MarkerOptions hoss44 = new MarkerOptions();
            hoss44.position(hoss4);
            hoss44.title("مستشفى حُميات منوف ");
            hoss44.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss5 = new LatLng(30.471370,30.930548);
            MarkerOptions hoss55 = new MarkerOptions();
            hoss55.position(hoss5);
            hoss55.title("مستشفى البسمة التخصصى");
            hoss55.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss6 = new LatLng(30.442480,30.960951);
            MarkerOptions hoss66 = new MarkerOptions();
            hoss66.position(hoss6);
            hoss66.title("مستشفى سرس الليان المركزى ");
            hoss66.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss7 = new LatLng(30.684485,30.950284);
            MarkerOptions hoss77 = new MarkerOptions();
            hoss77.position(hoss7);
            hoss77.title("مستشفى تلا المركزى ");
            hoss77.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng hoss8 = new LatLng(30.635560,31.090285);
            MarkerOptions hoss88 = new MarkerOptions();
            hoss88.position(hoss8);
            hoss88.title("مستشفى بركة السبع العام");
            hoss88.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));


            LatLng ho0 = new LatLng(30.432698,31.029018);
            MarkerOptions ho00 = new MarkerOptions();
            ho00.position(ho0);
            ho00.title("مستشفى الباجور المركزى ");
            ho00.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho1 = new LatLng(30.367077,30.505911);
            MarkerOptions ho11 = new MarkerOptions();
            ho11.position(ho1);
            ho11.title("مستشفى السادات المركزى");
            ho11.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho2 = new LatLng(30.562210,31.014535);
            MarkerOptions ho22 = new MarkerOptions();
            ho22.position(ho2);
            ho22.title("مستشفى الشهيد محمد الدرة للأطفال");
            ho22.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho3 = new LatLng(30.556779,31.019561);
            MarkerOptions ho33 = new MarkerOptions();
            ho33.position(ho3);
            ho33.title("مستشفي المنوفية العسكري");
            ho33.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho4 = new LatLng(30.597724,30.905224);
            MarkerOptions ho44 = new MarkerOptions();
            ho44.position(ho4);
            ho44.title("مستشفى الشهداء المركزى");
            ho44.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho5 = new LatLng(30.295695,30.989208);
            MarkerOptions ho55 = new MarkerOptions();
            ho55.position(ho5);
            ho55.title("مستشفى أشمون المركزي");
            ho55.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho6 = new LatLng(30.719794,31.244817);
            MarkerOptions ho66 = new MarkerOptions();
            ho66.position(ho6);
            ho66.title("مستشفى الرمد رفتى محافظة الغربية ");
            ho66.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho7 = new LatLng(30.791830,30.993715);
            MarkerOptions ho77 = new MarkerOptions();
            ho77.position(ho7);
            ho77.title("مكتب مصر الخير - محافظة الغربية");
            ho77.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho8 = new LatLng(30.796417,31.003844);
            MarkerOptions ho88 = new MarkerOptions();
            ho88.position(ho8);
            ho88.title("مستشفى الشروق للجراحات الدقيقه");
            ho88.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng ho9 = new LatLng(30.801106,30.999853);
            MarkerOptions ho99 = new MarkerOptions();
            ho99.position(ho9);
            ho99.title("مستشفى رمضان التخصصى");
            ho99.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));

            LatLng h0 = new LatLng(30.711847,31.250143);
            MarkerOptions h00 = new MarkerOptions();
            h00.position(h0);
            h00.title("مستشفي زفتي العام");
            h00.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h1 = new LatLng(31.004506,30.887400);
            MarkerOptions h11 = new MarkerOptions();
            h11.position(h1);
            h11.title("مستشفى قطور المركزى ");
            h11.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h2 = new LatLng(30.798430,30.994494);
            MarkerOptions h22 = new MarkerOptions();
            h22.position(h2);
            h22.title("المستشفى العالمى بطنطا  ");
            h22.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h3 = new LatLng(30.794495,31.002499);
            MarkerOptions h33 = new MarkerOptions();
            h33.position(h3);
            h33.title("مستشفى طيبة ");
            h33.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h4 = new LatLng(30.780504,30.992480);
            MarkerOptions h44 = new MarkerOptions();
            h44.position(h4);
            h44.title("مستشفى طنطا العسكري");
            h44.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h5 = new LatLng(30.962978, 31.165656);
            MarkerOptions h55 = new MarkerOptions();
            h55.position(h5);
            h55.title("مستشفى الهدى الدولى ");
            h55.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h6 = new LatLng(30.800119, 30.994065);
            MarkerOptions h66 = new MarkerOptions();
            h66.position(h6);
            h66.title("مستشفى الأورام بطنطا ");
            h66.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h7 = new LatLng(30.811271, 30.998276);
            MarkerOptions h77 = new MarkerOptions();
            h77.position(h7);
            h77.title("مركز طنطا الدولى للقلب والصدر");
            h77.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h8 = new LatLng(30.800383, 30.995338);
            MarkerOptions h88 = new MarkerOptions();
            h88.position(h8);
            h88.title("مستشفى الصحة النفسية بطنطا ");
            h88.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));
            LatLng h9 = new LatLng(30.786487, 30.992207);
            MarkerOptions h99 = new MarkerOptions();
            h99.position(h9);
            h99.title("مستشفى 57357 فرع طنطا  ");
            h99.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1));



            map.clear();

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.moveCamera(CameraUpdateFactory.zoomIn());
            map.addMarker(hos11);
            map.addMarker(hos22);
            map.addMarker(hos33);
            map.addMarker(hos44);
            map.addMarker(hos55);
            map.addMarker(hos66);
            map.addMarker(hos77);
            map.addMarker(hos88);
            map.addMarker(hos99);
            map.addMarker(hoss11);
            map.addMarker(hoss22);
            map.addMarker(hoss33);
            map.addMarker(hoss44);
            map.addMarker(hoss55);
            map.addMarker(hoss66);
            map.addMarker(hoss77);
            map.addMarker(hoss88);
            map.addMarker(ho00);
            map.addMarker(ho11);
            map.addMarker(ho22);
            map.addMarker(ho33);
            map.addMarker(ho44);
            map.addMarker(ho55);
            map.addMarker(ho66);
            map.addMarker(ho77);
            map.addMarker(ho88);
            map.addMarker(ho99);
            map.addMarker(h00);
            map.addMarker(h11);
            map.addMarker(h22);
            map.addMarker(h33);
            map.addMarker(h44);
            map.addMarker(h55);
            map.addMarker(h66);
            map.addMarker(h77);
            map.addMarker(h88);
            map.addMarker(h99);





        }

    }
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            assert currentLocation != null;
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(NearestHospitalActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MapXml);

        mapFragment.getMapAsync(NearestHospitalActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }





//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult: called.");
//        mLocationPermissionsGranted = false;
//
//        switch(requestCode){
//            case LOCATION_PERMISSION_REQUEST_CODE:{
//                if(grantResults.length > 0){
//                    for(int i = 0; i < grantResults.length; i++){
//                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
//                            mLocationPermissionsGranted = false;
//                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
//                            return;
//                        }
//                    }
//                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
//                    mLocationPermissionsGranted = true;
//                    //initialize our map
//                    initMap();
//                }
//            }
//        }
//    }

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
                        String lat = c.getString("height");
                        String lon = c.getString("num");
                        String phone = c.getString("spawn_chance");
                        String med = c.getString("weight");

                        name_hos.add(z, name);
                        lat_hos.add(z,lat);
                        lon_hos.add(z,lon);
                        phone_hos.add(z,phone);
                        id_hos.add(z, id);
                        medicine.add(z, med);
                        z++;

                    }

                } catch (final JSONException e) {

                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NearestHospitalActivity.this, "", Toast.LENGTH_SHORT).show();
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



}
