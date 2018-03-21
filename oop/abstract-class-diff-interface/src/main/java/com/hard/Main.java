package com.hard;

public class Main {
    public static void main(String[] args) {

    }
}

/**
 * 1) множественная реализация (C implements I1, I2, ...)
 * 2) может содержать только поля-константы: final int a = 1;
 * 3) может описывать методы: void f();
 * 4) может реализовывать только static и default методы: static void f() {}; default void f() {}
 *
 * Использование:
 * 1) могут быть реализованы классами которые не связаны друг с другом (class AndroidDevice implements Device; class IosDevice implements Device)
 * 2) расширение функциональности каждого класса-наследника ()
 *
 * default modifiers:
 * 1) все поля по-умолчанию public static final
 * 2) все методы по-умолчанию public abstract
 */
interface IEntity {

}

/**
 * 1) отсутствие множественного наследования (C extends A)
 * 2) может содержать поля: private int a;
 * 3) может описывать методы: public abstract void f();
 * 4) может реализовывать методы: public void f() {}
 *
 * Использование:
 * 1) используются только тогда, когда есть тип отношений "is a" (class AndroidMobile extends AndroidDevice; class AndroidTablet extends AndroidDevice)
 * 2) теряется индивидуальность класса-наследника ()
 *
 * default modifiers:
 * 1) все поля по-умолчанию default-package
 * 2) все методы по-умолчанию default-package
 */

abstract class AbstractEntity {

}

/**
 * example class
 */

class Entity extends AbstractEntity implements IEntity {

}
