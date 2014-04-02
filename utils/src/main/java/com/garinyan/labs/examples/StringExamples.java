package com.garinyan.labs.examples;

public class StringExamples {

    public static void main(String[] args) {
        
        String fileName = "/home/segments";
        
        System.out.println(fileName.lastIndexOf("/"));  //  5
        System.out.println(fileName.length());  //  14
        System.out.println(fileName.substring(fileName.lastIndexOf("/") + 1));  //  segments
        System.out.println(fileName.substring(0, fileName.lastIndexOf("/")));  //  /home

    }

}
