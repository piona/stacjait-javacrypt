package playground.crypto.random;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import playground.Helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LightweightAPI {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        DigestRandomGenerator random = new DigestRandomGenerator(new SHA256Digest());

        random.addSeedMaterial(0L);
//        random.addSeedMaterial(SecureRandom.getInstanceStrong().generateSeed(32));

        byte[] buffer = new byte[32];
        random.nextBytes(buffer);
        System.out.println(Helpers.hexDump("Random", buffer));

    }
}
