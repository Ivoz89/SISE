package fifteen;

import java.util.ArrayList;

/**
 * Created by Ivo on 09/04/15.
 */
public class Generator {

    private int rowCount;
    private int columnCount;
    private int depth;

    public Generator(int rowCount, int columnCount, int depth) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.depth = depth;
    }

    /**
     * Generuje wszystkie ułożenia planszy od stanu rozwiązanego do maksymalnie $depth ruchów od rozwiązania.
     * @return
     */
    public ArrayList<State> generateStates() {
        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        for(int row=0;row<rowCount;row++) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for(int column=0; column<columnCount;column++) {
                if(row==rowCount-1 && column==columnCount-1) {
                    newRow.add(0);
                } else {
                    newRow.add(row*columnCount+column+1);
                }
            }
            board.add(newRow);
        }
        State initialState = new State(board,rowCount-1,columnCount-1,Main.computeDirections("LGPD"));
        Solver solver = new Solver(initialState, Solver.Algorithm.BFS,depth,Integer.MAX_VALUE,Integer.MAX_VALUE,true);
        solver.run();
        ArrayList<State> generatedStates = new ArrayList<>(Solver.addedStates);
        for(State state : generatedStates) {
            state.setParent(null);
            state.setDistance(0);
            state.setTimestamp(0);
            state.setSource(null);
        }
        return generatedStates;
    }


}
