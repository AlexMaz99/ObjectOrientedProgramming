import agh.cs.lab2.MoveDirection;
import agh.cs.lab3.OptionsParsers;
import org.junit.jupiter.api.Test;

import static agh.cs.lab2.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionsParsersTest {
    @Test
    void parse(){
        String[] words = {"r", "forward", "b", "k", "l", "right", "hello","f", "backward", "left", "animal"};
        MoveDirection[] directions = {RIGHT, FORWARD, BACKWARD, LEFT, RIGHT, FORWARD, BACKWARD, LEFT};
        MoveDirection[] directions1 = OptionsParsers.parse(words);
        for (int i=0; i<directions1.length; i++)
            assertEquals(directions[i], directions1[i]);

    }
}
