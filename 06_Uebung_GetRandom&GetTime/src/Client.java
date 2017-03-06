import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {
    public static void main(String[] args) throws IOException {

        ClientMsg clientMsg= new ClientMsg();
        ServerMsg serverMsg= new ServerMsg();
        Socket s=null;

        boolean ipCorrect=false;
        try {
            s = new Socket("127.0.0.1", 9090);
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


        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String question = JOptionPane.showInputDialog(
                "Sequentiell oder Paralell? (1=Paralell,2=Sequentiell):\n");

        try {
            serverMsg.setMessage(question);// Die Entscheidung des Cients wird der Message-Klasse übergeben
            out.println(serverMsg.getMessage());// Message wird verschikt
            out.flush();

            String answer= input.readLine();
            System.out.println(answer);
            clientMsg.setMessage(answer); // Ergebnis Array wird aufgeteilt
            System.out.println("Zufällige Zahl: "+clientMsg.getRandomValue()+" "+clientMsg.getTimeValue());
            //Ressourcen werden Freigegeben
            out.close();
            input.close();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Message ist ungültig",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
        catch ( IOException ex) {
            ex.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
        finally {
            s.close();
        }


        System.exit(0);
    }
}


