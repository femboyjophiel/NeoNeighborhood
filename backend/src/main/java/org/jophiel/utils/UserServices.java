package org.jophiel.utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserServices {

    private static final String usersPath = "backend/src/main/java/org/jophiel/storage/users";

    public static String getUser(String username) {
        if (Files.exists(Paths.get(usersPath, username))) {
            return "yup";
        }
        return "nope";
    }
    
    public static boolean validateUsername(String username) {
        if (username == null || username.isBlank()) return false;
        String regex = 
            "^(?!(?i)(con|prn|aux|nul|com[0-9]|lpt[0-9])$)" + // Reserved windows folder names
            "(?![._-])" +                                      // No leading dot/underscore/hyphen
            "(?!.*[._-]{2})" +                                 // No consecutive dots/underscore/hyphen  
            "(?!.*[._-]$)" +                                   // No ending dot/underscore/hyphen
            "[a-z0-9._-]{1,30}$";                              // All lowercase letters, numbers, dots, underscores, hyphens allowed
        return username.matches(regex);
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isBlank()) return false;


        String regex = 
            "^(?=.*[a-z])" +                              // at least 1 lowercase
            "(?=.*[A-Z])" +                               // at least 1 uppercase
            "(?=.*\\d)" +                                 // at least 1 digit
            "(?=.*[!@#$%^&*()_+={}\\[\\];':\"\\\\|,.<>/?\\-])" +  // at least 1 special char
            "[A-Za-z\\d!@#$%^&*()_+={}\\[\\];':\"\\\\|,.<>/?\\-]{8,20}$";

        return password.matches(regex);
    }

    public static void createUser(String username, String password) throws Exception {
        // Create user folder
        Path userPath = Paths.get(usersPath, username);
        Files.createDirectories(userPath);

        // Store password in a file inside the folder
        Path passwordFile = userPath.resolve("password.txt");
        Files.writeString(passwordFile, PasswordServices.hashPassword(password));
    }


}
