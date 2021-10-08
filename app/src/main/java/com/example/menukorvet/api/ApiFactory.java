package com.example.menukorvet.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Objects;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static ApiFactory apiFactoryThis;
    private Retrofit retrofit;

    //Authorization
    private static final String KEY_AUTHORIZATION = "Authorization",
                                LOGIN = "canteen",
                                PASSWORD = "canteen";

    private static final String BASE_URL = "https://canteen.korvet-jsc.ru/";

    private ApiFactory() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    Request request = response.request();
                    if (request.header(KEY_AUTHORIZATION) != null)
                        // Логин и пароль неверны
                        return null;
                    return request.newBuilder()
                            .header(KEY_AUTHORIZATION, Credentials.basic(LOGIN, PASSWORD))
                            .build();
                })
                .build();

        retrofit = new Retrofit.Builder()
                               .client(okHttpClient)
                               .addConverterFactory(GsonConverterFactory.create())
                               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                               .baseUrl(BASE_URL)
                               .build();
    }

    public static ApiFactory getInstance() {
        if (Objects.isNull(apiFactoryThis)) {
            apiFactoryThis = new ApiFactory();
        }

        return apiFactoryThis;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

}
