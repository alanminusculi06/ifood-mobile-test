package br.com.minusculi.ifoodmobiletest.data.remote.google;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleNaturalLanguageService {

    private static GoogleNaturalLanguageService instance;
    private GoogleNaturalLanguageApi service;
    private Retrofit retrofit;

    private GoogleNaturalLanguageService() {
    }

    public static GoogleNaturalLanguageService getInstance() {
        if (null == instance)
            instance = new GoogleNaturalLanguageService();
        return instance;
    }

    public void configure() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://language.googleapis.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public GoogleNaturalLanguageApi getApiService() {
        if (null == service)
            service = retrofit.create(GoogleNaturalLanguageApi.class);
        return service;
    }

}
