package playground.crypto.cipher;

import playground.Helpers;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;

public class TransportKey {
    public static void main(String[] args) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecretKey transportKey = generator.generateKey();

        SecretKey secretKey = generator.generateKey();
        System.out.println(Helpers.hexDump("Secret key", secretKey.getEncoded()));

        // Cipher cipher = Cipher.getInstance("AESWrap/ECB/NoPadding");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

        cipher.init(Cipher.WRAP_MODE, transportKey);

        byte[] wrappedKey = cipher.wrap(secretKey);
        System.out.println(Helpers.hexDump("Wrapped key", wrappedKey));
        // wrappedKey[0]++;

        cipher.init(Cipher.UNWRAP_MODE, transportKey);
        Key recovered = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

        System.out.println(Helpers.hexDump("Recovered key", recovered.getEncoded()));
    }
}
