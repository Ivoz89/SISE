import fifteen.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Ivo on 07/04/15.
 */
public class DFSTest {

    @Test
    public void test() {
        State.maxDepth = Integer.MAX_VALUE;
        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        ArrayList<State.Direction> dirOrder = new ArrayList<>();
        dirOrder.add(State.Direction.L);
        dirOrder.add(State.Direction.G);
        dirOrder.add(State.Direction.D);
        dirOrder.add(State.Direction.P);
        ArrayList<Integer> row1 = new ArrayList<>();
        row1.add(0);
        row1.add(1);
        row1.add(2);
        ArrayList<Integer> row2 = new ArrayList<>();
        row2.add(4);
        row2.add(5);
        row2.add(3);
        ArrayList<Integer> row3 = new ArrayList<>();
        row3.add(7);
        row3.add(8);
        row3.add(6);
        board.add(row1);
        board.add(row2);
        board.add(row3);
        State state = new State(board, 0, 0, dirOrder);
        Comparator<State> comp = new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.getDistance() != o2.getDistance()) {
                    return o2.getDistance() - o1.getDistance();
                } else {
                    return (int) (o1.getTimestamp() - o2.getTimestamp());
                }
            }
        };
        State.queue = new PriorityQueue<>(comp);
        State.queue.add(state);
        int counter = 0;
        while (!State.queue.isEmpty()) {
            State removedState = State.queue.remove();
            System.out.println("VISITED=" + State.visitedStates.size());
            System.out.println("QUQUESIZE=" + State.getQueue().size());
            removedState.display();
            if (removedState.isSolution()) {
                System.out.println("SOLUTION!");
                break;
            }
            removedState.visit();
        }
    }
}
