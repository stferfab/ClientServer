import java.util.Date;
import java.util.Random;


public class MyThread extends Thread {
    private String answer;
    @Override
    public void run() {// service wird ausgeführt
            Date date = new Date(2017, 03, 04);
            long diff = date.getTime();
            answer = "Datum: " + date + " Secunden seit 01.01.1970: " + diff;

    }

    public String getAnswer() {
        return answer;
    }
}
