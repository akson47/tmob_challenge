package com.tmob.yakson.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tmob.michallange.R;
import com.tmob.yakson.helper.ViewHelper;

public class AppEditText extends RelativeLayout {

    private Context context;
    private View rootView;
    private EditText editText;
    private String hint, inputType;

    public AppEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppEditText, 0, 0);
        try {
            hint = a.getString(R.styleable.AppEditText_hint);
            inputType = a.getString(R.styleable.AppEditText_inputType);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {

        rootView = inflate(context, R.layout.view_app_edittext, this);
        editText = rootView.findViewById(R.id.editText);

        setInputType();

        if(hint != null && !hint.isEmpty()){
            editText.setHint(hint);
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.requestFocus();
                ViewHelper.getInstance().showKeyboard();
            }
        });
    }

    private void setInputType(){
        if (inputType == null)
            inputType = "text";
        switch (inputType){
            case "alphabetical":
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                editText.setFilters(new InputFilter[] {
                        new InputFilter() {
                            public CharSequence filter(CharSequence src, int start,
                                                       int end, Spanned dst, int dstart, int dend) {
                                if(src.equals("")){ // for backspace
                                    return src;
                                }
                                if(src.toString().matches("[a-zA-Z ]+")){
                                    return src;
                                }
                                return "";
                            }
                        }
                });
                break;
            case "name":
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                break;
            case "password":
                Typeface typeface = editText.getTypeface();
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTypeface(typeface);
                break;
            case "email":
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "number":
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_NUMBER);
                break;
            case "address":
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // if edittext has 10chars & this is not called yet, add new line
                        if(editText.getText().length() == 40 * editText.getLineCount()) {
                            editText.append("\n");
                        }
                    }
                });
                editText.setLines(5);
                editText.setGravity(Gravity.TOP | Gravity.START);
                break;
            case "textArea":
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // if edittext has 10chars & this is not called yet, add new line
                        if(editText.getText().length() == 40 * editText.getLineCount()) {
                            editText.append("\n");
                        }
                    }
                });
                editText.setLines(5);
                editText.setGravity(Gravity.TOP | Gravity.START);
                break;

            default:
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                break;
        }
    }

    public boolean isEmpty(){
        return editText.getText().toString().trim().isEmpty();
    }


    public String getText(){
        return editText.getText().toString().trim();
    }

    public void setText(String text){
        editText.setText(text);
    }
}

