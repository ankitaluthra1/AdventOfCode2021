import utils.InputFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day9 {

    class Point {
        int i;
        int j;
        boolean visited;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
            visited = false;
        }

        public boolean isVisited() {
            return visited;
        }

        public void visit() {
            this.visited = true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return i == point.i && j == point.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    class LavaPointBoard {
        int[][] smokePoints;
        List<Point> lowPoints;

        public LavaPointBoard(List<String> input) {
            smokePoints = new int[input.size()][input.get(0).length()];
            int index = 0;
            for (String s : input) {
                for (int j = 0; j < s.length(); j++) {
                    smokePoints[index][j] = Integer.parseInt(s.charAt(j) + "");
                }
                index++;
            }
            lowPoints = new ArrayList<>();
        }

        public List<Integer> getLowPoints() {
            List<Integer> lowNumbers = new ArrayList<>();

            for (int i = 0; i < smokePoints.length; i++) {
                for (int j = 0; j < smokePoints[0].length; j++) {
                    if (isLowestInAllNeighbors(i, j)) {
                        lowNumbers.add(smokePoints[i][j]);
                        lowPoints.add(new Point(i, j));
                    }
                }
            }

            return lowNumbers;
        }

        public List<Integer> getAllBasinLength() {

            List<Integer> basinLengths = new ArrayList<>();

            for (Point p : lowPoints) {
                basinLengths.add(getBasinLengthFor(p, new ArrayList<>()));
            }
            return basinLengths;
        }

        private int getBasinLengthFor(Point p, List<Point> visited) {
            if (p.i < 0 || p.i >= smokePoints.length || p.j < 0 || p.j >= smokePoints[0].length)
                return 0;
            if (smokePoints[p.i][p.j] == 9)
                return 0;
            if (visited.contains(p))
                return 0;
            visited.add(p);
            int length = 1 + getBasinLengthFor(new Point(p.i -1 , p.j), visited)
                    + getBasinLengthFor(new Point(p.i, p.j - 1), visited)
                    + getBasinLengthFor(new Point(p.i, p.j + 1), visited)
                    + getBasinLengthFor(new Point(p.i + 1, p.j), visited);
            return length;
        }

        private boolean isLowestInAllNeighbors(int i, int j) {
            int currentNum = smokePoints[i][j];
            if (currentNum == 0)
                return true;
            if (currentNum == 9)
                return false;
            return check(currentNum, i - 1, j) && check(currentNum, i, j - 1) &&
                    check(currentNum, i, j + 1) && check(currentNum, i + 1, j);
        }

        private boolean check(int num, int i, int j) {
            if (i < 0 || i >= smokePoints.length)
                return true;
            if (j < 0 || j >= smokePoints[0].length)
                return true;
            return num < smokePoints[i][j];
        }

    }


    public static void main(String[] args) {
        List<String> inputLines = InputFileReader.getInput("day-9-input");

        Day9 day9 = new Day9();
        day9.solve(inputLines);


    }

    private void solve(List<String> inputLines) {
        LavaPointBoard lavaPointBoard = new LavaPointBoard(inputLines);
        List<Integer> lowPoints = lavaPointBoard.getLowPoints();

        int sum = 0;
        for (int i : lowPoints) {
            sum = sum + 1 + i;
        }

        System.out.println(sum);

        List<Integer> list = lavaPointBoard.getAllBasinLength();
        int listSize = list.size();
        list.sort(Integer::compareTo);
        System.out.println(list.get(listSize - 1) * list.get(listSize - 2) * list.get(listSize - 3));
    }
}
