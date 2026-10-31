package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.Set;

public class App {

static final ZoneId CAMPUS = ZoneId.of("America/Boise");

public static void main(String[] args) {
// Example semester – these dates are for practice, not the official CWI calendar.
Semester sem = new Semester("SWDV-110 – Spring 2027",
LocalDate.of(2027, 1, 11), LocalDate.of(2027, 5, 7),
Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
LocalTime.of(18, 0), LocalTime.of(20, 30));
sem.addHoliday(DateTools.nthWeekday(2027, Month.JANUARY, 3, DayOfWeek.MONDAY)); // MLK Day
sem.addHoliday(DateTools.nthWeekday(2027, Month.FEBRUARY, 3, DayOfWeek.MONDAY)); // Presidents' Day
sem.addBreak(LocalDate.of(2027, 3, 22), LocalDate.of(2027, 3, 26)); // Spring break

// TODO Part 4a: print the name, a line of '=' the same length, then every meeting:
System.out.println(sem.getName());
System.out.println("=".repeat(sem.getName().length()));

int previousWeek = 0;

for (LocalDate date : sem.meetingDates()) {
    int week = sem.weekNumber(date);

    if (week != previousWeek) {
        System.out.println("Week " + week);
        previousWeek = week;
    }

    System.out.println(DateTools.formatShort(date));
}

System.out.println("Days off: " + sem.getDaysOff());
System.out.println("Number of meetings: " + sem.meetingDates().size());
System.out.println("Total class time: " + sem.totalClassTime().toHours()
    + "h " + sem.totalClassTime().toMinutesPart() + "m");

System.out.println("Semester length: " + sem.length().getMonths()
    + " months, " + sem.length().getDays() + " days");


// TODO Part 4c: build a ZonedDateTime for the FIRST meeting at the class start time
// in CAMPUS, and print it for America/Boise, America/Los_Angeles,
// America/New_York and Asia/Tokyo using DateTools.inZone

LocalDate firstMeeting = sem.meetingDates().get(0);

ZonedDateTime firstClass = ZonedDateTime.of(
    firstMeeting,
    sem.getClassStart(),
    CAMPUS
);

System.out.println("Boise: " + DateTools.inZone(firstClass, ZoneId.of("America/Boise")));
System.out.println("Los Angeles: " + DateTools.inZone(firstClass, ZoneId.of("America/Los_Angeles")));
System.out.println("New York: " + DateTools.inZone(firstClass, ZoneId.of("America/New_York")));
System.out.println("Tokyo: " + DateTools.inZone(firstClass, ZoneId.of("Asia/Tokyo")));

// TODO Part 4d: loop – read a date (any of the 3 formats) or 'quit'. Print:
// the next class and its week number, days until the semester ends, and the
// due date of a lab assigned at that class (5 school days, skipping days off).
// If there are no more classes say so. Catch IllegalArgumentException for bad input.
Scanner sc = new Scanner(System.in);
while (true) {
    System.out.print("Enter a date or 'quit': ");
    String input = sc.nextLine();

    if (input.equalsIgnoreCase("quit")) {
        break;
    }

    try {
        LocalDate date = DateTools.parseFlexible(input);
        LocalDate next = sem.nextMeeting(date);

        if (next == null) {
            System.out.println("No more classes.");
        } else {
            System.out.println("Next class: " + DateTools.formatShort(next));
            System.out.println("Week: " + sem.weekNumber(next));

            LocalDate dueDate = DateTools.addSchoolDays(
                next, 5, sem.getDaysOff()
            );
            System.out.println("Lab due: " + DateTools.formatShort(dueDate));
        }

        System.out.println("Days left: " + sem.daysLeft(date));

    } catch (IllegalArgumentException e) {
        System.out.println("Invalid date: " + e.getMessage());
    }
}

sc.close();
}
}
