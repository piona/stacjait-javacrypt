package playground.crypto.password;

import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.bouncycastle.crypto.generators.SCrypt;
import playground.Helpers;

import java.security.SecureRandom;

public class SCryptGenerate {
    public static void main(String[] args) {
        byte[] password = "sample password".getBytes();

        // CPU/memory cost (n), power of 2
        // should be 2^16 or more
        int N = 1 << 16;

        // block size
        int r = 8;

        // parallelization parameter
        int p = 1;

        // from more RAM, less CPU ...
        // N=2^16 (64 MiB), r=8 (1024 bytes), p=1
        // N=2^15 (32 MiB), r=8 (1024 bytes), p=2
        // N=2^14 (16 MiB), r=8 (1024 bytes), p=4
        // N=2^13 (8 MiB), r=8 (1024 bytes), p=8
        // N=2^12 (4 MiB), r=8 (1024 bytes), p=15
        // ... to less RAM, more CPU

        // derived output length
        int derivedLen = 32;

        // stored with the password hash
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // SCrypt
        byte[] derived = SCrypt.generate(password, salt, N, r, p, derivedLen);
        System.out.print(Helpers.hexDump("Salt", salt));
        System.out.print(Helpers.hexDump("SCrypt derived key", derived));
    }
}
