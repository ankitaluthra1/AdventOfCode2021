import utils.InputFileReader;

import java.util.*;

public class Day12 {

    class SmallCaveVisit {
        Map<Node, Integer> map;

        public SmallCaveVisit() {
            this.map = new HashMap<>();
        }

        public SmallCaveVisit(Map<Node, Integer> map) {
            this.map = new HashMap<>();
            this.map.putAll(map);
        }

        public void visit(Node node) {
            Integer count = map.getOrDefault(node, 0);
            map.put(node, count + 1);
        }

        public boolean isAnyCaveVisitedTwice() {
            Optional<Node> any = map.entrySet().stream().filter(e -> e.getKey().isSmallCave()).filter(e -> e.getValue() > 1).map(e -> e.getKey()).findAny();
            return any.isPresent();
        }

        public boolean isVisited(Node node) {
            return map.containsKey(node);
        }
    }

    final static class Node {
        final String value;

        public Node(String value) {
            this.value = value;
        }

        public boolean isEquals(String otherValue) {
            return otherValue.equals(value);
        }

        public boolean isSmallCave() {
            return value.charAt(0) >= 97;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    static Map<Node, List<Node>> graph = new HashMap<>();

    public static void main(String[] args) {

        List<String> inputStrings = InputFileReader.getInput("day-12-input");

        for (String s : inputStrings) {
            String startString = s.split("-")[0];
            String endString = s.split("-")[1];
            Node startNode = new Node(startString);
            Node endNode = new Node(endString);
            List<Node> startNodeNeighbors = graph.getOrDefault(startNode, new ArrayList<>());
            startNodeNeighbors.add(endNode);
            graph.put(startNode, startNodeNeighbors);
            List<Node> endNodeNeighbors = graph.getOrDefault(endNode, new ArrayList<>());
            endNodeNeighbors.add(startNode);
            graph.put(endNode, endNodeNeighbors);
        }
        graph.remove(new Node("end"));

        Day12 day12 = new Day12();
        day12.solveFirstPart();
        day12.solveSecondPart();

    }

    private void solveSecondPart() {
        System.out.println(getNewPathCountFor(new Node("start"), new SmallCaveVisit()));
    }

    private int getNewPathCountFor(Node node, SmallCaveVisit smallCaveVisit) {
        if (node.isEquals("start") && smallCaveVisit.isVisited(node))
            return 0;

        if (node.isEquals("end")) {
            return 1;
        }
        if (node.isSmallCave() && smallCaveVisit.isVisited(node) && smallCaveVisit.isAnyCaveVisitedTwice()) {
            return 0;
        }

        smallCaveVisit.visit(node);

        List<Node> neighbors = graph.getOrDefault(node, new ArrayList<>());
        int sum = 0;
        for (Node n : neighbors) {
            SmallCaveVisit smallCaveVisitUpdated = new SmallCaveVisit(smallCaveVisit.map);
            sum = sum + getNewPathCountFor(n, smallCaveVisitUpdated);
        }
        return sum;
    }

    private void solveFirstPart() {
        System.out.println(getPathCountFor(new Node("start"), new ArrayList<>()));
    }

    private int getPathCountFor(Node start, List<Node> visited) {

        if (start == null)
            return 0;
        if (start.equals(new Node("end")))
            return 1;
        if (visited.contains(start) && start.isSmallCave())
            return 0;
        visited.add(start);
        int sum = 0;

        for (Node neighbor : graph.getOrDefault(start, new ArrayList<>())) {
            List<Node> updatedVisited = new ArrayList<>(visited);
            sum = sum + getPathCountFor(neighbor, updatedVisited);
        }
        return sum;
    }


}