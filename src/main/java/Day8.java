import utils.InputFileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8 {

    class Decoder {

        String[] signals;
        String[] outputValues;

        public Decoder(String signalString, String outputValueString) {
            signals = signalString.split(" ");
            outputValues = outputValueString.split(" ");
        }

        public int decode() {
            char[] finalDisplay = deduceDisplay();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < outputValues.length; i++) {
                int output = decode(outputValues[i], finalDisplay);
                builder.append(output);
            }
            return Integer.parseInt(builder.toString());
        }

        private char[] deduceDisplay() {
            String result = findPossiblePermutation("abcdefg", "");
            return result.toCharArray();
        }

        private String findPossiblePermutation(String input, String output) {
            if (output.length() == 7) {
                if (testPermutation(output))
                    return output;
                return "";
            }

            for (int i = 0; i < input.length(); i++) {
                String current;
                if (i == 0) {
                    current = findPossiblePermutation(input.substring(1), output + input.charAt(i));
                } else {
                    current = findPossiblePermutation(input.substring(0, i) + input.substring(i + 1), output + input.charAt(i));
                }
                if (current.length() > 0)
                    return current;
            }
            return "";
        }

        private boolean testPermutation(String output) {
            char[] finalDisplay = output.toCharArray();
            for (int i = 0; i < signals.length; i++) {
                if (decode(signals[i], finalDisplay) == -1)
                    return false;
            }
            return true;
        }

        private int decode(String input, char[] finalDisplay) {
            if (input.length() == 7)
                return 8;
            if (input.length() == 2)
                return 1;
            if (input.length() == 4)
                return 4;
            if (input.length() == 3)
                return 7;
            if (input.length() == 5) {
                if (isFive(input, finalDisplay))
                    return 5;
                if (isThree(input, finalDisplay))
                    return 3;
                if (isTwo(input, finalDisplay))
                    return 2;
            }

            if (input.length() == 6) {
                if (isZero(input, finalDisplay))
                    return 0;
                if (isNine(input, finalDisplay))
                    return 9;
                if (isSix(input, finalDisplay))
                    return 6;
            }

            return -1;
        }

        private boolean isTwo(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[2]) && charContains(input, finalDisplay[3]) && charContains(input, finalDisplay[4]) &&charContains(input, finalDisplay[5]))
                return true;
            return false;
        }

        private boolean isThree(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[2]) && charContains(input, finalDisplay[3]) && charContains(input, finalDisplay[5]) && charContains(input, finalDisplay[6]))
                return true;
            return false;
        }

        private boolean isFive(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[0]) && charContains(input, finalDisplay[3]) && charContains(input, finalDisplay[5]) &&charContains(input, finalDisplay[6]))
                return true;
            return false;
        }

        private boolean isSix(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[0]) && charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[3]) && charContains(input, finalDisplay[4]) && charContains(input, finalDisplay[5]) && charContains(input, finalDisplay[6]))
                return true;
            return false;
        }

        private boolean isNine(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[0]) && charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[2]) && charContains(input, finalDisplay[3]) && charContains(input, finalDisplay[5]) && charContains(input, finalDisplay[6]))
                return true;
            return false;
        }

        private boolean isZero(String input, char[] finalDisplay) {
            if (charContains(input, finalDisplay[0]) && charContains(input, finalDisplay[1]) && charContains(input, finalDisplay[2]) && charContains(input, finalDisplay[4]) && charContains(input, finalDisplay[5]) && charContains(input, finalDisplay[6]))
                return true;
            return false;
        }
    }

    List<Integer> uniqueDigitCount = Arrays.asList(2, 4, 3, 7);

    public static void main(String[] args) {

        List<String> inputs = InputFileReader.getInput("day-8-input");
        List<String> digitsInput = new ArrayList<>();

        for (String in : inputs) {
            String outputValueString = in.split(" \\| ")[1];
            digitsInput.addAll(Arrays.asList(outputValueString.trim().split(" ")));
        }

        Day8 day8 = new Day8();
        System.out.println(day8.getUniqueSegmentDigits(digitsInput));

        System.out.println(day8.sumOfDecodedValues(inputs));
    }

    private long sumOfDecodedValues(List<String> inputs) {
        long sum = 0;
        for (String in : inputs) {
            StringBuilder numberString = new StringBuilder();
            Decoder decoder = new Decoder(in.split(" \\| ")[0], in.split(" \\| ")[1]);
            sum = sum + decoder.decode();
        }

        return sum;
    }

    private int getUniqueSegmentDigits(List<String> digitsInput) {
        int count = 0;
        for (String in : digitsInput) {
            if (hasUniqueSegment(in))
                count++;
        }
        return count;
    }

    private boolean hasUniqueSegment(String in) {
        return uniqueDigitCount.contains(in.length());
    }


    private boolean charContains(String s, char c) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c)
                return true;
        }
        return false;
    }
}
