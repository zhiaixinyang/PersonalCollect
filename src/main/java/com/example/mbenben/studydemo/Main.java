package com.example.mbenben.studydemo;

/**
 * Created by MBENBEN on 2018/10/20.
 */

public class Main {

    public static void main(String[] arg) {
        String str = "";
        boolean b = (str == "");
        String strr = new String("");
        System.out.print(str.equals("") + "!" + (str == "") + "!" + (strr == str));
    }
}
