package com.example.kalculator;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TextInput {
   TextView txtResult;

    AppCompatActivity aTextInput;
    
    public TextInput(AppCompatActivity appCompatActivity){
    aTextInput = appCompatActivity;
        txtResult = aTextInput.findViewById(R.id.txt_result);
    }

    /**
     * 계산
      * @param num1
     * @param num2
     * @param op
     * @return
     */
    String calculate(String num1, String num2, String op) {
        // 첫번째 숫자
        double first = Double.parseDouble(num1);
        // 두번째 숫자
        double second = Double.parseDouble(num2);
        // 결과 값
        double result = 0.0;
        try {
            switch (op) {   // 계산
                case "X":
                    result = first * second;
                    break;
                case "/":
                    result = first / second;
                    break;
                case "%":
                    result = first % second;
                    break;
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;

            }
        } catch (Exception e) {
            Toast.makeText(aTextInput, "연산할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(result);  // 결과 값 반환
    }

    /**
     * 최종 결과
     */
    public void result() {
        int i = 0;
        // 중위 연산 -> 후위 연산
        infixToPostfix();
        // 후위 연산 리스트 사이즈가 1이 아닐 때
        while (Define.ins().postfixList.size() != 1) {
            // 후위 연산 리스트 i번째가 숫자가 아닐 때
            if (!isNumber(Define.ins().postfixList.get(i))) {
                // i-2번째의 리스트 아이템을 삭제(삭제될시 i번째 이후의 아이템 포지션이 당겨진다)하며 인자값으로 전달하여 calculate를 불러온다
                Define.ins().postfixList.add(i - 2, calculate(Define.ins().postfixList.remove(i - 2), Define.ins().postfixList.remove(i - 2), Define.ins().postfixList.remove(i - 2)));
                // 처음부터 다시 찾기 위해 i를 초기화해준다
                i = -1;
            }
            i++;
        }
        // 최종 결과값을 텍스트에 지정
        txtResult.setText(Define.ins().postfixList.remove(0));
        // 중위 연산 리스트 클리어
        Define.ins().infixList.clear();
    }

    /**
     * 숫자 판별
     * @param str
     * @return
     */
    boolean isNumber(String str) {
        boolean result = true;
        try {
            Double.parseDouble(str);        // 문자열을 실수로 변환
        } catch (NumberFormatException e) {
            result = false;                 // 에러가 날 경우 false를 반환한다
        }
        return result;                      // 에러가 나지 않을 경우 true를 반환한다.
    }

    /**
     * 중위 -> 후위
     */
    void infixToPostfix() {
        // item에 infixList의 리스트값을 넣어준다
        // infixList의 리스트 개수만큼 for문 반복
        for (String item : Define.ins().infixList) {
            // item 값이 숫자라면 후위연산 리스트에 item값 삽입
            if (isNumber(item)) Define.ins().postfixList.add(String.valueOf(Double.parseDouble(item)));
            else {  // item 값이 연산자라면
                if (Define.ins().operatorStack.isEmpty())
                    Define.ins().operatorStack.push(item);  // 만약 연산자 스택이 비어있다면 item 값을 operator스택에 삽입
                else {  // 만약 연산자 스택이 비어있지 않다면
                    // 연산자 스택의 맨 위에 저장된 객체가 아이템보다 우선순위가 높다면 해당 객체를 후위연산 리스트에 삽입한다.
                    if (getWeight(Define.ins().operatorStack.peek()) >= getWeight(item))
                        Define.ins().postfixList.add(Define.ins().operatorStack.pop());
                    Define.ins().operatorStack.push(item);
                }
            }
        }
        // 연산자 스택이 비어있지 않을때 연산자 스택값을 빼서 후위연산 리스트에 추가
        while (!Define.ins().operatorStack.isEmpty()) Define.ins().postfixList.add(Define.ins().operatorStack.pop());
    }



    /**
     * 연산자 가중치 (우선순위 *,/,%,+,-)
     * 연산자의 우선순위에 따라 가중치를 부여한다
     * @param operator
     * @return
     */
    int getWeight(String operator) {
        int weight = 0;
        switch (operator) {
            case "X":
            case "/":
                weight = 5;
                break;
            case "%":
                weight = 3;
                break;
            case "+":
            case "-":
                weight = 1;
                break;
        }
        return weight;
    }
}
