package com.example.nullname.passwordmanager;

import java.util.Random;

public class Randomizer {


    public static String uperRus = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static String lowerRus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static String uperEng = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String lowerEng = "abcdefghijklmnopqrstuvwxyz";
    public static String number = "0123456789";

    public static String getBigString(boolean mRus, boolean mEng, boolean mUper, boolean mLower, boolean mNumber){
        String bigString = "";

        if (mRus&mUper == true)
            bigString += uperRus;
        if (mRus&mLower == true)
            bigString += lowerRus;
        if (mEng&mUper == true)
            bigString += uperEng;
        if (mEng&mLower == true)
            bigString += lowerEng;
        if (mNumber == true)
            bigString += number;

        return bigString;
    }

    public static String randomize(int amount, String s){
        String password = "";
        int k = 0;
        Random rand = new Random();

        if (s.length() != 0){
            for (int i = 0; i < amount; i ++){
                k = rand.nextInt(s.length());
                password += s.charAt(k);
            }
        }
        else
            password = "";
        return password;

    }
}
