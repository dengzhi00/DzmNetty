package com.dzm.recreation.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dzm.recreation.netty.DzmNettyManager;
import com.dzm.recreation.R;
import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.DzmResponse;
import com.dzm.recreation.netty.msg.dto.LoginDto;
import com.dzm.recreation.ui.base.BaseActivity;

/**
 * Created by 83642 on 2017/8/7.
 */

public class DzmLoginActivity extends BaseActivity {

    private EditText user_mesage;

    private EditText user_pass;

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate() {

        user_mesage = (EditText) findViewById(R.id.user_mesage);
        user_pass = (EditText) findViewById(R.id.user_pass);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = user_mesage.getText().toString();
                if(TextUtils.isEmpty(txt)){
                    return;
                }
                String pass = user_pass.getText().toString();
                if(TextUtils.isEmpty(pass)){
                    return;
                }
                LoginDto loginDto = new LoginDto();
                loginDto.password = pass;
                loginDto.userphone = txt;
                DzmRequest<LoginDto> dtoDzmRequest = new DzmRequest<>();
                dtoDzmRequest.head = 200;
                dtoDzmRequest.data = loginDto;
                DzmNettyManager.getInstacnce().send(dtoDzmRequest);
            }
        });
    }

    @Override
    public void recive(DzmResponse message) {
        if(message.head == 200){
            if(TextUtils.equals(message.data,"登录成功")){
                finish();
            }
        }
    }

}
