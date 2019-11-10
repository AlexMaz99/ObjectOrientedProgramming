import agh.cs.lab2.MoveDirection;
import agh.cs.lab3.OptionsParsers;
import org.junit.jupiter.api.Test;
import static agh.cs.lab2.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsParsersTest {
    @Test
    void parse(){
        String[] words = {"r", "forward", "b", "k", "l", "right", "hello","f", "backward", "left", "animal"};
        assertThrows (IllegalArgumentException.class, ()-> {
            MoveDirection[] directions1 = OptionsParsers.parse(words);
        });

        String[] words2 = {"r", "forward", "b", "l", "right","f", "backward", "left"};
        MoveDirection[] directions = {RIGHT, FORWARD, BACKWARD, LEFT, RIGHT, FORWARD, BACKWARD, LEFT};
        MoveDirection[] directions2 = OptionsParsers.parse(words2);
        for (int i=0; i<directions2.length; i++)
            assertEquals(directions[i], directions2[i]);

    }
}
