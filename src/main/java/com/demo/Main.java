package com.demo;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please provide the input for calculations:");
        String inputString = sc.nextLine();

        try {
            int result = calculator(inputString);
            System.out.println("Result = " + result);
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    private static int calculator(String inputString) {
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);

            if (Character.isDigit(ch)) {
                i = handleNumbers(inputString, i, numbers);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    performCalculation(numbers, operators);
                }
                operators.pop();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    performCalculation(numbers, operators);
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            performCalculation(numbers, operators);
        }

        return numbers.pop();
    }

    private static int handleNumbers(String expression, int currentIndex, Stack<Integer> numbers) {
        int startIndex = currentIndex;
        while (startIndex < expression.length() && Character.isDigit(expression.charAt(startIndex))) {
            startIndex++;
        }

        int num = Integer.parseInt(expression.substring(currentIndex, startIndex));
        numbers.push(num);
        return startIndex - 1;
    }

    private static void performCalculation(Stack<Integer> numbers, Stack<Character> operators) {
        char operator = operators.pop();
        int b = numbers.pop();
        int a = numbers.pop();
        int result = 0;

        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
        }

        numbers.push(result);
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}
