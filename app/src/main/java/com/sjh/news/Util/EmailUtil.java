package com.sjh.news.Util;

import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Activity.ForgetPasswordActivity;
import com.sjh.news.Activity.RegisterActivity;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.EmailConfirmInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 25235 on 2018/7/12.
 */

public class EmailUtil implements Runnable {
    private TextView txt_email;
    private String className;
    private String to;

    public EmailUtil(TextView txt_email, String className, String to) {
        this.txt_email = txt_email;
        this.className = className;
        this.to = to;
    }

    @Override
    public void run() {
        int time = 60;
        String address = "http://115.159.71.92/rps/fxemail?to=" + to;
        System.out.println(address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("***failure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EmailConfirmInfo.code = response.body().string();
                System.out.println("*****" + EmailConfirmInfo.code);
            }
        });

        while (time > 0) {
            txt_email.setText("剩余: " + time + " 秒");
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if ("RegisterActivity".equals(className)) {
            RegisterActivity.getEmailConfirmCodeAble = true;
        } else {
            ForgetPasswordActivity.getEmailConfirmCodeAble = true;
        }

        txt_email.setText(R.string.btn_getEmailConfirmCode);
    }
}
