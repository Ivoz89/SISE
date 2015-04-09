package fifteen;

import java.util.*;
import com.google.common.collect.*;
/**
 * Created by Ivo on 08/04/15.
 */
public class Solver {

    public enum Algorithm {
        DFS, BFS, IDFS, MANHATTAN
    }

    private static HashMap<Algorithm, Comparator<State>> algorithms;
    public static MinMaxPriorityQueue<State> queue; //kolejka priorytetowa
    public static HashSet<State> addedStates; //dodane stany
    public static LinkedList<State> addedStatesList; //zapamiętuje kolejność dodawania stanów do hashsetu
    public State initialState;
    public Algorithm algorithm; //algorytm rozwiązywania
    public int maxDepth; //maksymalna głębokość drzewa w którym poszukiwane jest rozwiązanie
    public int queueSize;
    public static int hashSetSize; //maksymalny rozmiar hashsetu
    public int visitedCounter; //licznik odwiedzonych stanów
    public boolean generatorMode;

    /**
     *
     * @param initialState początkowy stan planszy
     * @param algorithm algorytm rozwiązywania
     * @param maxDepth maksymalna głębokość przeszukiwania
     * @param queueSize rozmiar kolejki priorytetowej
     * @param hashSetSize rozmiar hashsetu
     * @param generatorMode true jeśli nie szukamy rozwiązania
     */
    public Solver(State initialState, Algorithm algorithm, int maxDepth, int queueSize, int hashSetSize, boolean generatorMode) {
        this.initialState = initialState;
        this.algorithm = algorithm;
        this.maxDepth = maxDepth;
        this.queueSize = queueSize;
        this.hashSetSize = hashSetSize;
        this.generatorMode = generatorMode;
        addedStatesList = new LinkedList<>();
    }

    private void reset() {
        Solver.queue = MinMaxPriorityQueue.orderedBy(algorithms.get(algorithm)).maximumSize(queueSize).create();
        Solver.addedStates = new HashSet<>();
        addedStatesList = new LinkedList<>();
        visitedCounter = 0;
        addVisitedState(initialState);
        queue.add(initialState);
        State.maxDepth=maxDepth;
    }

    /**
     * Uruchamia algorytm, zwraca rozwiązanie (po parencie finalnego stanu można odtworzyć rozwiązanie)
     *
     * @return finalny stan, null jeśli brak
     */
    public State run() {
        State removedState = null;
        State finalState = null;
        if (algorithm == Algorithm.IDFS) {
            return runIDFS();
        } else {
            reset();
            while (!Solver.queue.isEmpty()) {
                removedState = Solver.queue.remove();
                if (removedState.isSolution() && !generatorMode) {
                    finalState = removedState;
                    break;
                }
                addVisitedState(removedState); //Oznaczenie stanu jako odwiedzonego
                removedState.visit();
                visitedCounter++;
            }
            return finalState;
        }
    }

    private State runIDFS() {
        State removedState = null;
        State finalState = null;
        int depth = 0;
        boolean solutionFound = false;
        while (!solutionFound) {
            reset();
            depth++;
            if (depth > maxDepth) {
                return null;
            }
            State.maxDepth = depth;
            while (!Solver.queue.isEmpty()) {
                removedState = Solver.queue.remove();
                if (removedState.isSolution() && !generatorMode) {
                    solutionFound = true;
                    finalState = removedState;
                    break;
                }
                addVisitedState(removedState); //Oznaczenie stanu jako odwiedzony
                removedState.visit();
                visitedCounter++;
            }
        }
        return finalState;
    }

    /**
     * Wyświetla proces rozwiązywania
     *
     * @param finalState
     */
    public void displaySolvingProcess(State finalState) {
        ArrayList<State> solution = new ArrayList<>();
        initialState.display();
        while (finalState!=null) {
            solution.add(finalState);
            finalState = finalState.getParent();
        }
        ListIterator<State> it = solution.listIterator(solution.size()-1);
        while (true) {
            it.previous().display();
            if(!it.hasPrevious()) {
                break;
            }
        }
    }

    /**
     * Wyświetla rozwiązanie
     *
     * @param finalState
     */
    public void displaySolution(State finalState) {
        ArrayList<String> solution = new ArrayList<>();
        while (finalState.getParent() != null) {
            solution.add(finalState.getSource().name());
            finalState = finalState.getParent();
        }
        ListIterator<String> it = solution.listIterator(solution.size()-1);
        while (it.hasPrevious()) {
            System.out.printf(it.previous());
        }
    }

    /**
     * Kontroluje rozmiar hashSetu stanów odwiedzonych. Przy przekroczeniu limitu usuwa najstarszy dodany stan
     *
     * @param state
     */
    public static void addVisitedState(State state) {
        addedStatesList.add(state);
        Solver.addedStates.add(state);
        while (Solver.addedStates.size() > hashSetSize) {
            State stateToBeRemoved = addedStatesList.removeFirst();
            Solver.addedStates.remove(stateToBeRemoved);
        }
    }


    static {
        algorithms = new HashMap<>();
        algorithms.put(Algorithm.DFS, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.getDistance() != o2.getDistance()) {
                    return o2.getDistance() - o1.getDistance();
                } else {
                    return (int) (o1.getTimestamp() - o2.getTimestamp());
                }
            }
        });
        algorithms.put(Algorithm.IDFS, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.getDistance() != o2.getDistance()) {
                    return o2.getDistance() - o1.getDistance();
                } else {
                    return (int) (o1.getTimestamp() - o2.getTimestamp());
                }
            }
        });
        algorithms.put(Algorithm.BFS, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.getDistance() != o2.getDistance()) {
                    return o1.getDistance() - o2.getDistance();
                } else {
                    return (int) (o1.getTimestamp() - o2.getTimestamp());
                }
            }
        });
        algorithms.put(Algorithm.MANHATTAN, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.getDistance() != o2.getDistance()) {
                    return o1.getManValue() - o2.getManValue();
                } else {
                    return (int) (o1.getTimestamp() - o2.getTimestamp());
                }
            }
        });
    }
}
