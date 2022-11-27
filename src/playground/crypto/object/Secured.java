package playground.crypto.object;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

// Keystore initialization:
// keytool -genseckey -keystore secured.jceks -storetype jceks -storepass changeit -keyalg AES -keysize 128 -alias encryption
// keytool -genkeypair -keystore secured.jceks -storetype jceks -storepass changeit -keyalg RSA -keysize 2048 -alias signature

// Sign then seal or seal then sign?
// https://wiki.sei.cmu.edu/confluence/display/java/SER02-J.+Sign+then+seal+objects+before+sending+them+outside+a+trust+boundary
public class Secured {
    public static void main(String[] args) throws IOException, ClassNotFoundException, CertificateException,
            NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, SignatureException {
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        char[] password = "changeit".toCharArray();
        String encryptionAlias = "encryption";
        String signatureAlias = "signature";

        // load keystore
        InputStream keystoreStream = new FileInputStream("secured.jceks");
        KeyStore keystore = KeyStore.getInstance("JCEKS");
//        KeyStore keystore = KeyStore.getInstance("UBER");
        keystore.load(keystoreStream, "changeit".toCharArray());

        if (!keystore.containsAlias(encryptionAlias) || !keystore.containsAlias(signatureAlias)) {
            throw new RuntimeException("Key not found!");
        }

        Key encryptionKey = keystore.getKey("encryption", password);
        Key signatureKey = keystore.getKey("signature", password);

        final String filename = "secured.dat";

        List<String> messages = Arrays.asList("Hello", "World!");
        System.out.println("Object: " + messages);

        // seal and sign
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        SealedObject sealed = new SealedObject((Serializable) messages, cipher);

        Signature signature = Signature.getInstance("SHA256withRSA");
        SignedObject signed = new SignedObject(sealed, (PrivateKey) signatureKey, signature);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(signed);

        // check signature and unseal
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        signed = (SignedObject) ois.readObject();
        Certificate signatureCertificate = keystore.getCertificate(signatureAlias);
        System.out.println(signed.verify(signatureCertificate.getPublicKey(), signature));

        sealed = (SealedObject) signed.getObject();
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey);

        List<String> recoveredMessages = (List<String>) sealed.getObject(cipher);
        System.out.println("Recovered: " + recoveredMessages);
    }
}
