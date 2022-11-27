package playground.crypto.object;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Sealed {
    public static void main(String[] args) throws Exception {
        final String filename = "sealed.dat";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();

        // object
        List<String> messages = Arrays.asList("Hello", "World!");
        System.out.println("Object: " + messages);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serialized.dat"));
        oos.writeObject(messages);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // seal object
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        SealedObject sealed = new SealedObject((Serializable) messages, cipher);
        oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(sealed);

        // unseal object
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        sealed = (SealedObject) ois.readObject();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        List<String> recoveredMessages = (List<String>) sealed.getObject(cipher);

        System.out.println("Recovered: " + recoveredMessages);
    }
}
