package com.tmob.yakson.viewcontroller.main;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tmob.michallange.R;
import com.tmob.yakson.api.ApiErrorUtils;
import com.tmob.yakson.api.ApiRequest;
import com.tmob.yakson.customview.AppEditText;
import com.tmob.yakson.helper.DialogHelper;
import com.tmob.yakson.interfaces.ActivityInterface;
import com.tmob.yakson.model.SearchVenuesResponseModel;
import com.tmob.yakson.model.VenueModel;
import com.tmob.yakson.util.AppConstant;
import com.tmob.yakson.viewcontroller.places.PlacesActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MainActivity extends AppCompatActivity implements ActivityInterface, OnLocationUpdatedListener {

    @BindView(R.id.venueTypeAppEditText) AppEditText venueTypeAppEditText;
    @BindView(R.id.venueLocationAppEditText) AppEditText venueLocationAppEditText;

    @BindString(R.string.alert_search) String searchAlertText;

    private MainViewModel mainViewModel;
    private Location userLocation;
    private ArrayList<VenueModel> venueList = new ArrayList<>();
    private final int REQUEST_CODE_LOCATION = 6474;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void initObservers() {
        mainViewModel
                .getSearchVenuesRequestLiveData()
                .observe(
                        this,
                        new Observer<ApiRequest<SearchVenuesResponseModel>>() {
                            @Override
                            public void onChanged(@Nullable ApiRequest<SearchVenuesResponseModel> apiRequest) {
                                switch (apiRequest.getStatus()){
                                    case LOADING:
                                        DialogHelper.showLoadingDialog();
                                        break;
                                    case FINISH:
                                        DialogHelper.hideLoadingDialog();
                                        break;
                                    case SUCCESS:
                                        venueList.clear();
                                        venueList.addAll(apiRequest.getApiResponseBody().getResponse().getVenueList());
                                        Intent placesActivityIntent = new Intent(MainActivity.this, PlacesActivity.class);
                                        placesActivityIntent.putExtra("venueList", venueList);
                                        startActivity(placesActivityIntent);
                                        break;
                                    case ERROR:
                                        ApiErrorUtils.parseError(apiRequest.getRawApiResponse());
                                        break;
                                    case FAIL:
                                        DialogHelper.showAlertDialog(apiRequest.getApiError().getMessage());
                                        break;
                                }
                            }
                        }
                );
    }

    @Override
    public void initActivity() {
        getUserLocation();
    }

    @OnClick(R.id.searchLayout)
    protected void onClick(){
        if(venueTypeAppEditText.getText().length() < 3){
            DialogHelper.showAlertDialog(searchAlertText);
            return;
        }
        searchVenues();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getUserLocation();
        }
    }

    private void getUserLocation(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION);
            return;
        }

        if (!SmartLocation
                .with(this)
                .location()
                .state()
                .locationServicesEnabled()) {
            DialogHelper.showTwoButtonsDialog(
                    R.string.open_location_settings,
                    R.string.yes,
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    },
                    R.string.no,
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

        }

        SmartLocation.with(this)
                .location()
                .oneFix()
                .start(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        if (location != null) {
            userLocation = location;
        } else {
            DialogHelper.showAlertDialog(getString(R.string.could_not_get_location));
        }
    }

    private void searchVenues(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("client_id", AppConstant.FOURSQUARE_CLIENT_ID);
        params.put("client_secret", AppConstant.FOURSQUARE_CLIENT_SECRET);
        params.put("v", AppConstant.FOURSQUARE_QUERY_PARAM_V);
        params.put("limit", AppConstant.FOURSQUARE_QUERY_PARAM_LIMIT);
        params.put("query", venueTypeAppEditText.getText());
        if(!venueLocationAppEditText.isEmpty()){
            params.put("near", venueLocationAppEditText.getText());
        }
        else if(userLocation != null) {
            //ll=40.7243,-74.0018
            params.put("ll", userLocation.getLatitude() + "," + userLocation.getLongitude());
        }
        else {
            params.put("near", "istanbul");
        }
        mainViewModel.searchVenues(params);
    }
}
