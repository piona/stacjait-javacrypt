package playground.crypto.mac;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class CMAC {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // AESCMAC
        Mac mac = Mac.getInstance("DESedeCMAC");
        System.out.println("Provider: " + mac.getProvider().getName());

        byte[] key = Helpers.hexToBytes("01020304050607081112131415161718");

        SecretKeySpec macKey = new SecretKeySpec(key, "DESedeCMAC");
        mac.init(macKey);

        byte[] input = "data to mac".getBytes();
        mac.update(input);
        byte[] cmac = mac.doFinal();

        System.out.println(Helpers.hexDump("Input", input));
        System.out.println(Helpers.hexDump("MAC", cmac));

        // one-shot mac
        System.out.println(Helpers.hexDump("MAC (one-shot)", mac.doFinal(input)));
    }
}
