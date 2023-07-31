package com.example.lol;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        Button button = findViewById(R.id.button);
        ListenerInfo(button);

    };
    private void ListenerInfo(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkRequest request = new NetworkRequest();

                String url = "http://120.25.206.148:3000/api/lol/bp";

                EditText blueEditText = findViewById(R.id.blueEditText);
                EditText redEditText = findViewById(R.id.redEditText);
                EditText banEditText = findViewById(R.id.banEditText);

                String blue = blueEditText.getText().toString();
                String red = redEditText.getText().toString();
                String ban = banEditText.getText().toString();
                // 添加查询参数
                Uri.Builder builder = Uri.parse(url).buildUpon();
                builder.appendQueryParameter("blue", blue);
                builder.appendQueryParameter("red", red);
                builder.appendQueryParameter("ban", ban);
                String finalUrl = builder.build().toString();
                request.get(finalUrl, new NetworkRequest.OnResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // 获取matchHeros并展示在TextView上
                            JSONObject matchHerosObject = jsonObject.getJSONObject("matchHeros");
                            StringBuilder matchHerosString = new StringBuilder("Match Heros:\n");
                            Iterator<String> matchHerosKeys = matchHerosObject.keys();
                            while (matchHerosKeys.hasNext()) {
                                String heroName = matchHerosKeys.next();
                                int count = matchHerosObject.getInt(heroName);
                                matchHerosString.append(heroName).append(" ").append(count).append("\n");
                            }
                            TextView matchHerosTextView = findViewById(R.id.matchHerosTextView);
                            runOnUiThread(() -> matchHerosTextView.setText(matchHerosString.toString()));

                            // 获取counterHeros并展示在TextView上
                            JSONObject counterHerosObject = jsonObject.getJSONObject("counterHeros");
                            StringBuilder counterHerosString = new StringBuilder("Counter Heros:\n");
                            Iterator<String> counterHerosKeys = counterHerosObject.keys();
                            while (counterHerosKeys.hasNext()) {
                                String heroName = counterHerosKeys.next();
                                int count = counterHerosObject.getInt(heroName);
                                counterHerosString.append(heroName).append(" ").append(count).append("\n");
                            }
                            TextView counterHerosTextView = findViewById(R.id.counterHerosTextView);
                            runOnUiThread(() -> counterHerosTextView.setText(counterHerosString.toString()));

                            // 获取blue并展示在TextView上
                            JSONObject blueObject = jsonObject.getJSONObject("blue");
                            StringBuilder blueString = new StringBuilder("Blue:\n");
                            Iterator<String> blueKeys = blueObject.keys();
                            while (blueKeys.hasNext()) {
                                String key = blueKeys.next();
                                int count = blueObject.getInt(key);
                                blueString.append(key).append(" ").append(count).append("\n");
                            }
                            TextView blueTextView = findViewById(R.id.blueTextView);
                            runOnUiThread(() -> blueTextView.setText(blueString.toString()));

                            // 获取red并展示在TextView上
                            JSONObject redObject = jsonObject.getJSONObject("red");
                            StringBuilder redString = new StringBuilder("Red:\n");
                            Iterator<String> redKeys = redObject.keys();
                            while (redKeys.hasNext()) {
                                String key = redKeys.next();
                                int count = redObject.getInt(key);
                                redString.append(key).append(" ").append(count).append("\n");
                            }
                            TextView redTextView = findViewById(R.id.redTextView);
                            runOnUiThread(() -> redTextView.setText(redString.toString()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // 处理请求失败的情况
                    }
                });
            }
        });

    }
}