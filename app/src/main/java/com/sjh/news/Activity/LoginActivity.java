package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.R;
import com.sjh.news.StaicInfo.UserInfo;

/**
 * Created by 25235 on 2018/7/8.
 */

public class LoginActivity extends Activity {

    private EditText txt_username_login;
    private EditText txt_password_login;
    private Button btn_login_login;
    private TextView btn_register_login;
    private TextView btn_forgetPassword_login;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        txt_username_login = findViewById(R.id.txt_username_login);
        txt_password_login = findViewById(R.id.txt_password_login);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_register_login = findViewById(R.id.btn_register_login);
        btn_forgetPassword_login = findViewById(R.id.btn_forgetPassword_login);
    }

    /**
     * 登陆
     */
    public void login(View v) {
        username = txt_username_login.getText().toString();
        password = txt_password_login.getText().toString();
        if (username != null && password != null && username.length() > 0 && password.length() > 0) {
            Toast.makeText(LoginActivity.this, username + " - " + password, Toast.LENGTH_SHORT).show();
            //登陆成功
            //储存用于信息
            UserInfo.username = username;
            UserInfo.password = password;
            Intent toNewsPageIntent = new Intent(LoginActivity.this, NewsActivity.class);
            startActivity(toNewsPageIntent);
        } else {
            Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册界面
     */
    public void toRegisterPage(View v) {
        Intent toRegisterPageIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(toRegisterPageIntent);
    }

    /**
     * 忘记密码界面
     */
    public void toForgetPasswordPage(View v) {
        Intent toForgetPasswordIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(toForgetPasswordIntent);
    }
}
