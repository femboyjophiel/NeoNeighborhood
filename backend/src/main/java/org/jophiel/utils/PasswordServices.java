package org.jophiel.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordServices {
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.genSalt(10)); // Lower cost factor, server will be on slow hardware
    }

    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
