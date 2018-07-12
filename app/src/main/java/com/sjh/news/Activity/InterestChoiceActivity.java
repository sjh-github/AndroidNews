package com.sjh.news.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sjh.news.R;
import com.sjh.news.StaicInfo.TagInfo;
import com.sjh.news.StaicInfo.UserInfo;

import java.util.ArrayList;

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
    }

    /**
     * 兴趣提交
     */
    public void submit(View view) {
        if (checkBox_Technology.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.TECHNOLOGY);
        }
        if (checkBox_Fun.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.FUN);
        }
        if (checkBox_Military.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.MILITARY);
        }
        if (checkBox_IT.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.IT);
        }
        if (checkBox_Football.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.FOOTBALL);
        }
        if (checkBox_NBA.isChecked()) {
            UserInfo.intersetArrayList.add(TagInfo.NBA);
        }
        if (txt_CustomTag != null && txt_CustomTag.getText().toString().length() > 0) {
            UserInfo.intersetArrayList.add(txt_CustomTag.getText().toString());
        }
        //发送兴趣标签

        //进行登陆

        //页面跳转
        Intent toNewsPageIntent = new Intent(InterestChoiceActivity.this, NewsActivity.class);
        startActivity(toNewsPageIntent);
    }
}
