package org.jophiel.utils;
import java.nio.file.Paths;

public class UserServices {

    private static final String usersPath = "storage/users/";

    public static String getUser(String username) {

        if (!Paths.get(usersPath, username).toString().isBlank()){
            return "yup";
        };

        return null;
    }
    
    public static boolean validateUsername(String username) {
        if (username == null || username.isBlank()) return false;

        String regex = 
            "^(?!(?i)(con|prn|aux|nul|com[0-9]|lpt[0-9])$)" + // Reserved windows folder names
            "(?![._-])" + // No leading dot/underscore/hyphen
            "(?!.*[._-]{2})" + // No consecutive dots/underscore/hyphen  
            "?!.*[._-]$)" + // No ending dot/underscore/hyphen
            "[a-z0-9._-]{0,30}$"; // All lowercase letters, numbers, dots, underscores, hyphens allowed
        return username.matches(regex);
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isBlank()) return false;
        
        String regex = 
            "^(?=.*[a-z])" + // Atleast 1 lowercase letter
            "(?=.*[A-Z])" + // Atleast 1 upprcase letter
            "(?=.*\\d)" + // Atleast 1 number
            "(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?])" + // Atleast 1 approved special character 
            "[A-Za-z\\d!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?])]{8,20}$"; // No non-allowed characters

        return password.matches(regex);
    }




}
