package playground.crypto.random;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class SecureRandoms {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        SecureRandom random = new SecureRandom();
        // BouncyCastle: DEFAULT
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        SecureRandom random = SecureRandom.getInstance("DRBG", DrbgParameters.instantiation(
//                256, DrbgParameters.Capability.PR_AND_RESEED, "this is client side".getBytes()));
//        SecureRandom random = SecureRandom.getInstance("NativePRNG");
//        SecureRandom random = SecureRandom.getInstanceStrong();

        System.out.println("Provider: " + random.getProvider().getName());
        System.out.println("Algorithm: " + random.getAlgorithm());

        random.setSeed("sample seed".getBytes());

        byte[] buffer = new byte[32];
        random.nextBytes(buffer);
        System.out.println(Helpers.hexDump("Random", buffer));
        System.out.println(Helpers.hexDump("Seed", random.generateSeed(32)));
    }
}
