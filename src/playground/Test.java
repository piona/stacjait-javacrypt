package playground;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // register provider
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        // generate AES 256-bit key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        System.out.println("Algorithm: " + secretKey.getAlgorithm());
        System.out.println("Format: " + secretKey.getFormat());

        // prepare cipher
        Cipher cipher;
        cipher = Cipher.getInstance("AES/ECB/NoPadding");
        System.out.println("Provider: " +
                cipher.getProvider().getName() + " - " +
                cipher.getProvider().getInfo());

        // encrypt data
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plain = "00000000000000000000000000000000".getBytes();
        byte[] encrypted = cipher.doFinal(plain);

//        System.out.println(Helpers.hexDump(encrypted));

        // decrypt data
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(encrypted);

        // check
        if (Arrays.areEqual(plain, decrypted)) {
            System.out.println("Decryption: SUCCESS");
        } else {
            System.out.println("Decryption: FAIL");
        }
    }
}
