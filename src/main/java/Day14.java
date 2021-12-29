import utils.InputFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 {


    class Cache {
        String value;
        int frequency;

        public Cache(String value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cache cache = (Cache) o;
            return frequency == cache.frequency && Objects.equals(value, cache.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, frequency);
        }
    }

    Map<Cache, String> derivedMap = new HashMap<>();
    static Map<String, Character> givenMap = new HashMap<>();

    public static void main(String[] args) {
        List<String> inputList = InputFileReader.getInput("day-14-input");
        Day14 day14 = new Day14();
        String input = inputList.get(0);
        Map<String, Character> map = new HashMap<>();
        for (int i = 2; i < inputList.size(); i++) {
            String s = inputList.get(i);
            String key = s.split(" -> ")[0];
            Character value = s.split(" -> ")[1].toCharArray()[0];
            givenMap.put(key, value);
        }
        day14.solve(input, 4);
        //day14.solve(input, 40);
    }

    private void solve(String input, int steps) {
        String result = applyInsertion(input, steps);
        System.out.println(result);
        System.out.println(diff(result));
    }

    private String applyInsertion(String input, int steps) {
        if (steps == 0) {
            return input;
        }
        Cache key = new Cache(input, steps);
        if (derivedMap.containsKey(key)) {
            return derivedMap.get(key);
        }
        int index = 0;
        StringBuilder sg = new StringBuilder();
        while (index < (input.length() - 1)) {
            String currentPair = input.substring(index, index + 2);
            sg.append(currentPair.charAt(0)).append(givenMap.get(currentPair));
            index++;
        }

        String result = applyInsertion(sg.append(input.charAt(index)).toString(), steps - 1);
        derivedMap.put(key, result);

        return result;
    }

    private long diff(String polymerTemplate) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : polymerTemplate.toCharArray()) {
            int count = countMap.getOrDefault(c, 0);
            countMap.put(c, count + 1);
        }
        long minValue = Long.MAX_VALUE;
        long maxValue = -1;
        for (int i : countMap.values()) {
            minValue = Math.min(minValue, i);
            maxValue = Math.max(maxValue, i);
        }
        return (maxValue - minValue);
    }

    private LinkedList<Character> pairInsertion(LinkedList<Character> polymerTemplate, Map<String, Character> pairMap) {

        LinkedList<Character> result = new LinkedList<>();
        int index = 0;
        while (index < (polymerTemplate.size() - 1)) {
            String currentPair = polymerTemplate.get(index).toString() + polymerTemplate.get(index + 1).toString();
            result.addLast(polymerTemplate.get(index));
            result.addLast(pairMap.get(currentPair));
            index++;
        }

        result.addLast(polymerTemplate.get(index));
        return result;
    }

}
