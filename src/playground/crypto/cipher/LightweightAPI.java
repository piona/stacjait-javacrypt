package playground.crypto.cipher;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import playground.Helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LightweightAPI {
    public static void main(String[] args) throws InvalidCipherTextException, NoSuchAlgorithmException {
        // direct engine
        BlockCipher engine = new DESEngine();
//        BlockCipher engine = new DESedeEngine();
//        BlockCipher engine = new BlowfishEngine();

        // engine wrappers
        BufferedBlockCipher cipher = new BufferedBlockCipher(engine);
//        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine, new PKCS7Padding());
//        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new PKCS7Padding());

        // engine parameters
        byte[] key = Helpers.hexToBytes("0102030405060708");
        System.out.println(Helpers.hexDump("Key", key));

        cipher.init(true, new KeyParameter(key));
//        byte[] iv = new byte[cipher.getBlockSize()];
//        SecureRandom.getInstanceStrong().nextBytes(iv);
//        cipher.init(true, new ParametersWithIV(new KeyParameter(key), iv));

//        byte[] plain = Helpers.hexToBytes("deadbeefdeadbeef");
        byte[] plain = Helpers.hexToBytes("deadbeefdeadbeefCAFED00DCAFED00D");
        System.out.println(Helpers.hexDump("Plain", plain));

        byte[] encrypted = new byte[cipher.getOutputSize(plain.length)];
        int encryptedLen = cipher.processBytes(plain, 0, plain.length, encrypted, 0);
        cipher.doFinal(encrypted, encryptedLen);
        System.out.println(Helpers.hexDump("Encrypted", encrypted));

        cipher.init(false, new KeyParameter(key));
        // what happens if IV will be different?
//        SecureRandom.getInstanceStrong().nextBytes(iv);
//        cipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));

        byte[] decrypted = new byte[cipher.getOutputSize(encrypted.length)];
        int decryptedLen = cipher.processBytes(encrypted, 0, encrypted.length, decrypted, 0);
        decryptedLen += cipher.doFinal(decrypted, decryptedLen);
        System.out.println(Helpers.hexDump("Decrypted", decrypted, 0, decryptedLen));
    }
}
