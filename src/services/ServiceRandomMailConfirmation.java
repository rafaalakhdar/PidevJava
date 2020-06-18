// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.util.Random;

public class ServiceRandomMailConfirmation
{
    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 10;
    
    public String generateRandomString() {
        final StringBuffer randStr = new StringBuffer();
        for (int i = 0; i < 10; ++i) {
            final int number = this.getRandomNumber();
            final char ch = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
    
    private int getRandomNumber() {
        int randomInt = 0;
        final Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".length());
        if (randomInt - 1 == -1) {
            return randomInt;
        }
        return randomInt - 1;
    }
}
