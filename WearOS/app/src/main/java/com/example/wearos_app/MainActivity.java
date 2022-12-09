package com.example.wearos_app;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wearos_app.databinding.ActivityMainBinding;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String API_URL = "https://clsw-fe777-default-rtdb.europe-west1.firebasedatabase.app/";
    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;

        DataSnap initial = new DataSnap(new Date().toString(), 100f);
        Log.d(LOG_TAG, "Initial Data : "+ initial.toString());
        sendDataSnap(initial);
    }

    public void sendDataSnap(DataSnap ds) {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        FirebaseDatabaseApi post = retrofit.create(FirebaseDatabaseApi.class);
        Call<DataSnap> call = post.sendDataSnap(ds);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(LOG_TAG, "Data sent successfully");
                Log.d(LOG_TAG, String.valueOf(call.request()));
                Log.d(LOG_TAG, response.toString());
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(LOG_TAG, "Data sending failed");
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}