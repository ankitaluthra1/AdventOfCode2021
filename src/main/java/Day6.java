import utils.InputFileReader;

public class Day6 {


    public static void main(String[] args) {
        String inputArr[] = InputFileReader.getInput("day-6-input").get(0).split(",");
        Day6 day6 = new Day6();

        long[] fishes = new long[9];

        for (String input : inputArr) {
            int fishAge = Integer.parseInt(input);
            long count = fishes[fishAge];
            fishes[fishAge] = count + 1;
        }

        day6.findFinalCount(fishes, 256);
        long count = countTotal(fishes);

        System.out.println(count);
    }

    private static long countTotal(long[] fishes) {
        long count = 0;
        for (int i = 0; i < fishes.length; i++) {
            count = count + fishes[i];
        }
        return count;
    }

    private void findFinalCount(long[] fishes, int days) {
        for (int i = 0; i < days; i++) {
            ageFishes(fishes);
        }
    }

    private void ageFishes(long[] fishes) {
        long initial0DayCount = fishes[0];
        for (int i = 1; i < fishes.length; i++) {
            fishes[i - 1] = fishes[i];
        }
        long count = fishes[6];
        fishes[6] = initial0DayCount + count;
        fishes[8] = initial0DayCount;
    }

}
