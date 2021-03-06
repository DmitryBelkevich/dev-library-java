package com.hard._01_string._01_byte_array;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Entity entity = new Entity(1, "Hello World");

        // encode (serialization) from Object to byte[]

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            objectOutputStream.writeObject(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }

                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(byteArrayOutputStream.toString());
        byte[] bytes = byteArrayOutputStream.toByteArray();

        // decode (deserialization) from byte[] to Object

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Entity entity2 = null;
        try {
            entity2 = (Entity) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null)
                    objectInputStream.close();

                if (byteArrayInputStream != null)
                    byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(entity2);
    }
}
