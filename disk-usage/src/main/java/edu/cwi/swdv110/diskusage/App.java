package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Folder to scan (Enter for this project): ");
        String input = sc.hasNextLine() ? sc.nextLine().trim() : "";

        File root = Path.of(input.isEmpty() ? "." : input)
                .toAbsolutePath()
                .normalize()
                .toFile();

        if (!root.isDirectory()) {
            System.out.println("Not a folder: " + root);
            return;
        }

        System.out.println("\nFolder tree:");
        System.out.print(FileScanner.tree(root, 2));

        List<FileInfo> files = FileScanner.listAll(root);

        System.out.printf(
                "%nFiles: %d  Total: %s  Deepest nesting: %d levels%n",
                FileScanner.countFiles(root),
                FileScanner.humanSize(FileScanner.totalSize(root)),
                FileScanner.maxDepth(root)
        );

        System.out.println("\n5 largest files:");

        for (FileInfo file : Algorithms.largest(files, 5)) {
            System.out.printf(
                    "%10s %s%n",
                    FileScanner.humanSize(file.size()),
                    file.path()
            );
        }

        Map<String, Integer> extensions = new TreeMap<>();

        for (FileInfo file : files) {
            String name = file.path();
            int slash = name.lastIndexOf('/');
            name = name.substring(slash + 1);

            int dot = name.lastIndexOf('.');
            String ext = dot <= 0 || dot == name.length() - 1
                    ? "(none)"
                    : name.substring(dot + 1).toLowerCase();

            extensions.put(ext, extensions.getOrDefault(ext, 0) + 1);
        }

        System.out.println("\nFiles by extension:");
        System.out.println(extensions);

        List<FileInfo> sortedByPath =
                Algorithms.mergeSort(files, Algorithms.BY_PATH);

        while (true) {
            System.out.print("\nEnter a file path, 'race', or 'quit': ");

            if (!sc.hasNextLine()) {
                break;
            }

            String command = sc.nextLine().trim();

            if (command.equalsIgnoreCase("quit")) {
                break;
            }

            if (command.equalsIgnoreCase("race")) {
                sortRace();
                continue;
            }

            int index = Algorithms.binarySearchByPath(
                    sortedByPath, command
            );

            if (index == -1) {
                System.out.println("File not found.");
            } else {
                FileInfo found = sortedByPath.get(index);

                System.out.println(
                        found.path() + " (" +
                        FileScanner.humanSize(found.size()) + ")"
                );
            }
        }
    }

    static void sortRace() {
        Random rnd = new Random(110);

        System.out.printf(
                "%8s %14s %12s%n",
                "n", "selection ms", "merge ms"
        );

        for (int n = 1_000; n <= 16_000; n *= 2) {
            List<FileInfo> data = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                data.add(new FileInfo(
                        "file" + rnd.nextInt(1_000_000),
                        rnd.nextInt(10_000_000)
                ));
            }

            long t0 = System.nanoTime();
            Algorithms.selectionSort(data, Algorithms.BY_SIZE_DESC);

            long t1 = System.nanoTime();
            Algorithms.mergeSort(data, Algorithms.BY_SIZE_DESC);

            long t2 = System.nanoTime();

            System.out.printf(
                    "%8d %14.1f %12.1f%n",
                    n,
                    (t1 - t0) / 1e6,
                    (t2 - t1) / 1e6
            );
        }
    }
}
