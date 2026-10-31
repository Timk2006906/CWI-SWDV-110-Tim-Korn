# Semester Planner

This project is a Java program that creates a class schedule.

## Part 5 – Reflection

### 1. Why did Java replace Date and Calendar?

The older Date and Calendar classes could be confusing because Calendar started counting months at 0 instead of 1. Also, Date objects could be changed, which could cause unexpected problems. The newer java.time classes make dates easier to work with.
### 2. When do you use Period and Duration?

Period is used for calendar dates, like months and days. I used it to calculate the length of the semester. Duration is used for hours and minutes. I used it to calculate the total class time.

### 3. Why do Boise and Phoenix have different times in April?

Boise and Phoenix have the same time in January. In April, Boise moves its clocks forward because of daylight saving time, but Phoenix doesn't. This makes Boise one hour ahead.

### 4. Why use LocalDate instead of ZonedDateTime everywhere?

I used LocalDate because I only needed the dates for the class schedule. I didn't need a time zone for every date. ZonedDateTime is useful when showing class times in different time zones.