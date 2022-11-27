package playground.crypto.cipher;

import playground.Helpers;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AEAD {
    public static void main(String[] args) throws Exception {
        byte[] key = Helpers.hexToBytes("01020304050607080102030405060708");
        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher;

        cipher = Cipher.getInstance("AES/GCM/NoPadding");

        byte[] nonce = Helpers.hexToBytes("010203040506070809101112");
        GCMParameterSpec gcmps = new GCMParameterSpec(128, nonce);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmps);

        byte[] data = "Hello World!".getBytes();
        byte[] additionalData = "Greetings!".getBytes();

        System.out.println(Helpers.hexDump("Plain", data));

        cipher.update(data);
        // cipher.updateAAD(additionalData);
        byte[] dataEncrypted = cipher.doFinal();

        System.out.println(Helpers.hexDump("Encrypted", dataEncrypted));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmps);

        // cipher.updateAAD(additionalData);
        byte[] dataDecrypted = cipher.doFinal(dataEncrypted);

        System.out.println(Helpers.hexDump("Decrypted", dataDecrypted));
    }
}
