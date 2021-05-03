package com.scheduler;

import com.httpRequests.MessageApi;
import com.httpRequests.WeatherApi;

import java.io.IOException;
import java.util.TimerTask;

public class TimeSchedule extends TimerTask {
    private WeatherApi weather ;
    private MessageApi messageApi ;
    int counter=10;

    public TimeSchedule(WeatherApi weather, MessageApi messageApi) {
        this.weather = weather;
        this.messageApi = messageApi;
    }
    /*
        Timer scheduler that is used from main so as to repeat every X seconds the necessary calls of getting the current weather temperature
        and sending it to the task4 number
        Here we request the current weather and sending message without re-initializing the calls to the proper APIs.
     */
    public void run(){
        try {
            if(this.counter<1){
                System.exit(0);
            }
            weather.initialize();
            Double weatherTemp = weather.getResponse().getDouble("temp");

            messageApi.sendMessage(weatherTemp);
            this.counter --;
            System.out.println("counter is " + this.counter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
