package playground.auth;

import java.security.AccessControlException;
import java.security.Guard;
import java.security.GuardedObject;
import java.util.PropertyPermission;

public class ObjectGuard {
    public static void main(String[] args) {
        String password = "secret password";

        // guard for object
        Guard guard = new PropertyPermission("java.version", "read");
//        Guard guard = new PropertyPermission("user.home", "read");

        // embed object into guard
        GuardedObject guardedObject = new GuardedObject(password, guard);

//        System.setProperty("java.security.policy", "res/auth/sample.policy");
        System.setSecurityManager(new SecurityManager());

        try {
            // try to use guarded object
            password = (String) guardedObject.getObject();
            System.out.println("Protected object is " + password);
        } catch (AccessControlException e) {
            System.out.println("Permission is denied");
        }
    }
}
