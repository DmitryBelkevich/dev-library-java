package com.hard._01_interface_abstract_class;

public class Main {
    public static void main(String[] args) {
        I i = new C1() {
            @Override
            public String getStr() {
                return super.getStr() + " (override)";
            }
        };

        i.getStr();
        System.out.println(i.getStr());
    }
}

interface I {
    String getStr();
}

class C1 implements I {
    @Override
    public String getStr() {
        return "Hello World";
    }
}
