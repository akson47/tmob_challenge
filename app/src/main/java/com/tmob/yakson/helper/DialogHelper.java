package com.tmob.yakson.helper;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tmob.michallange.R;
import com.tmob.yakson.model.VenueModel;
import com.tmob.yakson.util.App;

public class DialogHelper {

    private static KProgressHUD spinLoadingDialog;
    public static void showLoadingDialog() {
        if (spinLoadingDialog != null && spinLoadingDialog.isShowing()) {
            return;
        }
        spinLoadingDialog = KProgressHUD.create(App.getInstance().getCurrentActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(ContextCompat.getColor(App.getInstance().getCurrentActivity(), android.R.color.transparent))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.70f)
                .show();
    }

    public static void hideLoadingDialog() {
        if (spinLoadingDialog != null && spinLoadingDialog.isShowing()) {
            spinLoadingDialog.dismiss();
            spinLoadingDialog = null;
        }
    }

    public static void showAlertDialog(String content){
        new MaterialDialog.Builder(App.getInstance().getCurrentActivity())
                .titleColorRes(R.color.colorAccent)
                .title(R.string.alert)
                .content(content)
                .contentColorRes(R.color.black)
                .positiveColorRes(R.color.colorAccent)
                .positiveText(R.string.ok)
                .show();
    }

    public static void showTwoButtonsDialog(int messageResId,
                                            int positiveTextResId,
                                            MaterialDialog.SingleButtonCallback positiveButtonCallback,
                                            int negativeResId,
                                            MaterialDialog.SingleButtonCallback negativeButtonCallback){
        new MaterialDialog.Builder(App.getInstance().getCurrentActivity())
                .title(R.string.alert)
                .content(messageResId)
                .titleColorRes(R.color.colorAccent)
                .contentColorRes(R.color.black)
                .positiveText(positiveTextResId)
                .onPositive(positiveButtonCallback)
                .positiveColorRes(R.color.colorAccent)
                .negativeText(negativeResId)
                .onNegative(negativeButtonCallback)
                .positiveColorRes(R.color.colorAccent)
                .show();
    }

    public static void showVenueDetailsDialog(final VenueModel venueModel){


        final MaterialDialog alertDialog =
                new MaterialDialog.Builder(App.getInstance().getCurrentActivity())
                        .customView(R.layout.dialog_venue_details, true)
                        .build();

        View dialogView = alertDialog.getCustomView();

        ImageView venueImageView = dialogView.findViewById(R.id.venueImageView);
        TextView venueNameTextView = dialogView.findViewById(R.id.venueNameTextView);
        MapView mapView = (MapView) dialogView.findViewById(R.id.mapView);
        MapsInitializer.initialize(App.getInstance().getCurrentActivity());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng latLng = new LatLng(venueModel.getLocation().getLat(),
                        venueModel.getLocation().getLng());
                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title(venueModel.getName()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            }
        });



        ImageLoadHelper.getInstance().loadImage(venueImageView, venueModel.getVenuePhotoModel().getImageUrl());
        venueNameTextView.setText(venueModel.getName());

        alertDialog.show();
    }

}
