package com.hard._06_interruption.my._03;

public class Main {
    public static void main(String[] args) {
        Entity entity = new Entity();

        entity.start();

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        entity.interrupt();

        System.out.println(Thread.currentThread().getName());
    }
}

class Entity extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                if (!this.isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + ": i = " + i);

                    Thread.sleep(1000);
                } else
                    throw new InterruptedException();
            }
        } catch (InterruptedException e) {
            System.out.println("interrupting of " + Thread.currentThread().getName());
            return;
        }
    }
}
