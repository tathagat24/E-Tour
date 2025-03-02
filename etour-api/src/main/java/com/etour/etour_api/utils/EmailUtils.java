package com.etour.etour_api.utils;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

public class EmailUtils {

    public static String getNewAccountEmailMessage(String name, String host, String key) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getNewAccountVerificationUrl(host, key) + "\n\nThe Support Team.";
    }

    public static String getResetPasswordEmailMessage(String name, String host, String key) {
        return "Hello " + name + ",\n\nPlease use this link below to reset your password.\n\n" +
                getResetPasswordVerificationUrl(host, key) + "\n\nThe Support Team.";
    }

    public static String getNewAccountVerificationUrl(String host, String key) {
        return host + "/verify/account?key=" + key;
    }

    public static String getResetPasswordVerificationUrl(String host, String key) {
        return host + "/verify/password?key=" + key;
    }
}
