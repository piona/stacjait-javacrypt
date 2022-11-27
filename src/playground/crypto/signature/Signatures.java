package playground.crypto.signature;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Signatures {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("EC");
        keyGenerator.initialize(new ECGenParameterSpec("secp256r1"), SecureRandom.getInstanceStrong());

        KeyPair keyPair = keyGenerator.generateKeyPair();
        Signature signature = Signature.getInstance("SHA256withECDSA");

        System.out.println("Provider: " + signature.getProvider().getName());
        System.out.println("Algorithm: " + signature.getAlgorithm());

        signature.initSign(keyPair.getPrivate());

        byte[] message = "sample".getBytes();
        signature.update(message);

        byte[] signatureBytes = signature.sign();
//        signatureBytes[0]++;
//        signatureBytes[8]++;
        System.out.println(Helpers.hexDump("Signature", signatureBytes));
        Helpers.fileDump("ecdsa.sgn", signatureBytes);

        signature.initVerify(keyPair.getPublic());
        signature.update(message);
        System.out.println("Verification: " + signature.verify(signatureBytes));
    }
}
