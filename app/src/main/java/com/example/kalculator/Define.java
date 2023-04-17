package com.example.kalculator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Define {




    public Stack<String> operatorStack = new Stack<>();    // 연산자를 위한 스택
    public List<String> infixList = new ArrayList<String>();   // 중위 표기
    public List<String> postfixList =new ArrayList<String>();       // 후위 표기



    private static Define instance;
    public static Define ins() {
        if (instance == null) {
            instance = new Define();
        }

        return instance;

    }

}
