package com.example.hw05;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw05.databinding.FragmentWeatherBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    FragmentWeatherBinding binding;
    Config config = new Config();
    private static final String ARG_PARAM_CITY = "city";


    private DataService.City mCity;


    public WeatherFragment() {
        // Required empty public constructor
    }


    public static WeatherFragment newInstance(DataService.City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewCityName.setText(mCity.toString());
        getWeather();

    }
    OkHttpClient client = new OkHttpClient();
    private void getWeather(){
        Gson gson = new Gson();


        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("appid", config.getApikey())
                .addQueryParameter("units", "imperial")
                .addQueryParameter("lon", String.valueOf(mCity.getLon()))
                .addQueryParameter("lat", String.valueOf(mCity.getLat()))
                .build();



        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseBody = response.body().string();
                    WeatherResponse weatherResponse = gson.fromJson(responseBody,WeatherResponse.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.textViewTempMax.setText(weatherResponse.main.temp_max.toString()+" F");
                            binding.textViewTemp.setText(weatherResponse.main.temp.toString()+" F");
                            binding.textViewTempMin.setText(weatherResponse.main.temp_min.toString()+" F");
                            binding.textViewHumidity.setText(weatherResponse.main.humidity.toString()+" %");
                            binding.textViewWindDegree.setText(weatherResponse.wind.deg.toString()+" degrees");
                            binding.textViewWindSpeed.setText(weatherResponse.wind.speed.toString()+" miles/hr");
                            binding.textViewDesc.setText(weatherResponse.weather.get(0).description);
                            binding.textViewCloudiness.setText(weatherResponse.clouds.all.toString()+" %");
                            Picasso.get().load("http://openweathermap.org/img/wn/10d@2x.png").into(binding.imageViewWeatherIcon);
                        }
                    });
                } else {
                    Log.d("demo", "Did not work");
                }
            }
        });
    }
}