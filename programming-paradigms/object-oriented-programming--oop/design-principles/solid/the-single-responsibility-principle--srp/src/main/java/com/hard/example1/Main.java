package com.hard.example1;

public class Main {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();

        computer.showImage();
        computer.playMusic();
    }
}

/**
 * Bad example
 */

//class Computer {
//    public void showImage() {
//        System.out.println("show image");
//    }
//
//    public void playMusic() {
//        System.out.println("play music");
//    }
//}

/**
 * Good example
 */

class Display {
    public void showImage() {
        System.out.println("show image");
    }
}

class Player {
    public void playMusic() {
        System.out.println("play music");
    }
}

class ComputerFacade {
    private Display display = new Display();
    private Player player = new Player();

    public void showImage() {
        display.showImage();
    }

    public void playMusic() {
        player.playMusic();
    }
}
