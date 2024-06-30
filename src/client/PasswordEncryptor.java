package client;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordEncryptor {

    public static String encrypt(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashArray = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger hashNumber = new BigInteger(1, hashArray);
            StringBuilder encryptedPassword = new StringBuilder(hashNumber.toString(16));
            encryptedPassword.insert(0, "0".repeat(64 - encryptedPassword.length()));
            return encryptedPassword.toString();
        } catch (Exception e) {
            return "";
        }
    }

}
