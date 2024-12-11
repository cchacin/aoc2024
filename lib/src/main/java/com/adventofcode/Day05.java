package com.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day05 {

    public static void main(String... args) throws Exception {
        var path = Paths.get(
                Objects.requireNonNull(
                        Day05.class.getClassLoader().getResource("day05.txt")
                ).toURI()
        );

        var content = convert(Files.readString(path));
        System.out.println("part1 = " + part1(content));
    }

    record Content(List<Rule> rules, List<Update> updates) {
    }

    record Rule(int a, int b) {
    }

    record Update(List<Integer> numbers) {

        int middle() {
            return numbers.get(numbers.size() / 2);
        }

        boolean isValid(List<Rule> rules) {
            return rules.stream().allMatch(this::isValid);
        }

        boolean isValid(Rule rule) {
            if (!numbers.contains(rule.a) || !numbers.contains(rule.b)) {
                return true;
            }
            return numbers.indexOf(rule.a) < numbers.indexOf(rule.b);
        }
    }

    static Content convert(String content) {
        var split = content.split("\n\n");
        return new Content(rules(split[0]), updates(split[1]));
    }

    static List<Rule> rules(String content) {
        return Arrays.stream(content.split("\n")).map(line -> {
            var numbers = line.split("\\|");
            return new Rule(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1])
            );
        }).toList();
    }

    static List<Update> updates(String content) {
        return Arrays.stream(content.split("\n"))
                .map(line -> new Update(
                        Arrays.stream(line.split(","))
                                .map(Integer::parseInt)
                                .toList())
                ).toList();
    }

    static long part1(Content content) {
        return content.updates.stream()
                .filter(update -> update.isValid(content.rules))
                .mapToLong(Update::middle)
                .sum();
    }
}
