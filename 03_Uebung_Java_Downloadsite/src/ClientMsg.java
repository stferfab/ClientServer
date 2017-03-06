import java.io.File;


public class ClientMsg extends Message {
String text;
    @Override
    String getMessage() {
        return text;
    }

    @Override
    void setMessage(String text) { //https wird hinzugefügt falls es nicht vorhanden ist
        if(!text.contains("http://") && !text.contains("https://"))//falls nicht vorhanden wird "https://" hinzugefügt
        {
            this.text= "https://"+text;
        }else
        {
            this.text=text;
        }

    }
}
