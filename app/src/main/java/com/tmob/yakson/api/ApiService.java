package com.tmob.yakson.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tmob.yakson.util.AppConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static ApiService apiService = new ApiService();
    public static ApiInterface apiInterface;
    public static Retrofit retrofit;


    private ApiService() { reset();}

    public static ApiService getInstance() {
        return apiService;
    }

    public void reset() {
        Gson gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Content-Type", AppConstant.API_CONTENT_TYPE_JSON);
                return chain.proceed(builder.build());
            }
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();  // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //builder.addInterceptor(logging);

        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.API_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        // Service setup
        apiInterface = retrofit.create(ApiInterface.class);
    }
}
