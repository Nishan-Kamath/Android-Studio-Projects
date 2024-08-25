package com.example.myapplication;

import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText etCity;
    TextView tvResult;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "b1693ed7ad181ad79817cc99d0523aa3";

    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.etCity);
        tvResult = findViewById(R.id.tvResult);
    }

        public void getWeatherDetails(View view){
            String tempUrl = "";
            String city = etCity.getText().toString().trim();

            if (city.isEmpty()) {
                tvResult.setText("City field cannot be empty!");
                return;
            }

            tempUrl = url + "?q=" + city + "&appid=" + appid + "&units=metric";

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject main = response.getJSONObject("main");
                                double temp = main.getDouble("temp");
                                String weatherDescription = response.getJSONArray("weather")
                                        .getJSONObject(0)
                                        .getString("description");

                                String resultText = String.format("Weather in %s: %s\nTemperature: %.2f°C", city, weatherDescription, temp);
                                tvResult.setText(resultText);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                tvResult.setText("Error parsing weather data");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tvResult.setText("Failed to fetch weather data");
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }






}