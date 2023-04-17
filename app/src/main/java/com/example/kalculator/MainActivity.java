package com.example.kalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    ButtonClick cButtonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
    }

    /**
     * 필드 초기화
     */
    void init() {
        // 타이틀 바 없애기
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        // ButtonClick 객체 생성
        cButtonClick = new ButtonClick(this);
    }

    public void ButtonClick(View v) {
        cButtonClick.btnClick(v);
    }

    public void ClearClick(View v){
        cButtonClick.clearClick(v);
    }
    public void DeleteClick(View v) {
        cButtonClick.deleteClick(v);
    }
    public void EqualClick(View v){
        cButtonClick.equalClick(v);
    }
}