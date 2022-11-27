package playground.crypto.mac;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.KeyParameter;
import playground.Helpers;

public class LightweightAPI {
    public static void main(String[] args) {
        BlockCipher engine = new AESEngine();
        CMac mac = new CMac(engine);

        byte[] key = Helpers.hexToBytes("01020304050607081112131415161718");
        System.out.println(Helpers.hexDump("Key", key));

        mac.init(new KeyParameter(key));
        byte[] input = "data to mac".getBytes();
        mac.update(input, 0, input.length);
        byte[] cmac = new byte[mac.getMacSize()];
        mac.doFinal(cmac, 0);

        System.out.println(Helpers.hexDump("CMAC", cmac));
    }
}
