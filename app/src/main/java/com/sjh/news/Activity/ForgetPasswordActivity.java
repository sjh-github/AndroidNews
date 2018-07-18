package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Executor.CachedExecutorService;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.EmailConfirmInfo;
import com.sjh.news.StaicInfo.UserInfo;
import com.sjh.news.Util.EmailUtil;

/**
 * Created by 25235 on 2018/7/9.
 */

public class ForgetPasswordActivity extends Activity {

    private EditText txt_email;
    private TextView btn_getEmailConfirmCode;
    private EditText txt_emailConfirmCode;
    private EditText txt_password;
    private EditText txt_passwordConfirm;
    private Button btn_resetpassword;

    public static boolean getEmailConfirmCodeAble;
    private String email;
    private String emailConfirmCode;
    private String password;
    private String passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        txt_email = findViewById(R.id.txt_email_forgetpassword);
        btn_getEmailConfirmCode = findViewById(R.id.btn_getEmailConfirmCode_forgetpassword);
        txt_emailConfirmCode = findViewById(R.id.txt_emailConfirmCode_forgetpassword);
        txt_password = findViewById(R.id.txt_password_forgetpassword);
        txt_passwordConfirm = findViewById(R.id.txt_passwordConfirm_forgetpassword);
        btn_resetpassword = findViewById(R.id.btn_resetpassword_forgetpassword);
        getEmailConfirmCodeAble = true;
    }

    /**
     * 返回登陆界面
     */
    public void backToLoginPage(View v) {
        Intent backToLoginPageIntent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(backToLoginPageIntent);
    }

    /**
     * 获取邮箱验证码
     */
    public void getEmailConfirmCode(View v) {
        String to = txt_email.getText().toString();
        if (to.length() < 1) {
            return;
        }
        if(getEmailConfirmCodeAble) {
            getEmailConfirmCodeAble = false;
            /*CachedExecutorService.cachedExecutorService.submit(new Runnable() {
                @Override
                public void run() {

                }
            });*/
            new Thread(new EmailUtil(btn_getEmailConfirmCode, "ForgetPasswordActivity", to)).start();
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
            Toast.makeText(ForgetPasswordActivity.this, "已发送邮件", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 重置密码
     */
    public void resetPassword(View v) {
        email = txt_email.getText().toString();
        emailConfirmCode = txt_emailConfirmCode.getText().toString();
        password = txt_password.getText().toString();
        passwordConfirm = txt_passwordConfirm.getText().toString();
        if (email != null && emailConfirmCode != null && passwordConfirm != null && password != null &&
                emailConfirmCode.length() > 0 && email.length() > 0 && password.length() > 0 && passwordConfirm.length() > 0) {
            if (!EmailConfirmInfo.code.equals(txt_emailConfirmCode.getText().toString())) {
                Toast.makeText(ForgetPasswordActivity.this, "验证码不正确", Toast.LENGTH_LONG).show();
                return;
            }
            //重置成功，记录基本信息
            UserInfo.email = email;
            UserInfo.password = password;
        } else {
            Toast.makeText(ForgetPasswordActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
        }
    }
}
