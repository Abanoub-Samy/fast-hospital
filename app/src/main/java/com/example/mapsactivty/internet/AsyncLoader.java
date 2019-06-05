package com.example.mapsactivty.internet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class AsyncLoader extends AsyncTaskLoader {
    private String mURL;

    public AsyncLoader(@NonNull Context context , String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<GetandSet> loadInBackground() {
        return null;
    }
}
