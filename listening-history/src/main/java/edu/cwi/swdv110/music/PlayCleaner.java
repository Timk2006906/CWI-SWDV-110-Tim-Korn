package edu.cwi.swdv110.music;
import java.util.List;
import java.util.function.UnaryOperator;

/** Fixes messy rows IN PLACE using the List methods that take lambdas. */
public final class PlayCleaner {

    private PlayCleaner() { }

    /** "  hip-HOP " -> "Hip-Hop",  "ELECTRONIC" -> "Electronic"  (capitalize after '-' and ' ') */
    public static String titleCase(String text) {
        String result ="";
        boolean capitalizeNext = true;

        for(char letter : text.trim().toLowerCase().toCharArray()){
            if (capitalizeNext){
                result += Character.toUpperCase(letter);
            } else {
                result += letter;
            }
            capitalizeNext = (letter == ' ' || letter == '-');
        }
       return result;
    }

    public static int clean(List<Play> plays) {
    int originalSize = plays.size();
    
    plays.removeIf(play -> play.seconds()<= 0 || play.song().isBlank());
    UnaryOperator<Play> cleaner = play -> new Play(
        play.user().toLowerCase(),play.song(), play.artist(),titleCase(play.genre()),play.date(), play.seconds()
);
    plays.replaceAll(cleaner);

    int removed = originalSize - plays.size();

    return removed;
    }
}