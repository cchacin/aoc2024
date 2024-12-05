package com.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day02 {

    public static void main(String... args) throws Exception {
        var path = Paths.get(
                Objects.requireNonNull(
                        Day02.class.getClassLoader().getResource("day02.txt")
                ).toURI()
        );

        System.out.println(
                Files.readAllLines(path)
                        .stream()
                        .map(Day02::toIntegers)
                        .filter(Day02::isLineSafe)
                        .count()
        );
    }

    static List<Integer> toIntegers(String line) {
        return Arrays.stream(line.split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }

    static boolean isLineSafe(List<Integer> levels) {
        var size = levels.size();
        var isIncreasing = false;
        var isDecreasing = false;
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                var a = levels.get(i);
                var b = levels.get(i + 1);
                if (Objects.equals(a, b)) {
                    return false;
                }
                if (Math.abs(a - b) > 3) {
                    return false;
                }
                if (i == 0) {
                    if (a < b) {
                        isIncreasing = true;
                    } else {
                        isDecreasing = true;
                    }
                    continue;
                }
                if ((isDecreasing && a < b) || (isIncreasing && a > b)) {
                    return false;
                }
            }
        }
        return true;
    }
}
