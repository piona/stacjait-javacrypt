package playground.crypto.password;

import playground.Helpers;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PBKDF2 {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] password = "sample password".toCharArray();

        // stored with cryptogram or password hash
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // PBKDF2WithHmacSHA1: >= 720000 iterations
        // PBKDF2WithHmacSHA256: >= 310000 iterations
        // PBKDF2WithHmacSHA512: >= 120000 iterations
        // recommendation: password length <= selected hash length
        int iterations = 310000;

        // PBKDF2 is FIPS-140 validated implementation
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(password, salt, iterations, 256);
        byte[] secret = factory.generateSecret(keySpec).getEncoded();

        System.out.print(Helpers.hexDump("Salt", salt));
        System.out.print(Helpers.hexDump("Secret", secret));
    }
}
