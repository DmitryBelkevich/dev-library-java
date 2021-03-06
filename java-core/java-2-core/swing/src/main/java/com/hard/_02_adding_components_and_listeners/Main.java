package com.hard._02_adding_components_and_listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();

        frame.create();
    }
}

class MainFrame {
    // 2.1 create components (if need access to components from listeners)
    private JButton button1 = new JButton("Button1");

    public void create() {
        /**
         * 1 frame:
         */

        // 1.1 create frame
        JFrame frame = new JFrame("App");

        // 1.2 window settings
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // 1.3 set layout
        frame.setLayout(new GridBagLayout());

        /**
         * 2 components:
         */

        // 2.2 components settings
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));	// add awt-components

        // 2.3 create layout components

        /**
         * 4 add components and listeners:
         */

        // 4.1 add components (may with layout)
        frame.add(button1);

        // 4.2 add Listeners (may use Anonymous class, lambdas)
        button1.addActionListener(new Button1ActionListener());
        frame.addWindowListener(new CloseWindowListener());

        // 1.4 other frame settings
        //frame.pack();
        frame.setVisible(true);
    }

    /**
     * 3 listeners
     */

    // 3.1 create listeners (may use any class: Inner Class, Nested Class)
    private class Button1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("Hello World");
            //event.getSource();	// возвращает объект
        }
    }

    private class CloseWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Window is closing");
        }
    }
}
