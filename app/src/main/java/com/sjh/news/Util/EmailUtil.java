package com.sjh.news.Util;

import android.widget.TextView;

import com.sjh.news.Activity.ForgetPasswordActivity;
import com.sjh.news.Activity.RegisterActivity;
import com.sjh.news.R;

/**
 * Created by 25235 on 2018/7/12.
 */

public class EmailUtil implements Runnable {
    private TextView txt_email;
    private String className;

    public EmailUtil(TextView txt_email, String className) {
        this.txt_email = txt_email;
        this.className = className;
    }

    @Override
    public void run() {
        int time = 60;
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
