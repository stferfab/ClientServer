
public class ClientMsg extends Message {
String text;
String time;
Integer random;
    @Override
    String getMessage() {
        return text;
    }

    @Override
    void setMessage(String text) {

        String[] array=text.split(" / ");
        time= (array[0]);
        random = Integer.parseInt(array[1]);

    }

    public String getTimeValue() {
        return time;
    }

    public Integer getRandomValue() {
        return random;
    }
}
