package playground.crypto.password;

import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import playground.Helpers;

import java.security.SecureRandom;

public class BCryptGenerate {
    public static void main(String[] args) {
        // limited up to 72 bytes, password shouldn't be hashed before
        char[] password = "sample password".toCharArray();

        // cost (2^cost)
        // should be 10 or more
        int cost = 13;

        // stored with the password hash, should be 16 bytes
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // BCrypt
        byte[] passwordBytes = BCrypt.passwordToByteArray(password);
        System.out.print(Helpers.hexDump("BCrypt.passwordToByteArray", passwordBytes));
        byte[] hash = BCrypt.generate(passwordBytes, salt, cost);
        System.out.print(Helpers.hexDump("BCrypt hashed password", hash));
        System.out.println("Encoded salt" + System.lineSeparator() + Helpers.bytesToBase64(salt));
        System.out.println("Encoded password" + System.lineSeparator() + Helpers.bytesToBase64(hash));

        // optional version as first parameter:
        // 2a - original
        // 2x - incorrect PHP 2a version, 2y - fixed PHP version - 8bit character handling in crypt_blowfish (June 2011)
        // 2b - fixed OpenBSD 2a version - incorrect password length (February 2014)
        // all correct implemented versions have the same output and strength
        String openBSDHash = OpenBSDBCrypt.generate(password, salt, cost);

        System.out.println("OpenBSDBCrypt encoded password" + System.lineSeparator() + openBSDHash);

        System.out.println("OpenBSDBCrypt.checkPassword: " + OpenBSDBCrypt.checkPassword(openBSDHash, password));
    }
}
