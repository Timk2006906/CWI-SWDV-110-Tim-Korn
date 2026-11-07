
package edu.cwi.swdv110.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {

        List<Play> plays = Files.lines(Path.of("plays.csv"))
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(Play::parse)
                .collect(Collectors.toCollection(ArrayList::new));

     
        int removed = PlayCleaner.clean(plays);
        System.out.println("Loaded " + plays.size()
                + " plays (" + removed + " bad rows removed)");

       
        PlayFormatter compact = play ->
                play.song() + " – " + play.artist();

        PlayFormatter detailed = play -> String.format(
                "%s  %-7s %-17s %-18s %d:%02d",
                play.date(),
                play.user(),
                play.song(),
                play.artist(),
                play.seconds() / 60,
                play.seconds() % 60
        );

      
        System.out.println("\nMinutes per user:");
        MusicStats.secondsPerUser(plays).forEach((user, seconds) ->
                System.out.printf("  %-8s %d min%n", user, seconds / 60)
        );

       
        System.out.println("\nPlays per genre:");
        MusicStats.playsPerGenre(plays).forEach((genre, count) ->
                System.out.printf("  %-12s %s%n", genre, "#".repeat(count.intValue()))
        );

     
        System.out.println("\nPlays by day:");
        MusicStats.playsByDay(plays).forEach((day, count) ->
                System.out.printf("  %-10s %d%n", day, count)
        );

        
        System.out.println("\nTop 3 artists: "
                + MusicStats.topArtists(plays, 3));

        MusicStats.longestPlay(plays).ifPresent(play ->
                System.out.println("Longest play: " + compact.format(play))
        );

      
        System.out.println("\nMost recent:");
        MusicStats.recent(plays, 5).forEach(play ->
                System.out.println("  " + detailed.format(play))
        );

       
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\nUser to summarize (or 'quit'): ");
            String user = sc.nextLine().trim();

            if (user.equalsIgnoreCase("quit")) {
                break;
            }

            Predicate<Play> userFilter = PlayFilters.byUser(user);

            long count = plays.stream()
                    .filter(userFilter)
                    .count();

            long minutes = MusicStats.totalMinutes(plays, userFilter);

            String genre = MusicStats.topGenre(plays, user)
                    .orElse("None");

            double skipRate = MusicStats.skipRate(plays, user);

            System.out.printf(
                    "%s: %d plays, %d min, top genre %s, skip rate %.0f%%%n",
                    user, count, minutes, genre, skipRate * 100
            );

          
            Predicate<Play> indieFilter = userFilter
                    .and(PlayFilters.byGenre("Indie"));

            System.out.println("  Indie minutes: "
                    + MusicStats.totalMinutes(plays, indieFilter));

           
            String fullListens = plays.stream()
                    .filter(userFilter.and(PlayFilters.skipped().negate()))
                    .map(compact::format)
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining("; "));

            System.out.println("  Full listens: " + fullListens);
        }

        sc.close();
    }
}
