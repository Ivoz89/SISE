package fifteen;

import java.util.*;

/**
 * Created by Ivo on 07/04/15.
 */
public class Main {

    //TODO losowanie kolejności
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
                default:
                    break;
            }
        }
        if(arg.startsWith("R") || arg.startsWith("r")) {
            Collections.shuffle(argList);
        }
        return argList;
    }

    public static void main(String[] args) {
        Solver.Algorithm algorithm = null;
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
            algorithm = Solver.Algorithm.BFS;
            dirOrder = computeDirections(args[1]);
        } else if (args[0].equals("-d") || args[0].equals("--dfs") || args[0].equals("-i") || args[0].equals("--idfs")) {
            idfs = args[0].equals("-i") || args[0].equals("--idfs");
            algorithm = idfs ? Solver.Algorithm.IDFS : Solver.Algorithm.DFS;
            dirOrder = computeDirections(args[1]);
        } else if (args[0].equals("-a") || args[0].equals("--a")) {
            algorithm = Solver.Algorithm.MANHATTAN;
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
        Solver solver = new Solver(initialState, algorithm, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, false);
        State finalState = solver.run();
        solver.displaySolution(finalState);
        solver.displaySolvingProcess(finalState);
        System.out.println(finalState.getDistance());
        while (finalState.getParent() != null) {
            System.out.printf(finalState.getSource().name());
            finalState = finalState.getParent();
        }
        System.out.println();
    }
}
