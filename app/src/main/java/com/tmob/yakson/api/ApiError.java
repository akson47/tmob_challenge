package com.tmob.yakson.api;

import retrofit2.Response;

public class ApiError {

    private int statusCode;
    private String Message;
    private String error;

    public ApiError() {}


    public ApiError(Response response){
        statusCode = response.code();
        Message = response.message();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
