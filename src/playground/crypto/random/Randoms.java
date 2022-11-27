package playground.crypto.random;

import playground.Helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Randoms {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Random random = new Random();
//        Random random = new Random(0);
//        Random random = new Random(System.currentTimeMillis());
//        Random random = new Random();

        random.setSeed(0);

        byte[] buffer = new byte[32];
        random.nextBytes(buffer);
        System.out.println(Helpers.hexDump("Random", buffer));
    }
}
