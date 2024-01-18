package digi.coders.weathersapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("current")
    Call<JsonObject> getCurrentWeather(
            @Query("access_key")String access_key,
            @Query("query")String query
    );
}
