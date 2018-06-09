package com.hard.my_chat.client;

import com.hard.my_chat.client.views.ConsoleView;
import com.hard.my_chat.client.views.FrameView;
import com.hard.my_chat.client.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class Client {
    private Collection<View> views;

    private InputStream inputStream;
    private OutputStream outputStream;

    private Socket socket;

    private boolean launched;

    public Client() {
        views = new ArrayList<>();

        views.add(new ConsoleView(this));
        views.add(new FrameView(this));

        launched = true;
    }

    public void run() {
        init();
        initStreams();

        while (launched) {

        }

        stop();
    }

    public void init() {
        for (View view : views)
            view.run();

        try {
            socket = new Socket("localhost", 9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initStreams() {
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyAllViews(String str) {
        for (View view : views)
            view.getMessage(str);
    }
}
