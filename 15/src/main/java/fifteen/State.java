package fifteen;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by Ivo on 21/03/15.
 */
public class State {


    public enum Direction {
        L, D, P, G
    }

    public static int maxDepth;
    public static Queue<State> queue;
    public static HashSet<State> visitedStates = new HashSet<>(); //odwiedzone stany
    private State parent;
    private Direction source; //ruch wykonany aby znaleźć się w tym stanie
    private int zeroColumn; //pozycja zera
    private int zeroRow; //pozycja zera
    private int distance; //dystans od stanu początkowego
    private long timestamp;
    final ArrayList<ArrayList<Integer>> board; // lewy górny róg planszy to (0,0)
    final ArrayList<Direction> dirOrder;

    /**
     * Dla pierwszego stanu
     *
     * @param board
     */
    public State(ArrayList<ArrayList<Integer>> board, int zeroRow, int zeroColumn, ArrayList<Direction> dirOrder) {
        this.dirOrder = dirOrder;
        this.zeroColumn = zeroColumn;
        this.zeroRow = zeroRow;
        distance = 0;
        this.board = board;
        this.timestamp = System.currentTimeMillis();
    }

    public State(ArrayList<ArrayList<Integer>> board, State parent, Direction source, int zeroRow, int zeroColumn, int distance, ArrayList<Direction> dirOrder) {
        this.board = board;
        this.dirOrder = dirOrder;
        this.parent = parent;
        this.source = source;
        this.zeroColumn = zeroColumn;
        this.zeroRow = zeroRow;
        this.distance = distance;
        this.timestamp = System.currentTimeMillis();
    }

    public void visit() {
        visitedStates.add(this);
        for (Direction dir : dirOrder) {
            switch (dir) {
                case L:
                    if (zeroColumn != board.get(0).size() - 1) { //możliwy ruch w lewo
                        ArrayList<ArrayList<Integer>> newBoard = new ArrayList<>();
                        board.forEach(l -> newBoard.add(new ArrayList<>(l)));
                        newBoard.get(zeroRow).set(zeroColumn, newBoard.get(zeroRow).get(zeroColumn + 1));
                        newBoard.get(zeroRow).set(zeroColumn + 1, 0);
                        State neighbour = new State(newBoard, this, Direction.L, zeroRow, zeroColumn + 1, distance + 1, dirOrder);
                        if (!visitedStates.contains(neighbour) && distance + 1 < maxDepth) {
                            queue.add(neighbour);
                        }
                    }
                    break;
                case P:
                    if (zeroColumn != 0) { //możliwy ruch w prawo
                        ArrayList<ArrayList<Integer>> newBoard = new ArrayList<>();
                        board.forEach(l -> newBoard.add(new ArrayList<>(l)));
                        newBoard.get(zeroRow).set(zeroColumn, newBoard.get(zeroRow).get(zeroColumn - 1));
                        newBoard.get(zeroRow).set(zeroColumn - 1, 0);
                        State neighbour = new State(newBoard, this, Direction.P, zeroRow, zeroColumn - 1, distance + 1, dirOrder);
                        if (!visitedStates.contains(neighbour) && distance + 1 < maxDepth) {
                            queue.add(neighbour);
                        }
                    }
                    break;
                case G:
                    if (zeroRow != board.size() - 1) { //możliwy ruch w górę
                        ArrayList<ArrayList<Integer>> newBoard = new ArrayList<>();
                        board.forEach(l -> newBoard.add(new ArrayList<>(l)));
                        newBoard.get(zeroRow).set(zeroColumn, newBoard.get(zeroRow + 1).get(zeroColumn));
                        newBoard.get(zeroRow + 1).set(zeroColumn, 0);
                        State neighbour = new State(newBoard, this, Direction.G, zeroRow + 1, zeroColumn, distance + 1, dirOrder);
                        if (!visitedStates.contains(neighbour) && distance + 1 < maxDepth) {
                            queue.add(neighbour);
                        }
                    }
                    break;
                case D:
                    if (zeroRow != 0) { //możliwy ruch w dół
                        ArrayList<ArrayList<Integer>> newBoard = new ArrayList<>();
                        board.forEach(l -> newBoard.add(new ArrayList<>(l)));
                        newBoard.get(zeroRow).set(zeroColumn, newBoard.get(zeroRow - 1).get(zeroColumn));
                        newBoard.get(zeroRow - 1).set(zeroColumn, 0);
                        State neighbour = new State(newBoard, this, Direction.D, zeroRow - 1, zeroColumn, distance + 1, dirOrder);
                        if (!visitedStates.contains(neighbour) && distance + 1 < maxDepth) {
                            queue.add(neighbour);
                        }
                    }
                    break;
            }
        }
    }

    public boolean isSolution() {
        int counter = 1;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                if (i == board.size() - 1 && j == board.get(0).size() - 1 && board.get(i).get(j) == 0) {
                    return true;
                } else if (board.get(i).get(j) != counter) {
                    return false;
                }
                counter++;
            }
        }
        return false;
    }

    public int getManValue() {
        int counter = 1;
        int sum = 0;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j) != counter) {
                    int currentValue = board.get(i).get(j);
                    if (currentValue == 0) {
                        sum = sum + Math.abs(board.size() - 1 - i) + Math.abs(board.get(0).size()-1 - j);
                    } else {
                        int finalRow = currentValue / board.get(0).size();
                        int finalColumn = (currentValue - 1) % board.get(0).size();
                        sum = sum + Math.abs(finalRow - i) + Math.abs(finalColumn - j);
                    }
                }
                counter++;
            }
        }
        return sum;
    }

    public void display() {
        System.out.println(this.toString());
        System.out.println("Source:" + source);
        System.out.println("Distance:" + distance);
        board.forEach(l -> System.out.println(l.toString()));
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        State other = (State) obj;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j) != other.board.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(board).
                toHashCode();
    }

    public static Queue<State> getQueue() {
        return queue;
    }

    public State getParent() {
        return parent;
    }

    public Direction getSource() {
        return source;
    }

    public int getZeroColumn() {
        return zeroColumn;
    }

    public int getZeroRow() {
        return zeroRow;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
