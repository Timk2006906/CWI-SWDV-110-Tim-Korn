
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

public class Date_time {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("M/d/uuuu h:mm a", Locale.US)
                .withResolverStyle(ResolverStyle.STRICT);

        ZoneId boiseZone = ZoneId.of("America/Boise");

        while (true) {
            System.out.print("Enter a meeting date and time (or 'quit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                LocalDateTime meeting = LocalDateTime.parse(input, formatter);

                ZonedDateTime boise = meeting.atZone(boiseZone);
                ZonedDateTime newYork = boise.withZoneSameInstant(
                        ZoneId.of("America/New_York"));
                ZonedDateTime london = boise.withZoneSameInstant(
                        ZoneId.of("Europe/London"));
                ZonedDateTime tokyo = boise.withZoneSameInstant(
                        ZoneId.of("Asia/Tokyo"));

                ZonedDateTime[] cities = {boise, newYork, london, tokyo};
                String[] names = {"Boise", "New York", "London", "Tokyo"};

                System.out.println("\nMeeting times:");

                for (int i = 0; i < cities.length; i++) {
                    LocalTime time = cities[i].toLocalTime();

                    System.out.print(names[i] + ": " +
                            cities[i].format(formatter));

                    if (time.isBefore(LocalTime.of(9, 0)) ||
                            !time.isBefore(LocalTime.of(17, 0))) {
                        System.out.println(" - Outside working hours!");
                    } else {
                        System.out.println(" - Within working hours.");
                    }
                }

                Duration countdown = Duration.between(
                        ZonedDateTime.now(boiseZone), boise);

                long minutes = countdown.toMinutes();

                if (minutes < 0) {
                    System.out.println("This meeting has already passed.");
                } else {
                    System.out.println("\nCountdown: " +
                            (minutes / 1440) + " days, " +
                            ((minutes % 1440) / 60) + " hours, " +
                            (minutes % 60) + " minutes");
                }

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time. Try again.");
            }

            System.out.println();
        }

        scanner.close();
    }
}
