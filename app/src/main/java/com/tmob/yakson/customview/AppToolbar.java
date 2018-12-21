package com.tmob.yakson.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmob.michallange.R;
import com.tmob.yakson.util.App;

public class AppToolbar extends Toolbar {

    private View rootView;
    private TextView toolbarTitleTextView;
    private ImageView backIcon;
    private String toolbarTitle;
    private boolean isBackIconVisible;

    public AppToolbar(Context context) {
        super(context);
        init(context);
    }

    public AppToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0);
        try {
            toolbarTitle = a.getString(R.styleable.AppToolbar_toolbarTitle);
            isBackIconVisible = a.getBoolean(R.styleable.AppToolbar_backIconVisibility, false);
        } finally {
            a.recycle();
        }
        init(context);
    }

    private void init(Context context){

        rootView = inflate(context, R.layout.view_app_toolbar, this);
        this.setPadding(0, 0, 0, 0);
        this.setContentInsetsAbsolute(0, 0);

        toolbarTitleTextView = rootView.findViewById(R.id.toolbarTitleTextView);
        backIcon = rootView.findViewById(R.id.backIcon);

        if(isBackIconVisible){
            backIcon.setVisibility(VISIBLE);
        }

        backIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getCurrentActivity().onBackPressed();
            }
        });

        if(toolbarTitle != null && !toolbarTitle.isEmpty()){
            toolbarTitleTextView.setText(toolbarTitle);
        }
    }

}
