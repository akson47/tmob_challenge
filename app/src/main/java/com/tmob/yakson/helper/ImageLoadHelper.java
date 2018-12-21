package com.tmob.yakson.helper;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tmob.michallange.R;
import com.tmob.yakson.util.App;

public class ImageLoadHelper {

    private static ImageLoadHelper imageLoadHelper;

    public static ImageLoadHelper getInstance() {
        if(imageLoadHelper == null){
            imageLoadHelper = new ImageLoadHelper();
        }
        return imageLoadHelper;
    }

    public void loadImage(ImageView imageView, String url){

        if(url == null || url.isEmpty()) {
            url = "empty";
        }

        Picasso.with(App.getInstance().getCurrentActivity())
                .load(url)
                .resize(500, 0)
                .onlyScaleDown()
                .placeholder(R.drawable.foursquare_splash_logo)
                .error(R.drawable.foursquare_splash_logo)
                .into(imageView);
    }
}