package org.example;

import org.example.javaClient.JavaClientExample;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        JavaClientExample example = new JavaClientExample();
        try {
            example.useJavaClient();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}