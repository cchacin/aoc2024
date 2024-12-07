package com.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day03 {

    public static void main(String... args) throws Exception {
        var path = Paths.get(
                Objects.requireNonNull(
                        Day03.class.getClassLoader().getResource("day03.txt")
                ).toURI()
        );

        System.out.println(
                calculate(Files.readString(path))
        );
    }

    private static long calculate(String content) {
        var result = 0L;
        var pattern = Pattern.compile("mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\)|do(n't)?\\(\\)");
        var matcher = pattern.matcher(content);
        var isDo = true;
        while (matcher.find()) {
            var group = matcher.group();
            if (group.equals("do()") || group.equals("don't()")) {
                isDo = group.equals("do()");
                continue;
            }
            if(isDo) {
                var first = Long.parseLong(matcher.group("first"));
                var second = Long.parseLong(matcher.group("second"));
                result += first * second;
            }
        }
        return result;
    }

}
