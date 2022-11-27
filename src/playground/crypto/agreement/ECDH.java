package playground.crypto.agreement;

import playground.Helpers;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

// Diffie–Hellman key exchange protocol with static keys (single side)
public class ECDH {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException {
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
        parameters.init(new ECGenParameterSpec("secp256r1"));
        ECParameterSpec ecParameterSpec = parameters.getParameterSpec(ECParameterSpec.class);

        // create A private key from scratch
        BigInteger privateKeyValue = new BigInteger("c2fae92f4d389764d358013c39be5c49edefe30f61d572dd00e37f886c25bb8e", 16);
        ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(privateKeyValue, ecParameterSpec);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        // create B public key from scratch
        ECPoint publicKeyValue = new ECPoint(
                new BigInteger("81d4ef9de495137bac4353c42fc115e4fd2e3294c6562ab00d16ae723411178f", 16),
                new BigInteger("8defb567a5fe1d3d683260c0453193f8ae74417742bac69af187afc957d0ec32", 16));
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(publicKeyValue, ecParameterSpec);

        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        // key agreement
        KeyAgreement ecdh = KeyAgreement.getInstance("ECDH");
        ecdh.init(privateKey);

        // true for lastPhase
        ecdh.doPhase(publicKey, true);
        byte[] secret = ecdh.generateSecret();
        System.out.println(Helpers.hexDump("Secret", secret));
    }
}
