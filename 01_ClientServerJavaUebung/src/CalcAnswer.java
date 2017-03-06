
public class CalcAnswer extends Message
{
    String text="";


    Double erg;

    @Override
    String getMessage() {
        return text;
    }

    @Override
    void setMessage(String text) {
        this.text =text;

    }

    public Double getErg() {
        return erg;
    }

}
