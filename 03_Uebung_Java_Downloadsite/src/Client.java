import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{

    public static void main(String[] args) throws IOException {

        ClientMsg clientMsg= new ClientMsg();//Variablen werden initializiert

        Socket socket=null;
        BufferedWriter outHtml = null;

        boolean ipCorrect=false;
            try {
                socket = new Socket("127.0.0.1", 9090);
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


        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String link = JOptionPane.showInputDialog(
                "Enter website url ([http/s://]www.example.com):\n");//Url wird eingegeben

        try {
            File file = new File("C:\\Users\\Fabian\\Desktop\\Server-Clinet\\Uebung_Java_Downloadsite\\src\\Files\\file.html"); //neues File wird erstellt mit dem Namen der Website
            outHtml = new BufferedWriter(new FileWriter(file));
            clientMsg.setMessage(link);// Der Url wird der Message-Klasse übergeben
            out.println(clientMsg.getMessage());// Url wird verschikt
            out.flush();

            clientMsg.setMessage(input.readLine()); // Heruntergelandene Website wird gespeichert
            outHtml.write(clientMsg.getMessage()); // Website wird in File reingeschrieben

            //ressourcen werden Freigegeben
            out.close();
            input.close();
        }catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Das System kann den angegebenen Pfad nicht finden",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        catch (ArrayIndexOutOfBoundsException ex)
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
        catch ( IOException ex) {
            ex.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
        finally {
            if ( outHtml != null ) {
                outHtml.close();
            }
            socket.close();
        }


        System.exit(0);
    }
}


