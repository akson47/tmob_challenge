package com.tmob.yakson.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tmob.yakson.util.App;

public class NetworkHelper {

    private static NetworkHelper networkHelper;

    public static NetworkHelper getInstance(){
        if(networkHelper == null){
            networkHelper = new NetworkHelper();
        }
        return networkHelper;
    }
    public boolean isNetworkAvaliable() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getCurrentActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
