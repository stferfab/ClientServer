
public class ServerMsg extends Message {

    String text;
    @Override
    String getMessage() {
        return text;
    }


    @Override
    void setMessage(String text) {
        this.text = text;
    }
}
