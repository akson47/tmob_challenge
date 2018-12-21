package com.tmob.yakson.api;

import com.tmob.michallange.R;
import com.tmob.yakson.helper.DialogHelper;
import com.tmob.yakson.util.App;

import org.json.JSONObject;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ApiErrorUtils {

    public static void parseError(ApiResponse apiResponse) {
        Converter<ResponseBody, ApiError> converter = ApiService.retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error = new ApiError();
        error.setStatusCode(apiResponse.getCode());
        try {
            //error = converter.convert(response.errorBody());
            //error.setStatusCode(response.code());
            JSONObject errorJsonObject = new JSONObject(apiResponse.getErrorMessage());
            error.setMessage(errorJsonObject.getString("Message"));

            if(error.getMessage() != null && !error.getMessage().isEmpty()){
                DialogHelper.showAlertDialog(error.getMessage());
            }
            else if(error.getError() != null && !error.getError().isEmpty()){
                DialogHelper.showAlertDialog(error.getError());
            }
            else{
                DialogHelper.showAlertDialog(
                        App.getInstance().getCurrentActivity().getString(R.string.generic_error)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.showAlertDialog(
                    App.getInstance().getCurrentActivity().getString(R.string.generic_error)
            );
        }
    }
}
