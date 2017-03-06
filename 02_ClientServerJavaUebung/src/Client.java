
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{

    public static void main(String[] args) throws IOException {

        CalcMsg msg= new CalcMsg();
        CalcAnswer msgAns= new CalcAnswer();
        Socket s=null;
        boolean ipCorrect=false;
        while(!ipCorrect) {
        String serverAddr = JOptionPane.showInputDialog(
                "Enter Host adress:\n");//IP-Adresse des Hosts wird eingegeben. Default:127.0.0.1

            try {
                s = new Socket(serverAddr, 9090);//Server auf Port 9090 wird kontaktiert
                ipCorrect=true;
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null,
                        "Falsche IP-Adresse",
                        "Error-Client",
                        JOptionPane.ERROR_MESSAGE);

            }catch ( ConnectException ex)
            {
                JOptionPane.showMessageDialog(null,
                        "Die Verbindung kann nicht aufgebaut werden (Ist der Server gestartet?)",
                        "Error-Client",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));//Kommunikationskanal wird initialisiert
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String username = JOptionPane.showInputDialog(
                "Enter username:\n");
        String calc = JOptionPane.showInputDialog(
                "Enter calculation (please split the single characters by spaces):\n");//Die Rechenoperation wird eingegeben, getrennt mit leerzeichen


        try {
            msg.setMessage(calc + " "+username); //Rechenoperation + username wird der Message-klasse übergeben
            if(msg.getY()==0 && msg.getZeichen()=='/')//Falls durch 0 dividiert wird, wird  abgebrochen
            {
                throw new NullPointerException() ;
            }
        }catch (ArrayIndexOutOfBoundsException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Message ist ungültig",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Der Vorgang wurde abgebrochen",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
        }
        catch (NullPointerException ex)
        {
            JOptionPane.showMessageDialog(null,
                "Division durch 0 ist nicht definiert!",
                "Error-Client",
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        }
        out.println(msg.getMessage());//Rechenoperation wird dem Server geschikt
        out.flush();

        String answer = input.readLine();//Ergebnis wird von Server geholt
        msgAns.setMessage(answer);



        if(!msgAns.getMessage().equals("ACCESS NOT GRANTED"))
        {
            double erg = Double.parseDouble(msgAns.getMessage());
            JOptionPane.showMessageDialog(null, "Ergebnis: " + erg);//Ergebnis wird Ausgegeben
        }
        else {
            JOptionPane.showMessageDialog(null, msgAns.getMessage());
        }
        // Resourcen werden freigegeben
        out.close();
        input.close();
        s.close();
        System.exit(0);
    }
}


