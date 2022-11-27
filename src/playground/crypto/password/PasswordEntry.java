package playground.crypto.password;

import java.io.Console;
import java.util.Arrays;

public class PasswordEntry {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console is null!");
            System.exit(0);
        }

        System.out.print("Enter password: ");
        char[] password = console.readLine().toCharArray();
//        char[] password = console.readPassword("Enter password: ");

        // password processing
        System.out.println("Password length: " + password.length);

        Arrays.fill(password, ' ');
    }
}
