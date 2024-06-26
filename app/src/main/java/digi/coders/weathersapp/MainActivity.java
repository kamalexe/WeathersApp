package digi.coders.weathersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.search.SearchBar;
import com.google.gson.JsonObject;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final String access_key = "1fd83e33f128c3f94f577fb72920520a";
//    String query = "Ghazipur";

    MaterialSearchBar edit;

    ImageView weather_icons;
    TextView names, countrys, regions, localtimes, utc_offsets, observation_times, temp, wind_speeds, wind_degrees, is_days, weather_description, humiditys, feelslikes;
    private Handler handler = new Handler();  // Handler to post delayed tasks
    private Runnable weatherQueryRunnable;   // Runnable that executes the API call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = findViewById(R.id.name);
        countrys = findViewById(R.id.country);
        regions = findViewById(R.id.region);
        localtimes = findViewById(R.id.localtime);
        utc_offsets = findViewById(R.id.utc_offset);
        observation_times = findViewById(R.id.observation_time);
        temp = findViewById(R.id.temp);
        wind_speeds = findViewById(R.id.wind_speed);
        wind_degrees = findViewById(R.id.wind_degree);
        is_days = findViewById(R.id.is_day);
        weather_description = findViewById(R.id.weather_descriptions);
        feelslikes = findViewById(R.id.feelslike);
        humiditys = findViewById(R.id.humidity);

        weather_icons = findViewById(R.id.weather_icons);

        edit = findViewById(R.id.edit);


        edit.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(weatherQueryRunnable);
                // Define the runnable to execute API call
                weatherQueryRunnable = () -> weather(s.toString());
                // Schedule the runnable to execute after 800 milliseconds
                handler.postDelayed(weatherQueryRunnable, 800);
            }
        });


//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(MainActivity.this, SearchActivity.class);
//                startActivity(a);
//            }
//        });
    }

    void weather(String query) {
        Log.d("KAMAL - weather", "Received query: " + query);  // To confirm what is being received
        GetRetroFIt.GetWeather().getCurrentWeather(access_key, query).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("KAMAL - CurrentWeather", "ResponseBody: " + response.body().toString());


                try {
                    if (response.isSuccessful()) {

                        Log.d("CurrentWeather", "Successfull : " + response.body());
                        JsonObject jsonObject = response.body();

                        String name = jsonObject.get("location").getAsJsonObject().get("name").getAsString();
                        names.setText(name);

                        String country = jsonObject.get("location").getAsJsonObject().get("country").getAsString();
                        countrys.setText(country);

                        String region = jsonObject.get("location").getAsJsonObject().get("region").getAsString();
                        regions.setText(region);

                        String localtime = jsonObject.get("location").getAsJsonObject().get("localtime").getAsString();
                        localtimes.setText(localtime);

                        String utc_offset = jsonObject.get("location").getAsJsonObject().get("utc_offset").getAsString();
                        utc_offsets.setText("utc_offset :- " + utc_offset);

                        String observation_time = jsonObject.get("current").getAsJsonObject().get("observation_time").getAsString();
                        observation_times.setText(observation_time);

                        String temperature = jsonObject.get("current").getAsJsonObject().get("temperature").getAsString();
                        temp.setText(temperature + " °C");

                        String wind_speed = jsonObject.get("current").getAsJsonObject().get("wind_speed").getAsString();
                        wind_speeds.setText(wind_speed);

                        String wind_degree = jsonObject.get("current").getAsJsonObject().get("wind_degree").getAsString();
                        wind_degrees.setText("wind_degree :- " + wind_degree);

                        String is_day = jsonObject.get("current").getAsJsonObject().get("is_day").getAsString();
                        is_days.setText("is_day :- " + is_day);

                        String feelslike = jsonObject.get("current").getAsJsonObject().get("feelslike").getAsString();
                        feelslikes.setText(feelslike);

                        String humidity = jsonObject.get("current").getAsJsonObject().get("humidity").getAsString();
                        humiditys.setText(humidity);

                        String weather_descriptions = jsonObject.get("current").getAsJsonObject().get("weather_descriptions").getAsJsonArray().get(0).getAsString();
                        weather_description.setText("weather_descriptions :- " + weather_descriptions);

                        String weather_icons_url = jsonObject.get("current").getAsJsonObject().get("weather_icons").getAsJsonArray().get(0).getAsString();
                        Picasso.get().load(weather_icons_url).into(weather_icons);


                        // Log.d("CurrentWeather", "Successfull" + response.body());

                    } else {
                        Log.d("CurrentWeather", " un Successfull : ");

                    }

                } catch (Exception e) {
                    Log.d("CurrentWeather", "Exception : " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("CurrentWeather", "onFailure : " + t.getMessage());
            }
        });


    }

}