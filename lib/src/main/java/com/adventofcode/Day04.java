package com.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day04 {

    public static void main(String... args) throws Exception {
        var path = Paths.get(
                Objects.requireNonNull(
                        Day04.class.getClassLoader().getResource("day04.txt")
                ).toURI()
        );

        var content = convert(Files.readAllLines(path));
        System.out.println(countXMAS(content));
        System.out.println(countXMAS2(content));
    }

    enum Direction {
        EAST(1, 0),
        SOUTHEAST(1, 1),
        SOUTH(0, 1),
        SOUTHWEST(-1, 1),
        WEST(-1, 0),
        NORTHWEST(-1, -1),
        NORTH(0, -1),
        NORTHEAST(1, -1);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    record Coordinate(int x, int y) {
    }

    static Map<Coordinate, Character> GRID = new HashMap<>();

    static char[][] convert(List<String> lines) {
        var result = new char[lines.size()][];
        int bound = lines.size();
        for (int i = 0; i < bound; i++) {
            result[i] = lines.get(i).toCharArray();
            for (int j = 0; j < result[i].length; j++) {
                GRID.put(new Coordinate(i, j), result[i][j]);
            }
        }
        return result;
    }

    static char getChar(int x, int y) {
        return GRID.getOrDefault(new Coordinate(x, y), '.');
    }

    static long countXMAS(char[][] array) {
        var count = 0L;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                var currentLetter = GRID.get(new Coordinate(i, j));
                if (currentLetter == 'X') {
                    for (var direction : Direction.values()) {
                        if (getChar(i + direction.x, j + direction.y) == 'M' &&
                                getChar(i + (direction.x * 2), j + (direction.y * 2)) == 'A' &&
                                getChar(i + (direction.x * 3), j + (direction.y * 3)) == 'S'
                        ) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    static long countXMAS2(char[][] array) {
        var count = 0L;
        var interCardinals = List.of(
                Direction.NORTHEAST,
                Direction.SOUTHWEST,
                Direction.SOUTHEAST,
                Direction.NORTHWEST
        );
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                var currentLetter = GRID.get(new Coordinate(i, j));
                if (currentLetter == 'A') {
                    var corners = new ArrayList<Character>();
                    for (var interCardinal : interCardinals) {
                        var corner = getChar(i + interCardinal.x, j + interCardinal.y);
                        corners.add(corner);
                    }
                    System.out.println("corners = " + corners);
                    if (corners.contains('.')) {
                        continue;
                    }
                    if (corners.get(0) == corners.get(1)) {
                        continue;
                    }
                    if (corners.get(2) == corners.get(3)) {
                        continue;
                    }
                    if (corners.stream()
                            .filter(corner -> 'M' == corner)
                            .count() != 2) {
                        continue;
                    }
                    if (corners.stream()
                            .filter(corner -> 'S' == corner)
                            .count() != 2) {
                        continue;
                    }
                    count++;
                }
            }
        }
        return count;
    }
}
