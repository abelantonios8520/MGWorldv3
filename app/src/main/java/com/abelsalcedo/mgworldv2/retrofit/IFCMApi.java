package com.abelsalcedo.mgworldv2.retrofit;

import com.abelsalcedo.mgworldv2.Model.FCMBody;
import com.abelsalcedo.mgworldv2.Model.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAx4_-yEE:APA91bFDvP4uiVVnW2LTYK7WUE9nSu6gS1IucZs5VJFKcgD8F0qOr7I6Zd__3pMQPDdNnU34Xido5kiqLIx7id_5XH3cx2CJokfCeW8WdnjSraV1w3ybOLzcANWr5QfkuTtd_7-wwOO9"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);

}