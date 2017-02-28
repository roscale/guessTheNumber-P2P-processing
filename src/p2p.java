import P2P.Connection;
import processing.core.PApplet;

/**
 * Created by roscale on 2/26/17.
 */
public class p2p extends PApplet {

    Connection network;

    boolean guessing;
    int drawnNumber;


    public void settings()
    {
        size(800, 800);
    }

    public void setup()
    {
    }

    public void draw()
    {
        background(0);

        if (network == null)
            return;

        for (Object obj : network.receiveObjects())
            parse(obj);
    }

    public void keyPressed()
    {
        if (key == 's')
        {
            network = new Connection(this, 12345);
            guessing = false;
            drawnNumber = floor(random(20));
            println("Try to guess this number... " + drawnNumber);
        }
        else if (key == 'c')
        {
            network = new Connection(this, "192.168.1.2", 12345);
            guessing = true;

            // Begin the loop
            Number toSend = new Number(floor(random(20)));
            println("Is it " + toSend.value + " ?");

            network.sendObject(toSend);
        }
    }

    void parse(Object obj)
    {
        if (!guessing)
        {
            Number number = (Number) obj;

            if (number.value == drawnNumber)
            {
                guessing = true;

                network.sendObject(new Bool(true));
                println("You guessed the number " + number.value + " !!");

                delay(1000);

                // Begin the loop
                Number toSend = new Number(floor(random(20)));
                println("Is it " + toSend.value + " ?");

                network.sendObject(toSend);

            }
            else
            {
                println("It's not " + number.value + ".");

                network.sendObject(new Bool(false));
            }
        }

        else if (guessing)
        {
            Bool response = (Bool) obj;

            if (response.value == false)
            {
                delay(1000);

                Number toSend = new Number(floor(random(20)));
                println("Is it " + toSend.value + " ?");

                network.sendObject(toSend);
            }
            else
            {
                println("I guessed the number !!");

                drawnNumber = floor(random(20));
                println("Guess this number... " + drawnNumber);
                guessing = false;
            }
        }
    }
}
