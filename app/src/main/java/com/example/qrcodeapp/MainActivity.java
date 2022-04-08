package com.example.qrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                        Intent i = new Intent( MainActivity.this, QRCodeActivity.class );
                        i.putExtra("userId", etUserId.getText().toString());
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "아이디를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }//onCreate..
}