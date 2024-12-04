package com.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String... args) throws Exception {
        var path = Paths.get(
                Day01.class.getClassLoader().getResource("day01.txt").toURI()
        );
        var lines = Files.readAllLines(path);
        var listA = new PriorityQueue<Long>(lines.size());
        var listB = new PriorityQueue<Long>(lines.size());

        lines.forEach(line -> {
            var parts = line.split("\\s+");
            var left = Long.parseLong(parts[0]);
            var right = Long.parseLong(parts[1]);
            listA.add(left);
            listB.add(right);
        });

        var frequencyMap = listB.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )
        );

        var result = 0L;
        var similarityScore = 0L;
        while (!listA.isEmpty() && !listB.isEmpty()) {
            var valueA = listA.poll();
            var valueB = listB.poll();
            result += Math.abs(valueA - valueB);
            similarityScore += valueA * frequencyMap.getOrDefault(valueA, 0L);
        }

        //result = 1197984
        //similarityScore = 23387399
        System.out.println("result = " + result);
        System.out.println("similarityScore = " + similarityScore);
    }
}
