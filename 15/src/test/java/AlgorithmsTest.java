import fifteen.Solver;
import fifteen.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Ivo on 09/04/15.
 */
public class AlgorithmsTest {

    State initialState;

    @Before
    public void init() {
        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        ArrayList<Integer> newRow = new ArrayList<>();
        newRow.add(0);
        newRow.add(1);
        newRow.add(2);
        newRow.add(3);
        board.add(newRow);
        newRow = new ArrayList<>();
        newRow.add(5);
        newRow.add(6);
        newRow.add(7);
        newRow.add(4);
        board.add(newRow);
        newRow = new ArrayList<>();
        newRow.add(9);
        newRow.add(10);
        newRow.add(11);
        newRow.add(8);
        board.add(newRow);
        newRow = new ArrayList<>();
        newRow.add(13);
        newRow.add(14);
        newRow.add(15);
        newRow.add(12);
        board.add(newRow);
        ArrayList<State.Direction> dirOrder = new ArrayList<>();
        dirOrder.add(State.Direction.P);
        dirOrder.add(State.Direction.D);
        dirOrder.add(State.Direction.G);
        dirOrder.add(State.Direction.L);
        initialState = new State(board,null,null,0,0,0,dirOrder);
    }

    @Test
    public void dfsTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.DFS,10,Integer.MAX_VALUE,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        System.out.println("DFS solution:");
        finalState.display();
        solver.displaySolution(finalState);
        solver.displaySolvingProcess(finalState);
    }

//    @Test
    public void idfsTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.IDFS,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        System.out.println("IDFS solution:");
        finalState.display();
        solver.displaySolution(finalState);
        solver.displaySolvingProcess(finalState);
    }

//    @Test
    public void bfsTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.BFS,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        System.out.println("BFS solution:");
        finalState.display();
        solver.displaySolution(finalState);
        solver.displaySolvingProcess(finalState);
    }

//    @Test
    public void manhattanTest() {
        System.out.println("MANHATTAN solution:");
        Solver solver = new Solver(initialState, Solver.Algorithm.MANHATTAN,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        finalState.display();
        solver.displaySolution(finalState);
        solver.displaySolvingProcess(finalState);
    }

//    @Test
    public void limitedHashSetTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.BFS,Integer.MAX_VALUE,Integer.MAX_VALUE,4,false);
        State finalState = solver.run();
        assertEquals(4,Solver.addedStates.size());
    }

//    @Test
    public void limitedQueueTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.MANHATTAN,Integer.MAX_VALUE,2,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        assertTrue(Solver.queue.size()<=2);
    }

//    @Test
    public void maxDepthTest() {
        Solver solver = new Solver(initialState, Solver.Algorithm.MANHATTAN,1,Integer.MAX_VALUE,Integer.MAX_VALUE,false);
        State finalState = solver.run();
        assertTrue(finalState==null);
    }
}
