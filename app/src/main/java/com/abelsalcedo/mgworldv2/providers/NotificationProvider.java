package com.abelsalcedo.mgworldv2.providers;

import com.abelsalcedo.mgworldv2.models.FCMBody;
import com.abelsalcedo.mgworldv2.retrofit.IFCMApi;
import com.abelsalcedo.mgworldv2.retrofit.RetrofitCliente;
import retrofit2.Call;

public class NotificationProvider {
    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call sendNotification(FCMBody body) {
        return RetrofitCliente.getClienteObject(url).create(IFCMApi.class).send(body);
    }

}
