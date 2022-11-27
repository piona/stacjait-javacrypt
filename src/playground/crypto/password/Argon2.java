package playground.crypto.password;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import playground.Helpers;

import java.security.SecureRandom;

public class Argon2 {
    public static void main(String[] args) {
        char[] password = "sample password".toCharArray();

        // time cost (t)
        // should be calculated form memory/parallelism to achieve operation time
        // frontend: 0.5s, backend: 1s, key derivation: 3s
        int iterations = 1;

        // memory cost (m), memory * 1024 bytes
        // frontend: 1024000 (1GB), backend: 4096000 (4GB)
        int memory = 8 * 1024; // 2^13

        // threads (p), 1 core = 2 threads
        // frontend: 4, backend: 8
        int parallelism = 1;

        // RFC 9106 recommendations (2021)
        // RFC 9106: t = 1, m = 2 GiB - 2048 * 1024 (recommended)
        // RFC 9106: t = 3, m = 64 MiB - 64 * 1024 (recommended for memory constrained)

        // low
//        iterations = 2;
//        memory = 64 * 1024; // 2^16
//        parallelism = 1;

        // medium
//        iterations = 2;
//        memory = 256 * 1024; // 2^18
//        parallelism = 4;

        // high
//        iterations = 3;
//        memory = 1024 * 1024; // 2^20
//        parallelism = 4;

        // very high
//        iterations = 3;
//        memory = 4096 * 1024; // 2^20
//        parallelism = 4;

        // stored with the password hash, should be >= 8 bytes
        byte[] salt = new byte[16];
//        new SecureRandom().nextBytes(salt);

        // stored in another location than the password hash, optional
        byte[] pepper = new byte[16];
//        new SecureRandom().nextBytes(pepper);

        // system or context name, optional
        String add = "internal system";

        //  password hash length, min 128 bits (16 bytes) for password hash
        int length = 32;

        System.out.println(Helpers.base64Dump("Pepper", pepper));
        System.out.println("Additional" + System.lineSeparator() + add);
        System.out.println(Helpers.base64Dump("Salt", salt));

        Argon2Parameters argon2Params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // older ARGON2_VERSION_10 with xor
                .withIterations(iterations)
                .withMemoryAsKB(memory)
                .withParallelism(parallelism)
                .withSalt(salt)
                .withSecret(pepper)
                .withAdditional(add.getBytes())
                .build();

        Argon2BytesGenerator argon2 = new Argon2BytesGenerator();
        argon2.init(argon2Params);
        byte[] hash = new byte[length];
        argon2.generateBytes(password, hash, 0, hash.length);

        System.out.println(Helpers.base64Dump("Hashed password/derived key", hash));

        // encoded string with params
        String encodedHash = "$" +
                (argon2Params.getType() == 0 ? "argon2d" : argon2Params.getType() == 1 ? "argon2i" : "argon2id") +
                "$v=" + argon2Params.getVersion() +
                "$m=" + argon2Params.getMemory() +
                ",t=" + argon2Params.getIterations() +
                ",p=" + argon2Params.getLanes() +
                "$" + Helpers.bytesToBase64(salt) +
                "$" + Helpers.bytesToBase64(hash);

        System.out.println("Encoded" + System.lineSeparator() + encodedHash);
    }
}
