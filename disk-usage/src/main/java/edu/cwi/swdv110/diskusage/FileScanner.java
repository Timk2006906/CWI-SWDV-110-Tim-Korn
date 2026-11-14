package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class FileScanner {

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

    private FileScanner() {
    }

    static File[] sortedChildren(File dir) {
        File[] kids = dir.listFiles();

        if (kids == null) {
            return new File[0];
        }

        Arrays.sort(kids, Comparator.comparing(File::getName));
        return kids;
    }

    public static long totalSize(File f) {
        // Base case: If it's a file, return its size.
        // Recursive case: Add up the sizes of everything in the folder.
        if (f.isFile()) {
            return f.length();
        }

        long total = 0;

        for (File child : sortedChildren(f)) {
            total += totalSize(child);
        }

        return total;
    }

    public static int countFiles(File f) {
        // Base case: If it's a file, count it as 1.
        // Recursive case: Count the files in each folder.
        if (f.isFile()) {
            return 1;
        }

        int count = 0;

        for (File child : sortedChildren(f)) {
            count += countFiles(child);
        }

        return count;
    }

    public static int maxDepth(File f) {
        // Base case: A file or empty folder has a depth of 0.
        // Recursive case: Find the deepest child and add 1.
        if (f.isFile()) {
            return 0;
        }

        File[] children = sortedChildren(f);

        if (children.length == 0) {
            return 0;
        }

        int deepest = 0;

        for (File child : children) {
            deepest = Math.max(deepest, 1 + maxDepth(child));
        }

        return deepest;
    }

    public static List<FileInfo> listAll(File root) {
        List<FileInfo> result = new ArrayList<>();

        if (root.isFile()) {
            result.add(new FileInfo(root.getName(), root.length()));
        } else if (root.isDirectory()) {
            collect(root, "", result);
        }

        return result;
    }

    private static void collect(File dir, String prefix, List<FileInfo> out) {
        // Base case: Add files to the list without another call.
        // Recursive case: Go into subfolders and collect their files.
        for (File child : sortedChildren(dir)) {
            String path = prefix + child.getName();

            if (child.isFile()) {
                out.add(new FileInfo(path, child.length()));
            } else if (child.isDirectory()) {
                collect(child, path + "/", out);
            }
        }
    }

    public static List<FileInfo> findByExtension(File root, String ext) {
        List<FileInfo> matches = new ArrayList<>();

        String extension = "." + ext.toLowerCase(Locale.US);

        for (FileInfo file : listAll(root)) {
            if (file.path().toLowerCase(Locale.US).endsWith(extension)) {
                matches.add(file);
            }
        }

        return matches;
    }

    public static String humanSize(long bytes) {
        return humanSize(bytes, 0);
    }

    private static String humanSize(double value, int unit) {
        // Base case: Print when the size is small enough or at the last unit.
        // Recursive case: Divide by 1024 and move to the next unit.
        if (value < 1024 || unit == UNITS.length - 1) {
            if (unit == 0) {
                return String.format(Locale.US, "%.0f %s", value, UNITS[unit]);
            }

            return String.format(Locale.US, "%.1f %s", value, UNITS[unit]);
        }

        return humanSize(value / 1024, unit + 1);
    }

    public static String tree(File root, int maxDepth) {
        StringBuilder sb = new StringBuilder();

        tree(root, 0, maxDepth, sb);

        return sb.toString();
    }

    private static void tree(File f, int depth, int maxDepth, StringBuilder sb) {
        // Base case: Stop at a file or when the max depth is reached.
        // Recursive case: Print each child with more indentation.
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }

        sb.append(f.getName());

        if (f.isDirectory()) {
            sb.append("/");
        }

        sb.append(" (");
        sb.append(humanSize(totalSize(f)));
        sb.append(")");
        sb.append(System.lineSeparator());

        if (!f.isDirectory() || depth >= maxDepth) {
            return;
        }

        for (File child : sortedChildren(f)) {
            tree(child, depth + 1, maxDepth, sb);
        }
    }
}