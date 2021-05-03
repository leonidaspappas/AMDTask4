package com.httpRequests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherApi {
    private static String apiUrl = "http://api.openweathermap.org/data/2.5/weather";
    //private static String city = "Thessaloniki";
    private static String appId = "b385aa7d4e568152288b3c9f5c2458a5";

    private String city ;

    private HttpURLConnection con;
    private DataOutputStream out;

    public WeatherApi(String city) {
        this.city = city;
    }

    /*
        Initialize properly the url to get the certain information about weather of a city
     */
    public void initialize() throws IOException {
        URL url = new URL (apiUrl+"?q="+city+"&appid="+appId+"&units=metric"); //+"?q="+city+"&appid="+appId
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");



        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

    }
    /*
        execute the request for current weather of a city
     */
    public JSONObject getResponse() throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        JSONObject jsonResponse = new JSONObject(content.toString());

        JSONObject main = jsonResponse.getJSONObject("main");



        in.close();
        return main;
    }
}
