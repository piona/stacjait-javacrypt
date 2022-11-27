package playground.crypto.mac;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class HMAC {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // HmacSHA256, HmacSHA384, HmacSHA512, HmacSHA3-256, Keccak-256
        Mac mac = Mac.getInstance("HmacSHA256");
        System.out.println("Provider: " + mac.getProvider().getName());

        byte[] key = Helpers.hexToBytes("01020304");

        // HmacSHA256
        SecretKeySpec macKey = new SecretKeySpec(key, "Hmac");
        mac.init(macKey);

        byte[] input = "data to mac".getBytes();
        mac.update(input);
//        input = "next part of data".getBytes();
//        md.update(input);
        byte[] hmac = mac.doFinal();

        System.out.println(Helpers.hexDump("Input", input));
        System.out.println(Helpers.hexDump("MAC", hmac));

        // one-shot mac
        System.out.println(Helpers.hexDump("MAC (one-shot)", mac.doFinal(input)));
    }
}
