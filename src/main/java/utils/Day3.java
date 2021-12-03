package utils;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    class PositionBit {

        int zeroCount;
        int oneCount;

        public PositionBit() {
            zeroCount = 0;
            oneCount = 0;
        }

        void increaseCount(char bit) {
            if (bit == '0')
                zeroCount++;
            else
                oneCount++;
        }

        int highestCountBit() {
            if (zeroCount > oneCount)
                return 0;
            else
                return 1;
        }
    }

    public static void main(String[] args) {

        List<String> input = MyFileReader.getInput("day-3-input");
        Day3 day3 = new Day3();
        day3.powerConsumption(input);
        day3.lifeSupportRating(input);
    }

    private void lifeSupportRating(List<String> input) {
        String O2Reading = getO2Reading(new ArrayList<>(input), 0);
        String CO2Reading = getCO2Reading(new ArrayList<>(input), 0);

        System.out.println(getDecimalValue(O2Reading) * getDecimalValue(CO2Reading));

    }

    private String getO2Reading(List<String> input, int currentPosition) {
        if (input.size() == 1)
            return input.get(0);
        int zeroCount = 0;
        int oneCount = 0;
        List<String> zeroBitReading = new ArrayList<>();
        List<String> oneBitReading = new ArrayList<>();
        for (String reading : input) {
            if (reading.charAt(currentPosition) == '0') {
                zeroBitReading.add(reading);
                zeroCount++;
            } else {
                oneCount++;
                oneBitReading.add(reading);
            }
        }

        if (zeroCount > oneCount)
            return getO2Reading(zeroBitReading, currentPosition + 1);
        else
            return getO2Reading(oneBitReading, currentPosition + 1);
    }

    private String getCO2Reading(List<String> input, int currentPosition) {
        if (input.size() == 1)
            return input.get(0);
        int zeroCount = 0;
        int oneCount = 0;
        List<String> zeroBitReading = new ArrayList<>();
        List<String> oneBitReading = new ArrayList<>();
        for (String reading : input) {
            if (reading.charAt(currentPosition) == '0') {
                zeroBitReading.add(reading);
                zeroCount++;
            } else {
                oneCount++;
                oneBitReading.add(reading);
            }
        }

        if (zeroCount <= oneCount)
            return getCO2Reading(zeroBitReading, currentPosition + 1);
        else
            return getCO2Reading(oneBitReading, currentPosition + 1);
    }

    private void powerConsumption(List<String> input) {
        int bitLength = input.get(0).length();
        PositionBit[] positionArray = new PositionBit[bitLength];

        for (int i = 0; i < bitLength; i++) {
            positionArray[i] = new PositionBit();
        }

        for (String in : input) {
            int index = 0;
            for (char c : in.toCharArray()) {
                PositionBit currentBit = positionArray[index];
                currentBit.increaseCount(c);
                index++;
            }
        }

        int[] gamma = getMostCommonBit(positionArray);
        int[] epsilon = inverse(gamma);

        System.out.println(getDecimalValue(gamma) * getDecimalValue(epsilon));
    }

    private int[] inverse(int[] gamma) {
        int[] result = new int[gamma.length];
        for (int i = 0; i < gamma.length; i++)
            result[i] = gamma[i] == 0 ? 1 : 0;
        return result;
    }

    private int getDecimalValue(int[] binary) {
        int result = 0;
        int index = 0;
        for (int i = binary.length - 1; i >= 0; i--) {
            result += Math.pow(2.0, index) * binary[i];
            index++;
        }
        return result;
    }

    private int getDecimalValue(String binary) {
        int result = 0;
        int index = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            result += Math.pow(2.0, index) * Integer.parseInt(binary.charAt(i)+"");
            index++;
        }
        return result;
    }

    private int[] getMostCommonBit(PositionBit[] positionArray) {
        int[] result = new int[positionArray.length];
        for (int i = 0; i < positionArray.length; i++) {
            result[i] = positionArray[i].highestCountBit();
        }
        return result;
    }

}
