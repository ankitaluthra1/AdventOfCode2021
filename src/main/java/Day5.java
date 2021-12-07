import utils.MyFileReader;

import java.util.*;

public class Day5 {

    class Line {

        Coordinate start;
        Coordinate end;

        public Line(int x1, int y1, int x2, int y2) {

            start = new Coordinate(x1, y1);
            end = new Coordinate(x2, y2);
        }

        public Set<Coordinate> allLinePoints() {
            Set<Coordinate> coordinates = new HashSet<>();
            if (start.x == end.x) {
                if (start.y > end.y) {
                    int tempY = end.y;
                    while (tempY <= start.y) {
                        coordinates.add(new Coordinate(start.x, tempY));
                        tempY++;
                    }
                } else {
                    int tempY = start.y;
                    while (tempY <= end.y) {
                        coordinates.add(new Coordinate(start.x, tempY));
                        tempY++;
                    }
                }
            } else {
                if (start.y == end.y) {
                    if (start.x > end.x) {
                        int tempX = end.x;
                        while (tempX <= start.x) {
                            coordinates.add(new Coordinate(tempX, start.y));
                            tempX++;
                        }
                    } else {
                        int tempX = start.x;
                        while (tempX <= end.x) {
                            coordinates.add(new Coordinate(tempX, start.y));
                            tempX++;
                        }
                    }
                } else {
                    int xMultiplicationFactor = 1;
                    if (start.x > end.x)
                        xMultiplicationFactor = -1;
                    int tempX = start.x;
                    int tempY = start.y;
                    int yMultiplicationFactor = 1;
                    if (start.y > end.y)
                        yMultiplicationFactor = -1;
                    coordinates.add(new Coordinate(start.x, start.y));
                    while (true) {
                        tempX = tempX + (xMultiplicationFactor);
                        tempY = tempY + (yMultiplicationFactor);
                        coordinates.add(new Coordinate(tempX, tempY));
                        if (tempX == end.x && tempY == end.y)
                            break;
                    }
                }
            }
            return coordinates;
        }
    }

    class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


    public static void main(String[] args) {

        List<String> inputs = MyFileReader.getInput("day-5-input");

        Day5 day5 = new Day5();
        List<Line> allLines = day5.getAllLines(inputs);

        Map<Coordinate, Integer> map = day5.getCoordinateMap(allLines);

        System.out.println(day5.getDangerousAreaCount(map));

    }

    private int getDangerousAreaCount(Map<Coordinate, Integer> map) {
        int count = 0;
        for (Map.Entry<Coordinate, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 2) {
                count++;
            }
        }
        return count;
    }

    private Map<Coordinate, Integer> getCoordinateMap(List<Line> allLines) {
        Map<Coordinate, Integer> map = new HashMap<>();

        for (Line line : allLines) {
            Set<Coordinate> coveredCoordinates = line.allLinePoints();
            for (Coordinate coordinate : coveredCoordinates) {
                int temp = map.getOrDefault(coordinate, 0);
                map.put(coordinate, temp + 1);
            }

        }
        return map;
    }

    private List<Line> getAllLines(List<String> inputs) {
        List<Line> lines = new ArrayList<>();

        for (String input : inputs) {
            String startString = input.split(" -> ")[0];
            String endString = input.split(" -> ")[1];

            int x1 = Integer.parseInt(startString.split(",")[0]);
            int y1 = Integer.parseInt(startString.split(",")[1]);
            int x2 = Integer.parseInt(endString.split(",")[0]);
            int y2 = Integer.parseInt(endString.split(",")[1]);

            lines.add(new Line(x1, y1, x2, y2));
        }
        return lines;
    }

}
