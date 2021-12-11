import utils.InputFileReader;

import java.util.HashSet;
import java.util.Set;

public class Day7 {


    public static void main(String[] args) {

        String[] inputArr = InputFileReader.getInput("day-7-input").get(0).split(",");
        Day7 day7 = new Day7();
        Set<Integer> set = new HashSet<>();

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < inputArr.length; i++) {
            int temp = Integer.parseInt(inputArr[i]);
            max = max > temp ? max : temp;
            min = min < temp ? min : temp;
            set.add(temp);
        }

        for (int i = min; i <= max; i++) {
            for (int j = min; j < max;j++){

            }
        }

    }

}
