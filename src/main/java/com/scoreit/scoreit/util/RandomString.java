package com.scoreit.scoreit.util;

import java.security.SecureRandom;

public class RandomString {
    private static final String CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandonString(int length){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i =0; i < length; i++){
            int index = secureRandom.nextInt(CHARACTER.length());
            sb.append(CHARACTER.charAt(index));
        }
        return sb.toString();
    }
}
