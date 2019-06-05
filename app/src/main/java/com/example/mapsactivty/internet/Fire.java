package com.example.mapsactivty.internet;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class Fire extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GetandSet>> {


    public void onCreate() {

        if (hasNetwork()) {
            getLoaderManager().initLoader(1, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);
        }
    }


    @NonNull
    @Override
    public Loader<List<GetandSet>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<GetandSet>> loader, List<GetandSet> getandSets) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<GetandSet>> loader) {

    }
    private boolean hasNetwork() {

        ConnectivityManager cM = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cM != null) {
            networkInfo = cM.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }
}
