package com.dzm.recreation.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dzm.recreation.netty.DzmNettyManager;
import com.dzm.recreation.R;
import com.dzm.recreation.netty.msg.DzmRequest;
import com.dzm.recreation.netty.msg.dto.RegisterDto;
import com.dzm.recreation.ui.base.BaseActivity;

/**
 * Created by 83642 on 2017/8/8.
 */

public class DzmRigisterActivity extends BaseActivity{

    private EditText et_username;

    private EditText et_password;

    @Override
    protected int layout() {
        return R.layout.activity_rigister;
    }

    @Override
    protected void onCreate() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.bt_rigister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                if(TextUtils.isEmpty(username)){
                    return;
                }

                String pass = et_password.getText().toString();
                if(TextUtils.isEmpty(pass)){
                    return;
                }

                DzmRequest<RegisterDto> dtoDzmRequest = new DzmRequest<>();
                RegisterDto registerDto = new RegisterDto();
                registerDto.passWord = pass;
                registerDto.userName = username;
                dtoDzmRequest.data = registerDto;
                dtoDzmRequest.head = 300;
                DzmNettyManager.getInstacnce().send(dtoDzmRequest);
            }
        });
    }
}
