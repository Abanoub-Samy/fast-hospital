package com.example.mapsactivty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapsactivty.internet.HttpHandler;

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


    ArrayList<String> name_hos = new ArrayList<>();
    ArrayList<String> lat = new ArrayList<>();
    ArrayList<String> lon = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    ArrayList<String> medicine = new ArrayList<>();
    ArrayList<String> blood = new ArrayList<>();



    String text;

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


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
                new Getsearch().execute();

                array(name_hos);
//                    for(int x=0 ; x < string.size();x++){
//                        Toast.makeText(Medicine.this, string.get(x), Toast.LENGTH_SHORT).show();
//                        //Toast.makeText(Medicine.this, string.size(), Toast.LENGTH_SHORT).show();
//
//
//                    }




            }
        });
    }
    public void array (ArrayList arrayList){
        for(int x=0 ; x < arrayList.size();x++){
            Toast.makeText(Medicine.this,  ""+arrayList.get(x), Toast.LENGTH_SHORT).show();
            //Toast.makeText(Medicine.this, string.size(), Toast.LENGTH_SHORT).show();


        }

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
                        String name = c.getString("name");
                        String id = c.getString("id");
                        String candy = c.getString("weight");
                        if(candy.contains("30.0")){
                            name_hos.add(z,name);
                            z++;
                        }

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
            URL url ;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }



    }

}


