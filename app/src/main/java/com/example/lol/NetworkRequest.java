package com.example.lol;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class NetworkRequest {
    private OkHttpClient client;

    public NetworkRequest() {
        client = new OkHttpClient();
    }

    public void get(String url, final OnResponseListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                if (listener != null) {
                    listener.onResponse(responseData);
                }
            }
        });
    }

    public interface OnResponseListener {
        void onResponse(String response);

        void onFailure(String error);
    }
}