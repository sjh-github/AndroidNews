package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sjh.news.Domain.Interests;
import com.sjh.news.Domain.User;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.TagInfo;
import com.sjh.news.StaicInfo.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 25235 on 2018/7/9.
 */

public class InterestChoiceActivity extends Activity {

    private CheckBox checkBox_Technology;
    private CheckBox checkBox_Fun;
    private CheckBox checkBox_Military;
    private CheckBox checkBox_IT;
    private CheckBox checkBox_Football;
    private CheckBox checkBox_NBA;
    private EditText txt_CustomTag;
    private Button btn_submit;
    //用户信息
    private User user;
    private Map<String, Integer> userInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interestchoice);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        checkBox_Technology = findViewById(R.id.checkbox_technology);
        checkBox_Fun = findViewById(R.id.checkbox_fun);
        checkBox_Military = findViewById(R.id.checkbox_military);
        checkBox_IT = findViewById(R.id.checkbox_it);
        checkBox_Football = findViewById(R.id.checkbox_football);
        checkBox_NBA = findViewById(R.id.checkbox_nba);
        txt_CustomTag = findViewById(R.id.txt_customTag);
        btn_submit = findViewById(R.id.btn_submit_interestChoice);

        user = new User(UserInfo.username, UserInfo.password, UserInfo.email);
        userInterests = new HashMap<>();
    }

    /**
     * 兴趣提交
     */
    public void submit(View view) {
        if (checkBox_Technology.isChecked()) {
            userInterests.put(Interests.TECHNOLOGY, 1);
        } else {
            userInterests.put(Interests.TECHNOLOGY, 0);
        }

        if (checkBox_Fun.isChecked()) {
            userInterests.put(Interests.FUN, 1);
        } else {
            userInterests.put(Interests.FUN, 0);
        }

        if (checkBox_Military.isChecked()) {
            userInterests.put(Interests.MILITARY, 1);
        } else {
            userInterests.put(Interests.MILITARY, 0);
        }

        if (checkBox_IT.isChecked()) {
            userInterests.put(Interests.IT, 1);
        } else {
            userInterests.put(Interests.IT, 0);
        }

        if (checkBox_Football.isChecked()) {
            userInterests.put(Interests.FOOTBALL, 1);
        } else {
            userInterests.put(Interests.FOOTBALL, 0);
        }

        if (checkBox_NBA.isChecked()) {
            userInterests.put(Interests.NBA, 1);
        } else {
            userInterests.put(Interests.NBA, 0);
        }

        if (txt_CustomTag != null && txt_CustomTag.getText().toString().length() > 0) {
            userInterests.put(txt_CustomTag.getText().toString(), 1);
            TagInfo.customTag = txt_CustomTag.getText().toString();
        } else {
            userInterests.put(Interests.customInterest, 0);
        }
        //发送兴趣标签
        user.setUserInterest(userInterests);
        UserInfo.userArrayList.add(user);
        //页面跳转
        Intent toNewsPageIntent = new Intent(InterestChoiceActivity.this, NewsActivity.class);
        startActivity(toNewsPageIntent);
    }
}
