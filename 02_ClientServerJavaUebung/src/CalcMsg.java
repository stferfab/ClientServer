
public class CalcMsg extends Message {

    private int x;
    private int y;
    String username ="";



    private char zeichen;
    @Override
    String getMessage() {
        String text = x+" "+zeichen+" "+y+" "+username;
        return text;
    }

    @Override
    void setMessage(String text)
    {
        String[] array=text.split(" ");// Rechenoperation wird gegebenermaßen aufgeteilt
        x= Integer.parseInt(array[0]);
        y= Integer.parseInt(array[2]);
        zeichen= array[1].charAt(0);
        username = array[3];

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getZeichen() {
        return zeichen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
