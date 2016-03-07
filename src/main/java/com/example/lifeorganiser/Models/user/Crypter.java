package com.example.lifeorganiser.src.Models.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypter {

    private static final String ALGORITHM = "MD5";
    private static Crypter instance;

    private MessageDigest messageDigest;

    private Crypter(){
        try {
            this.messageDigest = MessageDigest.getInstance(Crypter.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static Crypter getInstance(){
        if (Crypter.instance == null){
            Crypter.instance = new Crypter();
        }

        return Crypter.instance;
    }

    public String encrypt(String input){
        this.messageDigest.update(input.getBytes());
        byte[] hash = this.messageDigest.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }
}
