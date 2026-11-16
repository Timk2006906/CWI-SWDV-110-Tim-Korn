package edu.cwi.swdv110.contacts;

public final class ContactParser {

    private ContactParser() { }

    // Part 1: Names
    public static String titleCase(String word) {
        if (word.isEmpty()) {
            return "";
        }

        String lower = word.toLowerCase();
        StringBuilder builder = new StringBuilder(lower);

        builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));

        for (int i = 1; i < builder.length(); i++) {
            if (builder.charAt(i - 1) == '-') {
                builder.setCharAt(i, Character.toUpperCase(builder.charAt(i)));
            }
        }

        return builder.toString();
    }

    public static String normalizeName(String raw) {
        String name = raw.trim();

        if (name.isEmpty()) {
            return "";
        }

        if (name.contains(",")) {
            int comma = name.indexOf(',');
            String last = name.substring(0, comma).trim();
            String first = name.substring(comma + 1).trim();
            name = first + " " + last;
        }

        String[] words = name.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            words[i] = titleCase(words[i]);
        }

        return String.join(" ", words);
    }

    // Part 2: Emails
    public static boolean isValidEmail(String email) {
        email = email.trim();

        if (email.contains(" ")) {
            return false;
        }

        int atPosition = email.indexOf('@');

        if (atPosition <= 0) {
            return false;
        }

        if (atPosition != email.lastIndexOf('@')) {
            return false;
        }

        String domain = email.substring(atPosition + 1);

        if (domain.isEmpty() || domain.startsWith(".") || domain.endsWith(".")) {
            return false;
        }

        if (domain.contains("..")) {
            return false;
        }

        if (!domain.contains(".")) {
            return false;
        }

        return true;
    }

    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    public static String maskEmail(String email) {
        int atPosition = email.indexOf('@');

        if (atPosition < 2) {
            return email;
        }

        return email.charAt(0)
                + "*".repeat(atPosition - 1)
                + email.substring(atPosition);
    }

    // Part 3: Phone numbers
    public static String formatPhone(String raw) {
        StringBuilder digits = new StringBuilder();

        for (char c : raw.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }

        if (digits.length() == 11 && digits.charAt(0) == '1') {
            digits.deleteCharAt(0);
        }

        if (digits.length() != 10) {
            return "INVALID";
        }

        String phone = digits.toString();

        return String.format("(%s) %s-%s",
                phone.substring(0, 3),
                phone.substring(3, 6),
                phone.substring(6));
    }

    // Part 4: Usernames
    public static String makeUsername(String fullName) {
        String name = fullName.trim();

        if (name.isEmpty()) {
            return "";
        }

        String[] words = name.split("\\s+");
        String username;

        if (words.length == 1) {
            username = words[0];
        } else {
            username = words[0].substring(0, 1)
                    + words[words.length - 1];
        }

        StringBuilder letters = new StringBuilder();

        for (char c : username.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                letters.append(c);
            }
        }

        if (letters.length() > 8) {
            return letters.substring(0, 8);
        }

        return letters.toString();
    }

    // Part 4: Parsing contact lines
    public static Contact parseLine(String line) {
        String[] fields = line.split("\\|", -1);

        if (fields.length != 3) {
            throw new IllegalArgumentException(
                    "expected 3 fields but found " + fields.length);
        }

        String name = normalizeName(fields[0]);

        if (name.isEmpty()) {
            throw new IllegalArgumentException("name is blank");
        }

        String email = normalizeEmail(fields[1]);
        String phone = formatPhone(fields[2]);

        return new Contact(name, email, phone);
    }
}