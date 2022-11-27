package playground.crypto.digest;

import org.bouncycastle.crypto.digests.SHA256Digest;
import playground.Helpers;

import java.security.NoSuchAlgorithmException;

public class LightweightAPI {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        SHA256Digest md = new SHA256Digest();

        byte[] input = "data to digest".getBytes();
        md.update(input, 0, input.length);
//        input = "next part of data".getBytes();
//        md.update(input, 0, input.length);
        byte[] hash = new byte[md.getDigestSize()];
        md.doFinal(hash, 0);

        System.out.println(Helpers.hexDump("Input", input));
        System.out.println(Helpers.hexDump("Hash", hash));
    }
}
