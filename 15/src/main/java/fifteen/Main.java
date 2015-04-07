package fifteen;

import java.io.SyncFailedException;
import java.util.*;

/**
 * Created by Ivo on 07/04/15.
 */
public class Main {

    public static ArrayList<State.Direction> computeDirections(String arg) {
        ArrayList<State.Direction> argList = new ArrayList<>();
        for (char ch : arg.toCharArray()) {
            switch (ch) {
                case 'G':
                case 'g':
                    argList.add(State.Direction.G);
                    break;
                case 'D':
                case 'd':
                    argList.add(State.Direction.D);
                    break;
                case 'L':
                case 'l':
                    argList.add(State.Direction.L);
                    break;
                case 'P':
                case 'p':
                    argList.add(State.Direction.P);
                    break;
            }
        }
        return argList;
    }

    public static void main(String[] args) {
        int zeroStartRow = 0;
        int zeroStartColumn = 0;
        int rowCount;
        int columnCount;
        State initialState = null;
        State.maxDepth = Integer.MAX_VALUE;
        boolean idfs = false;
        ArrayList<State.Direction> dirOrder = null;
        Comparator<State> comp = null;
        if (args[0].equals("-b") || args[0].equals("--bfs")) {
            comp = new Comparator<State>() {
                @Override
                public int compare(State o1, State o2) {
                    if (o1.getDistance() != o2.getDistance()) {
                        return o1.getDistance() - o2.getDistance();
                    } else {
                        return (int) (o1.getTimestamp() - o2.getTimestamp());
                    }
                }
            };
            dirOrder = computeDirections(args[1]);
        } else if (args[0].equals("-d") || args[0].equals("--dfs") || args[0].equals("-i") || args[0].equals("--idfs")) {
            idfs = args[0].equals("-i") || args[0].equals("--idfs");
            comp = new Comparator<State>() {
                @Override
                public int compare(State o1, State o2) {
                    if (o1.getDistance() != o2.getDistance()) {
                        return o2.getDistance() - o1.getDistance();
                    } else {
                        return (int) (o1.getTimestamp() - o2.getTimestamp());
                    }
                }
            };
            dirOrder = computeDirections(args[1]);
        } else if (args[0].equals("-a") || args[0].equals("--a")) {
            comp = new Comparator<State>() {
                @Override
                public int compare(State o1, State o2) {
                    if (o1.getDistance() != o2.getDistance()) {
                        return o1.getManValue() - o2.getManValue();
                    } else {
                        return (int) (o1.getTimestamp() - o2.getTimestamp());
                    }
                }
            };
            dirOrder = computeDirections("GDLP");
        }
        Scanner scanner = new Scanner(System.in);
        rowCount = scanner.nextInt();
        columnCount = scanner.nextInt();
        ArrayList<ArrayList<Integer>> startingBoard = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columnCount; j++) {
                int value = scanner.nextInt();
                row.add(value);
                if (value == 0) {
                    zeroStartColumn = j;
                    zeroStartRow = i;
                }
            }
            startingBoard.add(row);
        }
        initialState = new State(startingBoard, zeroStartRow, zeroStartColumn, dirOrder);
        State removedState = null;
        if (idfs) {
            int maxDepth = 0;
            boolean solutionFound = false;
            while (!solutionFound) {
                State.visitedStates = new HashSet<>();
                State.queue = new PriorityQueue<>(comp);
                State.queue.add(initialState);
                maxDepth++;
                State.maxDepth = maxDepth;
                while (!State.queue.isEmpty()) {
                    removedState = State.queue.remove();
                    if (removedState.isSolution()) {
                        solutionFound=true;
                        break;
                    }
                    removedState.visit();
                }
            }
        } else {
            State.visitedStates = new HashSet<>();
            State.queue = new PriorityQueue<>(comp);
            State.queue.add(initialState);
            while (!State.queue.isEmpty()) {
                removedState = State.queue.remove();
                if (removedState.isSolution()) {
                    break;
                }
                removedState.visit();
            }
        }
        System.out.println(removedState.getDistance());
        Stack<String> solution = new Stack<>();
        while (removedState.getParent() != null) {
            solution.add(removedState.getSource().name());
            removedState = removedState.getParent();
        }
        while(!solution.empty()) {
            System.out.printf(solution.pop());
        }
        System.out.println();
    }
}
