package playground.crypto.object;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.Arrays;
import java.util.List;

public class Signed {
    public static void main(String[] args) throws Exception {
        final String filename = "signed.dat";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        // sign serializable object
        List<String> messages = Arrays.asList("Hello", "World!");
        System.out.println("Object: " + messages);

        Signature signature = Signature.getInstance("SHA256withRSA");
        SignedObject signed = new SignedObject((Serializable) messages, keyPair.getPrivate(), signature);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(signed);

        // verify the signed object
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        signed = (SignedObject) ois.readObject();
        boolean verified = signed.verify(keyPair.getPublic(), signature);

        System.out.println("Verification: " + verified);

        // retrieve the object
        List<String> recoveredMessages = (List<String>) signed.getObject();
        System.out.println("Recovered: " + recoveredMessages);
    }
}
