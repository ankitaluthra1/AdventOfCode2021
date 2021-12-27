import utils.InputFileReader;

import java.util.*;

public class Day13 {

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

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    class TransparentPaper {

        private Set<Coordinate> dotLocations;
        private List<Fold> folds;

        public void foldAll() {
            for (int i = 0; i < folds.size(); i++) {
                fold(i);
            }
        }

        class Fold {
            char axis;
            int value;

            public Fold(char axis, int value) {
                this.axis = axis;
                this.value = value;
            }
        }

        public TransparentPaper(List<String> inputList) {
            dotLocations = new HashSet<>();
            folds = new ArrayList<>();
            int index = 0;
            for (String s : inputList) {
                if (s.equals("")) {
                    index++;
                    break;
                }
                int x = Integer.parseInt(s.split(",")[0]);
                int y = Integer.parseInt(s.split(",")[1]);
                dotLocations.add(new Coordinate(x, y));
                index++;
            }
            while (index < inputList.size()) {
                String[] foldInput = inputList.get(index).split("=");
                String axisInput = foldInput[0];
                folds.add(new Fold(axisInput.charAt(axisInput.length() - 1), Integer.parseInt(foldInput[1])));
                index++;
            }
        }

        public void fold(int index) {
            Set<Coordinate> finalList;
            if (folds.get(index).axis == 'y')
                finalList = foldForYCoordinate(this.folds.get(index).value);
            else
                finalList = foldForXCoordinate(this.folds.get(index).value);

            this.dotLocations = finalList;
        }

        private Set<Coordinate> foldForYCoordinate(int foldValue) {
            Set<Coordinate> finalList = new HashSet<>();
            for (Coordinate current : this.dotLocations) {
                if (current.y < foldValue) {
                    finalList.add(current);
                } else {
                    int diff = current.y - foldValue;
                    finalList.add(new Coordinate(current.x, foldValue - diff));
                }
            }
            return finalList;
        }

        private Set<Coordinate> foldForXCoordinate(int foldValue) {
            Set<Coordinate> finalList = new HashSet<>();
            for (Coordinate current : this.dotLocations) {
                if (current.x <= foldValue) {
                    finalList.add(current);
                } else {
                    int diff = current.x - foldValue;
                    finalList.add(new Coordinate(foldValue - diff, current.y));
                }
            }
            return finalList;
        }

        public int countDots() {
            return dotLocations.size();
        }

        private void printAllDots() {
            int maxX = -1;
            int maxY = -1;
            for (Coordinate current : dotLocations) {
                maxX = Math.max(current.x, maxX);
                maxY = Math.max(current.y, maxY);
            }
            for (int row = 0; row <= maxY; row++) {
                for (int col = 0; col <= maxX; col++) {
                    if (dotLocations.contains(new Coordinate(col, row)))
                        System.out.print("#");
                    else
                        System.out.print(".");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        List<String> inputList = InputFileReader.getInput("day-13-input");
        Day13 day13 = new Day13();
        day13.solveFirstPart(inputList);
        day13.solveSecondPart(inputList);
    }

    private void solveFirstPart(List<String> inputList) {
        TransparentPaper transparentPaper = new TransparentPaper(inputList);
        transparentPaper.fold(0);
        System.out.println(transparentPaper.countDots());
    }

    private void solveSecondPart(List<String> inputList) {
        TransparentPaper transparentPaper = new TransparentPaper(inputList);
        transparentPaper.foldAll();
        transparentPaper.printAllDots();
    }

}
