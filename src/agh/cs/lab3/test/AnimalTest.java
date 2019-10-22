package agh.cs.lab3.test;

import agh.cs.lab3.main.Animal;
import org.junit.jupiter.api.Test;

import static agh.cs.lab2.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    void move(){
        Animal dog=new Animal();
        dog.move(RIGHT);
        assertEquals( dog.toString(), "Wschód(2,2)" );
        dog.move(LEFT);
        assertEquals( dog.toString(), "Północ(2,2)" );
        dog.move(FORWARD);
        assertEquals( dog.toString(), "Północ(2,3)" );
        dog.move(BACKWARD);
        assertEquals( dog.toString(), "Północ(2,2)" );
        for (int i=0; i<5; i++)
            dog.move(FORWARD);
        assertEquals(dog.toString(), "Północ(2,4)" );
        for (int i=0; i<8; i++)
            dog.move(BACKWARD);
        assertEquals(dog.toString(), "Północ(2,0)" );
    }
    @Test
    void tooString(){
        Animal dog = new Animal();
        assertEquals(dog.toString(), "Północ(2,2)");
        dog.move(RIGHT);
        assertEquals(dog.toString(), "Wschód(2,2)");
    }
}
