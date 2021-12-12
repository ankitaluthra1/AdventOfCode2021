import utils.InputFileReader;

import java.util.*;

public class Day10 {

    static Map<Character, Integer> corruptScoreMap = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};

    static Map<Character, Integer> completionScoreMap = new HashMap<>() {{
        put(')', 1);
        put(']', 2);
        put('}', 3);
        put('>', 4);
    }};

    static Map<Character, Character> brackets = new HashMap<>() {{
        put('(', ')');
        put('[',']');
        put('{','}');
        put('<','>');
    }};


    public static void main(String[] args) {

        List<String> inputs = InputFileReader.getInput("day-10-input");

        Day10 day10 = new Day10();

        List<String> inputForSecondPart = day10.solveFirstPart(inputs);

        day10.solveSecondPart(inputForSecondPart);

    }

    private void solveSecondPart(List<String> inputForSecondPart) {
        List<Long> completionScore = new ArrayList<>();
        for (String in : inputForSecondPart) {
            completionScore.add(getCompletionScore(in));
        }
        completionScore.sort(Long::compareTo);
        int index = completionScore.size() / 2;
        System.out.println(completionScore.get(index));
    }

    private long getCompletionScore(String line) {
        List<Character> stack = new ArrayList<>();
        for (char c : line.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.add(0, c);
            } else {
                char pop = stack.remove(0);
                if (!isMatching(pop, c))
                    throw new IllegalStateException("opening and closing bracket not matching: " + pop + " " + c);
            }
        }
        List<Character> completionBrackets = new ArrayList<>();
        for (char c : stack) {
            completionBrackets.add(brackets.get(c));
        }

        return getCompletionScoreFor(completionBrackets);
    }

    private long getCompletionScoreFor(List<Character> completionBrackets) {
        long sum = 0;
        for (char c : completionBrackets){
            sum = (sum * 5) + completionScoreMap.get(c);
        }
        return sum;
    }

    private List<String> solveFirstPart(List<String> inputs) {
        int corruptScore = 0;
        List<String> incompleteLines = new ArrayList<>();
        for (String in : inputs) {

            char c = findCorruptedChar(in);
            if (c == 'x') {
                incompleteLines.add(in);
                continue;
            }
            corruptScore = corruptScore + corruptScoreMap.get(c);
        }

        System.out.println(corruptScore);
        return incompleteLines;
    }

    private char findCorruptedChar(String line) {
        List<Character> stack = new ArrayList<>();
        for (char c : line.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.add(0, c);
                continue;
            }
            char pop = stack.remove(0);
            if (!isMatching(pop, c))
                return c;
        }
        return 'x';
    }

    private boolean isMatching(char open, char close) {
        return switch (open) {
            case '(' -> close == ')';
            case '[' -> close == ']';
            case '{' -> close == '}';
            case '<' -> close == '>';
            default -> throw new IllegalArgumentException("open bracket not matching any bracket");
        };
    }

    private boolean isOpeningBracket(char c) {
        List<Character> openingBrackets = Arrays.asList('(', '[', '{', '<');
        return openingBrackets.contains(c);
    }

}
