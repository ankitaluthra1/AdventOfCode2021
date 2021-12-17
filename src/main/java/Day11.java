import utils.InputFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day11 {

    class Coordinate {
        int i;
        int j;

        public Coordinate(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return i == that.i && j == that.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    class DumboOctopus {
        int[][] energyGrid;
        int rowSize;
        int colSize;

        public DumboOctopus(List<String> inputs) {
            rowSize = inputs.size();
            colSize = inputs.get(0).length();
            energyGrid = new int[rowSize][colSize];
            int index = 0;
            for (String s : inputs) {
                for (int j = 0; j < colSize; j++) {
                    energyGrid[index][j] = Integer.parseInt(s.charAt(j) + "");
                }
                index++;
            }
        }

        public int totalFlashesAfter(int steps) {
            int sum = 0;
            for (int i = 0; i < steps; i++) {
                incrementAll();
                sum = sum + getFlashesForOneStep();
            }
            return sum;
        }

        public int getFirstStepForSynchronisedFlashes() {
            int step = 0;
            while (true) {
                step++;
                incrementAll();
                if(isSynchronisedStep())
                    return step;
            }
        }

        private boolean isSynchronisedStep() {
            List<Coordinate> flashedCoordinates = new ArrayList<>();
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    checkWithAllNeighbors(i, j, flashedCoordinates);
                }
            }
            return isSynchronisedFlash();
        }

        private boolean isSynchronisedFlash() {
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    if(energyGrid[i][j] != 0)
                        return false;
                }
            }
            return true;
        }

        private void incrementAll() {
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    energyGrid[i][j] = energyGrid[i][j] + 1;
                }
            }
        }

        private void printGrid() {
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    System.out.print(energyGrid[i][j]+" ");
                }
                System.out.println();
            }
        }

        private int getFlashesForOneStep() {
            int flashes = 0;
            List<Coordinate> flashedCoordinates = new ArrayList<>();
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    flashes = flashes + checkWithAllNeighbors(i, j, flashedCoordinates);
                }
            }
            return flashes;
        }

        private int checkWithAllNeighbors(int current_i, int current_j, List<Coordinate> flashedCoordinates) {
            Coordinate currentCoordinate = new Coordinate(current_i, current_j);
            if (flashedCoordinates.contains(currentCoordinate))
                return 0;
            List<Coordinate> queue = new ArrayList<>();
            queue.add(currentCoordinate);
            int totalFlashes = 0;
            while (!queue.isEmpty()) {
                Coordinate coordinate = queue.remove(0);
                if (coordinate.i < 0 || coordinate.i >= rowSize || coordinate.j < 0 || coordinate.j >= colSize || flashedCoordinates.contains(coordinate))
                    continue;
                if (energyGrid[coordinate.i][coordinate.j] > 9) {
                    totalFlashes++;
                    energyGrid[coordinate.i][coordinate.j] = 0;
                    flashedCoordinates.add(coordinate);

                    queue.add(new Coordinate(coordinate.i, coordinate.j - 1));
                    increment(coordinate.i, coordinate.j - 1,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i - 1, coordinate.j - 1));
                    increment(coordinate.i - 1, coordinate.j - 1,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i - 1, coordinate.j));
                    increment(coordinate.i - 1, coordinate.j,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i - 1, coordinate.j + 1));
                    increment(coordinate.i - 1, coordinate.j + 1,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i, coordinate.j + 1));
                    increment(coordinate.i, coordinate.j + 1,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i + 1, coordinate.j + 1));
                    increment(coordinate.i + 1, coordinate.j + 1,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i + 1, coordinate.j));
                    increment(coordinate.i + 1, coordinate.j ,flashedCoordinates);

                    queue.add(new Coordinate(coordinate.i + 1, coordinate.j - 1));
                    increment(coordinate.i + 1, coordinate.j - 1,flashedCoordinates);
                }
            }

            return totalFlashes;
        }

        private void increment(int i, int j,List<Coordinate> flashed) {
            if (i < 0 || i >= rowSize || j < 0 || j >= colSize || flashed.contains(new Coordinate(i,j)))
                return;
            energyGrid[i][j] = energyGrid[i][j] + 1;
        }
    }

    public static void main(String[] args) {

        List<String> inputs = InputFileReader.getInput("day-11-input");
        Day11 day11 = new Day11();
        day11.solveFirstPart(inputs);
        day11.solveSecondPart(inputs);

    }

    private void solveFirstPart(List<String> inputs) {
        DumboOctopus dumboOctopus = new DumboOctopus(inputs);
        int result = dumboOctopus.totalFlashesAfter(100);
        System.out.println(result);
    }

    private void solveSecondPart(List<String> inputs) {
        DumboOctopus dumboOctopus = new DumboOctopus(inputs);
        int result = dumboOctopus.getFirstStepForSynchronisedFlashes();
        System.out.println(result);
    }

}
