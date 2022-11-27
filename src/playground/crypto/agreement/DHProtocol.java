package playground.crypto.agreement;

import org.bouncycastle.jcajce.spec.XDHParameterSpec;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import javax.crypto.KeyAgreement;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.interfaces.XECPrivateKey;
import java.security.interfaces.XECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.NamedParameterSpec;

// Diffie–Hellman key exchange protocol with session generated keys (ephemeral, both sides)
public class DHProtocol {
    public static void main(String[] args) throws Exception {
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DH");
        keyGenerator.initialize(2048);

        // elliptic curves: brainpoolP256r1, secp256r1/prime256v1/P-256, secp384r1/P-384, secp521r1/P-521, brainpoolP256r1
//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DH");
//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("ECDH");
//        keyGenerator.initialize(new ECGenParameterSpec("secp256r1"));

//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("X25519");

//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("XDH");
        // BC style
//        keyGenerator.initialize(new XDHParameterSpec("X25519"));
        // Java style
//        keyGenerator.initialize(new NamedParameterSpec("X25519"));

        KeyPair keyPairA = keyGenerator.generateKeyPair();
        System.out.println(Helpers.hexDump("A encoded private key", keyPairA.getPrivate().getEncoded()));
        Helpers.fileDump("dh.key", keyPairA.getPrivate().getEncoded());
        System.out.println("A private key" + System.lineSeparator() + keyPairA.getPrivate());
        System.out.println(Helpers.hexDump("A encoded public key", keyPairA.getPublic().getEncoded()));
        Helpers.fileDump("dh.pub", keyPairA.getPublic().getEncoded());
        System.out.println("A public key" + System.lineSeparator() + keyPairA.getPublic());

        // use BC API to parse the key pair
        if (keyGenerator.getProvider().getName().equalsIgnoreCase("BC")) {
            if (keyGenerator.getAlgorithm().equalsIgnoreCase("EC")) {
                byte[] privateKeyA = ((ECPrivateKey) keyPairA.getPrivate()).getD().toByteArray();
                // uncompressed key value (true - compressed)
                byte[] publicKeyA = ((ECPublicKey) keyPairA.getPublic()).getQ().getEncoded(false);
                System.out.println(Helpers.hexDump("A EC private key", privateKeyA));
                System.out.println(Helpers.hexDump("A EC public key", publicKeyA));
            } else if (keyGenerator.getAlgorithm().equalsIgnoreCase("XDH")) {
                byte[] privateKeyA = ((XECPrivateKey) keyPairA.getPrivate()).getScalar().orElse(null);
                byte[] publicKeyA = ((XECPublicKey) keyPairA.getPublic()).getU().toByteArray();
                System.out.println(Helpers.hexDump("A X private key", privateKeyA));
                System.out.println(Helpers.hexDump("A X public key", publicKeyA));
            } else {
                System.out.println(keyGenerator.getAlgorithm());
            }
        }

        KeyPair keyPairB = keyGenerator.generateKeyPair();
        System.out.println(Helpers.hexDump("B private key", keyPairB.getPrivate().getEncoded()));
        System.out.println("B public key" + System.lineSeparator() + keyPairB.getPublic());

        final String keyAgreementProtocol = keyGenerator.getAlgorithm();
//        final String keyAgreementProtocol = "DH";
//        final String keyAgreementProtocol = "ECDH";
//        final String keyAgreementProtocol = "XDH";

        // side A key agreement
        KeyAgreement ecdhA = KeyAgreement.getInstance(keyAgreementProtocol);
        ecdhA.init(keyPairA.getPrivate());
        // true for lastPhase
        ecdhA.doPhase(keyPairB.getPublic(), true);
        byte[] secretA = ecdhA.generateSecret();
        System.out.println(Helpers.hexDump("A secret", secretA));

        // side B key agreement
        KeyAgreement ecdhB = KeyAgreement.getInstance(keyAgreementProtocol);
        ecdhB.init(keyPairB.getPrivate());
        ecdhB.doPhase(keyPairA.getPublic(), true);
        byte[] secretB = ecdhB.generateSecret();
        System.out.println(Helpers.hexDump("B secret", secretB));
    }
}
