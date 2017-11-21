package com.dzm.recreation.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dzm.recreation.netty.DzmNettyManager;
import com.dzm.recreation.R;
import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.DzmResponse;
import com.dzm.recreation.netty.msg.dto.OnlinRequestDto;
import com.dzm.recreation.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private EditText et_user;

    private EditText et_message;


    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate() {
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DzmNettyManager.getInstacnce().initTaske();
                v.setVisibility(View.GONE);
            }
        });

        et_user = (EditText) findViewById(R.id.et_user);
        et_message = (EditText) findViewById(R.id.et_message);

        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = et_user.getText().toString();
                if(TextUtils.isEmpty(user)){
                    return;
                }
                String msg = et_message.getText().toString();
                if(TextUtils.isEmpty(msg)){
                    return;
                }


            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DzmLoginActivity.class));
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, DzmRigisterActivity.class));
                DzmRequest<OnlinRequestDto> dzmRequest = new DzmRequest<>();
                OnlinRequestDto onlinRequestDto = new OnlinRequestDto();
                onlinRequestDto.userName = "123456";
                dzmRequest.head = 400;
                dzmRequest.data = onlinRequestDto;
                DzmNettyManager.getInstacnce().send(dzmRequest);
            }
        });
    }

    @Override
    public void recive(DzmResponse message) {
        Toast.makeText(this,message.data,Toast.LENGTH_SHORT).show();
    }

}
