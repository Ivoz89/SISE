import fifteen.Generator;
import fifteen.State;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivo on 09/04/15.
 */
public class GeneratorTest {

    @Test
    public void testGenerator() {
        ArrayList<State> generatedStates = new Generator(4,4,1).generateStates();
        assertEquals(3,generatedStates.size());
        for(State s : generatedStates) {
            s.display();
        }
    }
 }
