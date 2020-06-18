// 
// Decompiled by Procyon v0.5.36
// 

package utilitez;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class SHA
{
    public static String encrypt(String passwordToHash) {
        passwordToHash = "#Void_" + passwordToHash + "_Chat$";
        String generatedPassword = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(passwordToHash.getBytes());
            final byte[] bytes = md.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
