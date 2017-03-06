
import java.util.Random;


public class MyThreadRandom extends Thread {
     private int randomInt;
    @Override
    public void run() {// service wird ausgeführt

            Random random= new Random();
           randomInt= random.nextInt(1000)+1;
        }

    public int getRandomInt() {
        return randomInt;
    }
}

