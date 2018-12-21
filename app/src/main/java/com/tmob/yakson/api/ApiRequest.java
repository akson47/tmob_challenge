package com.tmob.yakson.api;

import retrofit2.Response;

public class ApiRequest<T> {

    private ApiResponse<T> apiResponse;
    private boolean isLoading;

    public ApiRequest(boolean isLoading) {
        this.isLoading = isLoading;
        apiResponse = new ApiResponse<>();
    }

    public ApiRequest(ApiResponse<T> apiResponse) {
        isLoading = false;
        this.apiResponse = apiResponse;
    }

    public ApiRequest(ApiResponse<T> apiResponse, boolean isLoading) {
        this.isLoading = isLoading;
        this.apiResponse = apiResponse;
    }

    public ApiRequestStatus getStatus(){
        if(isLoading){
            return ApiRequestStatus.LOADING;
        }
        if(apiResponse.isSuccessful()){
            return ApiRequestStatus.SUCCESS;
        }
        if(apiResponse.getCode() == 500){
            return ApiRequestStatus.FAIL;
        }
        if (apiResponse.getError() != null) {
            return ApiRequestStatus.ERROR;
        }
        return ApiRequestStatus.FINISH;
    }

    public T getApiResponseBody(){
        return apiResponse.getBody();
    }

    public Response<T> getApiResponse(){
        return apiResponse.getResponse();
    }

    public ApiResponse<T> getRawApiResponse(){
        return apiResponse;
    }

    public Throwable getApiError(){
        return apiResponse.getError();
    }
}