package com.example.login_project;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyMethods {

    public static String MY_KEY = "";
    public static String encryptdata(String text) throws Exception{

        String plaintext = text;
        byte[] byteplaintext = plaintext.getBytes("UTF-8");

        String password = "mafi#12345678901";
        byte[] bytepassword = password.getBytes("UTF-8");

        SecretKeySpec secretKeySpec = new SecretKeySpec(bytepassword,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] securebyte = cipher.doFinal(byteplaintext);

        String encodeString = Base64.encodeToString(securebyte,Base64.DEFAULT);
        return encodeString;

    }
}
