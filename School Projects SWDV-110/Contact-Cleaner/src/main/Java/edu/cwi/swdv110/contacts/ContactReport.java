
package edu.cwi.swdv110.contacts;

import java.util.List;

public final class ContactReport {

    private static final String ROW = "%-22s %-30s %-15s %-9s%n";

    private ContactReport() {
    }

    public static String buildReport(List<Contact> contacts) {

        StringBuilder report = new StringBuilder();

        int invalidEmails = 0;
        int invalidPhones = 0;

        report.append(String.format(ROW, "NAME", "EMAIL", "PHONE", "USERNAME"));
        report.append("-".repeat(78)).append("\n");

        for (Contact contact : contacts) {

            String name = contact.name();
            String email = contact.email();
            String phone = contact.phone();
            String username = ContactParser.makeUsername(name);

            if (!ContactParser.isValidEmail(email)) {
                invalidEmails++;
                email = "!! " + email;
            }

            if (phone.equals("INVALID")) {
                invalidPhones++;
            }

            report.append(String.format(ROW, name, email, phone, username));
        }

        report.append("-".repeat(78)).append("\n");

        report.append("Total contacts: " + contacts.size()
                + " | Invalid emails: " + invalidEmails
                + " | Invalid phones: " + invalidPhones);

        return report.toString();
    }
}
