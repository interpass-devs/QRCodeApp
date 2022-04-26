package com.example.qrcodeapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.qrcodeapp.R;
import com.example.qrcodeapp.SpringController;
import com.example.qrcodeapp.VO.User_InfoVo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserId, etUserPw;
    private String stUserId, stUserPw;
    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

//                        Intent i = new Intent(LoginActivity.this, ShakeScreenActivity.class);
//                        i.putExtra("user_no", stUserId+stUserPw);
//                        startActivity(i);

//                        new AppLogin().execute(etUserId.getText().toString().trim(), etUserPw.getText().toString().trim());
                        new ERPAppLogin().execute(etUserId.getText().toString().trim(), etUserPw.getText().toString().trim());



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

                    }else{
                        Toast.makeText(LoginActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "아이디를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }//onCreate..


    private class AppLogin extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SpringController controller = retrofit.create(SpringController.class);
            String userId = strings[0];
            String userPw = strings[1];

            final Call<List<User_InfoVo>> call = controller.QRLoginInfo(userId, userPw);
            call.enqueue(new Callback<List<User_InfoVo>>() {
                @Override
                public void onResponse(Call<List<User_InfoVo>> call, Response<List<User_InfoVo>> response) {
                    List<User_InfoVo> list = response.body();
                    if (list == null || list.toString().equals("[null]")) {
                        Log.d("getUserInfo", "null");
                    }else {
                        Log.d("getUserInfo", list.get(0).getUser_no()+", "+list.get(0).getUser_id()+", "+list.get(0).getUser_pw());
//                        Intent i = new Intent(LoginActivity.this, QRCodeActivity.class);
//                        i.putExtra("user_no", list.get(0).getUser_no());
//                        startActivity(i);
                        Intent i = new Intent(LoginActivity.this, ShakeScreenActivity.class);
                        i.putExtra("user_no", list.get(0).getUser_no());
                        startActivity(i);
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



    private class ERPAppLogin extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SpringController erp = retrofit.create(SpringController.class);

            String emp_id = strings[0];
            String emp_pw = strings[1];

            final Call<List<User_InfoVo>> call = erp.app_login(emp_id,emp_pw);
            call.enqueue(new Callback<List<User_InfoVo>>() {
                @Override
                public void onResponse(Call<List<User_InfoVo>> call, Response<List<User_InfoVo>> response) {
                    List<User_InfoVo> list = response.body();
                    if (list == null || list.toString().equals("[null]")) {
                        etUserId.setText("");
                        etUserPw.setText("");
                        Toast.makeText(LoginActivity.this , "아이디랑 비민번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Intent i = new Intent(LoginActivity.this, ShakeScreenActivity.class);
                        i.putExtra("user_no", "00001");
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<List<User_InfoVo>> call, Throwable t) {

                }
            });

            return null;
        }
    }


}