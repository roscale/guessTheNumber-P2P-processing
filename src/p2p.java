import processing.core.PApplet;
import processing.core.PVector;
import processing.net.*;

/**
 * Created by roscale on 2/26/17.
 */
public class p2p extends PApplet {
    Network nw;

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

        if (nw == null)
            return;

        String receivedData = nw.receive();
        if (receivedData != null)
            for (String rawPacket : NetworkPacket.separate(receivedData))
                parse(rawPacket);
    }

    public void keyPressed()
    {
        if (key == 's')
        {
            nw = new Network(this, 12345);
            guessing = false;
            drawnNumber = floor(random(20));
            println("Try to guess this number... " + drawnNumber);
        }
        else if (key == 'c')
        {
            nw = new Network(this, "192.168.1.4", 12345);
            guessing = true;

            // Begin the loop
            Number toSend = new Number(floor(random(20)));
            println("Is it " + toSend.value + " ?");
            nw.send(toSend);
        }
    }

    void parse(String rawPacket)
    {
        if (!guessing)
        {
            Number number = Number.decode(rawPacket);

            if (number.value == drawnNumber)
            {
                guessing = true;
                nw.send(new Bool(true));
                println("You guessed the number " + number.value + " !!");

                delay(1000);

                // Begin the loop
                Number toSend = new Number(floor(random(20)));
                println("Is it " + toSend.value + " ?");
                nw.send(toSend);

            }
            else
            {
                println("It's not " + number.value + ".");
                nw.send(new Bool(false));
            }
            return;
        }

        if (guessing)
        {
            Bool response = Bool.decode(rawPacket);

            if (response.value == false)
            {
                delay(1000);

                Number toSend = new Number(floor(random(20)));
                println("Is it " + toSend.value + " ?");
                nw.send(toSend);
            }
            else
            {
                println("I guessed the number !!");

                drawnNumber = floor(random(20));
                println("Guess this number... " + drawnNumber);
                guessing = false;
            }
            return;
        }
    }
}
