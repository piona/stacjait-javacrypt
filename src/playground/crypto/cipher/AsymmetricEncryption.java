package playground.crypto.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

public class AsymmetricEncryption {
    public static void main(String[] args) throws Exception {
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        byte[] data = new byte[]{(byte) 0x00, (byte) 0xc0, (byte) 0xff, (byte) 0xee};

        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
//        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
//        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-256AndMGF1Padding");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        Key privateKey = keyPair.getPrivate();
        Key publicKey = keyPair.getPublic();

        System.out.println("Private key" + System.lineSeparator() + privateKey);
        System.out.println("Public key" + System.lineSeparator() + publicKey);

        Helpers.fileDump("rsa.key", privateKey.getEncoded());
        Helpers.fileDump("rsa.pub", publicKey.getEncoded());

        System.out.println(Helpers.hexDump("Modulus", ((RSAPublicKey) publicKey).getModulus().toByteArray()));

        System.out.println(Helpers.hexDump("Plain", data));

        // encryption
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] dataEncrypted = cipher.doFinal(data);

        System.out.println(Helpers.hexDump("Encrypted", dataEncrypted));

//         dataEncrypted[0]++;

//        // textbook attack - shift plaintext 8 bits left (multiply by 256)
//        // 2^e mod n
//        BigInteger multiplicator = BigInteger.valueOf(256);
//        multiplicator = multiplicator.modPow(((RSAPublicKey) publicKey).getPublicExponent(),
//                                             ((RSAPublicKey) publicKey).getModulus());
//        // add landing 0x00 if needed
//        if ((dataEncrypted[0] & 0x80) != 0) {
//            byte[] result = new byte[1 + dataEncrypted.length];
//            System.arraycopy(dataEncrypted, 0, result, 1, dataEncrypted.length);
//            dataEncrypted = result;
//        }
//        BigInteger cryptogram = new BigInteger(dataEncrypted);
//        // C * 2^e mod n
//        cryptogram = cryptogram.multiply(multiplicator).mod(((RSAPublicKey) publicKey).getModulus());
//        dataEncrypted = cryptogram.toByteArray();
//        System.out.println(((RSAPublicKey) publicKey).getModulus().bitLength());
//        // remove landing 0x00 if needed
//        if (dataEncrypted.length * 8 > ((RSAPublicKey) publicKey).getModulus().bitLength()) {
//            dataEncrypted = Arrays.copyOfRange(dataEncrypted, 1, dataEncrypted.length);
//        }

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] dataDecrypted = cipher.doFinal(dataEncrypted);

        System.out.println(Helpers.hexDump("Decrypted", dataDecrypted));
        System.out.println(Helpers.bytesToHex(dataDecrypted));
    }
}
