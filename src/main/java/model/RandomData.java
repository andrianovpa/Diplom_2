package model;

import java.util.Random;
import java.util.UUID;

public class RandomData {
    private static final String[] NAMES = {
            "Pavel", "Semen", "Cris", "Irina", "Uriy", "Иван", "Василий", "Павел", "Ибрагим", "Jack"
    };

    private static final Random random = new Random();

    public static String randomName() {
        return NAMES[random.nextInt(NAMES.length)];
    }

    public static String randomEmail() {
        String username = UUID.randomUUID().toString().substring(0, 8); // генерируем случайную строку
        String domain = "@test.com";
        return "user_" + username + domain;
    }

    public static String randomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
    public static String randomHash(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
