package com.hard._2_example;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        Expression expression = context.evaluate("1+2+3+4-10");
        int result = expression.interpret();

        System.out.println(result);
    }
}

/**
 * Expression
 */

interface Expression {
    int interpret();
}

/**
 * Concrete Expression
 */

class NumberExpression implements Expression {
    private int data;

    public NumberExpression(int data) {
        this.data = data;
    }

    @Override
    public int interpret() {
        return data;
    }
}

class PlusExpression implements Expression {
    private Expression expression1;
    private Expression expression2;

    public PlusExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int interpret() {
        return expression1.interpret() + expression2.interpret();
    }
}

class MinusExpression implements Expression {
    private Expression expression1;
    private Expression expression2;

    public MinusExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int interpret() {
        return expression1.interpret() - expression2.interpret();
    }
}

/**
 * Context
 */

class Context {
    public Expression evaluate(String str) {
        int position = str.length() - 1;

        while (position > 0) {
            if (Character.isDigit(str.charAt(position))) {
                position--;
            } else {
                Expression left = evaluate(str.substring(0, position));
                Expression right = new NumberExpression(Integer.valueOf(str.substring(position + 1, str.length())));

                char operator = str.charAt(position);

                switch (operator) {
                    case '+':
                        return new PlusExpression(left, right);
                    case '-':
                        return new MinusExpression(left, right);
                }
            }
        }

        int result = Integer.valueOf(str);

        return new NumberExpression(result);
    }
}