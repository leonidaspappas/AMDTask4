package com.company;

import com.httpRequests.MessageApi;
import com.httpRequests.WeatherApi;
import com.scheduler.TimeSchedule;
import com.squareup.okhttp.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;

public class Main {
    /*
    initialize all the api ( check each class for further explanation )
    then use Timer to run the requests of getting weather and sending message about it to certain phone number
    so as to run every 10 minute for 10 times
     */
    public static void main(String[] args) throws IOException {

        WeatherApi weatherApi = new WeatherApi("Thessaloniki");
        MessageApi messageApi = new MessageApi();
        weatherApi.initialize();
        messageApi.initialize();
        Response resp = messageApi.authenticate();



        Timer timer = new Timer();
        timer.schedule(new TimeSchedule(weatherApi,messageApi), 0, 10*60*1000);

    }
}
