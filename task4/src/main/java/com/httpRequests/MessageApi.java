package com.httpRequests;


import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

public class MessageApi {
    private static String appId = "5f9138288b71de3617a87cd3";
    private static String appSecret = "RSj69jLowJ";

    private Request request;
    private OkHttpClient client;

    private String accessToken;
    /*
        initialize the url with proper headers and base64 encoding of appId and secret
     */
    public void initialize(){
         client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");

        String originalInput = appId+":"+appSecret;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        request = new Request.Builder()
                .url("https://auth.routee.net/oauth/token")
                .post(body)
                .addHeader("authorization", "Basic "+encodedString)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
    }
    /*
        execute the first call with RouteApi so as to get the access_token we need for forecoming messaging
     */
    public Response authenticate() throws IOException {
        Response response = client.newCall(request).execute();
        String bodyStr= response.body().string();
        JSONObject jsonObjectbject = new JSONObject(bodyStr);
        accessToken = jsonObjectbject.getString("access_token");
        return response;
    }
    /*
        actual message sending to certain number ( task 4 number is '306978745957' )
     */
    public void sendMessage(Double weatherTemp) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String msg = "";
        if(weatherTemp<20){
            msg = "Leonidas Pappas . Temperature less than 20C. "+ weatherTemp;
        }else{
            msg = "Leonidas Pappas . Temperature more than 20C. "+ weatherTemp;
        }
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{ \"body\": \""+msg+"\",\"to\" : \"+306978745957\",\"from\": \"amdTelecom\"}");
        Request request = new Request.Builder()
                .url("https://connect.routee.net/sms")
                .post(body)
                .addHeader("authorization", "Bearer "+ accessToken)
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

    }
}
