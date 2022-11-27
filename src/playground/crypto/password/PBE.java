package playground.crypto.password;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.SecureRandom;
import java.security.Security;

public class PBE {
    public static void main(String[] args) throws Exception {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        char[] password = "sample password".toCharArray();

        // stored with cryptogram or password hash
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        System.out.print(Helpers.hexDump("Salt", salt));

        // PBEWithHmacSHA256: >= 720000 iterations
        // PBEWithHmacSHA256: >= 310000 iterations
        // PBEWithHmacSHA256: >= 120000 iterations
        // recommendation: password length should be <= selected hash length
        int iterations = 310000;

        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA256and256bitAES-CBC-BC");
        SecretKey secretKey = keyFactory.generateSecret(pbeKeySpec);

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, iterations);

        Cipher cipher = Cipher.getInstance("PBEWithSHA256and256bitAES-CBC-BC");

        byte[] data = "Bruce Schneier knows your password before you do.".getBytes();

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
        byte[] dataEncrypted = cipher.doFinal(data);
        System.out.println(Helpers.hexDump("Encrypted", dataEncrypted));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
        byte[] dataDecrypted = cipher.doFinal(dataEncrypted);
        System.out.println(Helpers.hexDump("Decrypted", dataDecrypted));
    }
}
