package com.gln.codenum1.chapter5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.MainActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/2.
 */
public class LoginActivity extends BaseActivity {

    private EditText mEditUserName;
    private EditText mEditPwd;
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUserName = (EditText) findViewById(R.id.edit_login_username);
        mEditPwd = (EditText) findViewById(R.id.edit_login_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditUserName.getText()) && !TextUtils.isEmpty(mEditPwd.getText())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Please input userName and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
