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
        var isUp = false;
        var isDown = false;
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
                    isUp = a < b;
                    isDown = !isUp;
                    continue;
                }
                if ((isDown && a < b) || (isUp && a > b)) {
                    return false;
                }
            }
        }
        return true;
    }
}
