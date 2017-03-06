
import java.io.File;


public class ClientMsg extends Message {
    String text="";
    Integer fileSize=0;

    public Integer getFileSize() {
        return fileSize;
    }

    public String getText() {
        return text;
    }

    @Override

    String getMessage() {
        return text;
    }

    @Override
    void setMessage(String text) {

        String[] array = text.split(" ");
        this.text= array[0];
        fileSize = Integer.parseInt(array[1]);
    }

}
