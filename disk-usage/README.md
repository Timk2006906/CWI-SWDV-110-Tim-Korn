# Disk Usage Analyzer

## Sorting Race Results

Files     Selection Sort (ms)    Merge Sort (ms)
1000      10.5                   1.2
2000      7.8                    0.7
4000      32.5                   1.0
8000      131.2                  1.6
16000     545.3                  3.4

## Big-O Questions

### 1.

Selection sort is O(n²), so when the amount of files doubles, it should take around 4 times longer.

My results were a little weird at first because the time went down from 1,000 to 2,000. But from 4,000 to 8,000 and then 16,000, it took about 4 times longer each time.

Merge sort is O(n log n), so it doesn't slow down as much. From 8,000 to 16,000, it went from 1.6 ms to 3.4 ms, which is a little more than double.

### 2.

`totalSize` is O(f), where f is the number of files and folders.

It has to visit each file and folder to add up all the sizes, so the more files there are, the longer it takes.

### 3.

If I'm only searching once, I don't think sorting everything first would be worth it. I could just search through the list.

If I'm searching 1,000 times, sorting first would be better because binary search is O(log n), so each search would be faster.

### 4.

Without a base case, the recursion could keep going without stopping.

Java would eventually throw a `StackOverflowError` if it kept making recursive calls because the call stack would fill up.

### 5.

A family tree is another example of recursion.

You can look at someone's parents, then their parents, and keep following the same pattern. It's similar to folders having more folders inside them.