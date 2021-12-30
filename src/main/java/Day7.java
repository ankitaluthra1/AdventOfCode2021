import utils.InputFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {


    public static void main(String[] args) {

        String[] inputArr = InputFileReader.getInput("day-7-input").get(0).split(",");
        Day7 day7 = new Day7();
        List<Integer> inputList = new ArrayList<>();

        for (int i = 0; i < inputArr.length; i++) {
            inputList.add(Integer.parseInt(inputArr[i]));
        }
        day7.solveFirstPart(inputList);
        day7.solveSecondPart(inputList);
    }

    private void solveSecondPart(List<  Integer> inputList) {
        int mean1 = findLowMean(inputList);
        int mean2 = findHighMean(inputList);
        int lowSum = 0;
        for (int i : inputList) {
            lowSum = lowSum + getFuelUsed(mean1, i);
        }

        int highSum = 0;
        for (int i : inputList) {
            highSum = highSum + getFuelUsed(mean2, i);
        }
        System.out.println(Math.min(lowSum,highSum));
    }

    private int findHighMean(List<Integer> inputList) {
        int sum = inputList.stream().reduce(Integer::sum).get();
        int mean = (sum) / inputList.size();
        return mean+1;
    }

    private int findLowMean(List<Integer> inputList) {
        int sum = inputList.stream().reduce(Integer::sum).get();
        int mean = (sum) / inputList.size();
        return mean;
    }

    private int getFuelUsed(int mean, int i) {
        int diff  = Math.abs(mean - i);
        return (diff * (diff + 1))/2;

    }

    private void solveFirstPart(List<Integer> inputList) {
        int median = findMedian(inputList);
        int sum = 0;
        for (int i : inputList) {
            sum = sum + (Math.abs(median - i));
        }
        System.out.println(sum);
    }

    private int findMedian(List<Integer> inputList) {
        List<Integer> sortedList = inputList.stream().sorted().collect(Collectors.toList());
        return sortedList.get(sortedList.size() / 2);
    }

}
