package playground.crypto.keystore;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class KeyStoreCreation {
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, OperatorCreationException {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
//        KeyStore keyStore = KeyStore.getInstance("UBER");
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

        char[] password = "changeit".toCharArray();
        String encryptionAlias = "encryption";
        String signatureAlias = "signature";

        keyStore.load(null, password);

        // generate and store AES 128 bit key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();

        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(password);
        keyStore.setEntry(encryptionAlias, secretKeyEntry, protectionParameter);

        // generate RSA keypair
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        // create self-signed certificate
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                new X500Name("CN=Self-Signed"),
                BigInteger.valueOf(new SecureRandom().nextLong()),
                new Date(System.currentTimeMillis()),
                // 2 years
                new Date(System.currentTimeMillis() + 2L * 365 * 24 * 3600 * 1000 - 1000),
                new X500Name("CN=Self-Signed"),
                SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded()));
        certificateBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
        certificateBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
        X509CertificateHolder certificateHolder = certificateBuilder
                .build(new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").build(keyPair.getPrivate()));
        X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC")
                .getCertificate(certificateHolder);

        // store RSA keypair with certificate path
        keyStore.setKeyEntry(signatureAlias, keyPair.getPrivate(), password, new Certificate[]{certificate});

        try (FileOutputStream fos = new FileOutputStream("secured.jceks")) {
            keyStore.store(fos, password);
        }
    }
}
