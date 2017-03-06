
public class ServerMsg extends Message {

    public String getText() {
        return text;
    }

    String text;

    public Integer getFileSize() {
        return fileSize;
    }

    Integer fileSize;
    byte[] fileByteArray;
    @Override
    String getMessage() {
        text= text+" "+fileSize;
        return text;
    }


    @Override
    void setMessage(String text) {
        this.text = text;
    }
    void setMessage(Integer fileSize) {

        this.fileSize=fileSize;


    }

}
