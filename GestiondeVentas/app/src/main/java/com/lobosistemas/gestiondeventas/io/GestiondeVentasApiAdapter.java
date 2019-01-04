package com.lobosistemas.gestiondeventas.io;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestiondeVentasApiAdapter {

    //private static GestiondeVentasApiService API_SERVICE;

    public static GestiondeVentasApiService getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
       // logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
       // httpClient.addInterceptor(logging);

        String baseUrl = "http://192.168.1.80/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //API_SERVICE = retrofit.create(GestiondeVentasApiService.class);

        GestiondeVentasApiService service = retrofit.create(GestiondeVentasApiService.class);

        return service;
    }

}