package digi.coders.weathersapp;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetroFIt {

    public static ApiService GetWeather(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        Log.d("Kamal - ApiService",retrofit.toString());

        return retrofit.create(ApiService.class);
    }
}
