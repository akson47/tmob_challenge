package com.tmob.yakson.api;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final Throwable error;
    private Response<T> response;
    private boolean isLoading;
    private String errorMessage;

    public ApiResponse() {
        this(false);
    }

    public ApiResponse(boolean isLoading) {
        this.isLoading = isLoading;
        code = -1;
        body = null;
        error = null;
        errorMessage = "";
    }

    public ApiResponse(Throwable error, boolean isLoading) {
        this(error);
        this.isLoading = isLoading;
        errorMessage = "";
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        this.error = error;
        errorMessage = "";
    }

    public ApiResponse(Response<T> response, boolean isLoading) {
        this(response);
        this.isLoading = isLoading;
        errorMessage = "";
    }

    public ApiResponse(Response<T> response) {
        isLoading = false;
        this.response = response;
        code = response.code();
        if(response.isSuccessful()) {
            body = response.body();
            error = null;
            errorMessage = "";
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e("ERROR", "error while parsing response", ignored);
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            error = new IOException(message);
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }


    public int getCode() {
        return code;
    }

    @Nullable
    public T getBody() {
        return body;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public Response<T> getResponse() {
        return response;
    }

    public ApiRequestStatus getStatus(){
        if(isLoading){
            return ApiRequestStatus.LOADING;
        }
        if(isSuccessful()){
            return ApiRequestStatus.SUCCESS;
        }
        if(code == 500){
            return ApiRequestStatus.FAIL;
        }
        if (error != null) {
            return ApiRequestStatus.ERROR;
        }
        return ApiRequestStatus.FINISH;
    }

    public String getErrorMessage() {
        if(errorMessage == null){
            errorMessage = "";
        }
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
