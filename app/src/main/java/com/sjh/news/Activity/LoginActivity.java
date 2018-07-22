package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Domain.Interests;
import com.sjh.news.Domain.User;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.HttpInfo;
import com.sjh.news.StaicInfo.UserInfo;

import java.util.HashMap;
import java.util.Map;

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
    private SQLiteDatabase db;

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
        //人员信息初始化
        User user = new User("sjh", "123456", "252355189@qq.com");
        Map<String, Integer> map = new HashMap<>();
        map.put(Interests.TECHNOLOGY, 0);
        map.put(Interests.FUN, 1);
        map.put(Interests.MILITARY, 1);
        map.put(Interests.IT, 0);
        map.put(Interests.FOOTBALL, 0);
        map.put(Interests.NBA, 0);
        user.setUserInterest(map);
        UserInfo.userArrayList.add(user);
        //数据库初始化
        db = openOrCreateDatabase("user.db", Context.MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF EXISTS userdb");
        db.execSQL("create table if not exists userdb (username text primary key, password text, email text, technology integer, fun integer, military integer, it integer, football integer, nba integer)");

    }

    /**
     * 登陆
     */
    public void login(View v) {
        username = txt_username_login.getText().toString();
        password = txt_password_login.getText().toString();
        if (username != null && password != null && username.length() > 0 && password.length() > 0) {
            //登陆成功
            if (login(username, password)) {
                //储存用户信息
                UserInfo.username = username;
                UserInfo.password = password;
                //页面跳转
                Intent toNewsPageIntent = new Intent(LoginActivity.this, NewsActivity.class);
                startActivity(toNewsPageIntent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
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

    /**
     * 登陆验证
     */
    private boolean login(String username, String password) {
       Cursor userCursor = db.query("userdb", new String[]{"username", "password", "email"}, null, null, null, null, null);
        if (userCursor.moveToFirst()) {
           for (int i = 0; i < userCursor.getCount(); i++) {
               userCursor.move(i);
               String dbusername = userCursor.getString(userCursor.getColumnIndex("username"));
               String dbpassword = userCursor.getString(userCursor.getColumnIndex("password"));
               String dbemail = userCursor.getString(userCursor.getColumnIndex("email"));
               UserInfo.userArrayList.add(new User(dbusername, dbpassword, dbemail));
           }
        }
        userCursor.close();
        for (User user : UserInfo.userArrayList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
