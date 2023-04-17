package com.example.kalculator;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonClick {
    AppCompatActivity aButtonClick;
    TextInput cTextInput;
     TextView txtExpression;         // 계산 과정 텍스트
     TextView txtResult;             // 결과 값 텍스트
     List<Integer> checkList;        // -1: 이콜, 0: 연산자, 1: 숫자, 2: . / 예외 발생을 막는 리스트

    public ButtonClick(AppCompatActivity appCompatActivity) {
        aButtonClick = appCompatActivity;
        // TextInput 객체 생성
        cTextInput = new TextInput(appCompatActivity);

        checkList = new ArrayList<>();

        // 아이디 연결
        txtExpression = aButtonClick.findViewById(R.id.txt_expression);
        txtResult = aButtonClick.findViewById(R.id.txt_result);
    }

    /**
     * 숫자, 연산자 버튼 이벤트 처리
     *
     * @param v
     */
    public void btnClick(View v) {
        // 체크리스트가 비어있지 않고 마지막이 이콜(=)일 때
        if (!checkList.isEmpty() && checkList.get(checkList.size() - 1) == -1) {
            txtExpression.setText(txtResult.getText().toString());
            checkList.clear();  // 체크리스트 클리어
            checkList.add(1);   // 정수
            checkList.add(2);   // .
            checkList.add(1);   // 소수점
            txtResult.setText("");
        }

        switch (v.getId()) {
            case R.id.btn_one:
                addNumber("1");
                break;
            case R.id.btn_two:
                addNumber("2");
                break;
            case R.id.btn_three:
                addNumber("3");
                break;
            case R.id.btn_four:
                addNumber("4");
                break;
            case R.id.btn_five:
                addNumber("5");
                break;
            case R.id.btn_six:
                addNumber("6");
                break;
            case R.id.btn_seven:
                addNumber("7");
                break;
            case R.id.btn_eight:
                addNumber("8");
                break;
            case R.id.btn_nine:
                addNumber("9");
                break;
            case R.id.btn_zero:
                addNumber("0");
                break;
            case R.id.btn_dot:
                addDot(".");
                break;
            case R.id.btn_division:
                addOperator("/");
                break;
            case R.id.btn_multi:
                addOperator("X");
                break;
            case R.id.btn_plus:
                addOperator("+");
                break;
            case R.id.btn_minus:
                addOperator("-");
                break;
        }
    }
    /**
     * 숫자 버튼 이벤트 처리
     *
     * @param str
     */
    void addNumber(String str) {
        checkList.add(1); // 체크리스트의 배열에 1(숫자) 추가
        txtExpression.append(str); // append는 setText와 달리 기존내용을 유지한채 뒤에 붙여준다

    }

    /**
     * . 버튼 이벤트 처리
     *
     * @param str
     */
    void addDot(String str) {
        // 만약 텍스트뷰가 비어있을 때
        if (checkList.isEmpty()) {
            Toast.makeText(aButtonClick, ". 을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 만약 마지막이 숫자가 아닐때
        else if (checkList.get(checkList.size() - 1) != 1) {
            Toast.makeText(aButtonClick, ". 을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 하나의 수에 . 이 여러 개 오는 것을 막기
        // 한자리 수는 .이 올 수 있는 장소가 정해져 있어서 for문에 들어오지 않는다
        for (int i = checkList.size() - 2; i >= 0; i--) {
            int check = checkList.get(i);
            // 만약 하나의 수에 .이 2개 이상 올때
            if (check == 2) {
                Toast.makeText(aButtonClick, ". 을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (check == 0) break;
            if (check == 1) continue;
        }
        // 체크리스트의 배열에 2(".") 추가
        checkList.add(2);
        txtExpression.append(str); // append는 setText와 달리 기존내용을 유지한채 뒤에 붙여준다
    }

    /**
     * 연산자 버튼 이벤트 처리
     *
     * @param str
     */
    void addOperator(String str) {
        try {
            // 만약 텍스트 뷰가 비어있을 때
            if (checkList.isEmpty()) {
                // 처음 연산자 사용 막기
                Toast.makeText(aButtonClick, "연산자가 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            // 만약 마지막이 연산자거나 "."일 때
            if (checkList.get(checkList.size() - 1) == 0 && checkList.get(checkList.size() - 1) == 2) {
                // 연산자 두 번 사용, 완벽한 수가 오지 않았을 때 막기
                System.out.println("값 : " + checkList);
                Toast.makeText(aButtonClick, "연산자가 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkList.size() > 0) {
                if (checkList.get(checkList.size() - 1) == 0) {
                    return;
                }
            }

            checkList.add(0);                       // 체크리스트의 배열에 0(연산자) 추가
            txtExpression.append(" " + str + " ");  // append는 setText와 달리 기존내용을 유지한채 뒤에 붙여준다
            System.out.println("리스트 값 : " + checkList);

        } catch (Exception e) {
            Log.e("addOperator", e.toString());
        }
    }

    /**
     * 클리어 버튼 이벤트 처리
     *
     * @param v
     */
    public void clearClick(View v) {
        Define.ins().infixList.clear();
        checkList.clear();
        txtExpression.setText("");
        txtResult.setText("");
        Define.ins().operatorStack.clear();
        Define.ins().postfixList.clear();
    }

    /**
     * 지우기 버튼 이벤트 처리
     *
     * @param v
     */
    public void deleteClick(View v) {
        // 계산 과정 텍스트의 길이가 0이 아닐 때
        if (txtExpression.length() != 0) {
            // 체크리스트의 사이즈 = -1
            checkList.remove(checkList.size() - 1);
            // 계산 과정에 있는 텍스트를 띄어쓰기를 기준으로 나눈다
            String[] ex = txtExpression.getText().toString().split(" ");
            List<String> li = new ArrayList<String>();
            Collections.addAll(li, ex);
            li.remove(li.size() - 1);
            // li사이즈가 0보다 크고 && 리스트의 마지막이 숫자가 아니라면
            if (li.size() > 0 && !cTextInput.isNumber(li.get(li.size() - 1))) {
                // " " 빈칸 추가
                li.add(li.remove(li.size() - 1) + " ");
            }
            // 계산 텍스트에 li 값을 쉼표 표시 없이 변경한다
            txtExpression.setText(TextUtils.join(" ", li));
        }
        // 결과 텍스트에 ""으로 변경한다
        txtResult.setText("");
    }

    /**
     * 이콜 버튼 이벤트 처리
     *
     * @param v
     */
    public void equalClick(View v) {
        // 만약 텍스트뷰가 비어있다면
        if (txtExpression.length() == 0) return;
        // 만약 마지막이 숫자가 아닐 때
        if (checkList.get(checkList.size() - 1) != 1) {
            Toast.makeText(aButtonClick, "숫자를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        Collections.addAll(Define.ins().infixList, txtExpression.getText().toString().split(" "));
        // 체크리스트의 배열에 -1(이콜) 추가
        checkList.add(-1);
        cTextInput.result();
    }
}

