package com.example.qrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import needle.Needle;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etUserId, etUserPw;
    private String stUserId, stUserPw;
    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserId = findViewById(R.id.et_userid);
        etUserPw = findViewById(R.id.et_userpw);
        enterBtn = findViewById(R.id.enter_btn);

        stUserId = etUserId.getText().toString();
        stUserPw = etUserPw.getText().toString();

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etUserId.getText().length() != 0) {
                    if (etUserPw.getText().length() != 0) {


                        new AppLogin().execute(etUserId.getText().toString().trim(), etUserPw.getText().toString().trim());


                        /**
                        Needle.onBackgroundThread().execute(new Runnable() {
                        @Override
                            public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .connectTimeout(2, TimeUnit.MINUTES)
                                    .readTimeout(1, TimeUnit.MINUTES)
                                    .writeTimeout(30, TimeUnit.SECONDS)
                                    .build();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(getResources().getString(R.string.base_url))
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            SpringController controller = retrofit.create(SpringController.class);
                            String userId = null;
                            String usesrPw = null;

                            final Call<List<User_InfoVo>> call = controller.app_login(userId, usesrPw);
                            call.enqueue(new Callback<List<User_InfoVo>>() {
                                @Override
                                public void onResponse(Call<List<User_InfoVo>> call, Response<List<User_InfoVo>> response) {
                                    List<User_InfoVo> list = response.body();
                                    if ( list == null || list.toString().equals("[null]") ){
                                        Toast.makeText(MainActivity.this, "get NULL", Toast.LENGTH_SHORT).show();
                                        Log.d("getUserInfo> ", "get Null");
                                    }else {
                                        Log.d("getUserInfo> ", list.get(0).getEmp_id()+", "+list.get(0).getEmp_pw());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<User_InfoVo>> call, Throwable t) {

                                }
                            });

                            }
                        });
                        **/

//                        Intent i = new Intent( MainActivity.this, QRCodeActivity.class );
//                        i.putExtra("userId", etUserId.getText().toString());
//                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "아이디를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }//onCreate..


    private class AppLogin extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SpringController controller = retrofit.create(SpringController.class);
            String userId = strings[0];
            String userPw = strings[1];
            final Call<List<User_InfoVo>> call = controller.app_login(userId, userPw);
            call.enqueue(new Callback<List<User_InfoVo>>() {
                @Override
                public void onResponse(Call<List<User_InfoVo>> call, Response<List<User_InfoVo>> response) {
                    List<User_InfoVo> list = response.body();
                    if (list == null || list.toString().equals("[null]")) {
                        Log.d("getUserInfo", "null");
                    }else {
                        Log.d("getUserInfo", list.get(0).getEmp_id()+", "+list.get(0).getEmp_pw());
                    }
                }

                @Override
                public void onFailure(Call<List<User_InfoVo>> call, Throwable t) {
                    Log.d("getUserInfo", "fail : "+t.toString());
                }
            });

            return null;
        }
    }


}