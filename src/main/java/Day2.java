import java.util.ArrayList;
import java.util.List;

enum Direction{
    forward,up,down
}

class Instruction{
    Direction type;
    int value;

    public Instruction(String type, int value) {
        this.type = Direction.valueOf(type);
        this.value = value;
    }

    public boolean isUpward() {
        return type.equals(Direction.up);
    }

    public boolean isDownward() {
        return type.equals(Direction.down);
    }

    public boolean isForward() {
        return type.equals(Direction.forward);
    }
}

class Position{
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position getNewPosition(Instruction instruction){
        if (instruction.isUpward()){
            return new Position(x, y-instruction.value);
        }
        if (instruction.isDownward()){
            return new Position(x, y+instruction.value);
        }
        if (instruction.isForward()){
            return new Position(x+instruction.value, y);
        }
        throw new IllegalArgumentException("Incorrect instruction");
    }
}

class PositionWithAim{
    int x;
    int y;
    int aim;

    public PositionWithAim(int x, int y, int aim) {
        this.x = x;
        this.y = y;
        this.aim = aim;
    }

    public PositionWithAim getNewPosition(Instruction instruction){
        if (instruction.isUpward()){
            return new PositionWithAim(x, y,aim-instruction.value);
        }
        if (instruction.isDownward()){
            return new PositionWithAim(x, y,aim+instruction.value);
        }
        if (instruction.isForward()){
            return new PositionWithAim(x+instruction.value, y + (aim * instruction.value),aim);
        }
        throw new IllegalArgumentException("Incorrect instruction");
    }
}

public class Day2 {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        List<Instruction> instructions = new ArrayList<>();
        List<String> input = MyFileReader.getInput("day-2-input");
        for (String current : input){
            String direction = current.split(" ")[0];
            String value = current.split(" ")[1];
            instructions.add(new Instruction(direction, Integer.parseInt(value)));
        }

        Position finalPosition = day2.getFinalPosition(instructions);

        System.out.println(finalPosition.x * finalPosition.y);

        PositionWithAim finalPosition2 = day2.getFinalPositionWithAim(instructions);

        System.out.println(finalPosition2.x * finalPosition2.y);
    }

    private Position getFinalPosition(List<Instruction> instructions) {
        Position finalPosition = new Position(0,0);
        for (Instruction currentInstruction: instructions){
            finalPosition = finalPosition.getNewPosition(currentInstruction);
        }
        return finalPosition;
    }

    private PositionWithAim getFinalPositionWithAim(List<Instruction> instructions) {
        PositionWithAim finalPosition = new PositionWithAim(0,0, 0);
        for (Instruction currentInstruction: instructions){
            finalPosition = finalPosition.getNewPosition(currentInstruction);
        }
        return finalPosition;
    }

}
