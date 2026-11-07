# Listening History Analyzer

## Week 4 Coding Exercise

This project reads music listening history from a CSV file and uses Java streams and lambdas to organize the data. It can show listening time, popular artists, genres, and summaries for different users.

## Part 5 – Reflection

### 1. Pick one method in MusicStats and rewrite it with a for loop. Which version is easier to read? Which is easier to change?

I picked the `totalMinutes` method.

Here is how I could write it using a for loop:

```java
public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
    int totalSeconds = 0;

    for (Play play : plays) {
        if (filter.test(play)) {
            totalSeconds += play.seconds();
        }
    }

    return totalSeconds / 60;
}
```

I think the for loop is easier to understand because I can see each step. The stream version is shorter and would probably be easier to change once I get more comfortable using streams.

### 2. Why might a team create its own functional interface instead of using Function<Play, String>?

A team might create its own interface because it makes the code easier to understand. `PlayFormatter` tells us exactly what the interface is supposed to do. It also makes the code more organized.

### 3. Why does PlayCleaner.clean need an ArrayList instead of List.of() or stream().toList()?

It needs an ArrayList because the cleaner removes and replaces items in the list. Lists made with `List.of()` or `stream().toList()` cannot be changed like that.

### 4. Name three terminal operations you used.

Three terminal operations I used are:

- `collect()` – Collects the results of a stream.
- `count()` – Counts how many items are in a stream.
- `toList()` – Turns the stream results into a list.

These are terminal operations because they finish the stream.