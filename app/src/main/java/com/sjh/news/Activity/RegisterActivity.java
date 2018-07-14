package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Domain.User;
import com.sjh.news.Executor.CachedExecutorService;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.UserInfo;
import com.sjh.news.Util.EmailUtil;

/**
 * Created by 25235 on 2018/7/8.
 */

public class RegisterActivity extends Activity {

    private EditText txt_username;
    private EditText txt_email;
    private EditText txt_emailConfirmCode;
    private EditText txt_password;
    private EditText txt_passwordConfirm;
    private Button btn_register;
    private TextView btn_getEmailConfirmCode;

    private String username;
    private String email;
    private String emailConfirmCode;
    private String password;
    private String passwordConfirm;
    public static boolean getEmailConfirmCodeAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        txt_username = findViewById(R.id.txt_username_register);
        txt_email = findViewById(R.id.txt_email_register);
        txt_emailConfirmCode = findViewById(R.id.txt_emailConfirmCode_register);
        txt_password = findViewById(R.id.txt_password_register);
        txt_passwordConfirm = findViewById(R.id.txt_passwordConfirm_register);
        btn_register = findViewById(R.id.btn_register_register);
        btn_getEmailConfirmCode = findViewById(R.id.btn_getEmailConfirmCode);
        getEmailConfirmCodeAble = true;
    }

    /**
     * 返回登陆界面
     */
    public void backToLoginPage(View v) {
        Intent backToLoginPageIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(backToLoginPageIntent);
    }

    /**
     * 获取邮箱验证码
     */
    public void getEmailConfirmCode(View v) {
        if(getEmailConfirmCodeAble) {
            getEmailConfirmCodeAble = false;
            /*CachedExecutorService.cachedExecutorService.submit(new Runnable() {
                @Override
                public void run() {

                }
            });*/
            new Thread(new EmailUtil(btn_getEmailConfirmCode, "RegisterActivity")).start();
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    int time = 60;
                    while (time > 0) {
                        btn_getEmailConfirmCode.setText("剩余: " + time + " 秒");
                        time--;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    getEmailConfirmCodeAble = true;
                    btn_getEmailConfirmCode.setText(R.string.btn_getEmailConfirmCode);
                }
            }).start();*/
            Toast.makeText(RegisterActivity.this, "已发送邮件", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册
     */
    public void register(View v) {
        username = txt_username.getText().toString();
        email = txt_email.getText().toString();
        emailConfirmCode = txt_emailConfirmCode.getText().toString();
        password = txt_password.getText().toString();
        passwordConfirm = txt_passwordConfirm.getText().toString();
        //不为空判断
        if (username != null && email != null && emailConfirmCode != null && password != null && passwordConfirm != null &&
                username.length() > 0 && email.length() > 0 && emailConfirmCode.length() > 0 && password.length() > 0 && passwordConfirm.length() > 0) {
            //密码与确认密码一致性判断
            if (password.equals(passwordConfirm)) {
                //检测用户名是否已有
                if (usernameCheck(username)) {
                    Toast.makeText(RegisterActivity.this, "已存在该用户名", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //注册成功，记录基本信息
                    UserInfo.username = username;
                    UserInfo.email = email;
                    UserInfo.password = password;
                    Intent toInterestChoiceIntent = new Intent(RegisterActivity.this, InterestChoiceActivity.class);
                    startActivity(toInterestChoiceIntent);
                }
            } else {
                Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检测是否已有用户名
     */
    private boolean usernameCheck(String username) {
        for (User user : UserInfo.userArrayList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
