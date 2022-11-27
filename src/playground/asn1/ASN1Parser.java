package playground.asn1;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.util.encoders.Base64;
import playground.Helpers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ASN1Parser {
    public static void main(String[] args) throws IOException {
        byte[] asn1Bytes = Helpers.readFile("ecdsa.sgn");

        try (ASN1InputStream input = new ASN1InputStream(new ByteArrayInputStream(asn1Bytes))) {
            while (input.available() > 0) {
                // read single primitive object
                ASN1Primitive asn1Object = input.readObject();
                // dump it using verbose mode
                System.out.println(ASN1Dump.dumpAsString(asn1Object, false));
            }
        }
    }
}
