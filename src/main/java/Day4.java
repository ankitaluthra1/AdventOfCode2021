import utils.MyFileReader;

import java.util.ArrayList;
import java.util.List;

public class Day4 {

    class TicketNumber {
        int value;
        boolean marked;

        TicketNumber(int value) {
            this.value = value;
            this.marked = false;
        }

        boolean isMarked() {
            return marked;
        }

        boolean mark(int number) {
            if (value == number)
                marked = true;
            return marked;
        }
    }

    class Ticket {
        TicketNumber[][] numbers;
        private boolean won;

        Ticket(int length) {
            numbers = new TicketNumber[length][length];
        }

        void addLine(String line, int index) {
            String[] lineArray = splitWithoutExtraSpaces(line);
            for (int i = 0; i < lineArray.length; i++) {
                String trim = lineArray[i].trim();
                numbers[index][i] = new TicketNumber(Integer.parseInt(trim));
            }
        }

        private String[] splitWithoutExtraSpaces(String line) {
            String[] lineArray = line.split(" ");
            String[] numberArray = new String[numbers.length];
            int index = 0;
            for (int i = 0; i < lineArray.length; i++) {
                if (!lineArray[i].isBlank()) {
                    numberArray[index] = lineArray[i];
                    index++;
                }
            }
            return numberArray;
        }

        public boolean play(int number) {
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers.length; j++) {
                    boolean marked = numbers[i][j].mark(number);
                    if (hasWon(i, j, marked))
                        return true;
                }
            }
            return false;
        }

        private boolean hasWon(int row, int col, boolean marked) {
            if (marked) {
                boolean columnMarked = true;
                boolean rowMarked = true;
                for (int i = 0; i < numbers.length; i++) {
                    if (!numbers[i][col].isMarked()) {
                        columnMarked = false;
                        break;
                    }
                }
                for (int i = 0; i < numbers.length; i++) {
                    if (!numbers[row][i].isMarked()) {
                        rowMarked = false;
                        break;
                    }
                }
                return columnMarked || rowMarked;
            }
            return false;
        }

        public int getRemainingNumberSum() {
            int sum = 0;
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers.length; j++) {
                    if (!numbers[i][j].isMarked()) {
                        sum = sum + numbers[i][j].value;
                    }
                }
            }
            return sum;
        }

        public void markWon() {
            this.won = true;
        }

        public boolean isMarkedWon() {
            return this.won;
        }
    }

    public static void main(String[] args) {

        List<String> lines = MyFileReader.getInput("day-4-input");
        Day4 day4 = new Day4();
        day4.firstFinalScore(lines);
    }

    void firstFinalScore(List<String> inputLines) {
        String[] numbersCalled = inputLines.get(0).split(",");
        int index = 1;
        List<Ticket> allTickets = new ArrayList<>();
        while (true) {
            index++;
            if (index >= inputLines.size())
                break;
            Ticket ticket = new Ticket(5);
            for (int i = 0; i < 5; i++) {
                ticket.addLine(inputLines.get(index).trim(), i);
                index++;
            }
            allTickets.add(ticket);
        }
        System.out.println(getWinningScore(numbersCalled, allTickets));
        System.out.println(getLastScore(numbersCalled, allTickets));
    }

    private long getWinningScore(String[] numbersCalled, List<Ticket> allTickets) {

        for (String numberString : numbersCalled) {
            int number = Integer.parseInt(numberString);
            for (Ticket ticket : allTickets) {
                boolean hasWon = ticket.play(number);
                if (hasWon) {
                    return ticket.getRemainingNumberSum() * number;
                }
            }
        }
        throw new IllegalArgumentException("No winner found");
    }

    private long getLastScore(String[] numbersCalled, List<Ticket> allTickets) {
        long lastScore = -1;
        for (String numberString : numbersCalled) {
            int number = Integer.parseInt(numberString);
            for (Ticket ticket : allTickets) {
                if (!ticket.isMarkedWon()) {
                    boolean hasWon = ticket.play(number);
                    if (hasWon) {
                        lastScore = ticket.getRemainingNumberSum() * number;
                        ticket.markWon();
                    }
                }
            }
        }
        return lastScore;
    }

}
