import utils.MyFileReader;

import java.util.List;

public class Day1 {

    public static void main(String[] args) {
        List<String> input = MyFileReader.getInput("day-1-first-input");
        Day1 day1 = new Day1();
        int incrementCount = day1.getSingleIncrementCount(input);
        int incrementSumCount = day1.getSumIncrementCount(input);
        System.out.println(incrementCount);
        System.out.println(incrementSumCount);
    }

    private int getSingleIncrementCount(List<String> input) {
        int incrementCount = 0;
        int previousDepth = Integer.parseInt(input.get(0));
        for (String current : input) {
            int currentDepth = Integer.parseInt(current);
            if (currentDepth > previousDepth)
                incrementCount++;
            previousDepth = currentDepth;
        }
        return incrementCount;
    }

    private int getSumIncrementCount(List<String> input) {
        int incrementCount = 0;
        int previousDepthSum = 0;
        int i = 0;
        while (true) {
            int currentDepthSum = 0;
            for (int j = 0; j < 3; j++) {
                currentDepthSum = currentDepthSum + Integer.parseInt(input.get(i+j));
            }
            if (currentDepthSum > previousDepthSum)
                incrementCount++;
            previousDepthSum = currentDepthSum;
            i++;
            if (i == input.size() - 3)
                return incrementCount;
        }
    }
}
