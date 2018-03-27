package com.hard;

public class Main {
    public static void main(String[] args) {
//        String str = C.str1;

//        static-init-vars 1
//        static-init-block 1
//        static-init-vars 2
//        static-init-block 2
//        static-init-vars 3
//        static-init-block 3

        new C();

//        static-init-vars 1
//        static-init-block 1
//        static-init-vars 2
//        static-init-block 2
//        static-init-vars 3
//        static-init-block 3

//        init-vars 1
//        init-block 1
//        Ctor 1
//        init-vars 2
//        init-block 2
//        Ctor 2
//        init-vars 3
//        init-block 3
//        Ctor 3
    }
}

class A {
    static {}

    {}

    public A() {}
}

class B extends A {
    static {}

    {}

    public B() {}
}

class C extends B {
    /**
     * 1)
     * статические переменне
     * статические блоки инициализации
     */
    // 1) выполняется инициализация статических переменных из 1 родительского класса
    // 2) выполняются статические блоки инициализации из 1 родительского класса

    // 1) выполняется инициализация статических переменных из 2 родительского класса
    // 2) выполняются статические блоки инициализации из 2 родительского класса

    // 1) выполняется инициализация статических переменных данного класса
    public static String str1 = "Hello World";

    // 2) выполняются статические блоки инициализации данного класса
    static {}

    /**
     * 2)
     * нестатические переменные
     * анонимные блоки инициализации
     * конструкторы
     */

    // 1) выполняется инициализация нестатических переменных из 1 родительского класса
    // 2) выполняются анонимные блоки инициализации из 1 родительского класса
    // 3) выполняется конструктор из 1 родительского класса

    // 1) выполняется инициализация нестатических переменных из 2 родительского класса
    // 2) выполняются анонимные блоки инициализации из 2 родительского класса
    // 3) выполняется конструктор из 2 родительского класса

    // 1) выполняется инициализация нестатических переменных данного класса
    private String str2 = "Hello World";

    // 2) выполняются анонимные блоки инициализации данного класса
    {}

    // 3) выполняется конструктор данного класса
    public C() {}
}
