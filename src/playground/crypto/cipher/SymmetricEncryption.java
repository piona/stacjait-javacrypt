package playground.crypto.cipher;

import playground.Helpers;

import javax.crypto.*;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class SymmetricEncryption {
    public static void main(String[] args) throws Exception {
        // -Djava.security.properties==
//        Security.setProperty("crypto.policy", "limited");
//        Security.setProperty("crypto.policy", "unlimited");
//        System.out.println(Security.getProperty("security.overridePropertiesFile"));
//        System.out.println(Security.getProperty("jdk.security.legacyAlgorithms"));

        // DESede, ChaCha20
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        SecretKey secretKey = keyGenerator.generateKey();

//        byte[] key = Helpers.hexToBytes("01020304050607080102030405060708");
//        secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher;

        // Algorithm: AES, DESede, ChaCha20
        // Mode: ECB, CBC, CTR, None
        // Padding: NoPadding, PKCS5Padding
        cipher = Cipher.getInstance("AES/ECB/NoPadding");

//        byte[] iv = Helpers.hexToBytes("0102030405060708");
//        IvParameterSpec ivps = new IvParameterSpec(iv);

//        byte[] iv = new byte[16];
//        new SecureRandom().nextBytes(iv);
//        IvParameterSpec ivps = new IvParameterSpec(iv);

//        byte[] nonce = new byte[12];
//        new SecureRandom().nextBytes(nonce);
//        ChaCha20ParameterSpec nonceps = new ChaCha20ParameterSpec(nonce, 0);

//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivps);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] data = "00000000000000000000000000000000".getBytes();

        System.out.println(Helpers.hexDump("Plain", data));

        byte[] dataEncrypted = cipher.doFinal(data);

        System.out.println(Helpers.hexDump("Encrypted", dataEncrypted));

//        cipher = Cipher.getInstance("ChaCha20/None/NoPadding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivps);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] dataDecrypted = cipher.doFinal(dataEncrypted);

        System.out.println(Helpers.hexDump("Decrypted", dataDecrypted));
    }
}
