package com.tmob.yakson.helper;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.tmob.yakson.util.App;

public class ViewHelper {

    private static ViewHelper viewHelper;

    public static ViewHelper getInstance() {
        if(viewHelper == null){
            viewHelper = new ViewHelper();
        }
        return viewHelper;
    }

    public void toogleKeyboard() {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getCurrentActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm == null){
            return;
        }
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
    }

    public void hideKeyboard() {
        View view = App.getInstance().getCurrentActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) App.getInstance().getCurrentActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getCurrentActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0); // show
        }
    }

    public void setViewHeightByRate(View view, double rate) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) ((double) layoutParams.width / rate);
        view.setLayoutParams(layoutParams);
    }
}