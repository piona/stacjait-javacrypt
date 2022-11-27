package playground.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

public class ProvidersServices {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        // all providers
        for (Provider provider : Security.getProviders()) {
            System.out.println("Provider: " + provider.getName() + " " + provider.getVersionStr());
            System.out.println("Provider info: " + provider.getInfo());

            // all services
            System.out.println("Services");
            for (Provider.Service service : provider.getServices()) {
                System.out.println("    " + service.getAlgorithm() + " (" + service.getType() + ")");
            }

            // all aliases to services
            System.out.println("Aliases");
            for (Object key : provider.keySet()) {
                final String prefix = "Alg.Alias.";
                if (key.toString().startsWith(prefix)) {
                    String name = provider.get(key.toString()).toString();
                    // split into type and algorithm
                    String[] serviceInfo = key.toString().substring(prefix.length()).split("\\.", 2);
                    System.out.println("    " + serviceInfo[1] + " -> " + name + " (" + serviceInfo[0] + ")");
                }
            }

            System.out.println();
        }
    }
}

