package com.example.mapsactivty;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Room extends AppCompatActivity {

    Spinner spinner;
    Button search;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        spinner = findViewById(R.id.spinner);
        search = findViewById(R.id.search);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String select = spinner.getSelectedItem().toString();
                if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
                else if (select.equals("")){

                }
            }
        });
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
}
